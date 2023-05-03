import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class DigitIndicator extends StatelessWidget {
  /// Whether this indicator is selected or not.
  final bool isSelected;

  final bool isCompleted;

  /// The child to be placed within the indicator.
  final Widget? child;
  final Widget? textChild;

  /// Action to be taken when this indictor is pressed.
  final Function? onPressed;

  /// Color of this indicator when it is not selected.
  final Color? color;

  /// Color of this indicator when it is selected.
  final Color? activeColor;

  /// Border color of this indicator when it is selected.
  final Color? activeBorderColor;
  final bool isTextWidget;

  /// The border width of this indicator when it is selected.
  final activeBorderWidth;

  /// Radius of this indicator.
  final double radius;

  /// The amount of padding around each side of the child.
  final double padding;

  /// The amount of margin around each side of the indicator.
  final double margin;

  const DigitIndicator({
    super.key,
    this.isSelected = false,
    this.isTextWidget = false,
    this.child,
    this.textChild,
    this.onPressed,
    this.color,
    this.activeColor,
    this.activeBorderColor,
    this.radius = 24.0,
    this.padding = 5.0,
    this.margin = 1.0,
    this.activeBorderWidth = 0.5,
    this.isCompleted = false,
  });

  @override
  Widget build(BuildContext context) {
    return isTextWidget
        ? Align(
            alignment: Alignment.center,
            child: Container(
                margin: const EdgeInsets.only(left: 2.0), child: textChild))
        : Container(
            padding: isSelected ? EdgeInsets.all(margin) : EdgeInsets.zero,
            decoration: BoxDecoration(
              border: isSelected
                  ? Border.all(
                      color: activeBorderColor ??
                          const DigitColors().burningOrange,
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
                padding: EdgeInsets.all(padding),
                decoration: BoxDecoration(
                  color: isSelected || isCompleted
                      ? activeBorderColor ?? Colors.green
                      : color ?? Colors.grey,
                  shape: BoxShape.circle,
                ),
                child: Center(
                  child: child,
                ),
              ),
            ),
          );
  }
}
