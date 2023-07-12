// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'get_muster_workflow.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$MusterGetWorkflowEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(DisposeMusterRollWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterGetWorkflowEventCopyWith<$Res> {
  factory $MusterGetWorkflowEventCopyWith(MusterGetWorkflowEvent value,
          $Res Function(MusterGetWorkflowEvent) then) =
      _$MusterGetWorkflowEventCopyWithImpl<$Res, MusterGetWorkflowEvent>;
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
}

/// @nodoc
abstract class _$$GetMusterWorkflowEventCopyWith<$Res> {
  factory _$$GetMusterWorkflowEventCopyWith(_$GetMusterWorkflowEvent value,
          $Res Function(_$GetMusterWorkflowEvent) then) =
      __$$GetMusterWorkflowEventCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId, String musterRollNumber, String musterSentBackCode});
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
    Object? musterSentBackCode = null,
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
      musterSentBackCode: null == musterSentBackCode
          ? _value.musterSentBackCode
          : musterSentBackCode // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$GetMusterWorkflowEvent implements GetMusterWorkflowEvent {
  const _$GetMusterWorkflowEvent(
      {required this.tenantId,
      required this.musterRollNumber,
      required this.musterSentBackCode});

  @override
  final String tenantId;
  @override
  final String musterRollNumber;
  @override
  final String musterSentBackCode;

  @override
  String toString() {
    return 'MusterGetWorkflowEvent.get(tenantId: $tenantId, musterRollNumber: $musterRollNumber, musterSentBackCode: $musterSentBackCode)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GetMusterWorkflowEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.musterRollNumber, musterRollNumber) ||
                other.musterRollNumber == musterRollNumber) &&
            (identical(other.musterSentBackCode, musterSentBackCode) ||
                other.musterSentBackCode == musterSentBackCode));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, tenantId, musterRollNumber, musterSentBackCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$GetMusterWorkflowEventCopyWith<_$GetMusterWorkflowEvent> get copyWith =>
      __$$GetMusterWorkflowEventCopyWithImpl<_$GetMusterWorkflowEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function() dispose,
  }) {
    return get(tenantId, musterRollNumber, musterSentBackCode);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult? Function()? dispose,
  }) {
    return get?.call(tenantId, musterRollNumber, musterSentBackCode);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (get != null) {
      return get(tenantId, musterRollNumber, musterSentBackCode);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) {
    return get(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) {
    return get?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(DisposeMusterRollWorkflowEvent value)? dispose,
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
      required final String musterRollNumber,
      required final String musterSentBackCode}) = _$GetMusterWorkflowEvent;

  String get tenantId;
  String get musterRollNumber;
  String get musterSentBackCode;
  @JsonKey(ignore: true)
  _$$GetMusterWorkflowEventCopyWith<_$GetMusterWorkflowEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeMusterRollWorkflowEventCopyWith<$Res> {
  factory _$$DisposeMusterRollWorkflowEventCopyWith(
          _$DisposeMusterRollWorkflowEvent value,
          $Res Function(_$DisposeMusterRollWorkflowEvent) then) =
      __$$DisposeMusterRollWorkflowEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeMusterRollWorkflowEventCopyWithImpl<$Res>
    extends _$MusterGetWorkflowEventCopyWithImpl<$Res,
        _$DisposeMusterRollWorkflowEvent>
    implements _$$DisposeMusterRollWorkflowEventCopyWith<$Res> {
  __$$DisposeMusterRollWorkflowEventCopyWithImpl(
      _$DisposeMusterRollWorkflowEvent _value,
      $Res Function(_$DisposeMusterRollWorkflowEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeMusterRollWorkflowEvent
    implements DisposeMusterRollWorkflowEvent {
  const _$DisposeMusterRollWorkflowEvent();

  @override
  String toString() {
    return 'MusterGetWorkflowEvent.dispose()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeMusterRollWorkflowEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
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
    required TResult Function(GetMusterWorkflowEvent value) get,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(DisposeMusterRollWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeMusterRollWorkflowEvent
    implements MusterGetWorkflowEvent {
  const factory DisposeMusterRollWorkflowEvent() =
      _$DisposeMusterRollWorkflowEvent;
}

/// @nodoc
mixin _$MusterGetWorkflowState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)
        loaded,
    required TResult Function() error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult? Function()? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult Function()? error,
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
abstract class $MusterGetWorkflowStateCopyWith<$Res> {
  factory $MusterGetWorkflowStateCopyWith(MusterGetWorkflowState value,
          $Res Function(MusterGetWorkflowState) then) =
      _$MusterGetWorkflowStateCopyWithImpl<$Res, MusterGetWorkflowState>;
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
}

/// @nodoc
abstract class _$$_InitialCopyWith<$Res> {
  factory _$$_InitialCopyWith(
          _$_Initial value, $Res Function(_$_Initial) then) =
      __$$_InitialCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_InitialCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'MusterGetWorkflowState.initial()';
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
    required TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)
        loaded,
    required TResult Function() error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult? Function()? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult Function()? error,
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

abstract class _Initial extends MusterGetWorkflowState {
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
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'MusterGetWorkflowState.loading()';
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
    required TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)
        loaded,
    required TResult Function() error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult? Function()? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult Function()? error,
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

abstract class _Loading extends MusterGetWorkflowState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow});

  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterWorkFlowModel = freezed,
    Object? isInWorkflow = null,
  }) {
    return _then(_$_Loaded(
      musterWorkFlowModel: freezed == musterWorkFlowModel
          ? _value.musterWorkFlowModel
          : musterWorkFlowModel // ignore: cast_nullable_to_non_nullable
              as MusterWorkFlowModel?,
      isInWorkflow: null == isInWorkflow
          ? _value.isInWorkflow
          : isInWorkflow // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel {
    if (_value.musterWorkFlowModel == null) {
      return null;
    }

    return $MusterWorkFlowModelCopyWith<$Res>(_value.musterWorkFlowModel!,
        (value) {
      return _then(_value.copyWith(musterWorkFlowModel: value));
    });
  }
}

/// @nodoc

class _$_Loaded extends _Loaded {
  const _$_Loaded({this.musterWorkFlowModel, this.isInWorkflow = false})
      : super._();

  @override
  final MusterWorkFlowModel? musterWorkFlowModel;
  @override
  @JsonKey()
  final bool isInWorkflow;

  @override
  String toString() {
    return 'MusterGetWorkflowState.loaded(musterWorkFlowModel: $musterWorkFlowModel, isInWorkflow: $isInWorkflow)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.musterWorkFlowModel, musterWorkFlowModel) ||
                other.musterWorkFlowModel == musterWorkFlowModel) &&
            (identical(other.isInWorkflow, isInWorkflow) ||
                other.isInWorkflow == isInWorkflow));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, musterWorkFlowModel, isInWorkflow);

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
    required TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)
        loaded,
    required TResult Function() error,
  }) {
    return loaded(musterWorkFlowModel, isInWorkflow);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult? Function()? error,
  }) {
    return loaded?.call(musterWorkFlowModel, isInWorkflow);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(musterWorkFlowModel, isInWorkflow);
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

abstract class _Loaded extends MusterGetWorkflowState {
  const factory _Loaded(
      {final MusterWorkFlowModel? musterWorkFlowModel,
      final bool isInWorkflow}) = _$_Loaded;
  const _Loaded._() : super._();

  MusterWorkFlowModel? get musterWorkFlowModel;
  bool get isInWorkflow;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_ErrorCopyWith<$Res> {
  factory _$$_ErrorCopyWith(_$_Error value, $Res Function(_$_Error) then) =
      __$$_ErrorCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_ErrorCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Error extends _Error {
  const _$_Error() : super._();

  @override
  String toString() {
    return 'MusterGetWorkflowState.error()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Error);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)
        loaded,
    required TResult Function() error,
  }) {
    return error();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult? Function()? error,
  }) {
    return error?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(
            MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow)?
        loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error();
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

abstract class _Error extends MusterGetWorkflowState {
  const factory _Error() = _$_Error;
  const _Error._() : super._();
}
