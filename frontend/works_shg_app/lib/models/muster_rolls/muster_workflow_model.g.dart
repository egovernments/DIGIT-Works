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
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      assignes: (json['assignes'] as List<dynamic>?)
          ?.map((e) => Assignees.fromJson(e as Map<String, dynamic>))
          .toList(),
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
      'auditDetails': instance.auditDetails,
      'assignes': instance.assignes,
      'state': instance.workflowState,
    };

_$_Assignees _$$_AssigneesFromJson(Map<String, dynamic> json) => _$_Assignees(
      emailId: json['emailId'] as String?,
      id: json['id'] as int?,
      mobileNumber: json['mobileNumber'] as String?,
      name: json['name'] as String?,
      tenantId: json['tenantId'] as String?,
      uuid: json['uuid'] as String?,
      userName: json['userName'] as String?,
    );

Map<String, dynamic> _$$_AssigneesToJson(_$_Assignees instance) =>
    <String, dynamic>{
      'emailId': instance.emailId,
      'id': instance.id,
      'mobileNumber': instance.mobileNumber,
      'name': instance.name,
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'userName': instance.userName,
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
