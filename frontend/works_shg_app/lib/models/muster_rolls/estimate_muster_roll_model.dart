// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

import 'muster_roll_model.dart';

part 'estimate_muster_roll_model.freezed.dart';
part 'estimate_muster_roll_model.g.dart';

@freezed
class EstimateMusterRollsModel with _$EstimateMusterRollsModel {
  const factory EstimateMusterRollsModel({
    @JsonKey(name: 'musterRolls') List<EstimateMusterRoll>? musterRoll,
    @JsonKey(name: 'count') int? count,
  }) = _EstimateMusterRollsModel;

  factory EstimateMusterRollsModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$EstimateMusterRollsModelFromJson(json);
}

@freezed
class EstimateMusterRoll with _$EstimateMusterRoll {
  const factory EstimateMusterRoll({
    String? id,
    required String tenantId,
    String? musterRollNumber,
    String? registerId,
    String? status,
    String? musterRollStatus,
    int? startDate,
    int? endDate,
    @JsonKey(name: 'individualEntries')
        List<EstimateIndividualEntries>? individualEntries,
    @JsonKey(name: 'additionalDetails')
        MusterAdditionalDetails? musterAdditionalDetails,
    @JsonKey(name: 'auditDetails')
        AuditDetails? musterAuditDetails,
  }) = _EstimateMusterRoll;

  factory EstimateMusterRoll.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$EstimateMusterRollFromJson(json);
}

@freezed
class EstimateIndividualEntries with _$EstimateIndividualEntries {
  const factory EstimateIndividualEntries({
    String? id,
    String? individualId,
    double? totalAttendance,
    @JsonKey(name: 'attendanceEntries')
        List<AttendanceEntries>? attendanceEntries,
    @JsonKey(name: 'additionalDetails')
        EstimateMusterIndividualAdditionalDetails?
            musterIndividualAdditionalDetails,
  }) = _EstimateIndividualEntries;

  factory EstimateIndividualEntries.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$EstimateIndividualEntriesFromJson(json);
}

@freezed
class EstimateMusterIndividualAdditionalDetails
    with _$EstimateMusterIndividualAdditionalDetails {
  const factory EstimateMusterIndividualAdditionalDetails({
    String? userName,
    String? fatherName,
    String? gender,
    String? aadharNumber,
    String? bankDetails,
    String? userId,
    List<String>? skillCode,
    String? accountHolderName,
    String? accountType,
    String? skillValue,
  }) = _EstimateMusterIndividualAdditionalDetails;

  factory EstimateMusterIndividualAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$EstimateMusterIndividualAdditionalDetailsFromJson(json);
}
