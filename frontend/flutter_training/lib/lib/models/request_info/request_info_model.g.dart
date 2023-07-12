// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'request_info_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_RequestInfoMapperModel _$$_RequestInfoMapperModelFromJson(
        Map<String, dynamic> json) =>
    _$_RequestInfoMapperModel(
      requestInfo: json['requestInfo'] == null
          ? null
          : RequestInfoModel.fromJson(
              json['requestInfo'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_RequestInfoMapperModelToJson(
        _$_RequestInfoMapperModel instance) =>
    <String, dynamic>{
      'requestInfo': instance.requestInfo,
    };

_$_RequestInfoModel _$$_RequestInfoModelFromJson(Map<String, dynamic> json) =>
    _$_RequestInfoModel(
      apiId: json['apiId'] as String?,
      ver: json['ver'] as String?,
      ts: json['ts'] as String?,
      action: json['action'] as String?,
      did: json['did'] as String?,
      key: json['key'] as String?,
      msgId: json['msgId'] as String?,
      authToken: json['authToken'] as String?,
    );

Map<String, dynamic> _$$_RequestInfoModelToJson(_$_RequestInfoModel instance) =>
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
