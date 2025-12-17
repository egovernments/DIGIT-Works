import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/repositories/remote/mdms.dart';
import '../../models/attendance/muster_submission.dart';
import '../../utils/global_variables.dart';

part 'muster_submission_mdms.freezed.dart';

typedef MusterSubmissionEmitter = Emitter<MusterSubmissionState>;

class MusterSubmissionBloc
    extends Bloc<MusterSubmissionEvent, MusterSubmissionState> {
  final MdmsRepository mdmsRepository;
  MusterSubmissionBloc(super.initialState, this.mdmsRepository) {
    on<MusterSubmissionEvent>(_onMusterSubmissionMDMS);
  }

  FutureOr<void> _onMusterSubmissionMDMS(
    MusterSubmissionEvent event,
    MusterSubmissionEmitter emit,
  ) async {
    try {
      emit(const MusterSubmissionState.loading());
      MusterSubmissionList result = await mdmsRepository.musterSubmissionMDMS(
          apiEndPoint: Urls.initServices.mdms,
          tenantId: GlobalVariables
              .globalConfigObject!.globalConfigs!.stateTenantId
              .toString(),
          moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {
                  "name": "CBOMusterSubmission",
                  "filter": "[?(@.active==true)]"
                },
              ],
            }
          ]);

      if (result.musterSubmission != null &&
          result.musterSubmission!.isNotEmpty) {
        emit(MusterSubmissionState.loaded(result.musterSubmission
                ?.where((e) => e.code == 'END_OF_WEEK')
                .first
                .active ??
            false));
      } else {
        emit(const MusterSubmissionState.loaded(false));
      }
    } on DioException catch (e) {
      emit(MusterSubmissionState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MusterSubmissionMDMSEvent with _$MusterSubmissionMDMSEvent {
  const factory MusterSubmissionMDMSEvent.onMusterSubmissionMDMS() =
      MusterSubmissionEvent;
}

@freezed
class MusterSubmissionState with _$MusterSubmissionState {
  const MusterSubmissionState._();
  const factory MusterSubmissionState.initial() = _Initial;
  const factory MusterSubmissionState.loading() = _Loading;
  const factory MusterSubmissionState.loaded(bool endOfWeek) = _Loaded;
  const factory MusterSubmissionState.error(String? error) = _Error;
}
