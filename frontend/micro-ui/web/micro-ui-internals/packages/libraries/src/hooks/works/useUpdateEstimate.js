import { WorksService } from "../../services/elements/Works";
import { useMutation } from "react-query";

const useUpdateEstimate=(businessService = "WORKS")=>{
    return useMutation((data)=>WorksService.updateEstimate(data,businessService));
}

export default useUpdateEstimate;