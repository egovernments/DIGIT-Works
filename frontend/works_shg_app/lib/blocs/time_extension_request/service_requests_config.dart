import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../utils/global_variables.dart';
import '../../data/remote_client.dart';
import '../../data/repositories/common_repository/common_repository.dart';
import '../../models/screen_config/home_screen_config.dart';
import '../../models/works/my_works_search_criteria.dart';

part 'service_requests_config.freezed.dart';

typedef ServiceRequestsConfigBlocEmitter
    = Emitter<ServiceRequestsConfigBlocState>;

class ServiceRequestsConfigBloc extends Bloc<ServiceRequestsConfigBlocEvent,
    ServiceRequestsConfigBlocState> {
  ServiceRequestsConfigBloc()
      : super(const ServiceRequestsConfigBlocState.initial()) {
    on<GetServiceRequestsConfigEvent>(_getServiceRequestsConfig);
  }

  FutureOr<void> _getServiceRequestsConfig(
    GetServiceRequestsConfigEvent event,
    ServiceRequestsConfigBlocEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const ServiceRequestsConfigBlocState.loading());
      HomeScreenConfigModel configModel = await CommonRepository(client.init())
          .getHomeConfig(
              apiEndPoint: Urls.initServices.mdms,
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {"name": "CBOMyServiceRequests"}
              ],
            }
          ]);

      emit(ServiceRequestsConfigBlocState.loaded(
          configModel.commonUiConfig?.cboMyServiceRequestsConfig?.first));
    } on DioException catch (e) {
      emit(ServiceRequestsConfigBlocState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class ServiceRequestsConfigBlocEvent with _$ServiceRequestsConfigBlocEvent {
  const factory ServiceRequestsConfigBlocEvent.getSearchCriteria() =
      GetServiceRequestsConfigEvent;
}

@freezed
class ServiceRequestsConfigBlocState with _$ServiceRequestsConfigBlocState {
  const ServiceRequestsConfigBlocState._();
  const factory ServiceRequestsConfigBlocState.initial() = _Initial;
  const factory ServiceRequestsConfigBlocState.loading() = _Loading;
  const factory ServiceRequestsConfigBlocState.loaded(
    final CBOMyServiceRequestsConfig? cboMyServiceRequestsConfig,
  ) = _Loaded;
  const factory ServiceRequestsConfigBlocState.error(String? error) = _Error;
}
