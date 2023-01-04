// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_request.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

AttendanceRequest _$AttendanceRequestFromJson(Map<String, dynamic> json) =>
    AttendanceRequest()
      ..tenantId = json['tenantId'] as String?
      ..registerNumber = json['registerNumber'] as String?
      ..name = json['name'] as String?;

Map<String, dynamic> _$AttendanceRequestToJson(AttendanceRequest instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'registerNumber': instance.registerNumber,
      'name': instance.name,
    };
