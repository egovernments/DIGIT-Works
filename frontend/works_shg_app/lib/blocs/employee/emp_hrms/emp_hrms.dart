//mb_crud

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/employee_repository/emp_hrms.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../data/remote_client.dart';
import '../../../models/employee/mb/role_based_hrms.dart';
part 'emp_hrms.freezed.dart';

typedef EmpHRMSBlocEventEmitter = Emitter<EmpHRMsState>;

class EmpHRMSBloc extends Bloc<EmpHRMSBlocEvent, EmpHRMsState> {
  EmpHRMSBloc() : super(const EmpHRMsState.initial()) {
    on<EmpHRMSLoadBlocEvent>(loadHrmsEmployee);
  }
  FutureOr<void> loadHrmsEmployee(
    EmpHRMSLoadBlocEvent event,
    EmpHRMSBlocEventEmitter emit,
  ) async {
    Client client = Client();

    try {
      emit(const EmpHRMsState.initial());
      final res = await EmpHRMSRepository(client.init()).loadHrmsEmployee(
          url: Urls.empHrms.leadHrmsEmployee,
          queryParameters: {
            "roles": event.roles.toString(),
            "isActive": true,
            "tenantId": GlobalVariables.tenantId,
          });

      emit(
        EmpHRMsState.loaded(
          res.employees,
        ),
      );
    } on DioException catch (e) {
      emit(
        EmpHRMsState.error(
          e.toString(),
        ),
      );
    }
  }
}

@freezed
class EmpHRMSBlocEvent with _$EmpHRMSBlocEvent {
  const factory EmpHRMSBlocEvent.load({
    required String tenantId,
    required String roles,
    required bool isActive,
  }) = EmpHRMSLoadBlocEvent;

  const factory EmpHRMSBlocEvent.clear() = EmpHRMSBlocClearEvent;
}

@freezed
class EmpHRMsState with _$EmpHRMsState {
  const EmpHRMsState._();

  const factory EmpHRMsState.initial() = _Initial;
  const factory EmpHRMsState.loading() = _Loading;
  const factory EmpHRMsState.loaded(
    List<HRMSEmployee>? hrmsEmployee,
  ) = _Loaded;
  const factory EmpHRMsState.error(String? error) = _Error;
}
