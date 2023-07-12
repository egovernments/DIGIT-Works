// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'skills.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_SkillsList _$$_SkillsListFromJson(Map<String, dynamic> json) =>
    _$_SkillsList(
      wageSeekerSkills: (json['WageSeekerSkills'] as List<dynamic>?)
          ?.map((e) => WageSeekerSkills.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_SkillsListToJson(_$_SkillsList instance) =>
    <String, dynamic>{
      'WageSeekerSkills': instance.wageSeekerSkills,
    };

_$_WageSeekerSkills _$$_WageSeekerSkillsFromJson(Map<String, dynamic> json) =>
    _$_WageSeekerSkills(
      code: json['code'] as String,
      amount: json['amount'] as int?,
      active: json['active'] as bool,
    );

Map<String, dynamic> _$$_WageSeekerSkillsToJson(_$_WageSeekerSkills instance) =>
    <String, dynamic>{
      'code': instance.code,
      'amount': instance.amount,
      'active': instance.active,
    };
