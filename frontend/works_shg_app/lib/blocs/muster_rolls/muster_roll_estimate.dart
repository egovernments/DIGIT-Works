import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/muster_roll_repository/muster_roll.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';

part 'muster_roll_estimate.freezed.dart';

typedef MusterRollEstimateEmitter = Emitter<MusterRollEstimateState>;

class MusterRollEstimateBloc
    extends Bloc<MusterRollEstimateEvent, MusterRollEstimateState> {
  MusterRollEstimateBloc() : super(const MusterRollEstimateState()) {
    on<EstimateMusterRollEvent>(_onEstimate);
    on<ViewEstimateMusterRollEvent>(_onViewEstimate);
  }

  FutureOr<void> _onEstimate(
      EstimateMusterRollEvent event, MusterRollEstimateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    MusterRollsModel musterRollsModel =
        await MusterRollRepository(client.init()).searchMusterRolls(
            url: Urls.musterRollServices.musterRollsEstimate,
            body: {
              "musterRoll": {
                "tenantId": event.tenantId,
                "registerId": event.registerId,
                "startDate": event.startDate,
                "endDate": event.endDate
              }
            },
            options: Options(extra: {
              "accessToken": GlobalVariables.getAuthToken(),
              "apiId": "asset-services",
              "msgId": "search with from and to values"
            }));
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(loading: false, musterRollsModel: musterRollsModel));
  }

  FutureOr<void> _onViewEstimate(
      ViewEstimateMusterRollEvent event, MusterRollEstimateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    MusterRollsModel musterRollsModel =
        await MusterRollRepository(client.init()).searchMusterRolls(
            url: Urls.musterRollServices.musterRollsEstimate,
            body: {
              "musterRoll": {
                "tenantId": event.tenantId,
                "registerId": event.registerId,
                "startDate": event.startDate,
                "endDate": event.endDate
              }
            },
            options: Options(extra: {
              "accessToken": GlobalVariables.getAuthToken(),
              "apiId": "asset-services",
              "msgId": "search with from and to values"
            }));
    await Future.delayed(const Duration(seconds: 1));
    emit(
        state.copyWith(loading: false, viewMusterRollsModel: musterRollsModel));
  }
}

@freezed
class MusterRollEstimateEvent with _$MusterRollEstimateEvent {
  const factory MusterRollEstimateEvent.estimate({
    required int startDate,
    required int endDate,
    required String registerId,
    required String tenantId,
  }) = EstimateMusterRollEvent;
  const factory MusterRollEstimateEvent.viewEstimate({
    required int startDate,
    required int endDate,
    required String registerId,
    required String tenantId,
  }) = ViewEstimateMusterRollEvent;
}

@freezed
class MusterRollEstimateState with _$MusterRollEstimateState {
  const MusterRollEstimateState._();

  const factory MusterRollEstimateState({
    @Default(false) bool loading,
    MusterRollsModel? musterRollsModel,
    MusterRollsModel? viewMusterRollsModel,
  }) = _MusterRollEstimateState;
}
