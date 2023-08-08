// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'estimate_muster_roll_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#custom-getters-and-methods');

EstimateMusterRollsModel _$EstimateMusterRollsModelFromJson(
    Map<String, dynamic> json) {
  return _EstimateMusterRollsModel.fromJson(json);
}

/// @nodoc
mixin _$EstimateMusterRollsModel {
  @JsonKey(name: 'musterRolls')
  List<EstimateMusterRoll>? get musterRoll =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'count')
  int? get count => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateMusterRollsModelCopyWith<EstimateMusterRollsModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateMusterRollsModelCopyWith<$Res> {
  factory $EstimateMusterRollsModelCopyWith(EstimateMusterRollsModel value,
          $Res Function(EstimateMusterRollsModel) then) =
      _$EstimateMusterRollsModelCopyWithImpl<$Res, EstimateMusterRollsModel>;
  @useResult
  $Res call(
      {@JsonKey(name: 'musterRolls') List<EstimateMusterRoll>? musterRoll,
      @JsonKey(name: 'count') int? count});
}

/// @nodoc
class _$EstimateMusterRollsModelCopyWithImpl<$Res,
        $Val extends EstimateMusterRollsModel>
    implements $EstimateMusterRollsModelCopyWith<$Res> {
  _$EstimateMusterRollsModelCopyWithImpl(this._value, this._then);

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
              as List<EstimateMusterRoll>?,
      count: freezed == count
          ? _value.count
          : count // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_EstimateMusterRollsModelCopyWith<$Res>
    implements $EstimateMusterRollsModelCopyWith<$Res> {
  factory _$$_EstimateMusterRollsModelCopyWith(
          _$_EstimateMusterRollsModel value,
          $Res Function(_$_EstimateMusterRollsModel) then) =
      __$$_EstimateMusterRollsModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {@JsonKey(name: 'musterRolls') List<EstimateMusterRoll>? musterRoll,
      @JsonKey(name: 'count') int? count});
}

/// @nodoc
class __$$_EstimateMusterRollsModelCopyWithImpl<$Res>
    extends _$EstimateMusterRollsModelCopyWithImpl<$Res,
        _$_EstimateMusterRollsModel>
    implements _$$_EstimateMusterRollsModelCopyWith<$Res> {
  __$$_EstimateMusterRollsModelCopyWithImpl(_$_EstimateMusterRollsModel _value,
      $Res Function(_$_EstimateMusterRollsModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRoll = freezed,
    Object? count = freezed,
  }) {
    return _then(_$_EstimateMusterRollsModel(
      musterRoll: freezed == musterRoll
          ? _value._musterRoll
          : musterRoll // ignore: cast_nullable_to_non_nullable
              as List<EstimateMusterRoll>?,
      count: freezed == count
          ? _value.count
          : count // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_EstimateMusterRollsModel implements _EstimateMusterRollsModel {
  const _$_EstimateMusterRollsModel(
      {@JsonKey(name: 'musterRolls') final List<EstimateMusterRoll>? musterRoll,
      @JsonKey(name: 'count') this.count})
      : _musterRoll = musterRoll;

  factory _$_EstimateMusterRollsModel.fromJson(Map<String, dynamic> json) =>
      _$$_EstimateMusterRollsModelFromJson(json);

  final List<EstimateMusterRoll>? _musterRoll;
  @override
  @JsonKey(name: 'musterRolls')
  List<EstimateMusterRoll>? get musterRoll {
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
    return 'EstimateMusterRollsModel(musterRoll: $musterRoll, count: $count)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_EstimateMusterRollsModel &&
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
  _$$_EstimateMusterRollsModelCopyWith<_$_EstimateMusterRollsModel>
      get copyWith => __$$_EstimateMusterRollsModelCopyWithImpl<
          _$_EstimateMusterRollsModel>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_EstimateMusterRollsModelToJson(
      this,
    );
  }
}

abstract class _EstimateMusterRollsModel implements EstimateMusterRollsModel {
  const factory _EstimateMusterRollsModel(
      {@JsonKey(name: 'musterRolls') final List<EstimateMusterRoll>? musterRoll,
      @JsonKey(name: 'count') final int? count}) = _$_EstimateMusterRollsModel;

  factory _EstimateMusterRollsModel.fromJson(Map<String, dynamic> json) =
      _$_EstimateMusterRollsModel.fromJson;

  @override
  @JsonKey(name: 'musterRolls')
  List<EstimateMusterRoll>? get musterRoll;
  @override
  @JsonKey(name: 'count')
  int? get count;
  @override
  @JsonKey(ignore: true)
  _$$_EstimateMusterRollsModelCopyWith<_$_EstimateMusterRollsModel>
      get copyWith => throw _privateConstructorUsedError;
}

EstimateMusterRoll _$EstimateMusterRollFromJson(Map<String, dynamic> json) {
  return _EstimateMusterRoll.fromJson(json);
}

/// @nodoc
mixin _$EstimateMusterRoll {
  String? get id => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get musterRollNumber => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  String? get status => throw _privateConstructorUsedError;
  String? get musterRollStatus => throw _privateConstructorUsedError;
  int? get startDate => throw _privateConstructorUsedError;
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'individualEntries')
  List<EstimateIndividualEntries>? get individualEntries =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get musterAuditDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateMusterRollCopyWith<EstimateMusterRoll> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateMusterRollCopyWith<$Res> {
  factory $EstimateMusterRollCopyWith(
          EstimateMusterRoll value, $Res Function(EstimateMusterRoll) then) =
      _$EstimateMusterRollCopyWithImpl<$Res, EstimateMusterRoll>;
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<EstimateIndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          MusterAdditionalDetails? musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          AuditDetails? musterAuditDetails});

  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails;
  $AuditDetailsCopyWith<$Res>? get musterAuditDetails;
}

/// @nodoc
class _$EstimateMusterRollCopyWithImpl<$Res, $Val extends EstimateMusterRoll>
    implements $EstimateMusterRollCopyWith<$Res> {
  _$EstimateMusterRollCopyWithImpl(this._value, this._then);

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
              as List<EstimateIndividualEntries>?,
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
abstract class _$$_EstimateMusterRollCopyWith<$Res>
    implements $EstimateMusterRollCopyWith<$Res> {
  factory _$$_EstimateMusterRollCopyWith(_$_EstimateMusterRoll value,
          $Res Function(_$_EstimateMusterRoll) then) =
      __$$_EstimateMusterRollCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<EstimateIndividualEntries>? individualEntries,
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
class __$$_EstimateMusterRollCopyWithImpl<$Res>
    extends _$EstimateMusterRollCopyWithImpl<$Res, _$_EstimateMusterRoll>
    implements _$$_EstimateMusterRollCopyWith<$Res> {
  __$$_EstimateMusterRollCopyWithImpl(
      _$_EstimateMusterRoll _value, $Res Function(_$_EstimateMusterRoll) _then)
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
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? individualEntries = freezed,
    Object? musterAdditionalDetails = freezed,
    Object? musterAuditDetails = freezed,
  }) {
    return _then(_$_EstimateMusterRoll(
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
              as List<EstimateIndividualEntries>?,
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
class _$_EstimateMusterRoll implements _EstimateMusterRoll {
  const _$_EstimateMusterRoll(
      {this.id,
      required this.tenantId,
      this.musterRollNumber,
      this.registerId,
      this.status,
      this.musterRollStatus,
      this.startDate,
      this.endDate,
      @JsonKey(name: 'individualEntries')
          final List<EstimateIndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          this.musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          this.musterAuditDetails})
      : _individualEntries = individualEntries;

  factory _$_EstimateMusterRoll.fromJson(Map<String, dynamic> json) =>
      _$$_EstimateMusterRollFromJson(json);

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
  final int? startDate;
  @override
  final int? endDate;
  final List<EstimateIndividualEntries>? _individualEntries;
  @override
  @JsonKey(name: 'individualEntries')
  List<EstimateIndividualEntries>? get individualEntries {
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
    return 'EstimateMusterRoll(id: $id, tenantId: $tenantId, musterRollNumber: $musterRollNumber, registerId: $registerId, status: $status, musterRollStatus: $musterRollStatus, startDate: $startDate, endDate: $endDate, individualEntries: $individualEntries, musterAdditionalDetails: $musterAdditionalDetails, musterAuditDetails: $musterAuditDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_EstimateMusterRoll &&
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
      startDate,
      endDate,
      const DeepCollectionEquality().hash(_individualEntries),
      musterAdditionalDetails,
      musterAuditDetails);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_EstimateMusterRollCopyWith<_$_EstimateMusterRoll> get copyWith =>
      __$$_EstimateMusterRollCopyWithImpl<_$_EstimateMusterRoll>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_EstimateMusterRollToJson(
      this,
    );
  }
}

abstract class _EstimateMusterRoll implements EstimateMusterRoll {
  const factory _EstimateMusterRoll(
      {final String? id,
      required final String tenantId,
      final String? musterRollNumber,
      final String? registerId,
      final String? status,
      final String? musterRollStatus,
      final int? startDate,
      final int? endDate,
      @JsonKey(name: 'individualEntries')
          final List<EstimateIndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          final MusterAdditionalDetails? musterAdditionalDetails,
      @JsonKey(name: 'auditDetails')
          final AuditDetails? musterAuditDetails}) = _$_EstimateMusterRoll;

  factory _EstimateMusterRoll.fromJson(Map<String, dynamic> json) =
      _$_EstimateMusterRoll.fromJson;

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
  int? get startDate;
  @override
  int? get endDate;
  @override
  @JsonKey(name: 'individualEntries')
  List<EstimateIndividualEntries>? get individualEntries;
  @override
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get musterAuditDetails;
  @override
  @JsonKey(ignore: true)
  _$$_EstimateMusterRollCopyWith<_$_EstimateMusterRoll> get copyWith =>
      throw _privateConstructorUsedError;
}

EstimateIndividualEntries _$EstimateIndividualEntriesFromJson(
    Map<String, dynamic> json) {
  return _EstimateIndividualEntries.fromJson(json);
}

/// @nodoc
mixin _$EstimateIndividualEntries {
  String? get id => throw _privateConstructorUsedError;
  String? get individualId => throw _privateConstructorUsedError;
  double? get totalAttendance => throw _privateConstructorUsedError;
  @JsonKey(name: 'attendanceEntries')
  List<AttendanceEntries>? get attendanceEntries =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  EstimateMusterIndividualAdditionalDetails?
      get musterIndividualAdditionalDetails =>
          throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateIndividualEntriesCopyWith<EstimateIndividualEntries> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateIndividualEntriesCopyWith<$Res> {
  factory $EstimateIndividualEntriesCopyWith(EstimateIndividualEntries value,
          $Res Function(EstimateIndividualEntries) then) =
      _$EstimateIndividualEntriesCopyWithImpl<$Res, EstimateIndividualEntries>;
  @useResult
  $Res call(
      {String? id,
      String? individualId,
      double? totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          EstimateMusterIndividualAdditionalDetails?
              musterIndividualAdditionalDetails});

  $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails;
}

/// @nodoc
class _$EstimateIndividualEntriesCopyWithImpl<$Res,
        $Val extends EstimateIndividualEntries>
    implements $EstimateIndividualEntriesCopyWith<$Res> {
  _$EstimateIndividualEntriesCopyWithImpl(this._value, this._then);

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
              as EstimateMusterIndividualAdditionalDetails?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails {
    if (_value.musterIndividualAdditionalDetails == null) {
      return null;
    }

    return $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res>(
        _value.musterIndividualAdditionalDetails!, (value) {
      return _then(
          _value.copyWith(musterIndividualAdditionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$_EstimateIndividualEntriesCopyWith<$Res>
    implements $EstimateIndividualEntriesCopyWith<$Res> {
  factory _$$_EstimateIndividualEntriesCopyWith(
          _$_EstimateIndividualEntries value,
          $Res Function(_$_EstimateIndividualEntries) then) =
      __$$_EstimateIndividualEntriesCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? individualId,
      double? totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          EstimateMusterIndividualAdditionalDetails?
              musterIndividualAdditionalDetails});

  @override
  $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res>?
      get musterIndividualAdditionalDetails;
}

/// @nodoc
class __$$_EstimateIndividualEntriesCopyWithImpl<$Res>
    extends _$EstimateIndividualEntriesCopyWithImpl<$Res,
        _$_EstimateIndividualEntries>
    implements _$$_EstimateIndividualEntriesCopyWith<$Res> {
  __$$_EstimateIndividualEntriesCopyWithImpl(
      _$_EstimateIndividualEntries _value,
      $Res Function(_$_EstimateIndividualEntries) _then)
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
    return _then(_$_EstimateIndividualEntries(
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
              as EstimateMusterIndividualAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_EstimateIndividualEntries implements _EstimateIndividualEntries {
  const _$_EstimateIndividualEntries(
      {this.id,
      this.individualId,
      this.totalAttendance,
      @JsonKey(name: 'attendanceEntries')
          final List<AttendanceEntries>? attendanceEntries,
      @JsonKey(name: 'additionalDetails')
          this.musterIndividualAdditionalDetails})
      : _attendanceEntries = attendanceEntries;

  factory _$_EstimateIndividualEntries.fromJson(Map<String, dynamic> json) =>
      _$$_EstimateIndividualEntriesFromJson(json);

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
  final EstimateMusterIndividualAdditionalDetails?
      musterIndividualAdditionalDetails;

  @override
  String toString() {
    return 'EstimateIndividualEntries(id: $id, individualId: $individualId, totalAttendance: $totalAttendance, attendanceEntries: $attendanceEntries, musterIndividualAdditionalDetails: $musterIndividualAdditionalDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_EstimateIndividualEntries &&
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
  _$$_EstimateIndividualEntriesCopyWith<_$_EstimateIndividualEntries>
      get copyWith => __$$_EstimateIndividualEntriesCopyWithImpl<
          _$_EstimateIndividualEntries>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_EstimateIndividualEntriesToJson(
      this,
    );
  }
}

abstract class _EstimateIndividualEntries implements EstimateIndividualEntries {
  const factory _EstimateIndividualEntries(
          {final String? id,
          final String? individualId,
          final double? totalAttendance,
          @JsonKey(name: 'attendanceEntries')
              final List<AttendanceEntries>? attendanceEntries,
          @JsonKey(name: 'additionalDetails')
              final EstimateMusterIndividualAdditionalDetails?
                  musterIndividualAdditionalDetails}) =
      _$_EstimateIndividualEntries;

  factory _EstimateIndividualEntries.fromJson(Map<String, dynamic> json) =
      _$_EstimateIndividualEntries.fromJson;

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
  EstimateMusterIndividualAdditionalDetails?
      get musterIndividualAdditionalDetails;
  @override
  @JsonKey(ignore: true)
  _$$_EstimateIndividualEntriesCopyWith<_$_EstimateIndividualEntries>
      get copyWith => throw _privateConstructorUsedError;
}

EstimateMusterIndividualAdditionalDetails
    _$EstimateMusterIndividualAdditionalDetailsFromJson(
        Map<String, dynamic> json) {
  return _EstimateMusterIndividualAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$EstimateMusterIndividualAdditionalDetails {
  String? get userName => throw _privateConstructorUsedError;
  String? get fatherName => throw _privateConstructorUsedError;
  String? get gender => throw _privateConstructorUsedError;
  String? get aadharNumber => throw _privateConstructorUsedError;
  String? get bankDetails => throw _privateConstructorUsedError;
  String? get userId => throw _privateConstructorUsedError;
  List<String>? get skillCode => throw _privateConstructorUsedError;
  String? get accountHolderName => throw _privateConstructorUsedError;
  String? get accountType => throw _privateConstructorUsedError;
  String? get skillValue => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $EstimateMusterIndividualAdditionalDetailsCopyWith<
          EstimateMusterIndividualAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res> {
  factory $EstimateMusterIndividualAdditionalDetailsCopyWith(
          EstimateMusterIndividualAdditionalDetails value,
          $Res Function(EstimateMusterIndividualAdditionalDetails) then) =
      _$EstimateMusterIndividualAdditionalDetailsCopyWithImpl<$Res,
          EstimateMusterIndividualAdditionalDetails>;
  @useResult
  $Res call(
      {String? userName,
      String? fatherName,
      String? gender,
      String? aadharNumber,
      String? bankDetails,
      String? userId,
      List<String>? skillCode,
      String? accountHolderName,
      String? accountType,
      String? skillValue});
}

/// @nodoc
class _$EstimateMusterIndividualAdditionalDetailsCopyWithImpl<$Res,
        $Val extends EstimateMusterIndividualAdditionalDetails>
    implements $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res> {
  _$EstimateMusterIndividualAdditionalDetailsCopyWithImpl(
      this._value, this._then);

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
    Object? skillCode = freezed,
    Object? accountHolderName = freezed,
    Object? accountType = freezed,
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
      skillCode: freezed == skillCode
          ? _value.skillCode
          : skillCode // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      accountHolderName: freezed == accountHolderName
          ? _value.accountHolderName
          : accountHolderName // ignore: cast_nullable_to_non_nullable
              as String?,
      accountType: freezed == accountType
          ? _value.accountType
          : accountType // ignore: cast_nullable_to_non_nullable
              as String?,
      skillValue: freezed == skillValue
          ? _value.skillValue
          : skillValue // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_EstimateMusterIndividualAdditionalDetailsCopyWith<$Res>
    implements $EstimateMusterIndividualAdditionalDetailsCopyWith<$Res> {
  factory _$$_EstimateMusterIndividualAdditionalDetailsCopyWith(
          _$_EstimateMusterIndividualAdditionalDetails value,
          $Res Function(_$_EstimateMusterIndividualAdditionalDetails) then) =
      __$$_EstimateMusterIndividualAdditionalDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? userName,
      String? fatherName,
      String? gender,
      String? aadharNumber,
      String? bankDetails,
      String? userId,
      List<String>? skillCode,
      String? accountHolderName,
      String? accountType,
      String? skillValue});
}

/// @nodoc
class __$$_EstimateMusterIndividualAdditionalDetailsCopyWithImpl<$Res>
    extends _$EstimateMusterIndividualAdditionalDetailsCopyWithImpl<$Res,
        _$_EstimateMusterIndividualAdditionalDetails>
    implements _$$_EstimateMusterIndividualAdditionalDetailsCopyWith<$Res> {
  __$$_EstimateMusterIndividualAdditionalDetailsCopyWithImpl(
      _$_EstimateMusterIndividualAdditionalDetails _value,
      $Res Function(_$_EstimateMusterIndividualAdditionalDetails) _then)
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
    Object? skillCode = freezed,
    Object? accountHolderName = freezed,
    Object? accountType = freezed,
    Object? skillValue = freezed,
  }) {
    return _then(_$_EstimateMusterIndividualAdditionalDetails(
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
      skillCode: freezed == skillCode
          ? _value._skillCode
          : skillCode // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      accountHolderName: freezed == accountHolderName
          ? _value.accountHolderName
          : accountHolderName // ignore: cast_nullable_to_non_nullable
              as String?,
      accountType: freezed == accountType
          ? _value.accountType
          : accountType // ignore: cast_nullable_to_non_nullable
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
class _$_EstimateMusterIndividualAdditionalDetails
    implements _EstimateMusterIndividualAdditionalDetails {
  const _$_EstimateMusterIndividualAdditionalDetails(
      {this.userName,
      this.fatherName,
      this.gender,
      this.aadharNumber,
      this.bankDetails,
      this.userId,
      final List<String>? skillCode,
      this.accountHolderName,
      this.accountType,
      this.skillValue})
      : _skillCode = skillCode;

  factory _$_EstimateMusterIndividualAdditionalDetails.fromJson(
          Map<String, dynamic> json) =>
      _$$_EstimateMusterIndividualAdditionalDetailsFromJson(json);

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
  final List<String>? _skillCode;
  @override
  List<String>? get skillCode {
    final value = _skillCode;
    if (value == null) return null;
    if (_skillCode is EqualUnmodifiableListView) return _skillCode;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final String? accountHolderName;
  @override
  final String? accountType;
  @override
  final String? skillValue;

  @override
  String toString() {
    return 'EstimateMusterIndividualAdditionalDetails(userName: $userName, fatherName: $fatherName, gender: $gender, aadharNumber: $aadharNumber, bankDetails: $bankDetails, userId: $userId, skillCode: $skillCode, accountHolderName: $accountHolderName, accountType: $accountType, skillValue: $skillValue)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_EstimateMusterIndividualAdditionalDetails &&
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
            const DeepCollectionEquality()
                .equals(other._skillCode, _skillCode) &&
            (identical(other.accountHolderName, accountHolderName) ||
                other.accountHolderName == accountHolderName) &&
            (identical(other.accountType, accountType) ||
                other.accountType == accountType) &&
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
      const DeepCollectionEquality().hash(_skillCode),
      accountHolderName,
      accountType,
      skillValue);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_EstimateMusterIndividualAdditionalDetailsCopyWith<
          _$_EstimateMusterIndividualAdditionalDetails>
      get copyWith =>
          __$$_EstimateMusterIndividualAdditionalDetailsCopyWithImpl<
              _$_EstimateMusterIndividualAdditionalDetails>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_EstimateMusterIndividualAdditionalDetailsToJson(
      this,
    );
  }
}

abstract class _EstimateMusterIndividualAdditionalDetails
    implements EstimateMusterIndividualAdditionalDetails {
  const factory _EstimateMusterIndividualAdditionalDetails(
      {final String? userName,
      final String? fatherName,
      final String? gender,
      final String? aadharNumber,
      final String? bankDetails,
      final String? userId,
      final List<String>? skillCode,
      final String? accountHolderName,
      final String? accountType,
      final String? skillValue}) = _$_EstimateMusterIndividualAdditionalDetails;

  factory _EstimateMusterIndividualAdditionalDetails.fromJson(
          Map<String, dynamic> json) =
      _$_EstimateMusterIndividualAdditionalDetails.fromJson;

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
  List<String>? get skillCode;
  @override
  String? get accountHolderName;
  @override
  String? get accountType;
  @override
  String? get skillValue;
  @override
  @JsonKey(ignore: true)
  _$$_EstimateMusterIndividualAdditionalDetailsCopyWith<
          _$_EstimateMusterIndividualAdditionalDetails>
      get copyWith => throw _privateConstructorUsedError;
}
