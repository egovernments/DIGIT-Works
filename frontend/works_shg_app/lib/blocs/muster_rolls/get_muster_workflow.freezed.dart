// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'get_muster_workflow.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$MusterGetWorkflowEvent {
  String get tenantId => throw _privateConstructorUsedError;
  String get musterRollNumber => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String musterRollNumber) get,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber)? get,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber)? get,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterGetWorkflowEventCopyWith<MusterGetWorkflowEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterGetWorkflowEventCopyWith<$Res> {
  factory $MusterGetWorkflowEventCopyWith(MusterGetWorkflowEvent value,
          $Res Function(MusterGetWorkflowEvent) then) =
      _$MusterGetWorkflowEventCopyWithImpl<$Res, MusterGetWorkflowEvent>;
  @useResult
  $Res call({String tenantId, String musterRollNumber});
}

/// @nodoc
class _$MusterGetWorkflowEventCopyWithImpl<$Res,
        $Val extends MusterGetWorkflowEvent>
    implements $MusterGetWorkflowEventCopyWith<$Res> {
  _$MusterGetWorkflowEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? musterRollNumber = null,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      musterRollNumber: null == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$GetMusterWorkflowEventCopyWith<$Res>
    implements $MusterGetWorkflowEventCopyWith<$Res> {
  factory _$$GetMusterWorkflowEventCopyWith(_$GetMusterWorkflowEvent value,
          $Res Function(_$GetMusterWorkflowEvent) then) =
      __$$GetMusterWorkflowEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String tenantId, String musterRollNumber});
}

/// @nodoc
class __$$GetMusterWorkflowEventCopyWithImpl<$Res>
    extends _$MusterGetWorkflowEventCopyWithImpl<$Res, _$GetMusterWorkflowEvent>
    implements _$$GetMusterWorkflowEventCopyWith<$Res> {
  __$$GetMusterWorkflowEventCopyWithImpl(_$GetMusterWorkflowEvent _value,
      $Res Function(_$GetMusterWorkflowEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? musterRollNumber = null,
  }) {
    return _then(_$GetMusterWorkflowEvent(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      musterRollNumber: null == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$GetMusterWorkflowEvent implements GetMusterWorkflowEvent {
  const _$GetMusterWorkflowEvent(
      {required this.tenantId, required this.musterRollNumber});

  @override
  final String tenantId;
  @override
  final String musterRollNumber;

  @override
  String toString() {
    return 'MusterGetWorkflowEvent.get(tenantId: $tenantId, musterRollNumber: $musterRollNumber)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GetMusterWorkflowEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.musterRollNumber, musterRollNumber) ||
                other.musterRollNumber == musterRollNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, musterRollNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$GetMusterWorkflowEventCopyWith<_$GetMusterWorkflowEvent> get copyWith =>
      __$$GetMusterWorkflowEventCopyWithImpl<_$GetMusterWorkflowEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String musterRollNumber) get,
  }) {
    return get(tenantId, musterRollNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber)? get,
  }) {
    return get?.call(tenantId, musterRollNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber)? get,
    required TResult orElse(),
  }) {
    if (get != null) {
      return get(tenantId, musterRollNumber);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
  }) {
    return get(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
  }) {
    return get?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    required TResult orElse(),
  }) {
    if (get != null) {
      return get(this);
    }
    return orElse();
  }
}

abstract class GetMusterWorkflowEvent implements MusterGetWorkflowEvent {
  const factory GetMusterWorkflowEvent(
      {required final String tenantId,
      required final String musterRollNumber}) = _$GetMusterWorkflowEvent;

  @override
  String get tenantId;
  @override
  String get musterRollNumber;
  @override
  @JsonKey(ignore: true)
  _$$GetMusterWorkflowEventCopyWith<_$GetMusterWorkflowEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterGetWorkflowState {
  bool get loading => throw _privateConstructorUsedError;
  MusterWorkFlowModel? get musterWorkFlowModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterGetWorkflowStateCopyWith<MusterGetWorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterGetWorkflowStateCopyWith<$Res> {
  factory $MusterGetWorkflowStateCopyWith(MusterGetWorkflowState value,
          $Res Function(MusterGetWorkflowState) then) =
      _$MusterGetWorkflowStateCopyWithImpl<$Res, MusterGetWorkflowState>;
  @useResult
  $Res call({bool loading, MusterWorkFlowModel? musterWorkFlowModel});

  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel;
}

/// @nodoc
class _$MusterGetWorkflowStateCopyWithImpl<$Res,
        $Val extends MusterGetWorkflowState>
    implements $MusterGetWorkflowStateCopyWith<$Res> {
  _$MusterGetWorkflowStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterWorkFlowModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterWorkFlowModel: freezed == musterWorkFlowModel
          ? _value.musterWorkFlowModel
          : musterWorkFlowModel // ignore: cast_nullable_to_non_nullable
              as MusterWorkFlowModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel {
    if (_value.musterWorkFlowModel == null) {
      return null;
    }

    return $MusterWorkFlowModelCopyWith<$Res>(_value.musterWorkFlowModel!,
        (value) {
      return _then(_value.copyWith(musterWorkFlowModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_MusterGetWorkflowStateCopyWith<$Res>
    implements $MusterGetWorkflowStateCopyWith<$Res> {
  factory _$$_MusterGetWorkflowStateCopyWith(_$_MusterGetWorkflowState value,
          $Res Function(_$_MusterGetWorkflowState) then) =
      __$$_MusterGetWorkflowStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, MusterWorkFlowModel? musterWorkFlowModel});

  @override
  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel;
}

/// @nodoc
class __$$_MusterGetWorkflowStateCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res,
        _$_MusterGetWorkflowState>
    implements _$$_MusterGetWorkflowStateCopyWith<$Res> {
  __$$_MusterGetWorkflowStateCopyWithImpl(_$_MusterGetWorkflowState _value,
      $Res Function(_$_MusterGetWorkflowState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? musterWorkFlowModel = freezed,
  }) {
    return _then(_$_MusterGetWorkflowState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      musterWorkFlowModel: freezed == musterWorkFlowModel
          ? _value.musterWorkFlowModel
          : musterWorkFlowModel // ignore: cast_nullable_to_non_nullable
              as MusterWorkFlowModel?,
    ));
  }
}

/// @nodoc

class _$_MusterGetWorkflowState extends _MusterGetWorkflowState {
  const _$_MusterGetWorkflowState(
      {this.loading = false, this.musterWorkFlowModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final MusterWorkFlowModel? musterWorkFlowModel;

  @override
  String toString() {
    return 'MusterGetWorkflowState(loading: $loading, musterWorkFlowModel: $musterWorkFlowModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterGetWorkflowState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.musterWorkFlowModel, musterWorkFlowModel) ||
                other.musterWorkFlowModel == musterWorkFlowModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading, musterWorkFlowModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterGetWorkflowStateCopyWith<_$_MusterGetWorkflowState> get copyWith =>
      __$$_MusterGetWorkflowStateCopyWithImpl<_$_MusterGetWorkflowState>(
          this, _$identity);
}

abstract class _MusterGetWorkflowState extends MusterGetWorkflowState {
  const factory _MusterGetWorkflowState(
          {final bool loading,
          final MusterWorkFlowModel? musterWorkFlowModel}) =
      _$_MusterGetWorkflowState;
  const _MusterGetWorkflowState._() : super._();

  @override
  bool get loading;
  @override
  MusterWorkFlowModel? get musterWorkFlowModel;
  @override
  @JsonKey(ignore: true)
  _$$_MusterGetWorkflowStateCopyWith<_$_MusterGetWorkflowState> get copyWith =>
      throw _privateConstructorUsedError;
}
