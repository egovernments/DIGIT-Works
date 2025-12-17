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

part 'decline_work_order.freezed.dart';

typedef DeclineWorkOrderEmitter = Emitter<DeclineWorkOrderState>;

class DeclineWorkOrderBloc
    extends Bloc<DeclineWorkOrderEvent, DeclineWorkOrderState> {
  DeclineWorkOrderBloc() : super(const DeclineWorkOrderState.initial()) {
    on<WorkOrderDeclineEvent>(_onDecline);
    on<DisposeDeclineEvent>(_onDisposeDecline);
  }

  FutureOr<void> _onDisposeDecline(
      DisposeDeclineEvent event, DeclineWorkOrderEmitter emit) async {
    emit(const DeclineWorkOrderState.initial());
  }

  FutureOr<void> _onDecline(
      WorkOrderDeclineEvent event, DeclineWorkOrderEmitter emit) async {
    Client client = Client();
    try {
      emit(const DeclineWorkOrderState.loading());

      ContractsModel contractsModel =
          await MyWorksRepository(client.init()).updateOrCreateContract(
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
      emit(DeclineWorkOrderState.loaded(contractsModel));
    } on DioException catch (e) {
      emit(DeclineWorkOrderState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class DeclineWorkOrderEvent with _$DeclineWorkOrderEvent {
  const factory DeclineWorkOrderEvent.search(
      {required Map<String, dynamic>? contractsModel,
      required String action,
      @Default('') String comments}) = WorkOrderDeclineEvent;
  const factory DeclineWorkOrderEvent.dispose() = DisposeDeclineEvent;
}

@freezed
class DeclineWorkOrderState with _$DeclineWorkOrderState {
  const DeclineWorkOrderState._();

  const factory DeclineWorkOrderState.initial() = _Initial;
  const factory DeclineWorkOrderState.loading() = _Loading;
  const factory DeclineWorkOrderState.loaded(ContractsModel? contractsModel) =
      _Loaded;
  const factory DeclineWorkOrderState.error(String? error) = _Error;
}
