// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_registry_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AttendanceRegistersModelImpl _$$AttendanceRegistersModelImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendanceRegistersModelImpl(
      attendanceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendanceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AttendanceRegistersModelImplToJson(
        _$AttendanceRegistersModelImpl instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendanceRegister,
    };

_$AttendanceRegisterImpl _$$AttendanceRegisterImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendanceRegisterImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      registerNumber: json['registerNumber'] as String?,
      serviceCode: json['serviceCode'] as String?,
      referenceId: json['referenceId'] as String?,
      name: json['name'] as String?,
      startDate: (json['startDate'] as num?)?.toInt(),
      endDate: (json['endDate'] as num?)?.toInt(),
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

Map<String, dynamic> _$$AttendanceRegisterImplToJson(
        _$AttendanceRegisterImpl instance) =>
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

_$AttendanceRegisterAdditionalDetailsImpl
    _$$AttendanceRegisterAdditionalDetailsImplFromJson(
            Map<String, dynamic> json) =>
        _$AttendanceRegisterAdditionalDetailsImpl(
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
          amount: (json['amount'] as num?)?.toInt(),
        );

Map<String, dynamic> _$$AttendanceRegisterAdditionalDetailsImplToJson(
        _$AttendanceRegisterAdditionalDetailsImpl instance) =>
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

_$RegisterAuditDetailsImpl _$$RegisterAuditDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$RegisterAuditDetailsImpl(
      createdBy: json['createdBy'] as String?,
      lastModifiedBy: json['lastModifiedBy'] as String?,
      createdTime: (json['createdTime'] as num?)?.toInt(),
      lastModifiedTime: (json['lastModifiedTime'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$RegisterAuditDetailsImplToJson(
        _$RegisterAuditDetailsImpl instance) =>
    <String, dynamic>{
      'createdBy': instance.createdBy,
      'lastModifiedBy': instance.lastModifiedBy,
      'createdTime': instance.createdTime,
      'lastModifiedTime': instance.lastModifiedTime,
    };

_$StaffEntriesImpl _$$StaffEntriesImplFromJson(Map<String, dynamic> json) =>
    _$StaffEntriesImpl(
      id: json['id'] as String?,
      userId: json['userId'] as String?,
      registerId: json['registerId'] as String?,
      enrollmentDate: (json['enrollmentDate'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$StaffEntriesImplToJson(_$StaffEntriesImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userId': instance.userId,
      'registerId': instance.registerId,
      'enrollmentDate': instance.enrollmentDate,
    };

_$AttendeesEntriesImpl _$$AttendeesEntriesImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendeesEntriesImpl(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String,
      registerId: json['registerId'] as String?,
      individualId: json['individualId'] as String?,
      enrollmentDate: (json['enrollmentDate'] as num?)?.toInt(),
      denrollmentDate: (json['denrollmentDate'] as num?)?.toInt(),
      additionalDetails: json['additionalDetails'] == null
          ? null
          : AttendeesAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$AttendeesEntriesImplToJson(
        _$AttendeesEntriesImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'registerId': instance.registerId,
      'individualId': instance.individualId,
      'enrollmentDate': instance.enrollmentDate,
      'denrollmentDate': instance.denrollmentDate,
      'additionalDetails': instance.additionalDetails,
    };

_$AttendeesAdditionalDetailsImpl _$$AttendeesAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendeesAdditionalDetailsImpl(
      individualName: json['individualName'] as String?,
      gender: json['gender'] as String?,
      individualGaurdianName: json['individualGaurdianName'] as String?,
      individualID: json['individualID'] as String?,
      identifierId: json['identifierId'] as String?,
      bankNumber: json['bankNumber'] as String?,
    );

Map<String, dynamic> _$$AttendeesAdditionalDetailsImplToJson(
        _$AttendeesAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'individualName': instance.individualName,
      'gender': instance.gender,
      'individualGaurdianName': instance.individualGaurdianName,
      'individualID': instance.individualID,
      'identifierId': instance.identifierId,
      'bankNumber': instance.bankNumber,
    };
