// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'organisation_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

OrganisationListModel _$OrganisationListModelFromJson(
    Map<String, dynamic> json) {
  return _OrganisationListModel.fromJson(json);
}

/// @nodoc
mixin _$OrganisationListModel {
  List<OrganisationModel>? get organisations =>
      throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrganisationListModelCopyWith<OrganisationListModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrganisationListModelCopyWith<$Res> {
  factory $OrganisationListModelCopyWith(OrganisationListModel value,
          $Res Function(OrganisationListModel) then) =
      _$OrganisationListModelCopyWithImpl<$Res, OrganisationListModel>;
  @useResult
  $Res call({List<OrganisationModel>? organisations});
}

/// @nodoc
class _$OrganisationListModelCopyWithImpl<$Res,
        $Val extends OrganisationListModel>
    implements $OrganisationListModelCopyWith<$Res> {
  _$OrganisationListModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? organisations = freezed,
  }) {
    return _then(_value.copyWith(
      organisations: freezed == organisations
          ? _value.organisations
          : organisations // ignore: cast_nullable_to_non_nullable
              as List<OrganisationModel>?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrganisationListModelImplCopyWith<$Res>
    implements $OrganisationListModelCopyWith<$Res> {
  factory _$$OrganisationListModelImplCopyWith(
          _$OrganisationListModelImpl value,
          $Res Function(_$OrganisationListModelImpl) then) =
      __$$OrganisationListModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({List<OrganisationModel>? organisations});
}

/// @nodoc
class __$$OrganisationListModelImplCopyWithImpl<$Res>
    extends _$OrganisationListModelCopyWithImpl<$Res,
        _$OrganisationListModelImpl>
    implements _$$OrganisationListModelImplCopyWith<$Res> {
  __$$OrganisationListModelImplCopyWithImpl(_$OrganisationListModelImpl _value,
      $Res Function(_$OrganisationListModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? organisations = freezed,
  }) {
    return _then(_$OrganisationListModelImpl(
      organisations: freezed == organisations
          ? _value._organisations
          : organisations // ignore: cast_nullable_to_non_nullable
              as List<OrganisationModel>?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrganisationListModelImpl implements _OrganisationListModel {
  const _$OrganisationListModelImpl(
      {final List<OrganisationModel>? organisations})
      : _organisations = organisations;

  factory _$OrganisationListModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrganisationListModelImplFromJson(json);

  final List<OrganisationModel>? _organisations;
  @override
  List<OrganisationModel>? get organisations {
    final value = _organisations;
    if (value == null) return null;
    if (_organisations is EqualUnmodifiableListView) return _organisations;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  String toString() {
    return 'OrganisationListModel(organisations: $organisations)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrganisationListModelImpl &&
            const DeepCollectionEquality()
                .equals(other._organisations, _organisations));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType, const DeepCollectionEquality().hash(_organisations));

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrganisationListModelImplCopyWith<_$OrganisationListModelImpl>
      get copyWith => __$$OrganisationListModelImplCopyWithImpl<
          _$OrganisationListModelImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrganisationListModelImplToJson(
      this,
    );
  }
}

abstract class _OrganisationListModel implements OrganisationListModel {
  const factory _OrganisationListModel(
          {final List<OrganisationModel>? organisations}) =
      _$OrganisationListModelImpl;

  factory _OrganisationListModel.fromJson(Map<String, dynamic> json) =
      _$OrganisationListModelImpl.fromJson;

  @override
  List<OrganisationModel>? get organisations;
  @override
  @JsonKey(ignore: true)
  _$$OrganisationListModelImplCopyWith<_$OrganisationListModelImpl>
      get copyWith => throw _privateConstructorUsedError;
}

OrganisationModel _$OrganisationModelFromJson(Map<String, dynamic> json) {
  return _OrganisationModel.fromJson(json);
}

/// @nodoc
mixin _$OrganisationModel {
  String? get name => throw _privateConstructorUsedError;
  String? get applicationNumber => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get id => throw _privateConstructorUsedError;
  String? get orgNumber => throw _privateConstructorUsedError;
  String? get applicationStatus => throw _privateConstructorUsedError;
  String? get externalRefNumber => throw _privateConstructorUsedError;
  List<OrgAddress>? get orgAddress => throw _privateConstructorUsedError;
  List<OrgContact>? get contactDetails => throw _privateConstructorUsedError;
  List<OrgIdentifier>? get identifiers => throw _privateConstructorUsedError;
  List<OrgFunctions>? get functions => throw _privateConstructorUsedError;
  OrgAdditionalDetails? get additionalDetails =>
      throw _privateConstructorUsedError;
  int? get dateOfIncorporation => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrganisationModelCopyWith<OrganisationModel> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrganisationModelCopyWith<$Res> {
  factory $OrganisationModelCopyWith(
          OrganisationModel value, $Res Function(OrganisationModel) then) =
      _$OrganisationModelCopyWithImpl<$Res, OrganisationModel>;
  @useResult
  $Res call(
      {String? name,
      String? applicationNumber,
      String tenantId,
      String? id,
      String? orgNumber,
      String? applicationStatus,
      String? externalRefNumber,
      List<OrgAddress>? orgAddress,
      List<OrgContact>? contactDetails,
      List<OrgIdentifier>? identifiers,
      List<OrgFunctions>? functions,
      OrgAdditionalDetails? additionalDetails,
      int? dateOfIncorporation});

  $OrgAdditionalDetailsCopyWith<$Res>? get additionalDetails;
}

/// @nodoc
class _$OrganisationModelCopyWithImpl<$Res, $Val extends OrganisationModel>
    implements $OrganisationModelCopyWith<$Res> {
  _$OrganisationModelCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? applicationNumber = freezed,
    Object? tenantId = null,
    Object? id = freezed,
    Object? orgNumber = freezed,
    Object? applicationStatus = freezed,
    Object? externalRefNumber = freezed,
    Object? orgAddress = freezed,
    Object? contactDetails = freezed,
    Object? identifiers = freezed,
    Object? functions = freezed,
    Object? additionalDetails = freezed,
    Object? dateOfIncorporation = freezed,
  }) {
    return _then(_value.copyWith(
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationNumber: freezed == applicationNumber
          ? _value.applicationNumber
          : applicationNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgNumber: freezed == orgNumber
          ? _value.orgNumber
          : orgNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      externalRefNumber: freezed == externalRefNumber
          ? _value.externalRefNumber
          : externalRefNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      orgAddress: freezed == orgAddress
          ? _value.orgAddress
          : orgAddress // ignore: cast_nullable_to_non_nullable
              as List<OrgAddress>?,
      contactDetails: freezed == contactDetails
          ? _value.contactDetails
          : contactDetails // ignore: cast_nullable_to_non_nullable
              as List<OrgContact>?,
      identifiers: freezed == identifiers
          ? _value.identifiers
          : identifiers // ignore: cast_nullable_to_non_nullable
              as List<OrgIdentifier>?,
      functions: freezed == functions
          ? _value.functions
          : functions // ignore: cast_nullable_to_non_nullable
              as List<OrgFunctions>?,
      additionalDetails: freezed == additionalDetails
          ? _value.additionalDetails
          : additionalDetails // ignore: cast_nullable_to_non_nullable
              as OrgAdditionalDetails?,
      dateOfIncorporation: freezed == dateOfIncorporation
          ? _value.dateOfIncorporation
          : dateOfIncorporation // ignore: cast_nullable_to_non_nullable
              as int?,
    ) as $Val);
  }

  @override
  @pragma('vm:prefer-inline')
  $OrgAdditionalDetailsCopyWith<$Res>? get additionalDetails {
    if (_value.additionalDetails == null) {
      return null;
    }

    return $OrgAdditionalDetailsCopyWith<$Res>(_value.additionalDetails!,
        (value) {
      return _then(_value.copyWith(additionalDetails: value) as $Val);
    });
  }
}

/// @nodoc
abstract class _$$OrganisationModelImplCopyWith<$Res>
    implements $OrganisationModelCopyWith<$Res> {
  factory _$$OrganisationModelImplCopyWith(_$OrganisationModelImpl value,
          $Res Function(_$OrganisationModelImpl) then) =
      __$$OrganisationModelImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? name,
      String? applicationNumber,
      String tenantId,
      String? id,
      String? orgNumber,
      String? applicationStatus,
      String? externalRefNumber,
      List<OrgAddress>? orgAddress,
      List<OrgContact>? contactDetails,
      List<OrgIdentifier>? identifiers,
      List<OrgFunctions>? functions,
      OrgAdditionalDetails? additionalDetails,
      int? dateOfIncorporation});

  @override
  $OrgAdditionalDetailsCopyWith<$Res>? get additionalDetails;
}

/// @nodoc
class __$$OrganisationModelImplCopyWithImpl<$Res>
    extends _$OrganisationModelCopyWithImpl<$Res, _$OrganisationModelImpl>
    implements _$$OrganisationModelImplCopyWith<$Res> {
  __$$OrganisationModelImplCopyWithImpl(_$OrganisationModelImpl _value,
      $Res Function(_$OrganisationModelImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? name = freezed,
    Object? applicationNumber = freezed,
    Object? tenantId = null,
    Object? id = freezed,
    Object? orgNumber = freezed,
    Object? applicationStatus = freezed,
    Object? externalRefNumber = freezed,
    Object? orgAddress = freezed,
    Object? contactDetails = freezed,
    Object? identifiers = freezed,
    Object? functions = freezed,
    Object? additionalDetails = freezed,
    Object? dateOfIncorporation = freezed,
  }) {
    return _then(_$OrganisationModelImpl(
      name: freezed == name
          ? _value.name
          : name // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationNumber: freezed == applicationNumber
          ? _value.applicationNumber
          : applicationNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgNumber: freezed == orgNumber
          ? _value.orgNumber
          : orgNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationStatus: freezed == applicationStatus
          ? _value.applicationStatus
          : applicationStatus // ignore: cast_nullable_to_non_nullable
              as String?,
      externalRefNumber: freezed == externalRefNumber
          ? _value.externalRefNumber
          : externalRefNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      orgAddress: freezed == orgAddress
          ? _value._orgAddress
          : orgAddress // ignore: cast_nullable_to_non_nullable
              as List<OrgAddress>?,
      contactDetails: freezed == contactDetails
          ? _value._contactDetails
          : contactDetails // ignore: cast_nullable_to_non_nullable
              as List<OrgContact>?,
      identifiers: freezed == identifiers
          ? _value._identifiers
          : identifiers // ignore: cast_nullable_to_non_nullable
              as List<OrgIdentifier>?,
      functions: freezed == functions
          ? _value._functions
          : functions // ignore: cast_nullable_to_non_nullable
              as List<OrgFunctions>?,
      additionalDetails: freezed == additionalDetails
          ? _value.additionalDetails
          : additionalDetails // ignore: cast_nullable_to_non_nullable
              as OrgAdditionalDetails?,
      dateOfIncorporation: freezed == dateOfIncorporation
          ? _value.dateOfIncorporation
          : dateOfIncorporation // ignore: cast_nullable_to_non_nullable
              as int?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrganisationModelImpl implements _OrganisationModel {
  const _$OrganisationModelImpl(
      {this.name,
      this.applicationNumber,
      required this.tenantId,
      this.id,
      this.orgNumber,
      this.applicationStatus,
      this.externalRefNumber,
      final List<OrgAddress>? orgAddress,
      final List<OrgContact>? contactDetails,
      final List<OrgIdentifier>? identifiers,
      final List<OrgFunctions>? functions,
      this.additionalDetails,
      this.dateOfIncorporation})
      : _orgAddress = orgAddress,
        _contactDetails = contactDetails,
        _identifiers = identifiers,
        _functions = functions;

  factory _$OrganisationModelImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrganisationModelImplFromJson(json);

  @override
  final String? name;
  @override
  final String? applicationNumber;
  @override
  final String tenantId;
  @override
  final String? id;
  @override
  final String? orgNumber;
  @override
  final String? applicationStatus;
  @override
  final String? externalRefNumber;
  final List<OrgAddress>? _orgAddress;
  @override
  List<OrgAddress>? get orgAddress {
    final value = _orgAddress;
    if (value == null) return null;
    if (_orgAddress is EqualUnmodifiableListView) return _orgAddress;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<OrgContact>? _contactDetails;
  @override
  List<OrgContact>? get contactDetails {
    final value = _contactDetails;
    if (value == null) return null;
    if (_contactDetails is EqualUnmodifiableListView) return _contactDetails;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<OrgIdentifier>? _identifiers;
  @override
  List<OrgIdentifier>? get identifiers {
    final value = _identifiers;
    if (value == null) return null;
    if (_identifiers is EqualUnmodifiableListView) return _identifiers;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  final List<OrgFunctions>? _functions;
  @override
  List<OrgFunctions>? get functions {
    final value = _functions;
    if (value == null) return null;
    if (_functions is EqualUnmodifiableListView) return _functions;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(value);
  }

  @override
  final OrgAdditionalDetails? additionalDetails;
  @override
  final int? dateOfIncorporation;

  @override
  String toString() {
    return 'OrganisationModel(name: $name, applicationNumber: $applicationNumber, tenantId: $tenantId, id: $id, orgNumber: $orgNumber, applicationStatus: $applicationStatus, externalRefNumber: $externalRefNumber, orgAddress: $orgAddress, contactDetails: $contactDetails, identifiers: $identifiers, functions: $functions, additionalDetails: $additionalDetails, dateOfIncorporation: $dateOfIncorporation)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrganisationModelImpl &&
            (identical(other.name, name) || other.name == name) &&
            (identical(other.applicationNumber, applicationNumber) ||
                other.applicationNumber == applicationNumber) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgNumber, orgNumber) ||
                other.orgNumber == orgNumber) &&
            (identical(other.applicationStatus, applicationStatus) ||
                other.applicationStatus == applicationStatus) &&
            (identical(other.externalRefNumber, externalRefNumber) ||
                other.externalRefNumber == externalRefNumber) &&
            const DeepCollectionEquality()
                .equals(other._orgAddress, _orgAddress) &&
            const DeepCollectionEquality()
                .equals(other._contactDetails, _contactDetails) &&
            const DeepCollectionEquality()
                .equals(other._identifiers, _identifiers) &&
            const DeepCollectionEquality()
                .equals(other._functions, _functions) &&
            (identical(other.additionalDetails, additionalDetails) ||
                other.additionalDetails == additionalDetails) &&
            (identical(other.dateOfIncorporation, dateOfIncorporation) ||
                other.dateOfIncorporation == dateOfIncorporation));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      name,
      applicationNumber,
      tenantId,
      id,
      orgNumber,
      applicationStatus,
      externalRefNumber,
      const DeepCollectionEquality().hash(_orgAddress),
      const DeepCollectionEquality().hash(_contactDetails),
      const DeepCollectionEquality().hash(_identifiers),
      const DeepCollectionEquality().hash(_functions),
      additionalDetails,
      dateOfIncorporation);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrganisationModelImplCopyWith<_$OrganisationModelImpl> get copyWith =>
      __$$OrganisationModelImplCopyWithImpl<_$OrganisationModelImpl>(
          this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrganisationModelImplToJson(
      this,
    );
  }
}

abstract class _OrganisationModel implements OrganisationModel {
  const factory _OrganisationModel(
      {final String? name,
      final String? applicationNumber,
      required final String tenantId,
      final String? id,
      final String? orgNumber,
      final String? applicationStatus,
      final String? externalRefNumber,
      final List<OrgAddress>? orgAddress,
      final List<OrgContact>? contactDetails,
      final List<OrgIdentifier>? identifiers,
      final List<OrgFunctions>? functions,
      final OrgAdditionalDetails? additionalDetails,
      final int? dateOfIncorporation}) = _$OrganisationModelImpl;

  factory _OrganisationModel.fromJson(Map<String, dynamic> json) =
      _$OrganisationModelImpl.fromJson;

  @override
  String? get name;
  @override
  String? get applicationNumber;
  @override
  String get tenantId;
  @override
  String? get id;
  @override
  String? get orgNumber;
  @override
  String? get applicationStatus;
  @override
  String? get externalRefNumber;
  @override
  List<OrgAddress>? get orgAddress;
  @override
  List<OrgContact>? get contactDetails;
  @override
  List<OrgIdentifier>? get identifiers;
  @override
  List<OrgFunctions>? get functions;
  @override
  OrgAdditionalDetails? get additionalDetails;
  @override
  int? get dateOfIncorporation;
  @override
  @JsonKey(ignore: true)
  _$$OrganisationModelImplCopyWith<_$OrganisationModelImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

OrgAdditionalDetails _$OrgAdditionalDetailsFromJson(Map<String, dynamic> json) {
  return _OrgAdditionalDetails.fromJson(json);
}

/// @nodoc
mixin _$OrgAdditionalDetails {
  String? get registeredByDept => throw _privateConstructorUsedError;
  String? get deptRegistrationNum => throw _privateConstructorUsedError;
  bool? get isLocalityMasked => throw _privateConstructorUsedError;
  String? get locality => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrgAdditionalDetailsCopyWith<OrgAdditionalDetails> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrgAdditionalDetailsCopyWith<$Res> {
  factory $OrgAdditionalDetailsCopyWith(OrgAdditionalDetails value,
          $Res Function(OrgAdditionalDetails) then) =
      _$OrgAdditionalDetailsCopyWithImpl<$Res, OrgAdditionalDetails>;
  @useResult
  $Res call(
      {String? registeredByDept,
      String? deptRegistrationNum,
      bool? isLocalityMasked,
      String? locality});
}

/// @nodoc
class _$OrgAdditionalDetailsCopyWithImpl<$Res,
        $Val extends OrgAdditionalDetails>
    implements $OrgAdditionalDetailsCopyWith<$Res> {
  _$OrgAdditionalDetailsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registeredByDept = freezed,
    Object? deptRegistrationNum = freezed,
    Object? isLocalityMasked = freezed,
    Object? locality = freezed,
  }) {
    return _then(_value.copyWith(
      registeredByDept: freezed == registeredByDept
          ? _value.registeredByDept
          : registeredByDept // ignore: cast_nullable_to_non_nullable
              as String?,
      deptRegistrationNum: freezed == deptRegistrationNum
          ? _value.deptRegistrationNum
          : deptRegistrationNum // ignore: cast_nullable_to_non_nullable
              as String?,
      isLocalityMasked: freezed == isLocalityMasked
          ? _value.isLocalityMasked
          : isLocalityMasked // ignore: cast_nullable_to_non_nullable
              as bool?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrgAdditionalDetailsImplCopyWith<$Res>
    implements $OrgAdditionalDetailsCopyWith<$Res> {
  factory _$$OrgAdditionalDetailsImplCopyWith(_$OrgAdditionalDetailsImpl value,
          $Res Function(_$OrgAdditionalDetailsImpl) then) =
      __$$OrgAdditionalDetailsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? registeredByDept,
      String? deptRegistrationNum,
      bool? isLocalityMasked,
      String? locality});
}

/// @nodoc
class __$$OrgAdditionalDetailsImplCopyWithImpl<$Res>
    extends _$OrgAdditionalDetailsCopyWithImpl<$Res, _$OrgAdditionalDetailsImpl>
    implements _$$OrgAdditionalDetailsImplCopyWith<$Res> {
  __$$OrgAdditionalDetailsImplCopyWithImpl(_$OrgAdditionalDetailsImpl _value,
      $Res Function(_$OrgAdditionalDetailsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? registeredByDept = freezed,
    Object? deptRegistrationNum = freezed,
    Object? isLocalityMasked = freezed,
    Object? locality = freezed,
  }) {
    return _then(_$OrgAdditionalDetailsImpl(
      registeredByDept: freezed == registeredByDept
          ? _value.registeredByDept
          : registeredByDept // ignore: cast_nullable_to_non_nullable
              as String?,
      deptRegistrationNum: freezed == deptRegistrationNum
          ? _value.deptRegistrationNum
          : deptRegistrationNum // ignore: cast_nullable_to_non_nullable
              as String?,
      isLocalityMasked: freezed == isLocalityMasked
          ? _value.isLocalityMasked
          : isLocalityMasked // ignore: cast_nullable_to_non_nullable
              as bool?,
      locality: freezed == locality
          ? _value.locality
          : locality // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrgAdditionalDetailsImpl implements _OrgAdditionalDetails {
  const _$OrgAdditionalDetailsImpl(
      {this.registeredByDept,
      this.deptRegistrationNum,
      this.isLocalityMasked,
      this.locality});

  factory _$OrgAdditionalDetailsImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrgAdditionalDetailsImplFromJson(json);

  @override
  final String? registeredByDept;
  @override
  final String? deptRegistrationNum;
  @override
  final bool? isLocalityMasked;
  @override
  final String? locality;

  @override
  String toString() {
    return 'OrgAdditionalDetails(registeredByDept: $registeredByDept, deptRegistrationNum: $deptRegistrationNum, isLocalityMasked: $isLocalityMasked, locality: $locality)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrgAdditionalDetailsImpl &&
            (identical(other.registeredByDept, registeredByDept) ||
                other.registeredByDept == registeredByDept) &&
            (identical(other.deptRegistrationNum, deptRegistrationNum) ||
                other.deptRegistrationNum == deptRegistrationNum) &&
            (identical(other.isLocalityMasked, isLocalityMasked) ||
                other.isLocalityMasked == isLocalityMasked) &&
            (identical(other.locality, locality) ||
                other.locality == locality));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, registeredByDept,
      deptRegistrationNum, isLocalityMasked, locality);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrgAdditionalDetailsImplCopyWith<_$OrgAdditionalDetailsImpl>
      get copyWith =>
          __$$OrgAdditionalDetailsImplCopyWithImpl<_$OrgAdditionalDetailsImpl>(
              this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrgAdditionalDetailsImplToJson(
      this,
    );
  }
}

abstract class _OrgAdditionalDetails implements OrgAdditionalDetails {
  const factory _OrgAdditionalDetails(
      {final String? registeredByDept,
      final String? deptRegistrationNum,
      final bool? isLocalityMasked,
      final String? locality}) = _$OrgAdditionalDetailsImpl;

  factory _OrgAdditionalDetails.fromJson(Map<String, dynamic> json) =
      _$OrgAdditionalDetailsImpl.fromJson;

  @override
  String? get registeredByDept;
  @override
  String? get deptRegistrationNum;
  @override
  bool? get isLocalityMasked;
  @override
  String? get locality;
  @override
  @JsonKey(ignore: true)
  _$$OrgAdditionalDetailsImplCopyWith<_$OrgAdditionalDetailsImpl>
      get copyWith => throw _privateConstructorUsedError;
}

OrgAddress _$OrgAddressFromJson(Map<String, dynamic> json) {
  return _OrgAddress.fromJson(json);
}

/// @nodoc
mixin _$OrgAddress {
  String? get id => throw _privateConstructorUsedError;
  String? get orgId => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get doorNo => throw _privateConstructorUsedError;
  String? get plotNo => throw _privateConstructorUsedError;
  String? get landmark => throw _privateConstructorUsedError;
  String? get city => throw _privateConstructorUsedError;
  String? get pincode => throw _privateConstructorUsedError;
  String? get district => throw _privateConstructorUsedError;
  String? get region => throw _privateConstructorUsedError;
  String? get state => throw _privateConstructorUsedError;
  String? get country => throw _privateConstructorUsedError;
  String? get buildingName => throw _privateConstructorUsedError;
  String? get street => throw _privateConstructorUsedError;
  String? get boundaryType => throw _privateConstructorUsedError;
  String? get boundaryCode => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrgAddressCopyWith<OrgAddress> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrgAddressCopyWith<$Res> {
  factory $OrgAddressCopyWith(
          OrgAddress value, $Res Function(OrgAddress) then) =
      _$OrgAddressCopyWithImpl<$Res, OrgAddress>;
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String tenantId,
      String? doorNo,
      String? plotNo,
      String? landmark,
      String? city,
      String? pincode,
      String? district,
      String? region,
      String? state,
      String? country,
      String? buildingName,
      String? street,
      String? boundaryType,
      String? boundaryCode});
}

/// @nodoc
class _$OrgAddressCopyWithImpl<$Res, $Val extends OrgAddress>
    implements $OrgAddressCopyWith<$Res> {
  _$OrgAddressCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? tenantId = null,
    Object? doorNo = freezed,
    Object? plotNo = freezed,
    Object? landmark = freezed,
    Object? city = freezed,
    Object? pincode = freezed,
    Object? district = freezed,
    Object? region = freezed,
    Object? state = freezed,
    Object? country = freezed,
    Object? buildingName = freezed,
    Object? street = freezed,
    Object? boundaryType = freezed,
    Object? boundaryCode = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      doorNo: freezed == doorNo
          ? _value.doorNo
          : doorNo // ignore: cast_nullable_to_non_nullable
              as String?,
      plotNo: freezed == plotNo
          ? _value.plotNo
          : plotNo // ignore: cast_nullable_to_non_nullable
              as String?,
      landmark: freezed == landmark
          ? _value.landmark
          : landmark // ignore: cast_nullable_to_non_nullable
              as String?,
      city: freezed == city
          ? _value.city
          : city // ignore: cast_nullable_to_non_nullable
              as String?,
      pincode: freezed == pincode
          ? _value.pincode
          : pincode // ignore: cast_nullable_to_non_nullable
              as String?,
      district: freezed == district
          ? _value.district
          : district // ignore: cast_nullable_to_non_nullable
              as String?,
      region: freezed == region
          ? _value.region
          : region // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      country: freezed == country
          ? _value.country
          : country // ignore: cast_nullable_to_non_nullable
              as String?,
      buildingName: freezed == buildingName
          ? _value.buildingName
          : buildingName // ignore: cast_nullable_to_non_nullable
              as String?,
      street: freezed == street
          ? _value.street
          : street // ignore: cast_nullable_to_non_nullable
              as String?,
      boundaryType: freezed == boundaryType
          ? _value.boundaryType
          : boundaryType // ignore: cast_nullable_to_non_nullable
              as String?,
      boundaryCode: freezed == boundaryCode
          ? _value.boundaryCode
          : boundaryCode // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrgAddressImplCopyWith<$Res>
    implements $OrgAddressCopyWith<$Res> {
  factory _$$OrgAddressImplCopyWith(
          _$OrgAddressImpl value, $Res Function(_$OrgAddressImpl) then) =
      __$$OrgAddressImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String tenantId,
      String? doorNo,
      String? plotNo,
      String? landmark,
      String? city,
      String? pincode,
      String? district,
      String? region,
      String? state,
      String? country,
      String? buildingName,
      String? street,
      String? boundaryType,
      String? boundaryCode});
}

/// @nodoc
class __$$OrgAddressImplCopyWithImpl<$Res>
    extends _$OrgAddressCopyWithImpl<$Res, _$OrgAddressImpl>
    implements _$$OrgAddressImplCopyWith<$Res> {
  __$$OrgAddressImplCopyWithImpl(
      _$OrgAddressImpl _value, $Res Function(_$OrgAddressImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? tenantId = null,
    Object? doorNo = freezed,
    Object? plotNo = freezed,
    Object? landmark = freezed,
    Object? city = freezed,
    Object? pincode = freezed,
    Object? district = freezed,
    Object? region = freezed,
    Object? state = freezed,
    Object? country = freezed,
    Object? buildingName = freezed,
    Object? street = freezed,
    Object? boundaryType = freezed,
    Object? boundaryCode = freezed,
  }) {
    return _then(_$OrgAddressImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      doorNo: freezed == doorNo
          ? _value.doorNo
          : doorNo // ignore: cast_nullable_to_non_nullable
              as String?,
      plotNo: freezed == plotNo
          ? _value.plotNo
          : plotNo // ignore: cast_nullable_to_non_nullable
              as String?,
      landmark: freezed == landmark
          ? _value.landmark
          : landmark // ignore: cast_nullable_to_non_nullable
              as String?,
      city: freezed == city
          ? _value.city
          : city // ignore: cast_nullable_to_non_nullable
              as String?,
      pincode: freezed == pincode
          ? _value.pincode
          : pincode // ignore: cast_nullable_to_non_nullable
              as String?,
      district: freezed == district
          ? _value.district
          : district // ignore: cast_nullable_to_non_nullable
              as String?,
      region: freezed == region
          ? _value.region
          : region // ignore: cast_nullable_to_non_nullable
              as String?,
      state: freezed == state
          ? _value.state
          : state // ignore: cast_nullable_to_non_nullable
              as String?,
      country: freezed == country
          ? _value.country
          : country // ignore: cast_nullable_to_non_nullable
              as String?,
      buildingName: freezed == buildingName
          ? _value.buildingName
          : buildingName // ignore: cast_nullable_to_non_nullable
              as String?,
      street: freezed == street
          ? _value.street
          : street // ignore: cast_nullable_to_non_nullable
              as String?,
      boundaryType: freezed == boundaryType
          ? _value.boundaryType
          : boundaryType // ignore: cast_nullable_to_non_nullable
              as String?,
      boundaryCode: freezed == boundaryCode
          ? _value.boundaryCode
          : boundaryCode // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrgAddressImpl implements _OrgAddress {
  const _$OrgAddressImpl(
      {this.id,
      this.orgId,
      required this.tenantId,
      this.doorNo,
      this.plotNo,
      this.landmark,
      this.city,
      this.pincode,
      this.district,
      this.region,
      this.state,
      this.country,
      this.buildingName,
      this.street,
      this.boundaryType,
      this.boundaryCode});

  factory _$OrgAddressImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrgAddressImplFromJson(json);

  @override
  final String? id;
  @override
  final String? orgId;
  @override
  final String tenantId;
  @override
  final String? doorNo;
  @override
  final String? plotNo;
  @override
  final String? landmark;
  @override
  final String? city;
  @override
  final String? pincode;
  @override
  final String? district;
  @override
  final String? region;
  @override
  final String? state;
  @override
  final String? country;
  @override
  final String? buildingName;
  @override
  final String? street;
  @override
  final String? boundaryType;
  @override
  final String? boundaryCode;

  @override
  String toString() {
    return 'OrgAddress(id: $id, orgId: $orgId, tenantId: $tenantId, doorNo: $doorNo, plotNo: $plotNo, landmark: $landmark, city: $city, pincode: $pincode, district: $district, region: $region, state: $state, country: $country, buildingName: $buildingName, street: $street, boundaryType: $boundaryType, boundaryCode: $boundaryCode)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrgAddressImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgId, orgId) || other.orgId == orgId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.doorNo, doorNo) || other.doorNo == doorNo) &&
            (identical(other.plotNo, plotNo) || other.plotNo == plotNo) &&
            (identical(other.landmark, landmark) ||
                other.landmark == landmark) &&
            (identical(other.city, city) || other.city == city) &&
            (identical(other.pincode, pincode) || other.pincode == pincode) &&
            (identical(other.district, district) ||
                other.district == district) &&
            (identical(other.region, region) || other.region == region) &&
            (identical(other.state, state) || other.state == state) &&
            (identical(other.country, country) || other.country == country) &&
            (identical(other.buildingName, buildingName) ||
                other.buildingName == buildingName) &&
            (identical(other.street, street) || other.street == street) &&
            (identical(other.boundaryType, boundaryType) ||
                other.boundaryType == boundaryType) &&
            (identical(other.boundaryCode, boundaryCode) ||
                other.boundaryCode == boundaryCode));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(
      runtimeType,
      id,
      orgId,
      tenantId,
      doorNo,
      plotNo,
      landmark,
      city,
      pincode,
      district,
      region,
      state,
      country,
      buildingName,
      street,
      boundaryType,
      boundaryCode);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrgAddressImplCopyWith<_$OrgAddressImpl> get copyWith =>
      __$$OrgAddressImplCopyWithImpl<_$OrgAddressImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrgAddressImplToJson(
      this,
    );
  }
}

abstract class _OrgAddress implements OrgAddress {
  const factory _OrgAddress(
      {final String? id,
      final String? orgId,
      required final String tenantId,
      final String? doorNo,
      final String? plotNo,
      final String? landmark,
      final String? city,
      final String? pincode,
      final String? district,
      final String? region,
      final String? state,
      final String? country,
      final String? buildingName,
      final String? street,
      final String? boundaryType,
      final String? boundaryCode}) = _$OrgAddressImpl;

  factory _OrgAddress.fromJson(Map<String, dynamic> json) =
      _$OrgAddressImpl.fromJson;

  @override
  String? get id;
  @override
  String? get orgId;
  @override
  String get tenantId;
  @override
  String? get doorNo;
  @override
  String? get plotNo;
  @override
  String? get landmark;
  @override
  String? get city;
  @override
  String? get pincode;
  @override
  String? get district;
  @override
  String? get region;
  @override
  String? get state;
  @override
  String? get country;
  @override
  String? get buildingName;
  @override
  String? get street;
  @override
  String? get boundaryType;
  @override
  String? get boundaryCode;
  @override
  @JsonKey(ignore: true)
  _$$OrgAddressImplCopyWith<_$OrgAddressImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

OrgContact _$OrgContactFromJson(Map<String, dynamic> json) {
  return _OrgContact.fromJson(json);
}

/// @nodoc
mixin _$OrgContact {
  String? get id => throw _privateConstructorUsedError;
  String? get orgId => throw _privateConstructorUsedError;
  String get tenantId => throw _privateConstructorUsedError;
  String? get contactName => throw _privateConstructorUsedError;
  String? get contactMobileNumber => throw _privateConstructorUsedError;
  String? get contactEmail => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrgContactCopyWith<OrgContact> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrgContactCopyWith<$Res> {
  factory $OrgContactCopyWith(
          OrgContact value, $Res Function(OrgContact) then) =
      _$OrgContactCopyWithImpl<$Res, OrgContact>;
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String tenantId,
      String? contactName,
      String? contactMobileNumber,
      String? contactEmail});
}

/// @nodoc
class _$OrgContactCopyWithImpl<$Res, $Val extends OrgContact>
    implements $OrgContactCopyWith<$Res> {
  _$OrgContactCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? tenantId = null,
    Object? contactName = freezed,
    Object? contactMobileNumber = freezed,
    Object? contactEmail = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      contactName: freezed == contactName
          ? _value.contactName
          : contactName // ignore: cast_nullable_to_non_nullable
              as String?,
      contactMobileNumber: freezed == contactMobileNumber
          ? _value.contactMobileNumber
          : contactMobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      contactEmail: freezed == contactEmail
          ? _value.contactEmail
          : contactEmail // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrgContactImplCopyWith<$Res>
    implements $OrgContactCopyWith<$Res> {
  factory _$$OrgContactImplCopyWith(
          _$OrgContactImpl value, $Res Function(_$OrgContactImpl) then) =
      __$$OrgContactImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String tenantId,
      String? contactName,
      String? contactMobileNumber,
      String? contactEmail});
}

/// @nodoc
class __$$OrgContactImplCopyWithImpl<$Res>
    extends _$OrgContactCopyWithImpl<$Res, _$OrgContactImpl>
    implements _$$OrgContactImplCopyWith<$Res> {
  __$$OrgContactImplCopyWithImpl(
      _$OrgContactImpl _value, $Res Function(_$OrgContactImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? tenantId = null,
    Object? contactName = freezed,
    Object? contactMobileNumber = freezed,
    Object? contactEmail = freezed,
  }) {
    return _then(_$OrgContactImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      tenantId: null == tenantId
          ? _value.tenantId
          : tenantId // ignore: cast_nullable_to_non_nullable
              as String,
      contactName: freezed == contactName
          ? _value.contactName
          : contactName // ignore: cast_nullable_to_non_nullable
              as String?,
      contactMobileNumber: freezed == contactMobileNumber
          ? _value.contactMobileNumber
          : contactMobileNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      contactEmail: freezed == contactEmail
          ? _value.contactEmail
          : contactEmail // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrgContactImpl implements _OrgContact {
  const _$OrgContactImpl(
      {this.id,
      this.orgId,
      required this.tenantId,
      this.contactName,
      this.contactMobileNumber,
      this.contactEmail});

  factory _$OrgContactImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrgContactImplFromJson(json);

  @override
  final String? id;
  @override
  final String? orgId;
  @override
  final String tenantId;
  @override
  final String? contactName;
  @override
  final String? contactMobileNumber;
  @override
  final String? contactEmail;

  @override
  String toString() {
    return 'OrgContact(id: $id, orgId: $orgId, tenantId: $tenantId, contactName: $contactName, contactMobileNumber: $contactMobileNumber, contactEmail: $contactEmail)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrgContactImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgId, orgId) || other.orgId == orgId) &&
            (identical(other.tenantId, tenantId) ||
                other.tenantId == tenantId) &&
            (identical(other.contactName, contactName) ||
                other.contactName == contactName) &&
            (identical(other.contactMobileNumber, contactMobileNumber) ||
                other.contactMobileNumber == contactMobileNumber) &&
            (identical(other.contactEmail, contactEmail) ||
                other.contactEmail == contactEmail));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, orgId, tenantId, contactName,
      contactMobileNumber, contactEmail);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrgContactImplCopyWith<_$OrgContactImpl> get copyWith =>
      __$$OrgContactImplCopyWithImpl<_$OrgContactImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrgContactImplToJson(
      this,
    );
  }
}

abstract class _OrgContact implements OrgContact {
  const factory _OrgContact(
      {final String? id,
      final String? orgId,
      required final String tenantId,
      final String? contactName,
      final String? contactMobileNumber,
      final String? contactEmail}) = _$OrgContactImpl;

  factory _OrgContact.fromJson(Map<String, dynamic> json) =
      _$OrgContactImpl.fromJson;

  @override
  String? get id;
  @override
  String? get orgId;
  @override
  String get tenantId;
  @override
  String? get contactName;
  @override
  String? get contactMobileNumber;
  @override
  String? get contactEmail;
  @override
  @JsonKey(ignore: true)
  _$$OrgContactImplCopyWith<_$OrgContactImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

OrgIdentifier _$OrgIdentifierFromJson(Map<String, dynamic> json) {
  return _OrgIdentifier.fromJson(json);
}

/// @nodoc
mixin _$OrgIdentifier {
  String? get id => throw _privateConstructorUsedError;
  String? get orgId => throw _privateConstructorUsedError;
  String? get type => throw _privateConstructorUsedError;
  String? get value => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrgIdentifierCopyWith<OrgIdentifier> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrgIdentifierCopyWith<$Res> {
  factory $OrgIdentifierCopyWith(
          OrgIdentifier value, $Res Function(OrgIdentifier) then) =
      _$OrgIdentifierCopyWithImpl<$Res, OrgIdentifier>;
  @useResult
  $Res call({String? id, String? orgId, String? type, String? value});
}

/// @nodoc
class _$OrgIdentifierCopyWithImpl<$Res, $Val extends OrgIdentifier>
    implements $OrgIdentifierCopyWith<$Res> {
  _$OrgIdentifierCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? type = freezed,
    Object? value = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      value: freezed == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrgIdentifierImplCopyWith<$Res>
    implements $OrgIdentifierCopyWith<$Res> {
  factory _$$OrgIdentifierImplCopyWith(
          _$OrgIdentifierImpl value, $Res Function(_$OrgIdentifierImpl) then) =
      __$$OrgIdentifierImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call({String? id, String? orgId, String? type, String? value});
}

/// @nodoc
class __$$OrgIdentifierImplCopyWithImpl<$Res>
    extends _$OrgIdentifierCopyWithImpl<$Res, _$OrgIdentifierImpl>
    implements _$$OrgIdentifierImplCopyWith<$Res> {
  __$$OrgIdentifierImplCopyWithImpl(
      _$OrgIdentifierImpl _value, $Res Function(_$OrgIdentifierImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? type = freezed,
    Object? value = freezed,
  }) {
    return _then(_$OrgIdentifierImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      value: freezed == value
          ? _value.value
          : value // ignore: cast_nullable_to_non_nullable
              as String?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrgIdentifierImpl implements _OrgIdentifier {
  const _$OrgIdentifierImpl({this.id, this.orgId, this.type, this.value});

  factory _$OrgIdentifierImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrgIdentifierImplFromJson(json);

  @override
  final String? id;
  @override
  final String? orgId;
  @override
  final String? type;
  @override
  final String? value;

  @override
  String toString() {
    return 'OrgIdentifier(id: $id, orgId: $orgId, type: $type, value: $value)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrgIdentifierImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgId, orgId) || other.orgId == orgId) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.value, value) || other.value == value));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, orgId, type, value);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrgIdentifierImplCopyWith<_$OrgIdentifierImpl> get copyWith =>
      __$$OrgIdentifierImplCopyWithImpl<_$OrgIdentifierImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrgIdentifierImplToJson(
      this,
    );
  }
}

abstract class _OrgIdentifier implements OrgIdentifier {
  const factory _OrgIdentifier(
      {final String? id,
      final String? orgId,
      final String? type,
      final String? value}) = _$OrgIdentifierImpl;

  factory _OrgIdentifier.fromJson(Map<String, dynamic> json) =
      _$OrgIdentifierImpl.fromJson;

  @override
  String? get id;
  @override
  String? get orgId;
  @override
  String? get type;
  @override
  String? get value;
  @override
  @JsonKey(ignore: true)
  _$$OrgIdentifierImplCopyWith<_$OrgIdentifierImpl> get copyWith =>
      throw _privateConstructorUsedError;
}

OrgFunctions _$OrgFunctionsFromJson(Map<String, dynamic> json) {
  return _OrgFunctions.fromJson(json);
}

/// @nodoc
mixin _$OrgFunctions {
  String? get id => throw _privateConstructorUsedError;
  String? get orgId => throw _privateConstructorUsedError;
  String? get applicationNumber => throw _privateConstructorUsedError;
  String? get type => throw _privateConstructorUsedError;
  String? get organisationType => throw _privateConstructorUsedError;
  String? get category => throw _privateConstructorUsedError;
  @JsonKey(name: 'class')
  String? get orgClass => throw _privateConstructorUsedError;
  int? get validFrom => throw _privateConstructorUsedError;
  int? get validTo => throw _privateConstructorUsedError;
  bool? get isActive => throw _privateConstructorUsedError;

  Map<String, dynamic> toJson() => throw _privateConstructorUsedError;
  @JsonKey(ignore: true)
  $OrgFunctionsCopyWith<OrgFunctions> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $OrgFunctionsCopyWith<$Res> {
  factory $OrgFunctionsCopyWith(
          OrgFunctions value, $Res Function(OrgFunctions) then) =
      _$OrgFunctionsCopyWithImpl<$Res, OrgFunctions>;
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String? applicationNumber,
      String? type,
      String? organisationType,
      String? category,
      @JsonKey(name: 'class') String? orgClass,
      int? validFrom,
      int? validTo,
      bool? isActive});
}

/// @nodoc
class _$OrgFunctionsCopyWithImpl<$Res, $Val extends OrgFunctions>
    implements $OrgFunctionsCopyWith<$Res> {
  _$OrgFunctionsCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? applicationNumber = freezed,
    Object? type = freezed,
    Object? organisationType = freezed,
    Object? category = freezed,
    Object? orgClass = freezed,
    Object? validFrom = freezed,
    Object? validTo = freezed,
    Object? isActive = freezed,
  }) {
    return _then(_value.copyWith(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationNumber: freezed == applicationNumber
          ? _value.applicationNumber
          : applicationNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      organisationType: freezed == organisationType
          ? _value.organisationType
          : organisationType // ignore: cast_nullable_to_non_nullable
              as String?,
      category: freezed == category
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as String?,
      orgClass: freezed == orgClass
          ? _value.orgClass
          : orgClass // ignore: cast_nullable_to_non_nullable
              as String?,
      validFrom: freezed == validFrom
          ? _value.validFrom
          : validFrom // ignore: cast_nullable_to_non_nullable
              as int?,
      validTo: freezed == validTo
          ? _value.validTo
          : validTo // ignore: cast_nullable_to_non_nullable
              as int?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$OrgFunctionsImplCopyWith<$Res>
    implements $OrgFunctionsCopyWith<$Res> {
  factory _$$OrgFunctionsImplCopyWith(
          _$OrgFunctionsImpl value, $Res Function(_$OrgFunctionsImpl) then) =
      __$$OrgFunctionsImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {String? id,
      String? orgId,
      String? applicationNumber,
      String? type,
      String? organisationType,
      String? category,
      @JsonKey(name: 'class') String? orgClass,
      int? validFrom,
      int? validTo,
      bool? isActive});
}

/// @nodoc
class __$$OrgFunctionsImplCopyWithImpl<$Res>
    extends _$OrgFunctionsCopyWithImpl<$Res, _$OrgFunctionsImpl>
    implements _$$OrgFunctionsImplCopyWith<$Res> {
  __$$OrgFunctionsImplCopyWithImpl(
      _$OrgFunctionsImpl _value, $Res Function(_$OrgFunctionsImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? id = freezed,
    Object? orgId = freezed,
    Object? applicationNumber = freezed,
    Object? type = freezed,
    Object? organisationType = freezed,
    Object? category = freezed,
    Object? orgClass = freezed,
    Object? validFrom = freezed,
    Object? validTo = freezed,
    Object? isActive = freezed,
  }) {
    return _then(_$OrgFunctionsImpl(
      id: freezed == id
          ? _value.id
          : id // ignore: cast_nullable_to_non_nullable
              as String?,
      orgId: freezed == orgId
          ? _value.orgId
          : orgId // ignore: cast_nullable_to_non_nullable
              as String?,
      applicationNumber: freezed == applicationNumber
          ? _value.applicationNumber
          : applicationNumber // ignore: cast_nullable_to_non_nullable
              as String?,
      type: freezed == type
          ? _value.type
          : type // ignore: cast_nullable_to_non_nullable
              as String?,
      organisationType: freezed == organisationType
          ? _value.organisationType
          : organisationType // ignore: cast_nullable_to_non_nullable
              as String?,
      category: freezed == category
          ? _value.category
          : category // ignore: cast_nullable_to_non_nullable
              as String?,
      orgClass: freezed == orgClass
          ? _value.orgClass
          : orgClass // ignore: cast_nullable_to_non_nullable
              as String?,
      validFrom: freezed == validFrom
          ? _value.validFrom
          : validFrom // ignore: cast_nullable_to_non_nullable
              as int?,
      validTo: freezed == validTo
          ? _value.validTo
          : validTo // ignore: cast_nullable_to_non_nullable
              as int?,
      isActive: freezed == isActive
          ? _value.isActive
          : isActive // ignore: cast_nullable_to_non_nullable
              as bool?,
    ));
  }
}

/// @nodoc
@JsonSerializable()
class _$OrgFunctionsImpl implements _OrgFunctions {
  const _$OrgFunctionsImpl(
      {this.id,
      this.orgId,
      this.applicationNumber,
      this.type,
      this.organisationType,
      this.category,
      @JsonKey(name: 'class') this.orgClass,
      this.validFrom,
      this.validTo,
      this.isActive});

  factory _$OrgFunctionsImpl.fromJson(Map<String, dynamic> json) =>
      _$$OrgFunctionsImplFromJson(json);

  @override
  final String? id;
  @override
  final String? orgId;
  @override
  final String? applicationNumber;
  @override
  final String? type;
  @override
  final String? organisationType;
  @override
  final String? category;
  @override
  @JsonKey(name: 'class')
  final String? orgClass;
  @override
  final int? validFrom;
  @override
  final int? validTo;
  @override
  final bool? isActive;

  @override
  String toString() {
    return 'OrgFunctions(id: $id, orgId: $orgId, applicationNumber: $applicationNumber, type: $type, organisationType: $organisationType, category: $category, orgClass: $orgClass, validFrom: $validFrom, validTo: $validTo, isActive: $isActive)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$OrgFunctionsImpl &&
            (identical(other.id, id) || other.id == id) &&
            (identical(other.orgId, orgId) || other.orgId == orgId) &&
            (identical(other.applicationNumber, applicationNumber) ||
                other.applicationNumber == applicationNumber) &&
            (identical(other.type, type) || other.type == type) &&
            (identical(other.organisationType, organisationType) ||
                other.organisationType == organisationType) &&
            (identical(other.category, category) ||
                other.category == category) &&
            (identical(other.orgClass, orgClass) ||
                other.orgClass == orgClass) &&
            (identical(other.validFrom, validFrom) ||
                other.validFrom == validFrom) &&
            (identical(other.validTo, validTo) || other.validTo == validTo) &&
            (identical(other.isActive, isActive) ||
                other.isActive == isActive));
  }

  @JsonKey(ignore: true)
  @override
  int get hashCode => Object.hash(runtimeType, id, orgId, applicationNumber,
      type, organisationType, category, orgClass, validFrom, validTo, isActive);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$OrgFunctionsImplCopyWith<_$OrgFunctionsImpl> get copyWith =>
      __$$OrgFunctionsImplCopyWithImpl<_$OrgFunctionsImpl>(this, _$identity);

  @override
  Map<String, dynamic> toJson() {
    return _$$OrgFunctionsImplToJson(
      this,
    );
  }
}

abstract class _OrgFunctions implements OrgFunctions {
  const factory _OrgFunctions(
      {final String? id,
      final String? orgId,
      final String? applicationNumber,
      final String? type,
      final String? organisationType,
      final String? category,
      @JsonKey(name: 'class') final String? orgClass,
      final int? validFrom,
      final int? validTo,
      final bool? isActive}) = _$OrgFunctionsImpl;

  factory _OrgFunctions.fromJson(Map<String, dynamic> json) =
      _$OrgFunctionsImpl.fromJson;

  @override
  String? get id;
  @override
  String? get orgId;
  @override
  String? get applicationNumber;
  @override
  String? get type;
  @override
  String? get organisationType;
  @override
  String? get category;
  @override
  @JsonKey(name: 'class')
  String? get orgClass;
  @override
  int? get validFrom;
  @override
  int? get validTo;
  @override
  bool? get isActive;
  @override
  @JsonKey(ignore: true)
  _$$OrgFunctionsImplCopyWith<_$OrgFunctionsImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
