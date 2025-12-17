import * as express from "express";
import { search_mdms, search_organisation } from "../../api/index";
import { errorResponder, sendResponse } from "../../utils/index";

// Define the BankAccountController class
class OrganisationController {
  public path = "/org-services/organisation/v1";
  public router = express.Router();

  constructor() {
    this.initializeRoutes();
  }

  public initializeRoutes() {
    this.router.post(`${this.path}/_search`, this.getOrganisation);
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
  public getOrganisation = async (
    request: express.Request,
    response: express.Response
  ) => {
    try {
      const { RequestInfo, SearchCriteria } = request.body;
      const tenantId = SearchCriteria.tenantId
      const roles = RequestInfo.userInfo.roles.map((role: any) => role.code);
      // Call the bank account service API
      const organisationResponse = await search_organisation(request.body);

      let { securityPolicy,maskingPatterns : patterns } = await this.fetchMDMSConfig(tenantId, RequestInfo);
      securityPolicy = securityPolicy.filter((ob: any) => ob?.model === "OrganisationSearch")?.[0];

      // Mask bank account details based on role and config
      const maskedOrganisation = organisationResponse?.organisations.map((details: any) => {
        let maskedDetails = { ...details };

        // Iterate through the attributes in the security policy
        securityPolicy.attributes.forEach((attributeConfig: any) => {
          const { name, jsonPath, patternId } = attributeConfig;
          const pattern = this.getPatternById(patternId, patterns);


          if (this.hasRoleAccess(roles, name, securityPolicy)) {
            this.changeValue(maskedDetails, jsonPath, false, pattern);
          } else {
            this.changeValue(maskedDetails, jsonPath, true, pattern);
          }
        });

        return maskedDetails;
      });

      // Format the final response structure
      const responseObj = {
        ResponseInfo: organisationResponse?.ResponseInfo,
        organisations: maskedOrganisation,  // Organisations with masked/unmasked values
        TotatCount: organisationResponse?.TotatCount
      };

      return sendResponse(response, responseObj, request);
    } catch (error) {
      console.error("Error fetching Organisation: ", error);
      return errorResponder(
        { error: "Internal Server Error" },
        request,
        response
      );
    }
  };

  // Function to handle changing value based on the role access and mask requirement
  private changeValue(obj: any, path: string, mask: boolean, pattern: string): void {
    const pathParts = path.split("/");
    let currentObj = obj;

    // Iterate through the path parts to find the correct position to update the value
    for (let i = 0; i < pathParts.length; i++) {
      const part = pathParts[i];

      if (part === "*") {
        if (Array.isArray(currentObj)) {
          // Iterate through array if '*' is found
          currentObj.forEach(item => {
            if (item && typeof item === 'object') {
              // Recursively call changeValue for each item in the array
              this.changeValue(item, pathParts.slice(i + 1).join("/"), mask, pattern);
            }
          });
          return;
        }
      } else if (part.includes("=")) {
        if (Array.isArray(currentObj)) {
          const key = part.split("=")[0];
          const value = part.split("=")[1];
          currentObj.forEach(item => {
            if (item && typeof item === 'object' && item[key] === value) {
              // Recursively call changeValue for each item in the array
              this.changeValue(item, pathParts.slice(i + 1).join("/"), mask, pattern);
            }
          });
          return;
        }
      } else {
        if (i === pathParts.length - 1) {
          // At the last part, update the value based on mask
          const currentValue = currentObj[part];
          if (currentValue) {
              if ( part === "locality") {
                currentObj.isLocalityMasked = mask;
              } else if (part === "boundaryCode") {
                if (currentObj.geoLocation.additionalDetails) {
                  currentObj.geoLocation.additionalDetails.isWardMasked = mask;
                } else {
                  currentObj.geoLocation.additionalDetails = {
                    isWardMasked: mask
                  }
                }
              } else {
                // Mask the value using the configured pattern if mask is true
                if (mask) {
                  if (pattern === ".") {
                    currentObj[part] = "CS_COMMON_UNDISCLOSED";
                  } else {
                    const maskedValue = this.applyMask(currentValue, pattern);
                    currentObj[part] = maskedValue;
                  }
                } else {
                  currentObj[part] = currentValue;
                }
              }
          }
        } else {
          // Navigate deeper into the object
          if (!currentObj[part]) {
            currentObj[part] = {}; // Create new object if it doesn't exist
          }
          currentObj = currentObj[part];
        }
      }
    }
  }
}

// Export the OrganisationController class
export default OrganisationController;