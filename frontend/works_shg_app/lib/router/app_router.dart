import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';

import '../pages/Authentication.dart';
import '../pages/Login.dart';

export 'package:auto_route/auto_route.dart';

part 'app_router.gr.dart';

@MaterialAutoRouter(
  replaceInRouteName: 'Page,Route',
  routes: [
    AutoRoute(page: LandingPage, path: ''),
    AutoRoute(page: LoginPage, path: '/login'),
  ],
)
class AppRouter extends _$AppRouter {}
