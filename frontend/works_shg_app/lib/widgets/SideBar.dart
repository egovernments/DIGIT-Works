import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/icons/shg_icons.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/global_variables.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../models/organisation/organisation_model.dart';

class SideBar extends StatefulWidget {
  final String module;
  const SideBar(
      {super.key, this.module = 'rainmaker-common,rainmaker-attendencemgmt'});
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
          BlocBuilder<LocalizationBloc, LocalizationState>(
              builder: (context, localeState) {
            return BlocBuilder<ORGSearchBloc, ORGSearchState>(
                builder: (context, orgState) {
              return orgState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => SizedBox(
                      height: MediaQuery.of(buildContext).size.height / 3,
                      child: Loaders.circularLoader(context)),
                  loaded: (OrganisationListModel? organisationListModel) {
                    return organisationListModel?.organisations != null
                        ? SizedBox(
                            height: MediaQuery.of(buildContext).size.height / 3,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Text(
                                  organisationListModel!
                                      .organisations!.first.name
                                      .toString(),
                                  style: theme
                                      .mobileTheme.textTheme.headlineMedium
                                      ?.apply(color: const DigitColors().black),
                                ),
                                Text(
                                  organisationListModel.organisations!.first
                                      .contactDetails!.first.contactMobileNumber
                                      .toString(),
                                  style: theme.mobileTheme.textTheme.bodyMedium
                                      ?.apply(
                                          color: const DigitColors().davyGray),
                                ),
                              ],
                            ),
                          )
                        : Container();
                  });
            });
          }),
          DigitIconTile(
            title: AppLocalizations.of(context).translate(i18.common.home),
            icon: Icons.home,
            onPressed: () => context.router.replace(const HomeRoute()),
          ),
          DigitIconTile(
            title: AppLocalizations.of(context).translate(i18.common.language),
            icon: SHGIcons.language,
            content: Padding(
              padding: const EdgeInsets.all(16),
              child: BlocBuilder<AppInitializationBloc, AppInitializationState>(
                builder: (context, state) {
                  return state.digitRowCardItems != null &&
                          state.isInitializationCompleted
                      ? DigitRowCard(
                          onChanged: (data) async {
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLangIndex:
                                        data.value != 'en_IN' ? 1 : 0));

                            await AppLocalizations(
                              Locale(data.value.split('_').first,
                                  data.value.split('_').last),
                            ).load();
                            context.read<LocalizationBloc>().add(
                                OnLoadLocalizationEvent(
                                    module: widget.module,
                                    tenantId: GlobalVariables
                                        .globalConfigObject!
                                        .globalConfigs!
                                        .stateTenantId
                                        .toString(),
                                    locale: data.value));
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLangIndex:
                                        data.value != 'en_IN' ? 1 : 0));
                            await AppLocalizations(
                              Locale(data.value.split('_').first,
                                  data.value.split('_').last),
                            ).load();
                          },
                          rowItems: state.digitRowCardItems
                              ?.map(
                                  (e) => DigitRowCardModel.fromJson(e.toJson()))
                              .toList() as List<DigitRowCardModel>,
                          width: 85)
                      : const Text('');
                },
              ),
            ),
            onPressed: () {},
          ),
          DigitIconTile(
              title:
                  AppLocalizations.of(context).translate(i18.common.orgProfile),
              icon: Icons.perm_contact_cal_sharp,
              onPressed: () {
                context.router.push(const ORGProfileRoute());
              }),
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
