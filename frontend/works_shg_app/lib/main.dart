import 'dart:async';
import 'dart:io';

import 'package:digit_components/theme/digit_theme.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:url_strategy/url_strategy.dart';
import 'package:works_shg_app/router/app_navigator_observer.dart';
import 'package:works_shg_app/router/app_router.dart';

import 'Env/app_config.dart';
import 'blocs/app_bloc_observer.dart';
import 'blocs/auth/auth.dart';

void main() {
  HttpOverrides.global = MyHttpOverrides();
  setPathUrlStrategy();
  setEnvironment(Environment.dev);
  Bloc.observer = AppBlocObserver();
  runZonedGuarded(() async {
    FlutterError.onError = (FlutterErrorDetails details) {
      FlutterError.dumpErrorToConsole(details);
      if (kDebugMode) {
        print(details.exception.toString());
      }
      // exit(1); /// to close the app smoothly
    };

    WidgetsFlutterBinding.ensureInitialized();

    runApp(MainApplication(appRouter: AppRouter()));
  }, (Object error, StackTrace stack) {
    if (kDebugMode) {
      print(error.toString());
    } // exit(1); /// to close the app smoothly
  });
  // runApp(const MyApp());
}

class MainApplication extends StatelessWidget {
  final AppRouter appRouter;

  const MainApplication({
    Key? key,
    required this.appRouter,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(create: (context) => AuthBloc(const AuthState())),
      ],
      child: BlocBuilder<AuthBloc, AuthState>(builder: (context, state) {
        return MaterialApp.router(
          theme: DigitTheme.instance.mobileTheme,
          routeInformationParser: appRouter.defaultRouteParser(),
          routerDelegate: AutoRouterDelegate.declarative(
            appRouter,
            navigatorObservers: () => [AppRouterObserver()],
            routes: (handler) => [
              const LandingRoute(),
            ],
          ),
        );
      }),
    );
  }
}

class MyHttpOverrides extends HttpOverrides {
  @override
  HttpClient createHttpClient(SecurityContext? context) {
    return super.createHttpClient(context)
      ..badCertificateCallback =
          (X509Certificate cert, String host, int port) => true;
  }
}
