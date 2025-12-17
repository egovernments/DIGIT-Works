import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/work_order_repository/my_works_repository.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../models/works/contracts_model.dart';

part 'my_service_requests_bloc.freezed.dart';

typedef SearchMyServiceRequestsEmitter = Emitter<SearchMyServiceRequestsState>;

class SearchMyServiceRequestsBloc
    extends Bloc<SearchMyServiceRequestsEvent, SearchMyServiceRequestsState> {
  SearchMyServiceRequestsBloc()
      : super(const SearchMyServiceRequestsState.initial()) {
    on<MyServiceRequestsSearchEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(MyServiceRequestsSearchEvent event,
      SearchMyServiceRequestsEmitter emit) async {
    Client client = Client();
    try {
      emit(const SearchMyServiceRequestsState.loading());

      ContractsModel contractsModel =
          await MyWorksRepository(client.init()).searchMyWorks(
              url: Urls.workServices.myWorks,
              body: {
                "tenantId": GlobalVariables
                    .organisationListModel?.organisations?.first.tenantId,
                "orgIds": [
                  GlobalVariables.organisationListModel?.organisations?.first.id
                ],
                "businessService": event.businessService,
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
      emit(SearchMyServiceRequestsState.loaded(contractsModel));
    } on DioException catch (e) {
      emit(SearchMyServiceRequestsState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SearchMyServiceRequestsEvent with _$SearchMyServiceRequestsEvent {
  const factory SearchMyServiceRequestsEvent.search({String? businessService}) =
      MyServiceRequestsSearchEvent;
}

@freezed
class SearchMyServiceRequestsState with _$SearchMyServiceRequestsState {
  const SearchMyServiceRequestsState._();

  const factory SearchMyServiceRequestsState.initial() = _Initial;
  const factory SearchMyServiceRequestsState.loading() = _Loading;
  const factory SearchMyServiceRequestsState.loaded(
      ContractsModel? contractsModel) = _Loaded;
  const factory SearchMyServiceRequestsState.error(String? error) = _Error;
}
