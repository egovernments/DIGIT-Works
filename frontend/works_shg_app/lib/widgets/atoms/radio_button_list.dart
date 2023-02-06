import 'package:flutter/material.dart';

import '../../utils/models.dart';

class DigitRadioButtonList extends StatelessWidget {
  final BuildContext context;
  final String labelText;
  final dynamic controller;
  final bool isRequired;

  final String input;
  final String prefixText;
  final List<KeyValue> options;
  final ValueChanged widget1;
  final bool? isEnabled;
  final Widget? secondaryBox;
  final String? refTextRadioBtn;

  const DigitRadioButtonList(this.context, this.labelText, this.controller,
      this.input, this.prefixText, this.isRequired, this.options, this.widget1,
      {super.key, this.isEnabled, this.secondaryBox, this.refTextRadioBtn});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      if (constraints.maxWidth > 760) {
        return Row(children: [
          Visibility(
            visible: labelText.trim().isNotEmpty,
            child: Container(
                width: MediaQuery.of(context).size.width / 3,
                padding: const EdgeInsets.only(
                    top: 5.0, bottom: 5, right: 20, left: 20),
                child: Align(
                    alignment: Alignment.centerLeft,
                    child: Row(children: <Widget>[
                      Text(labelText,
                          textAlign: TextAlign.left,
                          style: TextStyle(
                              fontWeight: FontWeight.w400,
                              fontSize: 16,
                              color: Theme.of(context).primaryColorDark)),
                      Text(isRequired ? '*' : '',
                          textAlign: TextAlign.left,
                          style: TextStyle(
                              fontWeight: FontWeight.w400,
                              fontSize: 16,
                              color: Theme.of(context).primaryColorDark)),
                    ]))),
          ),
          Container(
              width: MediaQuery.of(context).size.width / 2.5,
              padding: const EdgeInsets.only(bottom: 3, top: 18),
              child: Column(
                  children: options.map(
                (data) {
                  return RadioListTile(
                    title: Text(data.label),
                    value: data.key,
                    groupValue: controller,
                    onChanged: (isEnabled ?? true) ? widget1 : null,
                    secondary: data.key == refTextRadioBtn
                        ? SizedBox(
                            width: MediaQuery.of(context).size.width / 3.8,
                            child: secondaryBox)
                        : null,
                  );
                },
              ).toList())),
        ]);
      } else {
        return Container(
            margin: const EdgeInsets.only(top: 5.0, bottom: 5, right: 8),
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              Visibility(
                visible: labelText.trim().isNotEmpty,
                child: Container(
                    padding: const EdgeInsets.only(top: 18, bottom: 3, left: 8),
                    child: Align(
                        alignment: Alignment.centerLeft,
                        child: Row(children: <Widget>[
                          Text(
                            labelText,
                            textAlign: TextAlign.left,
                            style: TextStyle(
                                fontWeight: FontWeight.w400,
                                fontSize: 16,
                                color: Theme.of(context).primaryColorDark),
                          ),
                          Text(isRequired ? '*' : '',
                              textAlign: TextAlign.left,
                              style: TextStyle(
                                  fontWeight: FontWeight.w400,
                                  fontSize: 16,
                                  color: Theme.of(context).primaryColorDark)),
                        ]))),
              ),
              Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: options.map(
                    (data) {
                      return RadioListTile(
                        contentPadding: const EdgeInsets.all(0),
                        title: Text(data.label),
                        value: data.key,
                        groupValue: controller,
                        onChanged: (isEnabled ?? true) ? widget1 : null,
                        secondary: data.key == refTextRadioBtn
                            ? SizedBox(
                                width: MediaQuery.of(context).size.width / 2.8,
                                child: secondaryBox)
                            : null,
                      );
                    },
                  ).toList()),
            ]));
      }
    });
  }
}
