import { useMutation } from 'react-query'
import { WorksService } from "../../services/elements/Works";

const useUpdatePurchaseBill = () => {
    return useMutation(data => WorksService.updatePurchaseBill(data))
}

export default useUpdatePurchaseBill;