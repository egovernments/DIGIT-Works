import { useMutation } from "react-query";
import ApplicationUpdateActionsEstimate from "../../services/molecules/Works/ApplicationUpdateActionsEstimate";

const useUpdateEstimate = () => {
    return useMutation((applicationData) => ApplicationUpdateActionsEstimate(applicationData));
};

export default useUpdateEstimate;
