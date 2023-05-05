import 'package:flutter/material.dart';

class CustomDetailsCard extends StatelessWidget {
  final Widget widget;

  const CustomDetailsCard(this.widget, {super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: MediaQuery.of(context).size.width > 760
          ? const EdgeInsets.only(left: 16.0, right: 16.0, bottom: 8.0)
          : const EdgeInsets.all(8.0),
      margin: const EdgeInsets.only(left: 4.0, right: 4.0, bottom: 16.0),
      decoration: BoxDecoration(
          color: const Color.fromRGBO(238, 238, 238, 0.4),
          border: Border.all(color: Colors.grey, width: 0.6),
          borderRadius: const BorderRadius.all(
            Radius.circular(4.0),
          )),
      child: Wrap(
        children: [widget],
      ),
    );
  }
}
