// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';

import '../../../models/employee/mb/mb_detail_response.dart';
import '../../../models/employee/mb/mb_inbox_response.dart';
import '../../../utils/global_variables.dart';

class MBRepository {
  final Dio _client;
  MBRepository(this._client);

  Future<MBInboxResponse> fetchMbInbox({
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

      
      return MBInboxResponse.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  FutureOr<MBDetailResponse> fetchMbDetail({
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

      return MBDetailResponse.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  FutureOr<Measurement> updateMeasurement({
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

      return Measurement.fromJson(res.data['measurements'][0]);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
