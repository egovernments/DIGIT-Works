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
    String? tenantId,
    String? musterRollNumber,
    String? registerId,
    String? status,
    String? musterRollStatus,
    int? startDate,
    int? endDate,
    @JsonKey(name: 'individualEntries')
        List<IndividualEntries>? individualEntries,
    @JsonKey(name: 'additionalDetails')
        MusterAdditionalDetails? musterAdditionalDetails,
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
  }) = _MusterAdditionalDetails;

  factory MusterAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterAdditionalDetailsFromJson(json);
}

@freezed
class AttendanceEntries with _$AttendanceEntries {
  const factory AttendanceEntries(
      {String? id,
      double? attendance,
      int? time,
      AuditDetails? auditDetails}) = _AttendanceEntries;

  factory AttendanceEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AttendanceEntriesFromJson(json);
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
