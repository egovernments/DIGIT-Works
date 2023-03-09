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
              mapping[t(cols[columnIndex]?.label)] = t(row?.[cols[columnIndex]?.jsonPath]) || t("ES_COMMON_NA")
            }
          return mapping;
      })
      //console.log("cardData :", cardData);
      return cardData;
    }, [data]);

  function RenderResult() {
    if (searchResult?.length === 0) {
       return ( <NoResultsFound/> );
    } 
    return <div>
      {propsMobileInboxCards.map((row) => {
        return <Link to={Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.MobileDetailsOnClick(row, t)}>
        <div className="details-container">
          {Object.keys(row).map(key => {
            let toRender;
              if(Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.additionalCustomizationForMobile(row[key], key, t)){
                toRender = (
                  row[key] === t("ES_COMMON_NA")? 
                  (
                    <div className="detail">
                      <span className="label">
                        <h2>{key}</h2>
                      </span>
                      <span className="name">{String(t("ES_COMMON_NA"))}</span>
                    </div>

                  ) : (
                    <div className="detail">
                     <span className="label">
                       <h2>{key}</h2>
                     </span>
                     <span className="name">
                       <span className="link">
                        <Link to={Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.getLink(row,key)}>{String(row[key]? row[key] : t("ES_COMMON_NA"))}</Link>
                       </span>
                     </span>
                     </div>
                  )
                )
              }
              else
              {
                toRender = (
                <Details label={key} name={row[key]} onClick={() =>{}} row={row} />
                )
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
