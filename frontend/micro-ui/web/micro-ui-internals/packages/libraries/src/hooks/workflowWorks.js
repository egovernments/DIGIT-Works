import { useQuery, useQueryClient } from "react-query";

const useWorkflowDetailsWorks = ({ tenantId, id, moduleCode, role = "CITIZEN", serviceData = {}, getStaleData, getTripData = false, config }) => {
    const queryClient = useQueryClient();

    const staleDataConfig = { staleTime: Infinity };

    const { isLoading, error, isError, data } = useQuery(
        ["workFlowDetailsWorks", tenantId, id, moduleCode, role, config],
        () => Digit.WorkflowService.getDetailsByIdWorks({ tenantId, id, moduleCode, role, getTripData }),
        getStaleData ? { ...staleDataConfig, ...config } : config
    );

    if (getStaleData) return { isLoading, error, isError, data };

    return { isLoading, error, isError, data, revalidate: () => queryClient.invalidateQueries(["workFlowDetailsWorks", tenantId, id, moduleCode, role]) };
};

export default useWorkflowDetailsWorks;
