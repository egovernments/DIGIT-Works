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

part 'create_time_extension_request.freezed.dart';

typedef CreateTimeExtensionRequestEmitter
    = Emitter<CreateTimeExtensionRequestState>;

class CreateTimeExtensionRequestBloc extends Bloc<
    CreateTimeExtensionRequestEvent, CreateTimeExtensionRequestState> {
  CreateTimeExtensionRequestBloc()
      : super(const CreateTimeExtensionRequestState.initial()) {
    on<TimeExtensionRequestEvent>(_onAccept);
    on<TimeExtensionRequestDisposeEvent>(_onDispose);
  }

  FutureOr<void> _onAccept(TimeExtensionRequestEvent event,
      CreateTimeExtensionRequestEmitter emit) async {
    Client client = Client();
    try {
      emit(const CreateTimeExtensionRequestState.loading());
      Map? contract = {
        ...?event.contractsModel?.toMap(),
        'businessService': 'CONTRACT-REVISION',
        'additionalDetails': {
          ...?event.contractsModel?.additionalDetails?.toMap(),
          'timeExt': event.extensionDays,
          'timeExtReason': event.reason
        },
        'endDate': event.extensionDate
      };

      ContractsModel timeExtensions =
          await MyWorksRepository(client.init()).updateOrCreateContract(
              url: event.isEdit
                  ? Urls.workServices.updateWorkOrder
                  : Urls.workServices.createWorkOrder,
              body: {
                "contract": contract,
                "workflow": {"action": event.action, "assignees": []}
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "mukta-services",
                "msgId": "Create Contract"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(CreateTimeExtensionRequestState.loaded(timeExtensions));
    } on DioException catch (e) {
      emit(CreateTimeExtensionRequestState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(TimeExtensionRequestDisposeEvent event,
      CreateTimeExtensionRequestEmitter emit) async {
    emit(const CreateTimeExtensionRequestState.initial());
    emit(const CreateTimeExtensionRequestState.loaded(null));
  }
}

@freezed
class CreateTimeExtensionRequestEvent with _$CreateTimeExtensionRequestEvent {
  const factory CreateTimeExtensionRequestEvent.search(
      {required Contracts? contractsModel,
      required String extensionDays,
      required int extensionDate,
      required String action,
      required bool isEdit,
      @Default('') String reason}) = TimeExtensionRequestEvent;
  const factory CreateTimeExtensionRequestEvent.dispose() =
      TimeExtensionRequestDisposeEvent;
}

@freezed
class CreateTimeExtensionRequestState with _$CreateTimeExtensionRequestState {
  const CreateTimeExtensionRequestState._();

  const factory CreateTimeExtensionRequestState.initial() = _Initial;
  const factory CreateTimeExtensionRequestState.loading() = _Loading;
  const factory CreateTimeExtensionRequestState.loaded(
      ContractsModel? contractsModel) = _Loaded;
  const factory CreateTimeExtensionRequestState.error(String? error) = _Error;
}
