import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class Back extends StatelessWidget {
  final Widget? widget;
  final VoidCallback? callback;
  final String? backLabel;

  const Back({Key? key, this.widget, this.callback, this.backLabel})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.symmetric(vertical: 10),
        child: Row(
            mainAxisAlignment: widget == null
                ? MainAxisAlignment.start
                : MainAxisAlignment.spaceBetween,
            children: [
              TextButton(
                  onPressed: callback ?? () => Navigator.pop(context),
                  child: Wrap(
                    alignment: WrapAlignment.start,
                    crossAxisAlignment: WrapCrossAlignment.center,
                    children: [
                      Icon(
                        Icons.arrow_left,
                        color: const DigitColors().black,
                      ),
                      Text(backLabel ?? 'Back',
                          style: TextStyle(
                            color: const DigitColors().black,
                          ))
                    ],
                  )),
              if (widget != null) widget!
            ]));
  }
}
