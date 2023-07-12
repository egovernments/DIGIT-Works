// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:flutter_training/models/app_config/app_config_model.dart';

part 'init_mdms_model.freezed.dart';
part 'init_mdms_model.g.dart';

@freezed
class InitMdmsModel with _$InitMdmsModel {
  const factory InitMdmsModel({
    @JsonKey(name: 'common-masters')
        final CommonMastersModel? commonMastersModel,
    @JsonKey(name: 'tenant')
        final TenantModel? tenant,
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
