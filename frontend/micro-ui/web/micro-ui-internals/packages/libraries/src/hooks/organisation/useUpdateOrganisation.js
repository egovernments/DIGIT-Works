import { useMutation } from 'react-query'
import { OrganisationService } from '../../services/elements/Organisation'

const useUpdateOrganisation = () => {
    return useMutation(data => OrganisationService.update(data))
}

export default useUpdateOrganisation;