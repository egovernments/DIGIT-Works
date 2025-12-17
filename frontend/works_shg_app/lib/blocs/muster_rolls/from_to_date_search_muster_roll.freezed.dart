// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'from_to_date_search_muster_roll.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MusterRollFromToDateSearchEvent {
  String get registerId => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  int get fromDate => throw _privateConstructorUsedError;
  int get toDate => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String registerId, String tenantId, int fromDate, int toDate)
        fromToDateSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String registerId, String tenantId, int fromDate, int toDate)?
        fromToDateSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String registerId, String tenantId, int fromDate, int toDate)?
        fromToDateSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchMusterRollFromToDateEvent value)
        fromToDateSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchMusterRollFromToDateEvent value)? fromToDateSearch,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchMusterRollFromToDateEvent value)? fromToDateSearch,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterRollFromToDateSearchEventCopyWith<MusterRollFromToDateSearchEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollFromToDateSearchEventCopyWith<$Res> {
  factory $MusterRollFromToDateSearchEventCopyWith(
          MusterRollFromToDateSearchEvent value,
          $Res Function(MusterRollFromToDateSearchEvent) then) =
      _$MusterRollFromToDateSearchEventCopyWithImpl<$Res,
          MusterRollFromToDateSearchEvent>;
  @useResult
  $Res call({String registerId, String tenantId, int fromDate, int toDate});
}

/// @nodoc
class _$MusterRollFromToDateSearchEventCopyWithImpl<$Res,
        $Val extends MusterRollFromToDateSearchEvent>
    implements $MusterRollFromToDateSearchEventCopyWith<$Res> {
  _$MusterRollFromToDateSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registerId = null,
    Object? tenantId = null,
    Object? fromDate = null,
    Object? toDate = null,
  }) {
    return _then(_value.copyWith(
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      fromDate: null == fromDate
          ? _value.fromDate
          : fromDate // ignore: cast_nullable_to_non_nullable
              as int,
      toDate: null == toDate
          ? _value.toDate
          : toDate // ignore: cast_nullable_to_non_nullable
              as int,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$SearchMusterRollFromToDateEventImplCopyWith<$Res>
    implements $MusterRollFromToDateSearchEventCopyWith<$Res> {
  factory _$$SearchMusterRollFromToDateEventImplCopyWith(
          _$SearchMusterRollFromToDateEventImpl value,
          $Res Function(_$SearchMusterRollFromToDateEventImpl) then) =
      __$$SearchMusterRollFromToDateEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String registerId, String tenantId, int fromDate, int toDate});
}

/// @nodoc
class __$$SearchMusterRollFromToDateEventImplCopyWithImpl<$Res>
    extends _$MusterRollFromToDateSearchEventCopyWithImpl<$Res,
        _$SearchMusterRollFromToDateEventImpl>
    implements _$$SearchMusterRollFromToDateEventImplCopyWith<$Res> {
  __$$SearchMusterRollFromToDateEventImplCopyWithImpl(
      _$SearchMusterRollFromToDateEventImpl _value,
      $Res Function(_$SearchMusterRollFromToDateEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registerId = null,
    Object? tenantId = null,
    Object? fromDate = null,
    Object? toDate = null,
  }) {
    return _then(_$SearchMusterRollFromToDateEventImpl(
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      fromDate: null == fromDate
          ? _value.fromDate
          : fromDate // ignore: cast_nullable_to_non_nullable
              as int,
      toDate: null == toDate
          ? _value.toDate
          : toDate // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$SearchMusterRollFromToDateEventImpl
    with DiagnosticableTreeMixin
    implements SearchMusterRollFromToDateEvent {
  const _$SearchMusterRollFromToDateEventImpl(
      {this.registerId = '',
      this.tenantId = '',
      this.fromDate = 0,
      this.toDate = 0});

  @override
  @JsonKey()
  final String registerId;
  @override
  @JsonKey()
  final String tenantId;
  @override
  @JsonKey()
  final int fromDate;
  @override
  @JsonKey()
  final int toDate;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'MusterRollFromToDateSearchEvent.fromToDateSearch(registerId: $registerId, tenantId: $tenantId, fromDate: $fromDate, toDate: $toDate)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty(
          'type', 'MusterRollFromToDateSearchEvent.fromToDateSearch'))
      ..add(DiagnosticsProperty('registerId', registerId))
      ..add(DiagnosticsProperty('tenantId', tenantId))
      ..add(DiagnosticsProperty('fromDate', fromDate))
      ..add(DiagnosticsProperty('toDate', toDate));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchMusterRollFromToDateEventImpl &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.fromDate, fromDate) ||
                other.fromDate == fromDate) &&
            (identical(other.toDate, toDate) || other.toDate == toDate));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, registerId, tenantId, fromDate, toDate);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchMusterRollFromToDateEventImplCopyWith<
          _$SearchMusterRollFromToDateEventImpl>
      get copyWith => __$$SearchMusterRollFromToDateEventImplCopyWithImpl<
          _$SearchMusterRollFromToDateEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String registerId, String tenantId, int fromDate, int toDate)
        fromToDateSearch,
  }) {
    return fromToDateSearch(registerId, tenantId, fromDate, toDate);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String registerId, String tenantId, int fromDate, int toDate)?
        fromToDateSearch,
  }) {
    return fromToDateSearch?.call(registerId, tenantId, fromDate, toDate);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String registerId, String tenantId, int fromDate, int toDate)?
        fromToDateSearch,
    required TResult orElse(),
  }) {
    if (fromToDateSearch != null) {
      return fromToDateSearch(registerId, tenantId, fromDate, toDate);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchMusterRollFromToDateEvent value)
        fromToDateSearch,
  }) {
    return fromToDateSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchMusterRollFromToDateEvent value)? fromToDateSearch,
  }) {
    return fromToDateSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchMusterRollFromToDateEvent value)? fromToDateSearch,
    required TResult orElse(),
  }) {
    if (fromToDateSearch != null) {
      return fromToDateSearch(this);
    }
    return orElse();
  }
}

abstract class SearchMusterRollFromToDateEvent
    implements MusterRollFromToDateSearchEvent {
  const factory SearchMusterRollFromToDateEvent(
      {final String registerId,
      final String tenantId,
      final int fromDate,
      final int toDate}) = _$SearchMusterRollFromToDateEventImpl;

  @override
  String get registerId;
  @override
  String get tenantId;
  @override
  int get fromDate;
  @override
  int get toDate;
  @override
  @JsonKey(ignore: true)
  _$$SearchMusterRollFromToDateEventImplCopyWith<
          _$SearchMusterRollFromToDateEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterRollFromToDateSearchState {
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
abstract class $MusterRollFromToDateSearchStateCopyWith<$Res> {
  factory $MusterRollFromToDateSearchStateCopyWith(
          MusterRollFromToDateSearchState value,
          $Res Function(MusterRollFromToDateSearchState) then) =
      _$MusterRollFromToDateSearchStateCopyWithImpl<$Res,
          MusterRollFromToDateSearchState>;
}

/// @nodoc
class _$MusterRollFromToDateSearchStateCopyWithImpl<$Res,
        $Val extends MusterRollFromToDateSearchState>
    implements $MusterRollFromToDateSearchStateCopyWith<$Res> {
  _$MusterRollFromToDateSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$MusterRollFromToDateSearchStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'MusterRollFromToDateSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'MusterRollFromToDateSearchState.initial'));
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

abstract class _Initial extends MusterRollFromToDateSearchState {
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
    extends _$MusterRollFromToDateSearchStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'MusterRollFromToDateSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties.add(
        DiagnosticsProperty('type', 'MusterRollFromToDateSearchState.loading'));
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

abstract class _Loading extends MusterRollFromToDateSearchState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({MusterRollsModel? musterRollsModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MusterRollFromToDateSearchStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollsModel = freezed,
  }) {
    return _then(_$LoadedImpl(
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

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  const _$LoadedImpl(this.musterRollsModel) : super._();

  @override
  final MusterRollsModel? musterRollsModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'MusterRollFromToDateSearchState.loaded(musterRollsModel: $musterRollsModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'MusterRollFromToDateSearchState.loaded'))
      ..add(DiagnosticsProperty('musterRollsModel', musterRollsModel));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, musterRollsModel);

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

abstract class _Loaded extends MusterRollFromToDateSearchState {
  const factory _Loaded(final MusterRollsModel? musterRollsModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  MusterRollsModel? get musterRollsModel;
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
    extends _$MusterRollFromToDateSearchStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'MusterRollFromToDateSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(
          DiagnosticsProperty('type', 'MusterRollFromToDateSearchState.error'))
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

abstract class _Error extends MusterRollFromToDateSearchState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
