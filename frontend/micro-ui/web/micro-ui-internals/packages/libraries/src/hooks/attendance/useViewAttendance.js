import { useQuery } from "react-query";
import { fetchAttendanceDetails } from "../../services/molecules/Attendance/View";

const useViewAttendance = (tenantId, searchParams, config = {}) => {
  return useQuery(["ATTENDANCE_DETAILS", tenantId, searchParams], () => fetchAttendanceDetails(tenantId, searchParams), config);
};

export default useViewAttendance;
