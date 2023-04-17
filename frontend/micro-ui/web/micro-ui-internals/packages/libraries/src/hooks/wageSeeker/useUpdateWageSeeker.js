import { useMutation } from "react-query"
import { WageSeekerService } from "../../services/elements/WageSeeker"

const useUpdateWageSeeker = () => {
    return useMutation(data => WageSeekerService.update(data))
}

export default useUpdateWageSeeker;