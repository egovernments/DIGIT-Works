import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const WORKSService={
    WORKSEstimateSearch:({ tenantId, filters })=>
    {
        console.log("search Data3",filters,tenantId,Urls)
        return(
        Request({
            url: Urls.works.estimate_search,
            useCache: false,
            method: "POST",
            auth: true,
            userService: false,
            params: { tenantId, ...filters }
        }))
    }
}

