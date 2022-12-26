import 'package:digit_components/digit_components.dart';
import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../blocs/app_config/app_config.dart';
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
            BlocBuilder<ApplicationConfigBloc, ApplicationConfigState>(
              builder: (context, state) {
                return state.appConfigDetail?.configuration?.appConfig
                            .languages !=
                        null
                    ? DigitLanguageCard(
                        digitRowCardItems: state
                            .appConfigDetail?.configuration?.appConfig.languages
                            .map((e) => DigitRowCardModel.fromJson(e.toJson()))
                            .toList() as List<DigitRowCardModel>,
                        onLanguageSubmit: () =>
                            context.router.push(const LoginRoute()),
                        onLanguageChange: (data) {},
                        languageSubmitLabel: 'Continue',
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
