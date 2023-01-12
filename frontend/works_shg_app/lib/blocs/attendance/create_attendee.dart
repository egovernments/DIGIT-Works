import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/attendance/attendee_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/attendence_register.dart';
import '../../utils/global_variables.dart';

part 'create_attendee.freezed.dart';

typedef AttendeeCreateEmitter = Emitter<AttendeeCreateState>;

class AttendeeCreateBloc
    extends Bloc<AttendeeCreateEvent, AttendeeCreateState> {
  AttendeeCreateBloc() : super(const AttendeeCreateState()) {
    on<CreateAttendeeEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(
      AttendeeCreateEvent event, AttendeeCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    print(event);
    AttendeeModel attendeeModel =
        await AttendanceRegisterRepository(client.init()).createAttendee(
            url: Urls.attendanceRegisterServices.createAttendee,
            options: Options(extra: {
              "accessToken": GlobalVariables.getAuthToken(),
              "apiId": "mukta-services",
              "msgId": "Enroll attendee to register",
            }),
            body: {"attendees": event.attendeeList});
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(attendeeModel: attendeeModel, loading: false));
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
  }
}

@freezed
class AttendeeCreateEvent with _$AttendeeCreateEvent {
  const factory AttendeeCreateEvent.create(
      {required List<Map<String, dynamic>> attendeeList}) = CreateAttendeeEvent;
}

@freezed
class AttendeeCreateState with _$AttendeeCreateState {
  const AttendeeCreateState._();

  const factory AttendeeCreateState({
    @Default(false) bool loading,
    AttendeeModel? attendeeModel,
  }) = _AttendeeCreateState;
}
