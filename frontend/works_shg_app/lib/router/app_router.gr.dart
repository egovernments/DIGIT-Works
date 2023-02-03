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
      final args = routeData.argsAs<LoginRouteArgs>(
          orElse: () => const LoginRouteArgs());
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: LoginPage(key: args.key),
      );
    },
    HomeRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const HomePage(),
      );
    },
    AttendanceRegisterTableRoute.name: (routeData) {
      final args = routeData.argsAs<AttendanceRegisterTableRouteArgs>();
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: AttendanceRegisterTablePage(
          args.projectDetails,
          args.attendanceRegister,
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
      final args = routeData.argsAs<SHGInboxRouteArgs>();
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: SHGInboxPage(
          args.id,
          args.tenantId,
          args.projectDetails,
          key: args.key,
        ),
      );
    },
    ManageAttendanceRegisterRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const ManageAttendanceRegisterPage(),
      );
    },
    AttendanceInboxRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const AttendanceInboxPage(),
      );
    },
    TrackAttendanceRoute.name: (routeData) {
      final args = routeData.argsAs<TrackAttendanceRouteArgs>();
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: TrackAttendancePage(
          args.id,
          args.tenantId,
          args.projectDetails,
          args.attendanceRegister,
          key: args.key,
        ),
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
          ],
        ),
        RouteConfig(
          AuthenticatedRouteWrapper.name,
          path: '/',
          children: [
            RouteConfig(
              HomeRoute.name,
              path: '',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              AttendanceRegisterTableRoute.name,
              path: 'manageAttendance',
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
              path: 'shg-inbox/:id/:tenantId',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              ManageAttendanceRegisterRoute.name,
              path: 'manage-attendance',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              AttendanceInboxRoute.name,
              path: 'attendance-inbox',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              TrackAttendanceRoute.name,
              path: 'track-attendance/:id/:tenantId',
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
class LoginRoute extends PageRouteInfo<LoginRouteArgs> {
  LoginRoute({Key? key})
      : super(
          LoginRoute.name,
          path: 'login',
          args: LoginRouteArgs(key: key),
        );

  static const String name = 'LoginRoute';
}

class LoginRouteArgs {
  const LoginRouteArgs({this.key});

  final Key? key;

  @override
  String toString() {
    return 'LoginRouteArgs{key: $key}';
  }
}

/// generated route for
/// [HomePage]
class HomeRoute extends PageRouteInfo<void> {
  const HomeRoute()
      : super(
          HomeRoute.name,
          path: '',
        );

  static const String name = 'HomeRoute';
}

/// generated route for
/// [AttendanceRegisterTablePage]
class AttendanceRegisterTableRoute
    extends PageRouteInfo<AttendanceRegisterTableRouteArgs> {
  AttendanceRegisterTableRoute({
    required List<Map<String, dynamic>> projectDetails,
    required AttendanceRegister? attendanceRegister,
    Key? key,
  }) : super(
          AttendanceRegisterTableRoute.name,
          path: 'manageAttendance',
          args: AttendanceRegisterTableRouteArgs(
            projectDetails: projectDetails,
            attendanceRegister: attendanceRegister,
            key: key,
          ),
        );

  static const String name = 'AttendanceRegisterTableRoute';
}

class AttendanceRegisterTableRouteArgs {
  const AttendanceRegisterTableRouteArgs({
    required this.projectDetails,
    required this.attendanceRegister,
    this.key,
  });

  final List<Map<String, dynamic>> projectDetails;

  final AttendanceRegister? attendanceRegister;

  final Key? key;

  @override
  String toString() {
    return 'AttendanceRegisterTableRouteArgs{projectDetails: $projectDetails, attendanceRegister: $attendanceRegister, key: $key}';
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
    required String id,
    required String tenantId,
    required List<Map<String, dynamic>> projectDetails,
    Key? key,
  }) : super(
          SHGInboxRoute.name,
          path: 'shg-inbox/:id/:tenantId',
          args: SHGInboxRouteArgs(
            id: id,
            tenantId: tenantId,
            projectDetails: projectDetails,
            key: key,
          ),
          rawPathParams: {
            'id': id,
            'tenantId': tenantId,
          },
        );

  static const String name = 'SHGInboxRoute';
}

class SHGInboxRouteArgs {
  const SHGInboxRouteArgs({
    required this.id,
    required this.tenantId,
    required this.projectDetails,
    this.key,
  });

  final String id;

  final String tenantId;

  final List<Map<String, dynamic>> projectDetails;

  final Key? key;

  @override
  String toString() {
    return 'SHGInboxRouteArgs{id: $id, tenantId: $tenantId, projectDetails: $projectDetails, key: $key}';
  }
}

/// generated route for
/// [ManageAttendanceRegisterPage]
class ManageAttendanceRegisterRoute extends PageRouteInfo<void> {
  const ManageAttendanceRegisterRoute()
      : super(
          ManageAttendanceRegisterRoute.name,
          path: 'manage-attendance',
        );

  static const String name = 'ManageAttendanceRegisterRoute';
}

/// generated route for
/// [AttendanceInboxPage]
class AttendanceInboxRoute extends PageRouteInfo<void> {
  const AttendanceInboxRoute()
      : super(
          AttendanceInboxRoute.name,
          path: 'attendance-inbox',
        );

  static const String name = 'AttendanceInboxRoute';
}

/// generated route for
/// [TrackAttendancePage]
class TrackAttendanceRoute extends PageRouteInfo<TrackAttendanceRouteArgs> {
  TrackAttendanceRoute({
    required String id,
    required String tenantId,
    required List<Map<String, dynamic>> projectDetails,
    required AttendanceRegister? attendanceRegister,
    Key? key,
  }) : super(
          TrackAttendanceRoute.name,
          path: 'track-attendance/:id/:tenantId',
          args: TrackAttendanceRouteArgs(
            id: id,
            tenantId: tenantId,
            projectDetails: projectDetails,
            attendanceRegister: attendanceRegister,
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
    required this.projectDetails,
    required this.attendanceRegister,
    this.key,
  });

  final String id;

  final String tenantId;

  final List<Map<String, dynamic>> projectDetails;

  final AttendanceRegister? attendanceRegister;

  final Key? key;

  @override
  String toString() {
    return 'TrackAttendanceRouteArgs{id: $id, tenantId: $tenantId, projectDetails: $projectDetails, attendanceRegister: $attendanceRegister, key: $key}';
  }
}
