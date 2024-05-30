'''
    KPI-4
    KRA : 100% payments were completed within the stipulated timeframe
    KPI: Vendor payment approved within 2 working days of receiving verified request from ME.
    FORMULA: "Step 1: For each billing cycle, check [Y/N]:
            (Date of payment approval) - (Date of receiving verified request from ME) ≤ 2

            Step 2: Calculate the percentage of success:
            (Count of 'Y')*100/(Total count)"
'''

import os

from common import writeDataToFile
from es_client import search_es
from kpi5 import getTimeFromHistory
from kpi6 import getBillsForEachTenant


def calculate_KPI2(curser, tenantId):
    bills_map = {}
    bills = getBillsForEachTenant(tenantId)
    success_count = 0
    total_count = 0
    for bill in bills:
        bill_number = bill.get('billNumber')
        if bills_map.get(bill_number) is None:
            bills_map[bill_number] = {
                'billNumber': bill_number,
                'businessService': bill.get('businessService'),
                'paymentStatus': bill.get('paymentStatus'),
                'billCreatedTime': bill.get('auditDetails', {}).get('createdTime'),
                'projectId': bill.get('additionalDetails', {}).get('projectId'),
                'kpi4': 0,
                'billVerificationTime': getTimeFromHistory(bill.get('history', []), 'VERIFY_AND_FORWARD'),
                'billApprovedTime': getTimeFromHistory(bill.get('history', []), 'APPROVED')
            }
        if bills_map[bill_number]['billVerificationTime'] is None:
            continue

        total_count += 1
        if bills_map[bill_number]['billVerificationTime'] is not None and bills_map[bill_number]['billApprovedTime'] is not None:
            bill_verification_time = bills_map[bill_number]['billVerificationTime']
            bill_approval_time = bills_map[bill_number]['billSubmissionTime']
            if bill_verification_time - bill_approval_time < 2 * int(os.getenv('DAY_EPOCH_TIME')):
                success_count += 1
                bills_map[bill_number]['kpi4'] += 1
    
    print(bills_map)
    print(success_count)
    return bills_map