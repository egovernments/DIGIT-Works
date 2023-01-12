// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'attendee_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

AttendeeModel _$AttendeeModelFromJson(Map<String, dynamic> json) {
  return _AttendeeModel.fromJson(json);
}

/// @nodoc
mixin _$AttendeeModel {
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeeModelCopyWith<AttendeeModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeModelCopyWith<$Res> {
  factory $AttendeeModelCopyWith(
          AttendeeModel value, $Res Function(AttendeeModel) then) =
      _$AttendeeModelCopyWithImpl<$Res, AttendeeModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendanceRegister>? attendanceRegister});
}

/// @nodoc
class _$AttendeeModelCopyWithImpl<$Res, $Val extends AttendeeModel>
    implements $AttendeeModelCopyWith<$Res> {
  _$AttendeeModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegister = freezed,
  }) {
    return _then(_value.copyWith(
      attendanceRegister: freezed == attendanceRegister
          ? _value.attendanceRegister
          : attendanceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendanceRegister>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendeeModelCopyWith<$Res>
    implements $AttendeeModelCopyWith<$Res> {
  factory _$$_AttendeeModelCopyWith(
          _$_AttendeeModel value, $Res Function(_$_AttendeeModel) then) =
      __$$_AttendeeModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendanceRegister>? attendanceRegister});
}

/// @nodoc
class __$$_AttendeeModelCopyWithImpl<$Res>
    extends _$AttendeeModelCopyWithImpl<$Res, _$_AttendeeModel>
    implements _$$_AttendeeModelCopyWith<$Res> {
  __$$_AttendeeModelCopyWithImpl(
      _$_AttendeeModel _value, $Res Function(_$_AttendeeModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegister = freezed,
  }) {
    return _then(_$_AttendeeModel(
      attendanceRegister: freezed == attendanceRegister
          ? _value._attendanceRegister
          : attendanceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendanceRegister>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendeeModel implements _AttendeeModel {
  const _$_AttendeeModel(
      {@JsonKey(name: 'attendanceRegister')
          final List<AttendanceRegister>? attendanceRegister})
      : _attendanceRegister = attendanceRegister;

  factory _$_AttendeeModel.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeeModelFromJson(json);

  final List<AttendanceRegister>? _attendanceRegister;
  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister {
    final value = _attendanceRegister;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendeeModel(attendanceRegister: $attendanceRegister)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeeModel &&
            const DeepCollectionEquality()
                .equals(other._attendanceRegister, _attendanceRegister));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendanceRegister));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendeeModelCopyWith<_$_AttendeeModel> get copyWith =>
      __$$_AttendeeModelCopyWithImpl<_$_AttendeeModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendeeModelToJson(
      this,
    );
  }
}

abstract class _AttendeeModel implements AttendeeModel {
  const factory _AttendeeModel(
          {@JsonKey(name: 'attendanceRegister')
              final List<AttendanceRegister>? attendanceRegister}) =
      _$_AttendeeModel;

  factory _AttendeeModel.fromJson(Map<String, dynamic> json) =
      _$_AttendeeModel.fromJson;

  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeeModelCopyWith<_$_AttendeeModel> get copyWith =>
      throw _privateConstructorUsedError;
}
