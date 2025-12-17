// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'location_mdms.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$LocationImpl _$$LocationImplFromJson(Map<String, dynamic> json) =>
    _$LocationImpl(
      tenantBoundaryList: (json['TenantBoundary'] as List<dynamic>?)
          ?.map((e) => TenantBoundary.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$LocationImplToJson(_$LocationImpl instance) =>
    <String, dynamic>{
      'TenantBoundary': instance.tenantBoundaryList,
    };

_$TenantBoundaryImpl _$$TenantBoundaryImplFromJson(Map<String, dynamic> json) =>
    _$TenantBoundaryImpl(
      boundaryList: (json['boundary'] as List<dynamic>?)
          ?.map((e) => WardBoundary.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$TenantBoundaryImplToJson(
        _$TenantBoundaryImpl instance) =>
    <String, dynamic>{
      'boundary': instance.boundaryList,
    };

_$WardBoundaryImpl _$$WardBoundaryImplFromJson(Map<String, dynamic> json) =>
    _$WardBoundaryImpl(
      code: json['code'] as String?,
      name: json['name'] as String?,
      localityChildren: (json['children'] as List<dynamic>?)
          ?.map((e) => LocalityChild.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$WardBoundaryImplToJson(_$WardBoundaryImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'name': instance.name,
      'children': instance.localityChildren,
    };

_$LocalityChildImpl _$$LocalityChildImplFromJson(Map<String, dynamic> json) =>
    _$LocalityChildImpl(
      code: json['code'] as String?,
      name: json['name'] as String?,
      latitude: json['latitude'] as String?,
      longitude: json['longitude'] as String?,
    );

Map<String, dynamic> _$$LocalityChildImplToJson(_$LocalityChildImpl instance) =>
    <String, dynamic>{
      'code': instance.code,
      'name': instance.name,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
    };
