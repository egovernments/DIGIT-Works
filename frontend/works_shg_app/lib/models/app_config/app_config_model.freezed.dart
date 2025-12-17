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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$AppConfigModelImplCopyWith<$Res>
    implements $AppConfigModelCopyWith<$Res> {
  factory _$$AppConfigModelImplCopyWith(_$AppConfigModelImpl value,
          $Res Function(_$AppConfigModelImpl) then) =
      __$$AppConfigModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId, String moduleName, ConfigurationModel? configuration});

  @override
  $ConfigurationModelCopyWith<$Res>? get configuration;
}

/// @nodoc
class __$$AppConfigModelImplCopyWithImpl<$Res>
    extends _$AppConfigModelCopyWithImpl<$Res, _$AppConfigModelImpl>
    implements _$$AppConfigModelImplCopyWith<$Res> {
  __$$AppConfigModelImplCopyWithImpl(
      _$AppConfigModelImpl _value, $Res Function(_$AppConfigModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? moduleName = null,
    Object? configuration = freezed,
  }) {
    return _then(_$AppConfigModelImpl(
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
class _$AppConfigModelImpl implements _AppConfigModel {
  const _$AppConfigModelImpl(
      {required this.tenantId, required this.moduleName, this.configuration});

  factory _$AppConfigModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$AppConfigModelImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AppConfigModelImpl &&
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
  _$$AppConfigModelImplCopyWith<_$AppConfigModelImpl> get copyWith =>
      __$$AppConfigModelImplCopyWithImpl<_$AppConfigModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AppConfigModelImplToJson(
      this,
    );
  }
}

abstract class _AppConfigModel implements AppConfigModel {
  const factory _AppConfigModel(
      {required final String tenantId,
      required final String moduleName,
      final ConfigurationModel? configuration}) = _$AppConfigModelImpl;

  factory _AppConfigModel.fromJson(Map<String, dynamic> json) =
      _$AppConfigModelImpl.fromJson;

  @override
  String get tenantId;
  @override
  String get moduleName;
  @override
  ConfigurationModel? get configuration;
  @override
  @JsonKey(ignore: true)
  _$$AppConfigModelImplCopyWith<_$AppConfigModelImpl> get copyWith =>
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
abstract class _$$ConfigurationModelImplCopyWith<$Res>
    implements $ConfigurationModelCopyWith<$Res> {
  factory _$$ConfigurationModelImplCopyWith(_$ConfigurationModelImpl value,
          $Res Function(_$ConfigurationModelImpl) then) =
      __$$ConfigurationModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({int configVersion, AppConfig appConfig});

  @override
  $AppConfigCopyWith<$Res> get appConfig;
}

/// @nodoc
class __$$ConfigurationModelImplCopyWithImpl<$Res>
    extends _$ConfigurationModelCopyWithImpl<$Res, _$ConfigurationModelImpl>
    implements _$$ConfigurationModelImplCopyWith<$Res> {
  __$$ConfigurationModelImplCopyWithImpl(_$ConfigurationModelImpl _value,
      $Res Function(_$ConfigurationModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? configVersion = null,
    Object? appConfig = null,
  }) {
    return _then(_$ConfigurationModelImpl(
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
class _$ConfigurationModelImpl implements _ConfigurationModel {
  const _$ConfigurationModelImpl(
      {required this.configVersion, required this.appConfig});

  factory _$ConfigurationModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$ConfigurationModelImplFromJson(json);

  @override
  final int configVersion;
  @override
  final AppConfig appConfig;

  @override
  String toString() {
    return 'ConfigurationModel(configVersion: $configVersion, appConfig: $appConfig)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ConfigurationModelImpl &&
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
  _$$ConfigurationModelImplCopyWith<_$ConfigurationModelImpl> get copyWith =>
      __$$ConfigurationModelImplCopyWithImpl<_$ConfigurationModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$ConfigurationModelImplToJson(
      this,
    );
  }
}

abstract class _ConfigurationModel implements ConfigurationModel {
  const factory _ConfigurationModel(
      {required final int configVersion,
      required final AppConfig appConfig}) = _$ConfigurationModelImpl;

  factory _ConfigurationModel.fromJson(Map<String, dynamic> json) =
      _$ConfigurationModelImpl.fromJson;

  @override
  int get configVersion;
  @override
  AppConfig get appConfig;
  @override
  @JsonKey(ignore: true)
  _$$ConfigurationModelImplCopyWith<_$ConfigurationModelImpl> get copyWith =>
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
      {@JsonKey(name: 'LANGUAGES')
          List<Languages> languages,
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
abstract class _$$AppConfigImplCopyWith<$Res>
    implements $AppConfigCopyWith<$Res> {
  factory _$$AppConfigImplCopyWith(
          _$AppConfigImpl value, $Res Function(_$AppConfigImpl) then) =
      __$$AppConfigImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'LANGUAGES')
          List<Languages> languages,
      @JsonKey(name: 'LOCALIZATION_MODULES')
          List<LocalizationModules>? localizationModules});
}

/// @nodoc
class __$$AppConfigImplCopyWithImpl<$Res>
    extends _$AppConfigCopyWithImpl<$Res, _$AppConfigImpl>
    implements _$$AppConfigImplCopyWith<$Res> {
  __$$AppConfigImplCopyWithImpl(
      _$AppConfigImpl _value, $Res Function(_$AppConfigImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = null,
    Object? localizationModules = freezed,
  }) {
    return _then(_$AppConfigImpl(
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
class _$AppConfigImpl implements _AppConfig {
  _$AppConfigImpl(
      {@JsonKey(name: 'LANGUAGES')
          required final List<Languages> languages,
      @JsonKey(name: 'LOCALIZATION_MODULES')
          required final List<LocalizationModules>? localizationModules})
      : _languages = languages,
        _localizationModules = localizationModules;

  factory _$AppConfigImpl.fromJson(Map<String, dynamic> json) =>
      _$$AppConfigImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AppConfigImpl &&
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
  _$$AppConfigImplCopyWith<_$AppConfigImpl> get copyWith =>
      __$$AppConfigImplCopyWithImpl<_$AppConfigImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AppConfigImplToJson(
      this,
    );
  }
}

abstract class _AppConfig implements AppConfig {
  factory _AppConfig(
          {@JsonKey(name: 'LANGUAGES')
              required final List<Languages> languages,
          @JsonKey(name: 'LOCALIZATION_MODULES')
              required final List<LocalizationModules>? localizationModules}) =
      _$AppConfigImpl;

  factory _AppConfig.fromJson(Map<String, dynamic> json) =
      _$AppConfigImpl.fromJson;

  @override
  @JsonKey(name: 'LANGUAGES')
  List<Languages> get languages;
  @override
  @JsonKey(name: 'LOCALIZATION_MODULES')
  List<LocalizationModules>? get localizationModules;
  @override
  @JsonKey(ignore: true)
  _$$AppConfigImplCopyWith<_$AppConfigImpl> get copyWith =>
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
abstract class _$$LanguagesListImplCopyWith<$Res>
    implements $LanguagesListCopyWith<$Res> {
  factory _$$LanguagesListImplCopyWith(
          _$LanguagesListImpl value, $Res Function(_$LanguagesListImpl) then) =
      __$$LanguagesListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(name: 'languages') List<Languages>? languages});
}

/// @nodoc
class __$$LanguagesListImplCopyWithImpl<$Res>
    extends _$LanguagesListCopyWithImpl<$Res, _$LanguagesListImpl>
    implements _$$LanguagesListImplCopyWith<$Res> {
  __$$LanguagesListImplCopyWithImpl(
      _$LanguagesListImpl _value, $Res Function(_$LanguagesListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = freezed,
  }) {
    return _then(_$LanguagesListImpl(
      languages: freezed == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$LanguagesListImpl implements _LanguagesList {
  _$LanguagesListImpl(
      {@JsonKey(name: 'languages') required final List<Languages>? languages})
      : _languages = languages;

  factory _$LanguagesListImpl.fromJson(Map<String, dynamic> json) =>
      _$$LanguagesListImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LanguagesListImpl &&
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
  _$$LanguagesListImplCopyWith<_$LanguagesListImpl> get copyWith =>
      __$$LanguagesListImplCopyWithImpl<_$LanguagesListImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LanguagesListImplToJson(
      this,
    );
  }
}

abstract class _LanguagesList implements LanguagesList {
  factory _LanguagesList(
      {@JsonKey(name: 'languages')
          required final List<Languages>? languages}) = _$LanguagesListImpl;

  factory _LanguagesList.fromJson(Map<String, dynamic> json) =
      _$LanguagesListImpl.fromJson;

  @override
  @JsonKey(name: 'languages')
  List<Languages>? get languages;
  @override
  @JsonKey(ignore: true)
  _$$LanguagesListImplCopyWith<_$LanguagesListImpl> get copyWith =>
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
abstract class _$$LanguagesImplCopyWith<$Res>
    implements $LanguagesCopyWith<$Res> {
  factory _$$LanguagesImplCopyWith(
          _$LanguagesImpl value, $Res Function(_$LanguagesImpl) then) =
      __$$LanguagesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String label, String value, bool isSelected});
}

/// @nodoc
class __$$LanguagesImplCopyWithImpl<$Res>
    extends _$LanguagesCopyWithImpl<$Res, _$LanguagesImpl>
    implements _$$LanguagesImplCopyWith<$Res> {
  __$$LanguagesImplCopyWithImpl(
      _$LanguagesImpl _value, $Res Function(_$LanguagesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
    Object? isSelected = null,
  }) {
    return _then(_$LanguagesImpl(
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
class _$LanguagesImpl implements _Languages {
  _$LanguagesImpl(
      {required this.label, required this.value, this.isSelected = false});

  factory _$LanguagesImpl.fromJson(Map<String, dynamic> json) =>
      _$$LanguagesImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LanguagesImpl &&
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
  _$$LanguagesImplCopyWith<_$LanguagesImpl> get copyWith =>
      __$$LanguagesImplCopyWithImpl<_$LanguagesImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LanguagesImplToJson(
      this,
    );
  }
}

abstract class _Languages implements Languages {
  factory _Languages(
      {required final String label,
      required final String value,
      final bool isSelected}) = _$LanguagesImpl;

  factory _Languages.fromJson(Map<String, dynamic> json) =
      _$LanguagesImpl.fromJson;

  @override
  String get label;
  @override
  String get value;
  @override
  bool get isSelected;
  @override
  @JsonKey(ignore: true)
  _$$LanguagesImplCopyWith<_$LanguagesImpl> get copyWith =>
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
abstract class _$$LocalizationModulesImplCopyWith<$Res>
    implements $LocalizationModulesCopyWith<$Res> {
  factory _$$LocalizationModulesImplCopyWith(_$LocalizationModulesImpl value,
          $Res Function(_$LocalizationModulesImpl) then) =
      __$$LocalizationModulesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String label, String value});
}

/// @nodoc
class __$$LocalizationModulesImplCopyWithImpl<$Res>
    extends _$LocalizationModulesCopyWithImpl<$Res, _$LocalizationModulesImpl>
    implements _$$LocalizationModulesImplCopyWith<$Res> {
  __$$LocalizationModulesImplCopyWithImpl(_$LocalizationModulesImpl _value,
      $Res Function(_$LocalizationModulesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? label = null,
    Object? value = null,
  }) {
    return _then(_$LocalizationModulesImpl(
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
class _$LocalizationModulesImpl implements _LocalizationModules {
  _$LocalizationModulesImpl({required this.label, required this.value});

  factory _$LocalizationModulesImpl.fromJson(Map<String, dynamic> json) =>
      _$$LocalizationModulesImplFromJson(json);

  @override
  final String label;
  @override
  final String value;

  @override
  String toString() {
    return 'LocalizationModules(label: $label, value: $value)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LocalizationModulesImpl &&
            (identical(other.label, label) || other.label == label) &&
            (identical(other.value, value) || other.value == value));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, label, value);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LocalizationModulesImplCopyWith<_$LocalizationModulesImpl> get copyWith =>
      __$$LocalizationModulesImplCopyWithImpl<_$LocalizationModulesImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$LocalizationModulesImplToJson(
      this,
    );
  }
}

abstract class _LocalizationModules implements LocalizationModules {
  factory _LocalizationModules(
      {required final String label,
      required final String value}) = _$LocalizationModulesImpl;

  factory _LocalizationModules.fromJson(Map<String, dynamic> json) =
      _$LocalizationModulesImpl.fromJson;

  @override
  String get label;
  @override
  String get value;
  @override
  @JsonKey(ignore: true)
  _$$LocalizationModulesImplCopyWith<_$LocalizationModulesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
