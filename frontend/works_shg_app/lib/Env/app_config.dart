import 'dart:html';

import 'package:flutter/foundation.dart';

const _baseUrl = "baseUrl";

enum Environment { dev, stage, prod }

late Map<String, dynamic> _config;

void setEnvironment(Environment env) {
  switch (env) {
    case Environment.dev:
      _config = devConstants;
      break;
    case Environment.stage:
      _config = stageConstants;
      break;
    case Environment.prod:
      _config = prodConstants;
      break;
  }
}

dynamic get apiBaseUrl {
  return _config[_baseUrl];
}

class EnvironmentVariables {
  static const String baseUrl = "https://works-dev.digit.org/";
}

Map<String, dynamic> devConstants = {
  _baseUrl: kIsWeb
      ? window.location.origin
      : const String.fromEnvironment('BASE_URL'),
  // "https://works-qa.digit.org/works-shg-app/",
};

Map<String, dynamic> stageConstants = {
  _baseUrl: "https://api.stage.com/",
};

Map<String, dynamic> prodConstants = {
  _baseUrl: "https://api.production.com/",
};
