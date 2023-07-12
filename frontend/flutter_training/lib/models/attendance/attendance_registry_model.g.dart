// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_registry_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AttendanceRegistersModel _$$_AttendanceRegistersModelFromJson(
        Map<String, dynamic> json) =>
    _$_AttendanceRegistersModel(
      attendanceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendanceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendanceRegistersModelToJson(
        _$_AttendanceRegistersModel instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendanceRegister,
    };

_$_AttendanceRegister _$$_AttendanceRegisterFromJson(
        Map<String, dynamic> json) =>
    _$_AttendanceRegister(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      registerNumber: json['registerNumber'] as String?,
      serviceCode: json['serviceCode'] as String?,
      referenceId: json['referenceId'] as String?,
      name: json['name'] as String?,
      startDate: json['startDate'] as int?,
      endDate: json['endDate'] as int?,
      status: json['status'] as String?,
      attendanceRegisterAdditionalDetails: json['additionalDetails'] == null
          ? null
          : AttendanceRegisterAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      staffEntries: (json['staff'] as List<dynamic>?)
          ?.map((e) => StaffEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      registerAuditDetails: json['auditDetails'] == null
          ? null
          : RegisterAuditDetails.fromJson(
              json['auditDetails'] as Map<String, dynamic>),
      attendeesEntries: (json['attendees'] as List<dynamic>?)
          ?.map((e) => AttendeesEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendanceRegisterToJson(
        _$_AttendanceRegister instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'registerNumber': instance.registerNumber,
      'serviceCode': instance.serviceCode,
      'referenceId': instance.referenceId,
      'name': instance.name,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'status': instance.status,
      'additionalDetails': instance.attendanceRegisterAdditionalDetails,
      'staff': instance.staffEntries,
      'auditDetails': instance.registerAuditDetails,
      'attendees': instance.attendeesEntries,
    };

_$_AttendanceRegisterAdditionalDetails
    _$$_AttendanceRegisterAdditionalDetailsFromJson(
            Map<String, dynamic> json) =>
        _$_AttendanceRegisterAdditionalDetails(
          contractId: json['contractId'] as String?,
          orgName: json['orgName'] as String?,
          officerInCharge: json['officerInCharge'] as String?,
          executingAuthority: json['executingAuthority'] as String?,
          projectId: json['projectId'] as String?,
          projectName: json['projectName'] as String?,
          projectType: json['projectType'] as String?,
          projectDesc: json['projectDesc'] as String?,
          locality: json['locality'] as String?,
          ward: json['ward'] as String?,
          amount: json['amount'] as int?,
        );

Map<String, dynamic> _$$_AttendanceRegisterAdditionalDetailsToJson(
        _$_AttendanceRegisterAdditionalDetails instance) =>
    <String, dynamic>{
      'contractId': instance.contractId,
      'orgName': instance.orgName,
      'officerInCharge': instance.officerInCharge,
      'executingAuthority': instance.executingAuthority,
      'projectId': instance.projectId,
      'projectName': instance.projectName,
      'projectType': instance.projectType,
      'projectDesc': instance.projectDesc,
      'locality': instance.locality,
      'ward': instance.ward,
      'amount': instance.amount,
    };

_$_RegisterAuditDetails _$$_RegisterAuditDetailsFromJson(
        Map<String, dynamic> json) =>
    _$_RegisterAuditDetails(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: json['createdTime'] as int?,
      lastModifiedTime: json['lastModifiedTime'] as int?,
    );

Map<String, dynamic> _$$_RegisterAuditDetailsToJson(
        _$_RegisterAuditDetails instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };

_$_StaffEntries _$$_StaffEntriesFromJson(Map<String, dynamic> json) =>
    _$_StaffEntries(
      id: json['id'] as String?,
      userId: json['userId'] as String?,
      registerId: json['registerId'] as String?,
      enrollmentDate: json['enrollmentDate'] as int?,
    );

Map<String, dynamic> _$$_StaffEntriesToJson(_$_StaffEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userId': instance.userId,
      'registerId': instance.registerId,
      'enrollmentDate': instance.enrollmentDate,
    };

_$_AttendeesEntries _$$_AttendeesEntriesFromJson(Map<String, dynamic> json) =>
    _$_AttendeesEntries(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      registerId: json['registerId'] as String?,
      individualId: json['individualId'] as String?,
      enrollmentDate: json['enrollmentDate'] as int?,
      denrollmentDate: json['denrollmentDate'] as int?,
      additionalDetails: json['additionalDetails'] == null
          ? null
          : AttendeesAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_AttendeesEntriesToJson(_$_AttendeesEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'registerId': instance.registerId,
      'individualId': instance.individualId,
      'enrollmentDate': instance.enrollmentDate,
      'denrollmentDate': instance.denrollmentDate,
      'additionalDetails': instance.additionalDetails,
    };

_$_AttendeesAdditionalDetails _$$_AttendeesAdditionalDetailsFromJson(
        Map<String, dynamic> json) =>
    _$_AttendeesAdditionalDetails(
      individualName: json['individualName'] as String?,
      gender: json['gender'] as String?,
      individualGaurdianName: json['individualGaurdianName'] as String?,
      individualID: json['individualID'] as String?,
      identifierId: json['identifierId'] as String?,
      bankNumber: json['bankNumber'] as String?,
    );

Map<String, dynamic> _$$_AttendeesAdditionalDetailsToJson(
        _$_AttendeesAdditionalDetails instance) =>
    <String, dynamic>{
      'individualName': instance.individualName,
      'gender': instance.gender,
      'individualGaurdianName': instance.individualGaurdianName,
      'individualID': instance.individualID,
      'identifierId': instance.identifierId,
      'bankNumber': instance.bankNumber,
    };
