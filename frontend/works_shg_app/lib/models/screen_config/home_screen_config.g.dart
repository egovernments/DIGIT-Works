// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'home_screen_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$HomeScreenConfigModelImpl _$$HomeScreenConfigModelImplFromJson(
        Map<String, dynamic> json) =>
    _$HomeScreenConfigModelImpl(
      commonUiConfig: json['commonUiConfig'] == null
          ? null
          : CommonUIConfigModel.fromJson(
              json['commonUiConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$HomeScreenConfigModelImplToJson(
        _$HomeScreenConfigModelImpl instance) =>
    <String, dynamic>{
      'commonUiConfig': instance.commonUiConfig,
    };

_$CommonUIConfigModelImpl _$$CommonUIConfigModelImplFromJson(
        Map<String, dynamic> json) =>
    _$CommonUIConfigModelImpl(
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

Map<String, dynamic> _$$CommonUIConfigModelImplToJson(
        _$CommonUIConfigModelImpl instance) =>
    <String, dynamic>{
      'CBOHomeScreenConfig': instance.cboHomeScreenConfig,
      'CBOMyWorks': instance.cboMyWorksSearchCriteria,
      'CBOMyServiceRequests': instance.cboMyServiceRequestsConfig,
    };

_$CBOHomeScreenConfigModelImpl _$$CBOHomeScreenConfigModelImplFromJson(
        Map<String, dynamic> json) =>
    _$CBOHomeScreenConfigModelImpl(
      order: (json['order'] as num).toInt(),
      key: json['key'] as String,
      displayName: json['displayName'] as String?,
      label: json['label'] as String?,
      active: json['active'] as bool?,
    );

Map<String, dynamic> _$$CBOHomeScreenConfigModelImplToJson(
        _$CBOHomeScreenConfigModelImpl instance) =>
    <String, dynamic>{
      'order': instance.order,
      'key': instance.key,
      'displayName': instance.displayName,
      'label': instance.label,
      'active': instance.active,
    };
