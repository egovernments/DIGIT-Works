import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const BankAccountService = {
    search: (data, searchParams) =>
        Request({
            url: Urls.bankaccount.search,
            useCache: false,
            method: "POST",
            auth: true,
            userService: true,
            data: data,
            params: { ...searchParams }
        })
  };