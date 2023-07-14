// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

part 'home_screen_config.freezed.dart';
part 'home_screen_config.g.dart';

@freezed
class HomeScreenConfigModel with _$HomeScreenConfigModel {
  const factory HomeScreenConfigModel({
    @JsonKey(name: 'commonUiConfig') final CommonUIConfigModel? commonUiConfig,
  }) = _HomeScreenConfigModel;

  factory HomeScreenConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$HomeScreenConfigModelFromJson(json);
}

@freezed
class CommonUIConfigModel with _$CommonUIConfigModel {
  const factory CommonUIConfigModel({
    @JsonKey(name: 'HomeScreenCardConfig')
        List<HomeScreenCardConfigModel>? homeScreenCardConfig,
  }) = _CommonUIConfigModel;

  factory CommonUIConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$CommonUIConfigModelFromJson(json);
}

@freezed
class HomeScreenCardConfigModel with _$HomeScreenCardConfigModel {
  const factory HomeScreenCardConfigModel(
      {required final int order,
      required final String header,
      final String? displayName,
      final String? label,
      final String? icon,
      final List<HomeScreenLinksModel>? links,
      final bool? active}) = _HomeScreenCardConfigModel;

  factory HomeScreenCardConfigModel.fromJson(Map<String, dynamic> json) =>
      _$HomeScreenCardConfigModelFromJson(json);
}

@freezed
class HomeScreenLinksModel with _$HomeScreenLinksModel {
  const factory HomeScreenLinksModel(
      {required final int order,
      required final String key,
      final String? displayName,
      final String? label,
      final bool? active}) = _HomeScreenLinksModel;

  factory HomeScreenLinksModel.fromJson(Map<String, dynamic> json) =>
      _$HomeScreenLinksModelFromJson(json);
}
