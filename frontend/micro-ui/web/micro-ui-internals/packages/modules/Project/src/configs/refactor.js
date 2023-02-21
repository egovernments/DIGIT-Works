
import _ from "lodash";

const transform = (preProcesses, config, index, sectionIndex, t) => {
    Object.keys(preProcesses)?.map(preProcess=>{
        switch (preProcess) {
            case "translate" : {
                let keyToTranslate = config?.CreateProjectConfig?.form[index].body[sectionIndex]?.preProcess?.translate;
                let section = config?.CreateProjectConfig?.form[index].body[sectionIndex];
                _.set(section, keyToTranslate, t(_.get(section, keyToTranslate)));
            }
        }
    })
    return config;  
}

const preProcessMDMSConfig = (config, t) => {
    config?.CreateProjectConfig?.form?.map((sections, index)=>{
        sections?.body?.map((section, sectionIndex)=>{
        let preProcesses = section?.preProcess;
        if(preProcesses){
            config = transform(preProcesses, config, index, sectionIndex, t);
        }
       })
    })
    return config;
}

export default preProcessMDMSConfig;

