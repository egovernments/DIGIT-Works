import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../services/molecules/Expenditure/Bills/View';
import { ViewPaymentInstruction } from '../../services/molecules/Expenditure/Payments/ViewPaymentInstruction';

const useViewPaymentInstruction = ({tenantId, data, config = {}}) => {
    const { t } = useTranslation();
    return useQuery(
        ["PAYMENT_INST_DETAILS", tenantId], 
        () => ViewPaymentInstruction.fetchPaymentInstruction(t, tenantId, data), 
        config
    );
}

export default useViewPaymentInstruction;