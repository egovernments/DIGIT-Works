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
}