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
import 'package:open_filex/open_filex.dart';
import 'package:url_strategy/url_strategy.dart';
import 'package:works_shg_app/blocs/app_initilization/home_screen_bloc.dart';
import 'package:works_shg_app/blocs/attendance/attendance_user_search.dart';
import 'package:works_shg_app/blocs/attendance/individual_search.dart';
import 'package:works_shg_app/blocs/attendance/search_projects/search_projects.dart';
import 'package:works_shg_app/blocs/attendance/skills/skills_bloc.dart';
import 'package:works_shg_app/blocs/auth/otp_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/create_muster.dart';
import 'package:works_shg_app/blocs/muster_rolls/muster_roll_estimate.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/blocs/work_orders/decline_work_order.dart';
import 'package:works_shg_app/data/repositories/attendance_mdms.dart';
import 'package:works_shg_app/data/repositories/common_repository/common_repository.dart';
import 'package:works_shg_app/router/app_navigator_observer.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';

import 'Env/app_config.dart';
import 'Env/env_config.dart';
import 'blocs/app_bloc_observer.dart';
import 'blocs/app_initilization/app_initilization.dart';
import 'blocs/attendance/attendance_create_log.dart';
import 'blocs/attendance/attendance_hours_mdms.dart';
import 'blocs/attendance/create_attendance_register.dart';
import 'blocs/attendance/create_attendee.dart';
import 'blocs/attendance/de_enroll_attendee.dart';
import 'blocs/attendance/muster_submission_mdms.dart';
import 'blocs/attendance/search_projects/search_individual_project.dart';
import 'blocs/auth/auth.dart';
import 'blocs/localization/app_localization.dart';
import 'blocs/localization/localization.dart';
import 'blocs/muster_rolls/from_to_date_search_muster_roll.dart';
import 'blocs/muster_rolls/get_business_workflow.dart';
import 'blocs/muster_rolls/get_muster_workflow.dart';
import 'blocs/muster_rolls/muster_inbox_status_bloc.dart';
import 'blocs/muster_rolls/muster_roll_pdf.dart';
import 'blocs/muster_rolls/search_individual_muster_roll.dart';
import 'blocs/my_bills/search_my_bills.dart';
import 'blocs/organisation/org_financial_bloc.dart';
import 'blocs/organisation/org_search_bloc.dart';
import 'blocs/user/user_search.dart';
import 'blocs/wage_seeker_registration/wage_seeker_bank_create.dart';
import 'blocs/wage_seeker_registration/wage_seeker_create_bloc.dart';
import 'blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import 'blocs/wage_seeker_registration/wage_seeker_mdms_bloc.dart';
import 'blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import 'blocs/work_orders/accept_work_order.dart';
import 'blocs/work_orders/my_works_search_criteria.dart';
import 'blocs/work_orders/search_individual_work.dart';
import 'blocs/work_orders/search_my_works.dart';
import 'blocs/work_orders/work_order_pdf.dart';
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

  static void downloadCallback(
      String id, DownloadTaskStatus status, int progress) {
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

    return MultiBlocProvider(
      providers: [
        BlocProvider(
          create: (context) => AppInitializationBloc(
            const AppInitializationState(),
            MdmsRepository(client.init()),
          )..add(const AppInitializationSetupEvent(selectedLangIndex: 0)),
          lazy: false,
        ),
        BlocProvider(create: (context) => AuthBloc()),
        BlocProvider(create: (context) => OTPBloc()),
        BlocProvider(create: (context) => HomeScreenBloc()),
        BlocProvider(create: (context) => AttendanceRegisterCreateBloc()),
        BlocProvider(
          create: (_) => UserSearchBloc()..add(const SearchUserEvent()),
        ),
        BlocProvider(
          create: (_) =>
              MusterRollSearchBloc()..add(const SearchMusterRollEvent()),
        ),
        BlocProvider(
          create: (_) => AttendanceProjectsSearchBloc()
            ..add(const SearchAttendanceProjectsEvent()),
        ),
        BlocProvider(
          create: (_) => AttendanceIndividualProjectSearchBloc()
            ..add(const SearchIndividualAttendanceProjectEvent()),
        ),
        BlocProvider(
            create: (context) => AttendanceUserSearchBloc(
                const AttendanceUserSearchState.initial())),
        BlocProvider(create: (context) => AttendeeCreateBloc()),
        BlocProvider(create: (context) => MusterRollFromToDateSearchBloc()),
        BlocProvider(create: (context) => IndividualMusterRollSearchBloc()),
        BlocProvider(create: (context) => AttendeeDeEnrollBloc()),
        BlocProvider(create: (context) => MusterRollEstimateBloc()),
        BlocProvider(create: (context) => AttendanceLogCreateBloc()),
        BlocProvider(create: (context) => MusterCreateBloc()),
        BlocProvider(create: (context) => MusterGetWorkflowBloc()),
        BlocProvider(create: (context) => BusinessWorkflowBloc()),
        BlocProvider(create: (context) => SearchMyWorksBloc()),
        BlocProvider(create: (context) => SearchMyBillsBloc()),
        BlocProvider(create: (context) => AcceptWorkOrderBloc()),
        BlocProvider(create: (context) => MyWorksSearchCriteriaBloc()),
        BlocProvider(create: (context) => DeclineWorkOrderBloc()),
        BlocProvider(create: (context) => SearchIndividualWorkBloc()),
        BlocProvider(create: (context) => WageSeekerBloc()),
        BlocProvider(create: (context) => WageSeekerLocationBloc()),
        BlocProvider(create: (context) => WageSeekerCreateBloc()),
        BlocProvider(create: (context) => WageSeekerBankCreateBloc()),
        BlocProvider(create: (context) => ORGSearchBloc()),
        BlocProvider(create: (context) => ORGFinanceBloc()),
        BlocProvider(create: (context) => MusterRollPDFBloc()),
        BlocProvider(create: (context) => WorkOrderPDFBloc()),
        BlocProvider(
            create: (context) => WageSeekerMDMSBloc(
                const WageSeekerMDMSState.initial(),
                MdmsRepository(client.init()),
                CommonRepository(client.init()))),
        BlocProvider(
            create: (context) =>
                IndividualSearchBloc(const IndividualSearchState.initial())),
        BlocProvider(
            create: (context) => SkillsBloc(const SkillsBlocState.initial(),
                AttendanceMDMSRepository(client.init()))),
        BlocProvider(
            create: (context) => AttendanceHoursBloc(
                const AttendanceHoursState.initial(),
                MdmsRepository(client.init()))),
        BlocProvider(
            create: (context) => MusterSubmissionBloc(
                const MusterSubmissionState.initial(),
                MdmsRepository(client.init()))),
        BlocProvider(
            create: (context) => MusterInboxStatusBloc(
                const MusterInboxStatusState.initial(),
                MdmsRepository(client.init()))),
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
                              LocalizationRepository(client.init()),
                            )..add(LocalizationEvent.onLoadLocalization(
                                module:
                                    'rainmaker-common,rainmaker-common-masters,rainmaker-${appInitState.stateInfoListModel?.code}',
                                tenantId: appInitState.initMdmsModel!.tenant!
                                    .tenantListModel!.first.code
                                    .toString(),
                                locale: appInitState.digitRowCardItems!
                                    .firstWhere((e) => e.isSelected)
                                    .value,
                              ))
                        : (context) => LocalizationBloc(
                              const LocalizationState.initial(),
                              LocalizationRepository(client.init()),
                            ),
                    child: MaterialApp.router(
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
