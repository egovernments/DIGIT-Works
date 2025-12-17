// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'organisation_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$OrganisationListModelImpl _$$OrganisationListModelImplFromJson(
        Map<String, dynamic> json) =>
    _$OrganisationListModelImpl(
      organisations: (json['organisations'] as List<dynamic>?)
          ?.map((e) => OrganisationModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$OrganisationListModelImplToJson(
        _$OrganisationListModelImpl instance) =>
    <String, dynamic>{
      'organisations': instance.organisations,
    };

_$OrganisationModelImpl _$$OrganisationModelImplFromJson(
        Map<String, dynamic> json) =>
    _$OrganisationModelImpl(
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
      dateOfIncorporation: (json['dateOfIncorporation'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$OrganisationModelImplToJson(
        _$OrganisationModelImpl instance) =>
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

_$OrgAdditionalDetailsImpl _$$OrgAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$OrgAdditionalDetailsImpl(
      registeredByDept: json['registeredByDept'] as String?,
      deptRegistrationNum: json['deptRegistrationNum'] as String?,
      isLocalityMasked: json['isLocalityMasked'] as bool?,
      locality: json['locality'] as String?,
    );

Map<String, dynamic> _$$OrgAdditionalDetailsImplToJson(
        _$OrgAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'registeredByDept': instance.registeredByDept,
      'deptRegistrationNum': instance.deptRegistrationNum,
      'isLocalityMasked': instance.isLocalityMasked,
      'locality': instance.locality,
    };

_$OrgAddressImpl _$$OrgAddressImplFromJson(Map<String, dynamic> json) =>
    _$OrgAddressImpl(
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

Map<String, dynamic> _$$OrgAddressImplToJson(_$OrgAddressImpl instance) =>
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

_$OrgContactImpl _$$OrgContactImplFromJson(Map<String, dynamic> json) =>
    _$OrgContactImpl(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      tenantId: json['tenantId'] as String,
      contactName: json['contactName'] as String?,
      contactMobileNumber: json['contactMobileNumber'] as String?,
      contactEmail: json['contactEmail'] as String?,
    );

Map<String, dynamic> _$$OrgContactImplToJson(_$OrgContactImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'tenantId': instance.tenantId,
      'contactName': instance.contactName,
      'contactMobileNumber': instance.contactMobileNumber,
      'contactEmail': instance.contactEmail,
    };

_$OrgIdentifierImpl _$$OrgIdentifierImplFromJson(Map<String, dynamic> json) =>
    _$OrgIdentifierImpl(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      type: json['type'] as String?,
      value: json['value'] as String?,
    );

Map<String, dynamic> _$$OrgIdentifierImplToJson(_$OrgIdentifierImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orgId': instance.orgId,
      'type': instance.type,
      'value': instance.value,
    };

_$OrgFunctionsImpl _$$OrgFunctionsImplFromJson(Map<String, dynamic> json) =>
    _$OrgFunctionsImpl(
      id: json['id'] as String?,
      orgId: json['orgId'] as String?,
      applicationNumber: json['applicationNumber'] as String?,
      type: json['type'] as String?,
      organisationType: json['organisationType'] as String?,
      category: json['category'] as String?,
      orgClass: json['class'] as String?,
      validFrom: (json['validFrom'] as num?)?.toInt(),
      validTo: (json['validTo'] as num?)?.toInt(),
      isActive: json['isActive'] as bool?,
    );

Map<String, dynamic> _$$OrgFunctionsImplToJson(_$OrgFunctionsImpl instance) =>
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
