// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'global_config_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_GlobalConfigModel _$$_GlobalConfigModelFromJson(Map<String, dynamic> json) =>
    _$_GlobalConfigModel(
      globalConfigs: json['globalConfigs'] == null
          ? null
          : GlobalConfigs.fromJson(
              json['globalConfigs'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_GlobalConfigModelToJson(
        _$_GlobalConfigModel instance) =>
    <String, dynamic>{
      'globalConfigs': instance.globalConfigs,
    };

_$_GlobalConfigs _$$_GlobalConfigsFromJson(Map<String, dynamic> json) =>
    _$_GlobalConfigs(
      stateTenantId: json['stateTenantId'] as String,
      gmaps_api_key: json['gmaps_api_key'] as String?,
      finEnv: json['finEnv'] as String?,
      contextPath: json['contextPath'] as String?,
      footerLogoURL: json['footerLogoURL'] as String?,
      footerBWLogoURL: json['footerBWLogoURL'] as String?,
      centralInstanceEnabled: json['centralInstanceEnabled'] as bool?,
      assetS3Bucket: json['assetS3Bucket'] as String?,
    );

Map<String, dynamic> _$$_GlobalConfigsToJson(_$_GlobalConfigs instance) =>
    <String, dynamic>{
      'stateTenantId': instance.stateTenantId,
      'gmaps_api_key': instance.gmaps_api_key,
      'finEnv': instance.finEnv,
      'contextPath': instance.contextPath,
      'footerLogoURL': instance.footerLogoURL,
      'footerBWLogoURL': instance.footerBWLogoURL,
      'centralInstanceEnabled': instance.centralInstanceEnabled,
      'assetS3Bucket': instance.assetS3Bucket,
    };
