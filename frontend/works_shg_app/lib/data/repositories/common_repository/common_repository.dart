import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/mdms/location_mdms.dart';

class CommonRepository {
  final Dio _client;

  CommonRepository(this._client);

  Future<Location> getCities(
      {dynamic body,
      Map<String, String>? queryParameters,
      required String url,
      Options? options}) async {
    try {
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      return Location.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
