import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/data/fake_work_orders.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../blocs/attendance/create_attendance_register.dart';
import '../blocs/localization/app_localization.dart';
import '../utils/notifiers.dart';
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';

class WorkOrderPage extends StatefulWidget {
  const WorkOrderPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _WorkOrderPage();
  }
}

class _WorkOrderPage extends State<WorkOrderPage> {
  bool hasLoaded = true;

  @override
  Widget build(BuildContext context) {
    final List<Map<String, dynamic>> workOrders = fakeWorkOrderDetails
        .map((e) => {
              'cardDetails': {
                i18.workOrder.projects: e['Contract ID'],
                i18.attendanceMgmt.nameOfWork: e['Name of the Work'],
                i18.workOrder.contractIssueDate: e['Contract Issued Date'],
                i18.workOrder.contractAmount: e['Contract Amount'],
                i18.attendanceMgmt.engineerInCharge: e['Engineer-in-charge'],
                i18.common.status: e['Status'],
                i18.common.attachments: e['Attachments'],
              },
              'payload': {
                'tenantId': e['tenantId'],
                'contractCreated': e['contractCreated'],
                'orgName': e['orgName']
              }
            })
        .toList();
    return Scaffold(
      appBar: AppBar(),
      drawer: DrawerWrapper(const Drawer(
          child: SideBar(module: 'rainmaker-common,rainmaker-attendencemgmt'))),
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
        ),
        BlocBuilder<AttendanceRegisterCreateBloc,
            AttendanceRegisterCreateState>(builder: (context, state) {
          SchedulerBinding.instance.addPostFrameCallback((_) {
            state.maybeWhen(
                initial: () => Container(),
                loading: () => hasLoaded = false,
                error: () {
                  if (!hasLoaded) {
                    Notifiers.getToastMessage(
                        context,
                        AppLocalizations.of(context).translate(
                            i18.attendanceMgmt.attendanceCreateFailed),
                        'ERROR');
                    hasLoaded = true;
                  }
                },
                loaded: () {
                  if (!hasLoaded) {
                    Notifiers.getToastMessage(
                        context,
                        AppLocalizations.of(context).translate(
                            i18.attendanceMgmt.attendanceCreateSuccess),
                        'SUCCESS');
                    hasLoaded = true;
                  }
                },
                orElse: () => Container());
          });
          return Container();
        }),
      ])),
    );
  }
}
