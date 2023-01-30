// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

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
  bool get loading => throw _privateConstructorUsedError;
  AttendanceRegistersModel? get createAttendanceRegistersModel =>
      throw _privateConstructorUsedError;
  AttendanceRegistersModel? get updateAttendanceRegistersModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceLogCreateStateCopyWith<AttendanceLogCreateState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceLogCreateStateCopyWith<$Res> {
  factory $AttendanceLogCreateStateCopyWith(AttendanceLogCreateState value,
          $Res Function(AttendanceLogCreateState) then) =
      _$AttendanceLogCreateStateCopyWithImpl<$Res, AttendanceLogCreateState>;
  @useResult
  $Res call(
      {bool loading,
      AttendanceRegistersModel? createAttendanceRegistersModel,
      AttendanceRegistersModel? updateAttendanceRegistersModel});

  $AttendanceRegistersModelCopyWith<$Res>? get createAttendanceRegistersModel;
  $AttendanceRegistersModelCopyWith<$Res>? get updateAttendanceRegistersModel;
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

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? createAttendanceRegistersModel = freezed,
    Object? updateAttendanceRegistersModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      createAttendanceRegistersModel: freezed == createAttendanceRegistersModel
          ? _value.createAttendanceRegistersModel
          : createAttendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
      updateAttendanceRegistersModel: freezed == updateAttendanceRegistersModel
          ? _value.updateAttendanceRegistersModel
          : updateAttendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>? get createAttendanceRegistersModel {
    if (_value.createAttendanceRegistersModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.createAttendanceRegistersModel!, (value) {
      return _then(
          _value.copyWith(createAttendanceRegistersModel: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>? get updateAttendanceRegistersModel {
    if (_value.updateAttendanceRegistersModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.updateAttendanceRegistersModel!, (value) {
      return _then(
          _value.copyWith(updateAttendanceRegistersModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendanceLogCreateStateCopyWith<$Res>
    implements $AttendanceLogCreateStateCopyWith<$Res> {
  factory _$$_AttendanceLogCreateStateCopyWith(
          _$_AttendanceLogCreateState value,
          $Res Function(_$_AttendanceLogCreateState) then) =
      __$$_AttendanceLogCreateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {bool loading,
      AttendanceRegistersModel? createAttendanceRegistersModel,
      AttendanceRegistersModel? updateAttendanceRegistersModel});

  @override
  $AttendanceRegistersModelCopyWith<$Res>? get createAttendanceRegistersModel;
  @override
  $AttendanceRegistersModelCopyWith<$Res>? get updateAttendanceRegistersModel;
}

/// @nodoc
class __$$_AttendanceLogCreateStateCopyWithImpl<$Res>
    extends _$AttendanceLogCreateStateCopyWithImpl<$Res,
        _$_AttendanceLogCreateState>
    implements _$$_AttendanceLogCreateStateCopyWith<$Res> {
  __$$_AttendanceLogCreateStateCopyWithImpl(_$_AttendanceLogCreateState _value,
      $Res Function(_$_AttendanceLogCreateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? createAttendanceRegistersModel = freezed,
    Object? updateAttendanceRegistersModel = freezed,
  }) {
    return _then(_$_AttendanceLogCreateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      createAttendanceRegistersModel: freezed == createAttendanceRegistersModel
          ? _value.createAttendanceRegistersModel
          : createAttendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
      updateAttendanceRegistersModel: freezed == updateAttendanceRegistersModel
          ? _value.updateAttendanceRegistersModel
          : updateAttendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }
}

/// @nodoc

class _$_AttendanceLogCreateState extends _AttendanceLogCreateState {
  const _$_AttendanceLogCreateState(
      {this.loading = false,
      this.createAttendanceRegistersModel,
      this.updateAttendanceRegistersModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendanceRegistersModel? createAttendanceRegistersModel;
  @override
  final AttendanceRegistersModel? updateAttendanceRegistersModel;

  @override
  String toString() {
    return 'AttendanceLogCreateState(loading: $loading, createAttendanceRegistersModel: $createAttendanceRegistersModel, updateAttendanceRegistersModel: $updateAttendanceRegistersModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceLogCreateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(other.createAttendanceRegistersModel,
                    createAttendanceRegistersModel) ||
                other.createAttendanceRegistersModel ==
                    createAttendanceRegistersModel) &&
            (identical(other.updateAttendanceRegistersModel,
                    updateAttendanceRegistersModel) ||
                other.updateAttendanceRegistersModel ==
                    updateAttendanceRegistersModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, loading,
      createAttendanceRegistersModel, updateAttendanceRegistersModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceLogCreateStateCopyWith<_$_AttendanceLogCreateState>
      get copyWith => __$$_AttendanceLogCreateStateCopyWithImpl<
          _$_AttendanceLogCreateState>(this, _$identity);
}

abstract class _AttendanceLogCreateState extends AttendanceLogCreateState {
  const factory _AttendanceLogCreateState(
          {final bool loading,
          final AttendanceRegistersModel? createAttendanceRegistersModel,
          final AttendanceRegistersModel? updateAttendanceRegistersModel}) =
      _$_AttendanceLogCreateState;
  const _AttendanceLogCreateState._() : super._();

  @override
  bool get loading;
  @override
  AttendanceRegistersModel? get createAttendanceRegistersModel;
  @override
  AttendanceRegistersModel? get updateAttendanceRegistersModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceLogCreateStateCopyWith<_$_AttendanceLogCreateState>
      get copyWith => throw _privateConstructorUsedError;
}
