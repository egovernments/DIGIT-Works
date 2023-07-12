// ignore_for_file: avoid_dynamic_calls

import 'dart:async';

import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:universal_html/html.dart';
import 'package:flutter_training/Env/env_config.dart';

import '../../../models/init_mdms/global_config_model.dart';

class GetGlobalConfig {
  Future<GlobalConfigModel> getGlobalConfig() async {
    final dio = Dio();
    String? globalurl;
    try {
      if (kIsWeb && !kDebugMode) {
        HeadElement? head = querySelector('head') as HeadElement;

        ScriptElement? script = head
            .querySelector('script[type="text/javascript"]') as ScriptElement;

        globalurl = script.attributes['src'];
        // Extract the URL from the src attribute
      }
      var response = await dio.get(kIsWeb && !kDebugMode
          ? globalurl ?? envConfig.variables.assets
          : envConfig.variables.assets);

      return GlobalConfigModel.fromJson(
        response.data,
      );
    } on DioError catch (ex) {
      // Assuming there will be an errorMessage property in the JSON object
      rethrow;
    }
  }
}
