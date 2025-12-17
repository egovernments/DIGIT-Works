// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'wage_seeker_mdms.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$WageSeekerMDMSImpl _$$WageSeekerMDMSImplFromJson(Map<String, dynamic> json) =>
    _$WageSeekerMDMSImpl(
      commonMDMS: json['common-masters'] == null
          ? null
          : WageSeekerCommonMDMS.fromJson(
              json['common-masters'] as Map<String, dynamic>),
      worksMDMS: json['works'] == null
          ? null
          : WageSeekerWorksMDMS.fromJson(json['works'] as Map<String, dynamic>),
      tenantMDMS: json['tenant'] == null
          ? null
          : TenantMDMS.fromJson(json['tenant'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$WageSeekerMDMSImplToJson(
        _$WageSeekerMDMSImpl instance) =>
    <String, dynamic>{
      'common-masters': instance.commonMDMS,
      'works': instance.worksMDMS,
      'tenant': instance.tenantMDMS,
    };

_$WageSeekerWorksMDMSImpl _$$WageSeekerWorksMDMSImplFromJson(
        Map<String, dynamic> json) =>
    _$WageSeekerWorksMDMSImpl(
      bankAccType: (json['BankAccType'] as List<dynamic>?)
          ?.map((e) => BankAccType.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$WageSeekerWorksMDMSImplToJson(
        _$WageSeekerWorksMDMSImpl instance) =>
    <String, dynamic>{
      'BankAccType': instance.bankAccType,
    };

_$TenantMDMSImpl _$$TenantMDMSImplFromJson(Map<String, dynamic> json) =>
    _$TenantMDMSImpl(
      cityModule: (json['citymodule'] as List<dynamic>?)
          ?.map((e) => CityModule.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$TenantMDMSImplToJson(_$TenantMDMSImpl instance) =>
    <String, dynamic>{
      'citymodule': instance.cityModule,
    };

_$CityModuleImpl _$$CityModuleImplFromJson(Map<String, dynamic> json) =>
    _$CityModuleImpl(
      active: json['active'] as bool?,
      code: json['code'] as String?,
      module: json['module'] as String?,
      order: (json['order'] as num?)?.toInt(),
      tenants: (json['tenants'] as List<dynamic>?)
          ?.map((e) => TenantList.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$CityModuleImplToJson(_$CityModuleImpl instance) =>
    <String, dynamic>{
      'active': instance.active,
      'code': instance.code,
      'module': instance.module,
      'order': instance.order,
      'tenants': instance.tenants,
    };

_$TenantListImpl _$$TenantListImplFromJson(Map<String, dynamic> json) =>
    _$TenantListImpl(
      code: json['code'] as String?,
    );

Map<String, dynamic> _$$TenantListImplToJson(_$TenantListImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
    };

_$WageSeekerCommonMDMSImpl _$$WageSeekerCommonMDMSImplFromJson(
        Map<String, dynamic> json) =>
    _$WageSeekerCommonMDMSImpl(
      genderType: (json['GenderType'] as List<dynamic>?)
          ?.map((e) => GenderType.fromJson(e as Map<String, dynamic>))
          .toList(),
      wageSeekerSkills: (json['WageSeekerSkills'] as List<dynamic>?)
          ?.map((e) => WageSeekerSkills.fromJson(e as Map<String, dynamic>))
          .toList(),
      relationship: (json['Relationship'] as List<dynamic>?)
          ?.map((e) => Relationship.fromJson(e as Map<String, dynamic>))
          .toList(),
      socialCategory: (json['SocialCategory'] as List<dynamic>?)
          ?.map((e) => SocialCategory.fromJson(e as Map<String, dynamic>))
          .toList(),
      documentType: (json['DocumentType'] as List<dynamic>?)
          ?.map((e) => DocumentType.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$WageSeekerCommonMDMSImplToJson(
        _$WageSeekerCommonMDMSImpl instance) =>
    <String, dynamic>{
      'GenderType': instance.genderType,
      'WageSeekerSkills': instance.wageSeekerSkills,
      'Relationship': instance.relationship,
      'SocialCategory': instance.socialCategory,
      'DocumentType': instance.documentType,
    };

_$GenderTypeImpl _$$GenderTypeImplFromJson(Map<String, dynamic> json) =>
    _$GenderTypeImpl(
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$GenderTypeImplToJson(_$GenderTypeImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'active': instance.active,
    };

_$RelationshipImpl _$$RelationshipImplFromJson(Map<String, dynamic> json) =>
    _$RelationshipImpl(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$RelationshipImplToJson(_$RelationshipImpl instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };

_$SocialCategoryImpl _$$SocialCategoryImplFromJson(Map<String, dynamic> json) =>
    _$SocialCategoryImpl(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$SocialCategoryImplToJson(
        _$SocialCategoryImpl instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };

_$BankAccTypeImpl _$$BankAccTypeImplFromJson(Map<String, dynamic> json) =>
    _$BankAccTypeImpl(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$BankAccTypeImplToJson(_$BankAccTypeImpl instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };

_$DocumentTypeImpl _$$DocumentTypeImplFromJson(Map<String, dynamic> json) =>
    _$DocumentTypeImpl(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$DocumentTypeImplToJson(_$DocumentTypeImpl instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };
