// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';

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
      final response =
          await _client.post(url, queryParameters: queryParameters, data: body);

      return MusterRollsModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }
}
