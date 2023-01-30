// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'muster_roll_estimate.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$MusterRollEstimateEvent {
  int get startDate => throw _privateConstructorUsedError;
  int get endDate => throw _privateConstructorUsedError;
  String get registerId => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        estimate,
    required TResult Function(
            int startDate, int endDate, String registerId, String tenantId)
        viewEstimate,
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
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(EstimateMusterRollEvent value) estimate,
    required TResult Function(ViewEstimateMusterRollEvent value) viewEstimate,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterRollEstimateEventCopyWith<MusterRollEstimateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollEstimateEventCopyWith<$Res> {
  factory $MusterRollEstimateEventCopyWith(MusterRollEstimateEvent value,
          $Res Function(MusterRollEstimateEvent) then) =
      _$MusterRollEstimateEventCopyWithImpl<$Res, MusterRollEstimateEvent>;
  @useResult
  $Res call({int startDate, int endDate, String registerId, String tenantId});
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

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? startDate = null,
    Object? endDate = null,
    Object? registerId = null,
    Object? tenantId = null,
  }) {
    return _then(_value.copyWith(
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
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$EstimateMusterRollEventCopyWith<$Res>
    implements $MusterRollEstimateEventCopyWith<$Res> {
  factory _$$EstimateMusterRollEventCopyWith(_$EstimateMusterRollEvent value,
          $Res Function(_$EstimateMusterRollEvent) then) =
      __$$EstimateMusterRollEventCopyWithImpl<$Res>;
  @override
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
  }) {
    return estimate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
  }) {
    return estimate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
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

  @override
  int get startDate;
  @override
  int get endDate;
  @override
  String get registerId;
  @override
  String get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$EstimateMusterRollEventCopyWith<_$EstimateMusterRollEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ViewEstimateMusterRollEventCopyWith<$Res>
    implements $MusterRollEstimateEventCopyWith<$Res> {
  factory _$$ViewEstimateMusterRollEventCopyWith(
          _$ViewEstimateMusterRollEvent value,
          $Res Function(_$ViewEstimateMusterRollEvent) then) =
      __$$ViewEstimateMusterRollEventCopyWithImpl<$Res>;
  @override
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
  }) {
    return viewEstimate(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(EstimateMusterRollEvent value)? estimate,
    TResult? Function(ViewEstimateMusterRollEvent value)? viewEstimate,
  }) {
    return viewEstimate?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(EstimateMusterRollEvent value)? estimate,
    TResult Function(ViewEstimateMusterRollEvent value)? viewEstimate,
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

  @override
  int get startDate;
  @override
  int get endDate;
  @override
  String get registerId;
  @override
  String get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$ViewEstimateMusterRollEventCopyWith<_$ViewEstimateMusterRollEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterRollEstimateState {
  bool get loading => throw _privateConstructorUsedError;
  MusterRollsModel? get musterRollsModel => throw _privateConstructorUsedError;
  MusterRollsModel? get viewMusterRollsModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterRollEstimateStateCopyWith<MusterRollEstimateState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollEstimateStateCopyWith<$Res> {
  factory $MusterRollEstimateStateCopyWith(MusterRollEstimateState value,
          $Res Function(MusterRollEstimateState) then) =
      _$MusterRollEstimateStateCopyWithImpl<$Res, MusterRollEstimateState>;
  @useResult
  $Res call(
      {bool loading,
      MusterRollsModel? musterRollsModel,
      MusterRollsModel? viewMusterRollsModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
  $MusterRollsModelCopyWith<$Res>? get viewMusterRollsModel;
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

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
    Object? viewMusterRollsModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterRollsModel: freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
      viewMusterRollsModel: freezed == viewMusterRollsModel
          ? _value.viewMusterRollsModel
          : viewMusterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel {
    if (_value.musterRollsModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.musterRollsModel!, (value) {
      return _then(_value.copyWith(musterRollsModel: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get viewMusterRollsModel {
    if (_value.viewMusterRollsModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.viewMusterRollsModel!,
        (value) {
      return _then(_value.copyWith(viewMusterRollsModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_MusterRollEstimateStateCopyWith<$Res>
    implements $MusterRollEstimateStateCopyWith<$Res> {
  factory _$$_MusterRollEstimateStateCopyWith(_$_MusterRollEstimateState value,
          $Res Function(_$_MusterRollEstimateState) then) =
      __$$_MusterRollEstimateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {bool loading,
      MusterRollsModel? musterRollsModel,
      MusterRollsModel? viewMusterRollsModel});

  @override
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
  @override
  $MusterRollsModelCopyWith<$Res>? get viewMusterRollsModel;
}

/// @nodoc
class __$$_MusterRollEstimateStateCopyWithImpl<$Res>
    extends _$MusterRollEstimateStateCopyWithImpl<$Res,
        _$_MusterRollEstimateState>
    implements _$$_MusterRollEstimateStateCopyWith<$Res> {
  __$$_MusterRollEstimateStateCopyWithImpl(_$_MusterRollEstimateState _value,
      $Res Function(_$_MusterRollEstimateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterRollsModel = freezed,
    Object? viewMusterRollsModel = freezed,
  }) {
    return _then(_$_MusterRollEstimateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterRollsModel: freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
      viewMusterRollsModel: freezed == viewMusterRollsModel
          ? _value.viewMusterRollsModel
          : viewMusterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ));
  }
}

/// @nodoc

class _$_MusterRollEstimateState extends _MusterRollEstimateState {
  const _$_MusterRollEstimateState(
      {this.loading = false, this.musterRollsModel, this.viewMusterRollsModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final MusterRollsModel? musterRollsModel;
  @override
  final MusterRollsModel? viewMusterRollsModel;

  @override
  String toString() {
    return 'MusterRollEstimateState(loading: $loading, musterRollsModel: $musterRollsModel, viewMusterRollsModel: $viewMusterRollsModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterRollEstimateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel) &&
            (identical(other.viewMusterRollsModel, viewMusterRollsModel) ||
                other.viewMusterRollsModel == viewMusterRollsModel));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, loading, musterRollsModel, viewMusterRollsModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterRollEstimateStateCopyWith<_$_MusterRollEstimateState>
      get copyWith =>
          __$$_MusterRollEstimateStateCopyWithImpl<_$_MusterRollEstimateState>(
              this, _$identity);
}

abstract class _MusterRollEstimateState extends MusterRollEstimateState {
  const factory _MusterRollEstimateState(
          {final bool loading,
          final MusterRollsModel? musterRollsModel,
          final MusterRollsModel? viewMusterRollsModel}) =
      _$_MusterRollEstimateState;
  const _MusterRollEstimateState._() : super._();

  @override
  bool get loading;
  @override
  MusterRollsModel? get musterRollsModel;
  @override
  MusterRollsModel? get viewMusterRollsModel;
  @override
  @JsonKey(ignore: true)
  _$$_MusterRollEstimateStateCopyWith<_$_MusterRollEstimateState>
      get copyWith => throw _privateConstructorUsedError;
}
