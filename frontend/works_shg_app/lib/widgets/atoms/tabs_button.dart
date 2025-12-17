// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
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
                color: Colors.white,
                shape: BoxShape.rectangle,
                border: Border.all(
                  color: (isSelected ?? false)
                      ? Theme.of(context).colorScheme.primary
                      : Colors.white,
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
                    ? Theme.of(context).colorTheme.paper.secondary
                    : Theme.of(context).colorTheme.primary.primary1,
                fontSize: 16.0,
                fontWeight: FontWeight.w700),
            textAlign: TextAlign.center,
          ),
        ),
      ),
    );
  }
}
