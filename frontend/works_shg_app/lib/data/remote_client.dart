import 'dart:async';

import "package:dio/dio.dart";

import '../models/request_info/request_info_model.dart';
import '../utils/constants.dart';

class Client {
  Dio init() {
    final Dio dio = Dio();
    dio.interceptors.add(ApiInterceptors());
    dio.options.baseUrl = Constants.baseURL;

    return dio;
  }
}

class ApiInterceptors extends Interceptor {
  @override
  Future<dynamic> onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    options.data = {
      ...options.data,
      "RequestInfo": {
        ...const RequestInfoModel(
          apiId: 'Rainmaker',
          ver: ".01",
          ts: "",
          action: "_search",
          did: "1",
          key: "",
          msgId: "20170310130900|en_IN",
        ).toJson(),
      },
    };
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
