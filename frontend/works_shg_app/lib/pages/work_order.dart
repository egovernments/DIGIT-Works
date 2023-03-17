import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart';

import '../blocs/localization/app_localization.dart';
import '../blocs/work_orders/accept_work_order.dart';
import '../blocs/work_orders/decline_work_order.dart';
import '../blocs/work_orders/search_my_works.dart';
import '../models/works/contracts_model.dart';
import '../utils/date_formats.dart';
import '../utils/notifiers.dart';
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/drawer_wrapper.dart';

class WorkOrderPage extends StatefulWidget {
  const WorkOrderPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _WorkOrderPage();
  }
}

class _WorkOrderPage extends State<WorkOrderPage> {
  bool hasLoaded = true;
  List<Map<String, dynamic>> workOrderList = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<SearchMyWorksBloc>().add(
          const MyWorksSearchEvent(),
        );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(),
        drawer: DrawerWrapper(const Drawer(
            child:
                SideBar(module: 'rainmaker-common,rainmaker-attendencemgmt'))),
        body: SingleChildScrollView(
          child: BlocListener<SearchMyWorksBloc, SearchMyWorksState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => false,
                  loading: () => Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded: (ContractsModel? contracts) {
                    workOrderList = contracts!.contracts!
                        .where((e) => e.wfStatus == 'APPROVED')
                        .map((e) => {
                              'cardDetails': {
                                i18.workOrder.workOrderNo:
                                    e.contractNumber ?? 'NA',
                                i18.workOrder.roleOfCBO:
                                    AppLocalizations.of(context).translate(
                                        e.executingAuthority ?? 'NA'),
                                i18.attendanceMgmt.engineerInCharge:
                                    e.additionalDetails?.officerInChargeId ??
                                        'NA',
                                i18.workOrder.contractIssueDate: e.issueDate !=
                                        null
                                    ? DateFormats.timeStampToDate(e.issueDate,
                                        format: "dd/MM/yyyy")
                                    : 'NA',
                                i18.workOrder.dueDate: e.issueDate != null
                                    ? DateFormats.getFilteredDate(
                                        DateTime.fromMillisecondsSinceEpoch(
                                                e.issueDate ?? 0)
                                            .add(const Duration(days: 7))
                                            .toLocal()
                                            .toString())
                                    : 'NA',
                                i18.workOrder.contractAmount:
                                    '₹ ${e.totalContractedAmount ?? 0}',
                                i18.common.status: e.wfStatus,
                              },
                              'payload': e.toMap()
                            })
                        .toList();
                  });
            },
            child: BlocBuilder<SearchMyWorksBloc, SearchMyWorksState>(
                builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => Loaders.circularLoader(context),
                  loaded: (ContractsModel? contractsModel) => Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Back(
                              backLabel: AppLocalizations.of(context)
                                  .translate(i18.common.back),
                            ),
                            Column(
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Padding(
                                    padding: const EdgeInsets.all(16.0),
                                    child: Text(
                                      '${AppLocalizations.of(context).translate(i18.home.workOrder)} (${workOrderList.length})',
                                      style: Theme.of(context)
                                          .textTheme
                                          .displayMedium,
                                      textAlign: TextAlign.left,
                                    ),
                                  ),
                                  workOrderList.isNotEmpty
                                      ? WorkDetailsCard(
                                          workOrderList,
                                          isWorkOrderInbox: true,
                                          elevatedButtonLabel:
                                              AppLocalizations.of(context)
                                                  .translate(i18.common.accept),
                                          outlinedButtonLabel:
                                              AppLocalizations.of(context)
                                                  .translate(
                                                      i18.common.decline),
                                        )
                                      : EmptyImage(
                                          label: AppLocalizations.of(context)
                                              .translate(i18.workOrder
                                                  .noWorkOrderAssigned),
                                          align: Alignment.center,
                                        ),
                                ]),
                            BlocBuilder<DeclineWorkOrderBloc,
                                    DeclineWorkOrderState>(
                                builder: (context, state) {
                              SchedulerBinding.instance
                                  .addPostFrameCallback((_) {
                                state.maybeWhen(
                                    initial: () => Container(),
                                    loading: () => hasLoaded = false,
                                    error: (String? error) {
                                      if (!hasLoaded) {
                                        Notifiers.getToastMessage(
                                            context, error.toString(), 'ERROR');
                                        hasLoaded = true;
                                      }
                                    },
                                    loaded: (ContractsModel? contractsModel) {
                                      if (!hasLoaded) {
                                        Notifiers.getToastMessage(
                                            context,
                                            '${contractsModel?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                                            'SUCCESS');
                                        context.read<SearchMyWorksBloc>().add(
                                              const MyWorksSearchEvent(),
                                            );
                                        hasLoaded = true;
                                      }
                                    },
                                    orElse: () => Container());
                              });
                              return Container();
                            }),
                            BlocBuilder<AcceptWorkOrderBloc,
                                    AcceptWorkOrderState>(
                                builder: (context, state) {
                              SchedulerBinding.instance
                                  .addPostFrameCallback((_) {
                                state.maybeWhen(
                                    initial: () => Container(),
                                    loading: () => hasLoaded = false,
                                    error: (String? error) {
                                      if (!hasLoaded) {
                                        Notifiers.getToastMessage(
                                            context, error.toString(), 'ERROR');
                                        hasLoaded = true;
                                      }
                                    },
                                    loaded: (ContractsModel? contractsModel) {
                                      if (!hasLoaded) {
                                        Notifiers.getToastMessage(
                                            context,
                                            '${contractsModel?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${contractsModel?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                                            'SUCCESS');
                                        context.read<SearchMyWorksBloc>().add(
                                              const MyWorksSearchEvent(),
                                            );
                                        hasLoaded = true;
                                      }
                                    },
                                    orElse: () => Container());
                              });
                              return Container();
                            }),
                          ]));
            }),
          ),
        ));
  }
}
