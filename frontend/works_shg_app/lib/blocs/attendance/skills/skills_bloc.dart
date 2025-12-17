import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../data/repositories/attendance_mdms.dart';
import '../../../models/skills/skills.dart';
import '../../../utils/global_variables.dart';

part 'skills_bloc.freezed.dart';

typedef SkillsBlocEmitter = Emitter<SkillsBlocState>;

class SkillsBloc extends Bloc<SkillsBlocEvent, SkillsBlocState> {
  final AttendanceMDMSRepository mdmsRepository;
  SkillsBloc(super.initialState, this.mdmsRepository) {
    on<SkillsEvent>(_onSkillsMDMS);
  }

  FutureOr<void> _onSkillsMDMS(
    SkillsEvent event,
    SkillsBlocEmitter emit,
  ) async {
    try {
      emit(const SkillsBlocState.loading());
      SkillsList result = await mdmsRepository.skillsMDMS(
          apiEndPoint: Urls.initServices.mdmsSkill,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "WORKS-SOR",
              "masterDetails": [
                {"name": "SOR",
                  "filter": "[?(@.sorType =~ /.*L.*/i)]"
                 
                 },
              ],
            }
          ]);

      emit(SkillsBlocState.loaded(result));
    } on DioException catch (e) {
      emit(SkillsBlocState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SkillsBlocEvent with _$SkillsBlocEvent {
  const factory SkillsBlocEvent.onSkillsMDMS() = SkillsEvent;
}

@freezed
class SkillsBlocState with _$SkillsBlocState {
  const SkillsBlocState._();
  const factory SkillsBlocState.initial() = _Initial;
  const factory SkillsBlocState.loading() = _Loading;
  const factory SkillsBlocState.loaded(SkillsList? skillsList) = _Loaded;
  const factory SkillsBlocState.error(String? error) = _Error;
}