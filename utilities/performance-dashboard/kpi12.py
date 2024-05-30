from kpi2 import getProjectsFromLastFinancialYear
from kpi5 import getContractDetailByProjectId
import os
from kpi5 import getTimeFromHistory, getEstimateDetailByProjectId

DAY_EPOCH_TIME = os.getenv('DAY_EPOCH_TIME')
"""
 KPI: Work Order was verified and sent for approval for 100% of the final list of projects within 5 days from Administrative Approval 
 KRA: At least 75% of projects included in yearly MUKTA action plan were completed during the financial year
    FORMULA: "Step 1: For each project, check [Y/N]:
(Date of WO verified) - (Date of AA) ≤ 5

Step 2: Calculate the percentage of success: 
(Count of 'Y')*100/(Total count)"
"""


def calculate_kpi12(cursor, tenantId):
    projectDataMap = {}
    count = 0
    totalCount = 0
    projects = getProjectsFromLastFinancialYear(tenantId)
    for project in projects:
        projectNumber = project.get('projectNumber')
        if projectDataMap.get(projectNumber) is None:
            projectDataMap[projectNumber] = {
                'projectId': projectNumber,
                'contractNumber': None,
                'estimateNumber': None,
                'projectCreatedTime': project.get('auditDetails').get('createdTime'),
                'contractCreatedTime': None,
                'estimateCreatedTime': None,
                'kpi12': 0,
                'workOrderVerifiedTime': None,
                'estimateApprovedTime': None,
            }
        contracts = getContractDetailByProjectId(tenantId, project.get('projectNumber'))
        estimates = getEstimateDetailByProjectId(tenantId, project.get('id'))
        if len(estimates) > 0:
            totalCount += 1
            if len(contracts) > 0:
                contract = contracts[0]
                estimate = estimates[0]

                projectDataMap[projectNumber]['contractNumber'] = contract.get('contractNumber')
                projectDataMap[projectNumber]['estimateNumber'] = estimate.get('estimateNumber')
                projectDataMap[projectNumber]['contractCreatedTime'] = contract.get('auditDetails').get('createdTime')
                projectDataMap[projectNumber]['estimateCreatedTime'] = estimate.get('auditDetails').get('createdTime')
                projectDataMap[projectNumber]['estimateApprovedTime'] = getTimeFromHistory(estimate.get('history'),
                                                                                           'APPROVE')
                projectDataMap[projectNumber]['workOrderVerifiedTime'] = getTimeFromHistory(contract.get('history'),
                                                                                            'VERIFY_AND_FORWARD')
                if projectDataMap[projectNumber]['workOrderVerifiedTime'] is not None and projectDataMap[projectNumber][
                    'estimateApprovedTime'] is not None:
                    if (projectDataMap[projectNumber]['workOrderVerifiedTime'] - projectDataMap[projectNumber][
                        'estimateApprovedTime']) <= 5 * int(DAY_EPOCH_TIME):
                        count += 1
                        projectDataMap[projectNumber]['kpi12'] = 1

    print(projectDataMap)
    print(count / totalCount)
    projectDataMap['kpi12'] = count / totalCount
    projectDataMap['pos'] = count
    projectDataMap['neg'] = totalCount - count
    return projectDataMap
