import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const WageSeekerService = {
    search: (tenantId, data, searchParams) =>
      {
        if(!data) return []
        return Request({
        url: Urls.wageseeker.search,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data,
        params: { tenantId, ...searchParams },
      })
    },

    update: (data) => 
      Request({
        url: Urls.wageseeker.update,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data
      }),
    
    create: (data) => 
      Request({
        url: Urls.wageseeker.create,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data
      }),

    delete: (data) => 
      Request({
        url: Urls.wageseeker.delete,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        data: data
      })
};
