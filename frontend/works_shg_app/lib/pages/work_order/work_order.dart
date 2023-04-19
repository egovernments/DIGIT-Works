import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/work_orders/accept_work_order.dart';
import '../../blocs/work_orders/decline_work_order.dart';
import '../../blocs/work_orders/search_my_works.dart';
import '../../models/works/contracts_model.dart';
import '../../utils/common_methods.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/atoms/tabs_button.dart';
import '../../widgets/drawer_wrapper.dart';

class WorkOrderPage extends StatefulWidget {
  const WorkOrderPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _WorkOrderPage();
  }
}

class _WorkOrderPage extends State<WorkOrderPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  List<Map<String, dynamic>> workOrderList = [];
  List<Map<String, dynamic>> inProgressWorkOrderList = [];
  List<Map<String, dynamic>> completedWorkOrderList = [];

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
  void deactivate() {
    context.read<AcceptWorkOrderBloc>().add(
          const WorkOrderDisposeEvent(),
        );
    super.deactivate();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(Drawer(
            child:
                SideBar(module: CommonMethods.getLocaleModules()))),
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
                        .where((e) =>
                            e.wfStatus == 'APPROVED' ||
                            e.wfStatus == 'ACCEPTED')
                        .map((e) => {
                              'cardDetails': {
                                i18.workOrder.workOrderNo:
                                    e.contractNumber ?? 'NA',
                                i18.attendanceMgmt.projectDesc:
                                    e.additionalDetails?.projectDesc ?? 'NA',
                                i18.workOrder.roleOfCBO:
                                    AppLocalizations.of(context).translate(
                                        e.executingAuthority ?? 'NA'),
                                i18.attendanceMgmt.engineerInCharge: e
                                        .additionalDetails
                                        ?.officerInChargeName
                                        ?.name ??
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
                                    '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
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
                                      '${AppLocalizations.of(context).translate(i18.home.myWorks)} (${workOrderList.length})',
                                      style: Theme.of(context)
                                          .textTheme
                                          .displayMedium,
                                      textAlign: TextAlign.left,
                                    ),
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      TabButton(
                                        t.translate(i18.common.inProgress),
                                        isMainTab: true,
                                        isSelected: inProgress,
                                        onPressed: null,
                                      ),
                                      TabButton(
                                        t.translate(i18.common.completed),
                                        isMainTab: true,
                                        isSelected: !inProgress,
                                        onPressed: null,
                                      )
                                    ],
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
                            BlocListener<DeclineWorkOrderBloc,
                                DeclineWorkOrderState>(
                              listener: (context, state) {
                                state.maybeWhen(
                                    initial: () => Container(),
                                    loading: () => hasLoaded = false,
                                    error: (String? error) {
                                      Notifiers.getToastMessage(
                                          context, error.toString(), 'ERROR');
                                    },
                                    loaded: (ContractsModel? declinedContract) {
                                      Notifiers.getToastMessage(
                                          context,
                                          '${declinedContract?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                                          'SUCCESS');
                                      context.router
                                          .popAndPush(const WorkOrderRoute());
                                    },
                                    orElse: () => Container());
                              },
                              child: Container(),
                            ),
                            BlocListener<AcceptWorkOrderBloc,
                                AcceptWorkOrderState>(
                              listener: (context, state) {
                                state.maybeWhen(
                                    initial: () => Container(),
                                    loading: () =>
                                        Loaders.circularLoader(context),
                                    error: (String? error) {
                                      Notifiers.getToastMessage(
                                          context, error.toString(), 'ERROR');
                                    },
                                    loaded: (ContractsModel? acceptedContract) {
                                      Notifiers.getToastMessage(
                                          context,
                                          '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${acceptedContract?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                                          'SUCCESS');
                                      context.router
                                          .popAndPush(const WorkOrderRoute());
                                    },
                                    orElse: () => false);
                              },
                              child: Container(),
                            ),
                          ]));
            }),
          ),
        ));
  }
}
