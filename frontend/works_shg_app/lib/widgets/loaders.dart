import 'package:flutter/material.dart';

class Loaders {
  static circularLoader(BuildContext context) {
    return WillPopScope(
        onWillPop: () async => false,
        child: SizedBox(
          height: MediaQuery.of(context).size.height,
          width: MediaQuery.of(context).size.width,
          child: SimpleDialog(
              elevation: 0.0,
              backgroundColor: Colors.transparent,
              children: <Widget>[
                Center(
                  child: Column(children: [
                    CircularProgressIndicator(
                      color: Theme.of(context).colorScheme.secondary,
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    const Text(
                      'Loading...',
                      style: TextStyle(
                          color: Color.fromRGBO(0, 0, 0, 1),
                          fontFamily: 'Roboto',
                          fontSize: 16,
                          fontWeight: FontWeight.w700),
                    )
                  ]),
                )
              ]),
        ));
  }

  static Future<void> showLoadingDialog(BuildContext context,
      {String? label}) async {
    return showDialog<void>(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return WillPopScope(
              onWillPop: () async => false,
              child: SizedBox(
                height: MediaQuery.of(context).size.height,
                width: MediaQuery.of(context).size.width,
                child: SimpleDialog(
                    elevation: 0.0,
                    backgroundColor: Colors.transparent,
                    children: <Widget>[
                      Center(
                        child: Column(children: [
                          // CircularLoader(
                          //   color: Theme.of(context).accentColor,
                          //   size: 30.0,
//                            controller: AnimationController(vsync: this, duration: const Duration(milliseconds: 1200)),
                          // ),
                          CircularProgressIndicator(
                            color: Theme.of(context).colorScheme.secondary,
                          ),
                          const SizedBox(
                            height: 10,
                          ),
                          Text(
                            label ?? 'Loading...',
                            style: const TextStyle(
                                color: Color(0xffFFFFFF),
                                fontFamily: 'Roboto',
                                fontSize: 16,
                                fontWeight: FontWeight.w700),
                          )
                        ]),
                      )
                    ]),
              ));
        });
  }
}
