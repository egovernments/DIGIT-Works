import 'package:freezed_annotation/freezed_annotation.dart';

import '../mb/mb_inbox_response.dart';

part 'wo_inbox_response.freezed.dart';
part 'wo_inbox_response.g.dart';

@freezed
class WOInboxResponse with _$WOInboxResponse {
  const factory WOInboxResponse(
      {@JsonKey(name: 'totalCount') int? totalCount,
      @JsonKey(name: 'nearingSlaCount') int? nearingSlaCount,
      @JsonKey(name: 'statusMap') List<StatusMap>? statusMap,
      @JsonKey(name: 'items') List<WOItemData>? items}) = _WOInboxResponse;

  factory WOInboxResponse.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WOInboxResponseFromJson(json);
}

@freezed
class WOItemData with _$WOItemData {
  const factory WOItemData({
    @JsonKey(name: 'ProcessInstance') ProcessInstance? processInstance,
    @JsonKey(name: 'businessObject') WOBusinessObject? woBusinessObject,
  }) = _WOItemData;

  factory WOItemData.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WOItemDataFromJson(json);
}

@freezed
class WOBusinessObject with _$WOBusinessObject {
  const factory WOBusinessObject({
    @JsonKey(name: 'totalContractedAmount') double? totalContractedAmount,
    @JsonKey(name: 'businessService') String? businessService,
    @JsonKey(name: 'contractNumber') String? contractNumber,
    @JsonKey(name: 'serviceSla') int? serviceSla,
    @JsonKey(name: 'additionalDetails') WOAdditionalDetails? woAdditionalDetails,
    @JsonKey(name: 'auditDetails') AuditDetails? auditDetails,
  }) = _WOBusinessObject;

  factory WOBusinessObject.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WOBusinessObjectFromJson(json);
}



@freezed
class WOAdditionalDetails with _$WOAdditionalDetails {
  const factory WOAdditionalDetails({
    @JsonKey(name: 'orgName') String? orgName,
    @JsonKey(name: 'projectId') String? projectId,
    @JsonKey(name: 'projectName') String? projectName,
    
  }) = _WOAdditionalDetails;

  factory WOAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WOAdditionalDetailsFromJson(json);
}