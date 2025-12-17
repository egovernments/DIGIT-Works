import os

import psycopg2
import requests
from dotenv import load_dotenv
from flask import Flask, request, jsonify
from kafka import KafkaProducer
import json
from psycopg2 import connect

app = Flask(__name__)
load_dotenv(".env")

DB_HOST = os.getenv('DB_HOST')
DB_PORT = os.getenv('DB_PORT')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')


# Function to establish a database connection
def connect_to_database():
    return psycopg2.connect(
        host=DB_HOST, port=DB_PORT, database=DB_NAME, user=DB_USER, password=DB_PASSWORD
    )


def extract_codes(api_response):
    # Assuming the structure of the response is as provided
    tenants = api_response.get("MdmsRes", {}).get("tenant", {}).get("tenants", [])

    # Extracting 'code' from each tenant
    codes = [tenant.get("code") for tenant in tenants]

    return codes

def fetch_tenants_from_mdms(request_info):
    mdms_host = os.getenv('MDMS_HOST')
    mdms_search = os.getenv('MDMS_SEARCH')
    state_level_tenant_id = os.getenv('STATE_LEVEL_TENANT_ID')
    api_url = f"{mdms_host}{mdms_search}"
    auth_token = request_info.get("authToken", "")  # Extract authToken from RequestInfo

    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {auth_token}",
    }

    payload = {
        "RequestInfo": request_info,
        "MdmsCriteria": {
            "tenantId": state_level_tenant_id,
            "moduleDetails": [
                {
                    "moduleName": "tenant",
                    "masterDetails": [
                        {"name": "tenants"}
                    ],
                }
            ],
        },
    }

    response = requests.post(api_url, json=payload, headers=headers)
    data = response.json()
    codes = extract_codes(data)
    codes = [code for code in codes if code != state_level_tenant_id]
    return codes


def fetch_estimate(estimate_id, tenant_id, request_info):
    print(f"Fetching estimate for tenant_id: {tenant_id} and estimate_id: {estimate_id}")
    estimate_host = os.getenv('ESTIMATE_HOST')
    estimate_search = os.getenv('ESTIMATE_SEARCH')

    api_url = f"{estimate_host}{estimate_search}"

    headers = {
        'Content-Type': 'application/json'
    }

    data = {
        "RequestInfo": request_info
    }
    query_params = {
        "tenantId": tenant_id,
        "ids": estimate_id
    }

    response = requests.post(api_url, json=data, headers=headers, params=query_params)

    if response.status_code == 200:
        estimate = response.json()
        if len(estimate['estimates']) > 0:
            return estimate['estimates'][0]
        else:
            print(f"No estimate found for tenant_id: {tenant_id} and estimate_id: {estimate_id}")
            return None

    if response.status_code != 200:
        print(f"Failed to fetch estimate for tenant_id: {tenant_id} and estimate_id: {estimate_id}")



def fetch_work_order_details(work_order_number, tenant_id, request_info):
    print(f"Fetching work order details for tenant_id: {tenant_id} and work_order_number: {work_order_number}")
    contract_host = os.getenv('CONTRACT_HOST')
    contract_search = os.getenv('CONTRACT_SEARCH')

    api_url = f"{contract_host}{contract_search}"

    headers = {
        'Content-Type': 'application/json'
    }

    data = {
        "RequestInfo": request_info,
        "contractNumber": work_order_number,
        "tenantId": tenant_id
    }

    response = requests.post(api_url, json=data, headers=headers)

    if response.status_code == 200:
        work_order = response.json()
        if len(work_order['contracts']) > 0:
            return work_order['contracts'][0]
        else:
            print(f"No work order found for tenant_id: {tenant_id} and work_order_number: {work_order_number}")
            return None

    if response.status_code != 200:
        print(f"Failed to fetch work order details for tenant_id: {tenant_id} and work_order_number: {work_order_number}")


def fetch_project_details(project_id, tenant_id, request_info):
    print(f"Fetching project details for tenant_id: {tenant_id} and project_id: {project_id}")
    project_host = os.getenv('PROJECT_HOST')
    project_search = os.getenv('PROJECT_SEARCH')

    api_url = f"{project_host}{project_search}"

    headers = {
        'Content-Type': 'application/json'
    }

    data = {
        "RequestInfo": request_info,
        "Projects": [
        {
            "tenantId": tenant_id,
            "id": project_id
        }
    ]
    }

    query_params = {
        "tenantId": tenant_id,
        "limit": 1,
        "offset": 0
    }

    response = requests.post(api_url, json=data, headers=headers, params=query_params)

    if response.status_code == 200:
        project = response.json()
        if len(project['Project']) > 0:
            return project['Project'][0]
        else:
            print(f"No project found for tenant_id: {tenant_id} and project_id: {project_id}")
            return None

    if response.status_code != 200:
        print(f"Failed to fetch project details for tenant_id: {tenant_id} and project_id: {project_id}")


def process_bill(bill, tenant_id, request_info):
    print(f"Processing bill for tenant_id: {tenant_id} and bill_number: {bill['billNumber']}")
    work_order_number = bill['referenceId'].split('_')[0]
    contract = fetch_work_order_details(work_order_number, tenant_id, request_info)
    if contract:
        estimate_id = contract['lineItems'][0]['estimateId']
        estimate = fetch_estimate(estimate_id, tenant_id, request_info)
        project_id = estimate['projectId']
        project = fetch_project_details(project_id, tenant_id, request_info)
        # Ensure additional_details is not None; if it's None, initialize as an empty dictionary
        additional_details = bill.get('additionalDetails')

        if additional_details is None:
            additional_details = {}

        # Now safely assign the values
        additional_details['projectId'] = project.get('projectNumber', 'N/A')
        additional_details['projectName'] = project.get('name', 'N/A')
        additional_details['ward'] = project.get('address', {}).get('boundary', 'N/A')
        additional_details['projectDescription'] = project.get('description', 'N/A')
        additional_details['projectCreatedDate'] = project.get('auditDetails', {}).get('createdTime', 'N/A')

        # Ensure the modified additional_details is saved back to the bill dictionary
        bill['additionalDetails'] = additional_details
        bill_request = {
            "RequestInfo": request_info,
            "bill": bill
        }
        publish_to_kafka(bill_request, os.getenv('EXPENSE_BILL_INDEX_TOPIC'))
        publish_to_kafka(bill_request, os.getenv('EXPENSE_BILL_UPDATE_TOPIC'))



def publish_to_kafka(data, topic):
    producer = KafkaProducer(bootstrap_servers=os.getenv('KAFKA_SERVER'))

    # Convert data to JSON before publishing
    json_data = json.dumps(data).encode('utf-8')

    # Publish data to the Kafka topic
    producer.send(topic, json_data)
    producer.flush()

    # Close the producer
    producer.close()


def check_if_done(cursor, param):
    query = """SELECT migrated FROM BillMigrationStatus WHERE id = %s"""
    cursor.execute(query, (param,))
    result = cursor.fetchone()
    if result:
        return True
    else:
        return False


def process_bill_for_each_tenant(request_info, tenant_id, bill_numbers,cursor,connection):
    if bill_numbers is None:
        return
    expense_host = os.getenv('EXPENSE_HOST')
    expense_search = os.getenv('EXPENSE_BILL_SEARCH')

    api_url = f"{expense_host}{expense_search}"

    headers = {
        'Content-Type': 'application/json'
    }
    for bill_number in bill_numbers:
        if check_if_done(cursor, bill_number['bill_number']) is True:
            continue
        data = {
            "RequestInfo": request_info,
            "billCriteria": {
                "tenantId": tenant_id,
            "businessService": bill_number['business_service'],
        "billNumbers": [bill_number['bill_number']]
        },
        "pagination": {
            "limit": 10,
            "offSet": 0,
            "sortBy": "string"
        }
        }

        response = requests.post(api_url, json=data, headers=headers)

        if response.status_code == 202:
            bill = response.json()
            if len(bill['bills']) > 0:
                process_bill(bill['bills'][0], tenant_id, request_info)
                cursor.execute('''INSERT INTO BillMigrationStatus (id, migrated) VALUES (%s, %s)''', (bill_number['bill_number'], True))
                connection.commit()

        if response.status_code != 202:
            print(f"Failed to fetch bill for tenant_id: {tenant_id} and bill_number: {bill_number['bill_number']}")

def fetch_bill(request_info, tenant_id, bill_number):
    print(f"Fetching bill for tenant_id: {tenant_id} and bill_number: {bill_number}")
    expense_host = os.getenv('EXPENSE_HOST')
    expense_search = os.getenv('EXPENSE_BILL_SEARCH')

    api_url = f"{expense_host}{expense_search}"

    headers = {
        'Content-Type': 'application/json'
    }
    business_service = "EXPENSE.PURCHASE"
    if bill_number[0] == 'W':
        business_service = "EXPENSE.WAGES"

    if bill_number[0] == 'S':
        business_service = "EXPENSE.SUPERVISION"

    data = {
            "RequestInfo": request_info,
            "billCriteria": {
                "tenantId": tenant_id,
                "businessService": business_service,
                "billNumbers": [bill_number]
            },
            "pagination": {
                "limit": 10,
                "offSet": 0,
                "sortBy": "string"
            }
    }

    response = requests.post(api_url, json=data, headers=headers)

    if response.status_code == 202:
        bill = response.json()
        if len(bill['bills']) > 0:
            return bill['bills'][0]
        else:
            print(f"No bill found for tenant_id: {tenant_id} and bill_number: {bill_number}")
            return None

    if response.status_code != 202:
        print(f"Failed to fetch bill for tenant_id: {tenant_id} and bill_number: {bill_number}")
        return None



def get_bill_numbers_for_tenant(tenant_id, cursor):
    # Define the query to fetch bill numbers for the given tenant_id
    query_to_fetch_bill_numbers = """SELECT businessservice, billnumber FROM eg_expense_bill WHERE tenantid = %s"""

    # Execute the query with the provided tenant_id
    cursor.execute(query_to_fetch_bill_numbers, (tenant_id,))

    # Fetch all the results from the executed query
    result = cursor.fetchall()

    # If there are no results, return None
    if not result:
        return None

    # Iterate over the results and print or collect them
    bill_numbers = []
    for row in result:
        business_service, bill_number = row
        bill_numbers.append({'business_service': business_service, 'bill_number': bill_number})

    return bill_numbers


def get_dummy_bill_numbers():
    return [{'business_service': 'EXPENSE.SUPERVISION', 'bill_number': 'SB/2023-24/001137'}]


@app.route("/app/migrate", methods=["POST"])
def migrate():
    with app.app_context():
        connection = None
        cursor = None
        try:
            connection = connect_to_database()
            cursor = connection.cursor()
            cursor.execute('''CREATE TABLE IF NOT EXISTS BillMigrationStatus (
                                               id TEXT PRIMARY KEY,
                                               migrated BOOLEAN
                                           )''')
            connection.commit()
            data = request.get_json()
            request_info = data['RequestInfo']
            tenant_ids = fetch_tenants_from_mdms(request_info)

            print(tenant_ids)
            for tenant_id in tenant_ids:
                bill_numbers = get_bill_numbers_for_tenant(tenant_id, cursor)
                # bill_numbers = get_dummy_bill_numbers()
                process_bill_for_each_tenant(request_info, tenant_id,bill_numbers,cursor, connection)
            return jsonify({"message": "Migration successful!"}), 200
        except Exception as e:
            return {"error": str(e)}, 500


def process_pis(pi, tenant_id, request_info):
    print(f"Processing PI for tenant_id: {tenant_id} and PI id: {pi['id']}")
    # bill_number = pi['additionalDetails']['billNumber']
    # if bill_number is not None:
    #     bill = fetch_bill(request_info,tenant_id,bill_number[0])
    #     if bill:
    #         additional_details = bill['additionalDetails']
    #         if additional_details is not None:
    #             pi['additionalDetails']['projectId'] = additional_details['projectId']
    #             pi['additionalDetails']['projectCreatedDate'] = additional_details['projectCreatedDate']

    pi_request = {
        "RequestInfo": request_info,
        "paymentInstruction": pi
    }

    publish_to_kafka(pi_request, os.getenv('MUKTA_PI_INDEX_TOPIC'))


def check_if_done_pi(pi, cursor, connection):
    query = """SELECT migrated FROM PIMigrationStatus WHERE id = %s"""
    cursor.execute(query, (pi['id'],))
    result = cursor.fetchone()
    if result:
        return True
    else:
        return False


def process_pi_for_tenant(request_info, tenant_id, connection, cursor):
    mukta_host = os.getenv('MUKTA_ADAPTER_HOST')
    mukta_search = os.getenv('MUKTA_ADAPTER_SEARCH')

    api_url = f"{mukta_host}{mukta_search}"

    headers = {
        'Content-Type': 'application/json'
    }

    data = {
        "RequestInfo": request_info,
        "criteria": {
        "tenantId": tenant_id
        },
        "pagination": {
            "limit": 100,
            "offSet": 0,
            "sortBy": "createdtime",
            "order": "ASC"
        }
    }

    while True:
        response = requests.post(api_url, json=data, headers=headers)

        if response.status_code == 200:
            response_data = response.json()
            for pi in response_data.get('paymentInstructions', []):
                # check_if_done_pi(pi, cursor, connection)
                if check_if_done_pi(pi, cursor, connection) is True:
                    continue
                process_pis(pi, tenant_id, request_info)
                cursor.execute('''INSERT INTO PIMigrationStatus (id, migrated) VALUES (%s, %s)''', (pi['id'], True))
            if len(response_data.get('paymentInstructions', [])) < int(data["pagination"]["limit"]):
                break

                # Update the offset for the next request
            data["pagination"]["offSet"] = str(int(data["pagination"]["offSet"]) + int(data["pagination"]["limit"]))

        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
            data["pagination"]["offSet"] = str(int(data["pagination"]["offSet"]) + 1)
            # break




@app.route("/app/pi/migrate", methods=["POST"])
def migrate_pi():
    with app.app_context():
        connection = None
        cursor = None
        try:
            connection = connect_to_database()
            cursor = connection.cursor()
            cursor.execute('''CREATE TABLE IF NOT EXISTS PIMigrationStatus (
                                                           id TEXT PRIMARY KEY,
                                                           migrated BOOLEAN
                                                       )''')
            connection.commit()
            data = request.get_json()
            request_info = data['RequestInfo']
            tenant_ids = fetch_tenants_from_mdms(request_info)

            print(tenant_ids)
            for tenant_id in tenant_ids:
                process_pi_for_tenant(request_info, tenant_id, connection, cursor)
            return jsonify({"message": "Migration successful!"}), 200
        except Exception as e:
            return {"error": str(e)}, 500



