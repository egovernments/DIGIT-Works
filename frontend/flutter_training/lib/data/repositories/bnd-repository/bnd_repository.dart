// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter_training/models/create-birth-registration/birth_certificates_model.dart';
import 'package:flutter_training/models/create-birth-registration/create_birth_response.dart';

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
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body ?? {},
          options: Options(extra: {
            "accessToken": GlobalVariables.authToken,
          }));

      return CreateBirthResponseMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<BirthCertificatesList> searchBirthCertificates({
    Map<String, dynamic>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      print(body);
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body ?? {},
          options: Options(extra: {
            "accessToken": GlobalVariables.authToken,
          }));

      return BirthCertificatesListMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
