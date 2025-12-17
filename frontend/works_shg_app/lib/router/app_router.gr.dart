// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// AutoRouterGenerator
// **************************************************************************

// ignore_for_file: type=lint
// coverage:ignore-file

part of 'app_router.dart';

abstract class _$AppRouter extends RootStackRouter {
  // ignore: unused_element
  _$AppRouter({super.navigatorKey});

  @override
  final Map<String, PageFactory> pagesMap = {
    AttendanceRegisterTableRoute.name: (routeData) {
      final pathParams = routeData.inheritedPathParams;
      final args = routeData.argsAs<AttendanceRegisterTableRouteArgs>(
          orElse: () => AttendanceRegisterTableRouteArgs(
                registerId: pathParams.getString('registerId'),
                tenantId: pathParams.getString('tenantId'),
              ));
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: AttendanceRegisterTablePage(
          args.registerId,
          args.tenantId,
          key: args.key,
        ),
      );
    },
    AuthenticatedWrapperRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const AuthenticatedWrapperPage(),
      );
    },
    CreateTimeExtensionRequestRoute.name: (routeData) {
      final queryParams = routeData.queryParams;
      final args = routeData.argsAs<CreateTimeExtensionRequestRouteArgs>(
          orElse: () => CreateTimeExtensionRequestRouteArgs(
                contractNumber: queryParams.optString('contractNumber'),
                isEdit: queryParams.optBool('isEdit'),
              ));
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: CreateTimeExtensionRequestPage(
          key: args.key,
          contractNumber: args.contractNumber,
          isEdit: args.isEdit,
        ),
      );
    },
    HomeRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const HomePage(),
      );
    },
    LanguageSelectionRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const LanguageSelectionPage(),
      );
    },
    LoginRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const LoginPage(),
      );
    },
    MBDetailRoute.name: (routeData) {
      final args = routeData.argsAs<MBDetailRouteArgs>();
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: MBDetailPage(
          key: args.key,
          contractNumber: args.contractNumber,
          mbNumber: args.mbNumber,
          tenantId: args.tenantId,
          type: args.type,
        ),
      );
    },
    MBFilterRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const MBFilterPage(),
      );
    },
    MBHistoryBookRoute.name: (routeData) {
      final args = routeData.argsAs<MBHistoryBookRouteArgs>();
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: MBHistoryBookPage(
          key: args.key,
          contractNumber: args.contractNumber,
          mbNumber: args.mbNumber,
          tenantId: args.tenantId,
          type: args.type,
        ),
      );
    },
    MBMusterScreenRoute.name: (routeData) {
      final args = routeData.argsAs<MBMusterScreenRouteArgs>();
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: MBMusterScreenPage(
          key: args.key,
          tenantId: args.tenantId,
          musterRollNumber: args.musterRollNumber,
          mbNumber: args.mbNumber,
        ),
      );
    },
    MBTypeConfirmationRoute.name: (routeData) {
      final args = routeData.argsAs<MBTypeConfirmationRouteArgs>();
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: MBTypeConfirmationPage(
          key: args.key,
          nextActions: args.nextActions,
          contractNumber: args.contractNumber,
          mbNumber: args.mbNumber,
          type: args.type,
          stateActions: args.stateActions,
        ),
      );
    },
    MeasurementBookInboxRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const MeasurementBookInboxPage(),
      );
    },
    MeasurementBookWrapperRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: WrappedRoute(child: const MeasurementBookWrapperPage()),
      );
    },
    MyBillsRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const MyBillsPage(),
      );
    },
    MyServiceRequestsRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const MyServiceRequestsPage(),
      );
    },
    ORGProfileRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const ORGProfilePage(),
      );
    },
    OTPVerificationRoute.name: (routeData) {
      final args = routeData.argsAs<OTPVerificationRouteArgs>();
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: OTPVerificationPage(
          key: args.key,
          mobileNumber: args.mobileNumber,
        ),
      );
    },
    RegisterIndividualRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const RegisterIndividualPage(),
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
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: SHGInboxPage(
          args.tenantId,
          args.musterRollNo,
          args.sentBackCode,
          key: args.key,
        ),
      );
    },
    SuccessResponseRoute.name: (routeData) {
      final args = routeData.argsAs<SuccessResponseRouteArgs>();
      return AutoRoutePage<dynamic>(
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
    TrackAttendanceInboxRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
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
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: TrackAttendancePage(
          args.id,
          args.tenantId,
          key: args.key,
        ),
      );
    },
    UnauthenticatedWrapperRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const UnauthenticatedWrapperPage(),
      );
    },
    ViewMusterRollsRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const ViewMusterRollsPage(),
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
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: ViewWorkDetailsPage(
          key: args.key,
          contractNumber: args.contractNumber,
          wfStatus: args.wfStatus,
        ),
      );
    },
    WOFilterRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const WOFilterPage(),
      );
    },
    WorkOderInboxRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const WorkOderInboxPage(),
      );
    },
    WorkOrderRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: const WorkOrderPage(),
      );
    },
    WorkOrderWrapperRoute.name: (routeData) {
      return AutoRoutePage<dynamic>(
        routeData: routeData,
        child: WrappedRoute(child: const WorkOrderWrapperPage()),
      );
    },
  };
}

/// generated route for
/// [AttendanceRegisterTablePage]
class AttendanceRegisterTableRoute
    extends PageRouteInfo<AttendanceRegisterTableRouteArgs> {
  AttendanceRegisterTableRoute({
    required String registerId,
    required String tenantId,
    Key? key,
    List<PageRouteInfo>? children,
  }) : super(
          AttendanceRegisterTableRoute.name,
          args: AttendanceRegisterTableRouteArgs(
            registerId: registerId,
            tenantId: tenantId,
            key: key,
          ),
          rawPathParams: {
            'registerId': registerId,
            'tenantId': tenantId,
          },
          initialChildren: children,
        );

  static const String name = 'AttendanceRegisterTableRoute';

  static const PageInfo<AttendanceRegisterTableRouteArgs> page =
      PageInfo<AttendanceRegisterTableRouteArgs>(name);
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
/// [AuthenticatedWrapperPage]
class AuthenticatedWrapperRoute extends PageRouteInfo<void> {
  const AuthenticatedWrapperRoute({List<PageRouteInfo>? children})
      : super(
          AuthenticatedWrapperRoute.name,
          initialChildren: children,
        );

  static const String name = 'AuthenticatedWrapperRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [CreateTimeExtensionRequestPage]
class CreateTimeExtensionRequestRoute
    extends PageRouteInfo<CreateTimeExtensionRequestRouteArgs> {
  CreateTimeExtensionRequestRoute({
    Key? key,
    String? contractNumber,
    bool? isEdit,
    List<PageRouteInfo>? children,
  }) : super(
          CreateTimeExtensionRequestRoute.name,
          args: CreateTimeExtensionRequestRouteArgs(
            key: key,
            contractNumber: contractNumber,
            isEdit: isEdit,
          ),
          rawQueryParams: {
            'contractNumber': contractNumber,
            'isEdit': isEdit,
          },
          initialChildren: children,
        );

  static const String name = 'CreateTimeExtensionRequestRoute';

  static const PageInfo<CreateTimeExtensionRequestRouteArgs> page =
      PageInfo<CreateTimeExtensionRequestRouteArgs>(name);
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
/// [HomePage]
class HomeRoute extends PageRouteInfo<void> {
  const HomeRoute({List<PageRouteInfo>? children})
      : super(
          HomeRoute.name,
          initialChildren: children,
        );

  static const String name = 'HomeRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [LanguageSelectionPage]
class LanguageSelectionRoute extends PageRouteInfo<void> {
  const LanguageSelectionRoute({List<PageRouteInfo>? children})
      : super(
          LanguageSelectionRoute.name,
          initialChildren: children,
        );

  static const String name = 'LanguageSelectionRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [LoginPage]
class LoginRoute extends PageRouteInfo<void> {
  const LoginRoute({List<PageRouteInfo>? children})
      : super(
          LoginRoute.name,
          initialChildren: children,
        );

  static const String name = 'LoginRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [MBDetailPage]
class MBDetailRoute extends PageRouteInfo<MBDetailRouteArgs> {
  MBDetailRoute({
    Key? key,
    required String contractNumber,
    required String mbNumber,
    String? tenantId,
    required MBScreen type,
    List<PageRouteInfo>? children,
  }) : super(
          MBDetailRoute.name,
          args: MBDetailRouteArgs(
            key: key,
            contractNumber: contractNumber,
            mbNumber: mbNumber,
            tenantId: tenantId,
            type: type,
          ),
          initialChildren: children,
        );

  static const String name = 'MBDetailRoute';

  static const PageInfo<MBDetailRouteArgs> page =
      PageInfo<MBDetailRouteArgs>(name);
}

class MBDetailRouteArgs {
  const MBDetailRouteArgs({
    this.key,
    required this.contractNumber,
    required this.mbNumber,
    this.tenantId,
    required this.type,
  });

  final Key? key;

  final String contractNumber;

  final String mbNumber;

  final String? tenantId;

  final MBScreen type;

  @override
  String toString() {
    return 'MBDetailRouteArgs{key: $key, contractNumber: $contractNumber, mbNumber: $mbNumber, tenantId: $tenantId, type: $type}';
  }
}

/// generated route for
/// [MBFilterPage]
class MBFilterRoute extends PageRouteInfo<void> {
  const MBFilterRoute({List<PageRouteInfo>? children})
      : super(
          MBFilterRoute.name,
          initialChildren: children,
        );

  static const String name = 'MBFilterRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [MBHistoryBookPage]
class MBHistoryBookRoute extends PageRouteInfo<MBHistoryBookRouteArgs> {
  MBHistoryBookRoute({
    Key? key,
    required String contractNumber,
    required String mbNumber,
    String? tenantId,
    required MBScreen type,
    List<PageRouteInfo>? children,
  }) : super(
          MBHistoryBookRoute.name,
          args: MBHistoryBookRouteArgs(
            key: key,
            contractNumber: contractNumber,
            mbNumber: mbNumber,
            tenantId: tenantId,
            type: type,
          ),
          initialChildren: children,
        );

  static const String name = 'MBHistoryBookRoute';

  static const PageInfo<MBHistoryBookRouteArgs> page =
      PageInfo<MBHistoryBookRouteArgs>(name);
}

class MBHistoryBookRouteArgs {
  const MBHistoryBookRouteArgs({
    this.key,
    required this.contractNumber,
    required this.mbNumber,
    this.tenantId,
    required this.type,
  });

  final Key? key;

  final String contractNumber;

  final String mbNumber;

  final String? tenantId;

  final MBScreen type;

  @override
  String toString() {
    return 'MBHistoryBookRouteArgs{key: $key, contractNumber: $contractNumber, mbNumber: $mbNumber, tenantId: $tenantId, type: $type}';
  }
}

/// generated route for
/// [MBMusterScreenPage]
class MBMusterScreenRoute extends PageRouteInfo<MBMusterScreenRouteArgs> {
  MBMusterScreenRoute({
    Key? key,
    required String tenantId,
    required String musterRollNumber,
    required String mbNumber,
    List<PageRouteInfo>? children,
  }) : super(
          MBMusterScreenRoute.name,
          args: MBMusterScreenRouteArgs(
            key: key,
            tenantId: tenantId,
            musterRollNumber: musterRollNumber,
            mbNumber: mbNumber,
          ),
          initialChildren: children,
        );

  static const String name = 'MBMusterScreenRoute';

  static const PageInfo<MBMusterScreenRouteArgs> page =
      PageInfo<MBMusterScreenRouteArgs>(name);
}

class MBMusterScreenRouteArgs {
  const MBMusterScreenRouteArgs({
    this.key,
    required this.tenantId,
    required this.musterRollNumber,
    required this.mbNumber,
  });

  final Key? key;

  final String tenantId;

  final String musterRollNumber;

  final String mbNumber;

  @override
  String toString() {
    return 'MBMusterScreenRouteArgs{key: $key, tenantId: $tenantId, musterRollNumber: $musterRollNumber, mbNumber: $mbNumber}';
  }
}

/// generated route for
/// [MBTypeConfirmationPage]
class MBTypeConfirmationRoute
    extends PageRouteInfo<MBTypeConfirmationRouteArgs> {
  MBTypeConfirmationRoute({
    Key? key,
    required NextActions? nextActions,
    String? contractNumber,
    String? mbNumber,
    required MBScreen type,
    StateActions? stateActions,
    List<PageRouteInfo>? children,
  }) : super(
          MBTypeConfirmationRoute.name,
          args: MBTypeConfirmationRouteArgs(
            key: key,
            nextActions: nextActions,
            contractNumber: contractNumber,
            mbNumber: mbNumber,
            type: type,
            stateActions: stateActions,
          ),
          initialChildren: children,
        );

  static const String name = 'MBTypeConfirmationRoute';

  static const PageInfo<MBTypeConfirmationRouteArgs> page =
      PageInfo<MBTypeConfirmationRouteArgs>(name);
}

class MBTypeConfirmationRouteArgs {
  const MBTypeConfirmationRouteArgs({
    this.key,
    required this.nextActions,
    this.contractNumber,
    this.mbNumber,
    required this.type,
    this.stateActions,
  });

  final Key? key;

  final NextActions? nextActions;

  final String? contractNumber;

  final String? mbNumber;

  final MBScreen type;

  final StateActions? stateActions;

  @override
  String toString() {
    return 'MBTypeConfirmationRouteArgs{key: $key, nextActions: $nextActions, contractNumber: $contractNumber, mbNumber: $mbNumber, type: $type, stateActions: $stateActions}';
  }
}

/// generated route for
/// [MeasurementBookInboxPage]
class MeasurementBookInboxRoute extends PageRouteInfo<void> {
  const MeasurementBookInboxRoute({List<PageRouteInfo>? children})
      : super(
          MeasurementBookInboxRoute.name,
          initialChildren: children,
        );

  static const String name = 'MeasurementBookInboxRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [MeasurementBookWrapperPage]
class MeasurementBookWrapperRoute extends PageRouteInfo<void> {
  const MeasurementBookWrapperRoute({List<PageRouteInfo>? children})
      : super(
          MeasurementBookWrapperRoute.name,
          initialChildren: children,
        );

  static const String name = 'MeasurementBookWrapperRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [MyBillsPage]
class MyBillsRoute extends PageRouteInfo<void> {
  const MyBillsRoute({List<PageRouteInfo>? children})
      : super(
          MyBillsRoute.name,
          initialChildren: children,
        );

  static const String name = 'MyBillsRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [MyServiceRequestsPage]
class MyServiceRequestsRoute extends PageRouteInfo<void> {
  const MyServiceRequestsRoute({List<PageRouteInfo>? children})
      : super(
          MyServiceRequestsRoute.name,
          initialChildren: children,
        );

  static const String name = 'MyServiceRequestsRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [ORGProfilePage]
class ORGProfileRoute extends PageRouteInfo<void> {
  const ORGProfileRoute({List<PageRouteInfo>? children})
      : super(
          ORGProfileRoute.name,
          initialChildren: children,
        );

  static const String name = 'ORGProfileRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [OTPVerificationPage]
class OTPVerificationRoute extends PageRouteInfo<OTPVerificationRouteArgs> {
  OTPVerificationRoute({
    Key? key,
    required String mobileNumber,
    List<PageRouteInfo>? children,
  }) : super(
          OTPVerificationRoute.name,
          args: OTPVerificationRouteArgs(
            key: key,
            mobileNumber: mobileNumber,
          ),
          initialChildren: children,
        );

  static const String name = 'OTPVerificationRoute';

  static const PageInfo<OTPVerificationRouteArgs> page =
      PageInfo<OTPVerificationRouteArgs>(name);
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
/// [RegisterIndividualPage]
class RegisterIndividualRoute extends PageRouteInfo<void> {
  const RegisterIndividualRoute({List<PageRouteInfo>? children})
      : super(
          RegisterIndividualRoute.name,
          initialChildren: children,
        );

  static const String name = 'RegisterIndividualRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [SHGInboxPage]
class SHGInboxRoute extends PageRouteInfo<SHGInboxRouteArgs> {
  SHGInboxRoute({
    required String tenantId,
    required String musterRollNo,
    required String sentBackCode,
    Key? key,
    List<PageRouteInfo>? children,
  }) : super(
          SHGInboxRoute.name,
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
          initialChildren: children,
        );

  static const String name = 'SHGInboxRoute';

  static const PageInfo<SHGInboxRouteArgs> page =
      PageInfo<SHGInboxRouteArgs>(name);
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
    List<PageRouteInfo>? children,
  }) : super(
          SuccessResponseRoute.name,
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
          initialChildren: children,
        );

  static const String name = 'SuccessResponseRoute';

  static const PageInfo<SuccessResponseRouteArgs> page =
      PageInfo<SuccessResponseRouteArgs>(name);
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
/// [TrackAttendanceInboxPage]
class TrackAttendanceInboxRoute extends PageRouteInfo<void> {
  const TrackAttendanceInboxRoute({List<PageRouteInfo>? children})
      : super(
          TrackAttendanceInboxRoute.name,
          initialChildren: children,
        );

  static const String name = 'TrackAttendanceInboxRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [TrackAttendancePage]
class TrackAttendanceRoute extends PageRouteInfo<TrackAttendanceRouteArgs> {
  TrackAttendanceRoute({
    required String id,
    required String tenantId,
    Key? key,
    List<PageRouteInfo>? children,
  }) : super(
          TrackAttendanceRoute.name,
          args: TrackAttendanceRouteArgs(
            id: id,
            tenantId: tenantId,
            key: key,
          ),
          rawPathParams: {
            'id': id,
            'tenantId': tenantId,
          },
          initialChildren: children,
        );

  static const String name = 'TrackAttendanceRoute';

  static const PageInfo<TrackAttendanceRouteArgs> page =
      PageInfo<TrackAttendanceRouteArgs>(name);
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
/// [UnauthenticatedWrapperPage]
class UnauthenticatedWrapperRoute extends PageRouteInfo<void> {
  const UnauthenticatedWrapperRoute({List<PageRouteInfo>? children})
      : super(
          UnauthenticatedWrapperRoute.name,
          initialChildren: children,
        );

  static const String name = 'UnauthenticatedWrapperRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [ViewMusterRollsPage]
class ViewMusterRollsRoute extends PageRouteInfo<void> {
  const ViewMusterRollsRoute({List<PageRouteInfo>? children})
      : super(
          ViewMusterRollsRoute.name,
          initialChildren: children,
        );

  static const String name = 'ViewMusterRollsRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [ViewWorkDetailsPage]
class ViewWorkDetailsRoute extends PageRouteInfo<ViewWorkDetailsRouteArgs> {
  ViewWorkDetailsRoute({
    Key? key,
    String? contractNumber = 'contractNumber',
    String? wfStatus = 'wfStatus',
    List<PageRouteInfo>? children,
  }) : super(
          ViewWorkDetailsRoute.name,
          args: ViewWorkDetailsRouteArgs(
            key: key,
            contractNumber: contractNumber,
            wfStatus: wfStatus,
          ),
          rawQueryParams: {
            'contractNumber': contractNumber,
            'wfStatus': wfStatus,
          },
          initialChildren: children,
        );

  static const String name = 'ViewWorkDetailsRoute';

  static const PageInfo<ViewWorkDetailsRouteArgs> page =
      PageInfo<ViewWorkDetailsRouteArgs>(name);
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
/// [WOFilterPage]
class WOFilterRoute extends PageRouteInfo<void> {
  const WOFilterRoute({List<PageRouteInfo>? children})
      : super(
          WOFilterRoute.name,
          initialChildren: children,
        );

  static const String name = 'WOFilterRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [WorkOderInboxPage]
class WorkOderInboxRoute extends PageRouteInfo<void> {
  const WorkOderInboxRoute({List<PageRouteInfo>? children})
      : super(
          WorkOderInboxRoute.name,
          initialChildren: children,
        );

  static const String name = 'WorkOderInboxRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [WorkOrderPage]
class WorkOrderRoute extends PageRouteInfo<void> {
  const WorkOrderRoute({List<PageRouteInfo>? children})
      : super(
          WorkOrderRoute.name,
          initialChildren: children,
        );

  static const String name = 'WorkOrderRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}

/// generated route for
/// [WorkOrderWrapperPage]
class WorkOrderWrapperRoute extends PageRouteInfo<void> {
  const WorkOrderWrapperRoute({List<PageRouteInfo>? children})
      : super(
          WorkOrderWrapperRoute.name,
          initialChildren: children,
        );

  static const String name = 'WorkOrderWrapperRoute';

  static const PageInfo<void> page = PageInfo<void>(name);
}
