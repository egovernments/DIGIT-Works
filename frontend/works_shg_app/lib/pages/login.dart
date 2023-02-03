import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/global_variables.dart';

import '../blocs/auth/auth.dart';
import '../blocs/localization/app_localization.dart';
import '../utils/notifiers.dart';
import '../widgets/molecules/desktop_view.dart';
import '../widgets/molecules/mobile_view.dart';

class LoginPage extends StatefulWidget {
  LoginPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LoginPage();
  }
}

class _LoginPage extends State<LoginPage> {
  var userIdController = TextEditingController();
  var passwordController = TextEditingController();
  var passwordVisible = false;

  Widget getLoginCard(BuildContext loginContext) {
    return DigitCard(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text(
            AppLocalizations.of(loginContext).translate(i18.login.loginLabel),
            style: Theme.of(loginContext).textTheme.displayMedium,
          ),
          DigitTextField(
            label: AppLocalizations.of(loginContext)
                .translate(i18.login.loginUserName),
            controller: userIdController,
            maxLength: 10,
          ),
          DigitTextField(
            label: AppLocalizations.of(loginContext)
                .translate(i18.login.loginPassword),
            controller: passwordController,
            obscureText: !passwordVisible,
            suffixIcon: buildPasswordVisibility(),
            maxLines: 1,
          ),
          const SizedBox(height: 16),
          DigitElevatedButton(
            onPressed: () async {
              loginContext.read<AuthBloc>().add(
                    AuthLoginEvent(
                      userId: userIdController.text,
                      password: passwordController.text,
                    ),
                  );
            },
            child: Center(
              child: Text(AppLocalizations.of(loginContext)
                  .translate(i18.login.loginLabel)),
            ),
          ),
          BlocBuilder<AuthBloc, AuthState>(builder: (context, state) {
            SchedulerBinding.instance.addPostFrameCallback((_) {
              state.maybeWhen(
                  error: () => Notifiers.getToastMessage(
                      context,
                      AppLocalizations.of(context)
                          .translate(i18.common.invalidCredentials),
                      'ERROR'),
                  orElse: () => Container());
            });
            return Container();
          }),
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
    );
  }

  Widget buildPasswordVisibility() {
    return IconButton(
      icon: Icon(
        passwordVisible ? Icons.visibility : Icons.visibility_off,
        color: Theme.of(context).primaryColorLight,
      ),
      onPressed: () {
        setState(() {
          passwordVisible = !passwordVisible;
        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        body: LayoutBuilder(builder: (context, constraints) {
          if (constraints.maxWidth < 760) {
            return MobileView(
                getLoginCard(context), GlobalVariables.bannerURL());
          } else {
            return DesktopView(
                getLoginCard(context), GlobalVariables.bannerURL());
          }
        }));
  }
}
