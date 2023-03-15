import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';

class EmptyImage extends StatelessWidget {
  // final void Function()? widgetFunction;
  final AlignmentGeometry align;
  final String? label;
  const EmptyImage({super.key, this.align = Alignment.centerLeft, this.label});

  @override
  Widget build(BuildContext context) {
    return Align(
        alignment: align,
        child: Padding(
            padding: const EdgeInsets.only(top: 10, bottom: 10, right: 8),
            child: Column(
              children: [
                SvgPicture.asset('assets/svg/no_result.svg'),
                Text(
                  label ?? '',
                )
              ],
            )));
  }
}
