import os

from kpi2 import getProjectsFromLastFinancialYear
from kpi5 import getContractDetailByProjectId
from kpi5 import getTimeFromHistory, getEstimateDetailByProjectId

DAY_EPOCH_TIME = os.getenv('DAY_EPOCH_TIME')
"""
 KPI: Submitted draft Work Orders for 100% of projects to ME within 30 days from Technical Sanction and Administrative Approval 
 KRA: At least 75% of the projects had no time overrun
    FORMULA: "Step 1: For each project, check [Y/N]: 
(Date of draft WO submission to ME) - (Date of Administrative Approval) ≤ 30

Step 2: Calculate the percentage of success: 
(Count of 'Y')*100/(Total count of projects)"
"""


def calculate_kpi8(cursor, tenantId):
    projectDataMap = {}
    totalCount = 0
    count = 0
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
                'kpi8': 0,
                'workOrderAcceptedTime': None,
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
                projectDataMap[projectNumber]['workOrderAcceptedTime'] = getTimeFromHistory(contract.get('history'),
                                                                                            'ACCEPT')
                if projectDataMap[projectNumber]['workOrderAcceptedTime'] is not None and projectDataMap[projectNumber][
                    'estimateApprovedTime'] is not None:
                    if (projectDataMap[projectNumber]['workOrderAcceptedTime'] - projectDataMap[projectNumber][
                        'estimateApprovedTime']) <= 30 * int(DAY_EPOCH_TIME):
                        count += 1
                        projectDataMap[projectNumber]['kpi8'] = 1

    print(projectDataMap)
    print(count / totalCount)
    projectDataMap['kpi8'] = count / totalCount
    projectDataMap['pos'] = count
    projectDataMap['neg'] = totalCount - count
    return projectDataMap
