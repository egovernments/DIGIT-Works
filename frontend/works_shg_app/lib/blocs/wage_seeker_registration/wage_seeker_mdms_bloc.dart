import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/models/skills/skills.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/common_repository/common_repository.dart';
import '../../data/repositories/remote/mdms.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../utils/global_variables.dart';

part 'wage_seeker_mdms_bloc.freezed.dart';

typedef WageSeekerMDMSEmitter = Emitter<WageSeekerMDMSState>;

class WageSeekerMDMSBloc
    extends Bloc<WageSeekerMDMSEvent, WageSeekerMDMSState> {
  final MdmsRepository mdmsRepository;
  final CommonRepository commonRepository;
  WageSeekerMDMSBloc(
      super.initialState, this.mdmsRepository, this.commonRepository) {
    on<WageSeekerMDMSEvent>(_onWageSeekerMDMS);
  }

  FutureOr<void> _onWageSeekerMDMS(
    WageSeekerMDMSEvent event,
    WageSeekerMDMSEmitter emit,
  ) async {
    try {
      emit(const WageSeekerMDMSState.loading());
      WageSeekerMDMS result = await mdmsRepository.wageSeekerMDMS(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "common-masters",
              "masterDetails": [
                {"name": "GenderType", "filter": "[?(@.active==true)]"},
                {"name": "Relationship", "filter": "[?(@.active==true)]"},
                {"name": "SocialCategory", "filter": "[?(@.active==true)]"},
              ],
            },
            {
              "moduleName": "works",
              "masterDetails": [
                {"name": "BankAccType", "filter": "[?(@.active==true)]"},
              ],
            },
            {
              "moduleName": "tenant",
              "masterDetails": [
                {
                  "name": "citymodule",
                  "filter": "[?(@.active==true && @.module=='Works')]"
                },
              ],
            }
          ]);
//{"name": "WageSeekerSkills", "filter": "[?(@.active==true)]"},
      List<WageSeekerSkills> skillResult = await mdmsRepository.skillsMDMS(
          apiEndPoint: Urls.initServices.mdmsSkill,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "WORKS-SOR",
              "masterDetails": [
                {
                  "name": "SOR",
                  "filter": "[?(@.sorType =~ /.*L.*/i)]"
                },
              ],
            }
          ]);

      final updatedCommonMDMS = result.copyWith(
          commonMDMS:
              result.commonMDMS?.copyWith(wageSeekerSkills: skillResult));

      if (updatedCommonMDMS != null) {
        emit(WageSeekerMDMSState.loaded(updatedCommonMDMS));
      }
    } on DioException catch (e) {
      emit(WageSeekerMDMSState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class WageSeekerMDMSMDMSEvent with _$WageSeekerMDMSMDMSEvent {
  const factory WageSeekerMDMSMDMSEvent.onWageSeekerMDMSMDMS() =
      WageSeekerMDMSEvent;
}

@freezed
class WageSeekerMDMSState with _$WageSeekerMDMSState {
  const WageSeekerMDMSState._();
  const factory WageSeekerMDMSState.initial() = _Initial;
  const factory WageSeekerMDMSState.loading() = _Loading;
  const factory WageSeekerMDMSState.loaded(WageSeekerMDMS? wageSeekerMDMS) =
      _Loaded;
  const factory WageSeekerMDMSState.error(String? error) = _Error;
}