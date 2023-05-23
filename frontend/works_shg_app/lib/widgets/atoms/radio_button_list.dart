import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';

class DigitRadioButtonList<T> extends StatelessWidget {
  final String labelText;
  final bool isRequired;
  final String formControlName;
  final String Function(T value) valueMapper;
  final List<T> options;
  final ValueChanged<T>? onValueChange;
  final bool? isEnabled;
  final Widget? secondaryWidget;
  final VisualDensity? visualDensity;
  final EdgeInsetsGeometry? contentPadding;
  final EdgeInsetsGeometry? margin;
  final EdgeInsetsGeometry? labelPadding;

  const DigitRadioButtonList({
    super.key,
    this.isEnabled,
    required this.formControlName,
    this.secondaryWidget,
    required this.valueMapper,
    this.labelText = '',
    this.isRequired = false,
    required this.options,
    this.onValueChange,
    this.visualDensity,
    this.contentPadding,
    this.margin,
    this.labelPadding,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
        margin: margin ?? const EdgeInsets.only(top: 12.0, bottom: 5, right: 8),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Visibility(
            visible: labelText.trim().isNotEmpty,
            child: Container(
                padding: labelPadding ??
                    const EdgeInsets.only(top: 18, bottom: 3, left: 8),
                child: Align(
                    alignment: Alignment.centerLeft,
                    child: Row(children: <Widget>[
                      Text(
                        labelText,
                        textAlign: TextAlign.left,
                        style:
                            DigitTheme.instance.mobileTheme.textTheme.bodyLarge,
                      ),
                      Text(isRequired ? ' *' : '',
                          textAlign: TextAlign.left,
                          style: DigitTheme
                              .instance.mobileTheme.textTheme.bodyLarge),
                    ]))),
          ),
          Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: options.map(
                (data) {
                  return ReactiveRadioListTile<T>(
                    formControlName: formControlName,
                    contentPadding: contentPadding ?? const EdgeInsets.all(0),
                    title: Text(valueMapper(data)),
                    value: data,
                    visualDensity: visualDensity ??
                        const VisualDensity(
                            horizontal: VisualDensity.minimumDensity,
                            vertical: VisualDensity.minimumDensity),
                    onChanged: (isEnabled ?? true)
                        ? (control) {
                            final value = control.value;
                            if (value == null) return;
                            onValueChange?.call(value);
                          }
                        : null,
                    secondary: secondaryWidget != null
                        ? SizedBox(
                            width: MediaQuery.of(context).size.width / 2.8,
                            child: secondaryWidget)
                        : null,
                  );
                },
              ).toList()),
        ]));
  }
}
