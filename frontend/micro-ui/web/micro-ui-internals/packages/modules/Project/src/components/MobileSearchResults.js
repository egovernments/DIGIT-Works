import React, { useEffect, useState, useMemo, useContext } from 'react'
import { useTranslation } from 'react-i18next';
import { DetailsCard, Card, NoResultsFound, Loader, Link } from "@egovernments/digit-ui-react-components";
import _ from "lodash";
import { MobileInboxContext } from "./MobileInboxContext";

export const MobileSearchResults = ({ tableContainerClass, config,data,isLoading,isFetching,fullConfig,revalidate }) => {
    //const {apiDetails} = fullConfig
    const { t } = useTranslation();
    const resultsKey = config.resultsJsonPath
    let searchResult = data?.[resultsKey]?.length>0 ? data?.[resultsKey] : []
    searchResult = searchResult.reverse()

    //reversing reason -> for some reason if we enable sorting on columns results from the api are reversed and shown, for now -> reversing the results(max size 50 so not a performance issue)
    
    // if (fullConfig?.postProcessResult){
    //     var { isPostProcessFetching,
    //         isPostProcessLoading,
    //         combinedResponse }  =  Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.postProcess(searchResult) 

    //     if(combinedResponse?.length > 0){
    //         searchResult = combinedResponse
    //     } 
    // }
  
    //const {state,dispatch} = useContext(MobileInboxContext);


    const propsMobileInboxCards = useMemo(() => {
        if (isLoading) {
          return [];
        }
        //console.log(searchResult, config.columns);
        return searchResult.map((row) => {
            let test = config?.columns?.map((column) => ({
                // if (column?.additionalCustomization){
                //     return Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.additionalCustomizations(row.original, column, col, row?.[column?.jsonPath], t) 
                // }

                [t(column?.label)]: t(row?.[column?.jsonPath]) || t("ES_COMMON_NA"),
            }))
            //console.log("TEST : ", test);
            return test;
        })
        
      }, [data]);

    if (isLoading || isFetching ) 
       return <Loader />
    if (data?.length === 0) 
       return <NoResultsFound/>
    return (
        <React.Fragment>
            if (!data || data?.length === 0){
               <div>
                 <Card style={{ marginTop: 20 }}>
                    {t("CS_MYAPPLICATIONS_NO_APPLICATION")
                          .split("\\n")
                          .map((text, index) => (
                          <p key={index} style={{ textAlign: "center" }}>
                             {text}
                          </p>
                          ))
                    }
                 </Card>
               </div>
             } 
          <div>
               <DetailsCard
               {...{
                  data: propsMobileInboxCards,
                  linkPrefix: `/${window.contextPath}/employee/works/view-estimate`,
                  //serviceRequestIdKey: t("WORKS_ESTIMATE_NO"),
                }}
              />
          </div>
        </React.Fragment>
    )
};
