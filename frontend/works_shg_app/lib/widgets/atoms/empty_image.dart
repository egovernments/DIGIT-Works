import 'package:flutter/material.dart';

class EmptyImage extends StatelessWidget {
  // final void Function()? widgetFunction;
  final AlignmentGeometry align;
  const EmptyImage({super.key, this.align = Alignment.centerLeft});

  @override
  Widget build(BuildContext context) {
    return Align(
        alignment: align,
        child: Padding(
            padding: const EdgeInsets.only(top: 10, bottom: 10, right: 8),
            child: Image.asset('assets/png/empty_message.png')));
  }
}
