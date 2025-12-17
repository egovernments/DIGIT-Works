// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'work_order_pdf.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$WorkOrderPDFEvent {
  String? get tenantId => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? tenantId, String? contractId)
        onWorkOrderPDF,
    required TResult Function(
            String? tenantId, String? estimateId, String? workorder)
        onAnalysisPDF,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult? Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(PDFEventWorkOrder value) onWorkOrderPDF,
    required TResult Function(PDFEventAnalysis value) onAnalysisPDF,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult? Function(PDFEventAnalysis value)? onAnalysisPDF,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult Function(PDFEventAnalysis value)? onAnalysisPDF,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $WorkOrderPDFEventCopyWith<WorkOrderPDFEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $WorkOrderPDFEventCopyWith<$Res> {
  factory $WorkOrderPDFEventCopyWith(
          WorkOrderPDFEvent value, $Res Function(WorkOrderPDFEvent) then) =
      _$WorkOrderPDFEventCopyWithImpl<$Res, WorkOrderPDFEvent>;
  @useResult
  $Res call({String? tenantId});
}

/// @nodoc
class _$WorkOrderPDFEventCopyWithImpl<$Res, $Val extends WorkOrderPDFEvent>
    implements $WorkOrderPDFEventCopyWith<$Res> {
  _$WorkOrderPDFEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$PDFEventWorkOrderImplCopyWith<$Res>
    implements $WorkOrderPDFEventCopyWith<$Res> {
  factory _$$PDFEventWorkOrderImplCopyWith(_$PDFEventWorkOrderImpl value,
          $Res Function(_$PDFEventWorkOrderImpl) then) =
      __$$PDFEventWorkOrderImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? tenantId, String? contractId});
}

/// @nodoc
class __$$PDFEventWorkOrderImplCopyWithImpl<$Res>
    extends _$WorkOrderPDFEventCopyWithImpl<$Res, _$PDFEventWorkOrderImpl>
    implements _$$PDFEventWorkOrderImplCopyWith<$Res> {
  __$$PDFEventWorkOrderImplCopyWithImpl(_$PDFEventWorkOrderImpl _value,
      $Res Function(_$PDFEventWorkOrderImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? contractId = freezed,
  }) {
    return _then(_$PDFEventWorkOrderImpl(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$PDFEventWorkOrderImpl implements PDFEventWorkOrder {
  const _$PDFEventWorkOrderImpl({this.tenantId, this.contractId});

  @override
  final String? tenantId;
  @override
  final String? contractId;

  @override
  String toString() {
    return 'WorkOrderPDFEvent.onWorkOrderPDF(tenantId: $tenantId, contractId: $contractId)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PDFEventWorkOrderImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, contractId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$PDFEventWorkOrderImplCopyWith<_$PDFEventWorkOrderImpl> get copyWith =>
      __$$PDFEventWorkOrderImplCopyWithImpl<_$PDFEventWorkOrderImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? tenantId, String? contractId)
        onWorkOrderPDF,
    required TResult Function(
            String? tenantId, String? estimateId, String? workorder)
        onAnalysisPDF,
  }) {
    return onWorkOrderPDF(tenantId, contractId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult? Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
  }) {
    return onWorkOrderPDF?.call(tenantId, contractId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
    required TResult orElse(),
  }) {
    if (onWorkOrderPDF != null) {
      return onWorkOrderPDF(tenantId, contractId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(PDFEventWorkOrder value) onWorkOrderPDF,
    required TResult Function(PDFEventAnalysis value) onAnalysisPDF,
  }) {
    return onWorkOrderPDF(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult? Function(PDFEventAnalysis value)? onAnalysisPDF,
  }) {
    return onWorkOrderPDF?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult Function(PDFEventAnalysis value)? onAnalysisPDF,
    required TResult orElse(),
  }) {
    if (onWorkOrderPDF != null) {
      return onWorkOrderPDF(this);
    }
    return orElse();
  }
}

abstract class PDFEventWorkOrder implements WorkOrderPDFEvent {
  const factory PDFEventWorkOrder(
      {final String? tenantId,
      final String? contractId}) = _$PDFEventWorkOrderImpl;

  @override
  String? get tenantId;
  String? get contractId;
  @override
  @JsonKey(ignore: true)
  _$$PDFEventWorkOrderImplCopyWith<_$PDFEventWorkOrderImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$PDFEventAnalysisImplCopyWith<$Res>
    implements $WorkOrderPDFEventCopyWith<$Res> {
  factory _$$PDFEventAnalysisImplCopyWith(_$PDFEventAnalysisImpl value,
          $Res Function(_$PDFEventAnalysisImpl) then) =
      __$$PDFEventAnalysisImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? tenantId, String? estimateId, String? workorder});
}

/// @nodoc
class __$$PDFEventAnalysisImplCopyWithImpl<$Res>
    extends _$WorkOrderPDFEventCopyWithImpl<$Res, _$PDFEventAnalysisImpl>
    implements _$$PDFEventAnalysisImplCopyWith<$Res> {
  __$$PDFEventAnalysisImplCopyWithImpl(_$PDFEventAnalysisImpl _value,
      $Res Function(_$PDFEventAnalysisImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = freezed,
    Object? estimateId = freezed,
    Object? workorder = freezed,
  }) {
    return _then(_$PDFEventAnalysisImpl(
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      estimateId: freezed == estimateId
          ? _value.estimateId
          : estimateId // ignore: cast_nullable_to_non_nullable
              as String?,
      workorder: freezed == workorder
          ? _value.workorder
          : workorder // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$PDFEventAnalysisImpl implements PDFEventAnalysis {
  const _$PDFEventAnalysisImpl(
      {this.tenantId, this.estimateId, this.workorder});

  @override
  final String? tenantId;
  @override
  final String? estimateId;
  @override
  final String? workorder;

  @override
  String toString() {
    return 'WorkOrderPDFEvent.onAnalysisPDF(tenantId: $tenantId, estimateId: $estimateId, workorder: $workorder)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$PDFEventAnalysisImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.estimateId, estimateId) ||
                other.estimateId == estimateId) &&
            (identical(other.workorder, workorder) ||
                other.workorder == workorder));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, estimateId, workorder);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$PDFEventAnalysisImplCopyWith<_$PDFEventAnalysisImpl> get copyWith =>
      __$$PDFEventAnalysisImplCopyWithImpl<_$PDFEventAnalysisImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? tenantId, String? contractId)
        onWorkOrderPDF,
    required TResult Function(
            String? tenantId, String? estimateId, String? workorder)
        onAnalysisPDF,
  }) {
    return onAnalysisPDF(tenantId, estimateId, workorder);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult? Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
  }) {
    return onAnalysisPDF?.call(tenantId, estimateId, workorder);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? tenantId, String? contractId)? onWorkOrderPDF,
    TResult Function(String? tenantId, String? estimateId, String? workorder)?
        onAnalysisPDF,
    required TResult orElse(),
  }) {
    if (onAnalysisPDF != null) {
      return onAnalysisPDF(tenantId, estimateId, workorder);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(PDFEventWorkOrder value) onWorkOrderPDF,
    required TResult Function(PDFEventAnalysis value) onAnalysisPDF,
  }) {
    return onAnalysisPDF(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult? Function(PDFEventAnalysis value)? onAnalysisPDF,
  }) {
    return onAnalysisPDF?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(PDFEventWorkOrder value)? onWorkOrderPDF,
    TResult Function(PDFEventAnalysis value)? onAnalysisPDF,
    required TResult orElse(),
  }) {
    if (onAnalysisPDF != null) {
      return onAnalysisPDF(this);
    }
    return orElse();
  }
}

abstract class PDFEventAnalysis implements WorkOrderPDFEvent {
  const factory PDFEventAnalysis(
      {final String? tenantId,
      final String? estimateId,
      final String? workorder}) = _$PDFEventAnalysisImpl;

  @override
  String? get tenantId;
  String? get estimateId;
  String? get workorder;
  @override
  @JsonKey(ignore: true)
  _$$PDFEventAnalysisImplCopyWith<_$PDFEventAnalysisImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$WorkOrderPDFState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function() loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function()? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function()? loaded,
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
abstract class $WorkOrderPDFStateCopyWith<$Res> {
  factory $WorkOrderPDFStateCopyWith(
          WorkOrderPDFState value, $Res Function(WorkOrderPDFState) then) =
      _$WorkOrderPDFStateCopyWithImpl<$Res, WorkOrderPDFState>;
}

/// @nodoc
class _$WorkOrderPDFStateCopyWithImpl<$Res, $Val extends WorkOrderPDFState>
    implements $WorkOrderPDFStateCopyWith<$Res> {
  _$WorkOrderPDFStateCopyWithImpl(this._value, this._then);

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
    extends _$WorkOrderPDFStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'WorkOrderPDFState.initial()';
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
    required TResult Function() loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function()? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function()? loaded,
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

abstract class _Initial extends WorkOrderPDFState {
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
    extends _$WorkOrderPDFStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'WorkOrderPDFState.loading()';
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
    required TResult Function() loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function()? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function()? loaded,
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

abstract class _Loading extends WorkOrderPDFState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$WorkOrderPDFStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl() : super._();

  @override
  String toString() {
    return 'WorkOrderPDFState.loaded()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadedImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function() loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function()? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function()? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded();
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

abstract class _Loaded extends WorkOrderPDFState {
  const factory _Loaded() = _$LoadedImpl;
  const _Loaded._() : super._();
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
    extends _$WorkOrderPDFStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'WorkOrderPDFState.error(error: $error)';
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
    required TResult Function() loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function()? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function()? loaded,
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

abstract class _Error extends WorkOrderPDFState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
