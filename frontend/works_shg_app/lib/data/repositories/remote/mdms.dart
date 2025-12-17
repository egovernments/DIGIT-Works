// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';
import 'package:works_shg_app/models/skills/skills.dart';

import '../../../models/attendance/muster_submission.dart';
import '../../../models/mdms/attendance_hours.dart';
import '../../../models/mdms/wage_seeker_mdms.dart';
import '../../../models/muster_rolls/muster_inbox_status.dart';

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
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }

  Future<MusterInboxStatusList> musterInboxStatus({
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

      return MusterInboxStatusList.fromJson(
        json.decode(response.toString())['MdmsRes']['commonUiConfig'],
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<MusterSubmissionList> musterSubmissionMDMS({
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

      return MusterSubmissionList.fromJson(
        json.decode(response.toString())['MdmsRes']['commonUiConfig'],
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
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
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<WageSeekerMDMS> wageSeekerMDMS({
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

      return WageSeekerMDMS.fromJson(
        json.decode(response.toString())['MdmsRes'],
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }


// updated sor skills

Future<List<WageSeekerSkills>> skillsMDMS({
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

      return SkillsList.fromJson(
        json.decode(response.toString())['MdmsRes']['WORKS-SOR'],
      ).wageSeekerSkills??[];
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

}