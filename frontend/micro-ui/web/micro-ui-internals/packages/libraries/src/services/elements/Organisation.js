import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const OrganisationService = {
    search: ( data ) =>
        Request({
            url: Urls?.organisation?.search,
            data: data,
            useCache: false,
            setTimeParam: false,
            userService: true,
            method: "POST",
            params: {},
            auth: true,
        })
};
