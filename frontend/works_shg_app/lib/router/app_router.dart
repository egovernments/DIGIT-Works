import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/pages/attendance_inbox.dart';
import 'package:works_shg_app/pages/manage_attendance_register.dart';
import 'package:works_shg_app/pages/shg_inbox.dart';
import 'package:works_shg_app/pages/track_attendance.dart';

import '../models/attendance/attendance_registry_model.dart';
import '../pages/attendance_register.dart';
import '../pages/authenticated.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/unauthenticated.dart';
import '../pages/view_muster_rolls.dart';
import '../pages/work_order.dart';

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
        AutoRoute(page: AttendanceRegisterTablePage, path: 'manageAttendance'),
        AutoRoute(page: WorkOrderPage, path: 'work-orders'),
        AutoRoute(page: ViewMusterRollsPage, path: 'muster-rolls'),
        AutoRoute(page: SHGInboxPage, path: 'shg-inbox/:id/:tenantId'),
        AutoRoute(
            page: ManageAttendanceRegisterPage, path: 'manage-attendance'),
        AutoRoute(page: AttendanceInboxPage, path: 'attendance-inbox'),
        AutoRoute(
            page: TrackAttendancePage, path: 'track-attendance/:id/:tenantId')
      ],
    ),
  ],
)
class AppRouter extends _$AppRouter {}
