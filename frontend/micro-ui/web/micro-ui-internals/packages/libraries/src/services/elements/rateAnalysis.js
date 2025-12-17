import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const RateAnalysisService = {
  
    search: (tenantId, data) =>
      Request({
        url: Urls.rateAnalysis.search,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data,
        authHeader:true,
       
      })
};