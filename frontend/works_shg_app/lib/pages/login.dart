import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/otp_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/global_variables.dart';

import '../blocs/localization/app_localization.dart';
import '../utils/notifiers.dart';
import '../widgets/molecules/desktop_view.dart';
import '../widgets/molecules/mobile_view.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LoginPage();
  }
}

class _LoginPage extends State<LoginPage> {
  var userIdController = TextEditingController();
  bool canContinue = false;

  @override
  void deactivate() {
    context.read<OTPBloc>().add(
          const DisposeOTPEvent(),
        );
    super.deactivate();
  }

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
                .translate(i18.common.mobileNumber),
            controller: userIdController,
            onChange: (value) {
              setState(() {
                canContinue = value.length == 10;
              });
            },
            maxLength: 10,
          ),
          const SizedBox(height: 16),
          BlocListener<OTPBloc, OTPBlocState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => Container(),
                  loaded: () {
                    context.router.push(OTPVerificationRoute(
                        mobileNumber: userIdController.text));
                  },
                  error: () => Notifiers.getToastMessage(
                      context,
                      AppLocalizations.of(context)
                          .translate(i18.login.enteredMobileNotRegistered),
                      'ERROR'));
            },
            child: DigitElevatedButton(
              onPressed: canContinue
                  ? () async {
                      loginContext.read<OTPBloc>().add(
                            OTPSendEvent(
                              mobileNumber: userIdController.text,
                            ),
                          );
                    }
                  : null,
              child: Center(
                child: Text(AppLocalizations.of(loginContext)
                    .translate(i18.common.continueLabel)),
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        body: LayoutBuilder(builder: (context, constraints) {
          if (constraints.maxWidth < 760) {
            return MobileView(getLoginCard(context),
                GlobalVariables.stateInfoListModel!.bannerUrl.toString());
          } else {
            return DesktopView(getLoginCard(context),
                GlobalVariables.stateInfoListModel!.bannerUrl.toString());
          }
        }));
  }
}
