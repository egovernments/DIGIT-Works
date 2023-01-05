import { getFinancialDetails } from "../../services/molecules/Project/getFinancialDetails";

const useViewFinancialDetails = (tenantId, config = { }) => {
    // return useQuery(
    //     ["PROJECTDETAILS", tenantId],
    //     () => WorksSearch.viewProjectClosureScreen(tenantId),
    //     config
    // );
    return getFinancialDetails();
}

export default useViewFinancialDetails;