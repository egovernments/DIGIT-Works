class Urls {
  static UserServices userServices = const UserServices();
  static InitServices initServices = const InitServices();
  static MusterRollServices musterRollServices = const MusterRollServices();
  static AttendenceRegisterServices attendenceRegisterServices = const AttendenceRegisterServices();

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
}

class AttendenceRegisterServices {
  const AttendenceRegisterServices();
  String get CreateAttendenceRegister => 'attendance/v1/_create';
}
