import 'dart:async';
import 'dart:convert';

// import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:digit_ui_components/widgets/molecules/language_selection_card.dart';
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:hive_flutter/hive_flutter.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/blocs/app_initilization/app_initilization.dart';
import 'package:works_shg_app/data/schema/localization.dart';
import 'package:works_shg_app/models/user_details/user_details_model.dart';
import 'package:works_shg_app/services/urls.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../data/remote_client.dart';
import '../../data/repositories/auth_repository/auth.dart';
import '../../services/local_storage.dart';

part 'auth.freezed.dart';

typedef AuthEmitter = Emitter<AuthState>;

enum RoleType { cbo, employee, none }

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final AppInitializationBloc appInitializationBloc;
  AuthBloc(this.appInitializationBloc) : super(const AuthState.initial()) {
    on<AuthLoginEvent>(_onLogin);
    on<AuthLogoutEvent>(_onLogout);
    on<AuthClearLoggedDetailsEvent>(_onClearLoggedInDetails);
  }

  FutureOr<void> _onLogin(AuthLoginEvent event, AuthEmitter emit) async {
    Client client = Client();

    try {
      emit(const AuthState.loading());
      UserDetailsModel userDetailsModel = await AuthRepository(client.init())
          .validateLogin(url: Urls.userServices.authenticate, body: {
        "username": event.userId.toString(),
        "password": event.password.toString(),
        "userType": event.roleType == RoleType.cbo ? 'CITIZEN' : 'EMPLOYEE',
        "tenantId": event.roleType == RoleType.cbo
            ? GlobalVariables.globalConfigObject?.globalConfigs?.stateTenantId
            : event.tenantId,
        "scope": "read",
        "grant_type": "password"
      });
      await Future.delayed(const Duration(seconds: 1));
      GlobalVariables.roleType =
          event.roleType == RoleType.cbo ? RoleType.cbo : RoleType.employee;
      GlobalVariables.authToken = userDetailsModel.access_token;
      GlobalVariables.uuid = userDetailsModel.userRequestModel?.uuid;
      GlobalVariables.tenantId = userDetailsModel.userRequestModel?.tenantId;
      GlobalVariables.userRequestModel =
          jsonDecode(jsonEncode(userDetailsModel.userRequestModel));
       GlobalVariables.roles=   userDetailsModel.userRequestModel!.rolesModel!.map((e) => e.code!).toList();
      if (kIsWeb) {
        html.window.sessionStorage['accessToken'] =
            jsonEncode(userDetailsModel.access_token);
        html.window.sessionStorage['userRequest'] =
            jsonEncode(userDetailsModel.userRequestModel);
        html.window.sessionStorage['uuid'] =
            jsonEncode(userDetailsModel.userRequestModel?.uuid);
        html.window.sessionStorage['mobileNumber'] =
            jsonEncode(userDetailsModel.userRequestModel?.mobileNumber);
      } else {
        await storage.write(
            key: 'accessToken',
            value: jsonEncode(userDetailsModel.access_token));
        await storage.write(
            key: 'userRequest',
            value: jsonEncode(userDetailsModel.userRequestModel));
        await storage.write(
            key: 'uuid',
            value: jsonEncode(userDetailsModel.userRequestModel?.uuid));
        await storage.write(
            key: 'mobileNumber',
            value: jsonEncode(userDetailsModel.userRequestModel?.mobileNumber));
      }
      if (userDetailsModel != null) {
        emit(
          AuthState.loaded(
            userDetailsModel,
            userDetailsModel.access_token.toString(),
            event.roleType == RoleType.cbo ? RoleType.cbo : RoleType.employee,
          ),
        );
      } else {
        emit(const AuthState.error());
      }
    } on DioException catch (e) {
      emit(const AuthState.error());
    }
  }

  FutureOr<void> _onLogout(AuthLogoutEvent event, AuthEmitter emit) async {
    Client client = Client();

    try {
      List<DigitRowCardModel>? languages = await GlobalVariables.getLanguages();
      languages?.forEach((e) async {
        if (kIsWeb) {
          html.window.sessionStorage.remove(e.value);
        } else {
          await Hive.box<KeyLocaleModel>('keyValueModel').clear();
          await Hive.box<Localization>('localization').clear();
        }
      });
      await AuthRepository(client.init()).logOutUser(
        url: Urls.userServices.logOut,
        queryParameters: {
          'tenantId': GlobalVariables.userRequestModel!['tenantId']
        },
        body: {'access_token': GlobalVariables.authToken},
        options: Options(extra: {"accessToken": GlobalVariables.authToken}),
      );

      appInitializationBloc.add(
          AppInitializationSetupEvent(selectedLang: LanguageEnum.en_IN.name));

      GlobalVariables.organisationListModel = null;
      GlobalVariables.authToken = null;
      GlobalVariables.tenantId = null;
      GlobalVariables.roleType = RoleType.none;
      GlobalVariables.roles=[];
      emit(const AuthState.loaded(null, null, RoleType.none));
      emit(const AuthState.initial());
    } on DioException catch (e) {
      emit(const AuthState.loaded(null, null, RoleType.none));
    }
  }

  FutureOr<void> _onClearLoggedInDetails(
      AuthClearLoggedDetailsEvent event, AuthEmitter emit) async {

    try {
      List<DigitRowCardModel>? languages = await GlobalVariables.getLanguages();
      languages?.forEach((e) async {
        if (kIsWeb) {
          html.window.sessionStorage.remove(e.value);
        } else {
          await Hive.box<KeyLocaleModel>('keyValueModel').clear();
          await Hive.box<Localization>('localization').clear();
          await storage.delete(key: e.value);
        }
      });
 appInitializationBloc.add(
          AppInitializationSetupEvent(selectedLang: LanguageEnum.en_IN.name));
          
      GlobalVariables.organisationListModel = null;
      GlobalVariables.authToken = null;
      GlobalVariables.tenantId = null;
      GlobalVariables.roleType = RoleType.none;
      GlobalVariables.roles=[];
      emit(const AuthState.loaded(null, null, RoleType.none));
      emit(const AuthState.initial());
    } on DioException catch (e) {
      emit(const AuthState.error());
    }
  }
}

@freezed
class AuthEvent with _$AuthEvent {
  const factory AuthEvent.login({
    required String userId,
    required String password,
    required RoleType roleType,
    String? tenantId,
  }) = AuthLoginEvent;

  const factory AuthEvent.logout() = AuthLogoutEvent;

  const factory AuthEvent.clearLoggedInDetails() = AuthClearLoggedDetailsEvent;
}

@freezed
class AuthState with _$AuthState {
  const AuthState._();
  const factory AuthState.initial() = _Initial;
  const factory AuthState.loading() = _Loading;
  const factory AuthState.loaded(
    UserDetailsModel? userDetailsModel,
    String? accessToken,
    RoleType roleType,
  ) = _Loaded;
  const factory AuthState.error() = _Error;
}
