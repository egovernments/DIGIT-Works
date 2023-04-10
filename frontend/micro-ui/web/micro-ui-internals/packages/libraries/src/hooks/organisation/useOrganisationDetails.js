
import { useQuery } from 'react-query';
import { View } from '../../services/molecules/Organisation/View';

const useOrganisationDetails = ({tenantId, data, config = {}}) => {
    return useQuery(
        ["ORGANISATION_DETAILS_WITH_BANK_ACCOUNT", tenantId], 
        () => View.fetchOrganisationWithBankDetails(tenantId, data), 
        config
    );
}

export default useOrganisationDetails;