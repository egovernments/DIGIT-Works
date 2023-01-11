// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'localization.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$LocalizationEvent {
  String get module => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String get locale => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String module, String tenantId, String locale)
        onLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String module, String tenantId, String locale)?
        onLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String module, String tenantId, String locale)?
        onLoadLocalization,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(OnLoadLocalizationEvent value) onLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(OnLoadLocalizationEvent value)? onLoadLocalization,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(OnLoadLocalizationEvent value)? onLoadLocalization,
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
abstract class _$$OnLoadLocalizationEventCopyWith<$Res>
    implements $LocalizationEventCopyWith<$Res> {
  factory _$$OnLoadLocalizationEventCopyWith(_$OnLoadLocalizationEvent value,
          $Res Function(_$OnLoadLocalizationEvent) then) =
      __$$OnLoadLocalizationEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String module, String tenantId, String locale});
}

/// @nodoc
class __$$OnLoadLocalizationEventCopyWithImpl<$Res>
    extends _$LocalizationEventCopyWithImpl<$Res, _$OnLoadLocalizationEvent>
    implements _$$OnLoadLocalizationEventCopyWith<$Res> {
  __$$OnLoadLocalizationEventCopyWithImpl(_$OnLoadLocalizationEvent _value,
      $Res Function(_$OnLoadLocalizationEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? module = null,
    Object? tenantId = null,
    Object? locale = null,
  }) {
    return _then(_$OnLoadLocalizationEvent(
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

class _$OnLoadLocalizationEvent
    with DiagnosticableTreeMixin
    implements OnLoadLocalizationEvent {
  const _$OnLoadLocalizationEvent(
      {required this.module, required this.tenantId, required this.locale});

  @override
  final String module;
  @override
  final String tenantId;
  @override
  final String locale;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationEvent.onLoadLocalization(module: $module, tenantId: $tenantId, locale: $locale)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'LocalizationEvent.onLoadLocalization'))
      ..add(DiagnosticsProperty('module', module))
      ..add(DiagnosticsProperty('tenantId', tenantId))
      ..add(DiagnosticsProperty('locale', locale));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OnLoadLocalizationEvent &&
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
  _$$OnLoadLocalizationEventCopyWith<_$OnLoadLocalizationEvent> get copyWith =>
      __$$OnLoadLocalizationEventCopyWithImpl<_$OnLoadLocalizationEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String module, String tenantId, String locale)
        onLoadLocalization,
  }) {
    return onLoadLocalization(module, tenantId, locale);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String module, String tenantId, String locale)?
        onLoadLocalization,
  }) {
    return onLoadLocalization?.call(module, tenantId, locale);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String module, String tenantId, String locale)?
        onLoadLocalization,
    required TResult orElse(),
  }) {
    if (onLoadLocalization != null) {
      return onLoadLocalization(module, tenantId, locale);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(OnLoadLocalizationEvent value) onLoadLocalization,
  }) {
    return onLoadLocalization(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(OnLoadLocalizationEvent value)? onLoadLocalization,
  }) {
    return onLoadLocalization?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(OnLoadLocalizationEvent value)? onLoadLocalization,
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
      required final String locale}) = _$OnLoadLocalizationEvent;

  @override
  String get module;
  @override
  String get tenantId;
  @override
  String get locale;
  @override
  @JsonKey(ignore: true)
  _$$OnLoadLocalizationEventCopyWith<_$OnLoadLocalizationEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$LocalizationState {
  List<LocalizationMessageModel>? get localization =>
      throw _privateConstructorUsedError;
  bool get isLocalizationLoadCompleted => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $LocalizationStateCopyWith<LocalizationState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $LocalizationStateCopyWith<$Res> {
  factory $LocalizationStateCopyWith(
          LocalizationState value, $Res Function(LocalizationState) then) =
      _$LocalizationStateCopyWithImpl<$Res, LocalizationState>;
  @useResult
  $Res call(
      {List<LocalizationMessageModel>? localization,
      bool isLocalizationLoadCompleted});
}

/// @nodoc
class _$LocalizationStateCopyWithImpl<$Res, $Val extends LocalizationState>
    implements $LocalizationStateCopyWith<$Res> {
  _$LocalizationStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? localization = freezed,
    Object? isLocalizationLoadCompleted = null,
  }) {
    return _then(_value.copyWith(
      localization: freezed == localization
          ? _value.localization
          : localization // ignore: cast_nullable_to_non_nullable
              as List<LocalizationMessageModel>?,
      isLocalizationLoadCompleted: null == isLocalizationLoadCompleted
          ? _value.isLocalizationLoadCompleted
          : isLocalizationLoadCompleted // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_LocalizationStateCopyWith<$Res>
    implements $LocalizationStateCopyWith<$Res> {
  factory _$$_LocalizationStateCopyWith(_$_LocalizationState value,
          $Res Function(_$_LocalizationState) then) =
      __$$_LocalizationStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {List<LocalizationMessageModel>? localization,
      bool isLocalizationLoadCompleted});
}

/// @nodoc
class __$$_LocalizationStateCopyWithImpl<$Res>
    extends _$LocalizationStateCopyWithImpl<$Res, _$_LocalizationState>
    implements _$$_LocalizationStateCopyWith<$Res> {
  __$$_LocalizationStateCopyWithImpl(
      _$_LocalizationState _value, $Res Function(_$_LocalizationState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? localization = freezed,
    Object? isLocalizationLoadCompleted = null,
  }) {
    return _then(_$_LocalizationState(
      localization: freezed == localization
          ? _value._localization
          : localization // ignore: cast_nullable_to_non_nullable
              as List<LocalizationMessageModel>?,
      isLocalizationLoadCompleted: null == isLocalizationLoadCompleted
          ? _value.isLocalizationLoadCompleted
          : isLocalizationLoadCompleted // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc

class _$_LocalizationState
    with DiagnosticableTreeMixin
    implements _LocalizationState {
  const _$_LocalizationState(
      {final List<LocalizationMessageModel>? localization,
      this.isLocalizationLoadCompleted = false})
      : _localization = localization;

  final List<LocalizationMessageModel>? _localization;
  @override
  List<LocalizationMessageModel>? get localization {
    final value = _localization;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey()
  final bool isLocalizationLoadCompleted;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'LocalizationState(localization: $localization, isLocalizationLoadCompleted: $isLocalizationLoadCompleted)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'LocalizationState'))
      ..add(DiagnosticsProperty('localization', localization))
      ..add(DiagnosticsProperty(
          'isLocalizationLoadCompleted', isLocalizationLoadCompleted));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_LocalizationState &&
            const DeepCollectionEquality()
                .equals(other._localization, _localization) &&
            (identical(other.isLocalizationLoadCompleted,
                    isLocalizationLoadCompleted) ||
                other.isLocalizationLoadCompleted ==
                    isLocalizationLoadCompleted));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_localization),
      isLocalizationLoadCompleted);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LocalizationStateCopyWith<_$_LocalizationState> get copyWith =>
      __$$_LocalizationStateCopyWithImpl<_$_LocalizationState>(
          this, _$identity);
}

abstract class _LocalizationState implements LocalizationState {
  const factory _LocalizationState(
      {final List<LocalizationMessageModel>? localization,
      final bool isLocalizationLoadCompleted}) = _$_LocalizationState;

  @override
  List<LocalizationMessageModel>? get localization;
  @override
  bool get isLocalizationLoadCompleted;
  @override
  @JsonKey(ignore: true)
  _$$_LocalizationStateCopyWith<_$_LocalizationState> get copyWith =>
      throw _privateConstructorUsedError;
}
