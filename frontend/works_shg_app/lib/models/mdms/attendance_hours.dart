import 'package:freezed_annotation/freezed_annotation.dart';

part 'attendance_hours.freezed.dart';
part 'attendance_hours.g.dart';

@freezed
class AttendanceHoursList with _$AttendanceHoursList {
  const factory AttendanceHoursList({
    @JsonKey(name: 'AttendanceHours') List<AttendanceHours>? attendanceHours,
  }) = _AttendanceHoursList;

  factory AttendanceHoursList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceHoursListFromJson(json);
}

@freezed
class AttendanceHours with _$AttendanceHours {
  const factory AttendanceHours({
    required String code,
    required String value,
    required bool active,
  }) = _AttendanceHours;

  factory AttendanceHours.fromJson(Map<String, dynamic> json) =>
      _$AttendanceHoursFromJson(json);
}
