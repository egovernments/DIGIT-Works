import { useMutation } from "react-query"
import { WageSeekerService } from "../../services/elements/WageSeeker"

export const useUpdateWageSeeker = () => {
    return useMutation(data => WageSeekerService.update(data))
}

export default useUpdateWageSeeker;