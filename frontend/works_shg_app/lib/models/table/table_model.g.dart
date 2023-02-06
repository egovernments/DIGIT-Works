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
      monIndex: json['monIndex'] as int?,
      tueIndex: json['tueIndex'] as int?,
      wedIndex: json['wedIndex'] as int?,
      thursIndex: json['thursIndex'] as int?,
      friIndex: json['friIndex'] as int?,
      satIndex: json['satIndex'] as int?,
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
