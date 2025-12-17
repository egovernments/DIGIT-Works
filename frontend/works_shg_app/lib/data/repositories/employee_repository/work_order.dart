// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/employee/work_order/wo_inbox_response.dart';

import '../../../utils/global_variables.dart';

class WORepository {
  final Dio _client;
  WORepository(this._client);

  Future<WOInboxResponse> fetchWoInbox({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      
      final res = await _client.post(
        url,
        queryParameters: queryParameters,
        data: body ?? {},
        options: Options(extra: {
          "userInfo": GlobalVariables.userRequestModel,
          "accessToken": GlobalVariables.authToken
        }),
      );

      
      return WOInboxResponse.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

}
