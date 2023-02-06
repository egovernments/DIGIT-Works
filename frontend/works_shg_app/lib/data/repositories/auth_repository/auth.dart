// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/Env/app_config.dart';

import '../../../models/UserDetails/user_details_model.dart';

class AuthRepository {
  final Dio _client;
  AuthRepository(this._client);
  Future<UserDetailsModel> validateLogin({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      var headers = {
        "content-type": 'application/x-www-form-urlencoded',
        "Access-Control-Allow-Origin": "*",
        "authorization": "Basic ZWdvdi11c2VyLWNsaWVudDo=",
      };
      var formData = FormData.fromMap(body);
      final response =
          await Dio(BaseOptions(baseUrl: apiBaseUrl, headers: headers))
              .post(url, queryParameters: queryParameters, data: formData);

      return UserDetailsModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      rethrow;
      // Assuming there will be an errorMessage property in the JSON object
    }
  }
}
