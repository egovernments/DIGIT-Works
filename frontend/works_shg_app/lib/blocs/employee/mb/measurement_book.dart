import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../data/remote_client.dart';
import '../../../data/repositories/employee_repository/mb.dart';
import '../../../models/employee/mb/mb_inbox_response.dart';
import '../../../services/urls.dart';
part 'measurement_book.freezed.dart';

typedef MeasurementInboxBlocEventEmitter = Emitter<MeasurementInboxState>;

class MeasurementInboxBloc
    extends Bloc<MeasurementInboxBlocEvent, MeasurementInboxState> {
  MeasurementInboxBloc() : super(const MeasurementInboxState.initial()) {
    on<MeasurementBookInboxBlocEvent>(getMBInbox);
    on<MeasurementBookInboxSearchBlocEvent>(searchMb);
    on<MeasurementBookInboxBlocClearEvent>(initialStage);
    on<MeasurementBookInboxSearchRepeatBlocEvent>(repeatSearch);
    on<MeasurementBookInboxSortBlocEvent>(sort);
  }
  FutureOr<void> getMBInbox(
    MeasurementBookInboxBlocEvent event,
    MeasurementInboxBlocEventEmitter emit,
  ) async {
    Client client = Client();
    try {
      if (event.offset == 0) {
        emit(const MeasurementInboxState.loading());
      }

      final s = {
        "inbox": {
          "tenantId": GlobalVariables.tenantId,
          "moduleSearchCriteria": {
            "tenantId": GlobalVariables.tenantId,
            // "status":["1f4fa87c-b299-4adf-8691-409bf0b8e164",],
            //  "status":[],
            // "ward":[],
          },
          "processSearchCriteria": {
            "businessService": ["MB"],
            "moduleName": "measurement-service"
          },
          "limit": 10,
          "offset": event.offset
        }
      };
      final MBInboxResponse res =
          await MBRepository(client.init()).fetchMbInbox(
        url: Urls.measurementService.measurementInbox,
        body: s,
      );
      if (event.offset == 0) {
        emit(MeasurementInboxState.loaded(
            res,
            res.items!.length < 10 ? false : true,
            null,
            null,
            null,
            null,
            null,
            false, {}));
      } else {
        state.maybeMap(
          orElse: () {
            return null;
          },
          loaded: (value) {
            List<ItemData> data = [];
            data.addAll(value.mbInboxResponse.items ?? []);
            data.addAll(res.items!);

            emit(
              MeasurementInboxState.loaded(
                value.mbInboxResponse.copyWith(items: data),
                res.items!.length < 10 ? false : true,
                null,
                null,
                null,
                null,
                null,
                false,
                {},
              ),
            );
          },
        );
      }
    } on DioException catch (e) {
      emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
    } catch (e) {
      emit(const MeasurementInboxState.error("CORE_SOMETHING_WENT_WRONG"));
    }
  }
//search

  FutureOr<void> searchMb(
    MeasurementBookInboxSearchBlocEvent event,
    MeasurementInboxBlocEventEmitter emit,
  ) async {
    Client client = Client();
    try {
      if (event.offset == 0) {
        emit(const MeasurementInboxState.loading());
      }

      final MBInboxResponse res =
          await MBRepository(client.init()).fetchMbInbox(
        url: Urls.measurementService.measurementInbox,
        body: event.data,
      );
      if (event.offset == 0) {
        emit(MeasurementInboxState.loaded(
            res,
            res.items!.length < 10 ? false : true,
            null,
            null,
            null,
            null,
            null,
            true,
            event.data));
      } else {
        state.maybeMap(
          orElse: () {
            return null;
          },
          loaded: (value) {
            List<ItemData> data = [];
            data.addAll(value.mbInboxResponse.items ?? []);
            data.addAll(res.items!);

            emit(
              MeasurementInboxState.loaded(
                value.mbInboxResponse.copyWith(items: data),
                res.items!.length < 10 ? false : true,
                null,
                null,
                null,
                null,
                null,
                true,
                event.data,
              ),
            );
          },
        );
      }
    } on DioException catch (e) {
      emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> initialStage(
    MeasurementBookInboxBlocClearEvent event,
    MeasurementInboxBlocEventEmitter emit,
  ) {
    emit(const MeasurementInboxState.initial());
  }

  FutureOr<void> repeatSearch(
    MeasurementBookInboxSearchRepeatBlocEvent event,
    MeasurementInboxBlocEventEmitter emit,
  ) async {
    Client client = Client();
    try {
      await state.maybeMap(
        orElse: () {
          return null;
        },
        loaded: (value) async {
          value.data['inbox']!['offset'] = event.offset;
          final MBInboxResponse res = await MBRepository(client.init())
              .fetchMbInbox(
                  url: Urls.measurementService.measurementInbox,
                  body: value.data
                  //body: value.data,
                  );
          List<ItemData> data = [];
          data.addAll(value.mbInboxResponse.items ?? []);
          data.addAll(res.items!);

          emit(
            MeasurementInboxState.loaded(
              value.mbInboxResponse.copyWith(items: data),
              res.items!.length < 10 ? false : true,
              // event.ward,
              value.ward,
              // event.status,
              value.status,
              // event.projectId,
              value.projectId,
              // event.mbNumber,
              value.mbNumber,

              // event.projectName,
              value.projectName,
              true,
              value.data,
            ),
          );
        },
      );
    } on DioException catch (e) {
      emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
    }
  }

// function for sorting

  FutureOr<void> sort(
    MeasurementBookInboxSortBlocEvent event,
    MeasurementInboxBlocEventEmitter emit,
  ) async {
    try {
      state.maybeMap(
        orElse: () {
          return null;
        },
        loaded: (value) {
          List<ItemData> itemList =
              List.from(value.mbInboxResponse.items ?? []);
          switch (event.sortCode) {
            case 0:
              itemList.sort((a, b) => a.businessObject!.serviceSla!
                  .compareTo(b.businessObject!.serviceSla!));

              break;
            case 1:
              itemList.sort((a, b) => a.processInstance!.state!.state!
                  .compareTo(b.processInstance!.state!.state!));
              break;
            case 2:
              itemList.sort((a, b) => a.businessObject!.measures!.first
                  .measureAdditionalDetails!.mbAmount!
                  .compareTo(b.businessObject!.measures!.first
                      .measureAdditionalDetails!.mbAmount!));
              break;
            case 3:
              itemList.sort((a, b) => b.businessObject!.measures!.first
                  .measureAdditionalDetails!.mbAmount!
                  .compareTo(a.businessObject!.measures!.first
                      .measureAdditionalDetails!.mbAmount!));
              break;
            default:
          }

          emit(value.copyWith(
              mbInboxResponse:
                  value.mbInboxResponse.copyWith(items: itemList)));
        },
      );
    } on DioException catch (e) {
      emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MeasurementInboxBlocEvent with _$MeasurementInboxBlocEvent {
  const factory MeasurementInboxBlocEvent.create({
    required String tenantId,
    required String businessService,
    required String moduleName,
    required int limit,
    required int offset,
  }) = MeasurementBookInboxBlocEvent;

  const factory MeasurementInboxBlocEvent.search(
          {List<String>? ward,
          List<String>? status,
          String? projectId,
          String? mbNumber,
          String? projectName,
          required int limit,
          required int offset,
          required Map<String, Map<String, dynamic>> data}) =
      MeasurementBookInboxSearchBlocEvent;

  const factory MeasurementInboxBlocEvent.searchRepeat({
    required String tenantId,
    required String businessService,
    required String moduleName,
    required int limit,
    required int offset,
  }) = MeasurementBookInboxSearchRepeatBlocEvent;

  // event for sorting the list
  const factory MeasurementInboxBlocEvent.sort({
    required int sortCode,
  }) = MeasurementBookInboxSortBlocEvent;

  const factory MeasurementInboxBlocEvent.clear() =
      MeasurementBookInboxBlocClearEvent;
}

@freezed
class MeasurementInboxState with _$MeasurementInboxState {
  const MeasurementInboxState._();

  const factory MeasurementInboxState.initial() = _Initial;
  const factory MeasurementInboxState.loading() = _Loading;
  const factory MeasurementInboxState.loaded(
      MBInboxResponse mbInboxResponse,
      bool isLoading,
      List<String>? ward,
      List<String>? status,
      String? projectId,
      String? mbNumber,
      String? projectName,
      bool search,
      Map<String, Map<String, dynamic>> data) = _Loaded;
  const factory MeasurementInboxState.error(String? error) = _Error;
}
