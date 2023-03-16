
import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/Expenditure/Bills/View';

const useViewBill = (tenantId, data, searchParams, config = {}) => {
    const { t } = useTranslation();
    return useQuery(
        ["BILL_DETAILS", tenantId, searchParams], 
        () => View.fetchBillDetails(t, tenantId, data, searchParams), 
        config
    );
}

export default useViewBill;