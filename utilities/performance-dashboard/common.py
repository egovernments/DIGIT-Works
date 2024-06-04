import os
import json

def getIsProjectCompleted(contractedAmount, billAmount):

    return billAmount >= 0.95 * contractedAmount

def writeDataToFile(tenantId, kpiId, data):
    output_directory = os.path.join(os.getcwd(), kpiId)
    createFolderIfNotExists(output_directory)
    # Path to your Excel file
    file_name = f'{tenantId}.json'
    # Combine the directory and file name to get the full file path
    file_path = os.path.join(output_directory, file_name)
    with open(file_path, 'w') as f:
        json.dump(data, f, indent=4)
    print(data)

def createFolderIfNotExists(folderPath):
    # Check if the folder already exists
    if not os.path.exists(folderPath):
        # If the folder does not exist, create it
        os.makedirs(folderPath)
        print(f"Folder '{folderPath}' created.")

def getKpiDetails(kpiId):
    kpiDetails = []
    kpiFilePath = os.path.join(os.getcwd(), 'kpi_details.json')
    with open(kpiFilePath, 'r') as f:
        kpiDetails = json.load(f)
    kpis =  [item for item in kpiDetails if item["id"] == kpiId]
    if len(kpis) > 0:
        return kpis[0]
    return {}


def getAssigneeByDesignation(hrmsDetails, designations):
    # Create a set of designations to search for
    designationsSet = set(designations)
    hasRecordFound = False
    hrmsIdDesignationMap = {}
    for designation in designationsSet:
        if not hasRecordFound:
            for employee in hrmsDetails:
                if employee['assignments'][0]['designation'] == designation:
                    hrmsIdDesignationMap[employee.get('code')] = {'designation': designation, 'name': employee.get('user', {}).get('name', None)}
                    hasRecordFound = True
    return hrmsIdDesignationMap

def getHRMSIDMap(hrmsDetails):
    hrmsIdMap = {}
    for employee in hrmsDetails:
        hrmsIdMap[employee.get('code')] = {'designation': employee['assignments'][0]['designation'], 'name': employee.get('user', {}).get('name', None)}
    return hrmsIdMap


designation_map = {
    "WRK_EE": "Executive Engineer",
    "WRK_DEE": "Deputy Executive Engineer",
    "WRK_AEE": "Assistant Executive Engineer",
    "WRK_ME": "Municipal Engineer",
    "WRK_CE": "City Engineer",
    "WRK_AE": "Assistant Engineer",
    "WRK_JE": "Junior Engineer",
    "WRK_RI": "Revenue Inspector",
    "WRK_AM": "Ameen",
    "WRK_DA": "Dealing Assistant",
    "WRK_PC": "Program Coordinator",
    "WRK_IE": "Implementation Expert",
    "WRK_ACCE": "Account Expert",
    "ACC_AO": "Accounts Officer",
    "ACC_JAO": "Junior Accounts Officer",
    "ADM_MC": "Commissioner",
    "ADM_ADMC": "Additional Commissioner",
    "ADM_DMC": "Deputy Commissioner",
    "ADM_AMC": "Assistant Commissioner",
    "ADM_EO": "Executive Officer",
    "ADM_AEO": "Assistant Executive Officer",
    "Tax_WO": "Ward Officer",
    "ADM_DEO": "Data Entry Operator"
}
def getInitialKpiObject(tenantId, designation, employeeName, kpiId):
    desig = None
    kpiDetail = getKpiDetails(kpiId)
    district = tenantId.split('.')[1]
    if designation is not None:
        desig = designation_map[designation]
    return {
        "program": "Mukta",
        "start_date": "2023-04-01T00:00:00Z",
        "end_date": "2024-03-31T23:59:59Z",
        "district": district.capitalize(),
        "ulb": district.capitalize(),
        "kra": kpiDetail.get('kra', None),
        "kpi": kpiDetail.get('kpi_name', None),
        "designation": desig,
        "employee_name": employeeName,
        "score": 0,
        "total_count": 0,
        "positive_count": 0,
        "negative_count": 0
    }