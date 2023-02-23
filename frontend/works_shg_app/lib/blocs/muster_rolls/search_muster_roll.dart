import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/muster_roll_repository/muster_roll.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';

part 'search_muster_roll.freezed.dart';

typedef MusterRollSearchEmitter = Emitter<MusterRollSearchState>;

class MusterRollSearchBloc
    extends Bloc<MusterRollSearchEvent, MusterRollSearchState> {
  MusterRollSearchBloc() : super(const MusterRollSearchState.initial()) {
    on<SearchMusterRollEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      SearchMusterRollEvent event, MusterRollSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterRollSearchState.loading());

      MusterRollsModel musterRollsModel =
          await MusterRollRepository(client.init()).searchMusterRolls(
              url: Urls.musterRollServices.searchMusterRolls,
              queryParameters: {
                "tenantId": GlobalVariables
                    .globalConfigObject!.globalConfigs!.stateTenantId
                    .toString()
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(MusterRollSearchState.loaded(musterRollsModel));
    } on DioError catch (e) {
      emit(MusterRollSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MusterRollSearchEvent with _$MusterRollSearchEvent {
  const factory MusterRollSearchEvent.search() = SearchMusterRollEvent;
}

@freezed
class MusterRollSearchState with _$MusterRollSearchState {
  const MusterRollSearchState._();

  const factory MusterRollSearchState.initial() = _Initial;
  const factory MusterRollSearchState.loading() = _Loading;
  const factory MusterRollSearchState.loaded(
      MusterRollsModel? musterRollsModel) = _Loaded;
  const factory MusterRollSearchState.error(String? error) = _Error;
}
