import 'package:flutter/material.dart';
import 'package:flutter_training/utils/Toast/toaster.dart';

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
          const Text(
            "Unable to connect to the server",
            style: TextStyle(color: Colors.black, fontSize: 18),
          ),
          Padding(
            padding: const EdgeInsets.all(12.0),
            child: ElevatedButton(
              onPressed: callBack,
              child: const Text(
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
