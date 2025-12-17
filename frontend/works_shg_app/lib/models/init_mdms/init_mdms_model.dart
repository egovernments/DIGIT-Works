// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/app_config/app_config_model.dart';

part 'init_mdms_model.freezed.dart';
part 'init_mdms_model.g.dart';

@freezed
class InitMdmsModel with _$InitMdmsModel {
  const factory InitMdmsModel({
    @JsonKey(name: 'common-masters')
    final CommonMastersModel? commonMastersModel,
    @JsonKey(name: 'tenant') final TenantModel? tenant,
    @JsonKey(name: 'commonUiConfig')
    final CommonUIConfigModel? commonUIConfigModel,
  }) = _InitMdmsModel;

  factory InitMdmsModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$InitMdmsModelFromJson(json);
}

@freezed
class TenantModel with _$TenantModel {
  const factory TenantModel({
    @JsonKey(name: 'tenants') List<TenantListModel>? tenantListModel,
    @JsonKey(name: 'citymodule') List<CityModuleModel>? cityModuleModel,
  }) = _TenantModel;

  factory TenantModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$TenantModelFromJson(json);
}

@freezed
class CommonMastersModel with _$CommonMastersModel {
  const factory CommonMastersModel({
    @JsonKey(name: 'StateInfo') List<StateInfoListModel>? stateInfoListModel,
    @JsonKey(name: 'AppVersion') List<AppVersionModel>? appVersionModel,
  }) = _CommonMastersModel;

  factory CommonMastersModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$CommonMastersModelFromJson(json);
}

@freezed
class AppVersionModel with _$AppVersionModel {
  const factory AppVersionModel(
      {final String? version,
      final String? packageName,
      final String? iOSId}) = _AppVersionModel;

  factory AppVersionModel.fromJson(Map<String, dynamic> json) =>
      _$AppVersionModelFromJson(json);
}

@freezed
class StateInfoListModel with _$StateInfoListModel {
  const factory StateInfoListModel({
    final String? bannerUrl,
    final String? code,
    final bool? hasLocalisation,
    final List<Languages>? languages,
    final List<LocalizationModules>? localizationModules,
    final String? logoUrl,
    final String? logoUrlWhite,
    final String? statelogo,
    final String? qrCodeURL,
  }) = _StateInfoListModel;

  factory StateInfoListModel.fromJson(Map<String, dynamic> json) =>
      _$StateInfoListModelFromJson(json);
}

@freezed
class TenantListModel with _$TenantListModel {
  const factory TenantListModel({
    final String? code,
    final String? contactNumber,
    final String? imageId,
    final String? logoId,
    final String? pdfContactDetails,
    final String? pdfHeader,
  }) = _TenantListModel;

  factory TenantListModel.fromJson(Map<String, dynamic> json) =>
      _$TenantListModelFromJson(json);
}

@freezed
class CityModuleModel with _$CityModuleModel {
  const factory CityModuleModel({
    final bool? active,
    final String? code,
    final String? module,
    final int? order,
  }) = _CityModuleModel;

  factory CityModuleModel.fromJson(Map<String, dynamic> json) =>
      _$CityModuleModelFromJson(json);
}



@freezed
class CommonUIConfigModel with _$CommonUIConfigModel{
  const factory CommonUIConfigModel({
     @JsonKey(name: 'PrivacyPolicy') List<PrivacyPolicyModel>? privacyPolicyModels
  }) = _CommonUIConfigModel;
   factory CommonUIConfigModel.fromJson(Map<String, dynamic> json) =>
      _$CommonUIConfigModelFromJson(json);
}


@freezed
class PrivacyPolicyModel with _$PrivacyPolicyModel {
  const factory PrivacyPolicyModel({
    final String? header,
    final bool? active,
    final String? module,
    final List<ContentModel>? contents,
  }) = _PrivacyPolicyModel;

  factory PrivacyPolicyModel.fromJson(Map<String, dynamic> json) =>
      _$PrivacyPolicyModelFromJson(json);
}

// class PrivacyPolicy {
//   late String header;
//   late String module;
//   late bool? active;
//   late List<Content>? contents;
// }

@freezed
class ContentModel with _$ContentModel {
  const factory ContentModel(
      {final String? header,
      final List<DescriptionModel>? descriptions}) = _ContentModel;

  factory ContentModel.fromJson(Map<String, dynamic> json) =>
      _$ContentModelFromJson(json);
}
// @embedded
// class Content {
//   late String? header;
//   late List<Description>? descriptions;
// }

@freezed
class DescriptionModel with _$DescriptionModel {
  const factory DescriptionModel({
    final String? text,
    final String? type,
    final bool? isBold,
    final List<SubDescriptionModel>? subDescriptions,
  }) = _DescriptionModel;

  factory DescriptionModel.fromJson(Map<String, dynamic> json) =>
      _$DescriptionModelFromJson(json);
}

// @embedded
// class Description {
//   late String? text;
//   late String? type;
//   late bool? isBold;
//   late List<SubDescription>? subDescriptions;
// }

@freezed
class SubDescriptionModel with _$SubDescriptionModel {
  const factory SubDescriptionModel({
    final String? text,
    final String? type,
    final bool? isBold,
    final bool? isSpaceRequired,
  }) = _SubDescriptionModel;

  factory SubDescriptionModel.fromJson(Map<String, dynamic> json) =>
      _$SubDescriptionModelFromJson(json);
}

// @embedded
// class SubDescription {
//   late String? text;
//   late String? type;
//   late bool? isBold;
//   late bool? isSpaceRequired;
// }