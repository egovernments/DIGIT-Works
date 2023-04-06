// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element

part of 'organisation_model.dart';

class OrganisationListModelMapper extends MapperBase<OrganisationListModel> {
  static MapperContainer container = MapperContainer(
    mappers: {OrganisationListModelMapper()},
  )..linkAll({OrganisationModelMapper.container});

  @override
  OrganisationListModelMapperElement createElement(MapperContainer container) {
    return OrganisationListModelMapperElement._(this, container);
  }

  @override
  String get id => 'OrganisationListModel';

  static final fromMap = container.fromMap<OrganisationListModel>;
  static final fromJson = container.fromJson<OrganisationListModel>;
}

class OrganisationListModelMapperElement
    extends MapperElementBase<OrganisationListModel> {
  OrganisationListModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrganisationListModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrganisationListModel fromMap(Map<String, dynamic> map) =>
      OrganisationListModel(
          organisations: container.$getOpt(map, 'organisations'));

  @override
  Function get encoder => encode;
  dynamic encode(OrganisationListModel v) => toMap(v);
  Map<String, dynamic> toMap(OrganisationListModel o) =>
      {'organisations': container.$enc(o.organisations, 'organisations')};

  @override
  String stringify(OrganisationListModel self) =>
      'OrganisationListModel(organisations: ${container.asString(self.organisations)})';
  @override
  int hash(OrganisationListModel self) => container.hash(self.organisations);
  @override
  bool equals(OrganisationListModel self, OrganisationListModel other) =>
      container.isEqual(self.organisations, other.organisations);
}

mixin OrganisationListModelMappable {
  String toJson() => OrganisationListModelMapper.container
      .toJson(this as OrganisationListModel);
  Map<String, dynamic> toMap() => OrganisationListModelMapper.container
      .toMap(this as OrganisationListModel);
  OrganisationListModelCopyWith<OrganisationListModel, OrganisationListModel,
          OrganisationListModel>
      get copyWith => _OrganisationListModelCopyWithImpl(
          this as OrganisationListModel, $identity, $identity);
  @override
  String toString() => OrganisationListModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrganisationListModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrganisationListModelMapper.container.hash(this);
}

extension OrganisationListModelValueCopy<$R, $Out extends OrganisationListModel>
    on ObjectCopyWith<$R, OrganisationListModel, $Out> {
  OrganisationListModelCopyWith<$R, OrganisationListModel, $Out>
      get asOrganisationListModel =>
          base.as((v, t, t2) => _OrganisationListModelCopyWithImpl(v, t, t2));
}

typedef OrganisationListModelCopyWithBound = OrganisationListModel;

abstract class OrganisationListModelCopyWith<$R,
        $In extends OrganisationListModel, $Out extends OrganisationListModel>
    implements ObjectCopyWith<$R, $In, $Out> {
  OrganisationListModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends OrganisationListModel>(
          Then<OrganisationListModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, OrganisationModel,
          OrganisationModelCopyWith<$R, OrganisationModel, OrganisationModel>>?
      get organisations;
  $R call({List<OrganisationModel>? organisations});
}

class _OrganisationListModelCopyWithImpl<$R, $Out extends OrganisationListModel>
    extends CopyWithBase<$R, OrganisationListModel, $Out>
    implements OrganisationListModelCopyWith<$R, OrganisationListModel, $Out> {
  _OrganisationListModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrganisationListModelCopyWith<$R2, OrganisationListModel, $Out2>
      chain<$R2, $Out2 extends OrganisationListModel>(
              Then<OrganisationListModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrganisationListModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, OrganisationModel,
          OrganisationModelCopyWith<$R, OrganisationModel, OrganisationModel>>?
      get organisations => $value.organisations != null
          ? ListCopyWith(
              $value.organisations!,
              (v, t) => v.copyWith.chain<$R, OrganisationModel>($identity, t),
              (v) => call(organisations: v))
          : null;
  @override
  $R call({Object? organisations = $none}) => $then(OrganisationListModel(
      organisations: or(organisations, $value.organisations)));
}

class OrganisationModelMapper extends MapperBase<OrganisationModel> {
  static MapperContainer container = MapperContainer(
    mappers: {OrganisationModelMapper()},
  )..linkAll({
      OrgAddressMapper.container,
      OrgContactMapper.container,
      OrgIdentifierMapper.container,
    });

  @override
  OrganisationModelMapperElement createElement(MapperContainer container) {
    return OrganisationModelMapperElement._(this, container);
  }

  @override
  String get id => 'OrganisationModel';

  static final fromMap = container.fromMap<OrganisationModel>;
  static final fromJson = container.fromJson<OrganisationModel>;
}

class OrganisationModelMapperElement
    extends MapperElementBase<OrganisationModel> {
  OrganisationModelMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrganisationModel decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrganisationModel fromMap(Map<String, dynamic> map) => OrganisationModel(
      name: container.$getOpt(map, 'name'),
      id: container.$getOpt(map, 'id'),
      orgNumber: container.$getOpt(map, 'orgNumber'),
      applicationNumber: container.$getOpt(map, 'applicationNumber'),
      tenantId: container.$getOpt(map, 'tenantId'),
      applicationStatus: container.$getOpt(map, 'applicationStatus'),
      externalRefNumber: container.$getOpt(map, 'externalRefNumber'),
      orgAddress: container.$getOpt(map, 'orgAddress'),
      contactDetails: container.$getOpt(map, 'contactDetails'),
      identifiers: container.$getOpt(map, 'identifiers'));

  @override
  Function get encoder => encode;
  dynamic encode(OrganisationModel v) => toMap(v);
  Map<String, dynamic> toMap(OrganisationModel o) => {
        'name': container.$enc(o.name, 'name'),
        'id': container.$enc(o.id, 'id'),
        'orgNumber': container.$enc(o.orgNumber, 'orgNumber'),
        'applicationNumber':
            container.$enc(o.applicationNumber, 'applicationNumber'),
        'tenantId': container.$enc(o.tenantId, 'tenantId'),
        'applicationStatus':
            container.$enc(o.applicationStatus, 'applicationStatus'),
        'externalRefNumber':
            container.$enc(o.externalRefNumber, 'externalRefNumber'),
        'orgAddress': container.$enc(o.orgAddress, 'orgAddress'),
        'contactDetails': container.$enc(o.contactDetails, 'contactDetails'),
        'identifiers': container.$enc(o.identifiers, 'identifiers')
      };

  @override
  String stringify(OrganisationModel self) =>
      'OrganisationModel(name: ${container.asString(self.name)}, applicationNumber: ${container.asString(self.applicationNumber)}, tenantId: ${container.asString(self.tenantId)}, id: ${container.asString(self.id)}, orgNumber: ${container.asString(self.orgNumber)}, applicationStatus: ${container.asString(self.applicationStatus)}, externalRefNumber: ${container.asString(self.externalRefNumber)}, orgAddress: ${container.asString(self.orgAddress)}, contactDetails: ${container.asString(self.contactDetails)}, identifiers: ${container.asString(self.identifiers)})';
  @override
  int hash(OrganisationModel self) =>
      container.hash(self.name) ^
      container.hash(self.applicationNumber) ^
      container.hash(self.tenantId) ^
      container.hash(self.id) ^
      container.hash(self.orgNumber) ^
      container.hash(self.applicationStatus) ^
      container.hash(self.externalRefNumber) ^
      container.hash(self.orgAddress) ^
      container.hash(self.contactDetails) ^
      container.hash(self.identifiers);
  @override
  bool equals(OrganisationModel self, OrganisationModel other) =>
      container.isEqual(self.name, other.name) &&
      container.isEqual(self.applicationNumber, other.applicationNumber) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.orgNumber, other.orgNumber) &&
      container.isEqual(self.applicationStatus, other.applicationStatus) &&
      container.isEqual(self.externalRefNumber, other.externalRefNumber) &&
      container.isEqual(self.orgAddress, other.orgAddress) &&
      container.isEqual(self.contactDetails, other.contactDetails) &&
      container.isEqual(self.identifiers, other.identifiers);
}

mixin OrganisationModelMappable {
  String toJson() =>
      OrganisationModelMapper.container.toJson(this as OrganisationModel);
  Map<String, dynamic> toMap() =>
      OrganisationModelMapper.container.toMap(this as OrganisationModel);
  OrganisationModelCopyWith<OrganisationModel, OrganisationModel,
          OrganisationModel>
      get copyWith => _OrganisationModelCopyWithImpl(
          this as OrganisationModel, $identity, $identity);
  @override
  String toString() => OrganisationModelMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrganisationModelMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrganisationModelMapper.container.hash(this);
}

extension OrganisationModelValueCopy<$R, $Out extends OrganisationModel>
    on ObjectCopyWith<$R, OrganisationModel, $Out> {
  OrganisationModelCopyWith<$R, OrganisationModel, $Out>
      get asOrganisationModel =>
          base.as((v, t, t2) => _OrganisationModelCopyWithImpl(v, t, t2));
}

typedef OrganisationModelCopyWithBound = OrganisationModel;

abstract class OrganisationModelCopyWith<$R, $In extends OrganisationModel,
    $Out extends OrganisationModel> implements ObjectCopyWith<$R, $In, $Out> {
  OrganisationModelCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends OrganisationModel>(
          Then<OrganisationModel, $Out2> t, Then<$Out2, $R2> t2);
  ListCopyWith<$R, OrgAddress, OrgAddressCopyWith<$R, OrgAddress, OrgAddress>>?
      get orgAddress;
  ListCopyWith<$R, OrgContact, OrgContactCopyWith<$R, OrgContact, OrgContact>>?
      get contactDetails;
  ListCopyWith<$R, OrgIdentifier,
      OrgIdentifierCopyWith<$R, OrgIdentifier, OrgIdentifier>>? get identifiers;
  $R call(
      {String? name,
      String? id,
      String? orgNumber,
      String? applicationNumber,
      String? tenantId,
      String? applicationStatus,
      String? externalRefNumber,
      List<OrgAddress>? orgAddress,
      List<OrgContact>? contactDetails,
      List<OrgIdentifier>? identifiers});
}

class _OrganisationModelCopyWithImpl<$R, $Out extends OrganisationModel>
    extends CopyWithBase<$R, OrganisationModel, $Out>
    implements OrganisationModelCopyWith<$R, OrganisationModel, $Out> {
  _OrganisationModelCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrganisationModelCopyWith<$R2, OrganisationModel, $Out2>
      chain<$R2, $Out2 extends OrganisationModel>(
              Then<OrganisationModel, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrganisationModelCopyWithImpl($value, t, t2);

  @override
  ListCopyWith<$R, OrgAddress, OrgAddressCopyWith<$R, OrgAddress, OrgAddress>>?
      get orgAddress => $value.orgAddress != null
          ? ListCopyWith(
              $value.orgAddress!,
              (v, t) => v.copyWith.chain<$R, OrgAddress>($identity, t),
              (v) => call(orgAddress: v))
          : null;
  @override
  ListCopyWith<$R, OrgContact, OrgContactCopyWith<$R, OrgContact, OrgContact>>?
      get contactDetails => $value.contactDetails != null
          ? ListCopyWith(
              $value.contactDetails!,
              (v, t) => v.copyWith.chain<$R, OrgContact>($identity, t),
              (v) => call(contactDetails: v))
          : null;
  @override
  ListCopyWith<$R, OrgIdentifier,
          OrgIdentifierCopyWith<$R, OrgIdentifier, OrgIdentifier>>?
      get identifiers => $value.identifiers != null
          ? ListCopyWith(
              $value.identifiers!,
              (v, t) => v.copyWith.chain<$R, OrgIdentifier>($identity, t),
              (v) => call(identifiers: v))
          : null;
  @override
  $R call(
          {Object? name = $none,
          Object? id = $none,
          Object? orgNumber = $none,
          Object? applicationNumber = $none,
          Object? tenantId = $none,
          Object? applicationStatus = $none,
          Object? externalRefNumber = $none,
          Object? orgAddress = $none,
          Object? contactDetails = $none,
          Object? identifiers = $none}) =>
      $then(OrganisationModel(
          name: or(name, $value.name),
          id: or(id, $value.id),
          orgNumber: or(orgNumber, $value.orgNumber),
          applicationNumber: or(applicationNumber, $value.applicationNumber),
          tenantId: or(tenantId, $value.tenantId),
          applicationStatus: or(applicationStatus, $value.applicationStatus),
          externalRefNumber: or(externalRefNumber, $value.externalRefNumber),
          orgAddress: or(orgAddress, $value.orgAddress),
          contactDetails: or(contactDetails, $value.contactDetails),
          identifiers: or(identifiers, $value.identifiers)));
}

class OrgAddressMapper extends MapperBase<OrgAddress> {
  static MapperContainer container = MapperContainer(
    mappers: {OrgAddressMapper()},
  );

  @override
  OrgAddressMapperElement createElement(MapperContainer container) {
    return OrgAddressMapperElement._(this, container);
  }

  @override
  String get id => 'OrgAddress';

  static final fromMap = container.fromMap<OrgAddress>;
  static final fromJson = container.fromJson<OrgAddress>;
}

class OrgAddressMapperElement extends MapperElementBase<OrgAddress> {
  OrgAddressMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrgAddress decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrgAddress fromMap(Map<String, dynamic> map) => OrgAddress(
      orgId: container.$getOpt(map, 'orgId'),
      id: container.$getOpt(map, 'id'),
      doorNo: container.$getOpt(map, 'doorNo'),
      plotNo: container.$getOpt(map, 'plotNo'),
      tenantId: container.$getOpt(map, 'tenantId'),
      landmark: container.$getOpt(map, 'landmark'),
      city: container.$getOpt(map, 'city'),
      state: container.$getOpt(map, 'state'),
      street: container.$getOpt(map, 'street'),
      pincode: container.$getOpt(map, 'pincode'),
      buildingName: container.$getOpt(map, 'buildingName'),
      boundaryCode: container.$getOpt(map, 'boundaryCode'),
      boundaryType: container.$getOpt(map, 'boundaryType'),
      country: container.$getOpt(map, 'country'),
      district: container.$getOpt(map, 'district'),
      region: container.$getOpt(map, 'region'));

  @override
  Function get encoder => encode;
  dynamic encode(OrgAddress v) => toMap(v);
  Map<String, dynamic> toMap(OrgAddress o) => {
        'orgId': container.$enc(o.orgId, 'orgId'),
        'id': container.$enc(o.id, 'id'),
        'doorNo': container.$enc(o.doorNo, 'doorNo'),
        'plotNo': container.$enc(o.plotNo, 'plotNo'),
        'tenantId': container.$enc(o.tenantId, 'tenantId'),
        'landmark': container.$enc(o.landmark, 'landmark'),
        'city': container.$enc(o.city, 'city'),
        'state': container.$enc(o.state, 'state'),
        'street': container.$enc(o.street, 'street'),
        'pincode': container.$enc(o.pincode, 'pincode'),
        'buildingName': container.$enc(o.buildingName, 'buildingName'),
        'boundaryCode': container.$enc(o.boundaryCode, 'boundaryCode'),
        'boundaryType': container.$enc(o.boundaryType, 'boundaryType'),
        'country': container.$enc(o.country, 'country'),
        'district': container.$enc(o.district, 'district'),
        'region': container.$enc(o.region, 'region')
      };

  @override
  String stringify(OrgAddress self) =>
      'OrgAddress(id: ${container.asString(self.id)}, orgId: ${container.asString(self.orgId)}, tenantId: ${container.asString(self.tenantId)}, doorNo: ${container.asString(self.doorNo)}, plotNo: ${container.asString(self.plotNo)}, landmark: ${container.asString(self.landmark)}, city: ${container.asString(self.city)}, pincode: ${container.asString(self.pincode)}, district: ${container.asString(self.district)}, region: ${container.asString(self.region)}, state: ${container.asString(self.state)}, country: ${container.asString(self.country)}, buildingName: ${container.asString(self.buildingName)}, street: ${container.asString(self.street)}, boundaryType: ${container.asString(self.boundaryType)}, boundaryCode: ${container.asString(self.boundaryCode)})';
  @override
  int hash(OrgAddress self) =>
      container.hash(self.id) ^
      container.hash(self.orgId) ^
      container.hash(self.tenantId) ^
      container.hash(self.doorNo) ^
      container.hash(self.plotNo) ^
      container.hash(self.landmark) ^
      container.hash(self.city) ^
      container.hash(self.pincode) ^
      container.hash(self.district) ^
      container.hash(self.region) ^
      container.hash(self.state) ^
      container.hash(self.country) ^
      container.hash(self.buildingName) ^
      container.hash(self.street) ^
      container.hash(self.boundaryType) ^
      container.hash(self.boundaryCode);
  @override
  bool equals(OrgAddress self, OrgAddress other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.orgId, other.orgId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.doorNo, other.doorNo) &&
      container.isEqual(self.plotNo, other.plotNo) &&
      container.isEqual(self.landmark, other.landmark) &&
      container.isEqual(self.city, other.city) &&
      container.isEqual(self.pincode, other.pincode) &&
      container.isEqual(self.district, other.district) &&
      container.isEqual(self.region, other.region) &&
      container.isEqual(self.state, other.state) &&
      container.isEqual(self.country, other.country) &&
      container.isEqual(self.buildingName, other.buildingName) &&
      container.isEqual(self.street, other.street) &&
      container.isEqual(self.boundaryType, other.boundaryType) &&
      container.isEqual(self.boundaryCode, other.boundaryCode);
}

mixin OrgAddressMappable {
  String toJson() => OrgAddressMapper.container.toJson(this as OrgAddress);
  Map<String, dynamic> toMap() =>
      OrgAddressMapper.container.toMap(this as OrgAddress);
  OrgAddressCopyWith<OrgAddress, OrgAddress, OrgAddress> get copyWith =>
      _OrgAddressCopyWithImpl(this as OrgAddress, $identity, $identity);
  @override
  String toString() => OrgAddressMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrgAddressMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrgAddressMapper.container.hash(this);
}

extension OrgAddressValueCopy<$R, $Out extends OrgAddress>
    on ObjectCopyWith<$R, OrgAddress, $Out> {
  OrgAddressCopyWith<$R, OrgAddress, $Out> get asOrgAddress =>
      base.as((v, t, t2) => _OrgAddressCopyWithImpl(v, t, t2));
}

typedef OrgAddressCopyWithBound = OrgAddress;

abstract class OrgAddressCopyWith<$R, $In extends OrgAddress,
    $Out extends OrgAddress> implements ObjectCopyWith<$R, $In, $Out> {
  OrgAddressCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends OrgAddress>(
      Then<OrgAddress, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? orgId,
      String? id,
      String? doorNo,
      String? plotNo,
      String? tenantId,
      String? landmark,
      String? city,
      String? state,
      String? street,
      String? pincode,
      String? buildingName,
      String? boundaryCode,
      String? boundaryType,
      String? country,
      String? district,
      String? region});
}

class _OrgAddressCopyWithImpl<$R, $Out extends OrgAddress>
    extends CopyWithBase<$R, OrgAddress, $Out>
    implements OrgAddressCopyWith<$R, OrgAddress, $Out> {
  _OrgAddressCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrgAddressCopyWith<$R2, OrgAddress, $Out2>
      chain<$R2, $Out2 extends OrgAddress>(
              Then<OrgAddress, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrgAddressCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? orgId = $none,
          Object? id = $none,
          Object? doorNo = $none,
          Object? plotNo = $none,
          Object? tenantId = $none,
          Object? landmark = $none,
          Object? city = $none,
          Object? state = $none,
          Object? street = $none,
          Object? pincode = $none,
          Object? buildingName = $none,
          Object? boundaryCode = $none,
          Object? boundaryType = $none,
          Object? country = $none,
          Object? district = $none,
          Object? region = $none}) =>
      $then(OrgAddress(
          orgId: or(orgId, $value.orgId),
          id: or(id, $value.id),
          doorNo: or(doorNo, $value.doorNo),
          plotNo: or(plotNo, $value.plotNo),
          tenantId: or(tenantId, $value.tenantId),
          landmark: or(landmark, $value.landmark),
          city: or(city, $value.city),
          state: or(state, $value.state),
          street: or(street, $value.street),
          pincode: or(pincode, $value.pincode),
          buildingName: or(buildingName, $value.buildingName),
          boundaryCode: or(boundaryCode, $value.boundaryCode),
          boundaryType: or(boundaryType, $value.boundaryType),
          country: or(country, $value.country),
          district: or(district, $value.district),
          region: or(region, $value.region)));
}

class OrgContactMapper extends MapperBase<OrgContact> {
  static MapperContainer container = MapperContainer(
    mappers: {OrgContactMapper()},
  );

  @override
  OrgContactMapperElement createElement(MapperContainer container) {
    return OrgContactMapperElement._(this, container);
  }

  @override
  String get id => 'OrgContact';

  static final fromMap = container.fromMap<OrgContact>;
  static final fromJson = container.fromJson<OrgContact>;
}

class OrgContactMapperElement extends MapperElementBase<OrgContact> {
  OrgContactMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrgContact decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrgContact fromMap(Map<String, dynamic> map) => OrgContact(
      orgId: container.$getOpt(map, 'orgId'),
      id: container.$getOpt(map, 'id'),
      tenantId: container.$getOpt(map, 'tenantId'),
      contactEmail: container.$getOpt(map, 'contactEmail'),
      contactMobileNumber: container.$getOpt(map, 'contactMobileNumber'),
      contactName: container.$getOpt(map, 'contactName'));

  @override
  Function get encoder => encode;
  dynamic encode(OrgContact v) => toMap(v);
  Map<String, dynamic> toMap(OrgContact o) => {
        'orgId': container.$enc(o.orgId, 'orgId'),
        'id': container.$enc(o.id, 'id'),
        'tenantId': container.$enc(o.tenantId, 'tenantId'),
        'contactEmail': container.$enc(o.contactEmail, 'contactEmail'),
        'contactMobileNumber':
            container.$enc(o.contactMobileNumber, 'contactMobileNumber'),
        'contactName': container.$enc(o.contactName, 'contactName')
      };

  @override
  String stringify(OrgContact self) =>
      'OrgContact(id: ${container.asString(self.id)}, orgId: ${container.asString(self.orgId)}, tenantId: ${container.asString(self.tenantId)}, contactName: ${container.asString(self.contactName)}, contactMobileNumber: ${container.asString(self.contactMobileNumber)}, contactEmail: ${container.asString(self.contactEmail)})';
  @override
  int hash(OrgContact self) =>
      container.hash(self.id) ^
      container.hash(self.orgId) ^
      container.hash(self.tenantId) ^
      container.hash(self.contactName) ^
      container.hash(self.contactMobileNumber) ^
      container.hash(self.contactEmail);
  @override
  bool equals(OrgContact self, OrgContact other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.orgId, other.orgId) &&
      container.isEqual(self.tenantId, other.tenantId) &&
      container.isEqual(self.contactName, other.contactName) &&
      container.isEqual(self.contactMobileNumber, other.contactMobileNumber) &&
      container.isEqual(self.contactEmail, other.contactEmail);
}

mixin OrgContactMappable {
  String toJson() => OrgContactMapper.container.toJson(this as OrgContact);
  Map<String, dynamic> toMap() =>
      OrgContactMapper.container.toMap(this as OrgContact);
  OrgContactCopyWith<OrgContact, OrgContact, OrgContact> get copyWith =>
      _OrgContactCopyWithImpl(this as OrgContact, $identity, $identity);
  @override
  String toString() => OrgContactMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrgContactMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrgContactMapper.container.hash(this);
}

extension OrgContactValueCopy<$R, $Out extends OrgContact>
    on ObjectCopyWith<$R, OrgContact, $Out> {
  OrgContactCopyWith<$R, OrgContact, $Out> get asOrgContact =>
      base.as((v, t, t2) => _OrgContactCopyWithImpl(v, t, t2));
}

typedef OrgContactCopyWithBound = OrgContact;

abstract class OrgContactCopyWith<$R, $In extends OrgContact,
    $Out extends OrgContact> implements ObjectCopyWith<$R, $In, $Out> {
  OrgContactCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends OrgContact>(
      Then<OrgContact, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? orgId,
      String? id,
      String? tenantId,
      String? contactEmail,
      String? contactMobileNumber,
      String? contactName});
}

class _OrgContactCopyWithImpl<$R, $Out extends OrgContact>
    extends CopyWithBase<$R, OrgContact, $Out>
    implements OrgContactCopyWith<$R, OrgContact, $Out> {
  _OrgContactCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrgContactCopyWith<$R2, OrgContact, $Out2>
      chain<$R2, $Out2 extends OrgContact>(
              Then<OrgContact, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrgContactCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? orgId = $none,
          Object? id = $none,
          Object? tenantId = $none,
          Object? contactEmail = $none,
          Object? contactMobileNumber = $none,
          Object? contactName = $none}) =>
      $then(OrgContact(
          orgId: or(orgId, $value.orgId),
          id: or(id, $value.id),
          tenantId: or(tenantId, $value.tenantId),
          contactEmail: or(contactEmail, $value.contactEmail),
          contactMobileNumber:
              or(contactMobileNumber, $value.contactMobileNumber),
          contactName: or(contactName, $value.contactName)));
}

class OrgIdentifierMapper extends MapperBase<OrgIdentifier> {
  static MapperContainer container = MapperContainer(
    mappers: {OrgIdentifierMapper()},
  );

  @override
  OrgIdentifierMapperElement createElement(MapperContainer container) {
    return OrgIdentifierMapperElement._(this, container);
  }

  @override
  String get id => 'OrgIdentifier';

  static final fromMap = container.fromMap<OrgIdentifier>;
  static final fromJson = container.fromJson<OrgIdentifier>;
}

class OrgIdentifierMapperElement extends MapperElementBase<OrgIdentifier> {
  OrgIdentifierMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrgIdentifier decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrgIdentifier fromMap(Map<String, dynamic> map) => OrgIdentifier(
      orgId: container.$getOpt(map, 'orgId'),
      id: container.$getOpt(map, 'id'),
      type: container.$getOpt(map, 'type'),
      value: container.$getOpt(map, 'value'));

  @override
  Function get encoder => encode;
  dynamic encode(OrgIdentifier v) => toMap(v);
  Map<String, dynamic> toMap(OrgIdentifier o) => {
        'orgId': container.$enc(o.orgId, 'orgId'),
        'id': container.$enc(o.id, 'id'),
        'type': container.$enc(o.type, 'type'),
        'value': container.$enc(o.value, 'value')
      };

  @override
  String stringify(OrgIdentifier self) =>
      'OrgIdentifier(id: ${container.asString(self.id)}, orgId: ${container.asString(self.orgId)}, type: ${container.asString(self.type)}, value: ${container.asString(self.value)})';
  @override
  int hash(OrgIdentifier self) =>
      container.hash(self.id) ^
      container.hash(self.orgId) ^
      container.hash(self.type) ^
      container.hash(self.value);
  @override
  bool equals(OrgIdentifier self, OrgIdentifier other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.orgId, other.orgId) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.value, other.value);
}

mixin OrgIdentifierMappable {
  String toJson() =>
      OrgIdentifierMapper.container.toJson(this as OrgIdentifier);
  Map<String, dynamic> toMap() =>
      OrgIdentifierMapper.container.toMap(this as OrgIdentifier);
  OrgIdentifierCopyWith<OrgIdentifier, OrgIdentifier, OrgIdentifier>
      get copyWith => _OrgIdentifierCopyWithImpl(
          this as OrgIdentifier, $identity, $identity);
  @override
  String toString() => OrgIdentifierMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrgIdentifierMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrgIdentifierMapper.container.hash(this);
}

extension OrgIdentifierValueCopy<$R, $Out extends OrgIdentifier>
    on ObjectCopyWith<$R, OrgIdentifier, $Out> {
  OrgIdentifierCopyWith<$R, OrgIdentifier, $Out> get asOrgIdentifier =>
      base.as((v, t, t2) => _OrgIdentifierCopyWithImpl(v, t, t2));
}

typedef OrgIdentifierCopyWithBound = OrgIdentifier;

abstract class OrgIdentifierCopyWith<$R, $In extends OrgIdentifier,
    $Out extends OrgIdentifier> implements ObjectCopyWith<$R, $In, $Out> {
  OrgIdentifierCopyWith<$R2, $In, $Out2>
      chain<$R2, $Out2 extends OrgIdentifier>(
          Then<OrgIdentifier, $Out2> t, Then<$Out2, $R2> t2);
  $R call({String? orgId, String? id, String? type, String? value});
}

class _OrgIdentifierCopyWithImpl<$R, $Out extends OrgIdentifier>
    extends CopyWithBase<$R, OrgIdentifier, $Out>
    implements OrgIdentifierCopyWith<$R, OrgIdentifier, $Out> {
  _OrgIdentifierCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrgIdentifierCopyWith<$R2, OrgIdentifier, $Out2>
      chain<$R2, $Out2 extends OrgIdentifier>(
              Then<OrgIdentifier, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrgIdentifierCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? orgId = $none,
          Object? id = $none,
          Object? type = $none,
          Object? value = $none}) =>
      $then(OrgIdentifier(
          orgId: or(orgId, $value.orgId),
          id: or(id, $value.id),
          type: or(type, $value.type),
          value: or(value, $value.value)));
}

class OrgFunctionsMapper extends MapperBase<OrgFunctions> {
  static MapperContainer container = MapperContainer(
    mappers: {OrgFunctionsMapper()},
  );

  @override
  OrgFunctionsMapperElement createElement(MapperContainer container) {
    return OrgFunctionsMapperElement._(this, container);
  }

  @override
  String get id => 'OrgFunctions';

  static final fromMap = container.fromMap<OrgFunctions>;
  static final fromJson = container.fromJson<OrgFunctions>;
}

class OrgFunctionsMapperElement extends MapperElementBase<OrgFunctions> {
  OrgFunctionsMapperElement._(super.mapper, super.container);

  @override
  Function get decoder => decode;
  OrgFunctions decode(dynamic v) =>
      checkedType(v, (Map<String, dynamic> map) => fromMap(map));
  OrgFunctions fromMap(Map<String, dynamic> map) => OrgFunctions(
      orgId: container.$getOpt(map, 'orgId'),
      id: container.$getOpt(map, 'id'),
      type: container.$getOpt(map, 'type'),
      applicationNumber: container.$getOpt(map, 'applicationNumber'),
      category: container.$getOpt(map, 'category'),
      isActive: container.$getOpt(map, 'isActive'),
      organisationType: container.$getOpt(map, 'organisationType'),
      orgClass: container.$getOpt(map, 'orgClass'));

  @override
  Function get encoder => encode;
  dynamic encode(OrgFunctions v) => toMap(v);
  Map<String, dynamic> toMap(OrgFunctions o) => {
        'orgId': container.$enc(o.orgId, 'orgId'),
        'id': container.$enc(o.id, 'id'),
        'type': container.$enc(o.type, 'type'),
        'applicationNumber':
            container.$enc(o.applicationNumber, 'applicationNumber'),
        'category': container.$enc(o.category, 'category'),
        'isActive': container.$enc(o.isActive, 'isActive'),
        'organisationType':
            container.$enc(o.organisationType, 'organisationType'),
        'orgClass': container.$enc(o.orgClass, 'orgClass')
      };

  @override
  String stringify(OrgFunctions self) =>
      'OrgFunctions(id: ${container.asString(self.id)}, orgId: ${container.asString(self.orgId)}, applicationNumber: ${container.asString(self.applicationNumber)}, type: ${container.asString(self.type)}, organisationType: ${container.asString(self.organisationType)}, category: ${container.asString(self.category)}, orgClass: ${container.asString(self.orgClass)}, isActive: ${container.asString(self.isActive)})';
  @override
  int hash(OrgFunctions self) =>
      container.hash(self.id) ^
      container.hash(self.orgId) ^
      container.hash(self.applicationNumber) ^
      container.hash(self.type) ^
      container.hash(self.organisationType) ^
      container.hash(self.category) ^
      container.hash(self.orgClass) ^
      container.hash(self.isActive);
  @override
  bool equals(OrgFunctions self, OrgFunctions other) =>
      container.isEqual(self.id, other.id) &&
      container.isEqual(self.orgId, other.orgId) &&
      container.isEqual(self.applicationNumber, other.applicationNumber) &&
      container.isEqual(self.type, other.type) &&
      container.isEqual(self.organisationType, other.organisationType) &&
      container.isEqual(self.category, other.category) &&
      container.isEqual(self.orgClass, other.orgClass) &&
      container.isEqual(self.isActive, other.isActive);
}

mixin OrgFunctionsMappable {
  String toJson() => OrgFunctionsMapper.container.toJson(this as OrgFunctions);
  Map<String, dynamic> toMap() =>
      OrgFunctionsMapper.container.toMap(this as OrgFunctions);
  OrgFunctionsCopyWith<OrgFunctions, OrgFunctions, OrgFunctions> get copyWith =>
      _OrgFunctionsCopyWithImpl(this as OrgFunctions, $identity, $identity);
  @override
  String toString() => OrgFunctionsMapper.container.asString(this);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (runtimeType == other.runtimeType &&
          OrgFunctionsMapper.container.isEqual(this, other));
  @override
  int get hashCode => OrgFunctionsMapper.container.hash(this);
}

extension OrgFunctionsValueCopy<$R, $Out extends OrgFunctions>
    on ObjectCopyWith<$R, OrgFunctions, $Out> {
  OrgFunctionsCopyWith<$R, OrgFunctions, $Out> get asOrgFunctions =>
      base.as((v, t, t2) => _OrgFunctionsCopyWithImpl(v, t, t2));
}

typedef OrgFunctionsCopyWithBound = OrgFunctions;

abstract class OrgFunctionsCopyWith<$R, $In extends OrgFunctions,
    $Out extends OrgFunctions> implements ObjectCopyWith<$R, $In, $Out> {
  OrgFunctionsCopyWith<$R2, $In, $Out2> chain<$R2, $Out2 extends OrgFunctions>(
      Then<OrgFunctions, $Out2> t, Then<$Out2, $R2> t2);
  $R call(
      {String? orgId,
      String? id,
      String? type,
      String? applicationNumber,
      String? category,
      bool? isActive,
      String? organisationType,
      String? orgClass});
}

class _OrgFunctionsCopyWithImpl<$R, $Out extends OrgFunctions>
    extends CopyWithBase<$R, OrgFunctions, $Out>
    implements OrgFunctionsCopyWith<$R, OrgFunctions, $Out> {
  _OrgFunctionsCopyWithImpl(super.value, super.then, super.then2);
  @override
  OrgFunctionsCopyWith<$R2, OrgFunctions, $Out2>
      chain<$R2, $Out2 extends OrgFunctions>(
              Then<OrgFunctions, $Out2> t, Then<$Out2, $R2> t2) =>
          _OrgFunctionsCopyWithImpl($value, t, t2);

  @override
  $R call(
          {Object? orgId = $none,
          Object? id = $none,
          Object? type = $none,
          Object? applicationNumber = $none,
          Object? category = $none,
          Object? isActive = $none,
          Object? organisationType = $none,
          Object? orgClass = $none}) =>
      $then(OrgFunctions(
          orgId: or(orgId, $value.orgId),
          id: or(id, $value.id),
          type: or(type, $value.type),
          applicationNumber: or(applicationNumber, $value.applicationNumber),
          category: or(category, $value.category),
          isActive: or(isActive, $value.isActive),
          organisationType: or(organisationType, $value.organisationType),
          orgClass: or(orgClass, $value.orgClass)));
}
