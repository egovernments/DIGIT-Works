import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class SubLabelText extends StatelessWidget {
  final String input;
  const SubLabelText(this.input, {super.key});
  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: constraints.maxWidth > 760
                ? const EdgeInsets.all(20.0)
                : const EdgeInsets.all(8.0),
            child: Text(
              input,
              style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w400,
                  color: DigitTheme.instance.colorScheme.shadow),
              textAlign: TextAlign.left,
            ),
          ));
    });
  }
}
