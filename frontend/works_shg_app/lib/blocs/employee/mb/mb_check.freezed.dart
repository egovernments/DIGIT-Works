// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'mb_check.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MeasurementCheckBlocEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementCheckEvent value) create,
    required TResult Function(MeasurementCheckBlocClearEvent value) clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementCheckEvent value)? create,
    TResult? Function(MeasurementCheckBlocClearEvent value)? clear,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementCheckEvent value)? create,
    TResult Function(MeasurementCheckBlocClearEvent value)? clear,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MeasurementCheckBlocEventCopyWith<$Res> {
  factory $MeasurementCheckBlocEventCopyWith(MeasurementCheckBlocEvent value,
          $Res Function(MeasurementCheckBlocEvent) then) =
      _$MeasurementCheckBlocEventCopyWithImpl<$Res, MeasurementCheckBlocEvent>;
}

/// @nodoc
class _$MeasurementCheckBlocEventCopyWithImpl<$Res,
        $Val extends MeasurementCheckBlocEvent>
    implements $MeasurementCheckBlocEventCopyWith<$Res> {
  _$MeasurementCheckBlocEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$MeasurementCheckEventImplCopyWith<$Res> {
  factory _$$MeasurementCheckEventImplCopyWith(
          _$MeasurementCheckEventImpl value,
          $Res Function(_$MeasurementCheckEventImpl) then) =
      __$$MeasurementCheckEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId,
      String contractNumber,
      String measurementNumber,
      MBScreen screenType});
}

/// @nodoc
class __$$MeasurementCheckEventImplCopyWithImpl<$Res>
    extends _$MeasurementCheckBlocEventCopyWithImpl<$Res,
        _$MeasurementCheckEventImpl>
    implements _$$MeasurementCheckEventImplCopyWith<$Res> {
  __$$MeasurementCheckEventImplCopyWithImpl(_$MeasurementCheckEventImpl _value,
      $Res Function(_$MeasurementCheckEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? contractNumber = null,
    Object? measurementNumber = null,
    Object? screenType = null,
  }) {
    return _then(_$MeasurementCheckEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      contractNumber: null == contractNumber
          ? _value.contractNumber
          : contractNumber // ignore: cast_nullable_to_non_nullable
              as String,
      measurementNumber: null == measurementNumber
          ? _value.measurementNumber
          : measurementNumber // ignore: cast_nullable_to_non_nullable
              as String,
      screenType: null == screenType
          ? _value.screenType
          : screenType // ignore: cast_nullable_to_non_nullable
              as MBScreen,
    ));
  }
}

/// @nodoc

class _$MeasurementCheckEventImpl implements MeasurementCheckEvent {
  const _$MeasurementCheckEventImpl(
      {required this.tenantId,
      required this.contractNumber,
      required this.measurementNumber,
      required this.screenType});

  @override
  final String tenantId;
  @override
  final String contractNumber;
  @override
  final String measurementNumber;
  @override
  final MBScreen screenType;

  @override
  String toString() {
    return 'MeasurementCheckBlocEvent.create(tenantId: $tenantId, contractNumber: $contractNumber, measurementNumber: $measurementNumber, screenType: $screenType)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementCheckEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.contractNumber, contractNumber) ||
                other.contractNumber == contractNumber) &&
            (identical(other.measurementNumber, measurementNumber) ||
                other.measurementNumber == measurementNumber) &&
            (identical(other.screenType, screenType) ||
                other.screenType == screenType));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, contractNumber, measurementNumber, screenType);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$MeasurementCheckEventImplCopyWith<_$MeasurementCheckEventImpl>
      get copyWith => __$$MeasurementCheckEventImplCopyWithImpl<
          _$MeasurementCheckEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
  }) {
    return create(tenantId, contractNumber, measurementNumber, screenType);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
  }) {
    return create?.call(
        tenantId, contractNumber, measurementNumber, screenType);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
    TResult Function()? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, contractNumber, measurementNumber, screenType);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(MeasurementCheckEvent value) create,
    required TResult Function(MeasurementCheckBlocClearEvent value) clear,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementCheckEvent value)? create,
    TResult? Function(MeasurementCheckBlocClearEvent value)? clear,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementCheckEvent value)? create,
    TResult Function(MeasurementCheckBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class MeasurementCheckEvent implements MeasurementCheckBlocEvent {
  const factory MeasurementCheckEvent(
      {required final String tenantId,
      required final String contractNumber,
      required final String measurementNumber,
      required final MBScreen screenType}) = _$MeasurementCheckEventImpl;

  String get tenantId;
  String get contractNumber;
  String get measurementNumber;
  MBScreen get screenType;
  @JsonKey(ignore: true)
  _$$MeasurementCheckEventImplCopyWith<_$MeasurementCheckEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$MeasurementCheckBlocClearEventImplCopyWith<$Res> {
  factory _$$MeasurementCheckBlocClearEventImplCopyWith(
          _$MeasurementCheckBlocClearEventImpl value,
          $Res Function(_$MeasurementCheckBlocClearEventImpl) then) =
      __$$MeasurementCheckBlocClearEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$MeasurementCheckBlocClearEventImplCopyWithImpl<$Res>
    extends _$MeasurementCheckBlocEventCopyWithImpl<$Res,
        _$MeasurementCheckBlocClearEventImpl>
    implements _$$MeasurementCheckBlocClearEventImplCopyWith<$Res> {
  __$$MeasurementCheckBlocClearEventImplCopyWithImpl(
      _$MeasurementCheckBlocClearEventImpl _value,
      $Res Function(_$MeasurementCheckBlocClearEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$MeasurementCheckBlocClearEventImpl
    implements MeasurementCheckBlocClearEvent {
  const _$MeasurementCheckBlocClearEventImpl();

  @override
  String toString() {
    return 'MeasurementCheckBlocEvent.clear()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$MeasurementCheckBlocClearEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)
        create,
    required TResult Function() clear,
  }) {
    return clear();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
    TResult? Function()? clear,
  }) {
    return clear?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String contractNumber,
            String measurementNumber, MBScreen screenType)?
        create,
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
    required TResult Function(MeasurementCheckEvent value) create,
    required TResult Function(MeasurementCheckBlocClearEvent value) clear,
  }) {
    return clear(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(MeasurementCheckEvent value)? create,
    TResult? Function(MeasurementCheckBlocClearEvent value)? clear,
  }) {
    return clear?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(MeasurementCheckEvent value)? create,
    TResult Function(MeasurementCheckBlocClearEvent value)? clear,
    required TResult orElse(),
  }) {
    if (clear != null) {
      return clear(this);
    }
    return orElse();
  }
}

abstract class MeasurementCheckBlocClearEvent
    implements MeasurementCheckBlocEvent {
  const factory MeasurementCheckBlocClearEvent() =
      _$MeasurementCheckBlocClearEventImpl;
}

/// @nodoc
mixin _$MeasurementCheckState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
        loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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
abstract class $MeasurementCheckStateCopyWith<$Res> {
  factory $MeasurementCheckStateCopyWith(MeasurementCheckState value,
          $Res Function(MeasurementCheckState) then) =
      _$MeasurementCheckStateCopyWithImpl<$Res, MeasurementCheckState>;
}

/// @nodoc
class _$MeasurementCheckStateCopyWithImpl<$Res,
        $Val extends MeasurementCheckState>
    implements $MeasurementCheckStateCopyWith<$Res> {
  _$MeasurementCheckStateCopyWithImpl(this._value, this._then);

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
    extends _$MeasurementCheckStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'MeasurementCheckState.initial()';
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
    required TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)
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
    TResult? Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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
    TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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

abstract class _Initial extends MeasurementCheckState {
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
    extends _$MeasurementCheckStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'MeasurementCheckState.loading()';
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
    required TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)
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
    TResult? Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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
    TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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

abstract class _Loading extends MeasurementCheckState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {bool? workOrderStatus,
      bool? estimateStatus,
      bool? existingMB,
      String? workOrderNumber});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MeasurementCheckStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? workOrderStatus = freezed,
    Object? estimateStatus = freezed,
    Object? existingMB = freezed,
    Object? workOrderNumber = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == workOrderStatus
          ? _value.workOrderStatus
          : workOrderStatus // ignore: cast_nullable_to_non_nullable
              as bool?,
      freezed == estimateStatus
          ? _value.estimateStatus
          : estimateStatus // ignore: cast_nullable_to_non_nullable
              as bool?,
      freezed == existingMB
          ? _value.existingMB
          : existingMB // ignore: cast_nullable_to_non_nullable
              as bool?,
      freezed == workOrderNumber
          ? _value.workOrderNumber
          : workOrderNumber // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(this.workOrderStatus, this.estimateStatus, this.existingMB,
      this.workOrderNumber)
      : super._();

  @override
  final bool? workOrderStatus;
  @override
  final bool? estimateStatus;
  @override
  final bool? existingMB;
  @override
  final String? workOrderNumber;

  @override
  String toString() {
    return 'MeasurementCheckState.loaded(workOrderStatus: $workOrderStatus, estimateStatus: $estimateStatus, existingMB: $existingMB, workOrderNumber: $workOrderNumber)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.workOrderStatus, workOrderStatus) ||
                other.workOrderStatus == workOrderStatus) &&
            (identical(other.estimateStatus, estimateStatus) ||
                other.estimateStatus == estimateStatus) &&
            (identical(other.existingMB, existingMB) ||
                other.existingMB == existingMB) &&
            (identical(other.workOrderNumber, workOrderNumber) ||
                other.workOrderNumber == workOrderNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, workOrderStatus, estimateStatus,
      existingMB, workOrderNumber);

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
    required TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(workOrderStatus, estimateStatus, existingMB, workOrderNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
        loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(
        workOrderStatus, estimateStatus, existingMB, workOrderNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
        loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(
          workOrderStatus, estimateStatus, existingMB, workOrderNumber);
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

abstract class _Loaded extends MeasurementCheckState {
  const factory _Loaded(final bool? workOrderStatus, final bool? estimateStatus,
      final bool? existingMB, final String? workOrderNumber) = _$LoadedImpl;
  const _Loaded._() : super._();

  bool? get workOrderStatus;
  bool? get estimateStatus;
  bool? get existingMB;
  String? get workOrderNumber;
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
    extends _$MeasurementCheckStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'MeasurementCheckState.error(error: $error)';
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
    required TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)
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
    TResult? Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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
    TResult Function(bool? workOrderStatus, bool? estimateStatus,
            bool? existingMB, String? workOrderNumber)?
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

abstract class _Error extends MeasurementCheckState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
