import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/work_order_repository/my_works_repository.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../models/works/contracts_model.dart';

part 'search_my_works.freezed.dart';

typedef SearchMyWorksEmitter = Emitter<SearchMyWorksState>;

class SearchMyWorksBloc extends Bloc<SearchMyWorksEvent, SearchMyWorksState> {
  SearchMyWorksBloc() : super(const SearchMyWorksState.initial()) {
    on<MyWorksSearchEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      MyWorksSearchEvent event, SearchMyWorksEmitter emit) async {
    Client client = Client();
    try {
      emit(const SearchMyWorksState.loading());

      ContractsModel contractsModel =
          await MyWorksRepository(client.init()).searchMyWorks(
              url: Urls.workServices.myWorks,
              body: {
                // "status":"ACTIVE",
                "tenantId": GlobalVariables
                    .organisationListModel?.organisations?.first.tenantId,
                "orgIds": [
                  GlobalVariables.organisationListModel?.organisations?.first.id
                ],
                "wfStatus": event.searchCriteria,
                "pagination": {
                  "limit": "100",
                  "offSet": "0",
                  "sortBy": "lastModifiedTime",
                  "order": "desc"
                }
              },
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken,
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(SearchMyWorksState.loaded(ContractsModel(
          contracts: contractsModel.contracts
             )));
    } on DioException catch (e) {
      emit(SearchMyWorksState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SearchMyWorksEvent with _$SearchMyWorksEvent {
  const factory SearchMyWorksEvent.search(List<String>? searchCriteria) =
      MyWorksSearchEvent;
}

@freezed
class SearchMyWorksState with _$SearchMyWorksState {
  const SearchMyWorksState._();

  const factory SearchMyWorksState.initial() = _Initial;
  const factory SearchMyWorksState.loading() = _Loading;
  const factory SearchMyWorksState.loaded(ContractsModel? contractsModel) =
      _Loaded;
  const factory SearchMyWorksState.error(String? error) = _Error;
}
