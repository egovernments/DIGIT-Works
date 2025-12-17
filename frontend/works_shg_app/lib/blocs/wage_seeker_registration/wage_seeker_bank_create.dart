import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/wage_seeker_repository/wage_seeker_repository.dart';
import '../../models/wage_seeker/banking_details_model.dart';

part 'wage_seeker_bank_create.freezed.dart';

typedef WageSeekerBankCreateEmitter = Emitter<WageSeekerBankCreateState>;

class WageSeekerBankCreateBloc
    extends Bloc<WageSeekerBankCreateEvent, WageSeekerBankCreateState> {
  WageSeekerBankCreateBloc()
      : super(const WageSeekerBankCreateState.initial()) {
    on<CreateBankWageSeekerEvent>(_onCreate);
    on<CreateBankWageSeekerDisposeEvent>(_onDispose);
  }

  FutureOr<void> _onCreate(
      CreateBankWageSeekerEvent event, WageSeekerBankCreateEmitter emit) async {
    Client client = Client();
    try {
      emit(const WageSeekerBankCreateState.loading());
      BankingDetailsModel bankingDetailsModel =
          await WageSeekerRepository(client.init())
              .createBanking(url: Urls.wageSeekerServices.bankCreate, body: {
        "bankAccounts": [
          {
            "tenantId": event.tenantId,
            "serviceCode": "IND",
            "referenceId": event.referenceId,
            "bankAccountDetails": [
              {
                "tenantId": event.tenantId,
                "accountHolderName": event.accountHolderName?.trim().toString(),
                "accountNumber": event.accountNo?.trim().toString(),
                "accountType": event.accountType,
                "isPrimary": true,
                "bankBranchIdentifier": {
                  "type": "IFSC",
                  "code": event.ifscCode,
                  "additionalDetails": {
                    "ifsccode": event.bankName?.trim().toString()
                  }
                },
                "isActive": true,
                "documents": [],
              }
            ],
          }
        ]
      });

      await Future.delayed(const Duration(seconds: 1));
      emit(WageSeekerBankCreateState.loaded(
          bankingDetailsModel,
          bankingDetailsModel.bankAccounts?.first
              .copyWith(indID: event.indId)));
    } on DioException catch (e) {
      emit(WageSeekerBankCreateState.error(
          e.response?.data['Errors'][0]['code']));
    }
  }

  FutureOr<void> _onDispose(CreateBankWageSeekerDisposeEvent event,
      WageSeekerBankCreateEmitter emit) async {
    emit(const WageSeekerBankCreateState.initial());
  }
}

@freezed
class WageSeekerBankCreateEvent with _$WageSeekerBankCreateEvent {
  const factory WageSeekerBankCreateEvent.create(
      {String? tenantId,
      String? accountHolderName,
      String? accountNo,
      String? accountType,
      String? ifscCode,
      String? referenceId,
      String? indId,
      String? bankName}) = CreateBankWageSeekerEvent;
  const factory WageSeekerBankCreateEvent.dispose() =
      CreateBankWageSeekerDisposeEvent;
}

@freezed
class WageSeekerBankCreateState with _$WageSeekerBankCreateState {
  const WageSeekerBankCreateState._();

  const factory WageSeekerBankCreateState.initial() = _Initial;
  const factory WageSeekerBankCreateState.loading() = _Loading;
  const factory WageSeekerBankCreateState.loaded(
      BankingDetailsModel? bankingDetailsModel,
      BankAccounts? bankAccounts) = _Loaded;
  const factory WageSeekerBankCreateState.error(String? error) = _Error;
}
