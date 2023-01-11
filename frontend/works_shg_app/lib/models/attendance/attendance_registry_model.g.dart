// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_registry_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AttendenceRegistersModel _$$_AttendenceRegistersModelFromJson(
        Map<String, dynamic> json) =>
    _$_AttendenceRegistersModel(
      attendenceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendenceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendenceRegistersModelToJson(
        _$_AttendenceRegistersModel instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendenceRegister,
    };

_$_AttendenceRegister _$$_AttendenceRegisterFromJson(
        Map<String, dynamic> json) =>
    _$_AttendenceRegister(
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      registerNumber: json['registerNumber'] as String?,
      name: json['name'] as String?,
      startDate: json['startDate'] as int?,
      endDate: json['endDate'] as int?,
      staffEntries: (json['staff'] as List<dynamic>?)
          ?.map((e) => StaffEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
      attendeesEntries: (json['attendees'] as List<dynamic>?)
          ?.map((e) => AttendeesEntries.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendenceRegisterToJson(
        _$_AttendenceRegister instance) =>
    <String, dynamic>{
      'id': instance.id,
      'tenantId': instance.tenantId,
      'registerNumber': instance.registerNumber,
      'name': instance.name,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'staff': instance.staffEntries,
      'attendees': instance.attendeesEntries,
    };

_$_StaffEntries _$$_StaffEntriesFromJson(Map<String, dynamic> json) =>
    _$_StaffEntries(
      id: json['id'] as String?,
    );

Map<String, dynamic> _$$_StaffEntriesToJson(_$_StaffEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
    };

_$_AttendeesEntries _$$_AttendeesEntriesFromJson(Map<String, dynamic> json) =>
    _$_AttendeesEntries(
      id: json['id'] as String?,
    );

Map<String, dynamic> _$$_AttendeesEntriesToJson(_$_AttendeesEntries instance) =>
    <String, dynamic>{
      'id': instance.id,
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
