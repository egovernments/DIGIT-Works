import pandas as pd
import requests
import json
import os
import math
import numpy as np
from dotenv import load_dotenv
import uuid
import copy
import traceback

load_dotenv('.env')

def get_request_info():
    return {
        "apiId": "Rainmaker",
        "authToken": "23d9d4e0-5eca-41cc-8919-7a5857a9a30d",
        "userInfo": {
            "id": 357,
            "uuid": "4ec9da90-ef66-47c8-8a0b-eb87d8cf9c31",
            "userName": "EMPJIT",
            "name": "Sumana Naga sai",
            "mobileNumber": "9987656765",
            "type": "EMPLOYEE",
            "roles": [
                {
                    "name": "ESTIMATE APPROVER",
                    "code": "ESTIMATE_APPROVER",
                    "tenantId": "od"
                },
                {
                    "name": "WORK ORDER CREATOR",
                    "code": "WORK_ORDER_CREATOR",
                    "tenantId": "od"
                },
                {
                    "name": "Organization viewer",
                    "code": "ORG_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "MB_VERIFIER",
                    "code": "MB_VERIFIER",
                    "tenantId": "od"
                },
                {
                    "name": "ESTIMATE CREATOR",
                    "code": "ESTIMATE_CREATOR",
                    "tenantId": "od"
                },
                {
                    "name": "MB_VIEWER",
                    "code": "MB_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "MUKTA Admin",
                    "code": "MUKTA_ADMIN",
                    "tenantId": "od"
                },
                {
                    "name": "MDMS STATE VIEW ADMIN",
                    "code": "MDMS_STATE_VIEW_ADMIN",
                    "tenantId": "od"
                },
                {
                    "name": "Employee Common",
                    "code": "EMPLOYEE_COMMON",
                    "tenantId": "od"
                },
                {
                    "name": "TECHNICAL SANCTIONER",
                    "code": "TECHNICAL_SANCTIONER",
                    "tenantId": "od"
                },
                {
                    "name": "BILL_CREATOR",
                    "code": "BILL_CREATOR",
                    "tenantId": "od"
                },
                {
                    "name": "WORK_ORDER_VIEWER",
                    "code": "WORK_ORDER_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "BILL_ACCOUNTANT",
                    "code": "BILL_ACCOUNTANT",
                    "tenantId": "od"
                },
                {
                    "name": "ESTIMATE VERIFIER",
                    "code": "ESTIMATE_VERIFIER",
                    "tenantId": "od"
                },
                {
                    "name": "BILL_VERIFIER",
                    "code": "BILL_VERIFIER",
                    "tenantId": "od"
                },
                {
                    "name": "MUSTER ROLL APPROVER",
                    "code": "MUSTER_ROLL_APPROVER",
                    "tenantId": "od"
                },
                {
                    "name": "ESTIMATE VIEWER",
                    "code": "ESTIMATE_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "WORK ORDER APPROVER",
                    "code": "WORK_ORDER_APPROVER",
                    "tenantId": "od"
                },
                {
                    "name": "MB_APPROVER",
                    "code": "MB_APPROVER",
                    "tenantId": "od"
                },
                {
                    "name": "SUPER USER",
                    "code": "SUPERUSER",
                    "tenantId": "od"
                },
                {
                    "name": "OFFICER IN CHARGE",
                    "code": "OFFICER_IN_CHARGE",
                    "tenantId": "od"
                },
                {
                    "name": "PROJECT CREATOR",
                    "code": "PROJECT_CREATOR",
                    "tenantId": "od"
                },
                {
                    "name": "BILL_VIEWER",
                    "code": "BILL_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "WORK ORDER VERIFIER",
                    "code": "WORK_ORDER_VERIFIER",
                    "tenantId": "od"
                },
                {
                    "name": "PROJECT VIEWER",
                    "code": "PROJECT_VIEWER",
                    "tenantId": "od"
                },
                {
                    "name": "BILL_APPROVER",
                    "code": "BILL_APPROVER",
                    "tenantId": "od"
                },
                {
                    "name": "MB_CREATOR",
                    "code": "MB_CREATOR",
                    "tenantId": "od"
                },
                {
                    "name": "MUSTER ROLL VERIFIER",
                    "code": "MUSTER_ROLL_VERIFIER",
                    "tenantId": "od"
                },
                {
                    "name": "HRMS Admin",
                    "code": "HRMS_ADMIN",
                    "tenantId": "od"
                }
            ],
            "active": True,
            "tenantId": "od",
            "permanentCity": None
        },
        "msgId": "1716454404481|en_IN",
        "plainAccessRequest": {}
    }
def prepare_data_for_composition(data):
    PREV_WORKS_SOR_CODE = None
    PREV_WORKS_SOR_UNIT = None
    PREV_WORKS_SOR_COMP_QTY = None
    basic_sors_by_compositions = []
    sor_composition_map = {}
    for row in data:
        mainObj = {}
        if not is_nan_or_none(row.get('WORKS_SOR_CODE')):
            PREV_WORKS_SOR_CODE = row.get('WORKS_SOR_CODE')
        if not is_nan_or_none(row.get('WORKS_SOR_UNIT')):
            PREV_WORKS_SOR_UNIT = row.get('WORKS_SOR_UNIT')
        if not is_nan_or_none(row.get('WORKS_SOR_COMP_QTY')):
            PREV_WORKS_SOR_COMP_QTY = row.get('WORKS_SOR_COMP_QTY')

        mainObj['WORKS_SOR_CODE'] = PREV_WORKS_SOR_CODE if is_nan_or_none(row.get('WORKS_SOR_CODE')) else row.get('WORKS_SOR_CODE')
        mainObj['WORKS_SOR_UNIT'] = PREV_WORKS_SOR_UNIT if is_nan_or_none(row.get('WORKS_SOR_UNIT')) else row.get('WORKS_SOR_UNIT')
        mainObj['WORKS_SOR_UNIT'] = PREV_WORKS_SOR_UNIT if is_nan_or_none(row.get('WORKS_SOR_UNIT')) else row.get('WORKS_SOR_UNIT')
        mainObj['WORKS_SOR_COMP_QTY'] = PREV_WORKS_SOR_COMP_QTY if is_nan_or_none(row.get('WORKS_SOR_COMP_QTY')) else row.get('WORKS_SOR_COMP_QTY')
        mainObj['SOR_TYPE'] = row.get('SOR_TYPE')
        mainObj['SOR_CODE'] = row.get('SOR_CODE')
        mainObj['SOR_DESCRIPTION'] = row.get('SOR_DESCRIPTION')
        mainObj['SOR_UNIT'] = row.get('SOR_UNIT')
        mainObj['QUANTITY'] = row.get('QUANTITY')
        if not is_nan_or_none(mainObj['SOR_CODE']):
            basic_sors_by_compositions.append(mainObj.copy())

    for basic_sor in basic_sors_by_compositions:
        if is_nan_or_none(sor_composition_map.get(basic_sor.get('WORKS_SOR_CODE'))):
            sor_comp_obj = {}
            sor_comp_obj['sorId'] = basic_sor.get('WORKS_SOR_CODE')
            sor_comp_obj['sorType'] = 'W'
            sor_comp_obj['type'] = 'SOR'
            sor_comp_obj['quantity'] = basic_sor.get('WORKS_SOR_COMP_QTY')
            sor_comp_obj['active'] = True
            sor_comp_obj['effectiveFrom'] = '1696118400000'
            sor_comp_obj['basicSorDetails'] = []
            sor_composition_map[basic_sor.get('WORKS_SOR_CODE')] = sor_comp_obj
        sor_child = {
            "sorId": basic_sor.get('SOR_CODE'),
            "quantity": basic_sor.get('QUANTITY'),
            "perUnitQty": round(basic_sor['QUANTITY'] / basic_sor['WORKS_SOR_COMP_QTY'], 4)
        }
        if sor_composition_map.get(basic_sor.get('WORKS_SOR_CODE')):
            sor_composition_map[basic_sor.get('WORKS_SOR_CODE')]['basicSorDetails'].append(sor_child)

    return sor_composition_map

def get_applicable_on():
    applicable_on_map = {
        ''
    }
def prepare_extra_charges(data):
    sor_extra_charges = {}
    for row in data:
        extra_charges = {
            "figure": None,
            "description": None,
            "applicableOn": None,
            "calculationType": None
        }
        if not is_nan_or_none(row.get('SOR_Code')):
            if sor_extra_charges.get(row.get('SOR_Code')) == None:
                sor_extra_charges[row.get('SOR_Code')] = []
            extra_charges['figure'] = row.get('Figure')
            extra_charges['description'] = row.get('Description')
            extra_charges['applicableOn'] = row.get('ApplicableOn')
            extra_charges['calculationType'] = row.get('CalculationType')
            sor_extra_charges[row.get('SOR_Code')].append(extra_charges)
    return sor_extra_charges

def get_extra_charges():
    # Get the current working directory
    current_directory = os.getcwd()
    # Path to your Excel file
    file_name = 'Rate_analysis_for_composition.xlsx'
    # Combine the directory and file name to get the full file path
    file_path = os.path.join(current_directory, file_name)
    excel_file = file_path

    # Name of the sheet you want to read
    sheet_name = 'EXTRA_CHARGES'  # Change this to the actual sheet name

    # Read the Excel file into a DataFrame
    df = pd.read_excel(excel_file, sheet_name=sheet_name)

    # Strip leading and trailing spaces from all columns
    df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)

    # Replace new lines in cells with a space
    df = df.applymap(lambda x: x.replace('\n', ' ') if isinstance(x, str) else x)

    # Round numeric values to 4 decimal places
    df = df.applymap(lambda x: round(x, 4) if isinstance(x, (int, float, np.number)) else x)

    # Convert DataFrame to dictionary
    data_dict = df.to_dict(orient='records')

    extra_charges = prepare_extra_charges(data_dict)

    # Print the dictionary
    print(extra_charges)
    # If you want to write JSON data to a file, you can use the following:
    with open(os.path.join(current_directory, 'sor_extra_charges.json'), 'w') as f:
        json.dump(extra_charges, f)

    return extra_charges

def get_sor_composition():
    # Get the current working directory
    current_directory = os.getcwd()
    # Path to your Excel file
    file_name = 'Rate_analysis_for_composition_prod.xlsx'
    # Combine the directory and file name to get the full file path
    file_path = os.path.join(current_directory, file_name)
    excel_file = file_path

    # Name of the sheet you want to read
    sheet_name = 'SOR_COMPOSITION'  # Change this to the actual sheet name

    # Read the Excel file into a DataFrame
    df = pd.read_excel(excel_file, sheet_name=sheet_name)

    # Strip leading and trailing spaces from all columns
    df = df.map(lambda x: x.strip() if isinstance(x, str) else x)

    # Replace new lines in cells with a space
    df = df.map(lambda x: x.replace('\n', ' ') if isinstance(x, str) else x)

    # Round numeric values to 4 decimal places
    df = df.map(lambda x: round(x, 4) if isinstance(x, (int, float, np.number)) else x)

    # Convert DataFrame to dictionary
    data_dict = df.to_dict(orient='records')

    processed_sor = prepare_data_for_composition(data_dict)

    # Print the dictionary
    print(processed_sor)
    # If you want to write JSON data to a file, you can use the following:
    with open(os.path.join(current_directory, 'sor_description.json'), 'w') as f:
        json.dump(processed_sor, f)

    return processed_sor
def is_nan_or_none(value):
    if value is None:
        return True
    elif isinstance(value, float) and math.isnan(value):
        return True
    else:
        return False

def get_sor_composition_id():
    try:
        idgen_service_host = os.getenv('IDGEN_HOST')
        idgen_generate = os.getenv('IDGEN_GENERATE')

        api_url = f"{idgen_service_host}/{idgen_generate}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "idRequests": [
                {
                    "idName": "works.composition.number",
                    "tenantId": os.getenv('STATE_LEVEL_TENANT_ID')
                }
            ]
        }

        response = requests.post(api_url, json=request, headers=headers)
        composition_id = None
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            idgen_resp = response_data.get('idResponses', [])
            if len(idgen_resp) > 0:
                composition_id = idgen_resp[0]['id']
                return composition_id
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return composition_id
    except Exception as e:
        print("get_sor_composition_id : error {}".format(str(e)))
        raise e


def get_existing_sor_composition(sor_id):
    try:
        mdms_service_host = os.getenv('MDMS_V2_HOST')
        mdms_search = os.getenv('MDMS_V2_SEARCH')

        api_url = f"{mdms_service_host}/{mdms_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "MdmsCriteria": {
                "tenantId": os.getenv('STATE_LEVEL_TENANT_ID'),
                "schemaCode": "WORKS-SOR.Composition",
                "filters": {
                    "sorId": sor_id
                },
                "limit": 10,
                "offSet": 0
            }
        }

        response = requests.post(api_url, json=request, headers=headers)
        mdms = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            mdms.extend(response_data.get('mdms', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return mdms
    except Exception as e:
        print("get_existing_sor_composition : error {}".format(str(e)))
        raise e

def create_sor_composition(sor_composition_obj):
    try:
        mdms_service_host = os.getenv('MDMS_V2_HOST')
        composition_create = os.getenv('MDMS_v2_SOR_COMPOSITION_CREATE')

        api_url = f"{mdms_service_host}/{composition_create}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": get_request_info(),
            "Mdms": {
                "tenantId": os.getenv('STATE_LEVEL_TENANT_ID'),
                "schemaCode": "WORKS-SOR.Composition",
                "uniqueIdentifier": None,
                "data": sor_composition_obj,
                "isActive": True
            },
        }

        response = requests.post(api_url, json=request, headers=headers)
        mdms = []
        if response.status_code == 202:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            mdms.extend(response_data.get('mdms', []))
        else:
            print(f"Failed to create data from the API. Status code: {response.status_code}")
            print(response.text)
        return mdms
    except Exception as e:
        print("create_sor_composition : error {}".format(str(e)))
        raise e
def generate_sor_composition(sor_compositions, extra_charges):
    for sor_id in sor_compositions:
        print(sor_id)
        existing_sor = get_existing_sor_composition(sor_id)
        if existing_sor is not None and len(existing_sor) > 0:
            print('SOR Composition Already exists : ' + sor_id)
        else:
            print('Creating SOR Composition : ' + sor_id)
            sor_composition_obj = sor_compositions[sor_id]
            composition_id = get_sor_composition_id()
            sor_composition_obj['compositionId'] = composition_id
            resp = create_sor_composition(sor_composition_obj)
            if resp is not None and len(resp) > 0:
                print('SOR Composition Created : ' + sor_id)
            else:
                print('SOR Composition Creation Failed : ' + sor_id)
        print(existing_sor)
    return

def get_sor_composition_uat():
    # Get the current working directory
    current_directory = os.getcwd()
    # Path to your Excel file
    file_name = 'Rate_analysis_for_composition_uat.xlsx'
    # Combine the directory and file name to get the full file path
    file_path = os.path.join(current_directory, file_name)
    excel_file = file_path

    # Name of the sheet you want to read
    sheet_name = 'SOR_COMPOSITION_UAT'  # Change this to the actual sheet name

    # Read the Excel file into a DataFrame
    df = pd.read_excel(excel_file, sheet_name=sheet_name)

    # Strip leading and trailing spaces from all columns
    df = df.map(lambda x: x.strip() if isinstance(x, str) else x)

    # Replace new lines in cells with a space
    df = df.map(lambda x: x.replace('\n', ' ') if isinstance(x, str) else x)

    # Round numeric values to 4 decimal places
    df = df.map(lambda x: round(x, 4) if isinstance(x, (int, float, np.number)) else x)

    # Convert DataFrame to dictionary
    data_dict = df.to_dict(orient='records')

    processed_sor = prepare_data_for_composition_uat(data_dict)

    # Print the dictionary
    print(processed_sor)
    # If you want to write JSON data to a file, you can use the following:
    with open(os.path.join(current_directory, 'sor_description_uat.json'), 'w') as f:
        json.dump(processed_sor, f)

    return processed_sor

def prepare_data_for_composition_uat(data):
    PREV_WORKS_SOR_CODE = None
    PREV_WORKS_SOR_UNIT = None
    PREV_WORKS_SOR_COMP_QTY = None
    basic_sors_by_compositions = []

    sor_composition_map = {}
    for row in data:
        mainObj = {}
        if not is_nan_or_none(row.get('WORKS_SOR_CODE_UAT')) and not is_nan_or_none(row.get('WORKS_SOR_CODE_Prod')):
            PREV_WORKS_SOR_CODE = row.get('WORKS_SOR_CODE_UAT')
        elif not is_nan_or_none(row.get('WORKS_SOR_CODE_Prod')) and is_nan_or_none(row.get('WORKS_SOR_CODE_UAT')):
            PREV_WORKS_SOR_CODE = None
        if not is_nan_or_none(row.get('WORKS_SOR_UNIT')):
            PREV_WORKS_SOR_UNIT = row.get('WORKS_SOR_UNIT')
        if not is_nan_or_none(row.get('WORKS_SOR_COMP_QTY')):
            PREV_WORKS_SOR_COMP_QTY = row.get('WORKS_SOR_COMP_QTY')

        if PREV_WORKS_SOR_CODE is None:
            continue
        mainObj['WORKS_SOR_CODE'] = PREV_WORKS_SOR_CODE if is_nan_or_none(row.get('WORKS_SOR_CODE_UAT')) else row.get('WORKS_SOR_CODE_UAT')
        mainObj['WORKS_SOR_UNIT'] = PREV_WORKS_SOR_UNIT if is_nan_or_none(row.get('WORKS_SOR_UNIT')) else row.get('WORKS_SOR_UNIT')
        mainObj['WORKS_SOR_UNIT'] = PREV_WORKS_SOR_UNIT if is_nan_or_none(row.get('WORKS_SOR_UNIT')) else row.get('WORKS_SOR_UNIT')
        mainObj['WORKS_SOR_COMP_QTY'] = PREV_WORKS_SOR_COMP_QTY if is_nan_or_none(row.get('WORKS_SOR_COMP_QTY')) else row.get('WORKS_SOR_COMP_QTY')
        mainObj['SOR_TYPE'] = row.get('SOR_TYPE')
        mainObj['SOR_CODE'] = row.get('SOR_CODE_UAT')
        mainObj['SOR_DESCRIPTION'] = row.get('SOR_DESCRIPTION')
        mainObj['SOR_UNIT'] = row.get('SOR_UNIT')
        mainObj['QUANTITY'] = row.get('QUANTITY')
        if not is_nan_or_none(mainObj['SOR_CODE']):
            basic_sors_by_compositions.append(mainObj.copy())

    for basic_sor in basic_sors_by_compositions:
        if is_nan_or_none(sor_composition_map.get(basic_sor.get('WORKS_SOR_CODE'))):
            sor_comp_obj = {}
            sor_comp_obj['sorId'] = basic_sor.get('WORKS_SOR_CODE')
            sor_comp_obj['sorType'] = 'W'
            sor_comp_obj['type'] = 'SOR'
            sor_comp_obj['quantity'] = basic_sor.get('WORKS_SOR_COMP_QTY')
            sor_comp_obj['active'] = True
            sor_comp_obj['effectiveFrom'] = '1696118400000'
            sor_comp_obj['basicSorDetails'] = []
            sor_comp_obj['additionalCharges'] = []
            sor_composition_map[basic_sor.get('WORKS_SOR_CODE')] = sor_comp_obj
        sor_child = {
            "sorId": basic_sor.get('SOR_CODE'),
            "quantity": basic_sor.get('QUANTITY'),
            "perUnitQty": round(basic_sor['QUANTITY'] / basic_sor['WORKS_SOR_COMP_QTY'], 4)
        }
        if sor_composition_map.get(basic_sor.get('WORKS_SOR_CODE')):
            sor_composition_map[basic_sor.get('WORKS_SOR_CODE')]['basicSorDetails'].append(sor_child)

    return sor_composition_map


if __name__ == '__main__':
    works_sor_compositions = []
    sor_compositions = get_sor_composition()
    # extra_charges = get_extra_charges()
    extra_charges = {}
    generate_sor_composition(sor_compositions, extra_charges)