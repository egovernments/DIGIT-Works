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
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$AttendanceHoursListImplCopyWith<$Res>
    implements $AttendanceHoursListCopyWith<$Res> {
  factory _$$AttendanceHoursListImplCopyWith(_$AttendanceHoursListImpl value,
          $Res Function(_$AttendanceHoursListImpl) then) =
      __$$AttendanceHoursListImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'AttendanceHours')
          List<AttendanceHours>? attendanceHours});
}

/// @nodoc
class __$$AttendanceHoursListImplCopyWithImpl<$Res>
    extends _$AttendanceHoursListCopyWithImpl<$Res, _$AttendanceHoursListImpl>
    implements _$$AttendanceHoursListImplCopyWith<$Res> {
  __$$AttendanceHoursListImplCopyWithImpl(_$AttendanceHoursListImpl _value,
      $Res Function(_$AttendanceHoursListImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceHours = freezed,
  }) {
    return _then(_$AttendanceHoursListImpl(
      attendanceHours: freezed == attendanceHours
          ? _value._attendanceHours
          : attendanceHours // ignore: cast_nullable_to_non_nullable
              as List<AttendanceHours>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendanceHoursListImpl implements _AttendanceHoursList {
  const _$AttendanceHoursListImpl(
      {@JsonKey(name: 'AttendanceHours')
          final List<AttendanceHours>? attendanceHours})
      : _attendanceHours = attendanceHours;

  factory _$AttendanceHoursListImpl.fromJson(Map<String, dynamic> json) =>
      _$$AttendanceHoursListImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendanceHoursListImpl &&
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
  _$$AttendanceHoursListImplCopyWith<_$AttendanceHoursListImpl> get copyWith =>
      __$$AttendanceHoursListImplCopyWithImpl<_$AttendanceHoursListImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendanceHoursListImplToJson(
      this,
    );
  }
}

abstract class _AttendanceHoursList implements AttendanceHoursList {
  const factory _AttendanceHoursList(
          {@JsonKey(name: 'AttendanceHours')
              final List<AttendanceHours>? attendanceHours}) =
      _$AttendanceHoursListImpl;

  factory _AttendanceHoursList.fromJson(Map<String, dynamic> json) =
      _$AttendanceHoursListImpl.fromJson;

  @override
  @JsonKey(name: 'AttendanceHours')
  List<AttendanceHours>? get attendanceHours;
  @override
  @JsonKey(ignore: true)
  _$$AttendanceHoursListImplCopyWith<_$AttendanceHoursListImpl> get copyWith =>
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
abstract class _$$AttendanceHoursImplCopyWith<$Res>
    implements $AttendanceHoursCopyWith<$Res> {
  factory _$$AttendanceHoursImplCopyWith(_$AttendanceHoursImpl value,
          $Res Function(_$AttendanceHoursImpl) then) =
      __$$AttendanceHoursImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String code, String value, bool active});
}

/// @nodoc
class __$$AttendanceHoursImplCopyWithImpl<$Res>
    extends _$AttendanceHoursCopyWithImpl<$Res, _$AttendanceHoursImpl>
    implements _$$AttendanceHoursImplCopyWith<$Res> {
  __$$AttendanceHoursImplCopyWithImpl(
      _$AttendanceHoursImpl _value, $Res Function(_$AttendanceHoursImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? code = null,
    Object? value = null,
    Object? active = null,
  }) {
    return _then(_$AttendanceHoursImpl(
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
class _$AttendanceHoursImpl implements _AttendanceHours {
  const _$AttendanceHoursImpl(
      {required this.code, required this.value, required this.active});

  factory _$AttendanceHoursImpl.fromJson(Map<String, dynamic> json) =>
      _$$AttendanceHoursImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendanceHoursImpl &&
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
  _$$AttendanceHoursImplCopyWith<_$AttendanceHoursImpl> get copyWith =>
      __$$AttendanceHoursImplCopyWithImpl<_$AttendanceHoursImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendanceHoursImplToJson(
      this,
    );
  }
}

abstract class _AttendanceHours implements AttendanceHours {
  const factory _AttendanceHours(
      {required final String code,
      required final String value,
      required final bool active}) = _$AttendanceHoursImpl;

  factory _AttendanceHours.fromJson(Map<String, dynamic> json) =
      _$AttendanceHoursImpl.fromJson;

  @override
  String get code;
  @override
  String get value;
  @override
  bool get active;
  @override
  @JsonKey(ignore: true)
  _$$AttendanceHoursImplCopyWith<_$AttendanceHoursImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
