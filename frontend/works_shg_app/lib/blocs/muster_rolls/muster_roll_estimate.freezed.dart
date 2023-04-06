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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

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
abstract class _$$EstimateMusterRollEventCopyWith<$Res> {
  factory _$$EstimateMusterRollEventCopyWith(_$EstimateMusterRollEvent value,
          $Res Function(_$EstimateMusterRollEvent) then) =
      __$$EstimateMusterRollEventCopyWithImpl<$Res>;
  @useResult
  $Res call({int startDate, int endDate, String registerId, String tenantId});
}

/// @nodoc
class __$$EstimateMusterRollEventCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$EstimateMusterRollEvent>
    implements _$$EstimateMusterRollEventCopyWith<$Res> {
  __$$EstimateMusterRollEventCopyWithImpl(_$EstimateMusterRollEvent _value,
      $Res Function(_$EstimateMusterRollEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = null,
    Object? endDate = null,
    Object? registerId = null,
    Object? tenantId = null,
  }) {
    return _then(_$EstimateMusterRollEvent(
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

class _$EstimateMusterRollEvent implements EstimateMusterRollEvent {
  const _$EstimateMusterRollEvent(
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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$EstimateMusterRollEvent &&
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
  _$$EstimateMusterRollEventCopyWith<_$EstimateMusterRollEvent> get copyWith =>
      __$$EstimateMusterRollEventCopyWithImpl<_$EstimateMusterRollEvent>(
          this, _$identity);

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
      required final String tenantId}) = _$EstimateMusterRollEvent;

  int get startDate;
  int get endDate;
  String get registerId;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$EstimateMusterRollEventCopyWith<_$EstimateMusterRollEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ViewEstimateMusterRollEventCopyWith<$Res> {
  factory _$$ViewEstimateMusterRollEventCopyWith(
          _$ViewEstimateMusterRollEvent value,
          $Res Function(_$ViewEstimateMusterRollEvent) then) =
      __$$ViewEstimateMusterRollEventCopyWithImpl<$Res>;
  @useResult
  $Res call({int startDate, int endDate, String registerId, String tenantId});
}

/// @nodoc
class __$$ViewEstimateMusterRollEventCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$ViewEstimateMusterRollEvent>
    implements _$$ViewEstimateMusterRollEventCopyWith<$Res> {
  __$$ViewEstimateMusterRollEventCopyWithImpl(
      _$ViewEstimateMusterRollEvent _value,
      $Res Function(_$ViewEstimateMusterRollEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = null,
    Object? endDate = null,
    Object? registerId = null,
    Object? tenantId = null,
  }) {
    return _then(_$ViewEstimateMusterRollEvent(
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

class _$ViewEstimateMusterRollEvent implements ViewEstimateMusterRollEvent {
  const _$ViewEstimateMusterRollEvent(
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
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ViewEstimateMusterRollEvent &&
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
  _$$ViewEstimateMusterRollEventCopyWith<_$ViewEstimateMusterRollEvent>
      get copyWith => __$$ViewEstimateMusterRollEventCopyWithImpl<
          _$ViewEstimateMusterRollEvent>(this, _$identity);

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
      required final String tenantId}) = _$ViewEstimateMusterRollEvent;

  int get startDate;
  int get endDate;
  String get registerId;
  String get tenantId;
  @JsonKey(ignore: true)
  _$$ViewEstimateMusterRollEventCopyWith<_$ViewEstimateMusterRollEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeEstimateMusterRollEventCopyWith<$Res> {
  factory _$$DisposeEstimateMusterRollEventCopyWith(
          _$DisposeEstimateMusterRollEvent value,
          $Res Function(_$DisposeEstimateMusterRollEvent) then) =
      __$$DisposeEstimateMusterRollEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeEstimateMusterRollEventCopyWithImpl<$Res>
    extends _$MusterRollEstimateEventCopyWithImpl<$Res,
        _$DisposeEstimateMusterRollEvent>
    implements _$$DisposeEstimateMusterRollEventCopyWith<$Res> {
  __$$DisposeEstimateMusterRollEventCopyWithImpl(
      _$DisposeEstimateMusterRollEvent _value,
      $Res Function(_$DisposeEstimateMusterRollEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeEstimateMusterRollEvent
    implements DisposeEstimateMusterRollEvent {
  const _$DisposeEstimateMusterRollEvent();

  @override
  String toString() {
    return 'MusterRollEstimateEvent.dispose()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeEstimateMusterRollEvent);
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
      _$DisposeEstimateMusterRollEvent;
}

/// @nodoc
mixin _$MusterRollEstimateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
        loaded,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
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
abstract class _$$_InitialCopyWith<$Res> {
  factory _$$_InitialCopyWith(
          _$_Initial value, $Res Function(_$_Initial) then) =
      __$$_InitialCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_InitialCopyWithImpl<$Res>
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'MusterRollEstimateState.initial()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Initial);
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
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends MusterRollEstimateState {
  const factory _Initial() = _$_Initial;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$_LoadingCopyWith<$Res> {
  factory _$$_LoadingCopyWith(
          _$_Loading value, $Res Function(_$_Loading) then) =
      __$$_LoadingCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_LoadingCopyWithImpl<$Res>
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'MusterRollEstimateState.loading()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Loading);
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
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends MusterRollEstimateState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({EstimateMusterRollsModel? musterRollsModel});

  $EstimateMusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$MusterRollEstimateStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollsModel = freezed,
  }) {
    return _then(_$_Loaded(
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

class _$_Loaded extends _Loaded {
  const _$_Loaded(this.musterRollsModel) : super._();

  @override
  final EstimateMusterRollsModel? musterRollsModel;

  @override
  String toString() {
    return 'MusterRollEstimateState.loaded(musterRollsModel: $musterRollsModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, musterRollsModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      __$$_LoadedCopyWithImpl<_$_Loaded>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(EstimateMusterRollsModel? musterRollsModel)
        loaded,
  }) {
    return loaded(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
  }) {
    return loaded?.call(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(EstimateMusterRollsModel? musterRollsModel)? loaded,
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
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
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
      _$_Loaded;
  const _Loaded._() : super._();

  EstimateMusterRollsModel? get musterRollsModel;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      throw _privateConstructorUsedError;
}
