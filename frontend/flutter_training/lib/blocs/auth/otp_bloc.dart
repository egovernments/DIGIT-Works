import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/auth_repository/auth.dart';
import '../../services/urls.dart';
import '../../utils/global_variables.dart';

part 'otp_bloc.freezed.dart';

typedef OTPBlocEmitter = Emitter<OTPBlocState>;

class OTPBloc extends Bloc<OTPBlocEvent, OTPBlocState> {
  OTPBloc() : super(const OTPBlocState.initial()) {
    on<OTPSendEvent>(_onSendOTP);
    on<DisposeOTPEvent>(_onDispose);
  }

  FutureOr<void> _onSendOTP(OTPSendEvent event, OTPBlocEmitter emit) async {
    Client client = Client();

    try {
      emit(const OTPBlocState.loading());
      await AuthRepository(client.init())
          .sendOTP(url: Urls.userServices.sendOtp, body: {
        "otp": {
          "mobileNumber": event.mobileNumber,
          "tenantId": GlobalVariables.stateInfoListModel?.code,
          "type": "login",
          "userType": 'citizen'
        }
      });
      emit(const OTPBlocState.loaded());
    } on DioError catch (e) {
      emit(const OTPBlocState.error());
    }
  }

  FutureOr<void> _onDispose(DisposeOTPEvent event, OTPBlocEmitter emit) async {
    emit(const OTPBlocState.initial());
  }
}

@freezed
class OTPBlocEvent with _$OTPBlocEvent {
  const factory OTPBlocEvent.login({
    required String mobileNumber,
  }) = OTPSendEvent;
  const factory OTPBlocEvent.dispose() = DisposeOTPEvent;
}

@freezed
class OTPBlocState with _$OTPBlocState {
  const OTPBlocState._();
  const factory OTPBlocState.initial() = _Initial;
  const factory OTPBlocState.loading() = _Loading;
  const factory OTPBlocState.loaded() = _Loaded;
  const factory OTPBlocState.error() = _Error;
}
