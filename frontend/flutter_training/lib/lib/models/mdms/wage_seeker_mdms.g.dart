// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'wage_seeker_mdms.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_WageSeekerMDMS _$$_WageSeekerMDMSFromJson(Map<String, dynamic> json) =>
    _$_WageSeekerMDMS(
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

Map<String, dynamic> _$$_WageSeekerMDMSToJson(_$_WageSeekerMDMS instance) =>
    <String, dynamic>{
      'common-masters': instance.commonMDMS,
      'works': instance.worksMDMS,
      'tenant': instance.tenantMDMS,
    };

_$_WageSeekerWorksMDMS _$$_WageSeekerWorksMDMSFromJson(
        Map<String, dynamic> json) =>
    _$_WageSeekerWorksMDMS(
      bankAccType: (json['BankAccType'] as List<dynamic>?)
          ?.map((e) => BankAccType.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_WageSeekerWorksMDMSToJson(
        _$_WageSeekerWorksMDMS instance) =>
    <String, dynamic>{
      'BankAccType': instance.bankAccType,
    };

_$_TenantMDMS _$$_TenantMDMSFromJson(Map<String, dynamic> json) =>
    _$_TenantMDMS(
      cityModule: (json['citymodule'] as List<dynamic>?)
          ?.map((e) => CityModule.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_TenantMDMSToJson(_$_TenantMDMS instance) =>
    <String, dynamic>{
      'citymodule': instance.cityModule,
    };

_$_CityModule _$$_CityModuleFromJson(Map<String, dynamic> json) =>
    _$_CityModule(
      active: json['active'] as bool?,
      code: json['code'] as String?,
      module: json['module'] as String?,
      order: json['order'] as int?,
      tenants: (json['tenants'] as List<dynamic>?)
          ?.map((e) => TenantList.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_CityModuleToJson(_$_CityModule instance) =>
    <String, dynamic>{
      'active': instance.active,
      'code': instance.code,
      'module': instance.module,
      'order': instance.order,
      'tenants': instance.tenants,
    };

_$_TenantList _$$_TenantListFromJson(Map<String, dynamic> json) =>
    _$_TenantList(
      code: json['code'] as String?,
    );

Map<String, dynamic> _$$_TenantListToJson(_$_TenantList instance) =>
    <String, dynamic>{
      'code': instance.code,
    };

_$_WageSeekerCommonMDMS _$$_WageSeekerCommonMDMSFromJson(
        Map<String, dynamic> json) =>
    _$_WageSeekerCommonMDMS(
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
    );

Map<String, dynamic> _$$_WageSeekerCommonMDMSToJson(
        _$_WageSeekerCommonMDMS instance) =>
    <String, dynamic>{
      'GenderType': instance.genderType,
      'WageSeekerSkills': instance.wageSeekerSkills,
      'Relationship': instance.relationship,
      'SocialCategory': instance.socialCategory,
    };

_$_GenderType _$$_GenderTypeFromJson(Map<String, dynamic> json) =>
    _$_GenderType(
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_GenderTypeToJson(_$_GenderType instance) =>
    <String, dynamic>{
      'code': instance.code,
      'active': instance.active,
    };

_$_Relationship _$$_RelationshipFromJson(Map<String, dynamic> json) =>
    _$_Relationship(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_RelationshipToJson(_$_Relationship instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };

_$_SocialCategory _$$_SocialCategoryFromJson(Map<String, dynamic> json) =>
    _$_SocialCategory(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_SocialCategoryToJson(_$_SocialCategory instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };

_$_BankAccType _$$_BankAccTypeFromJson(Map<String, dynamic> json) =>
    _$_BankAccType(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_BankAccTypeToJson(_$_BankAccType instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };
