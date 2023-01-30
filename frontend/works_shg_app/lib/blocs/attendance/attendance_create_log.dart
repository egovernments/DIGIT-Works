import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
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
  AttendanceLogCreateBloc() : super(const AttendanceLogCreateState()) {
    on<CreateAttendanceLogEvent>(_onCreate);
    on<UpdateAttendanceLogEvent>(_onUpdate);
  }

  FutureOr<void> _onCreate(
      CreateAttendanceLogEvent event, AttendanceLogCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    AttendanceRegistersModel attendanceRegistersModel =
        await AttendanceRegisterRepository(client.init()).createAttendanceLog(
            url: Urls.attendanceRegisterServices.createAttendanceLog,
            options:
                Options(extra: {"accessToken": GlobalVariables.getAuthToken()}),
            body: {"attendance": event.attendanceList});
    await Future.delayed(const Duration(seconds: 2));
    emit(state.copyWith(
        createAttendanceRegistersModel: attendanceRegistersModel,
        loading: false));
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
  }

  FutureOr<void> _onUpdate(
      UpdateAttendanceLogEvent event, AttendanceLogCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    AttendanceRegistersModel attendanceRegistersModel =
        await AttendanceRegisterRepository(client.init()).createAttendanceLog(
            url: Urls.attendanceRegisterServices.updateAttendanceLog,
            options: Options(extra: {
              "accessToken": GlobalVariables.getAuthToken(),
              "userInfo": GlobalVariables.getUserInfo(),
            }),
            body: {"attendance": event.attendanceList});
    await Future.delayed(const Duration(seconds: 2));
    emit(state.copyWith(
      updateAttendanceRegistersModel: attendanceRegistersModel,
      loading: false,
    ));
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
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

  const factory AttendanceLogCreateState({
    @Default(false) bool loading,
    AttendanceRegistersModel? createAttendanceRegistersModel,
    AttendanceRegistersModel? updateAttendanceRegistersModel,
  }) = _AttendanceLogCreateState;
}
