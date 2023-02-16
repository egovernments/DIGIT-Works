import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/remote/mdms.dart';
import '../../models/mdms/attendance_hours.dart';
import '../../utils/constants.dart';

part 'attendance_hours_mdms.freezed.dart';

typedef AttendanceHoursEmitter = Emitter<AttendanceHoursState>;

class AttendanceHoursBloc
    extends Bloc<AttendanceHoursEvent, AttendanceHoursState> {
  final MdmsRepository mdmsRepository;
  AttendanceHoursBloc(super.initialState, this.mdmsRepository) {
    on<AttendanceHoursEvent>(_onAttendanceHoursMDMS);
  }

  FutureOr<void> _onAttendanceHoursMDMS(
    AttendanceHoursEvent event,
    AttendanceHoursEmitter emit,
  ) async {
    emit(state.copyWith(isAttendanceMDMSLoaded: false));
    AttendanceHoursList result = await mdmsRepository.attendanceHoursMDMS(
        apiEndPoint: Urls.initServices.mdms,
        tenantId: Constants.mdms_tenant_id,
        moduleDetails: [
          {
            "moduleName": "common-masters",
            "masterDetails": [
              {"name": "AttendanceHours", "filter": "[?(@.active==true)]"},
            ],
          }
        ]);

    if (result != null) {
      emit(state.copyWith(
          isAttendanceMDMSLoaded: true, attendanceHoursList: result));
    }
  }
}

@freezed
class AttendanceHoursMDMSEvent with _$AttendanceHoursMDMSEvent {
  const factory AttendanceHoursMDMSEvent.onAttendanceHoursMDMS() =
      AttendanceHoursEvent;
}

@freezed
class AttendanceHoursState with _$AttendanceHoursState {
  const AttendanceHoursState._();

  const factory AttendanceHoursState(
      {@Default(false) bool isAttendanceMDMSLoaded,
      AttendanceHoursList? attendanceHoursList}) = _AttendanceHoursState;
}
