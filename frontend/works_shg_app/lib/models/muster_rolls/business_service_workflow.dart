import 'package:freezed_annotation/freezed_annotation.dart';

part 'business_service_workflow.freezed.dart';
part 'business_service_workflow.g.dart';

@freezed
class BusinessServiceWorkflowModel with _$BusinessServiceWorkflowModel {
  const factory BusinessServiceWorkflowModel({
    @JsonKey(name: 'BusinessServices') List<BusinessServices>? businessServices,
  }) = _BusinessServiceWorkflowModel;

  factory BusinessServiceWorkflowModel.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$BusinessServiceWorkflowModelFromJson(json);
}

@freezed
class BusinessServices with _$BusinessServices {
  const factory BusinessServices({
    required String tenantId,
    required String uuid,
    String? businessService,
    String? business,
    int? businessServiceSla,
    @JsonKey(name: 'states') List<BusinessWorkflowState>? workflowState,
  }) = _BusinessServices;

  factory BusinessServices.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$BusinessServicesFromJson(json);
}

@freezed
class BusinessWorkflowState with _$BusinessWorkflowState {
  const factory BusinessWorkflowState({
    required String tenantId,
    String? businessServiceId,
    String? applicationStatus,
    String? state,
    bool? isStartState,
    bool? isTerminateState,
    bool? isStateUpdatable,
    List<StateActions>? actions,
  }) = _BusinessWorkflowState;

  factory BusinessWorkflowState.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$BusinessWorkflowStateFromJson(json);
}

@freezed
class StateActions with _$StateActions {
  const factory StateActions({
    required String tenantId,
    required String uuid,
    String? currentState,
    String? action,
    String? state,
    String? nextState,
    List<String>? roles,
  }) = _StateActions;

  factory StateActions.fromJson(
    Map<String, dynamic> json,
  ) =>
      _$StateActionsFromJson(json);
}
