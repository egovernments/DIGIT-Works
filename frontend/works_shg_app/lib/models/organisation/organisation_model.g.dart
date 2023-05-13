// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'organisation_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_OrganisationListModel _$$_OrganisationListModelFromJson(
        Map<String, dynamic> json) =>
    _$_OrganisationListModel(
      organisations: (json['organisations'] as List<dynamic>?)
          ?.map((e) => OrganisationModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_OrganisationListModelToJson(
        _$_OrganisationListModel instance) =>
    <String, dynamic>{
      'organisations': instance.organisations,
    };

_$_OrganisationModel _$$_OrganisationModelFromJson(Map<String, dynamic> json) =>
    _$_OrganisationModel(
      name: json['name'] as String?,
      applicationNumber: json['applicationNumber'] as String?,
      tenantId: json['tenantId'] as String,
      id: json['id'] as String?,
      orgNumber: json['orgNumber'] as String?,
      applicationStatus: json['applicationStatus'] as String?,
      externalRefNumber: json['externalRefNumber'] as String?,
      orgAddress: (json['orgAddress'] as List<dynamic>?)
          ?.map((e) => OrgAddress.fromJson(e as Map<String, dynamic>))
          .toList(),
      contactDetails: (json['contactDetails'] as List<dynamic>?)
          ?.map((e) => OrgContact.fromJson(e as Map<String, dynamic>))
          .toList(),
      identifiers: (json['identifiers'] as List<dynamic>?)
          ?.map((e) => OrgIdentifier.fromJson(e as Map<String, dynamic>))
          .toList(),
      functions: (json['functions'] as List<dynamic>?)
          ?.map((e) => OrgFunctions.fromJson(e as Map<String, dynamic>))
          .toList(),
      additionalDetails: json['additionalDetails'] == null
          ? null
          : OrgAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      dateOfIncorporation: json['dateOfIncorporation'] as int?,
    );

Map<String, dynamic> _$$_OrganisationModelToJson(
        _$_OrganisationModel instance) =>
    <String, dynamic>{
      'name': instance.name,
      'applicationNumber': instance.applicationNumber,
      'tenantId': instance.tenantId,
      'id': instance.id,
      'orgNumber': instance.orgNumber,
      'applicationStatus': instance.applicationStatus,
      'externalRefNumber': instance.externalRefNumber,
      'orgAddress': instance.orgAddress,
      'contactDetails': instance.contactDetails,
      'identifiers': instance.identifiers,
      'functions': instance.functions,
      'additionalDetails': instance.additionalDetails,
      'dateOfIncorporation': instance.dateOfIncorporation,
    };

_$_OrgAdditionalDetails _$$_OrgAdditionalDetailsFromJson(
        Map<String, dynamic> json) =>
    _$_OrgAdditionalDetails(
      registeredByDept: json['registeredByDept'] as String?,
      deptRegistrationNum: json['deptRegistrationNum'] as String?,
      locality: json['locality'] as String?,
    );

Map<String, dynamic> _$$_OrgAdditionalDetailsToJson(
        _$_OrgAdditionalDetails instance) =>
    <String, dynamic>{
      'registeredByDept': instance.registeredByDept,
      'deptRegistrationNum': instance.deptRegistrationNum,
      'locality': instance.locality,
    };

_$_OrgAddress _$$_OrgAddressFromJson(Map<String, dynamic> json) =>
    _$_OrgAddress(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      tenantId: json['tenantId'] as String,
      doorNo: json['doorNo'] as String?,
      plotNo: json['plotNo'] as String?,
      landmark: json['landmark'] as String?,
      city: json['city'] as String?,
      pincode: json['pincode'] as String?,
      district: json['district'] as String?,
      region: json['region'] as String?,
      state: json['state'] as String?,
      country: json['country'] as String?,
      buildingName: json['buildingName'] as String?,
      street: json['street'] as String?,
      boundaryType: json['boundaryType'] as String?,
      boundaryCode: json['boundaryCode'] as String?,
    );

Map<String, dynamic> _$$_OrgAddressToJson(_$_OrgAddress instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'tenantId': instance.tenantId,
      'doorNo': instance.doorNo,
      'plotNo': instance.plotNo,
      'landmark': instance.landmark,
      'city': instance.city,
      'pincode': instance.pincode,
      'district': instance.district,
      'region': instance.region,
      'state': instance.state,
      'country': instance.country,
      'buildingName': instance.buildingName,
      'street': instance.street,
      'boundaryType': instance.boundaryType,
      'boundaryCode': instance.boundaryCode,
    };

_$_OrgContact _$$_OrgContactFromJson(Map<String, dynamic> json) =>
    _$_OrgContact(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      tenantId: json['tenantId'] as String,
      contactName: json['contactName'] as String?,
      contactMobileNumber: json['contactMobileNumber'] as String?,
      contactEmail: json['contactEmail'] as String?,
    );

Map<String, dynamic> _$$_OrgContactToJson(_$_OrgContact instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'tenantId': instance.tenantId,
      'contactName': instance.contactName,
      'contactMobileNumber': instance.contactMobileNumber,
      'contactEmail': instance.contactEmail,
    };

_$_OrgIdentifier _$$_OrgIdentifierFromJson(Map<String, dynamic> json) =>
    _$_OrgIdentifier(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      type: json['type'] as String?,
      value: json['value'] as String?,
    );

Map<String, dynamic> _$$_OrgIdentifierToJson(_$_OrgIdentifier instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'type': instance.type,
      'value': instance.value,
    };

_$_OrgFunctions _$$_OrgFunctionsFromJson(Map<String, dynamic> json) =>
    _$_OrgFunctions(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      applicationNumber: json['applicationNumber'] as String?,
      type: json['type'] as String?,
      organisationType: json['organisationType'] as String?,
      category: json['category'] as String?,
      orgClass: json['class'] as String?,
      validFrom: json['validFrom'] as int?,
      validTo: json['validTo'] as int?,
      isActive: json['isActive'] as bool?,
    );

Map<String, dynamic> _$$_OrgFunctionsToJson(_$_OrgFunctions instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'applicationNumber': instance.applicationNumber,
      'type': instance.type,
      'organisationType': instance.organisationType,
      'category': instance.category,
      'class': instance.orgClass,
      'validFrom': instance.validFrom,
      'validTo': instance.validTo,
      'isActive': instance.isActive,
    };
