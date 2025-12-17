import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/individual_repository.dart';
import '../../models/attendance/individual_list_model.dart';

part 'individual_search.freezed.dart';

typedef IndividualSearchEmitter = Emitter<IndividualSearchState>;

class IndividualSearchBloc
    extends Bloc<IndividualSearchEvent, IndividualSearchState> {
  IndividualSearchBloc(super.initialState) {
    on<SearchIndividualEvent>(_onSearch);
    on<SearchIndividualNameEvent>(_onNameSearch);
    on<SearchIndividualIdEvent>(_onIdSearch);
    on<DisposeSearchIndividualEvent>(_onDispose);
  }

  FutureOr<void> _onSearch(
      SearchIndividualEvent event, IndividualSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualSearchState.initial());
      emit(const IndividualSearchState.loading());
      IndividualListModel individualListModel =
          await IndividualRepository(client.init()).searchIndividual(
              url: Urls.attendanceRegisterServices.individualSearch,
              queryParameters: {
            "offset": '0',
            "limit": '100',
            "tenantId": event.tenant.toString(),
          },
              body: {
            "Individual": {"mobileNumber": event.mobileNumber}
          });
      emit(IndividualSearchState.loaded(individualListModel));
    } on DioException catch (e) {
      emit(IndividualSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onNameSearch(
      SearchIndividualNameEvent event, IndividualSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualSearchState.initial());
      emit(const IndividualSearchState.loading());
      IndividualListModel individualListModel =
          await IndividualRepository(client.init()).searchIndividual(
              url: Urls.attendanceRegisterServices.individualSearch,
              queryParameters: {
            "offset": '0',
            "limit": '100',
            "tenantId": event.tenant.toString(),
          },
              body: {
            "Individual": {
              "name": {"givenName": event.name}
            }
          });
      emit(IndividualSearchState.loaded(individualListModel));
    } on DioException catch (e) {
      emit(IndividualSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onIdSearch(
      SearchIndividualIdEvent event, IndividualSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualSearchState.initial());
      emit(const IndividualSearchState.loading());
      IndividualListModel individualListModel =
          await IndividualRepository(client.init()).searchIndividual(
              url: Urls.attendanceRegisterServices.individualSearch,
              queryParameters: {
            "offset": '0',
            "limit": '100',
            "tenantId": event.tenant.toString(),
          },
              body: {
            "Individual": {"id": event.ids}
          });
      emit(IndividualSearchState.loaded(individualListModel));
    } on DioException catch (e) {
      emit(IndividualSearchState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(
      DisposeSearchIndividualEvent event, IndividualSearchEmitter emit) async {
    emit(const IndividualSearchState.initial());
  }
}

@freezed
class IndividualSearchEvent with _$IndividualSearchEvent {
  const factory IndividualSearchEvent.search(
      {required String tenant,
      required String mobileNumber}) = SearchIndividualEvent;
  const factory IndividualSearchEvent.nameSearch(
      {required String tenant,
      required String name}) = SearchIndividualNameEvent;
  const factory IndividualSearchEvent.idSearch(
      {required String tenant, List<String>? ids}) = SearchIndividualIdEvent;
  const factory IndividualSearchEvent.dispose() = DisposeSearchIndividualEvent;
}

@freezed
class IndividualSearchState with _$IndividualSearchState {
  const IndividualSearchState._();
  const factory IndividualSearchState.initial() = _Initial;
  const factory IndividualSearchState.loading() = _Loading;
  const factory IndividualSearchState.loaded(
      IndividualListModel? individualListModel) = _Loaded;
  const factory IndividualSearchState.error(String? error) = _Error;
}
