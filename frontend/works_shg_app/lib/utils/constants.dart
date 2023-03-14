import 'package:flutter/material.dart';

import 'models.dart';

class Constants {
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
  static final navKey = GlobalKey<NavigatorState>();
  static const String active = 'ACTIVE';
  static const String rejected = 'REJECTED';
  static const muktaIcon = 'assets/svg/mukta.svg';
  static const devAssets =
      'https://s3.ap-south-1.amazonaws.com/works-dev-asset/worksGlobalConfig.json';
  static List<KeyValue> gender = [
    KeyValue('CORE_COMMON_GENDER_MALE', 'MALE'),
    KeyValue('CORE_COMMON_GENDER_FEMALE', 'FEMALE'),
    KeyValue('CORE_COMMON_GENDER_TRANSGENDER', 'TRANSGENDER'),
  ];
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
