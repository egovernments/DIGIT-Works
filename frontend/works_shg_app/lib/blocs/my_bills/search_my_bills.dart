import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/my_bills_repository/my_bills_repo.dart';
import '../../models/my_bills/my_bills_model.dart';

part 'search_my_bills.freezed.dart';

typedef SearchMyBillsEmitter = Emitter<SearchMyBillsState>;

class SearchMyBillsBloc extends Bloc<SearchMyBillsEvent, SearchMyBillsState> {
  SearchMyBillsBloc() : super(const SearchMyBillsState.initial()) {
    on<MyBillsSearchEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      MyBillsSearchEvent event, SearchMyBillsEmitter emit) async {
    Client client = Client();
    try {
      emit(const SearchMyBillsState.loading());

      MyBillsListModel billsModel =
          await MyBillsRepository(client.init()).searchMyBills(
              url: Urls.billServices.searchMyBills,
              body: {
                "searchCriteria": {
                  "tenantId": GlobalVariables
                      .organisationListModel?.organisations?.first.tenantId,
                  "orgNumbers": [
                    GlobalVariables
                        .organisationListModel?.organisations?.first.id
                  ]
                },
                "pagination": {
                  "limit": "100",
                  "offSet": "0",
                  "sortBy": "lastModifiedTime",
                  "order": "desc"
                }
              },
              options: Options(extra: {
                "accessToken": GlobalVariables.authToken,
                "apiId": "asset-services",
                "msgId": "search with from and to values"
              }));
      await Future.delayed(const Duration(seconds: 1));
      emit(SearchMyBillsState.loaded(billsModel));
    } on DioException catch (e) {
      emit(SearchMyBillsState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SearchMyBillsEvent with _$SearchMyBillsEvent {
  const factory SearchMyBillsEvent.search() = MyBillsSearchEvent;
}

@freezed
class SearchMyBillsState with _$SearchMyBillsState {
  const SearchMyBillsState._();

  const factory SearchMyBillsState.initial() = _Initial;
  const factory SearchMyBillsState.loading() = _Loading;
  const factory SearchMyBillsState.loaded(MyBillsListModel? contractsModel) =
      _Loaded;
  const factory SearchMyBillsState.error(String? error) = _Error;
}
