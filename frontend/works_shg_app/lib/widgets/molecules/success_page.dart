// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';

import 'package:digit_ui_components/widgets/molecules/panel_cards.dart';
import 'package:digit_ui_components/widgets/molecules/panel_cards.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';
import 'package:flutter/material.dart';

import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/theme.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

@RoutePage()
class SuccessResponsePage extends StatelessWidget {
  final String header;
  final String? subHeader;
  final String? subText;
  final String? subTitle;
  final VoidCallback? callBack;
  final VoidCallback? callBackWhatsapp;
  final String? downloadLabel;
  final String? whatsAppLabel;
  final String? printLabel;
  final VoidCallback? callBackDownload;
  final VoidCallback? callBackPrint;
  final bool? backButton;
  final String? buttonLabel;
  final bool isWithoutLogin;
  final String? backButtonLabel;

  const SuccessResponsePage(
      {super.key,
      required this.header,
      this.subHeader,
      this.subText,
      this.subTitle,
      this.callBack,
      this.callBackWhatsapp,
      this.callBackDownload,
      this.callBackPrint,
      this.backButton,
      this.buttonLabel,
      this.isWithoutLogin = false,
      this.downloadLabel,
      this.printLabel,
      this.whatsAppLabel,
      this.backButtonLabel});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final textTheme = theme.digitTextTheme(context);
    return PopScope(
      canPop: true,
      onPopInvoked: (value) async {
        context.router.push(const HomeRoute());
      },
      child: Scaffold(
        backgroundColor: Theme.of(context).colorTheme.generic.background,
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
        // appBar: isWithoutLogin
        //     ? AppBar(
        //         backgroundColor: const Color(0xff0B4B66),
        //         title: const Text('MuktaSoft'),
        //         automaticallyImplyLeading: false,
        //       )
        //     : customAppBar(),
        drawer: isWithoutLogin ? null : const MySideBar(),
        body: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              backButton == true
                  ? Padding(
                       padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          BackNavigationButton(
                             backNavigationButtonThemeData: const BackNavigationButtonThemeData().copyWith(
                  context: context,
                  backButtonIcon: Icon(
                    Icons.arrow_circle_left_outlined,
                    size: MediaQuery.of(context).size.width < 500
                        ? Theme.of(context).spacerTheme.spacer5
                        : Theme.of(context).spacerTheme.spacer6,
                    color: Theme.of(context).colorTheme.primary.primary2,
                  )),
                              backButtonText: AppLocalizations.of(context)
                                      .translate(i18.common.back) ??
                                  'Back',
                              handleBack: () =>
                                  context.router.push(const HomeRoute())),
                        ],
                      ),
                    )
                  : const SizedBox.shrink(),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: PanelCard(
                  title: header,
                  type: PanelType.success,
                  description: subTitle,
                  additionalDetails: subHeader!=null && subText!=null? [
                    Text(subHeader ?? '',textAlign:TextAlign.center, style: textTheme.headingS.copyWith(
                      color: theme.colorTheme.paper.primary

                    )),
                    Text(subText ?? '',textAlign:TextAlign.center,style: textTheme.headingL.copyWith(
                      color: theme.colorTheme.paper.primary
                    ))
                  ]:[],
                  actions: [
                    ui_component.Button(
                      type: ButtonType.primary,
                      size: ButtonSize.large,
                      mainAxisSize: MainAxisSize.max,
                      onPressed: () {
                        context.router.push(const HomeRoute());
                      },
                      label: buttonLabel ?? '',
                    )
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
