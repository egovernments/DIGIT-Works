import { useQuery, useQueryClient } from "react-query";
import { CustomService } from "../services/elements/CustomService";

/**
 * Custom hook which can make api call and format response
 *
 * @author jagankumar-egov
 *
 *
 * @example
 * 
 const requestCriteria = [
      "/user/_search",             // API details
    {},                            //requestParam
    {data : {uuid:[Useruuid]}},    // requestBody
    {} ,                           // privacy value 
    {                              // other configs
      enabled: privacyState,
      cacheTime: 100,
      select: (data) => {
                                    // format data
        return  _.get(data, loadData?.jsonPath, value);
      },
    },
  ];
  const { isLoading, data, revalidate } = Digit.Hooks.useCustomAPIHook(...requestCriteria);

 *
 * @returns {Object} Returns the object which contains data and isLoading flag
 */


const useMultiCustomAPIHook = ({ url, params, body, config = {}, plainAccessRequest,changeQueryName="Random",apiDetails }) => {
  const client = useQueryClient();

  const { isLoading, data, isFetching } = useQuery(
    [url,changeQueryName].filter((e) => e),
    () => CustomService.getResponse({ url, params, body, plainAccessRequest }),
    {
      cacheTime:0,
      select:(res) => {
        return res   
      },
      ...config,
    }
  );

    const nextRequestCriteria = Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.postProcess?.getApiConfig(apiDetails,data)  
    // let nextRequestCriteria = {
    //   url:'/user/_search',
    //   params:{
      
    //   },
    //   body:{
    //     tenantId:'pg.citya',
    //     pageSize:'100',
    //     uuid:data?.items?.map(item => {
    //       return item.businessObject.auditDetails.createdBy
    //     })
    //   },
    //   config: {
    //       enabled: !!data,
    //   }
    // };

  const { isLoading:isLoadingNext, data:dataNext, isFetching:isFetchingNext } = useQuery(
    [nextRequestCriteria.url,changeQueryName].filter((e) => e),
    () => CustomService.getResponse({ url:nextRequestCriteria.url, params:nextRequestCriteria.params, body:nextRequestCriteria.body, plainAccessRequest:nextRequestCriteria.plainAccessRequest }),
    {
      cacheTime:0,
      select:(dataNext)=> {
        return Digit?.Customizations?.[apiDetails?.masterName]?.[apiDetails?.moduleName]?.postProcess?.combineResponse(data,dataNext)
      },
      ...nextRequestCriteria.config
    }
  );

  const returnObj = {
    isLoading: isLoading || isLoadingNext,
    isFetching : isFetching && isFetchingNext,
    data: dataNext,
    revalidate: () => {
      data && client.invalidateQueries({ queryKey: [url].filter((e) => e) });
    },
  }
  return returnObj   
};

export default useMultiCustomAPIHook;