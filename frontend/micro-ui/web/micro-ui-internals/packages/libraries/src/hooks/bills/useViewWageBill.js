
import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/Expenditure/Bills/View';

const useViewWageBill = ({tenantId, data, config = {}}) => {
    const { t } = useTranslation();
    return useQuery(
        ["BILL_DETAILS", tenantId], 
        () => View.fetchBillDetails(t, tenantId, data), 
        config
    );
}

export default useViewWageBill;