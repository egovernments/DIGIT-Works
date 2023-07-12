import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class TabButton extends StatelessWidget {
  final String buttonLabel;
  final bool? isSelected;
  final bool? isMainTab;
  final Function()? onPressed;

  const TabButton(this.buttonLabel,
      {super.key, this.isSelected, this.isMainTab, this.onPressed});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onPressed,
      child: Container(
        width:
            (isMainTab ?? false) ? MediaQuery.of(context).size.width / 3 : null,
        height: 35,
        decoration: (isMainTab ?? false)
            ? BoxDecoration(
                color: const DigitColors().white,
                shape: BoxShape.rectangle,
                border: Border.all(
                  color: (isSelected ?? false)
                      ? DigitTheme.instance.colorScheme.primary
                      : const DigitColors().white,
                  width: 1.5,
                ),
                borderRadius: BorderRadius.circular(0.0))
            : BoxDecoration(
                color: (isSelected ?? false)
                    ? Colors.white
                    : const Color.fromRGBO(244, 119, 56, 0.12),
                borderRadius: BorderRadius.circular(18.0)),
        padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 0.0),
        child: Center(
          child: Text(
            buttonLabel,
            style: TextStyle(
                color: (isMainTab ?? false) && !(isSelected ?? false)
                    ? const DigitColors().cloudGray
                    : DigitTheme.instance.colorScheme.primary,
                fontSize: 16.0,
                fontWeight: FontWeight.w700),
            textAlign: TextAlign.center,
          ),
        ),
      ),
    );
  }
}
