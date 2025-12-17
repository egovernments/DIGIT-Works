import 'package:dio/dio.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import '../../../models/employee/mb/role_based_hrms.dart';

class EmpHRMSRepository {
  final Dio _client;
  EmpHRMSRepository(this._client);

  Future<HRMSResponse> loadHrmsEmployee({
    Map<String, dynamic>? queryParameters,
    dynamic body,
    required String url,
  }) async {
    try {
      //Dio http=Dio();
      final res = await _client.post(
        url,
        queryParameters: queryParameters,
        data: body ?? {},
        options: Options(extra: {
          "userInfo": GlobalVariables.userRequestModel,
          "accessToken": GlobalVariables.authToken
        }),
      );

      
      return HRMSResponse.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  

  
}