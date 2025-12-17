library i18;

Login login = const Login();
Common common = const Common();
Home home = const Home();
WorkOrder workOrder = const WorkOrder();
WageSeeker wageSeeker = const WageSeeker();
AttendanceMgmt attendanceMgmt = const AttendanceMgmt();
MyBills myBills = const MyBills();
MyServiceRequests myServiceRequests = const MyServiceRequests();
MeasurementBook measurementBook = const MeasurementBook();

PrivacyPolicy privacyPolicy=const PrivacyPolicy();
class PrivacyPolicy {
  const PrivacyPolicy();
  String get byClick => 'ES_BY_CLICKING';
  String get privacyPolicyLink => 'ES_PRIVACY_POLICY';
  String get accept=>'DIGIT_I_ACCEPT';
  String get decline=>'DIGIT_I_DO_NOT_ACCEPT';
  String get privacyPolicyValidationText=>"PRIVACY_POLICY_VALIDATION_ERROR";
  
}

class Login {
  const Login();
  String get loginLabel => 'CORE_COMMON_LOGIN';
  String get loginUserName => 'CORE_LOGIN_USERNAME';
  String get loginPhoneNumber => 'LOGIN_PHONE_NO';
  String get loginPassword => 'CORE_LOGIN_PASSWORD';
  String get forgotPassword => 'CORE_COMMON_FORGOT_PASSWORD';
  String get invalidOTP => 'LOGIN_INVALID_OTP';
  String get contactAdministrator => 'CONTACT_ADMINISTRATOR_FOR_PASSWORD';
  String get otpVerification => 'OTP_VERIFICATION';
  String get enterOTPSent => 'ENTER_OTP_SENT_TO';
  String get resendOTP => 'CS_RESEND_OTP';
  String get resendOTPInSec => 'RESEND_OTP_IN_SEC';
  String get enteredMobileNotRegistered =>
      'ENTERED_MOBILE_NO_NOT_REGISTERED_AS_CBO';
  String get pleaseEnterMobile => 'ENTER_REGISTERED_MOBILE';
  String get forgotPasswordMsg => "CORE_COMMON_FORGOT_MESSAGE";
}

class Common {
  const Common();
  String get wentWrong=>"CORE_SOMETHING_WENT_WRONG";
  String get statementnotfound=>"WORK_ORDER_STATEMENT_NOT_FOUND";
  String get noFileSelected=>"WORKS_NO_FILE_SELECTED";
  String get comments=>"WF_COMMON_COMMENTS";
  String get photoInfo=>"WORKS_DOC_UPLOAD_HINT";
  String get searchCriteria=>"ES_COMMON_MIN_SEARCH_CRITERIA_MSG";
  String get workOrderNotFound=>"COMMON_WO_NOT_FOUND";
  String get notFound=>"ES_COMMON_NO_DATA";
  String get empLoginError=>"ES_INVALID_LOGIN_CREDENTIALS";
  String get continueLabel => 'CORE_COMMON_CONTINUE';
  String get nameLabel => 'CORE_COMMON_NAME';
  String get continueToLogin => 'CONTINUE_TO_LOGIN';
  String get mobileNumber => 'CORE_COMMON_MOBILE_NUMBER';
  String get logOut => 'CORE_COMMON_LOGOUT';
  String get orgProfile => 'ORG_PROFILE';
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
  String get home => 'ES_COMMON_HOME';
  String get editProfile => 'CORE_COMMON_EDIT_PROFILE';
  String get language => 'CS_HOME_HEADER_LANGUAGE';
  String get decline => 'CORE_BUTTON_DECLINE';
  String get accept => 'CORE_BUTTON_ACCEPT';
  String get viewTermsAndConditions => 'COMMON_TERMS_AND_CONDITIONS';
  String get termsAndConditions => 'WORK_ORDER_TERMS_AND_CONDITIONS';
  String get noTermsNConditions => 'NO_TERMS_AND_CONDITIONS';
  String get confirm => 'CORE_BUTTON_CONFIRM';
  String get back => 'CORE_BACK_BUTTON';
  String get sendForApproval => 'SEND_FOR_APPROVAL';
  String get saveAsDraft => 'SAVE_AS_DRAFT';
  String get warning => 'CORE_MSG_WARNING';
  String get dates => 'CORE_COMMON_DATES';
  String get status => 'CORE_COMMON_STATUS';
  String get aadhaarNumber => 'CORE_COMMON_AADHAAR';
  String get relationship => 'CORE_COMMON_RELATIONSHIP';
  String get socialCategory => 'COMMON_SOCIAL_CATEGORY';
  String get dateOfBirth => 'CORE_DOB';
  String get locationDetails => 'COMMON_LOCATION_DETAILS';
  String get pinCode => 'CORE_COMMON_PINCODE';
  String get financialDetails => 'COMMON_FINANCIAL_DETAILS';
  String get city => 'CORE_COMMON_CITY';
  String get ward => 'CORE_COMMON_WARD';
  String get locality => 'CORE_COMMON_LOCALITY';
  String get location => 'ES_COMMON_LOCATION';
  String get streetName => 'CORE_STREET_NAME';
  String get doorNo => 'CORE_DOOR_NO';
  String get bankAccountNumber => 'CORE_COMMON_BANK_ACCOUNT_NO';
  String get searchByNameAadhaar => 'CORE_SEARCH_BY_NAME_AADHAAR';
  String get searchByName => 'CORE_SEARCH_BY_NAME';
  String get searchByMobileNumber => 'CORE_SEARCH_BY_MOBILE';
  String get searchByNameMobileNumber => 'CORE_SEARCH_BY_NAME_MOBILE_NO';
  String get mon => 'CORE_MON';
  String get tue => 'CORE_TUE';
  String get wed => 'CORE_WED';
  String get thu => 'CORE_THU';
  String get fri => 'CORE_FRI';
  String get sat => 'CORE_SAT';
  String get sun => 'CORE_SUN';
  String get total => 'TOTAL';
  String get attachments => 'CS_COMMON_ATTACHMENTS';
  String get apply => 'ES_COMMON_APPLY';
  String get noItems => 'ES_COMMON_NO_ITEMS';
  String get cancel => 'CS_ACTION_CANCEL';
  String get close => 'CS_ACTION_CLOSE';
  String get startDate => 'EVENTS_START_DATE_LABEL';
  String get endDate => 'EVENTS_END_DATE_LABEL';
  String get invalidCredentials => 'INVALID_LOGIN_CREDENTIALS';
  String get selectAnOption => 'ES_SELECT_AN_OPTION';
  String get selectSkill => 'ATM_SELECT_SKILL';
  String get viewDetails => 'ACTION_VIEW_DETAILS';
  String get action => 'CS_COMMON_ACTION';
  String get next => 'CS_COMMON_NEXT';
  String get fileSize => 'FILE_SIZE';
  String get invalidImageFile => 'CS_COMMON_INVALID_IMAGE_FILE';
  String get chooseFile => 'CHOOSE_FILE';
  String get noFileUploaded => 'NO_FILE_UPLOADED';
  String get camera => 'CAMERA';
  String get fileManager => 'FILE_MANAGER';
  String get chooseAnAction => 'CHOOSE_AN_ACTION';
  String get fatherName => 'FATHER_NAME';
  String get guardianName => 'COMMON_GUARDIAN_NAME';
  String get accountHolderName => 'ACCOUNT_HOLDER_NAME';
  String get accountNo => 'CORE_COMMON_ACCOUNT_NO';
  String get reEnterAccountNo => 'COMMON_RE_ENTER_ACCOUNT_NO';
  String get accountType => 'CORE_COMMON_ACCOUNT_TYPE';
  String get ifscCode => 'COMMON_IFSC_CODE';
  String get bankHint => 'HINT_BANK_DETAILS';
  String get branchName => 'COMMON_BRANCH_NAME';
  String get photoGraph => 'COMMON_PHOTOGRAPH';
  String get inProgress => 'IN_PROGRESS_LABEL';
  String get wageSeekerID => 'MASTERS_WAGESEEKER_ID';
  String get completed => 'COMPLETED_LABEL';
  String get info => 'ES_COMMON_INFO';
  String get clickToAddPhoto => 'CLICK_TO_ADD_PHOTO';
  String get workOrderInfo => 'WORK_ORDER_INFO';
  String get days => 'COMMON_DAYS';
  String get orgId => 'COMMON_ORG_ID';
  String get orgName => 'COMMON_ORG_NAME';
  String get registeredDept => 'REGISTERED_WITH_DEPT';
  String get deptRegNo => 'DEPT_REG_NO';
  String get dateOfIncorporation => 'DATE_OF_INCORP';
  String get orgType => 'ORG_TYPE';
  String get orgSubType => 'ORG_SUB_TYPE';
  String get funcCat => 'ORG_FUNC_CAT';
  String get functionalDetails => 'ORG_FUNC_DETAILS';
  String get contactDetails => 'ORG_CONTACT_DETAILS';
  String get classOrRank => 'ORG_CLASS_OR_RANK';
  String get validFrom => 'ORG_VALID_FROM';
  String get validTo => 'ORG_VALID_TO';
  String get contactPersonName => 'ORG_CONTACT_PERSON_NAME';
  String get download => "COMMON_DOWNLOAD";
   String get workOrderdownload => "COMMON_WORK_ORDER_DOWNLOAD";
   String get analysisdownload => "COMMON_ANALYSIS_STATEMENT_DOWNLOAD";
  String get showWorkflowTimeline => 'SHOW_WORKFLOW_TIMELINE';
  String get hideWorkflowTimeline => 'HIDE_WORKFLOW_TIMELINE';
  String get workflowTimeline => 'WORKS_WORKFLOW_TIMELINE';
  String get effectiveFrom => 'MASTERS_EFFECTIVE_FROM';
  String get effectiveTo => 'MASTERS_EFFECTIVE_TO';
  String get validPhotoGraph => 'PHOTOGRAPH_VALID_SIZE';
  String get individualAlreadyAdded => 'IND_ALREADY_ADDED_TO_THE_TABLE';
  String get noValue => 'ES_COMMON_NA';
  String get noOrgLinkedWithMob => 'ES_COMMON_NO_ORG_LINKED_WITH_MOBILE_NUMBER';
  String get takeAction => 'ES_COMMON_TAKE_ACTION';
  String get assignee => "COMMON_ASSIGNEE";
  String get commonWorkflowStates => "COMMON_WORKFLOW_STATES";
  String get supportingDocumentHeader => "WORKFLOW_MODAL_UPLOAD_FILES";
  String get date => "ES_COMMON_DATE";
  String get filter => "ES_COMMON_FILTER";
  String get musterRollId => "ES_COMMON_MUSTER_ROLL_ID";
  String get allFieldsMandatory =>
      "ES_COMMON_PLEASE_ENTER_ALL_MANDATORY_FIELDS";
  String get slaDaysRemaining => "COMMON_SLA_DAYS";
  String get issuedDate => "WORKS_CONTRACT_ISSUE_DATE";
  String get loading=>"ES_COMMON_LOADING";
  String get uploading=>"ES_COMMON_UPLOADING";

  
}

class Home {
  const Home();
  String get registerIndividual => 'REGISTER_INDIVIDUAL';
  String get registerWageSeeker => 'REGISTER_WAGE_SEEKER';
  String get mukta => 'MUKTA';
  String get myWorks => 'MY_WORKS';
  String get myBills => 'ACTION_TEST_MY_BILLS';
  String get manageWageSeekers => 'MANAGE_WAGE_SEEKERS';
  String get workOrder => 'ACTION_TEST_VIEW_WORK_ORDER';
  String get worksMgmt => 'WORKS_MGMT';
  String get attendanceMgmt => 'ACTION_TEST_ATTENDENCEMGMT';
  String get musterRoll => 'ACTION_TEST_MUSTER_ROLL';
  String get trackAttendance => 'TRACK_ATTENDENCE';
  String get inbox => 'ES_COMMON_INBOX';
  String get musterRolls => 'WORKS_MUSTERROLLS';
}

class WorkOrder {
  const WorkOrder();
  String get warningMsg => 'WRNG_MSG_WORKORDER';
  String get projects => 'WORKS_PROJECT';
  String get contractID => 'WORKS_CONTRACT_ID';
  String get workOrderNo => 'WORKS_ORDER_NO';
  String get nameOfCBO => 'COMMON_NAME_OF_CBO';
  String get roleOfCBO => 'WORKS_ROLE_CBO';
  String get completionPeriod => 'WORKS_COMPLETION_PERIOD';
  String get contractIssueDate => 'WORKS_CONTRACT_ISSUE_DATE';
  String get workOrderAmount => 'WORK_ORDER_AMOUNT';
  String get dueDate => 'WORKS_DUE_DATE';
  String get noWorkOrderAssigned => 'NO_WORK_ORDERS_ASSIGNED';
  String get noCompletedWorkOrderFound => 'NO_COMPLETED_WORK_ORDERS_FOUND';
  String get workOrderAcceptSuccess => 'WORK_ORDER_ACCEPTED_SUCCESS';
  String get workOrderDeclineSuccess => 'WORK_ORDER_DECLINE_SUCCESS';
  String get workOrderDetails => 'WORK_ORDER_DETAILS';
  String get contractDetails => 'WORK_ORDER_CONTRACT_DETAILS';
  String get timeLineDetails => 'WORK_ORDER_TIMELINE_DETAILS';
  String get relevantDocuments => 'WORK_RELEVANT_DOCUMENTS';
  String get workStartDate => 'WORKS_START_DATE';
  String get workEndDate => 'WORKS_END_DATE';
  String get extensionReqInDays => 'WORKS_EXTENSION_REQ_IN_DAYS';
  String get reasonForExtension => 'WORKS_REASON_FOR_EXTENSION';
  String get extensionReqInDaysIsRequired => 'EXTENSION_DAYS_IS_REQUIRED';
  String get reasonForExtensionIsRequired =>
      'WORKS_REASON_FOR_EXTENSION_IS_REQUIRED';
  String get extensionReqInDaysMinVal => 'WORKS_EXTENSION_DAYS_MIN_VALUE';
  String get extensionReqInDaysMaxVal => 'WORKS_EXTENSION_DAYS_MAX_VALUE';
  String get reasonForExtensionMinChar =>
      'WORKS_REASON_FOR_EXTENSION_MIN_CHARS';
  String get reasonForExtensionMaxChar =>
      'WORKS_REASON_FOR_EXTENSION_MAX_CHARS';
  String get timeExtensionRequestedSuccess =>
      'WORKS_TIME_EXTENSION_REQ_SUCCESSFULLY';
  String get requestID => 'WORKS_TIME_EXT_REQUEST_ID';
  String get timeExtensionRequestedSuccessSubText =>
      'WORKS_TIME_EXTENSION_REQ_SUCCESSFULLY_SUB_TEXT';
  String get timeExtensionRequestedUpdatedSuccessfully =>
      'WORKS_TIME_EXTENSION_REQ_UPDATED_SUCCESSFULLY';
  String get requestTimeExtension => 'ACTION_TEST_TIME_EXTENSION';
  String get projectClosure => 'ACTION_TEST_PROJECT_CLOSURE';
  String get errNoMusterRollExists => 'ERR_NO_MUSTER_EXISTS';
  String get errTimeExtReqAlreadyRaised => 'ERR_TIME_EXT_REQ_ALREADY_RAISED';
  String get closureRequests => 'WORKS_CLOSURE_REQUESTS';
  String get timeExtRequests => 'WORKS_TIME_EXT_REQUESTS';

  String get estimateRevisionError=>"WORKS_REVISION_ESTIMATE_IN_WORKFLOW_CREATE_MEASUREMENT";
  String get timeExtensionError=>"WORKS_TIME_EXTENSION_IN_WORKFLOW_CREATE_MEASUREMENT";
  String get existingMBCreateError=>"MB_EXISTING_IN_WORKFLOW_CREATE_MEASUREMENT";

}

class MyBills {
  const MyBills();
  String get billType => 'WORKS_BILL_TYPE';
  String get billNumber => 'WORKS_BILL_NUMBER';
  String get billDate => 'WORKS_BILL_DATE';
  String get netPayable => 'EXP_NET_PAYABLE';
  String get invoiceId => 'EXP_INVOICE_NUMBER';
  String get invoiceDate => 'EXP_INVOICE_DATE';
  String get payeeName => 'WORKS_PAYEE_NAME';
  String get noBills => 'EXP_NO_BILLS';
}

class WageSeeker {
  const WageSeeker();
  String get aadhaarRequired => 'AADHAAR_IS_REQUIRED';
  String get nameRequired => 'NAME_IS_REQUIRED';
  String get fatherNameRequired => 'FATHER_NAME_IS_REQUIRED';
  String get dobRequired => 'DOB_IS_REQUIRED';
  String get relationshipRequired => 'RELATIONSHIP_IS_REQUIRED';
  String get socialCatRequired => 'SOCIAL_CATEGORY_IS_REQUIRED';
  String get genderRequired => 'GENDER_IS_REQUIRED';
  String get mobileRequired => 'MOBILE_NUMBER_IS_REQUIRED';
  String get minMobileCharacters => 'MIN_MOBILE_CHARCTERS_REQUIRED';
  String get minAadhaarCharacters => 'MIN_AADHAAR_CHARCTERS_REQUIRED';
  String get maxMobileCharacters => 'MAX_MOBILE_CHARCTERS_REQUIRED';
  String get validMobileCharacters => 'MAX_VALID_MOBILE_CHARCTERS_REQUIRED';
  String get maxAadhaarCharacters => 'MAX_AADHAAR_CHARCTERS_REQUIRED';
  String get pinCodeRequired => 'PINCODE_IS_REQUIRED';
  String get localityRequired => 'LOCALITY_IS_REQUIRED';
  String get wardRequired => 'WARD_IS_REQUIRED';
  String get cityRequired => 'CITY_IS_REQUIRED';
  String get accountHolderNameRequired => 'ACCOUNT_HOLDER_NAME_IS_REQUIRED';
  String get accountNumberRequired => 'ACCOUNT_NUMBER_IS_REQUIRED';
  String get reEnterAccountNumber => 'RE_ENTER_ACCOUNT_NUMBER';
  String get accountTypeRequired => 'ACCOUNT_TYPE_IS_REQUIRED';
  String get ifscCodeRequired => 'IFSC_CODE_IS_REQUIRED';
  String get summaryDetails => 'SUMMARY_DETAILS';
  String get nameOfWageSeeker => 'NAME_OF_WAGE_SEEKER';
  String get skillCategory => 'WAGE_SEEKER_SKILL_CAT';
  String get skill => 'WAGE_SEEKER_SKILL';
  String get createIndSuccess => 'CREATE_INDIVIDUAL_SUCCESS';
  String get ageValidation => 'AGE_LESS_THAN_18_YEARS';
  String get ageInfo => 'AGE_SHOULD_BE_18_OR_ABOVE';
  String get wageSeekerSuccessSubText => 'CREATE_INDIVIDUAL_SUCCESS_SUB_TEXT';
  String get selectSkillValidation => 'CANNOT_HAVE_SAME_SKILL_TYPE';
  String get enterValidIFSC => 'ENTER_VALID_IFSC_CODE';
  String get minAccNoCharacters => 'MIN_ACCOUNT_NO_CHARACTERS_REQUIRED';
  String get maxAccNoCharacters => 'MAX_ACCOUNT_NO_CHARACTERS_REQUIRED';
  String get minNameCharacters => 'MIN_NAME_CHARCTERS_REQUIRED';
  String get minFatherNameCharacters => 'MIN_FATHER_CHARCTERS_REQUIRED';
  String get maxNameCharacters => 'MAX_NAME_CHARCTERS_REQUIRED';
  String get maxFatherNameCharacters => 'MAX_FATHER_CHARCTERS_REQUIRED';
  String get skillsRequired => 'SKILLS_IS_REQUIRED';
  String get pinCodeValidation => 'COMMON_PINCODE_VALIDATION';
  String get maxStreetCharacters => 'MAX_STREET_NAME_CHARACTERS';
  String get maxDoorNoCharacters => 'MAX_DOOR_NO_CHARACTERS';

  // wage seeker indentification flow

  String get identificationHeader => 'WAGE_SEEKER_IDENTIFICATION_HEADER';
  String get personalDetailHeader => 'WAGE_SEEKER_PERSONAL_DETAIL_HEADER';
  String get identityDocumentLabel => 'WAGE_SEEKER_IDENTITY_DOCUMENT_LABEL';
  String get identityNumberLabel => 'WAGE_SEEKER_IDENTITY_NUMBER_LABEL';
  String get identityNameLabel => 'WAGE_SEEKER_IDENTITY_NAME_LABEL';
  String get adharValidate => 'AADHAAR_VALIDATE';
  String get adharVerifySuccess => 'WAGE_SEEKER_AADHAAR_VERIFY_SUCCESS';
  String get adharVerifyError => 'WAGE_SEEKER_AADHAAR_VERIFY_ERROR';
  String get adharVerifyFailed => 'WAGE_SEEKER_AADHAAR_VERIFY_FAILED';
  String get individualSkillHeader => 'WAGE_SEEKER_SKILL_DETAIL_HEADER';
  String get individualPhotoHeader => 'WAGE_SEEKER_PHOTO_DETAIL_HEADER';
}

class AttendanceMgmt {
  const AttendanceMgmt();
  String get registerId => 'REGISTER_ID';
  String get attendanceRegisters => 'ATTENDANCE_REGISTERS';
  String get cboRole => 'CBO_ROLE';
  String get officeInCharge => 'COMMON_DESGN_OF_OFFICER_IN_CHARGE';
  String get projectId => 'WORKS_PROJECT_ID';
  String get projectType => 'WORKS_PROJECT_TYPE';
  String get projectDesc => 'WORKS_PROJECT_DESCRIPTION';
  String get projectName => 'ES_COMMON_PROJECT_NAME';
  String get musterRolls => 'ATM_MUSTER_ROLLS';
  String get musterRollPeriod => 'MUSTER_ROLL_PERIOD';
  String get enrollWageSeeker => 'WORKS_ENROLL_WAGE_SEEKER';
  String get updateAttendance => 'UPDATE_ATTENDANCE';
  String get nameOfWork => 'WORKS_NAME_OF_WORK';
  String get noProjectsFound => 'NO_PROJECTS_FOUND';
  String get winCode => 'ATM_WIN_CODE';
  String get engineerInCharge => 'WORKS_INCHARGE_ENGG';
  String get musterRollId => 'ATM_MUSTER_ROLL_ID';
  String get markAttendanceForTheWeek => 'ATM_MARK_ATTENDENCE_LABEL';
  String get addNewWageSeeker => 'ATM_ADD_NEW_WAGE_SEEKER';
  String get selectDateRangeFirst => 'SELECT_DATE_RANGE_FIRST';
  String get individualsCount => 'REGISTER_INDIVIDUAL_COUNT';
  String get attendanceLoggedSuccess => 'ATM_LOGGED_SUCCESSFULLY';
  String get attendanceLoggedFailed => 'ATM_LOGGED_FAILED';
  String get attendanceCreateFailed => 'ATM_CREATE_REGISTER_FAILED';
  String get attendanceCreateSuccess => 'ATM_CREATE_REGISTER_SUCCESS';
  String get musterUpdateFailed => 'MUSTER_UPDATE_FAILED';
  String get musterUpdateSuccess => 'MUSTER_UPDATE_SUCCESS';
  String get musterCreateFailed => 'MUSTER_CREATE_FAILED';
  String get musterSentForApproval => 'MUSTER_SENT_FOR_APPROVAL_SUCCESS';
  String get applicationInWorkFlow => 'MUSTER_ROLL_IN_WORKFLOW_STATE';
  String get notModifyApprovedApplication => 'CANNOT_MODIFY_APPROVED_MUSTER';
  String get unableToCheckWorkflowStatus => 'MUSTER_ROLL_WORKFLOW_CHECK_FAIL';
  String get resubmitMusterRoll => 'RESUBMIT_MUSTER_ROLL';
  String get attendeeCreateFailed => 'ATTENDEE_CREATE_FAILED';
  String get attendeeCreateSuccess => 'ATTENDEE_CREATE_SUCCESS';
  String get attendeeDeEnrollFailed => 'ATTENDEE_DE_ENROLL_FAILED';
  String get attendeeDeEnrollSuccess => 'ATTENDEE_DE_ENROLL_SUCCESS';
  String get noMusterRollsFound => 'NO_MUSTER_ROLLS_FOUND';
  String get noRegistersFound => 'NO_REGISTERS_FOUND';
  String get skill => 'ATM_SKILLS';
  String get skillDetails => 'ATM_SKILLS_DETAILS';
  String get reviewSkills => 'ATM_REVIEW_SKILLS_FOR_EACH_ATTENDEE';
  String get noSkillPresent => 'ATM_NO_SKILL_IS_PRESENT';
  String get attendanceChangedValidation =>
      'ATM_ATT_CHANGED_CLICK_SAVE_DRAFT_FIRST';
  String get individualDetails => 'ATM_INDIVIDUAL_DETAILS';
  String get editMusterRoll => 'ATM_EDIT_MUSTERROLL';
  String get singleClick => 'ATM_SINGLE_CLICK';
  String get doubleClick => 'ATM_DOUBLE_CLICK';
  String get tripleClick => 'ATM_TRIPLE_CLICK';
  String get fullDay => 'ATM_FULL_DAY';
  String get halfDay => 'ATM_HALF_DAY';
  String get absent => 'ATM_ABSENT';
  String get toMarkAttendance => 'ATM_INFO_TO_MARK_ATTENDANCE';

  String get individualID=>"ATM_INDIVIDUAL_ID";
  String get name=>"ATM_NAME";
  String get attendanceAlert=>"ES_COMMON_ALERT";
  String get sameDayAttendanceError=>"ATM_SAME_DAY_ATTENDANCE_ERROR";
  
}

class MyServiceRequests {
  const MyServiceRequests();

  String get serviceRequestsLabel => 'WORKS_SERVICE_REQUESTS';
  String get timeExtRequestId => 'WORKS_TIME_EXT_REQUEST_ID';
  String get revisedEndDate => 'WORKS_REVISED_END_DATE';
  String get editAction => 'WF_CONTRACT_ACTION_EDIT';
  String get noServiceRequests => 'SR_NO_SERVICE_REQUEST_FOUND';
}

class MeasurementBook {
  const MeasurementBook();

  String get measurementBookTitle => "MEASUREMENT_BOOK_TITLE";
  String get primaryDetails => "PRIMARY_DETAILS";
  String get mbNumber => "MB_NUMBER";
  String get mbSlaDaysRemaining => "MB_SLA_DAYS_REMAINING";
  String get mbShowHistory => "MB_SHOW_HISTORY";
  String get mbWorksitePhotos => "MB_WORKSITE_PHOTOS";
  String get mbNonSor => "MB_NONSOR";
  String get mbSor => "MB_SORS";
  String get projectName => "MB_PROJECT_NAME";
  String get workflowState => "MB_WORKFLOW_STATE";
  String get mbAmount => "MB_AMOUNT";
  String get assignedToAll => "MB_ASSIGNED_TO_ALL";
  String get unit => "MB_UNIT";
  String get description => "MB_DESCRIPTION";
  String get rate => "MB_RATE";
  String get approvedQty => "MB_APPROVER_QUANT";
  String get consumedQty => "MB_CONSUMED_QUANT";
  String get mbStatus => "MB_STATUS";
  String get mbHistory => "MB_HISTORY";
  String get workOrderNumber => "MB_SEARCH_REFERENCE_NUMBER";
  String get measurementPeriod => "MB_MEASUREMENT_PERIOD";
  String get yes=>"MB_YES";
 String get no=>"MB_NO";
 String get widthLabel=>"MB_WIDTH";
 String get lengthLabel=>"MB_LENGTH";
 
 String get quantityLabel=>"MB_QUANTITY";

  // I updated
  String get totalSorAmount => "MB_TOTAL_SOR_AMT";
  String get forCurrentEntry => "MB_FOR_CURRENT_ENTRY";
  String get totalNonSorAmount => "MB_TOTAL_NON_SOR_AMT";
  String get totalMbAmount => "MB_TOTAL_MB_AMT";
  String get openMbBook => "MB_OPEN_BOOK";
  String get currentMBEntry => "CURRENT_MB_ENTRY";
  String get mbAmtCurrentEntry => "MB_AMT_CURRENT_ENTRY";
  String get mbAction => "MB_ACTION";
  String get projectDescription => "MB_PROJECT_DESC";
  String get mbWorkflowState => "MB_WORKFLOW_STATUS";
  String get mbInbox => "MB_INBOX";

  String get createMb => "MB_BUTTON_LEVEL_CREATE";
  String get sort => "MB_SORT";
  String get backToTop => "MB_BACK_TO_TOP";
  String get workOrderInbox => "MB_WORK_ORDER_INBOX";
  String get filter => "MB_FILTER";
  String get clear => "MB_CLEAR";
  String get numberLabel=>"MB_LABEL_NUMBER";

// sort


  String get workFlowState => "WORK_FLOW_STATE";
  String get amountLowToHigh => "AMOUNT_LOW_HIGH";
  String get amountHighToLow => "AMOUNT_HIGH_LOW";

  String get cboName => "CBO_NAME";
  String get sortBy => "SORT_BY";
 String get mbQtyErrMsg=>"MB_MEASUREMENT_QTY_ERROR";
 String get isDeduction=>"MB_IS_DEDUCTION";
 String get item=>"MB_ITEM";
String get heightLabel=>"MB_LABEL_HEIGHT";
String get addMeasurement=>"MB_ADD_MEASUREMENT";
 
 String get workOrderNumberInbox=>"MB_WORK_ORDER_NUMBER";
 String get cboRole=>"CBO_ROLE";
 String get officerInChargeName=>"OFFICER_INCHARGE";
 String get workValue=>"MB_WORK_VALUE";
 String get workSitePhotos=>"MB_WORK_SITE_PHOTO";
 String get noDocumentFound=>"NO_DOCUMENT_FOUND";
 String get delete=>"MB_DELETE";
 String get preConsumedKey=>"MB_CONSUMED_QUANT_KEY";
 String get preConsumedPre=>"MB_CONSUMED_QUANT_KEY_PRE";
 String get mbWorkOrderLabel=>"MB_WORK_ORDERS";
 String get mbMeasurementNumber=>"ACTION_TEST_5MEASUREMENT";
 String get mbCbo=>"MB_CBO";
 String get mbEmployee=>"MB_EMPLOYEE";
 String get mbPhotoInfo=>"MEASUREMENT_UPLOAD_BANNER_LABEL";
 String get searchHint=>"MB_SEARCH_HINT";
 String get projectId=>"MB_PROJECT_ID";
 String get projectType=>"MB_PROJECT_TYPE";
 String get mbcreateLabel=>"WORKS_SUBMIT_MEASUREMENT";
 String get mbSubmitLabel=>"WORKS_MB_FORWARD";
 String get mbCancel=>"WORKS_MB_CANCEL";
 String get noService=>"NO_SERVICE_AVAILABLE";
 String get infoImageTip=>"INFO_CARD_IMAGE";
 String get mbNotFound=>"MB_NUMBER_NOT_FOUND";
 String get imageLimit=>"MB_IMGAE_LIMIT";
 String get imageSize=>"MB_FILE_SIZE";

 String get measurementSummaryLabel=>"MB_SUMMARY_LABEL";

}
