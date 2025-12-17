// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'create_muster.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$MusterCreateEvent {
  String get tenantId => throw _privateConstructorUsedError;
  String get registerId => throw _privateConstructorUsedError;
  String get contractId => throw _privateConstructorUsedError;
  String get orgName => throw _privateConstructorUsedError;
  String get registerNo => throw _privateConstructorUsedError;
  String get registerName => throw _privateConstructorUsedError;
  int get startDate => throw _privateConstructorUsedError;
  List<Map<String, dynamic>>? get skillsList =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)
        create,
    required TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)
        update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult? Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateMusterEvent value) create,
    required TResult Function(UpdateMusterEvent value) update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateMusterEvent value)? create,
    TResult? Function(UpdateMusterEvent value)? update,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateMusterEvent value)? create,
    TResult Function(UpdateMusterEvent value)? update,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $MusterCreateEventCopyWith<MusterCreateEvent> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterCreateEventCopyWith<$Res> {
  factory $MusterCreateEventCopyWith(
          MusterCreateEvent value, $Res Function(MusterCreateEvent) then) =
      _$MusterCreateEventCopyWithImpl<$Res, MusterCreateEvent>;
  @useResult
  $Res call(
      {String tenantId,
      String registerId,
      String contractId,
      String orgName,
      String registerNo,
      String registerName,
      int startDate,
      List<Map<String, dynamic>>? skillsList});
}

/// @nodoc
class _$MusterCreateEventCopyWithImpl<$Res, $Val extends MusterCreateEvent>
    implements $MusterCreateEventCopyWith<$Res> {
  _$MusterCreateEventCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerId = null,
    Object? contractId = null,
    Object? orgName = null,
    Object? registerNo = null,
    Object? registerName = null,
    Object? startDate = null,
    Object? skillsList = freezed,
  }) {
    return _then(_value.copyWith(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      contractId: null == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String,
      orgName: null == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String,
      registerNo: null == registerNo
          ? _value.registerNo
          : registerNo // ignore: cast_nullable_to_non_nullable
              as String,
      registerName: null == registerName
          ? _value.registerName
          : registerName // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      skillsList: freezed == skillsList
          ? _value.skillsList
          : skillsList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$CreateMusterEventImplCopyWith<$Res>
    implements $MusterCreateEventCopyWith<$Res> {
  factory _$$CreateMusterEventImplCopyWith(_$CreateMusterEventImpl value,
          $Res Function(_$CreateMusterEventImpl) then) =
      __$$CreateMusterEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String registerId,
      String contractId,
      String orgName,
      String registerNo,
      String registerName,
      int startDate,
      String? serviceCode,
      String? referenceId,
      String? projectName,
      String? projectDesc,
      String? locality,
      String? projectId,
      String? projectType,
      String? ward,
      int? amount,
      String? executingAuthority,
      List<Map<String, dynamic>>? skillsList});
}

/// @nodoc
class __$$CreateMusterEventImplCopyWithImpl<$Res>
    extends _$MusterCreateEventCopyWithImpl<$Res, _$CreateMusterEventImpl>
    implements _$$CreateMusterEventImplCopyWith<$Res> {
  __$$CreateMusterEventImplCopyWithImpl(_$CreateMusterEventImpl _value,
      $Res Function(_$CreateMusterEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? registerId = null,
    Object? contractId = null,
    Object? orgName = null,
    Object? registerNo = null,
    Object? registerName = null,
    Object? startDate = null,
    Object? serviceCode = freezed,
    Object? referenceId = freezed,
    Object? projectName = freezed,
    Object? projectDesc = freezed,
    Object? locality = freezed,
    Object? projectId = freezed,
    Object? projectType = freezed,
    Object? ward = freezed,
    Object? amount = freezed,
    Object? executingAuthority = freezed,
    Object? skillsList = freezed,
  }) {
    return _then(_$CreateMusterEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      contractId: null == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String,
      orgName: null == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String,
      registerNo: null == registerNo
          ? _value.registerNo
          : registerNo // ignore: cast_nullable_to_non_nullable
              as String,
      registerName: null == registerName
          ? _value.registerName
          : registerName // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      serviceCode: freezed == serviceCode
          ? _value.serviceCode
          : serviceCode // ignore: cast_nullable_to_non_nullable
              as String?,
      referenceId: freezed == referenceId
          ? _value.referenceId
          : referenceId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectName: freezed == projectName
          ? _value.projectName
          : projectName // ignore: cast_nullable_to_non_nullable
              as String?,
      projectDesc: freezed == projectDesc
          ? _value.projectDesc
          : projectDesc // ignore: cast_nullable_to_non_nullable
              as String?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
      projectId: freezed == projectId
          ? _value.projectId
          : projectId // ignore: cast_nullable_to_non_nullable
              as String?,
      projectType: freezed == projectType
          ? _value.projectType
          : projectType // ignore: cast_nullable_to_non_nullable
              as String?,
      ward: freezed == ward
          ? _value.ward
          : ward // ignore: cast_nullable_to_non_nullable
              as String?,
      amount: freezed == amount
          ? _value.amount
          : amount // ignore: cast_nullable_to_non_nullable
              as int?,
      executingAuthority: freezed == executingAuthority
          ? _value.executingAuthority
          : executingAuthority // ignore: cast_nullable_to_non_nullable
              as String?,
      skillsList: freezed == skillsList
          ? _value._skillsList
          : skillsList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ));
  }
}

/// @nodoc

class _$CreateMusterEventImpl implements CreateMusterEvent {
  const _$CreateMusterEventImpl(
      {required this.tenantId,
      required this.registerId,
      required this.contractId,
      required this.orgName,
      required this.registerNo,
      required this.registerName,
      required this.startDate,
      this.serviceCode,
      this.referenceId,
      this.projectName,
      this.projectDesc,
      this.locality,
      this.projectId,
      this.projectType,
      this.ward,
      this.amount,
      this.executingAuthority,
      final List<Map<String, dynamic>>? skillsList})
      : _skillsList = skillsList;

  @override
  final String tenantId;
  @override
  final String registerId;
  @override
  final String contractId;
  @override
  final String orgName;
  @override
  final String registerNo;
  @override
  final String registerName;
  @override
  final int startDate;
  @override
  final String? serviceCode;
  @override
  final String? referenceId;
  @override
  final String? projectName;
  @override
  final String? projectDesc;
  @override
  final String? locality;
  @override
  final String? projectId;
  @override
  final String? projectType;
  @override
  final String? ward;
  @override
  final int? amount;
  @override
  final String? executingAuthority;
  final List<Map<String, dynamic>>? _skillsList;
  @override
  List<Map<String, dynamic>>? get skillsList {
    final value = _skillsList;
    if (value == null) return null;
    if (_skillsList is EqualUnmodifiableListView) return _skillsList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterCreateEvent.create(tenantId: $tenantId, registerId: $registerId, contractId: $contractId, orgName: $orgName, registerNo: $registerNo, registerName: $registerName, startDate: $startDate, serviceCode: $serviceCode, referenceId: $referenceId, projectName: $projectName, projectDesc: $projectDesc, locality: $locality, projectId: $projectId, projectType: $projectType, ward: $ward, amount: $amount, executingAuthority: $executingAuthority, skillsList: $skillsList)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$CreateMusterEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId) &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.registerNo, registerNo) ||
                other.registerNo == registerNo) &&
            (identical(other.registerName, registerName) ||
                other.registerName == registerName) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.serviceCode, serviceCode) ||
                other.serviceCode == serviceCode) &&
            (identical(other.referenceId, referenceId) ||
                other.referenceId == referenceId) &&
            (identical(other.projectName, projectName) ||
                other.projectName == projectName) &&
            (identical(other.projectDesc, projectDesc) ||
                other.projectDesc == projectDesc) &&
            (identical(other.locality, locality) ||
                other.locality == locality) &&
            (identical(other.projectId, projectId) ||
                other.projectId == projectId) &&
            (identical(other.projectType, projectType) ||
                other.projectType == projectType) &&
            (identical(other.ward, ward) || other.ward == ward) &&
            (identical(other.amount, amount) || other.amount == amount) &&
            (identical(other.executingAuthority, executingAuthority) ||
                other.executingAuthority == executingAuthority) &&
            const DeepCollectionEquality()
                .equals(other._skillsList, _skillsList));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      registerId,
      contractId,
      orgName,
      registerNo,
      registerName,
      startDate,
      serviceCode,
      referenceId,
      projectName,
      projectDesc,
      locality,
      projectId,
      projectType,
      ward,
      amount,
      executingAuthority,
      const DeepCollectionEquality().hash(_skillsList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$CreateMusterEventImplCopyWith<_$CreateMusterEventImpl> get copyWith =>
      __$$CreateMusterEventImplCopyWithImpl<_$CreateMusterEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)
        create,
    required TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)
        update,
  }) {
    return create(
        tenantId,
        registerId,
        contractId,
        orgName,
        registerNo,
        registerName,
        startDate,
        serviceCode,
        referenceId,
        projectName,
        projectDesc,
        locality,
        projectId,
        projectType,
        ward,
        amount,
        executingAuthority,
        skillsList);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult? Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
  }) {
    return create?.call(
        tenantId,
        registerId,
        contractId,
        orgName,
        registerNo,
        registerName,
        startDate,
        serviceCode,
        referenceId,
        projectName,
        projectDesc,
        locality,
        projectId,
        projectType,
        ward,
        amount,
        executingAuthority,
        skillsList);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(
          tenantId,
          registerId,
          contractId,
          orgName,
          registerNo,
          registerName,
          startDate,
          serviceCode,
          referenceId,
          projectName,
          projectDesc,
          locality,
          projectId,
          projectType,
          ward,
          amount,
          executingAuthority,
          skillsList);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateMusterEvent value) create,
    required TResult Function(UpdateMusterEvent value) update,
  }) {
    return create(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateMusterEvent value)? create,
    TResult? Function(UpdateMusterEvent value)? update,
  }) {
    return create?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateMusterEvent value)? create,
    TResult Function(UpdateMusterEvent value)? update,
    required TResult orElse(),
  }) {
    if (create != null) {
      return create(this);
    }
    return orElse();
  }
}

abstract class CreateMusterEvent implements MusterCreateEvent {
  const factory CreateMusterEvent(
      {required final String tenantId,
      required final String registerId,
      required final String contractId,
      required final String orgName,
      required final String registerNo,
      required final String registerName,
      required final int startDate,
      final String? serviceCode,
      final String? referenceId,
      final String? projectName,
      final String? projectDesc,
      final String? locality,
      final String? projectId,
      final String? projectType,
      final String? ward,
      final int? amount,
      final String? executingAuthority,
      final List<Map<String, dynamic>>? skillsList}) = _$CreateMusterEventImpl;

  @override
  String get tenantId;
  @override
  String get registerId;
  @override
  String get contractId;
  @override
  String get orgName;
  @override
  String get registerNo;
  @override
  String get registerName;
  @override
  int get startDate;
  String? get serviceCode;
  String? get referenceId;
  String? get projectName;
  String? get projectDesc;
  String? get locality;
  String? get projectId;
  String? get projectType;
  String? get ward;
  int? get amount;
  String? get executingAuthority;
  @override
  List<Map<String, dynamic>>? get skillsList;
  @override
  @JsonKey(ignore: true)
  _$$CreateMusterEventImplCopyWith<_$CreateMusterEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$UpdateMusterEventImplCopyWith<$Res>
    implements $MusterCreateEventCopyWith<$Res> {
  factory _$$UpdateMusterEventImplCopyWith(_$UpdateMusterEventImpl value,
          $Res Function(_$UpdateMusterEventImpl) then) =
      __$$UpdateMusterEventImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String tenantId,
      String id,
      String orgName,
      String contractId,
      String registerNo,
      String registerName,
      int startDate,
      String registerId,
      String? reSubmitAction,
      List<Map<String, dynamic>>? skillsList});
}

/// @nodoc
class __$$UpdateMusterEventImplCopyWithImpl<$Res>
    extends _$MusterCreateEventCopyWithImpl<$Res, _$UpdateMusterEventImpl>
    implements _$$UpdateMusterEventImplCopyWith<$Res> {
  __$$UpdateMusterEventImplCopyWithImpl(_$UpdateMusterEventImpl _value,
      $Res Function(_$UpdateMusterEventImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? tenantId = null,
    Object? id = null,
    Object? orgName = null,
    Object? contractId = null,
    Object? registerNo = null,
    Object? registerName = null,
    Object? startDate = null,
    Object? registerId = null,
    Object? reSubmitAction = freezed,
    Object? skillsList = freezed,
  }) {
    return _then(_$UpdateMusterEventImpl(
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      id: null == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String,
      orgName: null == orgName
          ? _value.orgName
          : orgName // ignore: cast_nullable_to_non_nullable
              as String,
      contractId: null == contractId
          ? _value.contractId
          : contractId // ignore: cast_nullable_to_non_nullable
              as String,
      registerNo: null == registerNo
          ? _value.registerNo
          : registerNo // ignore: cast_nullable_to_non_nullable
              as String,
      registerName: null == registerName
          ? _value.registerName
          : registerName // ignore: cast_nullable_to_non_nullable
              as String,
      startDate: null == startDate
          ? _value.startDate
          : startDate // ignore: cast_nullable_to_non_nullable
              as int,
      registerId: null == registerId
          ? _value.registerId
          : registerId // ignore: cast_nullable_to_non_nullable
              as String,
      reSubmitAction: freezed == reSubmitAction
          ? _value.reSubmitAction
          : reSubmitAction // ignore: cast_nullable_to_non_nullable
              as String?,
      skillsList: freezed == skillsList
          ? _value._skillsList
          : skillsList // ignore: cast_nullable_to_non_nullable
              as List<Map<String, dynamic>>?,
    ));
  }
}

/// @nodoc

class _$UpdateMusterEventImpl implements UpdateMusterEvent {
  const _$UpdateMusterEventImpl(
      {required this.tenantId,
      required this.id,
      required this.orgName,
      required this.contractId,
      required this.registerNo,
      required this.registerName,
      required this.startDate,
      required this.registerId,
      this.reSubmitAction,
      final List<Map<String, dynamic>>? skillsList})
      : _skillsList = skillsList;

  @override
  final String tenantId;
  @override
  final String id;
  @override
  final String orgName;
  @override
  final String contractId;
  @override
  final String registerNo;
  @override
  final String registerName;
  @override
  final int startDate;
  @override
  final String registerId;
  @override
  final String? reSubmitAction;
  final List<Map<String, dynamic>>? _skillsList;
  @override
  List<Map<String, dynamic>>? get skillsList {
    final value = _skillsList;
    if (value == null) return null;
    if (_skillsList is EqualUnmodifiableListView) return _skillsList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'MusterCreateEvent.update(tenantId: $tenantId, id: $id, orgName: $orgName, contractId: $contractId, registerNo: $registerNo, registerName: $registerName, startDate: $startDate, registerId: $registerId, reSubmitAction: $reSubmitAction, skillsList: $skillsList)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$UpdateMusterEventImpl &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgName, orgName) || other.orgName == orgName) &&
            (identical(other.contractId, contractId) ||
                other.contractId == contractId) &&
            (identical(other.registerNo, registerNo) ||
                other.registerNo == registerNo) &&
            (identical(other.registerName, registerName) ||
                other.registerName == registerName) &&
            (identical(other.startDate, startDate) ||
                other.startDate == startDate) &&
            (identical(other.registerId, registerId) ||
                other.registerId == registerId) &&
            (identical(other.reSubmitAction, reSubmitAction) ||
                other.reSubmitAction == reSubmitAction) &&
            const DeepCollectionEquality()
                .equals(other._skillsList, _skillsList));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      tenantId,
      id,
      orgName,
      contractId,
      registerNo,
      registerName,
      startDate,
      registerId,
      reSubmitAction,
      const DeepCollectionEquality().hash(_skillsList));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$UpdateMusterEventImplCopyWith<_$UpdateMusterEventImpl> get copyWith =>
      __$$UpdateMusterEventImplCopyWithImpl<_$UpdateMusterEventImpl>(
          this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)
        create,
    required TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)
        update,
  }) {
    return update(tenantId, id, orgName, contractId, registerNo, registerName,
        startDate, registerId, reSubmitAction, skillsList);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult? Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
  }) {
    return update?.call(tenantId, id, orgName, contractId, registerNo,
        registerName, startDate, registerId, reSubmitAction, skillsList);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function(
            String tenantId,
            String registerId,
            String contractId,
            String orgName,
            String registerNo,
            String registerName,
            int startDate,
            String? serviceCode,
            String? referenceId,
            String? projectName,
            String? projectDesc,
            String? locality,
            String? projectId,
            String? projectType,
            String? ward,
            int? amount,
            String? executingAuthority,
            List<Map<String, dynamic>>? skillsList)?
        create,
    TResult Function(
            String tenantId,
            String id,
            String orgName,
            String contractId,
            String registerNo,
            String registerName,
            int startDate,
            String registerId,
            String? reSubmitAction,
            List<Map<String, dynamic>>? skillsList)?
        update,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(tenantId, id, orgName, contractId, registerNo, registerName,
          startDate, registerId, reSubmitAction, skillsList);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(CreateMusterEvent value) create,
    required TResult Function(UpdateMusterEvent value) update,
  }) {
    return update(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(CreateMusterEvent value)? create,
    TResult? Function(UpdateMusterEvent value)? update,
  }) {
    return update?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(CreateMusterEvent value)? create,
    TResult Function(UpdateMusterEvent value)? update,
    required TResult orElse(),
  }) {
    if (update != null) {
      return update(this);
    }
    return orElse();
  }
}

abstract class UpdateMusterEvent implements MusterCreateEvent {
  const factory UpdateMusterEvent(
      {required final String tenantId,
      required final String id,
      required final String orgName,
      required final String contractId,
      required final String registerNo,
      required final String registerName,
      required final int startDate,
      required final String registerId,
      final String? reSubmitAction,
      final List<Map<String, dynamic>>? skillsList}) = _$UpdateMusterEventImpl;

  @override
  String get tenantId;
  String get id;
  @override
  String get orgName;
  @override
  String get contractId;
  @override
  String get registerNo;
  @override
  String get registerName;
  @override
  int get startDate;
  @override
  String get registerId;
  String? get reSubmitAction;
  @override
  List<Map<String, dynamic>>? get skillsList;
  @override
  @JsonKey(ignore: true)
  _$$UpdateMusterEventImplCopyWith<_$UpdateMusterEventImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
mixin _$MusterCreateState {
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function() error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function()? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) =>
      throw _privateConstructorUsedError;
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $MusterCreateStateCopyWith<$Res> {
  factory $MusterCreateStateCopyWith(
          MusterCreateState value, $Res Function(MusterCreateState) then) =
      _$MusterCreateStateCopyWithImpl<$Res, MusterCreateState>;
}

/// @nodoc
class _$MusterCreateStateCopyWithImpl<$Res, $Val extends MusterCreateState>
    implements $MusterCreateStateCopyWith<$Res> {
  _$MusterCreateStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;
}

/// @nodoc
abstract class _$$InitialImplCopyWith<$Res> {
  factory _$$InitialImplCopyWith(
          _$InitialImpl value, $Res Function(_$InitialImpl) then) =
      __$$InitialImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$InitialImplCopyWithImpl<$Res>
    extends _$MusterCreateStateCopyWithImpl<$Res, _$InitialImpl>
    implements _$$InitialImplCopyWith<$Res> {
  __$$InitialImplCopyWithImpl(
      _$InitialImpl _value, $Res Function(_$InitialImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$InitialImpl extends _Initial {
  const _$InitialImpl() : super._();

  @override
  String toString() {
    return 'MusterCreateState.initial()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$InitialImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function() error,
  }) {
    return initial();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function()? error,
  }) {
    return initial?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return initial(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return initial?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (initial != null) {
      return initial(this);
    }
    return orElse();
  }
}

abstract class _Initial extends MusterCreateState {
  const factory _Initial() = _$InitialImpl;
  const _Initial._() : super._();
}

/// @nodoc
abstract class _$$LoadingImplCopyWith<$Res> {
  factory _$$LoadingImplCopyWith(
          _$LoadingImpl value, $Res Function(_$LoadingImpl) then) =
      __$$LoadingImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$LoadingImplCopyWithImpl<$Res>
    extends _$MusterCreateStateCopyWithImpl<$Res, _$LoadingImpl>
    implements _$$LoadingImplCopyWith<$Res> {
  __$$LoadingImplCopyWithImpl(
      _$LoadingImpl _value, $Res Function(_$LoadingImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$LoadingImpl extends _Loading {
  const _$LoadingImpl() : super._();

  @override
  String toString() {
    return 'MusterCreateState.loading()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$LoadingImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function() error,
  }) {
    return loading();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function()? error,
  }) {
    return loading?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loading(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loading?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loading != null) {
      return loading(this);
    }
    return orElse();
  }
}

abstract class _Loading extends MusterCreateState {
  const factory _Loading() = _$LoadingImpl;
  const _Loading._() : super._();
}

/// @nodoc
abstract class _$$LoadedImplCopyWith<$Res> {
  factory _$$LoadedImplCopyWith(
          _$LoadedImpl value, $Res Function(_$LoadedImpl) then) =
      __$$LoadedImplCopyWithImpl<$Res>;
  @useResult
  $Res call({MusterRollsModel? musterRollsModel});

  $MusterRollsModelCopyWith<$Res>? get musterRollsModel;
}

/// @nodoc
class __$$LoadedImplCopyWithImpl<$Res>
    extends _$MusterCreateStateCopyWithImpl<$Res, _$LoadedImpl>
    implements _$$LoadedImplCopyWith<$Res> {
  __$$LoadedImplCopyWithImpl(
      _$LoadedImpl _value, $Res Function(_$LoadedImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? musterRollsModel = freezed,
  }) {
    return _then(_$LoadedImpl(
      freezed == musterRollsModel
          ? _value.musterRollsModel
          : musterRollsModel // ignore: cast_nullable_to_non_nullable
              as MusterRollsModel?,
    ));
  }

  @override
  @pragma('vm:prefer-inline')
  $MusterRollsModelCopyWith<$Res>? get musterRollsModel {
    if (_value.musterRollsModel == null) {
      return null;
    }

    return $MusterRollsModelCopyWith<$Res>(_value.musterRollsModel!, (value) {
      return _then(_value.copyWith(musterRollsModel: value));
    });
  }
}

/// @nodoc

class _$LoadedImpl extends _Loaded {
  const _$LoadedImpl(this.musterRollsModel) : super._();

  @override
  final MusterRollsModel? musterRollsModel;

  @override
  String toString() {
    return 'MusterCreateState.loaded(musterRollsModel: $musterRollsModel)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$LoadedImpl &&
            (identical(other.musterRollsModel, musterRollsModel) ||
                other.musterRollsModel == musterRollsModel));
  }

  @override
  int get hashCode => Object.hash(runtimeType, musterRollsModel);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      __$$LoadedImplCopyWithImpl<_$LoadedImpl>(this, _$identity);

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function() error,
  }) {
    return loaded(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function()? error,
  }) {
    return loaded?.call(musterRollsModel);
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(musterRollsModel);
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return loaded(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return loaded?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (loaded != null) {
      return loaded(this);
    }
    return orElse();
  }
}

abstract class _Loaded extends MusterCreateState {
  const factory _Loaded(final MusterRollsModel? musterRollsModel) =
      _$LoadedImpl;
  const _Loaded._() : super._();

  MusterRollsModel? get musterRollsModel;
  @JsonKey(ignore: true)
  _$$LoadedImplCopyWith<_$LoadedImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class _$$ErrorImplCopyWith<$Res> {
  factory _$$ErrorImplCopyWith(
          _$ErrorImpl value, $Res Function(_$ErrorImpl) then) =
      __$$ErrorImplCopyWithImpl<$Res>;
}

/// @nodoc
class __$$ErrorImplCopyWithImpl<$Res>
    extends _$MusterCreateStateCopyWithImpl<$Res, _$ErrorImpl>
    implements _$$ErrorImplCopyWith<$Res> {
  __$$ErrorImplCopyWithImpl(
      _$ErrorImpl _value, $Res Function(_$ErrorImpl) _then)
      : super(_value, _then);
}

/// @nodoc

class _$ErrorImpl extends _Error {
  const _$ErrorImpl() : super._();

  @override
  String toString() {
    return 'MusterCreateState.error()';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType && other is _$ErrorImpl);
  }

  @override
  int get hashCode => runtimeType.hashCode;

  @override
  @optionalTypeArgs
  TResult when<TResult extends Object?>({
    required TResult Function() initial,
    required TResult Function() loading,
    required TResult Function(MusterRollsModel? musterRollsModel) loaded,
    required TResult Function() error,
  }) {
    return error();
  }

  @override
  @optionalTypeArgs
  TResult? whenOrNull<TResult extends Object?>({
    TResult? Function()? initial,
    TResult? Function()? loading,
    TResult? Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult? Function()? error,
  }) {
    return error?.call();
  }

  @override
  @optionalTypeArgs
  TResult maybeWhen<TResult extends Object?>({
    TResult Function()? initial,
    TResult Function()? loading,
    TResult Function(MusterRollsModel? musterRollsModel)? loaded,
    TResult Function()? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error();
    }
    return orElse();
  }

  @override
  @optionalTypeArgs
  TResult map<TResult extends Object?>({
    required TResult Function(_Initial value) initial,
    required TResult Function(_Loading value) loading,
    required TResult Function(_Loaded value) loaded,
    required TResult Function(_Error value) error,
  }) {
    return error(this);
  }

  @override
  @optionalTypeArgs
  TResult? mapOrNull<TResult extends Object?>({
    TResult? Function(_Initial value)? initial,
    TResult? Function(_Loading value)? loading,
    TResult? Function(_Loaded value)? loaded,
    TResult? Function(_Error value)? error,
  }) {
    return error?.call(this);
  }

  @override
  @optionalTypeArgs
  TResult maybeMap<TResult extends Object?>({
    TResult Function(_Initial value)? initial,
    TResult Function(_Loading value)? loading,
    TResult Function(_Loaded value)? loaded,
    TResult Function(_Error value)? error,
    required TResult orElse(),
  }) {
    if (error != null) {
      return error(this);
    }
    return orElse();
  }
}

abstract class _Error extends MusterCreateState {
  const factory _Error() = _$ErrorImpl;
  const _Error._() : super._();
}
