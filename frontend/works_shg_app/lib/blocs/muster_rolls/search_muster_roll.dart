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
  MusterRollSearchBloc() : super(const MusterRollSearchState()) {
    on<SearchMusterRollEvent>(_onSearch);
    on<SearchIndividualMusterRollEvent>(_onIndividualSearch);
  }

  FutureOr<void> _onSearch(
      SearchMusterRollEvent event, MusterRollSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    MusterRollsModel musterRollsModel =
        await MusterRollRepository(client.init()).searchMusterRolls(
            url: Urls.musterRollServices.searchMusterRolls,
            queryParameters: {
              "tenantId": GlobalVariables.getTenantId().toString()
            },
            options: Options(extra: {
              "userInfo": GlobalVariables.getUserInfo(),
              "accessToken": GlobalVariables.getAuthToken(),
              "apiId": "asset-services",
              "msgId": "search with from and to values"
            }));
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(musterRollsModel: musterRollsModel, loading: false));
  }

  FutureOr<void> _onIndividualSearch(SearchIndividualMusterRollEvent event,
      MusterRollSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    MusterRollsModel individualMuster =
        await MusterRollRepository(client.init()).searchMusterRolls(
            url: Urls.musterRollServices.searchMusterRolls,
            queryParameters: {
              "tenantId": GlobalVariables.getTenantId().toString(),
              "ids": event.id
            },
            options: Options(extra: {
              "userInfo": GlobalVariables.getUserInfo(),
              "accessToken": GlobalVariables.getAuthToken(),
              "apiId": "asset-services",
              "msgId": "search with from and to values"
            }));
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(
        individualMusterRollModel: individualMuster, loading: false));
  }
}

@freezed
class MusterRollSearchEvent with _$MusterRollSearchEvent {
  const factory MusterRollSearchEvent.search() = SearchMusterRollEvent;
  const factory MusterRollSearchEvent.individualSearch(
      {@Default('') String id}) = SearchIndividualMusterRollEvent;
}

@freezed
class MusterRollSearchState with _$MusterRollSearchState {
  const MusterRollSearchState._();

  const factory MusterRollSearchState({
    @Default(false) bool loading,
    MusterRollsModel? musterRollsModel,
    MusterRollsModel? individualMusterRollModel,
  }) = _MusterRollSearchState;
}
