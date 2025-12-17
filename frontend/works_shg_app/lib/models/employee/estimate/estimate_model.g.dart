// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'estimate_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$EstimateDetailResponseImpl _$$EstimateDetailResponseImplFromJson(
        Map<String, dynamic> json) =>
    _$EstimateDetailResponseImpl(
      estimates: (json['estimates'] as List<dynamic>?)
          ?.map((e) => Estimate.fromJson(e as Map<String, dynamic>))
          .toList(),
      totalCount: (json['TotalCount'] as num?)?.toInt(),
    );

Map<String, dynamic> _$$EstimateDetailResponseImplToJson(
        _$EstimateDetailResponseImpl instance) =>
    <String, dynamic>{
      'estimates': instance.estimates,
      'TotalCount': instance.totalCount,
    };
