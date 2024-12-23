---
description: UI configuration for app
---

# UI Configuration

## **Overview**

This section provides details about the MUKTASoft UI configuration required to enable it in any environment.

**Steps:**

1. [DevOps configuration](./#devops-configuration)
2. [Global configuration](./#global-configuration)
3. [MDMS](./#mdms-configuration)
4. [Roles](./#roles-configuration)
5. [Localisation](./#localization-configuration)

## **DevOps Configuration**

In the DevOps repository of your organization, locate the following -  `"deploy-as-code/helm/environments/works-dev.yaml"`.&#x20;

Add the code block below to the environment YAML file used to deploy the Works platform.

```yaml
works-ui:
  custom-js-injection: |
    sub_filter.conf: "
      sub_filter  '<head>' '<head>
      <script src={{INSERT_YOUR_AWS_BUCKET_NAME}}/globalConfigsWorks.js type=text/javascript></script>';"
```

A dev environment sample file is available [here](https://github.com/egovernments/DIGIT-DevOps/blob/8f80d072be92a8a3cbcac438ca3abdd5e999d17b/deploy-as-code/helm/environments/works-dev.yaml#L587).  Make sure to modify this for your deployment.

## **Global Configuration**&#x20;

This section contains the configurations applicable globally to all UI modules. These need to be configured before service-specific UI configurations.

#### &#x20;Create a globalconfig.js file - Steps:

1. Generate a configuration file named "globalconfigs.js" using the settings provided (refer to the code below).
2. Set up all the images and logos needed in your S3 storage, and include the links as "footerBWLogoURL" and "footerLogoURL."
3. Specify the state tenant ID as "stateTenantId."
4. If any user roles should be marked as invalid, list them under "invalidEmployeeRoles."
5. Upload this global configuration file to your S3 bucket, naming it "globalconfigs.js."
6. Make sure to include the URL of the "globalConfigs" file in your [`Environment config`](./#devops-configuration) .

```javascript
var globalConfigs = (function () {
  var stateTenantId = 'pg' // statetenantId
   var gmaps_api_key = '<<INSERT_GMAP_GENERATED_TOKEN>>';
   var contextPath = 'works-ui'; 
   var configModuleName = 'commonMuktaUiConfig'; 
   var centralInstanceEnabled = false;
   var footerBWLogoURL = '{{INSERT_YOUR_AWS_BUCKET_NAME}}/digit-footer-bw.png';
   var footerLogoURL = '{{INSERT_YOUR_AWS_BUCKET_NAME}}/digit-footer.png';
   var digitHomeURL = 'https://www.digit.org/';
   var xstateWebchatServices = 'wss://dev.digit.org/xstate-webchat/';
   var assetS3Bucket = '{{INSERT_YOUR_AWS_BUCKET_NAME}}';
   var invalidEmployeeRoles = ["CBO_ADMIN","STADMIN","ORG_ADMIN","ORG_STAFF","SYSTEM"] 

 
   var getConfig = function (key) {
     if (key === 'STATE_LEVEL_TENANT_ID') {
       return stateTenantId;
     }
     else if (key === 'GMAPS_API_KEY') {
       return gmaps_api_key;
     }
     else if (key === 'ENABLE_SINGLEINSTANCE') {
       return centralInstanceEnabled;
     } else if (key === 'DIGIT_FOOTER_BW') {
       return footerBWLogoURL;
     } else if (key === 'DIGIT_FOOTER') {
       return footerLogoURL;
     } else if (key === 'DIGIT_HOME_URL') {
       return digitHomeURL;
     } else if (key === 'xstate-webchat-services') {
       return xstateWebchatServices;
     } else if (key === 'S3BUCKET') {
       return assetS3Bucket;
     } else if (key === 'CONTEXT_PATH'){
	return contextPath;
     } else if (key === 'UICONFIG_MODULENAME'){
	return configModuleName;
     } else if (key === 'INVALIDROLES'){
	return invalidEmployeeRoles;
     }
   };
 
 
   return {
     getConfig
   };
 }());
```

## **MDMS Configuration**

The UI Screen configurations required for MUKTA are present in the folder [commonMuktaUiConfig](https://github.com/egovernments/works-mdms-data/tree/DEV/data/pg/commonMuktaUiConfig).\
Click [here](common-configurations/mdms-configuration.md) for the remaining MDMS configurations.

## **Roles Configuration**

Refer to the table in [this document ](common-configurations/role-configuration.md)for role configurations specific to each screen. Keep in mind that certain configurations may already exist in the MDMS for backend service modules. If they are already present, there's no need to duplicate them.

## **Localization Configuration**

Refer to the [sheet here](https://docs.google.com/spreadsheets/d/1Pk5TD_GbnWB6z6cJ1IhsaVCxY9PKcBTg2IpIZ1dXgX4/edit#gid=934078231) which contains all module-specific localized strings for translations. To convert the UI into other languages, follow the information in this sheet and provide the correct translations in your desired language.

## **Reference Links**

Figma screens for the UI are available [here](https://www.figma.com/file/M2P3O9WlKtxuLCjQKxLLDg/DIGIT-Works?node-id=1-2). Refer to them to understand the MUKTA UI.&#x20;



