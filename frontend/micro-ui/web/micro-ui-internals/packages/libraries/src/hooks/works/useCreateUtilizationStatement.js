import { WorksService } from "../../services/elements/Works";
import { useMutation } from "react-query";

const useCreateUtilizationStatement = (businessService = "WORKS") => {
    //look here create a similary createutilizationstatement
    return useMutation((data) => WorksService.createUtilizationStatement(data));
}

export default useCreateUtilizationStatement;