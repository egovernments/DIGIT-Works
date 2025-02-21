import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const OrganisationService = {
    search: ( data ) =>
       { 
        if(!data) return []
        return Request({
            url: Urls?.organisation?.search,
            data: data,
            useCache: false,
            setTimeParam: false,
            userService: true,
            method: "POST",
            params: {},
            auth: true,
        })
      },

    create: (data) => 
        Request({
          url: Urls.organisation.create,
          useCache: false,
          method: "POST",
          auth: true,
          userService: true,
          data: data
        }),

    update: (data) => 
        Request({
          url: Urls.organisation.update,
          useCache: false,
          method: "POST",
          auth: true,
          userService: true,
          data: data
        })
};
