// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/organisation/organisation_model.dart';
import '../../../models/wage_seeker/banking_details_model.dart';
import '../../../utils/global_variables.dart';

class ORGRepository {
  final Dio _client;
  ORGRepository(this._client);
  Future<OrganisationListModel> searchORG({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body,
          options: Options(extra: {
            "userInfo": GlobalVariables.userRequestModel,
            "accessToken": GlobalVariables.authToken,
          }));

      return OrganisationListModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<BankingDetailsModel> searchORGFinance({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      final response = await _client.post(url,
          queryParameters: queryParameters,
          data: body,
          options: Options(extra: {
            "userInfo": GlobalVariables.userRequestModel,
            "accessToken": GlobalVariables.authToken,
          }));

      return BankingDetailsModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
