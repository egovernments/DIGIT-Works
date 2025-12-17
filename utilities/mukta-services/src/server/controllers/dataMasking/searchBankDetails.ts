import * as express from "express";
import { search_bank_account } from "../../api/index"; // Import bank account service API function
import {
  search_mdms
} from "../../api/index";
import { errorResponder, sendResponse } from "../../utils/index";

// Define the BankAccountController class
class BankAccountController {
  public path = "/bankaccount/v1";
  public router = express.Router();

  constructor() {
    this.initializeRoutes();
  }

  public initializeRoutes() {
    this.router.post(`${this.path}/_search`, this.getBankAccountDetails);
  }

  // Function to mask attributes based on pattern
  private applyMask(value: string, pattern: string): string {
    return value ? value.replace(new RegExp(pattern, "g"), "*") : "";
  }

  // Check if the role has access to view the attribute in plain text
  private hasRoleAccess(roles: string[], attribute: string, policy: any): boolean {
    const roleBasedPolicy = policy.roleBasedDecryptionPolicy.find((policy: any) =>
      policy.roles.some((role: string) => roles.includes(role))
    );
    
    if (!roleBasedPolicy) return false;
    
    const attributeAccess = roleBasedPolicy.attributeAccessList.find((access: any) => access.attribute === attribute);
    
    return attributeAccess ? attributeAccess.firstLevelVisibility === "PLAIN" && attributeAccess.secondLevelVisibility === "PLAIN" : false;
  }

  // Function to get the masking pattern for an attribute
  private getPatternById(patternId: string, patterns: any[]): string {
    const pattern = patterns.find((p) => p.patternId === patternId);
    return pattern ? pattern.pattern : "";
  }

  private async fetchMDMSConfig(tenantid:string, RequestInfo: any) {
    const tenantId = tenantid;
    const promises = [
      search_mdms(
        tenantId,
        "DataSecurity",
        "MaskingPatterns",
        RequestInfo
      
      ),
      search_mdms(
        tenantId,
        "commonMuktaUiConfig",
        "MaskingUIConfig",
        RequestInfo
      )
    ];

    const [maskingPatterns, MaskingUIConfig] = await Promise.all(promises);
    return {
      securityPolicy: MaskingUIConfig,
      maskingPatterns: maskingPatterns,
    };
  }

  // Main handler for retrieving and masking bank account details
  public getBankAccountDetails = async (
    request: express.Request,
    response: express.Response
  ) => {
    try {
      const { RequestInfo, bankAccountDetails } = request.body;
      const roles = RequestInfo.userInfo.roles.map((role: any) => role.code);
      // Call the bank account service API
      const bankAccountResponse = await search_bank_account(bankAccountDetails, RequestInfo);

      // Example Masking patterns from config
      // const patterns = [
      //   { patternId: "001", pattern: ".(?=.{4})" },
      //   { patternId: "009", pattern: "(?<=.{4}).(?=.{2})" },
      // ];

      // Example Security policy configuration
      // const securityPolicy = {
      //   model: "BankAccountSearch",
      //   attributes: [
      //     { name: "accountHolderName", jsonPath: "accountHolderName", patternId: "001", defaultVisibility: "MASKED" },
      //     { name: "accountNumber", jsonPath: "accountNumber", patternId: "001", defaultVisibility: "MASKED" },
      //     { name: "ifscCode", jsonPath: "bankBranchIdentifier/code", patternId: "009", defaultVisibility: "MASKED" }
      //   ],
      //   roleBasedDecryptionPolicy: [
      //     {
      //       roles: ["BILL_ACCOUNTANT", "MUKTA_ADMIN"],
      //       attributeAccessList: [
      //         { attribute: "accountHolderName", firstLevelVisibility: "PLAIN", secondLevelVisibility: "PLAIN" },
      //         { attribute: "accountNumber", firstLevelVisibility: "PLAIN", secondLevelVisibility: "PLAIN" },
      //         { attribute: "ifscCode", firstLevelVisibility: "PLAIN", secondLevelVisibility: "PLAIN" }
      //       ]
      //     }
      //   ]
      // };

      let { securityPolicy, maskingPatterns : patterns } = await this.fetchMDMSConfig(bankAccountDetails?.tenantId, RequestInfo);
      securityPolicy = securityPolicy.filter((ob: any) => ob?.model === "BankAccountSearch")?.[0];
      // Mask bank account details based on role and config
      const maskedBankAccountDetails = bankAccountResponse?.bankAccounts.map((account: any) => {
        let maskedAccount = { ...account };

        // Iterate through the attributes in the security policy
        securityPolicy.attributes.forEach((attributeConfig: any) => {
          const { name, jsonPath, patternId } = attributeConfig;
          const pattern = this.getPatternById(patternId, patterns);

          // Extract the value using jsonPath and check if it needs to be masked
          const attributeValue = this.getJsonPathValue(account?.bankAccountDetails?.[0], jsonPath);

          if (this.hasRoleAccess(roles, name, securityPolicy)) {
            // If the role has access, leave the value as plain text
            this.setJsonPathValue(maskedAccount?.bankAccountDetails?.[0], jsonPath, attributeValue);
          } else {
            // Mask the value using the configured pattern
            const maskedValue = this.applyMask(attributeValue, pattern);
            this.setJsonPathValue(maskedAccount?.bankAccountDetails?.[0], jsonPath, maskedValue);
          }
        });

        return maskedAccount;
      });

      // Format the final response structure
      const responseObj = {
        ResponseInfo: bankAccountResponse?.ResponseInfo,
        bankAccounts: maskedBankAccountDetails,  // The bank accounts with masked/unmasked values
        pagination: bankAccountResponse?.pagination
      };

      return sendResponse(response, responseObj, request);
    } catch (error) {
      console.error("Error fetching bank account details:", error);
      return errorResponder(
        { error: "Internal Server Error" },
        request,
        response
      );
    }
  };

  // Helper function to get the value from a nested JSON object using a JSON path
  private getJsonPathValue(obj: any, path: string): string {
    return path.split("/").reduce((acc, part) => acc && acc[part], obj);
  }

  // Helper function to set a value in a nested JSON object using a JSON path
  private setJsonPathValue(obj: any, path: string, value: any): void {
    const parts = path.split("/");
    const lastPart = parts.pop();
    const lastObj = parts.reduce((acc, part) => acc[part] = acc[part] || {}, obj);
    lastObj[lastPart!] = value;
  }
}

// Export the BankAccountController class
export default BankAccountController;
