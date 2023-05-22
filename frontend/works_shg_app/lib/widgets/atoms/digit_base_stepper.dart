import 'package:digit_components/digit_components.dart';
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
    this.stepTappingDisabled = true,
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

  final List<Widget>? children;
  final List<Widget>? textChildren;
  final bool stepTappingDisabled;
  final OnStepReached? onStepReached;
  final Color? stepColor;
  final Color? activeStepColor;
  final Color? activeStepBorderColor;
  final Color? lineColor;
  final double lineLength;
  final double lineDotRadius;
  final double stepRadius;
  final Curve stepReachedAnimationEffect;
  final Duration stepReachedAnimationDuration;
  final bool steppingEnabled;
  final double padding;
  final double margin;
  final double activeStepBorderWidth;
  final bool scrollingDisabled;
  final int activeStep;
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
      mainAxisAlignment: MainAxisAlignment.start,
      children: <Widget>[
        Expanded(
          child: _stepperBuilder(),
        ),
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
        margin: const EdgeInsets.only(left: 0.0, right: 0.0),
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
              index != 0
                  ? _customizedLine(index, Axis.horizontal, index != 0)
                  : const Padding(
                      padding:
                          EdgeInsets.only(left: 24.0, top: 8.0, bottom: 8.0),
                      child: SizedBox.shrink(),
                    ),
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
      isCompleted: index < _selectedIndex,
      onPressed: widget.stepTappingDisabled
          ? () {
              if (widget.steppingEnabled && index < widget.activeStep) {
                setState(() {
                  _selectedIndex = index;

                  if (widget.onStepReached != null &&
                      index < widget.activeStep) {
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
      textChild: index == 0 || index == (widget.children?.length ?? 1 - 1)
          ? widget.textChildren![index]
          : Container(
              alignment: Alignment.centerRight,
              child: widget.textChildren![index],
            ),
      child: index < _selectedIndex
          ? Icon(
              Icons.check,
              color: const DigitColors().white,
              size: 16,
            )
          : widget.children![index],
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
      textChild: SizedBox(height: 40, child: widget.textChildren![index]),
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
}
