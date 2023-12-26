import { Request } from "../atoms/Utils/Request";
import Urls from "../atoms/urls";

export const MeasurementService = {
    search: async (criteria, tenantId) => {
        try {
            const response = await Request({
                url: Urls.measurement.search,
                data: criteria,
                useCache: false,
                setTimeParam: false,
                method: "POST",
                auth: true,
                userService: true,
                params: { tenantId }
            });
            return response; 
        } catch (error) {
            throw new Error(error.response?.Errors[0].message);
        }
    }
};
