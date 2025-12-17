//mb_crud

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/employee_repository/estimate.dart';
import 'package:works_shg_app/models/employee/estimate/estimate_model.dart';

import '../../../data/remote_client.dart';
import '../../../services/urls.dart';
import '../../../utils/global_variables.dart';
part 'estimate.freezed.dart';

typedef EstimateBlocEventEmitter = Emitter<EstimateState>;

class EstimateBloc extends Bloc<EstimateBlocEvent, EstimateState> {
  EstimateBloc() : super(const EstimateState.initial()) {
    on<EstimateLoadBlocEvent>(loadEstimate);
  }
  FutureOr<void> loadEstimate(
    EstimateLoadBlocEvent event,
    EstimateBlocEventEmitter emit,
  ) async {
    Client client = Client();

    try {
      emit(const EstimateState.initial());
      final res = await EstimateRepository(client.init()).loadEstimate(
        url: Urls.estimateService.estimateSearch,
        queryParameters: {
          "tenantId": GlobalVariables.tenantId,
          "estimateNumber": event.roles,
        },
      );

      emit(
        EstimateState.loaded(
          res,
        ),
      );
    } on DioException catch (e) {
      emit(
        EstimateState.error(
          e.toString(),
        ),
      );
    }
  }
}

@freezed
class EstimateBlocEvent with _$EstimateBlocEvent {
  const factory EstimateBlocEvent.load({
    required String tenantId,
    required String roles,
    required bool isActive,
  }) = EstimateLoadBlocEvent;

  const factory EstimateBlocEvent.clear() = EstimateBlocClearEvent;
}

@freezed
class EstimateState with _$EstimateState {
  const EstimateState._();

  const factory EstimateState.initial() = _Initial;
  const factory EstimateState.loading() = _Loading;
  const factory EstimateState.loaded(
    EstimateDetailResponse? estimateDetailResponse,
  ) = _Loaded;
  const factory EstimateState.error(String? error) = _Error;
}
