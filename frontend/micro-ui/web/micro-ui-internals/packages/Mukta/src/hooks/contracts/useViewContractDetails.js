import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../../../libraries/src/services/molecules/Contracts/View';


const useViewContractDetails = (tenantId, data, searchParams, config = {}) => {
    const { t } = useTranslation();
    return useQuery(
        ["VIEW_CONTRACT_DETAILS", tenantId, searchParams?.contractNumber], 
        () => View.fetchContractDetails(t, tenantId, data, searchParams), 
        config
    );
}

export default useViewContractDetails;