// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'skills.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$SkillsListImpl _$$SkillsListImplFromJson(Map<String, dynamic> json) =>
    _$SkillsListImpl(
      wageSeekerSkills: (json['SOR'] as List<dynamic>?)
          ?.map((e) => WageSeekerSkills.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$SkillsListImplToJson(_$SkillsListImpl instance) =>
    <String, dynamic>{
      'SOR': instance.wageSeekerSkills,
    };

_$WageSeekerSkillsImpl _$$WageSeekerSkillsImplFromJson(
        Map<String, dynamic> json) =>
    _$WageSeekerSkillsImpl(
      json['code'] as String?,
      (json['amount'] as num?)?.toInt(),
      json['active'] as bool?,
      json['description'] as String?,
      json['id'] as String?,
    );

Map<String, dynamic> _$$WageSeekerSkillsImplToJson(
        _$WageSeekerSkillsImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'amount': instance.amount,
      'active': instance.active,
      'description': instance.description,
      'id': instance.id,
    };
