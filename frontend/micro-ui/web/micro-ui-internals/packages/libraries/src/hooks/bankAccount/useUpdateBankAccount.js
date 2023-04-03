import { useMutation } from "react-query"
import { BankAccountService } from "../../services/elements/BankAccount"

export const useUpdateBankAccount = () => {
    return useMutation(data => BankAccountService.update(data))
}

export default useUpdateBankAccount;