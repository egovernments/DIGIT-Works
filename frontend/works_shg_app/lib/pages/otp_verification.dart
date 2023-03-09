import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_countdown_timer/countdown_timer_controller.dart';
import 'package:flutter_countdown_timer/flutter_countdown_timer.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/atoms/digit_otp_builder.dart';

import '../blocs/auth/auth.dart';
import '../blocs/auth/otp_bloc.dart';
import '../utils/notifiers.dart';
import '../widgets/Back.dart';
import '../widgets/LabelText.dart';
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
  late CountdownTimerController timerController;
  int endTime = DateTime.now().millisecondsSinceEpoch + 1000 * 30;
  final TextEditingController otpController = TextEditingController();
  bool isDisabled = true;

  @override
  void initState() {
    super.initState();
    timerController = CountdownTimerController(endTime: endTime, onEnd: onEnd);
  }

  @override
  void dispose() {
    timerController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var localizationText =
        '${AppLocalizations.of(context).translate(i18.login.enterOTPSent)}';
    localizationText =
        localizationText.replaceFirst('{mobileNumber}', widget.mobileNumber);
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(children: [
          Back(
            backLabel: AppLocalizations.of(context).translate(i18.common.back),
          ),
          Card(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                LabelText(AppLocalizations.of(context)
                    .translate(i18.login.otpVerification)),
                const SizedBox(height: 10),
                SubLabelText(localizationText),
                DigitOTPField(
                  numberOfFields: 6,
                  onChanged: (value) {
                    otpController.text = value;
                  },
                ),
                Visibility(
                  maintainSize: true,
                  maintainAnimation: true,
                  maintainState: true,
                  visible: isDisabled,
                  child: CountdownTimer(
                    controller: timerController,
                    onEnd: onEnd,
                    endTime: endTime,
                  ),
                ),
                Visibility(
                  maintainSize: true,
                  maintainAnimation: true,
                  maintainState: true,
                  visible: !isDisabled,
                  child: TextButton(
                      onPressed: () {
                        context.read<OTPBloc>().add(
                              OTPSendEvent(
                                mobileNumber: widget.mobileNumber,
                              ),
                            );
                        endTime =
                            DateTime.now().millisecondsSinceEpoch + 1000 * 30;
                        timerController = CountdownTimerController(
                            endTime: endTime, onEnd: onEnd);
                        setState(() {
                          isDisabled = true;
                        });
                      },
                      child: Text(AppLocalizations.of(context)
                          .translate(i18.login.resendOTP))),
                ),
                const SizedBox(height: 10),
                DigitElevatedButton(
                  onPressed: () async {
                    context.read<AuthBloc>().add(
                          AuthLoginEvent(
                            userId: widget.mobileNumber,
                            password: otpController.text,
                          ),
                        );
                  },
                  child: Center(
                    child: Text(AppLocalizations.of(context)
                        .translate(i18.common.next)),
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
              ],
            ),
          ),
        ]),
      ),
    );
  }

  sendOtp() {
    context.read()<OTPBloc>().add(
          OTPSendEvent(
            mobileNumber: widget.mobileNumber,
          ),
        );
  }

  void onEnd() {
    setState(() {
      isDisabled = false;
    });
  }
}
