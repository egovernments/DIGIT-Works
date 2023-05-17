// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'my_bills_inbox_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MyBillsInboxConfigList _$$_MyBillsInboxConfigListFromJson(
        Map<String, dynamic> json) =>
    _$_MyBillsInboxConfigList(
      myBillsInboxConfig: (json['CBOBillInboxConfig'] as List<dynamic>)
          .map((e) => MyBillsInboxConfig.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_MyBillsInboxConfigListToJson(
        _$_MyBillsInboxConfigList instance) =>
    <String, dynamic>{
      'CBOBillInboxConfig': instance.myBillsInboxConfig,
    };

_$_MyBillsInboxConfig _$$_MyBillsInboxConfigFromJson(
        Map<String, dynamic> json) =>
    _$_MyBillsInboxConfig(
      rejectedCode: json['rejectedCode'] as String,
      approvedCode: json['approvedCode'] as String,
    );

Map<String, dynamic> _$$_MyBillsInboxConfigToJson(
        _$_MyBillsInboxConfig instance) =>
    <String, dynamic>{
      'rejectedCode': instance.rejectedCode,
      'approvedCode': instance.approvedCode,
    };
