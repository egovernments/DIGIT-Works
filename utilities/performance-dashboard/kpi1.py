'''
    KPI-1
    KRA : 100% payments were completed within the stipulated timeframe
    KPI: ULB disbursed 100% payments within stipulated timeframe*
    FORMULA: "Step 1: For each billing cycle, check [Y/N]:
            (Date of payment completion*) - (Date of muster roll submission) ≤ 9 (if NAC/Municipality) |11 (if corporation)

            Step 2: Calculate the percentage of success:
            (Count of 'Y')*100/(Total count)"

'''

import os

from common import writeDataToFile, getKpiDetails, getAssigneeByDesignation, getInitialKpiObject, getHRMSIDMap
from es_client import search_es
def getWageBills(tenantId, projectIds):
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
                        "term": {
                            "Data.businessService.keyword": {
                                "value": os.getenv('WAGE_BILL_BUSINESS_SERVICE')
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

def getMusterRolls(tenantId, projectIds):
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

    index_name = os.getenv('MUSTER_ROLL_INDEX')
    hasMoreRecords = True
    musterRolls = []
    while hasMoreRecords:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hasMoreRecords = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for musterRollHits in response.get('hits', {}).get('hits', []):
                musterRoll = musterRollHits.get('_source', {}).get('Data', {})
                musterRolls.append(musterRoll)
        else:
            hasMoreRecords = False
    return musterRolls


def getContractDetailByContractIds(tenantId, contractIds):
    query = {
        "from": 0,
        "size": 100,
        "_source": [
            "Data.id",
            "Data.contractNumber",
            "Data.additionalDetails.officerInChargeId"
        ],
        "sort": [
            {
                "Data.auditDetails.createdTime": {
                    "order": "asc"
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
                            "Data.contractNumber.keyword": contractIds
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


def processMusterRollDetails(tenantId, musterRollBillMap, hrmsDetails, contractIds):
    kpiDetail = getKpiDetails("KPI1")
    hrmsEmployeeIdUsrDetailMap = getAssigneeByDesignation(hrmsDetails, kpiDetail['designations'])
    hrmsIds = list(hrmsEmployeeIdUsrDetailMap.keys())
    hasDirectEmployees = False
    contractIdUserMap = {}
    kraByEmployeeIdMap = {}
    if len(hrmsIds) > 0:
        hasDirectEmployees = True

    if not hasDirectEmployees:
        hrmsEmployeeIdUsrDetailMap = getHRMSIDMap(hrmsDetails)
        contracts = getContractDetailByContractIds(tenantId, contractIds)
        if len(contracts) > 0:
            for contract in contracts:
                if contract.get('contractNumber') not in contractIdUserMap and contract.get('additionalDetails', {}).get('officerInChargeId', None) is not None:
                    contractIdUserMap[contract.get('contractNumber')] = contract.get('additionalDetails', {}).get('officerInChargeId', None)

    for hrmsId in hrmsIds:
        kraByEmployeeIdMap[hrmsId] = getInitialKpiObject(tenantId, hrmsEmployeeIdUsrDetailMap[hrmsId]['designation'], hrmsEmployeeIdUsrDetailMap[hrmsId]['name'], kpiDetail['id'])

    for musterRoll in musterRollBillMap:
        print('Processing muster roll : '+ musterRoll)
        muster = musterRollBillMap[musterRoll]
        if hasDirectEmployees:
            print('Process according to hrms user')
            for hrmsId in hrmsIds:
                kraByEmployeeIdMap[hrmsId]['total_count'] += 1
                if muster.get('isPaymentCompletedOnTime'):
                    kraByEmployeeIdMap[hrmsId]['positive_count'] += 1
                else:
                    kraByEmployeeIdMap[hrmsId]['negative_count'] += 1
        else:
            print('Process according to contract user')
            officerInchargeId = contractIdUserMap[muster.get('contractId', None)]
            if officerInchargeId is not None:
                print('Process according to contract user')
                if officerInchargeId not in kraByEmployeeIdMap and officerInchargeId in hrmsEmployeeIdUsrDetailMap:
                    kraByEmployeeIdMap[officerInchargeId] = getInitialKpiObject(tenantId,
                                                                     hrmsEmployeeIdUsrDetailMap[officerInchargeId]['designation'],
                                                                     hrmsEmployeeIdUsrDetailMap[officerInchargeId]['name'],
                                                                     kpiDetail['id'])
                if officerInchargeId in kraByEmployeeIdMap:
                    kraByEmployeeIdMap[officerInchargeId]['total_count'] += 1
                    if muster.get('isPaymentCompletedOnTime'):
                        kraByEmployeeIdMap[officerInchargeId]['positive_count'] += 1
                    else:
                        kraByEmployeeIdMap[officerInchargeId]['negative_count'] += 1
            else:
                print('Officer incharge not found for contractId: ', muster.get('contractId', None))

    return kraByEmployeeIdMap

def calculate_KPI1(curser, tenantId, projectIds, hrmsDetails):
    musterRollDetails = getMusterRolls(tenantId, projectIds)
    musterRollBillMap = {}
    contractIds = set()
    bills = getWageBills(tenantId, projectIds)
    for musterRoll in musterRollDetails:
        if musterRoll.get('musterRollNumber') not in musterRollBillMap:
            musterRollBillMap[musterRoll.get('musterRollNumber')] ={
                'id': musterRoll.get('id'),
                'projectId': musterRoll.get('additionalDetails', {}).get('projectId', None),
                'contractId': musterRoll.get('referenceId'),
                'isBillCreated': False,
                'isPaymentCompleted': False,
                'isPaymentCompletedOnTime': False,
                'musterRollCreatedTime': musterRoll.get('auditDetails', {}).get('createdTime', None),
                'paymentCompletionTime': None
            }
            contractIds.add(musterRoll.get('referenceId'))
    contractIds = list(contractIds)
    for bill in bills:
        referenceId = bill.get('billDetails', {})[0].get('referenceId', None)
        if referenceId is not None and referenceId in musterRollBillMap:
            musterRollBillMap[referenceId]['isBillCreated'] = True
            paymentCompleted = True if bill.get('paymentStatus', None) == 'SUCCESSFUL' else False
            print('Payment Status is : {} : {}'.format(bill.get('paymentStatus', None), paymentCompleted))
            musterRollBillMap[referenceId]['isPaymentCompleted'] = paymentCompleted
            if paymentCompleted:
                paymentCompletionTime = bill.get('auditDetails', {}).get('lastModifiedTime', None)
                musterRollBillMap[referenceId]['paymentCompletionTime'] = paymentCompletionTime
                musterRollCreatedTime = musterRollBillMap[referenceId]['musterRollCreatedTime']
                if (paymentCompletionTime - musterRollCreatedTime) <= 9 * int(os.getenv('DAY_EPOCH_TIME')):
                    musterRollBillMap[referenceId]['isPaymentCompletedOnTime'] = True

    kraByEmployeeIdMap = processMusterRollDetails(tenantId, musterRollBillMap, hrmsDetails, contractIds)
    kra1Response = []
    for employeeId in kraByEmployeeIdMap:
        if kraByEmployeeIdMap[employeeId]['total_count'] > 0:
            score = (kraByEmployeeIdMap[employeeId]['positive_count'] / kraByEmployeeIdMap[employeeId]['total_count']) * 100
            kraByEmployeeIdMap[employeeId]['score'] = round(score, 2)
            kra1Response.append(kraByEmployeeIdMap[employeeId])
    return kra1Response