// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'attendance_user_search.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$AttendanceUserSearchEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? mobileNumber) search,
    required TResult Function(List<String>? uuids) uuidSearch,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? mobileNumber)? search,
    TResult? Function(List<String>? uuids)? uuidSearch,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? mobileNumber)? search,
    TResult Function(List<String>? uuids)? uuidSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceUserEvent value) search,
    required TResult Function(SearchAttendanceUserUuidEvent value) uuidSearch,
    required TResult Function(DisposeSearchAttendanceUserEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
    TResult? Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult? Function(DisposeSearchAttendanceUserEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    TResult Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult Function(DisposeSearchAttendanceUserEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceUserSearchEventCopyWith<$Res> {
  factory $AttendanceUserSearchEventCopyWith(AttendanceUserSearchEvent value,
          $Res Function(AttendanceUserSearchEvent) then) =
      _$AttendanceUserSearchEventCopyWithImpl<$Res, AttendanceUserSearchEvent>;
}

/// @nodoc
class _$AttendanceUserSearchEventCopyWithImpl<$Res,
        $Val extends AttendanceUserSearchEvent>
    implements $AttendanceUserSearchEventCopyWith<$Res> {
  _$AttendanceUserSearchEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$SearchAttendanceUserEventImplCopyWith<$Res> {
  factory _$$SearchAttendanceUserEventImplCopyWith(
          _$SearchAttendanceUserEventImpl value,
          $Res Function(_$SearchAttendanceUserEventImpl) then) =
      __$$SearchAttendanceUserEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String? mobileNumber});
}

/// @nodoc
class __$$SearchAttendanceUserEventImplCopyWithImpl<$Res>
    extends _$AttendanceUserSearchEventCopyWithImpl<$Res,
        _$SearchAttendanceUserEventImpl>
    implements _$$SearchAttendanceUserEventImplCopyWith<$Res> {
  __$$SearchAttendanceUserEventImplCopyWithImpl(
      _$SearchAttendanceUserEventImpl _value,
      $Res Function(_$SearchAttendanceUserEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? mobileNumber = freezed,
  }) {
    return _then(_$SearchAttendanceUserEventImpl(
      mobileNumber: freezed == mobileNumber
          ? _value.mobileNumber
          : mobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc

class _$SearchAttendanceUserEventImpl
    with DiagnosticableTreeMixin
    implements SearchAttendanceUserEvent {
  const _$SearchAttendanceUserEventImpl({this.mobileNumber});

  @override
  final String? mobileNumber;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchEvent.search(mobileNumber: $mobileNumber)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceUserSearchEvent.search'))
      ..add(DiagnosticsProperty('mobileNumber', mobileNumber));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchAttendanceUserEventImpl &&
            (identical(other.mobileNumber, mobileNumber) ||
                other.mobileNumber == mobileNumber));
  }

  @override
  int get hashCode => Object.hash(runtimeType, mobileNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchAttendanceUserEventImplCopyWith<_$SearchAttendanceUserEventImpl>
      get copyWith => __$$SearchAttendanceUserEventImplCopyWithImpl<
          _$SearchAttendanceUserEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? mobileNumber) search,
    required TResult Function(List<String>? uuids) uuidSearch,
    required TResult Function() dispose,
  }) {
    return search(mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? mobileNumber)? search,
    TResult? Function(List<String>? uuids)? uuidSearch,
    TResult? Function()? dispose,
  }) {
    return search?.call(mobileNumber);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? mobileNumber)? search,
    TResult Function(List<String>? uuids)? uuidSearch,
    TResult Function()? dispose,
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
    required TResult Function(SearchAttendanceUserEvent value) search,
    required TResult Function(SearchAttendanceUserUuidEvent value) uuidSearch,
    required TResult Function(DisposeSearchAttendanceUserEvent value) dispose,
  }) {
    return search(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
    TResult? Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult? Function(DisposeSearchAttendanceUserEvent value)? dispose,
  }) {
    return search?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    TResult Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult Function(DisposeSearchAttendanceUserEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (search != null) {
      return search(this);
    }
    return orElse();
  }
}

abstract class SearchAttendanceUserEvent implements AttendanceUserSearchEvent {
  const factory SearchAttendanceUserEvent({final String? mobileNumber}) =
      _$SearchAttendanceUserEventImpl;

  String? get mobileNumber;
  @JsonKey(ignore: true)
  _$$SearchAttendanceUserEventImplCopyWith<_$SearchAttendanceUserEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$SearchAttendanceUserUuidEventImplCopyWith<$Res> {
  factory _$$SearchAttendanceUserUuidEventImplCopyWith(
          _$SearchAttendanceUserUuidEventImpl value,
          $Res Function(_$SearchAttendanceUserUuidEventImpl) then) =
      __$$SearchAttendanceUserUuidEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({List<String>? uuids});
}

/// @nodoc
class __$$SearchAttendanceUserUuidEventImplCopyWithImpl<$Res>
    extends _$AttendanceUserSearchEventCopyWithImpl<$Res,
        _$SearchAttendanceUserUuidEventImpl>
    implements _$$SearchAttendanceUserUuidEventImplCopyWith<$Res> {
  __$$SearchAttendanceUserUuidEventImplCopyWithImpl(
      _$SearchAttendanceUserUuidEventImpl _value,
      $Res Function(_$SearchAttendanceUserUuidEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? uuids = freezed,
  }) {
    return _then(_$SearchAttendanceUserUuidEventImpl(
      uuids: freezed == uuids
          ? _value._uuids
          : uuids // ignore: cast_nullable_to_non_nullable
              as List<String>?,
    ));
  }
}

/// @nodoc

class _$SearchAttendanceUserUuidEventImpl
    with DiagnosticableTreeMixin
    implements SearchAttendanceUserUuidEvent {
  const _$SearchAttendanceUserUuidEventImpl({final List<String>? uuids})
      : _uuids = uuids;

  final List<String>? _uuids;
  @override
  List<String>? get uuids {
    final value = _uuids;
    if (value == null) return null;
    if (_uuids is EqualUnmodifiableListView) return _uuids;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchEvent.uuidSearch(uuids: $uuids)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceUserSearchEvent.uuidSearch'))
      ..add(DiagnosticsProperty('uuids', uuids));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$SearchAttendanceUserUuidEventImpl &&
            const DeepCollectionEquality().equals(other._uuids, _uuids));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, const DeepCollectionEquality().hash(_uuids));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$SearchAttendanceUserUuidEventImplCopyWith<
          _$SearchAttendanceUserUuidEventImpl>
      get copyWith => __$$SearchAttendanceUserUuidEventImplCopyWithImpl<
          _$SearchAttendanceUserUuidEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? mobileNumber) search,
    required TResult Function(List<String>? uuids) uuidSearch,
    required TResult Function() dispose,
  }) {
    return uuidSearch(uuids);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? mobileNumber)? search,
    TResult? Function(List<String>? uuids)? uuidSearch,
    TResult? Function()? dispose,
  }) {
    return uuidSearch?.call(uuids);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? mobileNumber)? search,
    TResult Function(List<String>? uuids)? uuidSearch,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (uuidSearch != null) {
      return uuidSearch(uuids);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(SearchAttendanceUserEvent value) search,
    required TResult Function(SearchAttendanceUserUuidEvent value) uuidSearch,
    required TResult Function(DisposeSearchAttendanceUserEvent value) dispose,
  }) {
    return uuidSearch(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
    TResult? Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult? Function(DisposeSearchAttendanceUserEvent value)? dispose,
  }) {
    return uuidSearch?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    TResult Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult Function(DisposeSearchAttendanceUserEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (uuidSearch != null) {
      return uuidSearch(this);
    }
    return orElse();
  }
}

abstract class SearchAttendanceUserUuidEvent
    implements AttendanceUserSearchEvent {
  const factory SearchAttendanceUserUuidEvent({final List<String>? uuids}) =
      _$SearchAttendanceUserUuidEventImpl;

  List<String>? get uuids;
  @JsonKey(ignore: true)
  _$$SearchAttendanceUserUuidEventImplCopyWith<
          _$SearchAttendanceUserUuidEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DisposeSearchAttendanceUserEventImplCopyWith<$Res> {
  factory _$$DisposeSearchAttendanceUserEventImplCopyWith(
          _$DisposeSearchAttendanceUserEventImpl value,
          $Res Function(_$DisposeSearchAttendanceUserEventImpl) then) =
      __$$DisposeSearchAttendanceUserEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DisposeSearchAttendanceUserEventImplCopyWithImpl<$Res>
    extends _$AttendanceUserSearchEventCopyWithImpl<$Res,
        _$DisposeSearchAttendanceUserEventImpl>
    implements _$$DisposeSearchAttendanceUserEventImplCopyWith<$Res> {
  __$$DisposeSearchAttendanceUserEventImplCopyWithImpl(
      _$DisposeSearchAttendanceUserEventImpl _value,
      $Res Function(_$DisposeSearchAttendanceUserEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DisposeSearchAttendanceUserEventImpl
    with DiagnosticableTreeMixin
    implements DisposeSearchAttendanceUserEvent {
  const _$DisposeSearchAttendanceUserEventImpl();

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchEvent.dispose()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'AttendanceUserSearchEvent.dispose'));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DisposeSearchAttendanceUserEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String? mobileNumber) search,
    required TResult Function(List<String>? uuids) uuidSearch,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String? mobileNumber)? search,
    TResult? Function(List<String>? uuids)? uuidSearch,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String? mobileNumber)? search,
    TResult Function(List<String>? uuids)? uuidSearch,
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
    required TResult Function(SearchAttendanceUserEvent value) search,
    required TResult Function(SearchAttendanceUserUuidEvent value) uuidSearch,
    required TResult Function(DisposeSearchAttendanceUserEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(SearchAttendanceUserEvent value)? search,
    TResult? Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult? Function(DisposeSearchAttendanceUserEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(SearchAttendanceUserEvent value)? search,
    TResult Function(SearchAttendanceUserUuidEvent value)? uuidSearch,
    TResult Function(DisposeSearchAttendanceUserEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DisposeSearchAttendanceUserEvent
    implements AttendanceUserSearchEvent {
  const factory DisposeSearchAttendanceUserEvent() =
      _$DisposeSearchAttendanceUserEventImpl;
}

/// @nodoc
mixin _$AttendanceUserSearchState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(UserSearchModel? userSearchModel) loaded,
    required TResult Function(String? error) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(UserSearchModel? userSearchModel)? loaded,
    TResult? Function(String? error)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(UserSearchModel? userSearchModel)? loaded,
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
abstract class $AttendanceUserSearchStateCopyWith<$Res> {
  factory $AttendanceUserSearchStateCopyWith(AttendanceUserSearchState value,
          $Res Function(AttendanceUserSearchState) then) =
      _$AttendanceUserSearchStateCopyWithImpl<$Res, AttendanceUserSearchState>;
}

/// @nodoc
class _$AttendanceUserSearchStateCopyWithImpl<$Res,
        $Val extends AttendanceUserSearchState>
    implements $AttendanceUserSearchStateCopyWith<$Res> {
  _$AttendanceUserSearchStateCopyWithImpl(this._value, this._then);

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
    extends _$AttendanceUserSearchStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'AttendanceUserSearchState.initial()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'AttendanceUserSearchState.initial'));
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
    required TResult Function(UserSearchModel? userSearchModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(UserSearchModel? userSearchModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(UserSearchModel? userSearchModel)? loaded,
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

abstract class _Initial extends AttendanceUserSearchState {
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
    extends _$AttendanceUserSearchStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'AttendanceUserSearchState.loading()';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
        .add(DiagnosticsProperty('type', 'AttendanceUserSearchState.loading'));
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
    required TResult Function(UserSearchModel? userSearchModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(UserSearchModel? userSearchModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(UserSearchModel? userSearchModel)? loaded,
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

abstract class _Loading extends AttendanceUserSearchState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({UserSearchModel? userSearchModel});

  $UserSearchModelCopyWith<$Res>? get userSearchModel;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$AttendanceUserSearchStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? userSearchModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == userSearchModel
          ? _value.userSearchModel
          : userSearchModel // ignore: cast_nullable_to_non_nullable
              as UserSearchModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $UserSearchModelCopyWith<$Res>? get userSearchModel {
    if (_value.userSearchModel == null) {
      return null;
    }

    return $UserSearchModelCopyWith<$Res>(_value.userSearchModel!, (value) {
      return _then(_value.copyWith(userSearchModel: value));
    });
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded with DiagnosticableTreeMixin {
  const _$LoadedImpl(this.userSearchModel) : super._();

  @override
  final UserSearchModel? userSearchModel;

  @override
  String toString({DiagnosticLevel minLevel = DiagnosticLevel.info}) {
    return 'AttendanceUserSearchState.loaded(userSearchModel: $userSearchModel)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceUserSearchState.loaded'))
      ..add(DiagnosticsProperty('userSearchModel', userSearchModel));
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.userSearchModel, userSearchModel) ||
                other.userSearchModel == userSearchModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, userSearchModel);

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
    required TResult Function(UserSearchModel? userSearchModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return loaded(userSearchModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(UserSearchModel? userSearchModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return loaded?.call(userSearchModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(UserSearchModel? userSearchModel)? loaded,
    TResult Function(String? error)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(userSearchModel);
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

abstract class _Loaded extends AttendanceUserSearchState {
  const factory _Loaded(final UserSearchModel? userSearchModel) = _$LoadedImpl;
  const _Loaded._() : super._();

  UserSearchModel? get userSearchModel;
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
    extends _$AttendanceUserSearchStateCopyWithImpl<$Res, _$ErrorImpl>
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
    return 'AttendanceUserSearchState.error(error: $error)';
  }

  @override
  void debugFillProperties(DiagnosticPropertiesBuilder properties) {
    super.debugFillProperties(properties);
    properties
      ..add(DiagnosticsProperty('type', 'AttendanceUserSearchState.error'))
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
    required TResult Function(UserSearchModel? userSearchModel) loaded,
    required TResult Function(String? error) error,
  }) {
    return error(this.error);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(UserSearchModel? userSearchModel)? loaded,
    TResult? Function(String? error)? error,
  }) {
    return error?.call(this.error);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(UserSearchModel? userSearchModel)? loaded,
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

abstract class _Error extends AttendanceUserSearchState {
  const factory _Error(final String? error) = _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
