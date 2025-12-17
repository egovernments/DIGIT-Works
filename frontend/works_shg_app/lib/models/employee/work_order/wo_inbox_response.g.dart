// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'wo_inbox_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$WOInboxResponseImpl _$$WOInboxResponseImplFromJson(
        Map<String, dynamic> json) =>
    _$WOInboxResponseImpl(
      totalCount: (json['totalCount'] as num?)?.toInt(),
      nearingSlaCount: (json['nearingSlaCount'] as num?)?.toInt(),
      statusMap: (json['statusMap'] as List<dynamic>?)
          ?.map((e) => StatusMap.fromJson(e as Map<String, dynamic>))
          .toList(),
      items: (json['items'] as List<dynamic>?)
          ?.map((e) => WOItemData.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$WOInboxResponseImplToJson(
        _$WOInboxResponseImpl instance) =>
    <String, dynamic>{
      'totalCount': instance.totalCount,
      'nearingSlaCount': instance.nearingSlaCount,
      'statusMap': instance.statusMap,
      'items': instance.items,
    };

_$WOItemDataImpl _$$WOItemDataImplFromJson(Map<String, dynamic> json) =>
    _$WOItemDataImpl(
      processInstance: json['ProcessInstance'] == null
          ? null
          : ProcessInstance.fromJson(
              json['ProcessInstance'] as Map<String, dynamic>),
      woBusinessObject: json['businessObject'] == null
          ? null
          : WOBusinessObject.fromJson(
              json['businessObject'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$WOItemDataImplToJson(_$WOItemDataImpl instance) =>
    <String, dynamic>{
      'ProcessInstance': instance.processInstance,
      'businessObject': instance.woBusinessObject,
    };

_$WOBusinessObjectImpl _$$WOBusinessObjectImplFromJson(
        Map<String, dynamic> json) =>
    _$WOBusinessObjectImpl(
      totalContractedAmount:
          (json['totalContractedAmount'] as num?)?.toDouble(),
      businessService: json['businessService'] as String?,
      contractNumber: json['contractNumber'] as String?,
      serviceSla: (json['serviceSla'] as num?)?.toInt(),
      woAdditionalDetails: json['additionalDetails'] == null
          ? null
          : WOAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$WOBusinessObjectImplToJson(
        _$WOBusinessObjectImpl instance) =>
    <String, dynamic>{
      'totalContractedAmount': instance.totalContractedAmount,
      'businessService': instance.businessService,
      'contractNumber': instance.contractNumber,
      'serviceSla': instance.serviceSla,
      'additionalDetails': instance.woAdditionalDetails,
      'auditDetails': instance.auditDetails,
    };

_$WOAdditionalDetailsImpl _$$WOAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$WOAdditionalDetailsImpl(
      orgName: json['orgName'] as String?,
      projectId: json['projectId'] as String?,
      projectName: json['projectName'] as String?,
    );

Map<String, dynamic> _$$WOAdditionalDetailsImplToJson(
        _$WOAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'orgName': instance.orgName,
      'projectId': instance.projectId,
      'projectName': instance.projectName,
    };
