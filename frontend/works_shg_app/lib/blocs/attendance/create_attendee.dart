import 'dart:async';

import 'package:dio/dio.dart';
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
  AttendeeCreateBloc() : super(const AttendeeCreateState.initial()) {
    on<CreateAttendeeEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(
      AttendeeCreateEvent event, AttendeeCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendeeCreateState.loading());
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
      if (attendeeModel != null) {
        emit(const AttendeeCreateState.loaded());
      } else {
        emit(const AttendeeCreateState.error());
      }
    } on DioError catch (e) {
      emit(const AttendeeCreateState.error());
    }
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

  const factory AttendeeCreateState.initial() = _Initial;
  const factory AttendeeCreateState.loading() = _Loading;
  const factory AttendeeCreateState.loaded() = _Loaded;
  const factory AttendeeCreateState.error() = _Error;
}
