import { useMutation } from "react-query"
import { WageSeekerService } from "../../services/elements/WageSeeker"

export const useDeleteWageSeeker = () => {
    return useMutation(data => WageSeekerService.delete(data))
}

export default useDeleteWageSeeker;