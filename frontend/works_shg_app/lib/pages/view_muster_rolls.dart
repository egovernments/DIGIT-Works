import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../blocs/localization/app_localization.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../utils/date_formats.dart';
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/atoms/app_bar_logo.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart' as shg_loader;

class ViewMusterRollsPage extends StatefulWidget {
  const ViewMusterRollsPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _ViewMusterRollsPage();
  }
}

class _ViewMusterRollsPage extends State<ViewMusterRollsPage> {
  List<Map<String, dynamic>> musterList = [];
  List<MusterRoll> musters = [];

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(const Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: SingleChildScrollView(
            child: BlocListener<MusterRollSearchBloc, MusterRollSearchState>(
          listener: (context, state) {
            state.maybeWhen(
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (MusterRollsModel? musterRoll) {
                  if (musterRoll?.musterRoll != null) {
                    musters = List<MusterRoll>.from(musterRoll!.musterRoll!);
                    musters.sort((a, b) =>
                        b.musterAuditDetails!.lastModifiedTime!.compareTo(
                            a.musterAuditDetails!.lastModifiedTime!.toInt()));
                    musterList = musters
                        .map((e) => {
                              i18.attendanceMgmt.musterRollId:
                                  e.musterRollNumber,
                              i18.workOrder.workOrderNo:
                                  e.musterAdditionalDetails?.contractId ?? 'NA',
                              i18.attendanceMgmt.projectId:
                                  e.musterAdditionalDetails?.projectId ?? 'NA',
                              i18.attendanceMgmt.projectDesc:
                                  e.musterAdditionalDetails?.projectName ??
                                      'NA',
                              i18.attendanceMgmt.musterRollPeriod:
                                  '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                              i18.common.status: e.musterRollStatus
                            })
                        .toList();
                  }
                },
                orElse: () => Container());
          },
          child: BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
              builder: (context, state) {
            return state.maybeWhen(
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (MusterRollsModel? musterRollsModel) {
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
                            '${t.translate(i18.attendanceMgmt.musterRolls)}(${musterList.length})',
                            style: DigitTheme
                                .instance.mobileTheme.textTheme.displayMedium
                                ?.apply(color: const DigitColors().black),
                            textAlign: TextAlign.left,
                          ),
                        ),
                        musterList.isEmpty
                            ? EmptyImage(
                                align: Alignment.center,
                                label: t.translate(
                                  i18.attendanceMgmt.noMusterRollsFound,
                                ))
                            : WorkDetailsCard(
                                musterList,
                                isSHGInbox: true,
                                musterRollsModel: musters,
                                elevatedButtonLabel:
                                    t.translate(i18.common.viewDetails),
                              )
                      ]);
                },
                orElse: () => Container());
          }),
        )));
  }
}
