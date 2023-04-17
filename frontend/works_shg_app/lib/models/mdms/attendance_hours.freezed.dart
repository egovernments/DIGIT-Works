// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'attendance_hours.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

AttendanceHoursList _$AttendanceHoursListFromJson(Map<String, dynamic> json) {
  return _AttendanceHoursList.fromJson(json);
}

/// @nodoc
mixin _$AttendanceHoursList {
  @JsonKey(name: 'AttendanceHours')
  List<AttendanceHours>? get attendanceHours =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceHoursListCopyWith<AttendanceHoursList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceHoursListCopyWith<$Res> {
  factory $AttendanceHoursListCopyWith(
          AttendanceHoursList value, $Res Function(AttendanceHoursList) then) =
      _$AttendanceHoursListCopyWithImpl<$Res, AttendanceHoursList>;
  @useResult
  $Res call(
      {@JsonKey(name: 'AttendanceHours')
          List<AttendanceHours>? attendanceHours});
}

/// @nodoc
class _$AttendanceHoursListCopyWithImpl<$Res, $Val extends AttendanceHoursList>
    implements $AttendanceHoursListCopyWith<$Res> {
  _$AttendanceHoursListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceHours = freezed,
  }) {
    return _then(_value.copyWith(
      attendanceHours: freezed == attendanceHours
          ? _value.attendanceHours
          : attendanceHours // ignore: cast_nullable_to_non_nullable
              as List<AttendanceHours>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendanceHoursListCopyWith<$Res>
    implements $AttendanceHoursListCopyWith<$Res> {
  factory _$$_AttendanceHoursListCopyWith(_$_AttendanceHoursList value,
          $Res Function(_$_AttendanceHoursList) then) =
      __$$_AttendanceHoursListCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'AttendanceHours')
          List<AttendanceHours>? attendanceHours});
}

/// @nodoc
class __$$_AttendanceHoursListCopyWithImpl<$Res>
    extends _$AttendanceHoursListCopyWithImpl<$Res, _$_AttendanceHoursList>
    implements _$$_AttendanceHoursListCopyWith<$Res> {
  __$$_AttendanceHoursListCopyWithImpl(_$_AttendanceHoursList _value,
      $Res Function(_$_AttendanceHoursList) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceHours = freezed,
  }) {
    return _then(_$_AttendanceHoursList(
      attendanceHours: freezed == attendanceHours
          ? _value._attendanceHours
          : attendanceHours // ignore: cast_nullable_to_non_nullable
              as List<AttendanceHours>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceHoursList implements _AttendanceHoursList {
  const _$_AttendanceHoursList(
      {@JsonKey(name: 'AttendanceHours')
          final List<AttendanceHours>? attendanceHours})
      : _attendanceHours = attendanceHours;

  factory _$_AttendanceHoursList.fromJson(Map<String, dynamic> json) =>
      _$$_AttendanceHoursListFromJson(json);

  final List<AttendanceHours>? _attendanceHours;
  @override
  @JsonKey(name: 'AttendanceHours')
  List<AttendanceHours>? get attendanceHours {
    final value = _attendanceHours;
    if (value == null) return null;
    if (_attendanceHours is EqualUnmodifiableListView) return _attendanceHours;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendanceHoursList(attendanceHours: $attendanceHours)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceHoursList &&
            const DeepCollectionEquality()
                .equals(other._attendanceHours, _attendanceHours));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendanceHours));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceHoursListCopyWith<_$_AttendanceHoursList> get copyWith =>
      __$$_AttendanceHoursListCopyWithImpl<_$_AttendanceHoursList>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceHoursListToJson(
      this,
    );
  }
}

abstract class _AttendanceHoursList implements AttendanceHoursList {
  const factory _AttendanceHoursList(
          {@JsonKey(name: 'AttendanceHours')
              final List<AttendanceHours>? attendanceHours}) =
      _$_AttendanceHoursList;

  factory _AttendanceHoursList.fromJson(Map<String, dynamic> json) =
      _$_AttendanceHoursList.fromJson;

  @override
  @JsonKey(name: 'AttendanceHours')
  List<AttendanceHours>? get attendanceHours;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceHoursListCopyWith<_$_AttendanceHoursList> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendanceHours _$AttendanceHoursFromJson(Map<String, dynamic> json) {
  return _AttendanceHours.fromJson(json);
}

/// @nodoc
mixin _$AttendanceHours {
  String get code => throw _privateConstructorUsedError;
  String get value => throw _privateConstructorUsedError;
  bool get active => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceHoursCopyWith<AttendanceHours> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceHoursCopyWith<$Res> {
  factory $AttendanceHoursCopyWith(
          AttendanceHours value, $Res Function(AttendanceHours) then) =
      _$AttendanceHoursCopyWithImpl<$Res, AttendanceHours>;
  @useResult
  $Res call({String code, String value, bool active});
}

/// @nodoc
class _$AttendanceHoursCopyWithImpl<$Res, $Val extends AttendanceHours>
    implements $AttendanceHoursCopyWith<$Res> {
  _$AttendanceHoursCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? value = null,
    Object? active = null,
  }) {
    return _then(_value.copyWith(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendanceHoursCopyWith<$Res>
    implements $AttendanceHoursCopyWith<$Res> {
  factory _$$_AttendanceHoursCopyWith(
          _$_AttendanceHours value, $Res Function(_$_AttendanceHours) then) =
      __$$_AttendanceHoursCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String code, String value, bool active});
}

/// @nodoc
class __$$_AttendanceHoursCopyWithImpl<$Res>
    extends _$AttendanceHoursCopyWithImpl<$Res, _$_AttendanceHours>
    implements _$$_AttendanceHoursCopyWith<$Res> {
  __$$_AttendanceHoursCopyWithImpl(
      _$_AttendanceHours _value, $Res Function(_$_AttendanceHours) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? value = null,
    Object? active = null,
  }) {
    return _then(_$_AttendanceHours(
      code: null == code
          ? _value.code
          : code // ignore: cast_nullable_to_non_nullable
              as String,
      value: null == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String,
      active: null == active
          ? _value.active
          : active // ignore: cast_nullable_to_non_nullable
              as bool,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceHours implements _AttendanceHours {
  const _$_AttendanceHours(
      {required this.code, required this.value, required this.active});

  factory _$_AttendanceHours.fromJson(Map<String, dynamic> json) =>
      _$$_AttendanceHoursFromJson(json);

  @override
  final String code;
  @override
  final String value;
  @override
  final bool active;

  @override
  String toString() {
    return 'AttendanceHours(code: $code, value: $value, active: $active)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceHours &&
            (identical(other.code, code) || other.code == code) &&
            (identical(other.value, value) || other.value == value) &&
            (identical(other.active, active) || other.active == active));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, code, value, active);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceHoursCopyWith<_$_AttendanceHours> get copyWith =>
      __$$_AttendanceHoursCopyWithImpl<_$_AttendanceHours>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceHoursToJson(
      this,
    );
  }
}

abstract class _AttendanceHours implements AttendanceHours {
  const factory _AttendanceHours(
      {required final String code,
      required final String value,
      required final bool active}) = _$_AttendanceHours;

  factory _AttendanceHours.fromJson(Map<String, dynamic> json) =
      _$_AttendanceHours.fromJson;

  @override
  String get code;
  @override
  String get value;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceHoursCopyWith<_$_AttendanceHours> get copyWith =>
      throw _privateConstructorUsedError;
}
