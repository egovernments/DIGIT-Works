// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'business_service_workflow.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_$_BusinessServiceWorkflowModel _$$_BusinessServiceWorkflowModelFromJson(
        Map<String, dynamic> json) =>
    _$_BusinessServiceWorkflowModel(
      businessServices: (json['BusinessServices'] as List<dynamic>?)
          ?.map((e) => BusinessServices.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_BusinessServiceWorkflowModelToJson(
        _$_BusinessServiceWorkflowModel instance) =>
    <String, dynamic>{
      'BusinessServices': instance.businessServices,
    };

_$_BusinessServices _$$_BusinessServicesFromJson(Map<String, dynamic> json) =>
    _$_BusinessServices(
      tenantId: json['tenantId'] as String,
      uuid: json['uuid'] as String,
      businessService: json['businessService'] as String?,
      business: json['business'] as String?,
      businessServiceSla: json['businessServiceSla'] as int?,
      workflowState: (json['states'] as List<dynamic>?)
          ?.map(
              (e) => BusinessWorkflowState.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_BusinessServicesToJson(_$_BusinessServices instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'businessService': instance.businessService,
      'business': instance.business,
      'businessServiceSla': instance.businessServiceSla,
      'states': instance.workflowState,
    };

_$_BusinessWorkflowState _$$_BusinessWorkflowStateFromJson(
        Map<String, dynamic> json) =>
    _$_BusinessWorkflowState(
      tenantId: json['tenantId'] as String,
      businessServiceId: json['businessServiceId'] as String?,
      applicationStatus: json['applicationStatus'] as String?,
      state: json['state'] as String?,
      isStartState: json['isStartState'] as bool?,
      isTerminateState: json['isTerminateState'] as bool?,
      isStateUpdatable: json['isStateUpdatable'] as bool?,
      actions: (json['actions'] as List<dynamic>?)
          ?.map((e) => StateActions.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$$_BusinessWorkflowStateToJson(
        _$_BusinessWorkflowState instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'businessServiceId': instance.businessServiceId,
      'applicationStatus': instance.applicationStatus,
      'state': instance.state,
      'isStartState': instance.isStartState,
      'isTerminateState': instance.isTerminateState,
      'isStateUpdatable': instance.isStateUpdatable,
      'actions': instance.actions,
    };

_$_StateActions _$$_StateActionsFromJson(Map<String, dynamic> json) =>
    _$_StateActions(
      tenantId: json['tenantId'] as String,
      uuid: json['uuid'] as String,
      currentState: json['currentState'] as String?,
      action: json['action'] as String?,
      state: json['state'] as String?,
      nextState: json['nextState'] as String?,
      roles:
          (json['roles'] as List<dynamic>?)?.map((e) => e as String).toList(),
    );

Map<String, dynamic> _$$_StateActionsToJson(_$_StateActions instance) =>
    <String, dynamic>{
      'tenantId': instance.tenantId,
      'uuid': instance.uuid,
      'currentState': instance.currentState,
      'action': instance.action,
      'state': instance.state,
      'nextState': instance.nextState,
      'roles': instance.roles,
    };
