import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/atoms/button_group.dart';

import '../blocs/attendance/create_attendance_register.dart';
import '../blocs/localization/app_localization.dart';
import '../blocs/muster_rolls/muster_roll_estimate.dart';
import '../blocs/muster_rolls/search_muster_roll.dart';
import '../models/attendance/attendance_registry_model.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../utils/constants.dart';

class WorkDetailsCard extends StatelessWidget {
  final List<Map<String, dynamic>> detailsList;
  final String outlinedButtonLabel;
  final String elevatedButtonLabel;
  final bool isAttendanceInbox;
  final bool isManageAttendance;
  final bool isWorkOrderInbox;
  final bool isSHGInbox;
  final bool isTrackAttendance;
  final AttendanceRegistersModel? attendanceRegistersModel;
  final MusterRollsModel? musterRollsModel;

  const WorkDetailsCard(this.detailsList,
      {this.isAttendanceInbox = false,
      this.isManageAttendance = false,
      this.isWorkOrderInbox = false,
      this.isTrackAttendance = false,
      this.isSHGInbox = false,
      this.elevatedButtonLabel = '',
      this.outlinedButtonLabel = '',
      this.attendanceRegistersModel,
      this.musterRollsModel,
      super.key});

  @override
  Widget build(BuildContext context) {
    var list = <Widget>[];
    if (isManageAttendance || isTrackAttendance) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              child: getCardDetails(context, detailsList[i],
                  userList: attendanceRegistersModel!
                      .attendanceRegister![i].staffEntries!
                      .map((e) => e.userId.toString())
                      .toList(),
                  attendanceRegisterId:
                      attendanceRegistersModel!.attendanceRegister![i].id,
                  attendanceRegister:
                      attendanceRegistersModel!.attendanceRegister![i])),
        ));
      }
    } else if (isWorkOrderInbox) {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          child: DigitCard(
              child: getCardDetails(context, detailsList[i]['cardDetails'],
                  payload: detailsList[i]['payload'])),
        ));
      }
    } else {
      for (int i = 0; i < detailsList.length; i++) {
        list.add(GestureDetector(
          onTap: isSHGInbox
              ? () {
                  context.read<MusterRollSearchBloc>().add(
                        SearchIndividualMusterRollEvent(
                            id: musterRollsModel!.musterRoll![i].id ?? '',
                            tenantId: musterRollsModel!.musterRoll![i].tenantId
                                .toString()),
                      );
                  context.router.push(SHGInboxRoute(
                      projectDetails: [
                        detailsList[i],
                      ],
                      tenantId:
                          musterRollsModel!.musterRoll![i].tenantId.toString(),
                      musterRollNo: musterRollsModel!
                          .musterRoll![i].musterRollNumber
                          .toString()));
                  context.read<MusterRollEstimateBloc>().add(
                        ViewEstimateMusterRollEvent(
                          tenantId: musterRollsModel!.musterRoll![i].tenantId
                              .toString(),
                          registerId: musterRollsModel!
                              .musterRoll![i].registerId
                              .toString(),
                          startDate:
                              musterRollsModel!.musterRoll![i].startDate ?? 0,
                          endDate:
                              musterRollsModel!.musterRoll![i].endDate ?? 0,
                        ),
                      );
                }
              : null,
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
      Map<String, dynamic>? payload}) {
    var labelList = <Widget>[];
    for (int j = 0; j < cardDetails.length; j++) {
      labelList.add(getItemWidget(context,
          title: AppLocalizations.of(context)
              .translate(cardDetails.keys.elementAt(j).toString()),
          description:
              cardDetails.keys.elementAt(j).toString() == i18.common.status
                  ? AppLocalizations.of(context)
                      .translate(cardDetails.values.elementAt(j).toString())
                  : cardDetails.values.elementAt(j).toString(),
          isActiveStatus:
              cardDetails.values.elementAt(j).toString() == Constants.active,
          isRejectStatus: cardDetails.values.elementAt(j).toString() ==
              Constants.rejected));
    }
    if (isWorkOrderInbox) {
      labelList.add(Container(
        padding: const EdgeInsets.all(8.0),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            ButtonGroup(
              outlinedButtonLabel,
              elevatedButtonLabel,
              outLinedCallBack: () => DigitDialog.show(
                context,
                title:
                    AppLocalizations.of(context).translate(i18.common.warning),
                content: AppLocalizations.of(context)
                    .translate(i18.workOrder.warningMsg),
                primaryActionLabel:
                    AppLocalizations.of(context).translate(i18.common.confirm),
                primaryAction: () =>
                    Navigator.of(context, rootNavigator: true).pop(),
                secondaryActionLabel:
                    AppLocalizations.of(context).translate(i18.common.back),
                secondaryAction: () =>
                    Navigator.of(context, rootNavigator: true).pop(),
              ),
              elevatedCallBack: () {
                context.read<AttendanceRegisterCreateBloc>().add(
                      CreateAttendanceRegisterEvent(
                        tenantId: payload!['tenantId'],
                        registerNumber: "",
                        name: cardDetails.values.elementAt(1).toString(),
                        contractCreated: payload['contractCreated'],
                        contractId: cardDetails.values.elementAt(0).toString(),
                        orgName: payload['orgName'],
                        startDate: DateTime.now().millisecondsSinceEpoch,
                        endDate: DateTime.now()
                            .add(const Duration(days: 7))
                            .millisecondsSinceEpoch,
                      ),
                    );
              },
            ),
          ],
        ),
      ));
    } else if (isManageAttendance || isTrackAttendance) {
      labelList.add(Container(
        padding: const EdgeInsets.all(8.0),
        child: DigitElevatedButton(
          onPressed: () {
            if (isManageAttendance) {
              context.router.push(AttendanceRegisterTableRoute(
                  projectDetails: [cardDetails],
                  attendanceRegister: attendanceRegister,
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
          child: Padding(
              padding: const EdgeInsets.symmetric(vertical: 12),
              child: Text(elevatedButtonLabel,
                  style: Theme.of(context)
                      .textTheme
                      .titleMedium!
                      .apply(color: Colors.white))),
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
                    : MediaQuery.of(context).size.width / 3,
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
