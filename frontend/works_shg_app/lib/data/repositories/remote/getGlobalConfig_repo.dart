// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';

import '../../../Env/app_config.dart';
import '../../../models/init_mdms/global_config_model.dart';

class GetGlobalConfig {
  Future<GlobalConfigModel> getGlobalConfig() async {
    final dio = Dio();
    try {
      var response = await dio.get(runningEnvAssets);

      return GlobalConfigModel.fromJson(
        response.data,
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
