// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'estimate_muster_roll_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_EstimateMusterRollsModel _$$_EstimateMusterRollsModelFromJson(
        Map<String, dynamic> json) =>
    _$_EstimateMusterRollsModel(
      musterRoll: (json['musterRolls'] as List<dynamic>?)
          ?.map((e) => EstimateMusterRoll.fromJson(e as Map<String, dynamic>))
          .toList(),
      count: json['count'] as int?,
    );

Map<String, dynamic> _$$_EstimateMusterRollsModelToJson(
        _$_EstimateMusterRollsModel instance) =>
    <String, dynamic>{
      'musterRolls': instance.musterRoll,
      'count': instance.count,
    };

_$_EstimateMusterRoll _$$_EstimateMusterRollFromJson(
        Map<String, dynamic> json) =>
    _$_EstimateMusterRoll(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      musterRollNumber: json['musterRollNumber'] as String?,
      registerId: json['registerId'] as String?,
      status: json['status'] as String?,
      musterRollStatus: json['musterRollStatus'] as String?,
      startDate: json['startDate'] as int?,
      endDate: json['endDate'] as int?,
      individualEntries: (json['individualEntries'] as List<dynamic>?)
          ?.map((e) =>
              EstimateIndividualEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      musterAdditionalDetails: json['additionalDetails'] == null
          ? null
          : MusterAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      musterAuditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_EstimateMusterRollToJson(
        _$_EstimateMusterRoll instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'musterRollNumber': instance.musterRollNumber,
      'registerId': instance.registerId,
      'status': instance.status,
      'musterRollStatus': instance.musterRollStatus,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'individualEntries': instance.individualEntries,
      'additionalDetails': instance.musterAdditionalDetails,
      'auditDetails': instance.musterAuditDetails,
    };

_$_EstimateIndividualEntries _$$_EstimateIndividualEntriesFromJson(
        Map<String, dynamic> json) =>
    _$_EstimateIndividualEntries(
      id: json['id'] as String?,
      individualId: json['individualId'] as String?,
      totalAttendance: (json['totalAttendance'] as num?)?.toDouble(),
      attendanceEntries: (json['attendanceEntries'] as List<dynamic>?)
          ?.map((e) => AttendanceEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      musterIndividualAdditionalDetails: json['additionalDetails'] == null
          ? null
          : EstimateMusterIndividualAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_EstimateIndividualEntriesToJson(
        _$_EstimateIndividualEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
      'individualId': instance.individualId,
      'totalAttendance': instance.totalAttendance,
      'attendanceEntries': instance.attendanceEntries,
      'additionalDetails': instance.musterIndividualAdditionalDetails,
    };

_$_EstimateMusterIndividualAdditionalDetails
    _$$_EstimateMusterIndividualAdditionalDetailsFromJson(
            Map<String, dynamic> json) =>
        _$_EstimateMusterIndividualAdditionalDetails(
          userName: json['userName'] as String?,
          fatherName: json['fatherName'] as String?,
          gender: json['gender'] as String?,
          aadharNumber: json['aadharNumber'] as String?,
          bankDetails: json['bankDetails'] as String?,
          userId: json['userId'] as String?,
          skillCode: (json['skillCode'] as List<dynamic>?)
              ?.map((e) => e as String)
              .toList(),
          accountHolderName: json['accountHolderName'] as String?,
          accountType: json['accountType'] as String?,
          skillValue: json['skillValue'] as String?,
        );

Map<String, dynamic> _$$_EstimateMusterIndividualAdditionalDetailsToJson(
        _$_EstimateMusterIndividualAdditionalDetails instance) =>
    <String, dynamic>{
      'userName': instance.userName,
      'fatherName': instance.fatherName,
      'gender': instance.gender,
      'aadharNumber': instance.aadharNumber,
      'bankDetails': instance.bankDetails,
      'userId': instance.userId,
      'skillCode': instance.skillCode,
      'accountHolderName': instance.accountHolderName,
      'accountType': instance.accountType,
      'skillValue': instance.skillValue,
    };
