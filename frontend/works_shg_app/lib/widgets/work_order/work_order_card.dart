// ignore_for_file: public_member_api_docs, sort_constructors_first
// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:flutter/material.dart';

class WorkOrderCard extends StatefulWidget {
  final String? headLabel;
  final Map<String, dynamic> items;
  final Widget? widget1;
  final Widget? widget2;
  const WorkOrderCard({
    super.key,
    this.headLabel,
    required this.items,
    this.widget1,
    this.widget2,
  });

  @override
  State<WorkOrderCard> createState() => _WorkOrderCardState();
}

class _WorkOrderCardState extends State<WorkOrderCard> {
  String? data;

  @override
  Widget build(BuildContext context) {
  //  data = widget.items.remove("Status");
    return DigitCard(
      margin:  EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
      cardType: CardType.primary,
      children: [
        LabelValueList(
            heading: widget.headLabel,
            maxLines: 3,
            labelFlex: 5,
            valueFlex: 5,
            items: widget.items.entries.map((entry) {
              return LabelValuePair(
                label: entry.key,
                value: entry.value,
              );
            }).toList()),
        // data != null
        //     ? Row(
        //         mainAxisAlignment: MainAxisAlignment.start,
        //         crossAxisAlignment: CrossAxisAlignment.start,
        //         children: [
        //           Expanded(
        //             flex: 6,
        //             child: Text(
        //               "Status",
        //               style: Theme.of(context).textTheme.headlineSmall,
        //               textAlign: TextAlign.start,
        //             ),
        //           ),
        //           Expanded(
        //             flex: 5,
        //             child: Text(
        //               data.toString(),
        //               style: TextStyle(
        //                 color: Theme.of(context).colorScheme.onSurfaceVariant,
        //               ),
        //             ),
        //           ),
        //         ],
        //       )
        //     : const SizedBox.shrink(),
        widget.widget1 ?? const SizedBox.shrink(),
        widget.widget2 ?? const SizedBox.shrink(),
      ],
    );
  }
}
