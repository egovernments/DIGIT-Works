import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/user_search/user_search_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/user_search_repository/user_search.dart';
import '../../utils/global_variables.dart';

part 'attendance_user_search.freezed.dart';

typedef AttendanceUserSearchEmitter = Emitter<AttendanceUserSearchState>;

class AttendanceUserSearchBloc
    extends Bloc<AttendanceUserSearchEvent, AttendanceUserSearchState> {
  AttendanceUserSearchBloc(super.initialState) {
    on<SearchAttendanceUserEvent>(_onSearch);
    on<SearchAttendanceUserUuidEvent>(_onUUIDSearch);
    on<DisposeSearchAttendanceUserEvent>(_onDispose);
  }

  FutureOr<void> _onSearch(
      SearchAttendanceUserEvent event, AttendanceUserSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceUserSearchState.loading());
      UserSearchModel userSearchModel =
          await UserSearchRepository(client.init())
              .searchUser(url: Urls.userServices.userSearchProfile, body: {
        "tenantId":
            GlobalVariables.globalConfigObject?.globalConfigs?.stateTenantId,
        "mobileNumber": event.mobileNumber
      });
      emit(AttendanceUserSearchState.loaded(userSearchModel));
    } on DioException catch (e) {
      emit(AttendanceUserSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onUUIDSearch(SearchAttendanceUserUuidEvent event,
      AttendanceUserSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceUserSearchState.loading());
      UserSearchModel userSearchModel =
          await UserSearchRepository(client.init())
              .searchUser(url: Urls.userServices.userSearchProfile, body: {
        "tenantId":
            GlobalVariables.globalConfigObject?.globalConfigs?.stateTenantId,
        "uuid": event.uuids
      });
      emit(AttendanceUserSearchState.loaded(userSearchModel));
    } on DioException catch (e) {
      emit(AttendanceUserSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(DisposeSearchAttendanceUserEvent event,
      AttendanceUserSearchEmitter emit) async {
    emit(const AttendanceUserSearchState.initial());
  }
}

@freezed
class AttendanceUserSearchEvent with _$AttendanceUserSearchEvent {
  const factory AttendanceUserSearchEvent.search({String? mobileNumber}) =
      SearchAttendanceUserEvent;
  const factory AttendanceUserSearchEvent.uuidSearch({List<String>? uuids}) =
      SearchAttendanceUserUuidEvent;
  const factory AttendanceUserSearchEvent.dispose() =
      DisposeSearchAttendanceUserEvent;
}

@freezed
class AttendanceUserSearchState with _$AttendanceUserSearchState {
  const AttendanceUserSearchState._();
  const factory AttendanceUserSearchState.initial() = _Initial;
  const factory AttendanceUserSearchState.loading() = _Loading;
  const factory AttendanceUserSearchState.loaded(
      UserSearchModel? userSearchModel) = _Loaded;
  const factory AttendanceUserSearchState.error(String? error) = _Error;
}
