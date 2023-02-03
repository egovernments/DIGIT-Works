import { useQuery, useQueryClient } from "react-query";
import { CustomService } from "../services/elements/CustomService";


const useCustomAPIHook = ({url, params, body, config = {}}) => {
  const client = useQueryClient();
  
  const { isLoading, data,isFetching } = useQuery(
    [url].filter((e) => e),
    () => CustomService.getResponse({ url, params, body }),
    {...config
    }
  );
  
  return {
    isLoading,
    isFetching,
    data,
    revalidate: () => {
      data && client.invalidateQueries({ queryKey: [ url].filter((e) => e) });
    },
  };
};

export default useCustomAPIHook;
