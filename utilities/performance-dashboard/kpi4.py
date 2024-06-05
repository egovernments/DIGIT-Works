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

from common import writeDataToFile, current_milli_time, getKpiDetails, getAssigneeByDesignation, getHRMSIDMap, \
    getInitialKpiObject
from es_client import search_es
from kpi5 import getTimeFromHistory
from kpi6 import getBillsForEachTenant

KPI_ID = 'KPI4'
def processBills(tenantId, billsMap, hrmsDetails, projectDataMap):
    kpiDetail = getKpiDetails(KPI_ID)
    hrmsEmployeeIdUsrDetailMap = getAssigneeByDesignation(hrmsDetails, kpiDetail['designations'])
    hrmsIds = list(hrmsEmployeeIdUsrDetailMap.keys())
    hasDirectEmployees = False
    projectUserMap = {}
    kraByEmployeeIdMap = {}
    if len(hrmsIds) > 0:
        hasDirectEmployees = True

    if not hasDirectEmployees:
        hrmsEmployeeIdUsrDetailMap = getHRMSIDMap(hrmsDetails)
        if len(billsMap.keys()) > 0:
            for billId in billsMap:
                print('billId : ', billId)
                projectId = billsMap[billId].get('projectId', None)
                if projectId is None or projectId not in projectDataMap:
                    continue
                inchargeId = projectDataMap[projectId].get('officerInChargeId', None)
                print('inchargeId : ', inchargeId)
                if inchargeId is not None and inchargeId not in projectUserMap:
                    projectUserMap[billsMap[billId]['projectId']] = inchargeId

    for hrmsId in hrmsIds:
        kraByEmployeeIdMap[hrmsId] = getInitialKpiObject(tenantId, hrmsEmployeeIdUsrDetailMap[hrmsId]['designation'],
                                                         hrmsEmployeeIdUsrDetailMap[hrmsId]['name'], kpiDetail['id'])

    for billId in billsMap:
        print('Processing bill for KPI4 : ' + billId)
        billObj = billsMap[billId]

        if hasDirectEmployees:
            print('Process according to hrms user')
            for hrmsId in hrmsIds:
                kraByEmployeeIdMap[hrmsId]['total_count'] += 1
                if billObj.get('isBillApprovedOnTime'):
                    kraByEmployeeIdMap[hrmsId]['positive_count'] += 1
                else:
                    kraByEmployeeIdMap[hrmsId]['negative_count'] += 1
        else:
            print('Process according to contract user')
            projectId = billObj.get('projectId', None)
            if projectId is None:
                continue
            if projectId not in projectUserMap:
                continue
            officerInchargeId = projectUserMap[billObj.get('projectId', None)]
            if officerInchargeId is not None:
                print('Process according to contract user')
                if officerInchargeId not in kraByEmployeeIdMap and officerInchargeId in hrmsEmployeeIdUsrDetailMap:
                    kraByEmployeeIdMap[officerInchargeId] = getInitialKpiObject(tenantId,
                                                                                hrmsEmployeeIdUsrDetailMap[
                                                                                    officerInchargeId]['designation'],
                                                                                hrmsEmployeeIdUsrDetailMap[
                                                                                    officerInchargeId]['name'],
                                                                                kpiDetail['id'])
                if officerInchargeId in kraByEmployeeIdMap:
                    kraByEmployeeIdMap[officerInchargeId]['total_count'] += 1
                    if billObj.get('isBillApprovedOnTime'):
                        kraByEmployeeIdMap[officerInchargeId]['positive_count'] += 1
                    else:
                        kraByEmployeeIdMap[officerInchargeId]['negative_count'] += 1
            else:
                print('Officer incharge not found for billId: ', billId)

    return kraByEmployeeIdMap

def calculate_KPI4(curser, tenantId, projectIds, hrmsDetails, projectDataMap):
    billsMap = {}
    bills = getBillsForEachTenant(tenantId, projectIds)
    for bill in bills:
        billVerificationTime = getTimeFromHistory(bill.get('history', []), 'VERIFY_AND_FORWARD')
        if billVerificationTime is None:
            continue
        currentTime = current_milli_time()
        twoDaysBackTime = currentTime - 2 * int(os.getenv('DAY_EPOCH_TIME'))
        if billVerificationTime > twoDaysBackTime:
            continue

        bill_number = bill.get('billNumber')
        if billsMap.get(bill_number) is None:
            billsMap[bill_number] = {
                'billNumber': bill_number,
                'businessService': bill.get('businessService'),
                'paymentStatus': bill.get('paymentStatus'),
                'billCreatedTime': bill.get('auditDetails', {}).get('createdTime'),
                'projectId': bill.get('additionalDetails', {}).get('projectId'),
                'isBillApprovedOnTime': False,
                'billVerificationTime': getTimeFromHistory(bill.get('history', []), 'VERIFY_AND_FORWARD'),
                'billApprovedTime': getTimeFromHistory(bill.get('history', []), 'APPROVE')
            }
        if billsMap[bill_number]['billApprovedTime'] is None:
            continue

        if billsMap[bill_number]['billVerificationTime'] is not None and billsMap[bill_number]['billApprovedTime'] is not None:
            bill_verification_time = billsMap[bill_number]['billVerificationTime']
            bill_approval_time = billsMap[bill_number]['billApprovedTime']
            if bill_verification_time - bill_approval_time < 2 * int(os.getenv('DAY_EPOCH_TIME')):
                billsMap[bill_number]['isBillApprovedOnTime'] = True

    kraByEmployeeIdMap = processBills(tenantId, billsMap, hrmsDetails, projectDataMap)
    kpi4Response = []
    for employeeId in kraByEmployeeIdMap:
        if kraByEmployeeIdMap[employeeId]['total_count'] > 0:
            score = (kraByEmployeeIdMap[employeeId]['positive_count'] / kraByEmployeeIdMap[employeeId]['total_count']) * 100
            kraByEmployeeIdMap[employeeId]['score'] = round(score, 2)
            kpi4Response.append(kraByEmployeeIdMap[employeeId])
    print('calculate_KPI4 : Calculated KPI4 for tenantId : ', tenantId)
    return kpi4Response