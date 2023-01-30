import 'package:flutter/material.dart';
import 'package:works_shg_app/data/fake_work_orders.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../blocs/localization/app_localization.dart';
import '../widgets/Back.dart';

class WorkOrderPage extends StatelessWidget {
  const WorkOrderPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List<Map<String, dynamic>> workOrders = fakeWorkOrderDetails
        .map((e) => {
              i18.workOrder.projects: e['Contract ID'],
              i18.attendanceMgmt.nameOfWork: e['Name of the Work'],
              i18.workOrder.contractIssueDate: e['Contract Issued Date'],
              i18.workOrder.contractAmount: e['Contract Amount'],
              i18.attendanceMgmt.engineerInCharge: e['Engineer-in-charge'],
              i18.common.status: e['Status'],
              i18.common.attachments: e['Attachments']
            })
        .toList();
    return Scaffold(
      body: SingleChildScrollView(
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Back(
          backLabel: AppLocalizations.of(context).translate(i18.common.back),
        ),
        Padding(
          padding: const EdgeInsets.all(16.0),
          child: Text(
            '${AppLocalizations.of(context).translate(i18.home.workOrder)} (${fakeWorkOrderDetails.length})',
            style: Theme.of(context).textTheme.displayMedium,
            textAlign: TextAlign.left,
          ),
        ),
        WorkDetailsCard(
          workOrders,
          isWorkOrderInbox: true,
          elevatedButtonLabel:
              AppLocalizations.of(context).translate(i18.common.accept),
          outlinedButtonLabel:
              AppLocalizations.of(context).translate(i18.common.decline),
        )
      ])),
    );
  }
}
