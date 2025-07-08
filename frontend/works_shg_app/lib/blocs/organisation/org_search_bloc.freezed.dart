// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'org_search_bloc.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$ORGSearchEvent {
  String get mobileNumber => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String mobileNumber) search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String mobileNumber)? search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String mobileNumber)? search,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchORGEvent value) search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchORGEvent value)? search,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchORGEvent value)? search,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ORGSearchEventCopyWith<ORGSearchEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ORGSearchEventCopyWith<$Res> {
  factory $ORGSearchEventCopyWith(
          ORGSearchEvent value, $Res Function(ORGSearchEvent) then) =
      _$ORGSearchEventCopyWithImpl<$Res, ORGSearchEvent>;
  @useResult
  $Res call({String mobileNumber});
}

/// @nodoc
class _$ORGSearchEventCopyWithImpl<$Res, $Val extends ORGSearchEvent>
    implements $ORGSearchEventCopyWith<$Res> {
  _$ORGSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mobileNumber = null,
  }) {
    return _then(_value.copyWith(
      mobileNumber: null == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SearchORGEventCopyWith<$Res>
    implements $ORGSearchEventCopyWith<$Res> {
  factory _$$SearchORGEventCopyWith(
          _$SearchORGEvent value, $Res Function(_$SearchORGEvent) then) =
      __$$SearchORGEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String mobileNumber});
}

/// @nodoc
class __$$SearchORGEventCopyWithImpl<$Res>
    extends _$ORGSearchEventCopyWithImpl<$Res, _$SearchORGEvent>
    implements _$$SearchORGEventCopyWith<$Res> {
  __$$SearchORGEventCopyWithImpl(
      _$SearchORGEvent _value, $Res Function(_$SearchORGEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mobileNumber = null,
  }) {
    return _then(_$SearchORGEvent(
      null == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchORGEvent with DiagnosticableTreeMixin implements SearchORGEvent {
  const _$SearchORGEvent(this.mobileNumber);

  @override
  final String mobileNumber;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'ORGSearchEvent.search(mobileNumber: $mobileNumber)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'ORGSearchEvent.search'))
      ..add(DiagnosticsProperty('mobileNumber', mobileNumber));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchORGEvent &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, mobileNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchORGEventCopyWith<_$SearchORGEvent> get copyWith =>
      __$$SearchORGEventCopyWithImpl<_$SearchORGEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String mobileNumber) search,
  }) {
    return search(mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String mobileNumber)? search,
  }) {
    return search?.call(mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String mobileNumber)? search,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(mobileNumber);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchORGEvent value) search,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchORGEvent value)? search,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchORGEvent value)? search,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchORGEvent implements ORGSearchEvent {
  const factory SearchORGEvent(final String mobileNumber) = _$SearchORGEvent;

  @override
  String get mobileNumber;
  @override
  @JsonKey(ignore: true)
  _$$SearchORGEventCopyWith<_$SearchORGEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$ORGSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(OrganisationListModel? organisationListModel)
        loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(OrganisationListModel? organisationListModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(OrganisationListModel? organisationListModel)? loaded,
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
abstract class $ORGSearchStateCopyWith<$Res> {
  factory $ORGSearchStateCopyWith(
          ORGSearchState value, $Res Function(ORGSearchState) then) =
      _$ORGSearchStateCopyWithImpl<$Res, ORGSearchState>;
}

/// @nodoc
class _$ORGSearchStateCopyWithImpl<$Res, $Val extends ORGSearchState>
    implements $ORGSearchStateCopyWith<$Res> {
  _$ORGSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$ORGSearchStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'ORGSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'ORGSearchState.initial'));
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
    required TResult Function(OrganisationListModel? organisationListModel)
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
    TResult? Function(OrganisationListModel? organisationListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(OrganisationListModel? organisationListModel)? loaded,
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

abstract class _Initial extends ORGSearchState {
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
    extends _$ORGSearchStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'ORGSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(DiagnosticsProperty('type', 'ORGSearchState.loading'));
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
    required TResult Function(OrganisationListModel? organisationListModel)
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
    TResult? Function(OrganisationListModel? organisationListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(OrganisationListModel? organisationListModel)? loaded,
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

abstract class _Loading extends ORGSearchState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({OrganisationListModel? organisationListModel});

  $OrganisationListModelCopyWith<$Res>? get organisationListModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$ORGSearchStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? organisationListModel = freezed,
  }) {
    return _then(_$_Loaded(
      freezed == organisationListModel
          ? _value.organisationListModel
          : organisationListModel // ignore: cast_nullable_to_non_nullable
              as OrganisationListModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $OrganisationListModelCopyWith<$Res>? get organisationListModel {
    if (_value.organisationListModel == null) {
      return null;
    }

    return $OrganisationListModelCopyWith<$Res>(_value.organisationListModel!,
        (value) {
      return _then(_value.copyWith(organisationListModel: value));
    });
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.organisationListModel) : super._();

  @override
  final OrganisationListModel? organisationListModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'ORGSearchState.loaded(organisationListModel: $organisationListModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'ORGSearchState.loaded'))
      ..add(
          DiagnosticsProperty('organisationListModel', organisationListModel));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_Loaded &&
            (identical(other.organisationListModel, organisationListModel) ||
                other.organisationListModel == organisationListModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, organisationListModel);

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
    required TResult Function(OrganisationListModel? organisationListModel)
        loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(organisationListModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(OrganisationListModel? organisationListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(organisationListModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(OrganisationListModel? organisationListModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(organisationListModel);
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

abstract class _Loaded extends ORGSearchState {
  const factory _Loaded(final OrganisationListModel? organisationListModel) =
      _$_Loaded;
  const _Loaded._() : super._();

  OrganisationListModel? get organisationListModel;
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
    extends _$ORGSearchStateCopyWithImpl<$Res, _$_Error>
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
    return 'ORGSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'ORGSearchState.error'))
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
    required TResult Function(OrganisationListModel? organisationListModel)
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
    TResult? Function(OrganisationListModel? organisationListModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(OrganisationListModel? organisationListModel)? loaded,
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

abstract class _Error extends ORGSearchState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
