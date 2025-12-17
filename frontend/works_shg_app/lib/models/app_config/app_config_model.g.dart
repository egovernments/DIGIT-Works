// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'app_config_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$AppConfigModelImpl _$$AppConfigModelImplFromJson(Map<String, dynamic> json) =>
    _$AppConfigModelImpl(
      tenantId: json['tenantId'] as String,
      moduleName: json['moduleName'] as String,
      configuration: json['configuration'] == null
          ? null
          : ConfigurationModel.fromJson(
              json['configuration'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$AppConfigModelImplToJson(
        _$AppConfigModelImpl instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'moduleName': instance.moduleName,
      'configuration': instance.configuration,
    };

_$ConfigurationModelImpl _$$ConfigurationModelImplFromJson(
        Map<String, dynamic> json) =>
    _$ConfigurationModelImpl(
      configVersion: (json['configVersion'] as num).toInt(),
      appConfig: AppConfig.fromJson(json['appConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$ConfigurationModelImplToJson(
        _$ConfigurationModelImpl instance) =>
    <String, dynamic>{
      'configVersion': instance.configVersion,
      'appConfig': instance.appConfig,
    };

_$AppConfigImpl _$$AppConfigImplFromJson(Map<String, dynamic> json) =>
    _$AppConfigImpl(
      languages: (json['LANGUAGES'] as List<dynamic>)
          .map((e) => Languages.fromJson(e as Map<String, dynamic>))
          .toList(),
      localizationModules: (json['LOCALIZATION_MODULES'] as List<dynamic>?)
          ?.map((e) => LocalizationModules.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$AppConfigImplToJson(_$AppConfigImpl instance) =>
    <String, dynamic>{
      'LANGUAGES': instance.languages,
      'LOCALIZATION_MODULES': instance.localizationModules,
    };

_$LanguagesListImpl _$$LanguagesListImplFromJson(Map<String, dynamic> json) =>
    _$LanguagesListImpl(
      languages: (json['languages'] as List<dynamic>?)
          ?.map((e) => Languages.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$LanguagesListImplToJson(_$LanguagesListImpl instance) =>
    <String, dynamic>{
      'languages': instance.languages,
    };

_$LanguagesImpl _$$LanguagesImplFromJson(Map<String, dynamic> json) =>
    _$LanguagesImpl(
      label: json['label'] as String,
      value: json['value'] as String,
      isSelected: json['isSelected'] as bool? ?? false,
    );

Map<String, dynamic> _$$LanguagesImplToJson(_$LanguagesImpl instance) =>
    <String, dynamic>{
      'label': instance.label,
      'value': instance.value,
      'isSelected': instance.isSelected,
    };

_$LocalizationModulesImpl _$$LocalizationModulesImplFromJson(
        Map<String, dynamic> json) =>
    _$LocalizationModulesImpl(
      label: json['label'] as String,
      value: json['value'] as String,
    );

Map<String, dynamic> _$$LocalizationModulesImplToJson(
        _$LocalizationModulesImpl instance) =>
    <String, dynamic>{
      'label': instance.label,
      'value': instance.value,
    };
