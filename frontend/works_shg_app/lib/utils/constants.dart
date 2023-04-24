import 'package:flutter/material.dart';

class Constants {
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
  static final navKey = GlobalKey<NavigatorState>();
  static const String active = 'ACTIVE';
  static const String rejected = 'REJECTED';
  static const String sentBack = 'SENTBACKTOCBO';
  static const muktaIcon = 'assets/svg/mukta.svg';
  static const devAssets =
      'https://s3.ap-south-1.amazonaws.com/works-dev-asset/worksGlobalConfig.json';
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
