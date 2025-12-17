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
              "serviceCode": event.serviceCode,
              "referenceId": event.referenceId,
              "additionalDetails": {
                "orgName": event.orgName,
                "contractId": event.contractId,
                "attendanceRegisterNo": event.registerNo,
                "attendanceRegisterName": event.registerName,
                "projectName": event.projectName ?? "",
                "amount": event.amount ?? 14500,
                "projectType": event.projectType ?? '',
                "projectDesc": event.projectDesc ?? '',
                "locality": event.locality ?? '',
                "ward": event.ward ?? '',
                "orgId": GlobalVariables
                    .organisationListModel?.organisations?.first.id,
                "projectId": event.projectId ?? '',
                "executingAuthority": event.executingAuthority
              },
              "individualEntries": event.skillsList ?? []
            },
            "workflow": {"action": "SUBMIT", "comments": null}
          });
      if (musterRollsModel != null) {
        emit(MusterCreateState.loaded(musterRollsModel));
      } else {
        emit(const MusterCreateState.error());
      }
    } on DioException catch (e) {
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
              "startDate":event.startDate,
              "registerId":event.registerId,
              "tenantId": event.tenantId,
              "id": event.id,
              "additionalDetails": {"computeAttendance": "true"},
              "individualEntries": event.skillsList ?? []
            },
            "workflow": {
              "action": event.reSubmitAction ?? "RE-SUBMIT",
              "comments": null,
              "assignees": []
            }
          });
      if (musterRollsModel != null) {
        emit(MusterCreateState.loaded(musterRollsModel));
      } else {
        emit(const MusterCreateState.error());
      }
    } on DioException catch (e) {
      emit(const MusterCreateState.error());
    }
  }
}

@freezed
class MusterCreateEvent with _$MusterCreateEvent {
  const factory MusterCreateEvent.create(
      {required String tenantId,
      required String registerId,
      required String contractId,
      required String orgName,
      required String registerNo,
      required String registerName,
      required int startDate,
      String? serviceCode,
      String? referenceId,
      String? projectName,
      String? projectDesc,
      String? locality,
      String? projectId,
      String? projectType,
      String? ward,
      int? amount,
      String? executingAuthority,
      List<Map<String, dynamic>>? skillsList}) = CreateMusterEvent;
  const factory MusterCreateEvent.update(
      {required String tenantId,
      required String id,
      required String orgName,
      required String contractId,
      required String registerNo,
      required String registerName,
      required int startDate,
      required String registerId,
      String? reSubmitAction,
      List<Map<String, dynamic>>? skillsList}) = UpdateMusterEvent;
}

@freezed
class MusterCreateState with _$MusterCreateState {
  const MusterCreateState._();
  const factory MusterCreateState.initial() = _Initial;
  const factory MusterCreateState.loading() = _Loading;
  const factory MusterCreateState.loaded(MusterRollsModel? musterRollsModel) =
      _Loaded;
  const factory MusterCreateState.error() = _Error;
}
