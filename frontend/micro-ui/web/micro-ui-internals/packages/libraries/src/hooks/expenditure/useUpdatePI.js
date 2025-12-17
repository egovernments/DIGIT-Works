import { ExpenseService } from "../../services/elements/Expense";
import { useMutation } from "react-query";

const useUpdatePI=()=>{
    return useMutation((data)=>{
       return ExpenseService.updatePI(data);
    });
}

export default useUpdatePI;