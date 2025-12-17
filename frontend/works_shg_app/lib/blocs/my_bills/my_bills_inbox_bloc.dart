import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/my_bills_repository/my_bills_repo.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../models/my_bills/my_bills_inbox_config.dart';
import '../../utils/global_variables.dart';

part 'my_bills_inbox_bloc.freezed.dart';

typedef MyBillsInboxEmitter = Emitter<MyBillsInboxState>;

class MyBillInboxBloc extends Bloc<MyBillsInboxMDMSEvent, MyBillsInboxState> {
  MyBillInboxBloc() : super(const MyBillsInboxState.initial()) {
    on<MyBillsInboxMDMSEvent>(_onMyBillsInboxMDMS);
  }

  FutureOr<void> _onMyBillsInboxMDMS(
    MyBillsInboxMDMSEvent event,
    MyBillsInboxEmitter emit,
  ) async {
    try {
      Client client = Client();
      emit(const MyBillsInboxState.loading());
      MyBillsInboxConfigList result = await MyBillsRepository(client.init())
          .getMyBillsInboxConfig(
              apiEndPoint: Urls.initServices.mdms,
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              moduleDetails: [
            {
              "moduleName": "commonUiConfig",
              "masterDetails": [
                {
                  "name": "CBOBillInboxConfig",
                },
              ],
            }
          ]);

      if ((result.myBillsInboxConfig ?? []).isNotEmpty) {
        emit(MyBillsInboxState.loaded(
            result.myBillsInboxConfig.first.rejectedCode,
            result.myBillsInboxConfig.first.approvedCode));
      } else {
        emit(const MyBillsInboxState.error('MDMS_CONFIG_MISSING'));
      }
    } on DioException catch (e) {
      emit(MyBillsInboxState.error(e.response?.data['Errors'][0]['code']));
    }
  }
}

@freezed
class MyBillsInboxMDMSEvent with _$MyBillsInboxMDMSEvent {
  const factory MyBillsInboxMDMSEvent.onMyBillsInboxMDMS() = BillsInboxEvent;
}

@freezed
class MyBillsInboxState with _$MyBillsInboxState {
  const MyBillsInboxState._();
  const factory MyBillsInboxState.initial() = _Initial;
  const factory MyBillsInboxState.loading() = _Loading;
  const factory MyBillsInboxState.loaded(
      String? rejectCode, String? approvedCode) = _Loaded;
  const factory MyBillsInboxState.error(String? error) = _Error;
}
