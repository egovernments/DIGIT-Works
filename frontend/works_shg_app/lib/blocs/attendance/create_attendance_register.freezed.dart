// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'create_attendance_register.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

/// @nodoc
mixin _$AttendanceRegisterCreateEvent {
  String get tenantId => throw _privateConstructorUsedError;
  String get registerNumber => throw _privateConstructorUsedError;
  String get name => throw _privateConstructorUsedError;
  int get startDate => throw _privateConstructorUsedError;
  int get endDate => throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerNumber,
            String name, int startDate, int endDate)
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendanceRegisterEvent value) create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendanceRegisterEvent value)? create,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendanceRegisterEvent value)? create,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceRegisterCreateEventCopyWith<AttendanceRegisterCreateEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceRegisterCreateEventCopyWith<$Res> {
  factory $AttendanceRegisterCreateEventCopyWith(
          AttendanceRegisterCreateEvent value,
          $Res Function(AttendanceRegisterCreateEvent) then) =
      _$AttendanceRegisterCreateEventCopyWithImpl<$Res,
          AttendanceRegisterCreateEvent>;
  @useResult
  $Res call(
      {String tenantId,
      String registerNumber,
      String name,
      int startDate,
      int endDate});
}

/// @nodoc
class _$AttendanceRegisterCreateEventCopyWithImpl<$Res,
        $Val extends AttendanceRegisterCreateEvent>
    implements $AttendanceRegisterCreateEventCopyWith<$Res> {
  _$AttendanceRegisterCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerNumber = null,
    Object? name = null,
    Object? startDate = null,
    Object? endDate = null,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: null == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateAttendanceRegisterEventCopyWith<$Res>
    implements $AttendanceRegisterCreateEventCopyWith<$Res> {
  factory _$$CreateAttendanceRegisterEventCopyWith(
          _$CreateAttendanceRegisterEvent value,
          $Res Function(_$CreateAttendanceRegisterEvent) then) =
      __$$CreateAttendanceRegisterEventCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String registerNumber,
      String name,
      int startDate,
      int endDate});
}

/// @nodoc
class __$$CreateAttendanceRegisterEventCopyWithImpl<$Res>
    extends _$AttendanceRegisterCreateEventCopyWithImpl<$Res,
        _$CreateAttendanceRegisterEvent>
    implements _$$CreateAttendanceRegisterEventCopyWith<$Res> {
  __$$CreateAttendanceRegisterEventCopyWithImpl(
      _$CreateAttendanceRegisterEvent _value,
      $Res Function(_$CreateAttendanceRegisterEvent) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerNumber = null,
    Object? name = null,
    Object? startDate = null,
    Object? endDate = null,
  }) {
    return _then(_$CreateAttendanceRegisterEvent(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: null == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String,
      name: null == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      endDate: null == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int,
    ));
  }
}

/// @nodoc

class _$CreateAttendanceRegisterEvent implements CreateAttendanceRegisterEvent {
  const _$CreateAttendanceRegisterEvent(
      {required this.tenantId,
      required this.registerNumber,
      required this.name,
      required this.startDate,
      required this.endDate});

  @override
  final String tenantId;
  @override
  final String registerNumber;
  @override
  final String name;
  @override
  final int startDate;
  @override
  final int endDate;

  @override
  String toString() {
    return 'AttendanceRegisterCreateEvent.create(tenantId: $tenantId, registerNumber: $registerNumber, name: $name, startDate: $startDate, endDate: $endDate)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateAttendanceRegisterEvent &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType, tenantId, registerNumber, name, startDate, endDate);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateAttendanceRegisterEventCopyWith<_$CreateAttendanceRegisterEvent>
      get copyWith => __$$CreateAttendanceRegisterEventCopyWithImpl<
          _$CreateAttendanceRegisterEvent>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(String tenantId, String registerNumber,
            String name, int startDate, int endDate)
        create,
  }) {
    return create(tenantId, registerNumber, name, startDate, endDate);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
  }) {
    return create?.call(tenantId, registerNumber, name, startDate, endDate);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(String tenantId, String registerNumber, String name,
            int startDate, int endDate)?
        create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(tenantId, registerNumber, name, startDate, endDate);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateAttendanceRegisterEvent value) create,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateAttendanceRegisterEvent value)? create,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateAttendanceRegisterEvent value)? create,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateAttendanceRegisterEvent
    implements AttendanceRegisterCreateEvent {
  const factory CreateAttendanceRegisterEvent(
      {required final String tenantId,
      required final String registerNumber,
      required final String name,
      required final int startDate,
      required final int endDate}) = _$CreateAttendanceRegisterEvent;

  @override
  String get tenantId;
  @override
  String get registerNumber;
  @override
  String get name;
  @override
  int get startDate;
  @override
  int get endDate;
  @override
  @JsonKey(ignore: true)
  _$$CreateAttendanceRegisterEventCopyWith<_$CreateAttendanceRegisterEvent>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$AttendanceRegisterCreateState {
  bool get loading => throw _privateConstructorUsedError;
  AttendanceRegistersModel? get attendanceRegistersModel =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $AttendanceRegisterCreateStateCopyWith<AttendanceRegisterCreateState>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceRegisterCreateStateCopyWith<$Res> {
  factory $AttendanceRegisterCreateStateCopyWith(
          AttendanceRegisterCreateState value,
          $Res Function(AttendanceRegisterCreateState) then) =
      _$AttendanceRegisterCreateStateCopyWithImpl<$Res,
          AttendanceRegisterCreateState>;
  @useResult
  $Res call({bool loading, AttendanceRegistersModel? attendanceRegistersModel});

  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
}

/// @nodoc
class _$AttendanceRegisterCreateStateCopyWithImpl<$Res,
        $Val extends AttendanceRegisterCreateState>
    implements $AttendanceRegisterCreateStateCopyWith<$Res> {
  _$AttendanceRegisterCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendanceRegistersModel = freezed,
  }) {
    return _then(_value.copyWith(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendanceRegistersModel: freezed == attendanceRegistersModel
          ? _value.attendanceRegistersModel
          : attendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel {
    if (_value.attendanceRegistersModel == null) {
      return null;
    }

    return $AttendanceRegistersModelCopyWith<$Res>(
        _value.attendanceRegistersModel!, (value) {
      return _then(_value.copyWith(attendanceRegistersModel: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendanceRegisterCreateStateCopyWith<$Res>
    implements $AttendanceRegisterCreateStateCopyWith<$Res> {
  factory _$$_AttendanceRegisterCreateStateCopyWith(
          _$_AttendanceRegisterCreateState value,
          $Res Function(_$_AttendanceRegisterCreateState) then) =
      __$$_AttendanceRegisterCreateStateCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({bool loading, AttendanceRegistersModel? attendanceRegistersModel});

  @override
  $AttendanceRegistersModelCopyWith<$Res>? get attendanceRegistersModel;
}

/// @nodoc
class __$$_AttendanceRegisterCreateStateCopyWithImpl<$Res>
    extends _$AttendanceRegisterCreateStateCopyWithImpl<$Res,
        _$_AttendanceRegisterCreateState>
    implements _$$_AttendanceRegisterCreateStateCopyWith<$Res> {
  __$$_AttendanceRegisterCreateStateCopyWithImpl(
      _$_AttendanceRegisterCreateState _value,
      $Res Function(_$_AttendanceRegisterCreateState) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? loading = null,
    Object? attendanceRegistersModel = freezed,
  }) {
    return _then(_$_AttendanceRegisterCreateState(
      loading: null == loading
          ? _value.loading
          : loading // ignore: cast_nullable_to_non_nullable
              as bool,
      attendanceRegistersModel: freezed == attendanceRegistersModel
          ? _value.attendanceRegistersModel
          : attendanceRegistersModel // ignore: cast_nullable_to_non_nullable
              as AttendanceRegistersModel?,
    ));
  }
}

/// @nodoc

class _$_AttendanceRegisterCreateState extends _AttendanceRegisterCreateState {
  const _$_AttendanceRegisterCreateState(
      {this.loading = false, this.attendanceRegistersModel})
      : super._();

  @override
  @JsonKey()
  final bool loading;
  @override
  final AttendanceRegistersModel? attendanceRegistersModel;

  @override
  String toString() {
    return 'AttendanceRegisterCreateState(loading: $loading, attendanceRegistersModel: $attendanceRegistersModel)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceRegisterCreateState &&
            (identical(other.loading, loading) || other.loading == loading) &&
            (identical(
                    other.attendanceRegistersModel, attendanceRegistersModel) ||
                other.attendanceRegistersModel == attendanceRegistersModel));
  }

  @override
  int get hashCode =>
      Object.hash(runtimeType, loading, attendanceRegistersModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceRegisterCreateStateCopyWith<_$_AttendanceRegisterCreateState>
      get copyWith => __$$_AttendanceRegisterCreateStateCopyWithImpl<
          _$_AttendanceRegisterCreateState>(this, _$identity);
}

abstract class _AttendanceRegisterCreateState
    extends AttendanceRegisterCreateState {
  const factory _AttendanceRegisterCreateState(
          {final bool loading,
          final AttendanceRegistersModel? attendanceRegistersModel}) =
      _$_AttendanceRegisterCreateState;
  const _AttendanceRegisterCreateState._() : super._();

  @override
  bool get loading;
  @override
  AttendanceRegistersModel? get attendanceRegistersModel;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceRegisterCreateStateCopyWith<_$_AttendanceRegisterCreateState>
      get copyWith => throw _privateConstructorUsedError;
}
