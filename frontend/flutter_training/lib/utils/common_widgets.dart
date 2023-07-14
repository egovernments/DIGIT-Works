import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';

class CommonWidgets {
  static getItemWidget(BuildContext context,
      {String title = '', String description = '', String subtitle = ''}) {
    var t = AppLocalizations.of(context);
    return Container(
        padding: const EdgeInsets.all(8.0),
        child: (Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
                padding: const EdgeInsets.only(right: 16),
                width: MediaQuery.of(context).size.width > 720
                    ? MediaQuery.of(context).size.width / 3.5
                    : MediaQuery.of(context).size.width / 3.5,
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        t.translate(title),
                        style: const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                        textAlign: TextAlign.start,
                      ),
                      subtitle.trim.toString() != ''
                          ? Text(
                              subtitle,
                              style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.w400,
                                  color: Theme.of(context).primaryColorLight),
                            )
                          : const Text('')
                    ])),
            SizedBox(
                width: MediaQuery.of(context).size.width / 2,
                child: Text(
                  description,
                  style: const TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w400,
                  ),
                  textAlign: TextAlign.left,
                ))
          ],
        )));
  }

  static Widget downloadButton(String label, void Function()? onPressed) {
    return Padding(
      padding: const EdgeInsets.only(top: 12.0, right: 8.0),
      child: TextButton.icon(
          onPressed: onPressed,
          icon: Icon(
            Icons.download_sharp,
            color: DigitTheme.instance.colorScheme.primary,
          ),
          label: Text(
            label,
            style: TextStyle(color: DigitTheme.instance.colorScheme.primary),
          )),
    );
  }
}
