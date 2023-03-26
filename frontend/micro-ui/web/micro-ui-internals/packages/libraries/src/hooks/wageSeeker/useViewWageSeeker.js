

import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/WageSeeker/View';

const useViewWageSeeker = ({tenantId, data, searchParams, config = {}}) => {
    const { t } = useTranslation();
    return useQuery(
        ["WAGE_SEEKER_DETAILS", tenantId, searchParams], 
        () => View.fetchWageSeekerDetails(t, tenantId, data, searchParams), 
        config
    );
}

export default useViewWageSeeker;