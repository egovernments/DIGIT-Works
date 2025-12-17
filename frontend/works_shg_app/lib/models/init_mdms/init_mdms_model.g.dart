// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'init_mdms_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$InitMdmsModelImpl _$$InitMdmsModelImplFromJson(Map<String, dynamic> json) =>
    _$InitMdmsModelImpl(
      commonMastersModel: json['common-masters'] == null
          ? null
          : CommonMastersModel.fromJson(
              json['common-masters'] as Map<String, dynamic>),
      tenant: json['tenant'] == null
          ? null
          : TenantModel.fromJson(json['tenant'] as Map<String, dynamic>),
      commonUIConfigModel: json['commonUiConfig'] == null
          ? null
          : CommonUIConfigModel.fromJson(
              json['commonUiConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$InitMdmsModelImplToJson(_$InitMdmsModelImpl instance) =>
    <String, dynamic>{
      'common-masters': instance.commonMastersModel,
      'tenant': instance.tenant,
      'commonUiConfig': instance.commonUIConfigModel,
    };

_$TenantModelImpl _$$TenantModelImplFromJson(Map<String, dynamic> json) =>
    _$TenantModelImpl(
      tenantListModel: (json['tenants'] as List<dynamic>?)
          ?.map((e) => TenantListModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      cityModuleModel: (json['citymodule'] as List<dynamic>?)
          ?.map((e) => CityModuleModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$TenantModelImplToJson(_$TenantModelImpl instance) =>
    <String, dynamic>{
      'tenants': instance.tenantListModel,
      'citymodule': instance.cityModuleModel,
    };

_$CommonMastersModelImpl _$$CommonMastersModelImplFromJson(
        Map<String, dynamic> json) =>
    _$CommonMastersModelImpl(
      stateInfoListModel: (json['StateInfo'] as List<dynamic>?)
          ?.map((e) => StateInfoListModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      appVersionModel: (json['AppVersion'] as List<dynamic>?)
          ?.map((e) => AppVersionModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$CommonMastersModelImplToJson(
        _$CommonMastersModelImpl instance) =>
    <String, dynamic>{
      'StateInfo': instance.stateInfoListModel,
      'AppVersion': instance.appVersionModel,
    };

_$AppVersionModelImpl _$$AppVersionModelImplFromJson(
        Map<String, dynamic> json) =>
    _$AppVersionModelImpl(
      version: json['version'] as String?,
      packageName: json['packageName'] as String?,
      iOSId: json['iOSId'] as String?,
    );

Map<String, dynamic> _$$AppVersionModelImplToJson(
        _$AppVersionModelImpl instance) =>
    <String, dynamic>{
      'version': instance.version,
      'packageName': instance.packageName,
      'iOSId': instance.iOSId,
    };

_$StateInfoListModelImpl _$$StateInfoListModelImplFromJson(
        Map<String, dynamic> json) =>
    _$StateInfoListModelImpl(
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

Map<String, dynamic> _$$StateInfoListModelImplToJson(
        _$StateInfoListModelImpl instance) =>
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

_$TenantListModelImpl _$$TenantListModelImplFromJson(
        Map<String, dynamic> json) =>
    _$TenantListModelImpl(
      code: json['code'] as String?,
      contactNumber: json['contactNumber'] as String?,
      imageId: json['imageId'] as String?,
      logoId: json['logoId'] as String?,
      pdfContactDetails: json['pdfContactDetails'] as String?,
      pdfHeader: json['pdfHeader'] as String?,
    );

Map<String, dynamic> _$$TenantListModelImplToJson(
        _$TenantListModelImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'contactNumber': instance.contactNumber,
      'imageId': instance.imageId,
      'logoId': instance.logoId,
      'pdfContactDetails': instance.pdfContactDetails,
      'pdfHeader': instance.pdfHeader,
    };

_$CityModuleModelImpl _$$CityModuleModelImplFromJson(
        Map<String, dynamic> json) =>
    _$CityModuleModelImpl(
      active: json['active'] as bool?,
      code: json['code'] as String?,
      module: json['module'] as String?,
      order: (json['order'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$CityModuleModelImplToJson(
        _$CityModuleModelImpl instance) =>
    <String, dynamic>{
      'active': instance.active,
      'code': instance.code,
      'module': instance.module,
      'order': instance.order,
    };

_$CommonUIConfigModelImpl _$$CommonUIConfigModelImplFromJson(
        Map<String, dynamic> json) =>
    _$CommonUIConfigModelImpl(
      privacyPolicyModels: (json['PrivacyPolicy'] as List<dynamic>?)
          ?.map((e) => PrivacyPolicyModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$CommonUIConfigModelImplToJson(
        _$CommonUIConfigModelImpl instance) =>
    <String, dynamic>{
      'PrivacyPolicy': instance.privacyPolicyModels,
    };

_$PrivacyPolicyModelImpl _$$PrivacyPolicyModelImplFromJson(
        Map<String, dynamic> json) =>
    _$PrivacyPolicyModelImpl(
      header: json['header'] as String?,
      active: json['active'] as bool?,
      module: json['module'] as String?,
      contents: (json['contents'] as List<dynamic>?)
          ?.map((e) => ContentModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$PrivacyPolicyModelImplToJson(
        _$PrivacyPolicyModelImpl instance) =>
    <String, dynamic>{
      'header': instance.header,
      'active': instance.active,
      'module': instance.module,
      'contents': instance.contents,
    };

_$ContentModelImpl _$$ContentModelImplFromJson(Map<String, dynamic> json) =>
    _$ContentModelImpl(
      header: json['header'] as String?,
      descriptions: (json['descriptions'] as List<dynamic>?)
          ?.map((e) => DescriptionModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$ContentModelImplToJson(_$ContentModelImpl instance) =>
    <String, dynamic>{
      'header': instance.header,
      'descriptions': instance.descriptions,
    };

_$DescriptionModelImpl _$$DescriptionModelImplFromJson(
        Map<String, dynamic> json) =>
    _$DescriptionModelImpl(
      text: json['text'] as String?,
      type: json['type'] as String?,
      isBold: json['isBold'] as bool?,
      subDescriptions: (json['subDescriptions'] as List<dynamic>?)
          ?.map((e) => SubDescriptionModel.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$DescriptionModelImplToJson(
        _$DescriptionModelImpl instance) =>
    <String, dynamic>{
      'text': instance.text,
      'type': instance.type,
      'isBold': instance.isBold,
      'subDescriptions': instance.subDescriptions,
    };

_$SubDescriptionModelImpl _$$SubDescriptionModelImplFromJson(
        Map<String, dynamic> json) =>
    _$SubDescriptionModelImpl(
      text: json['text'] as String?,
      type: json['type'] as String?,
      isBold: json['isBold'] as bool?,
      isSpaceRequired: json['isSpaceRequired'] as bool?,
    );

Map<String, dynamic> _$$SubDescriptionModelImplToJson(
        _$SubDescriptionModelImpl instance) =>
    <String, dynamic>{
      'text': instance.text,
      'type': instance.type,
      'isBold': instance.isBold,
      'isSpaceRequired': instance.isSpaceRequired,
    };
