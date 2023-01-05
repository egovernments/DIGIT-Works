// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

part 'attendance_registry_model.freezed.dart';
part 'attendance_registry_model.g.dart';

@freezed
class AttendenceRegistersModel with _$AttendenceRegistersModel {
  const factory AttendenceRegistersModel({
    @JsonKey(name: 'attendanceRegister') List<AttendenceRegister>? attendenceRegister,
  }) = _AttendenceRegistersModel;

  factory AttendenceRegistersModel.fromJson(
      Map<String, dynamic> json,
      ) =>
      _$AttendenceRegistersModelFromJson(json);
}

@freezed
class AttendenceRegister with _$AttendenceRegister {
  const factory AttendenceRegister({
    String? id,
    String? tenantId,
    String? registerNumber,
    String? name,
    int? startDate,
    int? endDate,
    @JsonKey(name: 'staff')
    List<StaffEntries>? staffEntries,
    @JsonKey(name: 'attendees')
    List<AttendeesEntries>? attendeesEntries,
  }) = _AttendenceRegister;

  factory AttendenceRegister.fromJson(
      Map<String, dynamic> json,
      ) =>
      _$AttendenceRegisterFromJson(json);
}

@freezed
class StaffEntries with _$StaffEntries {
  const factory StaffEntries({
    String? id,
  }) = _StaffEntries;

  factory StaffEntries.fromJson(
      Map<String, dynamic> json,
      ) =>
      _$StaffEntriesFromJson(json);
}

@freezed
class AttendeesEntries with _$AttendeesEntries {
  const factory AttendeesEntries(
      {String? id,
        }) = _AttendeesEntries;

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