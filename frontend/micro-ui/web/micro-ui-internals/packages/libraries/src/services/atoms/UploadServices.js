import Axios from "axios";
import Urls from "./urls";
import { ServiceRequest } from "./Utils/Request";
import { PersistantStorage } from "./Utils/Storage";
import { ApiCacheService } from "./ApiCacheService";

const getCacheSetting = (moduleName) => {
  return ApiCacheService.getSettingByServiceUrl(Urls.FileFetch, moduleName);
};

const mergedData = {};
const mergedPromises = {};
const callAllPromises = (success, promises = [], resData) => {
  promises.forEach((promise) => {
    if (success) {
      promise.resolve(resData);
    } else {
      promise.reject(resData);
    }
  });
};
const mergeData = (fileStoreIds, tenantId) => {
  if (!mergedData[tenantId] || Object.keys(mergedData[tenantId]).length === 0) {
    mergedData[tenantId] = fileStoreIds;
  } else {
    mergedData[tenantId] = mergedData[tenantId] + "," + fileStoreIds;
  }
};
const debouncedCall = ({ serviceName, url, useCache, params }, resolve, reject) => {
  if (!mergedPromises[params.tenantId] || mergedPromises[params.tenantId].length === 0) {
    const cacheSetting = getCacheSetting();
    setTimeout(() => {
      let callData = JSON.parse(JSON.stringify(mergedData[params.tenantId]));
      mergedData[params.tenantId] = {};
      let callPromises = [...mergedPromises[params.tenantId]];
      mergedPromises[params.tenantId] = [];
      params["fileStoreIds"] = callData;
      ServiceRequest({
        serviceName,
        url,
        method: "GET",
        data: {},
        useCache,
        params,
      })
        .then((data) => {
          callAllPromises(true, callPromises, data);
        })
        .catch((err) => {
          callAllPromises(false, callPromises, err);
        });
    }, cacheSetting.debounceTimeInMS || 500);
  }
  mergeData(params?.fileStoreIds, params.tenantId);
  if (!mergedPromises[params.tenantId]) {
    mergedPromises[params.tenantId] = [];
  }
  mergedPromises[params.tenantId].push({ resolve, reject });
};

export const UploadServices = {
  Filestorage: async (module, filedata, tenantId) => {
    const formData = new FormData();

    formData.append("file", filedata, filedata.name);
    formData.append("tenantId", tenantId);
    formData.append("module", module);
    let tenantInfo = window?.globalConfigs?.getConfig("ENABLE_SINGLEINSTANCE") ? `?tenantId=${tenantId}` : "";
    var config = {
      method: "post",
      url: `${Urls.FileStore}${tenantInfo}`,
      data: formData,
      headers: { "auth-token": Digit.UserService.getUser() ? Digit.UserService.getUser()?.access_token : null },
    };

    return Axios(config);
  },

  MultipleFilesStorage: async (module, filesData, tenantId) => {
    const formData = new FormData();
    const filesArray = Array.from(filesData);
    filesArray?.forEach((fileData, index) => (fileData ? formData.append("file", fileData, fileData.name) : null));
    formData.append("tenantId", tenantId);
    formData.append("module", module);
    let tenantInfo = window?.globalConfigs?.getConfig("ENABLE_SINGLEINSTANCE") ? `?tenantId=${tenantId}` : "";
    var config = {
      method: "post",
      url: `${Urls.FileStore}${tenantInfo}`,
      data: formData,
      headers: { "Content-Type": "multipart/form-data", "auth-token": Digit.UserService.getUser().access_token },
    };

    return Axios(config);
  },
  call: (tenantId, filesArray) => {
    return new Promise((resolve, reject) =>
      debouncedCall(
        {
          serviceName: "filestoreFetch",
          url: Urls.FileFetch,
          useCache: true,
          params: { tenantId: tenantId, fileStoreIds: filesArray?.join(",") },
        },
        resolve,
        reject
      )
    );
  },
  /* old filestore service used to fetch files without caching */
  FilefetchV0: async (filesArray, tenantId) => {
    let tenantInfo = window?.globalConfigs?.getConfig("ENABLE_SINGLEINSTANCE") ? `?tenantId=${tenantId}` : "";
    var config = {
      method: "get",
      url: `${Urls.FileFetch}${tenantInfo}`,
      params: {
        tenantId: tenantId,
        fileStoreIds: filesArray?.join(","),
      },
    };
    const res = await Axios(config);
    return res;
  },

  /* New Filestore method added to have a file fetch with caching PFM-3698*/
  Filefetch: async (filesArray, tenantId) => {
    const key = `FILE.${tenantId}.${JSON.stringify(filesArray)}`;
    const inStoreValue = PersistantStorage.get(key);
    if (inStoreValue) {
      return inStoreValue;
    }
    const response = await UploadServices.call(tenantId, filesArray);
    const responseValue = { data: response };
    const cacheSetting = getCacheSetting();
    PersistantStorage.set(key, responseValue, cacheSetting.cacheTimeInSecs);
    return responseValue;
  },
};
