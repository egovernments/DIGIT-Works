class Urls {
  static UserServices userServices = const UserServices();
  static InitServices initServices = const InitServices();
  static MusterRollServices musterRollServices = const MusterRollServices();
  static AttendanceRegisterServices attendanceRegisterServices =
      const AttendanceRegisterServices();
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
}

class MusterRollServices {
  const MusterRollServices();
  String get searchMusterRolls => 'muster-roll/v1/_search';
  String get musterRollsEstimate => 'muster-roll/v1/_estimate';
  String get createMuster => 'muster-roll/v1/_create';
}

class AttendanceRegisterServices {
  const AttendanceRegisterServices();
  String get createAttendanceRegister => 'attendance/v1/_create';
  String get searchAttendanceRegister => 'attendance/v1/_search';
  String get createAttendee => 'attendance/attendee/v1/_create';
  String get createAttendanceLog => 'attendance/log/v1/_create';
  String get updateAttendanceLog => 'attendance/log/v1/_update';
}
