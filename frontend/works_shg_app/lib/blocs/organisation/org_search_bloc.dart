import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/org_repository/org_repository.dart';
import '../../models/organisation/organisation_model.dart';

part 'org_search_bloc.freezed.dart';

typedef ORGSearchEmitter = Emitter<ORGSearchState>;

class ORGSearchBloc extends Bloc<ORGSearchEvent, ORGSearchState> {
  ORGSearchBloc() : super(const ORGSearchState.initial()) {
    on<SearchORGEvent>(_onSearch);
    on<SearchMbORGEvent>(mbOrgSearch);
  }

  FutureOr<void> _onSearch(SearchORGEvent event, ORGSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const ORGSearchState.loading());
      OrganisationListModel organisationListModel =
          await ORGRepository(client.init())
              .searchORG(url: Urls.orgServices.orgSearch, body: {
        "SearchCriteria": {
          "contactMobileNumber": event.mobileNumber,
          "tenantId": GlobalVariables.tenantId
           //"tenantId": "pg.citya"
        },
        "Pagination": {"offSet": 0, "limit": 10}
      });
      GlobalVariables.organisationListModel = organisationListModel;
      GlobalVariables.tenantId =
          organisationListModel.organisations?.first.tenantId ??
              GlobalVariables.tenantId;
      await Future.delayed(const Duration(seconds: 1));
      emit(ORGSearchState.loaded(organisationListModel));
    } on DioException catch (e) {
      emit(ORGSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> mbOrgSearch(
      SearchMbORGEvent event, ORGSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const ORGSearchState.loading());
      OrganisationListModel organisationListModel =
          await ORGRepository(client.init())
              .searchORG(url: Urls.orgServices.orgSearch, body: {
        "SearchCriteria": {"tenantId": event.tenantId},
        "Pagination": {"offSet": 0, "limit": 1000}
      });
      GlobalVariables.organisationListModel = organisationListModel;
      GlobalVariables.tenantId =
          organisationListModel.organisations?.first.tenantId ??
              GlobalVariables.tenantId;
      await Future.delayed(const Duration(seconds: 1));
      emit(ORGSearchState.loaded(organisationListModel));
    } on DioException catch (e) {
      emit(ORGSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class ORGSearchEvent with _$ORGSearchEvent {
  const factory ORGSearchEvent.search(String mobileNumber) = SearchORGEvent;
  const factory ORGSearchEvent.mbOrgsearch({required String tenantId}) =
      SearchMbORGEvent;
}

@freezed
class ORGSearchState with _$ORGSearchState {
  const ORGSearchState._();
  const factory ORGSearchState.initial() = _Initial;
  const factory ORGSearchState.loading() = _Loading;
  const factory ORGSearchState.loaded(
      OrganisationListModel? organisationListModel) = _Loaded;
  const factory ORGSearchState.error(String? error) = _Error;
}
