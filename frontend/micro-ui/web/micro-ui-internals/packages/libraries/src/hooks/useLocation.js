import { useQuery } from 'react-query';
import { LocationService } from "../services/elements/Location";
import { MdmsService } from '../services/elements/MDMS';

const useLocation = (tenantId, config = {}, locationType) => {
    switch(locationType) {
        case 'Locality':
            return useQuery(["LOCALITY_DETAILS", tenantId ], () => LocationService.getLocalities(tenantId), config);   
        case 'Ward':
            return useQuery(["WARD_DETAILS", tenantId ], () => LocationService.getWards(tenantId), config);
        default:
            break
    } 
}

export default useLocation;
