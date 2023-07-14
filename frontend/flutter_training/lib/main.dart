import 'dart:async';
import 'dart:io';
import 'dart:isolate';
import 'dart:ui';

import 'package:digit_components/theme/digit_theme.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_downloader/flutter_downloader.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_training/blocs/app_initilization/home_screen_bloc.dart';
import 'package:flutter_training/blocs/bnd/create_birth_bloc.dart';
import 'package:flutter_training/blocs/bnd/search_birth_certificate_bloc.dart';
import 'package:flutter_training/data/init_client.dart';
import 'package:flutter_training/router/app_navigator_observer.dart';
import 'package:flutter_training/router/app_router.dart';
import 'package:flutter_training/utils/common_methods.dart';
import 'package:flutter_training/utils/constants.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:open_filex/open_filex.dart';
import 'package:url_strategy/url_strategy.dart';

import 'Env/app_config.dart';
import 'Env/env_config.dart';
import 'blocs/app_bloc_observer.dart';
import 'blocs/app_initilization/app_initilization.dart';
import 'blocs/app_initilization/app_version_bloc.dart';
import 'blocs/auth/auth.dart';
import 'blocs/auth/otp_bloc.dart';
import 'blocs/localization/app_localization.dart';
import 'blocs/localization/localization.dart';
import 'blocs/user/user_search.dart';
import 'data/remote_client.dart';
import 'data/repositories/remote/localization.dart';
import 'data/repositories/remote/mdms.dart';
import 'models/user_details/user_details_model.dart';

void main() async {
  HttpOverrides.global = MyHttpOverrides();
  setPathUrlStrategy();
  if (kIsWeb && !kDebugMode) {
    setEnvironment(Environment.dev);
  } else {
    await envConfig.initialize();
  }
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
    if (!kIsWeb) {
      await FlutterDownloader.initialize(
          debug: true // optional: set false to disable printing logs to console
          );
    }

    await CommonMethods.fetchPackageInfo();
    runApp(MainApplication(appRouter: AppRouter()));
  }, (Object error, StackTrace stack) {
    if (kDebugMode) {
      print(error.toString());
    } // exit(1); /// to close the app smoothly
  });
  // runApp(const MyApp());
}

class MainApplication extends StatefulWidget {
  const MainApplication({super.key, required AppRouter appRouter});

  @override
  State<StatefulWidget> createState() {
    return _MainApplicationState();
  }
}

class _MainApplicationState extends State<MainApplication> {
  final AppRouter appRouter = AppRouter();
  ReceivePort port = ReceivePort();

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  @override
  void dispose() {
    IsolateNameServer.removePortNameMapping('downloader_send_port');
    super.dispose();
  }

  static void downloadCallback(String id, int status, int progress) {
    final SendPort send =
        IsolateNameServer.lookupPortByName('downloader_send_port')!;

    send.send([id, status, progress]);
  }

  afterViewBuild() async {
    if (kIsWeb) return;
    IsolateNameServer.registerPortWithName(
        port.sendPort, 'downloader_send_port');
    port.listen((dynamic data) {
      String id = data[0];
      DownloadTaskStatus status = data[1];
      int progress = data[2];
      if (status == DownloadTaskStatus.complete) {
        if (GlobalVariables.downloadUrl.containsKey(id)) {
          if (Platform.isIOS && GlobalVariables.downloadUrl[id] != null) {
            OpenFilex.open(GlobalVariables.downloadUrl[id] ?? '');
            GlobalVariables.downloadUrl.remove(id);
          }
        } else if (status == DownloadTaskStatus.failed ||
            status == DownloadTaskStatus.canceled ||
            status == DownloadTaskStatus.undefined) {
          if (GlobalVariables.downloadUrl.containsKey(id)) {
            GlobalVariables.downloadUrl.remove(id);
          }
        }
      }
      setState(() {});
    });
    await FlutterDownloader.registerCallback(downloadCallback);
  }

  @override
  Widget build(BuildContext context) {
    Client client = Client();
    InitClient initClient = InitClient();

    return MultiBlocProvider(
      providers: [
        BlocProvider(
          create: (context) => AppInitializationBloc(
            const AppInitializationState(),
            MdmsRepository(initClient.init()),
          )..add(const AppInitializationSetupEvent(selectedLang: 'en_IN')),
          lazy: false,
        ),
        BlocProvider(create: (context) => AuthBloc()),
        BlocProvider(create: (context) => OTPBloc()),
        BlocProvider(create: (context) => HomeScreenBloc()),
        BlocProvider(create: (context) => AppVersionBloc()),
        BlocProvider(
          create: (_) => UserSearchBloc()..add(const SearchUserEvent()),
        ),
        BlocProvider(create: (context) => BirthRegBloc()),
        BlocProvider(create: (context) => BirthSearchCertBloc()),
      ],
      child: BlocBuilder<AppInitializationBloc, AppInitializationState>(
          builder: (context, appInitState) {
        return appInitState.isInitializationCompleted &&
                appInitState.initMdmsModel != null
            ? BlocBuilder<AuthBloc, AuthState>(builder: (context, authState) {
                return BlocProvider(
                    create: (appInitState.initMdmsModel != null &&
                            appInitState
                                    .stateInfoListModel?.localizationModules !=
                                null)
                        ? (context) => LocalizationBloc(
                              const LocalizationState.initial(),
                              LocalizationRepository(initClient.init()),
                            )..add(LocalizationEvent.onLoadLocalization(
                                module:
                                    'rainmaker-common,rainmaker-common-masters,rainmaker-bnd,rainmaker-${appInitState.stateInfoListModel?.code}',
                                tenantId: appInitState.initMdmsModel!.tenant!
                                    .tenantListModel!.first.code
                                    .toString(),
                                locale: appInitState.digitRowCardItems!
                                    .firstWhere((e) => e.isSelected)
                                    .value,
                              ))
                        : (context) => LocalizationBloc(
                              const LocalizationState.initial(),
                              LocalizationRepository(initClient.init()),
                            ),
                    child: MaterialApp.router(
                      title: 'BND App',
                      supportedLocales: appInitState.initMdmsModel != null
                          ? appInitState.digitRowCardItems!.map((e) {
                              final results = e.value.split('_');

                              return results.isNotEmpty
                                  ? Locale(results.first, results.last)
                                  : const Locale('en', 'IN');
                            })
                          : [],
                      locale: const Locale('en', 'IN'),
                      localizationsDelegates: const [
                        AppLocalizations.delegate,
                        GlobalWidgetsLocalizations.delegate,
                        GlobalCupertinoLocalizations.delegate,
                        GlobalMaterialLocalizations.delegate,
                      ],
                      localeResolutionCallback: (locale, supportedLocales) {
                        for (var supportedLocaleLanguage in supportedLocales) {
                          if (supportedLocaleLanguage.languageCode ==
                                  locale?.languageCode &&
                              supportedLocaleLanguage.countryCode ==
                                  locale?.countryCode) {
                            return supportedLocaleLanguage;
                          }
                        }
                        return supportedLocales.first;
                      },
                      theme: DigitTheme.instance.mobileTheme,
                      scaffoldMessengerKey: scaffoldMessengerKey,
                      routeInformationParser: appRouter.defaultRouteParser(),
                      routerDelegate: AutoRouterDelegate.declarative(
                        appRouter,
                        navigatorObservers: () => [AppRouterObserver()],
                        routes: (handler) => [
                          authState.maybeWhen(
                              initial: () =>
                                  const UnauthenticatedRouteWrapper(),
                              loaded: (UserDetailsModel? userDetailsModel,
                                      String? accessToken) =>
                                  const AuthenticatedRouteWrapper(),
                              orElse: () => const UnauthenticatedRouteWrapper())
                        ],
                      ),
                    ));
              })
            : Container();
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
