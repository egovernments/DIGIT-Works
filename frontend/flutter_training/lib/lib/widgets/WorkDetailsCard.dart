import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/atoms/button_group.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
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
  final bool orgProfile;
  final bool isSHGInbox;
  final String? cardTitle;
  final bool isTrackAttendance;
  final List<AttendanceRegister>? attendanceRegistersModel;
  final List<MusterRoll>? musterRollsModel;
  final ContractsModel? contractModel;
  final bool? showButtonLink;
  final String? linkLabel;
  final void Function()? onLinkPressed;
  final String? acceptWorkOrderCode;
  final String? musterBackToCBOCode;

  const WorkDetailsCard(this.detailsList,
      {this.isAttendanceInbox = false,
      this.isManageAttendance = false,
      this.isWorkOrderInbox = false,
      this.isTrackAttendance = false,
      this.isSHGInbox = false,
      this.viewWorkOrder = false,
      this.showButtonLink = false,
      this.linkLabel = '',
      this.onLinkPressed,
      this.elevatedButtonLabel = '',
      this.outlinedButtonLabel = '',
      this.cardTitle,
      this.attendanceRegistersModel,
      this.musterRollsModel,
      this.contractModel,
      this.orgProfile = false,
      this.acceptWorkOrderCode,
      this.musterBackToCBOCode,
      super.key});

  @override
  Widget build(BuildContext context) {
    var list = <Widget>[];
    if (isManageAttendance || isTrackAttendance) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              padding: const EdgeInsets.all(8.0),
              child: getCardDetails(context, detailsList[i],
                  attendanceRegisterId: attendanceRegistersModel![i].id,
                  attendanceRegister: attendanceRegistersModel![i])),
        ));
      }
    } else if (isWorkOrderInbox || viewWorkOrder) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              padding: const EdgeInsets.all(8.0),
              child: getCardDetails(context, detailsList[i]['cardDetails'],
                  payload: detailsList[i]['payload'],
                  isAccept: acceptWorkOrderCode != null &&
                          detailsList[i]['cardDetails']
                                  [Constants.activeInboxStatus] ==
                              'true'
                      ? false
                      : true,
                  contractNumber: detailsList[i]['cardDetails']
                      [i18.workOrder.workOrderNo])),
        ));
      }
    } else if (isSHGInbox) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              padding: const EdgeInsets.all(8.0),
              child: getCardDetails(context, detailsList[i],
                  musterRoll: musterRollsModel![i])),
        ));
      }
    } else {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              padding: const EdgeInsets.all(8.0),
              child: getCardDetails(context, detailsList[i])),
        ));
      }
    }
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return Column(
        children: list,
      );
    });
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
    if (isWorkOrderInbox && !isAccept!) {
      labelList.add(Padding(
        padding: const EdgeInsets.only(left: 4.0, bottom: 16.0, top: 8.0),
        child: Align(
          alignment: Alignment.centerLeft,
          child: SvgPicture.asset('assets/svg/new_tag.svg'),
        ),
      ));
    }
    if ((viewWorkOrder || orgProfile) && cardTitle != null) {
      labelList.add(Align(
        alignment: Alignment.centerLeft,
        child: Padding(
          padding: const EdgeInsets.only(left: 4.0, bottom: 16.0, top: 8.0),
          child: Text(
            cardTitle ?? '',
            style: DigitTheme.instance.mobileTheme.textTheme.headlineLarge
                ?.apply(color: const DigitColors().black),
            textAlign: TextAlign.left,
          ),
        ),
      ));
    }
    for (int j = 0; j < cardDetails.length; j++) {
      labelList.add(getItemWidget(
        context,
        title: AppLocalizations.of(context)
            .translate(cardDetails.keys.elementAt(j).toString()),
        description: AppLocalizations.of(context)
            .translate(cardDetails.values.elementAt(j).toString()),
        isActiveStatus: (isWorkOrderInbox || viewWorkOrder) &&
                cardDetails.keys.elementAt(j).toString() == i18.common.status &&
                cardDetails.length == j + 2
            ? true
            : !(isWorkOrderInbox || viewWorkOrder) &&
                cardDetails.keys.elementAt(j).toString() == i18.common.status &&
                cardDetails.length == j + 2 &&
                ((cardDetails.values.elementAt(j + 1) == 'true') ||
                    (cardDetails.values.elementAt(j) == Constants.active)),
        isRejectStatus: (isWorkOrderInbox || viewWorkOrder) &&
                cardDetails.keys.elementAt(j).toString() == i18.common.status
            ? false
            : !(isWorkOrderInbox || viewWorkOrder) &&
                cardDetails.keys.elementAt(j).toString() == i18.common.status &&
                cardDetails.length == j + 2 &&
                (cardDetails.values.elementAt(j + 1) == 'false'),
      ));
    }
    if (isWorkOrderInbox && !isAccept!) {
      labelList.add(Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(4.0),
            child: ButtonLink(
              AppLocalizations.of(context).translate(i18.common.viewDetails),
              () => context.router.push(ViewWorkDetailsRoute(
                  contractNumber: contractNumber.toString())),
              style: TextStyle(
                  fontWeight: FontWeight.w700,
                  fontSize: 16,
                  color: DigitTheme.instance.colorScheme.primary),
            ),
          ),
          Container(
            alignment: Alignment.centerLeft,
            padding: const EdgeInsets.all(4.0),
            child: ButtonGroup(
              outlinedButtonLabel,
              elevatedButtonLabel,
              outLinedCallBack: () => DigitDialog.show(context,
                  options: DigitDialogOptions(
                      titleIcon: const Icon(
                        Icons.warning,
                        color: Colors.red,
                      ),
                      titleText: AppLocalizations.of(context)
                          .translate(i18.common.warning),
                      contentText: AppLocalizations.of(context)
                          .translate(i18.workOrder.warningMsg),
                      primaryAction: DigitDialogActions(
                        label: AppLocalizations.of(context)
                            .translate(i18.common.confirm),
                        action: (BuildContext context) {
                          context.read<DeclineWorkOrderBloc>().add(
                                WorkOrderDeclineEvent(
                                    contractsModel: payload,
                                    action: 'DECLINE',
                                    comments:
                                        'Work Order has been declined by CBO'),
                              );
                          Navigator.of(context, rootNavigator: true).pop();
                        },
                      ),
                      secondaryAction: DigitDialogActions(
                        label: AppLocalizations.of(context)
                            .translate(i18.common.back),
                        action: (BuildContext context) =>
                            Navigator.of(context, rootNavigator: true).pop(),
                      ))),
              elevatedCallBack: () {
                context.read<AcceptWorkOrderBloc>().add(
                      WorkOrderAcceptEvent(
                          contractsModel: payload,
                          action: 'ACCEPT',
                          comments: 'Work Order has been accepted by CBO'),
                    );
              },
            ),
          ),
        ],
      ));
    } else if (isWorkOrderInbox && isAccept!) {
      labelList.add(Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(2.0),
            child: ButtonLink(
              AppLocalizations.of(context).translate(i18.common.viewDetails),
              () => context.router.push(ViewWorkDetailsRoute(
                  contractNumber: contractNumber.toString())),
              style: TextStyle(
                  fontWeight: FontWeight.w700,
                  fontSize: 16,
                  color: DigitTheme.instance.colorScheme.primary),
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(bottom: 8.0),
            child: DigitElevatedButton(
              onPressed: () => DigitActionDialog.show(context,
                  widget: Center(
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(bottom: 8.0),
                          child: DigitOutlineIconButton(
                            buttonStyle: OutlinedButton.styleFrom(
                                minimumSize: Size(
                                    MediaQuery.of(context).size.width / 2.8,
                                    50),
                                shape: const RoundedRectangleBorder(),
                                side: BorderSide(
                                    color: const DigitColors().burningOrange,
                                    width: 1)),
                            onPressed: () {
                              context.router.push(AttendanceRegisterTableRoute(
                                  registerId: payload!['additionalDetails']
                                          ['attendanceRegisterNumber']
                                      .toString(),
                                  tenantId: payload['tenantId'].toString()));
                              Navigator.of(context, rootNavigator: true).pop();
                            },
                            label: AppLocalizations.of(context)
                                .translate(i18.home.manageWageSeekers),
                            icon: Icons.fingerprint,
                            textStyle: const TextStyle(
                                fontWeight: FontWeight.w700, fontSize: 18),
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.only(bottom: 8.0),
                          child: DigitOutlineIconButton(
                            label: AppLocalizations.of(context)
                                .translate(i18.workOrder.projectClosure),
                            icon: Icons.cancel_outlined,
                            buttonStyle: OutlinedButton.styleFrom(
                                minimumSize: Size(
                                    MediaQuery.of(context).size.width / 2.8,
                                    50),
                                shape: const RoundedRectangleBorder(),
                                side: BorderSide(
                                    color: const DigitColors().burningOrange,
                                    width: 1)),
                            onPressed: () =>
                                Navigator.of(context, rootNavigator: true)
                                    .pop(),
                            textStyle: const TextStyle(
                                fontWeight: FontWeight.w700, fontSize: 18),
                          ),
                        ),
                        DigitOutlineIconButton(
                          label: AppLocalizations.of(context)
                              .translate(i18.workOrder.requestTimeExtension),
                          icon: Icons.calendar_today_rounded,
                          buttonStyle: OutlinedButton.styleFrom(
                              minimumSize: Size(
                                  MediaQuery.of(context).size.width / 2.8, 50),
                              shape: const RoundedRectangleBorder(),
                              side: BorderSide(
                                  color: const DigitColors().burningOrange,
                                  width: 1)),
                          onPressed: () {
                            Navigator.of(context, rootNavigator: true).pop();
                            context.router.push(CreateTimeExtensionRequestRoute(
                                contractNumber: contractNumber.toString()));
                          },
                          textStyle: const TextStyle(
                              fontWeight: FontWeight.w700, fontSize: 18),
                        )
                      ],
                    ),
                  )),
              child: Center(
                child: Text(
                    AppLocalizations.of(context)
                        .translate(i18.common.takeAction),
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
              ));
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
            context.router.push(SHGInboxRoute(
                tenantId: musterRoll.tenantId.toString(),
                musterRollNo: musterRoll.musterRollNumber.toString(),
                sentBackCode: musterBackToCBOCode ?? Constants.sentBack));
          },
          child: Center(
            child: Text(
                musterRoll!.musterRollStatus == musterBackToCBOCode
                    ? AppLocalizations.of(context)
                        .translate(i18.attendanceMgmt.editMusterRoll)
                    : elevatedButtonLabel,
                style: Theme.of(context)
                    .textTheme
                    .titleMedium!
                    .apply(color: Colors.white)),
          ),
        ),
      ));
    }
    if (showButtonLink! && linkLabel!.isNotEmpty) {
      labelList.add(Padding(
        padding: const EdgeInsets.all(4.0),
        child: ButtonLink(linkLabel ?? '', onLinkPressed),
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
    return title != Constants.activeInboxStatus
        ? Container(
            padding: const EdgeInsets.all(4.0),
            child: (Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                SizedBox(
                    width: MediaQuery.of(context).size.width / 3,
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            title.trim(),
                            style: const TextStyle(
                                fontSize: 16, fontWeight: FontWeight.w700),
                            textAlign: TextAlign.start,
                          ),
                          subtitle.trim.toString() != ''
                              ? Text(
                                  subtitle.trim(),
                                  style: TextStyle(
                                      fontSize: 14,
                                      fontWeight: FontWeight.w400,
                                      color:
                                          Theme.of(context).primaryColorLight),
                                )
                              : const Text('')
                        ])),
                SizedBox(
                    width: MediaQuery.of(context).size.width / 2,
                    child: Text(
                      description.trim(),
                      style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w400,
                          color: isActiveStatus && !isRejectStatus
                              ? DigitTheme.instance.colorScheme.onSurfaceVariant
                              : isRejectStatus && !isActiveStatus
                                  ? DigitTheme.instance.colorScheme.error
                                  : DigitTheme.instance.colorScheme.onSurface),
                      textAlign: TextAlign.left,
                    ))
              ],
            )))
        : const SizedBox.shrink();
  }
}
