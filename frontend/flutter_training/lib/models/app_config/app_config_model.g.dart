// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'app_config_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_AppConfigModel _$$_AppConfigModelFromJson(Map<String, dynamic> json) =>
    _$_AppConfigModel(
      tenantId: json['tenantId'] as String,
      moduleName: json['moduleName'] as String,
      configuration: json['configuration'] == null
          ? null
          : ConfigurationModel.fromJson(
              json['configuration'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_AppConfigModelToJson(_$_AppConfigModel instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'moduleName': instance.moduleName,
      'configuration': instance.configuration,
    };

_$_ConfigurationModel _$$_ConfigurationModelFromJson(
        Map<String, dynamic> json) =>
    _$_ConfigurationModel(
      configVersion: json['configVersion'] as int,
      appConfig: AppConfig.fromJson(json['appConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_ConfigurationModelToJson(
        _$_ConfigurationModel instance) =>
    <String, dynamic>{
      'configVersion': instance.configVersion,
      'appConfig': instance.appConfig,
    };

_$_AppConfig _$$_AppConfigFromJson(Map<String, dynamic> json) => _$_AppConfig(
      languages: (json['LANGUAGES'] as List<dynamic>)
          .map((e) => Languages.fromJson(e as Map<String, dynamic>))
          .toList(),
      localizationModules: (json['LOCALIZATION_MODULES'] as List<dynamic>?)
          ?.map((e) => LocalizationModules.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_AppConfigToJson(_$_AppConfig instance) =>
    <String, dynamic>{
      'LANGUAGES': instance.languages,
      'LOCALIZATION_MODULES': instance.localizationModules,
    };

_$_LanguagesList _$$_LanguagesListFromJson(Map<String, dynamic> json) =>
    _$_LanguagesList(
      languages: (json['languages'] as List<dynamic>?)
          ?.map((e) => Languages.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_LanguagesListToJson(_$_LanguagesList instance) =>
    <String, dynamic>{
      'languages': instance.languages,
    };

_$_Languages _$$_LanguagesFromJson(Map<String, dynamic> json) => _$_Languages(
      label: json['label'] as String,
      value: json['value'] as String,
      isSelected: json['isSelected'] as bool? ?? false,
    );

Map<String, dynamic> _$$_LanguagesToJson(_$_Languages instance) =>
    <String, dynamic>{
      'label': instance.label,
      'value': instance.value,
      'isSelected': instance.isSelected,
    };

_$_LocalizationModules _$$_LocalizationModulesFromJson(
        Map<String, dynamic> json) =>
    _$_LocalizationModules(
      label: json['label'] as String,
      value: json['value'] as String,
    );

Map<String, dynamic> _$$_LocalizationModulesToJson(
        _$_LocalizationModules instance) =>
    <String, dynamic>{
      'label': instance.label,
      'value': instance.value,
    };
