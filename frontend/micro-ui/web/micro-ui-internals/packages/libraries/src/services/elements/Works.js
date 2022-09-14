import { Request } from "../atoms/Utils/Request";
import Urls from "../atoms/urls";

export const WorksService = {
    createLOI: (details) =>
        Request({
            url: Urls.works.create,
            data: details,
            useCache: false,
            setTimeParam: false,
            userService: true,
            method: "POST",
            params: {},
            auth: true,
        }),
    estimateSearch: ({ tenantId, filters }) =>
        Request({
            url: Urls.works.estimateSearch,
            useCache: false,
            method: "POST",
            auth: true,
            userService: false,
            params: { tenantId, ...filters },
        }),
    loiSearch: ({ tenantId, filters }) =>
        Request({
            url: Urls.works.loiSearch,
            useCache: false,
            method: "POST",
            auth: true,
            userService: false,
            params: { tenantId, ...filters },
        }),
    createEstimate:(details)=>
        Request({
            url: Urls.works.createEstimate,
            data:details,
            useCache:false,
            setTimeParam:false,
            userService:true,
            method:"POST",
            // params:{},
            auth:true,

        }),
    approvedEstimateSearch:(details)=>
         Request({
            //update URL for Approved Estimate Search
            url: Urls.works.approvedEstimateSearch,
            data:details,
            useCache:false,
            setTimeParam:false,
            userService:true,
            method:"POST",
            // params:{},
            auth:true,
        })
}