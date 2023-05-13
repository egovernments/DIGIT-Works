// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'my_works_search_criteria.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MyWorksSearchCriteriaModel _$$_MyWorksSearchCriteriaModelFromJson(
        Map<String, dynamic> json) =>
    _$_MyWorksSearchCriteriaModel(
      commonUiConfig: json['commonUiConfig'] == null
          ? null
          : CommonUIConfigModel.fromJson(
              json['commonUiConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_MyWorksSearchCriteriaModelToJson(
        _$_MyWorksSearchCriteriaModel instance) =>
    <String, dynamic>{
      'commonUiConfig': instance.commonUiConfig,
    };

_$_CBOMyWorksSearchCriteriaModel _$$_CBOMyWorksSearchCriteriaModelFromJson(
        Map<String, dynamic> json) =>
    _$_CBOMyWorksSearchCriteriaModel(
      searchCriteria: (json['searchCriteria'] as List<dynamic>?)
          ?.map((e) => e as String)
          .toList(),
      acceptCode: json['acceptCode'] as String?,
    );

Map<String, dynamic> _$$_CBOMyWorksSearchCriteriaModelToJson(
        _$_CBOMyWorksSearchCriteriaModel instance) =>
    <String, dynamic>{
      'searchCriteria': instance.searchCriteria,
      'acceptCode': instance.acceptCode,
    };
