import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:works_shg_app/data/repositories/auth/auth.dart';
import 'package:works_shg_app/models/UserDetails/user_details_model.dart';

import '../../data/remote_client.dart';

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
        .validateLogin(url: 'user/oauth/token', body: {
      "username": event.userId.toString(),
      "password": event.password.toString(),
      "userType": 'CITIZEN',
      "tenantId": 'pb.amritsar',
      "scope": "read",
      "grant_type": "password"
    });
    await Future.delayed(const Duration(seconds: 1));
    emit(state.copyWith(
        accessToken: userDetailsModel.access_token, loading: false));
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
  }) = _AuthState;

  bool get isAuthenticated => accessToken == null ? false : true;
}
