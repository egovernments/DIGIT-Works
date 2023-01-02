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
    AttendanceInboxRoute.name: (routeData) {
      return MaterialPageX<dynamic>(
        routeData: routeData,
        child: const AttendanceInboxPage(),
      );
    },
    WorkOrderPageRoute.name:(routeData) {
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
          args.musterDetails,
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
              AttendanceInboxRoute.name,
              path: 'manageAttendance',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              ViewMusterRollsRoute.name,
              path: 'muster-rolls',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              WorkOrderPageRoute.name,
              path: 'work-orders',
              parent: AuthenticatedRouteWrapper.name,
            ),
            RouteConfig(
              SHGInboxRoute.name,
              path: 'shg-inbox',
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
/// [AttendanceInboxPage]
class AttendanceInboxRoute extends PageRouteInfo<void> {
  const AttendanceInboxRoute()
      : super(
          AttendanceInboxRoute.name,
          path: 'manageAttendance',
        );

  static const String name = 'AttendanceInboxRoute';
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
/// [WorkOrderPage]
class WorkOrderPageRoute extends PageRouteInfo<void> {
  const WorkOrderPageRoute()
      : super(
    WorkOrderPageRoute.name,
    path: 'work-orders',
  );

  static const String name = 'WorkOrderPageRoute';
}

/// generated route for
/// [SHGInboxPage]
class SHGInboxRoute extends PageRouteInfo<SHGInboxRouteArgs> {
  SHGInboxRoute({
    required List<Map<String, dynamic>> musterDetails,
    Key? key,
  }) : super(
          SHGInboxRoute.name,
          path: 'shg-inbox',
          args: SHGInboxRouteArgs(
            musterDetails: musterDetails,
            key: key,
          ),
        );

  static const String name = 'SHGInboxRoute';
}

class SHGInboxRouteArgs {
  const SHGInboxRouteArgs({
    required this.musterDetails,
    this.key,
  });

  final List<Map<String, dynamic>> musterDetails;

  final Key? key;

  @override
  String toString() {
    return 'SHGInboxRouteArgs{musterDetails: $musterDetails, key: $key}';
  }
}
