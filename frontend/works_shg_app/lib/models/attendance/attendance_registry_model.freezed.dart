// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

part of 'attendance_registry_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

AttendenceRegistersModel _$AttendenceRegistersModelFromJson(
    Map<String, dynamic> json) {
  return _AttendenceRegistersModel.fromJson(json);
}

/// @nodoc
mixin _$AttendenceRegistersModel {
  @JsonKey(name: 'attendanceRegister')
  List<AttendenceRegister>? get attendenceRegister =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendenceRegistersModelCopyWith<AttendenceRegistersModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendenceRegistersModelCopyWith<$Res> {
  factory $AttendenceRegistersModelCopyWith(AttendenceRegistersModel value,
          $Res Function(AttendenceRegistersModel) then) =
      _$AttendenceRegistersModelCopyWithImpl<$Res, AttendenceRegistersModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendenceRegister>? attendenceRegister});
}

/// @nodoc
class _$AttendenceRegistersModelCopyWithImpl<$Res,
        $Val extends AttendenceRegistersModel>
    implements $AttendenceRegistersModelCopyWith<$Res> {
  _$AttendenceRegistersModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendenceRegister = freezed,
  }) {
    return _then(_value.copyWith(
      attendenceRegister: freezed == attendenceRegister
          ? _value.attendenceRegister
          : attendenceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendenceRegister>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendenceRegistersModelCopyWith<$Res>
    implements $AttendenceRegistersModelCopyWith<$Res> {
  factory _$$_AttendenceRegistersModelCopyWith(
          _$_AttendenceRegistersModel value,
          $Res Function(_$_AttendenceRegistersModel) then) =
      __$$_AttendenceRegistersModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendenceRegister>? attendenceRegister});
}

/// @nodoc
class __$$_AttendenceRegistersModelCopyWithImpl<$Res>
    extends _$AttendenceRegistersModelCopyWithImpl<$Res,
        _$_AttendenceRegistersModel>
    implements _$$_AttendenceRegistersModelCopyWith<$Res> {
  __$$_AttendenceRegistersModelCopyWithImpl(_$_AttendenceRegistersModel _value,
      $Res Function(_$_AttendenceRegistersModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendenceRegister = freezed,
  }) {
    return _then(_$_AttendenceRegistersModel(
      attendenceRegister: freezed == attendenceRegister
          ? _value._attendenceRegister
          : attendenceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendenceRegister>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendenceRegistersModel implements _AttendenceRegistersModel {
  const _$_AttendenceRegistersModel(
      {@JsonKey(name: 'attendanceRegister')
          final List<AttendenceRegister>? attendenceRegister})
      : _attendenceRegister = attendenceRegister;

  factory _$_AttendenceRegistersModel.fromJson(Map<String, dynamic> json) =>
      _$$_AttendenceRegistersModelFromJson(json);

  final List<AttendenceRegister>? _attendenceRegister;
  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendenceRegister>? get attendenceRegister {
    final value = _attendenceRegister;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendenceRegistersModel(attendenceRegister: $attendenceRegister)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendenceRegistersModel &&
            const DeepCollectionEquality()
                .equals(other._attendenceRegister, _attendenceRegister));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendenceRegister));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendenceRegistersModelCopyWith<_$_AttendenceRegistersModel>
      get copyWith => __$$_AttendenceRegistersModelCopyWithImpl<
          _$_AttendenceRegistersModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendenceRegistersModelToJson(
      this,
    );
  }
}

abstract class _AttendenceRegistersModel implements AttendenceRegistersModel {
  const factory _AttendenceRegistersModel(
          {@JsonKey(name: 'attendanceRegister')
              final List<AttendenceRegister>? attendenceRegister}) =
      _$_AttendenceRegistersModel;

  factory _AttendenceRegistersModel.fromJson(Map<String, dynamic> json) =
      _$_AttendenceRegistersModel.fromJson;

  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendenceRegister>? get attendenceRegister;
  @override
  @JsonKey(ignore: true)
  _$$_AttendenceRegistersModelCopyWith<_$_AttendenceRegistersModel>
      get copyWith => throw _privateConstructorUsedError;
}

AttendenceRegister _$AttendenceRegisterFromJson(Map<String, dynamic> json) {
  return _AttendenceRegister.fromJson(json);
}

/// @nodoc
mixin _$AttendenceRegister {
  String? get id => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get registerNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  int? get startDate => throw _privateConstructorUsedError;
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'staff')
  List<StaffEntries>? get staffEntries => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendenceRegisterCopyWith<AttendenceRegister> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendenceRegisterCopyWith<$Res> {
  factory $AttendenceRegisterCopyWith(
          AttendenceRegister value, $Res Function(AttendenceRegister) then) =
      _$AttendenceRegisterCopyWithImpl<$Res, AttendenceRegister>;
  @useResult
  $Res call(
      {String? id,
      String? tenantId,
      String? registerNumber,
      String? name,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'staff') List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees') List<AttendeesEntries>? attendeesEntries});
}

/// @nodoc
class _$AttendenceRegisterCopyWithImpl<$Res, $Val extends AttendenceRegister>
    implements $AttendenceRegisterCopyWith<$Res> {
  _$AttendenceRegisterCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? registerNumber = freezed,
    Object? name = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? staffEntries = freezed,
    Object? attendeesEntries = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      registerNumber: freezed == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      staffEntries: freezed == staffEntries
          ? _value.staffEntries
          : staffEntries // ignore: cast_nullable_to_non_nullable
              as List<StaffEntries>?,
      attendeesEntries: freezed == attendeesEntries
          ? _value.attendeesEntries
          : attendeesEntries // ignore: cast_nullable_to_non_nullable
              as List<AttendeesEntries>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendenceRegisterCopyWith<$Res>
    implements $AttendenceRegisterCopyWith<$Res> {
  factory _$$_AttendenceRegisterCopyWith(_$_AttendenceRegister value,
          $Res Function(_$_AttendenceRegister) then) =
      __$$_AttendenceRegisterCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? tenantId,
      String? registerNumber,
      String? name,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'staff') List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees') List<AttendeesEntries>? attendeesEntries});
}

/// @nodoc
class __$$_AttendenceRegisterCopyWithImpl<$Res>
    extends _$AttendenceRegisterCopyWithImpl<$Res, _$_AttendenceRegister>
    implements _$$_AttendenceRegisterCopyWith<$Res> {
  __$$_AttendenceRegisterCopyWithImpl(
      _$_AttendenceRegister _value, $Res Function(_$_AttendenceRegister) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = freezed,
    Object? registerNumber = freezed,
    Object? name = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? staffEntries = freezed,
    Object? attendeesEntries = freezed,
  }) {
    return _then(_$_AttendenceRegister(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      registerNumber: freezed == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      staffEntries: freezed == staffEntries
          ? _value._staffEntries
          : staffEntries // ignore: cast_nullable_to_non_nullable
              as List<StaffEntries>?,
      attendeesEntries: freezed == attendeesEntries
          ? _value._attendeesEntries
          : attendeesEntries // ignore: cast_nullable_to_non_nullable
              as List<AttendeesEntries>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendenceRegister implements _AttendenceRegister {
  const _$_AttendenceRegister(
      {this.id,
      this.tenantId,
      this.registerNumber,
      this.name,
      this.startDate,
      this.endDate,
      @JsonKey(name: 'staff')
          final List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees')
          final List<AttendeesEntries>? attendeesEntries})
      : _staffEntries = staffEntries,
        _attendeesEntries = attendeesEntries;

  factory _$_AttendenceRegister.fromJson(Map<String, dynamic> json) =>
      _$$_AttendenceRegisterFromJson(json);

  @override
  final String? id;
  @override
  final String? tenantId;
  @override
  final String? registerNumber;
  @override
  final String? name;
  @override
  final int? startDate;
  @override
  final int? endDate;
  final List<StaffEntries>? _staffEntries;
  @override
  @JsonKey(name: 'staff')
  List<StaffEntries>? get staffEntries {
    final value = _staffEntries;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<AttendeesEntries>? _attendeesEntries;
  @override
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries {
    final value = _attendeesEntries;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendenceRegister(id: $id, tenantId: $tenantId, registerNumber: $registerNumber, name: $name, startDate: $startDate, endDate: $endDate, staffEntries: $staffEntries, attendeesEntries: $attendeesEntries)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendenceRegister &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            const DeepCollectionEquality()
                .equals(other._staffEntries, _staffEntries) &&
            const DeepCollectionEquality()
                .equals(other._attendeesEntries, _attendeesEntries));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      registerNumber,
      name,
      startDate,
      endDate,
      const DeepCollectionEquality().hash(_staffEntries),
      const DeepCollectionEquality().hash(_attendeesEntries));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendenceRegisterCopyWith<_$_AttendenceRegister> get copyWith =>
      __$$_AttendenceRegisterCopyWithImpl<_$_AttendenceRegister>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendenceRegisterToJson(
      this,
    );
  }
}

abstract class _AttendenceRegister implements AttendenceRegister {
  const factory _AttendenceRegister(
          {final String? id,
          final String? tenantId,
          final String? registerNumber,
          final String? name,
          final int? startDate,
          final int? endDate,
          @JsonKey(name: 'staff')
              final List<StaffEntries>? staffEntries,
          @JsonKey(name: 'attendees')
              final List<AttendeesEntries>? attendeesEntries}) =
      _$_AttendenceRegister;

  factory _AttendenceRegister.fromJson(Map<String, dynamic> json) =
      _$_AttendenceRegister.fromJson;

  @override
  String? get id;
  @override
  String? get tenantId;
  @override
  String? get registerNumber;
  @override
  String? get name;
  @override
  int? get startDate;
  @override
  int? get endDate;
  @override
  @JsonKey(name: 'staff')
  List<StaffEntries>? get staffEntries;
  @override
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries;
  @override
  @JsonKey(ignore: true)
  _$$_AttendenceRegisterCopyWith<_$_AttendenceRegister> get copyWith =>
      throw _privateConstructorUsedError;
}

StaffEntries _$StaffEntriesFromJson(Map<String, dynamic> json) {
  return _StaffEntries.fromJson(json);
}

/// @nodoc
mixin _$StaffEntries {
  String? get id => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $StaffEntriesCopyWith<StaffEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $StaffEntriesCopyWith<$Res> {
  factory $StaffEntriesCopyWith(
          StaffEntries value, $Res Function(StaffEntries) then) =
      _$StaffEntriesCopyWithImpl<$Res, StaffEntries>;
  @useResult
  $Res call({String? id});
}

/// @nodoc
class _$StaffEntriesCopyWithImpl<$Res, $Val extends StaffEntries>
    implements $StaffEntriesCopyWith<$Res> {
  _$StaffEntriesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_StaffEntriesCopyWith<$Res>
    implements $StaffEntriesCopyWith<$Res> {
  factory _$$_StaffEntriesCopyWith(
          _$_StaffEntries value, $Res Function(_$_StaffEntries) then) =
      __$$_StaffEntriesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? id});
}

/// @nodoc
class __$$_StaffEntriesCopyWithImpl<$Res>
    extends _$StaffEntriesCopyWithImpl<$Res, _$_StaffEntries>
    implements _$$_StaffEntriesCopyWith<$Res> {
  __$$_StaffEntriesCopyWithImpl(
      _$_StaffEntries _value, $Res Function(_$_StaffEntries) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
  }) {
    return _then(_$_StaffEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_StaffEntries implements _StaffEntries {
  const _$_StaffEntries({this.id});

  factory _$_StaffEntries.fromJson(Map<String, dynamic> json) =>
      _$$_StaffEntriesFromJson(json);

  @override
  final String? id;

  @override
  String toString() {
    return 'StaffEntries(id: $id)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_StaffEntries &&
            (identical(other.id, id) || other.id == id));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_StaffEntriesCopyWith<_$_StaffEntries> get copyWith =>
      __$$_StaffEntriesCopyWithImpl<_$_StaffEntries>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_StaffEntriesToJson(
      this,
    );
  }
}

abstract class _StaffEntries implements StaffEntries {
  const factory _StaffEntries({final String? id}) = _$_StaffEntries;

  factory _StaffEntries.fromJson(Map<String, dynamic> json) =
      _$_StaffEntries.fromJson;

  @override
  String? get id;
  @override
  @JsonKey(ignore: true)
  _$$_StaffEntriesCopyWith<_$_StaffEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendeesEntries _$AttendeesEntriesFromJson(Map<String, dynamic> json) {
  return _AttendeesEntries.fromJson(json);
}

/// @nodoc
mixin _$AttendeesEntries {
  String? get id => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeesEntriesCopyWith<AttendeesEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeesEntriesCopyWith<$Res> {
  factory $AttendeesEntriesCopyWith(
          AttendeesEntries value, $Res Function(AttendeesEntries) then) =
      _$AttendeesEntriesCopyWithImpl<$Res, AttendeesEntries>;
  @useResult
  $Res call({String? id});
}

/// @nodoc
class _$AttendeesEntriesCopyWithImpl<$Res, $Val extends AttendeesEntries>
    implements $AttendeesEntriesCopyWith<$Res> {
  _$AttendeesEntriesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendeesEntriesCopyWith<$Res>
    implements $AttendeesEntriesCopyWith<$Res> {
  factory _$$_AttendeesEntriesCopyWith(
          _$_AttendeesEntries value, $Res Function(_$_AttendeesEntries) then) =
      __$$_AttendeesEntriesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? id});
}

/// @nodoc
class __$$_AttendeesEntriesCopyWithImpl<$Res>
    extends _$AttendeesEntriesCopyWithImpl<$Res, _$_AttendeesEntries>
    implements _$$_AttendeesEntriesCopyWith<$Res> {
  __$$_AttendeesEntriesCopyWithImpl(
      _$_AttendeesEntries _value, $Res Function(_$_AttendeesEntries) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
  }) {
    return _then(_$_AttendeesEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendeesEntries implements _AttendeesEntries {
  const _$_AttendeesEntries({this.id});

  factory _$_AttendeesEntries.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeesEntriesFromJson(json);

  @override
  final String? id;

  @override
  String toString() {
    return 'AttendeesEntries(id: $id)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeesEntries &&
            (identical(other.id, id) || other.id == id));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendeesEntriesCopyWith<_$_AttendeesEntries> get copyWith =>
      __$$_AttendeesEntriesCopyWithImpl<_$_AttendeesEntries>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendeesEntriesToJson(
      this,
    );
  }
}

abstract class _AttendeesEntries implements AttendeesEntries {
  const factory _AttendeesEntries({final String? id}) = _$_AttendeesEntries;

  factory _AttendeesEntries.fromJson(Map<String, dynamic> json) =
      _$_AttendeesEntries.fromJson;

  @override
  String? get id;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeesEntriesCopyWith<_$_AttendeesEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

AuditDetails _$AuditDetailsFromJson(Map<String, dynamic> json) {
  return _AuditDetails.fromJson(json);
}

/// @nodoc
mixin _$AuditDetails {
  String? get createdBy => throw _privateConstructorUsedError;
  String? get lastModifiedBy => throw _privateConstructorUsedError;
  int? get createdTime => throw _privateConstructorUsedError;
  int? get lastModifiedTime => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AuditDetailsCopyWith<AuditDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AuditDetailsCopyWith<$Res> {
  factory $AuditDetailsCopyWith(
          AuditDetails value, $Res Function(AuditDetails) then) =
      _$AuditDetailsCopyWithImpl<$Res, AuditDetails>;
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class _$AuditDetailsCopyWithImpl<$Res, $Val extends AuditDetails>
    implements $AuditDetailsCopyWith<$Res> {
  _$AuditDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? createdBy = freezed,
    Object? lastModifiedBy = freezed,
    Object? createdTime = freezed,
    Object? lastModifiedTime = freezed,
  }) {
    return _then(_value.copyWith(
      createdBy: freezed == createdBy
          ? _value.createdBy
          : createdBy // ignore: cast_nullable_to_non_nullable
              as String?,
      lastModifiedBy: freezed == lastModifiedBy
          ? _value.lastModifiedBy
          : lastModifiedBy // ignore: cast_nullable_to_non_nullable
              as String?,
      createdTime: freezed == createdTime
          ? _value.createdTime
          : createdTime // ignore: cast_nullable_to_non_nullable
              as int?,
      lastModifiedTime: freezed == lastModifiedTime
          ? _value.lastModifiedTime
          : lastModifiedTime // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AuditDetailsCopyWith<$Res>
    implements $AuditDetailsCopyWith<$Res> {
  factory _$$_AuditDetailsCopyWith(
          _$_AuditDetails value, $Res Function(_$_AuditDetails) then) =
      __$$_AuditDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class __$$_AuditDetailsCopyWithImpl<$Res>
    extends _$AuditDetailsCopyWithImpl<$Res, _$_AuditDetails>
    implements _$$_AuditDetailsCopyWith<$Res> {
  __$$_AuditDetailsCopyWithImpl(
      _$_AuditDetails _value, $Res Function(_$_AuditDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? createdBy = freezed,
    Object? lastModifiedBy = freezed,
    Object? createdTime = freezed,
    Object? lastModifiedTime = freezed,
  }) {
    return _then(_$_AuditDetails(
      createdBy: freezed == createdBy
          ? _value.createdBy
          : createdBy // ignore: cast_nullable_to_non_nullable
              as String?,
      lastModifiedBy: freezed == lastModifiedBy
          ? _value.lastModifiedBy
          : lastModifiedBy // ignore: cast_nullable_to_non_nullable
              as String?,
      createdTime: freezed == createdTime
          ? _value.createdTime
          : createdTime // ignore: cast_nullable_to_non_nullable
              as int?,
      lastModifiedTime: freezed == lastModifiedTime
          ? _value.lastModifiedTime
          : lastModifiedTime // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AuditDetails implements _AuditDetails {
  const _$_AuditDetails(
      {this.createdBy,
      this.lastModifiedBy,
      this.createdTime,
      this.lastModifiedTime});

  factory _$_AuditDetails.fromJson(Map<String, dynamic> json) =>
      _$$_AuditDetailsFromJson(json);

  @override
  final String? createdBy;
  @override
  final String? lastModifiedBy;
  @override
  final int? createdTime;
  @override
  final int? lastModifiedTime;

  @override
  String toString() {
    return 'AuditDetails(createdBy: $createdBy, lastModifiedBy: $lastModifiedBy, createdTime: $createdTime, lastModifiedTime: $lastModifiedTime)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AuditDetails &&
            (identical(other.createdBy, createdBy) ||
                other.createdBy == createdBy) &&
            (identical(other.lastModifiedBy, lastModifiedBy) ||
                other.lastModifiedBy == lastModifiedBy) &&
            (identical(other.createdTime, createdTime) ||
                other.createdTime == createdTime) &&
            (identical(other.lastModifiedTime, lastModifiedTime) ||
                other.lastModifiedTime == lastModifiedTime));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, createdBy, lastModifiedBy, createdTime, lastModifiedTime);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AuditDetailsCopyWith<_$_AuditDetails> get copyWith =>
      __$$_AuditDetailsCopyWithImpl<_$_AuditDetails>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AuditDetailsToJson(
      this,
    );
  }
}

abstract class _AuditDetails implements AuditDetails {
  const factory _AuditDetails(
      {final String? createdBy,
      final String? lastModifiedBy,
      final int? createdTime,
      final int? lastModifiedTime}) = _$_AuditDetails;

  factory _AuditDetails.fromJson(Map<String, dynamic> json) =
      _$_AuditDetails.fromJson;

  @override
  String? get createdBy;
  @override
  String? get lastModifiedBy;
  @override
  int? get createdTime;
  @override
  int? get lastModifiedTime;
  @override
  @JsonKey(ignore: true)
  _$$_AuditDetailsCopyWith<_$_AuditDetails> get copyWith =>
      throw _privateConstructorUsedError;
}
