import Urls from "../atoms/urls";
import { Request } from "../atoms/Utils/Request";

const AttendanceService = {
    search: (tenantId, searchParams) =>
      Request({
        url: Urls.attendencemgmt.mustorRoll.search,
        useCache: false,
        method: "POST",
        auth: true,
        userService: true,
        params: { tenantId, ...searchParams },
      })
  };

export default AttendanceService