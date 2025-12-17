import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/works/contracts_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../../data/remote_client.dart';
import '../../data/repositories/work_order_repository/my_works_repository.dart';
import '../../utils/constants.dart';

part 'valid_time_extension.freezed.dart';

typedef ValidTimeExtCreationsSearchEmitter
    = Emitter<ValidTimeExtCreationsSearchState>;

class ValidTimeExtCreationsSearchBloc extends Bloc<
    ValidTimeExtCreationsSearchEvent, ValidTimeExtCreationsSearchState> {
  ValidTimeExtCreationsSearchBloc()
      : super(const ValidTimeExtCreationsSearchState.initial()) {
    on<SearchValidTimeExtCreationsEvent>(_onMusterSearch);
  }

  FutureOr<void> _onMusterSearch(SearchValidTimeExtCreationsEvent event,
      ValidTimeExtCreationsSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const ValidTimeExtCreationsSearchState.loading());
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
        emit(ValidTimeExtCreationsSearchState.error(
            i18.workOrder.errTimeExtReqAlreadyRaised));
      } else {
        emit(ValidTimeExtCreationsSearchState.loaded(contractsModel.contracts
            ?.firstWhere((c) => c.status != Constants.inActive)));
      }
    } on DioException catch (e) {
      emit(ValidTimeExtCreationsSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class ValidTimeExtCreationsSearchEvent with _$ValidTimeExtCreationsSearchEvent {
  const factory ValidTimeExtCreationsSearchEvent.individualSearch(
      {@Default('') String contractNo,
      @Default('') String tenantId,
      String? status,
      Contracts? contract}) = SearchValidTimeExtCreationsEvent;
}

@freezed
class ValidTimeExtCreationsSearchState with _$ValidTimeExtCreationsSearchState {
  const ValidTimeExtCreationsSearchState._();

  const factory ValidTimeExtCreationsSearchState.initial() = _Initial;
  const factory ValidTimeExtCreationsSearchState.loading() = _Loading;
  const factory ValidTimeExtCreationsSearchState.loaded(Contracts? contracts) =
      _Loaded;
  const factory ValidTimeExtCreationsSearchState.error(String? error) = _Error;
}
