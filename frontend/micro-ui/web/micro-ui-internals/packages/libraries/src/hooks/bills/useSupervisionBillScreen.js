import { BillsSearch } from "../../services/molecules/Expenditure/Search";
import { useQuery } from "react-query";

const useSupervisionBillScreen = ({t, tenantId, estimateNumber, config, isStateChanged}) => {
  return useQuery(
    ["SUPERVISION_BILL_SEARCH", "BILL_SEARCH", tenantId, estimateNumber, isStateChanged],
    () => BillsSearch.viewSupervisionBill({t, tenantId, estimateNumber}),
    { staleTime: 0, ...config }
  );
};

export default useSupervisionBillScreen;
