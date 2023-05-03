import { BillsSearch } from "../../services/molecules/Expenditure/Search";
import { useQuery } from "react-query";

const useSupervisionBillScreen = ({t, tenantId, billNumber, config, isStateChanged}) => {
  return useQuery(
    ["SUPERVISION_BILL_SEARCH", "BILL_SEARCH", tenantId, billNumber, isStateChanged],
    () => BillsSearch.viewSupervisionBill({t, tenantId, billNumber}),
    { staleTime: 0, ...config }
  );
};

export default useSupervisionBillScreen;
