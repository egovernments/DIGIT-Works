// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'my_bills_inbox_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MyBillsInboxConfigListImpl _$$MyBillsInboxConfigListImplFromJson(
        Map<String, dynamic> json) =>
    _$MyBillsInboxConfigListImpl(
      myBillsInboxConfig: (json['CBOBillInboxConfig'] as List<dynamic>)
          .map((e) => MyBillsInboxConfig.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MyBillsInboxConfigListImplToJson(
        _$MyBillsInboxConfigListImpl instance) =>
    <String, dynamic>{
      'CBOBillInboxConfig': instance.myBillsInboxConfig,
    };

_$MyBillsInboxConfigImpl _$$MyBillsInboxConfigImplFromJson(
        Map<String, dynamic> json) =>
    _$MyBillsInboxConfigImpl(
      rejectedCode: json['rejectedCode'] as String,
      approvedCode: json['approvedCode'] as String,
    );

Map<String, dynamic> _$$MyBillsInboxConfigImplToJson(
        _$MyBillsInboxConfigImpl instance) =>
    <String, dynamic>{
      'rejectedCode': instance.rejectedCode,
      'approvedCode': instance.approvedCode,
    };
