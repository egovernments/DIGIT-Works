import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';

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
    required String tenantId,
    String? businessService,
    String? id,
    String? businessId,
    String? action,
    AuditDetails? auditDetails,
    Assigner? assigner,
    List<Assignees>? assignes,
    List<WorkflowDocument>? documents,
    String? comment,
    List<NextActions>? nextActions,
    @JsonKey(name: 'state') WorkflowState? workflowState,
  }) = _ProcessInstances;

  factory ProcessInstances.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$ProcessInstancesFromJson(json);
}

@freezed
class NextActions with _$NextActions {
  const factory NextActions({
    String? action,
    String? uuid,
    String? currentState,
    String? nextState,
    String? tenantId,
    List<String>? roles,
  }) = _NextActions;

  factory NextActions.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$NextActionsFromJson(json);
}

@freezed
class WorkflowDocument with _$WorkflowDocument {
  const factory WorkflowDocument({
    String? documentType,
    String? documentUid,
    String? fileStoreId,
    String? id,
    String? tenantId,
    String? fileStore,
    bool? isActive,
    int? indexing,
    @JsonKey(name: 'additionalDetails')
        DocumentAdditionalDetails? documentAdditionalDetails,
  }) = _WorkflowDocument;

  factory WorkflowDocument.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WorkflowDocumentFromJson(json);
}

@freezed
class DocumentAdditionalDetails with _$DocumentAdditionalDetails {
  const factory DocumentAdditionalDetails({
    String? fileName,
    String? fileType,
    String? tenantId,
  }) = _DocumentAdditionalDetails;

  factory DocumentAdditionalDetails.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$DocumentAdditionalDetailsFromJson(json);
}

@freezed
class Assignees with _$Assignees {
  const factory Assignees({
    String? emailId,
    int? id,
    String? mobileNumber,
    String? name,
    String? tenantId,
    String? uuid,
    String? userName,
  }) = _Assignees;

  factory Assignees.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AssigneesFromJson(json);
}

@freezed
class WorkflowState with _$WorkflowState {
  const factory WorkflowState({
    required String tenantId,
    String? businessServiceId,
    String? applicationStatus,
    String? state,
    bool? isStartState,
    bool? isTerminateState,
    List<WorkflowActions>? actions,
    bool? isStateUpdatable,
  }) = _WorkflowState;

  factory WorkflowState.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WorkflowStateFromJson(json);
}

@freezed
class WorkflowActions with _$WorkflowActions {
  const factory WorkflowActions({List<String>? roles}) = _WorkflowActions;

  factory WorkflowActions.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$WorkflowActionsFromJson(json);
}



@freezed
class Assigner with _$Assigner {
  const factory Assigner({
    String? emailId,
    int? id,
    String? mobileNumber,
    String? name,
    String? tenantId,
    String? uuid,
    String? userName,
  }) = _Assigner;

  factory Assigner.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$AssignerFromJson(json);
}