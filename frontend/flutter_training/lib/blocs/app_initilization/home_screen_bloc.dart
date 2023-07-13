import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/Env/env_config.dart';
import 'package:flutter_training/data/repositories/common_repository/common_repository.dart';
import 'package:flutter_training/services/urls.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

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
              tenantId: envConfig.variables.tenantId.toString(),
              moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {
                  "name": "HomeScreenCardConfig",
                  "filter": "[?(@.active==true)]"
                },
              ],
            }
          ]);

      List<HomeScreenCardConfigModel>? homeScreenConfig =
          configModel.commonUiConfig?.homeScreenCardConfig;
      homeScreenConfig = homeScreenConfig?.toList()
        ?..sort((a, b) => a.order.compareTo(b.order.toInt()));

      emit(HomeScreenBlocState.loaded(homeScreenConfig));
    } on DioError catch (e) {
      emit(HomeScreenBlocState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class HomeScreenBlocEvent with _$HomeScreenBlocEvent {
  const factory HomeScreenBlocEvent.getHomeScreenConfig() =
      GetHomeScreenConfigEvent;
}

@freezed
class HomeScreenBlocState with _$HomeScreenBlocState {
  const HomeScreenBlocState._();
  const factory HomeScreenBlocState.initial() = _Initial;
  const factory HomeScreenBlocState.loading() = _Loading;
  const factory HomeScreenBlocState.loaded(
      List<HomeScreenCardConfigModel>? homeScreenCardConfig) = _Loaded;
  const factory HomeScreenBlocState.error(String? error) = _Error;
}
