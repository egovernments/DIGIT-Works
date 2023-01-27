import { useQuery, useQueryClient } from "react-query";
import { CustomService } from "../services/elements/CustomService";

const useCustomAPIHook = (url, params, body, plainAccessRequest, config = {}) => {
  const client = useQueryClient();
  const { isLoading, data } = useQuery(
    ["CUSTOM", { ...params, ...body, ...plainAccessRequest }].filter((e) => e),
    () => CustomService.getResponse({ url, params, body, plainAccessRequest }),
    config
  );
  return {
    isLoading,
    data,
    revalidate: () => {
      data && client.invalidateQueries({ queryKey: ["CUSTOM", { ...params, ...body, ...plainAccessRequest }] });
    },
  };
};

export default useCustomAPIHook;
