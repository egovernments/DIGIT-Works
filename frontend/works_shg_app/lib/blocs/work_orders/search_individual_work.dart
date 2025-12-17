import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/data/repositories/work_order_repository/my_works_repository.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../models/works/contracts_model.dart';

part 'search_individual_work.freezed.dart';

typedef SearchIndividualWorkEmitter = Emitter<SearchIndividualWorkState>;

class SearchIndividualWorkBloc
    extends Bloc<SearchIndividualWorkEvent, SearchIndividualWorkState> {
  SearchIndividualWorkBloc()
      : super(const SearchIndividualWorkState.initial()) {
    on<IndividualWorkSearchEvent>(_onSearch);
    on<DisposeIndividualContract>(_onDispose);
  }

  FutureOr<void> _onDispose(
      DisposeIndividualContract event, SearchIndividualWorkEmitter emit) async {
    emit(const SearchIndividualWorkState.initial());
  }

  FutureOr<void> _onSearch(
      IndividualWorkSearchEvent event, SearchIndividualWorkEmitter emit) async {
    Client client = Client();
    try {
      if (event.contractNumber == null || event.contractNumber!.isEmpty) {
        emit(const SearchIndividualWorkState.initial());
      } else {
        emit(const SearchIndividualWorkState.loading());
        ContractsModel contractsModel =
            await MyWorksRepository(client.init()).searchMyWorks(
                url: Urls.workServices.myWorks,
                body: {
                  ...?event.body,
                  "tenantId": GlobalVariables.roleType == RoleType.employee
                      ? GlobalVariables.tenantId
                      : GlobalVariables
                          .organisationListModel!.organisations!.first.tenantId,
                  "orgIds": [],
                  "contractNumber": event.contractNumber,
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
        emit(SearchIndividualWorkState.loaded(ContractsModel(
            contracts: contractsModel.contracts
                )));
      }
    } on DioException catch (e) {
      emit(SearchIndividualWorkState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SearchIndividualWorkEvent with _$SearchIndividualWorkEvent {
  const factory SearchIndividualWorkEvent.search({
    @Default('') String? contractNumber,
    Map? body,
  }) = IndividualWorkSearchEvent;
  const factory SearchIndividualWorkEvent.dispose() = DisposeIndividualContract;
}

@freezed
class SearchIndividualWorkState with _$SearchIndividualWorkState {
  const SearchIndividualWorkState._();

  const factory SearchIndividualWorkState.initial() = _Initial;
  const factory SearchIndividualWorkState.loading() = _Loading;
  const factory SearchIndividualWorkState.loaded(
      ContractsModel? contractsModel) = _Loaded;
  const factory SearchIndividualWorkState.error(String? error) = _Error;
}
