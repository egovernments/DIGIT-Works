// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'muster_workflow_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$MusterWorkFlowModelImpl _$$MusterWorkFlowModelImplFromJson(
        Map<String, dynamic> json) =>
    _$MusterWorkFlowModelImpl(
      processInstances: (json['ProcessInstances'] as List<dynamic>?)
          ?.map((e) => ProcessInstances.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$MusterWorkFlowModelImplToJson(
        _$MusterWorkFlowModelImpl instance) =>
    <String, dynamic>{
      'ProcessInstances': instance.processInstances,
    };

_$ProcessInstancesImpl _$$ProcessInstancesImplFromJson(
        Map<String, dynamic> json) =>
    _$ProcessInstancesImpl(
      tenantId: json['tenantId'] as String,
      businessService: json['businessService'] as String?,
      id: json['id'] as String?,
      businessId: json['businessId'] as String?,
      action: json['action'] as String?,
      auditDetails: json['auditDetails'] == null
          ? null
          : AuditDetails.fromJson(json['auditDetails'] as Map<String, dynamic>),
      assigner: json['assigner'] == null
          ? null
          : Assigner.fromJson(json['assigner'] as Map<String, dynamic>),
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

Map<String, dynamic> _$$ProcessInstancesImplToJson(
        _$ProcessInstancesImpl instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'businessService': instance.businessService,
      'id': instance.id,
      'businessId': instance.businessId,
      'action': instance.action,
      'auditDetails': instance.auditDetails,
      'assigner': instance.assigner,
      'assignes': instance.assignes,
      'documents': instance.documents,
      'comment': instance.comment,
      'nextActions': instance.nextActions,
      'state': instance.workflowState,
    };

_$NextActionsImpl _$$NextActionsImplFromJson(Map<String, dynamic> json) =>
    _$NextActionsImpl(
      action: json['action'] as String?,
      uuid: json['uuid'] as String?,
      currentState: json['currentState'] as String?,
      nextState: json['nextState'] as String?,
      tenantId: json['tenantId'] as String?,
      roles:
          (json['roles'] as List<dynamic>?)?.map((e) => e as String).toList(),
    );

Map<String, dynamic> _$$NextActionsImplToJson(_$NextActionsImpl instance) =>
    <String, dynamic>{
      'action': instance.action,
      'uuid': instance.uuid,
      'currentState': instance.currentState,
      'nextState': instance.nextState,
      'tenantId': instance.tenantId,
      'roles': instance.roles,
    };

_$WorkflowDocumentImpl _$$WorkflowDocumentImplFromJson(
        Map<String, dynamic> json) =>
    _$WorkflowDocumentImpl(
      documentType: json['documentType'] as String?,
      documentUid: json['documentUid'] as String?,
      fileStoreId: json['fileStoreId'] as String?,
      id: json['id'] as String?,
      tenantId: json['tenantId'] as String?,
      fileStore: json['fileStore'] as String?,
      isActive: json['isActive'] as bool?,
      indexing: (json['indexing'] as num?)?.toInt(),
      documentAdditionalDetails: json['additionalDetails'] == null
          ? null
          : DocumentAdditionalDetails.fromJson(
              json['additionalDetails'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$$WorkflowDocumentImplToJson(
        _$WorkflowDocumentImpl instance) =>
    <String, dynamic>{
      'documentType': instance.documentType,
      'documentUid': instance.documentUid,
      'fileStoreId': instance.fileStoreId,
      'id': instance.id,
      'tenantId': instance.tenantId,
      'fileStore': instance.fileStore,
      'isActive': instance.isActive,
      'indexing': instance.indexing,
      'additionalDetails': instance.documentAdditionalDetails,
    };

_$DocumentAdditionalDetailsImpl _$$DocumentAdditionalDetailsImplFromJson(
        Map<String, dynamic> json) =>
    _$DocumentAdditionalDetailsImpl(
      fileName: json['fileName'] as String?,
      fileType: json['fileType'] as String?,
      tenantId: json['tenantId'] as String?,
    );

Map<String, dynamic> _$$DocumentAdditionalDetailsImplToJson(
        _$DocumentAdditionalDetailsImpl instance) =>
    <String, dynamic>{
      'fileName': instance.fileName,
      'fileType': instance.fileType,
      'tenantId': instance.tenantId,
    };

_$AssigneesImpl _$$AssigneesImplFromJson(Map<String, dynamic> json) =>
    _$AssigneesImpl(
      emailId: json['emailId'] as String?,
      id: (json['id'] as num?)?.toInt(),
      mobileNumber: json['mobileNumber'] as String?,
      name: json['name'] as String?,
      tenantId: json['tenantId'] as String?,
      uuid: json['uuid'] as String?,
      userName: json['userName'] as String?,
    );

Map<String, dynamic> _$$AssigneesImplToJson(_$AssigneesImpl instance) =>
    <String, dynamic>{
      'emailId': instance.emailId,
      'id': instance.id,
      'mobileNumber': instance.mobileNumber,
      'name': instance.name,
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'userName': instance.userName,
    };

_$WorkflowStateImpl _$$WorkflowStateImplFromJson(Map<String, dynamic> json) =>
    _$WorkflowStateImpl(
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

Map<String, dynamic> _$$WorkflowStateImplToJson(_$WorkflowStateImpl instance) =>
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

_$WorkflowActionsImpl _$$WorkflowActionsImplFromJson(
        Map<String, dynamic> json) =>
    _$WorkflowActionsImpl(
      roles:
          (json['roles'] as List<dynamic>?)?.map((e) => e as String).toList(),
    );

Map<String, dynamic> _$$WorkflowActionsImplToJson(
        _$WorkflowActionsImpl instance) =>
    <String, dynamic>{
      'roles': instance.roles,
    };

_$AssignerImpl _$$AssignerImplFromJson(Map<String, dynamic> json) =>
    _$AssignerImpl(
      emailId: json['emailId'] as String?,
      id: (json['id'] as num?)?.toInt(),
      mobileNumber: json['mobileNumber'] as String?,
      name: json['name'] as String?,
      tenantId: json['tenantId'] as String?,
      uuid: json['uuid'] as String?,
      userName: json['userName'] as String?,
    );

Map<String, dynamic> _$$AssignerImplToJson(_$AssignerImpl instance) =>
    <String, dynamic>{
      'emailId': instance.emailId,
      'id': instance.id,
      'mobileNumber': instance.mobileNumber,
      'name': instance.name,
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'userName': instance.userName,
    };
