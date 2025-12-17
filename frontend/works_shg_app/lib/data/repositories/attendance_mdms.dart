import 'dart:convert';

import 'package:dio/dio.dart';

import '../../models/skills/skills.dart';

class AttendanceMDMSRepository {
  final Dio _client;

  AttendanceMDMSRepository(this._client);

  Future<SkillsList> skillsMDMS({
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
      );
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
