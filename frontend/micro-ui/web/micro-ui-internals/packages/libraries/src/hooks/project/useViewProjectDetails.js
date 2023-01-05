import { getProjectDetails } from "../../services/molecules/Project/getProjectDetails";

const useViewProjectDetails = (tenantId, config = { }) => {
    // return useQuery(
    //     ["PROJECTDETAILS", tenantId],
    //     () => WorksSearch.viewProjectClosureScreen(tenantId),
    //     config
    // );
    return getProjectDetails();
}

export default useViewProjectDetails;