// import 'package:digit_components/theme/digit_theme.dart';
// import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/widgets/atoms/digit_divider.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

class FloatActionCard extends StatelessWidget {
  final VoidCallback openButtonSheet;
  final VoidCallback actions;
  final String totalAmountText;
  final String? subtext;
  final String amount;
  final bool showAction;
  const FloatActionCard(
      {super.key,
      required this.openButtonSheet,
      required this.actions,
      required this.totalAmountText,
      this.subtext,
      required this.amount,
      required this.showAction});

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Card(
        // color: Colors.grey,
        shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(20),
            topRight: Radius.circular(20),
          ),
        ),

        //  margin: const EdgeInsets.symmetric(horizontal: 8),
        child: IntrinsicHeight(
            child: Padding(
          padding: const EdgeInsets.only(
              left: 8.0, top: 8.0, right: 8.0, bottom: 0.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              const Center(
                child: SizedBox(
                  width: 100,
                  child: DigitDivider(
                    dividerType: DividerType.large,
                  ),
                ),
              ),
              GestureDetector(
                onTap: openButtonSheet,
                child: Container(
                  height: 100,
                  width: MediaQuery.sizeOf(context).width,
                  padding: const EdgeInsets.symmetric(
                      horizontal: 8.0, vertical: 0.0),
                  // decoration: BoxDecoration(
                  //   border: Border.all(color: Colors.grey),
                  //   borderRadius: BorderRadius.circular(8),
                  // ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        flex: 6,
                        child: RichText(
                          text: TextSpan(
                              text: '$totalAmountText \n',
                              style: Theme.of(context).textTheme.headlineSmall,
                              children: <TextSpan>[
                                TextSpan(
                                  text: subtext ?? "",
                                  style: Theme.of(context).textTheme.bodySmall,
                                )
                              ]),
                        ),
                      ),
                      Expanded(
                        flex: 2,
                        child: Text(
                          amount,
                          style: Theme.of(context).textTheme.headlineMedium,
                          textAlign: TextAlign.end,
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              showAction
                  ? Padding(
                      padding: const EdgeInsets.only(bottom: 8.0),
                      child: Button(
                        mainAxisSize: MainAxisSize.max,
                        label: t.translate(i18.measurementBook.mbAction),
                        onPressed: actions,
                        type: ButtonType.primary,
                        size: ButtonSize.large,
                      ),
                    )
                  : const SizedBox.shrink(),
            ],
          ),
        )));
  }
}
