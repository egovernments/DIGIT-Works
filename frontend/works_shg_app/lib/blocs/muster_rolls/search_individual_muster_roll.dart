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

part 'search_individual_muster_roll.freezed.dart';

typedef IndividualMusterRollSearchEmitter
    = Emitter<IndividualMusterRollSearchState>;

class IndividualMusterRollSearchBloc extends Bloc<
    IndividualMusterRollSearchEvent, IndividualMusterRollSearchState> {
  IndividualMusterRollSearchBloc()
      : super(const IndividualMusterRollSearchState.initial()) {
    on<SearchIndividualMusterRollEvent>(_onIndividualSearch);
  }

  FutureOr<void> _onIndividualSearch(SearchIndividualMusterRollEvent event,
      IndividualMusterRollSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualMusterRollSearchState.loading());

      MusterRollsModel individualMuster =
          await MusterRollRepository(client.init()).searchMusterRolls(
              url: Urls.musterRollServices.searchMusterRolls,
              queryParameters: {"tenantId": event.tenantId, "ids": event.id},
              options: Options(extra: {
                "userInfo": GlobalVariables.getUserInfo(),
                "accessToken": GlobalVariables.getAuthToken(),
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(IndividualMusterRollSearchState.loaded(individualMuster));
    } on DioError catch (e) {
      emit(IndividualMusterRollSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class IndividualMusterRollSearchEvent with _$IndividualMusterRollSearchEvent {
  const factory IndividualMusterRollSearchEvent.individualSearch(
      {@Default('') String id,
      @Default('') String tenantId}) = SearchIndividualMusterRollEvent;
}

@freezed
class IndividualMusterRollSearchState with _$IndividualMusterRollSearchState {
  const IndividualMusterRollSearchState._();

  const factory IndividualMusterRollSearchState.initial() = _Initial;
  const factory IndividualMusterRollSearchState.loading() = _Loading;
  const factory IndividualMusterRollSearchState.loaded(
      MusterRollsModel? musterRollsModel) = _Loaded;
  const factory IndividualMusterRollSearchState.error(String? error) = _Error;
}
