// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target

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
  String? get tenantId => throw _privateConstructorUsedError;
  String? get musterRollNumber => throw _privateConstructorUsedError;
  String? get registerId => throw _privateConstructorUsedError;
  String? get status => throw _privateConstructorUsedError;
  String? get musterRollStatus => throw _privateConstructorUsedError;
  int? get startDate => throw _privateConstructorUsedError;
  int? get endDate => throw _privateConstructorUsedError;
  @JsonKey(name: 'individualEntries')
  List<IndividualEntries>? get individualEntries =>
      throw _privateConstructorUsedError;
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails =>
      throw _privateConstructorUsedError;

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
      String? tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          MusterAdditionalDetails? musterAdditionalDetails});

  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails;
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
    Object? tenantId = freezed,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? individualEntries = freezed,
    Object? musterAdditionalDetails = freezed,
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
              as List<IndividualEntries>?,
      musterAdditionalDetails: freezed == musterAdditionalDetails
          ? _value.musterAdditionalDetails
          : musterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterAdditionalDetails?,
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
      String? tenantId,
      String? musterRollNumber,
      String? registerId,
      String? status,
      String? musterRollStatus,
      int? startDate,
      int? endDate,
      @JsonKey(name: 'individualEntries')
          List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          MusterAdditionalDetails? musterAdditionalDetails});

  @override
  $MusterAdditionalDetailsCopyWith<$Res>? get musterAdditionalDetails;
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
    Object? tenantId = freezed,
    Object? musterRollNumber = freezed,
    Object? registerId = freezed,
    Object? status = freezed,
    Object? musterRollStatus = freezed,
    Object? startDate = freezed,
    Object? endDate = freezed,
    Object? individualEntries = freezed,
    Object? musterAdditionalDetails = freezed,
  }) {
    return _then(_$_MusterRoll(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: freezed == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String?,
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
              as List<IndividualEntries>?,
      musterAdditionalDetails: freezed == musterAdditionalDetails
          ? _value.musterAdditionalDetails
          : musterAdditionalDetails // ignore: cast_nullable_to_non_nullable
              as MusterAdditionalDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterRoll implements _MusterRoll {
  const _$_MusterRoll(
      {this.id,
      this.tenantId,
      this.musterRollNumber,
      this.registerId,
      this.status,
      this.musterRollStatus,
      this.startDate,
      this.endDate,
      @JsonKey(name: 'individualEntries')
          final List<IndividualEntries>? individualEntries,
      @JsonKey(name: 'additionalDetails')
          this.musterAdditionalDetails})
      : _individualEntries = individualEntries;

  factory _$_MusterRoll.fromJson(Map<String, dynamic> json) =>
      _$$_MusterRollFromJson(json);

  @override
  final String? id;
  @override
  final String? tenantId;
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
  final List<IndividualEntries>? _individualEntries;
  @override
  @JsonKey(name: 'individualEntries')
  List<IndividualEntries>? get individualEntries {
    final value = _individualEntries;
    if (value == null) return null;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey(name: 'additionalDetails')
  final MusterAdditionalDetails? musterAdditionalDetails;

  @override
  String toString() {
    return 'MusterRoll(id: $id, tenantId: $tenantId, musterRollNumber: $musterRollNumber, registerId: $registerId, status: $status, musterRollStatus: $musterRollStatus, startDate: $startDate, endDate: $endDate, individualEntries: $individualEntries, musterAdditionalDetails: $musterAdditionalDetails)';
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
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.endDate, endDate) || other.endDate == endDate) &&
            const DeepCollectionEquality()
                .equals(other._individualEntries, _individualEntries) &&
            (identical(
                    other.musterAdditionalDetails, musterAdditionalDetails) ||
                other.musterAdditionalDetails == musterAdditionalDetails));
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
      musterAdditionalDetails);

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
          final String? tenantId,
          final String? musterRollNumber,
          final String? registerId,
          final String? status,
          final String? musterRollStatus,
          final int? startDate,
          final int? endDate,
          @JsonKey(name: 'individualEntries')
              final List<IndividualEntries>? individualEntries,
          @JsonKey(name: 'additionalDetails')
              final MusterAdditionalDetails? musterAdditionalDetails}) =
      _$_MusterRoll;

  factory _MusterRoll.fromJson(Map<String, dynamic> json) =
      _$_MusterRoll.fromJson;

  @override
  String? get id;
  @override
  String? get tenantId;
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
  List<IndividualEntries>? get individualEntries;
  @override
  @JsonKey(name: 'additionalDetails')
  MusterAdditionalDetails? get musterAdditionalDetails;
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
          List<AttendanceEntries>? attendanceEntries});
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
    ) as $Val);
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
          List<AttendanceEntries>? attendanceEntries});
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
          final List<AttendanceEntries>? attendanceEntries})
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
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'IndividualEntries(id: $id, individualId: $individualId, totalAttendance: $totalAttendance, attendanceEntries: $attendanceEntries)';
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
                .equals(other._attendanceEntries, _attendanceEntries));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, individualId,
      totalAttendance, const DeepCollectionEquality().hash(_attendanceEntries));

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
              final List<AttendanceEntries>? attendanceEntries}) =
      _$_IndividualEntries;

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
      String? orgName});
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
      String? orgName});
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
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_MusterAdditionalDetails implements _MusterAdditionalDetails {
  const _$_MusterAdditionalDetails(
      {this.attendanceRegisterName, this.attendanceRegisterNo, this.orgName});

  factory _$_MusterAdditionalDetails.fromJson(Map<String, dynamic> json) =>
      _$$_MusterAdditionalDetailsFromJson(json);

  @override
  final String? attendanceRegisterName;
  @override
  final String? attendanceRegisterNo;
  @override
  final String? orgName;

  @override
  String toString() {
    return 'MusterAdditionalDetails(attendanceRegisterName: $attendanceRegisterName, attendanceRegisterNo: $attendanceRegisterNo, orgName: $orgName)';
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
            (identical(other.orgName, orgName) || other.orgName == orgName));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, attendanceRegisterName, attendanceRegisterNo, orgName);

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
      final String? orgName}) = _$_MusterAdditionalDetails;

  factory _MusterAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$_MusterAdditionalDetails.fromJson;

  @override
  String? get attendanceRegisterName;
  @override
  String? get attendanceRegisterNo;
  @override
  String? get orgName;
  @override
  @JsonKey(ignore: true)
  _$$_MusterAdditionalDetailsCopyWith<_$_MusterAdditionalDetails>
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
      {String? id, double? attendance, int? time, AuditDetails? auditDetails});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
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
      {String? id, double? attendance, int? time, AuditDetails? auditDetails});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
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
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendanceEntries implements _AttendanceEntries {
  const _$_AttendanceEntries(
      {this.id, this.attendance, this.time, this.auditDetails});

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
  String toString() {
    return 'AttendanceEntries(id: $id, attendance: $attendance, time: $time, auditDetails: $auditDetails)';
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
                other.auditDetails == auditDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode =>
      Object.hash(runtimeType, id, attendance, time, auditDetails);

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
      final AuditDetails? auditDetails}) = _$_AttendanceEntries;

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
  @JsonKey(ignore: true)
  _$$_AttendanceEntriesCopyWith<_$_AttendanceEntries> get copyWith =>
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
