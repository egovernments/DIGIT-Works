import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:syncfusion_flutter_datepicker/datepicker.dart';

class DateRangePicker extends StatelessWidget {
  final DateFormat? format;
  final String label;
  final String? applyLabel;
  final String? cancelLabel;
  final TextEditingController controller;
  final DateRangePickerController rangePickerController;
  final DateRangePickerSelectionMode selectionMode;
  final void Function(DateRangePickerSelectionChangedArgs)? onChangeOfDate;
  final void Function(DateRangePickerViewChangedArgs)? onViewChange;
  final void Function()? onSubmit;
  final void Function()? onCancel;
  final DateTime? minDate;
  final DateTime? maxDate;

  DateRangePicker(
      {Key? key,
      required this.label,
      required this.controller,
      required this.rangePickerController,
      required this.selectionMode,
      this.onChangeOfDate,
      this.onViewChange,
      this.onSubmit,
      this.applyLabel,
      this.cancelLabel,
      this.onCancel,
        this.minDate,
      this.maxDate,
      this.format,})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    Widget datePicker(BuildContext dialogContext) {
      return SafeArea(
        child: Column(
          children: [
            Expanded(
                child: SfDateRangePicker(
                    viewSpacing: 30,
                    onSelectionChanged: onChangeOfDate,
                    controller: rangePickerController,
                    selectionMode: selectionMode,
                    selectionRadius: 30,
                    onViewChanged: onViewChange,
                    minDate: minDate,
                    maxDate: maxDate,
                    startRangeSelectionColor:
                        DigitTheme.instance.colorScheme.secondary,
                    endRangeSelectionColor:
                        DigitTheme.instance.colorScheme.secondary,
                    selectionColor: Theme.of(context).hintColor,
                    monthViewSettings: const DateRangePickerMonthViewSettings(
                        enableSwipeSelection: false,
                        firstDayOfWeek: 1,
                        dayFormat: 'EEE'))),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: [
                TextButton(
                  onPressed: () {
                    onCancel!();
                    Navigator.pop(dialogContext);
                  },
                  child: Text(cancelLabel ?? ''),
                ),
                DigitElevatedButton(
                  onPressed: () {
                    onSubmit!();
                    Navigator.pop(dialogContext);
                  },
                  child: Text(applyLabel ?? 'Apply'),
                ),
              ],
            ),
          ],
        ),
      );
    }

    Widget getDateRangePicker(BuildContext dialogContext) {
      return SizedBox(
          height: 250, child: Card(child: datePicker(dialogContext)));
    }

    if (MediaQuery.of(context).size.width < 760) {
      return Container(
          margin: const EdgeInsets.only(top: 5.0, bottom: 5, right: 8, left: 8),
          child: Column(children: [
            DigitTextField(
                label: label,
                readOnly: true,
                controller: controller,
                suffixIcon: IconButton(
                    onPressed: () {
                      showDialog(
                          context: context,
                          builder: (BuildContext dialogContext) {
                            return Dialog(
                                child: SizedBox(
                              height: 350,
                              child: Column(
                                mainAxisSize: MainAxisSize.min,
                                children: <Widget>[
                                  getDateRangePicker(dialogContext),
                                ],
                              ),
                            ));
                          });
                    },
                    icon: const Icon(
                      Icons.date_range,
                      size: 24,
                    ))),
          ]));
    } else {
      return Container(
          margin:
              const EdgeInsets.only(top: 20.0, bottom: 5, right: 20, left: 20),
          child: Row(children: [
            SizedBox(
                width: MediaQuery.of(context).size.width / 2,
                child: DigitTextField(
                    label: label,
                    readOnly: true,
                    controller: controller,
                    suffixIcon: IconButton(
                        onPressed: () {
                          showDialog(
                              context: context,
                              builder: (BuildContext dialogContext) {
                                return Dialog(
                                    child: SizedBox(
                                  height: 350,
                                  child: Column(
                                    children: <Widget>[
                                      getDateRangePicker(dialogContext),
                                    ],
                                  ),
                                ));
                              });
                        },
                        icon: const Icon(
                          Icons.date_range,
                        )))),
          ]));
    }
  }
}
