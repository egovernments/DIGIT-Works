import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../blocs/localization/app_localization.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../router/app_router.dart';
import '../utils/constants.dart';
import '../utils/date_formats.dart';
import '../utils/notifiers.dart';
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart';

class ViewMusterRollsPage extends StatelessWidget {
  const ViewMusterRollsPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(),
        drawer: DrawerWrapper(const Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: SingleChildScrollView(
            child: BlocListener<MusterRollSearchBloc, MusterRollSearchState>(
          listener: (context, state) {
            state.maybeWhen(
                loading: () => Loaders.circularLoader(context),
                error: (String? error) {
                  context.router.push(const HomeRoute());
                  SchedulerBinding.instance.addPostFrameCallback((_) {
                    Notifiers.getToastMessage(
                        context, t.translate(error.toString()), 'ERROR');
                  });
                },
                orElse: () => Container());
          },
          child: BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
              builder: (context, state) {
            return state.maybeWhen(
                loading: () => Loaders.circularLoader(context),
                loaded: (MusterRollsModel? musterRollsModel) {
                  final musters =
                      List<MusterRoll>.from(musterRollsModel!.musterRoll!);
                  musters.sort((a, b) => b.musterAuditDetails!.lastModifiedTime!
                      .compareTo(
                          a.musterAuditDetails!.lastModifiedTime!.toInt()));
                  final List<Map<String, dynamic>> musterList = musters
                      .map((e) => {
                            i18.attendanceMgmt.nameOfWork: e
                                    .musterAdditionalDetails
                                    ?.attendanceRegisterName ??
                                'NA',
                            i18.attendanceMgmt.winCode: e
                                    .musterAdditionalDetails
                                    ?.attendanceRegisterNo ??
                                'NA',
                            i18.attendanceMgmt.musterRollId: e.musterRollNumber,
                            i18.common.dates:
                                '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                            i18.common.status:
                                e.musterRollStatus == Constants.rejected
                                    ? e.musterRollStatus
                                    : Constants.active
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
                            '${t.translate(i18.attendanceMgmt.musterRolls)}(${musterRollsModel!.musterRoll!.length})',
                            style: Theme.of(context).textTheme.displayMedium,
                            textAlign: TextAlign.left,
                          ),
                        ),
                        musterList.isEmpty
                            ? EmptyImage(
                                label: t.translate(
                                i18.attendanceMgmt.noMusterRollsFound,
                              ))
                            : WorkDetailsCard(
                                musterList,
                                isSHGInbox: true,
                                musterRollsModel: musters,
                              )
                      ]);
                },
                orElse: () => Container());
          }),
        )));
  }
}
