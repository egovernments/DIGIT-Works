// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'search_individual_muster_roll.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$IndividualMusterRollSearchEvent {
  String get musterRollNumber => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String musterRollNumber, String tenantId)
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String musterRollNumber, String tenantId)?
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String musterRollNumber, String tenantId)?
        individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualMusterRollEvent value)
        individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualMusterRollEvent value)? individualSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualMusterRollEvent value)? individualSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $IndividualMusterRollSearchEventCopyWith<IndividualMusterRollSearchEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualMusterRollSearchEventCopyWith<$Res> {
  factory $IndividualMusterRollSearchEventCopyWith(
          IndividualMusterRollSearchEvent value,
          $Res Function(IndividualMusterRollSearchEvent) then) =
      _$IndividualMusterRollSearchEventCopyWithImpl<$Res,
          IndividualMusterRollSearchEvent>;
  @useResult
  $Res call({String musterRollNumber, String tenantId});
}

/// @nodoc
class _$IndividualMusterRollSearchEventCopyWithImpl<$Res,
        $Val extends IndividualMusterRollSearchEvent>
    implements $IndividualMusterRollSearchEventCopyWith<$Res> {
  _$IndividualMusterRollSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollNumber = null,
    Object? tenantId = null,
  }) {
    return _then(_value.copyWith(
      musterRollNumber: null == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SearchIndividualMusterRollEventCopyWith<$Res>
    implements $IndividualMusterRollSearchEventCopyWith<$Res> {
  factory _$$SearchIndividualMusterRollEventCopyWith(
          _$SearchIndividualMusterRollEvent value,
          $Res Function(_$SearchIndividualMusterRollEvent) then) =
      __$$SearchIndividualMusterRollEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String musterRollNumber, String tenantId});
}

/// @nodoc
class __$$SearchIndividualMusterRollEventCopyWithImpl<$Res>
    extends _$IndividualMusterRollSearchEventCopyWithImpl<$Res,
        _$SearchIndividualMusterRollEvent>
    implements _$$SearchIndividualMusterRollEventCopyWith<$Res> {
  __$$SearchIndividualMusterRollEventCopyWithImpl(
      _$SearchIndividualMusterRollEvent _value,
      $Res Function(_$SearchIndividualMusterRollEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollNumber = null,
    Object? tenantId = null,
  }) {
    return _then(_$SearchIndividualMusterRollEvent(
      musterRollNumber: null == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$SearchIndividualMusterRollEvent
    with DiagnosticableTreeMixin
    implements SearchIndividualMusterRollEvent {
  const _$SearchIndividualMusterRollEvent(
      {this.musterRollNumber = '', this.tenantId = ''});

  @override
  @JsonKey()
  final String musterRollNumber;
  @override
  @JsonKey()
  final String tenantId;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualMusterRollSearchEvent.individualSearch(musterRollNumber: $musterRollNumber, tenantId: $tenantId)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'IndividualMusterRollSearchEvent.individualSearch'))
      ..add(DiagnosticsProperty('musterRollNumber', musterRollNumber))
      ..add(DiagnosticsProperty('tenantId', tenantId));
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchIndividualMusterRollEvent &&
            (identical(other.musterRollNumber, musterRollNumber) ||
                other.musterRollNumber == musterRollNumber) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId));
  }

  @override
  int get hashCode => Object.hash(runtimeType, musterRollNumber, tenantId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchIndividualMusterRollEventCopyWith<_$SearchIndividualMusterRollEvent>
      get copyWith => __$$SearchIndividualMusterRollEventCopyWithImpl<
          _$SearchIndividualMusterRollEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String musterRollNumber, String tenantId)
        individualSearch,
  }) {
    return individualSearch(musterRollNumber, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String musterRollNumber, String tenantId)?
        individualSearch,
  }) {
    return individualSearch?.call(musterRollNumber, tenantId);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String musterRollNumber, String tenantId)?
        individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(musterRollNumber, tenantId);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchIndividualMusterRollEvent value)
        individualSearch,
  }) {
    return individualSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchIndividualMusterRollEvent value)? individualSearch,
  }) {
    return individualSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchIndividualMusterRollEvent value)? individualSearch,
    required TResult orElse(),
  }) {
    if (individualSearch != null) {
      return individualSearch(this);
    }
    return orElse();
  }
}

abstract class SearchIndividualMusterRollEvent
    implements IndividualMusterRollSearchEvent {
  const factory SearchIndividualMusterRollEvent(
      {final String musterRollNumber,
      final String tenantId}) = _$SearchIndividualMusterRollEvent;

  @override
  String get musterRollNumber;
  @override
  String get tenantId;
  @override
  @JsonKey(ignore: true)
  _$$SearchIndividualMusterRollEventCopyWith<_$SearchIndividualMusterRollEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$IndividualMusterRollSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
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
abstract class $IndividualMusterRollSearchStateCopyWith<$Res> {
  factory $IndividualMusterRollSearchStateCopyWith(
          IndividualMusterRollSearchState value,
          $Res Function(IndividualMusterRollSearchState) then) =
      _$IndividualMusterRollSearchStateCopyWithImpl<$Res,
          IndividualMusterRollSearchState>;
}

/// @nodoc
class _$IndividualMusterRollSearchStateCopyWithImpl<$Res,
        $Val extends IndividualMusterRollSearchState>
    implements $IndividualMusterRollSearchStateCopyWith<$Res> {
  _$IndividualMusterRollSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$IndividualMusterRollSearchStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial with DiagnosticableTreeMixin {
  const _$_Initial() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualMusterRollSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'IndividualMusterRollSearchState.initial'));
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
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Initial extends IndividualMusterRollSearchState {
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
    extends _$IndividualMusterRollSearchStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading with DiagnosticableTreeMixin {
  const _$_Loading() : super._();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualMusterRollSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'IndividualMusterRollSearchState.loading'));
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
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Loading extends IndividualMusterRollSearchState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
  @useResult
  $Res call({MusterRollsModel? musterRollsModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$IndividualMusterRollSearchStateCopyWithImpl<$Res, _$_Loaded>
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
              as MusterRollsModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel {
    if (_value.musterRollsModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.musterRollsModel!, (value) {
      return _then(_value.copyWith(musterRollsModel: value));
    });
  }
}

/// @nodoc

class _$_Loaded extends _Loaded with DiagnosticableTreeMixin {
  const _$_Loaded(this.musterRollsModel) : super._();

  @override
  final MusterRollsModel? musterRollsModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'IndividualMusterRollSearchState.loaded(musterRollsModel: $musterRollsModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'IndividualMusterRollSearchState.loaded'))
      ..add(DiagnosticsProperty('musterRollsModel', musterRollsModel));
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
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function(String? error)? error,
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

abstract class _Loaded extends IndividualMusterRollSearchState {
  const factory _Loaded(final MusterRollsModel? musterRollsModel) = _$_Loaded;
  const _Loaded._() : super._();

  MusterRollsModel? get musterRollsModel;
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
    extends _$IndividualMusterRollSearchStateCopyWithImpl<$Res, _$_Error>
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
    return 'IndividualMusterRollSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'IndividualMusterRollSearchState.error'))
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
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
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

abstract class _Error extends IndividualMusterRollSearchState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
