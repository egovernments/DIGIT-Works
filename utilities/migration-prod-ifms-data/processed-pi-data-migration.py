import logging
import json
import requests  # Import the requests library
import psycopg2
from psycopg2.extras import Json
from dotenv import load_dotenv
import os
import uuid
import copy
import traceback
from kafka import KafkaProducer


load_dotenv('.env')

logging.basicConfig(filename='app.log', level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
# Replace these with your PostgreSQL database details
DB_HOST = os.getenv('DB_HOST')
DB_PORT = os.getenv('DB_PORT')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')

# Connect to PostgreSQL
def connect_to_database():
    return psycopg2.connect(
        host=DB_HOST, port=DB_PORT, database=DB_NAME, user=DB_USER, password=DB_PASSWORD
    )

def get_request_info():
    return {
        "apiId": "Rainmaker",
        "ver": None,
        "ts": None,
        "action": None,
        "did": None,
        "key": None,
        "msgId": "1686748436982|en_IN",
        "authToken": None,
        "correlationId": "ad773f43-7b52-45fa-b690-d08044455f1a",
        "plainAccessRequest": {
            "recordId": None,
            "plainRequestFields": None
        },
        "userInfo": {
            "id": 435,
            "userName": "KEERTHI",
            "name": "Keerthi",
            "type": "EMPLOYEE",
            "mobileNumber": "9998887771",
            "emailId": None,
            "roles": [
                {
                    "id": None,
                    "name": "BILL_ACCOUNTANT",
                    "code": "BILL_ACCOUNTANT",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "WORK_ORDER_VIEWER",
                    "code": "WORK_ORDER_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "HRMS Admin",
                    "code": "HRMS_ADMIN",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "BILL_VERIFIER",
                    "code": "BILL_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "ESTIMATE VERIFIER",
                    "code": "ESTIMATE_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "WORK ORDER CREATOR",
                    "code": "WORK_ORDER_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "ESTIMATE APPROVER",
                    "code": "ESTIMATE_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "Organization viewer",
                    "code": "ORG_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "WORK ORDER VERIFIER",
                    "code": "WORK_ORDER_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "PROJECT VIEWER",
                    "code": "PROJECT_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "BILL_APPROVER",
                    "code": "BILL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "MUSTER ROLL VERIFIER",
                    "code": "MUSTER_ROLL_VERIFIER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "OFFICER IN CHARGE",
                    "code": "OFFICER_IN_CHARGE",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "Employee Common",
                    "code": "EMPLOYEE_COMMON",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "PROJECT CREATOR",
                    "code": "PROJECT_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "BILL_VIEWER",
                    "code": "BILL_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "TECHNICAL SANCTIONER",
                    "code": "TECHNICAL_SANCTIONER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "BILL_CREATOR",
                    "code": "BILL_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "MUSTER ROLL APPROVER",
                    "code": "MUSTER_ROLL_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "ESTIMATE VIEWER",
                    "code": "ESTIMATE_VIEWER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "WORK ORDER APPROVER",
                    "code": "WORK_ORDER_APPROVER",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "ESTIMATE CREATOR",
                    "code": "ESTIMATE_CREATOR",
                    "tenantId": "pg.citya"
                },
                {
                    "id": None,
                    "name": "MUKTA Admin",
                    "code": "MUKTA_ADMIN",
                    "tenantId": "pg.citya"
                }
            ],
            "tenantId": "pg.citya",
            "uuid": "5ec3feaa-1148-4462-9842-2896c866c349"
        }
    }

def load_file(file_name):
    # Get the current working directory
    current_directory = os.getcwd()

    # Combine the directory and file name to get the full file path
    file_path = os.path.join(current_directory, file_name)
    data = {}
    # Check if the file exists
    if os.path.exists(file_path):
        # Open the file and load the JSON data
        with open(file_path, "r") as json_file:
            data = json.load(json_file)
        print("JSON data loaded successfully.")
    else:
        print("File not found:", file_path)

    return data


def get_processed_in_progress_pi_and_failed_pis(processed_pi, in_progress_pi):
    # Get the payment numbers from the in_progress_pi dictionary
    # in progress payments which are inprogress and payment is not success
    payments_unique_inprogress = []
    duplicate_payments_inprogress_and_processed = []
    pis_to_delete_processed = []
    pis_to_delete_inprogress = []
    pis_to_fail_inprogress = []
    pis_to_save_processed = []
    processed_payment_numbers = processed_pi.keys()
    in_progress_payment_numbers = in_progress_pi.keys()
    for payment_number in in_progress_payment_numbers:
        if payment_number not in processed_payment_numbers:
            payments_unique_inprogress.append(payment_number)
            for pi in in_progress_pi[payment_number]:
                pis_to_fail_inprogress.append(pi);
        else:
            duplicate_payments_inprogress_and_processed.append(payment_number)
            for pi in in_progress_pi[payment_number]:
                pis_to_delete_inprogress.append(pi)
            
    for payment_number in processed_payment_numbers:
        processed_pis = processed_pi[payment_number]
        count = 0;
        for pi in processed_pis:
            if count > 0:
                pis_to_delete_processed.append(pi)
            else:
                pis_to_save_processed.append(pi)
            
            count = count + 1
        
            
    # print("pis_to_fail_inprogress", pis_to_fail_inprogress)
    # print("pis_to_delete_inprogress", pis_to_delete_inprogress)
    # print("pis_to_delete_processed", pis_to_delete_processed)
    # print("pis_to_save_processed", pis_to_save_processed)

    return [pis_to_save_processed, pis_to_fail_inprogress, pis_to_delete_inprogress, pis_to_delete_processed]

def search_disburse_from_program_service(payment_number, locaiton_code):
    try:
        program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
        program_disburse_search = os.getenv('PROGRAM_DISBURSE_SEARCH')

        api_url = f"{program_service_host}/{program_disburse_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "signature": None,
            "header": {
                "message_id": "123",
                "message_ts": "1708428280",
                "message_type": "disburse",
                "action": "search",
                "sender_id": "program@http://localhost:8083/ifms/digit-exchange",
                "receiver_id": "program@http://localhost:8083/ifms/digit-exchange"
            },
            "message": {
                "location_code": locaiton_code,
                "target_id": payment_number,
                "pagination": {
                    "limit": 100,
                    "offset": 0
                }
            }
        }

        response = requests.post(api_url, json=request, headers=headers)
        disbursements = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            disbursements.extend(response_data.get('disbursements', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return disbursements
    except Exception as e:
        print("search_disburse_from_program_service : error {}".format(str(e)))
        raise e

def search_payment_instruction_from_ifms_adapter(payment_number, tenant_id):
    try:
        ifms_service_host = os.getenv('IFMS_HOST')
        ifms_pi_search = os.getenv('IFMS_PI_SEARCH')

        api_url = f"{ifms_service_host}/{ifms_pi_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "searchCriteria": {
                "tenantId": tenant_id,
                "muktaReferenceId": payment_number,
                "limit": "10000",
                "offset": "0",
                "sortBy": "createdTime",
                "order": "ASC"
            }
        }

        response = requests.post(api_url, json=request, headers=headers)
        paymentInstructions = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            paymentInstructions.extend(response_data.get('paymentInstructions', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return paymentInstructions
    except Exception as e:
        print("search_payment_instruction_from_ifms_adapter : error {}".format(str(e)))
        raise e

def get_disburse_from_db(payment_number, cursor):
    try:
        cursor.execute('''SELECT target_id, location_code FROM eg_program_disburse WHERE target_id = %s''', (payment_number,))
        result = cursor.fetchone()
        if result is None:
            return False
        if result:
            return result
        else:
            return False  
    except Exception as e:
        print("get_disburse_from_db : error {}".format(str(e)))
        raise e

def get_sanction_details(ssuallotmentid, tenantid, cursor):
    print('sanction_id {}'.format(ssuallotmentid))
    try:
        cursor.execute('''select sanctionid from jit_allotment_details where ssuallotmentid = %s and tenantid = %s''', (str(ssuallotmentid), tenantid))
        result = cursor.fetchone()
        if result is None:
            return None
        if result:
            return result
        else:
            return None
    except Exception as e:
        print("get_sanction_details : error {}".format(str(e)))
        raise e

def migrate_payment_instruction(disbursement, payment_instruction, pi_object, benf_account_map, cursor, connection):
    try:
        # Deep clone the object
        payment_instruction_copy = copy.deepcopy(payment_instruction)
        print('create_payment_instruction')
        payment_instruction['id'] = disbursement['id']
        payment_instruction['piStatus'] = 'INITIATED'
        payment_instruction['piSuccessCode'] = '0'
        payment_instruction['piSuccessDesc'] = 'Jit Bill is received successfully ,Payment Instruction will be generated after Bill is submitted by SSU in JIT-FS'
        payment_instruction['piApprovedId'] = None
        payment_instruction['piApprovalDate'] = None
        payment_instruction['jitBillNo'] = pi_object['JIT_BILL_NUMBER']
        pi_additional_details = payment_instruction.get('additionalDetails', {})
        pi_additional_details['hoaCode'] = pi_object.get('HOA')
        pi_additional_details['mstAllotmentId'] = pi_object.get('MST_ALLOTMENT_DIST_ID')
        pi_additional_details['ssuAllotmentId'] = pi_object.get('SSU_IA_ID')
        paDetails = {
            "id": str(uuid.uuid4()),
            "tenantId": payment_instruction.get('tenantId'),
            "muktaReferenceId": payment_instruction.get('muktaReferenceId'),
            "piId": payment_instruction.get('id'),
            "paBillRefNumber": None,
            "paFinYear": None,
            "paAdviceId": None,
            "paAdviceDate": None,
            "paTokenNumber": None,
            "paTokenDate": None,
            "paErrorMsg": None,
            "additionalDetails": None,
            "auditDetails": payment_instruction.get('auditDetails')
        }
        pi_additional_details['paDetails'] = paDetails
        payment_instruction['additionalDetails'] = pi_additional_details
        payment_instruction['paDetails'] = [paDetails]

        #transaction details
        sanction = get_sanction_details(pi_object['AW_ALLOTMENT_DIST_ID'], payment_instruction['tenantId'], cursor)
        if sanction == None:
            raise Exception('Sanction details not found for payment number {}'.format(payment_instruction.get('muktaReferenceId')))
        transaction_details = [{
            "id": str(uuid.uuid4()),
            "tenantId": payment_instruction.get('tenantId'),
            "sanctionId": sanction[0],
            "paymentInstId": payment_instruction.get('id'),
            "transactionAmount": payment_instruction.get('grossAmount'),
            "transactionDate": 0,
            "transactionType": "DEBIT",
            "additionalDetails": {},
            "auditDetails": payment_instruction.get('auditDetails')
        }]
        payment_instruction['transactionDetails'] = transaction_details

        # enrich beneficiary numbers
        # assigning random
        payment_instruction['beneficiaryDetails'] = sorted(payment_instruction.get('beneficiaryDetails'), key=lambda x: x['amount'])
        pi_object['beneficiaryDetails'] = sorted(pi_object.get('beneficiaryDetails'), key=lambda x: x['BENF_AMOUNT'])
        beneficiary_line_item_map = {}

        bank_account_benf_id_map = {}
        for idx, beneficiary in enumerate(pi_object['beneficiaryDetails']):
            bank_account_benf_id_map[benf_account_map[beneficiary.get('BENF_ID')]] = beneficiary.get('BENF_ID')

        line_item_id_benf_id_map = {}
        for idx, disburse in enumerate(disbursement['children']):
            line_item_id_benf_id_map[disburse.get('target_id')] = bank_account_benf_id_map.get(disburse.get('account_code'), None)
            disburse['transaction_id'] = line_item_id_benf_id_map.get(disburse.get('target_id'), None)
            disburse["status"] = {
                "status_code": "INITIATED",
                "status_message": "INITIATED"
            }

        disbursement["status"] = {
            "status_code": "INITIATED",
            "status_message": "INITIATED"
        }

        disbursement['transaction_id'] = payment_instruction['jitBillNo']

        for idx, beneficiary in enumerate(payment_instruction['beneficiaryDetails']):
            beneficiary['id'] = str(uuid.uuid4())
            beneficiary['piId'] = payment_instruction.get('id')
            beneficiary['paymentStatus'] = 'Payment Initiated';
            for benf_line_item in beneficiary.get('benfLineItems'):
                benf_line_item['beneficiaryId'] = beneficiary.get('id')
                beneficiary_line_item_map[benf_line_item['lineItemId']] = beneficiary.get('beneficiaryNumber')
                if (benf_line_item['lineItemId'] in line_item_id_benf_id_map):
                    beneficiary['beneficiaryNumber'] = line_item_id_benf_id_map[benf_line_item['lineItemId']]

        insert_payment_instruction_db(payment_instruction, payment_instruction_copy, cursor, connection)
        push_pi_data_for_indexer(payment_instruction)
        call_on_disburse_update_api(disbursement)

    except Exception as e:
        print("create_payment_instruction : error {}".format(str(e)))
        raise e

def insert_payment_instruction_db(payment_instruction, payment_instruction_copy, cursor, connection):
    try:
        delete_payment_instruction_data_from_db(payment_instruction_copy, cursor, connection)
        print('update_payment_instruction_db')
        pi_insert_query = """
            INSERT INTO jit_payment_inst_details 
            (id, tenantId, piNumber, programCode, parentPiNumber, muktaReferenceId, numBeneficiaries, grossAmount, netAmount, piStatus, isActive, piSuccessCode, piSuccessDesc, 
            piApprovedId, piApprovalDate, piErrorResp, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);
        """
        cursor.execute(pi_insert_query, [
            payment_instruction.get('id'),
            payment_instruction.get('tenantId'),
            payment_instruction.get('jitBillNo'),
            payment_instruction.get('programCode'),
            payment_instruction.get('parentPiNumber', None),
            payment_instruction.get('muktaReferenceId'),
            payment_instruction.get('numBeneficiaries'),
            payment_instruction.get('grossAmount'),
            payment_instruction.get('netAmount'),
            payment_instruction.get('piStatus'),
            payment_instruction.get('isActive', True),
            payment_instruction.get('piSuccessCode', '0'),
            payment_instruction.get('piSuccessDesc'),
            payment_instruction.get('piApprovedId', None),
            payment_instruction.get('piApprovalDate', None),
            payment_instruction.get('piErrorResp', None),
            Json(payment_instruction.get('additionalDetails')),
            payment_instruction.get('auditDetails', {}).get('createdTime'),
            payment_instruction.get('auditDetails', {}).get('createdBy'),
            payment_instruction.get('auditDetails', {}).get('lastModifiedTime'),
            payment_instruction.get('auditDetails', {}).get('lastModifiedBy')
        ])
        pa_insert_query = """
            INSERT INTO jit_payment_advice_details 
            (id, tenantId, muktaReferenceId, piId, paBillRefNumber, paFinYear, paAdviceId, paAdviceDate, paTokenNumber, paTokenDate,
            paErrorMsg, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);
        """
        for idx, pa_details in enumerate(payment_instruction['paDetails']):
            cursor.execute(pa_insert_query, [
                pa_details.get('id'),
                pa_details.get('tenantId'),
                pa_details.get('muktaReferenceId'),
                payment_instruction.get('id'),
                pa_details.get('paBillRefNumber', None),
                pa_details.get('paFinYear', None),
                pa_details.get('paAdviceId', None),
                pa_details.get('paAdviceDate', None),
                pa_details.get('paTokenNumber', None),
                pa_details.get('paTokenDate', None),
                pa_details.get('paErrorMsg', None),
                Json(pa_details.get('additionalDetails')),
                pa_details.get('auditDetails', {}).get('createdTime'),
                pa_details.get('auditDetails', {}).get('createdBy'),
                pa_details.get('auditDetails', {}).get('lastModifiedTime'),
                pa_details.get('auditDetails', {}).get('lastModifiedBy')
            ])

        JIT_BENEFICIARY_DETAILS_INSERT_QUERY = """
            INSERT INTO jit_beneficiary_details 
            (id, tenantId, muktaReferenceId, piId, beneficiaryId, beneficiaryType, beneficiaryNumber, bankAccountCode, amount, voucherNumber, voucherDate, utrNo, utrDate, endToEndId, challanNumber, 
            challanDate, paymentStatus, paymentStatusMessage, additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 
            %s, %s, %s, %s, %s, %s, %s, %s);
        """
        for idx, benf_details in enumerate(payment_instruction['beneficiaryDetails']):
            cursor.execute(JIT_BENEFICIARY_DETAILS_INSERT_QUERY, [
                benf_details.get('id'),
                benf_details.get('tenantId'),
                benf_details.get('muktaReferenceId'),
                payment_instruction.get('id'),
                benf_details.get('beneficiaryId'),
                benf_details.get('beneficiaryType'),
                benf_details.get('beneficiaryNumber'),
                benf_details.get('bankAccountId'),
                benf_details.get('amount'),
                benf_details.get('voucherNumber', None),
                benf_details.get('voucherDate', None),
                benf_details.get('utrNo', None),
                benf_details.get('utrDate', None),
                benf_details.get('endToEndId', None),
                benf_details.get('challanNumber', None),
                benf_details.get('challanDate', None),
                benf_details.get('paymentStatus', 'Payment Initiated'),
                benf_details.get('paymentStatusMessage', None),
                Json(benf_details.get('additionalDetails', {})),
                benf_details.get('auditDetails', {}).get('createdTime'),
                benf_details.get('auditDetails', {}).get('createdBy'),
                benf_details.get('auditDetails', {}).get('lastModifiedTime'),
                benf_details.get('auditDetails', {}).get('lastModifiedBy')
            ])
            JIT_BENEFICIARY_LINEITEMS_INSERT_QUERY = """
                INSERT INTO jit_beneficiary_lineitems 
                (id, beneficiaryId, lineItemId, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
                VALUES (%s, %s, %s, %s, %s, %s, %s);
            """
            for idy, line_item in enumerate(benf_details['benfLineItems']):
                cursor.execute(JIT_BENEFICIARY_LINEITEMS_INSERT_QUERY, [
                    line_item.get('id'),
                    line_item.get('beneficiaryId'),
                    line_item.get('lineItemId'),
                    line_item.get('auditDetails', {}).get('createdTime'),
                    line_item.get('auditDetails', {}).get('createdBy'),
                    line_item.get('auditDetails', {}).get('lastModifiedTime'),
                    line_item.get('auditDetails', {}).get('lastModifiedBy')
                ])

        for idx, transaction_details in enumerate(payment_instruction['transactionDetails']):
            JIT_TRANSACTION_DETAILS_INSERT_QUERY = """
                INSERT INTO jit_transaction_details 
                (id, tenantId, sanctionId, paymentInstId, transactionAmount, transactionDate, transactionType, 
                additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
                VALUES (%s, %s, %s, %s, %s, %s, %s, 
                %s, %s, %s, %s, %s);
            """
            cursor.execute(JIT_TRANSACTION_DETAILS_INSERT_QUERY, [
                transaction_details.get('id'),
                transaction_details.get('tenantId'),
                transaction_details.get('sanctionId'),
                payment_instruction.get('id'),
                transaction_details.get('transactionAmount'),
                transaction_details.get('transactionDate'),
                transaction_details.get('transactionType', 'DEBIT'),
                Json(transaction_details.get('additionalDetails', {})),
                transaction_details.get('auditDetails', {}).get('createdTime'),
                transaction_details.get('auditDetails', {}).get('createdBy'),
                transaction_details.get('auditDetails', {}).get('lastModifiedTime'),
                transaction_details.get('auditDetails', {}).get('lastModifiedBy')
            ])

        connection.commit()
        print("update_payment_instruction_db : success")

    except Exception as e:
        print("update_payment_instruction_db : error {}".format(str(e)))
        traceback.print_exc()
        raise e


def delete_disburse_data_from_db(disbursement, cursor, connection):
    try:
        # Delete from eg_mukta_ifms_message_codes table
        delete_transaction_log_query = '''DELETE FROM eg_program_transaction_logs WHERE disburse_id = %s;'''
        cursor.execute(delete_transaction_log_query, (disbursement['id'],))

        delete_transaction_log_query = '''DELETE FROM ifix.eg_program_transaction_logs WHERE disburse_id = %s;'''
        cursor.execute(delete_transaction_log_query, (disbursement['id'],))

        # Delete from eg_mukta_ifms_message_codes table
        delete_message_query = '''DELETE FROM eg_mukta_ifms_message_codes WHERE id = %s;'''
        cursor.execute(delete_message_query, (disbursement['id'],))

        delete_message_query_from_program = '''DELETE FROM eg_program_message_codes WHERE id = %s;'''
        cursor.execute(delete_message_query_from_program, (disbursement['id'],))

        delete_message_query_from_program = '''DELETE FROM ifix.eg_program_message_codes WHERE id = %s;'''
        cursor.execute(delete_message_query_from_program, (disbursement['id'],))

        # Delete from eg_mukta_ifms_disburse table
        delete_disburse_query = '''DELETE FROM eg_mukta_ifms_disburse WHERE id = %s;'''
        cursor.execute(delete_disburse_query, (disbursement['id'],))

        delete_disburse_query_from_program = '''DELETE FROM eg_program_disburse WHERE id = %s;'''
        cursor.execute(delete_disburse_query_from_program, (disbursement['id'],))

        delete_disburse_query_from_program = '''DELETE FROM ifix.eg_program_disburse WHERE id = %s;'''
        cursor.execute(delete_disburse_query_from_program, (disbursement['id'],))

        for child in disbursement['children']:
            # Delete from eg_mukta_ifms_message_codes table
            delete_message_query = '''DELETE FROM eg_mukta_ifms_message_codes WHERE id = %s;'''
            cursor.execute(delete_message_query, (child['id'],))

            delete_message_query_from_program = '''DELETE FROM eg_program_message_codes WHERE id = %s;'''
            cursor.execute(delete_message_query_from_program, (child['id'],))

            delete_message_query_from_program = '''DELETE FROM ifix.eg_program_message_codes WHERE id = %s;'''
            cursor.execute(delete_message_query_from_program, (child['id'],))

            # Delete from eg_mukta_ifms_disburse table
            delete_disburse_query = '''DELETE FROM eg_mukta_ifms_disburse WHERE id = %s;'''
            cursor.execute(delete_disburse_query, (child['id'],))

            delete_disburse_query_from_program = '''DELETE FROM eg_program_disburse WHERE id = %s;'''
            cursor.execute(delete_disburse_query_from_program, (child['id'],))

            delete_disburse_query_from_program = '''DELETE FROM ifix.eg_program_disburse WHERE id = %s;'''
            cursor.execute(delete_disburse_query_from_program, (child['id'],))

        connection.commit()
    except Exception as e:
        print("delete_disburse_data_from_db : error {}".format(str(e)))
        raise e


def delete_payment_instruction_data_from_db(payment_instruction, cursor, connection):
    try:
        # Delete From transaction_details
        delete_transaction_query = '''DELETE FROM jit_transaction_details WHERE id = %s;'''
        if payment_instruction['transactionDetails'] is not None and len(payment_instruction['transactionDetails']) > 0:
            for transaction in payment_instruction['transactionDetails']:
                cursor.execute(delete_transaction_query, (transaction['id'],))

        # Delete From Pa Details
        delete_pa_query = '''DELETE FROM jit_payment_advice_details WHERE id = %s;'''
        if payment_instruction['paDetails'] is not None and len(payment_instruction['paDetails']) > 0:
            for pa in payment_instruction['paDetails']:
                cursor.execute(delete_pa_query, (pa['id'],))

        # Delete From pi status logs
        delete_pi_status_logs_query = '''DELETE FROM jit_pi_status_logs WHERE id = %s;'''
        if 'piStatusLogs' in payment_instruction and payment_instruction['piStatusLogs'] is not None and len(payment_instruction['piStatusLogs']) > 0:
            for pi_status_log in payment_instruction['piStatusLogs']:
                cursor.execute(delete_pi_status_logs_query, (pi_status_log['id'],))

        # Delete From benf line items
        delete_benf_line_item_query = '''DELETE FROM jit_beneficiary_lineitems WHERE id = %s;'''
        for beneficiary in payment_instruction['beneficiaryDetails']:
            for benf_line_item in beneficiary['benfLineItems']:
                cursor.execute(delete_benf_line_item_query, (benf_line_item['id'],))

        # Delete From benf details
        delete_benf_query = '''DELETE FROM jit_beneficiary_details WHERE id = %s;'''
        for beneficiary in payment_instruction['beneficiaryDetails']:
            cursor.execute(delete_benf_query, (beneficiary['id'],))

        # Delete From payment_instruction
        delete_pi_query = '''DELETE FROM jit_payment_inst_details WHERE id = %s;'''
        cursor.execute(delete_pi_query, (payment_instruction['id'],))

        # connection.commit()
    except Exception as e:
        print("delete_payment_instruction_data_from_db : error {}".format(str(e)))
        traceback.print_exc()

def process_successful_pi(processed_pi, benf_account_map, cursor, connection):
    try:
        for payment_number in processed_pi:
            print('process_successful_pi_for_payment_number {}'.format(payment_number))
            disburse_from_db = get_disburse_from_db(payment_number, cursor)
            if disburse_from_db == None:
                raise Exception('Disburse details not found for payment number {}'.format(payment_number))
            disbursements = search_disburse_from_program_service(disburse_from_db[0], disburse_from_db[1])
            disbursements = sorted(disbursements, key=lambda x: x['audit_details']['createdTime'])
            disbursements_for_delete = []
            first_disburse = disbursements[0]
            count = 0
            for disburse in disbursements:
                print('process_successful_pi_for_disburse {}'.format(disburse['target_id']))
                if count > 0:
                    disbursements_for_delete.append(disburse)
                count = count + 1

            payment_instructions = search_payment_instruction_from_ifms_adapter(payment_number, disbursements[0]['location_code'])
            payment_instructions_for_delete = []
            payment_instructions = sorted(payment_instructions, key=lambda x: x['auditDetails']['createdTime'])
            first_payment_instruction = payment_instructions[0]
            count = 0
            for payment_instruction in payment_instructions:
                print('process_successful_pi_payment_instruction {}'.format(payment_instruction['id']))
                if count > 0:
                    payment_instructions_for_delete.append(payment_instruction)
                count = count + 1

            pi_number = next(iter(processed_pi.get(payment_number)))
            print('pi_number {}'.format(pi_number))
            pi_json = processed_pi.get(payment_number).get(pi_number);
            print('pi_json {}'.format(pi_json.get('PAYMENT_NO')))
            migrate_payment_instruction(first_disburse, first_payment_instruction, pi_json, benf_account_map, cursor, connection)

            if len(disbursements_for_delete) > 0:
                for disburse in disbursements_for_delete:
                    print('delete_disburse_data_from_db_disburse {}'.format(disburse['id']))
                    delete_disburse_data_from_db(disburse, cursor, connection)


            if len(payment_instructions_for_delete) > 0:
                for payment_instruction in payment_instructions_for_delete:
                    print('delete_payment_instruction_data_from_db_for_disburse {}'.format(payment_instruction['id']))
                    delete_payment_instruction_data_from_db(payment_instruction, cursor, connection)
                    connection.commit()

    except Exception as e:
        print("process_successfull_pi : error {}".format(str(e)))
        traceback.print_exc()
        raise e


def call_on_disburse_update_api(disburse):
    try:
        program_service_host = os.getenv('PROGRAM_SERVICE_HOST')
        program_disburse_update = os.getenv('PROGRAM_ON_DISBURSE_CREATE')

        api_url = f"{program_service_host}/{program_disburse_update}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "signature": None,
            "header": {
                "message_id": disburse['id'],
                "message_ts": "1708428280",
                "message_type": "on-disburse",
                "action": "create",
                "sender_id": os.getenv('PROGRAM_DISBURSE_UPDATE_SENDER_ID'),
                "receiver_id": os.getenv('PROGRAM_DISBURSE_UPDATE_RECEIVER_ID')
            },
            "message": disburse
        }

        response = requests.post(api_url, json=request, headers=headers)
        if response.status_code == 200:
            print(f"Disbursement updated for payment number: {disburse['target_id']}")
        else:
            print(f"Failed to update disburse for {disburse['target_id']} from the API. Status code: {response.status_code}")
            print(response.text)
    except Exception as e:
        print("search_disburse_from_program_service : error {}".format(str(e)))
        raise e

def process_initiated_inprogress_pis_for_fail(pis_to_fail_inprogress, payments_to_be_fail, cursor, connection):
    try:
        for payment_number in payments_to_be_fail:
            print('process_initiated_inprogress_pis_for_fail_for_payment_number {}'.format(payment_number))
            disburse_from_db = get_disburse_from_db(payment_number, cursor)
            if disburse_from_db == None:
                raise Exception('Disburse details not found for payment number {}'.format(payment_number))
            disbursements = search_disburse_from_program_service(disburse_from_db[0], disburse_from_db[1])
            payment_instructions = search_payment_instruction_from_ifms_adapter(payment_number, disbursements[0]['location_code'])
            if len(payment_instructions) > 0:
                for payment_instruction in payment_instructions:
                    print('process_initiated_inprogress_pis_for_fail_payment_instruction {}'.format(payment_instruction.get('id')))
                    if (payment_instruction['piStatus'] != 'FAILED'):
                        upate_pi_to_fail_in_db(payment_instruction, cursor, connection)

            if len(disbursements) > 0:
                for disburse in disbursements:
                    if disburse['status']['status_code'] == 'INITIATED' or disburse['status']['status_code'] == 'INPROGRESS':
                        print('process_initiated_inprogress_pis_for_fail_disburse {}'.format(disburse.get('id')))
                        update_disburse_to_fail(disburse)
    except Exception as e:
        print("process_initiated_inprogress_pis_for_fail : error {}".format(str(e)))
        traceback.print_exc()

def upate_pi_to_fail_in_db(payment_instruction, cursor, connection):
    try:
        payment_instruction['piStatus'] = 'FAILED'
        update_query = '''UPDATE jit_payment_inst_details SET pistatus = %s WHERE id = %s;'''
        cursor.execute(update_query, (payment_instruction['piStatus'], payment_instruction['id']))
        update_query = '''UPDATE jit_beneficiary_details SET paymentStatus = %s WHERE piId = %s;'''
        cursor.execute(update_query, ('Payment Failed', payment_instruction['id']))
        for beneficiary in payment_instruction['beneficiaryDetails']:
            beneficiary['paymentStatus'] = 'Payment Failed'
        transaction_details = copy.deepcopy(payment_instruction['transactionDetails'][0]);
        transaction_details['id'] = str(uuid.uuid4())
        transaction_details['transactionType'] = 'REVERSAL'
        payment_instruction['transactionDetails'].append(transaction_details)

        JIT_TRANSACTION_DETAILS_INSERT_QUERY = """
            INSERT INTO jit_transaction_details 
            (id, tenantId, sanctionId, paymentInstId, transactionAmount, transactionDate, transactionType, 
            additionalDetails, createdtime, createdby, lastmodifiedtime, lastmodifiedby)
            VALUES (%s, %s, %s, %s, %s, %s, %s, 
            %s, %s, %s, %s, %s);
        """
        cursor.execute(JIT_TRANSACTION_DETAILS_INSERT_QUERY, [
            transaction_details.get('id'),
            transaction_details.get('tenantId'),
            transaction_details.get('sanctionId'),
            payment_instruction.get('id'),
            transaction_details.get('transactionAmount'),
            transaction_details.get('transactionDate'),
            transaction_details.get('transactionType', 'DEBIT'),
            Json(transaction_details.get('additionalDetails', {})),
            transaction_details.get('auditDetails', {}).get('createdTime'),
            transaction_details.get('auditDetails', {}).get('createdBy'),
            transaction_details.get('auditDetails', {}).get('lastModifiedTime'),
            transaction_details.get('auditDetails', {}).get('lastModifiedBy')
        ])
        connection.commit()

        # TODO:push to indexer
        push_pi_data_for_indexer(payment_instruction)

    except Exception as e:
        print("upate_pi_to_fail : error {}".format(str(e)))
        traceback.print_exc()

def update_disburse_to_fail(disburse):
    try:
        print('updating disbursement for fail {}'.format(disburse['id']))
        disburse['status'] = {
            'status_code': 'FAILED',
            'status_message': 'FAILED'
        }
        for benficiary in disburse['children']:
            benficiary['status'] = {
                'status_code': 'FAILED',
                'status_message': 'FAILED'
            }
            call_on_disburse_update_api(disburse)
    except Exception as e:
        print("update_disburse_to_fail : error {}".format(str(e)))
        traceback.print_exc()


def store_sanction_amounts_for_backup(processed_pi, in_progress_pi, cursor, connection):
    print('store_sanction_amounts_for_backup')
    try:
        cursor.execute('''CREATE TABLE IF NOT EXISTS ifms_prod_issue_transaction_table (
            payment_no VARCHAR(255),
            jit_bill_no VARCHAR(255),
            sanction_id VARCHAR(255),
            master_allotment_id VARCHAR(255),
            allotment_id VARCHAR(255),
            amount DECIMAL(10, 2),
            status VARCHAR(50)
        );''')
        connection.commit()
        for payment in processed_pi:
            for pi in processed_pi.get(payment):
                payment_instruction = processed_pi.get(payment).get(pi)
                print('store_sanction_amounts_for_backup_pi {}'.format(pi))
                cursor.execute('''select sanctionid from jit_allotment_details where ssuallotmentid = %s''', (str(payment_instruction['AW_ALLOTMENT_DIST_ID']),))
                result = cursor.fetchone()
                sanction_id = result[0]

                cursor.execute('''INSERT INTO ifms_prod_issue_transaction_table
                    (payment_no, jit_bill_no, sanction_id, master_allotment_id, allotment_id, amount, status)
                    VALUES (%s, %s, %s, %s, %s, %s, %s)''', (payment_instruction['PAYMENT_NO'], payment_instruction['JIT_BILL_NUMBER'], sanction_id, str(payment_instruction['MST_ALLOTMENT_DIST_ID']), str(payment_instruction['AW_ALLOTMENT_DIST_ID']), payment_instruction['GROSS_AMOUNT'], 'SUCCESS'))
                connection.commit()

        for payment in in_progress_pi:
            for pi in in_progress_pi.get(payment):
                payment_instruction = in_progress_pi.get(payment).get(pi)
                print('store_sanction_amounts_for_backup_pi {}'.format(pi))
                cursor.execute('''select sanctionid from jit_allotment_details where ssuallotmentid = %s''',(str(payment_instruction['AW_ALLOTMENT_DIST_ID']),))
                result = cursor.fetchone()
                sanction_id = result[0]

                cursor.execute('''INSERT INTO ifms_prod_issue_transaction_table
                    (payment_no, jit_bill_no, sanction_id, master_allotment_id, allotment_id, amount, status)
                    VALUES (%s, %s, %s, %s, %s, %s, %s)''', (payment_instruction['PAYMENT_NO'], payment_instruction['JIT_BILL_NUMBER'], sanction_id, str(payment_instruction['MST_ALLOTMENT_DIST_ID']), str(payment_instruction['AW_ALLOTMENT_DIST_ID']), payment_instruction['GROSS_AMOUNT'], 'FAILED'))
                connection.commit()

    except Exception as e:
        print("store_sanction_amounts_for_backup : error {}".format(str(e)))
        traceback.print_exc()

def push_pi_data_for_indexer(payment_instruction):
    payment_instruction['paDetails'] = None
    payment_instruction['piType']= 'ORIGINAL';
    payment_instruction['parentPiNumber'] = '';
    payment_instruction['piErrorResp'] = ''
    producer = KafkaProducer(bootstrap_servers=os.getenv('KAFKA_SERVER'))
    request = {}
    request['RequestInfo'] = get_request_info()
    request['paymentInstruction'] = payment_instruction
    json_data = json.dumps(request).encode('utf-8')
    producer.send(os.getenv('KAFKA_UPDATE_IFMS_PI_INDEX_TOPIC'), json_data)
    producer.send(os.getenv('KAFKA_UPDATE_MUKTA_PI_INDEX_TOPIC'), json_data)
    producer.flush()
    producer.close()

def process_pi():
    processed_pi = load_file('processed_pi_output.json')
    in_progress_pi = load_file('in_progress_pi_output.json')
    benf_account_map = load_file('benf_account.json')
    [pis_to_save_processed, pis_to_fail_inprogress, pis_to_delete_inprogress, pis_to_delete_processed] = get_processed_in_progress_pi_and_failed_pis(processed_pi, in_progress_pi)

    # Connect to PostgreSQL
    connection = connect_to_database()
    # Create a cursor object
    cursor = connection.cursor()

    process_successful_pi(processed_pi, benf_account_map, cursor, connection)
    in_progress = in_progress_pi.keys()
    success = processed_pi.keys()
    payments_to_be_fail = set(in_progress) - set(success)
    process_initiated_inprogress_pis_for_fail(in_progress_pi, payments_to_be_fail, cursor, connection)
    store_sanction_amounts_for_backup(processed_pi, in_progress_pi, cursor, connection)



def search_payment_instruction_by_id(id, tenant_id):
    try:
        ifms_service_host = os.getenv('IFMS_HOST')
        ifms_pi_search = os.getenv('IFMS_PI_SEARCH')

        api_url = f"{ifms_service_host}/{ifms_pi_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "searchCriteria": {
                "tenantId": tenant_id,
                "ids": [id],
                "limit": "10000",
                "offset": "0",
                "sortBy": "createdTime",
                "order": "ASC"
            }
        }

        response = requests.post(api_url, json=request, headers=headers)
        paymentInstructions = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            paymentInstructions.extend(response_data.get('paymentInstructions', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return paymentInstructions[0]
    except Exception as e:
        print("search_payment_instruction_from_ifms_adapter : error {}".format(str(e)))
        raise e


def reindex():
    try:
        payment_inst = [
            {"id": "38be1320-7864-4cee-adc2-cf264dba9bb6", "tenantid": "od.balasore"},
            {"id": "d157c4c8-554b-4ba8-ba11-7ea0e2f0fcbe", "tenantid": "od.balasore"},
            {"id": "04b5e39c-6d01-4876-bb1c-bed28ba772e8", "tenantid": "od.balasore"},
            {"id": "86295294-2422-4101-b23f-0f4bdc6e7c44", "tenantid": "od.balasore"},
            {"id": "3de62d8e-044b-45d6-adf8-ce3ad2220dac", "tenantid": "od.balasore"},
            {"id": "4ba54060-ce76-4294-aa9c-492017535292", "tenantid": "od.balasore"},
            {"id": "994b4094-2e53-4f63-af3e-f867a7109345", "tenantid": "od.balasore"},
            {"id": "bb43d19c-4880-4fe7-8030-e2e7aba29d04", "tenantid": "od.balasore"},
            {"id": "e2dbab5a-eb12-4f2b-a543-42ad5d0d69ba", "tenantid": "od.balasore"},
            {"id": "20a0d3ae-5ed6-403a-8106-2d435652e28f", "tenantid": "od.padampur"},
            {"id": "4cdce082-2ba3-4a97-959b-d1fec5e4156b", "tenantid": "od.balasore"},
            {"id": "1eee9e3d-2caf-481c-af86-4772518d904c", "tenantid": "od.balasore"},
            {"id": "8737cbf0-cc44-48a4-8e9c-d6417f2596bb", "tenantid": "od.balasore"},
            {"id": "cde2db94-3405-480b-993b-4e6355f322ba", "tenantid": "od.balasore"},
            {"id": "7579062e-169b-4dc4-b1d3-5f936b2719de", "tenantid": "od.balasore"},
            {"id": "e7f0a2c1-2c01-4390-ba9c-bdf957d2afba", "tenantid": "od.boudhgarh"},
            {"id": "3ab1e22a-141a-469a-8fe6-7c77d99ab693", "tenantid": "od.padampur"},
            {"id": "cf95c4d5-8787-4249-ae2d-03aa48499f2d", "tenantid": "od.padampur"},
            {"id": "4287ff20-83be-45b6-ba5c-4de17453e2bc", "tenantid": "od.padampur"},
            {"id": "fd3b98b6-1c47-497a-8dad-5068854dd437", "tenantid": "od.padampur"},
            {"id": "c0fea4db-9e0f-4559-be82-3f881e1c43fc", "tenantid": "od.padampur"},
            {"id": "ba5f257c-5c81-4be5-abde-e640af0f6609", "tenantid": "od.boudhgarh"},
            {"id": "3bec52cc-bd0e-4150-bfc9-ed7a7ff5dd37", "tenantid": "od.boudhgarh"},
            {"id": "0ca31410-7dc2-4533-bb5a-8a65bfd796fc", "tenantid": "od.bhadrak"},
            {"id": "3510271b-5cb1-4196-bc73-fa14cfd65a65", "tenantid": "od.bhadrak"},
            {"id": "17a2f474-a8ab-4f19-98fd-7a4d0361ff8f", "tenantid": "od.balangir"},
            {"id": "e544d710-aa90-4039-b7ef-eb9ccfab7af2", "tenantid": "od.balangir"},
            {"id": "54d92561-b4bf-4900-b8a8-8031d02ebaca", "tenantid": "od.balangir"},
            {"id": "3cc5485b-ef40-4a74-9cb3-1ce831b7038e", "tenantid": "od.padampur"},
            {"id": "b0f59eff-9c3e-4f49-aecd-e14827aea0fc", "tenantid": "od.padampur"},
            {"id": "ea234b7f-859a-44af-9914-c2f9b416568a", "tenantid": "od.padampur"},
            {"id": "3796bd72-b2b2-4efa-aa6b-7754926384e0", "tenantid": "od.padampur"},
            {"id": "697ff1a9-e571-43c9-9060-d371b8786e22", "tenantid": "od.padampur"},
            {"id": "28b4c6db-fe67-471b-9c68-c83c0886e912", "tenantid": "od.padampur"},
            {"id": "ce4f74d0-e9e5-433e-a327-e51f2bb75c01", "tenantid": "od.padampur"},
            {"id": "71c048da-4ef7-4be4-92f6-27a9a15b27d6", "tenantid": "od.balangir"},
            {"id": "c0a1576a-040f-427c-ba62-7904d231e78a", "tenantid": "od.balangir"},
            {"id": "5a62c99b-da73-4423-8d1b-13536ec3c3b0", "tenantid": "od.balangir"},
            {"id": "5a4f3226-6ef1-4e84-aa3f-0648b736e672", "tenantid": "od.balangir"},
            {"id": "9ab33ba8-fbfd-4e2f-bc51-9904f6468099", "tenantid": "od.balangir"},
            {"id": "eb0474d4-c10e-40c0-8cbd-d91939556123", "tenantid": "od.balangir"},
            {"id": "0d054b6e-728d-44ef-a7ee-1cf28e9ebcba", "tenantid": "od.balangir"},
            {"id": "2feb351d-7962-4823-942c-c645d7eba106", "tenantid": "od.balangir"},
            {"id": "6690d6fa-77bb-4c9b-baa6-0a3267391bb6", "tenantid": "od.balangir"},
            {"id": "f248b861-8385-4b0d-9aea-55e4c6c68ee4", "tenantid": "od.balangir"},
            {"id": "6e67f4b2-3dbf-4011-9afe-a690ce1b4992", "tenantid": "od.balangir"},
            {"id": "1ebbce02-9ef8-4021-81b1-4ff0c1f1c76b", "tenantid": "od.balangir"},
            {"id": "ee65d1bb-f6f4-47a0-bca2-d7dad40e169d", "tenantid": "od.balangir"},
            {"id": "3c21738f-165a-4a8f-b1e8-e96eb8e69d4d", "tenantid": "od.balangir"},
            {"id": "b8948fa9-72c0-4e05-af1e-93acde123255", "tenantid": "od.padampur"},
            {"id": "651944a4-df5d-452c-8200-68dd1bd61df0", "tenantid": "od.padampur"},
            {"id": "6b089e76-0846-4a0f-9956-435bdaf6447c", "tenantid": "od.padampur"},
            {"id": "524f1110-2492-4fda-8d15-8c046fc366c8", "tenantid": "od.balangir"},
            {"id": "271cd846-bffe-4f4c-b1bf-9ff6c729464d", "tenantid": "od.balangir"},
            {"id": "ac5e145e-9bc5-48ac-ba87-4ee548808676", "tenantid": "od.balangir"},
            {"id": "f2305aa9-5c28-4621-a306-23f29deea8a4", "tenantid": "od.balangir"},
            {"id": "eca47fc2-e861-4f7f-8b42-152f6a870dcc", "tenantid": "od.balangir"},
            {"id": "56cfd3ac-93b7-4eed-994e-c22487a3d91c", "tenantid": "od.balangir"},
            {"id": "dfeddfdf-c840-4d22-9b4b-13999e86cf8d", "tenantid": "od.balangir"},
            {"id": "0efcf4cb-9dd6-4f99-98f3-c9abbd07dc70", "tenantid": "od.balangir"},
            {"id": "c31c9693-15f9-4b94-ba9f-afb6885796c4", "tenantid": "od.balangir"},
            {"id": "0ac164c4-40e6-49c7-a158-fa06e4b4cb67", "tenantid": "od.balangir"},
            {"id": "aaac9b97-5dda-4696-83eb-d41bfd342ff2", "tenantid": "od.balangir"},
            {"id": "65549931-9406-42c4-98a7-5715b0fb5c19", "tenantid": "od.balangir"},
            {"id": "d2f66dec-3520-4f80-b0ce-37275944ff66", "tenantid": "od.balangir"},
            {"id": "018e3117-779b-4de8-8378-0ebe994de90b", "tenantid": "od.dhenkanal"},
            {"id": "e3d63f11-c38e-41d8-b11c-ce0cffe121ce", "tenantid": "od.kotpad"},
            {"id": "961c21cf-8d5f-4a21-8f31-0a09e192c2a7", "tenantid": "od.kotpad"},
            {"id": "a601c225-a2e6-4460-bf19-e8ee834b1e2d", "tenantid": "od.kotpad"},
            {"id": "bf3bdac1-92da-4e3d-98eb-a01f1ed0a88d", "tenantid": "od.kotpad"},
            {"id": "1fa5b73c-c5b3-41ed-b1ea-f00e898b0719", "tenantid": "od.kotpad"},
            {"id": "835f8a18-83ae-4178-8fbe-ac37a60b30ed", "tenantid": "od.kotpad"},
            {"id": "bfef506d-3077-4524-99f8-23c0ece9475b", "tenantid": "od.kotpad"},
            {"id": "b22da4b2-130b-4bea-8d48-c419fce990b0", "tenantid": "od.kotpad"},
            {"id": "9410623e-d549-4da0-9709-2119bd193854", "tenantid": "od.padampur"},
            {"id": "30a5d15d-9805-4dd7-88de-4aa4bd14e648", "tenantid": "od.padampur"},
            {"id": "f1aeacf5-2ca8-4664-a8b8-b82c25f0a821", "tenantid": "od.padampur"},
            {"id": "3056011f-d2fb-4c05-a454-c1233c160f12", "tenantid": "od.kotpad"},
            {"id": "9e830020-c324-44f9-b775-4964240a5d65", "tenantid": "od.kotpad"},
            {"id": "a5cd036e-809f-4c61-8b3c-e4607d404f64", "tenantid": "od.kotpad"},
            {"id": "c7b7e20a-4d9d-47c6-bef9-3ac91d4cf94c", "tenantid": "od.kotpad"},
            {"id": "154fe2d2-340f-449c-b354-eafba7848264", "tenantid": "od.padampur"},
            {"id": "03554fb6-cb98-4ff8-bf4f-cafcda06992a", "tenantid": "od.padampur"},
            {"id": "a2445c1d-9711-4239-b809-e78d7328397f", "tenantid": "od.padampur"},
            {"id": "40015af4-c1d4-410d-ae78-2b3524220480", "tenantid": "od.padampur"},
            {"id": "a5468dce-4234-4131-8a93-12232b2d79cc", "tenantid": "od.paradeep"},
            {"id": "e93aee2e-fcf6-4e70-a296-b362e3619185", "tenantid": "od.paradeep"},
            {"id": "56b56907-23b1-47b7-b136-160157c045d4", "tenantid": "od.paradeep"},
            {"id": "602fcea8-2717-4e98-9f7e-c3042e105d6d", "tenantid": "od.paradeep"},
            {"id": "4e5c0fc6-9bd2-48f8-a320-ca5d2b331376", "tenantid": "od.paradeep"},
            {"id": "c849682f-0fab-4e6e-b484-b0cfd15270b3", "tenantid": "od.paradeep"},
            {"id": "08ca1d18-ec55-43db-8385-fa74a56a5b78", "tenantid": "od.paradeep"},
            {"id": "96cfbdf9-7677-4362-8f50-bd755bbb0c20", "tenantid": "od.paradeep"},
            {"id": "e128e4ad-10da-4061-9888-6bea7fcb9e21", "tenantid": "od.paradeep"},
            {"id": "0c41e8b1-7c26-408a-8dac-8c14476f8c22", "tenantid": "od.paradeep"},
            {"id": "e4a7a43e-8737-4bdd-adee-d636b25ef72e", "tenantid": "od.paradeep"},
            {"id": "2bd55edf-09a9-4a3a-b8a0-ef63448c6319", "tenantid": "od.paradeep"},
            {"id": "1610078d-e10b-4b0d-a78d-0ed26261c8ea", "tenantid": "od.paradeep"},
            {"id": "7aeca68d-a388-449e-a06e-05e506946383", "tenantid": "od.paradeep"},
            {"id": "a2fff63e-0812-4f33-b3bd-f023f0dafc51", "tenantid": "od.paradeep"},
            {"id": "52eaf507-650e-4633-be33-0404193d56b3", "tenantid": "od.paradeep"},
            {"id": "3e7ca78e-33f3-4026-9a24-e8e16ea1e034", "tenantid": "od.paradeep"},
            {"id": "64475b84-a04a-40aa-846d-cf4f3e7ce7a9", "tenantid": "od.paradeep"},
            {"id": "4c77a534-1814-41aa-b2ba-c13e2c836660", "tenantid": "od.paradeep"},
            {"id": "f9932039-6d02-49eb-97fa-ad8b84a92bce", "tenantid": "od.paradeep"},
            {"id": "1481f97a-278e-4159-a9e4-f65707748cba", "tenantid": "od.paradeep"},
            {"id": "2a1cdf60-8cd4-4207-aad9-d70a8718b14e", "tenantid": "od.paradeep"},
            {"id": "5850f245-d641-447f-8acd-a4477326dfd3", "tenantid": "od.paradeep"},
            {"id": "74ac982f-34ea-4648-abc5-be4e72b91a45", "tenantid": "od.paradeep"},
            {"id": "92084e88-1396-41e5-960b-6099141db26b", "tenantid": "od.paradeep"},
            {"id": "bca9f810-c355-4c33-aefa-cb7ae7d36639", "tenantid": "od.paradeep"},
            {"id": "ed586af2-5e01-4698-859d-1b9e8e49cfae", "tenantid": "od.paradeep"},
            {"id": "b0c49069-3a77-4361-84d9-ba5f1938b412", "tenantid": "od.rourkela"},
            {"id": "842a64e4-b1e0-40ac-81ff-b94af8b777a1", "tenantid": "od.rourkela"}
        ]
        print("reindex : start")
        producer = KafkaProducer(bootstrap_servers=os.getenv('KAFKA_SERVER'))
        for pi in payment_inst:
            payment_inst_details = search_payment_instruction_by_id(pi.get('id'), pi.get('tenantid'))
            payment_inst_details['paDetails'] = None
            payment_inst_details['piType'] = 'ORIGINAL'
            payment_inst_details['parentPiNumber'] = ''
            payment_inst_details['piErrorResp'] = ''
            request = {}
            request['RequestInfo'] = get_request_info()
            request['paymentInstruction'] = payment_inst_details
            json_data = json.dumps(request).encode('utf-8')
            producer.send(os.getenv('KAFKA_UPDATE_MUKTA_PI_INDEX_TOPIC'), json_data)
            producer.flush()
        producer.close()
        print("reindex : end")
    except Exception as e:
        print("reindex : error {}".format(str(e)))
        traceback.print_exc()

if __name__ == '__main__':
    # process_pi()
    reindex()




