// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_inbox_status.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MusterInboxStatusListImpl _$$MusterInboxStatusListImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterInboxStatusListImpl(
      musterInboxStatus: (json['CBOMusterInboxConfig'] as List<dynamic>?)
          ?.map((e) => MusterInboxStatus.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MusterInboxStatusListImplToJson(
        _$MusterInboxStatusListImpl instance) =>
    <String, dynamic>{
      'CBOMusterInboxConfig': instance.musterInboxStatus,
    };

_$MusterInboxStatusImpl _$$MusterInboxStatusImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterInboxStatusImpl(
      reSubmitCode: json['reSubmitCode'] as String,
    );

Map<String, dynamic> _$$MusterInboxStatusImplToJson(
        _$MusterInboxStatusImpl instance) =>
    <String, dynamic>{
      'reSubmitCode': instance.reSubmitCode,
    };
