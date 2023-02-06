// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attendee_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AttendeeModel _$$_AttendeeModelFromJson(Map<String, dynamic> json) =>
    _$_AttendeeModel(
      attendanceRegister: (json['attendanceRegister'] as List<dynamic>?)
          ?.map((e) => AttendanceRegister.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AttendeeModelToJson(_$_AttendeeModel instance) =>
    <String, dynamic>{
      'attendanceRegister': instance.attendanceRegister,
    };
