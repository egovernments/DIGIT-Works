import 'package:freezed_annotation/freezed_annotation.dart';

part 'location_mdms.freezed.dart';
part 'location_mdms.g.dart';

@freezed
class Location with _$Location {
  const factory Location({
    @JsonKey(name: 'TenantBoundary') List<TenantBoundary>? tenantBoundaryList,
  }) = _Location;

  factory Location.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$LocationFromJson(json);
}

@freezed
class TenantBoundary with _$TenantBoundary {
  const factory TenantBoundary({
    @JsonKey(name: 'boundary') List<WardBoundary>? boundaryList,
  }) = _TenantBoundary;

  factory TenantBoundary.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$TenantBoundaryFromJson(json);
}

@freezed
class WardBoundary with _$WardBoundary {
  const factory WardBoundary({
    @JsonKey(name: 'code') String? code,
    @JsonKey(name: 'name') String? name,
    @JsonKey(name: 'children') List<LocalityChild>? localityChildren,
  }) = _WardBoundary;

  factory WardBoundary.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WardBoundaryFromJson(json);
}

@freezed
class LocalityChild with _$LocalityChild {
  const factory LocalityChild({
    @JsonKey(name: 'code') String? code,
    @JsonKey(name: 'name') String? name,
    @JsonKey(name: 'latitude') String? latitude,
    @JsonKey(name: 'longitude') String? longitude,
  }) = _LocalityChild;

  factory LocalityChild.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$LocalityChildFromJson(json);
}
