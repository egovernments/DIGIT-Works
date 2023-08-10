import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/muster_roll_repository/muster_roll.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';
import 'package:works_shg_app/models/works/contracts_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../../data/remote_client.dart';
import '../../data/repositories/work_order_repository/my_works_repository.dart';

part 'muster_search_for_time_extension.freezed.dart';

typedef ValidMusterRollsSearchEmitter = Emitter<ValidMusterRollsSearchState>;

class ValidMusterRollsSearchBloc
    extends Bloc<ValidMusterRollsSearchEvent, ValidMusterRollsSearchState> {
  ValidMusterRollsSearchBloc()
      : super(const ValidMusterRollsSearchState.initial()) {
    on<SearchValidMusterRollsEvent>(_onMusterSearch);
  }

  FutureOr<void> _onMusterSearch(SearchValidMusterRollsEvent event,
      ValidMusterRollsSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const ValidMusterRollsSearchState.loading());
      ContractsModel contractsModel =
          await MyWorksRepository(client.init()).searchMyWorks(
              url: Urls.workServices.myWorks,
              body: {
                "tenantId": GlobalVariables
                    .organisationListModel!.organisations!.first.tenantId,
                "orgIds": [],
                "contractNumber": event.contract?.contractNumber,
                // "pagination": {
                //   "limit": "100",
                //   "offSet": "0",
                //   "sortBy": "lastModifiedTime",
                //   "order": "desc"
                // }
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
      if ((contractsModel.contracts ?? []).any((e) =>
          e.businessService == 'CONTRACT-REVISION' &&
          e.status == 'INWORKFLOW')) {
        emit(ValidMusterRollsSearchState.error(
            i18.workOrder.errTimeExtReqAlreadyRaised));
      } else {
        MusterRollsModel individualMuster =
            await MusterRollRepository(client.init()).searchMusterRolls(
                url: Urls.musterRollServices.searchMusterRolls,
                queryParameters: event.status == null
                    ? {
                        "tenantId": event.tenantId,
                        "referenceId": event.contractNo
                      }
                    : {
                        "tenantId": event.tenantId,
                        "referenceId": event.contractNo,
                        "musterRollStatus": event.status ?? ''
                      },
                options: Options(extra: {
                  "userInfo": GlobalVariables.userRequestModel,
                  "accessToken": GlobalVariables.authToken,
                  "apiId": "asset-services",
                  "msgId": "search with from and to values"
                }));
        await Future.delayed(const Duration(milliseconds: 500));
        if ((individualMuster.musterRoll ?? []).isEmpty) {
          emit(ValidMusterRollsSearchState.error(
              i18.workOrder.errNoMusterRollExists));
        } else {
          emit(ValidMusterRollsSearchState.loaded(individualMuster));
        }
      }
    } on DioError catch (e) {
      emit(ValidMusterRollsSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class ValidMusterRollsSearchEvent with _$ValidMusterRollsSearchEvent {
  const factory ValidMusterRollsSearchEvent.individualSearch(
      {@Default('') String contractNo,
      @Default('') String tenantId,
      String? status,
      Contracts? contract}) = SearchValidMusterRollsEvent;
}

@freezed
class ValidMusterRollsSearchState with _$ValidMusterRollsSearchState {
  const ValidMusterRollsSearchState._();

  const factory ValidMusterRollsSearchState.initial() = _Initial;
  const factory ValidMusterRollsSearchState.loading() = _Loading;
  const factory ValidMusterRollsSearchState.loaded(
      MusterRollsModel? musterRollsModel) = _Loaded;
  const factory ValidMusterRollsSearchState.error(String? error) = _Error;
}
