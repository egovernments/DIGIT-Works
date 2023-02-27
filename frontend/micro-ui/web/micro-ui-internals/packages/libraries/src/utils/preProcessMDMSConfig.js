
import _ from "lodash";

const translate = (config, index, sectionIndex, t) => {

    //iterate all translate keys and handle translation
    for(let toTranslate = 0; toTranslate<config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.translate?.length; toTranslate++) {
        let keyToTranslate = config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.translate[toTranslate];
        let section = config?.CreateProjectConfig?.form[index].body[sectionIndex];
        _.set(section, keyToTranslate, t(_.get(section, keyToTranslate)));    
    }

    return config;
}

const updateOptions = (config, index, sectionIndex, sectionKey, additionalOptions) => {
    // console.log("UPDATING TO",additionalOptions?.updateOptions?.filter(options=>options?.key === sectionKey)?.[0]?.value);
    //iterate all update options keys and add options as params
    for(let toUpdate = 0; toUpdate<config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.updateOptions?.length; toUpdate++) {
        let keyToUpdate = config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.updateOptions[toUpdate];
        let section = config?.CreateProjectConfig?.form[index].body[sectionIndex];
        _.set(section, keyToUpdate, (additionalOptions?.updateOptions?.filter(options=>options?.key === sectionKey)?.[0]?.value));    
    }

    return config;
}

const transform = (preProcesses, config, index, sectionIndex, sectionKey, t, additionalOptions) => {
    Object.keys(preProcesses)?.map(preProcess=>{
        switch (preProcess) {
            case "translate" : {
                config = translate(config, index, sectionIndex, t);
            }
            case "updateDropdownOptions" : {
                config = updateOptions(config, index, sectionIndex, sectionKey, additionalOptions);
            }
        }
    })
    return config;  
}

const preProcessMDMSConfig = (t, sessionFormData, config, additionalOptions) => {
    console.log(sessionFormData);
    config?.CreateProjectConfig?.form?.map((section, index)=>{
        section?.body?.map((input, inputIndex)=>{
        let preProcesses = input?.preProcess;
        if(preProcesses){
            config = transform(preProcesses, config, index, inputIndex, input?.key, t, additionalOptions);
        }
       })
    })
    return config;
}

export default preProcessMDMSConfig;

