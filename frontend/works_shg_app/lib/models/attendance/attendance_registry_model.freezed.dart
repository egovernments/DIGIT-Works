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

AttendanceRegistersModel _$AttendanceRegistersModelFromJson(
    Map<String, dynamic> json) {
  return _AttendanceRegistersModel.fromJson(json);
}

/// @nodoc
mixin _$AttendanceRegistersModel {
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceRegistersModelCopyWith<AttendanceRegistersModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceRegistersModelCopyWith<$Res> {
  factory $AttendanceRegistersModelCopyWith(AttendanceRegistersModel value,
          $Res Function(AttendanceRegistersModel) then) =
      _$AttendanceRegistersModelCopyWithImpl<$Res, AttendanceRegistersModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendanceRegister>? attendanceRegister});
}

/// @nodoc
class _$AttendanceRegistersModelCopyWithImpl<$Res,
        $Val extends AttendanceRegistersModel>
    implements $AttendanceRegistersModelCopyWith<$Res> {
  _$AttendanceRegistersModelCopyWithImpl(this._value, this._then);

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
abstract class _$$_AttendanceRegistersModelCopyWith<$Res>
    implements $AttendanceRegistersModelCopyWith<$Res> {
  factory _$$_AttendanceRegistersModelCopyWith(
          _$_AttendanceRegistersModel value,
          $Res Function(_$_AttendanceRegistersModel) then) =
      __$$_AttendanceRegistersModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendanceRegister>? attendanceRegister});
}

/// @nodoc
class __$$_AttendanceRegistersModelCopyWithImpl<$Res>
    extends _$AttendanceRegistersModelCopyWithImpl<$Res,
        _$_AttendanceRegistersModel>
    implements _$$_AttendanceRegistersModelCopyWith<$Res> {
  __$$_AttendanceRegistersModelCopyWithImpl(_$_AttendanceRegistersModel _value,
      $Res Function(_$_AttendanceRegistersModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegister = freezed,
  }) {
    return _then(_$_AttendanceRegistersModel(
      attendanceRegister: freezed == attendanceRegister
          ? _value._attendanceRegister
          : attendanceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendanceRegister>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceRegistersModel implements _AttendanceRegistersModel {
  const _$_AttendanceRegistersModel(
      {@JsonKey(name: 'attendanceRegister')
          final List<AttendanceRegister>? attendanceRegister})
      : _attendanceRegister = attendanceRegister;

  factory _$_AttendanceRegistersModel.fromJson(Map<String, dynamic> json) =>
      _$$_AttendanceRegistersModelFromJson(json);

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
    return 'AttendanceRegistersModel(attendanceRegister: $attendanceRegister)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceRegistersModel &&
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
  _$$_AttendanceRegistersModelCopyWith<_$_AttendanceRegistersModel>
      get copyWith => __$$_AttendanceRegistersModelCopyWithImpl<
          _$_AttendanceRegistersModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceRegistersModelToJson(
      this,
    );
  }
}

abstract class _AttendanceRegistersModel implements AttendanceRegistersModel {
  const factory _AttendanceRegistersModel(
          {@JsonKey(name: 'attendanceRegister')
              final List<AttendanceRegister>? attendanceRegister}) =
      _$_AttendanceRegistersModel;

  factory _AttendanceRegistersModel.fromJson(Map<String, dynamic> json) =
      _$_AttendanceRegistersModel.fromJson;

  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceRegistersModelCopyWith<_$_AttendanceRegistersModel>
      get copyWith => throw _privateConstructorUsedError;
}

AttendanceRegister _$AttendanceRegisterFromJson(Map<String, dynamic> json) {
  return _AttendanceRegister.fromJson(json);
}

/// @nodoc
mixin _$AttendanceRegister {
  String? get id => throw _privateConstructorUsedError;
  String? get tenantId => throw _privateConstructorUsedError;
  String? get registerNumber => throw _privateConstructorUsedError;
  String? get name => throw _privateConstructorUsedError;
  int? get startDate => throw _privateConstructorUsedError;
  int? get endDate => throw _privateConstructorUsedError;
  String? get status => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  AttendanceRegisterAdditionalDetails?
      get attendanceRegisterAdditionalDetails =>
          throw _privateConstructorUsedError;
  @JsonKey(name: 'staff')
  List<StaffEntries>? get staffEntries => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceRegisterCopyWith<AttendanceRegister> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceRegisterCopyWith<$Res> {
  factory $AttendanceRegisterCopyWith(
          AttendanceRegister value, $Res Function(AttendanceRegister) then) =
      _$AttendanceRegisterCopyWithImpl<$Res, AttendanceRegister>;
  @useResult
  $Res call(
      {String? id,
      String? tenantId,
      String? registerNumber,
      String? name,
      int? startDate,
      int? endDate,
      String? status,
      @JsonKey(name: 'additionalDetails')
          AttendanceRegisterAdditionalDetails?
              attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees')
          List<AttendeesEntries>? attendeesEntries});

  $AttendanceRegisterAdditionalDetailsCopyWith<$Res>?
      get attendanceRegisterAdditionalDetails;
}

/// @nodoc
class _$AttendanceRegisterCopyWithImpl<$Res, $Val extends AttendanceRegister>
    implements $AttendanceRegisterCopyWith<$Res> {
  _$AttendanceRegisterCopyWithImpl(this._value, this._then);

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
    Object? status = freezed,
    Object? attendanceRegisterAdditionalDetails = freezed,
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
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterAdditionalDetails: freezed ==
              attendanceRegisterAdditionalDetails
          ? _value.attendanceRegisterAdditionalDetails
          : attendanceRegisterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendanceRegisterAdditionalDetails?,
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

  @override
  @pragma('vm:prefer-inline')
  $AttendanceRegisterAdditionalDetailsCopyWith<$Res>?
      get attendanceRegisterAdditionalDetails {
    if (_value.attendanceRegisterAdditionalDetails == null) {
      return null;
    }

    return $AttendanceRegisterAdditionalDetailsCopyWith<$Res>(
        _value.attendanceRegisterAdditionalDetails!, (value) {
      return _then(
          _value.copyWith(attendanceRegisterAdditionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendanceRegisterCopyWith<$Res>
    implements $AttendanceRegisterCopyWith<$Res> {
  factory _$$_AttendanceRegisterCopyWith(_$_AttendanceRegister value,
          $Res Function(_$_AttendanceRegister) then) =
      __$$_AttendanceRegisterCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? tenantId,
      String? registerNumber,
      String? name,
      int? startDate,
      int? endDate,
      String? status,
      @JsonKey(name: 'additionalDetails')
          AttendanceRegisterAdditionalDetails?
              attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees')
          List<AttendeesEntries>? attendeesEntries});

  @override
  $AttendanceRegisterAdditionalDetailsCopyWith<$Res>?
      get attendanceRegisterAdditionalDetails;
}

/// @nodoc
class __$$_AttendanceRegisterCopyWithImpl<$Res>
    extends _$AttendanceRegisterCopyWithImpl<$Res, _$_AttendanceRegister>
    implements _$$_AttendanceRegisterCopyWith<$Res> {
  __$$_AttendanceRegisterCopyWithImpl(
      _$_AttendanceRegister _value, $Res Function(_$_AttendanceRegister) _then)
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
    Object? status = freezed,
    Object? attendanceRegisterAdditionalDetails = freezed,
    Object? staffEntries = freezed,
    Object? attendeesEntries = freezed,
  }) {
    return _then(_$_AttendanceRegister(
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
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterAdditionalDetails: freezed ==
              attendanceRegisterAdditionalDetails
          ? _value.attendanceRegisterAdditionalDetails
          : attendanceRegisterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendanceRegisterAdditionalDetails?,
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
class _$_AttendanceRegister implements _AttendanceRegister {
  const _$_AttendanceRegister(
      {this.id,
      this.tenantId,
      this.registerNumber,
      this.name,
      this.startDate,
      this.endDate,
      this.status,
      @JsonKey(name: 'additionalDetails')
          this.attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          final List<StaffEntries>? staffEntries,
      @JsonKey(name: 'attendees')
          final List<AttendeesEntries>? attendeesEntries})
      : _staffEntries = staffEntries,
        _attendeesEntries = attendeesEntries;

  factory _$_AttendanceRegister.fromJson(Map<String, dynamic> json) =>
      _$$_AttendanceRegisterFromJson(json);

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
  @override
  final String? status;
  @override
  @JsonKey(name: 'additionalDetails')
  final AttendanceRegisterAdditionalDetails?
      attendanceRegisterAdditionalDetails;
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
    return 'AttendanceRegister(id: $id, tenantId: $tenantId, registerNumber: $registerNumber, name: $name, startDate: $startDate, endDate: $endDate, status: $status, attendanceRegisterAdditionalDetails: $attendanceRegisterAdditionalDetails, staffEntries: $staffEntries, attendeesEntries: $attendeesEntries)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceRegister &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.attendanceRegisterAdditionalDetails,
                    attendanceRegisterAdditionalDetails) ||
                other.attendanceRegisterAdditionalDetails ==
                    attendanceRegisterAdditionalDetails) &&
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
      status,
      attendanceRegisterAdditionalDetails,
      const DeepCollectionEquality().hash(_staffEntries),
      const DeepCollectionEquality().hash(_attendeesEntries));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceRegisterCopyWith<_$_AttendanceRegister> get copyWith =>
      __$$_AttendanceRegisterCopyWithImpl<_$_AttendanceRegister>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceRegisterToJson(
      this,
    );
  }
}

abstract class _AttendanceRegister implements AttendanceRegister {
  const factory _AttendanceRegister(
          {final String? id,
          final String? tenantId,
          final String? registerNumber,
          final String? name,
          final int? startDate,
          final int? endDate,
          final String? status,
          @JsonKey(name: 'additionalDetails')
              final AttendanceRegisterAdditionalDetails?
                  attendanceRegisterAdditionalDetails,
          @JsonKey(name: 'staff')
              final List<StaffEntries>? staffEntries,
          @JsonKey(name: 'attendees')
              final List<AttendeesEntries>? attendeesEntries}) =
      _$_AttendanceRegister;

  factory _AttendanceRegister.fromJson(Map<String, dynamic> json) =
      _$_AttendanceRegister.fromJson;

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
  String? get status;
  @override
  @JsonKey(name: 'additionalDetails')
  AttendanceRegisterAdditionalDetails? get attendanceRegisterAdditionalDetails;
  @override
  @JsonKey(name: 'staff')
  List<StaffEntries>? get staffEntries;
  @override
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceRegisterCopyWith<_$_AttendanceRegister> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendanceRegisterAdditionalDetails
    _$AttendanceRegisterAdditionalDetailsFromJson(Map<String, dynamic> json) {
  return _AttendanceRegisterAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$AttendanceRegisterAdditionalDetails {
  String? get contractId => throw _privateConstructorUsedError;
  String? get orgName => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceRegisterAdditionalDetailsCopyWith<
          AttendanceRegisterAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceRegisterAdditionalDetailsCopyWith<$Res> {
  factory $AttendanceRegisterAdditionalDetailsCopyWith(
          AttendanceRegisterAdditionalDetails value,
          $Res Function(AttendanceRegisterAdditionalDetails) then) =
      _$AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res,
          AttendanceRegisterAdditionalDetails>;
  @useResult
  $Res call({String? contractId, String? orgName});
}

/// @nodoc
class _$AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res,
        $Val extends AttendanceRegisterAdditionalDetails>
    implements $AttendanceRegisterAdditionalDetailsCopyWith<$Res> {
  _$AttendanceRegisterAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractId = freezed,
    Object? orgName = freezed,
  }) {
    return _then(_value.copyWith(
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendanceRegisterAdditionalDetailsCopyWith<$Res>
    implements $AttendanceRegisterAdditionalDetailsCopyWith<$Res> {
  factory _$$_AttendanceRegisterAdditionalDetailsCopyWith(
          _$_AttendanceRegisterAdditionalDetails value,
          $Res Function(_$_AttendanceRegisterAdditionalDetails) then) =
      __$$_AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? contractId, String? orgName});
}

/// @nodoc
class __$$_AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res>
    extends _$AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res,
        _$_AttendanceRegisterAdditionalDetails>
    implements _$$_AttendanceRegisterAdditionalDetailsCopyWith<$Res> {
  __$$_AttendanceRegisterAdditionalDetailsCopyWithImpl(
      _$_AttendanceRegisterAdditionalDetails _value,
      $Res Function(_$_AttendanceRegisterAdditionalDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractId = freezed,
    Object? orgName = freezed,
  }) {
    return _then(_$_AttendanceRegisterAdditionalDetails(
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceRegisterAdditionalDetails
    implements _AttendanceRegisterAdditionalDetails {
  const _$_AttendanceRegisterAdditionalDetails({this.contractId, this.orgName});

  factory _$_AttendanceRegisterAdditionalDetails.fromJson(
          Map<String, dynamic> json) =>
      _$$_AttendanceRegisterAdditionalDetailsFromJson(json);

  @override
  final String? contractId;
  @override
  final String? orgName;

  @override
  String toString() {
    return 'AttendanceRegisterAdditionalDetails(contractId: $contractId, orgName: $orgName)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceRegisterAdditionalDetails &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId) &&
            (identical(other.orgName, orgName) || other.orgName == orgName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, contractId, orgName);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceRegisterAdditionalDetailsCopyWith<
          _$_AttendanceRegisterAdditionalDetails>
      get copyWith => __$$_AttendanceRegisterAdditionalDetailsCopyWithImpl<
          _$_AttendanceRegisterAdditionalDetails>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceRegisterAdditionalDetailsToJson(
      this,
    );
  }
}

abstract class _AttendanceRegisterAdditionalDetails
    implements AttendanceRegisterAdditionalDetails {
  const factory _AttendanceRegisterAdditionalDetails(
      {final String? contractId,
      final String? orgName}) = _$_AttendanceRegisterAdditionalDetails;

  factory _AttendanceRegisterAdditionalDetails.fromJson(
          Map<String, dynamic> json) =
      _$_AttendanceRegisterAdditionalDetails.fromJson;

  @override
  String? get contractId;
  @override
  String? get orgName;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceRegisterAdditionalDetailsCopyWith<
          _$_AttendanceRegisterAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

StaffEntries _$StaffEntriesFromJson(Map<String, dynamic> json) {
  return _StaffEntries.fromJson(json);
}

/// @nodoc
mixin _$StaffEntries {
  String? get id => throw _privateConstructorUsedError;
  String? get userId => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  int? get enrollmentDate => throw _privateConstructorUsedError;

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
  $Res call(
      {String? id, String? userId, String? registerId, int? enrollmentDate});
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
    Object? userId = freezed,
    Object? registerId = freezed,
    Object? enrollmentDate = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      userId: freezed == userId
          ? _value.userId
          : userId // ignore: cast_nullable_to_non_nullable
              as String?,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      enrollmentDate: freezed == enrollmentDate
          ? _value.enrollmentDate
          : enrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
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
  $Res call(
      {String? id, String? userId, String? registerId, int? enrollmentDate});
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
    Object? userId = freezed,
    Object? registerId = freezed,
    Object? enrollmentDate = freezed,
  }) {
    return _then(_$_StaffEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      userId: freezed == userId
          ? _value.userId
          : userId // ignore: cast_nullable_to_non_nullable
              as String?,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      enrollmentDate: freezed == enrollmentDate
          ? _value.enrollmentDate
          : enrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_StaffEntries implements _StaffEntries {
  const _$_StaffEntries(
      {this.id, this.userId, this.registerId, this.enrollmentDate});

  factory _$_StaffEntries.fromJson(Map<String, dynamic> json) =>
      _$$_StaffEntriesFromJson(json);

  @override
  final String? id;
  @override
  final String? userId;
  @override
  final String? registerId;
  @override
  final int? enrollmentDate;

  @override
  String toString() {
    return 'StaffEntries(id: $id, userId: $userId, registerId: $registerId, enrollmentDate: $enrollmentDate)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_StaffEntries &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.userId, userId) || other.userId == userId) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.enrollmentDate, enrollmentDate) ||
                other.enrollmentDate == enrollmentDate));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, id, userId, registerId, enrollmentDate);

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
  const factory _StaffEntries(
      {final String? id,
      final String? userId,
      final String? registerId,
      final int? enrollmentDate}) = _$_StaffEntries;

  factory _StaffEntries.fromJson(Map<String, dynamic> json) =
      _$_StaffEntries.fromJson;

  @override
  String? get id;
  @override
  String? get userId;
  @override
  String? get registerId;
  @override
  int? get enrollmentDate;
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
  String? get tenantId => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  String? get individualId => throw _privateConstructorUsedError;
  int? get enrollmentDate => throw _privateConstructorUsedError;

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
  $Res call(
      {String? id,
      String? tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate});
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
    Object? tenantId = freezed,
    Object? registerId = freezed,
    Object? individualId = freezed,
    Object? enrollmentDate = freezed,
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
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      enrollmentDate: freezed == enrollmentDate
          ? _value.enrollmentDate
          : enrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
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
  $Res call(
      {String? id,
      String? tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate});
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
    Object? tenantId = freezed,
    Object? registerId = freezed,
    Object? individualId = freezed,
    Object? enrollmentDate = freezed,
  }) {
    return _then(_$_AttendeesEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      enrollmentDate: freezed == enrollmentDate
          ? _value.enrollmentDate
          : enrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendeesEntries implements _AttendeesEntries {
  const _$_AttendeesEntries(
      {this.id,
      this.tenantId,
      this.registerId,
      this.individualId,
      this.enrollmentDate});

  factory _$_AttendeesEntries.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeesEntriesFromJson(json);

  @override
  final String? id;
  @override
  final String? tenantId;
  @override
  final String? registerId;
  @override
  final String? individualId;
  @override
  final int? enrollmentDate;

  @override
  String toString() {
    return 'AttendeesEntries(id: $id, tenantId: $tenantId, registerId: $registerId, individualId: $individualId, enrollmentDate: $enrollmentDate)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeesEntries &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.individualId, individualId) ||
                other.individualId == individualId) &&
            (identical(other.enrollmentDate, enrollmentDate) ||
                other.enrollmentDate == enrollmentDate));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, id, tenantId, registerId, individualId, enrollmentDate);

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
  const factory _AttendeesEntries(
      {final String? id,
      final String? tenantId,
      final String? registerId,
      final String? individualId,
      final int? enrollmentDate}) = _$_AttendeesEntries;

  factory _AttendeesEntries.fromJson(Map<String, dynamic> json) =
      _$_AttendeesEntries.fromJson;

  @override
  String? get id;
  @override
  String? get tenantId;
  @override
  String? get registerId;
  @override
  String? get individualId;
  @override
  int? get enrollmentDate;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeesEntriesCopyWith<_$_AttendeesEntries> get copyWith =>
      throw _privateConstructorUsedError;
}
