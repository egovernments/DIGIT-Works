import React from "react";
import { useMutation } from "react-query";
import { WorksService } from "../../services/elements/Works";


const useSearch = ()=>{
    return useMutation((data)=>WorksService.estimateSearch(data));
}

export default useSearch
