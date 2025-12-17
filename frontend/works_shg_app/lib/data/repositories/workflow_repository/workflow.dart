// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/muster_rolls/muster_workflow_model.dart';

class WorkFlowRepository {
  final Dio _client;
  WorkFlowRepository(this._client);
  Future<MusterWorkFlowModel> getWorkFlow(
      {dynamic body,
      required String url,
      Map<String, String>? queryParameters,
      required Options options}) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          data: body ?? {}, queryParameters: queryParameters, options: options);

      return MusterWorkFlowModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (e) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
