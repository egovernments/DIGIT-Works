import 'dart:async';

import 'package:dio/dio.dart';
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
      : super(const AttendanceRegisterCreateState.initial()) {
    on<CreateAttendanceRegisterEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(AttendanceRegisterCreateEvent event,
      AttendanceRegisterCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendanceRegisterCreateState.initial());
      emit(const AttendanceRegisterCreateState.loading());
      AttendanceRegistersModel attendanceRegistersModel =
          await AttendanceRegisterRepository(client.init())
              .createAttendanceRegisters(
                  url: Urls.attendanceRegisterServices.createAttendanceRegister,
                  options: Options(extra: {
                    "userInfo": GlobalVariables.userRequestModel,
                    "accessToken": GlobalVariables.authToken
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
                  "contractId": event.contractId,
                  "contractCreatedByUUID": GlobalVariables.uuid,
                  "contractCreated": event.contractCreated,
                  "orgName": event.orgName
                }
              }
            ]
          });
      if (attendanceRegistersModel != null) {
        emit(AttendanceRegisterCreateState.loaded(attendanceRegistersModel
            .attendanceRegister!.first.registerNumber
            .toString()));
      } else {
        emit(const AttendanceRegisterCreateState.error());
      }
    } on DioException catch (e) {
      emit(const AttendanceRegisterCreateState.error());
    }
  }
}

@freezed
class AttendanceRegisterCreateEvent with _$AttendanceRegisterCreateEvent {
  const factory AttendanceRegisterCreateEvent.create({
    required String tenantId,
    required String registerNumber,
    required String contractId,
    required String contractCreated,
    required String orgName,
    required String name,
    required int startDate,
    required int endDate,
  }) = CreateAttendanceRegisterEvent;
}

@freezed
class AttendanceRegisterCreateState with _$AttendanceRegisterCreateState {
  const AttendanceRegisterCreateState._();
  const factory AttendanceRegisterCreateState.initial() = _Initial;
  const factory AttendanceRegisterCreateState.loading() = _Loading;
  const factory AttendanceRegisterCreateState.loaded(String? registerNumber) =
      _Loaded;
  const factory AttendanceRegisterCreateState.error() = _Error;
}
