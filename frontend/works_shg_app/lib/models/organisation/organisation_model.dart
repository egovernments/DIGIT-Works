import 'package:freezed_annotation/freezed_annotation.dart';

part 'organisation_model.freezed.dart';
part 'organisation_model.g.dart';

@freezed
class OrganisationListModel with _$OrganisationListModel {
  const factory OrganisationListModel(
      {List<OrganisationModel>? organisations}) = _OrganisationListModel;

  factory OrganisationListModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrganisationListModelFromJson(json);
}

@freezed
class OrganisationModel with _$OrganisationModel {
  const factory OrganisationModel({
    String? name,
    String? applicationNumber,
    required String tenantId,
    String? id,
    String? orgNumber,
    String? applicationStatus,
    String? externalRefNumber,
    List<OrgAddress>? orgAddress,
    List<OrgContact>? contactDetails,
    List<OrgIdentifier>? identifiers,
    List<OrgFunctions>? functions,
    OrgAdditionalDetails? additionalDetails,
    int? dateOfIncorporation,
  }) = _OrganisationModel;

  factory OrganisationModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrganisationModelFromJson(json);
}

@freezed
class OrgAdditionalDetails with _$OrgAdditionalDetails {
  const factory OrgAdditionalDetails({
   
    String? registeredByDept,
    String? deptRegistrationNum,
    bool? isLocalityMasked,
    String? locality,
  }) = _OrgAdditionalDetails;

  factory OrgAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrgAdditionalDetailsFromJson(json);
}

@freezed
class OrgAddress with _$OrgAddress {
  const factory OrgAddress({
    String? id,
    String? orgId,
    required String tenantId,
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
    String? boundaryCode,
  }) = _OrgAddress;

  factory OrgAddress.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrgAddressFromJson(json);
}

@freezed
class OrgContact with _$OrgContact {
  const factory OrgContact(
      {String? id,
      String? orgId,
      required String tenantId,
      String? contactName,
      String? contactMobileNumber,
      String? contactEmail}) = _OrgContact;

  factory OrgContact.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrgContactFromJson(json);
}

@freezed
class OrgIdentifier with _$OrgIdentifier {
  const factory OrgIdentifier(
      {String? id,
      String? orgId,
      String? type,
      String? value}) = _OrgIdentifier;

  factory OrgIdentifier.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrgIdentifierFromJson(json);
}

@freezed
class OrgFunctions with _$OrgFunctions {
  const factory OrgFunctions({
    String? id,
    String? orgId,
    String? applicationNumber,
    String? type,
    String? organisationType,
    String? category,
    @JsonKey(name: 'class') String? orgClass,
    int? validFrom,
    int? validTo,
    bool? isActive,
  }) = _OrgFunctions;

  factory OrgFunctions.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$OrgFunctionsFromJson(json);
}
