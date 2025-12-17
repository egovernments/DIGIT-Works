// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';
import 'package:works_shg_app/models/attendance/attendee_model.dart';

import '../../../utils/global_variables.dart';

class AttendanceRegisterRepository {
  final Dio _client;
  AttendanceRegisterRepository(this._client);
  Future<AttendanceRegistersModel> createAttendanceRegisters(
      {dynamic body, required String url, required Options options}) async {
    try {
      final response = await _client.post(url, data: body, options: options);

      return AttendanceRegistersModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<AttendeeModel> createAttendee(
      {dynamic body, required String url, required Options options}) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url, data: body, options: options);

      return AttendeeModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<AttendeeModel> deEnrollAttendee(
      {dynamic body, required String url, required Options options}) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url, data: body, options: options);

      return AttendeeModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<AttendanceRegistersModel> searchAttendanceProjects({
    dynamic body,
    Map<String, String>? queryParameters,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url,
          data: body ?? {},
          queryParameters: queryParameters,
          options: Options(extra: {
            "accessToken": GlobalVariables.authToken,
            "apiId": "mukta-services",
          }));

      return AttendanceRegistersModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
     
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  Future<AttendanceRegistersModel> createAttendanceLog(
      {dynamic body, required String url, required Options options}) async {
    try {
      // var formData = FormData.fromMap(body);
      final response = await _client.post(url, data: body, options: options);

      return AttendanceRegistersModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioException catch (ex) {
     
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
