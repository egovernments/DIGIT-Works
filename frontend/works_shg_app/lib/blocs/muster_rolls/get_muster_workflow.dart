import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/workflow_repository/workflow.dart';
import '../../models/muster_rolls/muster_workflow_model.dart';
import '../../utils/global_variables.dart';

part 'get_muster_workflow.freezed.dart';

typedef MusterGetWorkflowEmitter = Emitter<MusterGetWorkflowState>;

class MusterGetWorkflowBloc
    extends Bloc<MusterGetWorkflowEvent, MusterGetWorkflowState> {
  MusterGetWorkflowBloc() : super(const MusterGetWorkflowState.initial()) {
    on<GetMusterWorkflowEvent>(_onGetWorkflow);
    on<FetchMBWorkFlowEvent>(_onGetMBWorkflow);
    on<DisposeMusterRollWorkflowEvent>(_onDispose);
  }

  FutureOr<void> _onGetWorkflow(
      GetMusterWorkflowEvent event, MusterGetWorkflowEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterGetWorkflowState.loading());
      MusterWorkFlowModel musterWorkFlowModel =
          await WorkFlowRepository(client.init()).getWorkFlow(
              url: Urls.commonServices.workflow,
              options:
                  Options(extra: {"accessToken": GlobalVariables.authToken}),
              queryParameters: {
            "history": "true",
            "tenantId": event.tenantId,
            "businessServices": "muster-roll-approval",
            "businessIds": event.musterRollNumber,
          });
      WorkflowState? state =
          musterWorkFlowModel.processInstances?.first.workflowState;
      await Future.delayed(const Duration(seconds: 2));

      emit(MusterGetWorkflowState.loaded(
          musterWorkFlowModel: musterWorkFlowModel,
          isInWorkflow: !(musterWorkFlowModel.processInstances!.isNotEmpty &&
              state!.state == event.musterSentBackCode)));
    } on DioException catch (e) {
      emit(const MusterGetWorkflowState.error());
    }
  }
  // mb 
  FutureOr<void> _onGetMBWorkflow(
      FetchMBWorkFlowEvent event, MusterGetWorkflowEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterGetWorkflowState.initial());
      emit(const MusterGetWorkflowState.loading());
      MusterWorkFlowModel musterWorkFlowModel =
          await WorkFlowRepository(client.init()).getWorkFlow(
              url: Urls.commonServices.workflow,
              options:
                  Options(extra: {"accessToken": GlobalVariables.authToken}),
              queryParameters: {
            "history": "true",
            "tenantId": event.tenantId,
          
            "businessIds": event.mbNumber,
          });
      WorkflowState? state =
          musterWorkFlowModel.processInstances?.first.workflowState;
      

      emit(MusterGetWorkflowState.loaded(
          musterWorkFlowModel: musterWorkFlowModel,
          isInWorkflow: true));
    } on DioException catch (e) {
      emit(const MusterGetWorkflowState.error());
    }
  }


  FutureOr<void> _onDispose(DisposeMusterRollWorkflowEvent event,
      MusterGetWorkflowEmitter emit) async {
    emit(const MusterGetWorkflowState.initial());
  }
}

@freezed
class MusterGetWorkflowEvent with _$MusterGetWorkflowEvent {
  const factory MusterGetWorkflowEvent.get(
      {required String tenantId,
      required String musterRollNumber,
      required String musterSentBackCode}) = GetMusterWorkflowEvent;
const factory MusterGetWorkflowEvent.fetch(
      {required String tenantId,
      required String mbNumber,
     
      }) = FetchMBWorkFlowEvent;

  const factory MusterGetWorkflowEvent.dispose() =
      DisposeMusterRollWorkflowEvent;
}

@freezed
class MusterGetWorkflowState with _$MusterGetWorkflowState {
  const MusterGetWorkflowState._();
  const factory MusterGetWorkflowState.initial() = _Initial;
  const factory MusterGetWorkflowState.loading() = _Loading;
  const factory MusterGetWorkflowState.loaded(
      {MusterWorkFlowModel? musterWorkFlowModel,

      @Default(false) bool isInWorkflow,
      
      
      }) = _Loaded;
  const factory MusterGetWorkflowState.error() = _Error;

  // const factory MusterGetWorkflowState({
  //   @Default(false) bool loading,
  //   MusterWorkFlowModel? musterWorkFlowModel,
  // }) = _MusterGetWorkflowState;
}
