import { useQuery, useQueryClient } from "react-query";
import { CustomService } from "../services/elements/CustomService";


const useCustomAPIHook = ({url, params, body, config = {},jsonPath}) => {
  const client = useQueryClient();
  
  const { isLoading, data } = useQuery(
    ["CUSTOM", { ...params,...body.Projects }].filter((e) => e),
    () => CustomService.getResponse({ url, params, body }),
    {...config,
    }
  );
  return {
    isLoading,
    data,
    revalidate: () => {
      data && client.invalidateQueries({ queryKey: ["CUSTOM", { ...params,...body.Projects }].filter((e) => e) });
    },
  };
};

export default useCustomAPIHook;
