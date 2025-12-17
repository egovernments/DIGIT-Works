import 'package:dio/dio.dart';
import 'package:works_shg_app/models/employee/estimate/estimate_model.dart';
import 'package:works_shg_app/utils/global_variables.dart';

class EstimateRepository {
  final Dio _client;
  EstimateRepository(this._client);

  Future<EstimateDetailResponse> loadEstimate({
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

      
      return EstimateDetailResponse.fromJson(res.data);
    } on DioException catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }

  

  
}