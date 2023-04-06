import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:digit_components/widgets/molecules/digit_language_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/atoms/app_logo.dart';
import 'package:works_shg_app/widgets/molecules/desktop_view.dart';
import 'package:works_shg_app/widgets/molecules/mobile_view.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/localization.dart';
import '../router/app_router.dart';
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
  }

  Widget getLanguageCard(BuildContext context) {
    final theme = Theme.of(context);
    return BlocBuilder<AppInitializationBloc, AppInitializationState>(
      builder: (context, state) {
        return state.isInitializationCompleted == false &&
                state.stateInfoListModel == null
            ? shg_app.Loaders.circularLoader(context)
            : Column(
                children: [
                  state.digitRowCardItems != null &&
                          state.isInitializationCompleted
                      ? DigitLanguageCard(
                          appLogo: AppLogo(),
                          digitRowCardItems: state.digitRowCardItems
                              ?.map(
                                  (e) => DigitRowCardModel.fromJson(e.toJson()))
                              .toList() as List<DigitRowCardModel>,
                          onLanguageSubmit: () async {
                            context.router.push(const LoginRoute());
                          },
                          onLanguageChange: (data) async {
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLangIndex:
                                        data!.value == 'en_IN' ? 0 : 1));

                            await AppLocalizations(
                              Locale(data.value.split('_').first,
                                  data.value.split('_').last),
                            ).load();
                            context.read<LocalizationBloc>().add(
                                OnLoadLocalizationEvent(
                                    module: 'rainmaker-common',
                                    tenantId: state.stateInfoListModel!.code
                                        .toString(),
                                    locale: data.value));
                            context.read<AppInitializationBloc>().add(
                                AppInitializationSetupEvent(
                                    selectedLangIndex:
                                        data!.value == 'en_IN' ? 0 : 1));
                          },
                          languageSubmitLabel: AppLocalizations.of(context)
                              .translate(i18.common.continueLabel),
                        )
                      : const SizedBox(
                          width: 0,
                          height: 0,
                        ),
                ],
              );
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
