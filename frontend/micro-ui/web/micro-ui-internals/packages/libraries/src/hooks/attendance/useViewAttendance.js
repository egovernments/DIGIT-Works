import { useQuery } from "react-query";
import { useTranslation } from "react-i18next";
import { fetchAttendanceDetails } from "../../services/molecules/Attendance/View";

const useViewAttendance = (tenantId, searchParams, config = {}) => {
  const { t } = useTranslation();
  return useQuery(["ATTENDANCE_DETAILS", tenantId, searchParams], () => fetchAttendanceDetails(t, tenantId, searchParams), config);
};

export default useViewAttendance;
