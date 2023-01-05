// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/user_search/user_search_model.dart';

import '../../../utils/global_variables.dart';

class UserSearchRepository {
  final Dio _client;
  UserSearchRepository(this._client);
  Future<UserSearchModel> searchUser({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body,
          options: Options(extra: {
            "userInfo": GlobalVariables.getUserInfo(),
            "accessToken": GlobalVariables.getAuthToken(),
          }));

      return UserSearchModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }
}
