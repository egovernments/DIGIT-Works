import 'package:flutter/material.dart';

class Constants {
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
  static final navKey = GlobalKey<NavigatorState>();
  static const String active = 'ACTIVE';
  static const String activeInboxStatus = 'ACTIVE_INBOX_CARD_STATUS';
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
  static const homeMyWorks = 'HOME_MY_WORKS';
  static const homeTrackAttendance = 'HOME_TRACK_ATTENDENCE';
  static const homeMusterRolls = 'HOME_MUSTER_ROLLS';
  static const homeMyBills = 'HOME_MY_BILLS';
  static const homeRegisterWageSeeker = 'HOME_REGISTER_WAGE_SEEKER';
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
