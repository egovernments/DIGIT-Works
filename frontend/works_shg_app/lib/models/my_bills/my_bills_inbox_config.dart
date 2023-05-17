import 'package:freezed_annotation/freezed_annotation.dart';

part 'my_bills_inbox_config.freezed.dart';
part 'my_bills_inbox_config.g.dart';

@freezed
class MyBillsInboxConfigList with _$MyBillsInboxConfigList {
  const factory MyBillsInboxConfigList({
    @JsonKey(name: 'CBOBillInboxConfig')
        required List<MyBillsInboxConfig> myBillsInboxConfig,
  }) = _MyBillsInboxConfigList;

  factory MyBillsInboxConfigList.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MyBillsInboxConfigListFromJson(json);
}

@freezed
class MyBillsInboxConfig with _$MyBillsInboxConfig {
  const factory MyBillsInboxConfig({
    required String rejectedCode,
    required String approvedCode,
  }) = _MyBillsInboxConfig;

  factory MyBillsInboxConfig.fromJson(Map<String, dynamic> json) =>
      _$MyBillsInboxConfigFromJson(json);
}
