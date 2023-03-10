import { getThumbnails } from "../../../utils/thumbnail";
import { WageSeekerService } from "../../elements/WageSeeker";

const dummyData = {
    "ResponseInfo": {
      "apiId": "string",
      "ver": "string",
      "ts": 0,
      "resMsgId": "string",
      "msgId": "string",
      "status": "SUCCESSFUL"
    },
    "Individuals": [
      {
        "id": "WS-23-000051",
        "tenantId": "citya",
        "clientReferenceId": "string",
        "userId": "string",
        "name": {
          "givenName": "Asha Devi",
          "familyName": "Asha Devi",
          "otherNames": "Asha Devi"
        },
        "dateOfBirth": "01/12/1993",
        "gender": "Female",
        "bloodGroup": "str",
        "mobileNumber": "string",
        "altContactNumber": "string",
        "email": "user@example.com",
        "address": [
          {
            "id": "string",
            "tenantId": "citya",
            "doorNo": "101/A",
            "latitude": 90,
            "longitude": 180,
            "locationAccuracy": 10000,
            "type": "string",
            "addressLine1": "string",
            "addressLine2": "string",
            "landmark": "string",
            "city": "Jatni",
            "pincode": "string",
            "buildingName": "string",
            "street": "Kanal Road",
            "locality": {
              "code": "string",
              "name": "MG Road",
              "label": "string",
              "latitude": "string",
              "longitude": "string",
              "children": [
                "string"
              ],
              "materializedPath": "string"
            }
          }
        ],
        "fatherName": "N Prasad",
        "husbandName": "N Prasad",
        "identifiers": [
          {
            "identifierType": "SYSTEM_GENERATED",
            "identifierId": "ABCD-1212"
          }
        ],
        "skills": [
          {
            "id": "1",
            "type": "Man Mulia",
            "level": "Unskilled",
            "experience": "string"
          },
          {
            "id": "2",
            "type": "Man Mulia",
            "level": "Semi-skilled",
            "experience": "string"
          }
        ],
        "photo": "859303bc-d889-4775-be36-1f2c23c88301",
        "additionalFields": {
          "schema": "HOUSEHOLD",
          "version": 2,
          "fields": [
            {
              "key": "height",
              "value": "180"
            }
          ]
        },
        "isDeleted": true,
        "rowVersion": 0,
        "auditDetails": {
          "createdBy": "string",
          "lastModifiedBy": "string",
          "createdTime": 0,
          "lastModifiedTime": 0
        }
      }
    ]
}
const transformViewDataToApplicationDetails = async (t, data, tenantId) => {
    if(data?.Individuals?.length === 0) return;

    const individual = data.Individuals[0]
    const thumbnails = await getThumbnails([individual?.photo], tenantId)
    const headerDetails = {
        title: " ",
        asSectionHeader: true,
        values: [
            { title: "MASTERS_WAGE_SEEKER_ID", value: individual?.id || t("NA")},
            { title: "ES_COMMON_AADHAR", value: individual?.aadharNumber || t("NA")},
            { title: "MASTERS_NAME_OF_WAGE_SEEKER", value: individual?.name?.familyName || t("NA")},
            { title: "MASTERS_FATHER_HUSBAND_NAME", value: individual?.fatherName || t("NA")},
            { title: "ES_COMMON_RELATIONSHIP", value: 'Father' || t("NA")},
            { title: "ES_COMMON_BIRTHDATE", value: individual?.dateOfBirth || t("NA")},
            { title: "CORE_COMMON_PROFILE_GENDER", value: individual?.gender || t("NA")},
            { title: "CORE_COMMON_PROFILE_MOBILE_NUMBER", value: individual?.mobileNumber || t("NA")},
            { title: "MASTERS_SOCIAL_CATEGORY", value: individual?.category || t("NA")}
        ],
        additionalDetails: {
          skills: {
            title: "MASTERS_SKILLS",
            skillData: individual?.skills || []
          },
          photo : {
            title: "ES_COMMON_PHOTOGRAPH",
            thumbnailsToShow: thumbnails          
          }
        }
    }
    const locationDetails = {
        title: "ES_COMMON_LOCATION_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "CORE_COMMON_PROFILE_CITY", value: individual?.address?.[0]?.city || t("NA")},
            { title: "COMMON_WARD", value: individual?.address?.[0]?.ward || t("NA")},
            { title: "COMMON_LOCALITY", value: individual?.address?.[0]?.locality?.name || t("NA")},
            { title: "ES_COMMON_STREET", value: individual?.address?.[0]?.street || t("NA")},
            { title: "ES_COMMON_DOOR_NO", value: individual?.address?.[0]?.doorNo || t("NA")},
        ]
    }
    const financialDetails = {
        title: "WORKS_FINANCIAL_DETAILS",
        asSectionHeader: true,
        values: [
            { title: "ES_COMMON_ACCOUNT_HOLDER_NAME", value: 'Asha Devi' || t("NA")},
            { title: "MASTERS_ACC_NO", value: '1000023401231' || t("NA")},
            { title: "MASTERS_IFSC", value: 'SBIN0000123' || t("NA")},
            { title: "ES_COMMON_BRANCH", value: 'Block 1, Kormangala, Bangalore' || t("NA")},
            { title: "MASTERS_EFFECTIVE_FROM", value: '01/04/2022' || t("NA")},
            { title: "MASTERS_EFFECTIVE_TO", value: 'NA' || t("NA")},
        ]
    }
    const applicationDetails = { applicationDetails: [headerDetails, locationDetails, financialDetails] };

  return {
    applicationDetails,
    applicationData: individual,
    processInstancesDetails: {},
    workflowDetails: {}
  }
}

export const View = {
    fetchWageSeekerDetails: async (t, tenantId, data, searchParams) => {
        return transformViewDataToApplicationDetails(t, dummyData, tenantId)
        /*
          try {
              const response = await WageSeekerService.search(tenantId, data, searchParams);
              console.log('response', response);
              return transformViewDataToApplicationDetails(t, response)
          } catch (error) {
              console.log('error', error);
              throw new Error(error?.response?.data?.Errors[0].message);
          }
        */
    }
}