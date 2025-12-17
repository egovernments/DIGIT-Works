// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'request_info_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$RequestInfoMapperModelImpl _$$RequestInfoMapperModelImplFromJson(
        Map<String, dynamic> json) =>
    _$RequestInfoMapperModelImpl(
      requestInfo: json['requestInfo'] == null
          ? null
          : RequestInfoModel.fromJson(
              json['requestInfo'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$RequestInfoMapperModelImplToJson(
        _$RequestInfoMapperModelImpl instance) =>
    <String, dynamic>{
      'requestInfo': instance.requestInfo,
    };

_$RequestInfoModelImpl _$$RequestInfoModelImplFromJson(
        Map<String, dynamic> json) =>
    _$RequestInfoModelImpl(
      apiId: json['apiId'] as String?,
      ver: json['ver'] as String?,
      ts: json['ts'] as String?,
      action: json['action'] as String?,
      did: json['did'] as String?,
      key: json['key'] as String?,
      msgId: json['msgId'] as String?,
      authToken: json['authToken'] as String?,
    );

Map<String, dynamic> _$$RequestInfoModelImplToJson(
        _$RequestInfoModelImpl instance) =>
    <String, dynamic>{
      'apiId': instance.apiId,
      'ver': instance.ver,
      'ts': instance.ts,
      'action': instance.action,
      'did': instance.did,
      'key': instance.key,
      'msgId': instance.msgId,
      'authToken': instance.authToken,
    };
