import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:flutter_training/Env/env_config.dart';
import 'package:flutter_training/widgets/atoms/app_bar_logo.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/app_initilization/home_screen_bloc.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../models/screen_config/home_screen_config.dart';
import '../router/app_router.dart';
import '../utils/common_methods.dart';
import '../utils/constants.dart';
import '../utils/global_variables.dart';
import '../widgets/ButtonLink.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';

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
          title: const AppBarLogo(),
        ),
        drawer: const DrawerWrapper(Drawer(child: SideBar())),
        body: BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return ScrollableContent(
            footer: const Padding(
              padding: EdgeInsets.all(16.0),
              child: PoweredByDigit(),
            ),
            children: [
              Align(
                  alignment: Alignment.topCenter,
                  child: BlocBuilder<HomeScreenBloc, HomeScreenBlocState>(
                      builder: (context, config) {
                    return config.maybeWhen(
                      orElse: () => Container(),
                      loading: () => Loaders.circularLoader(context),
                      loaded:
                          (List<HomeScreenCardConfigModel>? homeScreenConfig) {
                        return Column(
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisSize: MainAxisSize.min,
                            children: homeScreenConfig?.map((e) {
                                  return DigitCard(
                                    child: Column(
                                      children: [
                                        Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.spaceBetween,
                                          children: [
                                            Text(
                                              t.translate(e.header),
                                              style: DigitTheme
                                                  .instance
                                                  .mobileTheme
                                                  .textTheme
                                                  .headlineLarge,
                                            ),
                                            e.icon != null
                                                ? SvgPicture.asset(
                                                    e.icon ?? '',
                                                    color: const DigitColors()
                                                        .burningOrange,
                                                  )
                                                : const SizedBox.shrink()
                                          ],
                                        ),
                                        Column(
                                          children: e.links
                                                  ?.where((link) =>
                                                      link.active ?? false)
                                                  .map((l) {
                                                return ButtonLink(
                                                  t.translate(l.label ?? ''),
                                                  getRoute(l.key.toString(),
                                                      context),
                                                );
                                              }).toList() ??
                                              [],
                                        ),
                                      ],
                                    ),
                                  );
                                }).toList() ??
                                []);
                      },
                    );
                  }))
            ],
          );
        }));
  }

  Future<void> localeLoad() async {
    var currentLocale = await GlobalVariables.selectedLocale();
    context.read<LocalizationBloc>().add(
          LocalizationEvent.onLoadLocalization(
              module: CommonMethods.getLocaleModules(),
              tenantId: envConfig.variables.tenantId,
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
      case Constants.homeCreateBirthRegistration:
        return () {
          localeLoad();
          context.router.push(const CreateBirthRegistrationRoute());
        };
      default:
        return null;
    }
  }
}
