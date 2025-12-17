// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/employee/mb/mb_project_type.dart';

import '../../../utils/global_variables.dart';

class MBMDMSRepository {
  final Dio _client;
  MBMDMSRepository(this._client);

  FutureOr<MBProjectType> fetchProjectType({
    Map<String, String>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      
      final res = await _client.post(
        url,
        queryParameters: queryParameters,
        data: body ?? {},
        options: Options(extra: {"authToken": GlobalVariables.authToken}),
      );

      

      return MBProjectType.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
