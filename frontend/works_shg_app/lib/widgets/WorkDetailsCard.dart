import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/widgets/atoms/button_group.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import '../blocs/attendance/create_attendence_register.dart';
import '../blocs/localization/app_localization.dart';
import '../utils/global_variables.dart';

class WorkDetailsCard extends StatelessWidget {
  final List<Map<String, dynamic>> detailsList;
  final String outlinedButtonLabel;
  final String elevatedButtonLabel;
  final bool isAttendanceInbox;
  final bool isWorkOrderInbox;
  final bool isSHGInbox;

  const WorkDetailsCard(this.detailsList,
      {this.isAttendanceInbox = false,
      this.isWorkOrderInbox = false,
      this.isSHGInbox = false,
      this.elevatedButtonLabel = '',
      this.outlinedButtonLabel = '',
      super.key});

  @override
  Widget build(BuildContext context) {
    var list = <Widget>[];
    for (int i = 0; i < detailsList.length; i++) {
      list.add(GestureDetector(
        onTap: () => isSHGInbox
            ? context.router
                .push(SHGInboxRoute(musterDetails: [detailsList[i]]))
            : null,
        child: DigitCard(child: getCardDetails(context, detailsList[i])),
      ));
    }
    return Column(
      children: list,
    );
  }

  Widget getCardDetails(
      BuildContext context, Map<String, dynamic> cardDetails) {
    var labelList = <Widget>[];
    for (int j = 0; j < cardDetails.length; j++) {
      labelList.add(getItemWidget(context,
          title: cardDetails.keys.elementAt(j).toString(),
          description: cardDetails.values.elementAt(j).toString()));
    }
    if (isWorkOrderInbox) {
      labelList.add(Container(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            BlocBuilder<AttendenceRegisterCreateBloc,
                    AttendenceRegisterCreateState>(
                builder: (context, state) => ButtonGroup(
                      outlinedButtonLabel,
                      elevatedButtonLabel,
                      outLinedCallBack: () => DigitDialog.show(
                        context,
                        title: AppLocalizations.of(context).translate(i18.common.warning),
                        content: AppLocalizations.of(context).translate(i18.workOrder.warningMsg),
                        primaryActionLabel: AppLocalizations.of(context).translate(i18.common.confirm),
                        primaryAction: () => Navigator.pop(context),
                        secondaryActionLabel: AppLocalizations.of(context).translate(i18.common.back),
                        secondaryAction: () => Navigator.pop(context),
                      ),
                      elevatedCallBack: state.loading
                          ? null
                          : () {
                              context.read<AttendenceRegisterCreateBloc>().add(
                                    CreateAttendenceRegisterEvent(
                                      tenantId:
                                          '${GlobalVariables.getTenantId()}',
                                      registerNumber: cardDetails.values
                                          .elementAt(0)
                                          .toString(),
                                      name: cardDetails.values
                                          .elementAt(1)
                                          .toString(),
                                      startDate:
                                          DateTime.now().millisecondsSinceEpoch,
                                      endDate: DateTime.now()
                                              .millisecondsSinceEpoch +
                                          110030000,
                                    ),
                                  );
                            },
                    ))
          ],
        ),
      ));
    }
    return Column(
      children: labelList,
    );
  }

  static getItemWidget(BuildContext context,
      {String title = '', String description = '', String subtitle = ''}) {
    return Container(
        padding: const EdgeInsets.all(8.0),
        child: (Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
                padding: const EdgeInsets.only(right: 16),
                width: MediaQuery.of(context).size.width / 3,
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        title,
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
            Text(
              description,
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w400),
              textAlign: TextAlign.center,
            )
          ],
        )));
  }
}
