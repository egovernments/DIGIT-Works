import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/remote/mdms.dart';
import '../../models/mdms/attendance_hours.dart';
import '../../utils/global_variables.dart';

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
    try {
      emit(const AttendanceHoursState.loading());
      AttendanceHoursList result = await mdmsRepository.attendanceHoursMDMS(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "common-masters",
              "masterDetails": [
                {"name": "AttendanceHours", "filter": "[?(@.active==true)]"},
              ],
            }
          ]);

      emit(AttendanceHoursState.loaded(result));
    } on DioException catch (e) {
      emit(AttendanceHoursState.error(e.response?.data['Errors'][0]['code']));
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
  const factory AttendanceHoursState.initial() = _Initial;
  const factory AttendanceHoursState.loading() = _Loading;
  const factory AttendanceHoursState.loaded(
      AttendanceHoursList? attendanceHoursList) = _Loaded;
  const factory AttendanceHoursState.error(String? error) = _Error;
}
