import 'package:flutter/material.dart';

class FormWrapper extends StatelessWidget {
  final Widget widget;

  FormWrapper(this.widget);

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return Container(
          margin: EdgeInsets.only(
            left: constraints.maxWidth > 760 ? 32 : 8,
            right: constraints.maxWidth > 760 ? 32 : 8,
          ),
          child: widget);
    });
  }
}
