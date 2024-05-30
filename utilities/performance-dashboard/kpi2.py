'''
    KPI-2
    KRA : At least 75% of projects included in yearly MUKTA action plan were completed during the financial year
    KPI: At least 75% of projects included in the ULB MUKTA action plan were completed during the financial year
    FORMULA: "Step 1: For each project, check  [Y/N]:
            (Actual date of project completion) ≤ 31 March 2024

            Step 2: Calculate the percentage of success:
            (Count of 'Y')*100/(Total count)"
'''

import os

from es_client import search_es
from common import getIsProjectCompleted, writeDataToFile


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
                      "gte": int(os.getenv('PROJECTS_FROM_DATE')),
                      "lte": int(os.getenv('PROJECTS_TO_DATE'))
                    }
                  }
                },
                # TODO: Remove after testing
                {
                  "terms": {
                    "Data.projectNumber.keyword": [
                      "PJ/2023-24/000188"
                    ]
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

def getBillsByProjectId(tenantId, projectId):
    query = {
        "size": 1,
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
        },
        "aggs": {
            "billAmount": {
                "sum": {
                    "field": "Data.totalAmount"
                }
            }
        }
    }

    index_name = os.getenv('EXPENSE_BILL_INDEX')
    response = search_es(index_name, query)
    bills = []
    sumOfBillAmount = 0
    if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
        for bill_hit in response.get('hits', {}).get('hits', []):
            bill = bill_hit.get('_source', {}).get('Data', {})
            bills.append(bill)
    if response and 'aggregations' in response and 'billAmount' in response['aggregations'] and 'value' in response['aggregations']['billAmount']:
        sumOfBillAmount = response['aggregations']['billAmount']['value']
    return [bills, sumOfBillAmount]

def calculate_KPI2(curser, tenantId):
    projectsDataMap = {}
    projects = getProjectsFromLastFinancialYear(tenantId)
    print(projects)
    for project in projects:
        projectMap = {}
        if project.get('projectNumber') not in projectsDataMap:
             projectMap = {
                'id': project.get('id'),
                'projectNumber': project.get('projectNumber'),
                'address': project.get('address'),
                'isProjectCompleted': False,
                'isProjectCompletedOnTime': False,
                'lastBillDate': None,
                'officerInChargeId': None,
                'contractedAmount': 0,
                'contractId': None,
                'contractNumber': None
            }
        else:
            projectMap = projectsDataMap[project.get('projectNumber')]
        print(project)
        contracts = getContractDetailByProjectId(tenantId, project.get('projectNumber'))
        if contracts and len(contracts) > 0:
            contract = contracts[0]
            projectMap['contractId'] = contract.get('id')
            projectMap['contractNumber'] = contract.get('contractNumber')
            projectMap['assigneeUserId'] = contract.get('assigneeUserId')
            projectMap['contractedAmount'] = contract.get('totalContractedAmount')
            projectMap['officerInChargeId'] = contract.get('additionalDetails', {}).get('assigneeUserId')
            bills = []
            sumOfBillAmount = 0
            [bills, sumOfBillAmount] = getBillsByProjectId(tenantId, project.get('projectNumber'))
            if len(bills) > 0:
                isProjectCompleted = getIsProjectCompleted(contractedAmount=projectMap['contractedAmount'], billAmount=sumOfBillAmount)
                projectMap['isProjectCompleted'] = isProjectCompleted
                if isProjectCompleted:
                    projectMap['lastBillDate'] = bills[0].get('auditDetails', {}).get('createdTime')
                    if projectMap['lastBillDate'] <= int(os.getenv('PROJECTS_COMPLETION_DATE')):
                        projectMap['isProjectCompletedOnTime'] = True
        projectsDataMap[project.get('projectNumber')] = projectMap

    print(projectsDataMap)
    #todo: get user details and assign to that
    writeDataToFile(tenantId=tenantId, kpiId='kpi2', data = projectsDataMap)

    return