// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

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
    if (_attendanceRegister is EqualUnmodifiableListView)
      return _attendanceRegister;
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

AttendeeTrackListModel _$AttendeeTrackListModelFromJson(
    Map<String, dynamic> json) {
  return _AttendeeTrackListModel.fromJson(json);
}

/// @nodoc
mixin _$AttendeeTrackListModel {
  @JsonKey(ignore: true)
  List<AttendeesTrackList>? get attendeeList =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeeTrackListModelCopyWith<AttendeeTrackListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeTrackListModelCopyWith<$Res> {
  factory $AttendeeTrackListModelCopyWith(AttendeeTrackListModel value,
          $Res Function(AttendeeTrackListModel) then) =
      _$AttendeeTrackListModelCopyWithImpl<$Res, AttendeeTrackListModel>;
  @useResult
  $Res call({@JsonKey(ignore: true) List<AttendeesTrackList>? attendeeList});
}

/// @nodoc
class _$AttendeeTrackListModelCopyWithImpl<$Res,
        $Val extends AttendeeTrackListModel>
    implements $AttendeeTrackListModelCopyWith<$Res> {
  _$AttendeeTrackListModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendeeList = freezed,
  }) {
    return _then(_value.copyWith(
      attendeeList: freezed == attendeeList
          ? _value.attendeeList
          : attendeeList // ignore: cast_nullable_to_non_nullable
              as List<AttendeesTrackList>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$_AttendeeTrackListModelCopyWith<$Res>
    implements $AttendeeTrackListModelCopyWith<$Res> {
  factory _$$_AttendeeTrackListModelCopyWith(_$_AttendeeTrackListModel value,
          $Res Function(_$_AttendeeTrackListModel) then) =
      __$$_AttendeeTrackListModelCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({@JsonKey(ignore: true) List<AttendeesTrackList>? attendeeList});
}

/// @nodoc
class __$$_AttendeeTrackListModelCopyWithImpl<$Res>
    extends _$AttendeeTrackListModelCopyWithImpl<$Res,
        _$_AttendeeTrackListModel>
    implements _$$_AttendeeTrackListModelCopyWith<$Res> {
  __$$_AttendeeTrackListModelCopyWithImpl(_$_AttendeeTrackListModel _value,
      $Res Function(_$_AttendeeTrackListModel) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? attendeeList = freezed,
  }) {
    return _then(_$_AttendeeTrackListModel(
      attendeeList: freezed == attendeeList
          ? _value._attendeeList
          : attendeeList // ignore: cast_nullable_to_non_nullable
              as List<AttendeesTrackList>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendeeTrackListModel implements _AttendeeTrackListModel {
  const _$_AttendeeTrackListModel(
      {@JsonKey(ignore: true)
          final List<AttendeesTrackList>? attendeeList = const []})
      : _attendeeList = attendeeList;

  factory _$_AttendeeTrackListModel.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeeTrackListModelFromJson(json);

  final List<AttendeesTrackList>? _attendeeList;
  @override
  @JsonKey(ignore: true)
  List<AttendeesTrackList>? get attendeeList {
    final value = _attendeeList;
    if (value == null) return null;
    if (_attendeeList is EqualUnmodifiableListView) return _attendeeList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'AttendeeTrackListModel(attendeeList: $attendeeList)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeeTrackListModel &&
            const DeepCollectionEquality()
                .equals(other._attendeeList, _attendeeList));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_attendeeList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendeeTrackListModelCopyWith<_$_AttendeeTrackListModel> get copyWith =>
      __$$_AttendeeTrackListModelCopyWithImpl<_$_AttendeeTrackListModel>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendeeTrackListModelToJson(
      this,
    );
  }
}

abstract class _AttendeeTrackListModel implements AttendeeTrackListModel {
  const factory _AttendeeTrackListModel(
          {@JsonKey(ignore: true)
              final List<AttendeesTrackList>? attendeeList}) =
      _$_AttendeeTrackListModel;

  factory _AttendeeTrackListModel.fromJson(Map<String, dynamic> json) =
      _$_AttendeeTrackListModel.fromJson;

  @override
  @JsonKey(ignore: true)
  List<AttendeesTrackList>? get attendeeList;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeeTrackListModelCopyWith<_$_AttendeeTrackListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendeesTrackList _$AttendeesTrackListFromJson(Map<String, dynamic> json) {
  return _AttendeesTrackList.fromJson(json);
}

/// @nodoc
mixin _$AttendeesTrackList {
  String? get name => throw _privateConstructorUsedError;
  String? get aadhaar => throw _privateConstructorUsedError;
  String? get gender => throw _privateConstructorUsedError;
  String? get individualGaurdianName => throw _privateConstructorUsedError;
  String? get individualId => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get skill => throw _privateConstructorUsedError;
  String? get monEntryId => throw _privateConstructorUsedError;
  String? get monExitId => throw _privateConstructorUsedError;
  double? get monIndex => throw _privateConstructorUsedError;
  String? get tueEntryId => throw _privateConstructorUsedError;
  String? get tueExitId => throw _privateConstructorUsedError;
  double? get tueIndex => throw _privateConstructorUsedError;
  String? get wedEntryId => throw _privateConstructorUsedError;
  String? get wedExitId => throw _privateConstructorUsedError;
  double? get wedIndex => throw _privateConstructorUsedError;
  String? get thuEntryId => throw _privateConstructorUsedError;
  String? get thuExitId => throw _privateConstructorUsedError;
  double? get thursIndex => throw _privateConstructorUsedError;
  String? get friEntryId => throw _privateConstructorUsedError;
  String? get friExitId => throw _privateConstructorUsedError;
  List<String>? get skillCodeList => throw _privateConstructorUsedError;
  double? get friIndex => throw _privateConstructorUsedError;
  String? get satEntryId => throw _privateConstructorUsedError;
  String? get satExitId => throw _privateConstructorUsedError;
  double? get satIndex => throw _privateConstructorUsedError;
  String? get sunEntryId => throw _privateConstructorUsedError;
  String? get sunExitId => throw _privateConstructorUsedError;
  double? get sunIndex => throw _privateConstructorUsedError;
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeesTrackListCopyWith<AttendeesTrackList> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeesTrackListCopyWith<$Res> {
  factory $AttendeesTrackListCopyWith(
          AttendeesTrackList value, $Res Function(AttendeesTrackList) then) =
      _$AttendeesTrackListCopyWithImpl<$Res, AttendeesTrackList>;
  @useResult
  $Res call(
      {String? name,
      String? aadhaar,
      String? gender,
      String? individualGaurdianName,
      String? individualId,
      String? id,
      String? skill,
      String? monEntryId,
      String? monExitId,
      double? monIndex,
      String? tueEntryId,
      String? tueExitId,
      double? tueIndex,
      String? wedEntryId,
      String? wedExitId,
      double? wedIndex,
      String? thuEntryId,
      String? thuExitId,
      double? thursIndex,
      String? friEntryId,
      String? friExitId,
      List<String>? skillCodeList,
      double? friIndex,
      String? satEntryId,
      String? satExitId,
      double? satIndex,
      String? sunEntryId,
      String? sunExitId,
      double? sunIndex,
      @JsonKey(name: 'auditDetails') AuditDetails? auditDetails});

  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class _$AttendeesTrackListCopyWithImpl<$Res, $Val extends AttendeesTrackList>
    implements $AttendeesTrackListCopyWith<$Res> {
  _$AttendeesTrackListCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? aadhaar = freezed,
    Object? gender = freezed,
    Object? individualGaurdianName = freezed,
    Object? individualId = freezed,
    Object? id = freezed,
    Object? skill = freezed,
    Object? monEntryId = freezed,
    Object? monExitId = freezed,
    Object? monIndex = freezed,
    Object? tueEntryId = freezed,
    Object? tueExitId = freezed,
    Object? tueIndex = freezed,
    Object? wedEntryId = freezed,
    Object? wedExitId = freezed,
    Object? wedIndex = freezed,
    Object? thuEntryId = freezed,
    Object? thuExitId = freezed,
    Object? thursIndex = freezed,
    Object? friEntryId = freezed,
    Object? friExitId = freezed,
    Object? skillCodeList = freezed,
    Object? friIndex = freezed,
    Object? satEntryId = freezed,
    Object? satExitId = freezed,
    Object? satIndex = freezed,
    Object? sunEntryId = freezed,
    Object? sunExitId = freezed,
    Object? sunIndex = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_value.copyWith(
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      aadhaar: freezed == aadhaar
          ? _value.aadhaar
          : aadhaar // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      individualGaurdianName: freezed == individualGaurdianName
          ? _value.individualGaurdianName
          : individualGaurdianName // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      skill: freezed == skill
          ? _value.skill
          : skill // ignore: cast_nullable_to_non_nullable
              as String?,
      monEntryId: freezed == monEntryId
          ? _value.monEntryId
          : monEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      monExitId: freezed == monExitId
          ? _value.monExitId
          : monExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      monIndex: freezed == monIndex
          ? _value.monIndex
          : monIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      tueEntryId: freezed == tueEntryId
          ? _value.tueEntryId
          : tueEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      tueExitId: freezed == tueExitId
          ? _value.tueExitId
          : tueExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      tueIndex: freezed == tueIndex
          ? _value.tueIndex
          : tueIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      wedEntryId: freezed == wedEntryId
          ? _value.wedEntryId
          : wedEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      wedExitId: freezed == wedExitId
          ? _value.wedExitId
          : wedExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      wedIndex: freezed == wedIndex
          ? _value.wedIndex
          : wedIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      thuEntryId: freezed == thuEntryId
          ? _value.thuEntryId
          : thuEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      thuExitId: freezed == thuExitId
          ? _value.thuExitId
          : thuExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      thursIndex: freezed == thursIndex
          ? _value.thursIndex
          : thursIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      friEntryId: freezed == friEntryId
          ? _value.friEntryId
          : friEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      friExitId: freezed == friExitId
          ? _value.friExitId
          : friExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      skillCodeList: freezed == skillCodeList
          ? _value.skillCodeList
          : skillCodeList // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      friIndex: freezed == friIndex
          ? _value.friIndex
          : friIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      satEntryId: freezed == satEntryId
          ? _value.satEntryId
          : satEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      satExitId: freezed == satExitId
          ? _value.satExitId
          : satExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      satIndex: freezed == satIndex
          ? _value.satIndex
          : satIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      sunEntryId: freezed == sunEntryId
          ? _value.sunEntryId
          : sunEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      sunExitId: freezed == sunExitId
          ? _value.sunExitId
          : sunExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      sunIndex: freezed == sunIndex
          ? _value.sunIndex
          : sunIndex // ignore: cast_nullable_to_non_nullable
              as double?,
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
abstract class _$$_AttendeesTrackListCopyWith<$Res>
    implements $AttendeesTrackListCopyWith<$Res> {
  factory _$$_AttendeesTrackListCopyWith(_$_AttendeesTrackList value,
          $Res Function(_$_AttendeesTrackList) then) =
      __$$_AttendeesTrackListCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? name,
      String? aadhaar,
      String? gender,
      String? individualGaurdianName,
      String? individualId,
      String? id,
      String? skill,
      String? monEntryId,
      String? monExitId,
      double? monIndex,
      String? tueEntryId,
      String? tueExitId,
      double? tueIndex,
      String? wedEntryId,
      String? wedExitId,
      double? wedIndex,
      String? thuEntryId,
      String? thuExitId,
      double? thursIndex,
      String? friEntryId,
      String? friExitId,
      List<String>? skillCodeList,
      double? friIndex,
      String? satEntryId,
      String? satExitId,
      double? satIndex,
      String? sunEntryId,
      String? sunExitId,
      double? sunIndex,
      @JsonKey(name: 'auditDetails') AuditDetails? auditDetails});

  @override
  $AuditDetailsCopyWith<$Res>? get auditDetails;
}

/// @nodoc
class __$$_AttendeesTrackListCopyWithImpl<$Res>
    extends _$AttendeesTrackListCopyWithImpl<$Res, _$_AttendeesTrackList>
    implements _$$_AttendeesTrackListCopyWith<$Res> {
  __$$_AttendeesTrackListCopyWithImpl(
      _$_AttendeesTrackList _value, $Res Function(_$_AttendeesTrackList) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? aadhaar = freezed,
    Object? gender = freezed,
    Object? individualGaurdianName = freezed,
    Object? individualId = freezed,
    Object? id = freezed,
    Object? skill = freezed,
    Object? monEntryId = freezed,
    Object? monExitId = freezed,
    Object? monIndex = freezed,
    Object? tueEntryId = freezed,
    Object? tueExitId = freezed,
    Object? tueIndex = freezed,
    Object? wedEntryId = freezed,
    Object? wedExitId = freezed,
    Object? wedIndex = freezed,
    Object? thuEntryId = freezed,
    Object? thuExitId = freezed,
    Object? thursIndex = freezed,
    Object? friEntryId = freezed,
    Object? friExitId = freezed,
    Object? skillCodeList = freezed,
    Object? friIndex = freezed,
    Object? satEntryId = freezed,
    Object? satExitId = freezed,
    Object? satIndex = freezed,
    Object? sunEntryId = freezed,
    Object? sunExitId = freezed,
    Object? sunIndex = freezed,
    Object? auditDetails = freezed,
  }) {
    return _then(_$_AttendeesTrackList(
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      aadhaar: freezed == aadhaar
          ? _value.aadhaar
          : aadhaar // ignore: cast_nullable_to_non_nullable
              as String?,
      gender: freezed == gender
          ? _value.gender
          : gender // ignore: cast_nullable_to_non_nullable
              as String?,
      individualGaurdianName: freezed == individualGaurdianName
          ? _value.individualGaurdianName
          : individualGaurdianName // ignore: cast_nullable_to_non_nullable
              as String?,
      individualId: freezed == individualId
          ? _value.individualId
          : individualId // ignore: cast_nullable_to_non_nullable
              as String?,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      skill: freezed == skill
          ? _value.skill
          : skill // ignore: cast_nullable_to_non_nullable
              as String?,
      monEntryId: freezed == monEntryId
          ? _value.monEntryId
          : monEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      monExitId: freezed == monExitId
          ? _value.monExitId
          : monExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      monIndex: freezed == monIndex
          ? _value.monIndex
          : monIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      tueEntryId: freezed == tueEntryId
          ? _value.tueEntryId
          : tueEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      tueExitId: freezed == tueExitId
          ? _value.tueExitId
          : tueExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      tueIndex: freezed == tueIndex
          ? _value.tueIndex
          : tueIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      wedEntryId: freezed == wedEntryId
          ? _value.wedEntryId
          : wedEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      wedExitId: freezed == wedExitId
          ? _value.wedExitId
          : wedExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      wedIndex: freezed == wedIndex
          ? _value.wedIndex
          : wedIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      thuEntryId: freezed == thuEntryId
          ? _value.thuEntryId
          : thuEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      thuExitId: freezed == thuExitId
          ? _value.thuExitId
          : thuExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      thursIndex: freezed == thursIndex
          ? _value.thursIndex
          : thursIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      friEntryId: freezed == friEntryId
          ? _value.friEntryId
          : friEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      friExitId: freezed == friExitId
          ? _value.friExitId
          : friExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      skillCodeList: freezed == skillCodeList
          ? _value._skillCodeList
          : skillCodeList // ignore: cast_nullable_to_non_nullable
              as List<String>?,
      friIndex: freezed == friIndex
          ? _value.friIndex
          : friIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      satEntryId: freezed == satEntryId
          ? _value.satEntryId
          : satEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      satExitId: freezed == satExitId
          ? _value.satExitId
          : satExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      satIndex: freezed == satIndex
          ? _value.satIndex
          : satIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      sunEntryId: freezed == sunEntryId
          ? _value.sunEntryId
          : sunEntryId // ignore: cast_nullable_to_non_nullable
              as String?,
      sunExitId: freezed == sunExitId
          ? _value.sunExitId
          : sunExitId // ignore: cast_nullable_to_non_nullable
              as String?,
      sunIndex: freezed == sunIndex
          ? _value.sunIndex
          : sunIndex // ignore: cast_nullable_to_non_nullable
              as double?,
      auditDetails: freezed == auditDetails
          ? _value.auditDetails
          : auditDetails // ignore: cast_nullable_to_non_nullable
              as AuditDetails?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$_AttendeesTrackList implements _AttendeesTrackList {
  const _$_AttendeesTrackList(
      {this.name,
      this.aadhaar,
      this.gender,
      this.individualGaurdianName,
      this.individualId,
      this.id,
      this.skill,
      this.monEntryId,
      this.monExitId,
      this.monIndex = -1,
      this.tueEntryId,
      this.tueExitId,
      this.tueIndex = -1,
      this.wedEntryId,
      this.wedExitId,
      this.wedIndex = -1,
      this.thuEntryId,
      this.thuExitId,
      this.thursIndex = -1,
      this.friEntryId,
      this.friExitId,
      final List<String>? skillCodeList,
      this.friIndex = -1,
      this.satEntryId,
      this.satExitId,
      this.satIndex = -1,
      this.sunEntryId,
      this.sunExitId,
      this.sunIndex = -1,
      @JsonKey(name: 'auditDetails') this.auditDetails})
      : _skillCodeList = skillCodeList;

  factory _$_AttendeesTrackList.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeesTrackListFromJson(json);

  @override
  final String? name;
  @override
  final String? aadhaar;
  @override
  final String? gender;
  @override
  final String? individualGaurdianName;
  @override
  final String? individualId;
  @override
  final String? id;
  @override
  final String? skill;
  @override
  final String? monEntryId;
  @override
  final String? monExitId;
  @override
  @JsonKey()
  final double? monIndex;
  @override
  final String? tueEntryId;
  @override
  final String? tueExitId;
  @override
  @JsonKey()
  final double? tueIndex;
  @override
  final String? wedEntryId;
  @override
  final String? wedExitId;
  @override
  @JsonKey()
  final double? wedIndex;
  @override
  final String? thuEntryId;
  @override
  final String? thuExitId;
  @override
  @JsonKey()
  final double? thursIndex;
  @override
  final String? friEntryId;
  @override
  final String? friExitId;
  final List<String>? _skillCodeList;
  @override
  List<String>? get skillCodeList {
    final value = _skillCodeList;
    if (value == null) return null;
    if (_skillCodeList is EqualUnmodifiableListView) return _skillCodeList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  @JsonKey()
  final double? friIndex;
  @override
  final String? satEntryId;
  @override
  final String? satExitId;
  @override
  @JsonKey()
  final double? satIndex;
  @override
  final String? sunEntryId;
  @override
  final String? sunExitId;
  @override
  @JsonKey()
  final double? sunIndex;
  @override
  @JsonKey(name: 'auditDetails')
  final AuditDetails? auditDetails;

  @override
  String toString() {
    return 'AttendeesTrackList(name: $name, aadhaar: $aadhaar, gender: $gender, individualGaurdianName: $individualGaurdianName, individualId: $individualId, id: $id, skill: $skill, monEntryId: $monEntryId, monExitId: $monExitId, monIndex: $monIndex, tueEntryId: $tueEntryId, tueExitId: $tueExitId, tueIndex: $tueIndex, wedEntryId: $wedEntryId, wedExitId: $wedExitId, wedIndex: $wedIndex, thuEntryId: $thuEntryId, thuExitId: $thuExitId, thursIndex: $thursIndex, friEntryId: $friEntryId, friExitId: $friExitId, skillCodeList: $skillCodeList, friIndex: $friIndex, satEntryId: $satEntryId, satExitId: $satExitId, satIndex: $satIndex, sunEntryId: $sunEntryId, sunExitId: $sunExitId, sunIndex: $sunIndex, auditDetails: $auditDetails)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeesTrackList &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.aadhaar, aadhaar) || other.aadhaar == aadhaar) &&
            (identical(other.gender, gender) || other.gender == gender) &&
            (identical(other.individualGaurdianName, individualGaurdianName) ||
                other.individualGaurdianName == individualGaurdianName) &&
            (identical(other.individualId, individualId) ||
                other.individualId == individualId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.skill, skill) || other.skill == skill) &&
            (identical(other.monEntryId, monEntryId) ||
                other.monEntryId == monEntryId) &&
            (identical(other.monExitId, monExitId) ||
                other.monExitId == monExitId) &&
            (identical(other.monIndex, monIndex) ||
                other.monIndex == monIndex) &&
            (identical(other.tueEntryId, tueEntryId) ||
                other.tueEntryId == tueEntryId) &&
            (identical(other.tueExitId, tueExitId) ||
                other.tueExitId == tueExitId) &&
            (identical(other.tueIndex, tueIndex) ||
                other.tueIndex == tueIndex) &&
            (identical(other.wedEntryId, wedEntryId) ||
                other.wedEntryId == wedEntryId) &&
            (identical(other.wedExitId, wedExitId) ||
                other.wedExitId == wedExitId) &&
            (identical(other.wedIndex, wedIndex) ||
                other.wedIndex == wedIndex) &&
            (identical(other.thuEntryId, thuEntryId) ||
                other.thuEntryId == thuEntryId) &&
            (identical(other.thuExitId, thuExitId) ||
                other.thuExitId == thuExitId) &&
            (identical(other.thursIndex, thursIndex) ||
                other.thursIndex == thursIndex) &&
            (identical(other.friEntryId, friEntryId) ||
                other.friEntryId == friEntryId) &&
            (identical(other.friExitId, friExitId) ||
                other.friExitId == friExitId) &&
            const DeepCollectionEquality()
                .equals(other._skillCodeList, _skillCodeList) &&
            (identical(other.friIndex, friIndex) ||
                other.friIndex == friIndex) &&
            (identical(other.satEntryId, satEntryId) ||
                other.satEntryId == satEntryId) &&
            (identical(other.satExitId, satExitId) ||
                other.satExitId == satExitId) &&
            (identical(other.satIndex, satIndex) ||
                other.satIndex == satIndex) &&
            (identical(other.sunEntryId, sunEntryId) ||
                other.sunEntryId == sunEntryId) &&
            (identical(other.sunExitId, sunExitId) ||
                other.sunExitId == sunExitId) &&
            (identical(other.sunIndex, sunIndex) ||
                other.sunIndex == sunIndex) &&
            (identical(other.auditDetails, auditDetails) ||
                other.auditDetails == auditDetails));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hashAll([
        runtimeType,
        name,
        aadhaar,
        gender,
        individualGaurdianName,
        individualId,
        id,
        skill,
        monEntryId,
        monExitId,
        monIndex,
        tueEntryId,
        tueExitId,
        tueIndex,
        wedEntryId,
        wedExitId,
        wedIndex,
        thuEntryId,
        thuExitId,
        thursIndex,
        friEntryId,
        friExitId,
        const DeepCollectionEquality().hash(_skillCodeList),
        friIndex,
        satEntryId,
        satExitId,
        satIndex,
        sunEntryId,
        sunExitId,
        sunIndex,
        auditDetails
      ]);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$_AttendeesTrackListCopyWith<_$_AttendeesTrackList> get copyWith =>
      __$$_AttendeesTrackListCopyWithImpl<_$_AttendeesTrackList>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendeesTrackListToJson(
      this,
    );
  }
}

abstract class _AttendeesTrackList implements AttendeesTrackList {
  const factory _AttendeesTrackList(
          {final String? name,
          final String? aadhaar,
          final String? gender,
          final String? individualGaurdianName,
          final String? individualId,
          final String? id,
          final String? skill,
          final String? monEntryId,
          final String? monExitId,
          final double? monIndex,
          final String? tueEntryId,
          final String? tueExitId,
          final double? tueIndex,
          final String? wedEntryId,
          final String? wedExitId,
          final double? wedIndex,
          final String? thuEntryId,
          final String? thuExitId,
          final double? thursIndex,
          final String? friEntryId,
          final String? friExitId,
          final List<String>? skillCodeList,
          final double? friIndex,
          final String? satEntryId,
          final String? satExitId,
          final double? satIndex,
          final String? sunEntryId,
          final String? sunExitId,
          final double? sunIndex,
          @JsonKey(name: 'auditDetails') final AuditDetails? auditDetails}) =
      _$_AttendeesTrackList;

  factory _AttendeesTrackList.fromJson(Map<String, dynamic> json) =
      _$_AttendeesTrackList.fromJson;

  @override
  String? get name;
  @override
  String? get aadhaar;
  @override
  String? get gender;
  @override
  String? get individualGaurdianName;
  @override
  String? get individualId;
  @override
  String? get id;
  @override
  String? get skill;
  @override
  String? get monEntryId;
  @override
  String? get monExitId;
  @override
  double? get monIndex;
  @override
  String? get tueEntryId;
  @override
  String? get tueExitId;
  @override
  double? get tueIndex;
  @override
  String? get wedEntryId;
  @override
  String? get wedExitId;
  @override
  double? get wedIndex;
  @override
  String? get thuEntryId;
  @override
  String? get thuExitId;
  @override
  double? get thursIndex;
  @override
  String? get friEntryId;
  @override
  String? get friExitId;
  @override
  List<String>? get skillCodeList;
  @override
  double? get friIndex;
  @override
  String? get satEntryId;
  @override
  String? get satExitId;
  @override
  double? get satIndex;
  @override
  String? get sunEntryId;
  @override
  String? get sunExitId;
  @override
  double? get sunIndex;
  @override
  @JsonKey(name: 'auditDetails')
  AuditDetails? get auditDetails;
  @override
  @JsonKey(ignore: true)
  _$$_AttendeesTrackListCopyWith<_$_AttendeesTrackList> get copyWith =>
      throw _privateConstructorUsedError;
}

AttendeeAuditDetails _$AttendeeAuditDetailsFromJson(Map<String, dynamic> json) {
  return _AttendeeAuditDetails.fromJson(json);
}

/// @nodoc
mixin _$AttendeeAuditDetails {
  String? get createdBy => throw _privateConstructorUsedError;
  String? get lastModifiedBy => throw _privateConstructorUsedError;
  int? get createdTime => throw _privateConstructorUsedError;
  int? get lastModifiedTime => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $AttendeeAuditDetailsCopyWith<AttendeeAuditDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $AttendeeAuditDetailsCopyWith<$Res> {
  factory $AttendeeAuditDetailsCopyWith(AttendeeAuditDetails value,
          $Res Function(AttendeeAuditDetails) then) =
      _$AttendeeAuditDetailsCopyWithImpl<$Res, AttendeeAuditDetails>;
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class _$AttendeeAuditDetailsCopyWithImpl<$Res,
        $Val extends AttendeeAuditDetails>
    implements $AttendeeAuditDetailsCopyWith<$Res> {
  _$AttendeeAuditDetailsCopyWithImpl(this._value, this._then);

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
abstract class _$$_AttendeeAuditDetailsCopyWith<$Res>
    implements $AttendeeAuditDetailsCopyWith<$Res> {
  factory _$$_AttendeeAuditDetailsCopyWith(_$_AttendeeAuditDetails value,
          $Res Function(_$_AttendeeAuditDetails) then) =
      __$$_AttendeeAuditDetailsCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? createdBy,
      String? lastModifiedBy,
      int? createdTime,
      int? lastModifiedTime});
}

/// @nodoc
class __$$_AttendeeAuditDetailsCopyWithImpl<$Res>
    extends _$AttendeeAuditDetailsCopyWithImpl<$Res, _$_AttendeeAuditDetails>
    implements _$$_AttendeeAuditDetailsCopyWith<$Res> {
  __$$_AttendeeAuditDetailsCopyWithImpl(_$_AttendeeAuditDetails _value,
      $Res Function(_$_AttendeeAuditDetails) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? createdBy = freezed,
    Object? lastModifiedBy = freezed,
    Object? createdTime = freezed,
    Object? lastModifiedTime = freezed,
  }) {
    return _then(_$_AttendeeAuditDetails(
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
class _$_AttendeeAuditDetails implements _AttendeeAuditDetails {
  const _$_AttendeeAuditDetails(
      {this.createdBy,
      this.lastModifiedBy,
      this.createdTime,
      this.lastModifiedTime});

  factory _$_AttendeeAuditDetails.fromJson(Map<String, dynamic> json) =>
      _$$_AttendeeAuditDetailsFromJson(json);

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
    return 'AttendeeAuditDetails(createdBy: $createdBy, lastModifiedBy: $lastModifiedBy, createdTime: $createdTime, lastModifiedTime: $lastModifiedTime)';
  }

  @override
  bool operator ==(dynamic other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$_AttendeeAuditDetails &&
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
  _$$_AttendeeAuditDetailsCopyWith<_$_AttendeeAuditDetails> get copyWith =>
      __$$_AttendeeAuditDetailsCopyWithImpl<_$_AttendeeAuditDetails>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$_AttendeeAuditDetailsToJson(
      this,
    );
  }
}

abstract class _AttendeeAuditDetails implements AttendeeAuditDetails {
  const factory _AttendeeAuditDetails(
      {final String? createdBy,
      final String? lastModifiedBy,
      final int? createdTime,
      final int? lastModifiedTime}) = _$_AttendeeAuditDetails;

  factory _AttendeeAuditDetails.fromJson(Map<String, dynamic> json) =
      _$_AttendeeAuditDetails.fromJson;

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
  _$$_AttendeeAuditDetailsCopyWith<_$_AttendeeAuditDetails> get copyWith =>
      throw _privateConstructorUsedError;
}
