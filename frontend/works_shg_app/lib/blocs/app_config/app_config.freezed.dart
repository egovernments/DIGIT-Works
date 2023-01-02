// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'app_config.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$ApplicationConfigEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() onfetchConfig,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? onfetchConfig,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? onfetchConfig,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(UpdateActionEvent value) onfetchConfig,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(UpdateActionEvent value)? onfetchConfig,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(UpdateActionEvent value)? onfetchConfig,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ApplicationConfigEventCopyWith<$Res> {
  factory $ApplicationConfigEventCopyWith(ApplicationConfigEvent value,
          $Res Function(ApplicationConfigEvent) then) =
      _$ApplicationConfigEventCopyWithImpl<$Res, ApplicationConfigEvent>;
}

/// @nodoc
class _$ApplicationConfigEventCopyWithImpl<$Res,
        $Val extends ApplicationConfigEvent>
    implements $ApplicationConfigEventCopyWith<$Res> {
  _$ApplicationConfigEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$UpdateActionEventCopyWith<$Res> {
  factory _$$UpdateActionEventCopyWith(
          _$UpdateActionEvent value, $Res Function(_$UpdateActionEvent) then) =
      __$$UpdateActionEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$UpdateActionEventCopyWithImpl<$Res>
    extends _$ApplicationConfigEventCopyWithImpl<$Res, _$UpdateActionEvent>
    implements _$$UpdateActionEventCopyWith<$Res> {
  __$$UpdateActionEventCopyWithImpl(
      _$UpdateActionEvent _value, $Res Function(_$UpdateActionEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$UpdateActionEvent implements UpdateActionEvent {
  const _$UpdateActionEvent();

  @override
  String toString() {
    return 'ApplicationConfigEvent.onfetchConfig()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$UpdateActionEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() onfetchConfig,
  }) {
    return onfetchConfig();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? onfetchConfig,
  }) {
    return onfetchConfig?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? onfetchConfig,
    required TResult orElse(),
  }) {
    if (onfetchConfig != null) {
      return onfetchConfig();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(UpdateActionEvent value) onfetchConfig,
  }) {
    return onfetchConfig(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(UpdateActionEvent value)? onfetchConfig,
  }) {
    return onfetchConfig?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(UpdateActionEvent value)? onfetchConfig,
    required TResult orElse(),
  }) {
    if (onfetchConfig != null) {
      return onfetchConfig(this);
    }
    return orElse();
  }
}

abstract class UpdateActionEvent implements ApplicationConfigEvent {
  const factory UpdateActionEvent() = _$UpdateActionEvent;
}

/// @nodoc
mixin _$ApplicationConfigState {
  bool get isloading => throw _privateConstructorUsedError;
  AppConfigModel? get appConfigDetail => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ApplicationConfigStateCopyWith<ApplicationConfigState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ApplicationConfigStateCopyWith<$Res> {
  factory $ApplicationConfigStateCopyWith(ApplicationConfigState value,
          $Res Function(ApplicationConfigState) then) =
      _$ApplicationConfigStateCopyWithImpl<$Res, ApplicationConfigState>;
  @useResult
  $Res call({bool isloading, AppConfigModel? appConfigDetail});

  $AppConfigModelCopyWith<$Res>? get appConfigDetail;
}

/// @nodoc
class _$ApplicationConfigStateCopyWithImpl<$Res,
        $Val extends ApplicationConfigState>
    implements $ApplicationConfigStateCopyWith<$Res> {
  _$ApplicationConfigStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? isloading = null,
    Object? appConfigDetail = freezed,
  }) {
    return _then(_value.copyWith(
      isloading: null == isloading
          ? _value.isloading
          : isloading // ignore: cast_nullable_to_non_nullable
              as bool,
      appConfigDetail: freezed == appConfigDetail
          ? _value.appConfigDetail
          : appConfigDetail // ignore: cast_nullable_to_non_nullable
              as AppConfigModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AppConfigModelCopyWith<$Res>? get appConfigDetail {
    if (_value.appConfigDetail == null) {
      return null;
    }

    return $AppConfigModelCopyWith<$Res>(_value.appConfigDetail!, (value) {
      return _then(_value.copyWith(appConfigDetail: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_ApplicationConfigStateCopyWith<$Res>
    implements $ApplicationConfigStateCopyWith<$Res> {
  factory _$$_ApplicationConfigStateCopyWith(_$_ApplicationConfigState value,
          $Res Function(_$_ApplicationConfigState) then) =
      __$$_ApplicationConfigStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool isloading, AppConfigModel? appConfigDetail});

  @override
  $AppConfigModelCopyWith<$Res>? get appConfigDetail;
}

/// @nodoc
class __$$_ApplicationConfigStateCopyWithImpl<$Res>
    extends _$ApplicationConfigStateCopyWithImpl<$Res,
        _$_ApplicationConfigState>
    implements _$$_ApplicationConfigStateCopyWith<$Res> {
  __$$_ApplicationConfigStateCopyWithImpl(_$_ApplicationConfigState _value,
      $Res Function(_$_ApplicationConfigState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? isloading = null,
    Object? appConfigDetail = freezed,
  }) {
    return _then(_$_ApplicationConfigState(
      isloading: null == isloading
          ? _value.isloading
          : isloading // ignore: cast_nullable_to_non_nullable
              as bool,
      appConfigDetail: freezed == appConfigDetail
          ? _value.appConfigDetail
          : appConfigDetail // ignore: cast_nullable_to_non_nullable
              as AppConfigModel?,
    ));
  }
}

/// @nodoc

class _$_ApplicationConfigState extends _ApplicationConfigState {
  const _$_ApplicationConfigState(
      {this.isloading = false, this.appConfigDetail})
      : super._();

  @override
  @JsonKey()
  final bool isloading;
  @override
  final AppConfigModel? appConfigDetail;

  @override
  String toString() {
    return 'ApplicationConfigState(isloading: $isloading, appConfigDetail: $appConfigDetail)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_ApplicationConfigState &&
            (identical(other.isloading, isloading) ||
                other.isloading == isloading) &&
            (identical(other.appConfigDetail, appConfigDetail) ||
                other.appConfigDetail == appConfigDetail));
  }

  @override
  int get hashCode => Object.hash(runtimeType, isloading, appConfigDetail);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ApplicationConfigStateCopyWith<_$_ApplicationConfigState> get copyWith =>
      __$$_ApplicationConfigStateCopyWithImpl<_$_ApplicationConfigState>(
          this, _$identity);
}

abstract class _ApplicationConfigState extends ApplicationConfigState {
  const factory _ApplicationConfigState(
      {final bool isloading,
      final AppConfigModel? appConfigDetail}) = _$_ApplicationConfigState;
  const _ApplicationConfigState._() : super._();

  @override
  bool get isloading;
  @override
  AppConfigModel? get appConfigDetail;
  @override
  @JsonKey(ignore: true)
  _$$_ApplicationConfigStateCopyWith<_$_ApplicationConfigState> get copyWith =>
      throw _privateConstructorUsedError;
}
