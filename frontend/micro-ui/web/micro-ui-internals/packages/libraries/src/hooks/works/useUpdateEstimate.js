import { useMutation } from "react-query";
import ApplicationUpdateActionsEstimate from "../../services/molecules/Works/ApplicationUpdateActionsEstimate";

const useApplicationActionsEstimate = (businessService) => {
    return useMutation((applicationData) => ApplicationUpdateActionsEstimate(applicationData, businessService));
};

export default useApplicationActionsEstimate;
 