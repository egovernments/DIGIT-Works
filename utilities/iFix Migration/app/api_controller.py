import copy

from flask import Flask, request, jsonify
import psycopg2
from psycopg2.extras import Json
import requests  # Import the requests library
from kafka import KafkaProducer, KafkaConsumer
import json
import threading
from dotenv import load_dotenv
import os
import logging

app = Flask(__name__)
load_dotenv('.env')

logging.basicConfig(filename='app.log', level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
# Replace these with your PostgreSQL database details
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


# Function to fetch data from an external API
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
    return codes


def fetch_data_from_mdms(request_info, tenant_id):
    mdms_host = os.getenv('DEV_MDMS_HOST')
    mdms_search = os.getenv('MDMS_SEARCH')
    api_url = f"{mdms_host}{mdms_search}"
    auth_token = request_info.get("authToken", "")  # Extract authToken from RequestInfo

    headers = {
        "Content-Type": "application/json",
        "Authorization": f"Bearer {auth_token}",
    }

    payload = {
        "RequestInfo": request_info,
        "MdmsCriteria": {
            "tenantId": tenant_id,
            "moduleDetails": [
                {
                    "moduleName": "segment-codes",
                    "masterDetails": [
                        {"name": "AdministrativeCodes"},
                        {"name": "FunctionCodes"},
                        {"name": "EconomicSegmentCodes"},
                        {"name": "SourceOfFundCodes"},
                        {"name": "TargetSegmentCodes"},
                        {"name": "RecipientSegmentCodes"},
                        {"name": "GeographicSegmentCodes"},
                    ],
                },
                {
                    "moduleName": "ifms",
                    "masterDetails": [
                        {"name": "SSUDetails"}
                    ],
                }
            ],
        },
    }

    response = requests.post(api_url, json=payload, headers=headers)
    return response.json()


def process_pi_data_for_each_tenant(request_info, tenant_id, cursor, connection, mdms_data):
    ifms_host = os.getenv('IFMS_HOST')
    ifms_pi_search = os.getenv('IFMS_PI_SEARCH')
    api_url = f"{ifms_host}{ifms_pi_search}"
    headers = {
        'Content-Type': 'application/json'
    }
    data = {
        "RequestInfo": request_info,
        "searchCriteria": {"tenantId": tenant_id},
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
            all_data = response_data.get("paymentInstructions", [])
            enrich_disbursement_from_pi_and_insert(request_info, all_data, cursor, connection, mdms_data)
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
    individual_host = os.getenv('INDIVIDUAL_HOST')
    individual_search = os.getenv('INDIVIDUAL_SEARCH')
    url = f"{individual_host}{individual_search}"
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
    organization_host = os.getenv('ORGANIZATION_HOST')
    organization_search = os.getenv('ORGANIZATION_SEARCH')
    url = f"{organization_host}{organization_search}"
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
    expense_host = os.getenv('EXPENSE_HOST')
    expense_search = os.getenv('EXPENSE_PAYMENT_SEARCH')
    url = f"{expense_host}{expense_search}"
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
    bank_account_host = os.getenv('BANK_ACCOUNT_HOST')
    bank_account_search = os.getenv('BANK_ACCOUNT_SEARCH')
    url = f"{bank_account_host}{bank_account_search}"

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
    enc_host = os.getenv('ENC_HOST')
    enc_decrypt = os.getenv('ENC_DECRYPT_ENDPOINT')
    api_url = f"{enc_host}{enc_decrypt}"
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
        return account_number


def get_account_code(request_info, response_data, pi_created_time):
    sorted_logs = sorted(response_data["AuditLogs"], key=lambda x: x["changeDate"])

    # Find the first log with changeDate greater than the given time
    matching_log_eg_bank_account = next((log for log in sorted_logs if log["changeDate"] < pi_created_time and log[
        "entityName"] == 'eg_bank_account_detail'), None)
    matching_log_eg_bank_branch_identifier = next((log for log in sorted_logs if
                                                   log["changeDate"] < pi_created_time and log[
                                                       "entityName"] == 'eg_bank_branch_identifier'), None)
    if matching_log_eg_bank_account is None and matching_log_eg_bank_branch_identifier is None and len(sorted_logs) > 0:
        matching_log_eg_bank_account = next(
            (log for log in sorted_logs if log["entityName"] == 'eg_bank_account_detail'), None)
        matching_log_eg_bank_branch_identifier = next(
            (log for log in sorted_logs if log["entityName"] == 'eg_bank_branch_identifier'), None)
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
    audit_log_host = os.getenv('AUDIT_LOG_HOST')
    audit_log_search = os.getenv('AUDIT_LOG_SEARCH')
    api_url = f"{audit_log_host}{audit_log_search}"
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


def create_disbursement_for_individual(beneficiary, individual, request_info, pi_created_time, mdms_data):
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
            "additional_details": {
                "beneficiaryId": entry['beneficiaryId'],
                "beneficiaryType": "IND",
                "beneficiaryStatus": beneficiary['paymentStatus']
            },
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        enrich_codes_from_mdms(disbursement, mdms_data)
        disbursements.append(disbursement)

    return disbursements


def create_disbursement_for_organization(beneficiary, organization, request_info, pi_created_time, mdms_data):
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
            "additional_details": {
                "beneficiaryId": entry['beneficiaryId'],
                "beneficiaryType": "ORG",
                "beneficiaryStatus": beneficiary['paymentStatus']
            },
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        enrich_codes_from_mdms(disbursement, mdms_data)
        disbursements.append(disbursement)

    return disbursements


def create_disbursement_for_department(beneficiary, request_info, pi_created_time, mdms_data):
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
                "name": beneficiary['beneficiaryType'],
                "gender": None,
                "address": beneficiary['tenantId'],
                "email": None,
                "phone": "99999999999",
            },
            "program_code": "PG/2023-24/000310",
            "additional_details": {
                "beneficiaryId": entry['beneficiaryId'],
                "beneficiaryType": "DEPT",
                "beneficiaryStatus": beneficiary['paymentStatus']
            },
            "audit_details": {
                "createdBy": beneficiary['auditDetails']['createdBy'],
                "lastModifiedBy": beneficiary['auditDetails']['lastModifiedBy'],
                "createdTime": beneficiary['auditDetails']['createdTime'],
                "lastModifiedTime": beneficiary['auditDetails']['lastModifiedTime']
            }
        }
        enrich_codes_from_mdms(disbursement, mdms_data)
        disbursements.append(disbursement)

    return disbursements


def encrypt_disbursement(disbursement_data):
    program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
    program_service_encrypt = os.getenv('PROGRAM_SERVICE_ENCRYPT')
    url = f"{program_service_host}{program_service_encrypt}"
    headers = {'Content-Type': 'application/json'}

    response = requests.post(url, json=disbursement_data, headers=headers)

    if response.status_code == 200:
        return response.json()
    else:
        return jsonify({'error': 'Failed to encrypt disbursement.'}), response.status_code


def enrich_codes_from_mdms(main_disbursement, mdms_data):
    # Assuming the structure of the response is as provided
    ssu_details = mdms_data.get("MdmsRes", {}).get("ifms", {}).get("SSUDetails", [{}])[0]
    mdms_data = mdms_data.get("MdmsRes", {}).get("segment-codes", {})

    # Extracting codes from the response
    admin_codes = mdms_data.get("AdministrativeCodes", [])
    function_codes = mdms_data.get("FunctionCodes", [])
    economic_segment_codes = mdms_data.get("EconomicSegmentCodes", [])
    source_of_fund_codes = mdms_data.get("SourceOfFundCodes", [])
    target_segment_codes = mdms_data.get("TargetSegmentCodes", [])
    recipient_segment_codes = mdms_data.get("RecipientSegmentCodes", [])
    geographic_segment_codes = mdms_data.get("GeographicSegmentCodes", [])

    # Assigning the extracted codes to the main_disbursement object
    main_disbursement["administration_code"] = admin_codes[0]["code"]
    main_disbursement["function_code"] = function_codes[0]["code"]
    main_disbursement["economic_segment_code"] = economic_segment_codes[0]["code"]
    main_disbursement["source_of_fund_code"] = source_of_fund_codes[0]["code"]
    main_disbursement["target_segment_code"] = target_segment_codes[0]["code"]
    main_disbursement["recipient_segment_code"] = recipient_segment_codes[0]["code"]
    main_disbursement["geographic_segment_code"] = geographic_segment_codes[0]["code"]
    main_disbursement["program_code"] = ssu_details["programCode"]


def process_pi_data(request_info, entry, mdms_data):
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
            disbursement = create_disbursement_for_individual(beneficiary, individual, request_info, pi_created_time,
                                                              mdms_data)

        if beneficiary["beneficiaryType"] == "ORG":
            organization = organizations_beneficiary_id_map[beneficiary["beneficiaryId"]]
            disbursement = create_disbursement_for_organization(beneficiary, organization, request_info,
                                                                pi_created_time, mdms_data)

        if beneficiary["beneficiaryType"] == "DEPT":
            disbursement = create_disbursement_for_department(beneficiary, request_info, pi_created_time, mdms_data)

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
        "children": disbursements,
        "location_code": entry['tenantId'],
        "transaction_id": entry['jitBillNo'],
        "gross_amount": entry['grossAmount'],
        "sanction_id": None,
        "account_code": None,
        "individual": None,
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
            "status_message": entry['piErrorResp'] if 'piErrorResp' in entry else None
        },
        "program_code": "PG/2023-24/000310",
        "additional_details": {
            "piStatus": entry['piStatus'],
            "billNumber": entry['additionalDetails']['billNumber'],
            "referenceId": entry['additionalDetails']['referenceId'],
            "paDetails": entry['paDetails'][0],
            "parentPiNumber": entry['parentPiNumber'] if 'parentPiNumber' in entry else None,
            "isRevised": True if 'parentPiNumber' in entry else False,
            "numBeneficiaries": entry['numBeneficiaries']
        },
        "audit_details": {
            "createdBy": entry['auditDetails']['createdBy'],
            "lastModifiedBy": entry['auditDetails']['lastModifiedBy'],
            "createdTime": entry['auditDetails']['createdTime'],
            "lastModifiedTime": entry['auditDetails']['lastModifiedTime']
        }
    }
    enrich_codes_from_mdms(main_disbursement, mdms_data)
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
        'disburse',
        disbursement['id'],
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
        disbursement['transaction_id'],
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
        message['audit_details']['lastModifiedBy'],
        "disburse"
    ]
    return values_eg_mukta_ifms_message_codes


def insert_migration_status(id, migrated, cursor):
    cursor.execute('''INSERT INTO MigrationStatus (id, migrated)
                      VALUES (%s, %s)
                      ON CONFLICT (id) DO UPDATE
                      SET migrated = EXCLUDED.migrated''', (id, migrated))


def get_migration_status(field_name, cursor):
    cursor.execute('''SELECT migrated FROM MigrationStatus WHERE id = %s''', (field_name,))
    result = cursor.fetchone()
    if result is None:
        return False
    if result:
        return result[0]
    else:
        return False  # Field not found


def push_data_to_db(disbursement, cursor):
    query_eg_program_disburse = """
        INSERT INTO eg_program_disburse 
        (id, location_code, program_code, target_id, transaction_id, parent_id, sanction_id, account_code, individual, net_amount, 
        gross_amount, status, status_message, additional_details, created_time, created_by, last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    values_eg_program_disburse = get_program_disburse_values(disbursement)
    cursor.execute(query_eg_program_disburse, values_eg_program_disburse)
    # Insert data into eg_program_message_codes table
    query_eg_program_message_codes = """
        INSERT INTO eg_program_message_codes 
        (id, location_code, type, reference_id, function_code, administration_code, program_code, recipient_segment_code, 
        economic_segment_code, source_of_fund_code, target_segment_code, created_time, created_by, 
        last_modified_time, last_modified_by) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """
    values_eg_program_message_codes = get_program_message_codes_values(disbursement)
    cursor.execute(query_eg_program_message_codes, values_eg_program_message_codes)
    for disburse in disbursement['children']:
        values_eg_program_disburse = get_program_disburse_values(disburse, disbursement['id'])
        cursor.execute(query_eg_program_disburse, values_eg_program_disburse)
        # Insert data into eg_program_message_codes table
        values_eg_program_message_codes = get_program_message_codes_values(disburse)
        cursor.execute(query_eg_program_message_codes, values_eg_program_message_codes)


def encrypt_disbursement_for_mukta(disbursement):
    tenant_id = disbursement['location_code']
    disbursement_copy = copy.deepcopy(disbursement)
    for child in disbursement_copy['children']:
        object_for_enc = {
            "name": child['individual']['name'],
            "address": child['individual']['address'],
            "email": child['individual']['email'],
            "phone": child['individual']['phone'],
            "pin": child['individual']['pin'],
            "account_code": child['account_code']
        }
        encrypted_object = encrypt_object(object_for_enc,tenant_id)
        child['individual']['name'] = encrypted_object[0]
        child['individual']['address'] = encrypted_object[1]
        child['individual']['email'] = encrypted_object[2]
        child['individual']['phone'] = encrypted_object[3]
        child['individual']['pin'] = encrypted_object[4]
        child['account_code'] = encrypted_object[5]

    return disbursement_copy


def push_mukta_data_to_db(mukta_disbursement, cursor):
    query_eg_mukta_ifms_disburse = """
            INSERT INTO eg_mukta_ifms_disburse 
            (id, program_code, target_id, parent_id, transaction_id, account_code, status, status_message, individual, net_amount, 
            gross_amount, created_time, created_by, last_modified_time, last_modified_by) 
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
    query_eg_mukta_ifms_message_codes = """
            INSERT INTO eg_mukta_ifms_message_codes 
            (id, location_code, parent_id, function_code, administration_code, program_code, recipient_segment_code, 
            economic_segment_code, source_of_fund_code, target_segment_code, additional_details, created_time, 
            created_by, last_modified_time, last_modified_by, type) 
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
    values_eg_mukta_ifms_disburse = get_mukta_ifms_disburse_values(mukta_disbursement)
    values_eg_mukta_ifms_message_codes = get_mukta_ifms_message_codes_values(mukta_disbursement)
    cursor.execute(query_eg_mukta_ifms_disburse, values_eg_mukta_ifms_disburse)
    cursor.execute(query_eg_mukta_ifms_message_codes, values_eg_mukta_ifms_message_codes)
    for disburse in mukta_disbursement['children']:
        values_eg_mukta_ifms_disburse = get_mukta_ifms_disburse_values(disburse, mukta_disbursement['id'])
        values_eg_mukta_ifms_message_codes = get_mukta_ifms_message_codes_values(disburse)
        cursor.execute(query_eg_mukta_ifms_disburse, values_eg_mukta_ifms_disburse)
        cursor.execute(query_eg_mukta_ifms_message_codes, values_eg_mukta_ifms_message_codes)



def enrich_disbursement_from_pi_and_insert(request_info, all_data, cursor, connection, mdms_data):
    for entry in all_data:
        is_migrated = get_migration_status(entry['id'], cursor)
        if is_migrated:
            continue
        print('Processing data for: ' + entry['id'])
        logging.info(f"Processing data for: {entry['id']}")
        disbursement = process_pi_data(request_info, entry, mdms_data)
        mukta_disbursement = encrypt_disbursement_for_mukta(disbursement)
        disbursement = encrypt_disbursement(disbursement)
        if isinstance(disbursement, tuple):
            print('Failed to process data for: ' + entry['id'])
            logging.error(f"Failed to process data for: {entry['id']}")
            continue
        push_mukta_data_to_db(mukta_disbursement, cursor)
        push_data_to_db(disbursement, cursor)
        insert_migration_status(entry['id'], True, cursor)
        connection.commit()
        logging.info(f"Data inserted successfully for: {entry['id']}")
        print('Data inserted successfully for: ' + entry['id'])


@app.route('/api/data', methods=['POST'])
def insert_data(request_info):
    with app.app_context():
        connection = None
        cursor = None

        try:
            # Connect to PostgreSQL
            connection = connect_to_database()

            # Create a cursor object
            cursor = connection.cursor()

            # Create a table to store migration status
            cursor.execute('''CREATE TABLE IF NOT EXISTS MigrationStatus (
                                    id TEXT PRIMARY KEY,
                                    migrated BOOLEAN
                                )''')
            connection.commit()

            # Fetch data from the external API using RequestInfo
            request_info = request_info['RequestInfo']
            tenant_ids = fetch_tenants_from_mdms(request_info)
            # Process for each tenant id
            for tenant_id in tenant_ids:
                print('Processing for tenant: ', tenant_id)
                logging.info(f"Processing for tenant: {tenant_id}")
                mdms_data = fetch_data_from_mdms(request_info, tenant_id)
                process_pi_data_for_each_tenant(request_info, tenant_id, cursor, connection, mdms_data)

            # Use Flask's current_app to access the application context
            return jsonify({"message": "Data inserted successfully"}), 201

        except Exception as e:
            # Use Flask's current_app to access the application context
            print(f"Last Error: {str(e)}")
            logging.error(f"Last Error: {str(e)}")
            return jsonify({"error": str(e)}), 500

        finally:
            # Close the cursor and connection in the finally block
            if cursor:
                cursor.close()

            if connection:
                connection.close()


def process_disbursement_data_for_each_tenant(tenant_id, type, result_type):
    program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
    api_url = f"{program_service_host}program-service/v1/{type}/_search"
    headers = {
        'Content-Type': 'application/json'
    }
    data = {
        "signature": None,
        "header": {
            "message_id": "123",
            "message_ts": "1707460264352",
            "action": "search",
            "message_type": type,
            "sender_id": "program@https://unified-dev.digit.org/",
            "sender_uri": "https://spp.example.org/{namespace}/callback/on-search",
            "receiver_id": "program@https://unified-qa.digit.org/"
        },
        "message": {
            "location_code": tenant_id
        },
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
            all_data.extend(response_data.get(result_type, []))

            # Check if there are more records to fetch
            if len(response_data.get(result_type, [])) < int(data["pagination"]["limit"]):
                break

            # Update the offset for the next request
            data["pagination"]["offSet"] = str(int(data["pagination"]["offSet"]) + int(data["pagination"]["limit"]))

        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
            break

    return all_data


def publish_data_to_kafka(all_data, value):
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    topic = 'exchange-topic'
    signature = None
    header = {
        "message_id": "123",
        "message_ts": "1707460264352",
        "action": "create",
        "message_type": value,
        "sender_id": "program@https://unified-dev.digit.org/",
        "sender_uri": "https://spp.example.org/{namespace}/callback/on-push",
        "receiver_id": "program@https://unified-qa.digit.org/"
    }
    d = {
        "signature": signature,
        "header": header,
        "message": None
    }
    for data in all_data:
        d['message'] = data
        json_data = json.dumps(d).encode('utf-8')
        producer.send(topic, json_data)
        producer.flush()

    producer.close()


@app.route('/exchange/push', methods=['POST'])
def push_data_to_exchange():
    try:
        request_info = request.get_json()
        request_info = request_info['RequestInfo']
        tenant_ids = fetch_data_from_mdms(request_info)
        for tenant_id in tenant_ids:
            all_data_for_disbursement = process_disbursement_data_for_each_tenant(tenant_id, 'disburse',
                                                                                  'disbursements')
            all_data_for_program = process_disbursement_data_for_each_tenant(tenant_id, 'program', 'programs')
            all_data_for_sanction = process_disbursement_data_for_each_tenant(tenant_id, 'sanction', 'sanctions')
            all_data_for_allocation = process_disbursement_data_for_each_tenant(tenant_id, 'allocation', 'allocations')
            publish_data_to_kafka(all_data_for_disbursement, 'disburse')
            publish_data_to_kafka(all_data_for_program, 'program')
            publish_data_to_kafka(all_data_for_sanction, 'sanction')
            publish_data_to_kafka(all_data_for_allocation, 'allocation')

    except Exception as e:
        return jsonify({"error": str(e)}), 500


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


def enrich_bankaccount_and_program_codes_ifms_data(mdms_data, cursor, connection, tenant_id, request_info):
    program_code = mdms_data.get("MdmsRes", {}).get("ifms", {}).get("SSUDetails", [{}])[0].get("programCode")
    jit_payment_inst_details_query = '''Select id from jit_payment_inst_details where tenantid = %s'''
    cursor.execute(jit_payment_inst_details_query, (tenant_id,))
    jit_payment_inst_details = cursor.fetchall()
    for jit_payment_inst_detail in jit_payment_inst_details:
        jit_payment_inst_detail = jit_payment_inst_detail[0]
        program_code_query = '''Update jit_payment_inst_details set programcode = %s where id = %s'''
        cursor.execute(program_code_query, (program_code, jit_payment_inst_detail))
        connection.commit()
        logging.info(f"Program code updated for JIT Payment Inst Detail: {jit_payment_inst_detail}")
        print(f"Program code updated for JIT Payment Inst Detail: {jit_payment_inst_detail}")

    jit_sanction_details = '''Select id from jit_sanction_details where tenantid = %s'''
    cursor.execute(jit_sanction_details, (tenant_id,))
    jit_sanction_details = cursor.fetchall()
    for jit_sanction_detail in jit_sanction_details:
        jit_sanction_detail = jit_sanction_detail[0]
        program_code_query = '''Update jit_sanction_details set programcode = %s where id = %s'''
        cursor.execute(program_code_query, (program_code, jit_sanction_detail))
        connection.commit()
        logging.info(f"Program code updated for JIT Sanction Detail: {jit_sanction_detail}")
        print(f"Program code updated for JIT Sanction Detail: {jit_sanction_detail}")

    jit_beneficiary_details_query = '''Select id, beneficiaryid, createdtime from jit_beneficiary_details where tenantid = %s'''
    cursor.execute(jit_beneficiary_details_query, (tenant_id,))
    jit_beneficiary_details = cursor.fetchall()
    for jit_beneficiary_detail in jit_beneficiary_details:
        jit_beneficiary_detail_id = jit_beneficiary_detail[0]
        beneficiary_id = jit_beneficiary_detail[1]
        created_time = jit_beneficiary_detail[2]
        bank_account = fetch_bank_account_from_audit_logs(request_info, beneficiary_id, tenant_id, created_time)
        object_to_enc = {
            "bankaccountcode": bank_account
        }
        bank_account = encrypt_object(object_to_enc, tenant_id)
        bank_account_query = '''Update jit_beneficiary_details set bankaccountcode = %s where id = %s'''
        try:
            cursor.execute(bank_account_query, (bank_account[0], jit_beneficiary_detail_id))
            connection.commit()
        except Exception as e:
            print(f"Error: {str(e)}")
            connection.rollback()
            continue
        logging.info(f"Bank Account updated for JIT Beneficiary Detail: {jit_beneficiary_detail_id}")
        print("Bank Account updated for JIT Beneficiary Detail: ", jit_beneficiary_detail_id)


def encrypt_object(object, tenant_id):
    enc_host = os.getenv('ENC_HOST')
    enc_encrypt = os.getenv('ENC_ENCRYPT_ENDPOINT')
    api_url = f"{enc_host}{enc_encrypt}"
    headers = {
        'Content-Type': 'application/json',
    }
    enc_data = []
    for key, value in object.items():
        enc_data.append({
            "tenantId": tenant_id,
            "type": "Normal",
            "value": value
        })
    data = {
        "encryptionRequests": enc_data,
    }
    response = requests.post(api_url, json=data, headers=headers)

    # Check if the request was successful (status code 200)
    if response.status_code == 200:
        response = response.json()
        return response
    else:
        # If not successful, print the error status code and response text
        print(f"Error: {response.status_code}\n{response.text}")


@app.route('/ifms/migrate', methods=['POST'])
def migrate_ifms_data():
    connection = None
    cursor = None
    try:
        logging.info("Starting IFMS data migration")
        # Connect to PostgreSQL
        connection = connect_to_database()
        cursor = connection.cursor()
        # Extract data from the incoming request
        request_data = request.get_json()
        request_info = request_data['RequestInfo']
        tenant_ids = fetch_tenants_from_mdms(request_info)
        for tenant_id in tenant_ids:
            mdms_data = fetch_data_from_mdms(request_info, tenant_id)
            enrich_bankaccount_and_program_codes_ifms_data(mdms_data, cursor, connection, tenant_id, request_info)

        return jsonify({"message": "Data migrated successfully"}), 200
    except Exception as e:
        # Use Flask's current_app to access the application context
        print(f"Last Error: {str(e)}")
        return jsonify({"error": str(e)}), 500

    finally:
        # Close the cursor and connection in the finally block
        if cursor:
            cursor.close()

        if connection:
            connection.close()
