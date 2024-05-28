
import os

from es_client import search_es


def getProjectsFromLastFinancialYear(tenantId):
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
                  "range": {
                    "Data.auditDetails.createdTime": {
                      "gte": 1680307200000,
                      "lte": 1711929599000
                    }
                  }
                }
              ]
            }
        }
    }

    index_name = os.getenv('PROJECT_INDEX')
    hit_again = True
    projects = []
    while hit_again:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hit_again = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for project_hit in response.get('hits', {}).get('hits', []):
                project = project_hit.get('_source', {}).get('Data', {})
                projects.append(project)
        else:
            hit_again = False
    return projects


def getContractDetailByProjectId(tenantId, projectId):
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
                ]
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


def calculateKPI2(curser, tenantId):
    projectDataMap = {}
    projects = getProjectsFromLastFinancialYear(tenantId)
    print(projects)
    for project in projects:
        if projectDataMap[project.get('projectNumber')] is None:
            projectDataMap[project.get('projectNumber')] = {
                'id': project.get('id'),
                'projectNumber': project.get('projectNumber'),
                'address': project.get('address'),
                'isProjectCompleted': False,
                'officerInChargeId': None,
                'contractedAmount': 0,
                'contractId': None,
                'contractNumber': None,
            }
        print(project)
        contracts = getContractDetailByProjectId(tenantId, project.get('projectNumber'))
        if contracts and len(contracts) > 0:
            contract = contracts[0]
            projectDataMap[project.get('projectNumber')]['contractId'] = contract.get('id')
            projectDataMap[project.get('projectNumber')]['contractNumber'] = contract.get('contractNumber')
            projectDataMap[project.get('projectNumber')]['assigneeUserId'] = contract.get('assigneeUserId')
            projectDataMap[project.get('projectNumber')]['contractedAmount'] = contract.get('totalContractedAmount')
            projectDataMap[project.get('projectNumber')]['officerInChargeId'] = contract.get('additionalDetails', {}).get('assigneeUserId')



    return