// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';
import 'package:works_shg_app/utils/global_variables.dart';

class MusterRollRepository {
  final Dio _client;
  MusterRollRepository(this._client);
  Future<MusterRollsModel> searchMusterRolls({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body ?? {},
          options: Options(extra: {
            "userInfo": GlobalVariables.getUserInfo(),
            "accessToken": GlobalVariables.getAuthToken(),
            "apiId": "asset-services",
            "msgId": "search with from and to values"
          }));

      return MusterRollsModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }
}
