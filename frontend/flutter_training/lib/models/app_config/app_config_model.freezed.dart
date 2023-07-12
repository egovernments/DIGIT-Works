// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'app_config_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

AppConfigModel _$AppConfigModelFromJson(Map<String, dynamic> json) {
  return _AppConfigModel.fromJson(json);
}

/// @nodoc
mixin _$AppConfigModel {
  String get tenantId => throw _privateConstructorUsedError;
  String get moduleName => throw _privateConstructorUsedError;
  ConfigurationModel? get configuration => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AppConfigModelCopyWith<AppConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AppConfigModelCopyWith<$Res> {
  factory $AppConfigModelCopyWith(
          AppConfigModel value, $Res Function(AppConfigModel) then) =
      _$AppConfigModelCopyWithImpl<$Res, AppConfigModel>;
  @useResult
  $Res call(
      {String tenantId, String moduleName, ConfigurationModel? configuration});

  $ConfigurationModelCopyWith<$Res>? get configuration;
}

/// @nodoc
class _$AppConfigModelCopyWithImpl<$Res, $Val extends AppConfigModel>
    implements $AppConfigModelCopyWith<$Res> {
  _$AppConfigModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? moduleName = null,
    Object? configuration = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      moduleName: null == moduleName
          ? _value.moduleName
          : moduleName // ignore: cast_nullable_to_non_nullable
              as String,
      configuration: freezed == configuration
          ? _value.configuration
          : configuration // ignore: cast_nullable_to_non_nullable
              as ConfigurationModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $ConfigurationModelCopyWith<$Res>? get configuration {
    if (_value.configuration == null) {
      return null;
    }

    return $ConfigurationModelCopyWith<$Res>(_value.configuration!, (value) {
      return _then(_value.copyWith(configuration: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AppConfigModelCopyWith<$Res>
    implements $AppConfigModelCopyWith<$Res> {
  factory _$$_AppConfigModelCopyWith(
          _$_AppConfigModel value, $Res Function(_$_AppConfigModel) then) =
      __$$_AppConfigModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId, String moduleName, ConfigurationModel? configuration});

  @override
  $ConfigurationModelCopyWith<$Res>? get configuration;
}

/// @nodoc
class __$$_AppConfigModelCopyWithImpl<$Res>
    extends _$AppConfigModelCopyWithImpl<$Res, _$_AppConfigModel>
    implements _$$_AppConfigModelCopyWith<$Res> {
  __$$_AppConfigModelCopyWithImpl(
      _$_AppConfigModel _value, $Res Function(_$_AppConfigModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? moduleName = null,
    Object? configuration = freezed,
  }) {
    return _then(_$_AppConfigModel(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      moduleName: null == moduleName
          ? _value.moduleName
          : moduleName // ignore: cast_nullable_to_non_nullable
              as String,
      configuration: freezed == configuration
          ? _value.configuration
          : configuration // ignore: cast_nullable_to_non_nullable
              as ConfigurationModel?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AppConfigModel implements _AppConfigModel {
  const _$_AppConfigModel(
      {required this.tenantId, required this.moduleName, this.configuration});

  factory _$_AppConfigModel.fromJson(Map<String, dynamic> json) =>
      _$$_AppConfigModelFromJson(json);

  @override
  final String tenantId;
  @override
  final String moduleName;
  @override
  final ConfigurationModel? configuration;

  @override
  String toString() {
    return 'AppConfigModel(tenantId: $tenantId, moduleName: $moduleName, configuration: $configuration)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AppConfigModel &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.moduleName, moduleName) ||
                other.moduleName == moduleName) &&
            (identical(other.configuration, configuration) ||
                other.configuration == configuration));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, tenantId, moduleName, configuration);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AppConfigModelCopyWith<_$_AppConfigModel> get copyWith =>
      __$$_AppConfigModelCopyWithImpl<_$_AppConfigModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AppConfigModelToJson(
      this,
    );
  }
}

abstract class _AppConfigModel implements AppConfigModel {
  const factory _AppConfigModel(
      {required final String tenantId,
      required final String moduleName,
      final ConfigurationModel? configuration}) = _$_AppConfigModel;

  factory _AppConfigModel.fromJson(Map<String, dynamic> json) =
      _$_AppConfigModel.fromJson;

  @override
  String get tenantId;
  @override
  String get moduleName;
  @override
  ConfigurationModel? get configuration;
  @override
  @JsonKey(ignore: true)
  _$$_AppConfigModelCopyWith<_$_AppConfigModel> get copyWith =>
      throw _privateConstructorUsedError;
}

ConfigurationModel _$ConfigurationModelFromJson(Map<String, dynamic> json) {
  return _ConfigurationModel.fromJson(json);
}

/// @nodoc
mixin _$ConfigurationModel {
  int get configVersion => throw _privateConstructorUsedError;
  AppConfig get appConfig => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $ConfigurationModelCopyWith<ConfigurationModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ConfigurationModelCopyWith<$Res> {
  factory $ConfigurationModelCopyWith(
          ConfigurationModel value, $Res Function(ConfigurationModel) then) =
      _$ConfigurationModelCopyWithImpl<$Res, ConfigurationModel>;
  @useResult
  $Res call({int configVersion, AppConfig appConfig});

  $AppConfigCopyWith<$Res> get appConfig;
}

/// @nodoc
class _$ConfigurationModelCopyWithImpl<$Res, $Val extends ConfigurationModel>
    implements $ConfigurationModelCopyWith<$Res> {
  _$ConfigurationModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? configVersion = null,
    Object? appConfig = null,
  }) {
    return _then(_value.copyWith(
      configVersion: null == configVersion
          ? _value.configVersion
          : configVersion // ignore: cast_nullable_to_non_nullable
              as int,
      appConfig: null == appConfig
          ? _value.appConfig
          : appConfig // ignore: cast_nullable_to_non_nullable
              as AppConfig,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AppConfigCopyWith<$Res> get appConfig {
    return $AppConfigCopyWith<$Res>(_value.appConfig, (value) {
      return _then(_value.copyWith(appConfig: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_ConfigurationModelCopyWith<$Res>
    implements $ConfigurationModelCopyWith<$Res> {
  factory _$$_ConfigurationModelCopyWith(_$_ConfigurationModel value,
          $Res Function(_$_ConfigurationModel) then) =
      __$$_ConfigurationModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int configVersion, AppConfig appConfig});

  @override
  $AppConfigCopyWith<$Res> get appConfig;
}

/// @nodoc
class __$$_ConfigurationModelCopyWithImpl<$Res>
    extends _$ConfigurationModelCopyWithImpl<$Res, _$_ConfigurationModel>
    implements _$$_ConfigurationModelCopyWith<$Res> {
  __$$_ConfigurationModelCopyWithImpl(
      _$_ConfigurationModel _value, $Res Function(_$_ConfigurationModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? configVersion = null,
    Object? appConfig = null,
  }) {
    return _then(_$_ConfigurationModel(
      configVersion: null == configVersion
          ? _value.configVersion
          : configVersion // ignore: cast_nullable_to_non_nullable
              as int,
      appConfig: null == appConfig
          ? _value.appConfig
          : appConfig // ignore: cast_nullable_to_non_nullable
              as AppConfig,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_ConfigurationModel implements _ConfigurationModel {
  const _$_ConfigurationModel(
      {required this.configVersion, required this.appConfig});

  factory _$_ConfigurationModel.fromJson(Map<String, dynamic> json) =>
      _$$_ConfigurationModelFromJson(json);

  @override
  final int configVersion;
  @override
  final AppConfig appConfig;

  @override
  String toString() {
    return 'ConfigurationModel(configVersion: $configVersion, appConfig: $appConfig)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ConfigurationModel &&
            (identical(other.configVersion, configVersion) ||
                other.configVersion == configVersion) &&
            (identical(other.appConfig, appConfig) ||
                other.appConfig == appConfig));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, configVersion, appConfig);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ConfigurationModelCopyWith<_$_ConfigurationModel> get copyWith =>
      __$$_ConfigurationModelCopyWithImpl<_$_ConfigurationModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_ConfigurationModelToJson(
      this,
    );
  }
}

abstract class _ConfigurationModel implements ConfigurationModel {
  const factory _ConfigurationModel(
      {required final int configVersion,
      required final AppConfig appConfig}) = _$_ConfigurationModel;

  factory _ConfigurationModel.fromJson(Map<String, dynamic> json) =
      _$_ConfigurationModel.fromJson;

  @override
  int get configVersion;
  @override
  AppConfig get appConfig;
  @override
  @JsonKey(ignore: true)
  _$$_ConfigurationModelCopyWith<_$_ConfigurationModel> get copyWith =>
      throw _privateConstructorUsedError;
}

AppConfig _$AppConfigFromJson(Map<String, dynamic> json) {
  return _AppConfig.fromJson(json);
}

/// @nodoc
mixin _$AppConfig {
  @JsonKey(name: 'LANGUAGES')
  List<Languages> get languages => throw _privateConstructorUsedError;
  @JsonKey(name: 'LOCALIZATION_MODULES')
  List<LocalizationModules>? get localizationModules =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AppConfigCopyWith<AppConfig> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AppConfigCopyWith<$Res> {
  factory $AppConfigCopyWith(AppConfig value, $Res Function(AppConfig) then) =
      _$AppConfigCopyWithImpl<$Res, AppConfig>;
  @useResult
  $Res call(
      {@JsonKey(name: 'LANGUAGES') List<Languages> languages,
      @JsonKey(name: 'LOCALIZATION_MODULES')
      List<LocalizationModules>? localizationModules});
}

/// @nodoc
class _$AppConfigCopyWithImpl<$Res, $Val extends AppConfig>
    implements $AppConfigCopyWith<$Res> {
  _$AppConfigCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = null,
    Object? localizationModules = freezed,
  }) {
    return _then(_value.copyWith(
      languages: null == languages
          ? _value.languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>,
      localizationModules: freezed == localizationModules
          ? _value.localizationModules
          : localizationModules // ignore: cast_nullable_to_non_nullable
              as List<LocalizationModules>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AppConfigCopyWith<$Res> implements $AppConfigCopyWith<$Res> {
  factory _$$_AppConfigCopyWith(
          _$_AppConfig value, $Res Function(_$_AppConfig) then) =
      __$$_AppConfigCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'LANGUAGES') List<Languages> languages,
      @JsonKey(name: 'LOCALIZATION_MODULES')
      List<LocalizationModules>? localizationModules});
}

/// @nodoc
class __$$_AppConfigCopyWithImpl<$Res>
    extends _$AppConfigCopyWithImpl<$Res, _$_AppConfig>
    implements _$$_AppConfigCopyWith<$Res> {
  __$$_AppConfigCopyWithImpl(
      _$_AppConfig _value, $Res Function(_$_AppConfig) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = null,
    Object? localizationModules = freezed,
  }) {
    return _then(_$_AppConfig(
      languages: null == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>,
      localizationModules: freezed == localizationModules
          ? _value._localizationModules
          : localizationModules // ignore: cast_nullable_to_non_nullable
              as List<LocalizationModules>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AppConfig implements _AppConfig {
  _$_AppConfig(
      {@JsonKey(name: 'LANGUAGES') required final List<Languages> languages,
      @JsonKey(name: 'LOCALIZATION_MODULES')
      required final List<LocalizationModules>? localizationModules})
      : _languages = languages,
        _localizationModules = localizationModules;

  factory _$_AppConfig.fromJson(Map<String, dynamic> json) =>
      _$$_AppConfigFromJson(json);

  final List<Languages> _languages;
  @override
  @JsonKey(name: 'LANGUAGES')
  List<Languages> get languages {
    if (_languages is EqualUnmodifiableListView) return _languages;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_languages);
  }

  final List<LocalizationModules>? _localizationModules;
  @override
  @JsonKey(name: 'LOCALIZATION_MODULES')
  List<LocalizationModules>? get localizationModules {
    final value = _localizationModules;
    if (value == null) return null;
    if (_localizationModules is EqualUnmodifiableListView)
      return _localizationModules;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AppConfig(languages: $languages, localizationModules: $localizationModules)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AppConfig &&
            const DeepCollectionEquality()
                .equals(other._languages, _languages) &&
            const DeepCollectionEquality()
                .equals(other._localizationModules, _localizationModules));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_languages),
      const DeepCollectionEquality().hash(_localizationModules));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AppConfigCopyWith<_$_AppConfig> get copyWith =>
      __$$_AppConfigCopyWithImpl<_$_AppConfig>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AppConfigToJson(
      this,
    );
  }
}

abstract class _AppConfig implements AppConfig {
  factory _AppConfig(
          {@JsonKey(name: 'LANGUAGES') required final List<Languages> languages,
          @JsonKey(name: 'LOCALIZATION_MODULES')
          required final List<LocalizationModules>? localizationModules}) =
      _$_AppConfig;

  factory _AppConfig.fromJson(Map<String, dynamic> json) =
      _$_AppConfig.fromJson;

  @override
  @JsonKey(name: 'LANGUAGES')
  List<Languages> get languages;
  @override
  @JsonKey(name: 'LOCALIZATION_MODULES')
  List<LocalizationModules>? get localizationModules;
  @override
  @JsonKey(ignore: true)
  _$$_AppConfigCopyWith<_$_AppConfig> get copyWith =>
      throw _privateConstructorUsedError;
}

LanguagesList _$LanguagesListFromJson(Map<String, dynamic> json) {
  return _LanguagesList.fromJson(json);
}

/// @nodoc
mixin _$LanguagesList {
  @JsonKey(name: 'languages')
  List<Languages>? get languages => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $LanguagesListCopyWith<LanguagesList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LanguagesListCopyWith<$Res> {
  factory $LanguagesListCopyWith(
          LanguagesList value, $Res Function(LanguagesList) then) =
      _$LanguagesListCopyWithImpl<$Res, LanguagesList>;
  @useResult
  $Res call({@JsonKey(name: 'languages') List<Languages>? languages});
}

/// @nodoc
class _$LanguagesListCopyWithImpl<$Res, $Val extends LanguagesList>
    implements $LanguagesListCopyWith<$Res> {
  _$LanguagesListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = freezed,
  }) {
    return _then(_value.copyWith(
      languages: freezed == languages
          ? _value.languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LanguagesListCopyWith<$Res>
    implements $LanguagesListCopyWith<$Res> {
  factory _$$_LanguagesListCopyWith(
          _$_LanguagesList value, $Res Function(_$_LanguagesList) then) =
      __$$_LanguagesListCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'languages') List<Languages>? languages});
}

/// @nodoc
class __$$_LanguagesListCopyWithImpl<$Res>
    extends _$LanguagesListCopyWithImpl<$Res, _$_LanguagesList>
    implements _$$_LanguagesListCopyWith<$Res> {
  __$$_LanguagesListCopyWithImpl(
      _$_LanguagesList _value, $Res Function(_$_LanguagesList) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = freezed,
  }) {
    return _then(_$_LanguagesList(
      languages: freezed == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_LanguagesList implements _LanguagesList {
  _$_LanguagesList(
      {@JsonKey(name: 'languages') required final List<Languages>? languages})
      : _languages = languages;

  factory _$_LanguagesList.fromJson(Map<String, dynamic> json) =>
      _$$_LanguagesListFromJson(json);

  final List<Languages>? _languages;
  @override
  @JsonKey(name: 'languages')
  List<Languages>? get languages {
    final value = _languages;
    if (value == null) return null;
    if (_languages is EqualUnmodifiableListView) return _languages;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'LanguagesList(languages: $languages)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_LanguagesList &&
            const DeepCollectionEquality()
                .equals(other._languages, _languages));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(_languages));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LanguagesListCopyWith<_$_LanguagesList> get copyWith =>
      __$$_LanguagesListCopyWithImpl<_$_LanguagesList>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_LanguagesListToJson(
      this,
    );
  }
}

abstract class _LanguagesList implements LanguagesList {
  factory _LanguagesList(
      {@JsonKey(name: 'languages')
      required final List<Languages>? languages}) = _$_LanguagesList;

  factory _LanguagesList.fromJson(Map<String, dynamic> json) =
      _$_LanguagesList.fromJson;

  @override
  @JsonKey(name: 'languages')
  List<Languages>? get languages;
  @override
  @JsonKey(ignore: true)
  _$$_LanguagesListCopyWith<_$_LanguagesList> get copyWith =>
      throw _privateConstructorUsedError;
}

Languages _$LanguagesFromJson(Map<String, dynamic> json) {
  return _Languages.fromJson(json);
}

/// @nodoc
mixin _$Languages {
  String get label => throw _privateConstructorUsedError;
  String get value => throw _privateConstructorUsedError;
  bool get isSelected => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $LanguagesCopyWith<Languages> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LanguagesCopyWith<$Res> {
  factory $LanguagesCopyWith(Languages value, $Res Function(Languages) then) =
      _$LanguagesCopyWithImpl<$Res, Languages>;
  @useResult
  $Res call({String label, String value, bool isSelected});
}

/// @nodoc
class _$LanguagesCopyWithImpl<$Res, $Val extends Languages>
    implements $LanguagesCopyWith<$Res> {
  _$LanguagesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
    Object? isSelected = null,
  }) {
    return _then(_value.copyWith(
      label: null == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      isSelected: null == isSelected
          ? _value.isSelected
          : isSelected // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LanguagesCopyWith<$Res> implements $LanguagesCopyWith<$Res> {
  factory _$$_LanguagesCopyWith(
          _$_Languages value, $Res Function(_$_Languages) then) =
      __$$_LanguagesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String label, String value, bool isSelected});
}

/// @nodoc
class __$$_LanguagesCopyWithImpl<$Res>
    extends _$LanguagesCopyWithImpl<$Res, _$_Languages>
    implements _$$_LanguagesCopyWith<$Res> {
  __$$_LanguagesCopyWithImpl(
      _$_Languages _value, $Res Function(_$_Languages) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
    Object? isSelected = null,
  }) {
    return _then(_$_Languages(
      label: null == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      isSelected: null == isSelected
          ? _value.isSelected
          : isSelected // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_Languages implements _Languages {
  _$_Languages(
      {required this.label, required this.value, this.isSelected = false});

  factory _$_Languages.fromJson(Map<String, dynamic> json) =>
      _$$_LanguagesFromJson(json);

  @override
  final String label;
  @override
  final String value;
  @override
  @JsonKey()
  final bool isSelected;

  @override
  String toString() {
    return 'Languages(label: $label, value: $value, isSelected: $isSelected)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Languages &&
            (identical(other.label, label) || other.label == label) &&
            (identical(other.value, value) || other.value == value) &&
            (identical(other.isSelected, isSelected) ||
                other.isSelected == isSelected));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, label, value, isSelected);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LanguagesCopyWith<_$_Languages> get copyWith =>
      __$$_LanguagesCopyWithImpl<_$_Languages>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_LanguagesToJson(
      this,
    );
  }
}

abstract class _Languages implements Languages {
  factory _Languages(
      {required final String label,
      required final String value,
      final bool isSelected}) = _$_Languages;

  factory _Languages.fromJson(Map<String, dynamic> json) =
      _$_Languages.fromJson;

  @override
  String get label;
  @override
  String get value;
  @override
  bool get isSelected;
  @override
  @JsonKey(ignore: true)
  _$$_LanguagesCopyWith<_$_Languages> get copyWith =>
      throw _privateConstructorUsedError;
}

LocalizationModules _$LocalizationModulesFromJson(Map<String, dynamic> json) {
  return _LocalizationModules.fromJson(json);
}

/// @nodoc
mixin _$LocalizationModules {
  String get label => throw _privateConstructorUsedError;
  String get value => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $LocalizationModulesCopyWith<LocalizationModules> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocalizationModulesCopyWith<$Res> {
  factory $LocalizationModulesCopyWith(
          LocalizationModules value, $Res Function(LocalizationModules) then) =
      _$LocalizationModulesCopyWithImpl<$Res, LocalizationModules>;
  @useResult
  $Res call({String label, String value});
}

/// @nodoc
class _$LocalizationModulesCopyWithImpl<$Res, $Val extends LocalizationModules>
    implements $LocalizationModulesCopyWith<$Res> {
  _$LocalizationModulesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
  }) {
    return _then(_value.copyWith(
      label: null == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LocalizationModulesCopyWith<$Res>
    implements $LocalizationModulesCopyWith<$Res> {
  factory _$$_LocalizationModulesCopyWith(_$_LocalizationModules value,
          $Res Function(_$_LocalizationModules) then) =
      __$$_LocalizationModulesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String label, String value});
}

/// @nodoc
class __$$_LocalizationModulesCopyWithImpl<$Res>
    extends _$LocalizationModulesCopyWithImpl<$Res, _$_LocalizationModules>
    implements _$$_LocalizationModulesCopyWith<$Res> {
  __$$_LocalizationModulesCopyWithImpl(_$_LocalizationModules _value,
      $Res Function(_$_LocalizationModules) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
  }) {
    return _then(_$_LocalizationModules(
      label: null == label
          ? _value.label
          : label // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_LocalizationModules implements _LocalizationModules {
  _$_LocalizationModules({required this.label, required this.value});

  factory _$_LocalizationModules.fromJson(Map<String, dynamic> json) =>
      _$$_LocalizationModulesFromJson(json);

  @override
  final String label;
  @override
  final String value;

  @override
  String toString() {
    return 'LocalizationModules(label: $label, value: $value)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_LocalizationModules &&
            (identical(other.label, label) || other.label == label) &&
            (identical(other.value, value) || other.value == value));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, label, value);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LocalizationModulesCopyWith<_$_LocalizationModules> get copyWith =>
      __$$_LocalizationModulesCopyWithImpl<_$_LocalizationModules>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_LocalizationModulesToJson(
      this,
    );
  }
}

abstract class _LocalizationModules implements LocalizationModules {
  factory _LocalizationModules(
      {required final String label,
      required final String value}) = _$_LocalizationModules;

  factory _LocalizationModules.fromJson(Map<String, dynamic> json) =
      _$_LocalizationModules.fromJson;

  @override
  String get label;
  @override
  String get value;
  @override
  @JsonKey(ignore: true)
  _$$_LocalizationModulesCopyWith<_$_LocalizationModules> get copyWith =>
      throw _privateConstructorUsedError;
}
