import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/pages/shg_inbox.dart';

import '../pages/attendance_inbox.dart';
import '../pages/authenticated.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/unauthenticated.dart';
import '../pages/view_muster_rolls.dart';

export 'package:auto_route/auto_route.dart';

part 'app_router.gr.dart';

@MaterialAutoRouter(
  replaceInRouteName: 'Page,Route',
  routes: [
    AutoRoute(
      page: UnauthenticatedPageWrapper,
      path: '/',
      children: [
        AutoRoute(
          page: LanguageSelectionPage,
          path: 'language_selection',
          initial: true,
        ),
        AutoRoute(page: LoginPage, path: 'login'),
      ],
    ),
    AutoRoute(
      page: AuthenticatedPageWrapper,
      path: '/',
      children: [
        AutoRoute(page: HomePage, path: ''),
        AutoRoute(page: AttendanceInboxPage, path: 'manageAttendance'),
        AutoRoute(page: ViewMusterRollsPage, path: 'muster-rolls'),
        AutoRoute(page: SHGInboxPage, path: 'shg-inbox'),
      ],
    ),
  ],
)
class AppRouter extends _$AppRouter {}
