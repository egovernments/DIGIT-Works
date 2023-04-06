import 'package:dart_mappable/dart_mappable.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'organisation_model.mapper.dart';

@MappableClass()
class OrganisationListModel with OrganisationListModelMappable {
  List<OrganisationModel>? organisations;
  OrganisationListModel({this.organisations});
}

@MappableClass()
class OrganisationModel with OrganisationModelMappable {
  String? name;
  String? applicationNumber;
  String? tenantId;
  String? id;
  String? orgNumber;
  String? applicationStatus;
  String? externalRefNumber;
  List<OrgAddress>? orgAddress;
  List<OrgContact>? contactDetails;
  List<OrgIdentifier>? identifiers;
  OrganisationModel(
      {this.name,
      this.id,
      this.orgNumber,
      this.applicationNumber,
      this.tenantId,
      this.applicationStatus,
      this.externalRefNumber,
      this.orgAddress,
      this.contactDetails,
      this.identifiers});
}

@MappableClass()
class OrgAddress with OrgAddressMappable {
  String? id;
  String? orgId;
  String? tenantId;
  String? doorNo;
  String? plotNo;
  String? landmark;
  String? city;
  String? pincode;
  String? district;
  String? region;
  String? state;
  String? country;
  String? buildingName;
  String? street;
  String? boundaryType;
  String? boundaryCode;

  OrgAddress(
      {this.orgId,
      this.id,
      this.doorNo,
      this.plotNo,
      this.tenantId,
      this.landmark,
      this.city,
      this.state,
      this.street,
      this.pincode,
      this.buildingName,
      this.boundaryCode,
      this.boundaryType,
      this.country,
      this.district,
      this.region});
}

@MappableClass()
class OrgContact with OrgContactMappable {
  String? id;
  String? orgId;
  String? tenantId;
  String? contactName;
  String? contactMobileNumber;
  String? contactEmail;

  OrgContact({
    this.orgId,
    this.id,
    this.tenantId,
    this.contactEmail,
    this.contactMobileNumber,
    this.contactName,
  });
}

@MappableClass()
class OrgIdentifier with OrgIdentifierMappable {
  String? id;
  String? orgId;
  String? type;
  String? value;

  OrgIdentifier({
    this.orgId,
    this.id,
    this.type,
    this.value,
  });
}

@MappableClass()
class OrgFunctions with OrgFunctionsMappable {
  String? id;
  String? orgId;
  String? applicationNumber;
  String? type;
  String? organisationType;
  String? category;
  @JsonKey(name: 'class')
  String? orgClass;
  bool? isActive;
  OrgFunctions(
      {this.orgId,
      this.id,
      this.type,
      this.applicationNumber,
      this.category,
      this.isActive,
      this.organisationType,
      this.orgClass});
}
