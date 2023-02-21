// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';

import '../../../models/mdms/attendance_hours.dart';

class MdmsRepository {
  final Dio _client;
  MdmsRepository(this._client);

  Future<InitMdmsModel> initMdmsRegistry({
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
      return InitMdmsModel.fromJson(
        json.decode(response.toString())['MdmsRes'],
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }

  Future<AttendanceHoursList> attendanceHoursMDMS({
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

      return AttendanceHoursList.fromJson(
        json.decode(response.toString())['MdmsRes']['common-masters'],
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
