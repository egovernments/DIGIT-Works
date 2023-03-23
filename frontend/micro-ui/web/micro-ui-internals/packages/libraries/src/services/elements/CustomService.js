import { Request } from "../atoms/Utils/Request";

export const CustomService = {
  getResponse: ({ url, params, body, plainAccessRequest,useCache=true,userService=true,setTimeParam=true }) =>
    Request({
      url: url,
      data: body,
      useCache,
      userService,
      method: "POST",
      auth: true,
      params: params,
      plainAccessRequest: plainAccessRequest,
      setTimeParam
    })
};


