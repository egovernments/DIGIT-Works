// **************************************************************************
// AutoRouteGenerator
// **************************************************************************

// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// AutoRouteGenerator
// **************************************************************************
//
// ignore_for_file: type=lint

part of 'app_router.dart';

class _$AppRouter extends RootStackRouter {
  _$AppRouter([GlobalKey<NavigatorState>? navigatorKey]) : super(navigatorKey);

  @override
  final Map<String, PageFactory> pagesMap = {
    UnauthenticatedRouteWrapper.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const UnauthenticatedPageWrapper(),
      );
    },
    AuthenticatedRouteWrapper.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const AuthenticatedPageWrapper(),
      );
    },
    LanguageSelectionRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const LanguageSelectionPage(),
      );
    },
    LoginRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const LoginPage(),
      );
    },
    OTPVerificationRoute.name: (routeData) {
      final args = routeData.argsAs<OTPVerificationRouteArgs>();
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: OTPVerificationPage(
          key: args.key,
          mobileNumber: args.mobileNumber,
        ),
      );
    },
    HomeRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const HomePage(),
      );
    },
    ORGProfileRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const ORGProfilePage(),
      );
    },
    AttendanceRegisterTableRoute.name: (routeData) {
      final pathParams = routeData.inheritedPathParams;
      final args = routeData.argsAs<AttendanceRegisterTableRouteArgs>(
          orElse: () => AttendanceRegisterTableRouteArgs(
                registerId: pathParams.getString('registerId'),
                tenantId: pathParams.getString('tenantId'),
              ));
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: AttendanceRegisterTablePage(
          args.registerId,
          args.tenantId,
          key: args.key,
        ),
      );
    },
    WorkOrderRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const WorkOrderPage(),
      );
    },
    ViewMusterRollsRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const ViewMusterRollsPage(),
      );
    },
    SHGInboxRoute.name: (routeData) {
      final pathParams = routeData.inheritedPathParams;
      final args = routeData.argsAs<SHGInboxRouteArgs>(
          orElse: () => SHGInboxRouteArgs(
                tenantId: pathParams.getString('tenantId'),
                musterRollNo: pathParams.getString('musterRollNo'),
                sentBackCode: pathParams.getString('sentBackCode'),
              ));
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: SHGInboxPage(
          args.tenantId,
          args.musterRollNo,
          args.sentBackCode,
          key: args.key,
        ),
      );
    },
    TrackAttendanceInboxRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const TrackAttendanceInboxPage(),
      );
    },
    TrackAttendanceRoute.name: (routeData) {
      final pathParams = routeData.inheritedPathParams;
      final args = routeData.argsAs<TrackAttendanceRouteArgs>(
          orElse: () => TrackAttendanceRouteArgs(
                id: pathParams.getString('id'),
                tenantId: pathParams.getString('tenantId'),
              ));
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: TrackAttendancePage(
          args.id,
          args.tenantId,
          key: args.key,
        ),
      );
    },
    RegisterIndividualRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const RegisterIndividualPage(),
      );
    },
    ViewWorkDetailsRoute.name: (routeData) {
      final queryParams = routeData.queryParams;
      final args = routeData.argsAs<ViewWorkDetailsRouteArgs>(
          orElse: () => ViewWorkDetailsRouteArgs(
                contractNumber: queryParams.optString(
                  'contractNumber',
                  'contractNumber',
                ),
                wfStatus: queryParams.optString(
                  'wfStatus',
                  'wfStatus',
                ),
              ));
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: ViewWorkDetailsPage(
          key: args.key,
          contractNumber: args.contractNumber,
          wfStatus: args.wfStatus,
        ),
      );
    },
    SuccessResponseRoute.name: (routeData) {
      final args = routeData.argsAs<SuccessResponseRouteArgs>();
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: SuccessResponsePage(
          key: args.key,
          header: args.header,
          subHeader: args.subHeader,
          subText: args.subText,
          subTitle: args.subTitle,
          callBack: args.callBack,
          callBackWhatsapp: args.callBackWhatsapp,
          callBackDownload: args.callBackDownload,
          callBackPrint: args.callBackPrint,
          backButton: args.backButton,
          buttonLabel: args.buttonLabel,
          isWithoutLogin: args.isWithoutLogin,
          downloadLabel: args.downloadLabel,
          printLabel: args.printLabel,
          whatsAppLabel: args.whatsAppLabel,
          backButtonLabel: args.backButtonLabel,
        ),
      );
    },
    MyBillsRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const MyBillsPage(),
      );
    },
    CreateTimeExtensionRequestRoute.name: (routeData) {
      final queryParams = routeData.queryParams;
      final args = routeData.argsAs<CreateTimeExtensionRequestRouteArgs>(
          orElse: () => CreateTimeExtensionRequestRouteArgs(
                contractNumber: queryParams.optString('contractNumber'),
                isEdit: queryParams.optBool('isEdit'),
              ));
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: CreateTimeExtensionRequestPage(
          key: args.key,
          contractNumber: args.contractNumber,
          isEdit: args.isEdit,
        ),
      );
    },
    MyServiceRequestsRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const MyServiceRequestsPage(),
      );
    },
  };

  @override
  List<RouteConfig> get routes => [
        RouteConfig(
          UnauthenticatedRouteWrapper.name,
          path: '/',
          children: [
            RouteConfig(
              '#redirect',
              path: '',
              parent: UnauthenticatedRouteWrapper.name,
              redirectTo: 'language_selection',
              fullMatch: true,
            ),
            RouteConfig(
              LanguageSelectionRoute.name,
              path: 'language_selection',
              parent: UnauthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              LoginRoute.name,
              path: 'login',
              parent: UnauthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              OTPVerificationRoute.name,
              path: 'otp',
              parent: UnauthenticatedRouteWrapper.name,
            ),
          ],
        ),
        RouteConfig(
          AuthenticatedRouteWrapper.name,
          path: '/',
          children: [
            RouteConfig(
              '#redirect',
              path: '',
              parent: AuthenticatedRouteWrapper.name,
              redirectTo: 'home',
              fullMatch: true,
            ),
            RouteConfig(
              HomeRoute.name,
              path: 'home',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              ORGProfileRoute.name,
              path: 'orgProfile',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              AttendanceRegisterTableRoute.name,
              path: 'manageAttendanceTable/:registerId/:tenantId',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              WorkOrderRoute.name,
              path: 'work-orders',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              ViewMusterRollsRoute.name,
              path: 'muster-rolls',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              SHGInboxRoute.name,
              path: 'shg-inbox/:tenantId/:musterRollNo/:sentBackCode',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              TrackAttendanceInboxRoute.name,
              path: 'track-attendance-inbox',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              TrackAttendanceRoute.name,
              path: 'track-attendance/:id/:tenantId',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              RegisterIndividualRoute.name,
              path: 'register-individual',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              ViewWorkDetailsRoute.name,
              path: 'view-work-order',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              SuccessResponseRoute.name,
              path: 'success',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              MyBillsRoute.name,
              path: 'my-bills',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              CreateTimeExtensionRequestRoute.name,
              path: 'create-time-extension',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              MyServiceRequestsRoute.name,
              path: 'my-service-requests',
              parent: AuthenticatedRouteWrapper.name,
            ),
          ],
        ),
      ];
}

/// generated route for
/// [UnauthenticatedPageWrapper]
class UnauthenticatedRouteWrapper extends PageRouteInfo<void> {
  const UnauthenticatedRouteWrapper({List<PageRouteInfo>? children})
      : super(
          UnauthenticatedRouteWrapper.name,
          path: '/',
          initialChildren: children,
        );

  static const String name = 'UnauthenticatedRouteWrapper';
}

/// generated route for
/// [AuthenticatedPageWrapper]
class AuthenticatedRouteWrapper extends PageRouteInfo<void> {
  const AuthenticatedRouteWrapper({List<PageRouteInfo>? children})
      : super(
          AuthenticatedRouteWrapper.name,
          path: '/',
          initialChildren: children,
        );

  static const String name = 'AuthenticatedRouteWrapper';
}

/// generated route for
/// [LanguageSelectionPage]
class LanguageSelectionRoute extends PageRouteInfo<void> {
  const LanguageSelectionRoute()
      : super(
          LanguageSelectionRoute.name,
          path: 'language_selection',
        );

  static const String name = 'LanguageSelectionRoute';
}

/// generated route for
/// [LoginPage]
class LoginRoute extends PageRouteInfo<void> {
  const LoginRoute()
      : super(
          LoginRoute.name,
          path: 'login',
        );

  static const String name = 'LoginRoute';
}

/// generated route for
/// [OTPVerificationPage]
class OTPVerificationRoute extends PageRouteInfo<OTPVerificationRouteArgs> {
  OTPVerificationRoute({
    Key? key,
    required String mobileNumber,
  }) : super(
          OTPVerificationRoute.name,
          path: 'otp',
          args: OTPVerificationRouteArgs(
            key: key,
            mobileNumber: mobileNumber,
          ),
        );

  static const String name = 'OTPVerificationRoute';
}

class OTPVerificationRouteArgs {
  const OTPVerificationRouteArgs({
    this.key,
    required this.mobileNumber,
  });

  final Key? key;

  final String mobileNumber;

  @override
  String toString() {
    return 'OTPVerificationRouteArgs{key: $key, mobileNumber: $mobileNumber}';
  }
}

/// generated route for
/// [HomePage]
class HomeRoute extends PageRouteInfo<void> {
  const HomeRoute()
      : super(
          HomeRoute.name,
          path: 'home',
        );

  static const String name = 'HomeRoute';
}

/// generated route for
/// [ORGProfilePage]
class ORGProfileRoute extends PageRouteInfo<void> {
  const ORGProfileRoute()
      : super(
          ORGProfileRoute.name,
          path: 'orgProfile',
        );

  static const String name = 'ORGProfileRoute';
}

/// generated route for
/// [AttendanceRegisterTablePage]
class AttendanceRegisterTableRoute
    extends PageRouteInfo<AttendanceRegisterTableRouteArgs> {
  AttendanceRegisterTableRoute({
    required String registerId,
    required String tenantId,
    Key? key,
  }) : super(
          AttendanceRegisterTableRoute.name,
          path: 'manageAttendanceTable/:registerId/:tenantId',
          args: AttendanceRegisterTableRouteArgs(
            registerId: registerId,
            tenantId: tenantId,
            key: key,
          ),
          rawPathParams: {
            'registerId': registerId,
            'tenantId': tenantId,
          },
        );

  static const String name = 'AttendanceRegisterTableRoute';
}

class AttendanceRegisterTableRouteArgs {
  const AttendanceRegisterTableRouteArgs({
    required this.registerId,
    required this.tenantId,
    this.key,
  });

  final String registerId;

  final String tenantId;

  final Key? key;

  @override
  String toString() {
    return 'AttendanceRegisterTableRouteArgs{registerId: $registerId, tenantId: $tenantId, key: $key}';
  }
}

/// generated route for
/// [WorkOrderPage]
class WorkOrderRoute extends PageRouteInfo<void> {
  const WorkOrderRoute()
      : super(
          WorkOrderRoute.name,
          path: 'work-orders',
        );

  static const String name = 'WorkOrderRoute';
}

/// generated route for
/// [ViewMusterRollsPage]
class ViewMusterRollsRoute extends PageRouteInfo<void> {
  const ViewMusterRollsRoute()
      : super(
          ViewMusterRollsRoute.name,
          path: 'muster-rolls',
        );

  static const String name = 'ViewMusterRollsRoute';
}

/// generated route for
/// [SHGInboxPage]
class SHGInboxRoute extends PageRouteInfo<SHGInboxRouteArgs> {
  SHGInboxRoute({
    required String tenantId,
    required String musterRollNo,
    required String sentBackCode,
    Key? key,
  }) : super(
          SHGInboxRoute.name,
          path: 'shg-inbox/:tenantId/:musterRollNo/:sentBackCode',
          args: SHGInboxRouteArgs(
            tenantId: tenantId,
            musterRollNo: musterRollNo,
            sentBackCode: sentBackCode,
            key: key,
          ),
          rawPathParams: {
            'tenantId': tenantId,
            'musterRollNo': musterRollNo,
            'sentBackCode': sentBackCode,
          },
        );

  static const String name = 'SHGInboxRoute';
}

class SHGInboxRouteArgs {
  const SHGInboxRouteArgs({
    required this.tenantId,
    required this.musterRollNo,
    required this.sentBackCode,
    this.key,
  });

  final String tenantId;

  final String musterRollNo;

  final String sentBackCode;

  final Key? key;

  @override
  String toString() {
    return 'SHGInboxRouteArgs{tenantId: $tenantId, musterRollNo: $musterRollNo, sentBackCode: $sentBackCode, key: $key}';
  }
}

/// generated route for
/// [TrackAttendanceInboxPage]
class TrackAttendanceInboxRoute extends PageRouteInfo<void> {
  const TrackAttendanceInboxRoute()
      : super(
          TrackAttendanceInboxRoute.name,
          path: 'track-attendance-inbox',
        );

  static const String name = 'TrackAttendanceInboxRoute';
}

/// generated route for
/// [TrackAttendancePage]
class TrackAttendanceRoute extends PageRouteInfo<TrackAttendanceRouteArgs> {
  TrackAttendanceRoute({
    required String id,
    required String tenantId,
    Key? key,
  }) : super(
          TrackAttendanceRoute.name,
          path: 'track-attendance/:id/:tenantId',
          args: TrackAttendanceRouteArgs(
            id: id,
            tenantId: tenantId,
            key: key,
          ),
          rawPathParams: {
            'id': id,
            'tenantId': tenantId,
          },
        );

  static const String name = 'TrackAttendanceRoute';
}

class TrackAttendanceRouteArgs {
  const TrackAttendanceRouteArgs({
    required this.id,
    required this.tenantId,
    this.key,
  });

  final String id;

  final String tenantId;

  final Key? key;

  @override
  String toString() {
    return 'TrackAttendanceRouteArgs{id: $id, tenantId: $tenantId, key: $key}';
  }
}

/// generated route for
/// [RegisterIndividualPage]
class RegisterIndividualRoute extends PageRouteInfo<void> {
  const RegisterIndividualRoute()
      : super(
          RegisterIndividualRoute.name,
          path: 'register-individual',
        );

  static const String name = 'RegisterIndividualRoute';
}

/// generated route for
/// [ViewWorkDetailsPage]
class ViewWorkDetailsRoute extends PageRouteInfo<ViewWorkDetailsRouteArgs> {
  ViewWorkDetailsRoute({
    Key? key,
    String? contractNumber = 'contractNumber',
    String? wfStatus = 'wfStatus',
  }) : super(
          ViewWorkDetailsRoute.name,
          path: 'view-work-order',
          args: ViewWorkDetailsRouteArgs(
            key: key,
            contractNumber: contractNumber,
            wfStatus: wfStatus,
          ),
          rawQueryParams: {
            'contractNumber': contractNumber,
            'wfStatus': wfStatus,
          },
        );

  static const String name = 'ViewWorkDetailsRoute';
}

class ViewWorkDetailsRouteArgs {
  const ViewWorkDetailsRouteArgs({
    this.key,
    this.contractNumber = 'contractNumber',
    this.wfStatus = 'wfStatus',
  });

  final Key? key;

  final String? contractNumber;

  final String? wfStatus;

  @override
  String toString() {
    return 'ViewWorkDetailsRouteArgs{key: $key, contractNumber: $contractNumber, wfStatus: $wfStatus}';
  }
}

/// generated route for
/// [SuccessResponsePage]
class SuccessResponseRoute extends PageRouteInfo<SuccessResponseRouteArgs> {
  SuccessResponseRoute({
    Key? key,
    required String header,
    String? subHeader,
    String? subText,
    String? subTitle,
    void Function()? callBack,
    void Function()? callBackWhatsapp,
    void Function()? callBackDownload,
    void Function()? callBackPrint,
    bool? backButton,
    String? buttonLabel,
    bool isWithoutLogin = false,
    String? downloadLabel,
    String? printLabel,
    String? whatsAppLabel,
    String? backButtonLabel,
  }) : super(
          SuccessResponseRoute.name,
          path: 'success',
          args: SuccessResponseRouteArgs(
            key: key,
            header: header,
            subHeader: subHeader,
            subText: subText,
            subTitle: subTitle,
            callBack: callBack,
            callBackWhatsapp: callBackWhatsapp,
            callBackDownload: callBackDownload,
            callBackPrint: callBackPrint,
            backButton: backButton,
            buttonLabel: buttonLabel,
            isWithoutLogin: isWithoutLogin,
            downloadLabel: downloadLabel,
            printLabel: printLabel,
            whatsAppLabel: whatsAppLabel,
            backButtonLabel: backButtonLabel,
          ),
        );

  static const String name = 'SuccessResponseRoute';
}

class SuccessResponseRouteArgs {
  const SuccessResponseRouteArgs({
    this.key,
    required this.header,
    this.subHeader,
    this.subText,
    this.subTitle,
    this.callBack,
    this.callBackWhatsapp,
    this.callBackDownload,
    this.callBackPrint,
    this.backButton,
    this.buttonLabel,
    this.isWithoutLogin = false,
    this.downloadLabel,
    this.printLabel,
    this.whatsAppLabel,
    this.backButtonLabel,
  });

  final Key? key;

  final String header;

  final String? subHeader;

  final String? subText;

  final String? subTitle;

  final void Function()? callBack;

  final void Function()? callBackWhatsapp;

  final void Function()? callBackDownload;

  final void Function()? callBackPrint;

  final bool? backButton;

  final String? buttonLabel;

  final bool isWithoutLogin;

  final String? downloadLabel;

  final String? printLabel;

  final String? whatsAppLabel;

  final String? backButtonLabel;

  @override
  String toString() {
    return 'SuccessResponseRouteArgs{key: $key, header: $header, subHeader: $subHeader, subText: $subText, subTitle: $subTitle, callBack: $callBack, callBackWhatsapp: $callBackWhatsapp, callBackDownload: $callBackDownload, callBackPrint: $callBackPrint, backButton: $backButton, buttonLabel: $buttonLabel, isWithoutLogin: $isWithoutLogin, downloadLabel: $downloadLabel, printLabel: $printLabel, whatsAppLabel: $whatsAppLabel, backButtonLabel: $backButtonLabel}';
  }
}

/// generated route for
/// [MyBillsPage]
class MyBillsRoute extends PageRouteInfo<void> {
  const MyBillsRoute()
      : super(
          MyBillsRoute.name,
          path: 'my-bills',
        );

  static const String name = 'MyBillsRoute';
}

/// generated route for
/// [CreateTimeExtensionRequestPage]
class CreateTimeExtensionRequestRoute
    extends PageRouteInfo<CreateTimeExtensionRequestRouteArgs> {
  CreateTimeExtensionRequestRoute({
    Key? key,
    String? contractNumber,
    bool? isEdit,
  }) : super(
          CreateTimeExtensionRequestRoute.name,
          path: 'create-time-extension',
          args: CreateTimeExtensionRequestRouteArgs(
            key: key,
            contractNumber: contractNumber,
            isEdit: isEdit,
          ),
          rawQueryParams: {
            'contractNumber': contractNumber,
            'isEdit': isEdit,
          },
        );

  static const String name = 'CreateTimeExtensionRequestRoute';
}

class CreateTimeExtensionRequestRouteArgs {
  const CreateTimeExtensionRequestRouteArgs({
    this.key,
    this.contractNumber,
    this.isEdit,
  });

  final Key? key;

  final String? contractNumber;

  final bool? isEdit;

  @override
  String toString() {
    return 'CreateTimeExtensionRequestRouteArgs{key: $key, contractNumber: $contractNumber, isEdit: $isEdit}';
  }
}

/// generated route for
/// [MyServiceRequestsPage]
class MyServiceRequestsRoute extends PageRouteInfo<void> {
  const MyServiceRequestsRoute()
      : super(
          MyServiceRequestsRoute.name,
          path: 'my-service-requests',
        );

  static const String name = 'MyServiceRequestsRoute';
}
