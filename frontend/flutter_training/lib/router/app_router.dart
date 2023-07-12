import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/pages/birth-registration/create_birth_registration.dart';
import 'package:flutter_training/pages/org_profile.dart';
import 'package:flutter_training/pages/wage_seeker_registration/register_individual.dart';

import '../pages/authenticated.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/otp_verification.dart';
import '../pages/unauthenticated.dart';
import '../pages/view_muster_rolls.dart';
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
        AutoRoute(page: ViewMusterRollsPage, path: 'muster-rolls'),
        AutoRoute(page: RegisterIndividualPage, path: 'register-individual'),
        AutoRoute(page: SuccessResponsePage, path: 'success'),
        AutoRoute(
            page: CreateBirthRegistrationPage,
            path: 'create-birth-certificate'),
      ],
    ),
  ],
)
class AppRouter extends _$AppRouter {}
