import { useMutation } from "react-query"
import { BankAccountService } from "../../services/elements/BankAccount"

export const useCreateBankAccount = () => {
    return useMutation(data => BankAccountService.create(data))
}

export default useCreateBankAccount;