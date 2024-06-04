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
from common import getIsProjectCompleted, writeDataToFile, getKpiDetails, getAssigneeByDesignation, getHRMSIDMap, \
    getInitialKpiObject

KPI_ID = 'KPI2'

def getProjectsFromLastFinancialYear(tenantId, fromDate, toDate):
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
                      "gte": fromDate,
                      "lte": toDate
                    }
                  }
                },
                # # TODO: Remove after testing
                # {
                #   "terms": {
                #     "Data.projectNumber.keyword": [
                #       "PJ/2023-24/000188"
                #     ]
                #   }
                # }
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
                    # {
                    #     "term": {
                    #         "Data.status.keyword": {
                    #             "value": "ACTIVE"
                    #         }
                    #     }
                    # },
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

def processBillDetails(tenantId, projectDataMap, hrmsDetails):
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
        print('Processing project : ' + projectId)
        projectObj = projectDataMap[projectId]
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

def calculate_KPI2(curser, tenantId, projects, hrmsDetails):
    projectsDataMap = {}
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
            projectMap['contractedAmount'] = contract.get('contractedAmount', 0)
            projectMap['officerInChargeId'] = contract.get('additionalDetails', {}).get('officerInChargeId', None)
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

    kraByEmployeeIdMap = processBillDetails(tenantId, projectsDataMap, hrmsDetails)
    kpi2Response = []
    for employeeId in kraByEmployeeIdMap:
        if kraByEmployeeIdMap[employeeId]['total_count'] > 0:
            score = (kraByEmployeeIdMap[employeeId]['positive_count'] / kraByEmployeeIdMap[employeeId][
                'total_count']) * 100
            kraByEmployeeIdMap[employeeId]['score'] = round(score, 2)
            kpi2Response.append(kraByEmployeeIdMap[employeeId])
    return kpi2Response