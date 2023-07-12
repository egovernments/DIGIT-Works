import 'dart:async';

import "package:dio/dio.dart";
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/Env/app_config.dart';
import 'package:flutter_training/utils/constants.dart';

import '../Env/env_config.dart';
import '../blocs/auth/auth.dart';
import '../models/request_info/request_info_model.dart';
import '../utils/global_variables.dart';

class Client {
  Dio init() {
    final Dio dio = Dio();
    dio.interceptors.add(ApiInterceptors());
    dio.options.baseUrl =
        kIsWeb && !kDebugMode ? apiBaseUrl : envConfig.variables.baseUrl;

    return dio;
  }
}

class ApiInterceptors extends Interceptor {
  dynamic request;

  @override
  Future<dynamic> onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    var ts = DateTime.now().millisecondsSinceEpoch;
    var selectedLocale = await GlobalVariables.selectedLocale() ?? "en_IN";
    options.data = {
      ...options.data,
      "RequestInfo": {
        ...RequestInfoModel(
          apiId: options.extra['apiId'] ?? 'Rainmaker',
          ver: options.extra['ver'] ?? ".01",
          ts: options.extra['ts'] ?? "",
          action: options.extra['action'] ?? "_search",
          did: options.extra['did'] ?? "1",
          key: options.extra['key'] ?? "",
          msgId: selectedLocale != null ? "$ts|$selectedLocale" : "",
          authToken: options.extra['accessToken'],
        ).toJson(),
        "userInfo": options.extra['userInfo']
      },
    };
    super.onRequest(options, handler);
  }

  @override
  void onError(DioError err, ErrorInterceptorHandler handler) async {
    // ignore: no-empty-block
    if (err.type == DioErrorType.response &&
        (err.response?.statusCode == 500 || err.response?.statusCode == 403)) {
      scaffoldMessengerKey.currentContext!
          .read<AuthBloc>()
          .add(const AuthLogoutEvent());
    } else {
      handler.next(err);
    }
  }

  @override
  Future<dynamic> onResponse(
    Response<dynamic> response,
    ResponseInterceptorHandler handler,
  ) async {
    // Notifiers.getToastMessage(scaffoldMessengerKey.currentContext!,
    //     'Created Successfully', 'SUCCESS');
    return handler.next(response);
  }
}
