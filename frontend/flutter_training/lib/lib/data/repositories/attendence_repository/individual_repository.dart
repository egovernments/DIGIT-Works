// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';

import '../../../models/attendance/individual_list_model.dart';
import '../../../utils/global_variables.dart';

class IndividualRepository {
  final Dio _client;
  IndividualRepository(this._client);
  Future<IndividualListModel> searchIndividual({
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
            "userInfo": GlobalVariables.userRequestModel,
            "accessToken": GlobalVariables.authToken,
          }));

      return IndividualListModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<WMSIndividualListModel> searchWMSIndividual({
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
            "userInfo": GlobalVariables.userRequestModel,
            "accessToken": GlobalVariables.authToken,
          }));

      return WMSIndividualListModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
