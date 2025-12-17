import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:works_shg_app/Env/app_config.dart';
import 'package:works_shg_app/Env/env_config.dart';
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../models/employee/homeconfig/home_config_model.dart';
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
    } on DioException catch (ex) {
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
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
  // emp mb home screen

  Future<HomeConfigModel> getEmpHomeConfig({
    required String apiEndPoint,
    required String tenantId,
    required List<String> roleCodes,
    required String actionMaster,
    required bool enabled,
  }) async {
    try {
      Dio client = Dio();

      client.options.baseUrl =
          kIsWeb && !kDebugMode ? apiBaseUrl : envConfig.variables.baseUrl;
      var response = await client.post(apiEndPoint, data: {
        "roleCodes": roleCodes,
        "tenantId": tenantId,
        "actionMaster": actionMaster,
        "enabled": enabled,
        "RequestInfo": {
          "apiId": 'Rainmaker',
          "ts": DateTime.now().millisecondsSinceEpoch,
          "action": "_search",
          "msgId": "",
          "authToken": GlobalVariables.authToken,
          "userInfo": null
        },
      });

      return HomeConfigModel.fromJson(response.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
  

  Future<AppVersionModel> getAppVersion({
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

      return AppVersionModel.fromJson(
        json.decode(response.toString())['MdmsRes']['common-masters']
            ['AppVersion'][0],
      );
    } on DioException catch (ex) {
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
    } on DioException catch (e) {
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
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
