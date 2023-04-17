import { useQuery } from "react-query";
import { useTranslation } from "react-i18next";
import { fetchAttendanceDetails } from "../../services/molecules/Attendance/View";

const useViewAttendance = (tenantId, searchParams, config = {},isStateChanged) => {
  const { t } = useTranslation();
  return useQuery(["ATTENDANCE_DETAILS", tenantId, searchParams,isStateChanged], () => fetchAttendanceDetails(t, tenantId, searchParams), {staleTime:0,...config});
};

export default useViewAttendance;
