library i18;

Login login = const Login();
Common common = const Common();
Home home = const Home();
WorkOrder workOrder = const WorkOrder();
AttendanceMgmt attendanceMgmt = const AttendanceMgmt();

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
  String get submit => 'CS_COMMON_SUBMIT';
  String get gender => 'CORE_COMMON_GENDER';
  String get male => 'CORE_COMMON_GENDER_MALE';
  String get female => 'CORE_COMMON_GENDER_FEMALE';
  String get transgender => 'CORE_COMMON_GENDER_TRANSGENDER';
  String get backToHome => 'CORE_COMMON_BACK_HOME_BUTTON';
  String get worksSHGLabel => 'CORE_COMMON_MGRAM_SEVA_LABEL';
  String get home => 'ACTION_TEST_HOME';
  String get editProfile => 'CORE_COMMON_EDIT_PROFILE';
  String get language => 'CS_HOME_HEADER_LANGUAGE';
  String get decline => 'CORE_BUTTON_DECLINE';
  String get accept => 'CORE_BUTTON_ACCEPT';
  String get confirm => 'CORE_BUTTON_CONFIRM';
  String get back => 'CORE_BUTTON_BACK';
  String get sendForApproval => 'SEND_FOR_APPROVAL';
  String get saveAsDraft => 'SAVE_AS_DRAFT';
  String get warning => 'CORE_MSG_WARNING';
  String get dates => 'CORE_COMMON_DATES';
  String get status => 'CORE_COMMON_STATUS';
  String get aadhaarNumber => 'CORE_COMMON_AADHAAR';
  String get bankAccountNumber => 'CORE_COMMON_BANK_ACCOUNT_NO';
  String get searchByNameAadhaar => 'CORE_SEARCH_BY_NAME_AADHAAR';
  String get mon => 'CORE_MON';
  String get tue => 'CORE_TUE';
  String get wed => 'CORE_WED';
  String get thu => 'CORE_THU';
  String get fri => 'CORE_FRI';
  String get sat => 'CORE_SAT';
  String get sun => 'CORE_SUN';
  String get attachments => 'CS_COMMON_ATTACHMENTS';
  String get apply => 'ES_COMMON_APPLY';
  String get cancel => 'CS_ACTION_CANCEL';
  String get startDate => 'EVENTS_START_DATE_LABEL';
  String get endDate => 'EVENTS_END_DATE_LABEL';
  String get invalidCredentials => 'INVALID_LOGIN_CREDENTIALS';
}

class Home {
  const Home();
  String get registerIndividual => 'REGISTER_INDIVIDUAL';
  String get manageWageSeekers => 'MANAGE_WAGE_SEEKERS';
  String get workOrder => 'ACTION_TEST_WORK_ORDER';
  String get worksMgmt => 'WORKS_MGMT';
  String get attendanceMgmt => 'ACTION_TEST_ATTENDENCEMGMT';
  String get musterRoll => 'ACTION_TEST_MUSTER_ROLL';
  String get trackAttendance => 'TRACK_ATTENDENCE';
}

class WorkOrder {
  const WorkOrder();
  String get warningMsg => 'WRNG_MSG_WORKORDER';
  String get projects => 'WORKS_PROJECT';
  String get contractID => 'WORKS_CONTRACT_ID';
  String get contractIssueDate => 'WORKS_CONTRACT_ISSUE_DATE';
  String get contractAmount => 'WORKS_CONTRACT_AMOUNT';
}

class AttendanceMgmt {
  const AttendanceMgmt();
  String get enrollWageSeeker => 'WORKS_ENROLL_WAGE_SEEKER';
  String get updateAttendance => 'UPDATE_ATTENDANCE';
  String get nameOfWork => 'WORKS_NAME_OF_WORK';
  String get winCode => 'ATM_WIN_CODE';
  String get engineerInCharge => 'WORKS_INCHARGE_ENGG';
  String get musterRollId => 'ATM_MUSTER_ROLL_ID';
  String get markAttendanceForTheWeek => 'ATM_MARK_ATTENDENCE_LABEL';
  String get addNewWageSeeker => 'ATM_ADD_NEW_WAGE_SEEKER';
  String get selectDateRangeFirst => 'SELECT_DATE_RANGE_FIRST';
  String get individualsCount => 'REGISTER_INDIVIDUAL_COUNT';
  String get attendanceLoggedSuccess => 'ATM_LOGGED_SUCCESSFULLY';
  String get attendanceLoggedFailed => 'ATM_LOGGED_FAILED';
}
