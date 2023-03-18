import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../../blocs/attendance/search_projects/search_projects.dart';
import '../../blocs/localization/app_localization.dart';
import '../../models/attendance/attendance_registry_model.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/atoms/empty_image.dart';
import '../../widgets/drawer_wrapper.dart';
import '../../widgets/loaders.dart';

class TrackAttendanceInboxPage extends StatelessWidget {
  const TrackAttendanceInboxPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(const Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: SingleChildScrollView(child: BlocBuilder<
            AttendanceProjectsSearchBloc,
            AttendanceProjectsSearchState>(builder: (context, state) {
          return state.maybeWhen(
              loading: () => Loaders.circularLoader(context),
              loaded: (AttendanceRegistersModel? attendanceRegistersModel) {
                final attendanceRegisters = List<AttendanceRegister>.from(
                    attendanceRegistersModel!.attendanceRegister!);

                attendanceRegisters.sort((a, b) =>
                    b.registerAuditDetails!.lastModifiedTime!.compareTo(
                        a.registerAuditDetails!.lastModifiedTime!.toInt()));
                final List<Map<String, dynamic>> projectList =
                    attendanceRegisters
                        .map((e) => {
                              i18.workOrder.workOrderNo: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.contractId ??
                                  'NA',
                              i18.attendanceMgmt.registerId: e.registerNumber,
                              i18.attendanceMgmt.projectId: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectId ??
                                  'NA',
                              i18.attendanceMgmt.projectId: e
                                      .attendanceRegisterAdditionalDetails
                                      ?.projectName ??
                                  'NA',
                              i18.attendanceMgmt.individualsCount:
                                  e.attendeesEntries != null
                                      ? e.attendeesEntries
                                          ?.where((att) =>
                                              att.denrollmentDate == null ||
                                              !(att.denrollmentDate! <=
                                                  e.endDate!.toInt()))
                                          .toList()
                                          .length
                                      : 0,
                              i18.common.startDate: DateFormats.timeStampToDate(
                                  e.startDate,
                                  format: "dd/MM/yyyy"),
                              i18.common.endDate: DateFormats.timeStampToDate(
                                  e.endDate,
                                  format: "dd/MM/yyyy"),
                            })
                        .toList();

                return Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Back(
                        backLabel: AppLocalizations.of(context)
                            .translate(i18.common.back),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Text(
                          '${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceRegisters)}(${attendanceRegistersModel!.attendanceRegister!.length})',
                          style: Theme.of(context).textTheme.displayMedium,
                          textAlign: TextAlign.left,
                        ),
                      ),
                      projectList.isEmpty
                          ? EmptyImage(
                              align: Alignment.center,
                              label: AppLocalizations.of(context).translate(
                                i18.attendanceMgmt.noRegistersFound,
                              ))
                          : WorkDetailsCard(
                              projectList,
                              isTrackAttendance: true,
                              elevatedButtonLabel: AppLocalizations.of(context)
                                  .translate(
                                      i18.attendanceMgmt.updateAttendance),
                              attendanceRegistersModel: attendanceRegisters,
                            )
                    ]);
              },
              error: (String? error) =>
                  Notifiers.getToastMessage(context, error.toString(), 'ERROR'),
              orElse: () => Container());
        })));
  }
}
