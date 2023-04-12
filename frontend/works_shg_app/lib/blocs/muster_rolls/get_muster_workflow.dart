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
      await Future.delayed(const Duration(seconds: 2));
      emit(MusterGetWorkflowState.loaded(
          musterWorkFlowModel: musterWorkFlowModel,
          isInWorkflow: !(musterWorkFlowModel.processInstances!.isNotEmpty &&
              musterWorkFlowModel.processInstances?.first.workflowState
                      ?.applicationStatus ==
                  'REJECTED')));
    } on DioError catch (e) {
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
  const factory MusterGetWorkflowEvent.get({
    required String tenantId,
    required String musterRollNumber,
  }) = GetMusterWorkflowEvent;
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
      @Default(false) bool isInWorkflow}) = _Loaded;
  const factory MusterGetWorkflowState.error() = _Error;

  // const factory MusterGetWorkflowState({
  //   @Default(false) bool loading,
  //   MusterWorkFlowModel? musterWorkFlowModel,
  // }) = _MusterGetWorkflowState;
}
