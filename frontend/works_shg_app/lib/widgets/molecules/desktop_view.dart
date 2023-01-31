import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

import '../atoms/background_container.dart';

class DesktopView extends StatelessWidget {
  final Widget widget;
  final String bannerURL;
  const DesktopView(this.widget, this.bannerURL, {super.key});

  @override
  Widget build(BuildContext context) {
    return (BackgroundContainer(
        Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              (Center(
                  child: Container(
                      width: 500,
                      padding: const EdgeInsets.all(15),
                      child: Container(
                          padding: const EdgeInsets.all(8), child: widget)))),
              const Center(child: PoweredByDigit())
            ]),
        bannerURL));
  }
}
