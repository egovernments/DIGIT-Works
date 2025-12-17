// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'attendance_registry_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

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
abstract class _$$AttendanceRegistersModelImplCopyWith<$Res>
    implements $AttendanceRegistersModelCopyWith<$Res> {
  factory _$$AttendanceRegistersModelImplCopyWith(
          _$AttendanceRegistersModelImpl value,
          $Res Function(_$AttendanceRegistersModelImpl) then) =
      __$$AttendanceRegistersModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'attendanceRegister')
          List<AttendanceRegister>? attendanceRegister});
}

/// @nodoc
class __$$AttendanceRegistersModelImplCopyWithImpl<$Res>
    extends _$AttendanceRegistersModelCopyWithImpl<$Res,
        _$AttendanceRegistersModelImpl>
    implements _$$AttendanceRegistersModelImplCopyWith<$Res> {
  __$$AttendanceRegistersModelImplCopyWithImpl(
      _$AttendanceRegistersModelImpl _value,
      $Res Function(_$AttendanceRegistersModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegister = freezed,
  }) {
    return _then(_$AttendanceRegistersModelImpl(
      attendanceRegister: freezed == attendanceRegister
          ? _value._attendanceRegister
          : attendanceRegister // ignore: cast_nullable_to_non_nullable
              as List<AttendanceRegister>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendanceRegistersModelImpl implements _AttendanceRegistersModel {
  const _$AttendanceRegistersModelImpl(
      {@JsonKey(name: 'attendanceRegister')
          final List<AttendanceRegister>? attendanceRegister})
      : _attendanceRegister = attendanceRegister;

  factory _$AttendanceRegistersModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$AttendanceRegistersModelImplFromJson(json);

  final List<AttendanceRegister>? _attendanceRegister;
  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister {
    final value = _attendanceRegister;
    if (value == null) return null;
    if (_attendanceRegister is EqualUnmodifiableListView)
      return _attendanceRegister;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendanceRegistersModel(attendanceRegister: $attendanceRegister)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendanceRegistersModelImpl &&
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
  _$$AttendanceRegistersModelImplCopyWith<_$AttendanceRegistersModelImpl>
      get copyWith => __$$AttendanceRegistersModelImplCopyWithImpl<
          _$AttendanceRegistersModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendanceRegistersModelImplToJson(
      this,
    );
  }
}

abstract class _AttendanceRegistersModel implements AttendanceRegistersModel {
  const factory _AttendanceRegistersModel(
          {@JsonKey(name: 'attendanceRegister')
              final List<AttendanceRegister>? attendanceRegister}) =
      _$AttendanceRegistersModelImpl;

  factory _AttendanceRegistersModel.fromJson(Map<String, dynamic> json) =
      _$AttendanceRegistersModelImpl.fromJson;

  @override
  @JsonKey(name: 'attendanceRegister')
  List<AttendanceRegister>? get attendanceRegister;
  @override
  @JsonKey(ignore: true)
  _$$AttendanceRegistersModelImplCopyWith<_$AttendanceRegistersModelImpl>
      get copyWith => throw _privateConstructorUsedError;
}

AttendanceRegister _$AttendanceRegisterFromJson(Map<String, dynamic> json) {
  return _AttendanceRegister.fromJson(json);
}

/// @nodoc
mixin _$AttendanceRegister {
  String? get id => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get registerNumber => throw _privateConstructorUsedError;
  String? get serviceCode => throw _privateConstructorUsedError;
  String? get referenceId => throw _privateConstructorUsedError;
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
  @JsonKey(name: 'auditDetails')
  RegisterAuditDetails? get registerAuditDetails =>
      throw _privateConstructorUsedError;
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
      String tenantId,
      String? registerNumber,
      String? serviceCode,
      String? referenceId,
      String? name,
      int? startDate,
      int? endDate,
      String? status,
      @JsonKey(name: 'additionalDetails')
          AttendanceRegisterAdditionalDetails?
              attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          List<StaffEntries>? staffEntries,
      @JsonKey(name: 'auditDetails')
          RegisterAuditDetails? registerAuditDetails,
      @JsonKey(name: 'attendees')
          List<AttendeesEntries>? attendeesEntries});

  $AttendanceRegisterAdditionalDetailsCopyWith<$Res>?
      get attendanceRegisterAdditionalDetails;
  $RegisterAuditDetailsCopyWith<$Res>? get registerAuditDetails;
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
    Object? tenantId = null,
    Object? registerNumber = freezed,
    Object? serviceCode = freezed,
    Object? referenceId = freezed,
    Object? name = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? status = freezed,
    Object? attendanceRegisterAdditionalDetails = freezed,
    Object? staffEntries = freezed,
    Object? registerAuditDetails = freezed,
    Object? attendeesEntries = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: freezed == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
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
      registerAuditDetails: freezed == registerAuditDetails
          ? _value.registerAuditDetails
          : registerAuditDetails // ignore: cast_nullable_to_non_nullable
              as RegisterAuditDetails?,
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

  @override
  @pragma('vm:prefer-inline')
  $RegisterAuditDetailsCopyWith<$Res>? get registerAuditDetails {
    if (_value.registerAuditDetails == null) {
      return null;
    }

    return $RegisterAuditDetailsCopyWith<$Res>(_value.registerAuditDetails!,
        (value) {
      return _then(_value.copyWith(registerAuditDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$AttendanceRegisterImplCopyWith<$Res>
    implements $AttendanceRegisterCopyWith<$Res> {
  factory _$$AttendanceRegisterImplCopyWith(_$AttendanceRegisterImpl value,
          $Res Function(_$AttendanceRegisterImpl) then) =
      __$$AttendanceRegisterImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? registerNumber,
      String? serviceCode,
      String? referenceId,
      String? name,
      int? startDate,
      int? endDate,
      String? status,
      @JsonKey(name: 'additionalDetails')
          AttendanceRegisterAdditionalDetails?
              attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          List<StaffEntries>? staffEntries,
      @JsonKey(name: 'auditDetails')
          RegisterAuditDetails? registerAuditDetails,
      @JsonKey(name: 'attendees')
          List<AttendeesEntries>? attendeesEntries});

  @override
  $AttendanceRegisterAdditionalDetailsCopyWith<$Res>?
      get attendanceRegisterAdditionalDetails;
  @override
  $RegisterAuditDetailsCopyWith<$Res>? get registerAuditDetails;
}

/// @nodoc
class __$$AttendanceRegisterImplCopyWithImpl<$Res>
    extends _$AttendanceRegisterCopyWithImpl<$Res, _$AttendanceRegisterImpl>
    implements _$$AttendanceRegisterImplCopyWith<$Res> {
  __$$AttendanceRegisterImplCopyWithImpl(_$AttendanceRegisterImpl _value,
      $Res Function(_$AttendanceRegisterImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = null,
    Object? registerNumber = freezed,
    Object? serviceCode = freezed,
    Object? referenceId = freezed,
    Object? name = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? status = freezed,
    Object? attendanceRegisterAdditionalDetails = freezed,
    Object? staffEntries = freezed,
    Object? registerAuditDetails = freezed,
    Object? attendeesEntries = freezed,
  }) {
    return _then(_$AttendanceRegisterImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNumber: freezed == registerNumber
          ? _value.registerNumber
          : registerNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
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
      registerAuditDetails: freezed == registerAuditDetails
          ? _value.registerAuditDetails
          : registerAuditDetails // ignore: cast_nullable_to_non_nullable
              as RegisterAuditDetails?,
      attendeesEntries: freezed == attendeesEntries
          ? _value._attendeesEntries
          : attendeesEntries // ignore: cast_nullable_to_non_nullable
              as List<AttendeesEntries>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendanceRegisterImpl implements _AttendanceRegister {
  const _$AttendanceRegisterImpl(
      {this.id,
      required this.tenantId,
      this.registerNumber,
      this.serviceCode,
      this.referenceId,
      this.name,
      this.startDate,
      this.endDate,
      this.status,
      @JsonKey(name: 'additionalDetails')
          this.attendanceRegisterAdditionalDetails,
      @JsonKey(name: 'staff')
          final List<StaffEntries>? staffEntries,
      @JsonKey(name: 'auditDetails')
          this.registerAuditDetails,
      @JsonKey(name: 'attendees')
          final List<AttendeesEntries>? attendeesEntries})
      : _staffEntries = staffEntries,
        _attendeesEntries = attendeesEntries;

  factory _$AttendanceRegisterImpl.fromJson(Map<String, dynamic> json) =>
      _$$AttendanceRegisterImplFromJson(json);

  @override
  final String? id;
  @override
  final String tenantId;
  @override
  final String? registerNumber;
  @override
  final String? serviceCode;
  @override
  final String? referenceId;
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
    if (_staffEntries is EqualUnmodifiableListView) return _staffEntries;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'auditDetails')
  final RegisterAuditDetails? registerAuditDetails;
  final List<AttendeesEntries>? _attendeesEntries;
  @override
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries {
    final value = _attendeesEntries;
    if (value == null) return null;
    if (_attendeesEntries is EqualUnmodifiableListView)
      return _attendeesEntries;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendanceRegister(id: $id, tenantId: $tenantId, registerNumber: $registerNumber, serviceCode: $serviceCode, referenceId: $referenceId, name: $name, startDate: $startDate, endDate: $endDate, status: $status, attendanceRegisterAdditionalDetails: $attendanceRegisterAdditionalDetails, staffEntries: $staffEntries, registerAuditDetails: $registerAuditDetails, attendeesEntries: $attendeesEntries)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendanceRegisterImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerNumber, registerNumber) ||
                other.registerNumber == registerNumber) &&
            (identical(other.serviceCode, serviceCode) ||
                other.serviceCode == serviceCode) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
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
            (identical(other.registerAuditDetails, registerAuditDetails) ||
                other.registerAuditDetails == registerAuditDetails) &&
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
      serviceCode,
      referenceId,
      name,
      startDate,
      endDate,
      status,
      attendanceRegisterAdditionalDetails,
      const DeepCollectionEquality().hash(_staffEntries),
      registerAuditDetails,
      const DeepCollectionEquality().hash(_attendeesEntries));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AttendanceRegisterImplCopyWith<_$AttendanceRegisterImpl> get copyWith =>
      __$$AttendanceRegisterImplCopyWithImpl<_$AttendanceRegisterImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendanceRegisterImplToJson(
      this,
    );
  }
}

abstract class _AttendanceRegister implements AttendanceRegister {
  const factory _AttendanceRegister(
          {final String? id,
          required final String tenantId,
          final String? registerNumber,
          final String? serviceCode,
          final String? referenceId,
          final String? name,
          final int? startDate,
          final int? endDate,
          final String? status,
          @JsonKey(name: 'additionalDetails')
              final AttendanceRegisterAdditionalDetails?
                  attendanceRegisterAdditionalDetails,
          @JsonKey(name: 'staff')
              final List<StaffEntries>? staffEntries,
          @JsonKey(name: 'auditDetails')
              final RegisterAuditDetails? registerAuditDetails,
          @JsonKey(name: 'attendees')
              final List<AttendeesEntries>? attendeesEntries}) =
      _$AttendanceRegisterImpl;

  factory _AttendanceRegister.fromJson(Map<String, dynamic> json) =
      _$AttendanceRegisterImpl.fromJson;

  @override
  String? get id;
  @override
  String get tenantId;
  @override
  String? get registerNumber;
  @override
  String? get serviceCode;
  @override
  String? get referenceId;
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
  @JsonKey(name: 'auditDetails')
  RegisterAuditDetails? get registerAuditDetails;
  @override
  @JsonKey(name: 'attendees')
  List<AttendeesEntries>? get attendeesEntries;
  @override
  @JsonKey(ignore: true)
  _$$AttendanceRegisterImplCopyWith<_$AttendanceRegisterImpl> get copyWith =>
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
  String? get officerInCharge => throw _privateConstructorUsedError;
  String? get executingAuthority => throw _privateConstructorUsedError;
  String? get projectId => throw _privateConstructorUsedError;
  String? get projectName => throw _privateConstructorUsedError;
  String? get projectType => throw _privateConstructorUsedError;
  String? get projectDesc => throw _privateConstructorUsedError;
  String? get locality => throw _privateConstructorUsedError;
  String? get ward => throw _privateConstructorUsedError;
  int? get amount => throw _privateConstructorUsedError;

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
  $Res call(
      {String? contractId,
      String? orgName,
      String? officerInCharge,
      String? executingAuthority,
      String? projectId,
      String? projectName,
      String? projectType,
      String? projectDesc,
      String? locality,
      String? ward,
      int? amount});
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
    Object? officerInCharge = freezed,
    Object? executingAuthority = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
    Object? projectType = freezed,
    Object? projectDesc = freezed,
    Object? locality = freezed,
    Object? ward = freezed,
    Object? amount = freezed,
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
      officerInCharge: freezed == officerInCharge
          ? _value.officerInCharge
          : officerInCharge // ignore: cast_nullable_to_non_nullable
              as String?,
      executingAuthority: freezed == executingAuthority
          ? _value.executingAuthority
          : executingAuthority // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
      ward: freezed == ward
          ? _value.ward
          : ward // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AttendanceRegisterAdditionalDetailsImplCopyWith<$Res>
    implements $AttendanceRegisterAdditionalDetailsCopyWith<$Res> {
  factory _$$AttendanceRegisterAdditionalDetailsImplCopyWith(
          _$AttendanceRegisterAdditionalDetailsImpl value,
          $Res Function(_$AttendanceRegisterAdditionalDetailsImpl) then) =
      __$$AttendanceRegisterAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? contractId,
      String? orgName,
      String? officerInCharge,
      String? executingAuthority,
      String? projectId,
      String? projectName,
      String? projectType,
      String? projectDesc,
      String? locality,
      String? ward,
      int? amount});
}

/// @nodoc
class __$$AttendanceRegisterAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$AttendanceRegisterAdditionalDetailsCopyWithImpl<$Res,
        _$AttendanceRegisterAdditionalDetailsImpl>
    implements _$$AttendanceRegisterAdditionalDetailsImplCopyWith<$Res> {
  __$$AttendanceRegisterAdditionalDetailsImplCopyWithImpl(
      _$AttendanceRegisterAdditionalDetailsImpl _value,
      $Res Function(_$AttendanceRegisterAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? contractId = freezed,
    Object? orgName = freezed,
    Object? officerInCharge = freezed,
    Object? executingAuthority = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
    Object? projectType = freezed,
    Object? projectDesc = freezed,
    Object? locality = freezed,
    Object? ward = freezed,
    Object? amount = freezed,
  }) {
    return _then(_$AttendanceRegisterAdditionalDetailsImpl(
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      officerInCharge: freezed == officerInCharge
          ? _value.officerInCharge
          : officerInCharge // ignore: cast_nullable_to_non_nullable
              as String?,
      executingAuthority: freezed == executingAuthority
          ? _value.executingAuthority
          : executingAuthority // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
      ward: freezed == ward
          ? _value.ward
          : ward // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendanceRegisterAdditionalDetailsImpl
    implements _AttendanceRegisterAdditionalDetails {
  const _$AttendanceRegisterAdditionalDetailsImpl(
      {this.contractId,
      this.orgName,
      this.officerInCharge,
      this.executingAuthority,
      this.projectId,
      this.projectName,
      this.projectType,
      this.projectDesc,
      this.locality,
      this.ward,
      this.amount});

  factory _$AttendanceRegisterAdditionalDetailsImpl.fromJson(
          Map<String, dynamic> json) =>
      _$$AttendanceRegisterAdditionalDetailsImplFromJson(json);

  @override
  final String? contractId;
  @override
  final String? orgName;
  @override
  final String? officerInCharge;
  @override
  final String? executingAuthority;
  @override
  final String? projectId;
  @override
  final String? projectName;
  @override
  final String? projectType;
  @override
  final String? projectDesc;
  @override
  final String? locality;
  @override
  final String? ward;
  @override
  final int? amount;

  @override
  String toString() {
    return 'AttendanceRegisterAdditionalDetails(contractId: $contractId, orgName: $orgName, officerInCharge: $officerInCharge, executingAuthority: $executingAuthority, projectId: $projectId, projectName: $projectName, projectType: $projectType, projectDesc: $projectDesc, locality: $locality, ward: $ward, amount: $amount)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendanceRegisterAdditionalDetailsImpl &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId) &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.officerInCharge, officerInCharge) ||
                other.officerInCharge == officerInCharge) &&
            (identical(other.executingAuthority, executingAuthority) ||
                other.executingAuthority == executingAuthority) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.projectType, projectType) ||
                other.projectType == projectType) &&
            (identical(other.projectDesc, projectDesc) ||
                other.projectDesc == projectDesc) &&
            (identical(other.locality, locality) ||
                other.locality == locality) &&
            (identical(other.ward, ward) || other.ward == ward) &&
            (identical(other.amount, amount) || other.amount == amount));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      contractId,
      orgName,
      officerInCharge,
      executingAuthority,
      projectId,
      projectName,
      projectType,
      projectDesc,
      locality,
      ward,
      amount);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AttendanceRegisterAdditionalDetailsImplCopyWith<
          _$AttendanceRegisterAdditionalDetailsImpl>
      get copyWith => __$$AttendanceRegisterAdditionalDetailsImplCopyWithImpl<
          _$AttendanceRegisterAdditionalDetailsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendanceRegisterAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _AttendanceRegisterAdditionalDetails
    implements AttendanceRegisterAdditionalDetails {
  const factory _AttendanceRegisterAdditionalDetails(
      {final String? contractId,
      final String? orgName,
      final String? officerInCharge,
      final String? executingAuthority,
      final String? projectId,
      final String? projectName,
      final String? projectType,
      final String? projectDesc,
      final String? locality,
      final String? ward,
      final int? amount}) = _$AttendanceRegisterAdditionalDetailsImpl;

  factory _AttendanceRegisterAdditionalDetails.fromJson(
          Map<String, dynamic> json) =
      _$AttendanceRegisterAdditionalDetailsImpl.fromJson;

  @override
  String? get contractId;
  @override
  String? get orgName;
  @override
  String? get officerInCharge;
  @override
  String? get executingAuthority;
  @override
  String? get projectId;
  @override
  String? get projectName;
  @override
  String? get projectType;
  @override
  String? get projectDesc;
  @override
  String? get locality;
  @override
  String? get ward;
  @override
  int? get amount;
  @override
  @JsonKey(ignore: true)
  _$$AttendanceRegisterAdditionalDetailsImplCopyWith<
          _$AttendanceRegisterAdditionalDetailsImpl>
      get copyWith => throw _privateConstructorUsedError;
}

RegisterAuditDetails _$RegisterAuditDetailsFromJson(Map<String, dynamic> json) {
  return _RegisterAuditDetails.fromJson(json);
}

/// @nodoc
mixin _$RegisterAuditDetails {
  String? get createdBy => throw _privateConstructorUsedError;
  String? get lastModifiedBy => throw _privateConstructorUsedError;
  int? get createdTime => throw _privateConstructorUsedError;
  int? get lastModifiedTime => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $RegisterAuditDetailsCopyWith<RegisterAuditDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $RegisterAuditDetailsCopyWith<$Res> {
  factory $RegisterAuditDetailsCopyWith(RegisterAuditDetails value,
          $Res Function(RegisterAuditDetails) then) =
      _$RegisterAuditDetailsCopyWithImpl<$Res, RegisterAuditDetails>;
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class _$RegisterAuditDetailsCopyWithImpl<$Res,
        $Val extends RegisterAuditDetails>
    implements $RegisterAuditDetailsCopyWith<$Res> {
  _$RegisterAuditDetailsCopyWithImpl(this._value, this._then);

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
abstract class _$$RegisterAuditDetailsImplCopyWith<$Res>
    implements $RegisterAuditDetailsCopyWith<$Res> {
  factory _$$RegisterAuditDetailsImplCopyWith(_$RegisterAuditDetailsImpl value,
          $Res Function(_$RegisterAuditDetailsImpl) then) =
      __$$RegisterAuditDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class __$$RegisterAuditDetailsImplCopyWithImpl<$Res>
    extends _$RegisterAuditDetailsCopyWithImpl<$Res, _$RegisterAuditDetailsImpl>
    implements _$$RegisterAuditDetailsImplCopyWith<$Res> {
  __$$RegisterAuditDetailsImplCopyWithImpl(_$RegisterAuditDetailsImpl _value,
      $Res Function(_$RegisterAuditDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? createdBy = freezed,
    Object? lastModifiedBy = freezed,
    Object? createdTime = freezed,
    Object? lastModifiedTime = freezed,
  }) {
    return _then(_$RegisterAuditDetailsImpl(
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
class _$RegisterAuditDetailsImpl implements _RegisterAuditDetails {
  const _$RegisterAuditDetailsImpl(
      {this.createdBy,
      this.lastModifiedBy,
      this.createdTime,
      this.lastModifiedTime});

  factory _$RegisterAuditDetailsImpl.fromJson(Map<String, dynamic> json) =>
      _$$RegisterAuditDetailsImplFromJson(json);

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
    return 'RegisterAuditDetails(createdBy: $createdBy, lastModifiedBy: $lastModifiedBy, createdTime: $createdTime, lastModifiedTime: $lastModifiedTime)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$RegisterAuditDetailsImpl &&
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
  _$$RegisterAuditDetailsImplCopyWith<_$RegisterAuditDetailsImpl>
      get copyWith =>
          __$$RegisterAuditDetailsImplCopyWithImpl<_$RegisterAuditDetailsImpl>(
              this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$RegisterAuditDetailsImplToJson(
      this,
    );
  }
}

abstract class _RegisterAuditDetails implements RegisterAuditDetails {
  const factory _RegisterAuditDetails(
      {final String? createdBy,
      final String? lastModifiedBy,
      final int? createdTime,
      final int? lastModifiedTime}) = _$RegisterAuditDetailsImpl;

  factory _RegisterAuditDetails.fromJson(Map<String, dynamic> json) =
      _$RegisterAuditDetailsImpl.fromJson;

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
  _$$RegisterAuditDetailsImplCopyWith<_$RegisterAuditDetailsImpl>
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
abstract class _$$StaffEntriesImplCopyWith<$Res>
    implements $StaffEntriesCopyWith<$Res> {
  factory _$$StaffEntriesImplCopyWith(
          _$StaffEntriesImpl value, $Res Function(_$StaffEntriesImpl) then) =
      __$$StaffEntriesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id, String? userId, String? registerId, int? enrollmentDate});
}

/// @nodoc
class __$$StaffEntriesImplCopyWithImpl<$Res>
    extends _$StaffEntriesCopyWithImpl<$Res, _$StaffEntriesImpl>
    implements _$$StaffEntriesImplCopyWith<$Res> {
  __$$StaffEntriesImplCopyWithImpl(
      _$StaffEntriesImpl _value, $Res Function(_$StaffEntriesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? userId = freezed,
    Object? registerId = freezed,
    Object? enrollmentDate = freezed,
  }) {
    return _then(_$StaffEntriesImpl(
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
class _$StaffEntriesImpl implements _StaffEntries {
  const _$StaffEntriesImpl(
      {this.id, this.userId, this.registerId, this.enrollmentDate});

  factory _$StaffEntriesImpl.fromJson(Map<String, dynamic> json) =>
      _$$StaffEntriesImplFromJson(json);

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
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$StaffEntriesImpl &&
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
  _$$StaffEntriesImplCopyWith<_$StaffEntriesImpl> get copyWith =>
      __$$StaffEntriesImplCopyWithImpl<_$StaffEntriesImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$StaffEntriesImplToJson(
      this,
    );
  }
}

abstract class _StaffEntries implements StaffEntries {
  const factory _StaffEntries(
      {final String? id,
      final String? userId,
      final String? registerId,
      final int? enrollmentDate}) = _$StaffEntriesImpl;

  factory _StaffEntries.fromJson(Map<String, dynamic> json) =
      _$StaffEntriesImpl.fromJson;

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
  _$$StaffEntriesImplCopyWith<_$StaffEntriesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendeesEntries _$AttendeesEntriesFromJson(Map<String, dynamic> json) {
  return _AttendeesEntries.fromJson(json);
}

/// @nodoc
mixin _$AttendeesEntries {
  String? get id => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  String? get individualId => throw _privateConstructorUsedError;
  int? get enrollmentDate => throw _privateConstructorUsedError;
  int? get denrollmentDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  AttendeesAdditionalDetails? get additionalDetails =>
      throw _privateConstructorUsedError;

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
      String tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate,
      int? denrollmentDate,
      @JsonKey(name: 'additionalDetails')
          AttendeesAdditionalDetails? additionalDetails});

  $AttendeesAdditionalDetailsCopyWith<$Res>? get additionalDetails;
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
    Object? tenantId = null,
    Object? registerId = freezed,
    Object? individualId = freezed,
    Object? enrollmentDate = freezed,
    Object? denrollmentDate = freezed,
    Object? additionalDetails = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      denrollmentDate: freezed == denrollmentDate
          ? _value.denrollmentDate
          : denrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
      additionalDetails: freezed == additionalDetails
          ? _value.additionalDetails
          : additionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendeesAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendeesAdditionalDetailsCopyWith<$Res>? get additionalDetails {
    if (_value.additionalDetails == null) {
      return null;
    }

    return $AttendeesAdditionalDetailsCopyWith<$Res>(_value.additionalDetails!,
        (value) {
      return _then(_value.copyWith(additionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$AttendeesEntriesImplCopyWith<$Res>
    implements $AttendeesEntriesCopyWith<$Res> {
  factory _$$AttendeesEntriesImplCopyWith(_$AttendeesEntriesImpl value,
          $Res Function(_$AttendeesEntriesImpl) then) =
      __$$AttendeesEntriesImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? registerId,
      String? individualId,
      int? enrollmentDate,
      int? denrollmentDate,
      @JsonKey(name: 'additionalDetails')
          AttendeesAdditionalDetails? additionalDetails});

  @override
  $AttendeesAdditionalDetailsCopyWith<$Res>? get additionalDetails;
}

/// @nodoc
class __$$AttendeesEntriesImplCopyWithImpl<$Res>
    extends _$AttendeesEntriesCopyWithImpl<$Res, _$AttendeesEntriesImpl>
    implements _$$AttendeesEntriesImplCopyWith<$Res> {
  __$$AttendeesEntriesImplCopyWithImpl(_$AttendeesEntriesImpl _value,
      $Res Function(_$AttendeesEntriesImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = null,
    Object? registerId = freezed,
    Object? individualId = freezed,
    Object? enrollmentDate = freezed,
    Object? denrollmentDate = freezed,
    Object? additionalDetails = freezed,
  }) {
    return _then(_$AttendeesEntriesImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
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
      denrollmentDate: freezed == denrollmentDate
          ? _value.denrollmentDate
          : denrollmentDate // ignore: cast_nullable_to_non_nullable
              as int?,
      additionalDetails: freezed == additionalDetails
          ? _value.additionalDetails
          : additionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendeesAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendeesEntriesImpl implements _AttendeesEntries {
  const _$AttendeesEntriesImpl(
      {this.id,
      required this.tenantId,
      this.registerId,
      this.individualId,
      this.enrollmentDate,
      this.denrollmentDate,
      @JsonKey(name: 'additionalDetails') this.additionalDetails});

  factory _$AttendeesEntriesImpl.fromJson(Map<String, dynamic> json) =>
      _$$AttendeesEntriesImplFromJson(json);

  @override
  final String? id;
  @override
  final String tenantId;
  @override
  final String? registerId;
  @override
  final String? individualId;
  @override
  final int? enrollmentDate;
  @override
  final int? denrollmentDate;
  @override
  @JsonKey(name: 'additionalDetails')
  final AttendeesAdditionalDetails? additionalDetails;

  @override
  String toString() {
    return 'AttendeesEntries(id: $id, tenantId: $tenantId, registerId: $registerId, individualId: $individualId, enrollmentDate: $enrollmentDate, denrollmentDate: $denrollmentDate, additionalDetails: $additionalDetails)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendeesEntriesImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.individualId, individualId) ||
                other.individualId == individualId) &&
            (identical(other.enrollmentDate, enrollmentDate) ||
                other.enrollmentDate == enrollmentDate) &&
            (identical(other.denrollmentDate, denrollmentDate) ||
                other.denrollmentDate == denrollmentDate) &&
            (identical(other.additionalDetails, additionalDetails) ||
                other.additionalDetails == additionalDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, tenantId, registerId,
      individualId, enrollmentDate, denrollmentDate, additionalDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AttendeesEntriesImplCopyWith<_$AttendeesEntriesImpl> get copyWith =>
      __$$AttendeesEntriesImplCopyWithImpl<_$AttendeesEntriesImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendeesEntriesImplToJson(
      this,
    );
  }
}

abstract class _AttendeesEntries implements AttendeesEntries {
  const factory _AttendeesEntries(
          {final String? id,
          required final String tenantId,
          final String? registerId,
          final String? individualId,
          final int? enrollmentDate,
          final int? denrollmentDate,
          @JsonKey(name: 'additionalDetails')
              final AttendeesAdditionalDetails? additionalDetails}) =
      _$AttendeesEntriesImpl;

  factory _AttendeesEntries.fromJson(Map<String, dynamic> json) =
      _$AttendeesEntriesImpl.fromJson;

  @override
  String? get id;
  @override
  String get tenantId;
  @override
  String? get registerId;
  @override
  String? get individualId;
  @override
  int? get enrollmentDate;
  @override
  int? get denrollmentDate;
  @override
  @JsonKey(name: 'additionalDetails')
  AttendeesAdditionalDetails? get additionalDetails;
  @override
  @JsonKey(ignore: true)
  _$$AttendeesEntriesImplCopyWith<_$AttendeesEntriesImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendeesAdditionalDetails _$AttendeesAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _AttendeesAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$AttendeesAdditionalDetails {
  String? get individualName => throw _privateConstructorUsedError;
  String? get gender => throw _privateConstructorUsedError;
  String? get individualGaurdianName => throw _privateConstructorUsedError;
  String? get individualID => throw _privateConstructorUsedError;
  String? get identifierId => throw _privateConstructorUsedError;
  String? get bankNumber => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeesAdditionalDetailsCopyWith<AttendeesAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeesAdditionalDetailsCopyWith<$Res> {
  factory $AttendeesAdditionalDetailsCopyWith(AttendeesAdditionalDetails value,
          $Res Function(AttendeesAdditionalDetails) then) =
      _$AttendeesAdditionalDetailsCopyWithImpl<$Res,
          AttendeesAdditionalDetails>;
  @useResult
  $Res call(
      {String? individualName,
      String? gender,
      String? individualGaurdianName,
      String? individualID,
      String? identifierId,
      String? bankNumber});
}

/// @nodoc
class _$AttendeesAdditionalDetailsCopyWithImpl<$Res,
        $Val extends AttendeesAdditionalDetails>
    implements $AttendeesAdditionalDetailsCopyWith<$Res> {
  _$AttendeesAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualName = freezed,
    Object? gender = freezed,
    Object? individualGaurdianName = freezed,
    Object? individualID = freezed,
    Object? identifierId = freezed,
    Object? bankNumber = freezed,
  }) {
    return _then(_value.copyWith(
      individualName: freezed == individualName
          ? _value.individualName
          : individualName // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      individualGaurdianName: freezed == individualGaurdianName
          ? _value.individualGaurdianName
          : individualGaurdianName // ignore: cast_nullable_to_non_nullable
              as String?,
      individualID: freezed == individualID
          ? _value.individualID
          : individualID // ignore: cast_nullable_to_non_nullable
              as String?,
      identifierId: freezed == identifierId
          ? _value.identifierId
          : identifierId // ignore: cast_nullable_to_non_nullable
              as String?,
      bankNumber: freezed == bankNumber
          ? _value.bankNumber
          : bankNumber // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$AttendeesAdditionalDetailsImplCopyWith<$Res>
    implements $AttendeesAdditionalDetailsCopyWith<$Res> {
  factory _$$AttendeesAdditionalDetailsImplCopyWith(
          _$AttendeesAdditionalDetailsImpl value,
          $Res Function(_$AttendeesAdditionalDetailsImpl) then) =
      __$$AttendeesAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? individualName,
      String? gender,
      String? individualGaurdianName,
      String? individualID,
      String? identifierId,
      String? bankNumber});
}

/// @nodoc
class __$$AttendeesAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$AttendeesAdditionalDetailsCopyWithImpl<$Res,
        _$AttendeesAdditionalDetailsImpl>
    implements _$$AttendeesAdditionalDetailsImplCopyWith<$Res> {
  __$$AttendeesAdditionalDetailsImplCopyWithImpl(
      _$AttendeesAdditionalDetailsImpl _value,
      $Res Function(_$AttendeesAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? individualName = freezed,
    Object? gender = freezed,
    Object? individualGaurdianName = freezed,
    Object? individualID = freezed,
    Object? identifierId = freezed,
    Object? bankNumber = freezed,
  }) {
    return _then(_$AttendeesAdditionalDetailsImpl(
      individualName: freezed == individualName
          ? _value.individualName
          : individualName // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      individualGaurdianName: freezed == individualGaurdianName
          ? _value.individualGaurdianName
          : individualGaurdianName // ignore: cast_nullable_to_non_nullable
              as String?,
      individualID: freezed == individualID
          ? _value.individualID
          : individualID // ignore: cast_nullable_to_non_nullable
              as String?,
      identifierId: freezed == identifierId
          ? _value.identifierId
          : identifierId // ignore: cast_nullable_to_non_nullable
              as String?,
      bankNumber: freezed == bankNumber
          ? _value.bankNumber
          : bankNumber // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$AttendeesAdditionalDetailsImpl implements _AttendeesAdditionalDetails {
  const _$AttendeesAdditionalDetailsImpl(
      {this.individualName,
      this.gender,
      this.individualGaurdianName,
      this.individualID,
      this.identifierId,
      this.bankNumber});

  factory _$AttendeesAdditionalDetailsImpl.fromJson(
          Map<String, dynamic> json) =>
      _$$AttendeesAdditionalDetailsImplFromJson(json);

  @override
  final String? individualName;
  @override
  final String? gender;
  @override
  final String? individualGaurdianName;
  @override
  final String? individualID;
  @override
  final String? identifierId;
  @override
  final String? bankNumber;

  @override
  String toString() {
    return 'AttendeesAdditionalDetails(individualName: $individualName, gender: $gender, individualGaurdianName: $individualGaurdianName, individualID: $individualID, identifierId: $identifierId, bankNumber: $bankNumber)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$AttendeesAdditionalDetailsImpl &&
            (identical(other.individualName, individualName) ||
                other.individualName == individualName) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.individualGaurdianName, individualGaurdianName) ||
                other.individualGaurdianName == individualGaurdianName) &&
            (identical(other.individualID, individualID) ||
                other.individualID == individualID) &&
            (identical(other.identifierId, identifierId) ||
                other.identifierId == identifierId) &&
            (identical(other.bankNumber, bankNumber) ||
                other.bankNumber == bankNumber));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, individualName, gender,
      individualGaurdianName, individualID, identifierId, bankNumber);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$AttendeesAdditionalDetailsImplCopyWith<_$AttendeesAdditionalDetailsImpl>
      get copyWith => __$$AttendeesAdditionalDetailsImplCopyWithImpl<
          _$AttendeesAdditionalDetailsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$AttendeesAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _AttendeesAdditionalDetails
    implements AttendeesAdditionalDetails {
  const factory _AttendeesAdditionalDetails(
      {final String? individualName,
      final String? gender,
      final String? individualGaurdianName,
      final String? individualID,
      final String? identifierId,
      final String? bankNumber}) = _$AttendeesAdditionalDetailsImpl;

  factory _AttendeesAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$AttendeesAdditionalDetailsImpl.fromJson;

  @override
  String? get individualName;
  @override
  String? get gender;
  @override
  String? get individualGaurdianName;
  @override
  String? get individualID;
  @override
  String? get identifierId;
  @override
  String? get bankNumber;
  @override
  @JsonKey(ignore: true)
  _$$AttendeesAdditionalDetailsImplCopyWith<_$AttendeesAdditionalDetailsImpl>
      get copyWith => throw _privateConstructorUsedError;
}
