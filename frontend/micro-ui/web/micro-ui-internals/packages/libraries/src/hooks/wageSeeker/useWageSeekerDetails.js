
import { useQuery } from 'react-query';
import { View } from '../../services/molecules/WageSeeker/View';

const useWageSeekerDetails = ({tenantId, data, searchParams, config = {}}) => {
    return useQuery(
        ["WAGE_SEEKER_DETAILS_WITH_BANK_ACCOUNT", tenantId, searchParams], 
        () => View.fetchWageSeekerWithBankDetails(tenantId, data, searchParams), 
        config
    );
}

export default useWageSeekerDetails;