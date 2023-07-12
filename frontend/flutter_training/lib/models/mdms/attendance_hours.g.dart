// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_hours.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AttendanceHoursList _$$_AttendanceHoursListFromJson(
        Map<String, dynamic> json) =>
    _$_AttendanceHoursList(
      attendanceHours: (json['AttendanceHours'] as List<dynamic>?)
          ?.map((e) => AttendanceHours.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendanceHoursListToJson(
        _$_AttendanceHoursList instance) =>
    <String, dynamic>{
      'AttendanceHours': instance.attendanceHours,
    };

_$_AttendanceHours _$$_AttendanceHoursFromJson(Map<String, dynamic> json) =>
    _$_AttendanceHours(
      code: json['code'] as String,
      value: json['value'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_AttendanceHoursToJson(_$_AttendanceHours instance) =>
    <String, dynamic>{
      'code': instance.code,
      'value': instance.value,
      'active': instance.active,
    };
