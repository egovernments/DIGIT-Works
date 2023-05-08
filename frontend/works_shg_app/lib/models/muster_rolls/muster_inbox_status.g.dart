// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_inbox_status.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MusterInboxStatusList _$$_MusterInboxStatusListFromJson(
        Map<String, dynamic> json) =>
    _$_MusterInboxStatusList(
      musterInboxStatus: (json['CBOMusterInboxConfig'] as List<dynamic>?)
          ?.map((e) => MusterInboxStatus.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_MusterInboxStatusListToJson(
        _$_MusterInboxStatusList instance) =>
    <String, dynamic>{
      'CBOMusterInboxConfig': instance.musterInboxStatus,
    };

_$_MusterInboxStatus _$$_MusterInboxStatusFromJson(Map<String, dynamic> json) =>
    _$_MusterInboxStatus(
      reSubmitCode: json['reSubmitCode'] as String,
    );

Map<String, dynamic> _$$_MusterInboxStatusToJson(
        _$_MusterInboxStatus instance) =>
    <String, dynamic>{
      'reSubmitCode': instance.reSubmitCode,
    };
