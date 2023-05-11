import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:digit_components/widgets/molecules/digit_language_card.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/atoms/app_logo.dart';
import 'package:works_shg_app/widgets/molecules/desktop_view.dart';
import 'package:works_shg_app/widgets/molecules/mobile_view.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/app_initilization/app_version_bloc.dart';
import '../blocs/localization/localization.dart';
import '../models/init_mdms/init_mdms_model.dart';
import '../router/app_router.dart';
import '../utils/common_methods.dart';
import '../widgets/loaders.dart' as shg_app;

class LanguageSelectionPage extends StatefulWidget {
  const LanguageSelectionPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LanguageSelectionPage();
  }
}

class _LanguageSelectionPage extends State<LanguageSelectionPage> {
  List<DigitRowCardModel>? digitRowCardItems;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    digitRowCardItems = await GlobalVariables.getLanguages();
    if (!kIsWeb) {
      context.read<AppVersionBloc>().add(
            const GetAppVersionEvent(),
          );
    }
  }

  Widget getLanguageCard(BuildContext context) {
    final theme = Theme.of(context);
    return BlocBuilder<AppInitializationBloc, AppInitializationState>(
      builder: (context, state) {
        return state.isInitializationCompleted == false &&
                state.stateInfoListModel == null
            ? shg_app.Loaders.circularLoader(context)
            : BlocListener<AppVersionBloc, AppVersionBlocState>(
                listener: (context, localeState) {
                  localeState.maybeWhen(
                      orElse: () => false,
                      loading: () => shg_app.Loaders.circularLoader(context),
                      loaded: (AppVersionModel? cboAppVersion) {
                        CommonMethods().checkVersion(
                            context,
                            cboAppVersion?.packageName,
                            cboAppVersion?.iOSId,
                            cboAppVersion?.version);
                      });
                },
                child: Column(
                  children: [
                    state.digitRowCardItems != null &&
                            state.isInitializationCompleted
                        ? DigitLanguageCard(
                            appLogo: const AppLogo(),
                            digitRowCardItems: state.digitRowCardItems
                                ?.map((e) =>
                                    DigitRowCardModel.fromJson(e.toJson()))
                                .toList() as List<DigitRowCardModel>,
                            onLanguageSubmit: () async {
                              context.router.push(const LoginRoute());
                            },
                            onLanguageChange: (data) async {
                              context.read<AppInitializationBloc>().add(
                                  AppInitializationSetupEvent(
                                      selectedLang: data.value));

                              context.read<LocalizationBloc>().add(
                                  OnLoadLocalizationEvent(
                                      module:
                                          'rainmaker-common,rainmaker-common-masters,rainmaker-${GlobalVariables.stateInfoListModel?.code}',
                                      tenantId: state.stateInfoListModel!.code
                                          .toString(),
                                      locale: data.value));
                              context.read<AppInitializationBloc>().add(
                                  AppInitializationSetupEvent(
                                      selectedLang: data.value));
                            },
                            languageSubmitLabel: AppLocalizations.of(context)
                                .translate(i18.common.continueLabel),
                          )
                        : const SizedBox(
                            width: 0,
                            height: 0,
                          ),
                  ],
                ));
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return MediaQuery.of(context).size.width > 760
        ? DesktopView(getLanguageCard(context),
            GlobalVariables.stateInfoListModel!.bannerUrl.toString())
        : MobileView(getLanguageCard(context),
            GlobalVariables.stateInfoListModel!.bannerUrl.toString());
  }
}
