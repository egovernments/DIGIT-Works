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

from es_client import search_es
def getWageBills(tenantId):
    query = {
        "from": 0,
        "size": 100,
        "sort": [
            {
                "Data.auditDetails.createdTime": {
                    "order": "desc"
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

def getMusterRollById(musterRollIds):
    query = {
        "from": 0,
        "size": 100,

        "query": {
            "bool": {
                "must": [
                    {
                        "terms": {
                            "Data.id.keyword": musterRollIds
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
            for bill_hit in response.get('hits', {}).get('hits', []):
                bill = bill_hit.get('_source', {}).get('Data', {})
                musterRolls.append(bill)
        else:
            hasMoreRecords = False
    return musterRolls


def processKPI1(curser, tenantId):
    bills = getWageBills(tenantId)
    musterRollIds = set([bill.get('additionalDetails', {}).get('referenceId') for bill in bills])
    musterRollDetails = getMusterRollById(musterRollIds)


    return