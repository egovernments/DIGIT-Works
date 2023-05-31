// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'attendance_create_log.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendanceLogCreateEvent {
  List<Map<String, dynamic>>? get attendanceList =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        create,
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult Function(List<Map<String, dynamic>>? attendanceList)? update,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendanceLogEvent value) create,
    required TResult Function(UpdateAttendanceLogEvent value) update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendanceLogEvent value)? create,
    TResult? Function(UpdateAttendanceLogEvent value)? update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendanceLogEvent value)? create,
    TResult Function(UpdateAttendanceLogEvent value)? update,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceLogCreateEventCopyWith<AttendanceLogCreateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceLogCreateEventCopyWith<$Res> {
  factory $AttendanceLogCreateEventCopyWith(AttendanceLogCreateEvent value,
          $Res Function(AttendanceLogCreateEvent) then) =
      _$AttendanceLogCreateEventCopyWithImpl<$Res, AttendanceLogCreateEvent>;
  @useResult
  $Res call({List<Map<String, dynamic>>? attendanceList});
}

/// @nodoc
class _$AttendanceLogCreateEventCopyWithImpl<$Res,
        $Val extends AttendanceLogCreateEvent>
    implements $AttendanceLogCreateEventCopyWith<$Res> {
  _$AttendanceLogCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceList = freezed,
  }) {
    return _then(_value.copyWith(
      attendanceList: freezed == attendanceList
          ? _value.attendanceList
          : attendanceList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateAttendanceLogEventCopyWith<$Res>
    implements $AttendanceLogCreateEventCopyWith<$Res> {
  factory _$$CreateAttendanceLogEventCopyWith(_$CreateAttendanceLogEvent value,
          $Res Function(_$CreateAttendanceLogEvent) then) =
      __$$CreateAttendanceLogEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<Map<String, dynamic>>? attendanceList});
}

/// @nodoc
class __$$CreateAttendanceLogEventCopyWithImpl<$Res>
    extends _$AttendanceLogCreateEventCopyWithImpl<$Res,
        _$CreateAttendanceLogEvent>
    implements _$$CreateAttendanceLogEventCopyWith<$Res> {
  __$$CreateAttendanceLogEventCopyWithImpl(_$CreateAttendanceLogEvent _value,
      $Res Function(_$CreateAttendanceLogEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceList = freezed,
  }) {
    return _then(_$CreateAttendanceLogEvent(
      attendanceList: freezed == attendanceList
          ? _value._attendanceList
          : attendanceList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ));
  }
}

/// @nodoc

class _$CreateAttendanceLogEvent implements CreateAttendanceLogEvent {
  const _$CreateAttendanceLogEvent(
      {final List<Map<String, dynamic>>? attendanceList})
      : _attendanceList = attendanceList;

  final List<Map<String, dynamic>>? _attendanceList;
  @override
  List<Map<String, dynamic>>? get attendanceList {
    final value = _attendanceList;
    if (value == null) return null;
    if (_attendanceList is EqualUnmodifiableListView) return _attendanceList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendanceLogCreateEvent.create(attendanceList: $attendanceList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateAttendanceLogEvent &&
            const DeepCollectionEquality()
                .equals(other._attendanceList, _attendanceList));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendanceList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateAttendanceLogEventCopyWith<_$CreateAttendanceLogEvent>
      get copyWith =>
          __$$CreateAttendanceLogEventCopyWithImpl<_$CreateAttendanceLogEvent>(
              this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        create,
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        update,
  }) {
    return create(attendanceList);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? update,
  }) {
    return create?.call(attendanceList);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult Function(List<Map<String, dynamic>>? attendanceList)? update,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(attendanceList);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendanceLogEvent value) create,
    required TResult Function(UpdateAttendanceLogEvent value) update,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendanceLogEvent value)? create,
    TResult? Function(UpdateAttendanceLogEvent value)? update,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendanceLogEvent value)? create,
    TResult Function(UpdateAttendanceLogEvent value)? update,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateAttendanceLogEvent implements AttendanceLogCreateEvent {
  const factory CreateAttendanceLogEvent(
          {final List<Map<String, dynamic>>? attendanceList}) =
      _$CreateAttendanceLogEvent;

  @override
  List<Map<String, dynamic>>? get attendanceList;
  @override
  @JsonKey(ignore: true)
  _$$CreateAttendanceLogEventCopyWith<_$CreateAttendanceLogEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateAttendanceLogEventCopyWith<$Res>
    implements $AttendanceLogCreateEventCopyWith<$Res> {
  factory _$$UpdateAttendanceLogEventCopyWith(_$UpdateAttendanceLogEvent value,
          $Res Function(_$UpdateAttendanceLogEvent) then) =
      __$$UpdateAttendanceLogEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<Map<String, dynamic>>? attendanceList});
}

/// @nodoc
class __$$UpdateAttendanceLogEventCopyWithImpl<$Res>
    extends _$AttendanceLogCreateEventCopyWithImpl<$Res,
        _$UpdateAttendanceLogEvent>
    implements _$$UpdateAttendanceLogEventCopyWith<$Res> {
  __$$UpdateAttendanceLogEventCopyWithImpl(_$UpdateAttendanceLogEvent _value,
      $Res Function(_$UpdateAttendanceLogEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceList = freezed,
  }) {
    return _then(_$UpdateAttendanceLogEvent(
      attendanceList: freezed == attendanceList
          ? _value._attendanceList
          : attendanceList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ));
  }
}

/// @nodoc

class _$UpdateAttendanceLogEvent implements UpdateAttendanceLogEvent {
  const _$UpdateAttendanceLogEvent(
      {final List<Map<String, dynamic>>? attendanceList})
      : _attendanceList = attendanceList;

  final List<Map<String, dynamic>>? _attendanceList;
  @override
  List<Map<String, dynamic>>? get attendanceList {
    final value = _attendanceList;
    if (value == null) return null;
    if (_attendanceList is EqualUnmodifiableListView) return _attendanceList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendanceLogCreateEvent.update(attendanceList: $attendanceList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateAttendanceLogEvent &&
            const DeepCollectionEquality()
                .equals(other._attendanceList, _attendanceList));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendanceList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateAttendanceLogEventCopyWith<_$UpdateAttendanceLogEvent>
      get copyWith =>
          __$$UpdateAttendanceLogEventCopyWithImpl<_$UpdateAttendanceLogEvent>(
              this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        create,
    required TResult Function(List<Map<String, dynamic>>? attendanceList)
        update,
  }) {
    return update(attendanceList);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult? Function(List<Map<String, dynamic>>? attendanceList)? update,
  }) {
    return update?.call(attendanceList);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(List<Map<String, dynamic>>? attendanceList)? create,
    TResult Function(List<Map<String, dynamic>>? attendanceList)? update,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(attendanceList);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendanceLogEvent value) create,
    required TResult Function(UpdateAttendanceLogEvent value) update,
  }) {
    return update(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendanceLogEvent value)? create,
    TResult? Function(UpdateAttendanceLogEvent value)? update,
  }) {
    return update?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendanceLogEvent value)? create,
    TResult Function(UpdateAttendanceLogEvent value)? update,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(this);
    }
    return orElse();
  }
}

abstract class UpdateAttendanceLogEvent implements AttendanceLogCreateEvent {
  const factory UpdateAttendanceLogEvent(
          {final List<Map<String, dynamic>>? attendanceList}) =
      _$UpdateAttendanceLogEvent;

  @override
  List<Map<String, dynamic>>? get attendanceList;
  @override
  @JsonKey(ignore: true)
  _$$UpdateAttendanceLogEventCopyWith<_$UpdateAttendanceLogEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AttendanceLogCreateState {
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
abstract class $AttendanceLogCreateStateCopyWith<$Res> {
  factory $AttendanceLogCreateStateCopyWith(AttendanceLogCreateState value,
          $Res Function(AttendanceLogCreateState) then) =
      _$AttendanceLogCreateStateCopyWithImpl<$Res, AttendanceLogCreateState>;
}

/// @nodoc
class _$AttendanceLogCreateStateCopyWithImpl<$Res,
        $Val extends AttendanceLogCreateState>
    implements $AttendanceLogCreateStateCopyWith<$Res> {
  _$AttendanceLogCreateStateCopyWithImpl(this._value, this._then);

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
    extends _$AttendanceLogCreateStateCopyWithImpl<$Res, _$_Initial>
    implements _$$_InitialCopyWith<$Res> {
  __$$_InitialCopyWithImpl(_$_Initial _value, $Res Function(_$_Initial) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Initial extends _Initial {
  const _$_Initial() : super._();

  @override
  String toString() {
    return 'AttendanceLogCreateState.initial()';
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

abstract class _Initial extends AttendanceLogCreateState {
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
    extends _$AttendanceLogCreateStateCopyWithImpl<$Res, _$_Loading>
    implements _$$_LoadingCopyWith<$Res> {
  __$$_LoadingCopyWithImpl(_$_Loading _value, $Res Function(_$_Loading) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loading extends _Loading {
  const _$_Loading() : super._();

  @override
  String toString() {
    return 'AttendanceLogCreateState.loading()';
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

abstract class _Loading extends AttendanceLogCreateState {
  const factory _Loading() = _$_Loading;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$_LoadedCopyWith<$Res> {
  factory _$$_LoadedCopyWith(_$_Loaded value, $Res Function(_$_Loaded) then) =
      __$$_LoadedCopyWithImpl<$Res>;
}

/// @nodoc
class __$$_LoadedCopyWithImpl<$Res>
    extends _$AttendanceLogCreateStateCopyWithImpl<$Res, _$_Loaded>
    implements _$$_LoadedCopyWith<$Res> {
  __$$_LoadedCopyWithImpl(_$_Loaded _value, $Res Function(_$_Loaded) _then)
      : super(_value, _then);
}

/// @nodoc

class _$_Loaded extends _Loaded {
  const _$_Loaded() : super._();

  @override
  String toString() {
    return 'AttendanceLogCreateState.loaded()';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$_Loaded);
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

abstract class _Loaded extends AttendanceLogCreateState {
  const factory _Loaded() = _$_Loaded;
  const _Loaded._() : super._();
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
    extends _$AttendanceLogCreateStateCopyWithImpl<$Res, _$_Error>
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

class _$_Error extends _Error {
  const _$_Error(this.error) : super._();

  @override
  final String? error;

  @override
  String toString() {
    return 'AttendanceLogCreateState.error(error: $error)';
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

abstract class _Error extends AttendanceLogCreateState {
  const factory _Error(final String? error) = _$_Error;
  const _Error._() : super._();

  String? get error;
  @JsonKey(ignore: true)
  _$$_ErrorCopyWith<_$_Error> get copyWith =>
      throw _privateConstructorUsedError;
}
