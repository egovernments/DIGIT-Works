import 'package:collection/collection.dart';
// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_action_card.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/atoms/pop_up_card.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/button_link.dart';
import 'package:works_shg_app/widgets/atoms/button_group.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/time_extension_request/valid_time_extension.dart';
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

  int getConditionValue() {
    if (isManageAttendance || isTrackAttendance) {
      return 1;
    } else if (isWorkOrderInbox || viewWorkOrder) {
      return 2;
    } else if (isSHGInbox) {
      return 3;
    } else {
      return 0;
    }
  }

  @override
  Widget build(BuildContext context) {
    int conditionValue = getConditionValue();

    switch (conditionValue) {
      case 1:
        return Column(
          children: detailsList.mapIndexed((index, e) {
            if ((showButtonLink! && linkLabel!.isNotEmpty)) {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                children: [
                  LabelValueList(
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e,
                      attendanceRegisterId: attendanceRegistersModel![index].id,
                      attendanceRegister: attendanceRegistersModel![index],
                    ),
                  ),
                  Button(
                    label: elevatedButtonLabel,
                    onPressed: () {
                      if (isManageAttendance) {
                        context.router.push(AttendanceRegisterTableRoute(
                            registerId:
                                attendanceRegistersModel![index].id.toString(),
                            tenantId: attendanceRegistersModel![index]!
                                .tenantId
                                .toString()));
                      } else {
                        context.router.push(TrackAttendanceRoute(
                          id: attendanceRegistersModel![index].id.toString(),
                          tenantId: attendanceRegistersModel![index]!
                              .tenantId
                              .toString(),
                        ));
                      }
                    },
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                    mainAxisSize: MainAxisSize.max,
                  ),
                  (showButtonLink! && linkLabel!.isNotEmpty)
                      ? Button(
                          mainAxisAlignment: MainAxisAlignment.start,
                          type: ButtonType.tertiary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          label: linkLabel ?? '',
                          onPressed: () => onLinkPressed!())
                      : const SizedBox.shrink(),
                ],
              );
            } else {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                children: [
                  LabelValueList(
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e,
                      attendanceRegisterId: attendanceRegistersModel![index].id,
                      attendanceRegister: attendanceRegistersModel![index],
                    ),
                  ),
                  Button(
                    label: elevatedButtonLabel,
                    onPressed: () {
                      if (isManageAttendance) {
                        context.router.push(AttendanceRegisterTableRoute(
                            registerId:
                                attendanceRegistersModel![index].id.toString(),
                            tenantId: attendanceRegistersModel![index]!
                                .tenantId
                                .toString()));
                      } else {
                        context.router.push(TrackAttendanceRoute(
                          id: attendanceRegistersModel![index].id.toString(),
                          tenantId: attendanceRegistersModel![index]!
                              .tenantId
                              .toString(),
                        ));
                      }
                    },
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                    mainAxisSize: MainAxisSize.max,
                  ),
                ],
              );
            }
            // return DigitCard(
            //   padding: const EdgeInsets.all(8.0),
            // child: getCardDetails(
            //   context,
            //   e,
            //   attendanceRegisterId: attendanceRegistersModel![index].id,
            //   attendanceRegister: attendanceRegistersModel![index],
            // ),
            // );
          }).toList(),
        );

      case 2:
        return Column(
          children: detailsList.mapIndexed((index, e) {
            if ((acceptWorkOrderCode != null &&
                    e['cardDetails'][Constants.activeInboxStatus] == 'true') &&
                (isWorkOrderInbox ||
                    acceptWorkOrderCode != null &&
                        e['cardDetails'][Constants.activeInboxStatus] ==
                            'true')) {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                //spacing: 0.0,
                children: [
                  isWorkOrderInbox &&
                          acceptWorkOrderCode != null &&
                          e['cardDetails'][Constants.activeInboxStatus] ==
                              'true'
                      ? Align(
                          alignment: Alignment.centerLeft,
                          child: SvgPicture.asset('assets/svg/new_tag.svg'),
                        )
                      : const Visibility(
                          visible: false,
                          maintainSize: false,
                          maintainAnimation: false,
                          maintainState: false,
                          child: Offstage(offstage: true)),
                  LabelValueList(
                    heading:
                        ((viewWorkOrder || orgProfile) && cardTitle != null)
                            ? cardTitle
                            : null,
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e['cardDetails'],
                      payload: e['payload'],
                      isAccept: acceptWorkOrderCode != null &&
                              e['cardDetails'][Constants.activeInboxStatus] ==
                                  'true'
                          ? false
                          : true,
                      contractNumber: e['cardDetails']
                          [i18.workOrder.workOrderNo],
                    ),
                  ),
                  Button(
                    label: AppLocalizations.of(context)
                        .translate(i18.common.viewDetails),
                    onPressed: () {
                      context.router.push(
                        ViewWorkDetailsRoute(
                          contractNumber: e['cardDetails']
                                  [i18.workOrder.workOrderNo]
                              .toString(),
                          wfStatus: e['payload']!['wfStatus'].toString(),
                        ),
                      );
                    },
                    type: ButtonType.tertiary,
                    size: ButtonSize.large,
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        flex: 10,
                        child: Button(
                            mainAxisSize: MainAxisSize.min,
                            label: outlinedButtonLabel,
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return Popup(
                                    onCrossTap: () {
                                      Navigator.of(context, rootNavigator: true)
                                          .pop();
                                    },
                                    type: PopUpType.simple,
                                    title: AppLocalizations.of(context)
                                        .translate(i18.common.warning),
                                    description: AppLocalizations.of(context)
                                        .translate(i18.workOrder.warningMsg),
                                    actions: [
                                      Button(
                                          label: AppLocalizations.of(context)
                                              .translate(i18.common.confirm),
                                          onPressed: () {
                                            context
                                                .read<DeclineWorkOrderBloc>()
                                                .add(
                                                  WorkOrderDeclineEvent(
                                                      contractsModel:
                                                          e['payload'],
                                                      action: 'DECLINE',
                                                      comments:
                                                          'Work Order has been declined by CBO'),
                                                );
                                            Navigator.of(context,
                                                    rootNavigator: true)
                                                .pop();
                                          },
                                          type: ButtonType.primary,
                                          size: ButtonSize.large)
                                    ],
                                  );
                                },
                              );
                            },
                            type: ButtonType.secondary,
                            size: ButtonSize.large),
                      ),
                      const Expanded(
                          flex: 1,
                          child: SizedBox(
                            width: 5,
                          )),
                      Expanded(
                        flex: 10,
                        child: Button(
                            mainAxisSize: MainAxisSize.min,
                            label: elevatedButtonLabel,
                            onPressed: () {
                              context.read<AcceptWorkOrderBloc>().add(
                                    WorkOrderAcceptEvent(
                                        contractsModel: e['payload'],
                                        action: 'ACCEPT',
                                        comments:
                                            'Work Order has been accepted by CBO'),
                                  );
                            },
                            type: ButtonType.primary,
                            size: ButtonSize.large),
                      ),
                    ],
                  ),
                ],
              );
            } else if ((isWorkOrderInbox ||
                acceptWorkOrderCode != null &&
                    e['cardDetails'][Constants.activeInboxStatus] == 'true')) {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                //spacing: 0.0,
                children: [
                  //TODO: temp comment
                  // isWorkOrderInbox &&
                  //         acceptWorkOrderCode != null &&
                  //         e['cardDetails'][Constants.activeInboxStatus] ==
                  //             'true'
                  //     ? Align(
                  //         alignment: Alignment.centerLeft,
                  //         child: SvgPicture.asset('assets/svg/new_tag.svg'),
                  //       )
                  //     : const Visibility(
                  //       visible:   false,
                  //     maintainSize: false,
                  //     maintainAnimation: false,
                  //     maintainState: false,
                  //       child: Offstage(offstage: true)),
// end

                  LabelValueList(
                    heading:
                        ((viewWorkOrder || orgProfile) && cardTitle != null)
                            ? cardTitle
                            : null,
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e['cardDetails'],
                      payload: e['payload'],
                      isAccept: acceptWorkOrderCode != null &&
                              e['cardDetails'][Constants.activeInboxStatus] ==
                                  'true'
                          ? false
                          : true,
                      contractNumber: e['cardDetails']
                          [i18.workOrder.workOrderNo],
                    ),
                  ),
                  Button(
                    label: AppLocalizations.of(context)
                        .translate(i18.common.viewDetails),
                    onPressed: () {
                      context.router.push(
                        ViewWorkDetailsRoute(
                          contractNumber: e['cardDetails']
                                  [i18.workOrder.workOrderNo]
                              .toString(),
                          wfStatus: e['payload']!['wfStatus'].toString(),
                        ),
                      );
                    },
                    type: ButtonType.tertiary,
                    size: ButtonSize.large,
                  ),
                  (isWorkOrderInbox && acceptWorkOrderCode != null) &&
                          !(e['cardDetails'][Constants.activeInboxStatus] ==
                              'true')
                      ? Button(
                          mainAxisSize: MainAxisSize.max,
                          label: AppLocalizations.of(context)
                              .translate(i18.common.takeAction),
                          onPressed: () {
                            showDialog(
                              context: context,
                              builder: (context) {
                                return ActionCard(
                                    onOutsideTap: () {
                                      Navigator.of(
                                        context,
                                        rootNavigator: true,
                                      ).popUntil(
                                        (route) => route is! PopupRoute,
                                      );
                                    },
                                    actions: [
                                      Button(
                                        label: AppLocalizations.of(context)
                                            .translate(
                                                i18.home.manageWageSeekers),
                                        onPressed: () {
                                          context.router.push(
                                              AttendanceRegisterTableRoute(
                                                  registerId: e['payload']![
                                                              'additionalDetails']
                                                          [
                                                          'attendanceRegisterNumber']
                                                      .toString(),
                                                  tenantId: e['payload']
                                                          ['tenantId']
                                                      .toString()));
                                          Navigator.of(context,
                                                  rootNavigator: true)
                                              .pop();
                                        },
                                        type: ButtonType.secondary,
                                        size: ButtonSize.large,
                                        mainAxisSize: MainAxisSize.max,
                                        prefixIcon: Icons.fingerprint,
                                      ),
                                      Button(
                                        label: AppLocalizations.of(context)
                                            .translate(i18.workOrder
                                                .requestTimeExtension),
                                        onPressed: () {
                                          Navigator.of(context,
                                                  rootNavigator: true)
                                              .pop();
                                          context
                                              .read<
                                                  ValidTimeExtCreationsSearchBloc>()
                                              .add(SearchValidTimeExtCreationsEvent(
                                                  contract:
                                                      ContractsMapper.fromMap(
                                                          e['payload'] ?? {}),
                                                  contractNo: e['cardDetails'][
                                                          i18.workOrder
                                                              .workOrderNo]
                                                      .toString(),
                                                  tenantId:
                                                      e['payload']!['tenantId']
                                                          .toString(),
                                                  status: 'APPROVED'));
                                        },
                                        type: ButtonType.secondary,
                                        size: ButtonSize.large,
                                        mainAxisSize: MainAxisSize.max,
                                        prefixIcon:
                                            Icons.calendar_today_rounded,
                                      )
                                    ]);
                              },
                            );
                          },
                          type: ButtonType.primary,
                          size: ButtonSize.large,
                        )
                      : const SizedBox.shrink(),
                ],
              );
            } else if ((isWorkOrderInbox && acceptWorkOrderCode != null) &&
                !(e['cardDetails'][Constants.activeInboxStatus] == 'true')) {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                //spacing: 0.0,
                children: [
                  isWorkOrderInbox &&
                          acceptWorkOrderCode != null &&
                          e['cardDetails'][Constants.activeInboxStatus] ==
                              'true'
                      ? Align(
                          alignment: Alignment.centerLeft,
                          child: SvgPicture.asset('assets/svg/new_tag.svg'),
                        )
                      : const Visibility(
                          visible: false,
                          maintainSize: false,
                          maintainAnimation: false,
                          maintainState: false,
                          child: Offstage(offstage: true)),
                  LabelValueList(
                    heading:
                        ((viewWorkOrder || orgProfile) && cardTitle != null)
                            ? cardTitle
                            : null,
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e['cardDetails'],
                      payload: e['payload'],
                      isAccept: acceptWorkOrderCode != null &&
                              e['cardDetails'][Constants.activeInboxStatus] ==
                                  'true'
                          ? false
                          : true,
                      contractNumber: e['cardDetails']
                          [i18.workOrder.workOrderNo],
                    ),
                  ),
                  Button(
                    mainAxisSize: MainAxisSize.max,
                    label: AppLocalizations.of(context)
                        .translate(i18.common.takeAction),
                    onPressed: () {
                      showDialog(
                        context: context,
                        builder: (context) {
                          return ActionCard(actions: [
                            Button(
                              label: AppLocalizations.of(context)
                                  .translate(i18.home.manageWageSeekers),
                              onPressed: () {
                                context.router.push(
                                    AttendanceRegisterTableRoute(
                                        registerId:
                                            e['payload']!['additionalDetails']
                                                    ['attendanceRegisterNumber']
                                                .toString(),
                                        tenantId: e['payload']['tenantId']
                                            .toString()));
                                Navigator.of(context, rootNavigator: true)
                                    .pop();
                              },
                              type: ButtonType.secondary,
                              size: ButtonSize.large,
                              mainAxisSize: MainAxisSize.max,
                              prefixIcon: Icons.fingerprint,
                            ),
                            Button(
                              label: AppLocalizations.of(context).translate(
                                  i18.workOrder.requestTimeExtension),
                              onPressed: () {
                                Navigator.of(context, rootNavigator: true)
                                    .pop();
                                context
                                    .read<ValidTimeExtCreationsSearchBloc>()
                                    .add(SearchValidTimeExtCreationsEvent(
                                        contract: ContractsMapper.fromMap(
                                            e['payload'] ?? {}),
                                        contractNo: e['cardDetails']
                                                [i18.workOrder.workOrderNo]
                                            .toString(),
                                        tenantId: e['payload']!['tenantId']
                                            .toString(),
                                        status: 'APPROVED'));
                              },
                              type: ButtonType.secondary,
                              size: ButtonSize.large,
                              mainAxisSize: MainAxisSize.max,
                              prefixIcon: Icons.calendar_today_rounded,
                            )
                          ]);
                        },
                      );
                    },
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                  ),
                ],
              );
            } else if (acceptWorkOrderCode != null &&
                e['cardDetails'][Constants.activeInboxStatus] == 'true') {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                //spacing: 0.0,
                children: [
                  isWorkOrderInbox &&
                          acceptWorkOrderCode != null &&
                          e['cardDetails'][Constants.activeInboxStatus] ==
                              'true'
                      ? Align(
                          alignment: Alignment.centerLeft,
                          child: SvgPicture.asset('assets/svg/new_tag.svg'),
                        )
                      : const Visibility(
                          visible: false,
                          maintainSize: false,
                          maintainAnimation: false,
                          maintainState: false,
                          child: Offstage(offstage: true)),
                  LabelValueList(
                    heading:
                        ((viewWorkOrder || orgProfile) && cardTitle != null)
                            ? cardTitle
                            : null,
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e['cardDetails'],
                      payload: e['payload'],
                      isAccept: acceptWorkOrderCode != null &&
                              e['cardDetails'][Constants.activeInboxStatus] ==
                                  'true'
                          ? false
                          : true,
                      contractNumber: e['cardDetails']
                          [i18.workOrder.workOrderNo],
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        flex: 10,
                        child: Button(
                            mainAxisSize: MainAxisSize.min,
                            label: outlinedButtonLabel,
                            onPressed: () {
                              showDialog(
                                context: context,
                                builder: (context) {
                                  return Popup(
                                    onCrossTap: () {
                                      Navigator.of(context, rootNavigator: true)
                                          .pop();
                                    },
                                    type: PopUpType.simple,
                                    title: AppLocalizations.of(context)
                                        .translate(i18.common.warning),
                                    description: AppLocalizations.of(context)
                                        .translate(i18.workOrder.warningMsg),
                                    actions: [
                                      Button(
                                          label: AppLocalizations.of(context)
                                              .translate(i18.common.confirm),
                                          onPressed: () {
                                            context
                                                .read<DeclineWorkOrderBloc>()
                                                .add(
                                                  WorkOrderDeclineEvent(
                                                      contractsModel:
                                                          e['payload'],
                                                      action: 'DECLINE',
                                                      comments:
                                                          'Work Order has been declined by CBO'),
                                                );
                                            Navigator.of(context,
                                                    rootNavigator: true)
                                                .pop();
                                          },
                                          type: ButtonType.primary,
                                          size: ButtonSize.large)
                                    ],
                                  );
                                },
                              );
                            },
                            type: ButtonType.secondary,
                            size: ButtonSize.large),
                      ),
                      const Expanded(
                          flex: 1,
                          child: SizedBox(
                            width: 5,
                          )),
                      Expanded(
                        flex: 10,
                        child: Button(
                            mainAxisSize: MainAxisSize.min,
                            label: elevatedButtonLabel,
                            onPressed: () {
                              context.read<AcceptWorkOrderBloc>().add(
                                    WorkOrderAcceptEvent(
                                        contractsModel: e['payload'],
                                        action: 'ACCEPT',
                                        comments:
                                            'Work Order has been accepted by CBO'),
                                  );
                            },
                            type: ButtonType.primary,
                            size: ButtonSize.large),
                      ),
                    ],
                  ),
                ],
              );
            } else {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                //spacing: 0.0,
                children: [
                  // isWorkOrderInbox &&
                  //         acceptWorkOrderCode != null &&
                  //         e['cardDetails'][Constants.activeInboxStatus] ==
                  //             'true'
                  //     ? Align(
                  //         alignment: Alignment.centerLeft,
                  //         child: SvgPicture.asset('assets/svg/new_tag.svg'),
                  //       )
                  //     : const SizedBox.shrink(),
                  LabelValueList(
                    heading:
                        ((viewWorkOrder || orgProfile) && cardTitle != null)
                            ? cardTitle
                            : null,
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: getCardDetails(
                      context,
                      e['cardDetails'],
                      payload: e['payload'],
                      isAccept: acceptWorkOrderCode != null &&
                              e['cardDetails'][Constants.activeInboxStatus] ==
                                  'true'
                          ? false
                          : true,
                      contractNumber: e['cardDetails']
                          [i18.workOrder.workOrderNo],
                    ),
                  ),
                ],
              );
            }
          }).toList(),
        );
      case 3:
        return Column(
          children: detailsList.mapIndexed((index, e) {
            if ((isSHGInbox && (showButtonLink! && linkLabel!.isNotEmpty))) {
              return ui_card.DigitCard(
                // spacing: 0.0,
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,

                // child: getCardDetails(context, e,
                //     musterRoll: musterRollsModel![index]),
                children: [
                  LabelValueList(
                    labelFlex: 5,
                    valueFlex: 5,
                    maxLines: 3,
                    items: getCardDetails(context, e,
                        musterRoll: musterRollsModel![index]),
                  ),
                  isSHGInbox
                      ? Button(
                          type: ButtonType.primary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          onPressed: () {
                            context.router.push(SHGInboxRoute(
                                tenantId: musterRollsModel![index]
                                    .tenantId
                                    .toString(),
                                musterRollNo: musterRollsModel![index]
                                    .musterRollNumber
                                    .toString(),
                                sentBackCode:
                                    musterBackToCBOCode ?? Constants.sentBack));
                          },
                          label: musterRollsModel![index]!.musterRollStatus ==
                                  musterBackToCBOCode
                              ? AppLocalizations.of(context)
                                  .translate(i18.attendanceMgmt.editMusterRoll)
                              : elevatedButtonLabel,
                        )
                      : const SizedBox.shrink(),
                  //ButtonLink(linkLabel ?? '', onLinkPressed)

                  (showButtonLink! && linkLabel!.isNotEmpty)
                      ? Button(
                          mainAxisAlignment: MainAxisAlignment.start,
                          type: ButtonType.tertiary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          label: linkLabel ?? '',
                          onPressed: () {
                            onLinkPressed!();
                          })
                      : const SizedBox.shrink(),
                ],
              );
            } else if (isSHGInbox) {
              return ui_card.DigitCard(
                // spacing: 0.0,
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,

                // child: getCardDetails(context, e,
                //     musterRoll: musterRollsModel![index]),
                children: [
                  LabelValueList(
                    labelFlex: 5,
                    valueFlex: 5,
                    maxLines: 3,
                    items: getCardDetails(context, e,
                        musterRoll: musterRollsModel![index]),
                  ),
                  Button(
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                    mainAxisSize: MainAxisSize.max,
                    onPressed: () {
                      context.router.push(SHGInboxRoute(
                          tenantId:
                              musterRollsModel![index].tenantId.toString(),
                          musterRollNo: musterRollsModel![index]
                              .musterRollNumber
                              .toString(),
                          sentBackCode:
                              musterBackToCBOCode ?? Constants.sentBack));
                    },
                    label: musterRollsModel![index]!.musterRollStatus ==
                            musterBackToCBOCode
                        ? AppLocalizations.of(context)
                            .translate(i18.attendanceMgmt.editMusterRoll)
                        : elevatedButtonLabel,
                  )

                  //ButtonLink(linkLabel ?? '', onLinkPressed)
                ],
              );
            } else if ((showButtonLink! && linkLabel!.isNotEmpty)) {
              return ui_card.DigitCard(
                // spacing: 0.0,
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,

                // child: getCardDetails(context, e,
                //     musterRoll: musterRollsModel![index]),
                children: [
                  LabelValueList(
                    labelFlex: 5,
                    valueFlex: 5,
                    maxLines: 3,
                    items: getCardDetails(context, e,
                        musterRoll: musterRollsModel![index]),
                  ),

                  //ButtonLink(linkLabel ?? '', onLinkPressed)

                  Button(
                      mainAxisAlignment: MainAxisAlignment.start,
                      type: ButtonType.tertiary,
                      size: ButtonSize.large,
                      mainAxisSize: MainAxisSize.max,
                      label: linkLabel ?? '',
                      onPressed: () {
                        onLinkPressed!();
                      })
                ],
              );
            } else {
              return ui_card.DigitCard(
                // spacing: 0.0,
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,

                // child: getCardDetails(context, e,
                //     musterRoll: musterRollsModel![index]),
                children: [
                  LabelValueList(
                    labelFlex: 5,
                    valueFlex: 5,
                    maxLines: 3,
                    items: getCardDetails(context, e,
                        musterRoll: musterRollsModel![index]),
                  ),
                  isSHGInbox
                      ? Button(
                          type: ButtonType.primary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          onPressed: () {
                            context.router.push(SHGInboxRoute(
                                tenantId: musterRollsModel![index]
                                    .tenantId
                                    .toString(),
                                musterRollNo: musterRollsModel![index]
                                    .musterRollNumber
                                    .toString(),
                                sentBackCode:
                                    musterBackToCBOCode ?? Constants.sentBack));
                          },
                          label: musterRollsModel![index]!.musterRollStatus ==
                                  musterBackToCBOCode
                              ? AppLocalizations.of(context)
                                  .translate(i18.attendanceMgmt.editMusterRoll)
                              : elevatedButtonLabel,
                        )
                      : const SizedBox.shrink(),
                  //ButtonLink(linkLabel ?? '', onLinkPressed)

                  (showButtonLink! && linkLabel!.isNotEmpty)
                      ? Button(
                          mainAxisAlignment: MainAxisAlignment.start,
                          type: ButtonType.tertiary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          label: linkLabel ?? '',
                          onPressed: () {
                            onLinkPressed!();
                          })
                      : const SizedBox.shrink(),
                ],
              );
            }
          }).toList(),
        );

      default:
        return Column(
          children: detailsList.mapIndexed((index, e) {
            if ((showButtonLink! && linkLabel!.isNotEmpty)) {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                //padding: const EdgeInsets.all(8.0),
                cardType: CardType.primary,

                children: [
                  LabelValueList(
                      maxLines: 3,
                      labelFlex: 5,
                      valueFlex: 5,
                      items: getCardDetails(context, e)),
                  (showButtonLink! && linkLabel!.isNotEmpty)
                      ? Button(
                          mainAxisAlignment: MainAxisAlignment.start,
                          type: ButtonType.tertiary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                          label: linkLabel ?? '',
                          onPressed: () => onLinkPressed!())
                      : const SizedBox.shrink(),
                ],
              );
            } else {
              return ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                children: [
                  LabelValueList(
                      maxLines: 3,
                      labelFlex: 5,
                      valueFlex: 5,
                      items: getCardDetails(context, e)),
                  // (showButtonLink! && linkLabel!.isNotEmpty)
                  //     ? Button(
                  //         mainAxisAlignment: MainAxisAlignment.start,
                  //         type: ButtonType.tertiary,
                  //         size: ButtonSize.large,
                  //         mainAxisSize: MainAxisSize.max,
                  //         label: linkLabel ?? '',
                  //         onPressed: () => onLinkPressed!())
                  //     : const SizedBox.shrink(),
                ],
              );
            }
          }).toList(),
        );
    }
  }

  dynamic getCardDetails(BuildContext context, Map<String, dynamic> cardDetails,
      {List<String>? userList,
      AttendanceRegister? attendanceRegister,
      String? attendanceRegisterId,
      Map<String, dynamic>? payload,
      bool? isAccept,
      MusterRoll? musterRoll,
      String? contractNumber,
      String? registerNumber}) {
    var labelList = <LabelValuePair>[];
    // if (isWorkOrderInbox && !isAccept!) {
    // labelList.add(Padding(
    //   padding: const EdgeInsets.only(left: 4.0, bottom: 16.0, top: 8.0),
    //   child: Align(
    //     alignment: Alignment.centerLeft,
    //     child: SvgPicture.asset('assets/svg/new_tag.svg'),
    //   ),
    // ));
    // }
    // if ((viewWorkOrder || orgProfile) && cardTitle != null) {
    //   labelList.add(Align(
    //     alignment: Alignment.centerLeft,
    //     child: Padding(
    //       padding: const EdgeInsets.only(left: 4.0, bottom: 16.0, top: 8.0),
    //       child: Text(
    //         cardTitle ?? '',
    //         style: DigitTheme.instance.mobileTheme.textTheme.headlineLarge
    //             ?.apply(color: const DigitColors().black),
    //         textAlign: TextAlign.left,
    //       ),
    //     ),
    //   ));
    // }
    for (int j = 0; j < cardDetails.length; j++) {
      if (AppLocalizations.of(context)
              .translate(cardDetails.keys.elementAt(j).toString()) ==
          Constants.activeInboxStatus) {
        continue;
      } else {
        labelList.add(getItemWidget(
          context,
          title: AppLocalizations.of(context)
              .translate(cardDetails.keys.elementAt(j).toString()),
          description: AppLocalizations.of(context)
              .translate(cardDetails.values.elementAt(j).toString()),
          isActiveStatus: (isWorkOrderInbox || viewWorkOrder) &&
                  cardDetails.keys.elementAt(j).toString() ==
                      i18.common.status &&
                  cardDetails.length == j + 2
              ? true
              : !(isWorkOrderInbox || viewWorkOrder) &&
                  cardDetails.keys.elementAt(j).toString() ==
                      i18.common.status &&
                  cardDetails.length == j + 2 &&
                  ((cardDetails.values.elementAt(j + 1) == 'true') ||
                      (cardDetails.values.elementAt(j) == Constants.active)),
          isRejectStatus: (isWorkOrderInbox || viewWorkOrder) &&
                  cardDetails.keys.elementAt(j).toString() == i18.common.status
              ? false
              : !(isWorkOrderInbox || viewWorkOrder) &&
                  cardDetails.keys.elementAt(j).toString() ==
                      i18.common.status &&
                  cardDetails.length == j + 2 &&
                  (cardDetails.values.elementAt(j + 1) == 'false'),
        ));
      }
    }

    // if (isWorkOrderInbox && !isAccept!) {
    //   labelList.add(Column(
    //     children: [
    //       Padding(
    //         padding: const EdgeInsets.all(4.0),
    //         child: ButtonLink(
    //           AppLocalizations.of(context).translate(i18.common.viewDetails),
    // () => context.router.push(
    //   ViewWorkDetailsRoute(
    //     contractNumber: contractNumber.toString(),
    //     wfStatus: payload!['wfStatus'].toString(),
    //   ),
    // ),
    //           style: TextStyle(
    //               fontWeight: FontWeight.w700,
    //               fontSize: 16,
    //               color: DigitTheme.instance.colorScheme.primary),
    //         ),
    //       ),
    //       Container(
    //         alignment: Alignment.centerLeft,
    //         padding: const EdgeInsets.all(4.0),
    //         child: ButtonGroup(
    //           outlinedButtonLabel,
    //           elevatedButtonLabel,
    // outLinedCallBack: () => DigitDialog.show(context,
    //     options: DigitDialogOptions(
    //         titleIcon: const Icon(
    //           Icons.warning,
    //           color: Colors.red,
    //         ),
    //         titleText: AppLocalizations.of(context)
    //             .translate(i18.common.warning),
    //         contentText: AppLocalizations.of(context)
    //             .translate(i18.workOrder.warningMsg),
    //         primaryAction: DigitDialogActions(
    //           label: AppLocalizations.of(context)
    //               .translate(i18.common.confirm),
    //           action: (BuildContext context) {
    //             context.read<DeclineWorkOrderBloc>().add(
    //                   WorkOrderDeclineEvent(
    //                       contractsModel: payload,
    //                       action: 'DECLINE',
    //                       comments:
    //                           'Work Order has been declined by CBO'),
    //                 );
    //             Navigator.of(context, rootNavigator: true).pop();
    //           },
    //         ),
    //                   secondaryAction: DigitDialogActions(
    //                     label: AppLocalizations.of(context)
    //                         .translate(i18.common.back),
    //                     action: (BuildContext context) =>
    //                         Navigator.of(context, rootNavigator: true).pop(),
    //                   ))),
    //           elevatedCallBack: () {
    // context.read<AcceptWorkOrderBloc>().add(
    //       WorkOrderAcceptEvent(
    //           contractsModel: payload,
    //           action: 'ACCEPT',
    //           comments: 'Work Order has been accepted by CBO'),
    //     );
    //           },
    //         ),
    //       ),
    //     ],
    //   ));
    // } else if (isWorkOrderInbox && isAccept!) {
    //   labelList.add(Column(
    //     children: [
    //       Padding(
    //         padding: const EdgeInsets.all(2.0),
    //         child: ButtonLink(
    //           AppLocalizations.of(context).translate(i18.common.viewDetails),
    //           () => context.router.push(
    //             ViewWorkDetailsRoute(
    //                 contractNumber: contractNumber.toString(),
    //                 wfStatus: payload!['wfStatus'].toString()),
    //           ),
    //           style: TextStyle(
    //               fontWeight: FontWeight.w700,
    //               fontSize: 16,
    //               color: DigitTheme.instance.colorScheme.primary),
    //         ),
    //       ),
    //       Padding(
    //         padding: const EdgeInsets.only(bottom: 8.0),
    //         child: ui_component.Button(type:ButtonType.primary ,
    //         size: ButtonSize.large,
    //           onPressed: () => DigitActionDialog.show(context,
    //               widget: Center(
    //                 child: Column(
    //                   mainAxisSize: MainAxisSize.min,
    //                   children: [
    //                     Padding(
    //                       padding: const EdgeInsets.only(bottom: 8.0),
    //                       child: DigitOutlineIconButton(
    //                         buttonStyle: OutlinedButton.styleFrom(
    //                             minimumSize: Size(
    //                                 MediaQuery.of(context).size.width / 2.8,
    //                                 50),
    //                             shape: const RoundedRectangleBorder(),
    //                             side: BorderSide(
    //                                 color: const DigitColors().burningOrange,
    //                                 width: 1)),
    //                         onPressed: () {
    //                           context.router.push(AttendanceRegisterTableRoute(
    //                               registerId: payload!['additionalDetails']
    //                                       ['attendanceRegisterNumber']
    //                                   .toString(),
    //                               tenantId: payload['tenantId'].toString()));
    //                           Navigator.of(context, rootNavigator: true).pop();
    //                         },
    //                         label: AppLocalizations.of(context)
    //                             .translate(i18.home.manageWageSeekers),
    //                         icon: Icons.fingerprint,
    //                         textStyle: const TextStyle(
    //                             fontWeight: FontWeight.w700, fontSize: 18),
    //                       ),
    //                     ),
    //
    //                     DigitOutlineIconButton(
    //                       label: AppLocalizations.of(context)
    //                           .translate(i18.workOrder.requestTimeExtension),
    //                       icon: Icons.calendar_today_rounded,
    //                       buttonStyle: OutlinedButton.styleFrom(
    //                           minimumSize: Size(
    //                               MediaQuery.of(context).size.width / 2.8, 50),
    //                           shape: const RoundedRectangleBorder(),
    //                           side: BorderSide(
    //                               color: const DigitColors().burningOrange,
    //                               width: 1)),
    //                       onPressed: () {
    //                         Navigator.of(context, rootNavigator: true).pop();
    //                         context.read<ValidTimeExtCreationsSearchBloc>().add(
    //                             SearchValidTimeExtCreationsEvent(
    //                                 contract:
    //                                     ContractsMapper.fromMap(payload ?? {}),
    //                                 contractNo: contractNumber.toString(),
    //                                 tenantId: payload!['tenantId'].toString(),
    //                                 status: 'APPROVED'));
    //                       },
    //                       textStyle: const TextStyle(
    //                           fontWeight: FontWeight.w700, fontSize: 18),
    //                     )
    //                   ],
    //                 ),
    //               )),
    //           label: AppLocalizations.of(context)
    //                   .translate(i18.common.takeAction),
    //                   mainAxisSize: MainAxisSize.max,
    //         ),
    //       ),
    //     ],
    //   ));
    // }
    //
    //// TODO: attendance
    // else if (isManageAttendance || isTrackAttendance) {
    //   labelList.add(Padding(
    //     padding: const EdgeInsets.all(4.0),
    //     child: DigitElevatedButton(
    //       onPressed: () {
    // if (isManageAttendance) {
    //   context.router.push(AttendanceRegisterTableRoute(
    //       registerId: attendanceRegisterId.toString(),
    //       tenantId: attendanceRegister!.tenantId.toString()));
    // } else {
    //   context.router.push(TrackAttendanceRoute(
    //     id: attendanceRegisterId.toString(),
    //     tenantId: attendanceRegister!.tenantId.toString(),
    //   ));
    // }
    //       },
    //       child: Center(
    //         child: Text(elevatedButtonLabel,
    //             style: Theme.of(context)
    //                 .textTheme
    //                 .titleMedium!
    //                 .apply(color: Colors.white)),
    //       ),
    //     ),
    //   ));
    // }
    //
    //
    //
    // else if (isSHGInbox) {
    //   labelList.add(
    // Padding(
    //   padding: const EdgeInsets.all(4.0),
    //   child: DigitElevatedButton(
    //     onPressed: () {
    //       context.router.push(SHGInboxRoute(
    //           tenantId: musterRoll.tenantId.toString(),
    //           musterRollNo: musterRoll.musterRollNumber.toString(),
    //           sentBackCode: musterBackToCBOCode ?? Constants.sentBack));
    //     },
    //     child: Center(
    //       child: Text(
    //           musterRoll!.musterRollStatus == musterBackToCBOCode
    //               ? AppLocalizations.of(context)
    //                   .translate(i18.attendanceMgmt.editMusterRoll)
    //               : elevatedButtonLabel,
    //           style: Theme.of(context)
    //               .textTheme
    //               .titleMedium!
    //               .apply(color: Colors.white)),
    //     ),
    //   ),
    // ),
    //   );
    // }
    // if (showButtonLink! && linkLabel!.isNotEmpty) {
    //   labelList.add(Padding(
    //     padding: const EdgeInsets.all(4.0),
    //     child: ButtonLink(linkLabel ?? '', onLinkPressed),
    //   ));
    // }
    return labelList;
  }

  // static getItemWidget(BuildContext context,
  //     {String title = '',
  //     String description = '',
  //     String subtitle = '',
  //     bool isActiveStatus = false,
  //     bool isRejectStatus = false}) {
  //   return title != Constants.activeInboxStatus
  //       ? Container(
  //           padding: const EdgeInsets.all(4.0),
  //           child: Row(
  //             mainAxisAlignment: MainAxisAlignment.spaceBetween,
  //             crossAxisAlignment: CrossAxisAlignment.start,
  //             children: [
  //               SizedBox(
  //                   width: MediaQuery.of(context).size.width / 3,
  //                   child: Column(
  //                       mainAxisAlignment: MainAxisAlignment.start,
  //                       crossAxisAlignment: CrossAxisAlignment.start,
  //                       children: [
  //                         Text(
  //                           title.trim(),
  //                           style: const TextStyle(
  //                               fontSize: 16, fontWeight: FontWeight.w700),
  //                           textAlign: TextAlign.start,
  //                         ),
  //                         subtitle.trim.toString() != ''
  //                             ? Text(
  //                                 subtitle.trim(),
  //                                 style: TextStyle(
  //                                     fontSize: 14,
  //                                     fontWeight: FontWeight.w400,
  //                                     color:
  //                                         Theme.of(context).primaryColorLight),
  //                               )
  //                             : const Text('')
  //                       ])),
  //               SizedBox(
  //                   width: MediaQuery.of(context).size.width / 2,
  //                   child: Text(
  //                     description.trim(),
  //                     style: TextStyle(
  //                         fontSize: 16,
  //                         fontWeight: FontWeight.w400,
  //                         color: isActiveStatus && !isRejectStatus
  //                             ? DigitTheme.instance.colorScheme.onSurfaceVariant
  //                             : isRejectStatus && !isActiveStatus
  //                                 ? DigitTheme.instance.colorScheme.error
  //                                 : DigitTheme.instance.colorScheme.onSurface),
  //                     textAlign: TextAlign.left,
  //                   ))
  //             ],
  //           ))
  //       : const SizedBox.shrink();
  // }

  static getItemWidget(BuildContext context,
      {String title = '',
      String description = '',
      String subtitle = '',
      bool isActiveStatus = false,
      bool isRejectStatus = false}) {
    // return title != Constants.activeInboxStatus
    //     ?
    return LabelValuePair(label: title.trim(), value: description.trim());

    // Row(
    //   mainAxisAlignment: MainAxisAlignment.spaceBetween,
    //   crossAxisAlignment: CrossAxisAlignment.start,
    //   children: [
    //     SizedBox(
    //         width: MediaQuery.of(context).size.width / 3,
    //         child: Column(
    //             mainAxisAlignment: MainAxisAlignment.start,
    //             crossAxisAlignment: CrossAxisAlignment.start,
    //             children: [
    //               Text(
    //                 title.trim(),
    //                 style: const TextStyle(
    //                     fontSize: 16, fontWeight: FontWeight.w700),
    //                 textAlign: TextAlign.start,
    //               ),
    //               subtitle.trim.toString() != ''
    //                   ? Text(
    //                       subtitle.trim(),
    //                       style: TextStyle(
    //                           fontSize: 14,
    //                           fontWeight: FontWeight.w400,
    //                           color:
    //                               Theme.of(context).primaryColorLight),
    //                     )
    //                   : const Text('')
    //             ])),
    //     SizedBox(
    //         width: MediaQuery.of(context).size.width / 2,
    //         child: Text(
    //           description.trim(),
    //           style: TextStyle(
    //               fontSize: 16,
    //               fontWeight: FontWeight.w400,
    //               color: isActiveStatus && !isRejectStatus
    //                   ? DigitTheme.instance.colorScheme.onSurfaceVariant
    //                   : isRejectStatus && !isActiveStatus
    //                       ? DigitTheme.instance.colorScheme.error
    //                       : DigitTheme.instance.colorScheme.onSurface),
    //           textAlign: TextAlign.left,
    //         ))
    //   ],
    // )
    //: const SizedBox.shrink();

    //  : LabelValuePair(
    //   label: '', value: '');
  }
}
