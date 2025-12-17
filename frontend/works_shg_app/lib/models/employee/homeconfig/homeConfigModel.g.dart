// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'homeConfigModel.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$HomeConfigModelImpl _$$HomeConfigModelImplFromJson(
        Map<String, dynamic> json) =>
    _$HomeConfigModelImpl(
      homeActions: (json['actions'] as List<dynamic>)
          .map((e) => HomeAction.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$HomeConfigModelImplToJson(
        _$HomeConfigModelImpl instance) =>
    <String, dynamic>{
      'actions': instance.homeActions,
    };

_$HomeActionImpl _$$HomeActionImplFromJson(Map<String, dynamic> json) =>
    _$HomeActionImpl(
      id: (json['id'] as num).toInt(),
      displayName: json['displayName'] as String,
      parentModule: json['parentModule'] as String,
      enabled: json['enabled'] as bool,
      tenantId: json['tenantId'] as String,
      url: json['url'] as String,
    );

Map<String, dynamic> _$$HomeActionImplToJson(_$HomeActionImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'displayName': instance.displayName,
      'parentModule': instance.parentModule,
      'enabled': instance.enabled,
      'tenantId': instance.tenantId,
      'url': instance.url,
    };
