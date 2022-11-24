import { useQuery } from "react-query";
import { fetchAttendanceDetails } from "../../services/molecules/Attendance/View";

const useViewAttendance = () => {
  // console.log(useQuery(["VIEWATTENDANCE"], () => fetchAttendanceDetails()));
  return fetchAttendanceDetails();
};

export default useViewAttendance;
