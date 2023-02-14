// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_workflow_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_MusterWorkFlowModel _$$_MusterWorkFlowModelFromJson(
        Map<String, dynamic> json) =>
    _$_MusterWorkFlowModel(
      processInstances: (json['ProcessInstances'] as List<dynamic>?)
          ?.map((e) => ProcessInstances.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_MusterWorkFlowModelToJson(
        _$_MusterWorkFlowModel instance) =>
    <String, dynamic>{
      'ProcessInstances': instance.processInstances,
    };

_$_ProcessInstances _$$_ProcessInstancesFromJson(Map<String, dynamic> json) =>
    _$_ProcessInstances(
      tenantId: json['tenantId'] as String?,
      businessService: json['businessService'] as String?,
      id: json['id'] as String?,
      businessId: json['businessId'] as String?,
      action: json['action'] as String?,
      workflowState: json['state'] == null
          ? null
          : WorkflowState.fromJson(json['state'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$_ProcessInstancesToJson(_$_ProcessInstances instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'businessService': instance.businessService,
      'id': instance.id,
      'businessId': instance.businessId,
      'action': instance.action,
      'state': instance.workflowState,
    };

_$_WorkflowState _$$_WorkflowStateFromJson(Map<String, dynamic> json) =>
    _$_WorkflowState(
      tenantId: json['tenantId'] as String?,
      businessServiceId: json['businessServiceId'] as String?,
      applicationStatus: json['applicationStatus'] as String?,
      state: json['state'] as String?,
      isStartState: json['isStartState'] as bool?,
      isTerminateState: json['isTerminateState'] as bool?,
      isStateUpdatable: json['isStateUpdatable'] as bool?,
    );

Map<String, dynamic> _$$_WorkflowStateToJson(_$_WorkflowState instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'businessServiceId': instance.businessServiceId,
      'applicationStatus': instance.applicationStatus,
      'state': instance.state,
      'isStartState': instance.isStartState,
      'isTerminateState': instance.isTerminateState,
      'isStateUpdatable': instance.isStateUpdatable,
    };
