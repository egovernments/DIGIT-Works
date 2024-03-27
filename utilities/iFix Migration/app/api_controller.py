from flask import Flask, request, jsonify, current_app
import psycopg2
from psycopg2.extras import Json
import requests  # Import the requests library
from kafka import KafkaProducer, KafkaConsumer
import json
import threading

app = Flask(__name__)

# Replace these with your PostgreSQL database details
DB_HOST = "localhost"
DB_PORT = 5432
DB_NAME = "digit-works"
DB_USER = "postgres"
DB_PASSWORD = "1234"


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


# Function to fetch data from an external API
def fetch_data_from_mdms(request_info):
    api_url = "https://mukta-uat.digit.org/egov-mdms-service/v1/_search"  # Replace with the actual API endpoint
    auth_token = request_info.get("authToken", "")  # Extract authToken from RequestInfo

    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {auth_token}",
    }

    payload = {
        "RequestInfo": request_info,
        "MdmsCriteria": {
            "tenantId": "od",
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
    return codes


def process_pi_data_for_each_tenant(request_info, tenant_id):
    api_url = "https://mukta-uat.digit.org/ifms/pi/v1/_search"
    headers = {
        'Content-Type': 'application/json'
    }
    data = {
        "RequestInfo": request_info,
        "searchCriteria": {"tenantId": "od.testing", "muktaReferenceId": "EP/TE/2023-24/02/23/002774"},
        "pagination": {
            "limit": "10",  # Adjust the limit as needed
            "offSet": "0",
            "sortBy": "",
            "order": "ASC"
        }
    }
    all_data = []

    while True:
        response = requests.post(api_url, json=data, headers=headers)

        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            all_data.extend(response_data.get("paymentInstructions", []))

            # Check if there are more records to fetch
            if len(response_data.get("paymentInstructions", [])) < int(data["pagination"]["limit"]):
                break

            # Update the offset for the next request
            data["pagination"]["offSet"] = str(int(data["pagination"]["offSet"]) + int(data["pagination"]["limit"]))

        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
            break

    return all_data


# API endpoint to receive data and insert into the database
def get_individuals(request_info, individual_ids, tenant_id):
    url = 'https://mukta-uat.digit.org/individual/v1/_search'
    headers = {
        'authority': 'mukta-uat.digit.org',
        'accept': 'application/json, text/plain, */*',
        'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
        'content-type': 'application/json',
        # Add other headers as needed
    }
    params = {
        'tenantId': tenant_id,
        'offset': 0,
        'limit': 100,
        '_': 1708590843633,
    }

    data = {
        'Individual': {
            'id': individual_ids
        },
        'RequestInfo': request_info
    }
    if len(individual_ids) == 0:
        return {}
    response = requests.post(url, headers=headers, json=data, params=params)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the JSON response
        response_data = response.json()
        # Create a map of id and individual
        individual_id_map = {item["id"]: item for item in response_data['Individual']}
        # Process the response_data as needed
        return individual_id_map
    else:
        # Handle the error
        print(f"Error: {response.status_code} - {response.text}")


def get_organizations(request_info, organization_ids, tenant_id):
    url = 'https://mukta-uat.digit.org/org-services/organisation/v1/_search'
    headers = {
        'authority': 'mukta-uat.digit.org',
        'accept': 'application/json, text/plain, */*',
        'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
        'content-type': 'application/json',
        # Add other headers as needed
    }
    params = {
        'tenantId': tenant_id,
        'offset': 0,
        'limit': 100,
        '_': 1708590843633,
    }

    data = {
        'SearchCriteria': {
            'id': organization_ids,
            "tenantId": tenant_id,
        },
        'RequestInfo': request_info
    }
    if len(organization_ids) == 0:
        return {}
    response = requests.post(url, headers=headers, json=data, params=params)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        # Parse the JSON response
        response_data = response.json()
        # Create a map of id and individual
        organization_id_map = {item["id"]: item for item in response_data['organisations']}
        # Process the response_data as needed
        return organization_id_map
    else:
        # Handle the error
        print(f"Error: {response.status_code} - {response.text}")


def extract_lineitem_paid_amount(response):
    lineitem_paid_amount_map = {}

    # Iterate over payments
    for payment in response.get("payments", []):
        # Iterate over bills
        for bill in payment.get("bills", []):
            # Iterate over bill details
            for bill_detail in bill.get("billDetails", []):
                # Iterate over payable line items
                for line_item in bill_detail.get("payableLineItems", []):
                    lineitem_id = line_item.get("lineItemId")
                    paid_amount = line_item.get("paidAmount")
                    # Add to map
                    lineitem_paid_amount_map[lineitem_id] = paid_amount

    return lineitem_paid_amount_map


def get_payment(request_info, payment_number, tenant_id):
    url = 'https://mukta-uat.digit.org/expense/payment/v1/_search'
    headers = {
        'authority': 'mukta-uat.digit.org',
        'accept': 'application/json, text/plain, */*',
        'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
        'content-type': 'application/json',
        # Add other headers as needed
    }
    params = {
        'tenantId': tenant_id,
        'offset': 0,
        'limit': 100,
        '_': 1708590843633,
    }

    data = {
        "paymentCriteria": {
            "tenantId": tenant_id,
            "paymentNumbers": [payment_number]
        },
        'RequestInfo': request_info
    }
    if len(payment_number) == 0:
        return {}
    response = requests.post(url, headers=headers, json=data, params=params)

    # Check if the request was successful (status code 200)
    if response.status_code == 202:
        # Parse the JSON response
        response_data = response.json()
        # Create a map of id and individual
        return extract_lineitem_paid_amount(response_data)
    else:
        # Handle the error
        print(f"Error: {response.status_code} - {response.text}")


def fetch_bank_account(request_info, bank_account_id, tenant_id):
    url = 'https://mukta-uat.digit.org/bankaccount-service/bankaccount/v1/_search?_=1708666856757'

    headers = {
        'authority': 'mukta-uat.digit.org',
        'accept': 'application/json, text/plain, */*',
        'accept-language': 'en-GB,en-US;q=0.9,en;q=0.8',
        'content-type': 'application/json',
        # Add other headers as needed
    }

    data = {
        'bankAccountDetails': {
            'tenantId': tenant_id,
            'referenceId': [bank_account_id]
        },
        'RequestInfo': request_info
    }

    response = requests.post(url, headers=headers, json=data)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        bank_account_data = response.json()
        if len(bank_account_data['bankAccounts']) > 0:
            return bank_account_data['bankAccounts'][0]['id']
        else:
            return None
    else:
        print(f"Error: {response.status_code}")
        print(response.text)
        return None


def decrypt_account_number(account_number):
    api_url = 'http://localhost:8090/egov-enc-service/crypto/v1/_decrypt'
    headers = {
        'Content-Type': 'application/json',
    }
    data = [
        account_number
    ]
    response = requests.post(api_url, json=data, headers=headers)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        response = response.json()
        return response[0]
    else:
        # If not successful, print the error status code and response text
        print(f"Error: {response.status_code}\n{response.text}")
        return None


def get_account_code(request_info, response_data, pi_created_time):
    sorted_logs = sorted(response_data["AuditLogs"], key=lambda x: x["changeDate"])

    # Find the first log with changeDate greater than the given time
    matching_log_eg_bank_account = next((log for log in sorted_logs if log["changeDate"] < pi_created_time and log[
        "entityName"] == 'eg_bank_account_detail'), None)
    matching_log_eg_bank_branch_identifier = next((log for log in sorted_logs if
                                                   log["changeDate"] < pi_created_time and log[
                                                       "entityName"] == 'eg_bank_branch_identifier'), None)

    # Extract the required information if a matching log is found
    if matching_log_eg_bank_account:
        account_number = matching_log_eg_bank_account["keyValueMap"]["accountNumber"]
    if matching_log_eg_bank_branch_identifier:
        branch_code = matching_log_eg_bank_branch_identifier["keyValueMap"]["code"]
    if account_number is not None:
        account_number = decrypt_account_number(account_number)
        return account_number + '@' + branch_code
    else:
        print("No log found with changeDate greater than the given time.")


def fetch_bank_account_from_audit_logs(request_info, bank_account_detail_id, tenant_id, pi_created_time):
    account_id = fetch_bank_account(request_info, bank_account_detail_id, tenant_id)
    api_url = 'http://localhost:8280/audit-service/log/v1/_search'
    headers = {
        'Content-Type': 'application/json',
    }

    params = {
        'offset': 0,
        'limit': 100,
        'tenantId': tenant_id,
        'objectId': account_id,
    }

    data = {
        "RequestInfo": request_info
    }

    response = requests.post(api_url, params=params, json=data, headers=headers)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        response = response.json()
        if len(response['AuditLogs']) > 0:
            return get_account_code(request_info, response, pi_created_time)
        else:
            return None
    else:
        # If not successful, print the error status code and response text
        print(f"Error: {response.status_code}\n{response.text}")
        return None


def create_disbursement_for_individual(beneficiary, individual, request_info, pi_created_time):
    print('Creating disbursement for individual: ', individual['id'])
    bank_account = fetch_bank_account_from_audit_logs(request_info, beneficiary['beneficiaryId'],
                                                      beneficiary["tenantId"], pi_created_time)
    payment = get_payment(request_info, beneficiary['muktaReferenceId'], beneficiary["tenantId"])
    disbursements = []
    status_map = {
        "Payment Pending": "FAILED",
        "Payment Initiated": "INITIATED",
        "Payment In Process": "INPROCESS",
        "Payment Successful": "SUCCESSFUL",
        "Payment Failed": "FAILED"
    }

    for entry in beneficiary['benfLineItems']:
        amount = payment[entry['lineItemId']]
        disbursement = {
            "administration_code": None,
            "function_code": None,
            "recipient_segment_code": None,
            "economic_segment_code": None,
            "source_of_fund_code": None,
            "target_segment_code": None,
            "currency_code": "INR",
            "locale_code": "en_IN",
            "status": {
                "status_code": status_map[beneficiary['paymentStatus']],
                "status_message": status_map[beneficiary['paymentStatus']]
            },
            "id": entry['id'],
            "target_id": entry['lineItemId'],
            "location_code": beneficiary['tenantId'],
            "transaction_id": beneficiary['beneficiaryNumber'],
            "parent_id": None,
            "disbursement_date": None,
            "sanction_id": None,
            "disbursements": None,
            "account_code": bank_account,
            "net_amount": amount,
            "gross_amount": amount,
            "individual": {
                "pin": individual['address'][0]['pincode'],
                "name": individual['name']['givenName'],
                "gender": individual['gender'],
                "address": individual['address'][0]['city'],
                "email": individual['email'],
                "phone": individual['mobileNumber'],
            },
            "program_code": "PG/2023-24/000310",
            "additional_details": None,
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        disbursements.append(disbursement)

    return disbursements


def create_disbursement_for_organization(beneficiary, organization, request_info, pi_created_time):
    print('Creating disbursement for organization: ', organization['id'])
    bank_account = fetch_bank_account_from_audit_logs(request_info, beneficiary['beneficiaryId'],
                                                      beneficiary["tenantId"], pi_created_time)
    payment = get_payment(request_info, beneficiary['muktaReferenceId'], beneficiary["tenantId"])
    disbursements = []
    status_map = {
        "Payment Pending": "FAILED",
        "Payment Initiated": "INITIATED",
        "Payment In Process": "INPROCESS",
        "Payment Successful": "SUCCESSFUL",
        "Payment Failed": "FAILED"
    }

    for entry in beneficiary['benfLineItems']:
        amount = payment[entry['lineItemId']]
        disbursement = {
            "administration_code": None,
            "function_code": None,
            "recipient_segment_code": None,
            "economic_segment_code": None,
            "source_of_fund_code": None,
            "target_segment_code": None,
            "currency_code": "INR",
            "locale_code": "en_IN",
            "status": {
                "status_code": status_map[beneficiary['paymentStatus']],
                "status_message": status_map[beneficiary['paymentStatus']]
            },
            "id": entry['id'],
            "target_id": entry['lineItemId'],
            "location_code": beneficiary['tenantId'],
            "transaction_id": beneficiary['beneficiaryNumber'],
            "parent_id": None,
            "disbursement_date": None,
            "sanction_id": None,
            "disbursements": None,
            "account_code": bank_account,
            "net_amount": amount,
            "gross_amount": amount,
            "individual": {
                "pin": organization['orgAddress'][0]['pincode'],
                "name": organization['name'],
                "gender": None,
                "address": organization['orgAddress'][0]['city'],
                "email": organization['contactDetails'][0]['contactEmail'],
                "phone": organization['contactDetails'][0]['contactMobileNumber'],
            },
            "program_code": "PG/2023-24/000310",
            "additional_details": None,
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        disbursements.append(disbursement)

    return disbursements


def create_disbursement_for_department(beneficiary, request_info, pi_created_time):
    print('Creating disbursement for department: ', beneficiary['beneficiaryId'])
    bank_account = fetch_bank_account_from_audit_logs(request_info, beneficiary['beneficiaryId'],
                                                      beneficiary["tenantId"], pi_created_time)
    payment = get_payment(request_info, beneficiary['muktaReferenceId'], beneficiary["tenantId"])
    disbursements = []
    status_map = {
        "Payment Pending": "FAILED",
        "Payment Initiated": "INITIATED",
        "Payment In Process": "INPROCESS",
        "Payment Successful": "SUCCESSFUL",
        "Payment Failed": "FAILED"
    }

    for entry in beneficiary['benfLineItems']:
        amount = payment[entry['lineItemId']]
        disbursement = {
            "administration_code": None,
            "function_code": None,
            "recipient_segment_code": None,
            "economic_segment_code": None,
            "source_of_fund_code": None,
            "target_segment_code": None,
            "currency_code": "INR",
            "locale_code": "en_IN",
            "status": {
                "status_code": status_map[beneficiary['paymentStatus']],
                "status_message": status_map[beneficiary['paymentStatus']]
            },
            "id": entry['id'],
            "target_id": entry['lineItemId'],
            "location_code": beneficiary['tenantId'],
            "transaction_id": beneficiary['beneficiaryNumber'],
            "parent_id": None,
            "disbursement_date": None,
            "sanction_id": None,
            "disbursements": None,
            "account_code": bank_account,
            "net_amount": amount,
            "gross_amount": amount,
            "individual": {
                "pin": None,
                "name": beneficiary['beneficiaryId'],
                "gender": None,
                "address": None,
                "email": None,
                "phone": "0000000000",
            },
            "program_code": "PG/2023-24/000310",
            "additional_details": None,
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        disbursements.append(disbursement)

    return disbursements


def process_pi_data(request_info, entry):
    beneficiary_details = entry["beneficiaryDetails"]
    pi_created_time = entry["auditDetails"]["createdTime"]
    individual_ids = []
    organization_ids = []
    for beneficiary in beneficiary_details:
        if beneficiary["beneficiaryType"] == "IND":
            individual_ids.append(beneficiary["beneficiaryId"])
        if beneficiary["beneficiaryType"] == "ORG":
            organization_ids.append(beneficiary["beneficiaryId"])
    individual_beneficiary_id_map = get_individuals(request_info, individual_ids, entry['tenantId'])
    organizations_beneficiary_id_map = get_organizations(request_info, organization_ids, entry['tenantId'])
    disbursements = []
    for beneficiary in beneficiary_details:
        if beneficiary["beneficiaryType"] == "IND":
            individual = individual_beneficiary_id_map[beneficiary["beneficiaryId"]]
            disbursement = create_disbursement_for_individual(beneficiary, individual, request_info, pi_created_time)

        if beneficiary["beneficiaryType"] == "ORG":
            organization = organizations_beneficiary_id_map[beneficiary["beneficiaryId"]]
            disbursement = create_disbursement_for_organization(beneficiary, organization, request_info,
                                                                pi_created_time)

        if beneficiary["beneficiaryType"] == "DEPT":
            disbursement = create_disbursement_for_department(beneficiary, request_info, pi_created_time)

        for d in disbursement:
            disbursements.append(d)
    status_map = {
        "INITIATED": "INITIATED",
        "IN PROCESS": "INPROCESS",
        "SUCCESSFUL": "SUCCESSFUL",
        "FAILED": "FAILED",
        "PARTIAL": "PARTIAL",
        "APPROVED": "INPROCESS",
        "COMPLETED": "COMPLETED"
    }
    main_disbursement = {
        "id": entry['id'],
        "disbursements": disbursements,
        "location_code": entry['tenantId'],
        "transaction_id": entry['jitBillNo'],
        "gross_amount": entry['grossAmount'],
        "sanction_id": None,
        "account_code": None,
        "individual": {
            "pin": None,
            "name": None,
            "gender": None,
            "address": None,
            "email": None,
            "phone": None,
        },
        "net_amount": entry['netAmount'],
        "target_id": entry['muktaReferenceId'],
        "currency_code": "INR",
        "locale_code": "en_IN",
        "administration_code": None,
        "function_code": None,
        "recipient_segment_code": None,
        "economic_segment_code": None,
        "source_of_fund_code": None,
        "target_segment_code": None,
        "status": {
            "status_code": status_map[entry['piStatus']],
            "status_message": entry['piErrorResp']
        },
        "program_code": "PG/2023-24/000310",
        "additional_details": {},
        "audit_details": {
            "createdBy": entry['auditDetails']['createdBy'],
            "lastModifiedBy": entry['auditDetails']['lastModifiedBy'],
            "createdTime": entry['auditDetails']['createdTime'],
            "lastModifiedTime": entry['auditDetails']['lastModifiedTime']
        }
    }
    return main_disbursement


def get_program_disburse_values(disbursement, parent_id=None):
    values_eg_program_disburse = [
        disbursement['id'],
        disbursement['location_code'],
        disbursement['program_code'],
        disbursement['target_id'],
        disbursement['transaction_id'],
        parent_id,  # Assign parent_id here
        disbursement['sanction_id'],
        disbursement['account_code'],
        Json(disbursement['individual']),
        disbursement['net_amount'],
        disbursement['gross_amount'],
        disbursement['status']['status_code'],
        disbursement['status']['status_message'],
        Json(disbursement['additional_details']),
        disbursement['audit_details']['createdTime'],
        disbursement['audit_details']['createdBy'],
        disbursement['audit_details']['lastModifiedTime'],
        disbursement['audit_details']['lastModifiedBy']
    ]
    return values_eg_program_disburse


def get_program_message_codes_values(disbursement):
    values_eg_program_message_codes = [
        disbursement['id'],
        disbursement['location_code'],
        None,
        None,
        disbursement['function_code'],
        disbursement['administration_code'],
        disbursement['program_code'],
        disbursement['recipient_segment_code'],
        disbursement['economic_segment_code'],
        disbursement['source_of_fund_code'],
        disbursement['target_segment_code'],
        disbursement['audit_details']['createdTime'],
        disbursement['audit_details']['createdBy'],
        disbursement['audit_details']['lastModifiedTime'],
        disbursement['audit_details']['lastModifiedBy']
    ]
    return values_eg_program_message_codes

def get_mukta_ifms_disburse_values(disbursement, parent_id=None):
    values_eg_mukta_ifms_disburse = [
        disbursement['id'],
        disbursement['program_code'],
        disbursement['target_id'],
        parent_id,  # Assign parent_id here
        disbursement['account_code'],
        disbursement['status']['status_code'],
        disbursement['status']['status_message'],
        Json(disbursement['individual']),
        disbursement['net_amount'],
        disbursement['gross_amount'],
        disbursement['audit_details']['createdTime'],
        disbursement['audit_details']['createdBy'],
        disbursement['audit_details']['lastModifiedTime'],
        disbursement['audit_details']['lastModifiedBy']
    ]
    return values_eg_mukta_ifms_disburse

def get_mukta_ifms_message_codes_values(message):
    values_eg_mukta_ifms_message_codes = [
        message['id'],
        message['location_code'],
        message['id'],
        message['function_code'],
        message['administration_code'],
        message['program_code'],
        message['recipient_segment_code'],
        message['economic_segment_code'],
        message['source_of_fund_code'],
        message['target_segment_code'],
        Json(message['additional_details']),
        message['audit_details']['createdTime'],
        message['audit_details']['createdBy'],
        message['audit_details']['lastModifiedTime'],
        message['audit_details']['lastModifiedBy']
    ]
    return values_eg_mukta_ifms_message_codes
def push_data_to_db(disbursement, cursor):
    query_eg_program_disburse = """
        INSERT INTO eg_program_disburse 
        (id, location_code, program_code, target_id, transaction_id, parent_id, sanction_id, account_code, individual, net_amount, 
        gross_amount, status, status_message, additional_details, created_time, created_by, last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    query_eg_mukta_ifms_disburse = """
        INSERT INTO eg_mukta_ifms_disburse 
        (id, program_code, target_id, parent_id, account_code, status, status_message, individual, net_amount, 
        gross_amount, created_time, created_by, last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    values_eg_program_disburse = get_program_disburse_values(disbursement)
    values_eg_mukta_ifms_disburse = get_mukta_ifms_disburse_values(disbursement)
    cursor.execute(query_eg_program_disburse, values_eg_program_disburse)
    cursor.execute(query_eg_mukta_ifms_disburse, values_eg_mukta_ifms_disburse)
    # Insert data into eg_program_message_codes table
    query_eg_program_message_codes = """
        INSERT INTO eg_program_message_codes 
        (id, location_code, type, reference_id, function_code, administration_code, program_code, recipient_segment_code, 
        economic_segment_code, source_of_fund_code, target_segment_code, created_time, created_by, 
        last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    query_eg_mukta_ifms_message_codes = """
        INSERT INTO eg_mukta_ifms_message_codes 
        (id, location_code, parent_id, function_code, administration_code, program_code, recipient_segment_code, 
        economic_segment_code, source_of_fund_code, target_segment_code, additional_details, created_time, 
        created_by, last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    values_eg_program_message_codes = get_program_message_codes_values(disbursement)
    values_eg_mukta_ifms_message_codes = get_mukta_ifms_message_codes_values(disbursement)
    cursor.execute(query_eg_program_message_codes, values_eg_program_message_codes)
    cursor.execute(query_eg_mukta_ifms_message_codes, values_eg_mukta_ifms_message_codes)
    for disburse in disbursement['disbursements']:
        values_eg_program_disburse = get_program_disburse_values(disburse, disbursement['id'])
        values_eg_mukta_ifms_disburse = get_mukta_ifms_disburse_values(disburse, disbursement['id'])
        cursor.execute(query_eg_program_disburse, values_eg_program_disburse)
        cursor.execute(query_eg_mukta_ifms_disburse, values_eg_mukta_ifms_disburse)
        # Insert data into eg_program_message_codes table
        values_eg_program_message_codes = get_program_message_codes_values(disburse)
        values_eg_mukta_ifms_message_codes = get_mukta_ifms_message_codes_values(disburse)
        cursor.execute(query_eg_program_message_codes, values_eg_program_message_codes)
        cursor.execute(query_eg_mukta_ifms_message_codes, values_eg_mukta_ifms_message_codes)


def enrich_disbursement_from_pi_and_insert(request_info, all_data, cursor):
    for entry in all_data:
        disbursement = process_pi_data(request_info, entry)
        push_data_to_db(disbursement, cursor)
        print('Migrated data for entry: ', entry['jitBillNo'])


@app.route('/api/data', methods=['POST'])
def insert_data(request_info):
    connection = None
    cursor = None

    try:
        # Connect to PostgreSQL
        connection = connect_to_database()

        # Create a cursor object
        cursor = connection.cursor()

        # Fetch data from the external API using RequestInfo
        request_info = request_info['RequestInfo']
        tenant_ids = fetch_data_from_mdms(request_info)
        # Process for each tenant id
        # for tenant_id in tenant_ids:
        #     print('Processing for tenant: ', tenant_id)
        all_data = process_pi_data_for_each_tenant(request_info, "od.testing")
        enrich_disbursement_from_pi_and_insert(request_info, all_data, cursor)

        # Commit the transaction
        connection.commit()

        # Use Flask's current_app to access the application context
        with current_app.app_context():
            return jsonify({"message": "Data inserted successfully"}), 201

    except Exception as e:
        # Use Flask's current_app to access the application context
        with current_app.app_context():
            return jsonify({"error": str(e)}), 500

    finally:
        # Close the cursor and connection in the finally block
        if cursor:
            cursor.close()

        if connection:
            connection.close()


def publish_to_kafka(data):
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    topic = 'migrate-data'

    # Convert data to JSON before publishing
    json_data = json.dumps(data).encode('utf-8')

    # Publish data to the Kafka topic
    producer.send(topic, json_data)
    producer.flush()

    # Close the producer
    producer.close()


@app.route('/api/push-data', methods=['POST'])
def push_data_to_kafka():
    try:
        # Extract data from the incoming request
        request_data = request.get_json()

        # Publish data to Kafka topic
        publish_to_kafka(request_data)

        return jsonify({"message": "Data pushed successfully"}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/api/consume-data', methods=['POST'])
def start_kafka_consumer():
    try:
        # Start Kafka consumer in a separate thread
        kafka_consumer_thread = threading.Thread(target=consume_from_kafka)
        kafka_consumer_thread.start()

        return jsonify({"message": "Kafka consumer started successfully"}), 200

    except Exception as e:
        return jsonify({"error": str(e)}), 500


def consume_from_kafka():
    consumer = KafkaConsumer('migrate-data', bootstrap_servers='localhost:9092',
                             group_id='ifix-migration')

    for message in consumer:
        # Decode and process the message payload
        payload = json.loads(message.value.decode('utf-8'))

        # Call the insert_data function with the payload
        insert_data(payload)
