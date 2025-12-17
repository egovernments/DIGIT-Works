import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/attendance/attendee_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/attendence_repository/attendence_register.dart';
import '../../utils/global_variables.dart';

part 'de_enroll_attendee.freezed.dart';

typedef AttendeeDeEnrollEmitter = Emitter<AttendeeDeEnrollState>;

class AttendeeDeEnrollBloc
    extends Bloc<AttendeeDeEnrollEvent, AttendeeDeEnrollState> {
  AttendeeDeEnrollBloc() : super(const AttendeeDeEnrollState.initial()) {
    on<DeEnrollAttendeeEvent>(_onDeEnroll);
    on<DeEnrollAttendeeDisposeEvent>(_onDispose);
  }

  FutureOr<void> _onDeEnroll(
      DeEnrollAttendeeEvent event, AttendeeDeEnrollEmitter emit) async {
    Client client = Client();
    try {
      emit(const AttendeeDeEnrollState.loading());
      AttendeeModel attendeeModel =
          await AttendanceRegisterRepository(client.init()).deEnrollAttendee(
              url: Urls.attendanceRegisterServices.deEnrollAttendee,
              options: Options(extra: {
                "accessToken": GlobalVariables.authToken,
                "apiId": "mukta-services",
              }),
              body: {"attendees": event.attendeeList});
      await Future.delayed(const Duration(seconds: 1));
      emit( AttendeeDeEnrollState.loaded(uuid: event.uuid));
    } on DioException catch (e) {
      emit(AttendeeDeEnrollState.error(e.response?.data['Errors'][0]['code'], uuid: event.uuid,));
    }
  }

  FutureOr<void> _onDispose(
      AttendeeDeEnrollEvent event, AttendeeDeEnrollEmitter emit) async {
    emit(const AttendeeDeEnrollState.initial());
  }
}

@freezed
class AttendeeDeEnrollEvent with _$AttendeeDeEnrollEvent {
  const factory AttendeeDeEnrollEvent.deEnroll(
          {required List<Map<String, dynamic>> attendeeList,
          required String uuid,
          }
          ) =
      DeEnrollAttendeeEvent;
  const factory AttendeeDeEnrollEvent.dispose() = DeEnrollAttendeeDisposeEvent;
}

@freezed
class AttendeeDeEnrollState with _$AttendeeDeEnrollState {
  const AttendeeDeEnrollState._();

  const factory AttendeeDeEnrollState.initial() = _Initial;
  const factory AttendeeDeEnrollState.loading() = _Loading;
  const factory AttendeeDeEnrollState.loaded(
    {required String uuid,}
  ) = _Loaded;
  const factory AttendeeDeEnrollState.error(String? error, {required String uuid}) = _Error;
}
