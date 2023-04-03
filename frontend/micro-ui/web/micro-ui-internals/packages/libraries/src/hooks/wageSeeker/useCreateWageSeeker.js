import { useMutation } from "react-query"
import { WageSeekerService } from "../../services/elements/WageSeeker"

export const useCreateWageSeeker = () => {
    return useMutation(data => WageSeekerService.create(data))
}

export default useCreateWageSeeker;