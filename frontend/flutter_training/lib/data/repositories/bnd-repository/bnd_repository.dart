// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_training/models/create-birth-registration/create_birth_response.dart';
import 'package:flutter_training/models/muster_rolls/muster_roll_model.dart';

import '../../../models/muster_rolls/estimate_muster_roll_model.dart';
import '../../../utils/global_variables.dart';

class BNDRepository {
  final Dio _client;
  BNDRepository(this._client);
  Future<CreateBirthResponse> createBirthCertificate({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      print(body);
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body ?? {},
          options: Options(extra: {
            "userInfo": GlobalVariables.userRequestModel,
            "accessToken": GlobalVariables.authToken,
          }));

      return CreateBirthResponseMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<EstimateMusterRollsModel> estimateMusterRolls({
    Map<String, String>? queryParameters,
    dynamic body,
    required Options options,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      return EstimateMusterRollsModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<MusterRollsModel> createMuster({
    Map<String, String>? queryParameters,
    dynamic body,
    required Options options,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      return MusterRollsModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
