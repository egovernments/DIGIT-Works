import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/blocs/work_orders/work_order_pdf.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/ButtonLink.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/time_extension_request/valid_time_extension.dart';
import '../../blocs/work_orders/accept_work_order.dart';
import '../../blocs/work_orders/decline_work_order.dart';
import '../../blocs/work_orders/my_works_search_criteria.dart';
import '../../blocs/work_orders/search_individual_work.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/works/contracts_model.dart';
import '../../router/app_router.dart';
import '../../utils/common_methods.dart';
import '../../utils/common_widgets.dart';
import '../../utils/constants.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/atoms/attachments.dart';
import '../../widgets/drawer_wrapper.dart';
import '../../widgets/loaders.dart' as shg_loader;

class ViewWorkDetailsPage extends StatefulWidget {
  final String? contractNumber;
  final String? wfStatus;
  const ViewWorkDetailsPage(
      {super.key, @queryParam this.contractNumber = 'contractNumber',  @queryParam this.wfStatus = 'wfStatus'});
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
          IndividualWorkSearchEvent(contractNumber: widget.contractNumber, body:{
            "wfStatus": [widget.wfStatus]
          }),
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
    return WillPopScope(
      onWillPop: () async {
        context.router.popUntilRouteWithPath('home') ;
        context.router.push(const WorkOrderRoute());
        return false;
      },
      child: Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(
            Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
        bottomNavigationBar:
        BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
            return BlocBuilder<MyWorksSearchCriteriaBloc,
                MyWorksSearchCriteriaBlocState>(
                builder: (context, searchState) {
                  return searchState.maybeWhen(orElse: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),

                    loaded: (List<String>? searchCriteria, String? acceptCode) => BlocBuilder<SearchIndividualWorkBloc, SearchIndividualWorkState>(
                        builder: (context, workState) {
              return workState.maybeWhen(
                      orElse: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      error: (String? error) => Notifiers.getToastMessage(context, t.translate(error.toString()), 'ERROR'),
                      loaded: (ContractsModel? contracts) {
                        return workOrderList.isNotEmpty &&
                                workOrderList.first['payload']['wfStatus'] == acceptCode
                            ? SizedBox(
                                height: 100,
                                child: DigitCard(
                                    padding: const EdgeInsets.all(8.0),
                                    margin: const EdgeInsets.all(0),
                                    child: Column(
                                      children: [
                                        DigitElevatedButton(
                                          onPressed: () {
                                            context.read<AcceptWorkOrderBloc>().add(
                                                  WorkOrderAcceptEvent(
                                                      contractsModel:
                                                          workOrderList.first['payload'],
                                                      action: 'ACCEPT',
                                                      comments: 'Work Order has been accepted by CBO'),
                                                );
                                          },
                                          child: Center(
                                            child: Padding(
                                                padding: const EdgeInsets.symmetric(
                                                    vertical: 4.0, horizontal: 4.0),
                                                child:
                                                    Text(t.translate(i18.common.accept),
                                                        textAlign: TextAlign.center,
                                                        style: TextStyle(
                                                          fontWeight: FontWeight.w400,
                                                          fontSize: 16,
                                                          color: DigitTheme.instance
                                                              .colorScheme.onPrimary,
                                                        ))),
                                          ),
                                        ),
                                        Center(
                                          child: Padding(
                                            padding: const EdgeInsets.all(4.0),
                                            child: ButtonLink(
                                              t.translate(i18.common.decline),
                                              () => DigitDialog.show(context,
                                                  options: DigitDialogOptions(
                                                      titleText: AppLocalizations.of(
                                                              context)
                                                          .translate(i18.common.warning),
                                                      contentText: AppLocalizations.of(
                                                              context)
                                                          .translate(
                                                              i18.workOrder.warningMsg),
                                                      primaryAction: DigitDialogActions(
                                                        label:
                                                            AppLocalizations.of(context)
                                                                .translate(
                                                                    i18.common.confirm),
                                                        action: (BuildContext context) {
                                                          context
                                                              .read<
                                                                  DeclineWorkOrderBloc>()
                                                              .add(
                                                                WorkOrderDeclineEvent(
                                                                    contractsModel:
                                                                        workOrderList
                                                                                .first[
                                                                            'payload'],
                                                                    action: 'DECLINE',
                                                                    comments:
                                                                        'Work Order has been declined by CBO'),
                                                              );
                                                          Navigator.of(context,
                                                                  rootNavigator: true)
                                                              .pop();
                                                        },
                                                      ),
                                                      secondaryAction: DigitDialogActions(
                                                        label: AppLocalizations.of(
                                                                context)
                                                            .translate(i18.common.back),
                                                        action: (BuildContext context) =>
                                                            Navigator.of(context,
                                                                    rootNavigator: true)
                                                                .pop(),
                                                      ))),
                                              align: Alignment.center,
                                              textAlign: TextAlign.center,
                                            ),
                                          ),
                                        )
                                      ],
                                    )),
                              )
                            : workOrderList.isNotEmpty &&
                                    workOrderList.first['payload']['wfStatus'] != acceptCode
                                ? SizedBox(
                          height: 60,
                                  child: DigitCard(
                                    padding: const EdgeInsets.all(8.0),
                                    margin: const EdgeInsets.all(0),
                                    child: DigitElevatedButton(
                                      onPressed: () => DigitActionDialog.show(context,
                                            widget: Center(
                                              child: Column(
                                                mainAxisSize: MainAxisSize.min,
                                                children: [
                                                  Padding(
                                                    padding: const EdgeInsets.only(bottom: 8.0),
                                                    child: DigitOutlineIconButton(
                                                      buttonStyle: OutlinedButton.styleFrom(
                                                          minimumSize: Size(
                                                              MediaQuery.of(context).size.width / 2.8,
                                                              50),
                                                          shape: const RoundedRectangleBorder(),
                                                          side: BorderSide(
                                                              color: const DigitColors().burningOrange,
                                                              width: 1)),
                                                      onPressed: () {
                                                        context.router.push(AttendanceRegisterTableRoute(
                                                            registerId: (contracts?.contracts ?? []).first.additionalDetails?.attendanceRegisterNumber.toString() ?? ''
                                                                .toString(),
                                                            tenantId: (contracts?.contracts ?? []).first.tenantId.toString()));
                                                        Navigator.of(context, rootNavigator: true).pop();
                                                      },
                                                      label: AppLocalizations.of(context)
                                                          .translate(i18.home.manageWageSeekers),
                                                      icon: Icons.fingerprint,
                                                      textStyle: const TextStyle(
                                                          fontWeight: FontWeight.w700, fontSize: 18),
                                                    ),
                                                  ),
                                                  /*Padding(
                                                    padding: const EdgeInsets.only(bottom: 8.0),
                                                    child: DigitOutlineIconButton(
                                                      label: AppLocalizations.of(context)
                                                          .translate(i18.workOrder.projectClosure),
                                                      icon: Icons.cancel_outlined,
                                                      buttonStyle: OutlinedButton.styleFrom(
                                                          minimumSize: Size(
                                                              MediaQuery.of(context).size.width / 2.8,
                                                              50),
                                                          shape: const RoundedRectangleBorder(),
                                                          side: BorderSide(
                                                              color: const DigitColors().burningOrange,
                                                              width: 1)),
                                                      onPressed: () =>
                                                          Navigator.of(context, rootNavigator: true)
                                                              .pop(),
                                                      textStyle: const TextStyle(
                                                          fontWeight: FontWeight.w700, fontSize: 18),
                                                    ),
                                                  )*/
                                                  DigitOutlineIconButton(
                                                    label: AppLocalizations.of(context)
                                                        .translate(i18.workOrder.requestTimeExtension),
                                                    icon: Icons.calendar_today_rounded,
                                                    buttonStyle: OutlinedButton.styleFrom(
                                                        minimumSize: Size(
                                                            MediaQuery.of(context).size.width / 2.8, 50),
                                                        shape: const RoundedRectangleBorder(),
                                                        side: BorderSide(
                                                            color: const DigitColors().burningOrange,
                                                            width: 1)),
                                                    onPressed: () {
                                                      Navigator.of(context, rootNavigator: true).pop();
                                                      context.read<ValidTimeExtCreationsSearchBloc>().add(
                                                          SearchValidTimeExtCreationsEvent(
                                                              contract: contracts?.contracts?.first,
                                                              contractNo: (contracts?.contracts ?? []).first.contractNumber.toString(),
                                                              tenantId: (contracts?.contracts ?? []).first.tenantId.toString(),
                                                              status: 'APPROVED'));
                                                    },
                                                    textStyle: const TextStyle(
                                                        fontWeight: FontWeight.w700, fontSize: 18),
                                                  )
                                                ],
                                              ),
                                            )),
                                      child: Center(
                                        child: Text(
                                              AppLocalizations.of(context)
                                                  .translate(i18.common.takeAction),
                                              style: Theme.of(context)
                                                  .textTheme
                                                  .titleMedium!
                                                  .apply(color: Colors.white)),
                                      ),
                                    ),
                                  ),
                                )
                                : Container();
                      });
            }));
                  }
                );
          }
        ),
        body: ScrollableContent(
          children: [
            BlocBuilder<LocalizationBloc, LocalizationState>(
                builder: (context, localState) {
                return BlocBuilder<MyWorksSearchCriteriaBloc,
                    MyWorksSearchCriteriaBlocState>(
                    builder: (context, searchState) {
                      return searchState.maybeWhen(orElse: () => Container(),
                    loading: () => shg_loader.Loaders.circularLoader(context),
                    loaded: (List<String>? searchCriteria, String? acceptCode) => BlocConsumer<SearchIndividualWorkBloc, SearchIndividualWorkState>(
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
                                        .where((w) => w != null && w.description != "")
                                        .map((e) => e!.description.toString())
                                        .toList()
                                    : [];
                                workOrderList = contracts.contracts!
                                    .map((e) => {
                                          'cardDetails': {
                                            i18.workOrder.workOrderNo:
                                                e.contractNumber ?? 'NA',
                                            i18.attendanceMgmt.projectId:
                                                e.additionalDetails?.projectId ?? 'NA',
                                            i18.common.location:
                                                '${t.translate('${CommonMethods.getConvertedLocalizedCode('locality', subString: e.additionalDetails?.locality ?? 'NA')}')}, ${t.translate(CommonMethods.getConvertedLocalizedCode('ward', subString: e.additionalDetails?.ward ?? 'NA'))}',
                                            i18.attendanceMgmt.projectType:
                                                'ES_COMMON_${e.additionalDetails?.projectType ?? 'NA'}',
                                            i18.attendanceMgmt.projectName:
                                                e.additionalDetails?.projectName ?? 'NA',
                                            i18.attendanceMgmt.projectDesc:
                                                e.additionalDetails?.projectDesc ?? 'NA',
                                          },
                                          'payload': e.toMap()
                                        })
                                    .toList();
                                contractDetails = contracts.contracts!
                                    .map((e) => {
                                          'cardDetails': {
                                            i18.workOrder.nameOfCBO:
                                                    e.additionalDetails?.cboName ?? 'NA',
                                            i18.workOrder.roleOfCBO:
                                                    e.executingAuthority ?? 'NA',
                                            i18.attendanceMgmt.engineerInCharge: e
                                                    .additionalDetails
                                                    ?.officerInChargeName
                                                    ?.name ??
                                                'NA',
                                            i18.attendanceMgmt.officeInCharge:
                                                e.additionalDetails
                                                        ?.officerInChargeDesgn ??
                                                    'NA',
                                            i18.workOrder.completionPeriod:
                                                '${e.completionPeriod} ${t.translate(i18.common.days)}',
                                            i18.workOrder.workOrderAmount:
                                                '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                            i18.common.status:
                                                'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}',
                                            Constants.activeInboxStatus: e.wfStatus == acceptCode ? 'true' : 'false'
                                          },
                                          'payload': e.toMap()
                                        })
                                    .toList();
                                workFlowDetails = contracts.contracts!
                                    .map((e) => {
                                          'cardDetails': {
                                            i18.workOrder.contractIssueDate:
                                                e.issueDate != null
                                                    ? DateFormats.timeStampToDate(
                                                        e.issueDate,
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
                                            i18.workOrder.workStartDate: e.startDate !=
                                                        null &&
                                                    e.startDate != 0
                                                ? DateFormats.getFilteredDate(
                                                    DateTime.fromMillisecondsSinceEpoch(
                                                            e.startDate ?? 0)
                                                        .toString())
                                                : 'NA',
                                            i18.workOrder.workEndDate: e.endDate !=
                                                        null &&
                                                    e.endDate != 0
                                                ? DateFormats.getFilteredDate(
                                                    DateTime.fromMillisecondsSinceEpoch(
                                                            e.endDate ?? 0)
                                                        .toString())
                                                : 'NA',
                                          },
                                          'payload': e.toMap()
                                        })
                                    .toList();
                                // fileStoreList = ;
                                attachedFiles = contracts.contracts!.first.documents != null && contracts.contracts!.first.additionalDetails?.estimateDocs != null ? [...contracts.contracts!.first.documents!.where((d) => d.fileStore != null && d.status != 'INACTIVE')
                                    .map((e) => FileStoreModel(
                                        name: e.documentType != 'OTHERS' ? e.documentType ?? '' : e.additionalDetails?.otherCategoryName ?? '', fileStoreId: e.fileStore)),
                                ...contracts.contracts!.first.additionalDetails!.estimateDocs!.where((m) => m.fileStoreId != null)
                                    .map((e) => FileStoreModel(
                                    name: e.fileType != 'Others' ?  e.fileType ?? '' : e.fileName ?? '', fileStoreId: e.fileStoreId))] : contracts.contracts!.first.documents != null && contracts.contracts!.first.additionalDetails?.estimateDocs == null  ? [...contracts.contracts!.first.documents!.where((d) => d.fileStore != null && d.status != 'INACTIVE')
                                    .map((e) => FileStoreModel(
                                    name: e.documentType != 'OTHERS' ? e.documentType ?? '' : e.additionalDetails?.otherCategoryName ?? '', fileStoreId: e.fileStore))] : contracts.contracts!.first.documents == null && contracts.contracts!.first.additionalDetails?.estimateDocs != null ? [...contracts.contracts!.first.additionalDetails!.estimateDocs!.where((m) => m.fileStoreId != null)
                                    .map((e) => FileStoreModel(
                                    name: e.fileType != 'Others' ?  e.fileType ?? '' : e.fileName ?? '', fileStoreId: e.fileStoreId))] : [];
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
                                      Row(
                                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                        children: [
                                          Back(
                                            backLabel: AppLocalizations.of(context)
                                                .translate(i18.common.back),
                                            callback: () {
                                            context.router.popUntilRouteWithPath('home') ;
                                            context.router.push(const WorkOrderRoute());
                                            },
                                          ),
                                          CommonWidgets.downloadButton(
                                              AppLocalizations.of(context)
                                                  .translate(i18.common.download), () {
                                            context.read<WorkOrderPDFBloc>().add(
                                                PDFEventWorkOrder(
                                                    contractId: widget.contractNumber,
                                                    tenantId: contracts.first.tenantId));
                                          })
                                        ],
                                      ),
                                      Column(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            Padding(
                                              padding: const EdgeInsets.only(
                                                  left: 8.0,
                                                  right: 16.0,
                                                  top: 16.0,
                                                  bottom: 16.0),
                                              child: Text(
                                                '${AppLocalizations.of(context).translate(i18.workOrder.workOrderDetails)}',
                                                style: DigitTheme.instance.mobileTheme
                                                    .textTheme.displayMedium,
                                                textAlign: TextAlign.left,
                                              ),
                                            ),
                                            DigitInfoCard(
                                                title: AppLocalizations.of(context)
                                                    .translate(i18.common.info),
                                                titleStyle: DigitTheme.instance
                                                    .mobileTheme.textTheme.headlineMedium
                                                    ?.apply(
                                                        color: const DigitColors().black),
                                                description: AppLocalizations.of(context)
                                                    .translate(i18.common.workOrderInfo),
                                                descStyle: DigitTheme.instance.mobileTheme
                                                    .textTheme.bodyLarge
                                                    ?.apply(
                                                        color: const Color.fromRGBO(
                                                            80, 90, 95, 1)),
                                                icon: Icons.info,
                                                iconColor:
                                                    const Color.fromRGBO(52, 152, 219, 1),
                                                backgroundColor:
                                                    DigitTheme.instance.colorScheme.tertiaryContainer),
                                            workOrderList.isNotEmpty
                                                ? Column(
                                                    children: [
                                                      WorkDetailsCard(
                                                        workOrderList,
                                                        viewWorkOrder: true,
                                                        elevatedButtonLabel:
                                                            AppLocalizations.of(context)
                                                                .translate(
                                                                    i18.common.accept),
                                                        outlinedButtonLabel:
                                                            AppLocalizations.of(context)
                                                                .translate(
                                                                    i18.common.decline),
                                                      ),
                                                      WorkDetailsCard(
                                                        contractDetails,
                                                        viewWorkOrder: true,
                                                        cardTitle:
                                                            AppLocalizations.of(context)
                                                                .translate(i18.workOrder
                                                                    .contractDetails),
                                                      ),
                                                      WorkDetailsCard(
                                                        workFlowDetails,
                                                        viewWorkOrder: true,
                                                        cardTitle:
                                                            AppLocalizations.of(context)
                                                                .translate(i18.workOrder
                                                                    .timeLineDetails),
                                                      ),
                                                      DigitCard(
                                                          child: Attachments(
                                                        t.translate(i18
                                                            .workOrder.relevantDocuments),
                                                        attachedFiles,
                                                      )),
                                                      Padding(
                                                        padding:
                                                            const EdgeInsets.all(8.0),
                                                        child: ButtonLink(
                                                          t.translate(i18.common
                                                              .viewTermsAndConditions),
                                                          () => DigitDialog.show(context,
                                                              options: DigitDialogOptions(
                                                                  title: Text(t.translate(i18.common.termsAndConditions),
                                                                      style: const TextStyle(
                                                                          fontWeight: FontWeight
                                                                              .w700,
                                                                          fontSize: 24,
                                                                          fontFamily:
                                                                              'Roboto Condensed',
                                                                          fontStyle: FontStyle
                                                                              .normal,
                                                                          color: Color.fromRGBO(
                                                                              11, 12, 12, 1))),
                                                                  content:
                                                                      termsNCond
                                                                              .isNotEmpty
                                                                          ? Column(
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
                                                                                        fontSize: 16,
                                                                                        fontWeight: FontWeight.w700,
                                                                                      ),
                                                                                      textAlign:
                                                                                          TextAlign.start,
                                                                                    ),
                                                                                  )
                                                                              ],
                                                                            )
                                                                          :  EmptyImage(
                                                                              align: Alignment
                                                                                  .center,
                                                                      label: t.translate(i18.common.noTermsNConditions),),
                                                                  titlePadding: const EdgeInsets.all(
                                                                      8.0),
                                                                  contentPadding: const EdgeInsets.all(
                                                                      8.0),
                                                                  barrierDismissible:
                                                                      true,
                                                                  primaryAction: DigitDialogActions(
                                                                      label: t.translate(i18.common.close),
                                                                      action: (context) => Navigator.of(context, rootNavigator: true).pop()),
                                                                  isScrollable: true)),
                                                          align: Alignment.centerLeft,
                                                        ),
                                                      ),
                                                      BlocListener<ValidTimeExtCreationsSearchBloc, ValidTimeExtCreationsSearchState>(
                                                        listener: (context, validContractState) {
                                                          validContractState.maybeWhen(
                                                              orElse: () => false,
                                                              loaded: (Contracts? contracts) => context.router
                                                                  .push(CreateTimeExtensionRequestRoute(
                                                                contractNumber:
                                                                contracts?.contractNumber,
                                                              )),
                                                              error: (String? error) => Notifiers.getToastMessage(
                                                                  context, error ?? 'ERR!', 'ERROR'));
                                                        },
                                                        child: const SizedBox.shrink(),
                                                      ),
                                                    ],
                                                  )
                                                : EmptyImage(
                                                    label: t.translate(i18
                                                        .workOrder.noWorkOrderAssigned),
                                                    align: Alignment.center,
                                                  ),
                                            const SizedBox(
                                              height: 16.0,
                                            ),
                                            const Align(
                                              alignment: Alignment.bottomCenter,
                                              child: PoweredByDigit(),
                                            )
                                          ]),
                                    ]);
                              } else {
                                return Container();
                              }
                            });
                      },
                    ));
                  }
                );
              }
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
                      context.router.popAndPush(const WorkOrderRoute());
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
                          contractNumber: workOrderList.first['payload']['contractNumber'],
                          wfStatus: contractsModel?.contracts?.first.wfStatus));
                    },
                    orElse: () => false);
              },
              child: Container(),
            ),
          ],
        ),
      ),
    );
  }
}
