import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class ButtonLink extends StatelessWidget {
  final String label;
  final void Function()? widgetFunction;
  final AlignmentGeometry align;
  final double fontSize;
  final TextStyle? style;
  const ButtonLink(this.label, this.widgetFunction,
      {super.key,
      this.align = Alignment.centerLeft,
      this.fontSize = 16,
      this.style});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: widgetFunction,
      child: Padding(
          padding: const EdgeInsets.only(top: 10, bottom: 10, right: 25),
          child: Align(
              alignment: align,
              child: Text(
                label,
                style: style ??
                    TextStyle(
                        fontSize: fontSize,
                        fontWeight: FontWeight.w400,
                        color: DigitTheme
                            .instance.mobileTheme.colorScheme.secondary),
              ))),
    );
  }
}
