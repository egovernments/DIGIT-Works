// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/my_bills/my_bills_inbox_config.dart';
import '../../../models/my_bills/my_bills_model.dart';

class MyBillsRepository {
  final Dio _client;
  MyBillsRepository(this._client);
  Future<MyBillsListModel> searchMyBills({
    Map<String, String>? queryParameters,
    dynamic body,
    required Options options,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);
      return MyBillsListModelMapper.fromMap(
          response.data as Map<String, dynamic>);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<MyBillsInboxConfigList> getMyBillsInboxConfig({
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

      return MyBillsInboxConfigList.fromJson(
        json.decode(response.toString())['MdmsRes']['commonUiConfig'],
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
