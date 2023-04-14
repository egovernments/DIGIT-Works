import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/mdms/location_mdms.dart';
import '../../../utils/save_file_mobile.dart'
    if (dart.library.html) '../../../utils/save_file_web.dart';

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

  Future<void> downloadPDF(
      {dynamic body,
      Map<String, String>? queryParameters,
      required String url,
      Options? options,
      String? fileName}) async {
    try {
      final response = await _client.post(url,
          queryParameters: queryParameters, data: body ?? {}, options: options);

      if (response.statusCode == 200) {
        final List<int> bytes = response.data;
        await saveAndLaunchFile(bytes, fileName ?? 'Common.pdf');
      } else {
        throw Exception('Failed to download file.');
      }
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
