import 'package:freezed_annotation/freezed_annotation.dart';

import '../skills/skills.dart';

part 'wage_seeker_mdms.freezed.dart';
part 'wage_seeker_mdms.g.dart';

@freezed
class WageSeekerMDMS with _$WageSeekerMDMS {
  const factory WageSeekerMDMS({
    @JsonKey(name: 'common-masters') WageSeekerCommonMDMS? commonMDMS,
    @JsonKey(name: 'works') WageSeekerWorksMDMS? worksMDMS,
    @JsonKey(name: 'tenant') TenantMDMS? tenantMDMS,
  }) = _WageSeekerMDMS;

  factory WageSeekerMDMS.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WageSeekerMDMSFromJson(json);
}

@freezed
class WageSeekerWorksMDMS with _$WageSeekerWorksMDMS {
  const factory WageSeekerWorksMDMS({
    @JsonKey(name: 'BankAccType') List<BankAccType>? bankAccType,
  }) = _WageSeekerWorksMDMS;

  factory WageSeekerWorksMDMS.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WageSeekerWorksMDMSFromJson(json);
}

@freezed
class TenantMDMS with _$TenantMDMS {
  const factory TenantMDMS({
    @JsonKey(name: 'citymodule') List<CityModule>? cityModule,
  }) = _TenantMDMS;

  factory TenantMDMS.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$TenantMDMSFromJson(json);
}

@freezed
class CityModule with _$CityModule {
  const factory CityModule({
    final bool? active,
    final String? code,
    final String? module,
    final int? order,
    final List<TenantList>? tenants,
  }) = _CityModule;

  factory CityModule.fromJson(Map<String, dynamic> json) =>
      _$CityModuleFromJson(json);
}

@freezed
class TenantList with _$TenantList {
  const factory TenantList({
    @JsonKey(name: 'code') String? code,
  }) = _TenantList;

  factory TenantList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$TenantListFromJson(json);
}

@freezed
class WageSeekerCommonMDMS with _$WageSeekerCommonMDMS {
  const factory WageSeekerCommonMDMS({
    @JsonKey(name: 'GenderType') List<GenderType>? genderType,
    @JsonKey(name: 'WageSeekerSkills') List<WageSeekerSkills>? wageSeekerSkills,
    @JsonKey(name: 'Relationship') List<Relationship>? relationship,
    @JsonKey(name: 'SocialCategory') List<SocialCategory>? socialCategory,
    @JsonKey(name: 'DocumentType')  List<DocumentType>? documentType,
  }) = _WageSeekerCommonMDMS;

  factory WageSeekerCommonMDMS.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WageSeekerCommonMDMSFromJson(json);
}

@freezed
class GenderType with _$GenderType {
  const factory GenderType({
    required String code,
    required bool active,
  }) = _GenderType;

  factory GenderType.fromJson(Map<String, dynamic> json) =>
      _$GenderTypeFromJson(json);
}

@freezed
class Relationship with _$Relationship {
  const factory Relationship({
    required String name,
    required String code,
    required bool active,
  }) = _Relationship;

  factory Relationship.fromJson(Map<String, dynamic> json) =>
      _$RelationshipFromJson(json);
}

@freezed
class SocialCategory with _$SocialCategory {
  const factory SocialCategory({
    required String name,
    required String code,
    required bool active,
  }) = _SocialCategory;

  factory SocialCategory.fromJson(Map<String, dynamic> json) =>
      _$SocialCategoryFromJson(json);
}

@freezed
class BankAccType with _$BankAccType {
  const factory BankAccType({
    required String name,
    required String code,
    required bool active,
  }) = _BankAccType;

  factory BankAccType.fromJson(Map<String, dynamic> json) =>
      _$BankAccTypeFromJson(json);
}


@freezed
class DocumentType with _$DocumentType {
  const factory DocumentType({
    required String name,
    required String code,
    required bool active,
  }) = _DocumentType;

  factory DocumentType.fromJson(Map<String, dynamic> json) =>
      _$DocumentTypeFromJson(json);
}
