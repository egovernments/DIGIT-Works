import { useQuery } from 'react-query';
import { useTranslation } from "react-i18next";
import { View } from '../../../../libraries/src/services/molecules/Measurement/View';


const useViewMeasurement = (tenantId, data, searchParams, config = {},revisedWONumber) => {
    const { t } = useTranslation();
    return useQuery(
        ["VIEW_MEASUREMENT_DETAILS", tenantId, searchParams?.contractNumber, data?.measurementNumber], 
        () => View.fetchMeasurementDetails(t, tenantId, data, searchParams,revisedWONumber), 
        config
    );
}

export default useViewMeasurement;