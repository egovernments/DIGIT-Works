import { useQuery } from 'react-query';
import { LocationService } from "../services/elements/Location";

const useLocation = (tenantId, config = {}, locationType) => {
    return useQuery([`${locationType}_DETAILS`, tenantId ], () => LocationService.getDataByLocationType(tenantId, locationType), config);  
}

export default useLocation;
