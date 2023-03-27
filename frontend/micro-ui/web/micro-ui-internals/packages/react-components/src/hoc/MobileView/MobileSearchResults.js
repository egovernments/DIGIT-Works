import React, { useMemo } from 'react'
import { useTranslation } from 'react-i18next';
import { Details } from "../../molecules/DetailsCard";
import { Link } from "react-router-dom";
import NoResultsFound from "../../atoms/NoResultsFound";
import { Loader } from "../../atoms/Loader";
import _ from "lodash";

const MobileSearchResults = ({ config, data, isLoading, isFetching,fullConfig }) => {
    const {apiDetails} = fullConfig
    const { t } = useTranslation();
    const resultsKey = config.resultsJsonPath
    //let searchResult = data?.[resultsKey]?.length>0 ? data?.[resultsKey] : []
    //searchResult = searchResult.reverse()
    //const tenantId =  Digit.ULBService.getCurrentTenantId();

    let searchResult = _.get(data,resultsKey,[])
    searchResult = searchResult?.length>0 ? searchResult : []
    searchResult = searchResult.reverse();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId);
    //reversing reason -> for some reason if we enable sorting on columns results from the api are reversed and shown, for now -> reversing the results(max size 50 so not a performance issue)
    
    // if (fullConfig?.postProcessResult){
    //     var { isPostProcessFetching,
    //         isPostProcessLoading,
    //         combinedResponse }  =  Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.postProcess(searchResult) 

    //     if(combinedResponse?.length > 0){
    //         searchResult = combinedResponse
    //     } 
    // }
    
    const propsMobileInboxCards = useMemo(() => {
      if (isLoading) {
        return [];
      }
      let cardData =  searchResult.map((row) => {
          let mapping = {};
          let cols = config?.columns;
          for(let columnIndex = 0; columnIndex<cols?.length; columnIndex++) {
              mapping[t(cols[columnIndex]?.label)] = _.get(row, cols[columnIndex]?.jsonPath, null)
            }
          return mapping;
      })
      return cardData;
    }, [data]);

  function RenderResult() {
    if (searchResult?.length === 0) {
       return ( <NoResultsFound/> );
    } 
    return <div>
      {propsMobileInboxCards.map((row) => {
        return <Link to={Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.MobileDetailsOnClick(row, t, tenantId)}>
        <div className="details-container">
          {Object.keys(row).map(key => {
            let toRender;
            let mobileCustomization = Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.MobileAdditionalCustomization(row, key, row[key], t, tenantId, searchResult, headerLocale);
              if(mobileCustomization){
                toRender = (<Details label={key} name={mobileCustomization} onClick={() =>{}} row={row} />)
              }
              else
              {
                toRender = row[key]? ( <Details label={key} name={row[key]} onClick={() =>{}} row={row} /> ) : ( <Details label={key} name={t("NA")} onClick={() =>{}} row={row} /> )
              }
              return toRender
            })}
        </div></Link>
      })}
     </div>
    }

    if (isLoading) 
    {   return <Loader /> }
    return (
        <React.Fragment>
          <RenderResult/>
        </React.Fragment>
    );
};

export default MobileSearchResults;
