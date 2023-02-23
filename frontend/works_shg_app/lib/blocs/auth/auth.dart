import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/models/UserDetails/user_details_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/auth_repository/auth.dart';
import '../../services/local_storage.dart';

part 'auth.freezed.dart';

typedef AuthEmitter = Emitter<AuthState>;

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  AuthBloc() : super(const AuthState.initial()) {
    on<AuthLoginEvent>(_onLogin);
    on<AuthLogoutEvent>(_onLogout);
  }

  FutureOr<void> _onLogin(AuthLoginEvent event, AuthEmitter emit) async {
    Client client = Client();

    try {
      emit(const AuthState.loading());
      UserDetailsModel userDetailsModel = await AuthRepository(client.init())
          .validateLogin(url: Urls.userServices.authenticate, body: {
        "username": event.userId.toString(),
        "password": event.password.toString(),
        "userType": 'CITIZEN',
        "tenantId":
            GlobalVariables.globalConfigObject?.globalConfigs?.stateTenantId,
        "scope": "read",
        "grant_type": "password"
      });
      await Future.delayed(const Duration(seconds: 1));
      GlobalVariables.authToken = userDetailsModel.access_token;
      GlobalVariables.uuid = userDetailsModel.userRequestModel?.uuid;
      GlobalVariables.userRequestModel =
          jsonDecode(jsonEncode(userDetailsModel.userRequestModel));
      if (kIsWeb) {
        html.window.localStorage['accessToken' ?? ''] =
            jsonEncode(userDetailsModel.access_token);
        html.window.localStorage['userRequest' ?? ''] =
            jsonEncode(userDetailsModel.userRequestModel);
        html.window.localStorage['uuid' ?? ''] =
            jsonEncode(userDetailsModel.userRequestModel?.uuid);
        html.window.localStorage['mobileNumber' ?? ''] =
            jsonEncode(userDetailsModel.userRequestModel?.mobileNumber);
      } else {
        await storage.write(
            key: 'accessToken' ?? '',
            value: jsonEncode(userDetailsModel.access_token));
        await storage.write(
            key: 'userRequest' ?? '',
            value: jsonEncode(userDetailsModel.userRequestModel));
        await storage.write(
            key: 'uuid' ?? '',
            value: jsonEncode(userDetailsModel.userRequestModel?.uuid));
        await storage.write(
            key: 'mobileNumber' ?? '',
            value: jsonEncode(userDetailsModel.userRequestModel?.mobileNumber));
      }
      if (userDetailsModel != null) {
        emit(AuthState.loaded(
            userDetailsModel, userDetailsModel.access_token.toString()));
      } else {
        emit(const AuthState.error());
      }
    } on DioError catch (e) {
      emit(const AuthState.error());
    }
  }

  FutureOr<void> _onLogout(AuthLogoutEvent event, AuthEmitter emit) async {
    emit(const AuthState.loaded(null, null));
    emit(const AuthState.initial());
  }
}

@freezed
class AuthEvent with _$AuthEvent {
  const factory AuthEvent.login({
    required String userId,
    required String password,
  }) = AuthLoginEvent;

  const factory AuthEvent.logout() = AuthLogoutEvent;
}

@freezed
class AuthState with _$AuthState {
  const AuthState._();
  const factory AuthState.initial() = _Initial;
  const factory AuthState.loading() = _Loading;
  const factory AuthState.loaded(
      UserDetailsModel? userDetailsModel, String? accessToken) = _Loaded;
  const factory AuthState.error() = _Error;
}
