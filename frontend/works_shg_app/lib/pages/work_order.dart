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
    return Scaffold(
      body: SingleChildScrollView(
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        const Back(),
        Padding(
          padding: const EdgeInsets.all(16.0),
          child: Text(
            '${AppLocalizations.of(context).translate(i18.home.workOrder)} (${fakeWorkOrderDetails.length})',
            style: Theme.of(context).textTheme.displayMedium,
            textAlign: TextAlign.left,
          ),
        ),
        WorkDetailsCard(
          fakeWorkOrderDetails,
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
