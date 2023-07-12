// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_submission.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MusterSubmissionList _$$_MusterSubmissionListFromJson(
        Map<String, dynamic> json) =>
    _$_MusterSubmissionList(
      musterSubmission: (json['CBOMusterSubmission'] as List<dynamic>?)
          ?.map((e) => MusterSubmission.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_MusterSubmissionListToJson(
        _$_MusterSubmissionList instance) =>
    <String, dynamic>{
      'CBOMusterSubmission': instance.musterSubmission,
    };

_$_MusterSubmission _$$_MusterSubmissionFromJson(Map<String, dynamic> json) =>
    _$_MusterSubmission(
      code: json['code'] as String,
      value: json['value'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_MusterSubmissionToJson(_$_MusterSubmission instance) =>
    <String, dynamic>{
      'code': instance.code,
      'value': instance.value,
      'active': instance.active,
    };
