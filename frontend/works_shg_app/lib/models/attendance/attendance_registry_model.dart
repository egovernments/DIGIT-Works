// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

part 'attendance_registry_model.freezed.dart';
part 'attendance_registry_model.g.dart';

@freezed
class AttendanceRegistersModel with _$AttendanceRegistersModel {
  const factory AttendanceRegistersModel({
    @JsonKey(name: 'attendanceRegister')
        List<AttendanceRegister>? attendanceRegister,
  }) = _AttendanceRegistersModel;

  factory AttendanceRegistersModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceRegistersModelFromJson(json);
}

@freezed
class AttendanceRegister with _$AttendanceRegister {
  const factory AttendanceRegister({
    String? id,
    String? tenantId,
    String? registerNumber,
    String? name,
    int? startDate,
    int? endDate,
    String? status,
    @JsonKey(name: 'additionalDetails')
        AttendanceRegisterAdditionalDetails?
            attendanceRegisterAdditionalDetails,
    @JsonKey(name: 'staff') List<StaffEntries>? staffEntries,
    @JsonKey(name: 'attendees') List<AttendeesEntries>? attendeesEntries,
  }) = _AttendanceRegister;

  factory AttendanceRegister.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceRegisterFromJson(json);
}

@freezed
class AttendanceRegisterAdditionalDetails
    with _$AttendanceRegisterAdditionalDetails {
  const factory AttendanceRegisterAdditionalDetails(
      {String? contractId,
      String? orgName}) = _AttendanceRegisterAdditionalDetails;

  factory AttendanceRegisterAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceRegisterAdditionalDetailsFromJson(json);
}

@freezed
class StaffEntries with _$StaffEntries {
  const factory StaffEntries(
      {String? id,
      String? userId,
      String? registerId,
      int? enrollmentDate}) = _StaffEntries;

  factory StaffEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$StaffEntriesFromJson(json);
}

@freezed
class AttendeesEntries with _$AttendeesEntries {
  const factory AttendeesEntries(
      {String? id,
      String? tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate}) = _AttendeesEntries;

  factory AttendeesEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeesEntriesFromJson(json);
}
