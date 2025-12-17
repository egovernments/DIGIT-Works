// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'mb_crud.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MeasurementCrudBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)
        update,
    required TResult Function() clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult? Function()? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult Function()? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUpdateBlocEvent value) update,
    required TResult Function(MeasurementCrudBlocClearEvent value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUpdateBlocEvent value)? update,
    TResult? Function(MeasurementCrudBlocClearEvent value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUpdateBlocEvent value)? update,
    TResult Function(MeasurementCrudBlocClearEvent value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementCrudBlocEventCopyWith<$Res> {
  factory $MeasurementCrudBlocEventCopyWith(MeasurementCrudBlocEvent value,
          $Res Function(MeasurementCrudBlocEvent) then) =
      _$MeasurementCrudBlocEventCopyWithImpl<$Res, MeasurementCrudBlocEvent>;
}

/// @nodoc
class _$MeasurementCrudBlocEventCopyWithImpl<$Res,
        $Val extends MeasurementCrudBlocEvent>
    implements $MeasurementCrudBlocEventCopyWith<$Res> {
  _$MeasurementCrudBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$MeasurementUpdateBlocEventImplCopyWith<$Res> {
  factory _$$MeasurementUpdateBlocEventImplCopyWith(
          _$MeasurementUpdateBlocEventImpl value,
          $Res Function(_$MeasurementUpdateBlocEventImpl) then) =
      __$$MeasurementUpdateBlocEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId,
      Measurement measurement,
      WorkFlow workFlow,
      MBScreen type});

  $MeasurementCopyWith<$Res> get measurement;
  $WorkFlowCopyWith<$Res> get workFlow;
}

/// @nodoc
class __$$MeasurementUpdateBlocEventImplCopyWithImpl<$Res>
    extends _$MeasurementCrudBlocEventCopyWithImpl<$Res,
        _$MeasurementUpdateBlocEventImpl>
    implements _$$MeasurementUpdateBlocEventImplCopyWith<$Res> {
  __$$MeasurementUpdateBlocEventImplCopyWithImpl(
      _$MeasurementUpdateBlocEventImpl _value,
      $Res Function(_$MeasurementUpdateBlocEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? measurement = null,
    Object? workFlow = null,
    Object? type = null,
  }) {
    return _then(_$MeasurementUpdateBlocEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      measurement: null == measurement
          ? _value.measurement
          : measurement // ignore: cast_nullable_to_non_nullable
              as Measurement,
      workFlow: null == workFlow
          ? _value.workFlow
          : workFlow // ignore: cast_nullable_to_non_nullable
              as WorkFlow,
      type: null == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as MBScreen,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MeasurementCopyWith<$Res> get measurement {
    return $MeasurementCopyWith<$Res>(_value.measurement, (value) {
      return _then(_value.copyWith(measurement: value));
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $WorkFlowCopyWith<$Res> get workFlow {
    return $WorkFlowCopyWith<$Res>(_value.workFlow, (value) {
      return _then(_value.copyWith(workFlow: value));
    });
  }
}

/// @nodoc

class _$MeasurementUpdateBlocEventImpl implements MeasurementUpdateBlocEvent {
  const _$MeasurementUpdateBlocEventImpl(
      {required this.tenantId,
      required this.measurement,
      required this.workFlow,
      required this.type});

  @override
  final String tenantId;
  @override
  final Measurement measurement;
  @override
  final WorkFlow workFlow;
  @override
  final MBScreen type;

  @override
  String toString() {
    return 'MeasurementCrudBlocEvent.update(tenantId: $tenantId, measurement: $measurement, workFlow: $workFlow, type: $type)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementUpdateBlocEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.measurement, measurement) ||
                other.measurement == measurement) &&
            (identical(other.workFlow, workFlow) ||
                other.workFlow == workFlow) &&
            (identical(other.type, type) || other.type == type));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, tenantId, measurement, workFlow, type);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementUpdateBlocEventImplCopyWith<_$MeasurementUpdateBlocEventImpl>
      get copyWith => __$$MeasurementUpdateBlocEventImplCopyWithImpl<
          _$MeasurementUpdateBlocEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)
        update,
    required TResult Function() clear,
  }) {
    return update(tenantId, measurement, workFlow, type);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult? Function()? clear,
  }) {
    return update?.call(tenantId, measurement, workFlow, type);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(tenantId, measurement, workFlow, type);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUpdateBlocEvent value) update,
    required TResult Function(MeasurementCrudBlocClearEvent value) clear,
  }) {
    return update(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUpdateBlocEvent value)? update,
    TResult? Function(MeasurementCrudBlocClearEvent value)? clear,
  }) {
    return update?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUpdateBlocEvent value)? update,
    TResult Function(MeasurementCrudBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(this);
    }
    return orElse();
  }
}

abstract class MeasurementUpdateBlocEvent implements MeasurementCrudBlocEvent {
  const factory MeasurementUpdateBlocEvent(
      {required final String tenantId,
      required final Measurement measurement,
      required final WorkFlow workFlow,
      required final MBScreen type}) = _$MeasurementUpdateBlocEventImpl;

  String get tenantId;
  Measurement get measurement;
  WorkFlow get workFlow;
  MBScreen get type;
  @JsonKey(ignore: true)
  _$$MeasurementUpdateBlocEventImplCopyWith<_$MeasurementUpdateBlocEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementCrudBlocClearEventImplCopyWith<$Res> {
  factory _$$MeasurementCrudBlocClearEventImplCopyWith(
          _$MeasurementCrudBlocClearEventImpl value,
          $Res Function(_$MeasurementCrudBlocClearEventImpl) then) =
      __$$MeasurementCrudBlocClearEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$MeasurementCrudBlocClearEventImplCopyWithImpl<$Res>
    extends _$MeasurementCrudBlocEventCopyWithImpl<$Res,
        _$MeasurementCrudBlocClearEventImpl>
    implements _$$MeasurementCrudBlocClearEventImplCopyWith<$Res> {
  __$$MeasurementCrudBlocClearEventImplCopyWithImpl(
      _$MeasurementCrudBlocClearEventImpl _value,
      $Res Function(_$MeasurementCrudBlocClearEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$MeasurementCrudBlocClearEventImpl
    implements MeasurementCrudBlocClearEvent {
  const _$MeasurementCrudBlocClearEventImpl();

  @override
  String toString() {
    return 'MeasurementCrudBlocEvent.clear()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementCrudBlocClearEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)
        update,
    required TResult Function() clear,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult? Function()? clear,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, Measurement measurement,
            WorkFlow workFlow, MBScreen type)?
        update,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementUpdateBlocEvent value) update,
    required TResult Function(MeasurementCrudBlocClearEvent value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementUpdateBlocEvent value)? update,
    TResult? Function(MeasurementCrudBlocClearEvent value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementUpdateBlocEvent value)? update,
    TResult Function(MeasurementCrudBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class MeasurementCrudBlocClearEvent
    implements MeasurementCrudBlocEvent {
  const factory MeasurementCrudBlocClearEvent() =
      _$MeasurementCrudBlocClearEventImpl;
}

/// @nodoc
mixin _$MeasurementCrudState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(Measurement? measurement) loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(Measurement? measurement)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(Measurement? measurement)? loaded,
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
abstract class $MeasurementCrudStateCopyWith<$Res> {
  factory $MeasurementCrudStateCopyWith(MeasurementCrudState value,
          $Res Function(MeasurementCrudState) then) =
      _$MeasurementCrudStateCopyWithImpl<$Res, MeasurementCrudState>;
}

/// @nodoc
class _$MeasurementCrudStateCopyWithImpl<$Res,
        $Val extends MeasurementCrudState>
    implements $MeasurementCrudStateCopyWith<$Res> {
  _$MeasurementCrudStateCopyWithImpl(this._value, this._then);

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
    extends _$MeasurementCrudStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial {
  const _$InitialImpl() : super._();

  @override
  String toString() {
    return 'MeasurementCrudState.initial()';
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
    required TResult Function(Measurement? measurement) loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(Measurement? measurement)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(Measurement? measurement)? loaded,
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

abstract class _Initial extends MeasurementCrudState {
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
    extends _$MeasurementCrudStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading {
  const _$LoadingImpl() : super._();

  @override
  String toString() {
    return 'MeasurementCrudState.loading()';
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
    required TResult Function(Measurement? measurement) loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(Measurement? measurement)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(Measurement? measurement)? loaded,
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

abstract class _Loading extends MeasurementCrudState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({Measurement? measurement});

  $MeasurementCopyWith<$Res>? get measurement;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MeasurementCrudStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? measurement = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == measurement
          ? _value.measurement
          : measurement // ignore: cast_nullable_to_non_nullable
              as Measurement?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MeasurementCopyWith<$Res>? get measurement {
    if (_value.measurement == null) {
      return null;
    }

    return $MeasurementCopyWith<$Res>(_value.measurement!, (value) {
      return _then(_value.copyWith(measurement: value));
    });
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(this.measurement) : super._();

  @override
  final Measurement? measurement;

  @override
  String toString() {
    return 'MeasurementCrudState.loaded(measurement: $measurement)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.measurement, measurement) ||
                other.measurement == measurement));
  }

  @override
  int get hashCode => Object.hash(runtimeType, measurement);

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
    required TResult Function(Measurement? measurement) loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(measurement);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(Measurement? measurement)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(measurement);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(Measurement? measurement)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(measurement);
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

abstract class _Loaded extends MeasurementCrudState {
  const factory _Loaded(final Measurement? measurement) = _$LoadedImpl;
  const _Loaded._() : super._();

  Measurement? get measurement;
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
    extends _$MeasurementCrudStateCopyWithImpl<$Res, _$ErrorImpl>
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

class _$ErrorImpl extends _Error {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString() {
    return 'MeasurementCrudState.error(error: $error)';
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
    required TResult Function(Measurement? measurement) loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(Measurement? measurement)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(Measurement? measurement)? loaded,
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

abstract class _Error extends MeasurementCrudState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
