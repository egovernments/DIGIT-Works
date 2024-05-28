from kpi2 import getProjectsFromLastFinancialYear
import os
from es_client import search_es

DAY_EPOCH_TIME = os.getenv('DAY_EPOCH_TIME')

"""
 KPI: Work Order was issued for 100% of the final list of projects within 7 days from Administrative Approval 
 KRA: At least 75% of projects included in yearly MUKTA action plan were completed during the financial year
 FORMULA: "Step 1: For each project, check [Y/N]:
            (Date of WO issue) - (Date of AA) ≤ 7
            
            Step 2: Calculate the percentage of success: 
            (Count of 'Y')*100/(Total count)"
"""


def getEstimateDetailByProjectId(tenantId, projectId):
    ESTIMATE_BUSINESS_SERVICE = os.getenv('ESTIMATE_BUSINESS_SERVICE')
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
                            "Data.projectId.keyword": {
                                "value": projectId
                            }
                        }
                    }
                ],
                "should": [
                    {
                        "term": {
                            "Data.businessService.keyword": {
                                "value": ESTIMATE_BUSINESS_SERVICE
                            }
                        }
                    },
                    {
                        "bool": {
                            "must_not": {
                                "exists": {
                                    "field": "Data.businessService.keyword"
                                }
                            }
                        }
                    }
                ],
                "minimum_should_match": 1
            }
        }
    }

    index_name = os.getenv('ESTIMATE_INDEX')
    response = search_es(index_name, query)
    contracts = []
    if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
        for contract_hit in response.get('hits', {}).get('hits', []):
            contract = contract_hit.get('_source', {}).get('Data', {})
            contracts.append(contract)
    return contracts


def getContractDetailByProjectId(tenantId, projectId):
    CONTRACT_BUSINESS_SERVICE = os.getenv('CONTRACT_BUSINESS_SERVICE')
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
                            "Data.additionalDetails.projectId.keyword": {
                                "value": projectId
                            }
                        }
                    }
                ],
                "should": [
                    {
                        "term": {
                            "Data.businessService.keyword": {
                                "value": CONTRACT_BUSINESS_SERVICE
                            }
                        }
                    },
                    {
                        "bool": {
                            "must_not": {
                                "exists": {
                                    "field": "Data.businessService.keyword"
                                }
                            }
                        }
                    }
                ],
                "minimum_should_match": 1
            }
        }
    }

    index_name = os.getenv('CONTRACT_INDEX')
    response = search_es(index_name, query)
    contracts = []
    if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
        for contract_hit in response.get('hits', {}).get('hits', []):
            contract = contract_hit.get('_source', {}).get('Data', {})
            contracts.append(contract)
    return contracts


def getTimeFromHistory(history, status):
    for historyItem in history:
        if historyItem.get('action') == status:
            return historyItem.get('auditDetails').get('createdTime')
    return None


def calculate_kpi5(cursor, tenantId):
    projectDataMap = {}
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
                'kpi5': 0,
                'workOrderInitiatedTime': None,
                'estimateApprovedTime': None,
            }
        contracts = getContractDetailByProjectId(tenantId, project.get('projectNumber'))
        estimates = getEstimateDetailByProjectId(tenantId, project.get('id'))
        if len(contracts) > 0 and len(estimates) > 0:
            contract = contracts[0]
            estimate = estimates[0]

            projectDataMap[projectNumber]['contractNumber'] = contract.get('contractNumber')
            projectDataMap[projectNumber]['estimateNumber'] = estimate.get('estimateNumber')
            projectDataMap[projectNumber]['contractCreatedTime'] = contract.get('auditDetails').get('createdTime')
            projectDataMap[projectNumber]['estimateCreatedTime'] = estimate.get('auditDetails').get('createdTime')
            projectDataMap[projectNumber]['estimateApprovedTime'] = getTimeFromHistory(estimate.get('history'),
                                                                                       'APPROVE')
            projectDataMap[projectNumber]['workOrderInitiatedTime'] = getTimeFromHistory(contract.get('history'),
                                                                                         'CREATE')
            if (projectDataMap[projectNumber]['workOrderInitiatedTime'] - projectDataMap[projectNumber][
                'estimateApprovedTime']) <= 7 * int(DAY_EPOCH_TIME):
                count += 1
                projectDataMap[projectNumber]['kpi5'] = 1

    print(projectDataMap)
    print(count)
    return projectDataMap
