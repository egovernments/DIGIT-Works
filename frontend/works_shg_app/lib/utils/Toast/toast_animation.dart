import 'package:flutter/material.dart';

class SlideInToastMessageAnimation extends StatelessWidget {
  final Widget child;

  SlideInToastMessageAnimation(this.child);

  @override
  Widget build(BuildContext context) {
    return AnimatedOpacity(
        // If the widget is visible, animate to 0.0 (invisible).
        // If the widget is hidden, animate to 1.0 (fully visible).
        opacity: 1.0,
        duration: const Duration(seconds: 5),
        // The green box must be a child of the AnimatedOpacity widget.
        child: this.child);
  }
}
