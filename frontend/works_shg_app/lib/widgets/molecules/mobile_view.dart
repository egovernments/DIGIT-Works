import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

import '../atoms/background_container.dart';

class MobileView extends StatelessWidget {
  final Widget widget;
  final String bannerURL;
  final double? logoBottomPosition;
  final double? cardBottomPosition;
  const MobileView(this.widget, this.bannerURL,
      {this.logoBottomPosition = 8.0,
      this.cardBottomPosition = 30.0,
      super.key});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
          Center(child: LayoutBuilder(builder:
              (BuildContext context, BoxConstraints viewportConstraints) {
            return ConstrainedBox(
                constraints: BoxConstraints(
                    minHeight: MediaQuery.of(context).size.height),
                child: IntrinsicHeight(
                    child: Column(children: <Widget>[
                  Expanded(
                      // A flexible child that will grow to fit the viewport but
                      // still be at least as big as necessary to fit its contents.
                      child: Container(
                          color: Colors.blue,
                          // height: MediaQuery.of(context).size.height,
                          child: BackgroundContainer(
                              Stack(children: <Widget>[
                                (Positioned(
                                    bottom: cardBottomPosition,
                                    child: Container(
                                        margin:
                                            const EdgeInsets.only(bottom: 24),
                                        width:
                                            MediaQuery.of(context).size.width,
                                        // height: MediaQuery.of(context).size.height + 20,
                                        padding: const EdgeInsets.all(8),
                                        child: widget))),
                                (Positioned(
                                    bottom: logoBottomPosition,
                                    left: MediaQuery.of(context).size.width / 4,
                                    right:
                                        MediaQuery.of(context).size.width / 4,
                                    child: const Align(
                                        alignment: Alignment.bottomCenter,
                                        child: PoweredByDigit(
                                          isWhiteLogo: true,
                                        ))))
                              ]),
                              bannerURL)))
                ])));
          }))
        ]));
  }
}
