import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
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
  MusterCreateBloc() : super(const MusterCreateState()) {
    on<CreateMusterEvent>(_onCreate);
  }

  FutureOr<void> _onCreate(
      CreateMusterEvent event, MusterCreateEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));
    MusterRollsModel musterRollsModel =
        await MusterRollRepository(client.init()).createMuster(
            url: Urls.musterRollServices.createMuster,
            options: Options(extra: {
              "userInfo": GlobalVariables.getUserInfo(),
              "accessToken": GlobalVariables.getAuthToken()
            }),
            body: {
          "musterRoll": {
            "tenantId": event.tenantId,
            "registerId": event.registerId,
            "startDate": event.startDate,
          },
          "workflow": {"action": "SUBMIT", "comments": "Submit muster roll"}
        });
    await Future.delayed(const Duration(seconds: 2));
    emit(state.copyWith(musterRollsModel: musterRollsModel, loading: false));
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
  }
}

@freezed
class MusterCreateEvent with _$MusterCreateEvent {
  const factory MusterCreateEvent.create({
    required String tenantId,
    required String registerId,
    required int startDate,
  }) = CreateMusterEvent;
}

@freezed
class MusterCreateState with _$MusterCreateState {
  const MusterCreateState._();

  const factory MusterCreateState({
    @Default(false) bool loading,
    MusterRollsModel? musterRollsModel,
  }) = _MusterCreateState;
}
