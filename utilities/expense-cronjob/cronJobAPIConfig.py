import requests
import json, os
from dotenv import load_dotenv
import logging

logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)

# The first argument to the script is the job name
# argumentList = sys.argv 
# job_name = sys.argv[1]
rootTenantId = None

def getContracts(requestInfo, tenantId, contractStatus):
    contracts = []
    try:
        limit = 100
        offset = 0
        pageNo = 1
        contract_host = os.getenv('CONTRACTS_SERVICE_HOST')
        contract_search = os.getenv('CONTRACTS_SEARCH')
        url = "{}{}".format(contract_host, contract_search)
        payload = {}
        payload["RequestInfo"] = requestInfo
        payload['tenantId'] = tenantId
        payload['status'] = contractStatus
        headers = {'Content-Type': 'application/json'}

        hasMoreRecords = True
        contractNumberSet= set()
        while hasMoreRecords:
            payload["pagination"] = { "limit": limit, "offSet": offset  }
            requestData = json.dumps(payload)
            response = requests.request("POST", url, headers=headers, data = requestData)
            # Convert the response to json
            payloadData = response.json()
            contractsData = payloadData.get("contracts", [])

            if (len(contractsData) > 0):
                for contract in contractsData:
                    contract_number = contract.get("contractNumber")
                    # Check if the contract number is not already added
                    if contract_number not in contractNumberSet:
                        contracts.append(contract)
                        contractNumberSet.add(contract_number)
                offset = limit * pageNo
                pageNo = pageNo+1
            else:
                hasMoreRecords = False
        return contracts
    except Exception as ex:
        logging.info("Exception while fetching contracts for tenant id: {}".format(tenantId))
        logging.error("Exception occurred", exc_info=True)
        return contracts



def getTenants():
    try:
        mdms_host = os.getenv('MDMS_SERVICE_HOST')
        mdms_search = os.getenv('MDMS_SEARCH')
        # Calls MDMS service to fetch cron job API endpoints configuration
        mdms_url = "{}{}".format(mdms_host, mdms_search)
        mdms_payload = {"RequestInfo":{"apiId":"asset-services","ver":None,"ts":None,"action":None,"did":None,"key":None,"msgId":"search with from and to values","authToken":"f81648a6-bfa0-4a5e-afc2-57d751f256b7"},"MdmsCriteria":{"tenantId":rootTenantId,"moduleDetails":[{"moduleName":"tenant","masterDetails":[{"name":"tenants"}]}]}}
        mdms_payload = json.dumps(mdms_payload)
        mdms_headers = {'Content-Type': 'application/json'}
        response = requests.request("POST", mdms_url, headers=mdms_headers, data = mdms_payload)
        # Convert the response to json
        mdms_data = response.json()
        tenants = mdms_data.get("MdmsRes", {}).get("tenant", {}).get("tenants", [])
        return tenants
    except Exception as ex:
        logging.info("Exception while fetching tenants")
        logging.error("Exception occurred", exc_info=True)
        return None

def getUser():
    try:
        userInfo = None
        user_host = os.getenv('USER_SERVICE_HOST')
        user_search = os.getenv('USER_SEARCH')
        # # Call user search to fetch SYSTEM user
        user_url = "{}{}?tenantId={}".format(user_host, user_search, rootTenantId)
        # It will be active after adding role config
        tenantId = rootTenantId
        userName = os.getenv('USER_NAME')
        user_payload = {"requestInfo":{"apiId":"ap.public","ver":"1","ts":45646456,"action":"POST","did":None,"key":None,"msgId":"8c11c5ca-03bd-11e7-93ae-92361f002671","userInfo":{"id":32},"authToken":"5eb3655f-31b1-4cd5-b8c2-4f9c033510d4"},"tenantId":tenantId,"userName":userName,"pageSize":"1"}

        # tenantId = "pg.citya"
        # uuid = os.getenv('USER_UUID')
        # user_payload = {"requestInfo":{"apiId":"ap.public","ver":"1","ts":45646456,"action":"POST","did":None,"key":None,"msgId":"8c11c5ca-03bd-11e7-93ae-92361f002671","userInfo":{"id":32},"authToken":"5eb3655f-31b1-4cd5-b8c2-4f9c033510d4"},"tenantId":tenantId,"uuid":[uuid],"pageSize":"1"}
        user_payload = json.dumps(user_payload)
        user_headers = {'Content-Type': 'application/json'}
        user_response = requests.request("POST", user_url, headers=user_headers, data = user_payload)
        users = user_response.json()['user']
        if len(users)==0:
            raise Exception("user not found")
        else:
            userInfo = users[0]
        return userInfo;
    except Exception as ex:
        logging.info("Exception while fetching user info.")
        logging.error("Exception occurred", exc_info=True)
        return None


def getRequestInfo(userInfo):
    requestInfo = json.loads("{\n    \"apiId\": \"Rainmaker\",\n    \"ver\": \".01\",\n    \"action\": \"\",\n    \"did\": \"1\",\n    \"key\": \"\",\n    \"msgId\": \"20170310130900|en_IN\",\n    \"requesterId\": \"\",\n    \"userInfo\": \"\"\n  }")
    requestInfo["userInfo"] = userInfo
    return requestInfo

def calculateExpense(contracts, requestInfo):
    success = 0
    failed = 0
    failedContractIds = []
    try:
        expense_calculator_host = os.getenv('EXPENSE_CALCULATOR_SERVICE_HOST')
        expense_calculator_URL = os.getenv('EXPENSE_CALCULATE_URL')
        url = "{}{}".format(expense_calculator_host, expense_calculator_URL)
        payload = {}
        payload["RequestInfo"] = requestInfo
        headers = {'Content-Type': 'application/json'}
        for contract in contracts:
            contractNumber = contract.get("contractNumber")
            logging.info("Generating expense for contract : {}".format(contractNumber))
            criteria = {}
            criteria["tenantId"] = contract.get("tenantId")
            criteria["contractId"] = contractNumber
            payload['criteria'] = criteria
            requestData = json.dumps(payload)
            try:
                response = requests.request("POST", url, headers=headers, data = requestData)
                logging.info("Response: {}".format(response.status_code))
                resp = response.json()
                if response.status_code >= 200 and response.status_code < 300:
                    success = success + 1
                else:
                    failed = failed + 1
                    failedContractIds.append(contractNumber)
                    if response.status_code == 400 and len(resp.get("Errors")):
                        errors = resp.get("Errors")
                        for error in errors:
                            logging.info("Error Message: {}".format(error.get("message")))
            except Exception as ex:
                logging.info("Exception while fetching contracts for tenant id: {}".format(contractNumber), exc_info=True)
                failed = failed + 1
                failedContractIds.append(contractNumber)
    except Exception as ex:
        logging.error("Exception while calculating expense", exc_info=True)

    return  [success, failed, failedContractIds]


'''
    Process the 
'''
def startExpenseCalcualtion():
    try:
        totalContracts = 0
        success = 0
        failed = 0
        failedContractIds = []
        logging.info("Fetching tenants.")
        tenants = getTenants()
        logging.info("Fetching User.")
        userInfo = getUser()
        contractStatus = os.getenv('CONTRACTS_STATUS')
        contractsList = []
        if tenants != None and len(tenants) > 0 and userInfo != None:
            requestInfo = getRequestInfo(userInfo=userInfo)
            for tenant in tenants:
                tenantId = tenant.get("code", None)
                if tenantId != rootTenantId:
                    logging.info("Fetching contracts for tenant : {}".format(tenantId))
                    contracts = getContracts(requestInfo=requestInfo, tenantId=tenantId, contractStatus=contractStatus)
                    logging.info("Get contracts : {}".format(len(contracts)))
                    contractsList = contractsList + contracts

            logging.info("========Calculat expense for contracts========")
            # Calculate contract
            totalContracts = len(contractsList)
            if totalContracts > 0:
                [success, failed, failedContractIds] = calculateExpense(contractsList, requestInfo)
            else:
                logging.info("Contracts are not available.")
        else:
            logging.info("TenantId list or User info is not available.")
        return [totalContracts, success, failed, failedContractIds]
    except Exception as ex:
        logging.error("Exception while processing data.", exc_info=True)
        raise(ex)

if __name__ == "__main__":
    try:
        logging.info('Started!')
        load_dotenv()
        rootTenantId = os.getenv('TENANT_ID')
        if rootTenantId != None:
            [totalContracts, success, failed, failedContractIds] = startExpenseCalcualtion()
            logging.info("Total contracts : {}".format(totalContracts))
            logging.info("Successfully generated : {}".format(success))
            logging.info("Failed : {}".format(failed))
            logging.info("Failed conract ids: {}".format(failedContractIds))
        else:
            logging.warning("Tenant id is missing in enviornment")
        logging.info('Ends!')
    except Exception as ex:
        logging.error("Exception occured on main.", exc_info=True)
        raise(ex)
    