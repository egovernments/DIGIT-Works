
import _ from "lodash";

/*
PRE-PROCESS MDMS CONFIG
-----------------------
@Author - Hariom Sinha

Usually FormComposer configs needs some dependendent params to drive the rendering and functionality. In MDMS config, we cannot inject those params.
So, this component is developed to help convert any MDMS config to a FormComposer enabled config by injecting multiple params.
As of now, two params are introduced - 
    a. translate
    b. updateDependent
    c. convertStringToRegEx
    Based on the newer requirement, this utility can be enhanced to support extra types of params.

How to use this Pre-Process Utility - 
1. Fetch any config from MDMS.
2. In each input object of the config, set preProcess object with required Params.
   ex - 
   {
        isMandatory: false,
        key: "noSubProject_locality",
        type: "radioordropdown",
        label: "WORKS_LOCALITY",
        disable: false,
        preProcess : {
            translate : ["populators.error"],
            updateDependent : ["populators.options"]
        },
        populators: {
        name: "noSubProject_locality",
        optionsKey: "i18nKey",
        error: "WORKS_REQUIRED_ERR",
        required: false,
        optionsCustomStyle : {
            top : "2.5rem"
        },
        options: []
        },
    },
3. Both 'translate' and 'updateDependent' supports multiple JSON paths. This means that we can inject multiple params at a time in the same input object.
4. Any component who wants to transform MDMS config to FormComposer config using this Pre-Process Utility should pass the dependencies based on their requirement.
   Example, if one needs to update the dropdown value based on other dropdown field change, then the component can pass that Param as state.
   The Component should make calls to this config only when the dependent state changes, to avoid performance issues.

    const config =  useMemo(
      () => Digit.Utils.preProcessMDMSConfig(t, createProjectConfig, {
        updateDependent : [
          {
            key : 'withSubProject_project_subScheme',
            value : [withSubProjectSubSchemeOptions]
          }
        ]
      }),
      [withSubProjectSubSchemeOptions]);
5. Translation and convertStringToRegEx will be handled by the Pre-Preprocess on its own. No params are required for this.
*/

const convertStringToRegEx = (target) => {
    //iterate all translate keys and handle translation
    for(let toValidate = 0; toValidate<target?.preProcess?.convertStringToRegEx?.length; toValidate++) {
        let keyToValidate = target?.preProcess?.convertStringToRegEx[toValidate];
        let regex = _.get(target, keyToValidate);
        if(typeof(regex) === "string") {
            regex =  new RegExp(regex);
        }
        _.set(target, keyToValidate, regex);    
    }
    return target;
}

const transform = (preProcesses, target, inputIndex, inputKey, t, dependencyConfig) => {
    //Do not loop preProcess object, to avoid unnecessary 'translate' and 'updateDependent' calls
    //To add any new transform object, simply add a new if statement
    if(preProcesses?.convertStringToRegEx) {
        target = convertStringToRegEx(target);
    }
    return target;  
}

const preProcessMDMSConfigInboxSearch = (t, config, jsonpath, dependencyConfig) => {
    let targetConfig = _.get(config, jsonpath);
    targetConfig?.map((target, inputIndex) => {
        let preProcesses = target?.preProcess;
        targetConfig = transform(preProcesses, target, inputIndex, target?.key, t, dependencyConfig);
    })
    _.set(config, jsonpath, targetConfig);
    debugger;
    return config;
}

export default preProcessMDMSConfigInboxSearch;

