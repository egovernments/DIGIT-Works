import 'dart:async';
import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/models/UserDetails/user_details_model.dart';
import 'package:works_shg_app/services/urls.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/auth_repository/auth.dart';
import '../../services/local_storage.dart';

part 'auth.freezed.dart';

typedef AuthEmitter = Emitter<AuthState>;

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  AuthBloc(super.initialState) {
    on<AuthLoginEvent>(_onLogin);
    on<AuthLogoutEvent>(_onLogout);
  }

  FutureOr<void> _onLogin(AuthLoginEvent event, AuthEmitter emit) async {
    Client client = Client();
    emit(state.copyWith(loading: true));

    UserDetailsModel userDetailsModel = await AuthRepository(client.init())
        .validateLogin(url: Urls.userServices.authenticate, body: {
      "username": event.userId.toString(),
      "password": event.password.toString(),
      "userType": 'CITIZEN',
      "tenantId": 'pb.amritsar',
      "scope": "read",
      "grant_type": "password"
    });
    await Future.delayed(const Duration(seconds: 1));
    dynamic localAuthDetails;
    if (kIsWeb) {
      html.window.localStorage['accessToken' ?? ''] =
          jsonEncode(userDetailsModel.access_token);
      html.window.localStorage['userRequest' ?? ''] =
          jsonEncode(userDetailsModel.userRequestModel);
      html.window.localStorage['uuid' ?? ''] =
          jsonEncode(userDetailsModel.userRequestModel?.uuid);
      html.window.localStorage['tenantId' ?? ''] =
          jsonEncode(userDetailsModel.userRequestModel?.tenantId);
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
          key: 'tenantId' ?? '',
          value: jsonEncode(userDetailsModel.userRequestModel?.tenantId));
    }
    emit(state.copyWith(
        accessToken: userDetailsModel.access_token,
        loading: false,
        userRequestModel: userDetailsModel.userRequestModel));
  }

  FutureOr<void> _onLogout(AuthLogoutEvent event, AuthEmitter emit) async {
    emit(state.copyWith(accessToken: null));
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

  const factory AuthState({
    @Default(false) bool initialized,
    @Default(false) bool loading,
    String? accessToken,
    String? refreshToken,
    UserRequestModel? userRequestModel,
  }) = _AuthState;

  bool get isAuthenticated => accessToken == null ? false : true;
}
