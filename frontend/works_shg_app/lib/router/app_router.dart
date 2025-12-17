import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/models/muster_rolls/muster_workflow_model.dart';
import 'package:works_shg_app/pages/bills/my_bills.dart';
import 'package:works_shg_app/pages/employee/mb_filter_page.dart';
import 'package:works_shg_app/pages/employee/mb_muster_screen.dart';
import 'package:works_shg_app/pages/employee/workOrder/wo_filter_page.dart';
import 'package:works_shg_app/pages/org_profile.dart';
import 'package:works_shg_app/pages/time_extension_request/create_time_extension.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/register_individual.dart';

import '../models/muster_rolls/business_service_workflow.dart';
import '../pages/attendance_register_table.dart';
import '../pages/authenticated.dart';
import '../pages/employee/mb_config_warning.dart';
import '../pages/employee/mb_detail_page.dart';
import '../pages/employee/mb_history.dart';
import '../pages/employee/mb_inbox.dart';
import '../pages/employee/mb_wrapper.dart';
import '../pages/employee/workOrder/work_order_inbox.dart';
import '../pages/employee/workOrder/work_order_wrapper.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/otp_verification.dart';
import '../pages/service_requests/service_requests.dart';
import '../pages/shg_inbox.dart';
import '../pages/trackAttendance/track_attendance_inbox.dart';
import '../pages/track_attendance.dart';
import '../pages/unauthenticated.dart';
import '../pages/view_muster_rolls.dart';
import '../pages/work_order/view_work_details.dart';
import '../pages/work_order/work_order.dart';
import '../utils/employee/mb/mb_logic.dart';
import '../widgets/molecules/success_page.dart';

export 'package:auto_route/auto_route.dart';

part 'app_router.gr.dart';


@AutoRouterConfig(replaceInRouteName: 'Page,Route')
class AppRouter extends _$AppRouter {

@override
List<AutoRoute> get routes => [
   AutoRoute(
      page: UnauthenticatedWrapperRoute.page,
      path: '/',
      children: [
        AutoRoute(
          page: LanguageSelectionRoute.page,
          path: 'language_selection',
          initial: true,
        ),
        AutoRoute(page: LoginRoute.page, path: 'login'),
        AutoRoute(page: OTPVerificationRoute.page, path: 'otp')
      ],
    ),
    AutoRoute(
      page: AuthenticatedWrapperRoute.page,
      path: '/',
      children: [
        AutoRoute(page: HomeRoute.page, path: 'home', initial: true),
        AutoRoute(page: ORGProfileRoute.page, path: 'orgProfile'),
        AutoRoute(
            page: AttendanceRegisterTableRoute.page,
            path: 'manageAttendanceTable/:registerId/:tenantId'),
        AutoRoute(page: WorkOrderRoute.page, path: 'work-orders'),
        AutoRoute(page: ViewMusterRollsRoute.page, path: 'muster-rolls'),
        AutoRoute(
            page: SHGInboxRoute.page,
            path: 'shg-inbox/:tenantId/:musterRollNo/:sentBackCode'),
        AutoRoute(
            page: TrackAttendanceInboxRoute.page, path: 'track-attendance-inbox'),
        AutoRoute(
            page: TrackAttendanceRoute.page, path: 'track-attendance/:id/:tenantId'),
        AutoRoute(page: RegisterIndividualRoute.page, path: 'register-individual'),
        AutoRoute(page: ViewWorkDetailsRoute.page, path: 'view-work-order'),
        AutoRoute(page: SuccessResponseRoute.page, path: 'success'),
        AutoRoute(page: MyBillsRoute.page, path: 'my-bills'),
        AutoRoute(
            page: CreateTimeExtensionRequestRoute.page,
            path: 'create-time-extension'),
        AutoRoute(page: MyServiceRequestsRoute.page, path: 'my-service-requests'),

        // mb for employee

        // MeasurementBookInboxPage
        AutoRoute(
          page: MeasurementBookInboxRoute.page,
          path: 'measurement-inbox',
        ),
        AutoRoute(page: MBFilterRoute.page, path: 'mb-filter'),
        AutoRoute(page: MBDetailRoute.page, path: 'mb-detail'),
        AutoRoute(page: MBHistoryBookRoute.page, path: 'mb-history'),
         AutoRoute(page: MBMusterScreenRoute.page, path: 'mb-muster-screen'),
        AutoRoute(page: MBTypeConfirmationRoute.page, path: 'mb-type-confirmation'),
       

        // work order -employee

        AutoRoute(
          page: WorkOderInboxRoute.page,
          path: 'workOrder-inbox',
        ),
     
        AutoRoute(page: WOFilterRoute.page, path: 'wo-filter'),
      ],
    ),
  ];
}


