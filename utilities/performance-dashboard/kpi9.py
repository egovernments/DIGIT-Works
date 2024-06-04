"""
 KPI: Vendor payment requests submitted within 3 days of project work completion
 KRA: At least 75% of projects included in yearly MUKTA action plan were completed during the financial year
 FORMULA: "Step 1: For each billing cycle, check [Y/N]:
        (Date of vendor payment request submission) - (Date of project completion) ≤ 3
        Step 2: Calculate the percentage of success: (Count of 'Y')*100/(Total count)"
"""


import os

from common import writeDataToFile, getKpiDetails, getAssigneeByDesignation, getInitialKpiObject, getHRMSIDMap
from es_client import search_es

KPI_NAME = "KPI9"
def getBills(tenantId, projectIds):
    query = {
        "from": 0,
        "size": 100,
        "sort": [
            {
                "Data.auditDetails.createdTime": {
                    "order": "asc"
                }
            }
        ],
        "_source": [
            "Data.id",
            "Data.billNumber",
            "Data.billDate",
            "Data.totalAmount",
            "Data.totalPaidAmount",
            "Data.businessService",
            "Data.paymentStatus",
            "Data.auditDetails",
            "Data.additionalDetails",
            "Data.billDetails.referenceId"
        ],
        "query": {
            "bool": {
                "must": [
                    {
                        "term": {
                            "Data.tenantId.keyword": {
                                "value": tenantId
                            }
                        }
                    },
                    {
                        "term": {
                            "Data.status.keyword": {
                                "value": "ACTIVE"
                            }
                        }
                    },
                    {
                        "terms": {
                            "Data.additionalDetails.projectId.keyword": projectIds
                        }
                    }
                ]
            }
        }
    }

    index_name = os.getenv('EXPENSE_BILL_INDEX')
    bills = []
    hasMoreRecords = True
    while hasMoreRecords:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hasMoreRecords = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for bill_hit in response.get('hits', {}).get('hits', []):
                bill = bill_hit.get('_source', {}).get('Data', {})
                bills.append(bill)
        else:
            hasMoreRecords = False
    return bills

def getContractDetailByProjectIds(tenantId, projectIds):
    query = {
        "from": 0,
        "size": 100,
        "_source": [
            "Data.id",
            "Data.contractNumber",
            "Data.completionPeriod",
            "Data.startDate",
            "Data.additionalDetails",
        ],
        "sort": [
            {
                "Data.auditDetails.createdTime": {
                    "order": "desc"
                }
            }
        ],
        "query": {
            "bool": {
                "must": [
                    {
                        "term": {
                            "Data.tenantId.keyword": {
                                "value": tenantId
                            }
                        }
                    },
                    {
                        "terms": {
                            "Data.additionalDetails.projectId.keyword": projectIds
                        }
                    }
                ]
            }
        }
    }

    index_name = os.getenv('CONTRACT_INDEX')
    hasMoreRecords = True
    contracts = []
    while hasMoreRecords:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hasMoreRecords = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for contractHits in response.get('hits', {}).get('hits', []):
                contract = contractHits.get('_source', {}).get('Data', {})
                contracts.append(contract)
        else:
            hasMoreRecords = False
    return contracts


def processBillDetails(tenantId, billDetailMap, projectContractMap, hrmsDetails):
    kpiDetail = getKpiDetails(KPI_NAME)
    hrmsEmployeeIdUsrDetailMap = getAssigneeByDesignation(hrmsDetails, kpiDetail['designations'])
    hrmsIds = list(hrmsEmployeeIdUsrDetailMap.keys())
    hasDirectEmployees = False
    kraByEmployeeIdMap = {}
    if len(hrmsIds) > 0:
        hasDirectEmployees = True

    for hrmsId in hrmsIds:
        kraByEmployeeIdMap[hrmsId] = getInitialKpiObject(tenantId, hrmsEmployeeIdUsrDetailMap[hrmsId]['designation'], hrmsEmployeeIdUsrDetailMap[hrmsId]['name'], kpiDetail['id'])

    for billid in billDetailMap:
        print('Processing muster roll : '+ billid)
        bill = billDetailMap[billid]
        if hasDirectEmployees:
            print('Process according to hrms user')
            for hrmsId in hrmsIds:
                kraByEmployeeIdMap[hrmsId]['total_count'] += 1
                if bill.get('billCreatedBeforeProjectCompletion'):
                    kraByEmployeeIdMap[hrmsId]['positive_count'] += 1
                else:
                    kraByEmployeeIdMap[hrmsId]['negative_count'] += 1

    return kraByEmployeeIdMap

def calculate_KPI9(curser, tenantId, projectIds, hrmsDetails):
    billDetailMap = {}
    billProjectIds = set()
    bills = getBills(tenantId, projectIds)
    for bill in bills:
        if bill.get('billNumber') not in billDetailMap:
            billDetailMap[bill.get('billNumber')] ={
                'id': bill.get('id'),
                'projectId': bill.get('additionalDetails', {}).get('projectId', None),
                'billCreatedTime': bill.get('auditDetails', {}).get('createdTime', None),
                'billCreatedBeforeProjectCompletion': False
            }
            billProjectIds.add(bill.get('additionalDetails', {}).get('projectId', None))

    billProjectIds = list(billProjectIds)
    contracts = getContractDetailByProjectIds(tenantId, billProjectIds)
    projectContractMap = {}
    for contract in contracts:
        projectId = contract.get('additionalDetails', {}).get('projectId', None)
        if projectId is not None and projectId not in projectContractMap:
            startDate = contract.get('startDate', None)
            completionPeriod = contract.get('completionPeriod', None)
            if startDate is not None and completionPeriod is not None:
                contract['contractEndDate'] = startDate + (completionPeriod * 24 * 60 * 60 * 1000)
            else:
                contract['contractEndDate'] = None
            projectContractMap[projectId] = contract

    for billId in billDetailMap:
        bill = billDetailMap[billId]
        contract = projectContractMap.get(bill.get('projectId', None), None)
        if contract is not None:
            if 'contractEndDate' in contract and 'billCreatedTime' in bill:
                isBillCreatedOnTime = False
                if bill.get('billCreatedTime') < contract.get('contractEndDate'):
                    isBillCreatedOnTime = True
                elif bill.get('billCreatedTime') - contract.get('contractEndDate') <= 3 * 24 * 60 * 60 * 1000:
                    isBillCreatedOnTime = True
                bill['billCreatedBeforeProjectCompletion'] = isBillCreatedOnTime

    kraByEmployeeIdMap = processBillDetails(tenantId, billDetailMap, projectContractMap, hrmsDetails)
    kra9Response = []
    for employeeId in kraByEmployeeIdMap:
        if kraByEmployeeIdMap[employeeId]['total_count'] > 0:
            score = (kraByEmployeeIdMap[employeeId]['positive_count'] / kraByEmployeeIdMap[employeeId]['total_count']) * 100
            kraByEmployeeIdMap[employeeId]['score'] = round(score, 2)
            kra9Response.append(kraByEmployeeIdMap[employeeId])
    return kra9Response