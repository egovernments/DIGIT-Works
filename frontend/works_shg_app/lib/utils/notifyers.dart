import 'package:flutter/material.dart';
import 'package:works_shg_app/utils/Toast/toaster.dart';

import '../blocs/localization/app_localization.dart';

class Notifiers {
  static getToastMessage(BuildContext context, String message, type) {
    ToastUtils.showCustomToast(
        context, AppLocalizations.of(context).translate(message), type);
  }

  static Widget networkErrorPage(context, VoidCallback callBack) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Text(
            "Unable to connect to the server",
            style: TextStyle(color: Colors.black, fontSize: 18),
          ),
          Padding(
            padding: EdgeInsets.all(12.0),
            child: ElevatedButton(
              onPressed: callBack,
              child: Text(
                'Retry',
                style: TextStyle(color: Colors.white),
              ),
            ),
          )
        ],
      ),
    );
  }
}
