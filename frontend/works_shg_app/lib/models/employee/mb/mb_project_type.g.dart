// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'mb_project_type.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MBProjectTypeImpl _$$MBProjectTypeImplFromJson(Map<String, dynamic> json) =>
    _$MBProjectTypeImpl(
      mdmsRes: json['MdmsRes'] == null
          ? null
          : MdmsRes.fromJson(json['MdmsRes'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$MBProjectTypeImplToJson(_$MBProjectTypeImpl instance) =>
    <String, dynamic>{
      'MdmsRes': instance.mdmsRes,
    };

_$MdmsResImpl _$$MdmsResImplFromJson(Map<String, dynamic> json) =>
    _$MdmsResImpl(
      mbWorks: json['works'] == null
          ? null
          : MBWorks.fromJson(json['works'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$MdmsResImplToJson(_$MdmsResImpl instance) =>
    <String, dynamic>{
      'works': instance.mbWorks,
    };

_$MBWorksImpl _$$MBWorksImplFromJson(Map<String, dynamic> json) =>
    _$MBWorksImpl(
      projectType: (json['ProjectType'] as List<dynamic>?)
          ?.map((e) => ProjectType.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MBWorksImplToJson(_$MBWorksImpl instance) =>
    <String, dynamic>{
      'ProjectType': instance.projectType,
    };

_$ProjectTypeImpl _$$ProjectTypeImplFromJson(Map<String, dynamic> json) =>
    _$ProjectTypeImpl(
      id: (json['id'] as num?)?.toInt(),
      name: json['name'] as String?,
      code: json['code'] as String?,
      active: json['active'] as bool?,
    );

Map<String, dynamic> _$$ProjectTypeImplToJson(_$ProjectTypeImpl instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'code': instance.code,
      'active': instance.active,
    };
