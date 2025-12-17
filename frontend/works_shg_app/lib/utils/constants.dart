import 'package:flutter/material.dart';



enum LanguageEnum { en_IN, or_IN }
class Constants {
  static const String deleteIcon='assets/svg/delete.svg';
  static const String mdmsApiEndPoint = 'egov-mdms-service/v1/_search';
  static final navKey = GlobalKey<NavigatorState>();
  static const userMobileNumberKey = 'mobileNumber';
  static const String active = 'ACTIVE';
  static const String inActive = 'INACTIVE';
  static const String pendingForAcceptance = 'PENDING_FOR_ACCEPTANCE';
  static const String pendingForCorrection = 'PENDINGFORCORRECTION';
  static const String activeInboxStatus = 'ACTIVE_INBOX_CARD_STATUS';
  static const String rejected = 'REJECTED';
  static const String sentBack = 'SENTBACKTOCBO';
  static const muktaIcon = 'assets/svg/mukta.svg';
  static const mbIcon = 'assets/svg/menu_book.svg';
  static const workOrderIcon = 'assets/svg/workorderInbox.svg';
  static const doubleArrow ='assets/svg/double_arrow.svg';
  static const sort ='assets/svg/sort.svg';
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
  static const homeMyServiceRequests = 'HOME_SERVICE_REQUESTS';
  static const myBillsWageType = 'EXPENSE.WAGES';
  static const myBillsPurchaseType = 'EXPENSE.PURCHASE';
  static const myBillsSupervisionType = 'EXPENSE.WAGES';
  static const approvedKey = 'APPROVED';
  static const tenantIdKey = 'tenantId';
  static const inWorkFlowKey = 'INWORKFLOW';
  static const verifyAdhar="SUCCESS";
  static const saveAsDraft="SAVE_AS_DRAFT";
  static const appVersion="2.1.1";
}

final scaffoldMessengerKey = GlobalKey<ScaffoldMessengerState>();
