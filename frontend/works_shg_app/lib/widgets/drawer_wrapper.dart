import 'package:flutter/material.dart';

class DrawerWrapper extends StatelessWidget {
  final widget;
  DrawerWrapper(this.widget);
  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return SafeArea(
          child: Container(
              width: constraints.maxWidth < 720 ? 290 : 350,
              margin: const EdgeInsets.only(top: kToolbarHeight),
              child: widget));
    });
  }
}
