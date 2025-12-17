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

part 'from_to_date_search_muster_roll.freezed.dart';

typedef MusterRollFromToDateSearchEmitter
    = Emitter<MusterRollFromToDateSearchState>;

class MusterRollFromToDateSearchBloc extends Bloc<
    MusterRollFromToDateSearchEvent, MusterRollFromToDateSearchState> {
  MusterRollFromToDateSearchBloc()
      : super(const MusterRollFromToDateSearchState.initial()) {
    on<SearchMusterRollFromToDateEvent>(_onFromToDateSearchEvent);
  }

  FutureOr<void> _onFromToDateSearchEvent(SearchMusterRollFromToDateEvent event,
      MusterRollFromToDateSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterRollFromToDateSearchState.loading());

      MusterRollsModel musterRollsSearch =
          await MusterRollRepository(client.init()).searchMusterRolls(
              url: Urls.musterRollServices.searchMusterRolls,
              queryParameters: {
                "tenantId": event.tenantId,
                "registerId": event.registerId,
                "fromDate": event.fromDate.toString(),
                "toDate": event.toDate.toString()
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
     // await Future.delayed(const Duration(seconds: 1));
      emit(MusterRollFromToDateSearchState.loaded(musterRollsSearch));
    } on DioException catch (e) {
      emit(MusterRollFromToDateSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MusterRollFromToDateSearchEvent with _$MusterRollFromToDateSearchEvent {
  const factory MusterRollFromToDateSearchEvent.fromToDateSearch(
      {@Default('') String registerId,
      @Default('') String tenantId,
      @Default(0) int fromDate,
      @Default(0) int toDate}) = SearchMusterRollFromToDateEvent;
}

@freezed
class MusterRollFromToDateSearchState with _$MusterRollFromToDateSearchState {
  const MusterRollFromToDateSearchState._();

  const factory MusterRollFromToDateSearchState.initial() = _Initial;
  const factory MusterRollFromToDateSearchState.loading() = _Loading;
  const factory MusterRollFromToDateSearchState.loaded(
      MusterRollsModel? musterRollsModel) = _Loaded;
  const factory MusterRollFromToDateSearchState.error(String? error) = _Error;
}
