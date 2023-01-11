import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/data/repositories/attendence_repository/attendence_register.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/models/request_info/request_info_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../services/local_storage.dart';
import '../../utils/global_variables.dart';

part 'create_attendence_register.freezed.dart';

typedef AttendenceRegisterCreateEmitter
    = Emitter<AttendenceRegisterCreateState>;

class AttendenceRegisterCreateBloc
    extends Bloc<AttendenceRegisterCreateEvent, AttendenceRegisterCreateState> {
  AttendenceRegisterCreateBloc()
      : super(const AttendenceRegisterCreateState()) {
    on<CreateAttendenceRegisterEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(AttendenceRegisterCreateEvent event,
      AttendenceRegisterCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    print(event);
    AttendenceRegistersModel attendenceRegistersModel =
        await AttendenceRegisterRepository(client.init())
            .createAttendenceRegisters(
                url: Urls.attendenceRegisterServices.CreateAttendenceRegister,
                options: Options(extra: {
                  "userInfo": GlobalVariables.getUserInfo(),
                  "accessToken": GlobalVariables.getAuthToken()
                }),
                body: {
          "attendanceRegister": [
            {
              "id": "",
              "tenantId": event.tenantId,
              "registerNumber": event.registerNumber,
              "name": event.name,
              "startDate": event.startDate,
              "endDate": event.endDate,
              "staff": [],
              "attendees": []
            }
          ]
        });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(
        attendenceRegistersModel: attendenceRegistersModel, loading: false));
  }
}

@freezed
class AttendenceRegisterCreateEvent with _$AttendenceRegisterCreateEvent {
  const factory AttendenceRegisterCreateEvent.create({
    required String tenantId,
    required String registerNumber,
    required String name,
    required int startDate,
    required int endDate,
  }) = CreateAttendenceRegisterEvent;
}

@freezed
class AttendenceRegisterCreateState with _$AttendenceRegisterCreateState {
  const AttendenceRegisterCreateState._();

  const factory AttendenceRegisterCreateState({
    @Default(false) bool loading,
    AttendenceRegistersModel? attendenceRegistersModel,
  }) = _AttendenceRegisterCreateState;
}
