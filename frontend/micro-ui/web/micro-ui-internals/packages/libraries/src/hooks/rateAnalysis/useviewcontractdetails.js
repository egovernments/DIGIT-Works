import { useQuery } from "react-query";
import { useTranslation } from "react-i18next";
import { View } from "../../services/molecules/rateAnalysis/View";

const useViewRateAnalysisDetails = (tenantId, data) => {
  const { t } = useTranslation();
  return useQuery(["VIEW_RATE_ANALYSIS_DETAILS", tenantId, data],
    
     () => View.fetchRateAnalysisDetails(tenantId, data),
     {
      staleTime: 0,  // Data is always considered stale
      cacheTime: 0,  // Data is immediately removed from the cache
    }
    );
};

export default useViewRateAnalysisDetails;
