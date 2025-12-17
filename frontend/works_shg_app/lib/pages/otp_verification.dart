// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_otp_input.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:pin_input_text_field/pin_input_text_field.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';

import '../blocs/auth/auth.dart';
import '../data/remote_client.dart';
import '../data/repositories/auth_repository/auth.dart';
import '../services/urls.dart';
import '../widgets/atoms/app_bar_logo.dart';
import '../widgets/atoms/resend_otp.dart';
import '../widgets/atoms/sub_label.dart';

@RoutePage()
class OTPVerificationPage extends StatefulWidget {
  final String mobileNumber;
  const OTPVerificationPage({super.key, required this.mobileNumber});

  @override
  State<StatefulWidget> createState() {
    return _OTPVerificationPage();
  }
}

class _OTPVerificationPage extends State<OTPVerificationPage> {
   final TextEditingController otpController = TextEditingController();

  // final OtpFieldControllerV2 otpController = OtpFieldControllerV2();
  bool next = false;
  Client client = Client();
  final FocusNode pinFocusNode = FocusNode();

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
      backgroundColor: Theme.of(context).colorTheme.generic.background,
      appBar: AppBar(
        backgroundColor: const Color(0xff0B4B66),
        iconTheme: Theme.of(context).iconTheme,
        titleSpacing: 16,
        title: const AppBarLogo(),
        automaticallyImplyLeading: false,
      ),
      bottomNavigationBar: Container(
        height: 60,
        padding: const EdgeInsets.all(8.0),
        child: const Align(
          alignment: Alignment.bottomCenter,
          child: PoweredByDigit(
            version: Constants.appVersion,
          ),
        ),
      ),
      body: BlocListener<AuthBloc, AuthState>(
        listener: (context, state) {
          state.maybeWhen(
              error: () {
                Notifiers.getToastMessage(
                    context,
                    AppLocalizations.of(context)
                        .translate(i18.login.invalidOTP),
                    'ERROR');
//new
                // Toast.showToast(context,
                //     message: AppLocalizations.of(context)
                //         .translate(i18.login.invalidOTP),
                //     type: ToastType.error);

                context.router.popAndPush(
                    OTPVerificationRoute(mobileNumber: widget.mobileNumber));
              },
              orElse: () => const Offstage());
        },
        child: SingleChildScrollView(
          child: Column(
            children: [
              Padding(
                padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    BackNavigationButton(
                      backNavigationButtonThemeData:
                          const BackNavigationButtonThemeData().copyWith(
                              context: context,
                              backButtonIcon: Icon(
                                Icons.arrow_circle_left_outlined,
                                size: MediaQuery.of(context).size.width < 500
                                    ? Theme.of(context).spacerTheme.spacer5
                                    : Theme.of(context).spacerTheme.spacer6,
                                color: Theme.of(context)
                                    .colorTheme
                                    .primary
                                    .primary2,
                              )),
                      handleBack: () {
                        Navigator.pop(context);
                      },
                      backButtonText: AppLocalizations.of(context)
                          .translate(i18.common.back),
                    ),
                  ],
                ),
              ),
              DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,

                //children:
                // mainAxisAlignment: MainAxisAlignment.start,
                children: [
                  // LabelText(AppLocalizations.of(context)
                  //     .translate(i18.login.otpVerification)),
                  DigitTextBlock(
                    heading: AppLocalizations.of(context)
                        .translate(i18.login.otpVerification),
                    description: localizationText,
                  ),
                  // DigitTextBlock(
                  //   caption: localizationText,
                  // ),
                  // const SizedBox(height: 10),

                  // SubLabelText(localizationText),
                  // old
                  Align(
                      alignment: Alignment.centerLeft,
                      child: SizedBox(
                        height: 40,
                        width: MediaQuery.of(context).size.width < 720
                            ? MediaQuery.of(context).size.width - 50
                            : 350,
                        child: PinInputTextField(
                          pinLength: 6,
                          cursor: Cursor(
                            width: 2,
                            height: 20,
                            color: Colors.black,
                            radius: const Radius.circular(0.5),
                            enabled: true,
                          ),
                          decoration: BoxLooseDecoration(
                              textStyle: DigitTheme
                                  .instance.mobileTheme.textTheme.bodyLarge,
                              strokeColorBuilder: PinListenColorBuilder(
                                  DigitTheme.instance.colorScheme.primary,
                                  Colors.grey),
                              radius: Radius.zero),
                          controller: otpController,
                          onChanged: (value) {
                            setState(() {
                              next = otpController.text.length == 6;
                            });
                          },
                          autoFocus: true,
                          focusNode: pinFocusNode,
                          textInputAction: TextInputAction.go,
                          keyboardType: TextInputType.phone,
                          enableInteractiveSelection: true,
                          inputFormatters: [
                            FilteringTextInputFormatter.allow(
                                RegExp(r'^[0-9]+$'))
                          ],
                        ),
                      )),
                  // end

                  // DigitOTPInput(
                  //     controller: otpController,
                  //     length: 6,
                  //     hasError: true,
                  //     autoFocus: true,
                  //     width: MediaQuery.of(context).size.width,
                  //     fieldWidth: 45,
                  //     outlineBorderRadius: 15,
                  //     onChanged: (pin) {
                  //       setState(() {
                  //         next = otpController.getOtpText().length == 6;
                  //       });
                  //     },
                  //     onCompleted: (pin) {
                  //       //  setState(() {
                  //       //                     next = otpController.getOtpText().length == 6;
                  //       //                   });
                  //     }),

                  Align(
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
                  // const SizedBox(height: 10),
                  Button(
                    mainAxisSize: MainAxisSize.max,
                    size: ButtonSize.large,
                    type: ButtonType.primary,
                    isDisabled: !next,
                    onPressed: () {
                      context.read<AuthBloc>().add(
                            AuthLoginEvent(
                              userId: widget.mobileNumber,
                              password: otpController.text,
                              roleType: RoleType.cbo,
                            ),
                          );
                    },
                    label:
                        AppLocalizations.of(context).translate(i18.common.next),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
