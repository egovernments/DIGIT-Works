// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/adharModel/adhar_response.dart';
import '../../../models/attendance/individual_list_model.dart';
import '../../../models/wage_seeker/banking_details_model.dart';
import '../../../utils/global_variables.dart';

class WageSeekerRepository {
  final Dio _client;
  WageSeekerRepository(this._client);
  Future<SingleIndividualModel> createIndividual({
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

      return SingleIndividualModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<BankingDetailsModel> createBanking({
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

// verify adhar

  Future<AdharCardResponse> verifyingAdharCard({
    dynamic body,
    required String url,
  }) async {
    final Dio dio = Dio();
    try {
      final response = await dio.post(
        url,
        data: body,
      );
      dio.close();
      return AdharCardResponse.fromJson(jsonDecode(response.data));
    } on DioException catch (ex) {
      dio.close();
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }


}
