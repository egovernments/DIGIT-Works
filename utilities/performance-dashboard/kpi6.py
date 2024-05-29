import os

from es_client import search_es
from kpi5 import getTimeFromHistory

DAY_EPOCH_TIME = os.getenv('DAY_EPOCH_TIME')

"""
 KPI: Vendor payment requests verified within 2 days after submission from JE/IE/AEE
 KRA: 100% payments were completed within the stipulated timeframe
    FORMULA:  "Step 1: For each billing cycle, check [Y/N]:
(Date of vendor payment request verification) - (Date of vendor payment request submission) ≤ 2

Step 2: Calculate the percentage of success: (Count of 'Y')*100/(Total count)"
"""


def getBillsForEachTenant(tenantId):
    PURCHASE_BILL_BUSINESS_SERVICE = os.getenv('PURCHASE_BILL_BUSINESS_SERVICE')
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
                                "value": PURCHASE_BILL_BUSINESS_SERVICE
                            }
                        }
                    }
                ]
            }
        }
    }
    index_name = os.getenv('EXPENSE_BILL_INDEX')
    hit_again = True
    bills = []
    while hit_again:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hit_again = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for bill_hit in response.get('hits', {}).get('hits', []):
                bill = bill_hit.get('_source', {}).get('Data', {})
                bills.append(bill)
        else:
            hit_again = False
    return bills


def calculate_kpi6(cursor, tenantId):
    bills_map = {}
    count = 0
    bills = getBillsForEachTenant(tenantId)
    for bill in bills:
        bill_number = bill.get('billNumber')
        if bills_map.get(bill_number) is None:
            bills_map[bill_number] = {
                'billNumber': bill_number,
                'businessService': bill.get('businessService'),
                'paymentStatus': bill.get('paymentStatus'),
                'billCreatedTime': bill.get('auditDetails', {}).get('createdTime'),
                'projectId': bill.get('additionalDetails', {}).get('projectId'),
                'kpi6': 0,
                'billVerificationTime': getTimeFromHistory(bill.get('history', []), 'VERIFY_AND_FORWARD'),
                'billSubmissionTime': getTimeFromHistory(bill.get('history', []), 'SUBMIT')
            }

        if bills_map[bill_number]['billVerificationTime'] is not None and bills_map[bill_number][
            'billSubmissionTime'] is not None:
            bill_verification_time = bills_map[bill_number]['billVerificationTime']
            bill_submission_time = bills_map[bill_number]['billSubmissionTime']
            if bill_verification_time - bill_submission_time < 2 * int(DAY_EPOCH_TIME):
                count += 1
                bills_map[bill_number]['kpi6'] += 1

    print(bills_map)
    print(count)
    return bills_map
