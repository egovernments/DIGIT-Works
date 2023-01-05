import 'dart:async';
import 'dart:convert';

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

part 'create_attendence_register.freezed.dart';

typedef AttendenceRegisterCreateEmitter = Emitter<AttendenceRegisterCreateState>;

class AttendenceRegisterCreateBloc
    extends Bloc<AttendenceRegisterCreateEvent, AttendenceRegisterCreateState> {
  AttendenceRegisterCreateBloc() : super(const AttendenceRegisterCreateState()) {
    on<CreateAttendenceRegisterEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(
      AttendenceRegisterCreateEvent event, AttendenceRegisterCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    dynamic localUserDetails;
    String? accessToken;
print(kIsWeb);
    if (kIsWeb) {
      localUserDetails = html.window.localStorage['userRequest' ?? ''];
      accessToken = html.window.localStorage['accessToken' ?? ''];
    } else {
      localUserDetails = await storage.read(key: 'userRequest' ?? '');
      accessToken = await storage.read(key: 'accessToken' ?? '');
    }
    print(accessToken);
    print(await storage.read(key: 'accessToken' ?? ''));
print(accessToken.toString());
    AttendenceRegistersModel attendenceRegistersModel =
    await AttendenceRegisterRepository(client.init()).createAttendenceRegisters(
        url: Urls.attendenceRegisterServices.CreateAttendenceRegister,
        body: {
          "RequestInfo": {
            ...RequestInfoModel(
              apiId: 'asset-services',
              ver: ".01",
              ts: "",
              action: "_search",
              did: "1",
              key: "",
              msgId: "search with from and to values",
                authToken: "618ce212-ac49-4db9-94bd-63910e787a97"
            ).toJson(),
            "userInfo": jsonDecode(localUserDetails)
          },
          "attendanceRegister": [
            {
              "id": "",
              "tenantId": "pb.amritsar",
              "registerNumber": "",
              "name": "register800",
              "startDate": 1640995200000,
              "endDate": 1703980800000,
              "staff": [],
              "attendees": []
            }
          ]
        });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(attendenceRegistersModel: attendenceRegistersModel, loading: false));
  }
}

@freezed
class AttendenceRegisterCreateEvent with _$AttendenceRegisterCreateEvent {
  const factory AttendenceRegisterCreateEvent.search() = CreateAttendenceRegisterEvent;
}

@freezed
class AttendenceRegisterCreateState with _$AttendenceRegisterCreateState {
  const AttendenceRegisterCreateState._();

  const factory AttendenceRegisterCreateState({
    @Default(false) bool loading,
    AttendenceRegistersModel? attendenceRegistersModel,
  }) = _AttendenceRegisterCreateState;
}