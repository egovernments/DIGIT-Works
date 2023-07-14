import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/pages/birth-registration/create_birth_registration.dart';

import '../pages/authenticated.dart';
import '../pages/birth-registration/birth_search_inbox.dart';
import '../pages/birth-registration/view_birth_certificates.dart';
import '../pages/home.dart';
import '../pages/language_selection_page.dart';
import '../pages/login.dart';
import '../pages/unauthenticated.dart';
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
      ],
    ),
    AutoRoute(
      page: AuthenticatedPageWrapper,
      path: '/',
      children: [
        AutoRoute(page: HomePage, path: 'home', initial: true),
        AutoRoute(page: SuccessResponsePage, path: 'success'),
        AutoRoute(
            page: CreateBirthRegistrationPage,
            path: 'create-birth-certificate'),
        AutoRoute(page: BirthRegSearchInboxPage, path: 'search-birth-cert'),
        AutoRoute(page: ViewBirthCertificatesPage, path: 'view-birth-certs'),
      ],
    ),
  ],
)
class AppRouter extends _$AppRouter {}
