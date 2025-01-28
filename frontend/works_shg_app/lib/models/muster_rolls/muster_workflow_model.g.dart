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
      tenantId: json['tenantId'] as String,
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
      documents: (json['documents'] as List<dynamic>?)
          ?.map((e) => WorkflowDocument.fromJson(e as Map<String, dynamic>))
          .toList(),
      comment: json['comment'] as String?,
      nextActions: (json['nextActions'] as List<dynamic>?)
          ?.map((e) => NextActions.fromJson(e as Map<String, dynamic>))
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
      'documents': instance.documents,
      'comment': instance.comment,
      'nextActions': instance.nextActions,
      'state': instance.workflowState,
    };

_$_NextActions _$$_NextActionsFromJson(Map<String, dynamic> json) =>
    _$_NextActions(
      action: json['action'] as String?,
    );

Map<String, dynamic> _$$_NextActionsToJson(_$_NextActions instance) =>
    <String, dynamic>{
      'action': instance.action,
    };

_$_WorkflowDocument _$$_WorkflowDocumentFromJson(Map<String, dynamic> json) =>
    _$_WorkflowDocument(
      documentType: json['documentType'] as String?,
      documentUid: json['documentUid'] as String?,
      fileStoreId: json['fileStoreId'] as String?,
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
    );

Map<String, dynamic> _$$_WorkflowDocumentToJson(_$_WorkflowDocument instance) =>
    <String, dynamic>{
      'documentType': instance.documentType,
      'documentUid': instance.documentUid,
      'fileStoreId': instance.fileStoreId,
      'id': instance.id,
      'tenantId': instance.tenantId,
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
      tenantId: json['tenantId'] as String,
      businessServiceId: json['businessServiceId'] as String?,
      applicationStatus: json['applicationStatus'] as String?,
      state: json['state'] as String?,
      isStartState: json['isStartState'] as bool?,
      isTerminateState: json['isTerminateState'] as bool?,
      actions: (json['actions'] as List<dynamic>?)
          ?.map((e) => WorkflowActions.fromJson(e as Map<String, dynamic>))
          .toList(),
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
      'actions': instance.actions,
      'isStateUpdatable': instance.isStateUpdatable,
    };

_$_WorkflowActions _$$_WorkflowActionsFromJson(Map<String, dynamic> json) =>
    _$_WorkflowActions(
      roles:
          (json['roles'] as List<dynamic>?)?.map((e) => e as String).toList(),
    );

Map<String, dynamic> _$$_WorkflowActionsToJson(_$_WorkflowActions instance) =>
    <String, dynamic>{
      'roles': instance.roles,
    };
