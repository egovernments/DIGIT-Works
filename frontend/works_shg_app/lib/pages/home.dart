import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/common_methods.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/atoms/app_bar_logo.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/app_initilization/home_screen_bloc.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../models/organisation/organisation_model.dart';
import '../models/screen_config/home_screen_config.dart';
import '../utils/constants.dart';
import '../utils/global_variables.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart' as shg_loader;

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _HomePage();
  }
}

class _HomePage extends State<HomePage> {
  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    context.read<ORGSearchBloc>().add(
          SearchORGEvent(GlobalVariables.userRequestModel!['mobileNumber']),
        );
    context.read<HomeScreenBloc>().add(
          const GetHomeScreenConfigEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: BlocBuilder<ORGSearchBloc, ORGSearchState>(
            builder: (context, state) {
              return state.maybeWhen(
                  orElse: () => Container(),
                  loaded: (OrganisationListModel? organisationListModel) {
                    return const AppBarLogo();
                  });
            },
          ),
        ),
        drawer: const DrawerWrapper(Drawer(child: SideBar())),
        body: BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return BlocListener<ORGSearchBloc, ORGSearchState>(
              listener: (context, orgState) {
            orgState.maybeWhen(
                orElse: () => false,
                error: (String? error) {
                  Notifiers.getToastMessage(context,
                      t.translate(i18.common.noOrgLinkedWithMob), 'ERROR');
                  context.read<AuthBloc>().add(const AuthLogoutEvent());
                },
                loaded: (OrganisationListModel? organisationListModel) async {
                  if ((organisationListModel?.organisations ?? []).isEmpty) {
                    Notifiers.getToastMessage(context,
                        t.translate(i18.common.noOrgLinkedWithMob), 'ERROR');
                    context.read<AuthBloc>().add(const AuthLogoutEvent());
                  } else {
                    var currLoc = await GlobalVariables.selectedLocale();
                    context.read<LocalizationBloc>().add(
                          LocalizationEvent.onLoadLocalization(
                              module: CommonMethods.getLocaleModules(),
                              tenantId: GlobalVariables.globalConfigObject!
                                  .globalConfigs!.stateTenantId
                                  .toString(),
                              locale: currLoc.toString()),
                        );
                    context.read<AppInitializationBloc>().add(
                        AppInitializationSetupEvent(
                            selectedLang: currLoc.toString()));
                    await AppLocalizations(
                      Locale(currLoc.toString().split('_').first,
                          currLoc.toString().split('_').last),
                    ).load();
                  }
                });
          }, child: BlocBuilder<ORGSearchBloc, ORGSearchState>(
                  builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (OrganisationListModel? organisationListModel) {
                  return ScrollableContent(
                    footer: const Padding(
                      padding: EdgeInsets.all(16.0),
                      child: PoweredByDigit(),
                    ),
                    children: [
                      DigitCard(
                        onPressed: null,
                        padding: const EdgeInsets.fromLTRB(16, 16, 16, 16),
                        child: Align(
                            alignment: Alignment.topCenter,
                            child: BlocBuilder<HomeScreenBloc,
                                HomeScreenBlocState>(
                              builder: (context, config) {
                                return config.maybeWhen(
                                    orElse: () => Container(),
                                    loading: () =>
                                        shg_loader.Loaders.circularLoader(
                                            context),
                                    loaded: (List<CBOHomeScreenConfigModel>?
                                        cboHomeScreenConfig) {
                                      return Column(
                                          mainAxisAlignment:
                                              MainAxisAlignment.start,
                                          crossAxisAlignment:
                                              CrossAxisAlignment.center,
                                          mainAxisSize: MainAxisSize.min,
                                          children: cboHomeScreenConfig
                                                  ?.map((e) {
                                                if (e.order == 1) {
                                                  return Column(
                                                    children: [
                                                      Row(
                                                        mainAxisAlignment:
                                                            MainAxisAlignment
                                                                .spaceBetween,
                                                        children: [
                                                          Text(
                                                            t.translate(
                                                                i18.home.mukta),
                                                            style: DigitTheme
                                                                .instance
                                                                .mobileTheme
                                                                .textTheme
                                                                .headlineLarge,
                                                          ),
                                                          SvgPicture.asset(
                                                              Constants
                                                                  .muktaIcon)
                                                        ],
                                                      ),
                                                      ButtonLink(
                                                          t.translate(
                                                              e.label ?? ''),
                                                          getRoute(
                                                              e.key.toString(),
                                                              context))
                                                    ],
                                                  );
                                                } else {
                                                  return ButtonLink(
                                                      t.translate(
                                                          e.label ?? ''),
                                                      getRoute(e.key.toString(),
                                                          context));
                                                }
                                              }).toList() ??
                                              []);
                                    });
                              },
                            )),
                      )
                    ],
                  );
                });
          }));
        }));
  }

  Future<void> localeLoad() async {
    var currentLocale = await GlobalVariables.selectedLocale();
    context.read<LocalizationBloc>().add(
          LocalizationEvent.onLoadLocalization(
              module: CommonMethods.getLocaleModules(),
              tenantId: GlobalVariables
                  .globalConfigObject!.globalConfigs!.stateTenantId
                  .toString(),
              locale: currentLocale.toString()),
        );
    context.read<AppInitializationBloc>().add(
        AppInitializationSetupEvent(selectedLang: currentLocale.toString()));
    await AppLocalizations(
      Locale(currentLocale.toString().split('_').first,
          currentLocale.toString().split('_').last),
    ).load();
  }

  void Function()? getRoute(String key, BuildContext context) {
    switch (key) {
      case Constants.homeMyWorks:
        return () {
          localeLoad();
          context.router.push(const WorkOrderRoute());
        };
      case Constants.homeTrackAttendance:
        return () {
          localeLoad();
          context.router.push(const TrackAttendanceInboxRoute());
        };
      case Constants.homeMusterRolls:
        return () {
          localeLoad();
          context.router.push(const ViewMusterRollsRoute());
        };
      case Constants.homeMyBills:
        return () {
          localeLoad();
          context.router.push(const MyBillsRoute());
        };
      case Constants.homeRegisterWageSeeker:
        return () {
          localeLoad();
          context.router.push(const RegisterIndividualRoute());
        };
      default:
        return null;
    }
  }
}
