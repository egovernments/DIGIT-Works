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
      homeScreenCardConfig: (json['HomeScreenCardConfig'] as List<dynamic>?)
          ?.map((e) =>
              HomeScreenCardConfigModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_CommonUIConfigModelToJson(
        _$_CommonUIConfigModel instance) =>
    <String, dynamic>{
      'HomeScreenCardConfig': instance.homeScreenCardConfig,
    };

_$_HomeScreenCardConfigModel _$$_HomeScreenCardConfigModelFromJson(
        Map<String, dynamic> json) =>
    _$_HomeScreenCardConfigModel(
      order: json['order'] as int,
      header: json['header'] as String,
      displayName: json['displayName'] as String?,
      label: json['label'] as String?,
      icon: json['icon'] as String?,
      links: (json['links'] as List<dynamic>?)
          ?.map((e) => HomeScreenLinksModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      active: json['active'] as bool?,
    );

Map<String, dynamic> _$$_HomeScreenCardConfigModelToJson(
        _$_HomeScreenCardConfigModel instance) =>
    <String, dynamic>{
      'order': instance.order,
      'header': instance.header,
      'displayName': instance.displayName,
      'label': instance.label,
      'icon': instance.icon,
      'links': instance.links,
      'active': instance.active,
    };

_$_HomeScreenLinksModel _$$_HomeScreenLinksModelFromJson(
        Map<String, dynamic> json) =>
    _$_HomeScreenLinksModel(
      order: json['order'] as int,
      key: json['key'] as String,
      displayName: json['displayName'] as String?,
      label: json['label'] as String?,
      active: json['active'] as bool?,
    );

Map<String, dynamic> _$$_HomeScreenLinksModelToJson(
        _$_HomeScreenLinksModel instance) =>
    <String, dynamic>{
      'order': instance.order,
      'key': instance.key,
      'displayName': instance.displayName,
      'label': instance.label,
      'active': instance.active,
    };
