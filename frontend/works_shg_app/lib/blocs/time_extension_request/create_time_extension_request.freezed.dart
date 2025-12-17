// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'create_time_extension_request.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$CreateTimeExtensionRequestEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)
        search,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
        search,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
        search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(TimeExtensionRequestEvent value) search,
    required TResult Function(TimeExtensionRequestDisposeEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(TimeExtensionRequestEvent value)? search,
    TResult? Function(TimeExtensionRequestDisposeEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(TimeExtensionRequestEvent value)? search,
    TResult Function(TimeExtensionRequestDisposeEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $CreateTimeExtensionRequestEventCopyWith<$Res> {
  factory $CreateTimeExtensionRequestEventCopyWith(
          CreateTimeExtensionRequestEvent value,
          $Res Function(CreateTimeExtensionRequestEvent) then) =
      _$CreateTimeExtensionRequestEventCopyWithImpl<$Res,
          CreateTimeExtensionRequestEvent>;
}

/// @nodoc
class _$CreateTimeExtensionRequestEventCopyWithImpl<$Res,
        $Val extends CreateTimeExtensionRequestEvent>
    implements $CreateTimeExtensionRequestEventCopyWith<$Res> {
  _$CreateTimeExtensionRequestEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$TimeExtensionRequestEventImplCopyWith<$Res> {
  factory _$$TimeExtensionRequestEventImplCopyWith(
          _$TimeExtensionRequestEventImpl value,
          $Res Function(_$TimeExtensionRequestEventImpl) then) =
      __$$TimeExtensionRequestEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call(
      {Contracts? contractsModel,
      String extensionDays,
      int extensionDate,
      String action,
      bool isEdit,
      String reason});
}

/// @nodoc
class __$$TimeExtensionRequestEventImplCopyWithImpl<$Res>
    extends _$CreateTimeExtensionRequestEventCopyWithImpl<$Res,
        _$TimeExtensionRequestEventImpl>
    implements _$$TimeExtensionRequestEventImplCopyWith<$Res> {
  __$$TimeExtensionRequestEventImplCopyWithImpl(
      _$TimeExtensionRequestEventImpl _value,
      $Res Function(_$TimeExtensionRequestEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractsModel = freezed,
    Object? extensionDays = null,
    Object? extensionDate = null,
    Object? action = null,
    Object? isEdit = null,
    Object? reason = null,
  }) {
    return _then(_$TimeExtensionRequestEventImpl(
      contractsModel: freezed == contractsModel
          ? _value.contractsModel
          : contractsModel // ignore: cast_nullable_to_non_nullable
              as Contracts?,
      extensionDays: null == extensionDays
          ? _value.extensionDays
          : extensionDays // ignore: cast_nullable_to_non_nullable
              as String,
      extensionDate: null == extensionDate
          ? _value.extensionDate
          : extensionDate // ignore: cast_nullable_to_non_nullable
              as int,
      action: null == action
          ? _value.action
          : action // ignore: cast_nullable_to_non_nullable
              as String,
      isEdit: null == isEdit
          ? _value.isEdit
          : isEdit // ignore: cast_nullable_to_non_nullable
              as bool,
      reason: null == reason
          ? _value.reason
          : reason // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$TimeExtensionRequestEventImpl
    with DiagnosticableTreeMixin
    implements TimeExtensionRequestEvent {
  const _$TimeExtensionRequestEventImpl(
      {required this.contractsModel,
      required this.extensionDays,
      required this.extensionDate,
      required this.action,
      required this.isEdit,
      this.reason = ''});

  @override
  final Contracts? contractsModel;
  @override
  final String extensionDays;
  @override
  final int extensionDate;
  @override
  final String action;
  @override
  final bool isEdit;
  @override
  @JsonKey()
  final String reason;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestEvent.search(contractsModel: $contractsModel, extensionDays: $extensionDays, extensionDate: $extensionDate, action: $action, isEdit: $isEdit, reason: $reason)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'CreateTimeExtensionRequestEvent.search'))
      ..add(DiagnosticsProperty('contractsModel', contractsModel))
      ..add(DiagnosticsProperty('extensionDays', extensionDays))
      ..add(DiagnosticsProperty('extensionDate', extensionDate))
      ..add(DiagnosticsProperty('action', action))
      ..add(DiagnosticsProperty('isEdit', isEdit))
      ..add(DiagnosticsProperty('reason', reason));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TimeExtensionRequestEventImpl &&
            (identical(other.contractsModel, contractsModel) ||
                other.contractsModel == contractsModel) &&
            (identical(other.extensionDays, extensionDays) ||
                other.extensionDays == extensionDays) &&
            (identical(other.extensionDate, extensionDate) ||
                other.extensionDate == extensionDate) &&
            (identical(other.action, action) || other.action == action) &&
            (identical(other.isEdit, isEdit) || other.isEdit == isEdit) &&
            (identical(other.reason, reason) || other.reason == reason));
  }

  @override
  int get hashCode => Object.hash(runtimeType, contractsModel, extensionDays,
      extensionDate, action, isEdit, reason);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$TimeExtensionRequestEventImplCopyWith<_$TimeExtensionRequestEventImpl>
      get copyWith => __$$TimeExtensionRequestEventImplCopyWithImpl<
          _$TimeExtensionRequestEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)
        search,
    required TResult Function() dispose,
  }) {
    return search(
        contractsModel, extensionDays, extensionDate, action, isEdit, reason);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
        search,
    TResult? Function()? dispose,
  }) {
    return search?.call(
        contractsModel, extensionDays, extensionDate, action, isEdit, reason);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
        search,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(
          contractsModel, extensionDays, extensionDate, action, isEdit, reason);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(TimeExtensionRequestEvent value) search,
    required TResult Function(TimeExtensionRequestDisposeEvent value) dispose,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(TimeExtensionRequestEvent value)? search,
    TResult? Function(TimeExtensionRequestDisposeEvent value)? dispose,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(TimeExtensionRequestEvent value)? search,
    TResult Function(TimeExtensionRequestDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class TimeExtensionRequestEvent
    implements CreateTimeExtensionRequestEvent {
  const factory TimeExtensionRequestEvent(
      {required final Contracts? contractsModel,
      required final String extensionDays,
      required final int extensionDate,
      required final String action,
      required final bool isEdit,
      final String reason}) = _$TimeExtensionRequestEventImpl;

  Contracts? get contractsModel;
  String get extensionDays;
  int get extensionDate;
  String get action;
  bool get isEdit;
  String get reason;
  @JsonKey(ignore: true)
  _$$TimeExtensionRequestEventImplCopyWith<_$TimeExtensionRequestEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$TimeExtensionRequestDisposeEventImplCopyWith<$Res> {
  factory _$$TimeExtensionRequestDisposeEventImplCopyWith(
          _$TimeExtensionRequestDisposeEventImpl value,
          $Res Function(_$TimeExtensionRequestDisposeEventImpl) then) =
      __$$TimeExtensionRequestDisposeEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$TimeExtensionRequestDisposeEventImplCopyWithImpl<$Res>
    extends _$CreateTimeExtensionRequestEventCopyWithImpl<$Res,
        _$TimeExtensionRequestDisposeEventImpl>
    implements _$$TimeExtensionRequestDisposeEventImplCopyWith<$Res> {
  __$$TimeExtensionRequestDisposeEventImplCopyWithImpl(
      _$TimeExtensionRequestDisposeEventImpl _value,
      $Res Function(_$TimeExtensionRequestDisposeEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$TimeExtensionRequestDisposeEventImpl
    with DiagnosticableTreeMixin
    implements TimeExtensionRequestDisposeEvent {
  const _$TimeExtensionRequestDisposeEventImpl();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'CreateTimeExtensionRequestEvent.dispose'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$TimeExtensionRequestDisposeEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)
        search,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
        search,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(Contracts? contractsModel, String extensionDays,
            int extensionDate, String action, bool isEdit, String reason)?
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
    required TResult Function(TimeExtensionRequestEvent value) search,
    required TResult Function(TimeExtensionRequestDisposeEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(TimeExtensionRequestEvent value)? search,
    TResult? Function(TimeExtensionRequestDisposeEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(TimeExtensionRequestEvent value)? search,
    TResult Function(TimeExtensionRequestDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class TimeExtensionRequestDisposeEvent
    implements CreateTimeExtensionRequestEvent {
  const factory TimeExtensionRequestDisposeEvent() =
      _$TimeExtensionRequestDisposeEventImpl;
}

/// @nodoc
mixin _$CreateTimeExtensionRequestState {
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
abstract class $CreateTimeExtensionRequestStateCopyWith<$Res> {
  factory $CreateTimeExtensionRequestStateCopyWith(
          CreateTimeExtensionRequestState value,
          $Res Function(CreateTimeExtensionRequestState) then) =
      _$CreateTimeExtensionRequestStateCopyWithImpl<$Res,
          CreateTimeExtensionRequestState>;
}

/// @nodoc
class _$CreateTimeExtensionRequestStateCopyWithImpl<$Res,
        $Val extends CreateTimeExtensionRequestState>
    implements $CreateTimeExtensionRequestStateCopyWith<$Res> {
  _$CreateTimeExtensionRequestStateCopyWithImpl(this._value, this._then);

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
    extends _$CreateTimeExtensionRequestStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial with DiagnosticableTreeMixin {
  const _$InitialImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'CreateTimeExtensionRequestState.initial'));
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

abstract class _Initial extends CreateTimeExtensionRequestState {
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
    extends _$CreateTimeExtensionRequestStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading with DiagnosticableTreeMixin {
  const _$LoadingImpl() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'CreateTimeExtensionRequestState.loading'));
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

abstract class _Loading extends CreateTimeExtensionRequestState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({ContractsModel? contractsModel});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$CreateTimeExtensionRequestStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractsModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == contractsModel
          ? _value.contractsModel
          : contractsModel // ignore: cast_nullable_to_non_nullable
              as ContractsModel?,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  const _$LoadedImpl(this.contractsModel) : super._();

  @override
  final ContractsModel? contractsModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestState.loaded(contractsModel: $contractsModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'CreateTimeExtensionRequestState.loaded'))
      ..add(DiagnosticsProperty('contractsModel', contractsModel));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.contractsModel, contractsModel) ||
                other.contractsModel == contractsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, contractsModel);

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

abstract class _Loaded extends CreateTimeExtensionRequestState {
  const factory _Loaded(final ContractsModel? contractsModel) = _$LoadedImpl;
  const _Loaded._() : super._();

  ContractsModel? get contractsModel;
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
    extends _$CreateTimeExtensionRequestStateCopyWithImpl<$Res, _$ErrorImpl>
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

class _$ErrorImpl extends _Error with DiagnosticableTreeMixin {
  const _$ErrorImpl(this.error) : super._();

  @override
  final String? error;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'CreateTimeExtensionRequestState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'CreateTimeExtensionRequestState.error'))
      ..add(DiagnosticsProperty('error', error));
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

abstract class _Error extends CreateTimeExtensionRequestState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
