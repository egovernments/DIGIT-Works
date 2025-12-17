// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_submission.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MusterSubmissionListImpl _$$MusterSubmissionListImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterSubmissionListImpl(
      musterSubmission: (json['CBOMusterSubmission'] as List<dynamic>?)
          ?.map((e) => MusterSubmission.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MusterSubmissionListImplToJson(
        _$MusterSubmissionListImpl instance) =>
    <String, dynamic>{
      'CBOMusterSubmission': instance.musterSubmission,
    };

_$MusterSubmissionImpl _$$MusterSubmissionImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterSubmissionImpl(
      code: json['code'] as String,
      value: json['value'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$MusterSubmissionImplToJson(
        _$MusterSubmissionImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'value': instance.value,
      'active': instance.active,
    };
