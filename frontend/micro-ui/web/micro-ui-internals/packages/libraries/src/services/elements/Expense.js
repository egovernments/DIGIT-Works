import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

export const ExpenseService = {
  
  createPayment: (data) => 
    Request({
      url: Urls.expense.createPayment,
      useCache: false,
      method: "POST",
      auth: true,
      userService: false,
      data: data
    }),
    searchPayment: (data) => 
    Request({
      url: Urls.expense.searchPayment,
      useCache: false,
      method: "POST",
      auth: true,
      userService: false,
      data: data
    }), 
     updatePayment: (data) => 
    Request({
      url: Urls.expense.updatePayment,
      useCache: false,
      method: "POST",
      auth: true,
      userService: false,
      data: data
    }),
    regeneratePDF: (data,tenantId) => 
    Request({
      url: Urls.expense.regenerate,
      useCache: false,
      method: "POST",
      auth: true,
      userService: false,
      data: data,
      params: { tenantId}
    })

};
