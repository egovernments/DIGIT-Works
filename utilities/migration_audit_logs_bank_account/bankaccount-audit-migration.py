import requests
import json, os
import logging
import sys

'''
    While running this migration script of bank account to audit log make sure to follow these these points
        *   install dependencies which is added in requirements.txt
        *   update rootTenantId which is hardcoded 'pg'
        *   update ulbTenantId which is hardcoded 'pg.citya'
        *   update userName which is hardcoded 'BILL_GEN_CRONJOB'
        *   Make sure user which you are using will have bank account access and data which you are fetching from bank account must be decrypted
        *   port-forword user services to 8091
        *   port-forword bankaccount services to 8092
        *   port-forword audit services to 8093
        *   port-forword encryption services to 8094
        *   then run the script for each ULB
'''

rootTenantId = "od"
ulbTenantId = "od.jatni"

user_host = "http://localhost:8091/"
bank_account_host = "http://localhost:8092/"
audit_host = "http://localhost:8093/"
encryption_host = "http://localhost:8094/"

user_search = "user/v1/_search"
userName = "IFMS_CRONJOB"

bank_account_search = "bankaccount-service/bankaccount/v1/_search"
audit_create = "audit-service/log/v1/_create"
audit_search = "audit-service/log/v1/_search"
encryption_encrypt = "egov-enc-service/crypto/v1/_encrypt"


#logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)
logging.basicConfig(
    filename='jatni_prod.log',
    format='%(asctime)s - %(message)s', level=logging.INFO
)



def getRequestInfo(userInfo):
    requestInfo = json.loads("{\n    \"apiId\": \"Rainmaker\",\n    \"ver\": \".01\",\n    \"action\": \"\",\n    \"did\": \"1\",\n    \"key\": \"\",\n    \"msgId\": \"20170310130900|en_IN\",\n  \"userInfo\": \"\"\n  }")
    newUserInfo = {}
    newUserInfo["id"] = userInfo["id"]
    newUserInfo["uuid"] = userInfo["uuid"]
    newUserInfo["userName"] = userInfo["userName"]
    newUserInfo["tenantId"] = userInfo["tenantId"]
    newUserInfo["roles"] = userInfo["roles"]
    requestInfo["userInfo"] = newUserInfo
    return requestInfo

def getUser(): 
    try:
        userInfo = None
        # # Call user search to fetch SYSTEM user
        user_url = "{}{}?tenantId={}".format(user_host, user_search, rootTenantId)
        # It will be active after adding role config
        tenantId = rootTenantId
        user_payload = {"requestInfo":{"apiId":"ap.public","ver":"1","ts":45646456,"action":"POST","did":None,"key":None,"msgId":"8c11c5ca-03bd-11e7-93ae-92361f002671","userInfo":{"id":32},"authToken":"5eb3655f-31b1-4cd5-b8c2-4f9c033510d4"},"tenantId":tenantId,"userName":userName,"pageSize":"1"}

        user_payload = json.dumps(user_payload)
        user_headers = {'Content-Type': 'application/json'}
        user_response = requests.request("POST", user_url, headers=user_headers, data = user_payload)
        logging.info("Recieved response from user:{}".format(user_response.status_code))
        users = user_response.json()['user']
        if len(users)==0:
            raise Exception("user not found")
        else:
            userInfo = users[0]
        return userInfo
    except Exception as ex:
        logging.info("Exception while fetching user info.")
        logging.error("Exception occurred", exc_info=True)
        return None

def getBankAccounts(requestInfo):
    bank_accounts = []
    hasData = True
    limit = 100
    pageNo = 0
    while hasData:
        logging.info("Started getBankAccounts")
        bankaccount_search_url = "{}{}".format(bank_account_host, bank_account_search)
        payload = {}
        payload["RequestInfo"] = requestInfo
        payload["bankAccountDetails"] = {
            "tenantId": ulbTenantId
        }
        payload["pagination"] = {
            "limit": limit,
            "offSet": limit * pageNo,
            "sortBy": "createdTime",
            "order": "asc"
        }
        headers = {'Content-Type': 'application/json'}
        requestData = json.dumps(payload)
        try:
            response = requests.request("POST", bankaccount_search_url, headers=headers, data = requestData)
            logging.info("Response: {}".format(response.status_code))
            bankaccount_response = response.json()
            bank_accounts_details = bankaccount_response["bankAccounts"]
            if bank_accounts_details != None and len(bank_accounts_details) != 0:
                pageNo = pageNo + 1
                bank_accounts = bank_accounts + bank_accounts_details
            else:
                hasData = False
        except Exception as ex:
            logging.info("Exception while fetching bankaccounts for tenant id {} : {}".format(ulbTenantId, ex))
            raise ex

    return bank_accounts

def searchAuditLog(requestInfo, bankAccountId):
    auditLogs = []
    try:
        logging.info("search audit logs for {}".format(bankAccountId))
        audit_search_url = "{}{}?offset=0&limit=100&tenantId={}&objectId={}".format(audit_host, audit_search, ulbTenantId, bankAccountId)
        payload = {}
        payload["RequestInfo"] = requestInfo
        headers = {'Content-Type': 'application/json'}
        requestData = json.dumps(payload)
        try:
            response = requests.request("POST", audit_search_url, headers=headers, data = requestData)
            logging.info("Response: {}".format(response.status_code))
            auditlog_response = response.json()
            auditLogs = auditlog_response["AuditLogs"]
        except Exception as ex:
            logging.info("Exception while fetching auditlog {}".format(bankAccountId))
                
    except Exception as ex:
        logging.error("Exception while fetching auditlogs {}".format(ex))

    return auditLogs


def crateAuditLog(requestInfo, bankaccounts):
    try:
        logging.info("Started creating audit logs")
        for bankaccount in bankaccounts:
            logging.info("Fetching audit log details for bankaccount {}".format(bankaccount["id"]))
            audit_logs = searchAuditLog(requestInfo, bankaccount["id"])
            if audit_logs == None or len(audit_logs) == 0:
                logging.info("Creating Audit log for {} bankaccount".format(bankaccount["id"]))
                audit_log_data = getAuditLogCreateData(bankaccount, requestInfo["userInfo"])
                if audit_log_data != None and len(audit_log_data) != 0:
                    audit_create_url = "{}{}".format(audit_host, audit_create)
                    payload = {}
                    payload["RequestInfo"] = requestInfo
                    payload["AuditLogs"] = audit_log_data
                    headers = {'Content-Type': 'application/json'}
                    requestData = json.dumps(payload)
                    try:
                        response = requests.request("POST", audit_create_url, headers=headers, data = requestData)
                        logging.info("Response: {}".format(response.status_code))
                        logging.info("Audit log created for bankaccount {}".format(bankaccount["id"]))
                    except Exception as ex:
                        logging.info("Exception while creating auditlog {}".format(bankaccount["id"]))
                        raise(ex)
                else:
                    logging.info("Audit data not created for bankaccount {}".format(bankaccount["id"]))
            else:
                logging.info("Audit log data already available for {}".format(bankaccount["id"]))
    except Exception as ex:
        logging.error("Exception while updating bankaccount {}".format(ex))


def getEncryptedValue(accountHolderName, accountNumber):
    try:
        encAccountHolderName = None
        encAccountNumber = None
        payload = {}
        payload["encryptionRequests"] = [{
            "tenantId": rootTenantId,
            "type": "Normal",
            "value": {
                "accountHolderName": accountHolderName,
                "accountNumber": accountNumber
            }
        }]
        enc_encrypt_url = "{}{}".format(encryption_host, encryption_encrypt)
        headers = {'Content-Type': 'application/json'}
        requestData = json.dumps(payload)
        try:
            logging.info("RequestData {}".format(json.dumps(requestData)))
            response = requests.request("POST", enc_encrypt_url, headers=headers, data = requestData)
            logging.info("Response: {}".format(response.status_code))
            enc_response = response.json()
            logging.info("Encrypted response {}".format(enc_response))
            if len(enc_response):
                encData = enc_response[0]
                encAccountHolderName = encData["accountHolderName"]
                encAccountNumber = encData["accountNumber"]
            else:
                raise Exception("Did not get encrypted data for {}".format(accountHolderName, accountNumber))
        except Exception as ex:
            logging.info("Exception while getting encrypted data {} {}".format(accountHolderName, accountNumber))
            raise(ex)
        return [encAccountHolderName, encAccountNumber]
    except Exception as ex:
        logging.error("Exception while updating bankaccount {}".format(ex))
        raise(ex)


def getAuditLogCreateData(bankAccountDetail, userDetails):
    try:
        logging.info("Generating audit log data.")
        if (bankAccountDetail == None):
            return None
        else:
            BANK_ACCOUNT_ID = bankAccountDetail.get("id")
            USER_UUID = userDetails.get("uuid")
            REFERENCE_ID = bankAccountDetail.get("referenceId")
            SERVICE_CODE = bankAccountDetail.get("serviceCode")

            bankAccountDetails = bankAccountDetail["bankAccountDetails"][0]
            BANK_ACCOUNT_DETAIL_ID = bankAccountDetails.get("id")
            ACCOUNT_HOLDER_NAME = bankAccountDetails.get("accountHolderName")
            ACCOUNT_NUMBER = bankAccountDetails.get("accountNumber")

            branchDetails = bankAccountDetails.get("bankBranchIdentifier", {})
            BANK_BRANCH_IDENTIFIER_ID = branchDetails.get("id")
            IFSC_CODE = branchDetails.get("code")
            BRANCH_ADDITIONAL_DETAILS = branchDetails.get("additionalDetails")
            if BRANCH_ADDITIONAL_DETAILS != None:
                BRANCH_ADDITIONAL_DETAILS = json.dumps(BRANCH_ADDITIONAL_DETAILS)

            auditDetails = bankAccountDetail.get("auditDetails",{})
            CREATED_DATE = auditDetails.get("createdTime")
            LAST_MODIFIED_TIME = auditDetails.get("lastModifiedTime")
            CREATED_BY = auditDetails.get("createdBy")
            LAST_MODIFIED_BY = auditDetails.get("lastModifiedBy")

            [encAccountHolderName, encAccountNumber] = getEncryptedValue(accountHolderName=ACCOUNT_HOLDER_NAME, accountNumber=ACCOUNT_NUMBER)

            auditData = [
                {
                    "userUUID": USER_UUID,
                    "module": "BAS",
                    "tenantId": ulbTenantId,
                    "transactionCode": REFERENCE_ID,
                    "changeDate": CREATED_DATE,
                    "entityName": "eg_bank_account",
                    "objectId": BANK_ACCOUNT_ID,
                    "keyValueMap": {
                        "additionalFields": {
                            "type": "jsonb",
                            "value": "null"
                        },
                        "createdBy": CREATED_BY,
                        "createdTime": CREATED_DATE,
                        "id": BANK_ACCOUNT_ID,
                        "lastModifiedBy": LAST_MODIFIED_BY,
                        "lastModifiedTime": LAST_MODIFIED_TIME,
                        "referenceId": REFERENCE_ID,
                        "serviceCode": SERVICE_CODE,
                        "tenantId": ulbTenantId
                    },
                    "operationType": "CREATE"
                },
                {
                    "userUUID": USER_UUID,
                    "module": "BAS",
                    "tenantId": ulbTenantId,
                    "transactionCode": REFERENCE_ID,
                    "changeDate": CREATED_DATE,
                    "entityName": "eg_bank_account_detail",
                    "objectId": BANK_ACCOUNT_ID,
                    "keyValueMap": {
                        "accountHolderName": encAccountHolderName,
                        "accountNumber": encAccountNumber,
                        "accountType": "SAVINGS",
                        "additionalFields": {
                            "type": "jsonb",
                            "value": "null"
                        },
                        "createdBy": CREATED_BY,
                        "createdTime": CREATED_DATE,
                        "id": BANK_ACCOUNT_DETAIL_ID,
                        "isActive": True,
                        "isPrimary": True,
                        "lastModifiedBy": LAST_MODIFIED_BY,
                        "lastModifiedTime": LAST_MODIFIED_TIME,
                        "tenantId": ulbTenantId
                    },
                    "operationType": "CREATE"
                },
                {
                    "userUUID": USER_UUID,
                    "module": "BAS",
                    "tenantId": ulbTenantId,
                    "transactionCode": REFERENCE_ID,
                    "changeDate": CREATED_DATE,
                    "entityName": "eg_bank_branch_identifier",
                    "objectId": BANK_ACCOUNT_ID,
                    "keyValueMap": {
                        "additionalDetails": {
                            "type": "jsonb",
                            "value": BRANCH_ADDITIONAL_DETAILS
                        },
                        "code": IFSC_CODE,
                        "id": BANK_BRANCH_IDENTIFIER_ID,
                        "type": "IFSC"
                    },
                    "operationType": "CREATE"
                }
            ]
            logging.info("Audit data for create {} ".format(json.dumps(auditData)))
            return auditData
    except Exception as ex:
        logging.error("Exception occured on getAuditLogCreateData for {} ".format(bankAccountDetail["id"]), exc_info=True)
        raise(ex)        




if __name__ == "__main__":
    try:
        logging.info('Migration started')
        userInfo = getUser()
        if userInfo != None:
            requestInfo=getRequestInfo(userInfo)
            bankaccounts = getBankAccounts(requestInfo)
            logging.info(bankaccounts)
            if bankaccounts != None and len(bankaccounts):
                crateAuditLog(requestInfo, bankaccounts)
            else:
                logging.info("Bank account details not available.")
        else:
            logging.error("Could not fetch user")

    except Exception as ex:
        logging.error("Exception occured on main.", exc_info=True)
        raise(ex)