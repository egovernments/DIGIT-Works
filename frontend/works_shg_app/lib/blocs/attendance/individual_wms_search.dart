import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/individual_repository.dart';
import '../../models/attendance/individual_list_model.dart';

part 'individual_wms_search.freezed.dart';

typedef IndividualWMSSearchEmitter = Emitter<IndividualWMSSearchState>;

class IndividualWMSSearchBloc
    extends Bloc<IndividualWMSSearchEvent, IndividualWMSSearchState> {
  IndividualWMSSearchBloc(super.initialState) {
    // on<SearchWMSIndividualEvent>(_onSearch);
    on<SearchWMSIndividualNameEvent>(_onNameSearch);
    on<SearchWMSIndividualIdEvent>(_onIdSearch);
    on<DisposeSearchWMSIndividualEvent>(_onDispose);
  }

  /*
  * @author Ramkrishna
  * ramkrishna.sahoo@egovernments.org
  *
  * */
  //Will take up the wms mobile search later on with PFM-3690
  // FutureOr<void> _onSearch(
  //     SearchWMSIndividualEvent event, IndividualWMSSearchEmitter emit) async {
  //   Client client = Client();
  //   try {
  //     emit(const IndividualWMSSearchState.initial());
  //     emit(const IndividualWMSSearchState.loading());
  //     IndividualListModel individualListModel =
  //         await IndividualRepository(client.init()).searchIndividual(
  //             url: Urls.attendanceRegisterServices.wmsIndividualSearch,
  //             queryParameters: {
  //           "tenantId": event.tenant.toString(),
  //         },
  //             body: {
  //           "inbox": {
  //             "limit": 50,
  //             "moduleSearchCriteria": {
  //               "name": event.name,
  //               "tenantId": event.tenant.toString()
  //             }
  //           }
  //         });
  //     emit(IndividualWMSSearchState.loaded(individualListModel));
  //   } on DioError catch (e) {
  //     emit(IndividualWMSSearchState.error(
  //         e.response?.data['Errors'][0]['code']));
  //   }
  // }

  FutureOr<void> _onNameSearch(SearchWMSIndividualNameEvent event,
      IndividualWMSSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualWMSSearchState.initial());
      emit(const IndividualWMSSearchState.loading());
      WMSIndividualListModel individualListModel =
          await IndividualRepository(client.init()).searchWMSIndividual(
              url: Urls.attendanceRegisterServices.wmsIndividualSearch,
              queryParameters: {
            "offset": '0',
            "limit": '100',
            "tenantId": event.tenant.toString(),
          },
              body: {
            "inbox": {
              "limit": 50,
              "moduleSearchCriteria": {
                "name": event.name,
                "tenantId": event.tenant.toString()
              },
              "tenantId": event.tenant.toString(),
              "offset": 0
            }
          });
      emit(IndividualWMSSearchState.loaded(individualListModel));
    } on DioError catch (e) {
      emit(IndividualWMSSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onIdSearch(
      SearchWMSIndividualIdEvent event, IndividualWMSSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const IndividualWMSSearchState.initial());
      emit(const IndividualWMSSearchState.loading());
      WMSIndividualListModel individualListModel =
          await IndividualRepository(client.init()).searchWMSIndividual(
              url: Urls.attendanceRegisterServices.wmsIndividualSearch,
              queryParameters: {
            "offset": '0',
            "limit": '100',
            "tenantId": event.tenant.toString(),
          },
              body: {
            "inbox": {
              "limit": 50,
              "moduleSearchCriteria": {
                "id": event.ids,
                "tenantId": event.tenant.toString()
              },
              "tenantId": event.tenant.toString(),
              "offset": 0
            }
          });
      emit(IndividualWMSSearchState.loaded(individualListModel));
    } on DioError catch (e) {
      emit(IndividualWMSSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(DisposeSearchWMSIndividualEvent event,
      IndividualWMSSearchEmitter emit) async {
    emit(const IndividualWMSSearchState.initial());
    emit(IndividualWMSSearchState.loaded(WMSIndividualListModel()));
  }
}

@freezed
class IndividualWMSSearchEvent with _$IndividualWMSSearchEvent {
  // const factory IndividualWMSSearchEvent.search(
  //     {required String tenant,
  //     required String mobileNumber}) = SearchWMSIndividualEvent;
  const factory IndividualWMSSearchEvent.nameSearch(
      {required String tenant,
      required String name}) = SearchWMSIndividualNameEvent;
  const factory IndividualWMSSearchEvent.idSearch(
      {required String tenant, List<String>? ids}) = SearchWMSIndividualIdEvent;
  const factory IndividualWMSSearchEvent.dispose() =
      DisposeSearchWMSIndividualEvent;
}

@freezed
class IndividualWMSSearchState with _$IndividualWMSSearchState {
  const IndividualWMSSearchState._();
  const factory IndividualWMSSearchState.initial() = _Initial;
  const factory IndividualWMSSearchState.loading() = _Loading;
  const factory IndividualWMSSearchState.loaded(
      WMSIndividualListModel? individualListModel) = _Loaded;
  const factory IndividualWMSSearchState.error(String? error) = _Error;
}
