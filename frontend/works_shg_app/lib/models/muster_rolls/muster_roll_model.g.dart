// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_roll_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MusterRollsModel _$$_MusterRollsModelFromJson(Map<String, dynamic> json) =>
    _$_MusterRollsModel(
      musterRoll: (json['musterRolls'] as List<dynamic>?)
          ?.map((e) => MusterRoll.fromJson(e as Map<String, dynamic>))
          .toList(),
      count: json['count'] as int?,
    );

Map<String, dynamic> _$$_MusterRollsModelToJson(_$_MusterRollsModel instance) =>
    <String, dynamic>{
      'musterRolls': instance.musterRoll,
      'count': instance.count,
    };

_$_MusterRoll _$$_MusterRollFromJson(Map<String, dynamic> json) =>
    _$_MusterRoll(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      musterRollNumber: json['musterRollNumber'] as String?,
      registerId: json['registerId'] as String?,
      status: json['status'] as String?,
      musterRollStatus: json['musterRollStatus'] as String?,
      serviceCode: json['serviceCode'] as String?,
      referenceId: json['referenceId'] as String?,
      startDate: json['startDate'] as int?,
      endDate: json['endDate'] as int?,
      individualEntries: (json['individualEntries'] as List<dynamic>?)
          ?.map((e) => IndividualEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      musterAdditionalDetails: json['additionalDetails'] == null
          ? null
          : MusterAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      musterAuditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_MusterRollToJson(_$_MusterRoll instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'musterRollNumber': instance.musterRollNumber,
      'registerId': instance.registerId,
      'status': instance.status,
      'musterRollStatus': instance.musterRollStatus,
      'serviceCode': instance.serviceCode,
      'referenceId': instance.referenceId,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'individualEntries': instance.individualEntries,
      'additionalDetails': instance.musterAdditionalDetails,
      'auditDetails': instance.musterAuditDetails,
    };

_$_IndividualEntries _$$_IndividualEntriesFromJson(Map<String, dynamic> json) =>
    _$_IndividualEntries(
      id: json['id'] as String?,
      individualId: json['individualId'] as String?,
      totalAttendance: (json['totalAttendance'] as num?)?.toDouble(),
      attendanceEntries: (json['attendanceEntries'] as List<dynamic>?)
          ?.map((e) => AttendanceEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      musterIndividualAdditionalDetails: json['additionalDetails'] == null
          ? null
          : MusterIndividualAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_IndividualEntriesToJson(
        _$_IndividualEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
      'individualId': instance.individualId,
      'totalAttendance': instance.totalAttendance,
      'attendanceEntries': instance.attendanceEntries,
      'additionalDetails': instance.musterIndividualAdditionalDetails,
    };

_$_MusterAdditionalDetails _$$_MusterAdditionalDetailsFromJson(
        Map<String, dynamic> json) =>
    _$_MusterAdditionalDetails(
      attendanceRegisterName: json['attendanceRegisterName'] as String?,
      attendanceRegisterNo: json['attendanceRegisterNo'] as String?,
      orgName: json['orgName'] as String?,
      amount: json['amount'] as int?,
      assignee: json['assignee'] as String?,
      billType: json['billType'] as String?,
      projectId: json['projectId'] as String?,
      projectName: json['projectName'] as String?,
      projectDesc: json['projectDesc'] as String?,
      contractId: json['contractId'] as String?,
    );

Map<String, dynamic> _$$_MusterAdditionalDetailsToJson(
        _$_MusterAdditionalDetails instance) =>
    <String, dynamic>{
      'attendanceRegisterName': instance.attendanceRegisterName,
      'attendanceRegisterNo': instance.attendanceRegisterNo,
      'orgName': instance.orgName,
      'amount': instance.amount,
      'assignee': instance.assignee,
      'billType': instance.billType,
      'projectId': instance.projectId,
      'projectName': instance.projectName,
      'projectDesc': instance.projectDesc,
      'contractId': instance.contractId,
    };

_$_MusterIndividualAdditionalDetails
    _$$_MusterIndividualAdditionalDetailsFromJson(Map<String, dynamic> json) =>
        _$_MusterIndividualAdditionalDetails(
          userName: json['userName'] as String?,
          fatherName: json['fatherName'] as String?,
          gender: json['gender'] as String?,
          aadharNumber: json['aadharNumber'] as String?,
          bankDetails: json['bankDetails'] as String?,
          userId: json['userId'] as String?,
          accountHolderName: json['accountHolderName'] as String?,
          accountType: json['accountType'] as String?,
          skillCode: json['skillCode'] as String?,
          skillValue: json['skillValue'] as String?,
        );

Map<String, dynamic> _$$_MusterIndividualAdditionalDetailsToJson(
        _$_MusterIndividualAdditionalDetails instance) =>
    <String, dynamic>{
      'userName': instance.userName,
      'fatherName': instance.fatherName,
      'gender': instance.gender,
      'aadharNumber': instance.aadharNumber,
      'bankDetails': instance.bankDetails,
      'userId': instance.userId,
      'accountHolderName': instance.accountHolderName,
      'accountType': instance.accountType,
      'skillCode': instance.skillCode,
      'skillValue': instance.skillValue,
    };

_$_AttendanceEntries _$$_AttendanceEntriesFromJson(Map<String, dynamic> json) =>
    _$_AttendanceEntries(
      id: json['id'] as String?,
      attendance: (json['attendance'] as num?)?.toDouble(),
      time: json['time'] as int?,
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      attendanceEntriesAdditionalDetails: json['additionalDetails'] == null
          ? null
          : AttendanceEntriesAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_AttendanceEntriesToJson(
        _$_AttendanceEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
      'attendance': instance.attendance,
      'time': instance.time,
      'auditDetails': instance.auditDetails,
      'additionalDetails': instance.attendanceEntriesAdditionalDetails,
    };

_$_AttendanceEntriesAdditionalDetails
    _$$_AttendanceEntriesAdditionalDetailsFromJson(Map<String, dynamic> json) =>
        _$_AttendanceEntriesAdditionalDetails(
          entryAttendanceLogId: json['entryAttendanceLogId'] as String?,
          exitAttendanceLogId: json['exitAttendanceLogId'] as String?,
        );

Map<String, dynamic> _$$_AttendanceEntriesAdditionalDetailsToJson(
        _$_AttendanceEntriesAdditionalDetails instance) =>
    <String, dynamic>{
      'entryAttendanceLogId': instance.entryAttendanceLogId,
      'exitAttendanceLogId': instance.exitAttendanceLogId,
    };

_$_AuditDetails _$$_AuditDetailsFromJson(Map<String, dynamic> json) =>
    _$_AuditDetails(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: json['createdTime'] as int?,
      lastModifiedTime: json['lastModifiedTime'] as int?,
    );

Map<String, dynamic> _$$_AuditDetailsToJson(_$_AuditDetails instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };
