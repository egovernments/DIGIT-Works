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
    loiSearch: ({tenantId,filters}) => 
        Request({
            url: Urls.works.loiSearch,
            useCache: false,
            method: "POST",
            auth: true,
            userService: false,
            params: { tenantId, ...filters },
        })
}