import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/attendence_register.dart';
import '../../models/attendance/attendance_registry_model.dart';

part 'search_projects.freezed.dart';

typedef AttendanceProjectsSearchEmitter
    = Emitter<AttendanceProjectsSearchState>;

class AttendanceProjectsSearchBloc
    extends Bloc<AttendanceProjectsSearchEvent, AttendanceProjectsSearchState> {
  AttendanceProjectsSearchBloc()
      : super(const AttendanceProjectsSearchState()) {
    on<SearchAttendanceProjectsEvent>(_onSearch);
    on<SearchIndividualAttendanceProjectEvent>(_onIndividualSearch);
    on<DisposeAttendanceRegisterEvent>(_onDispose);
  }

  FutureOr<void> _onSearch(SearchAttendanceProjectsEvent event,
      AttendanceProjectsSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    AttendanceRegistersModel attendanceRegistersModel =
        await AttendanceRegisterRepository(client.init())
            .searchAttendanceProjects(
      url: Urls.attendanceRegisterServices.searchAttendanceRegister,
      queryParameters: event.id.trim().toString().isNotEmpty
          ? {
              "tenantId": GlobalVariables.getTenantId().toString(),
              "ids": event.id
            }
          : {"tenantId": GlobalVariables.getTenantId().toString()},
    );
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(
        attendanceRegistersModel: attendanceRegistersModel, loading: false));
  }

  FutureOr<void> _onDispose(DisposeAttendanceRegisterEvent event,
      AttendanceProjectsSearchEmitter emit) async {
    emit(state.copyWith(
        loading: false, individualAttendanceRegisterModel: null));
  }

  FutureOr<void> _onIndividualSearch(
      SearchIndividualAttendanceProjectEvent event,
      AttendanceProjectsSearchEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    AttendanceRegistersModel attendanceRegistersModel =
        await AttendanceRegisterRepository(client.init())
            .searchAttendanceProjects(
      url: Urls.attendanceRegisterServices.searchAttendanceRegister,
      queryParameters: event.id.trim().toString().isNotEmpty
          ? {"tenantId": event.tenantId, "ids": event.id}
          : {"tenantId": GlobalVariables.getTenantId().toString()},
    );
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(
        individualAttendanceRegisterModel: attendanceRegistersModel,
        loading: false));
  }
}

@freezed
class AttendanceProjectsSearchEvent with _$AttendanceProjectsSearchEvent {
  const factory AttendanceProjectsSearchEvent.search({@Default('') String id}) =
      SearchAttendanceProjectsEvent;
  const factory AttendanceProjectsSearchEvent.individualSearch(
      {@Default('') String id,
      @Default('') String tenantId}) = SearchIndividualAttendanceProjectEvent;
  const factory AttendanceProjectsSearchEvent.dispose() =
      DisposeAttendanceRegisterEvent;
}

@freezed
class AttendanceProjectsSearchState with _$AttendanceProjectsSearchState {
  const AttendanceProjectsSearchState._();

  const factory AttendanceProjectsSearchState({
    @Default(false) bool loading,
    AttendanceRegistersModel? attendanceRegistersModel,
    AttendanceRegistersModel? individualAttendanceRegisterModel,
  }) = _AttendanceProjectsSearchState;
}
