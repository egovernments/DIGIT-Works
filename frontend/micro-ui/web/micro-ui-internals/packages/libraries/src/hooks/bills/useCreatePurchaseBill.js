import { WorksService } from "../../services/elements/Works";
import { useMutation } from "react-query";

const useCreatePurchaseBill=()=>{
    return useMutation((payload)=>{
       return WorksService.createPurchaseBill(payload);
    });
}

export default useCreatePurchaseBill;