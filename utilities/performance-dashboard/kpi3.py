'''
    KPI-3
    KRA : At least 75% of the projects had no time overrun
    KPI: At least 75% of the proposed projects had no time overrun
    FORMULA: "Step 1: For all completed projects, check [Y/N]:
            (Actual date of completion) ≤ (Scheduled date of completion as per WO)

            Step 2: Calculate the percentage of success:
            (Count of 'Y')*100/(Total count)"
'''
import os

from common import getKpiDetails, getAssigneeByDesignation, getHRMSIDMap, getInitialKpiObject
from es_client import search_es
KPI_ID = 'KPI3'
def processProjects(tenantId, projectDataMap, hrmsDetails):
    kpiDetail = getKpiDetails(KPI_ID)
    hrmsEmployeeIdUsrDetailMap = getAssigneeByDesignation(hrmsDetails, kpiDetail['designations'])
    hrmsIds = list(hrmsEmployeeIdUsrDetailMap.keys())
    hasDirectEmployees = False
    contractIdUserMap = {}
    kraByEmployeeIdMap = {}
    if len(hrmsIds) > 0:
        hasDirectEmployees = True

    if not hasDirectEmployees:
        hrmsEmployeeIdUsrDetailMap = getHRMSIDMap(hrmsDetails)
        if len(projectDataMap.keys()) > 0:
            for projectId in projectDataMap:
                print('projectId : ', projectId)
                inchargeId = projectDataMap[projectId].get('officerInChargeId', None)
                print('inchargeId : ', inchargeId)
                if inchargeId is not None and inchargeId not in contractIdUserMap:
                    contractIdUserMap[projectDataMap[projectId]['contractNumber']] = inchargeId

    for hrmsId in hrmsIds:
        kraByEmployeeIdMap[hrmsId] = getInitialKpiObject(tenantId, hrmsEmployeeIdUsrDetailMap[hrmsId]['designation'],
                                                         hrmsEmployeeIdUsrDetailMap[hrmsId]['name'], kpiDetail['id'])

    for projectId in projectDataMap:
        print('Processing project for KPI3 for : ' + projectId)
        projectObj = projectDataMap[projectId]
        if projectObj.get('isProjectCompleted') is False:
            continue

        projectObj['isProjectCompletedOnTime'] = False
        if projectObj.get('contractEndDate', None) is not None:
            if projectObj.get('lastBillDate') <= projectObj.get('contractEndDate'):
                projectObj['isProjectCompletedOnTime'] = True
            else:
                projectObj['isProjectCompletedOnTime'] = False
        if hasDirectEmployees:
            print('Process according to hrms user')
            for hrmsId in hrmsIds:
                kraByEmployeeIdMap[hrmsId]['total_count'] += 1
                if projectObj.get('isProjectCompletedOnTime'):
                    kraByEmployeeIdMap[hrmsId]['positive_count'] += 1
                else:
                    kraByEmployeeIdMap[hrmsId]['negative_count'] += 1
        else:
            print('Process according to contract user')
            contractNumber = projectObj.get('contractNumber', None)
            if contractNumber is None:
                continue
            if contractNumber not in contractIdUserMap:
                continue
            officerInchargeId = contractIdUserMap[projectObj.get('contractNumber', None)]
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
                    if projectObj.get('isProjectCompletedOnTime'):
                        kraByEmployeeIdMap[officerInchargeId]['positive_count'] += 1
                    else:
                        kraByEmployeeIdMap[officerInchargeId]['negative_count'] += 1
            else:
                print('Officer incharge not found for projectId: ', projectId)

    return kraByEmployeeIdMap


def calculate_KPI3(cursor, tenantId, projects, hrmsDetails, projectsDataMap):
    print('calculate_KPI3 : Calculating KPI3 for tenantId : ', tenantId)
    kraByEmployeeIdMap = processProjects(tenantId, projectsDataMap, hrmsDetails)
    kpi3Response = []
    for employeeId in kraByEmployeeIdMap:
        if kraByEmployeeIdMap[employeeId]['total_count'] > 0:
            score = (kraByEmployeeIdMap[employeeId]['positive_count'] / kraByEmployeeIdMap[employeeId]['total_count']) * 100
            kraByEmployeeIdMap[employeeId]['score'] = round(score, 2)
            kpi3Response.append(kraByEmployeeIdMap[employeeId])
    print('calculate_KPI3 : Calculated KPI3 for tenantId : ', tenantId)
    return kpi3Response