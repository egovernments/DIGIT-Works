import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const ContractService = {
    search: (tenantId, data, searchParams) =>
      Request({
        url: Urls.contracts.search,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data,
        authHeader:true,
        params: {tenantId, ...searchParams},
      })
};