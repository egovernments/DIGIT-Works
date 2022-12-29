import 'dart:html';

import 'package:flutter/material.dart';

class Constants {
  static String baseURL = '${window.location.origin}/';
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
