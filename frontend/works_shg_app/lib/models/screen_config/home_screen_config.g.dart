// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'home_screen_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_HomeScreenConfigModel _$$_HomeScreenConfigModelFromJson(
        Map<String, dynamic> json) =>
    _$_HomeScreenConfigModel(
      commonUiConfig: json['commonUiConfig'] == null
          ? null
          : CommonUIConfigModel.fromJson(
              json['commonUiConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_HomeScreenConfigModelToJson(
        _$_HomeScreenConfigModel instance) =>
    <String, dynamic>{
      'commonUiConfig': instance.commonUiConfig,
    };

_$_CommonUIConfigModel _$$_CommonUIConfigModelFromJson(
        Map<String, dynamic> json) =>
    _$_CommonUIConfigModel(
      cboHomeScreenConfig: (json['CBOHomeScreenConfig'] as List<dynamic>?)
          ?.map((e) =>
              CBOHomeScreenConfigModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      cboMyWorksSearchCriteria: (json['CBOMyWorks'] as List<dynamic>?)
          ?.map((e) =>
              CBOMyWorksSearchCriteriaModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      cboMyServiceRequestsConfig: (json['CBOMyServiceRequests']
              as List<dynamic>?)
          ?.map((e) =>
              CBOMyServiceRequestsConfig.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_CommonUIConfigModelToJson(
        _$_CommonUIConfigModel instance) =>
    <String, dynamic>{
      'CBOHomeScreenConfig': instance.cboHomeScreenConfig,
      'CBOMyWorks': instance.cboMyWorksSearchCriteria,
      'CBOMyServiceRequests': instance.cboMyServiceRequestsConfig,
    };

_$_CBOHomeScreenConfigModel _$$_CBOHomeScreenConfigModelFromJson(
        Map<String, dynamic> json) =>
    _$_CBOHomeScreenConfigModel(
      order: json['order'] as int,
      key: json['key'] as String,
      displayName: json['displayName'] as String?,
      label: json['label'] as String?,
      active: json['active'] as bool?,
    );

Map<String, dynamic> _$$_CBOHomeScreenConfigModelToJson(
        _$_CBOHomeScreenConfigModel instance) =>
    <String, dynamic>{
      'order': instance.order,
      'key': instance.key,
      'displayName': instance.displayName,
      'label': instance.label,
      'active': instance.active,
    };
