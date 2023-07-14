import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/Env/env_config.dart';
import 'package:flutter_training/blocs/auth/auth.dart';
import 'package:flutter_training/icons/shg_icons.dart';
import 'package:flutter_training/router/app_router.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';

class SideBar extends StatefulWidget {
  final String module;
  const SideBar(
      {super.key,
      this.module = 'rainmaker-common,rainmaker-bnd,rainmaker-common-masters'});
  @override
  State<StatefulWidget> createState() {
    return _SideBar();
  }
}

class _SideBar extends State<SideBar> {
  List<DigitRowCardModel>? digitRowCardItems;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    digitRowCardItems = await GlobalVariables.getLanguages();
  }

  @override
  dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext buildContext) {
    final theme = DigitTheme.instance;

    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localeState) {
      return ScrollableContent(
        footer: const PoweredByDigit(),
        children: [
          Row(
            children: [
              context.router.currentPath == '/home'
                  ? Container(
                      alignment: Alignment.centerLeft,
                      height: 60,
                      width: 9,
                      color: const DigitColors().burningOrange,
                    )
                  : const SizedBox.shrink(),
              Expanded(
                child: DigitIconTile(
                  title:
                      AppLocalizations.of(context).translate(i18.common.home),
                  selected: context.router.currentPath == '/home',
                  icon: Icons.home,
                  onPressed: () => context.router.replace(const HomeRoute()),
                ),
              ),
            ],
          ),
          DigitIconTile(
            title: AppLocalizations.of(context).translate(i18.common.language),
            icon: SHGIcons.language,
            content: Padding(
              padding: const EdgeInsets.only(top: 16),
              child: BlocBuilder<AppInitializationBloc, AppInitializationState>(
                builder: (context, state) {
                  return state.digitRowCardItems != null &&
                          state.isInitializationCompleted
                      ? DigitRowCard(
                          onChanged: (data) async {
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLang: data.value));
                            context.read<LocalizationBloc>().add(
                                OnLoadLocalizationEvent(
                                    module: widget.module,
                                    tenantId: envConfig.variables.tenantId,
                                    locale: data.value));
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLang: data.value));
                            await AppLocalizations(
                              Locale(data.value.split('_').first,
                                  data.value.split('_').last),
                            ).load();
                          },
                          rowItems: state.digitRowCardItems
                              ?.map(
                                  (e) => DigitRowCardModel.fromJson(e.toJson()))
                              .toList() as List<DigitRowCardModel>,
                          width: MediaQuery.of(context).size.width < 720
                              ? (MediaQuery.of(context).size.width *
                                      0.56 /
                                      (state.digitRowCardItems ?? []).length) -
                                  (3 * (state.digitRowCardItems ?? []).length)
                              : MediaQuery.of(context).size.width / 16)
                      : const Text('');
                },
              ),
            ),
            onPressed: () {},
          ),
          DigitIconTile(
              title: AppLocalizations.of(context).translate(i18.common.logOut),
              icon: Icons.logout,
              onPressed: () {
                context.read<AuthBloc>().add(const AuthLogoutEvent());
              }),
        ],
      );
    });
  }
}
