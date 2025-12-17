import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/employee/mb/mb_detail_response.dart';

part 'estimate_model.freezed.dart';
part 'estimate_model.g.dart';

@freezed
class EstimateDetailResponse with _$EstimateDetailResponse {
  const factory EstimateDetailResponse({
    
    @JsonKey(name: 'estimates') List<Estimate>? estimates,
     @JsonKey(name: 'TotalCount') int? totalCount,
    
  }) = _EstimateDetailResponse;

  factory EstimateDetailResponse.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$EstimateDetailResponseFromJson(json);
}