// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, unnecessary_cast, override_on_non_overriding_member
// ignore_for_file: strict_raw_type, inference_failure_on_untyped_parameter

part of 'individual_list_model.dart';

class IndividualListModelMapper extends ClassMapperBase<IndividualListModel> {
  IndividualListModelMapper._();

  static IndividualListModelMapper? _instance;
  static IndividualListModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualListModelMapper._());
      IndividualModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualListModel';

  static List<IndividualModel>? _$Individual(IndividualListModel v) =>
      v.Individual;
  static const Field<IndividualListModel, List<IndividualModel>> _f$Individual =
      Field('Individual', _$Individual, opt: true);

  @override
  final MappableFields<IndividualListModel> fields = const {
    #Individual: _f$Individual,
  };

  static IndividualListModel _instantiate(DecodingData data) {
    return IndividualListModel(Individual: data.dec(_f$Individual));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualListModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualListModel>(map);
  }

  static IndividualListModel fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualListModel>(json);
  }
}

mixin IndividualListModelMappable {
  String toJson() {
    return IndividualListModelMapper.ensureInitialized()
        .encodeJson<IndividualListModel>(this as IndividualListModel);
  }

  Map<String, dynamic> toMap() {
    return IndividualListModelMapper.ensureInitialized()
        .encodeMap<IndividualListModel>(this as IndividualListModel);
  }

  IndividualListModelCopyWith<IndividualListModel, IndividualListModel,
          IndividualListModel>
      get copyWith => _IndividualListModelCopyWithImpl(
          this as IndividualListModel, $identity, $identity);
  @override
  String toString() {
    return IndividualListModelMapper.ensureInitialized()
        .stringifyValue(this as IndividualListModel);
  }

  @override
  bool operator ==(Object other) {
    return IndividualListModelMapper.ensureInitialized()
        .equalsValue(this as IndividualListModel, other);
  }

  @override
  int get hashCode {
    return IndividualListModelMapper.ensureInitialized()
        .hashValue(this as IndividualListModel);
  }
}

extension IndividualListModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualListModel, $Out> {
  IndividualListModelCopyWith<$R, IndividualListModel, $Out>
      get $asIndividualListModel =>
          $base.as((v, t, t2) => _IndividualListModelCopyWithImpl(v, t, t2));
}

abstract class IndividualListModelCopyWith<$R, $In extends IndividualListModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<$R, IndividualModel,
          IndividualModelCopyWith<$R, IndividualModel, IndividualModel>>?
      get Individual;
  $R call({List<IndividualModel>? Individual});
  IndividualListModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualListModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualListModel, $Out>
    implements IndividualListModelCopyWith<$R, IndividualListModel, $Out> {
  _IndividualListModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualListModel> $mapper =
      IndividualListModelMapper.ensureInitialized();
  @override
  ListCopyWith<$R, IndividualModel,
          IndividualModelCopyWith<$R, IndividualModel, IndividualModel>>?
      get Individual => $value.Individual != null
          ? ListCopyWith($value.Individual!, (v, t) => v.copyWith.$chain(t),
              (v) => call(Individual: v))
          : null;
  @override
  $R call({Object? Individual = $none}) => $apply(
      FieldCopyWithData({if (Individual != $none) #Individual: Individual}));
  @override
  IndividualListModel $make(CopyWithData data) => IndividualListModel(
      Individual: data.get(#Individual, or: $value.Individual));

  @override
  IndividualListModelCopyWith<$R2, IndividualListModel, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _IndividualListModelCopyWithImpl($value, $cast, t);
}

class IndividualModelMapper extends ClassMapperBase<IndividualModel> {
  IndividualModelMapper._();

  static IndividualModelMapper? _instance;
  static IndividualModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualModelMapper._());
      IndividualNameMapper.ensureInitialized();
      CommonAuditDetailsMapper.ensureInitialized();
      IndividualSkillsMapper.ensureInitialized();
      IndividualAdditionalFieldsMapper.ensureInitialized();
      IndividualAddressMapper.ensureInitialized();
      IndividualIdentifiersMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualModel';

  static String? _$id(IndividualModel v) => v.id;
  static const Field<IndividualModel, String> _f$id =
      Field('id', _$id, opt: true);
  static IndividualName? _$name(IndividualModel v) => v.name;
  static const Field<IndividualModel, IndividualName> _f$name =
      Field('name', _$name, opt: true);
  static String _$tenantId(IndividualModel v) => v.tenantId;
  static const Field<IndividualModel, String> _f$tenantId =
      Field('tenantId', _$tenantId);
  static CommonAuditDetails? _$auditDetails(IndividualModel v) =>
      v.auditDetails;
  static const Field<IndividualModel, CommonAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static List<IndividualSkills>? _$skills(IndividualModel v) => v.skills;
  static const Field<IndividualModel, List<IndividualSkills>> _f$skills =
      Field('skills', _$skills, opt: true);
  static String? _$mobileNumber(IndividualModel v) => v.mobileNumber;
  static const Field<IndividualModel, String> _f$mobileNumber =
      Field('mobileNumber', _$mobileNumber, opt: true);
  static String? _$individualId(IndividualModel v) => v.individualId;
  static const Field<IndividualModel, String> _f$individualId =
      Field('individualId', _$individualId, opt: true);
  static String? _$gender(IndividualModel v) => v.gender;
  static const Field<IndividualModel, String> _f$gender =
      Field('gender', _$gender, opt: true);
  static String? _$userId(IndividualModel v) => v.userId;
  static const Field<IndividualModel, String> _f$userId =
      Field('userId', _$userId, opt: true);
  static IndividualAdditionalFields? _$additionalFields(IndividualModel v) =>
      v.additionalFields;
  static const Field<IndividualModel, IndividualAdditionalFields>
      _f$additionalFields =
      Field('additionalFields', _$additionalFields, opt: true);
  static List<IndividualAddress>? _$address(IndividualModel v) => v.address;
  static const Field<IndividualModel, List<IndividualAddress>> _f$address =
      Field('address', _$address, opt: true);
  static String? _$altContactNumber(IndividualModel v) => v.altContactNumber;
  static const Field<IndividualModel, String> _f$altContactNumber =
      Field('altContactNumber', _$altContactNumber, opt: true);
  static String? _$bloodGroup(IndividualModel v) => v.bloodGroup;
  static const Field<IndividualModel, String> _f$bloodGroup =
      Field('bloodGroup', _$bloodGroup, opt: true);
  static String? _$clientReferenceId(IndividualModel v) => v.clientReferenceId;
  static const Field<IndividualModel, String> _f$clientReferenceId =
      Field('clientReferenceId', _$clientReferenceId, opt: true);
  static String? _$dateOfBirth(IndividualModel v) => v.dateOfBirth;
  static const Field<IndividualModel, String> _f$dateOfBirth =
      Field('dateOfBirth', _$dateOfBirth, opt: true);
  static String? _$email(IndividualModel v) => v.email;
  static const Field<IndividualModel, String> _f$email =
      Field('email', _$email, opt: true);
  static String? _$fatherName(IndividualModel v) => v.fatherName;
  static const Field<IndividualModel, String> _f$fatherName =
      Field('fatherName', _$fatherName, opt: true);
  static String? _$husbandName(IndividualModel v) => v.husbandName;
  static const Field<IndividualModel, String> _f$husbandName =
      Field('husbandName', _$husbandName, opt: true);
  static List<IndividualIdentifiers>? _$identifiers(IndividualModel v) =>
      v.identifiers;
  static const Field<IndividualModel, List<IndividualIdentifiers>>
      _f$identifiers = Field('identifiers', _$identifiers, opt: true);
  static bool? _$isDeleted(IndividualModel v) => v.isDeleted;
  static const Field<IndividualModel, bool> _f$isDeleted =
      Field('isDeleted', _$isDeleted, opt: true);
  static String? _$photo(IndividualModel v) => v.photo;
  static const Field<IndividualModel, String> _f$photo =
      Field('photo', _$photo, opt: true);
  static String? _$relationship(IndividualModel v) => v.relationship;
  static const Field<IndividualModel, String> _f$relationship =
      Field('relationship', _$relationship, opt: true);
  static int? _$rowVersion(IndividualModel v) => v.rowVersion;
  static const Field<IndividualModel, int> _f$rowVersion =
      Field('rowVersion', _$rowVersion, opt: true);

  @override
  final MappableFields<IndividualModel> fields = const {
    #id: _f$id,
    #name: _f$name,
    #tenantId: _f$tenantId,
    #auditDetails: _f$auditDetails,
    #skills: _f$skills,
    #mobileNumber: _f$mobileNumber,
    #individualId: _f$individualId,
    #gender: _f$gender,
    #userId: _f$userId,
    #additionalFields: _f$additionalFields,
    #address: _f$address,
    #altContactNumber: _f$altContactNumber,
    #bloodGroup: _f$bloodGroup,
    #clientReferenceId: _f$clientReferenceId,
    #dateOfBirth: _f$dateOfBirth,
    #email: _f$email,
    #fatherName: _f$fatherName,
    #husbandName: _f$husbandName,
    #identifiers: _f$identifiers,
    #isDeleted: _f$isDeleted,
    #photo: _f$photo,
    #relationship: _f$relationship,
    #rowVersion: _f$rowVersion,
  };

  static IndividualModel _instantiate(DecodingData data) {
    return IndividualModel(
        id: data.dec(_f$id),
        name: data.dec(_f$name),
        tenantId: data.dec(_f$tenantId),
        auditDetails: data.dec(_f$auditDetails),
        skills: data.dec(_f$skills),
        mobileNumber: data.dec(_f$mobileNumber),
        individualId: data.dec(_f$individualId),
        gender: data.dec(_f$gender),
        userId: data.dec(_f$userId),
        additionalFields: data.dec(_f$additionalFields),
        address: data.dec(_f$address),
        altContactNumber: data.dec(_f$altContactNumber),
        bloodGroup: data.dec(_f$bloodGroup),
        clientReferenceId: data.dec(_f$clientReferenceId),
        dateOfBirth: data.dec(_f$dateOfBirth),
        email: data.dec(_f$email),
        fatherName: data.dec(_f$fatherName),
        husbandName: data.dec(_f$husbandName),
        identifiers: data.dec(_f$identifiers),
        isDeleted: data.dec(_f$isDeleted),
        photo: data.dec(_f$photo),
        relationship: data.dec(_f$relationship),
        rowVersion: data.dec(_f$rowVersion));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualModel>(map);
  }

  static IndividualModel fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualModel>(json);
  }
}

mixin IndividualModelMappable {
  String toJson() {
    return IndividualModelMapper.ensureInitialized()
        .encodeJson<IndividualModel>(this as IndividualModel);
  }

  Map<String, dynamic> toMap() {
    return IndividualModelMapper.ensureInitialized()
        .encodeMap<IndividualModel>(this as IndividualModel);
  }

  IndividualModelCopyWith<IndividualModel, IndividualModel, IndividualModel>
      get copyWith => _IndividualModelCopyWithImpl(
          this as IndividualModel, $identity, $identity);
  @override
  String toString() {
    return IndividualModelMapper.ensureInitialized()
        .stringifyValue(this as IndividualModel);
  }

  @override
  bool operator ==(Object other) {
    return IndividualModelMapper.ensureInitialized()
        .equalsValue(this as IndividualModel, other);
  }

  @override
  int get hashCode {
    return IndividualModelMapper.ensureInitialized()
        .hashValue(this as IndividualModel);
  }
}

extension IndividualModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualModel, $Out> {
  IndividualModelCopyWith<$R, IndividualModel, $Out> get $asIndividualModel =>
      $base.as((v, t, t2) => _IndividualModelCopyWithImpl(v, t, t2));
}

abstract class IndividualModelCopyWith<$R, $In extends IndividualModel, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  IndividualNameCopyWith<$R, IndividualName, IndividualName>? get name;
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails;
  ListCopyWith<$R, IndividualSkills,
          IndividualSkillsCopyWith<$R, IndividualSkills, IndividualSkills>>?
      get skills;
  IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields,
      IndividualAdditionalFields>? get additionalFields;
  ListCopyWith<$R, IndividualAddress,
          IndividualAddressCopyWith<$R, IndividualAddress, IndividualAddress>>?
      get address;
  ListCopyWith<
      $R,
      IndividualIdentifiers,
      IndividualIdentifiersCopyWith<$R, IndividualIdentifiers,
          IndividualIdentifiers>>? get identifiers;
  $R call(
      {String? id,
      IndividualName? name,
      String? tenantId,
      CommonAuditDetails? auditDetails,
      List<IndividualSkills>? skills,
      String? mobileNumber,
      String? individualId,
      String? gender,
      String? userId,
      IndividualAdditionalFields? additionalFields,
      List<IndividualAddress>? address,
      String? altContactNumber,
      String? bloodGroup,
      String? clientReferenceId,
      String? dateOfBirth,
      String? email,
      String? fatherName,
      String? husbandName,
      List<IndividualIdentifiers>? identifiers,
      bool? isDeleted,
      String? photo,
      String? relationship,
      int? rowVersion});
  IndividualModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualModel, $Out>
    implements IndividualModelCopyWith<$R, IndividualModel, $Out> {
  _IndividualModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualModel> $mapper =
      IndividualModelMapper.ensureInitialized();
  @override
  IndividualNameCopyWith<$R, IndividualName, IndividualName>? get name =>
      $value.name?.copyWith.$chain((v) => call(name: v));
  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, IndividualSkills,
          IndividualSkillsCopyWith<$R, IndividualSkills, IndividualSkills>>?
      get skills => $value.skills != null
          ? ListCopyWith($value.skills!, (v, t) => v.copyWith.$chain(t),
              (v) => call(skills: v))
          : null;
  @override
  IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields,
          IndividualAdditionalFields>?
      get additionalFields => $value.additionalFields?.copyWith
          .$chain((v) => call(additionalFields: v));
  @override
  ListCopyWith<$R, IndividualAddress,
          IndividualAddressCopyWith<$R, IndividualAddress, IndividualAddress>>?
      get address => $value.address != null
          ? ListCopyWith($value.address!, (v, t) => v.copyWith.$chain(t),
              (v) => call(address: v))
          : null;
  @override
  ListCopyWith<
      $R,
      IndividualIdentifiers,
      IndividualIdentifiersCopyWith<$R, IndividualIdentifiers,
          IndividualIdentifiers>>? get identifiers => $value.identifiers != null
      ? ListCopyWith($value.identifiers!, (v, t) => v.copyWith.$chain(t),
          (v) => call(identifiers: v))
      : null;
  @override
  $R call(
          {Object? id = $none,
          Object? name = $none,
          String? tenantId,
          Object? auditDetails = $none,
          Object? skills = $none,
          Object? mobileNumber = $none,
          Object? individualId = $none,
          Object? gender = $none,
          Object? userId = $none,
          Object? additionalFields = $none,
          Object? address = $none,
          Object? altContactNumber = $none,
          Object? bloodGroup = $none,
          Object? clientReferenceId = $none,
          Object? dateOfBirth = $none,
          Object? email = $none,
          Object? fatherName = $none,
          Object? husbandName = $none,
          Object? identifiers = $none,
          Object? isDeleted = $none,
          Object? photo = $none,
          Object? relationship = $none,
          Object? rowVersion = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (name != $none) #name: name,
        if (tenantId != null) #tenantId: tenantId,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (skills != $none) #skills: skills,
        if (mobileNumber != $none) #mobileNumber: mobileNumber,
        if (individualId != $none) #individualId: individualId,
        if (gender != $none) #gender: gender,
        if (userId != $none) #userId: userId,
        if (additionalFields != $none) #additionalFields: additionalFields,
        if (address != $none) #address: address,
        if (altContactNumber != $none) #altContactNumber: altContactNumber,
        if (bloodGroup != $none) #bloodGroup: bloodGroup,
        if (clientReferenceId != $none) #clientReferenceId: clientReferenceId,
        if (dateOfBirth != $none) #dateOfBirth: dateOfBirth,
        if (email != $none) #email: email,
        if (fatherName != $none) #fatherName: fatherName,
        if (husbandName != $none) #husbandName: husbandName,
        if (identifiers != $none) #identifiers: identifiers,
        if (isDeleted != $none) #isDeleted: isDeleted,
        if (photo != $none) #photo: photo,
        if (relationship != $none) #relationship: relationship,
        if (rowVersion != $none) #rowVersion: rowVersion
      }));
  @override
  IndividualModel $make(CopyWithData data) => IndividualModel(
      id: data.get(#id, or: $value.id),
      name: data.get(#name, or: $value.name),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      skills: data.get(#skills, or: $value.skills),
      mobileNumber: data.get(#mobileNumber, or: $value.mobileNumber),
      individualId: data.get(#individualId, or: $value.individualId),
      gender: data.get(#gender, or: $value.gender),
      userId: data.get(#userId, or: $value.userId),
      additionalFields:
          data.get(#additionalFields, or: $value.additionalFields),
      address: data.get(#address, or: $value.address),
      altContactNumber:
          data.get(#altContactNumber, or: $value.altContactNumber),
      bloodGroup: data.get(#bloodGroup, or: $value.bloodGroup),
      clientReferenceId:
          data.get(#clientReferenceId, or: $value.clientReferenceId),
      dateOfBirth: data.get(#dateOfBirth, or: $value.dateOfBirth),
      email: data.get(#email, or: $value.email),
      fatherName: data.get(#fatherName, or: $value.fatherName),
      husbandName: data.get(#husbandName, or: $value.husbandName),
      identifiers: data.get(#identifiers, or: $value.identifiers),
      isDeleted: data.get(#isDeleted, or: $value.isDeleted),
      photo: data.get(#photo, or: $value.photo),
      relationship: data.get(#relationship, or: $value.relationship),
      rowVersion: data.get(#rowVersion, or: $value.rowVersion));

  @override
  IndividualModelCopyWith<$R2, IndividualModel, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualModelCopyWithImpl($value, $cast, t);
}

class IndividualNameMapper extends ClassMapperBase<IndividualName> {
  IndividualNameMapper._();

  static IndividualNameMapper? _instance;
  static IndividualNameMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualNameMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualName';

  static String? _$familyName(IndividualName v) => v.familyName;
  static const Field<IndividualName, String> _f$familyName =
      Field('familyName', _$familyName, opt: true);
  static String? _$givenName(IndividualName v) => v.givenName;
  static const Field<IndividualName, String> _f$givenName =
      Field('givenName', _$givenName, opt: true);
  static String? _$otherNames(IndividualName v) => v.otherNames;
  static const Field<IndividualName, String> _f$otherNames =
      Field('otherNames', _$otherNames, opt: true);

  @override
  final MappableFields<IndividualName> fields = const {
    #familyName: _f$familyName,
    #givenName: _f$givenName,
    #otherNames: _f$otherNames,
  };

  static IndividualName _instantiate(DecodingData data) {
    return IndividualName(
        familyName: data.dec(_f$familyName),
        givenName: data.dec(_f$givenName),
        otherNames: data.dec(_f$otherNames));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualName fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualName>(map);
  }

  static IndividualName fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualName>(json);
  }
}

mixin IndividualNameMappable {
  String toJson() {
    return IndividualNameMapper.ensureInitialized()
        .encodeJson<IndividualName>(this as IndividualName);
  }

  Map<String, dynamic> toMap() {
    return IndividualNameMapper.ensureInitialized()
        .encodeMap<IndividualName>(this as IndividualName);
  }

  IndividualNameCopyWith<IndividualName, IndividualName, IndividualName>
      get copyWith => _IndividualNameCopyWithImpl(
          this as IndividualName, $identity, $identity);
  @override
  String toString() {
    return IndividualNameMapper.ensureInitialized()
        .stringifyValue(this as IndividualName);
  }

  @override
  bool operator ==(Object other) {
    return IndividualNameMapper.ensureInitialized()
        .equalsValue(this as IndividualName, other);
  }

  @override
  int get hashCode {
    return IndividualNameMapper.ensureInitialized()
        .hashValue(this as IndividualName);
  }
}

extension IndividualNameValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualName, $Out> {
  IndividualNameCopyWith<$R, IndividualName, $Out> get $asIndividualName =>
      $base.as((v, t, t2) => _IndividualNameCopyWithImpl(v, t, t2));
}

abstract class IndividualNameCopyWith<$R, $In extends IndividualName, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? familyName, String? givenName, String? otherNames});
  IndividualNameCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualNameCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualName, $Out>
    implements IndividualNameCopyWith<$R, IndividualName, $Out> {
  _IndividualNameCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualName> $mapper =
      IndividualNameMapper.ensureInitialized();
  @override
  $R call(
          {Object? familyName = $none,
          Object? givenName = $none,
          Object? otherNames = $none}) =>
      $apply(FieldCopyWithData({
        if (familyName != $none) #familyName: familyName,
        if (givenName != $none) #givenName: givenName,
        if (otherNames != $none) #otherNames: otherNames
      }));
  @override
  IndividualName $make(CopyWithData data) => IndividualName(
      familyName: data.get(#familyName, or: $value.familyName),
      givenName: data.get(#givenName, or: $value.givenName),
      otherNames: data.get(#otherNames, or: $value.otherNames));

  @override
  IndividualNameCopyWith<$R2, IndividualName, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualNameCopyWithImpl($value, $cast, t);
}

class CommonAuditDetailsMapper extends ClassMapperBase<CommonAuditDetails> {
  CommonAuditDetailsMapper._();

  static CommonAuditDetailsMapper? _instance;
  static CommonAuditDetailsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = CommonAuditDetailsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'CommonAuditDetails';

  static int? _$createdTime(CommonAuditDetails v) => v.createdTime;
  static const Field<CommonAuditDetails, int> _f$createdTime =
      Field('createdTime', _$createdTime, opt: true);
  static int? _$lastModifiedTime(CommonAuditDetails v) => v.lastModifiedTime;
  static const Field<CommonAuditDetails, int> _f$lastModifiedTime =
      Field('lastModifiedTime', _$lastModifiedTime, opt: true);
  static String? _$createdBy(CommonAuditDetails v) => v.createdBy;
  static const Field<CommonAuditDetails, String> _f$createdBy =
      Field('createdBy', _$createdBy, opt: true);
  static String? _$lastModifiedBy(CommonAuditDetails v) => v.lastModifiedBy;
  static const Field<CommonAuditDetails, String> _f$lastModifiedBy =
      Field('lastModifiedBy', _$lastModifiedBy, opt: true);

  @override
  final MappableFields<CommonAuditDetails> fields = const {
    #createdTime: _f$createdTime,
    #lastModifiedTime: _f$lastModifiedTime,
    #createdBy: _f$createdBy,
    #lastModifiedBy: _f$lastModifiedBy,
  };

  static CommonAuditDetails _instantiate(DecodingData data) {
    return CommonAuditDetails(
        createdTime: data.dec(_f$createdTime),
        lastModifiedTime: data.dec(_f$lastModifiedTime),
        createdBy: data.dec(_f$createdBy),
        lastModifiedBy: data.dec(_f$lastModifiedBy));
  }

  @override
  final Function instantiate = _instantiate;

  static CommonAuditDetails fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<CommonAuditDetails>(map);
  }

  static CommonAuditDetails fromJson(String json) {
    return ensureInitialized().decodeJson<CommonAuditDetails>(json);
  }
}

mixin CommonAuditDetailsMappable {
  String toJson() {
    return CommonAuditDetailsMapper.ensureInitialized()
        .encodeJson<CommonAuditDetails>(this as CommonAuditDetails);
  }

  Map<String, dynamic> toMap() {
    return CommonAuditDetailsMapper.ensureInitialized()
        .encodeMap<CommonAuditDetails>(this as CommonAuditDetails);
  }

  CommonAuditDetailsCopyWith<CommonAuditDetails, CommonAuditDetails,
          CommonAuditDetails>
      get copyWith => _CommonAuditDetailsCopyWithImpl(
          this as CommonAuditDetails, $identity, $identity);
  @override
  String toString() {
    return CommonAuditDetailsMapper.ensureInitialized()
        .stringifyValue(this as CommonAuditDetails);
  }

  @override
  bool operator ==(Object other) {
    return CommonAuditDetailsMapper.ensureInitialized()
        .equalsValue(this as CommonAuditDetails, other);
  }

  @override
  int get hashCode {
    return CommonAuditDetailsMapper.ensureInitialized()
        .hashValue(this as CommonAuditDetails);
  }
}

extension CommonAuditDetailsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, CommonAuditDetails, $Out> {
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, $Out>
      get $asCommonAuditDetails =>
          $base.as((v, t, t2) => _CommonAuditDetailsCopyWithImpl(v, t, t2));
}

abstract class CommonAuditDetailsCopyWith<$R, $In extends CommonAuditDetails,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {int? createdTime,
      int? lastModifiedTime,
      String? createdBy,
      String? lastModifiedBy});
  CommonAuditDetailsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _CommonAuditDetailsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, CommonAuditDetails, $Out>
    implements CommonAuditDetailsCopyWith<$R, CommonAuditDetails, $Out> {
  _CommonAuditDetailsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<CommonAuditDetails> $mapper =
      CommonAuditDetailsMapper.ensureInitialized();
  @override
  $R call(
          {Object? createdTime = $none,
          Object? lastModifiedTime = $none,
          Object? createdBy = $none,
          Object? lastModifiedBy = $none}) =>
      $apply(FieldCopyWithData({
        if (createdTime != $none) #createdTime: createdTime,
        if (lastModifiedTime != $none) #lastModifiedTime: lastModifiedTime,
        if (createdBy != $none) #createdBy: createdBy,
        if (lastModifiedBy != $none) #lastModifiedBy: lastModifiedBy
      }));
  @override
  CommonAuditDetails $make(CopyWithData data) => CommonAuditDetails(
      createdTime: data.get(#createdTime, or: $value.createdTime),
      lastModifiedTime:
          data.get(#lastModifiedTime, or: $value.lastModifiedTime),
      createdBy: data.get(#createdBy, or: $value.createdBy),
      lastModifiedBy: data.get(#lastModifiedBy, or: $value.lastModifiedBy));

  @override
  CommonAuditDetailsCopyWith<$R2, CommonAuditDetails, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _CommonAuditDetailsCopyWithImpl($value, $cast, t);
}

class IndividualSkillsMapper extends ClassMapperBase<IndividualSkills> {
  IndividualSkillsMapper._();

  static IndividualSkillsMapper? _instance;
  static IndividualSkillsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualSkillsMapper._());
      CommonAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualSkills';

  static String? _$individualId(IndividualSkills v) => v.individualId;
  static const Field<IndividualSkills, String> _f$individualId =
      Field('individualId', _$individualId, opt: true);
  static String? _$clientReferenceId(IndividualSkills v) => v.clientReferenceId;
  static const Field<IndividualSkills, String> _f$clientReferenceId =
      Field('clientReferenceId', _$clientReferenceId, opt: true);
  static bool? _$isDeleted(IndividualSkills v) => v.isDeleted;
  static const Field<IndividualSkills, bool> _f$isDeleted =
      Field('isDeleted', _$isDeleted, opt: true);
  static String? _$id(IndividualSkills v) => v.id;
  static const Field<IndividualSkills, String> _f$id =
      Field('id', _$id, opt: true);
  static CommonAuditDetails? _$auditDetails(IndividualSkills v) =>
      v.auditDetails;
  static const Field<IndividualSkills, CommonAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static String? _$type(IndividualSkills v) => v.type;
  static const Field<IndividualSkills, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$experience(IndividualSkills v) => v.experience;
  static const Field<IndividualSkills, String> _f$experience =
      Field('experience', _$experience, opt: true);
  static String? _$level(IndividualSkills v) => v.level;
  static const Field<IndividualSkills, String> _f$level =
      Field('level', _$level, opt: true);

  @override
  final MappableFields<IndividualSkills> fields = const {
    #individualId: _f$individualId,
    #clientReferenceId: _f$clientReferenceId,
    #isDeleted: _f$isDeleted,
    #id: _f$id,
    #auditDetails: _f$auditDetails,
    #type: _f$type,
    #experience: _f$experience,
    #level: _f$level,
  };

  static IndividualSkills _instantiate(DecodingData data) {
    return IndividualSkills(
        individualId: data.dec(_f$individualId),
        clientReferenceId: data.dec(_f$clientReferenceId),
        isDeleted: data.dec(_f$isDeleted),
        id: data.dec(_f$id),
        auditDetails: data.dec(_f$auditDetails),
        type: data.dec(_f$type),
        experience: data.dec(_f$experience),
        level: data.dec(_f$level));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualSkills fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualSkills>(map);
  }

  static IndividualSkills fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualSkills>(json);
  }
}

mixin IndividualSkillsMappable {
  String toJson() {
    return IndividualSkillsMapper.ensureInitialized()
        .encodeJson<IndividualSkills>(this as IndividualSkills);
  }

  Map<String, dynamic> toMap() {
    return IndividualSkillsMapper.ensureInitialized()
        .encodeMap<IndividualSkills>(this as IndividualSkills);
  }

  IndividualSkillsCopyWith<IndividualSkills, IndividualSkills, IndividualSkills>
      get copyWith => _IndividualSkillsCopyWithImpl(
          this as IndividualSkills, $identity, $identity);
  @override
  String toString() {
    return IndividualSkillsMapper.ensureInitialized()
        .stringifyValue(this as IndividualSkills);
  }

  @override
  bool operator ==(Object other) {
    return IndividualSkillsMapper.ensureInitialized()
        .equalsValue(this as IndividualSkills, other);
  }

  @override
  int get hashCode {
    return IndividualSkillsMapper.ensureInitialized()
        .hashValue(this as IndividualSkills);
  }
}

extension IndividualSkillsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualSkills, $Out> {
  IndividualSkillsCopyWith<$R, IndividualSkills, $Out>
      get $asIndividualSkills =>
          $base.as((v, t, t2) => _IndividualSkillsCopyWithImpl(v, t, t2));
}

abstract class IndividualSkillsCopyWith<$R, $In extends IndividualSkills, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails;
  $R call(
      {String? individualId,
      String? clientReferenceId,
      bool? isDeleted,
      String? id,
      CommonAuditDetails? auditDetails,
      String? type,
      String? experience,
      String? level});
  IndividualSkillsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualSkillsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualSkills, $Out>
    implements IndividualSkillsCopyWith<$R, IndividualSkills, $Out> {
  _IndividualSkillsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualSkills> $mapper =
      IndividualSkillsMapper.ensureInitialized();
  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? individualId = $none,
          Object? clientReferenceId = $none,
          Object? isDeleted = $none,
          Object? id = $none,
          Object? auditDetails = $none,
          Object? type = $none,
          Object? experience = $none,
          Object? level = $none}) =>
      $apply(FieldCopyWithData({
        if (individualId != $none) #individualId: individualId,
        if (clientReferenceId != $none) #clientReferenceId: clientReferenceId,
        if (isDeleted != $none) #isDeleted: isDeleted,
        if (id != $none) #id: id,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (type != $none) #type: type,
        if (experience != $none) #experience: experience,
        if (level != $none) #level: level
      }));
  @override
  IndividualSkills $make(CopyWithData data) => IndividualSkills(
      individualId: data.get(#individualId, or: $value.individualId),
      clientReferenceId:
          data.get(#clientReferenceId, or: $value.clientReferenceId),
      isDeleted: data.get(#isDeleted, or: $value.isDeleted),
      id: data.get(#id, or: $value.id),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      type: data.get(#type, or: $value.type),
      experience: data.get(#experience, or: $value.experience),
      level: data.get(#level, or: $value.level));

  @override
  IndividualSkillsCopyWith<$R2, IndividualSkills, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualSkillsCopyWithImpl($value, $cast, t);
}

class IndividualAdditionalFieldsMapper
    extends ClassMapperBase<IndividualAdditionalFields> {
  IndividualAdditionalFieldsMapper._();

  static IndividualAdditionalFieldsMapper? _instance;
  static IndividualAdditionalFieldsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = IndividualAdditionalFieldsMapper._());
      AdditionalIndividualFieldsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualAdditionalFields';

  static String? _$status(IndividualAdditionalFields v) => v.status;
  static const Field<IndividualAdditionalFields, String> _f$status =
      Field('status', _$status, opt: true);
  static String? _$version(IndividualAdditionalFields v) => v.version;
  static const Field<IndividualAdditionalFields, String> _f$version =
      Field('version', _$version, opt: true);
  static String? _$documentType(IndividualAdditionalFields v) => v.documentType;
  static const Field<IndividualAdditionalFields, String> _f$documentType =
      Field('documentType', _$documentType, opt: true);
  static List<AdditionalIndividualFields>? _$fields(
          IndividualAdditionalFields v) =>
      v.fields;
  static const Field<IndividualAdditionalFields,
          List<AdditionalIndividualFields>> _f$fields =
      Field('fields', _$fields, opt: true);
  static String? _$documentUid(IndividualAdditionalFields v) => v.documentUid;
  static const Field<IndividualAdditionalFields, String> _f$documentUid =
      Field('documentUid', _$documentUid, opt: true);
  static String? _$fileStore(IndividualAdditionalFields v) => v.fileStore;
  static const Field<IndividualAdditionalFields, String> _f$fileStore =
      Field('fileStore', _$fileStore, opt: true);
  static String? _$schema(IndividualAdditionalFields v) => v.schema;
  static const Field<IndividualAdditionalFields, String> _f$schema =
      Field('schema', _$schema, opt: true);

  @override
  final MappableFields<IndividualAdditionalFields> fields = const {
    #status: _f$status,
    #version: _f$version,
    #documentType: _f$documentType,
    #fields: _f$fields,
    #documentUid: _f$documentUid,
    #fileStore: _f$fileStore,
    #schema: _f$schema,
  };

  static IndividualAdditionalFields _instantiate(DecodingData data) {
    return IndividualAdditionalFields(
        status: data.dec(_f$status),
        version: data.dec(_f$version),
        documentType: data.dec(_f$documentType),
        fields: data.dec(_f$fields),
        documentUid: data.dec(_f$documentUid),
        fileStore: data.dec(_f$fileStore),
        schema: data.dec(_f$schema));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualAdditionalFields fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualAdditionalFields>(map);
  }

  static IndividualAdditionalFields fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualAdditionalFields>(json);
  }
}

mixin IndividualAdditionalFieldsMappable {
  String toJson() {
    return IndividualAdditionalFieldsMapper.ensureInitialized()
        .encodeJson<IndividualAdditionalFields>(
            this as IndividualAdditionalFields);
  }

  Map<String, dynamic> toMap() {
    return IndividualAdditionalFieldsMapper.ensureInitialized()
        .encodeMap<IndividualAdditionalFields>(
            this as IndividualAdditionalFields);
  }

  IndividualAdditionalFieldsCopyWith<IndividualAdditionalFields,
          IndividualAdditionalFields, IndividualAdditionalFields>
      get copyWith => _IndividualAdditionalFieldsCopyWithImpl(
          this as IndividualAdditionalFields, $identity, $identity);
  @override
  String toString() {
    return IndividualAdditionalFieldsMapper.ensureInitialized()
        .stringifyValue(this as IndividualAdditionalFields);
  }

  @override
  bool operator ==(Object other) {
    return IndividualAdditionalFieldsMapper.ensureInitialized()
        .equalsValue(this as IndividualAdditionalFields, other);
  }

  @override
  int get hashCode {
    return IndividualAdditionalFieldsMapper.ensureInitialized()
        .hashValue(this as IndividualAdditionalFields);
  }
}

extension IndividualAdditionalFieldsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualAdditionalFields, $Out> {
  IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields, $Out>
      get $asIndividualAdditionalFields => $base
          .as((v, t, t2) => _IndividualAdditionalFieldsCopyWithImpl(v, t, t2));
}

abstract class IndividualAdditionalFieldsCopyWith<
    $R,
    $In extends IndividualAdditionalFields,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<
      $R,
      AdditionalIndividualFields,
      AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields,
          AdditionalIndividualFields>>? get fields;
  $R call(
      {String? status,
      String? version,
      String? documentType,
      List<AdditionalIndividualFields>? fields,
      String? documentUid,
      String? fileStore,
      String? schema});
  IndividualAdditionalFieldsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualAdditionalFieldsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualAdditionalFields, $Out>
    implements
        IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields,
            $Out> {
  _IndividualAdditionalFieldsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualAdditionalFields> $mapper =
      IndividualAdditionalFieldsMapper.ensureInitialized();
  @override
  ListCopyWith<
      $R,
      AdditionalIndividualFields,
      AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields,
          AdditionalIndividualFields>>? get fields => $value.fields != null
      ? ListCopyWith($value.fields!, (v, t) => v.copyWith.$chain(t),
          (v) => call(fields: v))
      : null;
  @override
  $R call(
          {Object? status = $none,
          Object? version = $none,
          Object? documentType = $none,
          Object? fields = $none,
          Object? documentUid = $none,
          Object? fileStore = $none,
          Object? schema = $none}) =>
      $apply(FieldCopyWithData({
        if (status != $none) #status: status,
        if (version != $none) #version: version,
        if (documentType != $none) #documentType: documentType,
        if (fields != $none) #fields: fields,
        if (documentUid != $none) #documentUid: documentUid,
        if (fileStore != $none) #fileStore: fileStore,
        if (schema != $none) #schema: schema
      }));
  @override
  IndividualAdditionalFields $make(CopyWithData data) =>
      IndividualAdditionalFields(
          status: data.get(#status, or: $value.status),
          version: data.get(#version, or: $value.version),
          documentType: data.get(#documentType, or: $value.documentType),
          fields: data.get(#fields, or: $value.fields),
          documentUid: data.get(#documentUid, or: $value.documentUid),
          fileStore: data.get(#fileStore, or: $value.fileStore),
          schema: data.get(#schema, or: $value.schema));

  @override
  IndividualAdditionalFieldsCopyWith<$R2, IndividualAdditionalFields, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _IndividualAdditionalFieldsCopyWithImpl($value, $cast, t);
}

class AdditionalIndividualFieldsMapper
    extends ClassMapperBase<AdditionalIndividualFields> {
  AdditionalIndividualFieldsMapper._();

  static AdditionalIndividualFieldsMapper? _instance;
  static AdditionalIndividualFieldsMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = AdditionalIndividualFieldsMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'AdditionalIndividualFields';

  static String? _$key(AdditionalIndividualFields v) => v.key;
  static const Field<AdditionalIndividualFields, String> _f$key =
      Field('key', _$key, opt: true);
  static String? _$value(AdditionalIndividualFields v) => v.value;
  static const Field<AdditionalIndividualFields, String> _f$value =
      Field('value', _$value, opt: true);

  @override
  final MappableFields<AdditionalIndividualFields> fields = const {
    #key: _f$key,
    #value: _f$value,
  };

  static AdditionalIndividualFields _instantiate(DecodingData data) {
    return AdditionalIndividualFields(
        key: data.dec(_f$key), value: data.dec(_f$value));
  }

  @override
  final Function instantiate = _instantiate;

  static AdditionalIndividualFields fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<AdditionalIndividualFields>(map);
  }

  static AdditionalIndividualFields fromJson(String json) {
    return ensureInitialized().decodeJson<AdditionalIndividualFields>(json);
  }
}

mixin AdditionalIndividualFieldsMappable {
  String toJson() {
    return AdditionalIndividualFieldsMapper.ensureInitialized()
        .encodeJson<AdditionalIndividualFields>(
            this as AdditionalIndividualFields);
  }

  Map<String, dynamic> toMap() {
    return AdditionalIndividualFieldsMapper.ensureInitialized()
        .encodeMap<AdditionalIndividualFields>(
            this as AdditionalIndividualFields);
  }

  AdditionalIndividualFieldsCopyWith<AdditionalIndividualFields,
          AdditionalIndividualFields, AdditionalIndividualFields>
      get copyWith => _AdditionalIndividualFieldsCopyWithImpl(
          this as AdditionalIndividualFields, $identity, $identity);
  @override
  String toString() {
    return AdditionalIndividualFieldsMapper.ensureInitialized()
        .stringifyValue(this as AdditionalIndividualFields);
  }

  @override
  bool operator ==(Object other) {
    return AdditionalIndividualFieldsMapper.ensureInitialized()
        .equalsValue(this as AdditionalIndividualFields, other);
  }

  @override
  int get hashCode {
    return AdditionalIndividualFieldsMapper.ensureInitialized()
        .hashValue(this as AdditionalIndividualFields);
  }
}

extension AdditionalIndividualFieldsValueCopy<$R, $Out>
    on ObjectCopyWith<$R, AdditionalIndividualFields, $Out> {
  AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields, $Out>
      get $asAdditionalIndividualFields => $base
          .as((v, t, t2) => _AdditionalIndividualFieldsCopyWithImpl(v, t, t2));
}

abstract class AdditionalIndividualFieldsCopyWith<
    $R,
    $In extends AdditionalIndividualFields,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  $R call({String? key, String? value});
  AdditionalIndividualFieldsCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _AdditionalIndividualFieldsCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, AdditionalIndividualFields, $Out>
    implements
        AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields,
            $Out> {
  _AdditionalIndividualFieldsCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<AdditionalIndividualFields> $mapper =
      AdditionalIndividualFieldsMapper.ensureInitialized();
  @override
  $R call({Object? key = $none, Object? value = $none}) =>
      $apply(FieldCopyWithData(
          {if (key != $none) #key: key, if (value != $none) #value: value}));
  @override
  AdditionalIndividualFields $make(CopyWithData data) =>
      AdditionalIndividualFields(
          key: data.get(#key, or: $value.key),
          value: data.get(#value, or: $value.value));

  @override
  AdditionalIndividualFieldsCopyWith<$R2, AdditionalIndividualFields, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _AdditionalIndividualFieldsCopyWithImpl($value, $cast, t);
}

class IndividualAddressMapper extends ClassMapperBase<IndividualAddress> {
  IndividualAddressMapper._();

  static IndividualAddressMapper? _instance;
  static IndividualAddressMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualAddressMapper._());
      CommonAuditDetailsMapper.ensureInitialized();
      AddressWardMapper.ensureInitialized();
      AddressLocalityMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualAddress';

  static String? _$id(IndividualAddress v) => v.id;
  static const Field<IndividualAddress, String> _f$id =
      Field('id', _$id, opt: true);
  static String? _$tenantId(IndividualAddress v) => v.tenantId;
  static const Field<IndividualAddress, String> _f$tenantId =
      Field('tenantId', _$tenantId, opt: true);
  static CommonAuditDetails? _$auditDetails(IndividualAddress v) =>
      v.auditDetails;
  static const Field<IndividualAddress, CommonAuditDetails> _f$auditDetails =
      Field('auditDetails', _$auditDetails, opt: true);
  static bool? _$isDeleted(IndividualAddress v) => v.isDeleted;
  static const Field<IndividualAddress, bool> _f$isDeleted =
      Field('isDeleted', _$isDeleted, opt: true);
  static String? _$clientReferenceId(IndividualAddress v) =>
      v.clientReferenceId;
  static const Field<IndividualAddress, String> _f$clientReferenceId =
      Field('clientReferenceId', _$clientReferenceId, opt: true);
  static String? _$individualId(IndividualAddress v) => v.individualId;
  static const Field<IndividualAddress, String> _f$individualId =
      Field('individualId', _$individualId, opt: true);
  static AddressWard? _$ward(IndividualAddress v) => v.ward;
  static const Field<IndividualAddress, AddressWard> _f$ward =
      Field('ward', _$ward, opt: true);
  static String? _$type(IndividualAddress v) => v.type;
  static const Field<IndividualAddress, String> _f$type =
      Field('type', _$type, opt: true);
  static String? _$addressLine1(IndividualAddress v) => v.addressLine1;
  static const Field<IndividualAddress, String> _f$addressLine1 =
      Field('addressLine1', _$addressLine1, opt: true);
  static String? _$addressLine2(IndividualAddress v) => v.addressLine2;
  static const Field<IndividualAddress, String> _f$addressLine2 =
      Field('addressLine2', _$addressLine2, opt: true);
  static String? _$buildingName(IndividualAddress v) => v.buildingName;
  static const Field<IndividualAddress, String> _f$buildingName =
      Field('buildingName', _$buildingName, opt: true);
  static String? _$city(IndividualAddress v) => v.city;
  static const Field<IndividualAddress, String> _f$city =
      Field('city', _$city, opt: true);
  static String? _$doorNo(IndividualAddress v) => v.doorNo;
  static const Field<IndividualAddress, String> _f$doorNo =
      Field('doorNo', _$doorNo, opt: true);
  static String? _$landmark(IndividualAddress v) => v.landmark;
  static const Field<IndividualAddress, String> _f$landmark =
      Field('landmark', _$landmark, opt: true);
  static double? _$latitude(IndividualAddress v) => v.latitude;
  static const Field<IndividualAddress, double> _f$latitude =
      Field('latitude', _$latitude, opt: true);
  static AddressLocality? _$locality(IndividualAddress v) => v.locality;
  static const Field<IndividualAddress, AddressLocality> _f$locality =
      Field('locality', _$locality, opt: true);
  static double? _$locationAccuracy(IndividualAddress v) => v.locationAccuracy;
  static const Field<IndividualAddress, double> _f$locationAccuracy =
      Field('locationAccuracy', _$locationAccuracy, opt: true);
  static double? _$longitude(IndividualAddress v) => v.longitude;
  static const Field<IndividualAddress, double> _f$longitude =
      Field('longitude', _$longitude, opt: true);
  static String? _$pincode(IndividualAddress v) => v.pincode;
  static const Field<IndividualAddress, String> _f$pincode =
      Field('pincode', _$pincode, opt: true);
  static String? _$street(IndividualAddress v) => v.street;
  static const Field<IndividualAddress, String> _f$street =
      Field('street', _$street, opt: true);

  @override
  final MappableFields<IndividualAddress> fields = const {
    #id: _f$id,
    #tenantId: _f$tenantId,
    #auditDetails: _f$auditDetails,
    #isDeleted: _f$isDeleted,
    #clientReferenceId: _f$clientReferenceId,
    #individualId: _f$individualId,
    #ward: _f$ward,
    #type: _f$type,
    #addressLine1: _f$addressLine1,
    #addressLine2: _f$addressLine2,
    #buildingName: _f$buildingName,
    #city: _f$city,
    #doorNo: _f$doorNo,
    #landmark: _f$landmark,
    #latitude: _f$latitude,
    #locality: _f$locality,
    #locationAccuracy: _f$locationAccuracy,
    #longitude: _f$longitude,
    #pincode: _f$pincode,
    #street: _f$street,
  };

  static IndividualAddress _instantiate(DecodingData data) {
    return IndividualAddress(
        id: data.dec(_f$id),
        tenantId: data.dec(_f$tenantId),
        auditDetails: data.dec(_f$auditDetails),
        isDeleted: data.dec(_f$isDeleted),
        clientReferenceId: data.dec(_f$clientReferenceId),
        individualId: data.dec(_f$individualId),
        ward: data.dec(_f$ward),
        type: data.dec(_f$type),
        addressLine1: data.dec(_f$addressLine1),
        addressLine2: data.dec(_f$addressLine2),
        buildingName: data.dec(_f$buildingName),
        city: data.dec(_f$city),
        doorNo: data.dec(_f$doorNo),
        landmark: data.dec(_f$landmark),
        latitude: data.dec(_f$latitude),
        locality: data.dec(_f$locality),
        locationAccuracy: data.dec(_f$locationAccuracy),
        longitude: data.dec(_f$longitude),
        pincode: data.dec(_f$pincode),
        street: data.dec(_f$street));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualAddress fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualAddress>(map);
  }

  static IndividualAddress fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualAddress>(json);
  }
}

mixin IndividualAddressMappable {
  String toJson() {
    return IndividualAddressMapper.ensureInitialized()
        .encodeJson<IndividualAddress>(this as IndividualAddress);
  }

  Map<String, dynamic> toMap() {
    return IndividualAddressMapper.ensureInitialized()
        .encodeMap<IndividualAddress>(this as IndividualAddress);
  }

  IndividualAddressCopyWith<IndividualAddress, IndividualAddress,
          IndividualAddress>
      get copyWith => _IndividualAddressCopyWithImpl(
          this as IndividualAddress, $identity, $identity);
  @override
  String toString() {
    return IndividualAddressMapper.ensureInitialized()
        .stringifyValue(this as IndividualAddress);
  }

  @override
  bool operator ==(Object other) {
    return IndividualAddressMapper.ensureInitialized()
        .equalsValue(this as IndividualAddress, other);
  }

  @override
  int get hashCode {
    return IndividualAddressMapper.ensureInitialized()
        .hashValue(this as IndividualAddress);
  }
}

extension IndividualAddressValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualAddress, $Out> {
  IndividualAddressCopyWith<$R, IndividualAddress, $Out>
      get $asIndividualAddress =>
          $base.as((v, t, t2) => _IndividualAddressCopyWithImpl(v, t, t2));
}

abstract class IndividualAddressCopyWith<$R, $In extends IndividualAddress,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails;
  AddressWardCopyWith<$R, AddressWard, AddressWard>? get ward;
  AddressLocalityCopyWith<$R, AddressLocality, AddressLocality>? get locality;
  $R call(
      {String? id,
      String? tenantId,
      CommonAuditDetails? auditDetails,
      bool? isDeleted,
      String? clientReferenceId,
      String? individualId,
      AddressWard? ward,
      String? type,
      String? addressLine1,
      String? addressLine2,
      String? buildingName,
      String? city,
      String? doorNo,
      String? landmark,
      double? latitude,
      AddressLocality? locality,
      double? locationAccuracy,
      double? longitude,
      String? pincode,
      String? street});
  IndividualAddressCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualAddressCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualAddress, $Out>
    implements IndividualAddressCopyWith<$R, IndividualAddress, $Out> {
  _IndividualAddressCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualAddress> $mapper =
      IndividualAddressMapper.ensureInitialized();
  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  AddressWardCopyWith<$R, AddressWard, AddressWard>? get ward =>
      $value.ward?.copyWith.$chain((v) => call(ward: v));
  @override
  AddressLocalityCopyWith<$R, AddressLocality, AddressLocality>? get locality =>
      $value.locality?.copyWith.$chain((v) => call(locality: v));
  @override
  $R call(
          {Object? id = $none,
          Object? tenantId = $none,
          Object? auditDetails = $none,
          Object? isDeleted = $none,
          Object? clientReferenceId = $none,
          Object? individualId = $none,
          Object? ward = $none,
          Object? type = $none,
          Object? addressLine1 = $none,
          Object? addressLine2 = $none,
          Object? buildingName = $none,
          Object? city = $none,
          Object? doorNo = $none,
          Object? landmark = $none,
          Object? latitude = $none,
          Object? locality = $none,
          Object? locationAccuracy = $none,
          Object? longitude = $none,
          Object? pincode = $none,
          Object? street = $none}) =>
      $apply(FieldCopyWithData({
        if (id != $none) #id: id,
        if (tenantId != $none) #tenantId: tenantId,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (isDeleted != $none) #isDeleted: isDeleted,
        if (clientReferenceId != $none) #clientReferenceId: clientReferenceId,
        if (individualId != $none) #individualId: individualId,
        if (ward != $none) #ward: ward,
        if (type != $none) #type: type,
        if (addressLine1 != $none) #addressLine1: addressLine1,
        if (addressLine2 != $none) #addressLine2: addressLine2,
        if (buildingName != $none) #buildingName: buildingName,
        if (city != $none) #city: city,
        if (doorNo != $none) #doorNo: doorNo,
        if (landmark != $none) #landmark: landmark,
        if (latitude != $none) #latitude: latitude,
        if (locality != $none) #locality: locality,
        if (locationAccuracy != $none) #locationAccuracy: locationAccuracy,
        if (longitude != $none) #longitude: longitude,
        if (pincode != $none) #pincode: pincode,
        if (street != $none) #street: street
      }));
  @override
  IndividualAddress $make(CopyWithData data) => IndividualAddress(
      id: data.get(#id, or: $value.id),
      tenantId: data.get(#tenantId, or: $value.tenantId),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      isDeleted: data.get(#isDeleted, or: $value.isDeleted),
      clientReferenceId:
          data.get(#clientReferenceId, or: $value.clientReferenceId),
      individualId: data.get(#individualId, or: $value.individualId),
      ward: data.get(#ward, or: $value.ward),
      type: data.get(#type, or: $value.type),
      addressLine1: data.get(#addressLine1, or: $value.addressLine1),
      addressLine2: data.get(#addressLine2, or: $value.addressLine2),
      buildingName: data.get(#buildingName, or: $value.buildingName),
      city: data.get(#city, or: $value.city),
      doorNo: data.get(#doorNo, or: $value.doorNo),
      landmark: data.get(#landmark, or: $value.landmark),
      latitude: data.get(#latitude, or: $value.latitude),
      locality: data.get(#locality, or: $value.locality),
      locationAccuracy:
          data.get(#locationAccuracy, or: $value.locationAccuracy),
      longitude: data.get(#longitude, or: $value.longitude),
      pincode: data.get(#pincode, or: $value.pincode),
      street: data.get(#street, or: $value.street));

  @override
  IndividualAddressCopyWith<$R2, IndividualAddress, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _IndividualAddressCopyWithImpl($value, $cast, t);
}

class AddressWardMapper extends ClassMapperBase<AddressWard> {
  AddressWardMapper._();

  static AddressWardMapper? _instance;
  static AddressWardMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = AddressWardMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'AddressWard';

  static String? _$code(AddressWard v) => v.code;
  static const Field<AddressWard, String> _f$code =
      Field('code', _$code, opt: true);
  static String? _$name(AddressWard v) => v.name;
  static const Field<AddressWard, String> _f$name =
      Field('name', _$name, opt: true);
  static String? _$label(AddressWard v) => v.label;
  static const Field<AddressWard, String> _f$label =
      Field('label', _$label, opt: true);
  static double? _$latitude(AddressWard v) => v.latitude;
  static const Field<AddressWard, double> _f$latitude =
      Field('latitude', _$latitude, opt: true);
  static double? _$longitude(AddressWard v) => v.longitude;
  static const Field<AddressWard, double> _f$longitude =
      Field('longitude', _$longitude, opt: true);
  static String? _$materializedPath(AddressWard v) => v.materializedPath;
  static const Field<AddressWard, String> _f$materializedPath =
      Field('materializedPath', _$materializedPath, opt: true);

  @override
  final MappableFields<AddressWard> fields = const {
    #code: _f$code,
    #name: _f$name,
    #label: _f$label,
    #latitude: _f$latitude,
    #longitude: _f$longitude,
    #materializedPath: _f$materializedPath,
  };

  static AddressWard _instantiate(DecodingData data) {
    return AddressWard(
        code: data.dec(_f$code),
        name: data.dec(_f$name),
        label: data.dec(_f$label),
        latitude: data.dec(_f$latitude),
        longitude: data.dec(_f$longitude),
        materializedPath: data.dec(_f$materializedPath));
  }

  @override
  final Function instantiate = _instantiate;

  static AddressWard fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<AddressWard>(map);
  }

  static AddressWard fromJson(String json) {
    return ensureInitialized().decodeJson<AddressWard>(json);
  }
}

mixin AddressWardMappable {
  String toJson() {
    return AddressWardMapper.ensureInitialized()
        .encodeJson<AddressWard>(this as AddressWard);
  }

  Map<String, dynamic> toMap() {
    return AddressWardMapper.ensureInitialized()
        .encodeMap<AddressWard>(this as AddressWard);
  }

  AddressWardCopyWith<AddressWard, AddressWard, AddressWard> get copyWith =>
      _AddressWardCopyWithImpl(this as AddressWard, $identity, $identity);
  @override
  String toString() {
    return AddressWardMapper.ensureInitialized()
        .stringifyValue(this as AddressWard);
  }

  @override
  bool operator ==(Object other) {
    return AddressWardMapper.ensureInitialized()
        .equalsValue(this as AddressWard, other);
  }

  @override
  int get hashCode {
    return AddressWardMapper.ensureInitialized().hashValue(this as AddressWard);
  }
}

extension AddressWardValueCopy<$R, $Out>
    on ObjectCopyWith<$R, AddressWard, $Out> {
  AddressWardCopyWith<$R, AddressWard, $Out> get $asAddressWard =>
      $base.as((v, t, t2) => _AddressWardCopyWithImpl(v, t, t2));
}

abstract class AddressWardCopyWith<$R, $In extends AddressWard, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? code,
      String? name,
      String? label,
      double? latitude,
      double? longitude,
      String? materializedPath});
  AddressWardCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(Then<$Out2, $R2> t);
}

class _AddressWardCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, AddressWard, $Out>
    implements AddressWardCopyWith<$R, AddressWard, $Out> {
  _AddressWardCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<AddressWard> $mapper =
      AddressWardMapper.ensureInitialized();
  @override
  $R call(
          {Object? code = $none,
          Object? name = $none,
          Object? label = $none,
          Object? latitude = $none,
          Object? longitude = $none,
          Object? materializedPath = $none}) =>
      $apply(FieldCopyWithData({
        if (code != $none) #code: code,
        if (name != $none) #name: name,
        if (label != $none) #label: label,
        if (latitude != $none) #latitude: latitude,
        if (longitude != $none) #longitude: longitude,
        if (materializedPath != $none) #materializedPath: materializedPath
      }));
  @override
  AddressWard $make(CopyWithData data) => AddressWard(
      code: data.get(#code, or: $value.code),
      name: data.get(#name, or: $value.name),
      label: data.get(#label, or: $value.label),
      latitude: data.get(#latitude, or: $value.latitude),
      longitude: data.get(#longitude, or: $value.longitude),
      materializedPath:
          data.get(#materializedPath, or: $value.materializedPath));

  @override
  AddressWardCopyWith<$R2, AddressWard, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _AddressWardCopyWithImpl($value, $cast, t);
}

class AddressLocalityMapper extends ClassMapperBase<AddressLocality> {
  AddressLocalityMapper._();

  static AddressLocalityMapper? _instance;
  static AddressLocalityMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = AddressLocalityMapper._());
    }
    return _instance!;
  }

  @override
  final String id = 'AddressLocality';

  static String? _$code(AddressLocality v) => v.code;
  static const Field<AddressLocality, String> _f$code =
      Field('code', _$code, opt: true);
  static String? _$name(AddressLocality v) => v.name;
  static const Field<AddressLocality, String> _f$name =
      Field('name', _$name, opt: true);
  static String? _$label(AddressLocality v) => v.label;
  static const Field<AddressLocality, String> _f$label =
      Field('label', _$label, opt: true);
  static double? _$latitude(AddressLocality v) => v.latitude;
  static const Field<AddressLocality, double> _f$latitude =
      Field('latitude', _$latitude, opt: true);
  static double? _$longitude(AddressLocality v) => v.longitude;
  static const Field<AddressLocality, double> _f$longitude =
      Field('longitude', _$longitude, opt: true);
  static String? _$materializedPath(AddressLocality v) => v.materializedPath;
  static const Field<AddressLocality, String> _f$materializedPath =
      Field('materializedPath', _$materializedPath, opt: true);

  @override
  final MappableFields<AddressLocality> fields = const {
    #code: _f$code,
    #name: _f$name,
    #label: _f$label,
    #latitude: _f$latitude,
    #longitude: _f$longitude,
    #materializedPath: _f$materializedPath,
  };

  static AddressLocality _instantiate(DecodingData data) {
    return AddressLocality(
        code: data.dec(_f$code),
        name: data.dec(_f$name),
        label: data.dec(_f$label),
        latitude: data.dec(_f$latitude),
        longitude: data.dec(_f$longitude),
        materializedPath: data.dec(_f$materializedPath));
  }

  @override
  final Function instantiate = _instantiate;

  static AddressLocality fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<AddressLocality>(map);
  }

  static AddressLocality fromJson(String json) {
    return ensureInitialized().decodeJson<AddressLocality>(json);
  }
}

mixin AddressLocalityMappable {
  String toJson() {
    return AddressLocalityMapper.ensureInitialized()
        .encodeJson<AddressLocality>(this as AddressLocality);
  }

  Map<String, dynamic> toMap() {
    return AddressLocalityMapper.ensureInitialized()
        .encodeMap<AddressLocality>(this as AddressLocality);
  }

  AddressLocalityCopyWith<AddressLocality, AddressLocality, AddressLocality>
      get copyWith => _AddressLocalityCopyWithImpl(
          this as AddressLocality, $identity, $identity);
  @override
  String toString() {
    return AddressLocalityMapper.ensureInitialized()
        .stringifyValue(this as AddressLocality);
  }

  @override
  bool operator ==(Object other) {
    return AddressLocalityMapper.ensureInitialized()
        .equalsValue(this as AddressLocality, other);
  }

  @override
  int get hashCode {
    return AddressLocalityMapper.ensureInitialized()
        .hashValue(this as AddressLocality);
  }
}

extension AddressLocalityValueCopy<$R, $Out>
    on ObjectCopyWith<$R, AddressLocality, $Out> {
  AddressLocalityCopyWith<$R, AddressLocality, $Out> get $asAddressLocality =>
      $base.as((v, t, t2) => _AddressLocalityCopyWithImpl(v, t, t2));
}

abstract class AddressLocalityCopyWith<$R, $In extends AddressLocality, $Out>
    implements ClassCopyWith<$R, $In, $Out> {
  $R call(
      {String? code,
      String? name,
      String? label,
      double? latitude,
      double? longitude,
      String? materializedPath});
  AddressLocalityCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _AddressLocalityCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, AddressLocality, $Out>
    implements AddressLocalityCopyWith<$R, AddressLocality, $Out> {
  _AddressLocalityCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<AddressLocality> $mapper =
      AddressLocalityMapper.ensureInitialized();
  @override
  $R call(
          {Object? code = $none,
          Object? name = $none,
          Object? label = $none,
          Object? latitude = $none,
          Object? longitude = $none,
          Object? materializedPath = $none}) =>
      $apply(FieldCopyWithData({
        if (code != $none) #code: code,
        if (name != $none) #name: name,
        if (label != $none) #label: label,
        if (latitude != $none) #latitude: latitude,
        if (longitude != $none) #longitude: longitude,
        if (materializedPath != $none) #materializedPath: materializedPath
      }));
  @override
  AddressLocality $make(CopyWithData data) => AddressLocality(
      code: data.get(#code, or: $value.code),
      name: data.get(#name, or: $value.name),
      label: data.get(#label, or: $value.label),
      latitude: data.get(#latitude, or: $value.latitude),
      longitude: data.get(#longitude, or: $value.longitude),
      materializedPath:
          data.get(#materializedPath, or: $value.materializedPath));

  @override
  AddressLocalityCopyWith<$R2, AddressLocality, $Out2> $chain<$R2, $Out2>(
          Then<$Out2, $R2> t) =>
      _AddressLocalityCopyWithImpl($value, $cast, t);
}

class IndividualIdentifiersMapper
    extends ClassMapperBase<IndividualIdentifiers> {
  IndividualIdentifiersMapper._();

  static IndividualIdentifiersMapper? _instance;
  static IndividualIdentifiersMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = IndividualIdentifiersMapper._());
      CommonAuditDetailsMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'IndividualIdentifiers';

  static String? _$individualId(IndividualIdentifiers v) => v.individualId;
  static const Field<IndividualIdentifiers, String> _f$individualId =
      Field('individualId', _$individualId, opt: true);
  static String? _$clientReferenceId(IndividualIdentifiers v) =>
      v.clientReferenceId;
  static const Field<IndividualIdentifiers, String> _f$clientReferenceId =
      Field('clientReferenceId', _$clientReferenceId, opt: true);
  static bool? _$isDeleted(IndividualIdentifiers v) => v.isDeleted;
  static const Field<IndividualIdentifiers, bool> _f$isDeleted =
      Field('isDeleted', _$isDeleted, opt: true);
  static String? _$id(IndividualIdentifiers v) => v.id;
  static const Field<IndividualIdentifiers, String> _f$id =
      Field('id', _$id, opt: true);
  static CommonAuditDetails? _$auditDetails(IndividualIdentifiers v) =>
      v.auditDetails;
  static const Field<IndividualIdentifiers, CommonAuditDetails>
      _f$auditDetails = Field('auditDetails', _$auditDetails, opt: true);
  static String? _$identifierId(IndividualIdentifiers v) => v.identifierId;
  static const Field<IndividualIdentifiers, String> _f$identifierId =
      Field('identifierId', _$identifierId, opt: true);
  static String? _$identifierType(IndividualIdentifiers v) => v.identifierType;
  static const Field<IndividualIdentifiers, String> _f$identifierType =
      Field('identifierType', _$identifierType, opt: true);

  @override
  final MappableFields<IndividualIdentifiers> fields = const {
    #individualId: _f$individualId,
    #clientReferenceId: _f$clientReferenceId,
    #isDeleted: _f$isDeleted,
    #id: _f$id,
    #auditDetails: _f$auditDetails,
    #identifierId: _f$identifierId,
    #identifierType: _f$identifierType,
  };

  static IndividualIdentifiers _instantiate(DecodingData data) {
    return IndividualIdentifiers(
        individualId: data.dec(_f$individualId),
        clientReferenceId: data.dec(_f$clientReferenceId),
        isDeleted: data.dec(_f$isDeleted),
        id: data.dec(_f$id),
        auditDetails: data.dec(_f$auditDetails),
        identifierId: data.dec(_f$identifierId),
        identifierType: data.dec(_f$identifierType));
  }

  @override
  final Function instantiate = _instantiate;

  static IndividualIdentifiers fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<IndividualIdentifiers>(map);
  }

  static IndividualIdentifiers fromJson(String json) {
    return ensureInitialized().decodeJson<IndividualIdentifiers>(json);
  }
}

mixin IndividualIdentifiersMappable {
  String toJson() {
    return IndividualIdentifiersMapper.ensureInitialized()
        .encodeJson<IndividualIdentifiers>(this as IndividualIdentifiers);
  }

  Map<String, dynamic> toMap() {
    return IndividualIdentifiersMapper.ensureInitialized()
        .encodeMap<IndividualIdentifiers>(this as IndividualIdentifiers);
  }

  IndividualIdentifiersCopyWith<IndividualIdentifiers, IndividualIdentifiers,
          IndividualIdentifiers>
      get copyWith => _IndividualIdentifiersCopyWithImpl(
          this as IndividualIdentifiers, $identity, $identity);
  @override
  String toString() {
    return IndividualIdentifiersMapper.ensureInitialized()
        .stringifyValue(this as IndividualIdentifiers);
  }

  @override
  bool operator ==(Object other) {
    return IndividualIdentifiersMapper.ensureInitialized()
        .equalsValue(this as IndividualIdentifiers, other);
  }

  @override
  int get hashCode {
    return IndividualIdentifiersMapper.ensureInitialized()
        .hashValue(this as IndividualIdentifiers);
  }
}

extension IndividualIdentifiersValueCopy<$R, $Out>
    on ObjectCopyWith<$R, IndividualIdentifiers, $Out> {
  IndividualIdentifiersCopyWith<$R, IndividualIdentifiers, $Out>
      get $asIndividualIdentifiers =>
          $base.as((v, t, t2) => _IndividualIdentifiersCopyWithImpl(v, t, t2));
}

abstract class IndividualIdentifiersCopyWith<
    $R,
    $In extends IndividualIdentifiers,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails;
  $R call(
      {String? individualId,
      String? clientReferenceId,
      bool? isDeleted,
      String? id,
      CommonAuditDetails? auditDetails,
      String? identifierId,
      String? identifierType});
  IndividualIdentifiersCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _IndividualIdentifiersCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, IndividualIdentifiers, $Out>
    implements IndividualIdentifiersCopyWith<$R, IndividualIdentifiers, $Out> {
  _IndividualIdentifiersCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<IndividualIdentifiers> $mapper =
      IndividualIdentifiersMapper.ensureInitialized();
  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails =>
          $value.auditDetails?.copyWith.$chain((v) => call(auditDetails: v));
  @override
  $R call(
          {Object? individualId = $none,
          Object? clientReferenceId = $none,
          Object? isDeleted = $none,
          Object? id = $none,
          Object? auditDetails = $none,
          Object? identifierId = $none,
          Object? identifierType = $none}) =>
      $apply(FieldCopyWithData({
        if (individualId != $none) #individualId: individualId,
        if (clientReferenceId != $none) #clientReferenceId: clientReferenceId,
        if (isDeleted != $none) #isDeleted: isDeleted,
        if (id != $none) #id: id,
        if (auditDetails != $none) #auditDetails: auditDetails,
        if (identifierId != $none) #identifierId: identifierId,
        if (identifierType != $none) #identifierType: identifierType
      }));
  @override
  IndividualIdentifiers $make(CopyWithData data) => IndividualIdentifiers(
      individualId: data.get(#individualId, or: $value.individualId),
      clientReferenceId:
          data.get(#clientReferenceId, or: $value.clientReferenceId),
      isDeleted: data.get(#isDeleted, or: $value.isDeleted),
      id: data.get(#id, or: $value.id),
      auditDetails: data.get(#auditDetails, or: $value.auditDetails),
      identifierId: data.get(#identifierId, or: $value.identifierId),
      identifierType: data.get(#identifierType, or: $value.identifierType));

  @override
  IndividualIdentifiersCopyWith<$R2, IndividualIdentifiers, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _IndividualIdentifiersCopyWithImpl($value, $cast, t);
}

class SingleIndividualModelMapper
    extends ClassMapperBase<SingleIndividualModel> {
  SingleIndividualModelMapper._();

  static SingleIndividualModelMapper? _instance;
  static SingleIndividualModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = SingleIndividualModelMapper._());
      IndividualModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'SingleIndividualModel';

  static IndividualModel? _$Individual(SingleIndividualModel v) => v.Individual;
  static const Field<SingleIndividualModel, IndividualModel> _f$Individual =
      Field('Individual', _$Individual, opt: true);

  @override
  final MappableFields<SingleIndividualModel> fields = const {
    #Individual: _f$Individual,
  };

  static SingleIndividualModel _instantiate(DecodingData data) {
    return SingleIndividualModel(Individual: data.dec(_f$Individual));
  }

  @override
  final Function instantiate = _instantiate;

  static SingleIndividualModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<SingleIndividualModel>(map);
  }

  static SingleIndividualModel fromJson(String json) {
    return ensureInitialized().decodeJson<SingleIndividualModel>(json);
  }
}

mixin SingleIndividualModelMappable {
  String toJson() {
    return SingleIndividualModelMapper.ensureInitialized()
        .encodeJson<SingleIndividualModel>(this as SingleIndividualModel);
  }

  Map<String, dynamic> toMap() {
    return SingleIndividualModelMapper.ensureInitialized()
        .encodeMap<SingleIndividualModel>(this as SingleIndividualModel);
  }

  SingleIndividualModelCopyWith<SingleIndividualModel, SingleIndividualModel,
          SingleIndividualModel>
      get copyWith => _SingleIndividualModelCopyWithImpl(
          this as SingleIndividualModel, $identity, $identity);
  @override
  String toString() {
    return SingleIndividualModelMapper.ensureInitialized()
        .stringifyValue(this as SingleIndividualModel);
  }

  @override
  bool operator ==(Object other) {
    return SingleIndividualModelMapper.ensureInitialized()
        .equalsValue(this as SingleIndividualModel, other);
  }

  @override
  int get hashCode {
    return SingleIndividualModelMapper.ensureInitialized()
        .hashValue(this as SingleIndividualModel);
  }
}

extension SingleIndividualModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, SingleIndividualModel, $Out> {
  SingleIndividualModelCopyWith<$R, SingleIndividualModel, $Out>
      get $asSingleIndividualModel =>
          $base.as((v, t, t2) => _SingleIndividualModelCopyWithImpl(v, t, t2));
}

abstract class SingleIndividualModelCopyWith<
    $R,
    $In extends SingleIndividualModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>? get Individual;
  $R call({IndividualModel? Individual});
  SingleIndividualModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _SingleIndividualModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, SingleIndividualModel, $Out>
    implements SingleIndividualModelCopyWith<$R, SingleIndividualModel, $Out> {
  _SingleIndividualModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<SingleIndividualModel> $mapper =
      SingleIndividualModelMapper.ensureInitialized();
  @override
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get Individual =>
          $value.Individual?.copyWith.$chain((v) => call(Individual: v));
  @override
  $R call({Object? Individual = $none}) => $apply(
      FieldCopyWithData({if (Individual != $none) #Individual: Individual}));
  @override
  SingleIndividualModel $make(CopyWithData data) => SingleIndividualModel(
      Individual: data.get(#Individual, or: $value.Individual));

  @override
  SingleIndividualModelCopyWith<$R2, SingleIndividualModel, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _SingleIndividualModelCopyWithImpl($value, $cast, t);
}

class WMSIndividualListModelMapper
    extends ClassMapperBase<WMSIndividualListModel> {
  WMSIndividualListModelMapper._();

  static WMSIndividualListModelMapper? _instance;
  static WMSIndividualListModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals.use(_instance = WMSIndividualListModelMapper._());
      SingleWMSIndividualModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'WMSIndividualListModel';

  static List<SingleWMSIndividualModel>? _$items(WMSIndividualListModel v) =>
      v.items;
  static const Field<WMSIndividualListModel, List<SingleWMSIndividualModel>>
      _f$items = Field('items', _$items, opt: true);

  @override
  final MappableFields<WMSIndividualListModel> fields = const {
    #items: _f$items,
  };

  static WMSIndividualListModel _instantiate(DecodingData data) {
    return WMSIndividualListModel(items: data.dec(_f$items));
  }

  @override
  final Function instantiate = _instantiate;

  static WMSIndividualListModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<WMSIndividualListModel>(map);
  }

  static WMSIndividualListModel fromJson(String json) {
    return ensureInitialized().decodeJson<WMSIndividualListModel>(json);
  }
}

mixin WMSIndividualListModelMappable {
  String toJson() {
    return WMSIndividualListModelMapper.ensureInitialized()
        .encodeJson<WMSIndividualListModel>(this as WMSIndividualListModel);
  }

  Map<String, dynamic> toMap() {
    return WMSIndividualListModelMapper.ensureInitialized()
        .encodeMap<WMSIndividualListModel>(this as WMSIndividualListModel);
  }

  WMSIndividualListModelCopyWith<WMSIndividualListModel, WMSIndividualListModel,
          WMSIndividualListModel>
      get copyWith => _WMSIndividualListModelCopyWithImpl(
          this as WMSIndividualListModel, $identity, $identity);
  @override
  String toString() {
    return WMSIndividualListModelMapper.ensureInitialized()
        .stringifyValue(this as WMSIndividualListModel);
  }

  @override
  bool operator ==(Object other) {
    return WMSIndividualListModelMapper.ensureInitialized()
        .equalsValue(this as WMSIndividualListModel, other);
  }

  @override
  int get hashCode {
    return WMSIndividualListModelMapper.ensureInitialized()
        .hashValue(this as WMSIndividualListModel);
  }
}

extension WMSIndividualListModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, WMSIndividualListModel, $Out> {
  WMSIndividualListModelCopyWith<$R, WMSIndividualListModel, $Out>
      get $asWMSIndividualListModel =>
          $base.as((v, t, t2) => _WMSIndividualListModelCopyWithImpl(v, t, t2));
}

abstract class WMSIndividualListModelCopyWith<
    $R,
    $In extends WMSIndividualListModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  ListCopyWith<
      $R,
      SingleWMSIndividualModel,
      SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel,
          SingleWMSIndividualModel>>? get items;
  $R call({List<SingleWMSIndividualModel>? items});
  WMSIndividualListModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _WMSIndividualListModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, WMSIndividualListModel, $Out>
    implements
        WMSIndividualListModelCopyWith<$R, WMSIndividualListModel, $Out> {
  _WMSIndividualListModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<WMSIndividualListModel> $mapper =
      WMSIndividualListModelMapper.ensureInitialized();
  @override
  ListCopyWith<
      $R,
      SingleWMSIndividualModel,
      SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel,
          SingleWMSIndividualModel>>? get items => $value.items != null
      ? ListCopyWith(
          $value.items!, (v, t) => v.copyWith.$chain(t), (v) => call(items: v))
      : null;
  @override
  $R call({Object? items = $none}) =>
      $apply(FieldCopyWithData({if (items != $none) #items: items}));
  @override
  WMSIndividualListModel $make(CopyWithData data) =>
      WMSIndividualListModel(items: data.get(#items, or: $value.items));

  @override
  WMSIndividualListModelCopyWith<$R2, WMSIndividualListModel, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _WMSIndividualListModelCopyWithImpl($value, $cast, t);
}

class SingleWMSIndividualModelMapper
    extends ClassMapperBase<SingleWMSIndividualModel> {
  SingleWMSIndividualModelMapper._();

  static SingleWMSIndividualModelMapper? _instance;
  static SingleWMSIndividualModelMapper ensureInitialized() {
    if (_instance == null) {
      MapperContainer.globals
          .use(_instance = SingleWMSIndividualModelMapper._());
      IndividualModelMapper.ensureInitialized();
    }
    return _instance!;
  }

  @override
  final String id = 'SingleWMSIndividualModel';

  static IndividualModel? _$businessObject(SingleWMSIndividualModel v) =>
      v.businessObject;
  static const Field<SingleWMSIndividualModel, IndividualModel>
      _f$businessObject = Field('businessObject', _$businessObject, opt: true);

  @override
  final MappableFields<SingleWMSIndividualModel> fields = const {
    #businessObject: _f$businessObject,
  };

  static SingleWMSIndividualModel _instantiate(DecodingData data) {
    return SingleWMSIndividualModel(
        businessObject: data.dec(_f$businessObject));
  }

  @override
  final Function instantiate = _instantiate;

  static SingleWMSIndividualModel fromMap(Map<String, dynamic> map) {
    return ensureInitialized().decodeMap<SingleWMSIndividualModel>(map);
  }

  static SingleWMSIndividualModel fromJson(String json) {
    return ensureInitialized().decodeJson<SingleWMSIndividualModel>(json);
  }
}

mixin SingleWMSIndividualModelMappable {
  String toJson() {
    return SingleWMSIndividualModelMapper.ensureInitialized()
        .encodeJson<SingleWMSIndividualModel>(this as SingleWMSIndividualModel);
  }

  Map<String, dynamic> toMap() {
    return SingleWMSIndividualModelMapper.ensureInitialized()
        .encodeMap<SingleWMSIndividualModel>(this as SingleWMSIndividualModel);
  }

  SingleWMSIndividualModelCopyWith<SingleWMSIndividualModel,
          SingleWMSIndividualModel, SingleWMSIndividualModel>
      get copyWith => _SingleWMSIndividualModelCopyWithImpl(
          this as SingleWMSIndividualModel, $identity, $identity);
  @override
  String toString() {
    return SingleWMSIndividualModelMapper.ensureInitialized()
        .stringifyValue(this as SingleWMSIndividualModel);
  }

  @override
  bool operator ==(Object other) {
    return SingleWMSIndividualModelMapper.ensureInitialized()
        .equalsValue(this as SingleWMSIndividualModel, other);
  }

  @override
  int get hashCode {
    return SingleWMSIndividualModelMapper.ensureInitialized()
        .hashValue(this as SingleWMSIndividualModel);
  }
}

extension SingleWMSIndividualModelValueCopy<$R, $Out>
    on ObjectCopyWith<$R, SingleWMSIndividualModel, $Out> {
  SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel, $Out>
      get $asSingleWMSIndividualModel => $base
          .as((v, t, t2) => _SingleWMSIndividualModelCopyWithImpl(v, t, t2));
}

abstract class SingleWMSIndividualModelCopyWith<
    $R,
    $In extends SingleWMSIndividualModel,
    $Out> implements ClassCopyWith<$R, $In, $Out> {
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get businessObject;
  $R call({IndividualModel? businessObject});
  SingleWMSIndividualModelCopyWith<$R2, $In, $Out2> $chain<$R2, $Out2>(
      Then<$Out2, $R2> t);
}

class _SingleWMSIndividualModelCopyWithImpl<$R, $Out>
    extends ClassCopyWithBase<$R, SingleWMSIndividualModel, $Out>
    implements
        SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel, $Out> {
  _SingleWMSIndividualModelCopyWithImpl(super.value, super.then, super.then2);

  @override
  late final ClassMapperBase<SingleWMSIndividualModel> $mapper =
      SingleWMSIndividualModelMapper.ensureInitialized();
  @override
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get businessObject => $value.businessObject?.copyWith
          .$chain((v) => call(businessObject: v));
  @override
  $R call({Object? businessObject = $none}) => $apply(FieldCopyWithData(
      {if (businessObject != $none) #businessObject: businessObject}));
  @override
  SingleWMSIndividualModel $make(CopyWithData data) => SingleWMSIndividualModel(
      businessObject: data.get(#businessObject, or: $value.businessObject));

  @override
  SingleWMSIndividualModelCopyWith<$R2, SingleWMSIndividualModel, $Out2>
      $chain<$R2, $Out2>(Then<$Out2, $R2> t) =>
          _SingleWMSIndividualModelCopyWithImpl($value, $cast, t);
}
