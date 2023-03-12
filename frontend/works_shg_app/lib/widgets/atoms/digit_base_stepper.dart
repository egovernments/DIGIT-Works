import 'package:flutter/material.dart';

import 'digit_indicator.dart';

/// Callback is fired when a step is reached.
typedef OnStepReached = void Function(int index);

class DigitBaseStepper extends StatefulWidget {
  /// Creates a basic stepper.
  DigitBaseStepper({
    Key? key,
    this.children,
    this.textChildren,
    this.nextPreviousButtonsDisabled = true,
    this.stepTappingDisabled = true,
    this.previousButtonIcon,
    this.nextButtonIcon,
    this.onStepReached,
    this.stepColor,
    this.activeStepColor,
    this.activeStepBorderColor,
    this.lineColor,
    this.lineLength = 50.0,
    this.lineDotRadius = 1.0,
    this.stepRadius = 24.0,
    this.stepReachedAnimationEffect = Curves.bounceOut,
    this.stepReachedAnimationDuration = const Duration(seconds: 1),
    this.steppingEnabled = true,
    this.padding = 5.0,
    this.margin = 1.0,
    this.activeStepBorderWidth = 0.5,
    this.scrollingDisabled = false,
    this.activeStep = 0,
    this.alignment,
  }) : super(key: key) {
    assert(
      lineDotRadius <= 10 && lineDotRadius > 0,
      'lineDotRadius must be less than or equal to 10 and greater than 0',
    );

    assert(
      stepRadius > 0,
      'iconIndicatorRadius must be greater than 0',
    );

    assert(
      activeStep >= 0 && activeStep <= children!.length,
      'Error: Active Step out of range',
    );
  }

  /// Each child defines a step. Hence, total number of children determines the total number of steps.
  final List<Widget>? children;

  final List<Widget>? textChildren;

  /// Whether to enable or disable the next and previous buttons.
  final bool nextPreviousButtonsDisabled;

  /// Whether to allow tapping a step to move to that step or not.
  final bool stepTappingDisabled;

  /// Icon to be used for the previous button.
  final Icon? previousButtonIcon;

  /// Icon to be used for the next button.
  final Icon? nextButtonIcon;

  /// This callback provides the __index__ of the step that is reached.
  final OnStepReached? onStepReached;

  /// The color of the step when it is not reached.
  final Color? stepColor;

  /// The color of a step when it is reached.
  final Color? activeStepColor;

  /// The border color of a step when it is reached.
  final Color? activeStepBorderColor;

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

  /// Amount of padding on each side of the child widget.
  final double padding;

  /// Amount of margin on each side of the step.
  final double margin;

  /// The width of the active step border.
  final double activeStepBorderWidth;

  /// Whether to disable scrolling or not.
  final bool scrollingDisabled;

  /// The step that is currently active.
  final int activeStep;

  /// Specifies the alignment of the stepper.
  final AlignmentGeometry? alignment;

  @override
  DigitBaseStepperState createState() => DigitBaseStepperState();
}

class DigitBaseStepperState extends State<DigitBaseStepper> {
  ScrollController? _scrollController;
  late int _selectedIndex;

  @override
  void initState() {
    _selectedIndex = widget.activeStep;
    _scrollController = ScrollController();
    super.initState();
  }

  @override
  void didUpdateWidget(DigitBaseStepper oldWidget) {
    super.didUpdateWidget(oldWidget);

    // Verify that the active step falls within a valid range.
    if (widget.activeStep >= 0 && widget.activeStep < widget.children!.length) {
      _selectedIndex = widget.activeStep;
    }
  }

  @override
  void dispose() {
    _scrollController!.dispose();
    super.dispose();
  }

  /// Controls the step scrolling.
  void _afterLayout(_) {
    // ! Provide detailed explanation.
    for (int i = 0; i < widget.children!.length; i++) {
      _scrollController!.animateTo(
        i * ((widget.stepRadius * 2) + widget.lineLength),
        duration: widget.stepReachedAnimationDuration,
        curve: widget.stepReachedAnimationEffect,
      );

      if (_selectedIndex == i) break;
    }
  }

  @override
  Widget build(BuildContext context) {
    // Controls scrolling behavior.
    if (!widget.scrollingDisabled) {
      WidgetsBinding.instance.addPostFrameCallback(_afterLayout);
    }

    return Row(
      children: <Widget>[
        widget.nextPreviousButtonsDisabled
            ? _previousButton()
            : SizedBox.shrink(),
        Expanded(
          child: _stepperBuilder(),
        ),
        widget.nextPreviousButtonsDisabled ? _nextButton() : SizedBox.shrink(),
      ],
    );
  }

  /// Builds the stepper.
  Widget _stepperBuilder() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      controller: _scrollController,
      physics: widget.scrollingDisabled
          ? const NeverScrollableScrollPhysics()
          : const ClampingScrollPhysics(),
      child: Container(
        margin: const EdgeInsets.only(left: 0.0, right: 8.0),
        // padding: const EdgeInsets.all(8.0),
        child: Row(children: _buildSteps()),
      ),
    );
  }

  /// Builds the stepper steps.
  List<Widget> _buildSteps() {
    return List.generate(
      widget.children!.length,
      (index) {
        return Column(mainAxisAlignment: MainAxisAlignment.start, children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              _customizedLine(index, Axis.horizontal, index != 0),
              _customizedIndicator(index),
              _customizedLine(index, Axis.horizontal,
                  index != (widget.children!.length - 1)),
            ],
          ),
          const SizedBox(
            height: 8.0,
          ),
          Container(
              margin: const EdgeInsets.all(0.0),
              child: _customizedTextIndicator(index))
        ]);
      },
    );
  }

  /// A customized IconStep.
  Widget _customizedIndicator(int index) {
    return DigitIndicator(
      isSelected: _selectedIndex == index,
      onPressed: widget.stepTappingDisabled
          ? () {
              if (widget.steppingEnabled) {
                setState(() {
                  _selectedIndex = index;

                  if (widget.onStepReached != null) {
                    widget.onStepReached!(_selectedIndex);
                  }
                });
              }
            }
          : null,
      color: widget.stepColor,
      activeColor: widget.activeStepColor,
      activeBorderColor: widget.activeStepBorderColor,
      radius: widget.stepRadius,
      padding: widget.padding,
      margin: widget.margin,
      activeBorderWidth: widget.activeStepBorderWidth,
      textChild: widget.textChildren![index],
      child: widget.children![index],
    );
  }

  Widget _customizedTextIndicator(int index) {
    return DigitIndicator(
      isSelected: _selectedIndex == index,
      onPressed: widget.stepTappingDisabled
          ? () {
              if (widget.steppingEnabled) {
                setState(() {
                  _selectedIndex = index;

                  if (widget.onStepReached != null) {
                    widget.onStepReached!(_selectedIndex);
                  }
                });
              }
            }
          : null,
      color: widget.stepColor,
      activeColor: widget.activeStepColor,
      activeBorderColor: widget.activeStepBorderColor,
      radius: widget.stepRadius,
      padding: widget.padding,
      margin: widget.margin,
      activeBorderWidth: widget.activeStepBorderWidth,
      isTextWidget: true,
      textChild: widget.textChildren![index],
    );
  }

  /// A customized DottedLine.
  Widget _customizedLine(int index, Axis axis, bool isVisible) {
    return index < widget.children!.length
        ? RotatedBox(
            quarterTurns: 1,
            child: Container(
              width: !isVisible ? 0.0 : 2.0,
              height: !isVisible ? widget.lineLength + 20 : widget.lineLength,
              color: Colors.grey.shade400,
            ))
        : Container();
  }

  /// The previous button.
  Widget _previousButton() {
    return IgnorePointer(
      ignoring: _selectedIndex == 0,
      child: IconButton(
        visualDensity: VisualDensity.compact,
        icon: widget.previousButtonIcon ?? const Icon(Icons.arrow_left),
        onPressed: _goToPreviousStep,
      ),
    );
  }

  /// The next button.
  Widget _nextButton() {
    return IgnorePointer(
      ignoring: _selectedIndex == widget.children!.length - 1,
      child: IconButton(
        visualDensity: VisualDensity.compact,
        icon: widget.nextButtonIcon ?? const Icon(Icons.arrow_right),
        onPressed: _goToNextStep,
      ),
    );
  }

  /// Contains the logic for going to the next step.
  void _goToNextStep() {
    if (_selectedIndex < widget.children!.length - 1 &&
        widget.steppingEnabled) {
      setState(() {
        _selectedIndex++;

        if (widget.onStepReached != null) {
          widget.onStepReached!(_selectedIndex);
        }
      });
    }
  }

  /// Controls the logic for going to the previous step.
  void _goToPreviousStep() {
    if (_selectedIndex > 0) {
      setState(() {
        _selectedIndex--;

        if (widget.onStepReached != null) {
          widget.onStepReached!(_selectedIndex);
        }
      });
    }
  }
}
