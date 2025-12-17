import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

class Loaders {
  static circularLoader(BuildContext context) {
   final String msg= AppLocalizations.of(context).translate(i18.common.loading).toString();
    return PopScope(
        onPopInvoked: null,
        canPop: true,
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
                      color: Theme.of(context).colorTheme.primary.primary1,
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                     Text(
                       msg.toString().contains("_")==true?'Loading' :msg,
                      style:  TextStyle(
                          color: Theme.of(context).colorTheme.primary.primary1,
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
          return PopScope(
              onPopInvoked: null,
              canPop: true,
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
                            color: Theme.of(context).colorTheme.primary.primary1,
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
