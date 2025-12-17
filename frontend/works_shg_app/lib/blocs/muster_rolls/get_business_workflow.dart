import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/common_repository/common_repository.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../models/muster_rolls/business_service_workflow.dart';
import '../../utils/global_variables.dart';

part 'get_business_workflow.freezed.dart';

typedef BusinessWorkflowEmitter = Emitter<BusinessGetWorkflowState>;

class BusinessWorkflowBloc
    extends Bloc<BusinessWorkflowEvent, BusinessGetWorkflowState> {
  BusinessWorkflowBloc() : super(const BusinessGetWorkflowState.initial()) {
    on<GetBusinessWorkflowEvent>(_onGetWorkflow);
    on<DisposeBusinessWorkflowEvent>(_onDispose);
  }

  FutureOr<void> _onGetWorkflow(
      GetBusinessWorkflowEvent event, BusinessWorkflowEmitter emit) async {
    Client client = Client();
    try {
      emit(const BusinessGetWorkflowState.loading());
      BusinessServiceWorkflowModel businessWorkFlowModel =
          await CommonRepository(client.init()).getBusinessWorkflow(
              url: Urls.commonServices.businessWorkflow,
              options:
                  Options(extra: {"accessToken": GlobalVariables.authToken}),
              queryParameters: {
            "tenantId": event.tenantId,
            "businessServices": event.businessService,
          });
      await Future.delayed(const Duration(seconds: 2));
      emit(BusinessGetWorkflowState.loaded(
        businessWorkFlowModel: businessWorkFlowModel,
      ));
    } on DioException catch (e) {
      emit(const BusinessGetWorkflowState.error());
    }
  }

  FutureOr<void> _onDispose(
      DisposeBusinessWorkflowEvent event, BusinessWorkflowEmitter emit) async {
    emit(const BusinessGetWorkflowState.initial());
  }
}

@freezed
class BusinessWorkflowEvent with _$BusinessWorkflowEvent {
  const factory BusinessWorkflowEvent.get(
      {required String tenantId,
      required String businessService}) = GetBusinessWorkflowEvent;
  const factory BusinessWorkflowEvent.dispose() = DisposeBusinessWorkflowEvent;
}

@freezed
class BusinessGetWorkflowState with _$BusinessGetWorkflowState {
  const BusinessGetWorkflowState._();
  const factory BusinessGetWorkflowState.initial() = _Initial;
  const factory BusinessGetWorkflowState.loading() = _Loading;
  const factory BusinessGetWorkflowState.loaded(
      {BusinessServiceWorkflowModel? businessWorkFlowModel}) = _Loaded;
  const factory BusinessGetWorkflowState.error() = _Error;
}
