import os

from es_client import search_es
from kpi5 import getTimeFromHistory

DAY_EPOCH_TIME = os.getenv('DAY_EPOCH_TIME')

"""
    KPI: Muster roll approved within 3 days of submission
    KRA: 100% payments were completed within the stipulated timeframe
    FORMULA:  "Step 1: For each payment cycle, check [Y/N]: 
(Date of muster roll approval) - (Date of muster roll submission) ≤ 3

Step 2: Calculate the percentage of success: 
(Count of 'Y')*100/(Total count)"
"""


def getMusterRollsForEachTenant(tenantId, projectIds):
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
                        "terms": {
                            "Data.additionalDetails.projectId.keyword": projectIds
                        }
                    }
                ]
            }
        }
    }
    index_name = os.getenv('MUSTER_ROLL_INDEX')
    hit_again = True
    muster_rolls = []
    while hit_again:
        response = search_es(index_name, query)
        if response and 'hits' in response and len(response.get('hits', {}).get('hits', [])) > 0:
            hit_again = True
            query['from'] = query['from'] + len(response.get('hits', {}).get('hits', []))
            for bill_hit in response.get('hits', {}).get('hits', []):
                bill = bill_hit.get('_source', {}).get('Data', {})
                muster_rolls.append(bill)
        else:
            hit_again = False
    return muster_rolls


def calculate_kpi7(cursor, tenantId, projectIds):
    muster_roll_data_map = {}
    count = 0
    totalCount = 0
    muster_rolls = getMusterRollsForEachTenant(tenantId, projectIds)
    for muster_roll in muster_rolls:
        muster_roll_number = muster_roll.get('musterRollNumber')
        print("Processing KPI7 for muster roll number: ", muster_roll_number)
        projectId= muster_roll.get('additionalDetails').get('projectId')
        if muster_roll_data_map.get(muster_roll_number) is None:
            muster_roll_data_map[muster_roll_number] = {
                'musterRollNumber': muster_roll_number,
                'contractNumber': muster_roll.get('referenceId'),
                'projectId': muster_roll.get('additionalDetails').get('projectId'),
                'kpi7': 0,
                'musterRollCreatedTime': getTimeFromHistory(muster_roll.get('history'), 'SUBMIT'),
                'musterRollApprovedTime': getTimeFromHistory(muster_roll.get('history'), 'APPROVE')
            }

        totalCount += 1
        musterRollCreatedTime = muster_roll_data_map[muster_roll_number].get('musterRollCreatedTime')
        musterRollApprovedTime = muster_roll_data_map[muster_roll_number].get('musterRollApprovedTime')
        if musterRollCreatedTime is not None and musterRollApprovedTime is not None:
            if (musterRollApprovedTime - musterRollCreatedTime) <= 3 * int(DAY_EPOCH_TIME):
                count += 1
                muster_roll_data_map[muster_roll_number]['kpi7'] = 1

    if totalCount > 0:
        muster_roll_data_map['kpi7'] = count / totalCount
    else:
        muster_roll_data_map['kpi7'] = 0
    muster_roll_data_map['pos'] = count
    muster_roll_data_map['neg'] = totalCount - count
    print("KPI 7: ", muster_roll_data_map['kpi7'] * 100)
    return muster_roll_data_map
