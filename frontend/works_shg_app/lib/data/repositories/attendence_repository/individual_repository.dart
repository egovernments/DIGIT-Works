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
    } on DioException catch (ex) {
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
        // Added this filter so that wageseekers with corrupted data will not be visible in the search and not allowed to engage as well
        // Parse the JSON response
        final dynamic responseData = response.data;

        if (responseData is Map<String, dynamic> &&
            responseData.containsKey("items") &&
            responseData["items"] is List) {
          final List<dynamic> items = responseData["items"];
          final filteredItems = items.where((item) =>
              item != null &&
              item.containsKey("businessObject") &&
              item["businessObject"] is Map &&
              item["businessObject"]["individualId"] != null &&
              item["businessObject"]["individualId"] != "null" &&
              item["businessObject"].containsKey("individualId")).toList();
           
          // Update the "items" key with the filtered items
          responseData["items"] = filteredItems;
        }

      return WMSIndividualListModelMapper.fromMap(
          responseData);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
