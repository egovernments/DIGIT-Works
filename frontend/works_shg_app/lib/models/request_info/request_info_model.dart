// ignore_for_file: invalid_annotation_target
import 'package:freezed_annotation/freezed_annotation.dart';

part 'request_info_model.freezed.dart';
part 'request_info_model.g.dart';

@freezed
class RequestInfoMapperModel with _$RequestInfoMapperModel {
  const factory RequestInfoMapperModel({
    final RequestInfoModel? requestInfo,
  }) = _RequestInfoMapperModel;

  factory RequestInfoMapperModel.fromJson(Map<String, dynamic> json) =>
      _$RequestInfoMapperModelFromJson(json);
}

@freezed
class RequestInfoModel with _$RequestInfoModel {
  const factory RequestInfoModel({
    final String? apiId,
    final String? ver,
    final String? ts,
    final String? action,
    final String? did,
    final String? key,
    final String? msgId,
    final String? authToken,
  }) = _RequestInfoModel;

  factory RequestInfoModel.fromJson(Map<String, dynamic> json) =>
      _$RequestInfoModelFromJson(json);
}
