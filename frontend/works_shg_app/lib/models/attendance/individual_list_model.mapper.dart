// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'individual_list_model.dart';

class IndividualListModelMapper extends MapperBase<IndividualListModel> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualListModelMapper()},
  )..linkAll({IndividualModelMapper.container});

  @override
  IndividualListModelMapperElement createElement(MapperContainer container) {
    return IndividualListModelMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualListModel';

  static final fromMap = container.fromMap<IndividualListModel>;
  static final fromJson = container.fromJson<IndividualListModel>;
}

class IndividualListModelMapperElement
    extends MapperElementBase<IndividualListModel> {
  IndividualListModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualListModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualListModel fromMap(Map<String, dynamic> map) =>
      IndividualListModel(Individual: container.$getOpt(map, 'Individual'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualListModel v) => toMap(v);
  Map<String, dynamic> toMap(IndividualListModel i) =>
      {'Individual': container.$enc(i.Individual, 'Individual')};

  @override
  String stringify(IndividualListModel self) =>
      'IndividualListModel(Individual: ${container.asString(self.Individual)})';
  @override
  int hash(IndividualListModel self) => container.hash(self.Individual);
  @override
  bool equals(IndividualListModel self, IndividualListModel other) =>
      container.isEqual(self.Individual, other.Individual);
}

mixin IndividualListModelMappable {
  String toJson() =>
      IndividualListModelMapper.container.toJson(this as IndividualListModel);
  Map<String, dynamic> toMap() =>
      IndividualListModelMapper.container.toMap(this as IndividualListModel);
  IndividualListModelCopyWith<IndividualListModel, IndividualListModel,
          IndividualListModel>
      get copyWith => _IndividualListModelCopyWithImpl(
          this as IndividualListModel, $identity, $identity);
  @override
  String toString() => IndividualListModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualListModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualListModelMapper.container.hash(this);
}

extension IndividualListModelValueCopy<$R, $Out extends IndividualListModel>
    on ObjectCopyWith<$R, IndividualListModel, $Out> {
  IndividualListModelCopyWith<$R, IndividualListModel, $Out>
      get asIndividualListModel =>
          base.as((v, t, t2) => _IndividualListModelCopyWithImpl(v, t, t2));
}

typedef IndividualListModelCopyWithBound = IndividualListModel;

abstract class IndividualListModelCopyWith<$R, $In extends IndividualListModel,
    $Out extends IndividualListModel> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualListModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualListModel>(
          Then<IndividualListModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, IndividualModel,
          IndividualModelCopyWith<$R, IndividualModel, IndividualModel>>?
      get Individual;
  $R call({List<IndividualModel>? Individual});
}

class _IndividualListModelCopyWithImpl<$R, $Out extends IndividualListModel>
    extends CopyWithBase<$R, IndividualListModel, $Out>
    implements IndividualListModelCopyWith<$R, IndividualListModel, $Out> {
  _IndividualListModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualListModelCopyWith<$R2, IndividualListModel, $Out2>
      chain<$R2, $Out2 extends IndividualListModel>(
              Then<IndividualListModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualListModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, IndividualModel,
          IndividualModelCopyWith<$R, IndividualModel, IndividualModel>>?
      get Individual => $value.Individual != null
          ? ListCopyWith(
              $value.Individual!,
              (v, t) => v.copyWith.chain<$R, IndividualModel>($identity, t),
              (v) => call(Individual: v))
          : null;
  @override
  $R call({Object? Individual = $none}) =>
      $then(IndividualListModel(Individual: or(Individual, $value.Individual)));
}

class SingleIndividualModelMapper extends MapperBase<SingleIndividualModel> {
  static MapperContainer container = MapperContainer(
    mappers: {SingleIndividualModelMapper()},
  )..linkAll({IndividualModelMapper.container});

  @override
  SingleIndividualModelMapperElement createElement(MapperContainer container) {
    return SingleIndividualModelMapperElement._(this, container);
  }

  @override
  String get id => 'SingleIndividualModel';

  static final fromMap = container.fromMap<SingleIndividualModel>;
  static final fromJson = container.fromJson<SingleIndividualModel>;
}

class SingleIndividualModelMapperElement
    extends MapperElementBase<SingleIndividualModel> {
  SingleIndividualModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  SingleIndividualModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  SingleIndividualModel fromMap(Map<String, dynamic> map) =>
      SingleIndividualModel(Individual: container.$getOpt(map, 'Individual'));

  @override
  Function get encoder => encode;
  dynamic encode(SingleIndividualModel v) => toMap(v);
  Map<String, dynamic> toMap(SingleIndividualModel s) =>
      {'Individual': container.$enc(s.Individual, 'Individual')};

  @override
  String stringify(SingleIndividualModel self) =>
      'SingleIndividualModel(Individual: ${container.asString(self.Individual)})';
  @override
  int hash(SingleIndividualModel self) => container.hash(self.Individual);
  @override
  bool equals(SingleIndividualModel self, SingleIndividualModel other) =>
      container.isEqual(self.Individual, other.Individual);
}

mixin SingleIndividualModelMappable {
  String toJson() => SingleIndividualModelMapper.container
      .toJson(this as SingleIndividualModel);
  Map<String, dynamic> toMap() => SingleIndividualModelMapper.container
      .toMap(this as SingleIndividualModel);
  SingleIndividualModelCopyWith<SingleIndividualModel, SingleIndividualModel,
          SingleIndividualModel>
      get copyWith => _SingleIndividualModelCopyWithImpl(
          this as SingleIndividualModel, $identity, $identity);
  @override
  String toString() => SingleIndividualModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          SingleIndividualModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => SingleIndividualModelMapper.container.hash(this);
}

extension SingleIndividualModelValueCopy<$R, $Out extends SingleIndividualModel>
    on ObjectCopyWith<$R, SingleIndividualModel, $Out> {
  SingleIndividualModelCopyWith<$R, SingleIndividualModel, $Out>
      get asSingleIndividualModel =>
          base.as((v, t, t2) => _SingleIndividualModelCopyWithImpl(v, t, t2));
}

typedef SingleIndividualModelCopyWithBound = SingleIndividualModel;

abstract class SingleIndividualModelCopyWith<$R,
        $In extends SingleIndividualModel, $Out extends SingleIndividualModel>
    implements ObjectCopyWith<$R, $In, $Out> {
  SingleIndividualModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends SingleIndividualModel>(
          Then<SingleIndividualModel, $Out2> t, Then<$Out2, $R2> t2);
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>? get Individual;
  $R call({IndividualModel? Individual});
}

class _SingleIndividualModelCopyWithImpl<$R, $Out extends SingleIndividualModel>
    extends CopyWithBase<$R, SingleIndividualModel, $Out>
    implements SingleIndividualModelCopyWith<$R, SingleIndividualModel, $Out> {
  _SingleIndividualModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  SingleIndividualModelCopyWith<$R2, SingleIndividualModel, $Out2>
      chain<$R2, $Out2 extends SingleIndividualModel>(
              Then<SingleIndividualModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _SingleIndividualModelCopyWithImpl($value, t, t2);

  @override
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get Individual => $value.Individual?.copyWith
          .chain($identity, (v) => call(Individual: v));
  @override
  $R call({Object? Individual = $none}) => $then(
      SingleIndividualModel(Individual: or(Individual, $value.Individual)));
}

class WMSIndividualListModelMapper extends MapperBase<WMSIndividualListModel> {
  static MapperContainer container = MapperContainer(
    mappers: {WMSIndividualListModelMapper()},
  )..linkAll({SingleWMSIndividualModelMapper.container});

  @override
  WMSIndividualListModelMapperElement createElement(MapperContainer container) {
    return WMSIndividualListModelMapperElement._(this, container);
  }

  @override
  String get id => 'WMSIndividualListModel';

  static final fromMap = container.fromMap<WMSIndividualListModel>;
  static final fromJson = container.fromJson<WMSIndividualListModel>;
}

class WMSIndividualListModelMapperElement
    extends MapperElementBase<WMSIndividualListModel> {
  WMSIndividualListModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  WMSIndividualListModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  WMSIndividualListModel fromMap(Map<String, dynamic> map) =>
      WMSIndividualListModel(items: container.$getOpt(map, 'items'));

  @override
  Function get encoder => encode;
  dynamic encode(WMSIndividualListModel v) => toMap(v);
  Map<String, dynamic> toMap(WMSIndividualListModel w) =>
      {'items': container.$enc(w.items, 'items')};

  @override
  String stringify(WMSIndividualListModel self) =>
      'WMSIndividualListModel(items: ${container.asString(self.items)})';
  @override
  int hash(WMSIndividualListModel self) => container.hash(self.items);
  @override
  bool equals(WMSIndividualListModel self, WMSIndividualListModel other) =>
      container.isEqual(self.items, other.items);
}

mixin WMSIndividualListModelMappable {
  String toJson() => WMSIndividualListModelMapper.container
      .toJson(this as WMSIndividualListModel);
  Map<String, dynamic> toMap() => WMSIndividualListModelMapper.container
      .toMap(this as WMSIndividualListModel);
  WMSIndividualListModelCopyWith<WMSIndividualListModel, WMSIndividualListModel,
          WMSIndividualListModel>
      get copyWith => _WMSIndividualListModelCopyWithImpl(
          this as WMSIndividualListModel, $identity, $identity);
  @override
  String toString() => WMSIndividualListModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          WMSIndividualListModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => WMSIndividualListModelMapper.container.hash(this);
}

extension WMSIndividualListModelValueCopy<$R,
        $Out extends WMSIndividualListModel>
    on ObjectCopyWith<$R, WMSIndividualListModel, $Out> {
  WMSIndividualListModelCopyWith<$R, WMSIndividualListModel, $Out>
      get asWMSIndividualListModel =>
          base.as((v, t, t2) => _WMSIndividualListModelCopyWithImpl(v, t, t2));
}

typedef WMSIndividualListModelCopyWithBound = WMSIndividualListModel;

abstract class WMSIndividualListModelCopyWith<$R,
        $In extends WMSIndividualListModel, $Out extends WMSIndividualListModel>
    implements ObjectCopyWith<$R, $In, $Out> {
  WMSIndividualListModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends WMSIndividualListModel>(
          Then<WMSIndividualListModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<
      $R,
      SingleWMSIndividualModel,
      SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel,
          SingleWMSIndividualModel>>? get items;
  $R call({List<SingleWMSIndividualModel>? items});
}

class _WMSIndividualListModelCopyWithImpl<$R,
        $Out extends WMSIndividualListModel>
    extends CopyWithBase<$R, WMSIndividualListModel, $Out>
    implements
        WMSIndividualListModelCopyWith<$R, WMSIndividualListModel, $Out> {
  _WMSIndividualListModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  WMSIndividualListModelCopyWith<$R2, WMSIndividualListModel, $Out2>
      chain<$R2, $Out2 extends WMSIndividualListModel>(
              Then<WMSIndividualListModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _WMSIndividualListModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<
      $R,
      SingleWMSIndividualModel,
      SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel,
          SingleWMSIndividualModel>>? get items => $value.items != null
      ? ListCopyWith(
          $value.items!,
          (v, t) =>
              v.copyWith.chain<$R, SingleWMSIndividualModel>($identity, t),
          (v) => call(items: v))
      : null;
  @override
  $R call({Object? items = $none}) =>
      $then(WMSIndividualListModel(items: or(items, $value.items)));
}

class SingleWMSIndividualModelMapper
    extends MapperBase<SingleWMSIndividualModel> {
  static MapperContainer container = MapperContainer(
    mappers: {SingleWMSIndividualModelMapper()},
  )..linkAll({IndividualModelMapper.container});

  @override
  SingleWMSIndividualModelMapperElement createElement(
      MapperContainer container) {
    return SingleWMSIndividualModelMapperElement._(this, container);
  }

  @override
  String get id => 'SingleWMSIndividualModel';

  static final fromMap = container.fromMap<SingleWMSIndividualModel>;
  static final fromJson = container.fromJson<SingleWMSIndividualModel>;
}

class SingleWMSIndividualModelMapperElement
    extends MapperElementBase<SingleWMSIndividualModel> {
  SingleWMSIndividualModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  SingleWMSIndividualModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  SingleWMSIndividualModel fromMap(Map<String, dynamic> map) =>
      SingleWMSIndividualModel(
          businessObject: container.$getOpt(map, 'businessObject'));

  @override
  Function get encoder => encode;
  dynamic encode(SingleWMSIndividualModel v) => toMap(v);
  Map<String, dynamic> toMap(SingleWMSIndividualModel s) =>
      {'businessObject': container.$enc(s.businessObject, 'businessObject')};

  @override
  String stringify(SingleWMSIndividualModel self) =>
      'SingleWMSIndividualModel(businessObject: ${container.asString(self.businessObject)})';
  @override
  int hash(SingleWMSIndividualModel self) =>
      container.hash(self.businessObject);
  @override
  bool equals(SingleWMSIndividualModel self, SingleWMSIndividualModel other) =>
      container.isEqual(self.businessObject, other.businessObject);
}

mixin SingleWMSIndividualModelMappable {
  String toJson() => SingleWMSIndividualModelMapper.container
      .toJson(this as SingleWMSIndividualModel);
  Map<String, dynamic> toMap() => SingleWMSIndividualModelMapper.container
      .toMap(this as SingleWMSIndividualModel);
  SingleWMSIndividualModelCopyWith<SingleWMSIndividualModel,
          SingleWMSIndividualModel, SingleWMSIndividualModel>
      get copyWith => _SingleWMSIndividualModelCopyWithImpl(
          this as SingleWMSIndividualModel, $identity, $identity);
  @override
  String toString() => SingleWMSIndividualModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          SingleWMSIndividualModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => SingleWMSIndividualModelMapper.container.hash(this);
}

extension SingleWMSIndividualModelValueCopy<$R,
        $Out extends SingleWMSIndividualModel>
    on ObjectCopyWith<$R, SingleWMSIndividualModel, $Out> {
  SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel, $Out>
      get asSingleWMSIndividualModel => base
          .as((v, t, t2) => _SingleWMSIndividualModelCopyWithImpl(v, t, t2));
}

typedef SingleWMSIndividualModelCopyWithBound = SingleWMSIndividualModel;

abstract class SingleWMSIndividualModelCopyWith<
        $R,
        $In extends SingleWMSIndividualModel,
        $Out extends SingleWMSIndividualModel>
    implements ObjectCopyWith<$R, $In, $Out> {
  SingleWMSIndividualModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends SingleWMSIndividualModel>(
          Then<SingleWMSIndividualModel, $Out2> t, Then<$Out2, $R2> t2);
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get businessObject;
  $R call({IndividualModel? businessObject});
}

class _SingleWMSIndividualModelCopyWithImpl<$R,
        $Out extends SingleWMSIndividualModel>
    extends CopyWithBase<$R, SingleWMSIndividualModel, $Out>
    implements
        SingleWMSIndividualModelCopyWith<$R, SingleWMSIndividualModel, $Out> {
  _SingleWMSIndividualModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  SingleWMSIndividualModelCopyWith<$R2, SingleWMSIndividualModel, $Out2>
      chain<$R2, $Out2 extends SingleWMSIndividualModel>(
              Then<SingleWMSIndividualModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _SingleWMSIndividualModelCopyWithImpl($value, t, t2);

  @override
  IndividualModelCopyWith<$R, IndividualModel, IndividualModel>?
      get businessObject => $value.businessObject?.copyWith
          .chain($identity, (v) => call(businessObject: v));
  @override
  $R call({Object? businessObject = $none}) => $then(SingleWMSIndividualModel(
      businessObject: or(businessObject, $value.businessObject)));
}

class IndividualModelMapper extends MapperBase<IndividualModel> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualModelMapper()},
  )..linkAll({
      IndividualNameMapper.container,
      CommonAuditDetailsMapper.container,
      IndividualSkillsMapper.container,
      IndividualAdditionalFieldsMapper.container,
      IndividualAddressMapper.container,
      IndividualIdentifiersMapper.container,
    });

  @override
  IndividualModelMapperElement createElement(MapperContainer container) {
    return IndividualModelMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualModel';

  static final fromMap = container.fromMap<IndividualModel>;
  static final fromJson = container.fromJson<IndividualModel>;
}

class IndividualModelMapperElement extends MapperElementBase<IndividualModel> {
  IndividualModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualModel fromMap(Map<String, dynamic> map) => IndividualModel(
      id: container.$getOpt(map, 'id'),
      name: container.$getOpt(map, 'name'),
      tenantId: container.$get(map, 'tenantId'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      skills: container.$getOpt(map, 'skills'),
      mobileNumber: container.$getOpt(map, 'mobileNumber'),
      individualId: container.$getOpt(map, 'individualId'),
      gender: container.$getOpt(map, 'gender'),
      userId: container.$getOpt(map, 'userId'),
      additionalFields: container.$getOpt(map, 'additionalFields'),
      address: container.$getOpt(map, 'address'),
      altContactNumber: container.$getOpt(map, 'altContactNumber'),
      bloodGroup: container.$getOpt(map, 'bloodGroup'),
      clientReferenceId: container.$getOpt(map, 'clientReferenceId'),
      dateOfBirth: container.$getOpt(map, 'dateOfBirth'),
      email: container.$getOpt(map, 'email'),
      fatherName: container.$getOpt(map, 'fatherName'),
      husbandName: container.$getOpt(map, 'husbandName'),
      identifiers: container.$getOpt(map, 'identifiers'),
      isDeleted: container.$getOpt(map, 'isDeleted'),
      photo: container.$getOpt(map, 'photo'),
      relationship: container.$getOpt(map, 'relationship'),
      rowVersion: container.$getOpt(map, 'rowVersion'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualModel v) => toMap(v);
  Map<String, dynamic> toMap(IndividualModel i) => {
        'id': container.$enc(i.id, 'id'),
        'name': container.$enc(i.name, 'name'),
        'tenantId': container.$enc(i.tenantId, 'tenantId'),
        'auditDetails': container.$enc(i.auditDetails, 'auditDetails'),
        'skills': container.$enc(i.skills, 'skills'),
        'mobileNumber': container.$enc(i.mobileNumber, 'mobileNumber'),
        'individualId': container.$enc(i.individualId, 'individualId'),
        'gender': container.$enc(i.gender, 'gender'),
        'userId': container.$enc(i.userId, 'userId'),
        'additionalFields':
            container.$enc(i.additionalFields, 'additionalFields'),
        'address': container.$enc(i.address, 'address'),
        'altContactNumber':
            container.$enc(i.altContactNumber, 'altContactNumber'),
        'bloodGroup': container.$enc(i.bloodGroup, 'bloodGroup'),
        'clientReferenceId':
            container.$enc(i.clientReferenceId, 'clientReferenceId'),
        'dateOfBirth': container.$enc(i.dateOfBirth, 'dateOfBirth'),
        'email': container.$enc(i.email, 'email'),
        'fatherName': container.$enc(i.fatherName, 'fatherName'),
        'husbandName': container.$enc(i.husbandName, 'husbandName'),
        'identifiers': container.$enc(i.identifiers, 'identifiers'),
        'isDeleted': container.$enc(i.isDeleted, 'isDeleted'),
        'photo': container.$enc(i.photo, 'photo'),
        'relationship': container.$enc(i.relationship, 'relationship'),
        'rowVersion': container.$enc(i.rowVersion, 'rowVersion')
      };

  @override
  String stringify(IndividualModel self) =>
      'IndividualModel(id: ${container.asString(self.id)}, individualId: ${container.asString(self.individualId)}, tenantId: ${container.asString(self.tenantId)}, clientReferenceId: ${container.asString(self.clientReferenceId)}, userId: ${container.asString(self.userId)}, name: ${container.asString(self.name)}, dateOfBirth: ${container.asString(self.dateOfBirth)}, gender: ${container.asString(self.gender)}, bloodGroup: ${container.asString(self.bloodGroup)}, mobileNumber: ${container.asString(self.mobileNumber)}, altContactNumber: ${container.asString(self.altContactNumber)}, email: ${container.asString(self.email)}, address: ${container.asString(self.address)}, fatherName: ${container.asString(self.fatherName)}, husbandName: ${container.asString(self.husbandName)}, relationship: ${container.asString(self.relationship)}, identifiers: ${container.asString(self.identifiers)}, skills: ${container.asString(self.skills)}, photo: ${container.asString(self.photo)}, additionalFields: ${container.asString(self.additionalFields)}, isDeleted: ${container.asString(self.isDeleted)}, rowVersion: ${container.asString(self.rowVersion)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(IndividualModel self) =>
      container.hash(self.id) ^
      container.hash(self.individualId) ^
      container.hash(self.tenantId) ^
      container.hash(self.clientReferenceId) ^
      container.hash(self.userId) ^
      container.hash(self.name) ^
      container.hash(self.dateOfBirth) ^
      container.hash(self.gender) ^
      container.hash(self.bloodGroup) ^
      container.hash(self.mobileNumber) ^
      container.hash(self.altContactNumber) ^
      container.hash(self.email) ^
      container.hash(self.address) ^
      container.hash(self.fatherName) ^
      container.hash(self.husbandName) ^
      container.hash(self.relationship) ^
      container.hash(self.identifiers) ^
      container.hash(self.skills) ^
      container.hash(self.photo) ^
      container.hash(self.additionalFields) ^
      container.hash(self.isDeleted) ^
      container.hash(self.rowVersion) ^
      container.hash(self.auditDetails);
  @override
  bool equals(IndividualModel self, IndividualModel other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.individualId, other.individualId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.clientReferenceId, other.clientReferenceId) &&
      container.isEqual(self.userId, other.userId) &&
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.dateOfBirth, other.dateOfBirth) &&
      container.isEqual(self.gender, other.gender) &&
      container.isEqual(self.bloodGroup, other.bloodGroup) &&
      container.isEqual(self.mobileNumber, other.mobileNumber) &&
      container.isEqual(self.altContactNumber, other.altContactNumber) &&
      container.isEqual(self.email, other.email) &&
      container.isEqual(self.address, other.address) &&
      container.isEqual(self.fatherName, other.fatherName) &&
      container.isEqual(self.husbandName, other.husbandName) &&
      container.isEqual(self.relationship, other.relationship) &&
      container.isEqual(self.identifiers, other.identifiers) &&
      container.isEqual(self.skills, other.skills) &&
      container.isEqual(self.photo, other.photo) &&
      container.isEqual(self.additionalFields, other.additionalFields) &&
      container.isEqual(self.isDeleted, other.isDeleted) &&
      container.isEqual(self.rowVersion, other.rowVersion) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin IndividualModelMappable {
  String toJson() =>
      IndividualModelMapper.container.toJson(this as IndividualModel);
  Map<String, dynamic> toMap() =>
      IndividualModelMapper.container.toMap(this as IndividualModel);
  IndividualModelCopyWith<IndividualModel, IndividualModel, IndividualModel>
      get copyWith => _IndividualModelCopyWithImpl(
          this as IndividualModel, $identity, $identity);
  @override
  String toString() => IndividualModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualModelMapper.container.hash(this);
}

extension IndividualModelValueCopy<$R, $Out extends IndividualModel>
    on ObjectCopyWith<$R, IndividualModel, $Out> {
  IndividualModelCopyWith<$R, IndividualModel, $Out> get asIndividualModel =>
      base.as((v, t, t2) => _IndividualModelCopyWithImpl(v, t, t2));
}

typedef IndividualModelCopyWithBound = IndividualModel;

abstract class IndividualModelCopyWith<$R, $In extends IndividualModel,
    $Out extends IndividualModel> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualModel>(
          Then<IndividualModel, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _IndividualModelCopyWithImpl<$R, $Out extends IndividualModel>
    extends CopyWithBase<$R, IndividualModel, $Out>
    implements IndividualModelCopyWith<$R, IndividualModel, $Out> {
  _IndividualModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualModelCopyWith<$R2, IndividualModel, $Out2>
      chain<$R2, $Out2 extends IndividualModel>(
              Then<IndividualModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualModelCopyWithImpl($value, t, t2);

  @override
  IndividualNameCopyWith<$R, IndividualName, IndividualName>? get name =>
      $value.name?.copyWith.chain($identity, (v) => call(name: v));
  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  ListCopyWith<$R, IndividualSkills,
          IndividualSkillsCopyWith<$R, IndividualSkills, IndividualSkills>>?
      get skills => $value.skills != null
          ? ListCopyWith(
              $value.skills!,
              (v, t) => v.copyWith.chain<$R, IndividualSkills>($identity, t),
              (v) => call(skills: v))
          : null;
  @override
  IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields,
          IndividualAdditionalFields>?
      get additionalFields => $value.additionalFields?.copyWith
          .chain($identity, (v) => call(additionalFields: v));
  @override
  ListCopyWith<$R, IndividualAddress,
          IndividualAddressCopyWith<$R, IndividualAddress, IndividualAddress>>?
      get address => $value.address != null
          ? ListCopyWith(
              $value.address!,
              (v, t) => v.copyWith.chain<$R, IndividualAddress>($identity, t),
              (v) => call(address: v))
          : null;
  @override
  ListCopyWith<
      $R,
      IndividualIdentifiers,
      IndividualIdentifiersCopyWith<$R, IndividualIdentifiers,
          IndividualIdentifiers>>? get identifiers => $value.identifiers != null
      ? ListCopyWith(
          $value.identifiers!,
          (v, t) => v.copyWith.chain<$R, IndividualIdentifiers>($identity, t),
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
      $then(IndividualModel(
          id: or(id, $value.id),
          name: or(name, $value.name),
          tenantId: tenantId ?? $value.tenantId,
          auditDetails: or(auditDetails, $value.auditDetails),
          skills: or(skills, $value.skills),
          mobileNumber: or(mobileNumber, $value.mobileNumber),
          individualId: or(individualId, $value.individualId),
          gender: or(gender, $value.gender),
          userId: or(userId, $value.userId),
          additionalFields: or(additionalFields, $value.additionalFields),
          address: or(address, $value.address),
          altContactNumber: or(altContactNumber, $value.altContactNumber),
          bloodGroup: or(bloodGroup, $value.bloodGroup),
          clientReferenceId: or(clientReferenceId, $value.clientReferenceId),
          dateOfBirth: or(dateOfBirth, $value.dateOfBirth),
          email: or(email, $value.email),
          fatherName: or(fatherName, $value.fatherName),
          husbandName: or(husbandName, $value.husbandName),
          identifiers: or(identifiers, $value.identifiers),
          isDeleted: or(isDeleted, $value.isDeleted),
          photo: or(photo, $value.photo),
          relationship: or(relationship, $value.relationship),
          rowVersion: or(rowVersion, $value.rowVersion)));
}

class IndividualAddressMapper extends MapperBase<IndividualAddress> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualAddressMapper()},
  )..linkAll({
      CommonAuditDetailsMapper.container,
      AddressWardMapper.container,
      AddressLocalityMapper.container,
    });

  @override
  IndividualAddressMapperElement createElement(MapperContainer container) {
    return IndividualAddressMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualAddress';

  static final fromMap = container.fromMap<IndividualAddress>;
  static final fromJson = container.fromJson<IndividualAddress>;
}

class IndividualAddressMapperElement
    extends MapperElementBase<IndividualAddress> {
  IndividualAddressMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualAddress decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualAddress fromMap(Map<String, dynamic> map) => IndividualAddress(
      id: container.$getOpt(map, 'id'),
      tenantId: container.$getOpt(map, 'tenantId'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      isDeleted: container.$getOpt(map, 'isDeleted'),
      clientReferenceId: container.$getOpt(map, 'clientReferenceId'),
      individualId: container.$getOpt(map, 'individualId'),
      ward: container.$getOpt(map, 'ward'),
      type: container.$getOpt(map, 'type'),
      addressLine1: container.$getOpt(map, 'addressLine1'),
      addressLine2: container.$getOpt(map, 'addressLine2'),
      buildingName: container.$getOpt(map, 'buildingName'),
      city: container.$getOpt(map, 'city'),
      doorNo: container.$getOpt(map, 'doorNo'),
      landmark: container.$getOpt(map, 'landmark'),
      latitude: container.$getOpt(map, 'latitude'),
      locality: container.$getOpt(map, 'locality'),
      locationAccuracy: container.$getOpt(map, 'locationAccuracy'),
      longitude: container.$getOpt(map, 'longitude'),
      pincode: container.$getOpt(map, 'pincode'),
      street: container.$getOpt(map, 'street'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualAddress v) => toMap(v);
  Map<String, dynamic> toMap(IndividualAddress i) => {
        'id': container.$enc(i.id, 'id'),
        'tenantId': container.$enc(i.tenantId, 'tenantId'),
        'auditDetails': container.$enc(i.auditDetails, 'auditDetails'),
        'isDeleted': container.$enc(i.isDeleted, 'isDeleted'),
        'clientReferenceId':
            container.$enc(i.clientReferenceId, 'clientReferenceId'),
        'individualId': container.$enc(i.individualId, 'individualId'),
        'ward': container.$enc(i.ward, 'ward'),
        'type': container.$enc(i.type, 'type'),
        'addressLine1': container.$enc(i.addressLine1, 'addressLine1'),
        'addressLine2': container.$enc(i.addressLine2, 'addressLine2'),
        'buildingName': container.$enc(i.buildingName, 'buildingName'),
        'city': container.$enc(i.city, 'city'),
        'doorNo': container.$enc(i.doorNo, 'doorNo'),
        'landmark': container.$enc(i.landmark, 'landmark'),
        'latitude': container.$enc(i.latitude, 'latitude'),
        'locality': container.$enc(i.locality, 'locality'),
        'locationAccuracy':
            container.$enc(i.locationAccuracy, 'locationAccuracy'),
        'longitude': container.$enc(i.longitude, 'longitude'),
        'pincode': container.$enc(i.pincode, 'pincode'),
        'street': container.$enc(i.street, 'street')
      };

  @override
  String stringify(IndividualAddress self) =>
      'IndividualAddress(id: ${container.asString(self.id)}, clientReferenceId: ${container.asString(self.clientReferenceId)}, individualId: ${container.asString(self.individualId)}, tenantId: ${container.asString(self.tenantId)}, doorNo: ${container.asString(self.doorNo)}, latitude: ${container.asString(self.latitude)}, longitude: ${container.asString(self.longitude)}, locationAccuracy: ${container.asString(self.locationAccuracy)}, type: ${container.asString(self.type)}, addressLine1: ${container.asString(self.addressLine1)}, addressLine2: ${container.asString(self.addressLine2)}, landmark: ${container.asString(self.landmark)}, city: ${container.asString(self.city)}, pincode: ${container.asString(self.pincode)}, buildingName: ${container.asString(self.buildingName)}, street: ${container.asString(self.street)}, locality: ${container.asString(self.locality)}, ward: ${container.asString(self.ward)}, isDeleted: ${container.asString(self.isDeleted)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(IndividualAddress self) =>
      container.hash(self.id) ^
      container.hash(self.clientReferenceId) ^
      container.hash(self.individualId) ^
      container.hash(self.tenantId) ^
      container.hash(self.doorNo) ^
      container.hash(self.latitude) ^
      container.hash(self.longitude) ^
      container.hash(self.locationAccuracy) ^
      container.hash(self.type) ^
      container.hash(self.addressLine1) ^
      container.hash(self.addressLine2) ^
      container.hash(self.landmark) ^
      container.hash(self.city) ^
      container.hash(self.pincode) ^
      container.hash(self.buildingName) ^
      container.hash(self.street) ^
      container.hash(self.locality) ^
      container.hash(self.ward) ^
      container.hash(self.isDeleted) ^
      container.hash(self.auditDetails);
  @override
  bool equals(IndividualAddress self, IndividualAddress other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.clientReferenceId, other.clientReferenceId) &&
      container.isEqual(self.individualId, other.individualId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.doorNo, other.doorNo) &&
      container.isEqual(self.latitude, other.latitude) &&
      container.isEqual(self.longitude, other.longitude) &&
      container.isEqual(self.locationAccuracy, other.locationAccuracy) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.addressLine1, other.addressLine1) &&
      container.isEqual(self.addressLine2, other.addressLine2) &&
      container.isEqual(self.landmark, other.landmark) &&
      container.isEqual(self.city, other.city) &&
      container.isEqual(self.pincode, other.pincode) &&
      container.isEqual(self.buildingName, other.buildingName) &&
      container.isEqual(self.street, other.street) &&
      container.isEqual(self.locality, other.locality) &&
      container.isEqual(self.ward, other.ward) &&
      container.isEqual(self.isDeleted, other.isDeleted) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin IndividualAddressMappable {
  String toJson() =>
      IndividualAddressMapper.container.toJson(this as IndividualAddress);
  Map<String, dynamic> toMap() =>
      IndividualAddressMapper.container.toMap(this as IndividualAddress);
  IndividualAddressCopyWith<IndividualAddress, IndividualAddress,
          IndividualAddress>
      get copyWith => _IndividualAddressCopyWithImpl(
          this as IndividualAddress, $identity, $identity);
  @override
  String toString() => IndividualAddressMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualAddressMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualAddressMapper.container.hash(this);
}

extension IndividualAddressValueCopy<$R, $Out extends IndividualAddress>
    on ObjectCopyWith<$R, IndividualAddress, $Out> {
  IndividualAddressCopyWith<$R, IndividualAddress, $Out>
      get asIndividualAddress =>
          base.as((v, t, t2) => _IndividualAddressCopyWithImpl(v, t, t2));
}

typedef IndividualAddressCopyWithBound = IndividualAddress;

abstract class IndividualAddressCopyWith<$R, $In extends IndividualAddress,
    $Out extends IndividualAddress> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualAddressCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualAddress>(
          Then<IndividualAddress, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _IndividualAddressCopyWithImpl<$R, $Out extends IndividualAddress>
    extends CopyWithBase<$R, IndividualAddress, $Out>
    implements IndividualAddressCopyWith<$R, IndividualAddress, $Out> {
  _IndividualAddressCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualAddressCopyWith<$R2, IndividualAddress, $Out2>
      chain<$R2, $Out2 extends IndividualAddress>(
              Then<IndividualAddress, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualAddressCopyWithImpl($value, t, t2);

  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  AddressWardCopyWith<$R, AddressWard, AddressWard>? get ward =>
      $value.ward?.copyWith.chain($identity, (v) => call(ward: v));
  @override
  AddressLocalityCopyWith<$R, AddressLocality, AddressLocality>? get locality =>
      $value.locality?.copyWith.chain($identity, (v) => call(locality: v));
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
      $then(IndividualAddress(
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          auditDetails: or(auditDetails, $value.auditDetails),
          isDeleted: or(isDeleted, $value.isDeleted),
          clientReferenceId: or(clientReferenceId, $value.clientReferenceId),
          individualId: or(individualId, $value.individualId),
          ward: or(ward, $value.ward),
          type: or(type, $value.type),
          addressLine1: or(addressLine1, $value.addressLine1),
          addressLine2: or(addressLine2, $value.addressLine2),
          buildingName: or(buildingName, $value.buildingName),
          city: or(city, $value.city),
          doorNo: or(doorNo, $value.doorNo),
          landmark: or(landmark, $value.landmark),
          latitude: or(latitude, $value.latitude),
          locality: or(locality, $value.locality),
          locationAccuracy: or(locationAccuracy, $value.locationAccuracy),
          longitude: or(longitude, $value.longitude),
          pincode: or(pincode, $value.pincode),
          street: or(street, $value.street)));
}

class IndividualNameMapper extends MapperBase<IndividualName> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualNameMapper()},
  );

  @override
  IndividualNameMapperElement createElement(MapperContainer container) {
    return IndividualNameMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualName';

  static final fromMap = container.fromMap<IndividualName>;
  static final fromJson = container.fromJson<IndividualName>;
}

class IndividualNameMapperElement extends MapperElementBase<IndividualName> {
  IndividualNameMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualName decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualName fromMap(Map<String, dynamic> map) => IndividualName(
      familyName: container.$getOpt(map, 'familyName'),
      givenName: container.$getOpt(map, 'givenName'),
      otherNames: container.$getOpt(map, 'otherNames'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualName v) => toMap(v);
  Map<String, dynamic> toMap(IndividualName i) => {
        'familyName': container.$enc(i.familyName, 'familyName'),
        'givenName': container.$enc(i.givenName, 'givenName'),
        'otherNames': container.$enc(i.otherNames, 'otherNames')
      };

  @override
  String stringify(IndividualName self) =>
      'IndividualName(givenName: ${container.asString(self.givenName)}, familyName: ${container.asString(self.familyName)}, otherNames: ${container.asString(self.otherNames)})';
  @override
  int hash(IndividualName self) =>
      container.hash(self.givenName) ^
      container.hash(self.familyName) ^
      container.hash(self.otherNames);
  @override
  bool equals(IndividualName self, IndividualName other) =>
      container.isEqual(self.givenName, other.givenName) &&
      container.isEqual(self.familyName, other.familyName) &&
      container.isEqual(self.otherNames, other.otherNames);
}

mixin IndividualNameMappable {
  String toJson() =>
      IndividualNameMapper.container.toJson(this as IndividualName);
  Map<String, dynamic> toMap() =>
      IndividualNameMapper.container.toMap(this as IndividualName);
  IndividualNameCopyWith<IndividualName, IndividualName, IndividualName>
      get copyWith => _IndividualNameCopyWithImpl(
          this as IndividualName, $identity, $identity);
  @override
  String toString() => IndividualNameMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualNameMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualNameMapper.container.hash(this);
}

extension IndividualNameValueCopy<$R, $Out extends IndividualName>
    on ObjectCopyWith<$R, IndividualName, $Out> {
  IndividualNameCopyWith<$R, IndividualName, $Out> get asIndividualName =>
      base.as((v, t, t2) => _IndividualNameCopyWithImpl(v, t, t2));
}

typedef IndividualNameCopyWithBound = IndividualName;

abstract class IndividualNameCopyWith<$R, $In extends IndividualName,
    $Out extends IndividualName> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualNameCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualName>(
          Then<IndividualName, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? familyName, String? givenName, String? otherNames});
}

class _IndividualNameCopyWithImpl<$R, $Out extends IndividualName>
    extends CopyWithBase<$R, IndividualName, $Out>
    implements IndividualNameCopyWith<$R, IndividualName, $Out> {
  _IndividualNameCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualNameCopyWith<$R2, IndividualName, $Out2>
      chain<$R2, $Out2 extends IndividualName>(
              Then<IndividualName, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualNameCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? familyName = $none,
          Object? givenName = $none,
          Object? otherNames = $none}) =>
      $then(IndividualName(
          familyName: or(familyName, $value.familyName),
          givenName: or(givenName, $value.givenName),
          otherNames: or(otherNames, $value.otherNames)));
}

class IndividualIdentifiersMapper extends MapperBase<IndividualIdentifiers> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualIdentifiersMapper()},
  )..linkAll({CommonAuditDetailsMapper.container});

  @override
  IndividualIdentifiersMapperElement createElement(MapperContainer container) {
    return IndividualIdentifiersMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualIdentifiers';

  static final fromMap = container.fromMap<IndividualIdentifiers>;
  static final fromJson = container.fromJson<IndividualIdentifiers>;
}

class IndividualIdentifiersMapperElement
    extends MapperElementBase<IndividualIdentifiers> {
  IndividualIdentifiersMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualIdentifiers decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualIdentifiers fromMap(Map<String, dynamic> map) =>
      IndividualIdentifiers(
          individualId: container.$getOpt(map, 'individualId'),
          clientReferenceId: container.$getOpt(map, 'clientReferenceId'),
          isDeleted: container.$getOpt(map, 'isDeleted'),
          id: container.$getOpt(map, 'id'),
          auditDetails: container.$getOpt(map, 'auditDetails'),
          identifierId: container.$getOpt(map, 'identifierId'),
          identifierType: container.$getOpt(map, 'identifierType'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualIdentifiers v) => toMap(v);
  Map<String, dynamic> toMap(IndividualIdentifiers i) => {
        'individualId': container.$enc(i.individualId, 'individualId'),
        'clientReferenceId':
            container.$enc(i.clientReferenceId, 'clientReferenceId'),
        'isDeleted': container.$enc(i.isDeleted, 'isDeleted'),
        'id': container.$enc(i.id, 'id'),
        'auditDetails': container.$enc(i.auditDetails, 'auditDetails'),
        'identifierId': container.$enc(i.identifierId, 'identifierId'),
        'identifierType': container.$enc(i.identifierType, 'identifierType')
      };

  @override
  String stringify(IndividualIdentifiers self) =>
      'IndividualIdentifiers(id: ${container.asString(self.id)}, clientReferenceId: ${container.asString(self.clientReferenceId)}, individualId: ${container.asString(self.individualId)}, identifierType: ${container.asString(self.identifierType)}, identifierId: ${container.asString(self.identifierId)}, isDeleted: ${container.asString(self.isDeleted)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(IndividualIdentifiers self) =>
      container.hash(self.id) ^
      container.hash(self.clientReferenceId) ^
      container.hash(self.individualId) ^
      container.hash(self.identifierType) ^
      container.hash(self.identifierId) ^
      container.hash(self.isDeleted) ^
      container.hash(self.auditDetails);
  @override
  bool equals(IndividualIdentifiers self, IndividualIdentifiers other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.clientReferenceId, other.clientReferenceId) &&
      container.isEqual(self.individualId, other.individualId) &&
      container.isEqual(self.identifierType, other.identifierType) &&
      container.isEqual(self.identifierId, other.identifierId) &&
      container.isEqual(self.isDeleted, other.isDeleted) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin IndividualIdentifiersMappable {
  String toJson() => IndividualIdentifiersMapper.container
      .toJson(this as IndividualIdentifiers);
  Map<String, dynamic> toMap() => IndividualIdentifiersMapper.container
      .toMap(this as IndividualIdentifiers);
  IndividualIdentifiersCopyWith<IndividualIdentifiers, IndividualIdentifiers,
          IndividualIdentifiers>
      get copyWith => _IndividualIdentifiersCopyWithImpl(
          this as IndividualIdentifiers, $identity, $identity);
  @override
  String toString() => IndividualIdentifiersMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualIdentifiersMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualIdentifiersMapper.container.hash(this);
}

extension IndividualIdentifiersValueCopy<$R, $Out extends IndividualIdentifiers>
    on ObjectCopyWith<$R, IndividualIdentifiers, $Out> {
  IndividualIdentifiersCopyWith<$R, IndividualIdentifiers, $Out>
      get asIndividualIdentifiers =>
          base.as((v, t, t2) => _IndividualIdentifiersCopyWithImpl(v, t, t2));
}

typedef IndividualIdentifiersCopyWithBound = IndividualIdentifiers;

abstract class IndividualIdentifiersCopyWith<$R,
        $In extends IndividualIdentifiers, $Out extends IndividualIdentifiers>
    implements ObjectCopyWith<$R, $In, $Out> {
  IndividualIdentifiersCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualIdentifiers>(
          Then<IndividualIdentifiers, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _IndividualIdentifiersCopyWithImpl<$R, $Out extends IndividualIdentifiers>
    extends CopyWithBase<$R, IndividualIdentifiers, $Out>
    implements IndividualIdentifiersCopyWith<$R, IndividualIdentifiers, $Out> {
  _IndividualIdentifiersCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualIdentifiersCopyWith<$R2, IndividualIdentifiers, $Out2>
      chain<$R2, $Out2 extends IndividualIdentifiers>(
              Then<IndividualIdentifiers, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualIdentifiersCopyWithImpl($value, t, t2);

  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
  @override
  $R call(
          {Object? individualId = $none,
          Object? clientReferenceId = $none,
          Object? isDeleted = $none,
          Object? id = $none,
          Object? auditDetails = $none,
          Object? identifierId = $none,
          Object? identifierType = $none}) =>
      $then(IndividualIdentifiers(
          individualId: or(individualId, $value.individualId),
          clientReferenceId: or(clientReferenceId, $value.clientReferenceId),
          isDeleted: or(isDeleted, $value.isDeleted),
          id: or(id, $value.id),
          auditDetails: or(auditDetails, $value.auditDetails),
          identifierId: or(identifierId, $value.identifierId),
          identifierType: or(identifierType, $value.identifierType)));
}

class IndividualSkillsMapper extends MapperBase<IndividualSkills> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualSkillsMapper()},
  )..linkAll({CommonAuditDetailsMapper.container});

  @override
  IndividualSkillsMapperElement createElement(MapperContainer container) {
    return IndividualSkillsMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualSkills';

  static final fromMap = container.fromMap<IndividualSkills>;
  static final fromJson = container.fromJson<IndividualSkills>;
}

class IndividualSkillsMapperElement
    extends MapperElementBase<IndividualSkills> {
  IndividualSkillsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualSkills decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualSkills fromMap(Map<String, dynamic> map) => IndividualSkills(
      individualId: container.$getOpt(map, 'individualId'),
      clientReferenceId: container.$getOpt(map, 'clientReferenceId'),
      isDeleted: container.$getOpt(map, 'isDeleted'),
      id: container.$getOpt(map, 'id'),
      auditDetails: container.$getOpt(map, 'auditDetails'),
      type: container.$getOpt(map, 'type'),
      experience: container.$getOpt(map, 'experience'),
      level: container.$getOpt(map, 'level'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualSkills v) => toMap(v);
  Map<String, dynamic> toMap(IndividualSkills i) => {
        'individualId': container.$enc(i.individualId, 'individualId'),
        'clientReferenceId':
            container.$enc(i.clientReferenceId, 'clientReferenceId'),
        'isDeleted': container.$enc(i.isDeleted, 'isDeleted'),
        'id': container.$enc(i.id, 'id'),
        'auditDetails': container.$enc(i.auditDetails, 'auditDetails'),
        'type': container.$enc(i.type, 'type'),
        'experience': container.$enc(i.experience, 'experience'),
        'level': container.$enc(i.level, 'level')
      };

  @override
  String stringify(IndividualSkills self) =>
      'IndividualSkills(id: ${container.asString(self.id)}, clientReferenceId: ${container.asString(self.clientReferenceId)}, individualId: ${container.asString(self.individualId)}, type: ${container.asString(self.type)}, level: ${container.asString(self.level)}, experience: ${container.asString(self.experience)}, isDeleted: ${container.asString(self.isDeleted)}, auditDetails: ${container.asString(self.auditDetails)})';
  @override
  int hash(IndividualSkills self) =>
      container.hash(self.id) ^
      container.hash(self.clientReferenceId) ^
      container.hash(self.individualId) ^
      container.hash(self.type) ^
      container.hash(self.level) ^
      container.hash(self.experience) ^
      container.hash(self.isDeleted) ^
      container.hash(self.auditDetails);
  @override
  bool equals(IndividualSkills self, IndividualSkills other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.clientReferenceId, other.clientReferenceId) &&
      container.isEqual(self.individualId, other.individualId) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.level, other.level) &&
      container.isEqual(self.experience, other.experience) &&
      container.isEqual(self.isDeleted, other.isDeleted) &&
      container.isEqual(self.auditDetails, other.auditDetails);
}

mixin IndividualSkillsMappable {
  String toJson() =>
      IndividualSkillsMapper.container.toJson(this as IndividualSkills);
  Map<String, dynamic> toMap() =>
      IndividualSkillsMapper.container.toMap(this as IndividualSkills);
  IndividualSkillsCopyWith<IndividualSkills, IndividualSkills, IndividualSkills>
      get copyWith => _IndividualSkillsCopyWithImpl(
          this as IndividualSkills, $identity, $identity);
  @override
  String toString() => IndividualSkillsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualSkillsMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualSkillsMapper.container.hash(this);
}

extension IndividualSkillsValueCopy<$R, $Out extends IndividualSkills>
    on ObjectCopyWith<$R, IndividualSkills, $Out> {
  IndividualSkillsCopyWith<$R, IndividualSkills, $Out> get asIndividualSkills =>
      base.as((v, t, t2) => _IndividualSkillsCopyWithImpl(v, t, t2));
}

typedef IndividualSkillsCopyWithBound = IndividualSkills;

abstract class IndividualSkillsCopyWith<$R, $In extends IndividualSkills,
    $Out extends IndividualSkills> implements ObjectCopyWith<$R, $In, $Out> {
  IndividualSkillsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualSkills>(
          Then<IndividualSkills, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _IndividualSkillsCopyWithImpl<$R, $Out extends IndividualSkills>
    extends CopyWithBase<$R, IndividualSkills, $Out>
    implements IndividualSkillsCopyWith<$R, IndividualSkills, $Out> {
  _IndividualSkillsCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualSkillsCopyWith<$R2, IndividualSkills, $Out2>
      chain<$R2, $Out2 extends IndividualSkills>(
              Then<IndividualSkills, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualSkillsCopyWithImpl($value, t, t2);

  @override
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, CommonAuditDetails>?
      get auditDetails => $value.auditDetails?.copyWith
          .chain($identity, (v) => call(auditDetails: v));
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
      $then(IndividualSkills(
          individualId: or(individualId, $value.individualId),
          clientReferenceId: or(clientReferenceId, $value.clientReferenceId),
          isDeleted: or(isDeleted, $value.isDeleted),
          id: or(id, $value.id),
          auditDetails: or(auditDetails, $value.auditDetails),
          type: or(type, $value.type),
          experience: or(experience, $value.experience),
          level: or(level, $value.level)));
}

class CommonAuditDetailsMapper extends MapperBase<CommonAuditDetails> {
  static MapperContainer container = MapperContainer(
    mappers: {CommonAuditDetailsMapper()},
  );

  @override
  CommonAuditDetailsMapperElement createElement(MapperContainer container) {
    return CommonAuditDetailsMapperElement._(this, container);
  }

  @override
  String get id => 'CommonAuditDetails';

  static final fromMap = container.fromMap<CommonAuditDetails>;
  static final fromJson = container.fromJson<CommonAuditDetails>;
}

class CommonAuditDetailsMapperElement
    extends MapperElementBase<CommonAuditDetails> {
  CommonAuditDetailsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  CommonAuditDetails decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  CommonAuditDetails fromMap(Map<String, dynamic> map) => CommonAuditDetails(
      createdTime: container.$getOpt(map, 'createdTime'),
      lastModifiedTime: container.$getOpt(map, 'lastModifiedTime'),
      createdBy: container.$getOpt(map, 'createdBy'),
      lastModifiedBy: container.$getOpt(map, 'lastModifiedBy'));

  @override
  Function get encoder => encode;
  dynamic encode(CommonAuditDetails v) => toMap(v);
  Map<String, dynamic> toMap(CommonAuditDetails c) => {
        'createdTime': container.$enc(c.createdTime, 'createdTime'),
        'lastModifiedTime':
            container.$enc(c.lastModifiedTime, 'lastModifiedTime'),
        'createdBy': container.$enc(c.createdBy, 'createdBy'),
        'lastModifiedBy': container.$enc(c.lastModifiedBy, 'lastModifiedBy')
      };

  @override
  String stringify(CommonAuditDetails self) =>
      'CommonAuditDetails(createdBy: ${container.asString(self.createdBy)}, lastModifiedBy: ${container.asString(self.lastModifiedBy)}, createdTime: ${container.asString(self.createdTime)}, lastModifiedTime: ${container.asString(self.lastModifiedTime)})';
  @override
  int hash(CommonAuditDetails self) =>
      container.hash(self.createdBy) ^
      container.hash(self.lastModifiedBy) ^
      container.hash(self.createdTime) ^
      container.hash(self.lastModifiedTime);
  @override
  bool equals(CommonAuditDetails self, CommonAuditDetails other) =>
      container.isEqual(self.createdBy, other.createdBy) &&
      container.isEqual(self.lastModifiedBy, other.lastModifiedBy) &&
      container.isEqual(self.createdTime, other.createdTime) &&
      container.isEqual(self.lastModifiedTime, other.lastModifiedTime);
}

mixin CommonAuditDetailsMappable {
  String toJson() =>
      CommonAuditDetailsMapper.container.toJson(this as CommonAuditDetails);
  Map<String, dynamic> toMap() =>
      CommonAuditDetailsMapper.container.toMap(this as CommonAuditDetails);
  CommonAuditDetailsCopyWith<CommonAuditDetails, CommonAuditDetails,
          CommonAuditDetails>
      get copyWith => _CommonAuditDetailsCopyWithImpl(
          this as CommonAuditDetails, $identity, $identity);
  @override
  String toString() => CommonAuditDetailsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          CommonAuditDetailsMapper.container.isEqual(this, other));
  @override
  int get hashCode => CommonAuditDetailsMapper.container.hash(this);
}

extension CommonAuditDetailsValueCopy<$R, $Out extends CommonAuditDetails>
    on ObjectCopyWith<$R, CommonAuditDetails, $Out> {
  CommonAuditDetailsCopyWith<$R, CommonAuditDetails, $Out>
      get asCommonAuditDetails =>
          base.as((v, t, t2) => _CommonAuditDetailsCopyWithImpl(v, t, t2));
}

typedef CommonAuditDetailsCopyWithBound = CommonAuditDetails;

abstract class CommonAuditDetailsCopyWith<$R, $In extends CommonAuditDetails,
    $Out extends CommonAuditDetails> implements ObjectCopyWith<$R, $In, $Out> {
  CommonAuditDetailsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends CommonAuditDetails>(
          Then<CommonAuditDetails, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {int? createdTime,
      int? lastModifiedTime,
      String? createdBy,
      String? lastModifiedBy});
}

class _CommonAuditDetailsCopyWithImpl<$R, $Out extends CommonAuditDetails>
    extends CopyWithBase<$R, CommonAuditDetails, $Out>
    implements CommonAuditDetailsCopyWith<$R, CommonAuditDetails, $Out> {
  _CommonAuditDetailsCopyWithImpl(super.value, super.then, super.then2);
  @override
  CommonAuditDetailsCopyWith<$R2, CommonAuditDetails, $Out2>
      chain<$R2, $Out2 extends CommonAuditDetails>(
              Then<CommonAuditDetails, $Out2> t, Then<$Out2, $R2> t2) =>
          _CommonAuditDetailsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? createdTime = $none,
          Object? lastModifiedTime = $none,
          Object? createdBy = $none,
          Object? lastModifiedBy = $none}) =>
      $then(CommonAuditDetails(
          createdTime: or(createdTime, $value.createdTime),
          lastModifiedTime: or(lastModifiedTime, $value.lastModifiedTime),
          createdBy: or(createdBy, $value.createdBy),
          lastModifiedBy: or(lastModifiedBy, $value.lastModifiedBy)));
}

class IndividualAdditionalFieldsMapper
    extends MapperBase<IndividualAdditionalFields> {
  static MapperContainer container = MapperContainer(
    mappers: {IndividualAdditionalFieldsMapper()},
  )..linkAll({AdditionalIndividualFieldsMapper.container});

  @override
  IndividualAdditionalFieldsMapperElement createElement(
      MapperContainer container) {
    return IndividualAdditionalFieldsMapperElement._(this, container);
  }

  @override
  String get id => 'IndividualAdditionalFields';

  static final fromMap = container.fromMap<IndividualAdditionalFields>;
  static final fromJson = container.fromJson<IndividualAdditionalFields>;
}

class IndividualAdditionalFieldsMapperElement
    extends MapperElementBase<IndividualAdditionalFields> {
  IndividualAdditionalFieldsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  IndividualAdditionalFields decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  IndividualAdditionalFields fromMap(Map<String, dynamic> map) =>
      IndividualAdditionalFields(
          status: container.$getOpt(map, 'status'),
          version: container.$getOpt(map, 'version'),
          documentType: container.$getOpt(map, 'documentType'),
          fields: container.$getOpt(map, 'fields'),
          documentUid: container.$getOpt(map, 'documentUid'),
          fileStore: container.$getOpt(map, 'fileStore'),
          schema: container.$getOpt(map, 'schema'));

  @override
  Function get encoder => encode;
  dynamic encode(IndividualAdditionalFields v) => toMap(v);
  Map<String, dynamic> toMap(IndividualAdditionalFields i) => {
        'status': container.$enc(i.status, 'status'),
        'version': container.$enc(i.version, 'version'),
        'documentType': container.$enc(i.documentType, 'documentType'),
        'fields': container.$enc(i.fields, 'fields'),
        'documentUid': container.$enc(i.documentUid, 'documentUid'),
        'fileStore': container.$enc(i.fileStore, 'fileStore'),
        'schema': container.$enc(i.schema, 'schema')
      };

  @override
  String stringify(IndividualAdditionalFields self) =>
      'IndividualAdditionalFields(schema: ${container.asString(self.schema)}, version: ${container.asString(self.version)}, fields: ${container.asString(self.fields)}, documentType: ${container.asString(self.documentType)}, fileStore: ${container.asString(self.fileStore)}, documentUid: ${container.asString(self.documentUid)}, status: ${container.asString(self.status)})';
  @override
  int hash(IndividualAdditionalFields self) =>
      container.hash(self.schema) ^
      container.hash(self.version) ^
      container.hash(self.fields) ^
      container.hash(self.documentType) ^
      container.hash(self.fileStore) ^
      container.hash(self.documentUid) ^
      container.hash(self.status);
  @override
  bool equals(
          IndividualAdditionalFields self, IndividualAdditionalFields other) =>
      container.isEqual(self.schema, other.schema) &&
      container.isEqual(self.version, other.version) &&
      container.isEqual(self.fields, other.fields) &&
      container.isEqual(self.documentType, other.documentType) &&
      container.isEqual(self.fileStore, other.fileStore) &&
      container.isEqual(self.documentUid, other.documentUid) &&
      container.isEqual(self.status, other.status);
}

mixin IndividualAdditionalFieldsMappable {
  String toJson() => IndividualAdditionalFieldsMapper.container
      .toJson(this as IndividualAdditionalFields);
  Map<String, dynamic> toMap() => IndividualAdditionalFieldsMapper.container
      .toMap(this as IndividualAdditionalFields);
  IndividualAdditionalFieldsCopyWith<IndividualAdditionalFields,
          IndividualAdditionalFields, IndividualAdditionalFields>
      get copyWith => _IndividualAdditionalFieldsCopyWithImpl(
          this as IndividualAdditionalFields, $identity, $identity);
  @override
  String toString() =>
      IndividualAdditionalFieldsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          IndividualAdditionalFieldsMapper.container.isEqual(this, other));
  @override
  int get hashCode => IndividualAdditionalFieldsMapper.container.hash(this);
}

extension IndividualAdditionalFieldsValueCopy<$R,
        $Out extends IndividualAdditionalFields>
    on ObjectCopyWith<$R, IndividualAdditionalFields, $Out> {
  IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields, $Out>
      get asIndividualAdditionalFields => base
          .as((v, t, t2) => _IndividualAdditionalFieldsCopyWithImpl(v, t, t2));
}

typedef IndividualAdditionalFieldsCopyWithBound = IndividualAdditionalFields;

abstract class IndividualAdditionalFieldsCopyWith<
        $R,
        $In extends IndividualAdditionalFields,
        $Out extends IndividualAdditionalFields>
    implements ObjectCopyWith<$R, $In, $Out> {
  IndividualAdditionalFieldsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends IndividualAdditionalFields>(
          Then<IndividualAdditionalFields, $Out2> t, Then<$Out2, $R2> t2);
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
}

class _IndividualAdditionalFieldsCopyWithImpl<$R,
        $Out extends IndividualAdditionalFields>
    extends CopyWithBase<$R, IndividualAdditionalFields, $Out>
    implements
        IndividualAdditionalFieldsCopyWith<$R, IndividualAdditionalFields,
            $Out> {
  _IndividualAdditionalFieldsCopyWithImpl(super.value, super.then, super.then2);
  @override
  IndividualAdditionalFieldsCopyWith<$R2, IndividualAdditionalFields, $Out2>
      chain<$R2, $Out2 extends IndividualAdditionalFields>(
              Then<IndividualAdditionalFields, $Out2> t, Then<$Out2, $R2> t2) =>
          _IndividualAdditionalFieldsCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<
      $R,
      AdditionalIndividualFields,
      AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields,
          AdditionalIndividualFields>>? get fields => $value.fields != null
      ? ListCopyWith(
          $value.fields!,
          (v, t) =>
              v.copyWith.chain<$R, AdditionalIndividualFields>($identity, t),
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
      $then(IndividualAdditionalFields(
          status: or(status, $value.status),
          version: or(version, $value.version),
          documentType: or(documentType, $value.documentType),
          fields: or(fields, $value.fields),
          documentUid: or(documentUid, $value.documentUid),
          fileStore: or(fileStore, $value.fileStore),
          schema: or(schema, $value.schema)));
}

class AddressLocalityMapper extends MapperBase<AddressLocality> {
  static MapperContainer container = MapperContainer(
    mappers: {AddressLocalityMapper()},
  );

  @override
  AddressLocalityMapperElement createElement(MapperContainer container) {
    return AddressLocalityMapperElement._(this, container);
  }

  @override
  String get id => 'AddressLocality';

  static final fromMap = container.fromMap<AddressLocality>;
  static final fromJson = container.fromJson<AddressLocality>;
}

class AddressLocalityMapperElement extends MapperElementBase<AddressLocality> {
  AddressLocalityMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  AddressLocality decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  AddressLocality fromMap(Map<String, dynamic> map) => AddressLocality(
      code: container.$getOpt(map, 'code'),
      name: container.$getOpt(map, 'name'),
      label: container.$getOpt(map, 'label'),
      latitude: container.$getOpt(map, 'latitude'),
      longitude: container.$getOpt(map, 'longitude'),
      materializedPath: container.$getOpt(map, 'materializedPath'));

  @override
  Function get encoder => encode;
  dynamic encode(AddressLocality v) => toMap(v);
  Map<String, dynamic> toMap(AddressLocality a) => {
        'code': container.$enc(a.code, 'code'),
        'name': container.$enc(a.name, 'name'),
        'label': container.$enc(a.label, 'label'),
        'latitude': container.$enc(a.latitude, 'latitude'),
        'longitude': container.$enc(a.longitude, 'longitude'),
        'materializedPath':
            container.$enc(a.materializedPath, 'materializedPath')
      };

  @override
  String stringify(AddressLocality self) =>
      'AddressLocality(code: ${container.asString(self.code)}, name: ${container.asString(self.name)}, label: ${container.asString(self.label)}, latitude: ${container.asString(self.latitude)}, longitude: ${container.asString(self.longitude)}, materializedPath: ${container.asString(self.materializedPath)})';
  @override
  int hash(AddressLocality self) =>
      container.hash(self.code) ^
      container.hash(self.name) ^
      container.hash(self.label) ^
      container.hash(self.latitude) ^
      container.hash(self.longitude) ^
      container.hash(self.materializedPath);
  @override
  bool equals(AddressLocality self, AddressLocality other) =>
      container.isEqual(self.code, other.code) &&
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.label, other.label) &&
      container.isEqual(self.latitude, other.latitude) &&
      container.isEqual(self.longitude, other.longitude) &&
      container.isEqual(self.materializedPath, other.materializedPath);
}

mixin AddressLocalityMappable {
  String toJson() =>
      AddressLocalityMapper.container.toJson(this as AddressLocality);
  Map<String, dynamic> toMap() =>
      AddressLocalityMapper.container.toMap(this as AddressLocality);
  AddressLocalityCopyWith<AddressLocality, AddressLocality, AddressLocality>
      get copyWith => _AddressLocalityCopyWithImpl(
          this as AddressLocality, $identity, $identity);
  @override
  String toString() => AddressLocalityMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          AddressLocalityMapper.container.isEqual(this, other));
  @override
  int get hashCode => AddressLocalityMapper.container.hash(this);
}

extension AddressLocalityValueCopy<$R, $Out extends AddressLocality>
    on ObjectCopyWith<$R, AddressLocality, $Out> {
  AddressLocalityCopyWith<$R, AddressLocality, $Out> get asAddressLocality =>
      base.as((v, t, t2) => _AddressLocalityCopyWithImpl(v, t, t2));
}

typedef AddressLocalityCopyWithBound = AddressLocality;

abstract class AddressLocalityCopyWith<$R, $In extends AddressLocality,
    $Out extends AddressLocality> implements ObjectCopyWith<$R, $In, $Out> {
  AddressLocalityCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends AddressLocality>(
          Then<AddressLocality, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? code,
      String? name,
      String? label,
      double? latitude,
      double? longitude,
      String? materializedPath});
}

class _AddressLocalityCopyWithImpl<$R, $Out extends AddressLocality>
    extends CopyWithBase<$R, AddressLocality, $Out>
    implements AddressLocalityCopyWith<$R, AddressLocality, $Out> {
  _AddressLocalityCopyWithImpl(super.value, super.then, super.then2);
  @override
  AddressLocalityCopyWith<$R2, AddressLocality, $Out2>
      chain<$R2, $Out2 extends AddressLocality>(
              Then<AddressLocality, $Out2> t, Then<$Out2, $R2> t2) =>
          _AddressLocalityCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? code = $none,
          Object? name = $none,
          Object? label = $none,
          Object? latitude = $none,
          Object? longitude = $none,
          Object? materializedPath = $none}) =>
      $then(AddressLocality(
          code: or(code, $value.code),
          name: or(name, $value.name),
          label: or(label, $value.label),
          latitude: or(latitude, $value.latitude),
          longitude: or(longitude, $value.longitude),
          materializedPath: or(materializedPath, $value.materializedPath)));
}

class AddressWardMapper extends MapperBase<AddressWard> {
  static MapperContainer container = MapperContainer(
    mappers: {AddressWardMapper()},
  );

  @override
  AddressWardMapperElement createElement(MapperContainer container) {
    return AddressWardMapperElement._(this, container);
  }

  @override
  String get id => 'AddressWard';

  static final fromMap = container.fromMap<AddressWard>;
  static final fromJson = container.fromJson<AddressWard>;
}

class AddressWardMapperElement extends MapperElementBase<AddressWard> {
  AddressWardMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  AddressWard decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  AddressWard fromMap(Map<String, dynamic> map) => AddressWard(
      code: container.$getOpt(map, 'code'),
      name: container.$getOpt(map, 'name'),
      label: container.$getOpt(map, 'label'),
      latitude: container.$getOpt(map, 'latitude'),
      longitude: container.$getOpt(map, 'longitude'),
      materializedPath: container.$getOpt(map, 'materializedPath'));

  @override
  Function get encoder => encode;
  dynamic encode(AddressWard v) => toMap(v);
  Map<String, dynamic> toMap(AddressWard a) => {
        'code': container.$enc(a.code, 'code'),
        'name': container.$enc(a.name, 'name'),
        'label': container.$enc(a.label, 'label'),
        'latitude': container.$enc(a.latitude, 'latitude'),
        'longitude': container.$enc(a.longitude, 'longitude'),
        'materializedPath':
            container.$enc(a.materializedPath, 'materializedPath')
      };

  @override
  String stringify(AddressWard self) =>
      'AddressWard(code: ${container.asString(self.code)}, name: ${container.asString(self.name)}, label: ${container.asString(self.label)}, latitude: ${container.asString(self.latitude)}, longitude: ${container.asString(self.longitude)}, materializedPath: ${container.asString(self.materializedPath)})';
  @override
  int hash(AddressWard self) =>
      container.hash(self.code) ^
      container.hash(self.name) ^
      container.hash(self.label) ^
      container.hash(self.latitude) ^
      container.hash(self.longitude) ^
      container.hash(self.materializedPath);
  @override
  bool equals(AddressWard self, AddressWard other) =>
      container.isEqual(self.code, other.code) &&
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.label, other.label) &&
      container.isEqual(self.latitude, other.latitude) &&
      container.isEqual(self.longitude, other.longitude) &&
      container.isEqual(self.materializedPath, other.materializedPath);
}

mixin AddressWardMappable {
  String toJson() => AddressWardMapper.container.toJson(this as AddressWard);
  Map<String, dynamic> toMap() =>
      AddressWardMapper.container.toMap(this as AddressWard);
  AddressWardCopyWith<AddressWard, AddressWard, AddressWard> get copyWith =>
      _AddressWardCopyWithImpl(this as AddressWard, $identity, $identity);
  @override
  String toString() => AddressWardMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          AddressWardMapper.container.isEqual(this, other));
  @override
  int get hashCode => AddressWardMapper.container.hash(this);
}

extension AddressWardValueCopy<$R, $Out extends AddressWard>
    on ObjectCopyWith<$R, AddressWard, $Out> {
  AddressWardCopyWith<$R, AddressWard, $Out> get asAddressWard =>
      base.as((v, t, t2) => _AddressWardCopyWithImpl(v, t, t2));
}

typedef AddressWardCopyWithBound = AddressWard;

abstract class AddressWardCopyWith<$R, $In extends AddressWard,
    $Out extends AddressWard> implements ObjectCopyWith<$R, $In, $Out> {
  AddressWardCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends AddressWard>(
      Then<AddressWard, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? code,
      String? name,
      String? label,
      double? latitude,
      double? longitude,
      String? materializedPath});
}

class _AddressWardCopyWithImpl<$R, $Out extends AddressWard>
    extends CopyWithBase<$R, AddressWard, $Out>
    implements AddressWardCopyWith<$R, AddressWard, $Out> {
  _AddressWardCopyWithImpl(super.value, super.then, super.then2);
  @override
  AddressWardCopyWith<$R2, AddressWard, $Out2>
      chain<$R2, $Out2 extends AddressWard>(
              Then<AddressWard, $Out2> t, Then<$Out2, $R2> t2) =>
          _AddressWardCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? code = $none,
          Object? name = $none,
          Object? label = $none,
          Object? latitude = $none,
          Object? longitude = $none,
          Object? materializedPath = $none}) =>
      $then(AddressWard(
          code: or(code, $value.code),
          name: or(name, $value.name),
          label: or(label, $value.label),
          latitude: or(latitude, $value.latitude),
          longitude: or(longitude, $value.longitude),
          materializedPath: or(materializedPath, $value.materializedPath)));
}

class AdditionalIndividualFieldsMapper
    extends MapperBase<AdditionalIndividualFields> {
  static MapperContainer container = MapperContainer(
    mappers: {AdditionalIndividualFieldsMapper()},
  );

  @override
  AdditionalIndividualFieldsMapperElement createElement(
      MapperContainer container) {
    return AdditionalIndividualFieldsMapperElement._(this, container);
  }

  @override
  String get id => 'AdditionalIndividualFields';

  static final fromMap = container.fromMap<AdditionalIndividualFields>;
  static final fromJson = container.fromJson<AdditionalIndividualFields>;
}

class AdditionalIndividualFieldsMapperElement
    extends MapperElementBase<AdditionalIndividualFields> {
  AdditionalIndividualFieldsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  AdditionalIndividualFields decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  AdditionalIndividualFields fromMap(Map<String, dynamic> map) =>
      AdditionalIndividualFields(
          key: container.$getOpt(map, 'key'),
          value: container.$getOpt(map, 'value'));

  @override
  Function get encoder => encode;
  dynamic encode(AdditionalIndividualFields v) => toMap(v);
  Map<String, dynamic> toMap(AdditionalIndividualFields a) => {
        'key': container.$enc(a.key, 'key'),
        'value': container.$enc(a.value, 'value')
      };

  @override
  String stringify(AdditionalIndividualFields self) =>
      'AdditionalIndividualFields(key: ${container.asString(self.key)}, value: ${container.asString(self.value)})';
  @override
  int hash(AdditionalIndividualFields self) =>
      container.hash(self.key) ^ container.hash(self.value);
  @override
  bool equals(
          AdditionalIndividualFields self, AdditionalIndividualFields other) =>
      container.isEqual(self.key, other.key) &&
      container.isEqual(self.value, other.value);
}

mixin AdditionalIndividualFieldsMappable {
  String toJson() => AdditionalIndividualFieldsMapper.container
      .toJson(this as AdditionalIndividualFields);
  Map<String, dynamic> toMap() => AdditionalIndividualFieldsMapper.container
      .toMap(this as AdditionalIndividualFields);
  AdditionalIndividualFieldsCopyWith<AdditionalIndividualFields,
          AdditionalIndividualFields, AdditionalIndividualFields>
      get copyWith => _AdditionalIndividualFieldsCopyWithImpl(
          this as AdditionalIndividualFields, $identity, $identity);
  @override
  String toString() =>
      AdditionalIndividualFieldsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          AdditionalIndividualFieldsMapper.container.isEqual(this, other));
  @override
  int get hashCode => AdditionalIndividualFieldsMapper.container.hash(this);
}

extension AdditionalIndividualFieldsValueCopy<$R,
        $Out extends AdditionalIndividualFields>
    on ObjectCopyWith<$R, AdditionalIndividualFields, $Out> {
  AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields, $Out>
      get asAdditionalIndividualFields => base
          .as((v, t, t2) => _AdditionalIndividualFieldsCopyWithImpl(v, t, t2));
}

typedef AdditionalIndividualFieldsCopyWithBound = AdditionalIndividualFields;

abstract class AdditionalIndividualFieldsCopyWith<
        $R,
        $In extends AdditionalIndividualFields,
        $Out extends AdditionalIndividualFields>
    implements ObjectCopyWith<$R, $In, $Out> {
  AdditionalIndividualFieldsCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends AdditionalIndividualFields>(
          Then<AdditionalIndividualFields, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? key, String? value});
}

class _AdditionalIndividualFieldsCopyWithImpl<$R,
        $Out extends AdditionalIndividualFields>
    extends CopyWithBase<$R, AdditionalIndividualFields, $Out>
    implements
        AdditionalIndividualFieldsCopyWith<$R, AdditionalIndividualFields,
            $Out> {
  _AdditionalIndividualFieldsCopyWithImpl(super.value, super.then, super.then2);
  @override
  AdditionalIndividualFieldsCopyWith<$R2, AdditionalIndividualFields, $Out2>
      chain<$R2, $Out2 extends AdditionalIndividualFields>(
              Then<AdditionalIndividualFields, $Out2> t, Then<$Out2, $R2> t2) =>
          _AdditionalIndividualFieldsCopyWithImpl($value, t, t2);

  @override
  $R call({Object? key = $none, Object? value = $none}) =>
      $then(AdditionalIndividualFields(
          key: or(key, $value.key), value: or(value, $value.value)));
}
