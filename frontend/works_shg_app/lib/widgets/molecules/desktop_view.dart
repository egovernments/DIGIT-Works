// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/constants.dart';

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
                      width: MediaQuery.of(context).size.width*0.8,
                      padding: const EdgeInsets.all(15),
                      child: Container(
                          padding: const EdgeInsets.all(8), child: widget)))),
              const Center(
                  child: PoweredByDigit(
                    version: Constants.appVersion,
               
              ))
            ]),
        bannerURL));
  }
}
