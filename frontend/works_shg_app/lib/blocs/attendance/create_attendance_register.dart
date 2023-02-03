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

part 'create_attendance_register.freezed.dart';

typedef AttendanceRegisterCreateEmitter
    = Emitter<AttendanceRegisterCreateState>;

class AttendanceRegisterCreateBloc
    extends Bloc<AttendanceRegisterCreateEvent, AttendanceRegisterCreateState> {
  AttendanceRegisterCreateBloc()
      : super(const AttendanceRegisterCreateState()) {
    on<CreateAttendanceRegisterEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(AttendanceRegisterCreateEvent event,
      AttendanceRegisterCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    print(event);
    AttendanceRegistersModel attendanceRegistersModel =
        await AttendanceRegisterRepository(client.init())
            .createAttendanceRegisters(
                url: Urls.attendanceRegisterServices.createAttendanceRegister,
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
              "attendees": [],
              "additionalDetails": {
                "contractId": "WIN/2022-23/098/987",
                "contractCreatedByUUID": "e92e9650-dfea-4f41-863a-0ab1ecb098fb",
                "contractCreated": "XYZ",
                "orgName": "ABC"
              }
            }
          ]
        });
    await Future.delayed(const Duration(seconds: 2));
    emit(state.copyWith(
        attendanceRegistersModel: attendanceRegistersModel, loading: false));
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
  }
}

@freezed
class AttendanceRegisterCreateEvent with _$AttendanceRegisterCreateEvent {
  const factory AttendanceRegisterCreateEvent.create({
    required String tenantId,
    required String registerNumber,
    required String name,
    required int startDate,
    required int endDate,
  }) = CreateAttendanceRegisterEvent;
}

@freezed
class AttendanceRegisterCreateState with _$AttendanceRegisterCreateState {
  const AttendanceRegisterCreateState._();

  const factory AttendanceRegisterCreateState({
    @Default(false) bool loading,
    AttendanceRegistersModel? attendanceRegistersModel,
  }) = _AttendanceRegisterCreateState;
}
