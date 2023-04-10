import { useMutation } from "react-query"
import { OrganisationService } from "../../services/elements/Organisation"

const useCreateOrganisation = () => {
    return useMutation(data => OrganisationService.create(data))
}

export default useCreateOrganisation;