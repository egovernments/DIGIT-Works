import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/work_order_repository/my_works_repository.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../models/works/contracts_model.dart';

part 'accept_work_order.freezed.dart';

typedef AcceptWorkOrderEmitter = Emitter<AcceptWorkOrderState>;

class AcceptWorkOrderBloc
    extends Bloc<AcceptWorkOrderEvent, AcceptWorkOrderState> {
  AcceptWorkOrderBloc() : super(const AcceptWorkOrderState.initial()) {
    on<WorkOrderAcceptEvent>(_onAccept);
  }

  FutureOr<void> _onAccept(
      WorkOrderAcceptEvent event, AcceptWorkOrderEmitter emit) async {
    Client client = Client();
    try {
      emit(const AcceptWorkOrderState.loading());

      ContractsModel contractsModel =
          await MyWorksRepository(client.init()).acceptOrDeclineWorkOrder(
              url: Urls.workServices.updateWorkOrder,
              body: {
                "contract": event.contractsModel,
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
      emit(AcceptWorkOrderState.loaded(contractsModel));
    } on DioError catch (e) {
      emit(AcceptWorkOrderState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class AcceptWorkOrderEvent with _$AcceptWorkOrderEvent {
  const factory AcceptWorkOrderEvent.search(
      {required Map? contractsModel,
      required String action,
      @Default('') String comments}) = WorkOrderAcceptEvent;
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
