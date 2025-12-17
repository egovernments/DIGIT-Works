import requests
import json
import time
import os
import copy
from elasticsearch import Elasticsearch, helpers
from confluent_kafka import Producer, KafkaException


ES_HOST = os.getenv("ES_HOST_URL")
ES_USERNAME = os.getenv("ES_USERNAME")
ES_PASSWORD = os.getenv("ES_PASSWORD")
TRANSFORMER_TOPIC = os.getenv("TRANSFORMER_PI_KAFKA_TOPIC")
KAFKA_BROKER = os.getenv("KAFKA_BROKER")

PRODUCER_CONFIG = {
    "bootstrap.servers": KAFKA_BROKER,
    'client.id': 'index-transformer-producer',
}

es = Elasticsearch([ES_HOST], http_auth=(ES_USERNAME, ES_PASSWORD), verify_certs=False)
producer = Producer(PRODUCER_CONFIG)

gender_vs_beneficiary_map = {}

# Current time in milliseconds
current_time_millis = int(time.time() * 1000)

# 3 hour ago in milliseconds 
three_hours_ago_millis = current_time_millis - (3 * 60 * 60 * 1000)

print(current_time_millis)
print(three_hours_ago_millis)

def send_to_kafka(producer, topic, message):
    try:
        print(f"Trying to push data to Kafka topic: {topic}")
        producer.produce(topic, message)
        print(f"Pushed data to Kafka topic: {topic}")
    except KafkaException as e:
        print(f"Failed to produce message: {e} in topic {topic}")
    except Exception as e:
        print(f"An error occurred: {e} while trying to push data to topic {topic}")


def extract_data():
    beneficiary_ids = set()
    pi_index_name = os.getenv("ES_PI_INDEX")
    query = {
        "size": 10000, 
            "query": {
                "bool": {
                    "must": [
                        {
                            "range": {
                                "Data.auditDetails.lastModifiedTime": {
                                "gte": three_hours_ago_millis,
                                "lte": current_time_millis
                                }
                            }
                        }
                    ]
                }
            },
            "sort" : [
                {
                    "Data.@timestamp" : "asc"
                }
            ]
    }

    docs = es.search(index=pi_index_name, body=query)["hits"]["hits"]
    print(f"{len(docs)}, docs from ifms pi index")
    pi_data = []
    while len(docs) > 0:
        last_sort_value = docs[-1]["sort"][0]
        print(f"Last sort value: {last_sort_value}")

        for data in docs:
            pi_data.append(data)
            for pi in data["_source"]["Data"]["beneficiaryDetails"]:
                if pi["beneficiaryType"] == "IND":
                    beneficiary_ids.add(pi["beneficiaryId"])


        query_with_search_after = copy.deepcopy(query)
        query_with_search_after["search_after"] = [last_sort_value]
        response = es.search(index=pi_index_name, body=query_with_search_after)
        docs = response["hits"]["hits"]
        print(f"{len(docs)} docs from ifms pi index")
    print(f"{len(pi_data)} objects in pi data")
    create_gender_vs_beneficiary_map(beneficiary_ids)
    
    return pi_data


def transform_data(data):
    ES_TRANSFORMER_PI_INDEX = os.getenv("ES_TRANSFORMER_PI_INDEX")

    for pi in data:
        beneficiary_details = pi["_source"]["Data"]["beneficiaryDetails"]

        for index, details in enumerate(beneficiary_details):
            #Creating a new document for each beneficiaryDetails object
            transformed_data = {
                "Data": {
                    **pi["_source"]["Data"],  #Include all other fields in Data
                    "parentIndexId" : pi["_id"],
                    "beneficiaryDetails": details  #Overwrite beneficiaryDetails with one object at a time
                }
            }

            if "numBeneficiaries" in transformed_data:
                del transformed_data["Data"]["numBeneficiaries"]
            if "grossAmount" in transformed_data:
                del transformed_data["Data"]["grossAmount"]
            if "netAmount" in transformed_data:
                del transformed_data["Data"]["netAmount"]
            if "piStatus" in transformed_data["Data"]:
                del transformed_data["Data"]["piStatus"]

            beneficiary_id = transformed_data["Data"]["beneficiaryDetails"]["beneficiaryId"]

            transformed_data["Data"]["gender"] = gender_vs_beneficiary_map[beneficiary_id] if beneficiary_id in gender_vs_beneficiary_map else None

            # index_id = f"{transformed_data['Data']['id']}{transformed_data['Data']['beneficiaryDetails']['id']}{transformed_data['Data']['beneficiaryDetails']['beneficiaryId']}"
            # index_query = {"index": { "_index" : ES_TRANSFORMER_PI_INDEX ,"_id": index_id}}
            data_object = json.dumps(transformed_data)

            send_to_kafka(producer, TRANSFORMER_TOPIC, data_object)

    producer.flush()


def create_gender_vs_beneficiary_map(beneficiary_ids : set):
    beneficiary_ids_list = list(beneficiary_ids)
    individual_index = os.getenv("ES_INDIVIDUAL_INDEX")
    chunk_size = 10000
    for i in range(0, len(beneficiary_ids_list), chunk_size):
        chunk = beneficiary_ids_list[i:i + chunk_size]
        query = {
            "size" : len(chunk),
            "query" : {
                "terms" : {
                    "Data.id.keyword" : chunk
                }
            },
            "_source": ["Data.id", "Data.gender"]
        }
        # docs = index_batch(url= ES_INDIVIDUAL_SEARCH, query = query).json()["hits"]["hits"]
        docs = es.search(index=individual_index, body=query)["hits"]["hits"]
        for item in docs:
            gender_vs_beneficiary_map[item["_source"]["Data"]["id"]] = item["_source"]["Data"]["gender"] if "gender" in item["_source"]["Data"] else None

    return None

    


def main():
    pi_data = extract_data()

    transform_data(pi_data)


    return None



if __name__ == '__main__':
    main()