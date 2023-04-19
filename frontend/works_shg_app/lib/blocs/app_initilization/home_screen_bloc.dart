import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/common_repository/common_repository.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../utils/global_variables.dart';
import '../../data/remote_client.dart';
import '../../models/screen_config/home_screen_config.dart';

part 'home_screen_bloc.freezed.dart';

typedef HomeScreenBlocEmitter = Emitter<HomeScreenBlocState>;

class HomeScreenBloc extends Bloc<HomeScreenBlocEvent, HomeScreenBlocState> {
  HomeScreenBloc() : super(const HomeScreenBlocState.initial()) {
    on<GetHomeScreenConfigEvent>(_getHomeScreenConfig);
  }

  FutureOr<void> _getHomeScreenConfig(
    GetHomeScreenConfigEvent event,
    HomeScreenBlocEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const HomeScreenBlocState.loading());
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
                {
                  "name": "CBOHomeScreenConfig",
                  "filter": "[?(@.active==true)]"
                },
              ],
            }
          ]);

      List<CBOHomeScreenConfigModel>? cboHomeScreenConfig =
          configModel.commonUiConfig?.cboHomeScreenConfig;
      cboHomeScreenConfig = cboHomeScreenConfig?.toList()
        ?..sort((a, b) => a.order!.compareTo(b.order!.toInt()));

      emit(HomeScreenBlocState.loaded(cboHomeScreenConfig));
    } on DioError catch (e) {
      emit(HomeScreenBlocState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class HomeScreenBlocEvent with _$HomeScreenBlocEvent {
  const factory HomeScreenBlocEvent.getHomeSvreenConfig() =
      GetHomeScreenConfigEvent;
}

@freezed
class HomeScreenBlocState with _$HomeScreenBlocState {
  const HomeScreenBlocState._();
  const factory HomeScreenBlocState.initial() = _Initial;
  const factory HomeScreenBlocState.loading() = _Loading;
  const factory HomeScreenBlocState.loaded(
      List<CBOHomeScreenConfigModel>? cboHomeScreenConfig) = _Loaded;
  const factory HomeScreenBlocState.error(String? error) = _Error;
}
