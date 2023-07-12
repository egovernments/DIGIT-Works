// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'location_mdms.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_Location _$$_LocationFromJson(Map<String, dynamic> json) => _$_Location(
      tenantBoundaryList: (json['TenantBoundary'] as List<dynamic>?)
          ?.map((e) => TenantBoundary.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_LocationToJson(_$_Location instance) =>
    <String, dynamic>{
      'TenantBoundary': instance.tenantBoundaryList,
    };

_$_TenantBoundary _$$_TenantBoundaryFromJson(Map<String, dynamic> json) =>
    _$_TenantBoundary(
      boundaryList: (json['boundary'] as List<dynamic>?)
          ?.map((e) => WardBoundary.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_TenantBoundaryToJson(_$_TenantBoundary instance) =>
    <String, dynamic>{
      'boundary': instance.boundaryList,
    };

_$_WardBoundary _$$_WardBoundaryFromJson(Map<String, dynamic> json) =>
    _$_WardBoundary(
      code: json['code'] as String?,
      name: json['name'] as String?,
      localityChildren: (json['children'] as List<dynamic>?)
          ?.map((e) => LocalityChild.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_WardBoundaryToJson(_$_WardBoundary instance) =>
    <String, dynamic>{
      'code': instance.code,
      'name': instance.name,
      'children': instance.localityChildren,
    };

_$_LocalityChild _$$_LocalityChildFromJson(Map<String, dynamic> json) =>
    _$_LocalityChild(
      code: json['code'] as String?,
      name: json['name'] as String?,
      latitude: json['latitude'] as String?,
      longitude: json['longitude'] as String?,
    );

Map<String, dynamic> _$$_LocalityChildToJson(_$_LocalityChild instance) =>
    <String, dynamic>{
      'code': instance.code,
      'name': instance.name,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
    };
