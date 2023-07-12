import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class LabelText extends StatelessWidget {
  final input;
  final EdgeInsets? padding;
  const LabelText(this.input, {super.key, this.padding});
  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: padding ??
                (constraints.maxWidth > 760
                    ? const EdgeInsets.all(20.0)
                    : const EdgeInsets.all(8.0)),
            child: Text(
              input,
              style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                  ?.apply(color: const DigitColors().black),
              textAlign: TextAlign.left,
            ),
          ));
    });
  }
}
