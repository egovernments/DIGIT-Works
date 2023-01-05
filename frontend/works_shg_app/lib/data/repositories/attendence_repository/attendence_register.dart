// ignore_for_file: avoid_dynamic_calls

import 'dart:async';
import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:works_shg_app/models/attendance/attendance_registry_model.dart';

class AttendenceRegisterRepository {
  final Dio _client;
  AttendenceRegisterRepository(this._client);
  Future<AttendenceRegistersModel> createAttendenceRegisters({
    dynamic body,
    required String url,
  }) async {
    try {
      // var formData = FormData.fromMap(body);
      final response =
          await _client.post(url,  data: body);

      return AttendenceRegistersModel.fromJson(
        json.decode(response.toString()),
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      throw Exception(ex);
    }
  }
}
