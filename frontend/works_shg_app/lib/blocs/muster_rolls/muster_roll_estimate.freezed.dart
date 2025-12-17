// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_roll_estimate.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MusterRollEstimateEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        estimate,
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        viewEstimate,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(EstimateMusterRollEvent value) estimate,
    required TResult Function(ViewEstimateMusterRollEvent value) viewEstimate,
    required TResult Function(DisposeEstimateMusterRollEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult? Function(DisposeEstimateMusterRollEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult Function(DisposeEstimateMusterRollEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollEstimateEventCopyWith<$Res> {
  factory $MusterRollEstimateEventCopyWith(MusterRollEstimateEvent value,
          $Res Function(MusterRollEstimateEvent) then) =
      _$MusterRollEstimateEventCopyWithImpl<$Res, MusterRollEstimateEvent>;
}

/// @nodoc
class _$MusterRollEstimateEventCopyWithImpl<$Res,
        $Val extends MusterRollEstimateEvent>
    implements $MusterRollEstimateEventCopyWith<$Res> {
  _$MusterRollEstimateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$EstimateMusterRollEventImplCopyWith<$Res> {
  factory _$$EstimateMusterRollEventImplCopyWith(
          _$EstimateMusterRollEventImpl value,
          $Res Function(_$EstimateMusterRollEventImpl) then) =
      __$$EstimateMusterRollEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({int startDate, int endDate, String registerId, String tenantId});
}

/// @nodoc
class __$$EstimateMusterRollEventImplCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$EstimateMusterRollEventImpl>
    implements _$$EstimateMusterRollEventImplCopyWith<$Res> {
  __$$EstimateMusterRollEventImplCopyWithImpl(
      _$EstimateMusterRollEventImpl _value,
      $Res Function(_$EstimateMusterRollEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = null,
    Object? endDate = null,
    Object? registerId = null,
    Object? tenantId = null,
  }) {
    return _then(_$EstimateMusterRollEventImpl(
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$EstimateMusterRollEventImpl implements EstimateMusterRollEvent {
  const _$EstimateMusterRollEventImpl(
      {required this.startDate,
      required this.endDate,
      required this.registerId,
      required this.tenantId});

  @override
  final int startDate;
  @override
  final int endDate;
  @override
  final String registerId;
  @override
  final String tenantId;

  @override
  String toString() {
    return 'MusterRollEstimateEvent.estimate(startDate: $startDate, endDate: $endDate, registerId: $registerId, tenantId: $tenantId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateMusterRollEventImpl &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, startDate, endDate, registerId, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$EstimateMusterRollEventImplCopyWith<_$EstimateMusterRollEventImpl>
      get copyWith => __$$EstimateMusterRollEventImplCopyWithImpl<
          _$EstimateMusterRollEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        estimate,
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        viewEstimate,
    required TResult Function() dispose,
  }) {
    return estimate(startDate, endDate, registerId, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult? Function()? dispose,
  }) {
    return estimate?.call(startDate, endDate, registerId, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (estimate != null) {
      return estimate(startDate, endDate, registerId, tenantId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(EstimateMusterRollEvent value) estimate,
    required TResult Function(ViewEstimateMusterRollEvent value) viewEstimate,
    required TResult Function(DisposeEstimateMusterRollEvent value) dispose,
  }) {
    return estimate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult? Function(DisposeEstimateMusterRollEvent value)? dispose,
  }) {
    return estimate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult Function(DisposeEstimateMusterRollEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (estimate != null) {
      return estimate(this);
    }
    return orElse();
  }
}

abstract class EstimateMusterRollEvent implements MusterRollEstimateEvent {
  const factory EstimateMusterRollEvent(
      {required final int startDate,
      required final int endDate,
      required final String registerId,
      required final String tenantId}) = _$EstimateMusterRollEventImpl;

  int get startDate;
  int get endDate;
  String get registerId;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$EstimateMusterRollEventImplCopyWith<_$EstimateMusterRollEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ViewEstimateMusterRollEventImplCopyWith<$Res> {
  factory _$$ViewEstimateMusterRollEventImplCopyWith(
          _$ViewEstimateMusterRollEventImpl value,
          $Res Function(_$ViewEstimateMusterRollEventImpl) then) =
      __$$ViewEstimateMusterRollEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({int startDate, int endDate, String registerId, String tenantId});
}

/// @nodoc
class __$$ViewEstimateMusterRollEventImplCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$ViewEstimateMusterRollEventImpl>
    implements _$$ViewEstimateMusterRollEventImplCopyWith<$Res> {
  __$$ViewEstimateMusterRollEventImplCopyWithImpl(
      _$ViewEstimateMusterRollEventImpl _value,
      $Res Function(_$ViewEstimateMusterRollEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = null,
    Object? endDate = null,
    Object? registerId = null,
    Object? tenantId = null,
  }) {
    return _then(_$ViewEstimateMusterRollEventImpl(
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$ViewEstimateMusterRollEventImpl implements ViewEstimateMusterRollEvent {
  const _$ViewEstimateMusterRollEventImpl(
      {required this.startDate,
      required this.endDate,
      required this.registerId,
      required this.tenantId});

  @override
  final int startDate;
  @override
  final int endDate;
  @override
  final String registerId;
  @override
  final String tenantId;

  @override
  String toString() {
    return 'MusterRollEstimateEvent.viewEstimate(startDate: $startDate, endDate: $endDate, registerId: $registerId, tenantId: $tenantId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ViewEstimateMusterRollEventImpl &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, startDate, endDate, registerId, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ViewEstimateMusterRollEventImplCopyWith<_$ViewEstimateMusterRollEventImpl>
      get copyWith => __$$ViewEstimateMusterRollEventImplCopyWithImpl<
          _$ViewEstimateMusterRollEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        estimate,
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        viewEstimate,
    required TResult Function() dispose,
  }) {
    return viewEstimate(startDate, endDate, registerId, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult? Function()? dispose,
  }) {
    return viewEstimate?.call(startDate, endDate, registerId, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (viewEstimate != null) {
      return viewEstimate(startDate, endDate, registerId, tenantId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(EstimateMusterRollEvent value) estimate,
    required TResult Function(ViewEstimateMusterRollEvent value) viewEstimate,
    required TResult Function(DisposeEstimateMusterRollEvent value) dispose,
  }) {
    return viewEstimate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult? Function(DisposeEstimateMusterRollEvent value)? dispose,
  }) {
    return viewEstimate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult Function(DisposeEstimateMusterRollEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (viewEstimate != null) {
      return viewEstimate(this);
    }
    return orElse();
  }
}

abstract class ViewEstimateMusterRollEvent implements MusterRollEstimateEvent {
  const factory ViewEstimateMusterRollEvent(
      {required final int startDate,
      required final int endDate,
      required final String registerId,
      required final String tenantId}) = _$ViewEstimateMusterRollEventImpl;

  int get startDate;
  int get endDate;
  String get registerId;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$ViewEstimateMusterRollEventImplCopyWith<_$ViewEstimateMusterRollEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeEstimateMusterRollEventImplCopyWith<$Res> {
  factory _$$DisposeEstimateMusterRollEventImplCopyWith(
          _$DisposeEstimateMusterRollEventImpl value,
          $Res Function(_$DisposeEstimateMusterRollEventImpl) then) =
      __$$DisposeEstimateMusterRollEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeEstimateMusterRollEventImplCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$DisposeEstimateMusterRollEventImpl>
    implements _$$DisposeEstimateMusterRollEventImplCopyWith<$Res> {
  __$$DisposeEstimateMusterRollEventImplCopyWithImpl(
      _$DisposeEstimateMusterRollEventImpl _value,
      $Res Function(_$DisposeEstimateMusterRollEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeEstimateMusterRollEventImpl
    implements DisposeEstimateMusterRollEvent {
  const _$DisposeEstimateMusterRollEventImpl();

  @override
  String toString() {
    return 'MusterRollEstimateEvent.dispose()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeEstimateMusterRollEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        estimate,
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        viewEstimate,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult? Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        estimate,
    TResult Function(
            int startDate, int endDate, String registerId, String tenantId)?
        viewEstimate,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(EstimateMusterRollEvent value) estimate,
    required TResult Function(ViewEstimateMusterRollEvent value) viewEstimate,
    required TResult Function(DisposeEstimateMusterRollEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult? Function(DisposeEstimateMusterRollEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    TResult Function(DisposeEstimateMusterRollEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeEstimateMusterRollEvent
    implements MusterRollEstimateEvent {
  const factory DisposeEstimateMusterRollEvent() =
      _$DisposeEstimateMusterRollEventImpl;
}

/// @nodoc
mixin _$MusterRollEstimateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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
abstract class $MusterRollEstimateStateCopyWith<$Res> {
  factory $MusterRollEstimateStateCopyWith(MusterRollEstimateState value,
          $Res Function(MusterRollEstimateState) then) =
      _$MusterRollEstimateStateCopyWithImpl<$Res, MusterRollEstimateState>;
}

/// @nodoc
class _$MusterRollEstimateStateCopyWithImpl<$Res,
        $Val extends MusterRollEstimateState>
    implements $MusterRollEstimateStateCopyWith<$Res> {
  _$MusterRollEstimateStateCopyWithImpl(this._value, this._then);

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
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'MusterRollEstimateState.initial()';
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
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
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
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Initial extends MusterRollEstimateState {
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
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'MusterRollEstimateState.loading()';
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
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
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
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Loading extends MusterRollEstimateState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({EstimateMusterRollsModel? musterRollsModel});

  $EstimateMusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollsModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as EstimateMusterRollsModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $EstimateMusterRollsModelCopyWith<$Res>? get musterRollsModel {
    if (_value.musterRollsModel == null) {
      return null;
    }

    return $EstimateMusterRollsModelCopyWith<$Res>(_value.musterRollsModel!,
        (value) {
      return _then(_value.copyWith(musterRollsModel: value));
    });
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(this.musterRollsModel) : super._();

  @override
  final EstimateMusterRollsModel? musterRollsModel;

  @override
  String toString() {
    return 'MusterRollEstimateState.loaded(musterRollsModel: $musterRollsModel)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, musterRollsModel);

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
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(musterRollsModel);
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

abstract class _Loaded extends MusterRollEstimateState {
  const factory _Loaded(final EstimateMusterRollsModel? musterRollsModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  EstimateMusterRollsModel? get musterRollsModel;
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
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'MusterRollEstimateState.error(error: $error)';
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
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
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
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Error extends MusterRollEstimateState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
