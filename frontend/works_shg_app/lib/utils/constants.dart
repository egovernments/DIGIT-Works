import 'package:flutter/material.dart';

class Constants {
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
  static final navKey = GlobalKey<NavigatorState>();
  static const String active = 'ACTIVE';
  static const String rejected = 'REJECTED';
  static const app_tenant_id = 'pg';
  static const mdms_tenant_id = 'pg';
  static const devAssets =
      'https://s3.ap-south-1.amazonaws.com/works-dev-asset/globalConfigsWorks.js';
}

final scaffoldMessengerKey = new GlobalKey<ScaffoldMessengerState>();
