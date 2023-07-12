// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'init_mdms_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_InitMdmsModel _$$_InitMdmsModelFromJson(Map<String, dynamic> json) =>
    _$_InitMdmsModel(
      commonMastersModel: json['common-masters'] == null
          ? null
          : CommonMastersModel.fromJson(
              json['common-masters'] as Map<String, dynamic>),
      tenant: json['tenant'] == null
          ? null
          : TenantModel.fromJson(json['tenant'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_InitMdmsModelToJson(_$_InitMdmsModel instance) =>
    <String, dynamic>{
      'common-masters': instance.commonMastersModel,
      'tenant': instance.tenant,
    };

_$_TenantModel _$$_TenantModelFromJson(Map<String, dynamic> json) =>
    _$_TenantModel(
      tenantListModel: (json['tenants'] as List<dynamic>?)
          ?.map((e) => TenantListModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      cityModuleModel: (json['citymodule'] as List<dynamic>?)
          ?.map((e) => CityModuleModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_TenantModelToJson(_$_TenantModel instance) =>
    <String, dynamic>{
      'tenants': instance.tenantListModel,
      'citymodule': instance.cityModuleModel,
    };

_$_CommonMastersModel _$$_CommonMastersModelFromJson(
        Map<String, dynamic> json) =>
    _$_CommonMastersModel(
      stateInfoListModel: (json['StateInfo'] as List<dynamic>?)
          ?.map((e) => StateInfoListModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      appVersionModel: (json['AppVersion'] as List<dynamic>?)
          ?.map((e) => AppVersionModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_CommonMastersModelToJson(
        _$_CommonMastersModel instance) =>
    <String, dynamic>{
      'StateInfo': instance.stateInfoListModel,
      'AppVersion': instance.appVersionModel,
    };

_$_AppVersionModel _$$_AppVersionModelFromJson(Map<String, dynamic> json) =>
    _$_AppVersionModel(
      version: json['version'] as String?,
      packageName: json['packageName'] as String?,
      iOSId: json['iOSId'] as String?,
    );

Map<String, dynamic> _$$_AppVersionModelToJson(_$_AppVersionModel instance) =>
    <String, dynamic>{
      'version': instance.version,
      'packageName': instance.packageName,
      'iOSId': instance.iOSId,
    };

_$_StateInfoListModel _$$_StateInfoListModelFromJson(
        Map<String, dynamic> json) =>
    _$_StateInfoListModel(
      bannerUrl: json['bannerUrl'] as String?,
      code: json['code'] as String?,
      hasLocalisation: json['hasLocalisation'] as bool?,
      languages: (json['languages'] as List<dynamic>?)
          ?.map((e) => Languages.fromJson(e as Map<String, dynamic>))
          .toList(),
      localizationModules: (json['localizationModules'] as List<dynamic>?)
          ?.map((e) => LocalizationModules.fromJson(e as Map<String, dynamic>))
          .toList(),
      logoUrl: json['logoUrl'] as String?,
      logoUrlWhite: json['logoUrlWhite'] as String?,
      statelogo: json['statelogo'] as String?,
      qrCodeURL: json['qrCodeURL'] as String?,
    );

Map<String, dynamic> _$$_StateInfoListModelToJson(
        _$_StateInfoListModel instance) =>
    <String, dynamic>{
      'bannerUrl': instance.bannerUrl,
      'code': instance.code,
      'hasLocalisation': instance.hasLocalisation,
      'languages': instance.languages,
      'localizationModules': instance.localizationModules,
      'logoUrl': instance.logoUrl,
      'logoUrlWhite': instance.logoUrlWhite,
      'statelogo': instance.statelogo,
      'qrCodeURL': instance.qrCodeURL,
    };

_$_TenantListModel _$$_TenantListModelFromJson(Map<String, dynamic> json) =>
    _$_TenantListModel(
      code: json['code'] as String?,
      contactNumber: json['contactNumber'] as String?,
      imageId: json['imageId'] as String?,
      logoId: json['logoId'] as String?,
      pdfContactDetails: json['pdfContactDetails'] as String?,
      pdfHeader: json['pdfHeader'] as String?,
    );

Map<String, dynamic> _$$_TenantListModelToJson(_$_TenantListModel instance) =>
    <String, dynamic>{
      'code': instance.code,
      'contactNumber': instance.contactNumber,
      'imageId': instance.imageId,
      'logoId': instance.logoId,
      'pdfContactDetails': instance.pdfContactDetails,
      'pdfHeader': instance.pdfHeader,
    };

_$_CityModuleModel _$$_CityModuleModelFromJson(Map<String, dynamic> json) =>
    _$_CityModuleModel(
      active: json['active'] as bool?,
      code: json['code'] as String?,
      module: json['module'] as String?,
      order: json['order'] as int?,
    );

Map<String, dynamic> _$$_CityModuleModelToJson(_$_CityModuleModel instance) =>
    <String, dynamic>{
      'active': instance.active,
      'code': instance.code,
      'module': instance.module,
      'order': instance.order,
    };
