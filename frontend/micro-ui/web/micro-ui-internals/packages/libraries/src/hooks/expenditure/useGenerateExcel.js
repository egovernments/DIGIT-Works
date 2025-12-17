import { ExpenseService } from "../../services/elements/Expense";
import { useMutation } from "react-query";

const useGenerateExcel=()=>{
    return useMutation((data)=>{
       return ExpenseService.generateExcel(data);
    });
}

export default useGenerateExcel;