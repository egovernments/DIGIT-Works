import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/org_repository/org_repository.dart';
import '../../models/wage_seeker/banking_details_model.dart';

part 'org_financial_bloc.freezed.dart';

typedef ORGFinanceEmitter = Emitter<ORGFinanceState>;

class ORGFinanceBloc extends Bloc<ORGFinanceEvent, ORGFinanceState> {
  ORGFinanceBloc() : super(const ORGFinanceState.initial()) {
    on<FinanceORGEvent>(_onSearch);
  }

  FutureOr<void> _onSearch(
      ORGFinanceEvent event, ORGFinanceEmitter emit) async {
    Client client = Client();
    emit(const ORGFinanceState.loading());
    BankingDetailsModel bankingDetailsModel = await ORGRepository(client.init())
        .searchORGFinance(url: Urls.orgServices.bfffinanceSearch, body: {
      "bankAccountDetails": {
        "tenantId": event.tenantId,
        "referenceId": [event.referenceId]
      },
      "pagination": {
        "limit": 20,
        "offSet": 0,
        "sortBy": "createdTime",
        "order": {}
      }
    });
    await Future.delayed(const Duration(seconds: 1));
    emit(ORGFinanceState.loaded(bankingDetailsModel));
  }
}

@freezed
class ORGFinanceEvent with _$ORGFinanceEvent {
  const factory ORGFinanceEvent.search(String referenceId, String tenantId) =
      FinanceORGEvent;
}

@freezed
class ORGFinanceState with _$ORGFinanceState {
  const ORGFinanceState._();
  const factory ORGFinanceState.initial() = _Initial;
  const factory ORGFinanceState.loading() = _Loading;
  const factory ORGFinanceState.loaded(
      BankingDetailsModel? bankingDetailsModel) = _Loaded;
  const factory ORGFinanceState.error() = _Error;
}
