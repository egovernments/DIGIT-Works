// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';

class MdmsRepository {
  final Dio _client;
  MdmsRepository(this._client);

  Future<InitMdmsModel> initMdmsRegistry(
    String apiEndPoint,
    Map body,
  ) async {
    try {
      var response = await _client.post(apiEndPoint, data: body);

      return InitMdmsModel.fromJson(
        json.decode(response.toString())['MdmsRes'],
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }
}
