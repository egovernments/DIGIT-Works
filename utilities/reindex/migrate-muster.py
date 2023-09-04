import logging
import requests
from kafka import KafkaProducer
from json import dumps

producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                         value_serializer=lambda x: 
                         dumps(x).encode('utf-8'))

wms_search_url = "https://mukta-uat.digit.org/wms/muster/_search"
wms_search_request = {
    "inbox": {
        "moduleSearchCriteria": {
            "tenantId": "od.testing"
        },
        "tenantId": "od.testing",
        "limit": 300,
        "offset": 0
    },
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "2152d873-0c66-4f00-96e2-95cf91487073",
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
        }
        "msgId": "1692786979375|en_IN",
        "plainAccessRequest": {}
    }
}

wms_response = requests.post(wms_search_url, json = wms_search_request, headers = {"Content-Type": "application/json"}).json()


muster_numbers = []
for item in wms_response["items"]:
    muster_numbers.append(item["businessObject"]["musterRollNumber"])

print(len(muster_numbers))

searchRequest = {
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "2152d873-0c66-4f00-96e2-95cf91487073",
        "msgId": "1690971020980|en_IN",
        "plainAccessRequest": {}
    }
}

insertRequest = {
    "RequestInfo": {
        "apiId": "Rainmaker",
        "authToken": "2152d873-0c66-4f00-96e2-95cf91487073",
        "msgId": "1690971020980|en_IN",
        "plainAccessRequest": {}
    },
    "musterRoll": None,
    "workflow":  {
        "action": "",
        "assignees": [],
        "comment": ""
    }
}

count = 0
for i in range(len(muster_numbers)):
    uri = "https://mukta-uat.digit.org/muster-roll/v1/_search?tenantId=od.testing&musterRollNumber="+muster_numbers[i]+"&_=1691032860673"
    resp = requests.post(uri, json = searchRequest, headers = {"Content-Type": "application/json"})
    Response = resp.json()
    print(muster_numbers[i])
    if Response['ResponseInfo'] != None:
        if len(Response['musterRolls']) > 0:
            count = count + len(Response['musterRolls'])
            insertRequest['musterRoll'] = Response['musterRolls'][0]
            future=producer.send('migrate-musterroll',insertRequest)
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

print(count)