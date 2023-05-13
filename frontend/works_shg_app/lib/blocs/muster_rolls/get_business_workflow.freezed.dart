// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'get_business_workflow.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$BusinessWorkflowEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService) get,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService)? get,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService)? get,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetBusinessWorkflowEvent value) get,
    required TResult Function(DisposeBusinessWorkflowEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetBusinessWorkflowEvent value)? get,
    TResult? Function(DisposeBusinessWorkflowEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetBusinessWorkflowEvent value)? get,
    TResult Function(DisposeBusinessWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $BusinessWorkflowEventCopyWith<$Res> {
  factory $BusinessWorkflowEventCopyWith(BusinessWorkflowEvent value,
          $Res Function(BusinessWorkflowEvent) then) =
      _$BusinessWorkflowEventCopyWithImpl<$Res, BusinessWorkflowEvent>;
}

/// @nodoc
class _$BusinessWorkflowEventCopyWithImpl<$Res,
        $Val extends BusinessWorkflowEvent>
    implements $BusinessWorkflowEventCopyWith<$Res> {
  _$BusinessWorkflowEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$GetBusinessWorkflowEventCopyWith<$Res> {
  factory _$$GetBusinessWorkflowEventCopyWith(_$GetBusinessWorkflowEvent value,
          $Res Function(_$GetBusinessWorkflowEvent) then) =
      __$$GetBusinessWorkflowEventCopyWithImpl<$Res>;
  @useResult
  $Res call({String tenantId, String businessService});
}

/// @nodoc
class __$$GetBusinessWorkflowEventCopyWithImpl<$Res>
    extends _$BusinessWorkflowEventCopyWithImpl<$Res,
        _$GetBusinessWorkflowEvent>
    implements _$$GetBusinessWorkflowEventCopyWith<$Res> {
  __$$GetBusinessWorkflowEventCopyWithImpl(_$GetBusinessWorkflowEvent _value,
      $Res Function(_$GetBusinessWorkflowEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? businessService = null,
  }) {
    return _then(_$GetBusinessWorkflowEvent(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      businessService: null == businessService
          ? _value.businessService
          : businessService // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$GetBusinessWorkflowEvent implements GetBusinessWorkflowEvent {
  const _$GetBusinessWorkflowEvent(
      {required this.tenantId, required this.businessService});

  @override
  final String tenantId;
  @override
  final String businessService;

  @override
  String toString() {
    return 'BusinessWorkflowEvent.get(tenantId: $tenantId, businessService: $businessService)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$GetBusinessWorkflowEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.businessService, businessService) ||
                other.businessService == businessService));
  }

  @override
  int get hashCode => Object.hash(runtimeType, tenantId, businessService);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$GetBusinessWorkflowEventCopyWith<_$GetBusinessWorkflowEvent>
      get copyWith =>
          __$$GetBusinessWorkflowEventCopyWithImpl<_$GetBusinessWorkflowEvent>(
              this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService) get,
    required TResult Function() dispose,
  }) {
    return get(tenantId, businessService);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService)? get,
    TResult? Function()? dispose,
  }) {
    return get?.call(tenantId, businessService);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService)? get,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (get != null) {
      return get(tenantId, businessService);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(GetBusinessWorkflowEvent value) get,
    required TResult Function(DisposeBusinessWorkflowEvent value) dispose,
  }) {
    return get(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetBusinessWorkflowEvent value)? get,
    TResult? Function(DisposeBusinessWorkflowEvent value)? dispose,
  }) {
    return get?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetBusinessWorkflowEvent value)? get,
    TResult Function(DisposeBusinessWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (get != null) {
      return get(this);
    }
    return orElse();
  }
}

abstract class GetBusinessWorkflowEvent implements BusinessWorkflowEvent {
  const factory GetBusinessWorkflowEvent(
      {required final String tenantId,
      required final String businessService}) = _$GetBusinessWorkflowEvent;

  String get tenantId;
  String get businessService;
  @JsonKey(ignore: true)
  _$$GetBusinessWorkflowEventCopyWith<_$GetBusinessWorkflowEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeBusinessWorkflowEventCopyWith<$Res> {
  factory _$$DisposeBusinessWorkflowEventCopyWith(
          _$DisposeBusinessWorkflowEvent value,
          $Res Function(_$DisposeBusinessWorkflowEvent) then) =
      __$$DisposeBusinessWorkflowEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeBusinessWorkflowEventCopyWithImpl<$Res>
    extends _$BusinessWorkflowEventCopyWithImpl<$Res,
        _$DisposeBusinessWorkflowEvent>
    implements _$$DisposeBusinessWorkflowEventCopyWith<$Res> {
  __$$DisposeBusinessWorkflowEventCopyWithImpl(
      _$DisposeBusinessWorkflowEvent _value,
      $Res Function(_$DisposeBusinessWorkflowEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeBusinessWorkflowEvent implements DisposeBusinessWorkflowEvent {
  const _$DisposeBusinessWorkflowEvent();

  @override
  String toString() {
    return 'BusinessWorkflowEvent.dispose()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeBusinessWorkflowEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String businessService) get,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String businessService)? get,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String businessService)? get,
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
    required TResult Function(GetBusinessWorkflowEvent value) get,
    required TResult Function(DisposeBusinessWorkflowEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(GetBusinessWorkflowEvent value)? get,
    TResult? Function(DisposeBusinessWorkflowEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(GetBusinessWorkflowEvent value)? get,
    TResult Function(DisposeBusinessWorkflowEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeBusinessWorkflowEvent implements BusinessWorkflowEvent {
  const factory DisposeBusinessWorkflowEvent() = _$DisposeBusinessWorkflowEvent;
}

/// @nodoc
mixin _$BusinessGetWorkflowState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(
            BusinessServiceWorkflowModel? businessWorkFlowModel)
        loaded,
    required TResult Function() error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
        loaded,
    TResult? Function()? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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
abstract class $BusinessGetWorkflowStateCopyWith<$Res> {
  factory $BusinessGetWorkflowStateCopyWith(BusinessGetWorkflowState value,
          $Res Function(BusinessGetWorkflowState) then) =
      _$BusinessGetWorkflowStateCopyWithImpl<$Res, BusinessGetWorkflowState>;
}

/// @nodoc
class _$BusinessGetWorkflowStateCopyWithImpl<$Res,
        $Val extends BusinessGetWorkflowState>
    implements $BusinessGetWorkflowStateCopyWith<$Res> {
  _$BusinessGetWorkflowStateCopyWithImpl(this._value, this._then);

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
    extends _$BusinessGetWorkflowStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'BusinessGetWorkflowState.initial()';
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
            BusinessServiceWorkflowModel? businessWorkFlowModel)
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
    TResult? Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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
    TResult Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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

abstract class _Initial extends BusinessGetWorkflowState {
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
    extends _$BusinessGetWorkflowStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'BusinessGetWorkflowState.loading()';
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
            BusinessServiceWorkflowModel? businessWorkFlowModel)
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
    TResult? Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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
    TResult Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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

abstract class _Loading extends BusinessGetWorkflowState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({BusinessServiceWorkflowModel? businessWorkFlowModel});

  $BusinessServiceWorkflowModelCopyWith<$Res>? get businessWorkFlowModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$BusinessGetWorkflowStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? businessWorkFlowModel = freezed,
  }) {
    return _then(_$_Loaded(
      businessWorkFlowModel: freezed == businessWorkFlowModel
          ? _value.businessWorkFlowModel
          : businessWorkFlowModel // ignore: cast_nullable_to_non_nullable
              as BusinessServiceWorkflowModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $BusinessServiceWorkflowModelCopyWith<$Res>? get businessWorkFlowModel {
    if (_value.businessWorkFlowModel == null) {
      return null;
    }

    return $BusinessServiceWorkflowModelCopyWith<$Res>(
        _value.businessWorkFlowModel!, (value) {
      return _then(_value.copyWith(businessWorkFlowModel: value));
    });
  }
}

/// @nodoc

class _$_Loaded extends _Loaded {
  const _$_Loaded({this.businessWorkFlowModel}) : super._();

  @override
  final BusinessServiceWorkflowModel? businessWorkFlowModel;

  @override
  String toString() {
    return 'BusinessGetWorkflowState.loaded(businessWorkFlowModel: $businessWorkFlowModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.businessWorkFlowModel, businessWorkFlowModel) ||
                other.businessWorkFlowModel == businessWorkFlowModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, businessWorkFlowModel);

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
            BusinessServiceWorkflowModel? businessWorkFlowModel)
        loaded,
    required TResult Function() error,
  }) {
    return loaded(businessWorkFlowModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
        loaded,
    TResult? Function()? error,
  }) {
    return loaded?.call(businessWorkFlowModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
        loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(businessWorkFlowModel);
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

abstract class _Loaded extends BusinessGetWorkflowState {
  const factory _Loaded(
      {final BusinessServiceWorkflowModel? businessWorkFlowModel}) = _$_Loaded;
  const _Loaded._() : super._();

  BusinessServiceWorkflowModel? get businessWorkFlowModel;
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
    extends _$BusinessGetWorkflowStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Error extends _Error {
  const _$_Error() : super._();

  @override
  String toString() {
    return 'BusinessGetWorkflowState.error()';
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
            BusinessServiceWorkflowModel? businessWorkFlowModel)
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
    TResult? Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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
    TResult Function(BusinessServiceWorkflowModel? businessWorkFlowModel)?
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

abstract class _Error extends BusinessGetWorkflowState {
  const factory _Error() = _$_Error;
  const _Error._() : super._();
}
