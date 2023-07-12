import 'package:freezed_annotation/freezed_annotation.dart';

part 'app_config_model.freezed.dart';
part 'app_config_model.g.dart';

@freezed
class AppConfigModel with _$AppConfigModel {
  const factory AppConfigModel({
    required String tenantId,
    required String moduleName,
    ConfigurationModel? configuration,
  }) = _AppConfigModel;

  factory AppConfigModel.fromJson(Map<String, dynamic> json) =>
      _$AppConfigModelFromJson(json);
}

@freezed
class ConfigurationModel with _$ConfigurationModel {
  const factory ConfigurationModel({
    required int configVersion,
    required AppConfig appConfig,
  }) = _ConfigurationModel;

  factory ConfigurationModel.fromJson(Map<String, dynamic> json) =>
      _$ConfigurationModelFromJson(json);
}

@freezed
class AppConfig with _$AppConfig {
  factory AppConfig({
    @JsonKey(name: 'LANGUAGES') required List<Languages> languages,
    @JsonKey(name: 'LOCALIZATION_MODULES')
        required List<LocalizationModules>? localizationModules,
  }) = _AppConfig;

  factory AppConfig.fromJson(Map<String, dynamic> json) =>
      _$AppConfigFromJson(json);
}

@freezed
class LanguagesList with _$LanguagesList {
  factory LanguagesList({
    @JsonKey(name: 'languages') required List<Languages>? languages,
  }) = _LanguagesList;

  factory LanguagesList.fromJson(Map<String, dynamic> json) =>
      _$LanguagesListFromJson(json);
}

@freezed
class Languages with _$Languages {
  factory Languages({
    required String label,
    required String value,
    @Default(false) bool isSelected,
  }) = _Languages;

  factory Languages.fromJson(Map<String, dynamic> json) =>
      _$LanguagesFromJson(json);
}

@freezed
class LocalizationModules with _$LocalizationModules {
  factory LocalizationModules({required String label, required String value}) =
      _LocalizationModules;

  factory LocalizationModules.fromJson(Map<String, dynamic> json) =>
      _$LocalizationModulesFromJson(json);
}
