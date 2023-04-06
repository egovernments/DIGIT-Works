import React from "react";
import { useQuery } from "react-query";
import { WorksService } from "../../services/elements/Works";

//using this hook for searching only one estimate with estimateNumber
const useBillCreate = async ({ body, config = {} }) => {

    // return useQuery(
    //     ["CONTRACT_SEARCH"],
    //     () => WorksService.createBill(body),
    //     {
    //         ...config,
    //         cacheTime:10000
    //     }
    // )

    const result = await WorksService.createBill(body)
    return result 
}
export default useBillCreate
