import { useMutation } from "react-query"
import { WageSeekerService } from "../../services/elements/WageSeeker"

const useCreateWageSeeker = () => {
    return useMutation(data => WageSeekerService.create(data))
}

export default useCreateWageSeeker;