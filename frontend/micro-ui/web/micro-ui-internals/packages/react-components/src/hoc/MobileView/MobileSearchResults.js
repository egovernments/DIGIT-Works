import React, { useMemo } from 'react'
import { useTranslation } from 'react-i18next';
import { Details } from "../../molecules/DetailsCard";
import { Link } from "react-router-dom";
import NoResultsFound from "../../atoms/NoResultsFound";
import { Loader } from "../../atoms/Loader";
import _ from "lodash";
import Table from '../../atoms/Table';


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

    const columns = [
      {
        Header: "",
        accessor: "_searchResults",
        id : "_searchResults"
      }
    ]
    
    const propsMobileInboxCards = useMemo(() => {
      if (isLoading) {
        return [];
      }
      let cardData =  searchResult.map((details) => {
          let mapping = {};
          let additionalCustomization = {};
          let cols = config?.columns;
          for(let columnIndex = 0; columnIndex<cols?.length; columnIndex++) {
              mapping[cols[columnIndex]?.label] = _.get(details, cols[columnIndex]?.jsonPath, null)
              additionalCustomization[cols[columnIndex]?.label] = cols[columnIndex]?.additionalCustomization || false;
            }
          return {mapping, details, additionalCustomization};
      })
      console.log("carddata", cardData);
      return cardData;
    }, [data]);

    const rows = propsMobileInboxCards.map((row) => {
      return {
        _searchResults : <Link to={Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.MobileDetailsOnClick(row.mapping, tenantId)}>
        <div className="details-container">
          {Object.keys(row.mapping).map(key => {
            let toRender;
              if(row.additionalCustomization[key]){
                toRender = (
                <Details label={t(key)} 
                  name={Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.additionalCustomizations(row.details, key, {}, row.mapping[key], t, searchResult)} 
                  onClick={() =>{}} 
                  row={row.mapping} />)
              }
              else {
                toRender = row.mapping[key]? ( 
                <Details 
                  label={t(key)} 
                  name={row.mapping[key]} 
                  onClick={() =>{}} 
                  row={row.mapping} /> 
                ) : ( 
                <Details 
                  label={t(key)} 
                  name={t("NA")} 
                  onClick={() =>{}} 
                  row={row.mapping} /> )
              }
              return toRender
            })}
        </div></Link>
      }
    })

  function RenderResult() {
    if (searchResult?.length === 0) {
       return ( <NoResultsFound/> );
    } 
    return <div>
       <Table 
          className="table-fixed-first-column-wage-seekers wage-seekers-table" 
          t={t}
          manualPagination={false}
          data={rows}
          totalRecords={propsMobileInboxCards?.length}
          columns={columns}
          isPaginationRequired={true}
          getCellProps={(cellInfo) => {
            return {
              style: {width : "100vw"},
            };
          }}
        />
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
