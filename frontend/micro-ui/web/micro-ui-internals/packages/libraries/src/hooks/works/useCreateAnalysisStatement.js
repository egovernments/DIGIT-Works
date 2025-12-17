import { WorksService } from "../../services/elements/Works";
import { useMutation } from "react-query";

const useCreateAnalysisStatement = (businessService = "WORKS") => {
    //look here create a similary createutilizationstatement
    return useMutation((data) => WorksService.createAnalysisStatement(data));
}

export default useCreateAnalysisStatement;