import 'package:freezed_annotation/freezed_annotation.dart';

part 'global_config_model.freezed.dart';
part 'global_config_model.g.dart';

@freezed
class GlobalConfigModel with _$GlobalConfigModel {
  const factory GlobalConfigModel({
    @JsonKey(name: 'globalConfigs') final GlobalConfigs? globalConfigs,
  }) = _GlobalConfigModel;

  factory GlobalConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$GlobalConfigModelFromJson(json);
}

@freezed
class GlobalConfigs with _$GlobalConfigs {
  const factory GlobalConfigs({
    @JsonKey(name: 'stateTenantId') required final String stateTenantId,
    @JsonKey(name: 'gmaps_api_key') final String? gmaps_api_key,
    @JsonKey(name: 'finEnv') final String? finEnv,
    @JsonKey(name: 'contextPath') final String? contextPath,
    @JsonKey(name: 'footerLogoURL') final String? footerLogoURL,
    @JsonKey(name: 'footerBWLogoURL') final String? footerBWLogoURL,
    @JsonKey(name: 'centralInstanceEnabled') final bool? centralInstanceEnabled,
    @JsonKey(name: 'assetS3Bucket') final String? assetS3Bucket,
  }) = _GlobalConfigs;

  factory GlobalConfigs.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$GlobalConfigsFromJson(json);
}
