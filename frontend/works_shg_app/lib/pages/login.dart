import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/auth/otp_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/atoms/app_logo.dart';

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
  final formKey = GlobalKey<FormState>();
  bool autoValidation = false;
  bool phoneNumberAutoValidation = false;
  final FocusNode _numberFocus = FocusNode();

  @override
  void initState() {
    _numberFocus.addListener(_onFocusChange);
    super.initState();
  }

  @override
  dispose() {
    _numberFocus.removeListener(_onFocusChange);
    super.dispose();
  }

  @override
  void deactivate() {
    context.read<OTPBloc>().add(
          const DisposeOTPEvent(),
        );
    super.deactivate();
  }

  void _onFocusChange() {
    if (!_numberFocus.hasFocus) {
      setState(() {
        phoneNumberAutoValidation = true;
      });
    }
  }

  Widget getLoginCard(BuildContext loginContext) {
    return Form(
      key: formKey,
      autovalidateMode:
          autoValidation ? AutovalidateMode.always : AutovalidateMode.disabled,
      child: DigitCard(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const AppLogo(),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                  AppLocalizations.of(loginContext)
                      .translate(i18.login.loginLabel),
                  style: const TextStyle(
                      fontSize: 18, fontWeight: FontWeight.w700)),
            ),
            DigitTextField(
              label:
                  '${AppLocalizations.of(loginContext).translate(i18.common.mobileNumber)}*',
              controller: userIdController,
              isRequired: true,
              prefixText: '+91 - ',
              focusNode: _numberFocus,
              autoValidation: phoneNumberAutoValidation
                  ? AutovalidateMode.always
                  : AutovalidateMode.disabled,
              textInputType: TextInputType.number,
              inputFormatter: [
                FilteringTextInputFormatter.allow(RegExp("[0-9]"))
              ],
              validator: (val) {
                if (val!.trim().isEmpty || val!.trim().length != 10) {
                  return '${AppLocalizations.of(context).translate(i18.login.pleaseEnterMobile)}';
                }
                return null;
              },
              onChange: (value) {
                setState(() {
                  canContinue = value.length == 10;
                });
                if (value.length == 10) {
                  _numberFocus.unfocus();
                }
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
                    ? () {
                        if (formKey.currentState!.validate()) {
                          loginContext.read<OTPBloc>().add(
                                OTPSendEvent(
                                  mobileNumber: userIdController.text,
                                ),
                              );
                        } else {
                          setState(() {
                            autoValidation = true;
                          });
                        }
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
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: true,
        ),
        body: LayoutBuilder(builder: (context, constraints) {
          if (constraints.maxWidth < 720) {
            return MobileView(
              getLoginCard(context),
              GlobalVariables.stateInfoListModel!.bannerUrl.toString(),
              logoBottomPosition: constraints.maxHeight / 8,
              cardBottomPosition: constraints.maxHeight / 3,
            );
          } else {
            return DesktopView(getLoginCard(context),
                GlobalVariables.stateInfoListModel!.bannerUrl.toString());
          }
        }));
  }
}
