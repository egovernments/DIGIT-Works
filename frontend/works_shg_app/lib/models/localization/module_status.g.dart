// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'module_status.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$ModuleStatusImpl _$$ModuleStatusImplFromJson(Map<String, dynamic> json) =>
    _$ModuleStatusImpl(
      status: Map<String, bool>.from(json['status'] as Map),
      label: json['label'] as String,
      value: json['value'] as String,
    );

Map<String, dynamic> _$$ModuleStatusImplToJson(_$ModuleStatusImpl instance) =>
    <String, dynamic>{
      'status': instance.status,
      'label': instance.label,
      'value': instance.value,
    };
