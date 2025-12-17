import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/remote/mdms.dart';
import '../../models/muster_rolls/muster_inbox_status.dart';
import '../../utils/global_variables.dart';

part 'muster_inbox_status_bloc.freezed.dart';

typedef MusterInboxStatusEmitter = Emitter<MusterInboxStatusState>;

class MusterInboxStatusBloc
    extends Bloc<MusterInboxStatusEvent, MusterInboxStatusState> {
  final MdmsRepository mdmsRepository;
  MusterInboxStatusBloc(super.initialState, this.mdmsRepository) {
    on<MusterInboxStatusEvent>(_onMusterInboxStatusMDMS);
  }

  FutureOr<void> _onMusterInboxStatusMDMS(
    MusterInboxStatusEvent event,
    MusterInboxStatusEmitter emit,
  ) async {
    try {
      emit(const MusterInboxStatusState.loading());
      MusterInboxStatusList result = await mdmsRepository.musterInboxStatus(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {
                  "name": "CBOMusterInboxConfig",
                },
              ],
            }
          ]);

      if (result.musterInboxStatus != null &&
          result.musterInboxStatus!.isNotEmpty) {
        emit(MusterInboxStatusState.loaded(
            result.musterInboxStatus?.first.reSubmitCode));
      } else {
        emit(const MusterInboxStatusState.error('MDMS_CONFIG_MISSING'));
      }
    } on DioException catch (e) {
      emit(MusterInboxStatusState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MusterInboxStatusMDMSEvent with _$MusterInboxStatusMDMSEvent {
  const factory MusterInboxStatusMDMSEvent.onMusterInboxStatusMDMS() =
      MusterInboxStatusEvent;
}

@freezed
class MusterInboxStatusState with _$MusterInboxStatusState {
  const MusterInboxStatusState._();
  const factory MusterInboxStatusState.initial() = _Initial;
  const factory MusterInboxStatusState.loading() = _Loading;
  const factory MusterInboxStatusState.loaded(String? sentBackToCBOCode) =
      _Loaded;
  const factory MusterInboxStatusState.error(String? error) = _Error;
}
