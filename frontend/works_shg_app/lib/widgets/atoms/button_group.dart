import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

class ButtonGroup extends StatelessWidget {
  final String outlinedButtonLabel;
  final String elevatedButtonLabel;
  final VoidCallback? elevatedCallBack;
  final VoidCallback? outLinedCallBack;
  const ButtonGroup(this.outlinedButtonLabel, this.elevatedButtonLabel,
      {this.outLinedCallBack, this.elevatedCallBack, super.key});
  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Container(
          alignment: Alignment.centerLeft,
          width: MediaQuery.of(context).size.width > 760
              ? MediaQuery.of(context).size.width / 2
              : MediaQuery.of(context).size.width / 1.15,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: <Widget>[
              Expanded(
                child: Row(
                  children: <Widget>[
                    Expanded(
                        child: OutlinedButton(
                      onPressed: outLinedCallBack,
                      style: ButtonStyle(
                        alignment: Alignment.center,
                        padding: MaterialStateProperty.all(
                            const EdgeInsets.symmetric(vertical: 0.0)),
                        shape: MaterialStateProperty.all(RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(0.0),
                          side: BorderSide(
                              width: 2, color: Theme.of(context).primaryColor),
                        )),
                      ),
                      child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 15.0),
                          child: Text(outlinedButtonLabel,
                              style: Theme.of(context).textTheme.subtitle2)),
                    )),
                    Expanded(
                        child: DigitElevatedButton(
                      onPressed: elevatedCallBack,
                      child: Padding(
                          padding: const EdgeInsets.symmetric(vertical: 12),
                          child: Text(elevatedButtonLabel,
                              style: Theme.of(context)
                                  .textTheme
                                  .subtitle1!
                                  .apply(color: Colors.white))),
                    ))
                  ],
                ),
              )
            ],
          ),
        ),
      ],
    );
  }
}
