import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/localization.dart';
import '../router/app_router.dart';

class LanguageSelectionPage extends StatelessWidget {
  const LanguageSelectionPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      body: Container(
        color: theme.colorScheme.primary,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: [
            BlocBuilder<AppInitializationBloc, AppInitializationState>(
              builder: (context, state) {
                return state.digitRowCardItems != null &&
                        state.isInitializationCompleted
                    ? DigitLanguageCard(
                        digitRowCardItems: state.digitRowCardItems
                            ?.map((e) => DigitRowCardModel.fromJson(e.toJson()))
                            .toList() as List<DigitRowCardModel>,
                        onLanguageSubmit: () async {
                          context.router.push(LoginRoute());
                        },
                        onLanguageChange: (data) async {
                          context.read<AppInitializationBloc>().add(
                              AppInitializationSetupEvent(
                                  selectedLangIndex:
                                      data!.value == 'en_IN' ? 0 : 1));

                          await AppLocalizations(
                            Locale(data.value),
                          ).load();
                          context.read<LocalizationBloc>().add(
                              OnLoadLocalizationEvent(
                                  module: 'rainmaker-common',
                                  tenantId: 'pb',
                                  locale: data.value));
                          print('data');
                          print(data);
                        },
                        languageSubmitLabel: AppLocalizations.of(context)
                            .translate('CORE_COMMON_CONTINUE'),
                      )
                    : const SizedBox(
                        width: 0,
                        height: 0,
                      );
              },
            ),
            // const PoweredByDigit(),
          ],
        ),
      ),
    );
  }
}
