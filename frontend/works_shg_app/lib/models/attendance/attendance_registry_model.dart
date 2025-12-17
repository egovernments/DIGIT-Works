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
    required String tenantId,
    String? registerNumber,
    String? serviceCode,
    String? referenceId,
    String? name,
    int? startDate,
    int? endDate,
    String? status,
    @JsonKey(name: 'additionalDetails')
        AttendanceRegisterAdditionalDetails?
            attendanceRegisterAdditionalDetails,
    @JsonKey(name: 'staff')
        List<StaffEntries>? staffEntries,
    @JsonKey(name: 'auditDetails')
        RegisterAuditDetails? registerAuditDetails,
    @JsonKey(name: 'attendees')
        List<AttendeesEntries>? attendeesEntries,
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
      String? orgName,
      String? officerInCharge,
      String? executingAuthority,
      String? projectId,
      String? projectName,
      String? projectType,
      String? projectDesc,
      String? locality,
      String? ward,
      int? amount}) = _AttendanceRegisterAdditionalDetails;

  factory AttendanceRegisterAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceRegisterAdditionalDetailsFromJson(json);
}

@freezed
class RegisterAuditDetails with _$RegisterAuditDetails {
  const factory RegisterAuditDetails(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime}) = _RegisterAuditDetails;

  factory RegisterAuditDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$RegisterAuditDetailsFromJson(json);
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
      required String tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate,
      int? denrollmentDate,
      @JsonKey(name: 'additionalDetails')
          AttendeesAdditionalDetails? additionalDetails}) = _AttendeesEntries;

  factory AttendeesEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeesEntriesFromJson(json);
}

@freezed
class AttendeesAdditionalDetails with _$AttendeesAdditionalDetails {
  const factory AttendeesAdditionalDetails({
    String? individualName,
    String? gender,
    String? individualGaurdianName,
    String? individualID,
    String? identifierId,
    String? bankNumber,
  }) = _AttendeesAdditionalDetails;

  factory AttendeesAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendeesAdditionalDetailsFromJson(json);
}
