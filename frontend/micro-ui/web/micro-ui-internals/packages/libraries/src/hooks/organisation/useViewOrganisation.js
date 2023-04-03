import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/Organisation/View';

const useViewOrganisation = ({tenantId, data, config = {}}) => {
    const { t } = useTranslation();
    return useQuery(
        ["ORGANISATION_DETAILS", tenantId ], 
        () => View.fetchOrganisationDetails(t, tenantId, data), 
        config
    );
}

export default useViewOrganisation;