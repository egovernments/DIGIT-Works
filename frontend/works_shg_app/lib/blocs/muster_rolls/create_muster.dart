import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/muster_roll_repository/muster_roll.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../models/muster_rolls/muster_roll_model.dart';
import '../../utils/global_variables.dart';

part 'create_muster.freezed.dart';

typedef MusterCreateEmitter = Emitter<MusterCreateState>;

class MusterCreateBloc extends Bloc<MusterCreateEvent, MusterCreateState> {
  MusterCreateBloc() : super(const MusterCreateState.initial()) {
    on<CreateMusterEvent>(_onCreate);
    on<UpdateMusterEvent>(_onUpdate);
  }

  FutureOr<void> _onCreate(
      CreateMusterEvent event, MusterCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterCreateState.loading());
      MusterRollsModel musterRollsModel =
          await MusterRollRepository(client.init()).createMuster(
              url: Urls.musterRollServices.createMuster,
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken
              }),
              body: {
            "musterRoll": {
              "tenantId": event.tenantId,
              "registerId": event.registerId,
              "startDate": event.startDate,
              "additionalDetails": {
                "orgName": event.orgName,
                "contractId": event.contractId,
                "attendanceRegisterNo": event.registerNo,
                "attendanceRegisterName": event.registerName
              }
            },
            "workflow": {"action": "SUBMIT", "comments": "Submit muster roll"}
          });
      if (musterRollsModel != null) {
        emit(const MusterCreateState.loaded());
      } else {
        emit(const MusterCreateState.error());
      }
    } on DioError catch (e) {
      emit(const MusterCreateState.error());
    }
  }

  FutureOr<void> _onUpdate(
      UpdateMusterEvent event, MusterCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const MusterCreateState.loading());
      MusterRollsModel musterRollsModel =
          await MusterRollRepository(client.init()).createMuster(
              url: Urls.musterRollServices.updateMuster,
              options: Options(extra: {
                "userInfo": GlobalVariables.userRequestModel,
                "accessToken": GlobalVariables.authToken
              }),
              body: {
            "musterRoll": {
              "tenantId": event.tenantId,
              "id": event.id,
              "additionalDetails": {
                "orgName": event.orgName,
                "contractId": event.contractId,
                "attendanceRegisterNo": event.registerNo,
                "attendanceRegisterName": event.registerName
              }
            },
            "workflow": {
              "action": "RESUBMIT",
              "comments": "Resubmit muster roll",
              "assignees": [GlobalVariables.uuid]
            }
          });
      if (musterRollsModel != null) {
        emit(const MusterCreateState.loaded());
      } else {
        emit(const MusterCreateState.error());
      }
    } on DioError catch (e) {
      emit(const MusterCreateState.error());
    }
  }
}

@freezed
class MusterCreateEvent with _$MusterCreateEvent {
  const factory MusterCreateEvent.create({
    required String tenantId,
    required String registerId,
    required String contractId,
    required String orgName,
    required String registerNo,
    required String registerName,
    required int startDate,
  }) = CreateMusterEvent;
  const factory MusterCreateEvent.update({
    required String tenantId,
    required String id,
    required String orgName,
    required String contractId,
    required String registerNo,
    required String registerName,
  }) = UpdateMusterEvent;
}

@freezed
class MusterCreateState with _$MusterCreateState {
  const MusterCreateState._();
  const factory MusterCreateState.initial() = _Initial;
  const factory MusterCreateState.loading() = _Loading;
  const factory MusterCreateState.loaded() = _Loaded;
  const factory MusterCreateState.error() = _Error;
}
