import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_individual_muster_roll.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/atoms/button_group.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/muster_rolls/muster_roll_estimate.dart';
import '../blocs/work_orders/accept_work_order.dart';
import '../blocs/work_orders/decline_work_order.dart';
import '../models/attendance/attendance_registry_model.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../models/works/contracts_model.dart';
import '../utils/constants.dart';

class WorkDetailsCard extends StatelessWidget {
  final List<Map<String, dynamic>> detailsList;
  final String outlinedButtonLabel;
  final String elevatedButtonLabel;
  final bool isAttendanceInbox;
  final bool isManageAttendance;
  final bool isWorkOrderInbox;
  final bool viewWorkOrder;
  final bool isSHGInbox;
  final String? cardTitle;
  final bool isTrackAttendance;
  final List<AttendanceRegister>? attendanceRegistersModel;
  final List<MusterRoll>? musterRollsModel;
  final ContractsModel? contractModel;

  const WorkDetailsCard(this.detailsList,
      {this.isAttendanceInbox = false,
      this.isManageAttendance = false,
      this.isWorkOrderInbox = false,
      this.isTrackAttendance = false,
      this.isSHGInbox = false,
      this.viewWorkOrder = false,
      this.elevatedButtonLabel = '',
      this.outlinedButtonLabel = '',
      this.cardTitle,
      this.attendanceRegistersModel,
      this.musterRollsModel,
      this.contractModel,
      super.key});

  @override
  Widget build(BuildContext context) {
    var list = <Widget>[];
    if (isManageAttendance || isTrackAttendance) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              child: getCardDetails(context, detailsList[i],
                  attendanceRegisterId: attendanceRegistersModel![i].id,
                  attendanceRegister: attendanceRegistersModel![i])),
        ));
      }
    } else if (isWorkOrderInbox || viewWorkOrder) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              child: getCardDetails(context, detailsList[i]['cardDetails'],
                  payload: detailsList[i]['payload'],
                  isAccept: detailsList[i]['cardDetails'][i18.common.status] ==
                      'ACCEPTED',
                  contractNumber: detailsList[i]['cardDetails']
                      [i18.workOrder.workOrderNo])),
        ));
      }
    } else if (isSHGInbox) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              child: getCardDetails(context, detailsList[i],
                  musterRoll: musterRollsModel![i])),
        ));
      }
    } else {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(child: getCardDetails(context, detailsList[i])),
        ));
      }
    }
    return Column(
      children: list,
    );
  }

  Widget getCardDetails(BuildContext context, Map<String, dynamic> cardDetails,
      {List<String>? userList,
      AttendanceRegister? attendanceRegister,
      String? attendanceRegisterId,
      Map<String, dynamic>? payload,
      bool? isAccept,
      MusterRoll? musterRoll,
      String? contractNumber,
      String? registerNumber}) {
    var labelList = <Widget>[];
    if (viewWorkOrder && cardTitle != null) {
      labelList.add(Align(
        alignment: Alignment.centerLeft,
        child: Text(
          cardTitle ?? '',
          style: Theme.of(context).textTheme.displayMedium,
          textAlign: TextAlign.left,
        ),
      ));
    }
    for (int j = 0; j < cardDetails.length; j++) {
      labelList.add(getItemWidget(context,
          title: AppLocalizations.of(context)
              .translate(cardDetails.keys.elementAt(j).toString()),
          description:
              cardDetails.keys.elementAt(j).toString() == i18.common.status
                  ? AppLocalizations.of(context)
                      .translate(cardDetails.values.elementAt(j).toString())
                  : cardDetails.values.elementAt(j).toString(),
          isActiveStatus: cardDetails.keys.elementAt(j).toString() ==
                  i18.common.status &&
              cardDetails.values.elementAt(j).toString() != Constants.rejected,
          isRejectStatus: cardDetails.values.elementAt(j).toString() ==
              Constants.rejected));
    }
    if (isWorkOrderInbox && !isAccept!) {
      labelList.add(Column(
        children: [
          ButtonLink(
            AppLocalizations.of(context).translate(i18.common.viewDetails),
            () => context.router.push(ViewWorkDetailsRoute(
                contractNumber: contractNumber.toString())),
            style: TextStyle(
                fontWeight: FontWeight.w700,
                fontSize: 16,
                color: DigitTheme.instance.colorScheme.primary),
          ),
          Container(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                ButtonGroup(
                  outlinedButtonLabel,
                  elevatedButtonLabel,
                  outLinedCallBack: () => DigitDialog.show(
                    context,
                    title: AppLocalizations.of(context)
                        .translate(i18.common.warning),
                    content: AppLocalizations.of(context)
                        .translate(i18.workOrder.warningMsg),
                    primaryActionLabel: AppLocalizations.of(context)
                        .translate(i18.common.confirm),
                    primaryAction: () {
                      context.read<DeclineWorkOrderBloc>().add(
                            WorkOrderDeclineEvent(
                                contractsModel: payload,
                                action: 'DECLINE',
                                comments: 'DECLINE contract'),
                          );
                      Navigator.of(context, rootNavigator: true).pop();
                    },
                    secondaryActionLabel:
                        AppLocalizations.of(context).translate(i18.common.back),
                    secondaryAction: () =>
                        Navigator.of(context, rootNavigator: true).pop(),
                  ),
                  elevatedCallBack: () {
                    context.read<AcceptWorkOrderBloc>().add(
                          WorkOrderAcceptEvent(
                              contractsModel: payload,
                              action: 'ACCEPT',
                              comments: 'Accept contract'),
                        );
                  },
                ),
              ],
            ),
          ),
        ],
      ));
    } else if (isWorkOrderInbox && isAccept!) {
      labelList.add(Column(
        children: [
          ButtonLink(
            AppLocalizations.of(context).translate(i18.common.viewDetails),
            () => context.router.push(ViewWorkDetailsRoute(
                contractNumber: contractNumber.toString())),
            style: TextStyle(
                fontWeight: FontWeight.w700,
                fontSize: 16,
                color: DigitTheme.instance.colorScheme.primary),
          ),
          Padding(
            padding: const EdgeInsets.all(4.0),
            child: DigitElevatedButton(
              onPressed: () {
                context.router.push(AttendanceRegisterTableRoute(
                    registerId: payload!['additionalDetails']
                            ['attendanceRegisterNumber']
                        .toString(),
                    tenantId: payload!['tenantId'].toString()));
              },
              child: Center(
                child: Text(
                    AppLocalizations.of(context)
                        .translate(i18.home.manageWageSeekers),
                    style: Theme.of(context)
                        .textTheme
                        .titleMedium!
                        .apply(color: Colors.white)),
              ),
            ),
          ),
        ],
      ));
    } else if (isManageAttendance || isTrackAttendance) {
      labelList.add(Padding(
        padding: const EdgeInsets.all(4.0),
        child: DigitElevatedButton(
          onPressed: () {
            if (isManageAttendance) {
              context.router.push(AttendanceRegisterTableRoute(
                  registerId: attendanceRegisterId.toString(),
                  tenantId: attendanceRegister!.tenantId.toString()));
            } else {
              context.router.push(TrackAttendanceRoute(
                  id: attendanceRegisterId.toString(),
                  tenantId: attendanceRegister!.tenantId.toString(),
                  projectDetails: [cardDetails],
                  attendanceRegister: attendanceRegister));
            }
          },
          child: Center(
            child: Text(elevatedButtonLabel,
                style: Theme.of(context)
                    .textTheme
                    .titleMedium!
                    .apply(color: Colors.white)),
          ),
        ),
      ));
    } else if (isSHGInbox) {
      labelList.add(Padding(
        padding: const EdgeInsets.all(4.0),
        child: DigitElevatedButton(
          onPressed: () {
            context.read<IndividualMusterRollSearchBloc>().add(
                  SearchIndividualMusterRollEvent(
                      id: musterRoll?.id ?? '',
                      tenantId: musterRoll!.tenantId.toString()),
                );
            context.router.push(SHGInboxRoute(
                projectDetails: [cardDetails],
                tenantId: musterRoll.tenantId.toString(),
                musterRollNo: musterRoll.musterRollNumber.toString()));
            context.read<MusterRollEstimateBloc>().add(
                  ViewEstimateMusterRollEvent(
                    tenantId: musterRoll.tenantId.toString(),
                    registerId: musterRoll.registerId.toString(),
                    startDate: musterRoll.startDate ?? 0,
                    endDate: musterRoll.endDate ?? 0,
                  ),
                );
          },
          child: Center(
            child: Text(elevatedButtonLabel,
                style: Theme.of(context)
                    .textTheme
                    .titleMedium!
                    .apply(color: Colors.white)),
          ),
        ),
      ));
    }
    return Column(
      children: labelList,
    );
  }

  static getItemWidget(BuildContext context,
      {String title = '',
      String description = '',
      String subtitle = '',
      bool isActiveStatus = false,
      bool isRejectStatus = false}) {
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
            SizedBox(
                width: MediaQuery.of(context).size.width / 2,
                child: Text(
                  description,
                  style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w400,
                      color: isActiveStatus
                          ? DigitTheme.instance.colorScheme.onSurfaceVariant
                          : isRejectStatus
                              ? DigitTheme.instance.colorScheme.error
                              : DigitTheme.instance.colorScheme.onSurface),
                  textAlign: TextAlign.left,
                ))
          ],
        )));
  }
}
