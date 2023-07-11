// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'muster_roll_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

MusterRollsModel _$MusterRollsModelFromJson(Map<String, dynamic> json) {
  return _MusterRollsModel.fromJson(json);
}

/// @nodoc
mixin _$MusterRollsModel {
  @JsonKey(name: 'musterRolls')
  List<MusterRoll>? get musterRoll => throw _privateConstructorUsedError;
  @JsonKey(name: 'count')
  int? get count => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterRollsModelCopyWith<MusterRollsModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollsModelCopyWith<$Res> {
  factory $MusterRollsModelCopyWith(
          MusterRollsModel value, $Res Function(MusterRollsModel) then) =
      _$MusterRollsModelCopyWithImpl<$Res, MusterRollsModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'musterRolls') List<MusterRoll>? musterRoll,
      @JsonKey(name: 'count') int? count});
}

/// @nodoc
class _$MusterRollsModelCopyWithImpl<$Res, $Val extends MusterRollsModel>
    implements $MusterRollsModelCopyWith<$Res> {
  _$MusterRollsModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRoll = freezed,
    Object? count = freezed,
  }) {
    return _then(_value.copyWith(
      musterRoll: freezed == musterRoll
          ? _value.musterRoll
          : musterRoll // ignore: cast_nullable_to_non_nullable
              as List<MusterRoll>?,
      count: freezed == count
          ? _value.count
          : count // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterRollsModelCopyWith<$Res>
    implements $MusterRollsModelCopyWith<$Res> {
  factory _$$_MusterRollsModelCopyWith(
          _$_MusterRollsModel value, $Res Function(_$_MusterRollsModel) then) =
      __$$_MusterRollsModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'musterRolls') List<MusterRoll>? musterRoll,
      @JsonKey(name: 'count') int? count});
}

/// @nodoc
class __$$_MusterRollsModelCopyWithImpl<$Res>
    extends _$MusterRollsModelCopyWithImpl<$Res, _$_MusterRollsModel>
    implements _$$_MusterRollsModelCopyWith<$Res> {
  __$$_MusterRollsModelCopyWithImpl(
      _$_MusterRollsModel _value, $Res Function(_$_MusterRollsModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRoll = freezed,
    Object? count = freezed,
  }) {
    return _then(_$_MusterRollsModel(
      musterRoll: freezed == musterRoll
          ? _value._musterRoll
          : musterRoll // ignore: cast_nullable_to_non_nullable
              as List<MusterRoll>?,
      count: freezed == count
          ? _value.count
          : count // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterRollsModel implements _MusterRollsModel {
  const _$_MusterRollsModel(
      {@JsonKey(name: 'musterRolls') final List<MusterRoll>? musterRoll,
      @JsonKey(name: 'count') this.count})
      : _musterRoll = musterRoll;

  factory _$_MusterRollsModel.fromJson(Map<String, dynamic> json) =>
      _$$_MusterRollsModelFromJson(json);

  final List<MusterRoll>? _musterRoll;
  @override
  @JsonKey(name: 'musterRolls')
  List<MusterRoll>? get musterRoll {
    final value = _musterRoll;
    if (value == null) return null;
    if (_musterRoll is EqualUnmodifiableListView) return _musterRoll;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'count')
  final int? count;

  @override
  String toString() {
    return 'MusterRollsModel(musterRoll: $musterRoll, count: $count)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterRollsModel &&
            const DeepCollectionEquality()
                .equals(other._musterRoll, _musterRoll) &&
            (identical(other.count, count) || other.count == count));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_musterRoll), count);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterRollsModelCopyWith<_$_MusterRollsModel> get copyWith =>
      __$$_MusterRollsModelCopyWithImpl<_$_MusterRollsModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterRollsModelToJson(
      this,
    );
  }
}

abstract class _MusterRollsModel implements MusterRollsModel {
  const factory _MusterRollsModel(
      {@JsonKey(name: 'musterRolls') final List<MusterRoll>? musterRoll,
      @JsonKey(name: 'count') final int? count}) = _$_MusterRollsModel;

  factory _MusterRollsModel.fromJson(Map<String, dynamic> json) =
      _$_MusterRollsModel.fromJson;

  @override
  @JsonKey(name: 'musterRolls')
  List<MusterRoll>? get musterRoll;
  @override
  @JsonKey(name: 'count')
  int? get count;
  @override
  @JsonKey(ignore: true)
  _$$_MusterRollsModelCopyWith<_$_MusterRollsModel> get copyWith =>
      throw _privateConstructorUsedError;
}

MusterRoll _$MusterRollFromJson(Map<String, dynamic> json) {
  return _MusterRoll.fromJson(json);
}

/// @nodoc
mixin _$MusterRoll {
  String? get id => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get musterRollNumber => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  String? get status => throw _privateConstructorUsedError;
  String? get musterRollStatus => throw _privateConstructorUsedError;
  String? get serviceCode => throw _privateConstructorUsedError;
  String? get referenceId => throw _privateConstructorUsedError;
  int? get startDate => throw _privateConstructorUsedError;
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'individualEntries')
  List<IndividualEntries>? get individualEntries =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get musterAuditDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterRollCopyWith<MusterRoll> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterRollCopyWith<$Res> {
  factory $MusterRollCopyWith(
          MusterRoll value, $Res Function(MusterRoll) then) =
      _$MusterRollCopyWithImpl<$Res, MusterRoll>;
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      String? serviceCode,
      String? referenceId,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          MusterAdditionalDetails? musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          AuditDetails? musterAuditDetails});

  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails;
  $AuditDetailsCopyWith<$Res>? get musterAuditDetails;
}

/// @nodoc
class _$MusterRollCopyWithImpl<$Res, $Val extends MusterRoll>
    implements $MusterRollCopyWith<$Res> {
  _$MusterRollCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = null,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? serviceCode = freezed,
    Object? referenceId = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? individualEntries = freezed,
    Object? musterAdditionalDetails = freezed,
    Object? musterAuditDetails = freezed,
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
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollStatus: freezed == musterRollStatus
          ? _value.musterRollStatus
          : musterRollStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      individualEntries: freezed == individualEntries
          ? _value.individualEntries
          : individualEntries // ignore: cast_nullable_to_non_nullable
              as List<IndividualEntries>?,
      musterAdditionalDetails: freezed == musterAdditionalDetails
          ? _value.musterAdditionalDetails
          : musterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterAdditionalDetails?,
      musterAuditDetails: freezed == musterAuditDetails
          ? _value.musterAuditDetails
          : musterAuditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails {
    if (_value.musterAdditionalDetails == null) {
      return null;
    }

    return $MusterAdditionalDetailsCopyWith<$Res>(
        _value.musterAdditionalDetails!, (value) {
      return _then(_value.copyWith(musterAdditionalDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get musterAuditDetails {
    if (_value.musterAuditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.musterAuditDetails!, (value) {
      return _then(_value.copyWith(musterAuditDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_MusterRollCopyWith<$Res>
    implements $MusterRollCopyWith<$Res> {
  factory _$$_MusterRollCopyWith(
          _$_MusterRoll value, $Res Function(_$_MusterRoll) then) =
      __$$_MusterRollCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      String? serviceCode,
      String? referenceId,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          MusterAdditionalDetails? musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          AuditDetails? musterAuditDetails});

  @override
  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails;
  @override
  $AuditDetailsCopyWith<$Res>? get musterAuditDetails;
}

/// @nodoc
class __$$_MusterRollCopyWithImpl<$Res>
    extends _$MusterRollCopyWithImpl<$Res, _$_MusterRoll>
    implements _$$_MusterRollCopyWith<$Res> {
  __$$_MusterRollCopyWithImpl(
      _$_MusterRoll _value, $Res Function(_$_MusterRoll) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? tenantId = null,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? serviceCode = freezed,
    Object? referenceId = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? individualEntries = freezed,
    Object? musterAdditionalDetails = freezed,
    Object? musterAuditDetails = freezed,
  }) {
    return _then(_$_MusterRoll(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      musterRollNumber: freezed == musterRollNumber
          ? _value.musterRollNumber
          : musterRollNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      registerId: freezed == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String?,
      status: freezed == status
          ? _value.status
          : status // ignore: cast_nullable_to_non_nullable
              as String?,
      musterRollStatus: freezed == musterRollStatus
          ? _value.musterRollStatus
          : musterRollStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      startDate: freezed == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int?,
      endDate: freezed == endDate
          ? _value.endDate
          : endDate // ignore: cast_nullable_to_non_nullable
              as int?,
      individualEntries: freezed == individualEntries
          ? _value._individualEntries
          : individualEntries // ignore: cast_nullable_to_non_nullable
              as List<IndividualEntries>?,
      musterAdditionalDetails: freezed == musterAdditionalDetails
          ? _value.musterAdditionalDetails
          : musterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterAdditionalDetails?,
      musterAuditDetails: freezed == musterAuditDetails
          ? _value.musterAuditDetails
          : musterAuditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterRoll implements _MusterRoll {
  const _$_MusterRoll(
      {this.id,
      required this.tenantId,
      this.musterRollNumber,
      this.registerId,
      this.status,
      this.musterRollStatus,
      this.serviceCode,
      this.referenceId,
      this.startDate,
      this.endDate,
      @JsonKey(name: 'individualEntries')
          final List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          this.musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          this.musterAuditDetails})
      : _individualEntries = individualEntries;

  factory _$_MusterRoll.fromJson(Map<String, dynamic> json) =>
      _$$_MusterRollFromJson(json);

  @override
  final String? id;
  @override
  final String tenantId;
  @override
  final String? musterRollNumber;
  @override
  final String? registerId;
  @override
  final String? status;
  @override
  final String? musterRollStatus;
  @override
  final String? serviceCode;
  @override
  final String? referenceId;
  @override
  final int? startDate;
  @override
  final int? endDate;
  final List<IndividualEntries>? _individualEntries;
  @override
  @JsonKey(name: 'individualEntries')
  List<IndividualEntries>? get individualEntries {
    final value = _individualEntries;
    if (value == null) return null;
    if (_individualEntries is EqualUnmodifiableListView)
      return _individualEntries;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'additionalDetails')
  final MusterAdditionalDetails? musterAdditionalDetails;
  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? musterAuditDetails;

  @override
  String toString() {
    return 'MusterRoll(id: $id, tenantId: $tenantId, musterRollNumber: $musterRollNumber, registerId: $registerId, status: $status, musterRollStatus: $musterRollStatus, serviceCode: $serviceCode, referenceId: $referenceId, startDate: $startDate, endDate: $endDate, individualEntries: $individualEntries, musterAdditionalDetails: $musterAdditionalDetails, musterAuditDetails: $musterAuditDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterRoll &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.musterRollNumber, musterRollNumber) ||
                other.musterRollNumber == musterRollNumber) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.status, status) || other.status == status) &&
            (identical(other.musterRollStatus, musterRollStatus) ||
                other.musterRollStatus == musterRollStatus) &&
            (identical(other.serviceCode, serviceCode) ||
                other.serviceCode == serviceCode) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            const DeepCollectionEquality()
                .equals(other._individualEntries, _individualEntries) &&
            (identical(
                    other.musterAdditionalDetails, musterAdditionalDetails) ||
                other.musterAdditionalDetails == musterAdditionalDetails) &&
            (identical(other.musterAuditDetails, musterAuditDetails) ||
                other.musterAuditDetails == musterAuditDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      tenantId,
      musterRollNumber,
      registerId,
      status,
      musterRollStatus,
      serviceCode,
      referenceId,
      startDate,
      endDate,
      const DeepCollectionEquality().hash(_individualEntries),
      musterAdditionalDetails,
      musterAuditDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterRollCopyWith<_$_MusterRoll> get copyWith =>
      __$$_MusterRollCopyWithImpl<_$_MusterRoll>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterRollToJson(
      this,
    );
  }
}

abstract class _MusterRoll implements MusterRoll {
  const factory _MusterRoll(
      {final String? id,
      required final String tenantId,
      final String? musterRollNumber,
      final String? registerId,
      final String? status,
      final String? musterRollStatus,
      final String? serviceCode,
      final String? referenceId,
      final int? startDate,
      final int? endDate,
      @JsonKey(name: 'individualEntries')
          final List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          final MusterAdditionalDetails? musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? musterAuditDetails}) = _$_MusterRoll;

  factory _MusterRoll.fromJson(Map<String, dynamic> json) =
      _$_MusterRoll.fromJson;

  @override
  String? get id;
  @override
  String get tenantId;
  @override
  String? get musterRollNumber;
  @override
  String? get registerId;
  @override
  String? get status;
  @override
  String? get musterRollStatus;
  @override
  String? get serviceCode;
  @override
  String? get referenceId;
  @override
  int? get startDate;
  @override
  int? get endDate;
  @override
  @JsonKey(name: 'individualEntries')
  List<IndividualEntries>? get individualEntries;
  @override
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get musterAuditDetails;
  @override
  @JsonKey(ignore: true)
  _$$_MusterRollCopyWith<_$_MusterRoll> get copyWith =>
      throw _privateConstructorUsedError;
}

IndividualEntries _$IndividualEntriesFromJson(Map<String, dynamic> json) {
  return _IndividualEntries.fromJson(json);
}

/// @nodoc
mixin _$IndividualEntries {
  String? get id => throw _privateConstructorUsedError;
  String? get individualId => throw _privateConstructorUsedError;
  double? get totalAttendance => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendanceEntries')
  List<AttendanceEntries>? get attendanceEntries =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MusterIndividualAdditionalDetails? get musterIndividualAdditionalDetails =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $IndividualEntriesCopyWith<IndividualEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $IndividualEntriesCopyWith<$Res> {
  factory $IndividualEntriesCopyWith(
          IndividualEntries value, $Res Function(IndividualEntries) then) =
      _$IndividualEntriesCopyWithImpl<$Res, IndividualEntries>;
  @useResult
  $Res call(
      {String? id,
      String? individualId,
      double? totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          MusterIndividualAdditionalDetails?
              musterIndividualAdditionalDetails});

  $MusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails;
}

/// @nodoc
class _$IndividualEntriesCopyWithImpl<$Res, $Val extends IndividualEntries>
    implements $IndividualEntriesCopyWith<$Res> {
  _$IndividualEntriesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? individualId = freezed,
    Object? totalAttendance = freezed,
    Object? attendanceEntries = freezed,
    Object? musterIndividualAdditionalDetails = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      totalAttendance: freezed == totalAttendance
          ? _value.totalAttendance
          : totalAttendance // ignore: cast_nullable_to_non_nullable
              as double?,
      attendanceEntries: freezed == attendanceEntries
          ? _value.attendanceEntries
          : attendanceEntries // ignore: cast_nullable_to_non_nullable
              as List<AttendanceEntries>?,
      musterIndividualAdditionalDetails: freezed ==
              musterIndividualAdditionalDetails
          ? _value.musterIndividualAdditionalDetails
          : musterIndividualAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterIndividualAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails {
    if (_value.musterIndividualAdditionalDetails == null) {
      return null;
    }

    return $MusterIndividualAdditionalDetailsCopyWith<$Res>(
        _value.musterIndividualAdditionalDetails!, (value) {
      return _then(
          _value.copyWith(musterIndividualAdditionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_IndividualEntriesCopyWith<$Res>
    implements $IndividualEntriesCopyWith<$Res> {
  factory _$$_IndividualEntriesCopyWith(_$_IndividualEntries value,
          $Res Function(_$_IndividualEntries) then) =
      __$$_IndividualEntriesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? individualId,
      double? totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          MusterIndividualAdditionalDetails?
              musterIndividualAdditionalDetails});

  @override
  $MusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails;
}

/// @nodoc
class __$$_IndividualEntriesCopyWithImpl<$Res>
    extends _$IndividualEntriesCopyWithImpl<$Res, _$_IndividualEntries>
    implements _$$_IndividualEntriesCopyWith<$Res> {
  __$$_IndividualEntriesCopyWithImpl(
      _$_IndividualEntries _value, $Res Function(_$_IndividualEntries) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? individualId = freezed,
    Object? totalAttendance = freezed,
    Object? attendanceEntries = freezed,
    Object? musterIndividualAdditionalDetails = freezed,
  }) {
    return _then(_$_IndividualEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      totalAttendance: freezed == totalAttendance
          ? _value.totalAttendance
          : totalAttendance // ignore: cast_nullable_to_non_nullable
              as double?,
      attendanceEntries: freezed == attendanceEntries
          ? _value._attendanceEntries
          : attendanceEntries // ignore: cast_nullable_to_non_nullable
              as List<AttendanceEntries>?,
      musterIndividualAdditionalDetails: freezed ==
              musterIndividualAdditionalDetails
          ? _value.musterIndividualAdditionalDetails
          : musterIndividualAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterIndividualAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_IndividualEntries implements _IndividualEntries {
  const _$_IndividualEntries(
      {this.id,
      this.individualId,
      this.totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          final List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          this.musterIndividualAdditionalDetails})
      : _attendanceEntries = attendanceEntries;

  factory _$_IndividualEntries.fromJson(Map<String, dynamic> json) =>
      _$$_IndividualEntriesFromJson(json);

  @override
  final String? id;
  @override
  final String? individualId;
  @override
  final double? totalAttendance;
  final List<AttendanceEntries>? _attendanceEntries;
  @override
  @JsonKey(name: 'attendanceEntries')
  List<AttendanceEntries>? get attendanceEntries {
    final value = _attendanceEntries;
    if (value == null) return null;
    if (_attendanceEntries is EqualUnmodifiableListView)
      return _attendanceEntries;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'additionalDetails')
  final MusterIndividualAdditionalDetails? musterIndividualAdditionalDetails;

  @override
  String toString() {
    return 'IndividualEntries(id: $id, individualId: $individualId, totalAttendance: $totalAttendance, attendanceEntries: $attendanceEntries, musterIndividualAdditionalDetails: $musterIndividualAdditionalDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_IndividualEntries &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.individualId, individualId) ||
                other.individualId == individualId) &&
            (identical(other.totalAttendance, totalAttendance) ||
                other.totalAttendance == totalAttendance) &&
            const DeepCollectionEquality()
                .equals(other._attendanceEntries, _attendanceEntries) &&
            (identical(other.musterIndividualAdditionalDetails,
                    musterIndividualAdditionalDetails) ||
                other.musterIndividualAdditionalDetails ==
                    musterIndividualAdditionalDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      individualId,
      totalAttendance,
      const DeepCollectionEquality().hash(_attendanceEntries),
      musterIndividualAdditionalDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_IndividualEntriesCopyWith<_$_IndividualEntries> get copyWith =>
      __$$_IndividualEntriesCopyWithImpl<_$_IndividualEntries>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_IndividualEntriesToJson(
      this,
    );
  }
}

abstract class _IndividualEntries implements IndividualEntries {
  const factory _IndividualEntries(
      {final String? id,
      final String? individualId,
      final double? totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          final List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          final MusterIndividualAdditionalDetails?
              musterIndividualAdditionalDetails}) = _$_IndividualEntries;

  factory _IndividualEntries.fromJson(Map<String, dynamic> json) =
      _$_IndividualEntries.fromJson;

  @override
  String? get id;
  @override
  String? get individualId;
  @override
  double? get totalAttendance;
  @override
  @JsonKey(name: 'attendanceEntries')
  List<AttendanceEntries>? get attendanceEntries;
  @override
  @JsonKey(name: 'additionalDetails')
  MusterIndividualAdditionalDetails? get musterIndividualAdditionalDetails;
  @override
  @JsonKey(ignore: true)
  _$$_IndividualEntriesCopyWith<_$_IndividualEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

MusterAdditionalDetails _$MusterAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _MusterAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$MusterAdditionalDetails {
  String? get attendanceRegisterName => throw _privateConstructorUsedError;
  String? get attendanceRegisterNo => throw _privateConstructorUsedError;
  String? get orgName => throw _privateConstructorUsedError;
  int? get amount => throw _privateConstructorUsedError;
  String? get assignee => throw _privateConstructorUsedError;
  String? get billType => throw _privateConstructorUsedError;
  String? get projectId => throw _privateConstructorUsedError;
  String? get projectName => throw _privateConstructorUsedError;
  String? get projectDesc => throw _privateConstructorUsedError;
  String? get contractId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterAdditionalDetailsCopyWith<MusterAdditionalDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterAdditionalDetailsCopyWith<$Res> {
  factory $MusterAdditionalDetailsCopyWith(MusterAdditionalDetails value,
          $Res Function(MusterAdditionalDetails) then) =
      _$MusterAdditionalDetailsCopyWithImpl<$Res, MusterAdditionalDetails>;
  @useResult
  $Res call(
      {String? attendanceRegisterName,
      String? attendanceRegisterNo,
      String? orgName,
      int? amount,
      String? assignee,
      String? billType,
      String? projectId,
      String? projectName,
      String? projectDesc,
      String? contractId});
}

/// @nodoc
class _$MusterAdditionalDetailsCopyWithImpl<$Res,
        $Val extends MusterAdditionalDetails>
    implements $MusterAdditionalDetailsCopyWith<$Res> {
  _$MusterAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegisterName = freezed,
    Object? attendanceRegisterNo = freezed,
    Object? orgName = freezed,
    Object? amount = freezed,
    Object? assignee = freezed,
    Object? billType = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
    Object? projectDesc = freezed,
    Object? contractId = freezed,
  }) {
    return _then(_value.copyWith(
      attendanceRegisterName: freezed == attendanceRegisterName
          ? _value.attendanceRegisterName
          : attendanceRegisterName // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterNo: freezed == attendanceRegisterNo
          ? _value.attendanceRegisterNo
          : attendanceRegisterNo // ignore: cast_nullable_to_non_nullable
              as String?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      assignee: freezed == assignee
          ? _value.assignee
          : assignee // ignore: cast_nullable_to_non_nullable
              as String?,
      billType: freezed == billType
          ? _value.billType
          : billType // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterAdditionalDetailsCopyWith<$Res>
    implements $MusterAdditionalDetailsCopyWith<$Res> {
  factory _$$_MusterAdditionalDetailsCopyWith(_$_MusterAdditionalDetails value,
          $Res Function(_$_MusterAdditionalDetails) then) =
      __$$_MusterAdditionalDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? attendanceRegisterName,
      String? attendanceRegisterNo,
      String? orgName,
      int? amount,
      String? assignee,
      String? billType,
      String? projectId,
      String? projectName,
      String? projectDesc,
      String? contractId});
}

/// @nodoc
class __$$_MusterAdditionalDetailsCopyWithImpl<$Res>
    extends _$MusterAdditionalDetailsCopyWithImpl<$Res,
        _$_MusterAdditionalDetails>
    implements _$$_MusterAdditionalDetailsCopyWith<$Res> {
  __$$_MusterAdditionalDetailsCopyWithImpl(_$_MusterAdditionalDetails _value,
      $Res Function(_$_MusterAdditionalDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendanceRegisterName = freezed,
    Object? attendanceRegisterNo = freezed,
    Object? orgName = freezed,
    Object? amount = freezed,
    Object? assignee = freezed,
    Object? billType = freezed,
    Object? projectId = freezed,
    Object? projectName = freezed,
    Object? projectDesc = freezed,
    Object? contractId = freezed,
  }) {
    return _then(_$_MusterAdditionalDetails(
      attendanceRegisterName: freezed == attendanceRegisterName
          ? _value.attendanceRegisterName
          : attendanceRegisterName // ignore: cast_nullable_to_non_nullable
              as String?,
      attendanceRegisterNo: freezed == attendanceRegisterNo
          ? _value.attendanceRegisterNo
          : attendanceRegisterNo // ignore: cast_nullable_to_non_nullable
              as String?,
      orgName: freezed == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      assignee: freezed == assignee
          ? _value.assignee
          : assignee // ignore: cast_nullable_to_non_nullable
              as String?,
      billType: freezed == billType
          ? _value.billType
          : billType // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      contractId: freezed == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterAdditionalDetails implements _MusterAdditionalDetails {
  const _$_MusterAdditionalDetails(
      {this.attendanceRegisterName,
      this.attendanceRegisterNo,
      this.orgName,
      this.amount,
      this.assignee,
      this.billType,
      this.projectId,
      this.projectName,
      this.projectDesc,
      this.contractId});

  factory _$_MusterAdditionalDetails.fromJson(Map<String, dynamic> json) =>
      _$$_MusterAdditionalDetailsFromJson(json);

  @override
  final String? attendanceRegisterName;
  @override
  final String? attendanceRegisterNo;
  @override
  final String? orgName;
  @override
  final int? amount;
  @override
  final String? assignee;
  @override
  final String? billType;
  @override
  final String? projectId;
  @override
  final String? projectName;
  @override
  final String? projectDesc;
  @override
  final String? contractId;

  @override
  String toString() {
    return 'MusterAdditionalDetails(attendanceRegisterName: $attendanceRegisterName, attendanceRegisterNo: $attendanceRegisterNo, orgName: $orgName, amount: $amount, assignee: $assignee, billType: $billType, projectId: $projectId, projectName: $projectName, projectDesc: $projectDesc, contractId: $contractId)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterAdditionalDetails &&
            (identical(other.attendanceRegisterName, attendanceRegisterName) ||
                other.attendanceRegisterName == attendanceRegisterName) &&
            (identical(other.attendanceRegisterNo, attendanceRegisterNo) ||
                other.attendanceRegisterNo == attendanceRegisterNo) &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.assignee, assignee) ||
                other.assignee == assignee) &&
            (identical(other.billType, billType) ||
                other.billType == billType) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.projectDesc, projectDesc) ||
                other.projectDesc == projectDesc) &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      attendanceRegisterName,
      attendanceRegisterNo,
      orgName,
      amount,
      assignee,
      billType,
      projectId,
      projectName,
      projectDesc,
      contractId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterAdditionalDetailsCopyWith<_$_MusterAdditionalDetails>
      get copyWith =>
          __$$_MusterAdditionalDetailsCopyWithImpl<_$_MusterAdditionalDetails>(
              this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterAdditionalDetailsToJson(
      this,
    );
  }
}

abstract class _MusterAdditionalDetails implements MusterAdditionalDetails {
  const factory _MusterAdditionalDetails(
      {final String? attendanceRegisterName,
      final String? attendanceRegisterNo,
      final String? orgName,
      final int? amount,
      final String? assignee,
      final String? billType,
      final String? projectId,
      final String? projectName,
      final String? projectDesc,
      final String? contractId}) = _$_MusterAdditionalDetails;

  factory _MusterAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$_MusterAdditionalDetails.fromJson;

  @override
  String? get attendanceRegisterName;
  @override
  String? get attendanceRegisterNo;
  @override
  String? get orgName;
  @override
  int? get amount;
  @override
  String? get assignee;
  @override
  String? get billType;
  @override
  String? get projectId;
  @override
  String? get projectName;
  @override
  String? get projectDesc;
  @override
  String? get contractId;
  @override
  @JsonKey(ignore: true)
  _$$_MusterAdditionalDetailsCopyWith<_$_MusterAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

MusterIndividualAdditionalDetails _$MusterIndividualAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _MusterIndividualAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$MusterIndividualAdditionalDetails {
  String? get userName => throw _privateConstructorUsedError;
  String? get fatherName => throw _privateConstructorUsedError;
  String? get gender => throw _privateConstructorUsedError;
  String? get aadharNumber => throw _privateConstructorUsedError;
  String? get bankDetails => throw _privateConstructorUsedError;
  String? get userId => throw _privateConstructorUsedError;
  String? get accountHolderName => throw _privateConstructorUsedError;
  String? get accountType => throw _privateConstructorUsedError;
  String? get skillCode => throw _privateConstructorUsedError;
  String? get skillValue => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $MusterIndividualAdditionalDetailsCopyWith<MusterIndividualAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterIndividualAdditionalDetailsCopyWith<$Res> {
  factory $MusterIndividualAdditionalDetailsCopyWith(
          MusterIndividualAdditionalDetails value,
          $Res Function(MusterIndividualAdditionalDetails) then) =
      _$MusterIndividualAdditionalDetailsCopyWithImpl<$Res,
          MusterIndividualAdditionalDetails>;
  @useResult
  $Res call(
      {String? userName,
      String? fatherName,
      String? gender,
      String? aadharNumber,
      String? bankDetails,
      String? userId,
      String? accountHolderName,
      String? accountType,
      String? skillCode,
      String? skillValue});
}

/// @nodoc
class _$MusterIndividualAdditionalDetailsCopyWithImpl<$Res,
        $Val extends MusterIndividualAdditionalDetails>
    implements $MusterIndividualAdditionalDetailsCopyWith<$Res> {
  _$MusterIndividualAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? userName = freezed,
    Object? fatherName = freezed,
    Object? gender = freezed,
    Object? aadharNumber = freezed,
    Object? bankDetails = freezed,
    Object? userId = freezed,
    Object? accountHolderName = freezed,
    Object? accountType = freezed,
    Object? skillCode = freezed,
    Object? skillValue = freezed,
  }) {
    return _then(_value.copyWith(
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
      fatherName: freezed == fatherName
          ? _value.fatherName
          : fatherName // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      aadharNumber: freezed == aadharNumber
          ? _value.aadharNumber
          : aadharNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      bankDetails: freezed == bankDetails
          ? _value.bankDetails
          : bankDetails // ignore: cast_nullable_to_non_nullable
              as String?,
      userId: freezed == userId
          ? _value.userId
          : userId // ignore: cast_nullable_to_non_nullable
              as String?,
      accountHolderName: freezed == accountHolderName
          ? _value.accountHolderName
          : accountHolderName // ignore: cast_nullable_to_non_nullable
              as String?,
      accountType: freezed == accountType
          ? _value.accountType
          : accountType // ignore: cast_nullable_to_non_nullable
              as String?,
      skillCode: freezed == skillCode
          ? _value.skillCode
          : skillCode // ignore: cast_nullable_to_non_nullable
              as String?,
      skillValue: freezed == skillValue
          ? _value.skillValue
          : skillValue // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_MusterIndividualAdditionalDetailsCopyWith<$Res>
    implements $MusterIndividualAdditionalDetailsCopyWith<$Res> {
  factory _$$_MusterIndividualAdditionalDetailsCopyWith(
          _$_MusterIndividualAdditionalDetails value,
          $Res Function(_$_MusterIndividualAdditionalDetails) then) =
      __$$_MusterIndividualAdditionalDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? userName,
      String? fatherName,
      String? gender,
      String? aadharNumber,
      String? bankDetails,
      String? userId,
      String? accountHolderName,
      String? accountType,
      String? skillCode,
      String? skillValue});
}

/// @nodoc
class __$$_MusterIndividualAdditionalDetailsCopyWithImpl<$Res>
    extends _$MusterIndividualAdditionalDetailsCopyWithImpl<$Res,
        _$_MusterIndividualAdditionalDetails>
    implements _$$_MusterIndividualAdditionalDetailsCopyWith<$Res> {
  __$$_MusterIndividualAdditionalDetailsCopyWithImpl(
      _$_MusterIndividualAdditionalDetails _value,
      $Res Function(_$_MusterIndividualAdditionalDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? userName = freezed,
    Object? fatherName = freezed,
    Object? gender = freezed,
    Object? aadharNumber = freezed,
    Object? bankDetails = freezed,
    Object? userId = freezed,
    Object? accountHolderName = freezed,
    Object? accountType = freezed,
    Object? skillCode = freezed,
    Object? skillValue = freezed,
  }) {
    return _then(_$_MusterIndividualAdditionalDetails(
      userName: freezed == userName
          ? _value.userName
          : userName // ignore: cast_nullable_to_non_nullable
              as String?,
      fatherName: freezed == fatherName
          ? _value.fatherName
          : fatherName // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      aadharNumber: freezed == aadharNumber
          ? _value.aadharNumber
          : aadharNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      bankDetails: freezed == bankDetails
          ? _value.bankDetails
          : bankDetails // ignore: cast_nullable_to_non_nullable
              as String?,
      userId: freezed == userId
          ? _value.userId
          : userId // ignore: cast_nullable_to_non_nullable
              as String?,
      accountHolderName: freezed == accountHolderName
          ? _value.accountHolderName
          : accountHolderName // ignore: cast_nullable_to_non_nullable
              as String?,
      accountType: freezed == accountType
          ? _value.accountType
          : accountType // ignore: cast_nullable_to_non_nullable
              as String?,
      skillCode: freezed == skillCode
          ? _value.skillCode
          : skillCode // ignore: cast_nullable_to_non_nullable
              as String?,
      skillValue: freezed == skillValue
          ? _value.skillValue
          : skillValue // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterIndividualAdditionalDetails
    implements _MusterIndividualAdditionalDetails {
  const _$_MusterIndividualAdditionalDetails(
      {this.userName,
      this.fatherName,
      this.gender,
      this.aadharNumber,
      this.bankDetails,
      this.userId,
      this.accountHolderName,
      this.accountType,
      this.skillCode,
      this.skillValue});

  factory _$_MusterIndividualAdditionalDetails.fromJson(
          Map<String, dynamic> json) =>
      _$$_MusterIndividualAdditionalDetailsFromJson(json);

  @override
  final String? userName;
  @override
  final String? fatherName;
  @override
  final String? gender;
  @override
  final String? aadharNumber;
  @override
  final String? bankDetails;
  @override
  final String? userId;
  @override
  final String? accountHolderName;
  @override
  final String? accountType;
  @override
  final String? skillCode;
  @override
  final String? skillValue;

  @override
  String toString() {
    return 'MusterIndividualAdditionalDetails(userName: $userName, fatherName: $fatherName, gender: $gender, aadharNumber: $aadharNumber, bankDetails: $bankDetails, userId: $userId, accountHolderName: $accountHolderName, accountType: $accountType, skillCode: $skillCode, skillValue: $skillValue)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_MusterIndividualAdditionalDetails &&
            (identical(other.userName, userName) ||
                other.userName == userName) &&
            (identical(other.fatherName, fatherName) ||
                other.fatherName == fatherName) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.aadharNumber, aadharNumber) ||
                other.aadharNumber == aadharNumber) &&
            (identical(other.bankDetails, bankDetails) ||
                other.bankDetails == bankDetails) &&
            (identical(other.userId, userId) || other.userId == userId) &&
            (identical(other.accountHolderName, accountHolderName) ||
                other.accountHolderName == accountHolderName) &&
            (identical(other.accountType, accountType) ||
                other.accountType == accountType) &&
            (identical(other.skillCode, skillCode) ||
                other.skillCode == skillCode) &&
            (identical(other.skillValue, skillValue) ||
                other.skillValue == skillValue));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      userName,
      fatherName,
      gender,
      aadharNumber,
      bankDetails,
      userId,
      accountHolderName,
      accountType,
      skillCode,
      skillValue);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_MusterIndividualAdditionalDetailsCopyWith<
          _$_MusterIndividualAdditionalDetails>
      get copyWith => __$$_MusterIndividualAdditionalDetailsCopyWithImpl<
          _$_MusterIndividualAdditionalDetails>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_MusterIndividualAdditionalDetailsToJson(
      this,
    );
  }
}

abstract class _MusterIndividualAdditionalDetails
    implements MusterIndividualAdditionalDetails {
  const factory _MusterIndividualAdditionalDetails(
      {final String? userName,
      final String? fatherName,
      final String? gender,
      final String? aadharNumber,
      final String? bankDetails,
      final String? userId,
      final String? accountHolderName,
      final String? accountType,
      final String? skillCode,
      final String? skillValue}) = _$_MusterIndividualAdditionalDetails;

  factory _MusterIndividualAdditionalDetails.fromJson(
          Map<String, dynamic> json) =
      _$_MusterIndividualAdditionalDetails.fromJson;

  @override
  String? get userName;
  @override
  String? get fatherName;
  @override
  String? get gender;
  @override
  String? get aadharNumber;
  @override
  String? get bankDetails;
  @override
  String? get userId;
  @override
  String? get accountHolderName;
  @override
  String? get accountType;
  @override
  String? get skillCode;
  @override
  String? get skillValue;
  @override
  @JsonKey(ignore: true)
  _$$_MusterIndividualAdditionalDetailsCopyWith<
          _$_MusterIndividualAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

AttendanceEntries _$AttendanceEntriesFromJson(Map<String, dynamic> json) {
  return _AttendanceEntries.fromJson(json);
}

/// @nodoc
mixin _$AttendanceEntries {
  String? get id => throw _privateConstructorUsedError;
  double? get attendance => throw _privateConstructorUsedError;
  int? get time => throw _privateConstructorUsedError;
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  AttendanceEntriesAdditionalDetails? get attendanceEntriesAdditionalDetails =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceEntriesCopyWith<AttendanceEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceEntriesCopyWith<$Res> {
  factory $AttendanceEntriesCopyWith(
          AttendanceEntries value, $Res Function(AttendanceEntries) then) =
      _$AttendanceEntriesCopyWithImpl<$Res, AttendanceEntries>;
  @useResult
  $Res call(
      {String? id,
      double? attendance,
      int? time,
      AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          AttendanceEntriesAdditionalDetails?
              attendanceEntriesAdditionalDetails});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
  $AttendanceEntriesAdditionalDetailsCopyWith<$Res>?
      get attendanceEntriesAdditionalDetails;
}

/// @nodoc
class _$AttendanceEntriesCopyWithImpl<$Res, $Val extends AttendanceEntries>
    implements $AttendanceEntriesCopyWith<$Res> {
  _$AttendanceEntriesCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? attendance = freezed,
    Object? time = freezed,
    Object? auditDetails = freezed,
    Object? attendanceEntriesAdditionalDetails = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      attendance: freezed == attendance
          ? _value.attendance
          : attendance // ignore: cast_nullable_to_non_nullable
              as double?,
      time: freezed == time
          ? _value.time
          : time // ignore: cast_nullable_to_non_nullable
              as int?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      attendanceEntriesAdditionalDetails: freezed ==
              attendanceEntriesAdditionalDetails
          ? _value.attendanceEntriesAdditionalDetails
          : attendanceEntriesAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendanceEntriesAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $AuditDetailsCopyWith<$Res>? get auditDetails {
    if (_value.auditDetails == null) {
      return null;
    }

    return $AuditDetailsCopyWith<$Res>(_value.auditDetails!, (value) {
      return _then(_value.copyWith(auditDetails: value) as $Val);
    });
  }

  @override
  @pragma('vm:prefer-inline')
  $AttendanceEntriesAdditionalDetailsCopyWith<$Res>?
      get attendanceEntriesAdditionalDetails {
    if (_value.attendanceEntriesAdditionalDetails == null) {
      return null;
    }

    return $AttendanceEntriesAdditionalDetailsCopyWith<$Res>(
        _value.attendanceEntriesAdditionalDetails!, (value) {
      return _then(
          _value.copyWith(attendanceEntriesAdditionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_AttendanceEntriesCopyWith<$Res>
    implements $AttendanceEntriesCopyWith<$Res> {
  factory _$$_AttendanceEntriesCopyWith(_$_AttendanceEntries value,
          $Res Function(_$_AttendanceEntries) then) =
      __$$_AttendanceEntriesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      double? attendance,
      int? time,
      AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          AttendanceEntriesAdditionalDetails?
              attendanceEntriesAdditionalDetails});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
  @override
  $AttendanceEntriesAdditionalDetailsCopyWith<$Res>?
      get attendanceEntriesAdditionalDetails;
}

/// @nodoc
class __$$_AttendanceEntriesCopyWithImpl<$Res>
    extends _$AttendanceEntriesCopyWithImpl<$Res, _$_AttendanceEntries>
    implements _$$_AttendanceEntriesCopyWith<$Res> {
  __$$_AttendanceEntriesCopyWithImpl(
      _$_AttendanceEntries _value, $Res Function(_$_AttendanceEntries) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? attendance = freezed,
    Object? time = freezed,
    Object? auditDetails = freezed,
    Object? attendanceEntriesAdditionalDetails = freezed,
  }) {
    return _then(_$_AttendanceEntries(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      attendance: freezed == attendance
          ? _value.attendance
          : attendance // ignore: cast_nullable_to_non_nullable
              as double?,
      time: freezed == time
          ? _value.time
          : time // ignore: cast_nullable_to_non_nullable
              as int?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
      attendanceEntriesAdditionalDetails: freezed ==
              attendanceEntriesAdditionalDetails
          ? _value.attendanceEntriesAdditionalDetails
          : attendanceEntriesAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as AttendanceEntriesAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceEntries implements _AttendanceEntries {
  const _$_AttendanceEntries(
      {this.id,
      this.attendance,
      this.time,
      this.auditDetails,
      @JsonKey(name: 'additionalDetails')
          this.attendanceEntriesAdditionalDetails});

  factory _$_AttendanceEntries.fromJson(Map<String, dynamic> json) =>
      _$$_AttendanceEntriesFromJson(json);

  @override
  final String? id;
  @override
  final double? attendance;
  @override
  final int? time;
  @override
  final AuditDetails? auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  final AttendanceEntriesAdditionalDetails? attendanceEntriesAdditionalDetails;

  @override
  String toString() {
    return 'AttendanceEntries(id: $id, attendance: $attendance, time: $time, auditDetails: $auditDetails, attendanceEntriesAdditionalDetails: $attendanceEntriesAdditionalDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceEntries &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.attendance, attendance) ||
                other.attendance == attendance) &&
            (identical(other.time, time) || other.time == time) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails) &&
            (identical(other.attendanceEntriesAdditionalDetails,
                    attendanceEntriesAdditionalDetails) ||
                other.attendanceEntriesAdditionalDetails ==
                    attendanceEntriesAdditionalDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, attendance, time,
      auditDetails, attendanceEntriesAdditionalDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceEntriesCopyWith<_$_AttendanceEntries> get copyWith =>
      __$$_AttendanceEntriesCopyWithImpl<_$_AttendanceEntries>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceEntriesToJson(
      this,
    );
  }
}

abstract class _AttendanceEntries implements AttendanceEntries {
  const factory _AttendanceEntries(
      {final String? id,
      final double? attendance,
      final int? time,
      final AuditDetails? auditDetails,
      @JsonKey(name: 'additionalDetails')
          final AttendanceEntriesAdditionalDetails?
              attendanceEntriesAdditionalDetails}) = _$_AttendanceEntries;

  factory _AttendanceEntries.fromJson(Map<String, dynamic> json) =
      _$_AttendanceEntries.fromJson;

  @override
  String? get id;
  @override
  double? get attendance;
  @override
  int? get time;
  @override
  AuditDetails? get auditDetails;
  @override
  @JsonKey(name: 'additionalDetails')
  AttendanceEntriesAdditionalDetails? get attendanceEntriesAdditionalDetails;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceEntriesCopyWith<_$_AttendanceEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendanceEntriesAdditionalDetails _$AttendanceEntriesAdditionalDetailsFromJson(
    Map<String, dynamic> json) {
  return _AttendanceEntriesAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$AttendanceEntriesAdditionalDetails {
  String? get entryAttendanceLogId => throw _privateConstructorUsedError;
  String? get exitAttendanceLogId => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendanceEntriesAdditionalDetailsCopyWith<
          AttendanceEntriesAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendanceEntriesAdditionalDetailsCopyWith<$Res> {
  factory $AttendanceEntriesAdditionalDetailsCopyWith(
          AttendanceEntriesAdditionalDetails value,
          $Res Function(AttendanceEntriesAdditionalDetails) then) =
      _$AttendanceEntriesAdditionalDetailsCopyWithImpl<$Res,
          AttendanceEntriesAdditionalDetails>;
  @useResult
  $Res call({String? entryAttendanceLogId, String? exitAttendanceLogId});
}

/// @nodoc
class _$AttendanceEntriesAdditionalDetailsCopyWithImpl<$Res,
        $Val extends AttendanceEntriesAdditionalDetails>
    implements $AttendanceEntriesAdditionalDetailsCopyWith<$Res> {
  _$AttendanceEntriesAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? entryAttendanceLogId = freezed,
    Object? exitAttendanceLogId = freezed,
  }) {
    return _then(_value.copyWith(
      entryAttendanceLogId: freezed == entryAttendanceLogId
          ? _value.entryAttendanceLogId
          : entryAttendanceLogId // ignore: cast_nullable_to_non_nullable
              as String?,
      exitAttendanceLogId: freezed == exitAttendanceLogId
          ? _value.exitAttendanceLogId
          : exitAttendanceLogId // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendanceEntriesAdditionalDetailsCopyWith<$Res>
    implements $AttendanceEntriesAdditionalDetailsCopyWith<$Res> {
  factory _$$_AttendanceEntriesAdditionalDetailsCopyWith(
          _$_AttendanceEntriesAdditionalDetails value,
          $Res Function(_$_AttendanceEntriesAdditionalDetails) then) =
      __$$_AttendanceEntriesAdditionalDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? entryAttendanceLogId, String? exitAttendanceLogId});
}

/// @nodoc
class __$$_AttendanceEntriesAdditionalDetailsCopyWithImpl<$Res>
    extends _$AttendanceEntriesAdditionalDetailsCopyWithImpl<$Res,
        _$_AttendanceEntriesAdditionalDetails>
    implements _$$_AttendanceEntriesAdditionalDetailsCopyWith<$Res> {
  __$$_AttendanceEntriesAdditionalDetailsCopyWithImpl(
      _$_AttendanceEntriesAdditionalDetails _value,
      $Res Function(_$_AttendanceEntriesAdditionalDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? entryAttendanceLogId = freezed,
    Object? exitAttendanceLogId = freezed,
  }) {
    return _then(_$_AttendanceEntriesAdditionalDetails(
      entryAttendanceLogId: freezed == entryAttendanceLogId
          ? _value.entryAttendanceLogId
          : entryAttendanceLogId // ignore: cast_nullable_to_non_nullable
              as String?,
      exitAttendanceLogId: freezed == exitAttendanceLogId
          ? _value.exitAttendanceLogId
          : exitAttendanceLogId // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceEntriesAdditionalDetails
    implements _AttendanceEntriesAdditionalDetails {
  const _$_AttendanceEntriesAdditionalDetails(
      {this.entryAttendanceLogId, this.exitAttendanceLogId});

  factory _$_AttendanceEntriesAdditionalDetails.fromJson(
          Map<String, dynamic> json) =>
      _$$_AttendanceEntriesAdditionalDetailsFromJson(json);

  @override
  final String? entryAttendanceLogId;
  @override
  final String? exitAttendanceLogId;

  @override
  String toString() {
    return 'AttendanceEntriesAdditionalDetails(entryAttendanceLogId: $entryAttendanceLogId, exitAttendanceLogId: $exitAttendanceLogId)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendanceEntriesAdditionalDetails &&
            (identical(other.entryAttendanceLogId, entryAttendanceLogId) ||
                other.entryAttendanceLogId == entryAttendanceLogId) &&
            (identical(other.exitAttendanceLogId, exitAttendanceLogId) ||
                other.exitAttendanceLogId == exitAttendanceLogId));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, entryAttendanceLogId, exitAttendanceLogId);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendanceEntriesAdditionalDetailsCopyWith<
          _$_AttendanceEntriesAdditionalDetails>
      get copyWith => __$$_AttendanceEntriesAdditionalDetailsCopyWithImpl<
          _$_AttendanceEntriesAdditionalDetails>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendanceEntriesAdditionalDetailsToJson(
      this,
    );
  }
}

abstract class _AttendanceEntriesAdditionalDetails
    implements AttendanceEntriesAdditionalDetails {
  const factory _AttendanceEntriesAdditionalDetails(
          {final String? entryAttendanceLogId,
          final String? exitAttendanceLogId}) =
      _$_AttendanceEntriesAdditionalDetails;

  factory _AttendanceEntriesAdditionalDetails.fromJson(
          Map<String, dynamic> json) =
      _$_AttendanceEntriesAdditionalDetails.fromJson;

  @override
  String? get entryAttendanceLogId;
  @override
  String? get exitAttendanceLogId;
  @override
  @JsonKey(ignore: true)
  _$$_AttendanceEntriesAdditionalDetailsCopyWith<
          _$_AttendanceEntriesAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
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
