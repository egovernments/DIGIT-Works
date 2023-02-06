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
    @JsonKey(name: 'staff') List<StaffEntries>? staffEntries,
    @JsonKey(name: 'attendees') List<AttendeesEntries>? attendeesEntries,
  }) = _AttendanceRegister;

  factory AttendanceRegister.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceRegisterFromJson(json);
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

@freezed
class AuditDetails with _$AuditDetails {
  const factory AuditDetails(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime}) = _AuditDetails;

  factory AuditDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AuditDetailsFromJson(json);
}
