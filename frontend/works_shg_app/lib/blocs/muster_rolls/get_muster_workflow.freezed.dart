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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MusterGetWorkflowEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function(String tenantId, String mbNumber) fetch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult? Function(String tenantId, String mbNumber)? fetch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult Function(String tenantId, String mbNumber)? fetch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
    required TResult Function(FetchMBWorkFlowEvent value) fetch,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(FetchMBWorkFlowEvent value)? fetch,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(FetchMBWorkFlowEvent value)? fetch,
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
abstract class _$$GetMusterWorkflowEventImplCopyWith<$Res> {
  factory _$$GetMusterWorkflowEventImplCopyWith(
          _$GetMusterWorkflowEventImpl value,
          $Res Function(_$GetMusterWorkflowEventImpl) then) =
      __$$GetMusterWorkflowEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {String tenantId, String musterRollNumber, String musterSentBackCode});
}

/// @nodoc
class __$$GetMusterWorkflowEventImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowEventCopyWithImpl<$Res,
        _$GetMusterWorkflowEventImpl>
    implements _$$GetMusterWorkflowEventImplCopyWith<$Res> {
  __$$GetMusterWorkflowEventImplCopyWithImpl(
      _$GetMusterWorkflowEventImpl _value,
      $Res Function(_$GetMusterWorkflowEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? musterRollNumber = null,
    Object? musterSentBackCode = null,
  }) {
    return _then(_$GetMusterWorkflowEventImpl(
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

class _$GetMusterWorkflowEventImpl implements GetMusterWorkflowEvent {
  const _$GetMusterWorkflowEventImpl(
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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GetMusterWorkflowEventImpl &&
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
  _$$GetMusterWorkflowEventImplCopyWith<_$GetMusterWorkflowEventImpl>
      get copyWith => __$$GetMusterWorkflowEventImplCopyWithImpl<
          _$GetMusterWorkflowEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function(String tenantId, String mbNumber) fetch,
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
    TResult? Function(String tenantId, String mbNumber)? fetch,
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
    TResult Function(String tenantId, String mbNumber)? fetch,
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
    required TResult Function(FetchMBWorkFlowEvent value) fetch,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) {
    return get(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(FetchMBWorkFlowEvent value)? fetch,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) {
    return get?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(FetchMBWorkFlowEvent value)? fetch,
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
      required final String musterSentBackCode}) = _$GetMusterWorkflowEventImpl;

  String get tenantId;
  String get musterRollNumber;
  String get musterSentBackCode;
  @JsonKey(ignore: true)
  _$$GetMusterWorkflowEventImplCopyWith<_$GetMusterWorkflowEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$FetchMBWorkFlowEventImplCopyWith<$Res> {
  factory _$$FetchMBWorkFlowEventImplCopyWith(_$FetchMBWorkFlowEventImpl value,
          $Res Function(_$FetchMBWorkFlowEventImpl) then) =
      __$$FetchMBWorkFlowEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenantId, String mbNumber});
}

/// @nodoc
class __$$FetchMBWorkFlowEventImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowEventCopyWithImpl<$Res,
        _$FetchMBWorkFlowEventImpl>
    implements _$$FetchMBWorkFlowEventImplCopyWith<$Res> {
  __$$FetchMBWorkFlowEventImplCopyWithImpl(_$FetchMBWorkFlowEventImpl _value,
      $Res Function(_$FetchMBWorkFlowEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? mbNumber = null,
  }) {
    return _then(_$FetchMBWorkFlowEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      mbNumber: null == mbNumber
          ? _value.mbNumber
          : mbNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$FetchMBWorkFlowEventImpl implements FetchMBWorkFlowEvent {
  const _$FetchMBWorkFlowEventImpl(
      {required this.tenantId, required this.mbNumber});

  @override
  final String tenantId;
  @override
  final String mbNumber;

  @override
  String toString() {
    return 'MusterGetWorkflowEvent.fetch(tenantId: $tenantId, mbNumber: $mbNumber)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$FetchMBWorkFlowEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.mbNumber, mbNumber) ||
                other.mbNumber == mbNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, mbNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$FetchMBWorkFlowEventImplCopyWith<_$FetchMBWorkFlowEventImpl>
      get copyWith =>
          __$$FetchMBWorkFlowEventImplCopyWithImpl<_$FetchMBWorkFlowEventImpl>(
              this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function(String tenantId, String mbNumber) fetch,
    required TResult Function() dispose,
  }) {
    return fetch(tenantId, mbNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult? Function(String tenantId, String mbNumber)? fetch,
    TResult? Function()? dispose,
  }) {
    return fetch?.call(tenantId, mbNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String musterRollNumber,
            String musterSentBackCode)?
        get,
    TResult Function(String tenantId, String mbNumber)? fetch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (fetch != null) {
      return fetch(tenantId, mbNumber);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetMusterWorkflowEvent value) get,
    required TResult Function(FetchMBWorkFlowEvent value) fetch,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) {
    return fetch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(FetchMBWorkFlowEvent value)? fetch,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) {
    return fetch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(FetchMBWorkFlowEvent value)? fetch,
    TResult Function(DisposeMusterRollWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (fetch != null) {
      return fetch(this);
    }
    return orElse();
  }
}

abstract class FetchMBWorkFlowEvent implements MusterGetWorkflowEvent {
  const factory FetchMBWorkFlowEvent(
      {required final String tenantId,
      required final String mbNumber}) = _$FetchMBWorkFlowEventImpl;

  String get tenantId;
  String get mbNumber;
  @JsonKey(ignore: true)
  _$$FetchMBWorkFlowEventImplCopyWith<_$FetchMBWorkFlowEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeMusterRollWorkflowEventImplCopyWith<$Res> {
  factory _$$DisposeMusterRollWorkflowEventImplCopyWith(
          _$DisposeMusterRollWorkflowEventImpl value,
          $Res Function(_$DisposeMusterRollWorkflowEventImpl) then) =
      __$$DisposeMusterRollWorkflowEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeMusterRollWorkflowEventImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowEventCopyWithImpl<$Res,
        _$DisposeMusterRollWorkflowEventImpl>
    implements _$$DisposeMusterRollWorkflowEventImplCopyWith<$Res> {
  __$$DisposeMusterRollWorkflowEventImplCopyWithImpl(
      _$DisposeMusterRollWorkflowEventImpl _value,
      $Res Function(_$DisposeMusterRollWorkflowEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeMusterRollWorkflowEventImpl
    implements DisposeMusterRollWorkflowEvent {
  const _$DisposeMusterRollWorkflowEventImpl();

  @override
  String toString() {
    return 'MusterGetWorkflowEvent.dispose()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeMusterRollWorkflowEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId, String musterRollNumber, String musterSentBackCode)
        get,
    required TResult Function(String tenantId, String mbNumber) fetch,
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
    TResult? Function(String tenantId, String mbNumber)? fetch,
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
    TResult Function(String tenantId, String mbNumber)? fetch,
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
    required TResult Function(FetchMBWorkFlowEvent value) fetch,
    required TResult Function(DisposeMusterRollWorkflowEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetMusterWorkflowEvent value)? get,
    TResult? Function(FetchMBWorkFlowEvent value)? fetch,
    TResult? Function(DisposeMusterRollWorkflowEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetMusterWorkflowEvent value)? get,
    TResult Function(FetchMBWorkFlowEvent value)? fetch,
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
      _$DisposeMusterRollWorkflowEventImpl;
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
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'MusterGetWorkflowState.initial()';
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
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'MusterGetWorkflowState.loading()';
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
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({MusterWorkFlowModel? musterWorkFlowModel, bool isInWorkflow});

  $MusterWorkFlowModelCopyWith<$Res>? get musterWorkFlowModel;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterWorkFlowModel = freezed,
    Object? isInWorkflow = null,
  }) {
    return _then(_$LoadedImpl(
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

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl({this.musterWorkFlowModel, this.isInWorkflow = false})
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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
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
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

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
      final bool isInWorkflow}) = _$LoadedImpl;
  const _Loaded._() : super._();

  MusterWorkFlowModel? get musterWorkFlowModel;
  bool get isInWorkflow;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$MusterGetWorkflowStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$ErrorImpl extends _Error {
  const _$ErrorImpl() : super._();

  @override
  String toString() {
    return 'MusterGetWorkflowState.error()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$ErrorImpl);
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
  const factory _Error() = _$ErrorImpl;
  const _Error._() : super._();
}
