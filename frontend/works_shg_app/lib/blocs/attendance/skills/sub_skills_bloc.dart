import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../../data/repositories/attendance_mdms.dart';
import '../../../models/skills/sub_skills.dart';
import '../../../utils/global_variables.dart';

part 'sub_skills_bloc.freezed.dart';

typedef SubSkillsBlocEmitter = Emitter<SubSkillsBlocState>;

class SubSkillsBloc extends Bloc<SubSkillsBlocEvent, SubSkillsBlocState> {
  final AttendanceMDMSRepository mdmsRepository;
  SubSkillsBloc(super.initialState, this.mdmsRepository) {
    on<SubSkillsEvent>(_onSubSkillsMDMS);
  }

  FutureOr<void> _onSubSkillsMDMS(
    SubSkillsEvent event,
    SubSkillsBlocEmitter emit,
  ) async {
    try {
      emit(const SubSkillsBlocState.loading());
      SubSkillsList result = await mdmsRepository.subSkillsMDMS(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "common-masters",
              "masterDetails": [
                {
                  "name": "WageSeekerSubSkills",
                  "filter": "[?(@.active==true)]"
                },
              ],
            }
          ]);

      emit(SubSkillsBlocState.loaded(result));
    } on DioError catch (e) {
      emit(SubSkillsBlocState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class SubSkillsBlocEvent with _$SubSkillsBlocEvent {
  const factory SubSkillsBlocEvent.onSubSkillsMDMS() = SubSkillsEvent;
}

@freezed
class SubSkillsBlocState with _$SubSkillsBlocState {
  const SubSkillsBlocState._();
  const factory SubSkillsBlocState.initial() = _Initial;
  const factory SubSkillsBlocState.loading() = _Loading;
  const factory SubSkillsBlocState.loaded(SubSkillsList? subSkillsList) =
      _Loaded;
  const factory SubSkillsBlocState.error(String? error) = _Error;
}
