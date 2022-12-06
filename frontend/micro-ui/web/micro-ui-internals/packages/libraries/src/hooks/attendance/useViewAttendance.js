import { useQuery } from "react-query";
import { fetchAttendanceDetails } from "../../services/molecules/Attendance/View";

const useViewAttendance = () => {
  return fetchAttendanceDetails();
};

export default useViewAttendance;
