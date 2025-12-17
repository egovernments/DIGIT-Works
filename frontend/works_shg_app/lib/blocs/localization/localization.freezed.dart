// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'localization.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$LocalizationEvent {
  String get module => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String get locale => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)
        onLoadLocalization,
    required TResult Function(String module, String tenantId, String locale)
        onSpecificLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult? Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(OnLoadLocalizationEvent value) onLoadLocalization,
    required TResult Function(OnSpecificLoadLocalizationEvent value)
        onSpecificLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult? Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $LocalizationEventCopyWith<LocalizationEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocalizationEventCopyWith<$Res> {
  factory $LocalizationEventCopyWith(
          LocalizationEvent value, $Res Function(LocalizationEvent) then) =
      _$LocalizationEventCopyWithImpl<$Res, LocalizationEvent>;
  @useResult
  $Res call({String module, String tenantId, String locale});
}

/// @nodoc
class _$LocalizationEventCopyWithImpl<$Res, $Val extends LocalizationEvent>
    implements $LocalizationEventCopyWith<$Res> {
  _$LocalizationEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? module = null,
    Object? tenantId = null,
    Object? locale = null,
  }) {
    return _then(_value.copyWith(
      module: null == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      locale: null == locale
          ? _value.locale
          : locale // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OnLoadLocalizationEventImplCopyWith<$Res>
    implements $LocalizationEventCopyWith<$Res> {
  factory _$$OnLoadLocalizationEventImplCopyWith(
          _$OnLoadLocalizationEventImpl value,
          $Res Function(_$OnLoadLocalizationEventImpl) then) =
      __$$OnLoadLocalizationEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String module,
      String tenantId,
      String locale,
      List<Languages>? languages,
      List<LocalizationModules>? localizationModules});
}

/// @nodoc
class __$$OnLoadLocalizationEventImplCopyWithImpl<$Res>
    extends _$LocalizationEventCopyWithImpl<$Res, _$OnLoadLocalizationEventImpl>
    implements _$$OnLoadLocalizationEventImplCopyWith<$Res> {
  __$$OnLoadLocalizationEventImplCopyWithImpl(
      _$OnLoadLocalizationEventImpl _value,
      $Res Function(_$OnLoadLocalizationEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? module = null,
    Object? tenantId = null,
    Object? locale = null,
    Object? languages = freezed,
    Object? localizationModules = freezed,
  }) {
    return _then(_$OnLoadLocalizationEventImpl(
      module: null == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      locale: null == locale
          ? _value.locale
          : locale // ignore: cast_nullable_to_non_nullable
              as String,
      languages: freezed == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
      localizationModules: freezed == localizationModules
          ? _value._localizationModules
          : localizationModules // ignore: cast_nullable_to_non_nullable
              as List<LocalizationModules>?,
    ));
  }
}

/// @nodoc

class _$OnLoadLocalizationEventImpl
    with DiagnosticableTreeMixin
    implements OnLoadLocalizationEvent {
  const _$OnLoadLocalizationEventImpl(
      {required this.module,
      required this.tenantId,
      required this.locale,
      final List<Languages>? languages,
      final List<LocalizationModules>? localizationModules})
      : _languages = languages,
        _localizationModules = localizationModules;

  @override
  final String module;
  @override
  final String tenantId;
  @override
  final String locale;
  final List<Languages>? _languages;
  @override
  List<Languages>? get languages {
    final value = _languages;
    if (value == null) return null;
    if (_languages is EqualUnmodifiableListView) return _languages;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<LocalizationModules>? _localizationModules;
  @override
  List<LocalizationModules>? get localizationModules {
    final value = _localizationModules;
    if (value == null) return null;
    if (_localizationModules is EqualUnmodifiableListView)
      return _localizationModules;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationEvent.onLoadLocalization(module: $module, tenantId: $tenantId, locale: $locale, languages: $languages, localizationModules: $localizationModules)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'LocalizationEvent.onLoadLocalization'))
      ..add(DiagnosticsProperty('module', module))
      ..add(DiagnosticsProperty('tenantId', tenantId))
      ..add(DiagnosticsProperty('locale', locale))
      ..add(DiagnosticsProperty('languages', languages))
      ..add(DiagnosticsProperty('localizationModules', localizationModules));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OnLoadLocalizationEventImpl &&
            (identical(other.module, module) || other.module == module) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.locale, locale) || other.locale == locale) &&
            const DeepCollectionEquality()
                .equals(other._languages, _languages) &&
            const DeepCollectionEquality()
                .equals(other._localizationModules, _localizationModules));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      module,
      tenantId,
      locale,
      const DeepCollectionEquality().hash(_languages),
      const DeepCollectionEquality().hash(_localizationModules));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OnLoadLocalizationEventImplCopyWith<_$OnLoadLocalizationEventImpl>
      get copyWith => __$$OnLoadLocalizationEventImplCopyWithImpl<
          _$OnLoadLocalizationEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)
        onLoadLocalization,
    required TResult Function(String module, String tenantId, String locale)
        onSpecificLoadLocalization,
  }) {
    return onLoadLocalization(
        module, tenantId, locale, languages, localizationModules);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult? Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
  }) {
    return onLoadLocalization?.call(
        module, tenantId, locale, languages, localizationModules);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) {
    if (onLoadLocalization != null) {
      return onLoadLocalization(
          module, tenantId, locale, languages, localizationModules);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(OnLoadLocalizationEvent value) onLoadLocalization,
    required TResult Function(OnSpecificLoadLocalizationEvent value)
        onSpecificLoadLocalization,
  }) {
    return onLoadLocalization(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult? Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
  }) {
    return onLoadLocalization?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) {
    if (onLoadLocalization != null) {
      return onLoadLocalization(this);
    }
    return orElse();
  }
}

abstract class OnLoadLocalizationEvent implements LocalizationEvent {
  const factory OnLoadLocalizationEvent(
          {required final String module,
          required final String tenantId,
          required final String locale,
          final List<Languages>? languages,
          final List<LocalizationModules>? localizationModules}) =
      _$OnLoadLocalizationEventImpl;

  @override
  String get module;
  @override
  String get tenantId;
  @override
  String get locale;
  List<Languages>? get languages;
  List<LocalizationModules>? get localizationModules;
  @override
  @JsonKey(ignore: true)
  _$$OnLoadLocalizationEventImplCopyWith<_$OnLoadLocalizationEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$OnSpecificLoadLocalizationEventImplCopyWith<$Res>
    implements $LocalizationEventCopyWith<$Res> {
  factory _$$OnSpecificLoadLocalizationEventImplCopyWith(
          _$OnSpecificLoadLocalizationEventImpl value,
          $Res Function(_$OnSpecificLoadLocalizationEventImpl) then) =
      __$$OnSpecificLoadLocalizationEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String module, String tenantId, String locale});
}

/// @nodoc
class __$$OnSpecificLoadLocalizationEventImplCopyWithImpl<$Res>
    extends _$LocalizationEventCopyWithImpl<$Res,
        _$OnSpecificLoadLocalizationEventImpl>
    implements _$$OnSpecificLoadLocalizationEventImplCopyWith<$Res> {
  __$$OnSpecificLoadLocalizationEventImplCopyWithImpl(
      _$OnSpecificLoadLocalizationEventImpl _value,
      $Res Function(_$OnSpecificLoadLocalizationEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? module = null,
    Object? tenantId = null,
    Object? locale = null,
  }) {
    return _then(_$OnSpecificLoadLocalizationEventImpl(
      module: null == module
          ? _value.module
          : module // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      locale: null == locale
          ? _value.locale
          : locale // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$OnSpecificLoadLocalizationEventImpl
    with DiagnosticableTreeMixin
    implements OnSpecificLoadLocalizationEvent {
  const _$OnSpecificLoadLocalizationEventImpl(
      {required this.module, required this.tenantId, required this.locale});

  @override
  final String module;
  @override
  final String tenantId;
  @override
  final String locale;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationEvent.onSpecificLoadLocalization(module: $module, tenantId: $tenantId, locale: $locale)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'LocalizationEvent.onSpecificLoadLocalization'))
      ..add(DiagnosticsProperty('module', module))
      ..add(DiagnosticsProperty('tenantId', tenantId))
      ..add(DiagnosticsProperty('locale', locale));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OnSpecificLoadLocalizationEventImpl &&
            (identical(other.module, module) || other.module == module) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.locale, locale) || other.locale == locale));
  }

  @override
  int get hashCode => Object.hash(runtimeType, module, tenantId, locale);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OnSpecificLoadLocalizationEventImplCopyWith<
          _$OnSpecificLoadLocalizationEventImpl>
      get copyWith => __$$OnSpecificLoadLocalizationEventImplCopyWithImpl<
          _$OnSpecificLoadLocalizationEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)
        onLoadLocalization,
    required TResult Function(String module, String tenantId, String locale)
        onSpecificLoadLocalization,
  }) {
    return onSpecificLoadLocalization(module, tenantId, locale);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult? Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
  }) {
    return onSpecificLoadLocalization?.call(module, tenantId, locale);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String module,
            String tenantId,
            String locale,
            List<Languages>? languages,
            List<LocalizationModules>? localizationModules)?
        onLoadLocalization,
    TResult Function(String module, String tenantId, String locale)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) {
    if (onSpecificLoadLocalization != null) {
      return onSpecificLoadLocalization(module, tenantId, locale);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(OnLoadLocalizationEvent value) onLoadLocalization,
    required TResult Function(OnSpecificLoadLocalizationEvent value)
        onSpecificLoadLocalization,
  }) {
    return onSpecificLoadLocalization(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult? Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
  }) {
    return onSpecificLoadLocalization?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(OnLoadLocalizationEvent value)? onLoadLocalization,
    TResult Function(OnSpecificLoadLocalizationEvent value)?
        onSpecificLoadLocalization,
    required TResult orElse(),
  }) {
    if (onSpecificLoadLocalization != null) {
      return onSpecificLoadLocalization(this);
    }
    return orElse();
  }
}

abstract class OnSpecificLoadLocalizationEvent implements LocalizationEvent {
  const factory OnSpecificLoadLocalizationEvent(
      {required final String module,
      required final String tenantId,
      required final String locale}) = _$OnSpecificLoadLocalizationEventImpl;

  @override
  String get module;
  @override
  String get tenantId;
  @override
  String get locale;
  @override
  @JsonKey(ignore: true)
  _$$OnSpecificLoadLocalizationEventImplCopyWith<
          _$OnSpecificLoadLocalizationEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$LocalizationState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocalizationStateCopyWith<$Res> {
  factory $LocalizationStateCopyWith(
          LocalizationState value, $Res Function(LocalizationState) then) =
      _$LocalizationStateCopyWithImpl<$Res, LocalizationState>;
}

/// @nodoc
class _$LocalizationStateCopyWithImpl<$Res, $Val extends LocalizationState>
    implements $LocalizationStateCopyWith<$Res> {
  _$LocalizationStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$LocalizationStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial with DiagnosticableTreeMixin {
  const _$InitialImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'LocalizationState.initial'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends LocalizationState {
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$LocalizationStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading with DiagnosticableTreeMixin {
  const _$LoadingImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'LocalizationState.loading'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends LocalizationState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({List<Languages>? languages, List<ModuleStatus>? moduleStatus});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$LocalizationStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? languages = freezed,
    Object? moduleStatus = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == languages
          ? _value._languages
          : languages // ignore: cast_nullable_to_non_nullable
              as List<Languages>?,
      freezed == moduleStatus
          ? _value._moduleStatus
          : moduleStatus // ignore: cast_nullable_to_non_nullable
              as List<ModuleStatus>?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  _$LoadedImpl(
      final List<Languages>? languages, final List<ModuleStatus>? moduleStatus)
      : _languages = languages,
        _moduleStatus = moduleStatus,
        super._();

  final List<Languages>? _languages;
  @override
  List<Languages>? get languages {
    final value = _languages;
    if (value == null) return null;
    if (_languages is EqualUnmodifiableListView) return _languages;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<ModuleStatus>? _moduleStatus;
  @override
  List<ModuleStatus>? get moduleStatus {
    final value = _moduleStatus;
    if (value == null) return null;
    if (_moduleStatus is EqualUnmodifiableListView) return _moduleStatus;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationState.loaded(languages: $languages, moduleStatus: $moduleStatus)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'LocalizationState.loaded'))
      ..add(DiagnosticsProperty('languages', languages))
      ..add(DiagnosticsProperty('moduleStatus', moduleStatus));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            const DeepCollectionEquality()
                .equals(other._languages, _languages) &&
            const DeepCollectionEquality()
                .equals(other._moduleStatus, _moduleStatus));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_languages),
      const DeepCollectionEquality().hash(_moduleStatus));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(languages, moduleStatus);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(languages, moduleStatus);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(languages, moduleStatus);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends LocalizationState {
  factory _Loaded(final List<Languages>? languages,
      final List<ModuleStatus>? moduleStatus) = _$LoadedImpl;
  _Loaded._() : super._();

  List<Languages>? get languages;
  List<ModuleStatus>? get moduleStatus;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$LocalizationStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error with DiagnosticableTreeMixin {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'LocalizationState.error'))
      ..add(DiagnosticsProperty('error', error));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      __$$ErrorImplCopyWithImpl<_$ErrorImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            List<Languages>? languages, List<ModuleStatus>? moduleStatus)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends LocalizationState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
