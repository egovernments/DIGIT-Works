import Urls from "../atoms/urls";
import { ServiceRequest } from "../atoms/Utils/Request";

const getLocationWithi18nKeys = (data) => {
  let options = []
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const headerLocale = Digit.Utils.locale.getTransformedLocale(tenantId)
  data && data?.TenantBoundary[0]?.boundary.map(item => {
    options.push({code: item.code, name: item.name,  i18nKey: `${headerLocale}_ADMIN_${item?.code}` })
  })
  return options;
} 

export const LocationService = {
  getLocalities: (tenantId) => {
    return ServiceRequest({
      serviceName: "getLocalities",
      url: Urls.location.localities,
      params: { tenantId: tenantId },
      useCache: true,
    });
  },
  getRevenueLocalities: async (tenantId) => {
    const response = await ServiceRequest({
      serviceName: "getRevenueLocalities",
      url: Urls.location.revenue_localities,
      params: { tenantId: tenantId },
      useCache: true,
    });
    return response;
  },
  getWards: (tenantId) => {
    return ServiceRequest({
      serviceName: "getWards",
      url: Urls.location.wards,
      params: { tenantId: tenantId },
      useCache: true,
    });
  },
  getDataByLocationType: async (tenantId, type) => {
    let response;
    switch(type) {
      case 'Locality':
        response = await LocationService.getLocalities(tenantId);
        break;
      case 'Ward':
        response = await LocationService.getWards(tenantId);
        break;
      default:
          break
    }  
    const transformedResponse = getLocationWithi18nKeys(response);
    return transformedResponse;
  },
};
