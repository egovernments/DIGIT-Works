import { Request } from "../atoms/Utils/Request";

export const CustomService = {

  getResponse: ({url,params,body}) =>
    Request({
      url: url,
      data: body,
      useCache: true,
      userService: true,
      method: "POST",
      auth: true,
      params: params,
    }),
};
