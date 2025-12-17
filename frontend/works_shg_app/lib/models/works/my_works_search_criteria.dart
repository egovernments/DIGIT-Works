// ignore_for_file: invalid_annotation_target

import 'package:freezed_annotation/freezed_annotation.dart';

import '../screen_config/home_screen_config.dart';

part 'my_works_search_criteria.freezed.dart';
part 'my_works_search_criteria.g.dart';

@freezed
class MyWorksSearchCriteriaModel with _$MyWorksSearchCriteriaModel {
  const factory MyWorksSearchCriteriaModel({
    @JsonKey(name: 'commonUiConfig') final CommonUIConfigModel? commonUiConfig,
  }) = _MyWorksSearchCriteriaModel;

  factory MyWorksSearchCriteriaModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MyWorksSearchCriteriaModelFromJson(json);
}

@freezed
class CBOMyWorksSearchCriteriaModel with _$CBOMyWorksSearchCriteriaModel {
  const factory CBOMyWorksSearchCriteriaModel(
      {final List<String>? searchCriteria,
      final String? acceptCode}) = _CBOMyWorksSearchCriteriaModel;
  factory CBOMyWorksSearchCriteriaModel.fromJson(Map<String, dynamic> json) =>
      _$CBOMyWorksSearchCriteriaModelFromJson(json);
}

@freezed
class CBOMyServiceRequestsConfig with _$CBOMyServiceRequestsConfig {
  const factory CBOMyServiceRequestsConfig({
    final String? editTimeExtReqCode,
    final String? editActionCode,
    final String? searchCriteria,
  }) = _CBOMyServiceRequestsConfig;
  factory CBOMyServiceRequestsConfig.fromJson(Map<String, dynamic> json) =>
      _$CBOMyServiceRequestsConfigFromJson(json);
}
