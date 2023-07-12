import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/pages/bills/my_bills.dart';
import 'package:works_shg_app/pages/org_profile.dart';
import 'package:works_shg_app/pages/time_extension_request/create_time_extension.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/register_individual.dart';

import '../pages/attendance_register_table.dart';
import '../pages/authenticated.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/otp_verification.dart';
import '../pages/shg_inbox.dart';
import '../pages/trackAttendance/track-attendance_inbox.dart';
import '../pages/track_attendance.dart';
import '../pages/unauthenticated.dart';
import '../pages/view_muster_rolls.dart';
import '../pages/work_order/view_work_details.dart';
import '../pages/work_order/work_order.dart';
import '../widgets/molecules/success_page.dart';

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
        AutoRoute(page: OTPVerificationPage, path: 'otp')
      ],
    ),
    AutoRoute(
      page: AuthenticatedPageWrapper,
      path: '/',
      children: [
        AutoRoute(page: HomePage, path: 'home', initial: true),
        AutoRoute(page: ORGProfilePage, path: 'orgProfile'),
        AutoRoute(
            page: AttendanceRegisterTablePage,
            path: 'manageAttendanceTable/:registerId/:tenantId'),
        AutoRoute(page: WorkOrderPage, path: 'work-orders'),
        AutoRoute(page: ViewMusterRollsPage, path: 'muster-rolls'),
        AutoRoute(
            page: SHGInboxPage,
            path: 'shg-inbox/:tenantId/:musterRollNo/:sentBackCode'),
        AutoRoute(
            page: TrackAttendanceInboxPage, path: 'track-attendance-inbox'),
        AutoRoute(
            page: TrackAttendancePage, path: 'track-attendance/:id/:tenantId'),
        AutoRoute(page: RegisterIndividualPage, path: 'register-individual'),
        AutoRoute(page: ViewWorkDetailsPage, path: 'view-work-order'),
        AutoRoute(page: SuccessResponsePage, path: 'success'),
        AutoRoute(page: MyBillsPage, path: 'my-bills'),
        AutoRoute(
            page: CreateTimeExtensionRequestPage,
            path: 'create-time-extension'),
      ],
    ),
  ],
)
class AppRouter extends _$AppRouter {}
