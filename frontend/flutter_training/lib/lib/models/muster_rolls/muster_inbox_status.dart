import 'package:freezed_annotation/freezed_annotation.dart';

part 'muster_inbox_status.freezed.dart';
part 'muster_inbox_status.g.dart';

@freezed
class MusterInboxStatusList with _$MusterInboxStatusList {
  const factory MusterInboxStatusList({
    @JsonKey(name: 'CBOMusterInboxConfig')
        List<MusterInboxStatus>? musterInboxStatus,
  }) = _MusterInboxStatusList;

  factory MusterInboxStatusList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterInboxStatusListFromJson(json);
}

@freezed
class MusterInboxStatus with _$MusterInboxStatus {
  const factory MusterInboxStatus({
    required String reSubmitCode,
  }) = _MusterInboxStatus;

  factory MusterInboxStatus.fromJson(Map<String, dynamic> json) =>
      _$MusterInboxStatusFromJson(json);
}
