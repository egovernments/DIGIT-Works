import 'package:flutter/material.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';

import '../atoms/digit_base_stepper.dart';

class DigitStepper extends StatelessWidget {
  /// Each number defines a step. Hence, total count of numbers determines the total number of steps.
  final List<int>? numbers;
  final List<String>? headers;

  /// Whether to allow tapping a step to move to that step or not.
  final bool enableStepTapping;

  /// Determines what should happen when a step is reached. This callback provides the __index__ of the step that was reached.
  final OnStepReached? onStepReached;

  /// Whether to show the steps horizontally or vertically. __Note: Ensure horizontal stepper goes inside a column and vertical goes inside a row.__
  final Axis direction;

  /// The style applied to numbers except the `fontSize` which is calculated automatically.
  final TextStyle numberStyle;
  final TextStyle headerStyle;

  /// The color of the step when it is not reached.
  final Color? stepColor;

  /// The amount of padding inside a step.
  final double stepPadding;

  /// The color of a step when it is reached.
  final Color? activeStepColor;

  /// The border color of a step when it is reached.
  final Color? activeStepBorderColor;

  /// The border width of the active step.
  final double activeStepBorderWidth;

  /// Determines how far away the border should be drawn from the step when it is reached.
  final double activeStepBorderPadding;

  /// The color of the line that separates the steps.
  final Color? lineColor;

  /// The length of the line that separates the steps.
  final double lineLength;

  /// The radius of individual dot within the line that separates the steps.
  final double lineDotRadius;

  /// The radius of a step.
  final double stepRadius;

  /// The animation effect to show when a step is reached.
  final Curve stepReachedAnimationEffect;

  /// The duration of the animation effect to show when a step is reached.
  final Duration stepReachedAnimationDuration;

  /// Whether the stepping is enabled or disabled.
  final bool steppingEnabled;

  /// Whether the scrolling is disabled or not.
  final bool scrollingDisabled;

  /// The currently active step.
  final int activeStep;

  /// Specifies the alignment of the NumberStepper.
  final AlignmentGeometry alignment;

  /// Creates a NumberStepper widget.
  const DigitStepper({
    super.key,
    this.numbers,
    this.headers,
    this.enableStepTapping = true,
    this.onStepReached,
    this.direction = Axis.horizontal,
    this.numberStyle = const TextStyle(color: Colors.black),
    this.headerStyle = const TextStyle(color: Colors.black),
    this.stepColor,
    this.stepPadding = 0.0,
    this.activeStepColor,
    this.activeStepBorderColor,
    this.activeStepBorderWidth = 0.5,
    this.activeStepBorderPadding = 1.0,
    this.lineColor,
    this.lineLength = 50.0,
    this.lineDotRadius = 1.0,
    this.stepRadius = 24.0,
    this.stepReachedAnimationEffect = Curves.bounceOut,
    this.stepReachedAnimationDuration = const Duration(seconds: 1),
    this.steppingEnabled = true,
    this.scrollingDisabled = true,
    this.activeStep = 0,
    this.alignment = Alignment.center,
  });

  @override
  Widget build(BuildContext context) {
    return DigitBaseStepper(
      textChildren: _headersWrappedInText(context),
      stepTappingDisabled: enableStepTapping,
      onStepReached: onStepReached,
      stepColor: stepColor,
      activeStepColor: activeStepColor,
      activeStepBorderColor: activeStepBorderColor,
      activeStepBorderWidth: activeStepBorderWidth,
      lineColor: lineColor,
      lineLength: lineLength,
      lineDotRadius: lineDotRadius,
      stepRadius: stepRadius,
      stepReachedAnimationEffect: stepReachedAnimationEffect,
      stepReachedAnimationDuration: stepReachedAnimationDuration,
      steppingEnabled: steppingEnabled,
      margin: activeStepBorderPadding,
      padding: stepPadding,
      scrollingDisabled: scrollingDisabled,
      activeStep: activeStep,
      alignment: alignment,
      children: _numbersWrappedInText(),
    );
  }

  /// Wraps the integer numbers in Text widget. User style is also applied except the `fontSize` which is calculated automatically.
  List<Widget> _numbersWrappedInText() {
    return List.generate(numbers!.length, (index) {
      return FittedBox(
          alignment: Alignment.centerLeft,
          child: Text(
            '${numbers![index]}',
            style: numberStyle.copyWith(fontSize: stepRadius / 1),
          ));
    });
  }

  List<Widget> _headersWrappedInText(BuildContext context) {
    return List.generate(headers!.length, (index) {
      return Container(
        alignment: Alignment.center,
        margin: index == 0 || index == headers!.length - 1
            ? EdgeInsets.only(
                left: index == 0 ? 8.0 : 0,
                right: index == headers!.length - 1 ? 4.0 : 0)
            : const EdgeInsets.only(left: 20.0),
        width: 65,
        child: Text(
          AppLocalizations.of(context).translate(headers![index]),
          style: headerStyle.copyWith(fontSize: stepRadius),
          softWrap: true,
          maxLines: 4,
          textAlign: TextAlign.left,
        ),
      );
    });
  }
}
