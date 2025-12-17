// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'de_enroll_attendee.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$AttendeeDeEnrollEvent {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            List<Map<String, dynamic>> attendeeList, String uuid)
        deEnroll,
    required TResult Function() dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
    TResult? Function()? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
    TResult Function()? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(DeEnrollAttendeeEvent value) deEnroll,
    required TResult Function(DeEnrollAttendeeDisposeEvent value) dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult? Function(DeEnrollAttendeeDisposeEvent value)? dispose,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult Function(DeEnrollAttendeeDisposeEvent value)? dispose,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeDeEnrollEventCopyWith<$Res> {
  factory $AttendeeDeEnrollEventCopyWith(AttendeeDeEnrollEvent value,
          $Res Function(AttendeeDeEnrollEvent) then) =
      _$AttendeeDeEnrollEventCopyWithImpl<$Res, AttendeeDeEnrollEvent>;
}

/// @nodoc
class _$AttendeeDeEnrollEventCopyWithImpl<$Res,
        $Val extends AttendeeDeEnrollEvent>
    implements $AttendeeDeEnrollEventCopyWith<$Res> {
  _$AttendeeDeEnrollEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$DeEnrollAttendeeEventImplCopyWith<$Res> {
  factory _$$DeEnrollAttendeeEventImplCopyWith(
          _$DeEnrollAttendeeEventImpl value,
          $Res Function(_$DeEnrollAttendeeEventImpl) then) =
      __$$DeEnrollAttendeeEventImplCopyWithImpl<$Res>;
  @useResult
  $Res call({List<Map<String, dynamic>> attendeeList, String uuid});
}

/// @nodoc
class __$$DeEnrollAttendeeEventImplCopyWithImpl<$Res>
    extends _$AttendeeDeEnrollEventCopyWithImpl<$Res,
        _$DeEnrollAttendeeEventImpl>
    implements _$$DeEnrollAttendeeEventImplCopyWith<$Res> {
  __$$DeEnrollAttendeeEventImplCopyWithImpl(_$DeEnrollAttendeeEventImpl _value,
      $Res Function(_$DeEnrollAttendeeEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendeeList = null,
    Object? uuid = null,
  }) {
    return _then(_$DeEnrollAttendeeEventImpl(
      attendeeList: null == attendeeList
          ? _value._attendeeList
          : attendeeList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$DeEnrollAttendeeEventImpl implements DeEnrollAttendeeEvent {
  const _$DeEnrollAttendeeEventImpl(
      {required final List<Map<String, dynamic>> attendeeList,
      required this.uuid})
      : _attendeeList = attendeeList;

  final List<Map<String, dynamic>> _attendeeList;
  @override
  List<Map<String, dynamic>> get attendeeList {
    if (_attendeeList is EqualUnmodifiableListView) return _attendeeList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_attendeeList);
  }

  @override
  final String uuid;

  @override
  String toString() {
    return 'AttendeeDeEnrollEvent.deEnroll(attendeeList: $attendeeList, uuid: $uuid)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DeEnrollAttendeeEventImpl &&
            const DeepCollectionEquality()
                .equals(other._attendeeList, _attendeeList) &&
            (identical(other.uuid, uuid) || other.uuid == uuid));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendeeList), uuid);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$DeEnrollAttendeeEventImplCopyWith<_$DeEnrollAttendeeEventImpl>
      get copyWith => __$$DeEnrollAttendeeEventImplCopyWithImpl<
          _$DeEnrollAttendeeEventImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            List<Map<String, dynamic>> attendeeList, String uuid)
        deEnroll,
    required TResult Function() dispose,
  }) {
    return deEnroll(attendeeList, uuid);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
    TResult? Function()? dispose,
  }) {
    return deEnroll?.call(attendeeList, uuid);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
    TResult Function()? dispose,
    required TResult orElse(),
  }) {
    if (deEnroll != null) {
      return deEnroll(attendeeList, uuid);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(DeEnrollAttendeeEvent value) deEnroll,
    required TResult Function(DeEnrollAttendeeDisposeEvent value) dispose,
  }) {
    return deEnroll(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult? Function(DeEnrollAttendeeDisposeEvent value)? dispose,
  }) {
    return deEnroll?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult Function(DeEnrollAttendeeDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (deEnroll != null) {
      return deEnroll(this);
    }
    return orElse();
  }
}

abstract class DeEnrollAttendeeEvent implements AttendeeDeEnrollEvent {
  const factory DeEnrollAttendeeEvent(
      {required final List<Map<String, dynamic>> attendeeList,
      required final String uuid}) = _$DeEnrollAttendeeEventImpl;

  List<Map<String, dynamic>> get attendeeList;
  String get uuid;
  @JsonKey(ignore: true)
  _$$DeEnrollAttendeeEventImplCopyWith<_$DeEnrollAttendeeEventImpl>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$DeEnrollAttendeeDisposeEventImplCopyWith<$Res> {
  factory _$$DeEnrollAttendeeDisposeEventImplCopyWith(
          _$DeEnrollAttendeeDisposeEventImpl value,
          $Res Function(_$DeEnrollAttendeeDisposeEventImpl) then) =
      __$$DeEnrollAttendeeDisposeEventImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$DeEnrollAttendeeDisposeEventImplCopyWithImpl<$Res>
    extends _$AttendeeDeEnrollEventCopyWithImpl<$Res,
        _$DeEnrollAttendeeDisposeEventImpl>
    implements _$$DeEnrollAttendeeDisposeEventImplCopyWith<$Res> {
  __$$DeEnrollAttendeeDisposeEventImplCopyWithImpl(
      _$DeEnrollAttendeeDisposeEventImpl _value,
      $Res Function(_$DeEnrollAttendeeDisposeEventImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$DeEnrollAttendeeDisposeEventImpl
    implements DeEnrollAttendeeDisposeEvent {
  const _$DeEnrollAttendeeDisposeEventImpl();

  @override
  String toString() {
    return 'AttendeeDeEnrollEvent.dispose()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$DeEnrollAttendeeDisposeEventImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            List<Map<String, dynamic>> attendeeList, String uuid)
        deEnroll,
    required TResult Function() dispose,
  }) {
    return dispose();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
    TResult? Function()? dispose,
  }) {
    return dispose?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>> attendeeList, String uuid)?
        deEnroll,
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
    required TResult Function(DeEnrollAttendeeEvent value) deEnroll,
    required TResult Function(DeEnrollAttendeeDisposeEvent value) dispose,
  }) {
    return dispose(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult? Function(DeEnrollAttendeeDisposeEvent value)? dispose,
  }) {
    return dispose?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(DeEnrollAttendeeEvent value)? deEnroll,
    TResult Function(DeEnrollAttendeeDisposeEvent value)? dispose,
    required TResult orElse(),
  }) {
    if (dispose != null) {
      return dispose(this);
    }
    return orElse();
  }
}

abstract class DeEnrollAttendeeDisposeEvent implements AttendeeDeEnrollEvent {
  const factory DeEnrollAttendeeDisposeEvent() =
      _$DeEnrollAttendeeDisposeEventImpl;
}

/// @nodoc
mixin _$AttendeeDeEnrollState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(String uuid) loaded,
    required TResult Function(String? error, String uuid) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(String uuid)? loaded,
    TResult? Function(String? error, String uuid)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(String uuid)? loaded,
    TResult Function(String? error, String uuid)? error,
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
abstract class $AttendeeDeEnrollStateCopyWith<$Res> {
  factory $AttendeeDeEnrollStateCopyWith(AttendeeDeEnrollState value,
          $Res Function(AttendeeDeEnrollState) then) =
      _$AttendeeDeEnrollStateCopyWithImpl<$Res, AttendeeDeEnrollState>;
}

/// @nodoc
class _$AttendeeDeEnrollStateCopyWithImpl<$Res,
        $Val extends AttendeeDeEnrollState>
    implements $AttendeeDeEnrollStateCopyWith<$Res> {
  _$AttendeeDeEnrollStateCopyWithImpl(this._value, this._then);

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
    extends _$AttendeeDeEnrollStateCopyWithImpl<$Res, _$InitialImpl>
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
    return 'AttendeeDeEnrollState.initial()';
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
    required TResult Function(String uuid) loaded,
    required TResult Function(String? error, String uuid) error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(String uuid)? loaded,
    TResult? Function(String? error, String uuid)? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(String uuid)? loaded,
    TResult Function(String? error, String uuid)? error,
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

abstract class _Initial extends AttendeeDeEnrollState {
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
    extends _$AttendeeDeEnrollStateCopyWithImpl<$Res, _$LoadingImpl>
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
    return 'AttendeeDeEnrollState.loading()';
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
    required TResult Function(String uuid) loaded,
    required TResult Function(String? error, String uuid) error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(String uuid)? loaded,
    TResult? Function(String? error, String uuid)? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(String uuid)? loaded,
    TResult Function(String? error, String uuid)? error,
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

abstract class _Loading extends AttendeeDeEnrollState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({String uuid});
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$AttendeeDeEnrollStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? uuid = null,
  }) {
    return _then(_$LoadedImpl(
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl({required this.uuid}) : super._();

  @override
  final String uuid;

  @override
  String toString() {
    return 'AttendeeDeEnrollState.loaded(uuid: $uuid)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.uuid, uuid) || other.uuid == uuid));
  }

  @override
  int get hashCode => Object.hash(runtimeType, uuid);

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
    required TResult Function(String uuid) loaded,
    required TResult Function(String? error, String uuid) error,
  }) {
    return loaded(uuid);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(String uuid)? loaded,
    TResult? Function(String? error, String uuid)? error,
  }) {
    return loaded?.call(uuid);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(String uuid)? loaded,
    TResult Function(String? error, String uuid)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(uuid);
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

abstract class _Loaded extends AttendeeDeEnrollState {
  const factory _Loaded({required final String uuid}) = _$LoadedImpl;
  const _Loaded._() : super._();

  String get uuid;
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
  $Res call({String? error, String uuid});
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$AttendeeDeEnrollStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? error = freezed,
    Object? uuid = null,
  }) {
    return _then(_$ErrorImpl(
      freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as String?,
      uuid: null == uuid
          ? _value.uuid
          : uuid // ignore: cast_nullable_to_non_nullable
              as String,
    ));
  }
}

/// @nodoc

class _$ErrorImpl extends _Error {
  const _$ErrorImpl(this.error, {required this.uuid}) : super._();

  @override
  final String? error;
  @override
  final String uuid;

  @override
  String toString() {
    return 'AttendeeDeEnrollState.error(error: $error, uuid: $uuid)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ErrorImpl &&
            (identical(other.error, error) || other.error == error) &&
            (identical(other.uuid, uuid) || other.uuid == uuid));
  }

  @override
  int get hashCode => Object.hash(runtimeType, error, uuid);

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
    required TResult Function(String uuid) loaded,
    required TResult Function(String? error, String uuid) error,
  }) {
    return error(this.error, uuid);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(String uuid)? loaded,
    TResult? Function(String? error, String uuid)? error,
  }) {
    return error?.call(this.error, uuid);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(String uuid)? loaded,
    TResult Function(String? error, String uuid)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this.error, uuid);
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

abstract class _Error extends AttendeeDeEnrollState {
  const factory _Error(final String? error, {required final String uuid}) =
      _$ErrorImpl;
  const _Error._() : super._();

  String? get error;
  String get uuid;
  @JsonKey(ignore: true)
  _$$ErrorImplCopyWith<_$ErrorImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
