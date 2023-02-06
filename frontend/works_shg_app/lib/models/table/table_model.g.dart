// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'table_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_TableDataModel _$$_TableDataModelFromJson(Map<String, dynamic> json) =>
    _$_TableDataModel(
      name: json['name'] as String?,
      aadhaar: json['aadhaar'] as String?,
      bankNumber: json['bankNumber'] as String?,
      monIndex: (json['monIndex'] as num?)?.toDouble(),
      tueIndex: (json['tueIndex'] as num?)?.toDouble(),
      wedIndex: (json['wedIndex'] as num?)?.toDouble(),
      thursIndex: (json['thursIndex'] as num?)?.toDouble(),
      friIndex: (json['friIndex'] as num?)?.toDouble(),
      satIndex: (json['satIndex'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$$_TableDataModelToJson(_$_TableDataModel instance) =>
    <String, dynamic>{
      'name': instance.name,
      'aadhaar': instance.aadhaar,
      'bankNumber': instance.bankNumber,
      'monIndex': instance.monIndex,
      'tueIndex': instance.tueIndex,
      'wedIndex': instance.wedIndex,
      'thursIndex': instance.thursIndex,
      'friIndex': instance.friIndex,
      'satIndex': instance.satIndex,
    };
