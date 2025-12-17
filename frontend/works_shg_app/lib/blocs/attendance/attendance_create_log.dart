import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/attendence_register.dart';
import '../../utils/global_variables.dart';

part 'attendance_create_log.freezed.dart';

typedef AttendanceLogCreateEmitter = Emitter<AttendanceLogCreateState>;

class AttendanceLogCreateBloc
    extends Bloc<AttendanceLogCreateEvent, AttendanceLogCreateState> {
  AttendanceLogCreateBloc() : super(const AttendanceLogCreateState.initial()) {
    on<CreateAttendanceLogEvent>(_onCreate);
    on<UpdateAttendanceLogEvent>(_onUpdate);
  }

  FutureOr<void> _onCreate(
      CreateAttendanceLogEvent event, AttendanceLogCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceLogCreateState.loading());
      AttendanceRegistersModel attendanceRegistersModel =
          await AttendanceRegisterRepository(client.init()).createAttendanceLog(
              url: Urls.attendanceRegisterServices.createAttendanceLog,
              options:
                  Options(extra: {"accessToken": GlobalVariables.authToken}),
              body: {"attendance": event.attendanceList});
      await Future.delayed(const Duration(seconds: 2));
      if (attendanceRegistersModel != null) {
        emit(const AttendanceLogCreateState.loaded());
      }
    } on DioException catch (e) {
      emit(AttendanceLogCreateState.error(
          e.response?.data['Errors'][0]['code'],e.response?.data['Errors'][0]['message']));
    }
  }

  FutureOr<void> _onUpdate(
      UpdateAttendanceLogEvent event, AttendanceLogCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceLogCreateState.loading());
      AttendanceRegistersModel attendanceRegistersModel =
          await AttendanceRegisterRepository(client.init()).createAttendanceLog(
              url: Urls.attendanceRegisterServices.updateAttendanceLog,
              options: Options(extra: {
                "accessToken": GlobalVariables.authToken,
                "userInfo": GlobalVariables.userRequestModel,
              }),
              body: {"attendance": event.attendanceList});
      await Future.delayed(const Duration(seconds: 2));
      if (attendanceRegistersModel != null) {
        emit(const AttendanceLogCreateState.loaded());
      }
    } on DioException catch (e) {
      emit(AttendanceLogCreateState.error(
          e.response?.data['Errors'][0]['code'],e.response?.data['Errors'][0]['message']));
    }
  }
}

@freezed
class AttendanceLogCreateEvent with _$AttendanceLogCreateEvent {
  const factory AttendanceLogCreateEvent.create(
      {List<Map<String, dynamic>>? attendanceList}) = CreateAttendanceLogEvent;
  const factory AttendanceLogCreateEvent.update(
      {List<Map<String, dynamic>>? attendanceList}) = UpdateAttendanceLogEvent;
}

@freezed
class AttendanceLogCreateState with _$AttendanceLogCreateState {
  const AttendanceLogCreateState._();
  const factory AttendanceLogCreateState.initial() = _Initial;
  const factory AttendanceLogCreateState.loading() = _Loading;
  const factory AttendanceLogCreateState.loaded() = _Loaded;
  const factory AttendanceLogCreateState.error(String? error, String?message) = _Error;
}
