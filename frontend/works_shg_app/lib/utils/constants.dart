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
  static const qaAssets =
      'https://s3.ap-south-1.amazonaws.com/works-qa-asset/worksGlobalConfig.json';
  static const uatAssets =
      'https://s3.ap-south-1.amazonaws.com/works-uat-asset/worksGlobalConfig.json';
  static const prodAssets =
      'https://s3.ap-south-1.amazonaws.com/works-prod-asset/worksGlobalConfig.json';
  static const devEnv = 'dev';
  static const qaEnv = 'qa';
  static const uatEnv = 'uat';
  static const prodEnv = 'prod';
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
