import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../data/remote_client.dart';
import '../../../data/repositories/attendence_repository/attendence_register.dart';
import '../../../models/attendance/attendance_registry_model.dart';

part 'search_individual_project.freezed.dart';

typedef AttendanceIndividualProjectSearchEmitter
    = Emitter<AttendanceIndividualProjectSearchState>;

class AttendanceIndividualProjectSearchBloc extends Bloc<
    AttendanceIndividualProjectSearchEvent,
    AttendanceIndividualProjectSearchState> {
  AttendanceIndividualProjectSearchBloc()
      : super(const AttendanceIndividualProjectSearchState.initial()) {
    on<SearchIndividualAttendanceProjectEvent>(_onIndividualSearch);
    on<DisposeIndividualAttendanceRegisterEvent>(_onDispose);
  }

  FutureOr<void> _onDispose(DisposeIndividualAttendanceRegisterEvent event,
      AttendanceIndividualProjectSearchEmitter emit) async {
    emit(const AttendanceIndividualProjectSearchState.initial());
    emit(const AttendanceIndividualProjectSearchState.loaded(
        AttendanceRegistersModel()));
  }

  FutureOr<void> _onIndividualSearch(
      SearchIndividualAttendanceProjectEvent event,
      AttendanceIndividualProjectSearchEmitter emit) async {
    Client client = Client();
    try {
      if (event.id.trim().isEmpty || event.tenantId.trim().isEmpty) {
        emit(const AttendanceIndividualProjectSearchState.initial());
      } else {
        emit(const AttendanceIndividualProjectSearchState.initial());
        emit(const AttendanceIndividualProjectSearchState.loading());
        AttendanceRegistersModel attendanceRegistersModel =
            await AttendanceRegisterRepository(client.init())
                .searchAttendanceProjects(
                    url: Urls
                        .attendanceRegisterServices.searchAttendanceRegister,
                    queryParameters: {
              "tenantId": event.tenantId,
              "ids": event.id
            });
        await Future.delayed(const Duration(seconds: 1));
        emit(AttendanceIndividualProjectSearchState.loaded(
            attendanceRegistersModel));
      }
    } on DioError catch (e) {
      emit(AttendanceIndividualProjectSearchState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class AttendanceIndividualProjectSearchEvent
    with _$AttendanceIndividualProjectSearchEvent {
  const factory AttendanceIndividualProjectSearchEvent.individualSearch(
      {@Default('') String id,
      @Default('') String tenantId}) = SearchIndividualAttendanceProjectEvent;
  const factory AttendanceIndividualProjectSearchEvent.dispose() =
      DisposeIndividualAttendanceRegisterEvent;
}

@freezed
class AttendanceIndividualProjectSearchState
    with _$AttendanceIndividualProjectSearchState {
  const AttendanceIndividualProjectSearchState._();
  const factory AttendanceIndividualProjectSearchState.initial() = _Initial;
  const factory AttendanceIndividualProjectSearchState.loading() = _Loading;
  const factory AttendanceIndividualProjectSearchState.loaded(
          AttendanceRegistersModel? individualAttendanceRegisterModel) =
      _IndividualLoaded;
  const factory AttendanceIndividualProjectSearchState.error(String? error) =
      _Error;
}
