import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const WorksService = {
    createLOI: (details) =>
        Request({
            url:  Urls.works.create,
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
    approvedEstimateSearch:({ tenantId, filters })=>
         Request({
            //update URL for Approved Estimate Search
            url: Urls.works.approvedEstimateSearch,
            useCache: false,
            method: "POST",
            auth: true,
            userService: false,
            params: { tenantId, ...filters }
        })
}