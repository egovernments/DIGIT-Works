import React, { useMemo } from 'react'
import { useTranslation } from 'react-i18next';
import { DetailsCard, NoResultsFound, Loader } from "@egovernments/digit-ui-react-components";
import _ from "lodash";

export const MobileSearchResults = ({ config, data, isLoading, isFetching,fullConfig }) => {
    const {apiDetails} = fullConfig
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
      let cardData =  searchResult.map((row) => {
          let mapping = {};
          let cols = config?.columns;
          for(let columnIndex = 0; columnIndex<cols?.length; columnIndex++) {
            if (cols[columnIndex].additionalCustomization){
              let col=null,value =row?.[cols[columnIndex]?.jsonPath];
              mapping[t(cols[columnIndex]?.label)] = Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.additionalCustomizations(row,cols[columnIndex],col,value,t) 
            }
            else {mapping[t(cols[columnIndex]?.label)] = t(row?.[cols[columnIndex]?.jsonPath]) || t("ES_COMMON_NA")}
          }
          return mapping;
      })
      return cardData;
    }, [data]);

   function RenderResult() {
    if (searchResult?.length === 0) {
       return ( <NoResultsFound/> );
   } 
    return (
    <DetailsCard
    {...{
      data: propsMobileInboxCards,
      showActionBar : false,
   }}
   />);
  }
  
    if (isLoading) 
    {   return <Loader /> }
    return (
        <React.Fragment>
          <RenderResult/>
        </React.Fragment>
    );
};
