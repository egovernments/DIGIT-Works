// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendance_hours.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AttendanceHoursListImpl _$$AttendanceHoursListImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendanceHoursListImpl(
      attendanceHours: (json['AttendanceHours'] as List<dynamic>?)
          ?.map((e) => AttendanceHours.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AttendanceHoursListImplToJson(
        _$AttendanceHoursListImpl instance) =>
    <String, dynamic>{
      'AttendanceHours': instance.attendanceHours,
    };

_$AttendanceHoursImpl _$$AttendanceHoursImplFromJson(
        Map<String, dynamic> json) =>
    _$AttendanceHoursImpl(
      code: json['code'] as String,
      value: json['value'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$AttendanceHoursImplToJson(
        _$AttendanceHoursImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'value': instance.value,
      'active': instance.active,
    };
