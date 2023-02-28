// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'sub_skills.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_SubSkillsList _$$_SubSkillsListFromJson(Map<String, dynamic> json) =>
    _$_SubSkillsList(
      wageSeekerSubSkills: (json['WageSeekerSubSkills'] as List<dynamic>?)
          ?.map((e) => WageSeekerSubSkills.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_SubSkillsListToJson(_$_SubSkillsList instance) =>
    <String, dynamic>{
      'WageSeekerSubSkills': instance.wageSeekerSubSkills,
    };

_$_WageSeekerSubSkills _$$_WageSeekerSubSkillsFromJson(
        Map<String, dynamic> json) =>
    _$_WageSeekerSubSkills(
      name: json['name'] as String,
      code: json['code'] as String,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_WageSeekerSubSkillsToJson(
        _$_WageSeekerSubSkills instance) =>
    <String, dynamic>{
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };
