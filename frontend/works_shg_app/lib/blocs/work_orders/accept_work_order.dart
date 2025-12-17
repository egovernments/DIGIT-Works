import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/work_order_repository/my_works_repository.dart';
import '../../models/works/contracts_model.dart';
import '../../services/urls.dart';
import '../../utils/global_variables.dart';

part 'accept_work_order.freezed.dart';

typedef AcceptWorkOrderEmitter = Emitter<AcceptWorkOrderState>;

class AcceptWorkOrderBloc
    extends Bloc<AcceptWorkOrderEvent, AcceptWorkOrderState> {
  AcceptWorkOrderBloc() : super(const AcceptWorkOrderState.initial()) {
    on<WorkOrderAcceptEvent>(_onAccept);
    on<WorkOrderDisposeEvent>(_onDispose);
  }

  FutureOr<void> _onAccept(
      WorkOrderAcceptEvent event, AcceptWorkOrderEmitter emit) async {
    Client client = Client();
    try {
      emit(const AcceptWorkOrderState.loading());

      DateTime startOfToday = DateTime(
          DateTime.now().year, DateTime.now().month, DateTime.now().day);
      int startOfTodayTimestamp = startOfToday.millisecondsSinceEpoch;
      Map? contract = {
        ...?event.contractsModel,
        'startDate': startOfTodayTimestamp
      };

      ContractsModel acceptedContracts =
          await MyWorksRepository(client.init()).updateOrCreateContract(
              url: Urls.workServices.updateWorkOrder,
              body: {
                "contract": contract,
                "workflow": {
                  "action": event.action,
                  "comment": event.comments,
                  "assignees": []
                }
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "mukta-services",
                "msgId": "Create Contract"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(AcceptWorkOrderState.loaded(acceptedContracts));
    } on DioException catch (e) {
      emit(AcceptWorkOrderState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(
      WorkOrderDisposeEvent event, AcceptWorkOrderEmitter emit) async {
    emit(const AcceptWorkOrderState.initial());
  }
}

@freezed
class AcceptWorkOrderEvent with _$AcceptWorkOrderEvent {
  const factory AcceptWorkOrderEvent.search(
      {required Map? contractsModel,
      required String action,
      @Default('') String comments}) = WorkOrderAcceptEvent;
  const factory AcceptWorkOrderEvent.dispose() = WorkOrderDisposeEvent;
}

@freezed
class AcceptWorkOrderState with _$AcceptWorkOrderState {
  const AcceptWorkOrderState._();

  const factory AcceptWorkOrderState.initial() = _Initial;
  const factory AcceptWorkOrderState.loading() = _Loading;
  const factory AcceptWorkOrderState.loaded(ContractsModel? contractsModel) =
      _Loaded;
  const factory AcceptWorkOrderState.error(String? error) = _Error;
}
