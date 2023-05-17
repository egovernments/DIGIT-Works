import 'package:digit_components/theme/colors.dart';
import 'package:flutter/material.dart';

class DigitIndicator extends StatelessWidget {
  final bool isSelected;
  final bool isCompleted;
  final Color? activeColor;
  final Color? inActiveColor;
  final Function? onPressed;
  final IconData? icon;
  final Color? iconColor;
  final double? iconSize;
  final double radius;
  const DigitIndicator({
    super.key,
    this.isSelected = false,
    this.isCompleted = false,
    this.activeColor,
    this.inActiveColor,
    this.onPressed,
    this.icon,
    this.iconColor,
    this.iconSize,
    this.radius = 8,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.zero,
      decoration: BoxDecoration(
        border: isSelected
            ? Border.all(
                color: activeColor ?? const DigitColors().burningOrange,
                width: 0,
              )
            : null,
        shape: BoxShape.circle,
      ),
      child: InkWell(
        onTap: onPressed as void Function()?,
        child: Container(
          height: radius * 2,
          width: radius * 2,
          padding: const EdgeInsets.all(0),
          decoration: BoxDecoration(
            color: isSelected || isCompleted
                ? activeColor ?? const DigitColors().burningOrange
                : inActiveColor ?? const DigitColors().cloudGray,
            shape: BoxShape.circle,
          ),
          child: Center(
            child: Icon(
              icon ?? Icons.check,
              color: iconColor ?? const DigitColors().white,
              size: iconSize ?? 16,
            ),
          ),
        ),
      ),
    );
  }
}
