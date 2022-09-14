import { useQuery,useMutation } from "react-query";
import { WorksService } from "../../services/elements/Works";

const useSearchWORKS=(businessService = "WORKS")=>{
    return useMutation((data)=>WorksService.approvedEstimateSearch(data,businessService));
}


export default useSearchWORKS; 