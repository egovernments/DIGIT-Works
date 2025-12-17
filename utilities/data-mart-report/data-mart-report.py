import csv
import pandas as pd
from numpy import *
import requests
import json
from dateutil import parser
import datetime as dt
import psycopg2
import logging
import calendar
import time
import os
import pytz
import pytz
from datetime import datetime
import re
from dotenv import load_dotenv

load_dotenv('.env')

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

tenants = ['od.athagarh','od.berhampur','od.kesinga','od.jatni','od.chatrapur','od.puri','od.hinjilicut','od.sambalpur','od.balasore','od.padampur','od.jharsuguda','od.dhenkanal','od.jeypore','od.bhadrak','od.balangir','od.baripada','od.bhubaneswar','od.rourkela','od.jajpur','od.cuttack','od.phulbani','od.kotpad','od.paradeep','od.boudhgarh','od.keonjhargarh']

def get_recent_thursday_epoch():
    today = dt.datetime.now()
    days_since_last_thursday = (today.weekday() - 3) % 7
    last_thursday = today - dt.timedelta(days=days_since_last_thursday)
    last_thursday_time = last_thursday.replace(hour=20, minute=30, second=0, microsecond=0)
    last_thursday_epoch = int(last_thursday_time.timestamp() * 1000)
    return last_thursday_epoch


def getResponseFromExpenseCalculator(ids, tenant):
    totalValueOfMusterRoll = 0
    for i in range(0, len(ids), 50):
                id_batch = ids[i:i+50]
                host = os.getenv('EXPENSE_CALC_HOST') + os.getenv('EXPENSE_CALC_ESTIMATE')
                request_payload = {
                    "apiId": "Rainmaker",
                    "authToken": "56acd84a-9c67-4df8-87a6-12e8dfe283f0",
                    "userInfo": {
                        "id": 271,
                        "uuid": "81b1ce2d-262d-4632-b2a3-3e8227769a11",
                        "userName": "MUKTAUAT",
                        "name": "MUKTAUAT",
                        "mobileNumber": "9036146615",
                        "type": "EMPLOYEE",
                        "roles": [
                            {
                                "name": "MB_VERIFIER",
                                "code": "MB_VERIFIER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "Organization viewer",
                                "code": "ORG_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MDMS Admin",
                                "code": "MDMS_ADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MB_VIEWER",
                                "code": "MB_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MUKTA Admin",
                                "code": "MUKTA_ADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "State Dashboard Admin",
                                "code": "STADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "WORK_ORDER_VIEWER",
                                "code": "WORK_ORDER_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "BILL_ACCOUNTANT",
                                "code": "BILL_ACCOUNTANT",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "ESTIMATE VERIFIER",
                                "code": "ESTIMATE_VERIFIER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "WORK ORDER APPROVER",
                                "code": "WORK_ORDER_APPROVER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "ESTIMATE VIEWER",
                                "code": "ESTIMATE_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "OFFICER IN CHARGE",
                                "code": "OFFICER_IN_CHARGE",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "BILL_VIEWER",
                                "code": "BILL_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "PROJECT VIEWER",
                                "code": "PROJECT_VIEWER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MB_CREATOR",
                                "code": "MB_CREATOR",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "BILL_APPROVER",
                                "code": "BILL_APPROVER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MUSTER ROLL VERIFIER",
                                "code": "MUSTER_ROLL_VERIFIER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "WORK ORDER CREATOR",
                                "code": "WORK_ORDER_CREATOR",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "ESTIMATE APPROVER",
                                "code": "ESTIMATE_APPROVER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "ESTIMATE CREATOR",
                                "code": "ESTIMATE_CREATOR",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MDMS STATE VIEW ADMIN",
                                "code": "MDMS_STATE_VIEW_ADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "Employee Common",
                                "code": "EMPLOYEE_COMMON",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "TECHNICAL SANCTIONER",
                                "code": "TECHNICAL_SANCTIONER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "BILL_CREATOR",
                                "code": "BILL_CREATOR",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "BILL_VERIFIER",
                                "code": "BILL_VERIFIER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MUSTER ROLL APPROVER",
                                "code": "MUSTER_ROLL_APPROVER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MB_APPROVER",
                                "code": "MB_APPROVER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "MDMS CITY ADMIN",
                                "code": "MDMS_CITY_ADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "PROJECT CREATOR",
                                "code": "PROJECT_CREATOR",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "Employee Organization Admin",
                                "code": "EMP_ORG_ADMIN",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "WORK ORDER VERIFIER",
                                "code": "WORK_ORDER_VERIFIER",
                                "tenantId": "od.testing"
                            },
                            {
                                "name": "HRMS Admin",
                                "code": "HRMS_ADMIN",
                                "tenantId": "od.testing"
                            }
                        ]
                    },
                    "msgId": "1715595117903|en_IN",
                    "plainAccessRequest": {}
                }
                headers = {"Content-Type": "application/json"}
                api_payload = {"criteria": {"tenantId": tenant, "musterRollId": id_batch},"RequestInfo": request_payload}

                response = requests.post(host,headers=headers,data=json.dumps(api_payload)).json()
                if response["calculation"]["totalAmount"] is not None:
                    totalValueOfMusterRoll += response["calculation"]["totalAmount"]

    return totalValueOfMusterRoll
    

def getValueOfMusterRollCreated(connection, epoch_to):
    try:

        data = []
        for tenant in tenants:
            print(tenant)
            ids = []
            cursor = connection.cursor()

            cursor.execute("""select id from eg_wms_muster_roll where createdtime <= %s and tenant_id = %s order by createdtime;""", (epoch_to,tenant))
            result = cursor.fetchall()

            # Store the fetched IDs in the list
            ids.extend([row[0] for row in result])

            totalValueOfMusterRollCreated = getResponseFromExpenseCalculator(ids, tenant)

            # Append the result as a tuple (tenant, totalValueOfMusterRollCreated) to the data list
            data.append((tenant, totalValueOfMusterRollCreated))

        # Convert the data list to a pandas DataFrame for easier formatting
        df = pd.DataFrame(data, columns=['ulb', 'Total Value of Muster Roll Created'])
        return df
                
    except Exception as e:
        print(e)

def getValueOfMrApproved(connection, epoch_to):  
    try:

        data = []
        for tenant in tenants:
            print(tenant)
            ids = []
            cursor = connection.cursor()

            cursor.execute("""select id from eg_wms_muster_roll where musterroll_status='APPROVED' and createdtime <= %s and tenant_id = %s order by createdtime;""", (epoch_to,tenant))
            result = cursor.fetchall()

            # Store the fetched IDs in the list
            ids.extend([row[0] for row in result])

            totalValueOfMusterRollApproved = getResponseFromExpenseCalculator(ids, tenant)

            # Append the result as a tuple (tenant, totalValueOfMusterRollCreated) to the data list
            data.append((tenant, totalValueOfMusterRollApproved))

        # Convert the data list to a pandas DataFrame for easier formatting
        df = pd.DataFrame(data, columns=['ulb', 'Total Value of Muster Roll Approved(Rs)'])
        return df
                
    except Exception as e:
        print(e)  

def generateBasicInformations(connection, epoch_to):

    cursor = connection.cursor()

    #No. of ULB users/employee
    cursor.execute("""
        select tenantid as ulb, count(*) as employee 
        from eg_hrms_employee 
        where active=true and lastmodifieddate <= %s 
        group by tenantid order by tenantid;
    """, (epoch_to,))
    result = cursor.fetchall()
    employeeData = pd.DataFrame(result)

    #Fetch the Number of CBO users(No. of CBO's)
    cursor.execute("""select eg_org.tenant_id as ulb, count(eg_org.id) as cbo from eg_org inner join eg_org_function on eg_org.id=eg_org_function.org_id where eg_org_function.type like '%%CBO%%' and eg_org.last_modified_time <= %s group by eg_org.tenant_id order by eg_org.tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    cboData = pd.DataFrame(result)

    #No. of vendors(Contractors)
    cursor.execute("""select eg_org.tenant_id as ulb, count(eg_org.id) as vendors from eg_org inner join eg_org_function on eg_org.id=eg_org_function.org_id where eg_org_function.type='VEN.CMS' and eg_org.last_modified_time <= %s group by eg_org.tenant_id order by eg_org.tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    vendorsData = pd.DataFrame(result)

    #No. of Wageseekers
    cursor.execute("""select tenantid as ulb, count(*) as wageseekers from INDIVIDUAL where issystemuser=False and lastmodifiedtime <= %s group by tenantid;""", (epoch_to,))

    result = cursor.fetchall()
    wageseekersData = pd.DataFrame(result)

    #No. of projects created
    cursor.execute("""select tenantid as ulb, count(id) as projects from project where lastmodifiedtime <= %s group by tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    projectsData = pd.DataFrame(result)

    #No. of estimate created
    cursor.execute("""
                    SELECT est.tenant_id as ulb, COUNT(DISTINCT est.estimate_number) 
                    FROM (
                        SELECT 
                            tenant_id,
                            estimate_number, 
                            wf_status, 
                            ROW_NUMBER() OVER (PARTITION BY estimate_number ORDER BY created_time DESC) as rn 
                        FROM eg_wms_estimate
                        WHERE created_time <= %s 
                    ) AS est
                    WHERE est.rn = 1 AND est.wf_status != 'DRAFTED' and est.wf_status != 'REJECTED' and est.estimate_number not like '%%DE_LINK%%' group by est.tenant_id;
    """, (epoch_to,))
    result = cursor.fetchall()
    estimateData = pd.DataFrame(result)

    #Value of estimate created
    estimateValueData = getEstimatedValue(connection, epoch_to)

    #No Of Estimates Approved
    cursor.execute("""
                    SELECT est.tenant_id as ulb, COUNT(DISTINCT est.estimate_number) 
                    FROM (
                        SELECT 
                            tenant_id,
                            estimate_number, 
                            wf_status, 
                            ROW_NUMBER() OVER (PARTITION BY estimate_number ORDER BY created_time DESC) as rn 
                        FROM eg_wms_estimate
                        WHERE created_time <= %s 
                    ) AS est
                    WHERE est.rn = 1 AND est.wf_status = 'APPROVED' and est.estimate_number not like '%%DE_LINK%%' group by est.tenant_id;
    """, (epoch_to,))
    result = cursor.fetchall()
    estimateApprovedData = pd.DataFrame(result)
    print(estimateApprovedData)

    #Total value of estimate approved(Rs)
    approvedEstimateValueData = getApprovedEstimateValue(connection, epoch_to)

    #No. of work order created
    cursor.execute("""select tenant_id as ulb, count(distinct contract_number) as numberofwocreated from eg_wms_contract where last_modified_time<= %s and (wf_status = 'APPROVED' or wf_status = 'ACCEPTED') and contract_number not like '%%DE%%' group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    numberOfWoCreatedData = pd.DataFrame(result)

    #Total value of work order accepted by CBO's(Rs)
    cursor.execute("""select SUM(total_contracted_amount) as valueofwoaccepted, tenant_id as ulb from eg_wms_contract where wf_status='ACCEPTED' and last_modified_time<=%s group by tenant_id order by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    valueOfWoAcceptedData = pd.DataFrame(result)

    #No. Of work Orders Accepted By CBO
    cursor.execute("""select tenant_id as ulb, count(*) as noofwoaccepted  from eg_wms_contract where wf_status='ACCEPTED' and last_modified_time<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    numberOfWoAcceptedData = pd.DataFrame(result)

    #Value of the project(s) initiated(work started)
    valueOfProjectInitiatedData = getValueOfProjectInitiated(connection, epoch_to)

    #No Of Wage Seekers Engaged in Initiated Projects
    cursor.execute("""select eg_wms_muster_roll.tenant_id as ulb, count(distinct individual_id) as wageseekersEngagedInInitiatedProjects from eg_wms_muster_roll inner join eg_wms_attendance_summary on eg_wms_muster_roll.id=eg_wms_attendance_summary.muster_roll_id where eg_wms_muster_roll.lastmodifiedtime<=%s group by eg_wms_muster_roll.tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    wageseekersEngagedInInitiatedProjectsData = pd.DataFrame(result)

    #Total Number of Muster Rolls Generated
    cursor.execute("""select tenant_id as ulb, count(*) as totalNumberOfMusterRollGenerated from eg_wms_muster_roll where createdtime<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    totalNumberOfMusterRollGeneratedData = pd.DataFrame(result)

    # Total Value of Muster Rolls Created
    valueOfMusterRollCreatedData = getValueOfMusterRollCreated(connection, epoch_to)

    # No Of Wage Seekers Engaged in Initiated Projects
    cursor.execute("""select tenantid as ulb, count(distinct individual_id) as totalNumberOfWageseekerEngaged from eg_wms_attendance_attendee where lastmodifiedtime<=%s group by tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    totalNumberOfWageseekerEngagedData = pd.DataFrame(result)

    # Total Number of Muster Rolls Approved
    cursor.execute("""select tenant_id as ulb, count(*) as numberofmrapproved from eg_wms_muster_roll where musterroll_status='APPROVED' and lastmodifiedtime<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    numberOfMrApprovedData = pd.DataFrame(result)

    # Total Value of Muster Rolls Approved
    valueOfMrApprovedData = getValueOfMrApproved(connection, epoch_to)


    #Number of Wage Seekers in Non Approved Muster Roll
    cursor.execute("""select tenant_id as ulb, count(distinct individual_id) as numberofwsinnonapprovedmr from eg_wms_attendance_summary inner join eg_wms_muster_roll on eg_wms_attendance_summary.muster_roll_id=eg_wms_muster_roll.id  where musterroll_status  != 'APPROVED' and eg_wms_muster_roll.lastmodifiedtime<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    numberOfWsInNonApprovedMrData = pd.DataFrame(result)

    #Value of Work order Approved
    cursor.execute("""select SUM(total_contracted_amount) as totalAmount, tenant_id as ulb from eg_wms_contract where wf_status='APPROVED' and last_modified_time<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    valueOfWoApprovedData = pd.DataFrame(result)

    #No of Work order Approved
    cursor.execute("""select count(*) as woapproved, tenant_id as ulb from eg_wms_contract where wf_status='APPROVED' and last_modified_time<=%s group by tenant_id;""", (epoch_to,))
    result = cursor.fetchall()
    numberOfWoApprovedData = pd.DataFrame(result)

    #Prepare column
    employeeData.columns = ['ulb', 'No. of ULB users/employee']
    cboData.columns = ['ulb', 'No. of CBOs']
    vendorsData.columns = ['ulb', 'No. of vendors']
    wageseekersData.columns = ['ulb', 'No. of Wageseekers']
    projectsData.columns = ['ulb', 'No. of projects created']
    estimateData.columns = ['ulb', 'No. of estimate created']
    estimateValueData.columns = ['ulb', 'Total Values Of Estimates Created']
    estimateApprovedData.columns = ['ulb', 'No. Of Estimates Approved']
    approvedEstimateValueData.columns = ['ulb', 'Total Value Of Estimates Approved(Rs)']
    numberOfWoCreatedData.columns = ['ulb', 'No. of work order created']
    valueOfWoAcceptedData.columns = ['Total value of work order accepted by CBOs(Rs)','ulb']
    numberOfWoAcceptedData.columns = ['ulb', 'No. Of work Orders Accepted By CBO']
    valueOfProjectInitiatedData.columns = ['ulb', 'Value of the project(s) initiated(work started)']
    wageseekersEngagedInInitiatedProjectsData.columns = ['ulb', 'No Of Wage Seekers Engaged in Initiated Projects']
    totalNumberOfMusterRollGeneratedData.columns = ['ulb', 'Total Number of Muster Rolls Generated']
    valueOfMusterRollCreatedData.columns = ['ulb', 'Total Value of Muster Rolls Created(Rs)']
    totalNumberOfWageseekerEngagedData.columns = ['ulb', 'Total No Of Wage Seekers Engaged']
    numberOfMrApprovedData.columns = ['ulb', 'Total Number of Muster Rolls Approved']
    valueOfMrApprovedData.columns = ['ulb', 'Total Value of Muster Rolls Approved(Rs)']
    numberOfWsInNonApprovedMrData.columns = ['ulb', 'Number of Wage Seekers in Non Approved Muster Roll']
    valueOfWoApprovedData.columns = ['Value of Work order Approved(Rs)', 'ulb']
    numberOfWoApprovedData.columns = ['No of Work order Approved', 'ulb']


    ###########################################

    data = pd.DataFrame()
    data=pd.merge(employeeData,cboData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,vendorsData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,wageseekersData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,projectsData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,estimateData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,estimateValueData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,estimateApprovedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,approvedEstimateValueData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,numberOfWoCreatedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueOfWoAcceptedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,numberOfWoAcceptedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueOfProjectInitiatedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,wageseekersEngagedInInitiatedProjectsData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,totalNumberOfMusterRollGeneratedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueOfMusterRollCreatedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,totalNumberOfWageseekerEngagedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,numberOfMrApprovedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueOfMrApprovedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,numberOfWsInNonApprovedMrData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueOfWoApprovedData,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,numberOfWoApprovedData,left_on='ulb',right_on='ulb',how='left')

    data['ulb'] = data['ulb'].str.replace('od.','')
    print(data)
    return data

def getEstimatedValue(connection, epoch):
    cursor = connection.cursor()
    data = []
    for tenant in tenants:
        datalist = [tenant]
        cursor.execute("""
            Select SUM(eg_wms_estimate_amount_detail.amount) as totalEstimateAmount from ((select est.id as estimateId, estDetails.id  as estimateDetailId,est.tenant_id  from(WITH LatestEstimates AS (  SELECT  *,  ROW_NUMBER() OVER (PARTITION BY estimate_number ORDER BY created_time DESC) as rn  FROM  eg_wms_estimate where created_time<=%s)SELECT   id,  tenant_id,  estimate_number, project_id, proposal_date, status,wf_status, name,reference_number, description, executing_department, additional_details, created_by, last_modified_by,   created_time,last_modified_time, revision_number,version_number,old_uuid,business_service FROM  LatestEstimates WHERE rn = 1 and created_time<=%s) as est Inner Join eg_wms_estimate_detail as estDetails on est.id= estDetails.estimate_id where est.created_time<=%s  )) as tempTable inner join eg_wms_estimate_amount_detail on tempTable.estimatedetailid = eg_wms_estimate_amount_detail.estimate_detail_id where tempTable.tenant_id = %s;
        """, (epoch, epoch, epoch, tenant))
        getEstimatedProjectValue = cursor.fetchall()
        print(getEstimatedProjectValue)
        if not getEstimatedProjectValue or getEstimatedProjectValue[0][0] is None:
            datalist.append(0)
        else:
            datalist.append(getEstimatedProjectValue[0][0])
        data.append(datalist)
    df = pd.DataFrame(data, columns=['ulb', 'totalestimateamount'])
    return df

def getApprovedEstimateValue(connection, epoch):
    cursor = connection.cursor()
    data = []
    for tenant in tenants:
        datalist = [tenant]
        cursor.execute("""
            Select SUM(eg_wms_estimate_amount_detail.amount) as totalEstimateAmount from ((select est.id as estimateId, estDetails.id  as estimateDetailId,est.tenant_id  from(WITH LatestEstimates AS (  SELECT  *,  ROW_NUMBER() OVER (PARTITION BY estimate_number ORDER BY created_time DESC) as rn  FROM  eg_wms_estimate where wf_status='APPROVED' and last_modified_time<=%s)SELECT   id,  tenant_id,  estimate_number, project_id, proposal_date, status,wf_status, name,reference_number, description, executing_department, additional_details, created_by, last_modified_by,   created_time,last_modified_time, revision_number,version_number,old_uuid,business_service FROM  LatestEstimates WHERE rn = 1 and last_modified_time<=%s and wf_status='APPROVED') as est Inner Join eg_wms_estimate_detail as estDetails on est.id= estDetails.estimate_id where est.last_modified_time<=%s  )) as tempTable inner join eg_wms_estimate_amount_detail on tempTable.estimatedetailid = eg_wms_estimate_amount_detail.estimate_detail_id where tempTable.tenant_id = %s;
        """, (epoch, epoch, epoch, tenant))
        getEstimatedProjectValue = cursor.fetchall()
        if not getEstimatedProjectValue or getEstimatedProjectValue[0][0] is None:
            datalist.append(0)
        else:
            datalist.append(getEstimatedProjectValue[0][0])

        data.append(datalist)
    df = pd.DataFrame(data, columns=['ulb', 'totalestimateamount'])
    print(df)
    return df

def getValueOfProjectInitiated(connection, epoch):
    cursor = connection.cursor()
    data = []
    for tenant in tenants:
        datalist = [tenant]
        cursor.execute("""select sum(eg_wms_estimate_amount_detail.amount) as totalamount from (select distinct eg_wms_estimate_detail.id as estimateDetailsId from (select distinct * from (select project.id as projectId from project Inner Join (select eg_wms_attendance_register.additionaldetails as additionalDetails from eg_wms_attendance_register inner join eg_wms_attendance_log on eg_wms_attendance_register.id = eg_wms_attendance_log.register_id where eg_wms_attendance_log.lastmodifiedtime<=%s) logsTable on project.projectnumber = logsTable.additionalDetails ->> 'projectId') as tempTable1 inner join eg_wms_estimate on eg_wms_estimate.project_id = tempTable1.projectId ) as tempTable2 inner join eg_wms_estimate_detail on eg_wms_estimate_detail.estimate_id = tempTable2.id where tempTable2.tenant_id = %s) as tempTable3 inner join eg_wms_estimate_amount_detail on tempTable3.estimateDetailsId = eg_wms_estimate_amount_detail.estimate_detail_id where eg_wms_estimate_amount_detail.is_active=TRUE;""", (epoch, tenant))
        getEstimatedProjectValue = cursor.fetchall()
        if getEstimatedProjectValue[0][0] is None:
            datalist.append(0)
        else:
            datalist.append(getEstimatedProjectValue[0][0])

        data.append(datalist)
    df = pd.DataFrame(data, columns=['ulb', 'totalestimateamount'])
    return df


def generateTotalCountByProjectType(connection, epoch):
    cursor = connection.cursor()
    data = []
    counter = 0

    for tenant in tenants:
        counter += 1
        cursor.execute("""
            SELECT temptable.projectType, 
                   COUNT(*) as project_count 
            FROM (
                SELECT DISTINCT 
                       eg_wms_attendance_register.additionaldetails->>'projectType' as projectType, 
                       eg_wms_attendance_register.id 
                FROM eg_wms_attendance_register 
                INNER JOIN eg_wms_attendance_attendee 
                ON eg_wms_attendance_register.id = eg_wms_attendance_attendee.register_id  
                WHERE eg_wms_attendance_register.tenantid = %s 
                AND eg_wms_attendance_register.tenantid != 'od.testing' 
                AND eg_wms_attendance_register.lastmodifiedtime <= %s 
            ) as temptable 
            GROUP BY temptable.projectType;
        """, (tenant, epoch))
        
        result = cursor.fetchall()
        print(result)

        if not result:
            dataSet = pd.DataFrame([['NA', 0]], columns=['Project Type', tenant.replace("od.", "")])
        else:
            dataSet = pd.DataFrame(result, columns=['Project Type', tenant.replace("od.", "")])

        if counter == 1:
            data = dataSet
        else:
            data = pd.merge(data, dataSet, on='Project Type', how='outer')

    print(data)
    return data


def generateTotalAmountPaidAndCountOfBills(connection, epoch):
    cursor = connection.cursor()

    ############### WAGE BILL ############### 
    #Value of Wage Bill Paid
    cursor.execute("""select tenantid as ulb, sum(totalpaidamount) as amount from eg_expense_bill where businessservice='EXPENSE.WAGES' and paymentstatus='SUCCESSFUL' and lastmodifiedtime<=%s group by tenantid order by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueWageBillDataFrame = pd.DataFrame(result)

    #Value of Wage Bill Paid PARTIAL
    cursor.execute("""select  sum(totalpaidamount) as amount, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.WAGES' and paymentstatus='PARTIAL' and lastmodifiedtime<=%s group by tenantid order by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueWageBillPartialDataFrame = pd.DataFrame(result)

    #Count of Wage Bills Paid
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.WAGES' and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countWageBillPaidDataFrame = pd.DataFrame(result)

    #Count of Wage Bills Paid PARTIAL
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.WAGES' and lastmodifiedtime<=%s and paymentstatus='PARTIAL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countWageBillPartialPaidDataFrame = pd.DataFrame(result)

    #Count of Wage Bills Created
    cursor.execute("""select tenantid as ulb, count(*) as num from eg_expense_bill where businessservice='EXPENSE.WAGES' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countWageBillCreatedDataFrame = pd.DataFrame(result)

    #Value of Wage Bills Created
    cursor.execute("""select sum(totalamount),tenantid from eg_expense_bill where businessservice='EXPENSE.WAGES' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueWageBillCreatedDataFrame = pd.DataFrame(result)


    ###############Purchase Bill###############
    #Value of Purchase Bill Paid
    cursor.execute("""select sum(totalpaidamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and paymentstatus='SUCCESSFUL' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valuePurchaseBillDataFrame = pd.DataFrame(result)

    #Value of Purchase Bill Paid PARTIAL
    cursor.execute("""select sum(totalpaidamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and paymentstatus='PARTIAL' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valuePurchaseBillPartialDataFrame = pd.DataFrame(result)

    #Count of Purchase Bills Paid
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countPurchaseBillPaidDataFrame = pd.DataFrame(result)

    #Count of Purchase Bills Paid PARTIAL
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and lastmodifiedtime<=%s and paymentstatus='PARTIAL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countPurchaseBillPartialPaidDataFrame = pd.DataFrame(result)

    #Count of Purchase Bills Created
    cursor.execute("""select count(*) as num,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countPurchaseBillCreatedDataFrame = pd.DataFrame(result)

    #Value of Purchase Bills Created
    cursor.execute("""select sum(totalamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valuePurchaseBillCreatedDataFrame = pd.DataFrame(result)


    ###############Supervision Bill###############
    #Value of Supervision Bill Paid
    cursor.execute("""select sum(totalpaidamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and paymentstatus='SUCCESSFUL' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueSupervisionBillDataFrame = pd.DataFrame(result)

    #Value of Supervision Bill Paid PARTIAL
    cursor.execute("""select sum(totalpaidamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and paymentstatus='PARTIAL' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueSupervisionBillPartialDataFrame = pd.DataFrame(result)


    #Count of Supervision Bills Paid
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countSupervisionBillPaidDataFrame = pd.DataFrame(result)

    #Count of Supervision Bills Paid Partial
    cursor.execute("""select count(*) as num, tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and lastmodifiedtime<=%s and paymentstatus='PARTIAL' group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countSupervisionBillPartialPaidDataFrame = pd.DataFrame(result)


    #Count of Supervision Bills Created
    cursor.execute("""select count(*) as num,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    countSupervisionBillCreatedDataFrame = pd.DataFrame(result)

    #Value of Supervision Bills Created
    cursor.execute("""select sum(totalamount) as amount,tenantid as ulb from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and lastmodifiedtime<=%s Group by tenantid;""", (epoch,))
    result = cursor.fetchall()
    valueSupervisionBillCreatedDataFrame = pd.DataFrame(result)


    #Prepare column
    valueWageBillDataFrame.columns = ['ulb', 'Value of Wage Bill Paid']
    countWageBillPaidDataFrame.columns = ['Count of Wage Bills Paid', 'ulb']
    countWageBillCreatedDataFrame.columns = ['ulb', 'Count of Wage Bills Created']
    valueWageBillCreatedDataFrame.columns = ['Value of Wage Bills Created', 'ulb']
    valueWageBillPartialDataFrame.columns=['Value of Wage Bill Paid Partial','ulb']
    countWageBillPartialPaidDataFrame.columns= ['Count of Wage Bills Paid Partial','ulb']

    valuePurchaseBillDataFrame.columns = ['Value of Purchase Bill Paid','ulb']
    countPurchaseBillPaidDataFrame.columns = ['Count of Purchase Bills Paid','ulb']
    countPurchaseBillCreatedDataFrame.columns = ['Count of Purchase Bills Created','ulb']
    valuePurchaseBillCreatedDataFrame.columns = ['Value of Purchase Bills Created','ulb']
    valuePurchaseBillPartialDataFrame.columns=['Value of Purchase Bill Paid Partial','ulb']
    countPurchaseBillPartialPaidDataFrame.columns= ['Count of Purchase Bills Paid Partial','ulb']

    valueSupervisionBillDataFrame.columns = ['Value of Supervision Bill Paid','ulb']
    countSupervisionBillPaidDataFrame.columns = ['Count of Supervision Bills Paid','ulb']
    countSupervisionBillCreatedDataFrame.columns = ['Count of Supervision Bills Created','ulb']
    valueSupervisionBillCreatedDataFrame.columns = ['Value of Supervision Bills Created','ulb']
    valueSupervisionBillPartialDataFrame.columns=['Value of Supervision Bill Paid Partial','ulb']
    countSupervisionBillPartialPaidDataFrame.columns= ['Count of Supervision Bills Paid Partial','ulb']

    #####################################

    data = pd.DataFrame()
    data=pd.merge(countWageBillCreatedDataFrame,valueWageBillCreatedDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueWageBillDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countWageBillPaidDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueWageBillPartialDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countWageBillPartialPaidDataFrame,left_on='ulb',right_on='ulb',how='left')

    data=pd.merge(data,valuePurchaseBillDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countPurchaseBillPaidDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countPurchaseBillCreatedDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valuePurchaseBillCreatedDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valuePurchaseBillPartialDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countPurchaseBillPartialPaidDataFrame,left_on='ulb',right_on='ulb',how='left')
    

    data=pd.merge(data,valueSupervisionBillDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countSupervisionBillPaidDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countSupervisionBillCreatedDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueSupervisionBillCreatedDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,valueSupervisionBillPartialDataFrame,left_on='ulb',right_on='ulb',how='left')
    data=pd.merge(data,countSupervisionBillPartialPaidDataFrame,left_on='ulb',right_on='ulb',how='left')

    data['ulb'] = data['ulb'].str.replace('od.','')
    print(data)
    return data

def generateTotalCountOnPILevel(connection, epoch_from, epoch_to):
    cursor = connection.cursor()
    
    ############## Impact On Ground Level ##############
    # Weekly Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            SELECT COUNT(DISTINCT beneficiaryid) AS beneficiarycount, tenantid
            FROM jit_beneficiary_details
            WHERE paymentstatus = 'Payment Successful' and lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid) AS tempTable;""", (epoch_from, epoch_to))

    result_weekly = cursor.fetchall()
    ground_level_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            SELECT COUNT(DISTINCT beneficiaryid) AS beneficiarycount, tenantid
            FROM jit_beneficiary_details
            WHERE paymentstatus = 'Payment Successful' and lastmodifiedtime<=%s group by tenantid) AS tempTable;""", (epoch_to,))

    result_cummulative = cursor.fetchall()
    ground_level_cummulative_beneficiary_count = result_cummulative[0][0]


    ############### Failure in JIT by PI: Count of failure PI's which hit the JIT system(Show number of PI failed) ##############
    # Weekly Count
    cursor.execute("""select sum(tempTable.count) from (select count(*), tenantid from jit_payment_inst_details where pistatus='FAILED'  and  lastmodifiedtime>%s and lastmodifiedtime<=%s  group by tenantid) as tempTable;""", (epoch_from, epoch_to))
    result_weekly = cursor.fetchall()
    failure_by_pi_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""select sum(tempTable.count) from (select count(*), tenantid from jit_payment_inst_details where pistatus='FAILED'  and lastmodifiedtime<=%s  group by tenantid) as tempTable;""", (epoch_to,))
    result_weekly = cursor.fetchall()
    failure_by_pi_cummulative_beneficiary_count = result_weekly[0][0]


    ############### Partial in JIT by PI: Count of Partial PI's which hit the JIT system ##############
    # Weekly Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(*) AS beneficiarycount, tenantid from jit_payment_inst_details where pistatus='PARTIAL' and   lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid
        ) AS tempTable;""", (epoch_from, epoch_to))

    result_weekly = cursor.fetchall()
    partial_by_pi_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(*) AS beneficiarycount, tenantid from jit_payment_inst_details where pistatus='PARTIAL' and lastmodifiedtime<=%s group by tenantid
        ) AS tempTable;""", (epoch_to,))

    result_weekly = cursor.fetchall()
    partial_by_pi_cummulative_beneficiary_count = result_weekly[0][0]


    ############### Successful in JIT by PI: Count of Successful PI's which hit the JIT system(Show number of PI Successful) ##############
    # Weekly Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(*) AS beneficiarycount, tenantid from jit_payment_inst_details where pistatus not in ('FAILED', 'PARTIAL', 'COMPLETED', 'APPROVED', 'INITIATED') and     lastmodifiedtime>%s and lastmodifiedtime<=%s  group by tenantid 
        ) AS tempTable;""", (epoch_from, epoch_to))

    result_weekly = cursor.fetchall()
    successful_by_pi_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(*) AS beneficiarycount, tenantid from jit_payment_inst_details where pistatus not in ('FAILED', 'PARTIAL', 'COMPLETED', 'APPROVED', 'INITIATED') and lastmodifiedtime<=%s  group by tenantid 
        ) AS tempTable;""", (epoch_to,))

    result_weekly = cursor.fetchall()
    successful_by_pi_cummulative_beneficiary_count = result_weekly[0][0]


    ############### Failure in JIT by transaction: Count of failed transactions which hit the JIT system(Failed+Partial that are failed) ##############
    # Weekly Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(beneficiarynumber) as beneficiarycount, jit_payment_inst_details.tenantid from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  pistatus in  ('FAILED','PARTIAL') and  jit_payment_inst_details.lastmodifiedtime>%s and jit_payment_inst_details.lastmodifiedtime<= %s and jit_beneficiary_details.paymentstatus='Payment Failed' Group by jit_payment_inst_details.tenantid
        ) AS tempTable;""", (epoch_from, epoch_to))

    result_weekly = cursor.fetchall()
    failure_by_transaction_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(beneficiarynumber) as beneficiarycount, jit_payment_inst_details.tenantid from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  pistatus in  ('FAILED','PARTIAL') and jit_payment_inst_details.lastmodifiedtime<= %s and jit_beneficiary_details.paymentstatus='Payment Failed' Group by jit_payment_inst_details.tenantid
        ) AS tempTable;""", (epoch_to,))

    result_weekly = cursor.fetchall()
    failure_by_transaction_cummulative_beneficiary_count = result_weekly[0][0]


    ############### Success in JIT by transaction: Count of successful transactions which hit the JIT system(Successful + Partial that is success) ##############
    # Weekly Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(beneficiarynumber) as beneficiarycount, jit_payment_inst_details.tenantid from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  pistatus  in ('SUCCESSFUL', 'PARTIAL')  and jit_payment_inst_details.lastmodifiedtime>%s and jit_payment_inst_details.lastmodifiedtime<= %s and jit_beneficiary_details.paymentstatus='Payment Successful' Group by jit_payment_inst_details.tenantid
        ) AS tempTable;""", (epoch_from, epoch_to))

    result_weekly = cursor.fetchall()
    success_by_transaction_weekly_beneficiary_count = result_weekly[0][0]

    # Cummulative Count
    cursor.execute("""SELECT SUM(beneficiarycount) AS total_beneficiary_count
        FROM (
            select count(beneficiarynumber) as beneficiarycount, jit_payment_inst_details.tenantid from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  pistatus  in ('SUCCESSFUL', 'PARTIAL')  and jit_payment_inst_details.lastmodifiedtime<= %s and jit_beneficiary_details.paymentstatus='Payment Successful' Group by jit_payment_inst_details.tenantid
        ) AS tempTable;""", (epoch_to,))

    result_weekly = cursor.fetchall()
    success_by_transaction_cummulative_beneficiary_count = result_weekly[0][0]

    ####################################################################################3

    # Prepare the data with hardcoded names
    data = pd.DataFrame([
        {" ": "Impact On Ground", "weekly": ground_level_weekly_beneficiary_count, "cumulative": ground_level_cummulative_beneficiary_count},
        {" ": "Failure in JIT by PI: Count of failure PI's which hit the JIT system(Show number of PI failed) ", "weekly": failure_by_pi_weekly_beneficiary_count, "cumulative": failure_by_pi_cummulative_beneficiary_count},
        {" ": "Partial in JIT by PI: Count of Partial PI's which hit the JIT system", "weekly": partial_by_pi_weekly_beneficiary_count, "cumulative": partial_by_pi_cummulative_beneficiary_count},
        {" ": "Successful in JIT by PI: Count of Successful PI's which hit the JIT system(Show number of PI Successful)", "weekly": successful_by_pi_weekly_beneficiary_count, "cumulative": successful_by_pi_cummulative_beneficiary_count},
        {" ": "Failure in JIT by transaction: Count of failed transactions which hit the JIT system(Failed+Partial that are failed)", "weekly": failure_by_transaction_weekly_beneficiary_count, "cumulative": failure_by_transaction_cummulative_beneficiary_count},
        {" ": "Success in JIT by transaction: Count of successful transactions which hit the JIT system(Successful + Partial that is success)", "weekly": success_by_transaction_weekly_beneficiary_count, "cumulative": success_by_transaction_cummulative_beneficiary_count}
    ])

    print(data)
    return data


def generateCountBasedOnPIStatusCummulative(connection, epoch_to):
    cursor = connection.cursor()

    ################## INDIVIDUAL ##################
    # Successful Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND'  and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'SUCCESSFUL' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    successful_individual = pd.DataFrame(result, columns=['ULB', 'Successful Individual'])

    # Partial Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND'  and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'PARTIAL' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    partial_individual = pd.DataFrame(result, columns=['ULB', 'Partial Individual'])

    # Failure Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND'  and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'FAILED' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    failed_individual = pd.DataFrame(result, columns=['ULB', 'Failed Individual'])

    ################## CBO ##################
    # Successful CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('SUCCESSFUL')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    successful_cbo = pd.DataFrame(result, columns=['ULB', 'Successful CBO'])

    # Partial CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('PARTIAL')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    partial_cbo = pd.DataFrame(result, columns=['ULB', 'Partial CBO'])

    # Failure CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('FAILED')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    failed_cbo = pd.DataFrame(result, columns=['ULB', 'Failed CBO'])

    ######################### VENDOR ##########################
    # Successful Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('SUCCESSFUL')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    successful_vendor = pd.DataFrame(result, columns=['ULB', 'Successful Vendor'])

    # Partial Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('PARTIAL')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    partial_vendor = pd.DataFrame(result, columns=['ULB', 'Partial Vendor'])

    # Failure Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('FAILED')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    failed_vendor = pd.DataFrame(result, columns=['ULB', 'Failed Vendor'])

    ####################### DEPT ########################
    # Successful Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('SUCCESSFUL')group by jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    successful_dept = pd.DataFrame(result, columns=['ULB', 'Successful Dept'])

    # Partial Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('PARTIAL')group by jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    partial_dept = pd.DataFrame(result, columns=['ULB', 'Partial Dept'])

    # Failed Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('FAILED')group by jit_payment_inst_details.tenantid;""", (epoch_to,))
    result = cursor.fetchall()
    failed_dept = pd.DataFrame(result, columns=['ULB', 'Failed Dept'])

    ############################################################################################

    data = pd.merge(successful_individual, partial_individual, on='ULB', how='outer')
    data = pd.merge(data, failed_individual, on='ULB', how='outer')
    data = pd.merge(data, successful_cbo, on='ULB', how='outer')
    data = pd.merge(data, partial_cbo, on='ULB', how='outer')
    data = pd.merge(data, failed_cbo, on='ULB', how='outer')
    data = pd.merge(data, successful_vendor, on='ULB', how='outer')
    data = pd.merge(data, partial_vendor, on='ULB', how='outer')
    data = pd.merge(data, failed_vendor, on='ULB', how='outer')
    data = pd.merge(data, successful_dept, on='ULB', how='outer')
    data = pd.merge(data, partial_dept, on='ULB', how='outer')
    data = pd.merge(data, failed_dept, on='ULB', how='outer')

    data['ULB'] = data['ULB'].str.replace('od.','')
    print(data)
    return data

def generateCountBasedOnPIStatusWeekly(connection, epoch_from, epoch_to):
    cursor = connection.cursor()

    ################## INDIVIDUAL ##################
    # Successful Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND'  and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'SUCCESSFUL' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to))
    result = cursor.fetchall()
    successful_individual = pd.DataFrame(result, columns=['ULB', 'Successful Individual'])

    # Partial Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND' and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'PARTIAL' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    partial_individual = pd.DataFrame(result, columns=['ULB', 'Partial Individual'])

    # Failure Individual Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='IND' and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus = 'FAILED' group by pistatus ,jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    failed_individual = pd.DataFrame(result, columns=['ULB', 'Failed Individual'])

    ################## CBO ##################
    # Successful CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG'
        AND jit_payment_inst_details.lastmodifiedtime > %s            
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('SUCCESSFUL')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    successful_cbo = pd.DataFrame(result, columns=['ULB', 'Successful CBO'])

    # Partial CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime > %s
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('PARTIAL')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from,epoch_to,))
    result = cursor.fetchall()
    partial_cbo = pd.DataFrame(result, columns=['ULB', 'Partial CBO'])

    # Failure CBO Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG'
        AND jit_payment_inst_details.lastmodifiedtime > %s 
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('FAILED')
        AND eg_org_function.category like '%%CBO%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    failed_cbo = pd.DataFrame(result, columns=['ULB', 'Failed CBO'])

    ######################### VENDOR ##########################
    # Successful Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime > %s
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('SUCCESSFUL')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    successful_vendor = pd.DataFrame(result, columns=['ULB', 'Successful Vendor'])

    # Partial Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime > %s
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('PARTIAL')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    partial_vendor = pd.DataFrame(result, columns=['ULB', 'Partial Vendor'])

    # Failure Vendor Count
    cursor.execute("""
        SELECT jit_payment_inst_details.tenantid,
            COUNT(DISTINCT jit_beneficiary_details.beneficiaryid) AS distinct_beneficiary_count
        FROM jit_beneficiary_details
        INNER JOIN jit_payment_inst_details ON jit_payment_inst_details.muktareferenceid = jit_beneficiary_details.muktareferenceid
        INNER JOIN eg_org_function ON jit_beneficiary_details.beneficiaryid = eg_org_function.org_id
        WHERE jit_beneficiary_details.beneficiarytype = 'ORG' 
        AND jit_payment_inst_details.lastmodifiedtime > %s
        AND jit_payment_inst_details.lastmodifiedtime <= %s
        AND jit_payment_inst_details.pistatus IN ('FAILED')
        AND eg_org_function.category like '%%VEN%%'
        GROUP BY jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    failed_vendor = pd.DataFrame(result, columns=['ULB', 'Failed Vendor'])

    ####################### DEPT ########################
    # Successful Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('SUCCESSFUL')group by jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    successful_dept = pd.DataFrame(result, columns=['ULB', 'Successful Dept'])

    # Partial Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('PARTIAL')group by jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    partial_dept = pd.DataFrame(result, columns=['ULB', 'Partial Dept'])

    # Failed Dept Count
    cursor.execute("""select jit_payment_inst_details.tenantid, count(distinct beneficiaryid) from jit_beneficiary_details inner join jit_payment_inst_details on jit_payment_inst_details.id=jit_beneficiary_details.piid where  jit_beneficiary_details.beneficiarytype='DEPT' and jit_payment_inst_details.lastmodifiedtime > %s and jit_payment_inst_details.lastmodifiedtime<=%s and pistatus in ('FAILED')group by jit_payment_inst_details.tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    failed_dept = pd.DataFrame(result, columns=['ULB', 'Failed Dept'])

    ############################################################################################

    data = pd.merge(successful_individual, partial_individual, on='ULB', how='outer')
    data = pd.merge(data, failed_individual, on='ULB', how='outer')
    data = pd.merge(data, successful_cbo, on='ULB', how='outer')
    data = pd.merge(data, partial_cbo, on='ULB', how='outer')
    data = pd.merge(data, failed_cbo, on='ULB', how='outer')
    data = pd.merge(data, successful_vendor, on='ULB', how='outer')
    data = pd.merge(data, partial_vendor, on='ULB', how='outer')
    data = pd.merge(data, failed_vendor, on='ULB', how='outer')
    data = pd.merge(data, successful_dept, on='ULB', how='outer')
    data = pd.merge(data, partial_dept, on='ULB', how='outer')
    data = pd.merge(data, failed_dept, on='ULB', how='outer')

    data['ULB'] = data['ULB'].str.replace('od.','')
    print(data)
    return data

def getValueOfMRSubmittedButNotApproved(connection, epoch_from, epoch_to):
    try:

        data = []
        for tenant in tenants:
            print(tenant)
            ids = []
            cursor = connection.cursor()

            cursor.execute("""select id from eg_wms_muster_roll where musterroll_status !='APPROVED' and lastmodifiedtime > %s and lastmodifiedtime <= %s and tenant_id = %s order by createdtime;""", (epoch_from,epoch_to,tenant))
            result = cursor.fetchall()

            # Store the fetched IDs in the list
            ids.extend([row[0] for row in result])

            totalValueOfMRSubmittedButNotApproved = getResponseFromExpenseCalculator(ids, tenant)

            # Append the result as a tuple (tenant, totalValueOfMusterRollCreated) to the data list
            data.append((tenant, totalValueOfMRSubmittedButNotApproved))

        # Convert the data list to a pandas DataFrame for easier formatting
        df = pd.DataFrame(data, columns=['ULB', 'Total Value of Muster Rolls Submitted but Not Approved(Rs)'])
        return df
                
    except Exception as e:
        print(e)

def generateWeeklyBasisDataBasedOnLastModifiedTime(connection, epoch_from, epoch_to):
    cursor = connection.cursor()

    # Count of  Muster Rolls Submitted but Not Approved 
    cursor.execute("""select tenant_id, count(id) from eg_wms_muster_roll where musterroll_status !='APPROVED' and lastmodifiedtime > %s and lastmodifiedtime <= %s group by eg_wms_muster_roll.tenant_id;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    muster_roll_submitted_but_not_approved = pd.DataFrame(result, columns=['ULB', 'Muster Rolls Submitted but Not Approved'])

    # No. of Wage Seekers Impacted in Muster Rolls Submitted but Not Approved
    cursor.execute("""select tenant_id, count( distinct individual_id) from eg_wms_attendance_summary inner join eg_wms_muster_roll on eg_wms_attendance_summary.muster_roll_id=eg_wms_muster_roll.id where musterroll_status != 'APPROVED' and eg_wms_muster_roll.lastmodifiedtime> %s and eg_wms_muster_roll.lastmodifiedtime<= %s group by tenant_id;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    no_of_wage_seekers_impacted_in_muster_roll_submitted_but_not_approved = pd.DataFrame(result, columns=['ULB', 'No. of Wage Seekers Impacted in Muster Rolls Submitted but Not Approved'])

    # Total Value of Muster Rolls Submitted but Not Approved
    valueOfMRSubmittedButNotApproved = getValueOfMRSubmittedButNotApproved(connection, epoch_from, epoch_to)


    # Number of Wage Bill Successfully paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.WAGES' and  paymentstatus='SUCCESSFUL' and lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_wage_bill_successfully_paid = pd.DataFrame(result, columns=['Number of Wage Bill Successfully paid', 'ULB'])

    # Value of wage bill successfully paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.WAGES' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_wage_bill_successfully_paid = pd.DataFrame(result, columns=['Value of wage bill successfully paid', 'ULB'])

    # Number of Purchase Bill Successfully paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and  paymentstatus='SUCCESSFUL' and lastmodifiedtime>%s and lastmodifiedtime <= %s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_purchase_bill_successfully_paid = pd.DataFrame(result, columns=['Number of Purchase Bill Successfully paid', 'ULB'])

    # Value of Purchase bill successfully paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_purchase_bill_successfully_paid = pd.DataFrame(result, columns=['Value of Purchase bill successfully paid', 'ULB'])

    # Number of Supervision Bill Successfully paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and  paymentstatus='SUCCESSFUL' and  lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_supervision_bill_successfully_paid = pd.DataFrame(result, columns=['Number of Supervision Bill Successfully paid', 'ULB'])

    # Value of Supervision bill successfully paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='SUCCESSFUL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_supervision_bill_successfully_paid = pd.DataFrame(result, columns=['Value of Supervision bill successfully paid', 'ULB'])

    # Number of Work Order Approved
    cursor.execute("""select count(*), tenant_id from eg_wms_contract where wf_status='APPROVED' and business_service='CONTRACT' and last_modified_time>%s and last_modified_time<=%s group by tenant_id;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_work_order_approved = pd.DataFrame(result, columns=['Number of Work Order Approved', 'ULB'])

    # Value of Work Order Approved
    cursor.execute("""select SUM(total_contracted_amount),tenant_id as totalAmount from eg_wms_contract where wf_status='APPROVED' and business_service='CONTRACT'  and last_modified_time>%s and last_modified_time<=%s group by tenant_id;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_work_order_approved = pd.DataFrame(result, columns=['Value of Work Order Approved', 'ULB'])

    # Number of Wage Bill Partial paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.WAGES' and  paymentstatus='PARTIAL' and lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_wage_bill_partially_paid = pd.DataFrame(result, columns=['Number of Wage Bill Partial paid', 'ULB'])

    # Value of Wage Bill Partial paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.WAGES' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='PARTIAL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_wage_bill_partially_paid = pd.DataFrame(result, columns=['Value of Wage Bill Partial paid', 'ULB'])

    # Number of Purchase Bill Partially paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and  paymentstatus='PARTIAL' and lastmodifiedtime>%s and lastmodifiedtime <= %s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_purchase_bill_partially_paid = pd.DataFrame(result, columns=['Number of Purchase Bill Partially paid', 'ULB'])

    # Value of Purchase bill Partially paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.PURCHASE' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='PARTIAL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_purchase_bill_partially_paid = pd.DataFrame(result, columns=['Value of Purchase bill Partially paid', 'ULB'])

    # Number of Supervision Bill Partially paid
    cursor.execute("""select count(*), tenantid from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and  paymentstatus='PARTIAL' and  lastmodifiedtime>%s and lastmodifiedtime<=%s group by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    number_of_supervision_bill_partially_paid = pd.DataFrame(result, columns=['Number of Supervision Bill Partially paid', 'ULB'])

    # Value of Supervision bill successfully paid
    cursor.execute("""select sum(totalpaidamount),tenantid from eg_expense_bill where businessservice='EXPENSE.SUPERVISION' and  lastmodifiedtime>%s and lastmodifiedtime<=%s and paymentstatus='PARTIAL' Group by tenantid order by tenantid;""", (epoch_from, epoch_to,))
    result = cursor.fetchall()
    value_of_supervision_bill_partially_paid = pd.DataFrame(result, columns=['Value of Supervision bill successfully paid', 'ULB'])



    ###############################################################################################################

    data = pd.DataFrame()
    data = pd.merge(muster_roll_submitted_but_not_approved, no_of_wage_seekers_impacted_in_muster_roll_submitted_but_not_approved, on='ULB', how='outer')
    data = pd.merge(data, valueOfMRSubmittedButNotApproved, on='ULB', how='outer')
    data = pd.merge(data, number_of_wage_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_wage_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, number_of_purchase_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_purchase_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, number_of_supervision_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_supervision_bill_successfully_paid, on='ULB', how='outer')
    data = pd.merge(data, number_of_work_order_approved, on='ULB', how='outer')
    data = pd.merge(data, value_of_work_order_approved, on='ULB', how='outer')
    data = pd.merge(data, number_of_wage_bill_partially_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_wage_bill_partially_paid, on='ULB', how='outer')
    data = pd.merge(data, number_of_purchase_bill_partially_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_purchase_bill_partially_paid, on='ULB', how='outer')
    data = pd.merge(data, number_of_supervision_bill_partially_paid, on='ULB', how='outer')
    data = pd.merge(data, value_of_supervision_bill_partially_paid, on='ULB', how='outer')

    
    data['ULB'] = data['ULB'].str.replace('od.','')
    print(data)
    return data


def writeDataToCSV(data, filename):
    if data.empty:
        print("No data to write.")
        return
    data.to_csv(filename, index=False)

if __name__ == '__main__':
    try:
        logging.info('Report Started Generating')

        # Get current date in ddmmyyyy format
        current_date = dt.datetime.now().strftime('%d%m%Y')

        # directory = '/data-mart-demo/datamart_' + current_date
        directory = '/mukta-report/muktareport/datamart-report/datamart_' + current_date
        if not os.path.exists(directory):
            os.makedirs(directory)
        
        # Get the epoch time of the latest Thursday 3:30 PM
        epoch_to = get_recent_thursday_epoch()
        # epoch_to = 1724322600000

        # Get the epoch time exactly 7 days before the epoch
        epoch_from = epoch_to - 7 * 24 * 3600 * 1000   # Subtract 7 days in milliseconds

        epoch_to = epoch_to + 3 * 3600 * 1000

        print(epoch_from, epoch_to)

        # Get current date in ddmmyyyy format
        current_date = dt.datetime.now().strftime('%d%m%Y')

        # Generate filenames with the current date
        mukta_datamart_filename = f"{directory}/mukta_datamart_report_{current_date}.csv"
        project_type_filename = f"{directory}/project_type_report_{current_date}.csv"
        amount_paid_bill_count_data_filename = f"{directory}/amount_paid_bill_count_report_{current_date}.csv"
        pi_level_count_data_filename = f"{directory}/pi_level_count_report_{current_date}.csv"
        pi_status_count_data_cumulative_filename = f"{directory}/pi_status_count_cumulative_report_{current_date}.csv"
        pi_status_count_data_weekly_filename = f"{directory}/pi_status_count_weekly_report_{current_date}.csv"
        weekly_basis_data_filename = f"{directory}/weekly_basis_report_{current_date}.csv"

        connection = connect_to_database()
        print("Connected to PostgreSQL")

        # Generate Mukta Datamart Basic Report
        mukta_datamart_basic_data = generateBasicInformations(connection, epoch_to)
        mukta_datamart_basic_data_file_path = os.path.join(directory, mukta_datamart_filename)
        writeDataToCSV(mukta_datamart_basic_data, mukta_datamart_basic_data_file_path)

        # Generate Mukta Datamart Project Type Report
        mukta_datamart_project_type_data = generateTotalCountByProjectType(connection, epoch_to)
        project_type_data_file_path = os.path.join(directory, project_type_filename)
        writeDataToCSV(mukta_datamart_project_type_data, project_type_data_file_path)

        # Generate Total amount paid and Count of bills
        amount_paid_bill_count_data = generateTotalAmountPaidAndCountOfBills(connection, epoch_to)
        amount_paid_bill_count_data_file_path = os.path.join(directory, amount_paid_bill_count_data_filename)
        writeDataToCSV(amount_paid_bill_count_data, amount_paid_bill_count_data_file_path)

        # Generate PI level count report
        pi_level_count_data = generateTotalCountOnPILevel(connection, epoch_from, epoch_to)
        pi_level_count_data_file_path = os.path.join(directory, pi_level_count_data_filename)
        writeDataToCSV(pi_level_count_data, pi_level_count_data_file_path)

        # Generate Count Based On PI Status
        pi_status_count_data_cumulative = generateCountBasedOnPIStatusCummulative(connection, epoch_to)
        pi_status_count_data_cumulative_file_path = os.path.join(directory, pi_status_count_data_cumulative_filename)
        writeDataToCSV(pi_status_count_data_cumulative, pi_status_count_data_cumulative_file_path)

        # Generate Count Based On PI Status Weekly
        pi_status_count_data_weekly = generateCountBasedOnPIStatusWeekly(connection, epoch_from, epoch_to)
        pi_status_count_data_weekly_file_path = os.path.join(directory, pi_status_count_data_weekly_filename)
        writeDataToCSV(pi_status_count_data_weekly, pi_status_count_data_weekly_file_path)

        # Weekly Basis Data Based on LastModifiedTime
        weekly_basis_data = generateWeeklyBasisDataBasedOnLastModifiedTime(connection, epoch_from, epoch_to)
        weekly_basis_data_file_path = os.path.join(directory, weekly_basis_data_filename)
        writeDataToCSV(weekly_basis_data, weekly_basis_data_file_path)

        logging.info('Report Generated Successfully')
        print(f"Reports saved in directory: {directory}")


    except Exception as ex:
        logging.error("Exception occured on main.", exc_info=True)
        raise(ex)