import { ContractSearch } from "../../services/molecules/Contracts/Search";
import { useQuery } from "react-query";

const useViewContractDetailsClosureScreen = (t, tenantId, loiNumber, subEstiamteNumber, config = {}) =>{
    return useQuery(
        ["CONTRACT_WORKS_SEARCH", "CONTRACT_SEARCH", tenantId, loiNumber, subEstiamteNumber],
        () => ContractSearch.viewContractsClosureScreen(t, tenantId, loiNumber, subEstiamteNumber),
        config
    );
}

export default useViewContractDetailsClosureScreen