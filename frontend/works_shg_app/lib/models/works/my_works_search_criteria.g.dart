// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'my_works_search_criteria.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MyWorksSearchCriteriaModelImpl _$$MyWorksSearchCriteriaModelImplFromJson(
        Map<String, dynamic> json) =>
    _$MyWorksSearchCriteriaModelImpl(
      commonUiConfig: json['commonUiConfig'] == null
          ? null
          : CommonUIConfigModel.fromJson(
              json['commonUiConfig'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$MyWorksSearchCriteriaModelImplToJson(
        _$MyWorksSearchCriteriaModelImpl instance) =>
    <String, dynamic>{
      'commonUiConfig': instance.commonUiConfig,
    };

_$CBOMyWorksSearchCriteriaModelImpl
    _$$CBOMyWorksSearchCriteriaModelImplFromJson(Map<String, dynamic> json) =>
        _$CBOMyWorksSearchCriteriaModelImpl(
          searchCriteria: (json['searchCriteria'] as List<dynamic>?)
              ?.map((e) => e as String)
              .toList(),
          acceptCode: json['acceptCode'] as String?,
        );

Map<String, dynamic> _$$CBOMyWorksSearchCriteriaModelImplToJson(
        _$CBOMyWorksSearchCriteriaModelImpl instance) =>
    <String, dynamic>{
      'searchCriteria': instance.searchCriteria,
      'acceptCode': instance.acceptCode,
    };

_$CBOMyServiceRequestsConfigImpl _$$CBOMyServiceRequestsConfigImplFromJson(
        Map<String, dynamic> json) =>
    _$CBOMyServiceRequestsConfigImpl(
      editTimeExtReqCode: json['editTimeExtReqCode'] as String?,
      editActionCode: json['editActionCode'] as String?,
      searchCriteria: json['searchCriteria'] as String?,
    );

Map<String, dynamic> _$$CBOMyServiceRequestsConfigImplToJson(
        _$CBOMyServiceRequestsConfigImpl instance) =>
    <String, dynamic>{
      'editTimeExtReqCode': instance.editTimeExtReqCode,
      'editActionCode': instance.editActionCode,
      'searchCriteria': instance.searchCriteria,
    };
