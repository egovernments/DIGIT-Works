
import _ from "lodash";

const translate = (config, index, inputIndex, t) => {

    let input = config?.CreateProjectConfig?.form[index].body[inputIndex];
    //iterate all translate keys and handle translation
    for(let toTranslate = 0; toTranslate<config?.CreateProjectConfig?.form[index].body[inputIndex]?.preProcess?.translate?.length; toTranslate++) {
        let keyToTranslate = config?.CreateProjectConfig?.form[index].body[inputIndex]?.preProcess?.translate[toTranslate];
        _.set(input, keyToTranslate, t(_.get(input, keyToTranslate)));    
    }

    return config;
}

const updateDependent = (config, index, inputIndex, inputKey, dependencyConfig) => {
    let input = config?.CreateProjectConfig?.form[index].body[inputIndex];
    //iterate all update options keys and add options as params
    for(let toUpdate = 0; toUpdate<config?.CreateProjectConfig?.form[index].body[inputIndex]?.preProcess?.updateDependent?.length; toUpdate++) {
        let keyToUpdate = config?.CreateProjectConfig?.form[index].body[inputIndex]?.preProcess?.updateDependent[toUpdate];
        _.set(input, keyToUpdate, (dependencyConfig?.updateDependent?.filter(dependent=>dependent?.key === inputKey)?.[0]?.value?.[toUpdate]));    
    }

    return config;
}

const transform = (preProcesses, config, index, inputIndex, inputKey, t, dependencyConfig) => {
    //Do not loop preProcess object, to avoid unnecessary 'translate' and 'updateDependent' calls
    //To add any new transform object, simply add a new if statement
    if(preProcesses?.translate) {
        config = translate(config, index, inputIndex, t);
    }
    if(preProcesses?.updateDependent) {
        config = updateDependent(config, index, inputIndex, inputKey, dependencyConfig);
    }
    return config;  
}

const preProcessMDMSConfig = (t, config, dependencyConfig) => {
    config?.CreateProjectConfig?.form?.map((section, index)=>{
        section?.body?.map((input, inputIndex)=>{
        let preProcesses = input?.preProcess;
        if(preProcesses){
            config = transform(preProcesses, config, index, inputIndex, input?.key, t, dependencyConfig);
        }
       })
    })
    return config;
}

export default preProcessMDMSConfig;

