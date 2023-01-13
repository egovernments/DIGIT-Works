import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../blocs/auth/auth.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../utils/global_variables.dart';

class LoginPage extends StatelessWidget {
  LoginPage({Key? key}) : super(key: key);
  var userIdController = TextEditingController();
  var passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      appBar: AppBar(),
      body: ScrollableContent(
        children: [
          DigitCard(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  AppLocalizations.of(context).translate(i18.login.loginLabel),
                  style: theme.textTheme.displayMedium,
                ),
                DigitTextField(
                  label: AppLocalizations.of(context)
                      .translate(i18.login.loginUserName),
                  controller: userIdController,
                ),
                DigitTextField(
                  label: AppLocalizations.of(context)
                      .translate(i18.login.loginPassword),
                  controller: passwordController,
                ),
                const SizedBox(height: 16),
                BlocBuilder<AuthBloc, AuthState>(
                  builder: (context, state) => DigitElevatedButton(
                    onPressed: state.loading
                        ? null
                        : () {
                            context.read<LocalizationBloc>().add(
                                OnLoadLocalizationEvent(
                                    module: 'rainmaker-works,rainmaker-common',
                                    tenantId: GlobalVariables.getTenantId()
                                        .toString(),
                                    locale: GlobalVariables.selectedLocale()));
                            context.read<AuthBloc>().add(
                                  AuthLoginEvent(
                                    userId: userIdController.text,
                                    password: passwordController.text,
                                  ),
                                );
                          },
                    child: Center(
                      child: Text(AppLocalizations.of(context)
                          .translate(i18.login.loginLabel)),
                    ),
                  ),
                ),
                TextButton(
                  onPressed: () => DigitDialog.show(
                    context,
                    title: AppLocalizations.of(context)
                        .translate(i18.login.forgotPassword),
                    content:
                        'Please contact the administrator if you have forgotten your password',
                    primaryActionLabel:
                        AppLocalizations.of(context).translate(i18.common.oK),
                    primaryAction: () => Navigator.pop(context),
                  ),
                  child: Center(
                      child: Text(AppLocalizations.of(context)
                          .translate(i18.login.forgotPassword))),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
