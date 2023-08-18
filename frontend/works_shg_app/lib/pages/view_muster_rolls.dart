import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/localization/localization.dart';
import '../blocs/muster_rolls/muster_inbox_status_bloc.dart';
import '../models/muster_rolls/muster_roll_model.dart';
import '../utils/common_methods.dart';
import '../utils/constants.dart';
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
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<MusterRollSearchBloc>().add(
          const SearchMusterRollEvent(),
        );
    context.read<MusterInboxStatusBloc>().add(
          const MusterInboxStatusEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return Scaffold(
          appBar: AppBar(
            titleSpacing: 0,
            title: const AppBarLogo(),
          ),
          drawer: DrawerWrapper(Drawer(
              child: SideBar(
            module: CommonMethods.getLocaleModules(),
          ))),
          bottomNavigationBar:
              BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
                  builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (MusterRollsModel? musterRollsModel) {
                  return musterList.isEmpty || musterList.length == 1
                      ? const SizedBox(
                          height: 30,
                          child: Align(
                            alignment: Alignment.bottomCenter,
                            child: PoweredByDigit(),
                          ),
                        )
                      : const SizedBox.shrink();
                });
          }),
          body: SingleChildScrollView(child:
              BlocBuilder<MusterInboxStatusBloc, MusterInboxStatusState>(
                  builder: (context, searchState) {
            return searchState.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (String? sentBackToCBOCode) =>
                    BlocListener<MusterRollSearchBloc, MusterRollSearchState>(
                      listener: (context, state) {
                        state.maybeWhen(
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            loaded: (MusterRollsModel? musterRoll) {
                              if (musterRoll?.musterRoll != null) {
                                musters = List<MusterRoll>.from(
                                    musterRoll!.musterRoll!);
                                musters.sort((a, b) => b
                                    .musterAuditDetails!.lastModifiedTime!
                                    .compareTo(a
                                        .musterAuditDetails!.lastModifiedTime!
                                        .toInt()));
                                musterList = musters
                                    .map((e) => {
                                          i18.attendanceMgmt.musterRollId:
                                              e.musterRollNumber,
                                          i18.workOrder.workOrderNo: e
                                                  .musterAdditionalDetails
                                                  ?.contractId ??
                                              i18.common.noValue,
                                          i18.attendanceMgmt.projectId: e
                                                  .musterAdditionalDetails
                                                  ?.projectId ??
                                              t.translate(i18.common.noValue),
                                        i18.attendanceMgmt.projectName: e
                                            .musterAdditionalDetails
                                            ?.projectName ??
                                            i18.common.noValue,
                                          i18.attendanceMgmt.projectDesc: e
                                                  .musterAdditionalDetails
                                                  ?.projectDesc ??
                                              t.translate(i18.common.noValue),
                                          i18.attendanceMgmt.musterRollPeriod:
                                              '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                                          i18.common.status:
                                              'CBO_MUSTER_${e.musterRollStatus}',
                                          Constants.activeInboxStatus:
                                              e.musterRollStatus ==
                                                      sentBackToCBOCode
                                                  ? 'false'
                                                  : 'true'
                                        })
                                    .toList();
                              }
                            },
                            orElse: () => Container());
                      },
                      child: BlocBuilder<MusterRollSearchBloc,
                          MusterRollSearchState>(builder: (context, state) {
                        return state.maybeWhen(
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
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
                                        style: DigitTheme.instance.mobileTheme
                                            .textTheme.displayMedium
                                            ?.apply(
                                                color:
                                                    const DigitColors().black),
                                        textAlign: TextAlign.left,
                                      ),
                                    ),
                                    musterList.isEmpty
                                        ? EmptyImage(
                                            align: Alignment.center,
                                            label: t.translate(
                                              i18.attendanceMgmt
                                                  .noMusterRollsFound,
                                            ))
                                        : WorkDetailsCard(
                                            musterList,
                                            isSHGInbox: true,
                                            musterBackToCBOCode:
                                                sentBackToCBOCode,
                                            musterRollsModel: musters,
                                            elevatedButtonLabel: t.translate(
                                                i18.common.viewDetails),
                                          ),
                                    const SizedBox(
                                      height: 16.0,
                                    ),
                                    musterList.isNotEmpty &&
                                            musterList.length > 1
                                        ? const Align(
                                            alignment: Alignment.bottomCenter,
                                            child: PoweredByDigit(),
                                          )
                                        : const SizedBox.shrink()
                                  ]);
                            },
                            orElse: () => Container());
                      }),
                    ));
          })));
    });
  }
}
