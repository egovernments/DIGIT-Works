import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';
import 'package:works_shg_app/widgets/atoms/info_card.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/work_orders/accept_work_order.dart';
import '../../blocs/work_orders/decline_work_order.dart';
import '../../blocs/work_orders/search_individual_work.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/works/contracts_model.dart';
import '../../router/app_router.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/atoms/attachments.dart';
import '../../widgets/atoms/digit_timeline.dart';
import '../../widgets/drawer_wrapper.dart';
import '../../widgets/loaders.dart' as shg_loader;

class ViewWorkDetailsPage extends StatefulWidget {
  final String? contractNumber;
  const ViewWorkDetailsPage(
      {super.key, @queryParam this.contractNumber = 'contractNumber'});
  @override
  State<StatefulWidget> createState() {
    return _ViewWorkDetailsPage();
  }
}

class _ViewWorkDetailsPage extends State<ViewWorkDetailsPage> {
  bool hasLoaded = true;
  bool inProgress = true;
  List<Map<String, dynamic>> workOrderList = [];
  List<Map<String, dynamic>> contractDetails = [];
  List<Map<String, dynamic>> workFlowDetails = [];
  List<FileStoreModel>? fileStoreList;
  List<FileStoreModel> attachedFiles = [];
  List<String> termsNCond = [];

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() async {
    workOrderList = [];
    contractDetails = [];
    workFlowDetails = [];
    fileStoreList;
    attachedFiles = [];
    context.read<SearchIndividualWorkBloc>().add(
          IndividualWorkSearchEvent(contractNumber: widget.contractNumber),
        );
    context.read<AcceptWorkOrderBloc>().add(
          const WorkOrderDisposeEvent(),
        );
    context.read<DeclineWorkOrderBloc>().add(
          const DisposeDeclineEvent(),
        );
    await Future.delayed(const Duration(seconds: 1));
  }

  @override
  void deactivate() {
    context.read<SearchIndividualWorkBloc>().add(
          const DisposeIndividualContract(),
        );
    context.read<AcceptWorkOrderBloc>().add(
          const WorkOrderDisposeEvent(),
        );
    context.read<DeclineWorkOrderBloc>().add(
          const DisposeDeclineEvent(),
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
        drawer: DrawerWrapper(const Drawer(
            child:
                SideBar(module: 'rainmaker-common,rainmaker-attendencemgmt'))),
        body: SingleChildScrollView(
          child: Column(
            children: [
              BlocConsumer<SearchIndividualWorkBloc, SearchIndividualWorkState>(
                listener: (context, state) {
                  state.maybeWhen(
                      orElse: () => false,
                      initial: () => false,
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      error: (String? error) => Notifiers.getToastMessage(
                          context, error.toString(), 'ERROR'),
                      loaded: (ContractsModel? contracts) {
                        if (contracts?.contracts != null) {
                          termsNCond = contracts!.contracts!.first
                                      .additionalDetails!.termsAndConditions !=
                                  null
                              ? contracts.contracts!.first.additionalDetails!
                                  .termsAndConditions!
                                  .where(
                                      (w) => w != null && w.description != "")
                                  .map((e) => e!.description.toString())
                                  .toList()
                              : [];
                          workOrderList = contracts.contracts!
                              .map((e) => {
                                    'cardDetails': {
                                      i18.workOrder.workOrderNo:
                                          e.contractNumber ?? 'NA',
                                      i18.attendanceMgmt.projectId:
                                          e.additionalDetails?.projectId ??
                                              'NA',
                                      i18.common.location:
                                          '${e.additionalDetails?.locality}, ${e.additionalDetails?.ward}',
                                      i18.attendanceMgmt.projectType:
                                          e.additionalDetails?.projectType ??
                                              'NA',
                                      i18.attendanceMgmt.projectName:
                                          e.additionalDetails?.projectName ??
                                              'NA',
                                      i18.attendanceMgmt.projectDesc:
                                          e.additionalDetails?.projectName ??
                                              'NA',
                                    },
                                    'payload': e.toMap()
                                  })
                              .toList();
                          contractDetails = contracts!.contracts!
                              .map((e) => {
                                    'cardDetails': {
                                      i18.workOrder.nameOfCBO:
                                          AppLocalizations.of(context)
                                              .translate(e.additionalDetails
                                                      ?.cboName ??
                                                  'NA'),
                                      i18.workOrder.roleOfCBO:
                                          AppLocalizations.of(context)
                                              .translate(
                                                  e.executingAuthority ?? 'NA'),
                                      i18.attendanceMgmt.engineerInCharge: e
                                              .additionalDetails
                                              ?.officerInChargeId ??
                                          'NA',
                                      i18.workOrder.completionPeriod:
                                          '${e.completionPeriod} ${AppLocalizations.of(context).translate(i18.common.days)}',
                                      i18.workOrder.contractAmount:
                                          '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                      i18.common.status: e.wfStatus,
                                    },
                                    'payload': e.toMap()
                                  })
                              .toList();
                          workFlowDetails = contracts!.contracts!
                              .map((e) => {
                                    'cardDetails': {
                                      i18.workOrder.contractIssueDate:
                                          e.issueDate != null
                                              ? DateFormats.timeStampToDate(
                                                  e.issueDate,
                                                  format: "dd/MM/yyyy")
                                              : 'NA',
                                      i18.workOrder.dueDate: e.issueDate != null
                                          ? DateFormats.getFilteredDate(DateTime
                                                  .fromMillisecondsSinceEpoch(
                                                      e.issueDate ?? 0)
                                              .add(const Duration(days: 7))
                                              .toLocal()
                                              .toString())
                                          : 'NA',
                                    },
                                    'payload': e.toMap()
                                  })
                              .toList();
                          // fileStoreList = ;
                          attachedFiles = contracts!.contracts!.first.documents!
                              .map((e) => FileStoreModel(
                                  name: e.documentType,
                                  fileStoreId: e.fileStore))
                              .toList();
                        }
                      });
                },
                builder: (context, searchState) {
                  return searchState.maybeWhen(
                      orElse: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      initial: () => Container(),
                      loaded: (ContractsModel? contractsModel) {
                        final contracts = contractsModel?.contracts;
                        if (contracts != null) {
                          return Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Back(
                                  backLabel: AppLocalizations.of(context)
                                      .translate(i18.common.back),
                                ),
                                Column(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Text(
                                          '${AppLocalizations.of(context).translate(i18.workOrder.workOrderDetails)}',
                                          style: Theme.of(context)
                                              .textTheme
                                              .displayMedium,
                                          textAlign: TextAlign.left,
                                        ),
                                      ),
                                      InfoCard(
                                          title: AppLocalizations.of(context)
                                              .translate(i18.common.info),
                                          description:
                                              AppLocalizations.of(context)
                                                  .translate(
                                                      i18.common.workOrderInfo),
                                          icon: Icons.info,
                                          iconColor: DigitTheme.instance
                                              .colorScheme.secondaryContainer,
                                          backGroundColor: DigitTheme.instance
                                              .colorScheme.secondaryContainer),
                                      workOrderList.isNotEmpty
                                          ? Column(
                                              children: [
                                                WorkDetailsCard(
                                                  workOrderList,
                                                  viewWorkOrder: true,
                                                  elevatedButtonLabel:
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18
                                                              .common.accept),
                                                  outlinedButtonLabel:
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18
                                                              .common.decline),
                                                ),
                                                WorkDetailsCard(
                                                  contractDetails,
                                                  viewWorkOrder: true,
                                                  cardTitle:
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18
                                                              .workOrder
                                                              .contractDetails),
                                                ),
                                                const DigitCard(
                                                    child: DigitTimeline(
                                                  timelineOptions: [],
                                                )),
                                                WorkDetailsCard(
                                                  workFlowDetails,
                                                  viewWorkOrder: true,
                                                  cardTitle:
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18
                                                              .workOrder
                                                              .timeLineDetails),
                                                ),
                                                DigitCard(
                                                    child: Attachments(
                                                  t.translate(i18.workOrder
                                                      .relevantDocuments),
                                                  attachedFiles,
                                                )),
                                                Padding(
                                                  padding:
                                                      const EdgeInsets.all(4.0),
                                                  child: ButtonLink(
                                                    t.translate(i18.common
                                                        .termsAndConditions),
                                                    () => DigitDialog.show(
                                                        context,
                                                        options:
                                                            DigitDialogOptions(
                                                                title: Text(t.translate(i18.common.termsAndConditions),
                                                                    style: Theme.of(context)
                                                                        .textTheme
                                                                        .displayMedium),
                                                                content: Column(
                                                                  mainAxisAlignment:
                                                                      MainAxisAlignment
                                                                          .start,
                                                                  children: [
                                                                    for (var i =
                                                                            0;
                                                                        i < termsNCond.length;
                                                                        i++)
                                                                      Align(
                                                                        alignment:
                                                                            Alignment.centerLeft,
                                                                        child:
                                                                            Text(
                                                                          '${i + 1}. ${termsNCond[i]}',
                                                                          style:
                                                                              const TextStyle(
                                                                            fontSize:
                                                                                16,
                                                                            fontWeight:
                                                                                FontWeight.w700,
                                                                          ),
                                                                          textAlign:
                                                                              TextAlign.start,
                                                                        ),
                                                                      )
                                                                  ],
                                                                ),
                                                                titlePadding:
                                                                    const EdgeInsets.all(
                                                                        8.0),
                                                                contentPadding:
                                                                    const EdgeInsets.all(
                                                                        8.0),
                                                                barrierDismissible:
                                                                    true,
                                                                primaryAction: DigitDialogActions(
                                                                    label: t.translate(
                                                                        i18.common.close),
                                                                    action: (context) => Navigator.of(context, rootNavigator: true).pop()),
                                                                isScrollable: true)),
                                                    align: Alignment.centerLeft,
                                                  ),
                                                ),
                                                workOrderList.isNotEmpty &&
                                                        workOrderList.first[
                                                                    'payload']
                                                                ['wfStatus'] ==
                                                            'APPROVED'
                                                    ? DigitCard(
                                                        child: Column(
                                                        children: [
                                                          DigitElevatedButton(
                                                            onPressed: () {
                                                              context
                                                                  .read<
                                                                      AcceptWorkOrderBloc>()
                                                                  .add(
                                                                    WorkOrderAcceptEvent(
                                                                        contractsModel:
                                                                            workOrderList.first[
                                                                                'payload'],
                                                                        action:
                                                                            'ACCEPT',
                                                                        comments:
                                                                            'Accept contract'),
                                                                  );
                                                            },
                                                            child: Padding(
                                                                padding: const EdgeInsets
                                                                        .symmetric(
                                                                    vertical: 0,
                                                                    horizontal:
                                                                        4.0),
                                                                child: Text(
                                                                    t.translate(i18
                                                                        .common
                                                                        .accept),
                                                                    style: TextStyle(
                                                                        fontWeight:
                                                                            FontWeight
                                                                                .w400,
                                                                        fontSize:
                                                                            16,
                                                                        color: DigitTheme
                                                                            .instance
                                                                            .colorScheme
                                                                            .onPrimary))),
                                                          ),
                                                          Center(
                                                            child: ButtonLink(
                                                              t.translate(i18
                                                                  .common
                                                                  .decline),
                                                              () => DigitDialog.show(
                                                                  context,
                                                                  options:
                                                                      DigitDialogOptions(
                                                                          title: AppLocalizations.of(context).translate(i18
                                                                              .common
                                                                              .warning),
                                                                          content: AppLocalizations.of(context).translate(i18
                                                                              .workOrder
                                                                              .warningMsg),
                                                                          primaryAction:
                                                                              DigitDialogActions(
                                                                            label:
                                                                                AppLocalizations.of(context).translate(i18.common.confirm),
                                                                            action:
                                                                                (BuildContext context) {
                                                                              context.read<DeclineWorkOrderBloc>().add(
                                                                                    WorkOrderDeclineEvent(contractsModel: workOrderList.first['payload'], action: 'DECLINE', comments: 'DECLINE contract'),
                                                                                  );
                                                                              Navigator.of(context, rootNavigator: true).pop();
                                                                            },
                                                                          ),
                                                                          secondaryAction:
                                                                              DigitDialogActions(
                                                                            label:
                                                                                AppLocalizations.of(context).translate(i18.common.back),
                                                                            action: (BuildContext context) =>
                                                                                Navigator.of(context, rootNavigator: true).pop(),
                                                                          ))),
                                                              align: Alignment
                                                                  .center,
                                                            ),
                                                          )
                                                        ],
                                                      ))
                                                    : workOrderList
                                                                .isNotEmpty &&
                                                            workOrderList.first[
                                                                        'payload']
                                                                    [
                                                                    'wfStatus'] ==
                                                                'ACCEPTED'
                                                        ? DigitCard(
                                                            child:
                                                                DigitElevatedButton(
                                                              onPressed: () {
                                                                context.router.push(AttendanceRegisterTableRoute(
                                                                    registerId: workOrderList
                                                                        .first[
                                                                            'payload']
                                                                            [
                                                                            'additionalDetails']
                                                                            [
                                                                            'attendanceRegisterNumber']
                                                                        .toString(),
                                                                    tenantId: workOrderList
                                                                        .first[
                                                                            'payload']
                                                                            [
                                                                            'tenantId']
                                                                        .toString()));
                                                              },
                                                              child: Center(
                                                                child: Text(
                                                                    t.translate(i18
                                                                        .home
                                                                        .manageWageSeekers),
                                                                    style: Theme.of(
                                                                            context)
                                                                        .textTheme
                                                                        .titleMedium!
                                                                        .apply(
                                                                            color:
                                                                                Colors.white)),
                                                              ),
                                                            ),
                                                          )
                                                        : Container()
                                              ],
                                            )
                                          : EmptyImage(
                                              label: t.translate(i18.workOrder
                                                  .noWorkOrderAssigned),
                                              align: Alignment.center,
                                            ),
                                    ]),
                              ]);
                        } else {
                          return Container();
                        }
                      });
                },
              ),
              BlocListener<DeclineWorkOrderBloc, DeclineWorkOrderState>(
                listener: (context, state) {
                  state.maybeWhen(
                      initial: () => Container(),
                      loading: () => hasLoaded = false,
                      error: (String? error) {
                        Notifiers.getToastMessage(
                            context, error.toString(), 'ERROR');
                      },
                      loaded: (ContractsModel? contractsModel) {
                        Notifiers.getToastMessage(
                            context,
                            '${contractsModel?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                            'SUCCESS');
                        Future.delayed(const Duration(seconds: 1));
                        context.router.popAndPush(ViewWorkDetailsRoute(
                            contractNumber: workOrderList.first['payload']
                                ['contractNumber']));
                      },
                      orElse: () => false);
                },
                child: Container(),
              ),
              BlocListener<AcceptWorkOrderBloc, AcceptWorkOrderState>(
                listener: (context, state) {
                  state.maybeWhen(
                      initial: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      error: (String? error) {
                        Notifiers.getToastMessage(
                            context, error.toString(), 'ERROR');
                      },
                      loaded: (ContractsModel? contractsModel) {
                        Notifiers.getToastMessage(
                            context,
                            '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${contractsModel?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                            'SUCCESS');
                        Future.delayed(const Duration(seconds: 1));
                        context.router.popAndPush(ViewWorkDetailsRoute(
                            contractNumber: workOrderList.first['payload']
                                ['contractNumber']));
                      },
                      orElse: () => false);
                },
                child: Container(),
              ),
            ],
          ),
        ));
  }
}
