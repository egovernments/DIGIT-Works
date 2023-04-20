// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'decline_work_order.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$DeclineWorkOrderEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Map<String, dynamic>? contractsModel,
            String action, String comments)
        search,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WorkOrderDeclineEvent value) search,
    required TResult Function(DisposeDeclineEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WorkOrderDeclineEvent value)? search,
    TResult? Function(DisposeDeclineEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WorkOrderDeclineEvent value)? search,
    TResult Function(DisposeDeclineEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $DeclineWorkOrderEventCopyWith<$Res> {
  factory $DeclineWorkOrderEventCopyWith(DeclineWorkOrderEvent value,
          $Res Function(DeclineWorkOrderEvent) then) =
      _$DeclineWorkOrderEventCopyWithImpl<$Res, DeclineWorkOrderEvent>;
}

/// @nodoc
class _$DeclineWorkOrderEventCopyWithImpl<$Res,
        $Val extends DeclineWorkOrderEvent>
    implements $DeclineWorkOrderEventCopyWith<$Res> {
  _$DeclineWorkOrderEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$WorkOrderDeclineEventCopyWith<$Res> {
  factory _$$WorkOrderDeclineEventCopyWith(_$WorkOrderDeclineEvent value,
          $Res Function(_$WorkOrderDeclineEvent) then) =
      __$$WorkOrderDeclineEventCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {Map<String, dynamic>? contractsModel, String action, String comments});
}

/// @nodoc
class __$$WorkOrderDeclineEventCopyWithImpl<$Res>
    extends _$DeclineWorkOrderEventCopyWithImpl<$Res, _$WorkOrderDeclineEvent>
    implements _$$WorkOrderDeclineEventCopyWith<$Res> {
  __$$WorkOrderDeclineEventCopyWithImpl(_$WorkOrderDeclineEvent _value,
      $Res Function(_$WorkOrderDeclineEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractsModel = freezed,
    Object? action = null,
    Object? comments = null,
  }) {
    return _then(_$WorkOrderDeclineEvent(
      contractsModel: freezed == contractsModel
          ? _value._contractsModel
          : contractsModel // ignore: cast_nullable_to_non_nullable
              as Map<String, dynamic>?,
      action: null == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String,
      comments: null == comments
          ? _value.comments
          : comments // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$WorkOrderDeclineEvent
    with DiagnosticableTreeMixin
    implements WorkOrderDeclineEvent {
  const _$WorkOrderDeclineEvent(
      {required final Map<String, dynamic>? contractsModel,
      required this.action,
      this.comments = ''})
      : _contractsModel = contractsModel;

  final Map<String, dynamic>? _contractsModel;
  @override
  Map<String, dynamic>? get contractsModel {
    final value = _contractsModel;
    if (value == null) return null;
    if (_contractsModel is EqualUnmodifiableMapView) return _contractsModel;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableMapView(value);
  }

  @override
  final String action;
  @override
  @JsonKey()
  final String comments;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderEvent.search(contractsModel: $contractsModel, action: $action, comments: $comments)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'DeclineWorkOrderEvent.search'))
      ..add(DiagnosticsProperty('contractsModel', contractsModel))
      ..add(DiagnosticsProperty('action', action))
      ..add(DiagnosticsProperty('comments', comments));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$WorkOrderDeclineEvent &&
            const DeepCollectionEquality()
                .equals(other._contractsModel, _contractsModel) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.comments, comments) ||
                other.comments == comments));
  }

  @override
  int get hashCode => Object.hash(runtimeType,
      const DeepCollectionEquality().hash(_contractsModel), action, comments);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$WorkOrderDeclineEventCopyWith<_$WorkOrderDeclineEvent> get copyWith =>
      __$$WorkOrderDeclineEventCopyWithImpl<_$WorkOrderDeclineEvent>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Map<String, dynamic>? contractsModel,
            String action, String comments)
        search,
    required TResult Function() dispose,
  }) {
    return search(contractsModel, action, comments);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
    TResult? Function()? dispose,
  }) {
    return search?.call(contractsModel, action, comments);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(contractsModel, action, comments);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(WorkOrderDeclineEvent value) search,
    required TResult Function(DisposeDeclineEvent value) dispose,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WorkOrderDeclineEvent value)? search,
    TResult? Function(DisposeDeclineEvent value)? dispose,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WorkOrderDeclineEvent value)? search,
    TResult Function(DisposeDeclineEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class WorkOrderDeclineEvent implements DeclineWorkOrderEvent {
  const factory WorkOrderDeclineEvent(
      {required final Map<String, dynamic>? contractsModel,
      required final String action,
      final String comments}) = _$WorkOrderDeclineEvent;

  Map<String, dynamic>? get contractsModel;
  String get action;
  String get comments;
  @JsonKey(ignore: true)
  _$$WorkOrderDeclineEventCopyWith<_$WorkOrderDeclineEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeDeclineEventCopyWith<$Res> {
  factory _$$DisposeDeclineEventCopyWith(_$DisposeDeclineEvent value,
          $Res Function(_$DisposeDeclineEvent) then) =
      __$$DisposeDeclineEventCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeDeclineEventCopyWithImpl<$Res>
    extends _$DeclineWorkOrderEventCopyWithImpl<$Res, _$DisposeDeclineEvent>
    implements _$$DisposeDeclineEventCopyWith<$Res> {
  __$$DisposeDeclineEventCopyWithImpl(
      _$DisposeDeclineEvent _value, $Res Function(_$DisposeDeclineEvent) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeDeclineEvent
    with DiagnosticableTreeMixin
    implements DisposeDeclineEvent {
  const _$DisposeDeclineEvent();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'DeclineWorkOrderEvent.dispose'));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$DisposeDeclineEvent);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Map<String, dynamic>? contractsModel,
            String action, String comments)
        search,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Map<String, dynamic>? contractsModel, String action,
            String comments)?
        search,
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
    required TResult Function(WorkOrderDeclineEvent value) search,
    required TResult Function(DisposeDeclineEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(WorkOrderDeclineEvent value)? search,
    TResult? Function(DisposeDeclineEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(WorkOrderDeclineEvent value)? search,
    TResult Function(DisposeDeclineEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeDeclineEvent implements DeclineWorkOrderEvent {
  const factory DisposeDeclineEvent() = _$DisposeDeclineEvent;
}

/// @nodoc
mixin _$DeclineWorkOrderState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(ContractsModel? contractsModel) loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(ContractsModel? contractsModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(ContractsModel? contractsModel)? loaded,
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
abstract class $DeclineWorkOrderStateCopyWith<$Res> {
  factory $DeclineWorkOrderStateCopyWith(DeclineWorkOrderState value,
          $Res Function(DeclineWorkOrderState) then) =
      _$DeclineWorkOrderStateCopyWithImpl<$Res, DeclineWorkOrderState>;
}

/// @nodoc
class _$DeclineWorkOrderStateCopyWithImpl<$Res,
        $Val extends DeclineWorkOrderState>
    implements $DeclineWorkOrderStateCopyWith<$Res> {
  _$DeclineWorkOrderStateCopyWithImpl(this._value, this._then);

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
    extends _$DeclineWorkOrderStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'DeclineWorkOrderState.initial'));
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
    required TResult Function(ContractsModel? contractsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(ContractsModel? contractsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(ContractsModel? contractsModel)? loaded,
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

abstract class _Initial extends DeclineWorkOrderState {
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
    extends _$DeclineWorkOrderStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'DeclineWorkOrderState.loading'));
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
    required TResult Function(ContractsModel? contractsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(ContractsModel? contractsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(ContractsModel? contractsModel)? loaded,
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

abstract class _Loading extends DeclineWorkOrderState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({ContractsModel? contractsModel});
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$DeclineWorkOrderStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractsModel = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == contractsModel
          ? _value.contractsModel
          : contractsModel // ignore: cast_nullable_to_non_nullable
              as ContractsModel?,
    ));
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.contractsModel) : super._();

  @override
  final ContractsModel? contractsModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderState.loaded(contractsModel: $contractsModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'DeclineWorkOrderState.loaded'))
      ..add(DiagnosticsProperty('contractsModel', contractsModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.contractsModel, contractsModel) ||
                other.contractsModel == contractsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, contractsModel);

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
    required TResult Function(ContractsModel? contractsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(contractsModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(ContractsModel? contractsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(contractsModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(ContractsModel? contractsModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(contractsModel);
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

abstract class _Loaded extends DeclineWorkOrderState {
  const factory _Loaded(final ContractsModel? contractsModel) = _$_Loaded;
  const _Loaded._() : super._();

  ContractsModel? get contractsModel;
  @JsonKey(ignore: true)
  _$$_LoadedCopyWith<_$_Loaded> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$_ErrorCopyWith<$Res> {
  factory _$$_ErrorCopyWith(_$_Error value, $Res Function(_$_Error) then) =
      __$$_ErrorCopyWithImpl<$Res>;
  @useResult
  $Res call({String? error});
}

/// @nodoc
class __$$_ErrorCopyWithImpl<$Res>
    extends _$DeclineWorkOrderStateCopyWithImpl<$Res, _$_Error>
    implements _$$_ErrorCopyWith<$Res> {
  __$$_ErrorCopyWithImpl(_$_Error _value, $Res Function(_$_Error) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
  }) {
    return _then(_$_Error(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$_Error extends _Error with DiagnosticableTreeMixin {
  const _$_Error(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'DeclineWorkOrderState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'DeclineWorkOrderState.error'))
      ..add(DiagnosticsProperty('error', error));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Error &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      __$$_ErrorCopyWithImpl<_$_Error>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(ContractsModel? contractsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(ContractsModel? contractsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(ContractsModel? contractsModel)? loaded,
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

abstract class _Error extends DeclineWorkOrderState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
