// import 'package:digit_components/digit_components.dart';
// import 'package:digit_components/models/digit_row_card/digit_row_card_model.dart';
import 'dart:async';

import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/models/privacy_notice/privacy_notice_model.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/labelled_fields.dart'
    as ui_label;
import 'package:digit_ui_components/widgets/atoms/pop_up_card.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:digit_ui_components/widgets/privacy_policy/privacy_component.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';

import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/auth/otp_bloc.dart';
import 'package:works_shg_app/models/init_mdms/init_mdms_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/atoms/app_logo.dart';

import '../blocs/app_initilization/app_initilization.dart';
import '../blocs/localization/app_localization.dart';
import '../utils/employee/support_services.dart';
import '../widgets/molecules/desktop_view.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;

@RoutePage()
class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  final userIdController = TextEditingController();
  final userNameController = TextEditingController();
  final userPasswordController = TextEditingController();
  bool canContinue = false;
  final formKey = GlobalKey<FormState>();
  bool autoValidation = false;
  bool phoneNumberAutoValidation = false;
  final FocusNode _numberFocus = FocusNode();
  String selectTenantId = "";

  bool iconVisibility = true;

  String cityDropDownKey = "cityDropDownKey";
  String privacyPolicy = "privacyPolicy";

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _tabController.addListener(_handleTabSelection);
    _numberFocus.addListener(_onFocusChange);
  }

  @override
  void dispose() {
    _tabController.removeListener(_handleTabSelection);
    _tabController.dispose();
    _numberFocus.removeListener(_onFocusChange);
    userIdController.dispose();
    userNameController.dispose();
    userPasswordController.dispose();
    _numberFocus.dispose();
    super.dispose();
  }

  void _handleTabSelection() {
    userIdController.clear();
    userNameController.clear();
    userPasswordController.clear();
    setState(() {});
  }

  void _onFocusChange() {
    if (!_numberFocus.hasFocus) {
      setState(() {
        phoneNumberAutoValidation = true;
      });
    }
  }

  Widget getLoginCard(AppLocalizations t, BuildContext loginContext,
      AppInitializationState data) {
    return Center(
      child: Form(
        key: formKey,
        autovalidateMode: autoValidation
            ? AutovalidateMode.always
            : AutovalidateMode.disabled,
        child: Center(
          child: DigitCard(
            //padding: const EdgeInsets.all(16),
            margin: const EdgeInsets.all(16),
            cardType: CardType.primary,
            children: [
              // Top part (AppLogo and Text) - constant height
              const AppLogo(),
              Center(
                child: DigitTextBlock(
                  heading: t.translate(i18.login.loginLabel),
                  headingStyle: Theme.of(context)
                      .digitTextTheme(context)
                      .headingL
                      .copyWith(
                          color: Theme.of(context).colorTheme.primary.primary2),
                ),
              ),
//old

              ToggleList(
                capitalizeFirstLetter: false,
                contentPadding: const EdgeInsets.all(0),
                toggleWidth: MediaQuery.sizeOf(context).width * 0.4,
                toggleButtons: [
                  ToggleButtonModel(
                      code: "0",
                      name: t
                          .translate(i18.measurementBook.mbCbo)
                          .toString()
                          .toUpperCase()),
                  ToggleButtonModel(
                      name: t.translate(i18.measurementBook.mbEmployee),
                      code: "1")
                ],
                onChanged: (p0) {
                  _tabController.index = int.parse(p0.code);
                },
                selectedIndex: 0,
              ),

              AnimatedContainer(
                height: _tabController.index == 0
                    ? MediaQuery.of(context).size.height * 0.255
                    : MediaQuery.of(context).size.height * 0.399,
                duration: const Duration(milliseconds: 000),
                child: TabBarView(
                  physics: const NeverScrollableScrollPhysics(),
                  controller: _tabController,
                  children: [
                    cboLogin(loginContext),
                    employeeLogin(t, loginContext, data),
                  ],
                ),
              ),

              // _buildLoginButton(t, loginContext),

             if(_tabController.index == 1)...[ Visibility(
                visible: _tabController.index == 1,
                child: Center(
                  child: Button(
                    type: ButtonType.tertiary,
                    size: ButtonSize.large,
                    label: t.translate(i18.login.forgotPassword),
                    onPressed: () {
                      forgotPassword(t);
                    },
                  ),
                ),
              )],
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildLoginButton(
      AppLocalizations t, BuildContext loginContext, FormGroup formGroup) {
    if (_tabController.index == 0) {
      return BlocListener<OTPBloc, OTPBlocState>(
        listener: (context, state) {
          state.maybeWhen(
            orElse: () {},
            loaded: () {
              context.router.push(
                  OTPVerificationRoute(mobileNumber: userIdController.text));
            },
            error: () {
              Notifiers.getToastMessage(
                context,
                t.translate(i18.login.enteredMobileNotRegistered),
                'ERROR',
              );
              //new
              // Toast.showToast(
              //   context,
              //   message: t.translate(i18.login.enteredMobileNotRegistered),
              //   type: ToastType.error,
              // );
            },
          );
        },
        child: ReactiveFormConsumer(builder:
            (BuildContext context, FormGroup formGroup, Widget? child) {
          bool canPrivacy =
              (formGroup.control(privacyPolicy).value == true) ? true : false;
          return InkWell(
            onTap: (canContinue && canPrivacy)
                ? () {
                    if (formKey.currentState!.validate()) {
                      formGroup.markAllAsTouched();
                      if (!formGroup.valid) return;

                      FocusManager.instance.primaryFocus?.unfocus();
                      loginContext.read<OTPBloc>().add(
                          OTPSendEvent(mobileNumber: userIdController.text));
                    } else {
                      setState(() {
                        autoValidation = true;
                      });
                    }
                  }
                : null,
            child: IgnorePointer(
              child: Button(
                mainAxisSize: MainAxisSize.max,
                isDisabled: isDisabledFunction(canContinue, canPrivacy),
                size: ButtonSize.large,
                type: ButtonType.primary,
                onPressed: () {},
                label: t.translate(i18.common.continueLabel),
              ),
            ),
          );
        }),
      );
    } else {
      return BlocListener<AuthBloc, AuthState>(
        listener: (context, state) {
          state.maybeWhen(
            error: () {
              Notifiers.getToastMessage(
                context,
                t.translate(i18.common.empLoginError),
                'ERROR',
              );
              // new
              // Toast.showToast(
              //   context,
              //   message: t.translate(i18.common.empLoginError),
              //   type: ToastType.error,
              // );
            },
            orElse: () {},
          );
        },
        child: ReactiveFormConsumer(builder:
            (BuildContext context, FormGroup formGroup, Widget? child) {
          bool privacyCheck =
              (formGroup.control(privacyPolicy).value == true) ? true : false;
          return InkWell(
            onTap: (userNameController.text.isNotEmpty &&
                    userPasswordController.text.isNotEmpty &&
                    selectTenantId.isNotEmpty &&
                    privacyCheck)
                ? () {
                    if (userNameController.text.isNotEmpty &&
                        userPasswordController.text.isNotEmpty &&
                        selectTenantId.isNotEmpty) {
                      formGroup.markAllAsTouched();
                      if (!formGroup.valid) return;

                      FocusManager.instance.primaryFocus?.unfocus();

                      context.read<AuthBloc>().add(
                            AuthLoginEvent(
                              userId: userNameController.text,
                              password: userPasswordController.text,
                              roleType: RoleType.employee,
                              tenantId: selectTenantId,
                            ),
                          );
                    } else {
                      Notifiers.getToastMessage(
                        context,
                        t.translate(i18.common.allFieldsMandatory),
                        'ERROR',
                      );
                      //new
                      // Toast.showToast(
                      //   context,
                      //   message: t.translate(i18.common.allFieldsMandatory),
                      //   type: ToastType.error,
                      // );
                    }
                  }
                : null,
            child: IgnorePointer(
              child: Button(
                mainAxisSize: MainAxisSize.max,
                isDisabled: !(userNameController.text.isNotEmpty &&
                    userPasswordController.text.isNotEmpty &&
                    selectTenantId.isNotEmpty &&
                    privacyCheck),
                size: ButtonSize.large,
                type: ButtonType.primary,
                onPressed: () {},
                label: t.translate(i18.common.continueLabel),
              ),
            ),
          );
        }),
      );
    }
  }

  forgotPassword(AppLocalizations t) {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (context) {
        return Popup(
            title: t.translate(i18.login.forgotPassword),
            additionalWidgets: [
              SizedBox(
                width: MediaQuery.of(context).size.width,
                height: 122,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(bottom: 12.0),
                      child: DigitTextBlock(
                        subHeading: t.translate(i18.login.forgotPasswordMsg),
                      ),
                    ),
                    Button(
                      size: ButtonSize.large,
                      mainAxisSize: MainAxisSize.max,
                      type: ButtonType.primary,
                      label: t.translate(i18.common.oK),
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                    ),
                  ],
                ),
              ),
            ]);
      },
    );
  }

  ReactiveFormBuilder cboLogin(BuildContext loginContext) {
    return ReactiveFormBuilder(
      form: detailCboBuildForm,
      builder:
          (BuildContext loginContext, FormGroup formGroup, Widget? child) =>
              Padding(
        padding: const EdgeInsets.only(top: 0.0),
        child: SizedBox(
          height: MediaQuery.of(loginContext).size.height * 0.7,
          child: Column(
            children: [
              ui_label.LabeledField(
                isRequired: true,
                label:
                    '${AppLocalizations.of(loginContext).translate(i18.common.mobileNumber)}',
                child: DigitTextFormInput(
                  keyboardType: TextInputType.number,
                  // focusNode: _numberFocus,
                  prefixText: '+91',
                  controller: userIdController,
                  isRequired: true,
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                  ],
                  onChange: (value) {
                    if (value.length == 10) {
                      setState(() {
                        canContinue = true;
                      });
                    }
                    // if (value.length == 10) {
                    //   _numberFocus.unfocus();
                    // }
                  },
                ),
              ),

              
              Padding(
                padding: EdgeInsets.only(
                    top: Theme.of(context).spacerTheme.spacer4,
                    bottom: Theme.of(context).spacerTheme.spacer4),
                child:
                    BlocBuilder<AppInitializationBloc, AppInitializationState>(
                        builder: (context, initState) {
                  if (initState.initMdmsModel?.commonUIConfigModel
                              ?.privacyPolicyModels ==
                          null ||
                      initState.initMdmsModel!.commonUIConfigModel!
                          .privacyPolicyModels!.isEmpty) {
                    return const SizedBox.shrink();
                  }

                  formGroup
                      .control(privacyPolicy)
                      .setValidators([Validators.requiredTrue]);
                  formGroup.control(privacyPolicy).updateValueAndValidity();
                  return PrivacyComponent(
                    privacyPolicy: convertToPrivacyPolicyModel(
                        AppLocalizations.of(loginContext),
                        initState.initMdmsModel!.commonUIConfigModel!
                            .privacyPolicyModels!.first),
                    formControlName: privacyPolicy,
                    text: AppLocalizations.of(loginContext)
                        .translate(i18.privacyPolicy.byClick),
                    linkText: AppLocalizations.of(loginContext)
                        .translate(i18.privacyPolicy.privacyPolicyLink),
                    validationMessage: AppLocalizations.of(loginContext)
                        .translate(
                            i18.privacyPolicy.privacyPolicyValidationText),
                    acceptText: AppLocalizations.of(loginContext)
                        .translate(i18.privacyPolicy.accept),
                    declineText: AppLocalizations.of(loginContext)
                        .translate(i18.privacyPolicy.decline),
                  );
                }),
              ),
              _buildLoginButton(
                  AppLocalizations.of(loginContext), context, formGroup),
            ],
          ),
        ),
      ),
    );
  }

  // final privacyPolicyModel = PrivacyNoticeModel.fromJson(privacyPolicyContent);

  @override
  Widget build(BuildContext context) {
    double keyboardHeight = MediaQuery.of(context).viewInsets.bottom;

    // Check if the keyboard is open
    bool isKeyboardOpen = keyboardHeight > 0;
    var t = AppLocalizations.of(context);
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        //  resizeToAvoidBottomInset: true,
        appBar: AppBar(
          backgroundColor: const Color(0xff0B4B66),
          iconTheme: Theme.of(context)
              .iconTheme
              .copyWith(color: Theme.of(context).colorTheme.paper.primary),
          automaticallyImplyLeading: true,
        ),
        body: BlocBuilder<AppInitializationBloc, AppInitializationState>(
          builder: (context, state) {
            return LayoutBuilder(builder: (context, constraints) {
              if (constraints.maxWidth < 720) {
                return Stack(
                  children: [
                    Container(
                      decoration: BoxDecoration(
                        color: const Color(0xff0B4B66),
                        image: DecorationImage(
                          colorFilter: ColorFilter.mode(
                              Colors.black.withOpacity(0.2), BlendMode.dstATop),
                          image: NetworkImage(GlobalVariables
                              .stateInfoListModel!.bannerUrl
                              .toString()),
                          fit: BoxFit.cover,
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(
                          top: MediaQuery.sizeOf(context).height * 0.04),
                      child: IntrinsicHeight(
                        child: getLoginCard(t, context, state),
                      ),
                    ),
                    (isKeyboardOpen)
                        ? (Positioned(
                            bottom: 10,
                            left: MediaQuery.of(context).size.width / 4,
                            right: MediaQuery.of(context).size.width / 4,
                            child: const Align(
                              alignment: Alignment.bottomCenter,
                              child: PoweredByDigit(
                                version: Constants.appVersion,
                                //isWhiteLogo: true,
                              ),
                            ),
                          ))
                        : const Positioned(child: SizedBox.shrink()),
                  ],
                );
              } else {
                return DesktopView(
                  getLoginCard(t, context, state),
                  GlobalVariables.stateInfoListModel!.bannerUrl.toString(),
                );
              }
            });
          },
        ),
      ),
    );
  }

  Widget employeeLogin(
    AppLocalizations t,
    BuildContext context,
    AppInitializationState data,
  ) {
    return SizedBox(
      height: MediaQuery.of(context).size.height * 0.7,
      width: MediaQuery.of(context).size.width,
      child: SingleChildScrollView(
        child: ReactiveFormBuilder(
            form: detailBuildForm,
            builder:
                (BuildContext context, FormGroup formGroup, Widget? child) {
              return Column(
                children: [
                  ui_label.LabeledField(
                    isRequired: true,
                    label: '${t.translate(i18.login.loginUserName)}',
                    child: DigitTextFormInput(
                      controller: userNameController,
                      isRequired: true,
                      onChange: (value) {},
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 16.0),
                    child: ui_label.LabeledField(
                      isRequired: true,
                      wrapLabelText: true,
                      label: '${t.translate(i18.login.loginPassword)}',
                      child: DigitPasswordFormInput(
                        toggleSuffixIcon: iconVisibility
                            ? Icons.visibility_rounded
                            : Icons.visibility_off_rounded,
                        controller: userPasswordController,
                        isRequired: true,
                        onSuffixTap: (p0) {
                          setState(() {
                            iconVisibility = !iconVisibility;
                          });
                        },
                        onChange: (value) {},
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 16.0, bottom: 0.0),
                    child: ui_label.LabeledField(
                      isRequired: true,
                      label: "${t.translate(i18.common.city)}",
                      child: ui_component.DigitDropdown(
                        onSelect: (value) {
                          setState(() {
                            selectTenantId = value?.code ?? "";
                          });
                        },
                        // label: "${t.translate(i18.common.city)} *",
                        items: data.initMdmsModel!.tenant!.tenantListModel!
                            .map((e) => DropdownItem(
                                name: t.translate(
                                    Conversion.convertToTenant(e!.code!)),
                                code: e!.code!))
                            .toList(),
                        // valueMapper: (value) =>
                        //     t.translate(Conversion.convertToTenant(value!.code!)),
                      ),
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(
                        top: Theme.of(context).spacerTheme.spacer4,
                        bottom: Theme.of(context).spacerTheme.spacer4),
                    child: BlocBuilder<AppInitializationBloc,
                        AppInitializationState>(builder: (context, initState) {
                      if (initState.initMdmsModel?.commonUIConfigModel
                                  ?.privacyPolicyModels ==
                              null ||
                          initState.initMdmsModel!.commonUIConfigModel!
                              .privacyPolicyModels!.isEmpty) {
                        return const SizedBox.shrink();
                      }

                      formGroup
                          .control(privacyPolicy)
                          .setValidators([Validators.requiredTrue]);
                      formGroup.control(privacyPolicy).updateValueAndValidity();

                      return PrivacyComponent(
                        declineText: t.translate(i18.privacyPolicy.decline),
                        acceptText: t.translate(i18.privacyPolicy.accept),
                        privacyPolicy: convertToPrivacyPolicyModel(
                            t,
                            initState.initMdmsModel!.commonUIConfigModel!
                                .privacyPolicyModels!.first),
                        formControlName: privacyPolicy,
                        text: t.translate(i18.privacyPolicy.byClick),
                        linkText:
                            t.translate(i18.privacyPolicy.privacyPolicyLink),
                        validationMessage: t.translate(
                            i18.privacyPolicy.privacyPolicyValidationText),
                      );
                    }),
                  ),
                  _buildLoginButton(t, context, formGroup),
                ],
              );
            }),
      ),
    );
  }

  FutureOr<List<dynamic>> getLocalizedDataStringxx() async {
    return AppLocalizations.localizedStrings;
  }

  FormGroup detailBuildForm() => fb.group(<String, Object>{
        cityDropDownKey: FormControl<TenantListModel>(value: null),
        privacyPolicy: FormControl<bool>(value: false),
      });

  FormGroup detailCboBuildForm() => fb.group(<String, Object>{
        privacyPolicy: FormControl<bool>(value: false),
      });

  // convert to privacy notice model
  PrivacyNoticeModel? convertToPrivacyPolicyModel(
      AppLocalizations t, PrivacyPolicyModel? privacyPolicy) {
    return PrivacyNoticeModel(
      header: privacyPolicy?.header != null
          ? t.translate(privacyPolicy!.header!)
          : '',
      module: privacyPolicy?.module != null
          ? t.translate(privacyPolicy!.module!)
          : '',
      active: privacyPolicy?.active,
      contents: privacyPolicy?.contents
          ?.map((content) => ContentNoticeModel(
                header:
                    content.header != null ? t.translate(content.header!) : '',
                descriptions: content.descriptions
                    ?.map((description) => DescriptionNoticeModel(
                          text: description.text != null
                              ? t.translate(description.text!)
                              : '',
                          type: description.type != null
                              ? description.type == "step"
                                  ? 'Step'
                                  : t.translate(description.type!)
                              : '',
                          isBold: description.isBold,
                          subDescriptions: description.subDescriptions
                              ?.map(
                                  (subDescription) => SubDescriptionNoticeModel(
                                        text: subDescription.text != null
                                            ? t.translate(subDescription.text!)
                                            : '',
                                        type: subDescription.type != null
                                            ? t.translate(subDescription.type!)
                                            : '',
                                        isBold: subDescription.isBold,
                                        isSpaceRequired:
                                            subDescription.isSpaceRequired,
                                      ))
                              .toList(),
                        ))
                    .toList(),
              ))
          .toList(),
    );
  }

//

  bool isDisabledFunction(bool a, bool b) {
    if (a == true && b == true) {
      return false;
    } else {
      return true;
    }
  }
}
