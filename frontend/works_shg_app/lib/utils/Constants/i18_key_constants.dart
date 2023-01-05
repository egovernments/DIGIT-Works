library i18;

Login login = const Login();
Common common = const Common();
Home home = const Home();

class Login {
  const Login();
  String get loginLabel => 'CORE_COMMON_LOGIN';
  String get loginUserName => 'CORE_LOGIN_USERNAME';
  String get loginPhoneNumber => 'LOGIN_PHONE_NO';
  String get loginPassword => 'CORE_LOGIN_PASSWORD';
  String get forgotPassword => 'ES_FORGOT_PASSWORD';
  String get invalidCredentials => 'INVALID_CREDENTIALS';
}

class Common {
  const Common();
  String get continueLabel => 'CORE_COMMON_CONTINUE';
  String get nameLabel => 'CORE_COMMON_NAME';
  String get continueToLogin => 'CONTINUE_TO_LOGIN';
  String get mobileNumber => 'CORE_COMMON_MOBILE_NUMBER';
  String get logOut => 'CORE_COMMON_LOGOUT';
  String get oK => 'CORE_CHANGE_TENANT_OK';
  String get email => 'CORE_COMMON_EMAIL';
  String get save => 'CORE_COMMON_SAVE';
  String get submit => 'CORE_COMMON_BUTTON_SUBMIT';
  String get gender => 'CORE_COMMON_GENDER';
  String get male => 'CORE_COMMON_GENDER_MALE';
  String get female => 'CORE_COMMON_GENDER_FEMALE';
  String get transgender => 'CORE_COMMON_GENDER_TRANSGENDER';
  String get backToHome => 'CORE_COMMON_BACK_HOME_BUTTON';
  String get worksSHGLabel => 'CORE_COMMON_MGRAM_SEVA_LABEL';
  String get home => 'ACTION_TEST_HOME';
  String get editProfile => 'CORE_COMMON_EDIT_PROFILE';
  String get language => 'CORE_COMMON_LANGUAGE';
}

class Home {
  const Home();
  String get registerIndividual => 'REGISTER_INDIVIDUAL';
  String get manageWageSeekers => 'MANAGE_WAGE_SEEKERS';
  String get workOrder => 'ACTION_TEST_WORK_ORDER';
  String get worksMgmt => 'WORKS_MGMT';
  String get attendanceMgmt => 'ACTION_TEST_ATTENDENCEMGMT';
  String get musterRoll => 'ACTION_TEST_MUSTER_ROLL';
}
