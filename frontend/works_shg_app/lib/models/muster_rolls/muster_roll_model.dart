// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

part 'muster_roll_model.freezed.dart';
part 'muster_roll_model.g.dart';

@freezed
class MusterRollsModel with _$MusterRollsModel {
  const factory MusterRollsModel({
    @JsonKey(name: 'musterRolls') List<MusterRoll>? musterRoll,
    @JsonKey(name: 'count') int? count,
  }) = _MusterRollsModel;

  factory MusterRollsModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterRollsModelFromJson(json);
}

@freezed
class MusterRoll with _$MusterRoll {
  const factory MusterRoll({
    String? id,
    required String tenantId,
    String? musterRollNumber,
    String? registerId,
    String? status,
    String? musterRollStatus,
    String? serviceCode,
    String? referenceId,
    int? startDate,
    int? endDate,
    @JsonKey(name: 'individualEntries')
        List<IndividualEntries>? individualEntries,
    @JsonKey(name: 'additionalDetails')
        MusterAdditionalDetails? musterAdditionalDetails,
    @JsonKey(name: 'auditDetails')
        AuditDetails? musterAuditDetails,
  }) = _MusterRoll;

  factory MusterRoll.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterRollFromJson(json);
}

@freezed
class IndividualEntries with _$IndividualEntries {
  const factory IndividualEntries({
    String? id,
    String? individualId,
    double? totalAttendance,
    @JsonKey(name: 'attendanceEntries')
        List<AttendanceEntries>? attendanceEntries,
    @JsonKey(name: 'additionalDetails')
        MusterIndividualAdditionalDetails? musterIndividualAdditionalDetails,
  }) = _IndividualEntries;

  factory IndividualEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$IndividualEntriesFromJson(json);
}

@freezed
class MusterAdditionalDetails with _$MusterAdditionalDetails {
  const factory MusterAdditionalDetails({
    String? attendanceRegisterName,
    String? attendanceRegisterNo,
    String? orgName,
    int? amount,
    String? assignee,
    String? billType,
    String? projectId,
    String? projectName,
    String? projectDesc,
    String? contractId,
  }) = _MusterAdditionalDetails;

  factory MusterAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterAdditionalDetailsFromJson(json);
}

@freezed
class MusterIndividualAdditionalDetails
    with _$MusterIndividualAdditionalDetails {
  const factory MusterIndividualAdditionalDetails({
    String? userName,
    String? fatherName,
    String? gender,
    String? aadharNumber,
    String? bankDetails,
    String? userId,
    String? accountHolderName,
    String? accountType,
    String? skillCode,
    String? skillValue,
  }) = _MusterIndividualAdditionalDetails;

  factory MusterIndividualAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterIndividualAdditionalDetailsFromJson(json);
}

@freezed
class AttendanceEntries with _$AttendanceEntries {
  const factory AttendanceEntries({
    String? id,
    double? attendance,
    int? time,
    AuditDetails? auditDetails,
    @JsonKey(name: 'additionalDetails')
        AttendanceEntriesAdditionalDetails? attendanceEntriesAdditionalDetails,
  }) = _AttendanceEntries;

  factory AttendanceEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceEntriesFromJson(json);
}

@freezed
class AttendanceEntriesAdditionalDetails
    with _$AttendanceEntriesAdditionalDetails {
  const factory AttendanceEntriesAdditionalDetails(
      {String? entryAttendanceLogId,
      String? exitAttendanceLogId}) = _AttendanceEntriesAdditionalDetails;

  factory AttendanceEntriesAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceEntriesAdditionalDetailsFromJson(json);
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
