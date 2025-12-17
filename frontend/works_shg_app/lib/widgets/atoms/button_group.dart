// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:flutter/material.dart';

class ButtonGroup extends StatelessWidget {
  final String outlinedButtonLabel;
  final String elevatedButtonLabel;
  final VoidCallback elevatedCallBack;
  final VoidCallback outLinedCallBack;
  const ButtonGroup(this.outlinedButtonLabel, this.elevatedButtonLabel,
      { required this.outLinedCallBack, required this.elevatedCallBack, super.key});
  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Expanded(
          flex: 5,
          child: Button(
              mainAxisSize: MainAxisSize.max,
              label: outlinedButtonLabel,
              onPressed:  outLinedCallBack,
              type: ButtonType.secondary,
              size: ButtonSize.large),
        ),
        const Expanded(
          flex: 2,
          child: SizedBox(),
        ),
        Expanded(
          flex: 5,
          child: Button(
              mainAxisSize: MainAxisSize.max,
              label: elevatedButtonLabel,
              onPressed:  elevatedCallBack,
              type: ButtonType.primary,
              size: ButtonSize.large),
        ),

        // SizedBox(
        //   height: 35,
        //   width: MediaQuery.of(context).size.width > 720
        //       ? MediaQuery.of(context).size.width / 3
        //       : MediaQuery.of(context).size.width / 2.5,
        //   child: OutlinedButton(
        //     onPressed: outLinedCallBack,
        //     style: ButtonStyle(
        //       alignment: Alignment.center,
        //       padding: MaterialStateProperty.all(
        //           const EdgeInsets.symmetric(vertical: 0.0, horizontal: 4.0)),
        //       shape: MaterialStateProperty.all(RoundedRectangleBorder(
        //         borderRadius: BorderRadius.circular(0.0),
        //         side: BorderSide(
        //             width: 2, color: DigitTheme.instance.colorScheme.primary),
        //       )),
        //     ),
        //     child: Padding(
        //         padding: const EdgeInsets.symmetric(vertical: 4.0),
        //         child: Text(outlinedButtonLabel,
        //             style: TextStyle(
        //                 fontWeight: FontWeight.w400,
        //                 fontSize: 16,
        //                 color: DigitTheme.instance.colorScheme.primary))),
        //   ),
        // ),
        // SizedBox(
        //   height: 35,
        //   width: MediaQuery.of(context).size.width > 720
        //       ? MediaQuery.of(context).size.width / 3
        //       : MediaQuery.of(context).size.width / 2.5,
        //   child: DigitElevatedButton(
        //     onPressed: elevatedCallBack,
        //     child: Padding(
        //         padding:
        //             const EdgeInsets.symmetric(vertical: 0, horizontal: 4.0),
        //         child: Text(elevatedButtonLabel,
        //             style: TextStyle(
        //                 fontWeight: FontWeight.w400,
        //                 fontSize: 16,
        //                 color: DigitTheme.instance.colorScheme.onPrimary))),
        //   ),
        // ),
      ],
    );
  }
}
