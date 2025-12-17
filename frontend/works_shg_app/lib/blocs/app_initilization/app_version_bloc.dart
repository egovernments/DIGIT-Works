import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/common_repository/common_repository.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../utils/global_variables.dart';
import '../../data/remote_client.dart';
import '../../models/init_mdms/init_mdms_model.dart';

part 'app_version_bloc.freezed.dart';

typedef AppVersionBlocEmitter = Emitter<AppVersionBlocState>;

class AppVersionBloc extends Bloc<AppVersionBlocEvent, AppVersionBlocState> {
  AppVersionBloc() : super(const AppVersionBlocState.initial()) {
    on<GetAppVersionEvent>(_getAppVersion);
  }

  FutureOr<void> _getAppVersion(
    GetAppVersionEvent event,
    AppVersionBlocEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const AppVersionBlocState.loading());
      AppVersionModel appVersionModel = await CommonRepository(client.init())
          .getAppVersion(
              apiEndPoint: Urls.initServices.mdms,
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              moduleDetails: [
            {
              "moduleName": "common-masters",
              "masterDetails": [
                {
                  "name": "AppVersion",
                },
              ],
            }
          ]);

      emit(AppVersionBlocState.loaded(appVersionModel));
    } on DioException catch (e) {
      emit(AppVersionBlocState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class AppVersionBlocEvent with _$AppVersionBlocEvent {
  const factory AppVersionBlocEvent.getAppVersion() = GetAppVersionEvent;
}

@freezed
class AppVersionBlocState with _$AppVersionBlocState {
  const AppVersionBlocState._();
  const factory AppVersionBlocState.initial() = _Initial;
  const factory AppVersionBlocState.loading() = _Loading;
  const factory AppVersionBlocState.loaded(AppVersionModel? cboAppVersion) =
      _Loaded;
  const factory AppVersionBlocState.error(String? error) = _Error;
}
