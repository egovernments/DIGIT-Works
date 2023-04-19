import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/atoms/digit_otp_builder.dart';

import '../blocs/auth/auth.dart';
import '../data/remote_client.dart';
import '../data/repositories/auth_repository/auth.dart';
import '../services/urls.dart';
import '../utils/notifiers.dart';
import '../widgets/Back.dart';
import '../widgets/LabelText.dart';
import '../widgets/atoms/resend_otp.dart';
import '../widgets/atoms/sub_label.dart';

class OTPVerificationPage extends StatefulWidget {
  final String mobileNumber;
  const OTPVerificationPage({Key? key, required this.mobileNumber})
      : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _OTPVerificationPage();
  }
}

class _OTPVerificationPage extends State<OTPVerificationPage> {
  final TextEditingController otpController = TextEditingController();
  bool next = false;
  Client client = Client();

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var localizationText =
        '${AppLocalizations.of(context).translate(i18.login.enterOTPSent)}';
    localizationText = localizationText.replaceFirst(
        '{mobileNumber}', '+91 - ${widget.mobileNumber}');
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(children: [
          Back(
            backLabel: AppLocalizations.of(context).translate(i18.common.back),
          ),
          DigitCard(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                LabelText(AppLocalizations.of(context)
                    .translate(i18.login.otpVerification)),
                const SizedBox(height: 10),
                SubLabelText(localizationText),
                Align(
                  alignment: Alignment.centerLeft,
                  child: DigitOTPField(
                    numberOfFields: 6,
                    onChanged: (value) {
                      otpController.text = value;
                      setState(() {
                        next = value.length == 6;
                      });
                    },
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: ResendOTP(
                      onPressed: () {
                        AuthRepository(client.init())
                            .sendOTP(url: Urls.userServices.sendOtp, body: {
                          "otp": {
                            "mobileNumber": widget.mobileNumber,
                            "tenantId":
                                GlobalVariables.stateInfoListModel?.code,
                            "type": "login",
                            "userType": 'citizen'
                          }
                        });
                      },
                    ),
                  ),
                ),
                const SizedBox(height: 10),
                Padding(
                  padding: const EdgeInsets.all(4.0),
                  child: DigitElevatedButton(
                    onPressed: next
                        ? () {
                            context.read<AuthBloc>().add(
                                  AuthLoginEvent(
                                    userId: widget.mobileNumber,
                                    password: otpController.text,
                                  ),
                                );
                          }
                        : null,
                    child: Center(
                      child: Text(AppLocalizations.of(context)
                          .translate(i18.common.next)),
                    ),
                  ),
                ),
                BlocListener<AuthBloc, AuthState>(
                  listener: (context, state) {
                    state.maybeWhen(
                        error: () {
                          Notifiers.getToastMessage(
                              context,
                              AppLocalizations.of(context)
                                  .translate(i18.login.invalidOTP),
                              'ERROR');
                          context.router.popAndPush(OTPVerificationRoute(
                              mobileNumber: widget.mobileNumber));
                        },
                        orElse: () => Container());
                  },
                  child: Container(),
                ),
              ],
            ),
          ),
          const Align(
            alignment: Alignment.bottomCenter,
            child: PoweredByDigit(),
          )
        ]),
      ),
    );
  }
}
