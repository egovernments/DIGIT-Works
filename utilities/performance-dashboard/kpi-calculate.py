import logging
import json
import psycopg2
import requests
import datetime
import os
from dotenv import load_dotenv
from urllib.parse import urlencode
import urllib3

from kpi1 import calculate_KPI1
from kpi2 import calculate_KPI2
from kpi5 import calculate_kpi5
from kpi6 import calculate_kpi6
from kpi7 import calculate_kpi7
from kpi10 import calculate_kpi10
from kpi12 import calculate_kpi12
import warnings
load_dotenv('.env')

DB_HOST = os.getenv('DB_HOST')
DB_PORT = os.getenv('DB_PORT')
DB_NAME = os.getenv('DB_NAME')
DB_USER = os.getenv('DB_USER')
DB_PASSWORD = os.getenv('DB_PASSWORD')

tenantIds = ["od.testing"]
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)
warnings.filterwarnings("ignore", message="Connecting to 'https://localhost:9200' using TLS with verify_certs=False is insecure")

designation_map = {
  "WRK_EE": "Executive Engineer",
  "WRK_DEE": "Deputy Executive Engineer",
  "WRK_AEE": "Assistant Executive Engineer",
  "WRK_ME": "Municipal Engineer",
  "WRK_CE": "City Engineer",
  "WRK_AE": "Assistant Engineer",
  "WRK_JE": "Junior Engineer",
  "WRK_RI": "Revenue Inspector",
  "WRK_AM": "Ameen",
  "WRK_DA": "Dealing Assistant",
  "WRK_PC": "Program Coordinator (MUKTA)",
  "WRK_IE": "Implementation Expert (MUKTA)",
  "WRK_ACCE": "Account Expert (MUKTA)",
  "ACC_AO": "Accounts Officer",
  "ACC_JAO": "Junior Accounts Officer",
  "ADM_MC": "Commissioner",
  "ADM_ADMC": "Additional Commissioner",
  "ADM_DMC": "Deputy Commissioner",
  "ADM_AMC": "Assistant Commissioner",
  "ADM_EO": "Executive Officer",
  "ADM_AEO": "Assistant Executive Officer",
  "Tax_WO": "Ward Officer",
  "ADM_DEO": "Data Entry Operator"
}
def connect_to_database():
    return psycopg2.connect(
        host=DB_HOST, port=DB_PORT, database=DB_NAME, user=DB_USER, password=DB_PASSWORD
    )


def getRequestInfo():
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

def getListOfULBs():
    # call mdms to get list of ULBs
    return

def getProjectListBetweenDate(tenantId, fromDate, toDate, cursor):
    # call project search API for each ULB
    query  = '''Select id, tenantid as tenantId, projectnumber as projectNumber, createdtime as createdTime, lastmodifiedtime as lastModifiedTime  from project where tenantid = %s and createdtime BETWEEN %s AND %s;'''
    cursor.execute(query, (tenantId, fromDate, toDate))
    # Fetch all rows from the executed query
    rows = cursor.fetchall()
    # Get column names from the cursor
    colnames = [desc[0] for desc in cursor.description]
    # Format data into an array of key-value pairs (dictionaries)
    formatted_data = [dict(zip(colnames, row)) for row in rows]
    return formatted_data

def getProjectsFromLastFinancialYear(es, tenantId):
    query = {
        "bool": {
          "must": [
            {
              "term": {
                "Data.tenantId.keyword": {
                  "value": "od.testing"
                }
              }
            },
            {
              "range": {
                "Data.auditDetails.createdTime": {
                  "gte": 1680307200000,
                  "lte": 1711929599000
                }
              }
            }
          ]
        }
      }
    response = es.search(index=os.getenv('PROJECT_INDEX'), body=query, size=10000)


    return

def getMusterRollList():
    return

def getBillList():
    return

def getEstimateByProjectId(tenant_id, projectId):
    try:
        estimate_service_host = os.getenv('ESTIMATE_HOST')
        estimate_search = os.getenv('ESTIMATE_SEARCH')
        query = urlencode("projectId={projectId}&tenantId={tenant_id}".format(projectId=projectId, tenant_id=tenant_id))
        api_url = f"{estimate_service_host}/{estimate_search}?{query}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": getRequestInfo()
        }

        response = requests.post(api_url, json=request, headers=headers)
        estimates = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            estimates.extend(response_data.get('estimates', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return estimates
    except Exception as e:
        print("search_payment_instruction_from_ifms_adapter : error {}".format(str(e)))
        raise e

def getContractDetail(estimate):
    # get contract details from API
    try:
        contract_service_host = os.getenv('CONTRACT_HOST')
        contract_search = os.getenv('CONTRACT_SEARCH')
        api_url = f"{contract_service_host}/{contract_search}"
        headers = {
            'Content-Type': 'application/json'
        }
        request = {
            "RequestInfo": getRequestInfo(),

        }

        response = requests.post(api_url, json=request, headers=headers)
        estimates = []
        if response.status_code == 200:
            response_data = response.json()
            # Assuming your response is stored in the variable 'response_data'
            estimates.extend(response_data.get('estimates', []))
        else:
            print(f"Failed to fetch data from the API. Status code: {response.status_code}")
            print(response.text)
        return estimates
    except Exception as e:
        print("search_payment_instruction_from_ifms_adapter : error {}".format(str(e)))
        raise e
    estimateIds
    return


def getHrmsDetails(tenantID):
    # get hrms details from API
    hrms_host = os.getenv('HRMS_HOST')
    hrms_search = os.getenv('HRMS_SEARCH')
    api_url = f"{hrms_host}/{hrms_search}"
    headers = {
        'Content-Type': 'application/json'
    }
    queryParam = {
        "tenantId": tenantID
    }
    request = {
        "RequestInfo": getRequestInfo(),
    }

    response = requests.post(api_url, json=request, params=queryParam, headers=headers)
    hrmsDetails = []
    if response.status_code == 200:
        response_data = response.json()
        # Assuming your response is stored in the variable 'response_data'
        hrmsDetails.extend(response_data.get('Employees', []))
        return hrmsDetails
    else:
        print(f"Failed to fetch data from the API. Status code: {response.status_code}")
        print(response.text)

def get_kpi_obj(District, kra, kpi, designation, name, kpi_score, pos, neg):
    return {
  "program": "Mukta",
  "start_date": "2023-01-01T00:00:00Z",
  "end_date": "2023-12-31T23:59:59Z",
  "district": District,
  "ulb": District + " Municipal Corporation",
  "kra": kra,
  "kpi": kpi,
  "designation": designation_map[designation],
  "employee_name": name,
  "score": kpi_score * 100,
  "total_count": pos + neg,
  "positive_count": pos,
  "negative_count": neg
}
def getActualDateOfProjectCompletion(project):
    # get project details from API
    # get estimate detils from DB based on project

    # get all bills from DB based on contract number use expense calculator table
    # calculate sum of all bills, the sum of bills has to be >= 95% of contract amount
    # if sum of bills >= 95% of contract amount, return last bill date
    # else return project is not completed
    return


# ULB disbursed 100% payments within stipulated timeframe*
def calculateKPI1(cursor, tenantId):
    # start execution for each ULB
    # get list of projects from API
    # get list of muster roll from API
    # for each muster roll get bill details
    calculate_KPI1(cursor, tenantId)

    return

# At least 75% of projects included in the ULB MUKTA action plan were completed during the financial year
def calculateKPI2(cursor):
    # get list of projects from API for each ULB and last financial year
    # call getDateOfProjectCompletion to get end date of project completion
    # get executive officer details from DB contract table
    # Don't count those project for which bill is not created
    for tenantId in tenantIds:
        calculate_KPI2(cursor, tenantId)
        # projects = getProjectListBetweenDate(tenantId, 1680307200000, 1711929599000, cursor)
        # projects = getproject
        # projects = [
        #     {
        #         "id": "746126ee-95e1-428d-9b7d-8da66a2349e9",
        #         "tenantId": "od.testing",
        #         "projectNumber": "PJ/2023-24/05/000053",
        #         "createdTime": 1683885907565,
        #         "lastModifiedTime": 1683885907565
        #     }
        # ]
        # for project in projects:
        #     estimates = getEstimateByProjectId(project.get('tenantId'), project.get('id'))
        #     estimates = getEstimateByProjectId(project.get('tenantId'), project.get('id'))
        #     if (estimates is None or len(estimates) == 0):
        #         # get contract details from DB based on estimate
        #         estimate = estimates[0]
        #         getContractDetails(estimate)
        #     projectEndDate = getActualDateOfProjectCompletion(project, cursor)
        #     # get estimate detils from DB based on project
        #     print(projectEndDate)
    return

# At least 75% of the proposed projects had no time overrun
def calculateKPI3():
    return

# Vendor payment approved within 2 working days of receiving verified request from ME.
def calculateKPI4():
    return

# Work Order was issued for 100% of the final list of projects within 7 days from Administrative Approval 
def calculateKPI5(cursor, tenantId, hrmsDetails):
    designations = ['WRK_ME', 'WRK_EE', 'WRK_AEE']
    District = tenantId.split('.')[1]
    kpi = "Work Order was issued for 100% of the final list of projects within 7 days from Administrative Approval "
    kra = "At least 75% of projects included in yearly MUKTA action plan were completed during the financial year"
    kpi_5_users = []
    kpi5 = calculate_kpi5(cursor, tenantId)
    pos = kpi5.get('pos')
    neg = kpi5.get('neg')
    kpi5_score = kpi5.get('kpi5')
    for employee in hrmsDetails:
        designation = employee.get('assignments').get(0).get('designation')
        name = employee.get('user').get('name')
        if designation in designations:
            kpi_obj = get_kpi_obj(District, kra, kpi, designation, name, kpi5_score,pos, neg)
            kpi_5_users.append(kpi_obj)




# Vendor payment requests verified within 2 days after submission from JE/IE/AEE
def calculateKPI6():
    return

# Muster roll approved within 3 days of submission
def calculateKPI7():
    return


# All outstanding muster rolls approved and vendor bills submitted within 3 days of project completion
def calculateKPI8():
    return

# Vendor payment requests submitted within 3 days of project work completion
def calculateKPI9():
    return

# Submitted draft Work Orders for 100% of projects to ME within 30 days from Technical Sanction and Administrative Approval 
def calculateKPI10():
    return

# At least 75% of all projects had no time overrun
def calculateKPI11():
    return


if __name__ == '__main__':
    # Connect to PostgreSQL
    connection = connect_to_database()
    # Create a cursor object
    cursor = connection.cursor()
    for tenantId in tenantIds:
        hrmsDetails = getHrmsDetails(tenantId)
        calculateKPI5(cursor, tenantId, hrmsDetails)
        kpi6 = calculate_kpi6(cursor, tenantId)
        kpi7 = calculate_kpi7(cursor, tenantId)
        kpi8 = calculate_kpi8(cursor, tenantId)
        kpi12 = calculate_kpi12(cursor, tenantId)
        # calculate_KPI1(cursor, tenantId)
        # calculate_KPI2(cursor)
