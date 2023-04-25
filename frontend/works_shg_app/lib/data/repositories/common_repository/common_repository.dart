import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';

import '../../../models/mdms/location_mdms.dart';
import '../../../models/muster_rolls/business_service_workflow.dart';
import '../../../models/screen_config/home_screen_config.dart';
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

  Future<HomeScreenConfigModel> getHomeConfig({
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

      return HomeScreenConfigModel.fromJson(
        json.decode(response.toString())['MdmsRes'],
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<BusinessServiceWorkflowModel> getBusinessWorkflow(
      {dynamic body,
      required String url,
      Map<String, String>? queryParameters,
      required Options options}) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          data: body ?? {}, queryParameters: queryParameters, options: options);

      return BusinessServiceWorkflowModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (e) {
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
