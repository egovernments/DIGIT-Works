import 'dart:async';

import "package:dio/dio.dart";
import 'package:works_shg_app/Env/app_config.dart';

class Client {
  Dio init({dynamic requestInfo}) {
    final Dio dio = Dio();
    dio.interceptors.add(ApiInterceptors());
    dio.options.baseUrl = apiBaseUrl;

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
    options.data = options.data;
    super.onRequest(options, handler);
  }

  @override
  void onError(DioError err, ErrorInterceptorHandler handler) async {
    // ignore: no-empty-block
    if (err.type == DioErrorType.response && err.response?.statusCode == 401) {
    } else {
      handler.next(err);
    }
  }

  @override
  Future<dynamic> onResponse(
    Response<dynamic> response,
    ResponseInterceptorHandler handler,
  ) async {
    return handler.next(response);
  }
}
