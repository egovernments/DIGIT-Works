import { ContractSearch } from "../../services/molecules/Contracts/Search";
import { useQuery } from "react-query";

const useViewContractDetails = (t, tenantId, loiNumber,subEstiamteNumber, config = {}) => {
    
    return useQuery(
        ["CONTRACT_WORKS_SEARCH", "CONTRACT_SEARCH", tenantId, loiNumber, subEstiamteNumber],
        () => ContractSearch.viewContractScreen(t, tenantId, loiNumber, subEstiamteNumber),
        config
    );
}

export default useViewContractDetails