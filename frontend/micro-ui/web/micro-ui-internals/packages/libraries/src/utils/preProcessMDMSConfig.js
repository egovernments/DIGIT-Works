
import _ from "lodash";

const translate = (config, index, sectionIndex, t) => {

    //iterate all translate keys and handle translation
    for(let toTranslate = 0; toTranslate<config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.translate.length; toTranslate++) {
        let keyToTranslate = config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.translate[toTranslate];
        let section = config?.CreateProjectConfig?.form[index].body[sectionIndex];
        _.set(section, keyToTranslate, t(_.get(section, keyToTranslate)));    
    }

    return config;
}

const transform = (preProcesses, config, index, sectionIndex, t) => {
    Object.keys(preProcesses)?.map(preProcess=>{
        switch (preProcess) {
            case "translate" : {
                config = translate(config, index, sectionIndex, t);
            }
        }
    })
    return config;  
}

const preProcessMDMSConfig = (config, t) => {
    config?.CreateProjectConfig?.form?.map((section, index)=>{
        section?.body?.map((input, inputIndex)=>{
        let preProcesses = input?.preProcess;
        if(preProcesses){
            config = transform(preProcesses, config, index, inputIndex, t);
        }
       })
    })
    return config;
}

export default preProcessMDMSConfig;

