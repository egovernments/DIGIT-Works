import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../blocs/attendance/search_projects.dart';
import '../blocs/localization/app_localization.dart';
import '../utils/date_formats.dart';
import '../widgets/Back.dart';
import '../widgets/loaders.dart';

class ManageAttendanceRegisterPage extends StatelessWidget {
  const ManageAttendanceRegisterPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(body: SingleChildScrollView(child: BlocBuilder<
        AttendanceProjectsSearchBloc,
        AttendanceProjectsSearchState>(builder: (context, state) {
      if (!state.loading && state.attendanceRegistersModel != null) {
        final List<Map<String, dynamic>> projectList =
            state.attendanceRegistersModel!.attendanceRegister!
                .map((e) => {
                      i18.attendanceMgmt.nameOfWork: e.name,
                      i18.attendanceMgmt.winCode: e.registerNumber,
                      i18.attendanceMgmt.engineerInCharge: e.id,
                      i18.common.dates:
                          '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                      i18.common.status: e.status
                    })
                .toList();

        return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Back(
            backLabel: AppLocalizations.of(context).translate(i18.common.back),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(
              '${AppLocalizations.of(context).translate(i18.workOrder.projects)}(${state.attendanceRegistersModel!.attendanceRegister!.length})',
              style: Theme.of(context).textTheme.displayMedium,
              textAlign: TextAlign.left,
            ),
          ),
          projectList.isEmpty
              ? const Text('No Projects Found')
              : WorkDetailsCard(
                  projectList,
                  isManageAttendance: true,
                  elevatedButtonLabel: AppLocalizations.of(context)
                      .translate(i18.attendanceMgmt.enrollWageSeeker),
                  attendanceRegistersModel: state.attendanceRegistersModel,
                )
        ]);
      } else {
        return Loaders.circularLoader(context);
        ;
      }
    })));
  }
}
