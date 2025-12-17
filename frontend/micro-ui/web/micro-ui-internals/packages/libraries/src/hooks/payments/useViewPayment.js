import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/Expenditure/Bills/View';
import { ViewPayment } from '../../services/molecules/Expenditure/Payments/ViewPayment';


const useViewPayment = ({tenantId, data, config = {}}) => {
    const { t } = useTranslation();
    return useQuery(
        ["PAYMENT_INST_DETAILS", tenantId], 
        () => ViewPayment.fetchPayment(t, tenantId, data), 
        config
    );
}

export default useViewPayment;