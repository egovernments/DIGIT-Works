// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/works/contracts_model.dart';
import '../../../models/works/my_works_search_criteria.dart';

class MyWorksRepository {
  final Dio _client;
  MyWorksRepository(this._client);
  Future<ContractsModel> searchMyWorks({
    Map<String, String>? queryParameters,
    dynamic body,
    required Options options,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      return ContractsModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<ContractsModel> updateOrCreateContract({
    Map<String, String>? queryParameters,
    dynamic body,
    required Options options,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      return ContractsModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<MyWorksSearchCriteriaModel> getSearchCriteria({
    required String apiEndPoint,
    required String tenantId,
    required List<Map> moduleDetails,
  }) async {
    try {
      var response = await _client.post(apiEndPoint, data: {
        "MdmsCriteria": {
          "tenantId": tenantId,
          "moduleDetails": moduleDetails,
        },
      });

      return MyWorksSearchCriteriaModel.fromJson(
        json.decode(response.toString())['MdmsRes'],
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
