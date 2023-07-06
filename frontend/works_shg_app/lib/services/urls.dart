class Urls {
  static UserServices userServices = const UserServices();
  static InitServices initServices = const InitServices();
  static MusterRollServices musterRollServices = const MusterRollServices();
  static AttendanceRegisterServices attendanceRegisterServices =
      const AttendanceRegisterServices();
  static CommonServices commonServices = const CommonServices();
  static WageSeekerServices wageSeekerServices = const WageSeekerServices();
  static WorkServices workServices = const WorkServices();
  static ORGServices orgServices = const ORGServices();
  static BillServices billServices = const BillServices();
}

class CommonServices {
  const CommonServices();
  String get workflow => 'egov-workflow-v2/egov-wf/process/_search';
  String get businessWorkflow =>
      'egov-workflow-v2/egov-wf/businessservice/_search';
  String get fileUpload => 'filestore/v1/files';
  String get fileFetch => 'filestore/v1/files/url';
  String get fetchCities => 'egov-location/location/v11/boundarys/_search';
  String get bankDetails => 'https://ifsc.razorpay.com';
  String get pdfDownload => 'egov-pdf/download';
}

class ORGServices {
  const ORGServices();

  String get orgSearch => 'org-services/organisation/v1/_search';
  String get financeSearch => 'bankaccount-service/bankaccount/v1/_search';
}

class BillServices {
  const BillServices();

  String get searchMyBills => '/expense-calculator/v1/_search';
}

class WageSeekerServices {
  const WageSeekerServices();

  String get individualCreate => 'individual/v1/_create';
  String get bankCreate => '/bankaccount-service/bankaccount/v1/_create';
}

class InitServices {
  const InitServices();
  String get mdms => 'egov-mdms-service/v1/_search';
  String get localizationSearch => 'localization/messages/v1/_search';
}

class UserServices {
  const UserServices();
  String get authenticate => 'user/oauth/token';
  String get resetPassword => 'user/password/nologin/_update';
  String get otpResetPassword => 'user-otp/v1/_send';
  String get userSearchProfile => 'user/_search';
  String get editProfile => 'user/profile/_update';
  String get changePassword => 'user/password/_update';
  String get sendOtp => 'user-otp/v1/_send';
  String get logOut => 'user/_logout';
}

class WorkServices {
  const WorkServices();
  String get myWorks => 'contract/v1/_search';
  String get updateWorkOrder => 'contract/v1/_update';
}

class MusterRollServices {
  const MusterRollServices();
  String get searchMusterRolls => 'muster-roll/v1/_search';
  String get musterRollsEstimate => 'muster-roll/v1/_estimate';
  String get createMuster => 'muster-roll/v1/_create';
  String get updateMuster => 'muster-roll/v1/_update';
}

class AttendanceRegisterServices {
  const AttendanceRegisterServices();
  String get createAttendanceRegister => 'attendance/v1/_create';
  String get searchAttendanceRegister => 'attendance/v1/_search';
  String get createAttendee => 'attendance/attendee/v1/_create';
  String get createAttendanceLog => 'attendance/log/v1/_create';
  String get updateAttendanceLog => 'attendance/log/v1/_update';
  String get deEnrollAttendee => 'attendance/attendee/v1/_delete';
  String get individualSearch => 'individual/v1/_search';
  String get wmsIndividualSearch => 'wms/individual/_search';
}
