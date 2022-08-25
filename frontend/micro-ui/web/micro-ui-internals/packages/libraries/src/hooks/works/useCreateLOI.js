import { WorksService } from "../../services/elements/Works";
import { useMutation } from "react-query";

const useCreateLOI = () => {
    return useMutation((data) => WorksService.create(data));
};

export default useCreateLOI;