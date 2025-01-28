import 'package:dart_mappable/dart_mappable.dart';

part 'individual_list_model.mapper.dart';

@MappableClass()
class IndividualListModel with IndividualListModelMappable {
  List<IndividualModel>? Individual;
  IndividualListModel({this.Individual});
}

@MappableClass()
class SingleIndividualModel with SingleIndividualModelMappable {
  IndividualModel? Individual;
  SingleIndividualModel({this.Individual});
}

@MappableClass()
class WMSIndividualListModel with WMSIndividualListModelMappable {
  List<SingleWMSIndividualModel>? items;
  WMSIndividualListModel({this.items});
}

@MappableClass()
class SingleWMSIndividualModel with SingleWMSIndividualModelMappable {
  IndividualModel? businessObject;
  SingleWMSIndividualModel({this.businessObject});
}

@MappableClass()
class IndividualModel with IndividualModelMappable {
  String? id;
  String? individualId;
  String tenantId;
  String? clientReferenceId;
  String? userId;
  IndividualName? name;
  String? dateOfBirth;
  String? gender;
  String? bloodGroup;
  String? mobileNumber;
  String? altContactNumber;
  String? email;
  List<IndividualAddress>? address;
  String? fatherName;
  String? husbandName;
  String? relationship;
  List<IndividualIdentifiers>? identifiers;
  List<IndividualSkills>? skills;
  String? photo;
  IndividualAdditionalFields? additionalFields;
  bool? isDeleted;
  int? rowVersion;
  CommonAuditDetails? auditDetails;

  IndividualModel(
      {this.id,
      this.name,
      required this.tenantId,
      this.auditDetails,
      this.skills,
      this.mobileNumber,
      this.individualId,
      this.gender,
      this.userId,
      this.additionalFields,
      this.address,
      this.altContactNumber,
      this.bloodGroup,
      this.clientReferenceId,
      this.dateOfBirth,
      this.email,
      this.fatherName,
      this.husbandName,
      this.identifiers,
      this.isDeleted,
      this.photo,
      this.relationship,
      this.rowVersion});
}

@MappableClass()
class IndividualAddress with IndividualAddressMappable {
  String? id;
  String? clientReferenceId;
  String? individualId;
  String? tenantId;
  String? doorNo;
  double? latitude;
  double? longitude;
  double? locationAccuracy;
  String? type;
  String? addressLine1;
  String? addressLine2;
  String? landmark;
  String? city;
  String? pincode;
  String? buildingName;
  String? street;
  AddressLocality? locality;
  AddressWard? ward;
  bool? isDeleted;
  CommonAuditDetails? auditDetails;
  IndividualAddress(
      {this.id,
      this.tenantId,
      this.auditDetails,
      this.isDeleted,
      this.clientReferenceId,
      this.individualId,
      this.ward,
      this.type,
      this.addressLine1,
      this.addressLine2,
      this.buildingName,
      this.city,
      this.doorNo,
      this.landmark,
      this.latitude,
      this.locality,
      this.locationAccuracy,
      this.longitude,
      this.pincode,
      this.street});
}

@MappableClass()
class IndividualName with IndividualNameMappable {
  String? givenName;
  String? familyName;
  String? otherNames;

  IndividualName({this.familyName, this.givenName, this.otherNames});
}

@MappableClass()
class IndividualIdentifiers with IndividualIdentifiersMappable {
  String? id;
  String? clientReferenceId;
  String? individualId;
  String? identifierType;
  String? identifierId;
  bool? isDeleted;
  CommonAuditDetails? auditDetails;

  IndividualIdentifiers(
      {this.individualId,
      this.clientReferenceId,
      this.isDeleted,
      this.id,
      this.auditDetails,
      this.identifierId,
      this.identifierType});
}

@MappableClass()
class IndividualSkills with IndividualSkillsMappable {
  String? id;
  String? clientReferenceId;
  String? individualId;
  String? type;
  String? level;
  String? experience;
  bool? isDeleted;
  CommonAuditDetails? auditDetails;

  IndividualSkills(
      {this.individualId,
      this.clientReferenceId,
      this.isDeleted,
      this.id,
      this.auditDetails,
      this.type,
      this.experience,
      this.level});
}

@MappableClass()
class CommonAuditDetails with CommonAuditDetailsMappable {
  String? createdBy;
  String? lastModifiedBy;
  int? createdTime;
  int? lastModifiedTime;

  CommonAuditDetails(
      {this.createdTime,
      this.lastModifiedTime,
      this.createdBy,
      this.lastModifiedBy});
}

@MappableClass()
class IndividualAdditionalFields with IndividualAdditionalFieldsMappable {
  String? schema;
  String? version;
  List<AdditionalIndividualFields>? fields;
  String? documentType;
  String? fileStore;
  String? documentUid;
  String? status;

  IndividualAdditionalFields(
      {this.status,
      this.version,
      this.documentType,
      this.fields,
      this.documentUid,
      this.fileStore,
      this.schema});
}

@MappableClass()
class AddressLocality with AddressLocalityMappable {
  String? code;
  String? name;
  String? label;
  double? latitude;
  double? longitude;
  String? materializedPath;

  AddressLocality(
      {this.code,
      this.name,
      this.label,
      this.latitude,
      this.longitude,
      this.materializedPath});
}

@MappableClass()
class AddressWard with AddressWardMappable {
  String? code;
  String? name;
  String? label;
  double? latitude;
  double? longitude;
  String? materializedPath;

  AddressWard(
      {this.code,
      this.name,
      this.label,
      this.latitude,
      this.longitude,
      this.materializedPath});
}

@MappableClass()
class AdditionalIndividualFields with AdditionalIndividualFieldsMappable {
  String? key;
  String? value;

  AdditionalIndividualFields({
    this.key,
    this.value,
  });
}
