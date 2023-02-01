import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

import '../atoms/background_container.dart';

class MobileView extends StatelessWidget {
  final Widget widget;
  final String bannerURL;
  const MobileView(this.widget, this.bannerURL);

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        child: Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
          SafeArea(child: Center(child: LayoutBuilder(builder:
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
                          //height: 120.0,
                          child: BackgroundContainer(
                              Stack(children: <Widget>[
                                (Positioned(
                                    bottom: 30.0,
                                    child: Container(
                                        margin:
                                            const EdgeInsets.only(bottom: 24),
                                        width:
                                            MediaQuery.of(context).size.width,
                                        // height: MediaQuery.of(context).size.height + 20,
                                        padding: const EdgeInsets.all(8),
                                        child: widget))),
                                (Positioned(
                                    bottom: 8.0,
                                    left: MediaQuery.of(context).size.width / 4,
                                    right:
                                        MediaQuery.of(context).size.width / 4,
                                    child: const Align(
                                        alignment: Alignment.bottomCenter,
                                        child: PoweredByDigit())))
                              ]),
                              bannerURL)))
                ])));
          })))
        ]));
  }
}
