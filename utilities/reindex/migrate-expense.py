import logging
import requests
from kafka import KafkaProducer
from json import dumps

producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                         value_serializer=lambda x: 
                         dumps(x).encode('utf-8'))

wms_search_url = "https://mukta-uat.digit.org/wms/expense/_search"
wms_search_request = {
    "inbox": {
        "moduleSearchCriteria": {
            "tenantId": "od.testing",
            "billType": "EXPENSE.SUPERVISION"
        },
        "tenantId": "od.testing",
        "limit": 200,
        "offset": 0
    },
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "96878232-c25e-4b36-95ea-43856aadfd0b",
        "userInfo": {
            "id": 444,
            "uuid": "bff9c3f6-6a25-45c4-b7ae-dddc016598fd",
            "userName": "SMS QA testing",
            "name": "SMS QA testing",
            "mobileNumber": "9043685314",
            "emailId": "",
            "locale": None,
            "type": "EMPLOYEE",
            "roles": [],
            "active": True,
            "tenantId": "pg.citya",
            "permanentCity": None
        },
        "msgId": "1692786979375|en_IN",
        "plainAccessRequest": {}
    }
}

wms_response = requests.post(wms_search_url, json = wms_search_request, headers = {"Content-Type": "application/json"}).json()


bill_numbers = []

for item in wms_response["items"]:
    bill_numbers.append(item["businessObject"]["billNumber"])

print(len(bill_numbers))
print(bill_numbers)
ex_search_url = "https://mukta-uat.digit.org/expense/bill/v1/_search"
ex_search_request = {
    "billCriteria": {
        "tenantId": "od.testing",
        "businessService": "EXPENSE.SUPERVISION",
        "billNumbers": bill_numbers
    },
    "pagination": {
        "limit": 300,
        "offSet": 0,
        "sortBy": "",
        "order": "ASC"
    },
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "96878232-c25e-4b36-95ea-43856aadfd0b",
        "msgId": "1690971020980|en_IN",
        "plainAccessRequest": {}
    }
}

ex_search_response = requests.post(ex_search_url,json = ex_search_request, headers = {"Content-Type": "application/json"}).json()

print(len(ex_search_response["bills"]))

insertBillRequest = {
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "96878232-c25e-4b36-95ea-43856aadfd0b",
        "msgId": "1690971020980|en_IN",
        "plainAccessRequest": {}
    },
    "bill": None,
    "workflow":  {
        "action": "",
        "assignees": [],
        "comment": ""
    }
}
for i in range(len(ex_search_response['bills'])):
    insertBillRequest['bill'] = ex_search_response['bills'][i]
    future=producer.send('migrate-expense-bill',insertBillRequest)
    try:
        record_metadata = future.get(timeout=1)
    except KafkaError:
        # Decide what to do if produce request failed...
        logging.exception("message")
        pass

    # Successful result returns assigned partition and offset
    print (record_metadata.topic)
    print (record_metadata.partition)
    print (record_metadata.offset)


print (len(ex_search_response['bills'])) 