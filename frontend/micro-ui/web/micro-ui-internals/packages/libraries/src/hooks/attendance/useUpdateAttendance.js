import { useMutation } from "react-query"
import AttendanceService from "../../services/elements/Attendance"

export const useUpdateAttendance = () => {
    return useMutation(data => AttendanceService.update(data))
}

export default useUpdateAttendance;