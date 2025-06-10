// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

import '../works/my_works_search_criteria.dart';

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
    @JsonKey(name: 'CBOHomeScreenConfig')
        List<CBOHomeScreenConfigModel>? cboHomeScreenConfig,
    @JsonKey(name: 'CBOMyWorks')
        List<CBOMyWorksSearchCriteriaModel>? cboMyWorksSearchCriteria,
    @JsonKey(name: 'CBOMyServiceRequests')
        List<CBOMyServiceRequestsConfig>? cboMyServiceRequestsConfig,
  }) = _CommonUIConfigModel;

  factory CommonUIConfigModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$CommonUIConfigModelFromJson(json);
}

@freezed
class CBOHomeScreenConfigModel with _$CBOHomeScreenConfigModel {
  const factory CBOHomeScreenConfigModel(
      {required final int order,
      required final String key,
      final String? displayName,
      final String? label,
      final bool? active}) = _CBOHomeScreenConfigModel;

  factory CBOHomeScreenConfigModel.fromJson(Map<String, dynamic> json) =>
      _$CBOHomeScreenConfigModelFromJson(json);
}
