//mb_detail_view

import 'dart:async';
import 'dart:core';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/employee_repository/mdms.dart';
import 'package:works_shg_app/models/employee/mb/mb_project_type.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../data/remote_client.dart';
import '../../../services/urls.dart';

part 'project_type.freezed.dart';

typedef ProjectTypeBlocEventEmitter = Emitter<ProjectTypeState>;

class ProjectTypeBloc extends Bloc<ProjectTypeBlocEvent, ProjectTypeState> {
  ProjectTypeBloc() : super(const ProjectTypeState.initial()) {
    on<ProjectTypeEvent>(checkMB);
  }
  FutureOr<void> checkMB(
    ProjectTypeEvent event,
    ProjectTypeBlocEventEmitter emit,
  ) async {
    Client client = Client();
    try {
      emit(const ProjectTypeState.loading());

      final MBProjectType res =
          await MBMDMSRepository(client.init()).fetchProjectType(
        url: Urls.initServices.mdms,
        body: {
          "MdmsCriteria": {
            "tenantId": GlobalVariables.tenantId??"od",
            "moduleDetails": [
              {
                "moduleName": "works",
                "masterDetails": [
                  {"name": "ProjectType"}
                ]
              }
            ]
          }
        },
      );
      

      emit(
        ProjectTypeState.loaded(res),
      );
    } on DioException catch (e) {
      // emit(MeasurementInboxState.error(e.response?.data['Errors'][0]['code']));
      emit(ProjectTypeState.error(e.toString()));
    }
  }
}

@freezed
class ProjectTypeBlocEvent with _$ProjectTypeBlocEvent {
  const factory ProjectTypeBlocEvent.create({
    required String tenantId,
  }) = ProjectTypeEvent;

  const factory ProjectTypeBlocEvent.clear() = ProjectTypeBlocClearEvent;
}

@freezed
class ProjectTypeState with _$ProjectTypeState {
  const ProjectTypeState._();

  const factory ProjectTypeState.initial() = _Initial;
  const factory ProjectTypeState.loading() = _Loading;
  const factory ProjectTypeState.loaded(
    MBProjectType? mbProjectType,
  ) = _Loaded;
  const factory ProjectTypeState.error(String? error) = _Error;
}
