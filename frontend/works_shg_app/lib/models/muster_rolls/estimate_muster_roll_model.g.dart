// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'estimate_muster_roll_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$EstimateMusterRollsModelImpl _$$EstimateMusterRollsModelImplFromJson(
        Map<String, dynamic> json) =>
    _$EstimateMusterRollsModelImpl(
      musterRoll: (json['musterRolls'] as List<dynamic>?)
          ?.map((e) => EstimateMusterRoll.fromJson(e as Map<String, dynamic>))
          .toList(),
      count: (json['count'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$EstimateMusterRollsModelImplToJson(
        _$EstimateMusterRollsModelImpl instance) =>
    <String, dynamic>{
      'musterRolls': instance.musterRoll,
      'count': instance.count,
    };

_$EstimateMusterRollImpl _$$EstimateMusterRollImplFromJson(
        Map<String, dynamic> json) =>
    _$EstimateMusterRollImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      musterRollNumber: json['musterRollNumber'] as String?,
      registerId: json['registerId'] as String?,
      status: json['status'] as String?,
      musterRollStatus: json['musterRollStatus'] as String?,
      startDate: (json['startDate'] as num?)?.toInt(),
      endDate: (json['endDate'] as num?)?.toInt(),
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

Map<String, dynamic> _$$EstimateMusterRollImplToJson(
        _$EstimateMusterRollImpl instance) =>
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

_$EstimateIndividualEntriesImpl _$$EstimateIndividualEntriesImplFromJson(
        Map<String, dynamic> json) =>
    _$EstimateIndividualEntriesImpl(
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

Map<String, dynamic> _$$EstimateIndividualEntriesImplToJson(
        _$EstimateIndividualEntriesImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'individualId': instance.individualId,
      'totalAttendance': instance.totalAttendance,
      'attendanceEntries': instance.attendanceEntries,
      'additionalDetails': instance.musterIndividualAdditionalDetails,
    };

_$EstimateMusterIndividualAdditionalDetailsImpl
    _$$EstimateMusterIndividualAdditionalDetailsImplFromJson(
            Map<String, dynamic> json) =>
        _$EstimateMusterIndividualAdditionalDetailsImpl(
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

Map<String, dynamic> _$$EstimateMusterIndividualAdditionalDetailsImplToJson(
        _$EstimateMusterIndividualAdditionalDetailsImpl instance) =>
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
