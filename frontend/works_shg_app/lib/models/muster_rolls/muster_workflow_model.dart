import 'package:freezed_annotation/freezed_annotation.dart';

part 'muster_workflow_model.freezed.dart';
part 'muster_workflow_model.g.dart';

@freezed
class MusterWorkFlowModel with _$MusterWorkFlowModel {
  const factory MusterWorkFlowModel({
    @JsonKey(name: 'ProcessInstances') List<ProcessInstances>? processInstances,
  }) = _MusterWorkFlowModel;

  factory MusterWorkFlowModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$MusterWorkFlowModelFromJson(json);
}

@freezed
class ProcessInstances with _$ProcessInstances {
  const factory ProcessInstances({
    String? tenantId,
    String? businessService,
    String? id,
    String? businessId,
    String? action,
    @JsonKey(name: 'state') WorkflowState? workflowState,
  }) = _ProcessInstances;

  factory ProcessInstances.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$ProcessInstancesFromJson(json);
}

@freezed
class WorkflowState with _$WorkflowState {
  const factory WorkflowState({
    String? tenantId,
    String? businessServiceId,
    String? applicationStatus,
    String? state,
    bool? isStartState,
    bool? isTerminateState,
    bool? isStateUpdatable,
  }) = _WorkflowState;

  factory WorkflowState.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WorkflowStateFromJson(json);
}
