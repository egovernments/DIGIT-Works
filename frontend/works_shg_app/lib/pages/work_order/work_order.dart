import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/valid_time_extension.dart';
import '../../blocs/work_orders/accept_work_order.dart';
import '../../blocs/work_orders/decline_work_order.dart';
import '../../blocs/work_orders/my_works_search_criteria.dart';
import '../../blocs/work_orders/search_my_works.dart';
import '../../models/works/contracts_model.dart';
import '../../utils/common_methods.dart';
import '../../utils/constants.dart';
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
    context.read<MyWorksSearchCriteriaBloc>().add(
          const GetMyWorksSearchCriteriaConfigEvent(),
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
        drawer: DrawerWrapper(
            Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
        bottomNavigationBar: BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return BlocBuilder<SearchMyWorksBloc, SearchMyWorksState>(
              builder: (context, state) {
            return state.maybeWhen(
                orElse: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                loaded: (ContractsModel? contractsModel) {
                  return workOrderList.isEmpty || workOrderList.length == 1
                      ? const SizedBox(
                          height: 30,
                          child: Align(
                            alignment: Alignment.bottomCenter,
                            child: PoweredByDigit(),
                          ),
                        )
                      : const SizedBox.shrink();
                });
          });
        }),
        body: SingleChildScrollView(
          child: BlocBuilder<LocalizationBloc, LocalizationState>(
              builder: (context, localState) {
            return BlocListener<MyWorksSearchCriteriaBloc,
                    MyWorksSearchCriteriaBlocState>(
                listener: (context, searchCriteriaState) {
              searchCriteriaState.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (List<String>? searchCriteria, String? acceptCode) =>
                      context.read<SearchMyWorksBloc>().add(
                            MyWorksSearchEvent(searchCriteria),
                          ),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'));
            }, child: BlocBuilder<MyWorksSearchCriteriaBloc,
                        MyWorksSearchCriteriaBlocState>(
                    builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, error.toString(), 'ERROR'),
                  loaded: (List<String>? searchCriteria, String? acceptCode) =>
                      BlocListener<SearchMyWorksBloc, SearchMyWorksState>(
                          listener: (context, state) {
                        state.maybeWhen(
                            orElse: () => false,
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            error: (String? error) => Notifiers.getToastMessage(
                                context, error.toString(), 'ERROR'),
                            loaded: (ContractsModel? contracts) {
                              workOrderList = contracts!.contracts!
                                  .map((e) => {
                                        'cardDetails': {
                                          i18.workOrder.workOrderNo:
                                              e.contractNumber ??
                                                  i18.common.noValue,
                                          i18.attendanceMgmt.projectName: e
                                                  .additionalDetails
                                                  ?.projectName ??
                                              i18.common.noValue,
                                          i18.attendanceMgmt.projectDesc: e
                                                  .additionalDetails
                                                  ?.projectDesc ??
                                              i18.common.noValue,
                                          i18.workOrder.roleOfCBO:
                                              'COMMON_MASTERS_${e.executingAuthority ?? 'NA'}',
                                          i18.attendanceMgmt.engineerInCharge: e
                                                  .additionalDetails
                                                  ?.officerInChargeName
                                                  ?.name ??
                                              t.translate(i18.common.noValue),
                                          i18.workOrder.contractIssueDate:
                                              e.issueDate != null
                                                  ? DateFormats.timeStampToDate(
                                                      e.issueDate,
                                                      format: "dd/MM/yyyy")
                                                  : i18.common.noValue,
                                          i18.workOrder.dueDate: e.issueDate !=
                                                  null
                                              ? DateFormats.getFilteredDate(DateTime
                                                      .fromMillisecondsSinceEpoch(
                                                          e.issueDate ?? 0)
                                                  .add(const Duration(days: 7))
                                                  .toLocal()
                                                  .toString())
                                              : t.translate(i18.common.noValue),
                                          i18.workOrder.workOrderAmount:
                                              '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                          i18.common.status:
                                              'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}',
                                          Constants.activeInboxStatus:
                                              e.wfStatus != acceptCode
                                                  ? 'false'
                                                  : 'true'
                                        },
                                        'payload': e.toMap()
                                      })
                                  .toList();
                            });
                      }, child: BlocBuilder<SearchMyWorksBloc,
                                  SearchMyWorksState>(
                              builder: (context, searchState) {
                        return searchState.maybeWhen(
                            orElse: () => Container(),
                            loading: () =>
                                shg_loader.Loaders.circularLoader(context),
                            loaded:
                                (ContractsModel? contractsModel) => Column(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        children: [
                                          Back(
                                            backLabel:
                                                AppLocalizations.of(context)
                                                    .translate(i18.common.back),
                                          ),
                                          Column(
                                              mainAxisAlignment:
                                                  MainAxisAlignment.start,
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.start,
                                              children: [
                                                Padding(
                                                  padding: const EdgeInsets.all(
                                                      16.0),
                                                  child: Text(
                                                    '${AppLocalizations.of(context).translate(i18.home.myWorks)} (${workOrderList.length})',
                                                    style: Theme.of(context)
                                                        .textTheme
                                                        .displayMedium,
                                                    textAlign: TextAlign.left,
                                                  ),
                                                ),
                                                Row(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment.center,
                                                  children: [
                                                    TabButton(
                                                      t.translate(i18
                                                          .common.inProgress),
                                                      isMainTab: true,
                                                      isSelected: inProgress,
                                                      onPressed: () {
                                                        setState(() {
                                                          inProgress = true;
                                                          workOrderList =
                                                              contractsModel!
                                                                  .contracts!
                                                                  .map((e) => {
                                                                        'cardDetails':
                                                                            {
                                                                          i18.workOrder.workOrderNo:
                                                                              e.contractNumber ?? t.translate(i18.common.noValue),
                                                                          i18.attendanceMgmt.projectName:
                                                                              e.additionalDetails?.projectName ?? t.translate(i18.common.noValue),
                                                                          i18.attendanceMgmt.projectDesc:
                                                                              e.additionalDetails?.projectDesc ?? t.translate(i18.common.noValue),
                                                                          i18.workOrder.roleOfCBO:
                                                                              t.translate('COMMON_MASTERS_${e.executingAuthority ?? 'NA'}'),
                                                                          i18.attendanceMgmt.engineerInCharge:
                                                                              e.additionalDetails?.officerInChargeName?.name ?? t.translate(i18.common.noValue),
                                                                          i18.workOrder
                                                                              .contractIssueDate: e.issueDate !=
                                                                                  null
                                                                              ? DateFormats.timeStampToDate(e.issueDate, format: "dd/MM/yyyy")
                                                                              : t.translate(i18.common.noValue),
                                                                          i18.workOrder
                                                                              .dueDate: e.issueDate !=
                                                                                  null
                                                                              ? DateFormats.getFilteredDate(DateTime.fromMillisecondsSinceEpoch(e.issueDate ?? 0).add(const Duration(days: 7)).toLocal().toString())
                                                                              : t.translate(i18.common.noValue),
                                                                          i18.workOrder.workOrderAmount:
                                                                              '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                                                          i18.common.status:
                                                                              t.translate('WF_WORK_ORDER_STATE_${e.wfStatus.toString()}'),
                                                                          Constants
                                                                              .activeInboxStatus: e.wfStatus ==
                                                                                  acceptCode
                                                                              ? 'true'
                                                                              : 'false'
                                                                        },
                                                                        'payload':
                                                                            e.toMap()
                                                                      })
                                                                  .toList();
                                                        });
                                                      },
                                                    ),
                                                    TabButton(
                                                      t.translate(
                                                          i18.common.completed),
                                                      isMainTab: true,
                                                      isSelected: !inProgress,
                                                      onPressed: () {
                                                        setState(() {
                                                          inProgress = false;
                                                          workOrderList = [];
                                                        });
                                                      },
                                                    )
                                                  ],
                                                ),
                                                workOrderList.isNotEmpty
                                                    ? WorkDetailsCard(
                                                        workOrderList,
                                                        isWorkOrderInbox: true,
                                                        acceptWorkOrderCode:
                                                            acceptCode,
                                                        elevatedButtonLabel:
                                                            AppLocalizations.of(
                                                                    context)
                                                                .translate(i18
                                                                    .common
                                                                    .accept),
                                                        outlinedButtonLabel:
                                                            AppLocalizations.of(
                                                                    context)
                                                                .translate(i18
                                                                    .common
                                                                    .decline),
                                                      )
                                                    : inProgress
                                                        ? EmptyImage(
                                                            label: AppLocalizations
                                                                    .of(context)
                                                                .translate(i18
                                                                    .workOrder
                                                                    .noWorkOrderAssigned),
                                                            align: Alignment
                                                                .center,
                                                          )
                                                        : EmptyImage(
                                                            label: AppLocalizations
                                                                    .of(context)
                                                                .translate(i18
                                                                    .workOrder
                                                                    .noCompletedWorkOrderFound),
                                                            align: Alignment
                                                                .center,
                                                          ),
                                                const SizedBox(
                                                  height: 16.0,
                                                ),
                                                workOrderList.isNotEmpty &&
                                                        workOrderList.length > 1
                                                    ? const Align(
                                                        alignment: Alignment
                                                            .bottomCenter,
                                                        child: PoweredByDigit(),
                                                      )
                                                    : const SizedBox.shrink()
                                              ]),
                                          BlocListener<DeclineWorkOrderBloc,
                                              DeclineWorkOrderState>(
                                            listener: (context, state) {
                                              state.maybeWhen(
                                                  initial: () => Container(),
                                                  loading: () =>
                                                      hasLoaded = false,
                                                  error: (String? error) {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        error.toString(),
                                                        'ERROR');
                                                  },
                                                  loaded: (ContractsModel?
                                                      declinedContract) {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        '${declinedContract?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                                                        'SUCCESS');
                                                    context.router.popAndPush(
                                                        const WorkOrderRoute());
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
                                                  loading: () => shg_loader
                                                          .Loaders
                                                      .circularLoader(context),
                                                  error: (String? error) {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        error.toString(),
                                                        'ERROR');
                                                  },
                                                  loaded: (ContractsModel?
                                                      acceptedContract) {
                                                    Notifiers.getToastMessage(
                                                        context,
                                                        '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${acceptedContract?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                                                        'SUCCESS');
                                                    context.router.popAndPush(
                                                        const WorkOrderRoute());
                                                  },
                                                  orElse: () => false);
                                            },
                                            child: Container(),
                                          ),
                                          BlocListener<
                                              ValidTimeExtCreationsSearchBloc,
                                              ValidTimeExtCreationsSearchState>(
                                            listener:
                                                (context, validContractState) {
                                              validContractState.maybeWhen(
                                                  orElse: () => false,
                                                  loaded: (Contracts?
                                                          contracts) =>
                                                      context.router.push(
                                                          CreateTimeExtensionRequestRoute(
                                                        contractNumber: contracts
                                                            ?.contractNumber,
                                                      )),
                                                  error: (String? error) =>
                                                      Notifiers.getToastMessage(
                                                          context,
                                                          error ?? 'ERR!',
                                                          'ERROR'));
                                            },
                                            child: const SizedBox.shrink(),
                                          ),
                                        ]));
                      })));
            }));
          }),
        ));
  }
}
