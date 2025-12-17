import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../data/remote_client.dart';
import '../../../data/repositories/attendence_repository/attendence_register.dart';
import '../../../models/attendance/attendance_registry_model.dart';
import '../../../utils/global_variables.dart';

part 'search_projects.freezed.dart';

typedef AttendanceProjectsSearchEmitter
    = Emitter<AttendanceProjectsSearchState>;

class AttendanceProjectsSearchBloc
    extends Bloc<AttendanceProjectsSearchEvent, AttendanceProjectsSearchState> {
  AttendanceProjectsSearchBloc()
      : super(const AttendanceProjectsSearchState.initial()) {
    on<SearchAttendanceProjectsEvent>(_onSearch);
    on<DisposeAttendanceRegisterEvent>(_onDispose);
  }

  FutureOr<void> _onSearch(SearchAttendanceProjectsEvent event,
      AttendanceProjectsSearchEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceProjectsSearchState.initial());
      emit(const AttendanceProjectsSearchState.loading());

      AttendanceRegistersModel attendanceRegistersModel =
          await AttendanceRegisterRepository(client.init())
              .searchAttendanceProjects(
        url: Urls.attendanceRegisterServices.searchAttendanceRegister,
        queryParameters: event.id.trim().toString().isNotEmpty
            ? {
                "tenantId": GlobalVariables
                    .organisationListModel!.organisations!.first.tenantId
                    .toString(),
                "ids": event.id
              }
            : {
                "tenantId": GlobalVariables
                    .organisationListModel!.organisations!.first.tenantId
                    .toString()
              },
      );
      await Future.delayed(const Duration(seconds: 1));
      emit(AttendanceProjectsSearchState.loaded(attendanceRegistersModel));
    } on DioException catch (e) {
      emit(AttendanceProjectsSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(DisposeAttendanceRegisterEvent event,
      AttendanceProjectsSearchEmitter emit) async {
    emit(const AttendanceProjectsSearchState.initial());
  }
}

@freezed
class AttendanceProjectsSearchEvent with _$AttendanceProjectsSearchEvent {
  const factory AttendanceProjectsSearchEvent.search({@Default('') String id}) =
      SearchAttendanceProjectsEvent;
  const factory AttendanceProjectsSearchEvent.dispose() =
      DisposeAttendanceRegisterEvent;
}

@freezed
class AttendanceProjectsSearchState with _$AttendanceProjectsSearchState {
  const AttendanceProjectsSearchState._();
  const factory AttendanceProjectsSearchState.initial() = _Initial;
  const factory AttendanceProjectsSearchState.loading() = _Loading;
  const factory AttendanceProjectsSearchState.loaded(
      AttendanceRegistersModel? attendanceRegistersModel) = _Loaded;
  const factory AttendanceProjectsSearchState.error(String? error) = _Error;
  // const factory AttendanceProjectsSearchState({
  //   @Default(false) bool loading,
  //   AttendanceRegistersModel? attendanceRegistersModel,
  //   AttendanceRegistersModel? individualAttendanceRegisterModel,
  // }) = _AttendanceProjectsSearchState;
}
