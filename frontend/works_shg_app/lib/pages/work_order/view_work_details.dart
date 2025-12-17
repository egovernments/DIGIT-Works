// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_action_card.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/pop_up_card.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/widgets/widgets.dart';
// import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/employee/mb/mb_check.dart';
import 'package:works_shg_app/blocs/work_orders/work_order_pdf.dart';
import 'package:works_shg_app/utils/employee/mb/mb_logic.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';
import 'package:works_shg_app/widgets/work_details_card.dart';
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
import '../../widgets/atoms/attachments.dart';
import '../../widgets/loaders.dart' as shg_loader;

@RoutePage()
class ViewWorkDetailsPage extends StatefulWidget {
  final String? contractNumber;
  final String? wfStatus;
  const ViewWorkDetailsPage(
      {super.key,
      @queryParam this.contractNumber = 'contractNumber',
      @queryParam this.wfStatus = 'wfStatus'});
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
          IndividualWorkSearchEvent(
              contractNumber: widget.contractNumber,
              body: {
                "wfStatus": [widget.wfStatus],
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
    return PopScope(
      canPop: true,
      onPopInvoked: (value) async {
        if (GlobalVariables.roleType == RoleType.cbo) {
          context.router.popUntilRouteWithPath('home');
          context.router.push(const WorkOrderRoute());
        } else {
          Navigator.of(context).pop();
          // context.router.pop();
        }
      },
      child: Scaffold(
        backgroundColor: Theme.of(context).colorTheme.generic.background,
        // appBar: customAppBar(),
        // drawer: const MySideBar(),
        bottomNavigationBar:
            BlocBuilder<SearchIndividualWorkBloc, SearchIndividualWorkState>(
          builder: (context, state) {
            return state.maybeMap(
                orElse: () => const SizedBox.shrink(),
                loaded: (value) => GlobalVariables.roleType == RoleType.employee
                    ? BlocListener<MeasurementCheckBloc, MeasurementCheckState>(
                        listener: (context, measureMentState) {
                          measureMentState.maybeMap(
                            orElse: () => const SizedBox.shrink(),
                            loaded: (value) {
                              if (value.estimateStatus == true &&
                                  // TODO:[currently removed the workorder status]
                                  //  value.workOrderStatus == true  &&
                                  // end

                                  value.existingMB == true) {
                                context.router.push(MBDetailRoute(
                                  contractNumber: value.workOrderNumber ?? "",
                                  mbNumber: "",
                                  tenantId: GlobalVariables.tenantId,
                                  type: MBScreen.create,
                                ));
                              } else {
                                // TODO:[currently removed the workorder status]
                                // if (value.workOrderStatus == false) {
                                //   Notifiers.getToastMessage(
                                //       context,
                                //       t.translate(
                                //           i18.workOrder.timeExtensionError),
                                //       'ERROR');
                                // } else
                                // end
                                if (value.estimateStatus == false) {
                                  Notifiers.getToastMessage(
                                      context,
                                      t.translate(
                                          i18.workOrder.estimateRevisionError),
                                      'ERROR');
                                  // ui_component.Toast.showToast(context,
                                  //     message: t.translate(
                                  //         i18.workOrder.estimateRevisionError),
                                  //     type: ui_component.ToastType.error);
                                } else {
                                  Notifiers.getToastMessage(
                                      context,
                                      t.translate(
                                          i18.workOrder.existingMBCreateError),
                                      'ERROR');

                                  // ui_component.Toast.showToast(context,
                                  //     message: t.translate(
                                  //         i18.workOrder.existingMBCreateError),
                                  //     type: ui_component.ToastType.error);
                                }
                              }
                            },
                          );
                        },
                        child: ui_card.DigitCard(
                            cardType: CardType.primary,
                            padding: const EdgeInsets.all(8.0),
                            margin: const EdgeInsets.all(0),
                            children: [
                              ui_component.Button(
                                mainAxisSize: MainAxisSize.max,
                                type: ButtonType.primary,
                                size: ButtonSize.large,
                                onPressed: () {
                                  context
                                      .read<MeasurementCheckBloc>()
                                      .add(MeasurementCheckEvent(
                                        tenantId: GlobalVariables.tenantId!,
                                        contractNumber: widget.contractNumber!,
                                        measurementNumber: "",
                                        screenType: MBScreen.create,
                                      ));
                                },
                                label:
                                    t.translate(i18.measurementBook.createMb),
                              ),
                            ]),
                      )
                    : cboBottomNavigationBar(t));
          },
        ),
        body: GlobalVariables.roleType == RoleType.employee
            ? empScrollableContent(t)
            : cboScrollableContent(t),
      ),
    );
  }

// Content EMP
  ui_component.ScrollableContent empScrollableContent(AppLocalizations t) {
    return ui_component.ScrollableContent(
      backgroundColor: Theme.of(context).colorTheme.generic.background,
      children: [
        BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return BlocConsumer<SearchIndividualWorkBloc,
              SearchIndividualWorkState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => false,
                  initial: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  error: (String? error) => Notifiers.getToastMessage(
                      context, t.translate(error.toString()), "ERROR"),
                  //  ui_component.Toast.showToast(
                  //     context,
                  //     message: t.translate(error.toString()),
                  //     type: ToastType.error),

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
                                  i18.attendanceMgmt.officeInCharge: e
                                          .additionalDetails
                                          ?.officerInChargeDesgn ??
                                      'NA',
                                  i18.workOrder.completionPeriod:
                                      '${e.completionPeriod} ${t.translate(i18.common.days)}',
                                  i18.workOrder.workOrderAmount:
                                      '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                  i18.common.status:
                                      'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}',
                                  Constants.activeInboxStatus:
                                      e.wfStatus == "ACCEPTED"
                                          ? 'true'
                                          : 'false'
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
                      attachedFiles = contracts.contracts!.first.documents !=
                                  null &&
                              contracts.contracts!.first.additionalDetails
                                      ?.estimateDocs !=
                                  null
                          ? [
                              ...contracts.contracts!.first.documents!
                                  .where(
                                      (d) =>
                                          d.fileStore != null &&
                                          d.status != 'INACTIVE')
                                  .map((e) =>
                                      FileStoreModel(
                                          name: e.documentType != 'OTHERS'
                                              ? e.documentType ?? ''
                                              : e.additionalDetails
                                                      ?.otherCategoryName ??
                                                  '',
                                          fileStoreId: e.fileStore)),
                              ...contracts.contracts!.first.additionalDetails!
                                  .estimateDocs!
                                  .where((m) => m.fileStoreId != null)
                                  .map((e) => FileStoreModel(
                                      name: e.fileType != 'Others'
                                          ? e.fileType ?? ''
                                          : e.fileName ?? '',
                                      fileStoreId: e.fileStoreId))
                            ]
                          : contracts.contracts!.first.documents != null &&
                                  contracts.contracts!.first.additionalDetails
                                          ?.estimateDocs ==
                                      null
                              ? [
                                  ...contracts.contracts!.first.documents!
                                      .where((d) =>
                                          d.fileStore != null &&
                                          d.status != 'INACTIVE')
                                      .map((e) =>
                                          FileStoreModel(
                                              name: e.documentType != 'OTHERS'
                                                  ? e.documentType ?? ''
                                                  : e.additionalDetails
                                                          ?.otherCategoryName ??
                                                      '',
                                              fileStoreId: e.fileStore))
                                ]
                              : contracts.contracts!.first.documents == null &&
                                      contracts
                                              .contracts!
                                              .first
                                              .additionalDetails
                                              ?.estimateDocs !=
                                          null
                                  ? [
                                      ...contracts.contracts!.first
                                          .additionalDetails!.estimateDocs!
                                          .where((m) => m.fileStoreId != null)
                                          .map((e) => FileStoreModel(
                                              name: e.fileType != 'Others'
                                                  ? e.fileType ?? ''
                                                  : e.fileName ?? '',
                                              fileStoreId: e.fileStoreId))
                                    ]
                                  : [];
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
                                Padding(
                                   padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                                  child: BackNavigationButton(
                                    backNavigationButtonThemeData:
                                        const BackNavigationButtonThemeData()
                                            .copyWith(
                                                textColor: Theme.of(context)
                                                    .colorTheme
                                                    .primary
                                                    .primary2,
                                                contentPadding: EdgeInsets.zero,
                                                context: context,
                                                backButtonIcon: Icon(
                                                  Icons
                                                      .arrow_circle_left_outlined,
                                                  size: MediaQuery.of(context)
                                                              .size
                                                              .width <
                                                          500
                                                      ? Theme.of(context)
                                                          .spacerTheme
                                                          .spacer5
                                                      : Theme.of(context)
                                                          .spacerTheme
                                                          .spacer6,
                                                  color: Theme.of(context)
                                                      .colorTheme
                                                      .primary
                                                      .primary2,
                                                )
                                                // backButtonIcon: Icon(
                                                //   Icons.arrow_left,
                                                //   color: Theme.of(context)
                                                //       .colorTheme
                                                //       .primary
                                                //       .primary2,
                                                // ),
                                                ),
                                    backButtonText: AppLocalizations.of(context)
                                        .translate(i18.common.back),
                                    handleBack: () {
                                      if (GlobalVariables.roleType ==
                                          RoleType.cbo) {
                                        context.router
                                            .popUntilRouteWithPath('home');
                                        context.router
                                            .push(const WorkOrderRoute());
                                      } else {
                                        Navigator.of(context).pop();
                                        // context.router.pop();
                                      }
                                    },
                                  ),
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
                                    child: DigitTextBlock(
                                      heading:
                                          '${AppLocalizations.of(context).translate(i18.workOrder.workOrderDetails)}',
                                    ),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child: ui_component.InfoCard(
                                      title: AppLocalizations.of(context)
                                          .translate(i18.common.info),
                                      type: InfoType.info,
                                      description: AppLocalizations.of(context)
                                          .translate(i18.common.workOrderInfo),
                                    ),
                                  ),
                                  // TODO:[old info card]
                                  // DigitInfoCard(
                                  //     title: AppLocalizations.of(context)
                                  //         .translate(i18.common.info),
                                  //     titleStyle: DigitTheme.instance
                                  //         .mobileTheme.textTheme.headlineMedium
                                  //         ?.apply(
                                  //             color: const DigitColors().black),
                                  //     description: AppLocalizations.of(context)
                                  //         .translate(i18.common.workOrderInfo),
                                  //     descStyle: DigitTheme.instance.mobileTheme
                                  //         .textTheme.bodyLarge
                                  //         ?.apply(
                                  //             color: const Color.fromRGBO(
                                  //                 80, 90, 95, 1)),
                                  //     icon: Icons.info,
                                  //     iconColor:
                                  //         const Color.fromRGBO(52, 152, 219, 1),
                                  //     backgroundColor:
                                  //         DigitTheme.instance.colorScheme.tertiaryContainer,),

                                  workOrderList.isNotEmpty
                                      ? Column(
                                          crossAxisAlignment:
                                              CrossAxisAlignment.start,
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
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
                                            ui_card.DigitCard(
                                                margin: EdgeInsets.all(
                                                    Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2),
                                                cardType: CardType.primary,
                                                children: [
                                                  Attachments(
                                                    t.translate(i18.workOrder
                                                        .relevantDocuments),
                                                    attachedFiles,
                                                  )
                                                ]),
                                            Padding(
                                              padding:
                                                  const EdgeInsets.all(8.0),
                                              child: Button(
                                                type: ButtonType.tertiary,
                                                size: ButtonSize.large,
                                                label: t.translate(i18.common
                                                    .viewTermsAndConditions),
                                                onPressed: () => showDialog(
                                                  context: context,
                                                  builder: (context) {
                                                    return Popup(
                                                      actions: [
                                                        Button(
                                                            mainAxisSize:
                                                                MainAxisSize
                                                                    .max,
                                                            label: t.translate(
                                                                i18.common
                                                                    .close),
                                                            onPressed: () {
                                                              Navigator.of(
                                                                      context,
                                                                      rootNavigator:
                                                                          true)
                                                                  .pop();
                                                            },
                                                            type: ButtonType
                                                                .primary,
                                                            size: ButtonSize
                                                                .large)
                                                      ],
                                                      onCrossTap: () {
                                                        Navigator.of(context,
                                                                rootNavigator:
                                                                    true)
                                                            .pop();
                                                      },
                                                      title: t.translate(i18
                                                          .common
                                                          .termsAndConditions),
                                                      additionalWidgets:
                                                          termsNCond.isNotEmpty
                                                              ? List.generate(
                                                                  termsNCond
                                                                      .length,
                                                                  (i) => Align(
                                                                    alignment:
                                                                        Alignment
                                                                            .centerLeft,
                                                                    child: Text(
                                                                      '${i + 1}. ${termsNCond[i]}',
                                                                      style:
                                                                          const TextStyle(
                                                                        fontSize:
                                                                            16,
                                                                        fontWeight:
                                                                            FontWeight.w700,
                                                                      ),
                                                                      textAlign:
                                                                          TextAlign
                                                                              .start,
                                                                    ),
                                                                  ),
                                                                ).toList()
                                                              : [
                                                                  EmptyImage(
                                                                    align: Alignment
                                                                        .center,
                                                                    label: t.translate(i18
                                                                        .common
                                                                        .noTermsNConditions),
                                                                  ),
                                                                ],
                                                    );
                                                  },
                                                ),
                                                // align: Alignment.centerLeft,
                                              ),
                                            ),
                                            BlocListener<
                                                ValidTimeExtCreationsSearchBloc,
                                                ValidTimeExtCreationsSearchState>(
                                              listener: (context,
                                                  validContractState) {
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
                                                        // Notifiers
                                                        //     .getToastMessage(
                                                        //         context,
                                                        //         error ?? 'ERR!',
                                                        //         'ERROR'));
                                                        ui_component.Toast
                                                            .showToast(
                                                                context,
                                                                message:
                                                                    t.translate(
                                                                        error ??
                                                                            'ERR!'),
                                                                type: ToastType
                                                                    .error));
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
                                    child: ui_component.PoweredByDigit(
                                      version: Constants.appVersion,
                                    ),
                                  )
                                ]),
                          ]);
                    } else {
                      return Container();
                    }
                  });
            },
          );
        }),
        BlocListener<DeclineWorkOrderBloc, DeclineWorkOrderState>(
          listener: (context, state) {
            state.maybeWhen(
                initial: () => Container(),
                loading: () => hasLoaded = false,
                error: (String? error) {
                  Notifiers.getToastMessage(context, error.toString(), 'ERROR');
                  // ui_component.Toast.showToast(context,
                  //     message: t.translate(error.toString()),
                  //     type: ToastType.error);
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
                  Notifiers.getToastMessage(context, error.toString(), 'ERROR');
                },
                loaded: (ContractsModel? contractsModel) {
                  Notifiers.getToastMessage(
                      context,
                      '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${contractsModel?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                      'SUCCESS');
                  Future.delayed(const Duration(seconds: 1));
                  context.router.popAndPush(ViewWorkDetailsRoute(
                      contractNumber: workOrderList.first['payload']
                          ['contractNumber'],
                      wfStatus: contractsModel?.contracts?.first.wfStatus));
                },
                orElse: () => false);
          },
          child: Container(),
        ),
      ],
    );
  }

  BlocBuilder<LocalizationBloc, LocalizationState> cboBottomNavigationBar(
      AppLocalizations t) {
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return BlocBuilder<MyWorksSearchCriteriaBloc,
          MyWorksSearchCriteriaBlocState>(builder: (context, searchState) {
        return searchState.maybeWhen(
            orElse: () => Container(),
            loading: () => shg_loader.Loaders.circularLoader(context),
            loaded: (List<String>? searchCriteria, String? acceptCode) =>
                BlocBuilder<SearchIndividualWorkBloc,
                    SearchIndividualWorkState>(builder: (context, workState) {
                  return workState.maybeWhen(
                      orElse: () => Container(),
                      loading: () => shg_loader.Loaders.circularLoader(context),
                      error: (String? error) => Notifiers.getToastMessage(
                          context, t.translate(error.toString()), 'ERROR'),
                      loaded: (ContractsModel? contracts) {
                        return workOrderList.isNotEmpty &&
                                workOrderList.first['payload']['wfStatus'] ==
                                    acceptCode
                            // || workOrderList.first['payload']
                            //     ['wfStatus'] ==
                            // "ACCEPTED"
                            ? ui_card.DigitCard(
                                cardType: CardType.primary,
                                padding: const EdgeInsets.all(8.0),
                                margin: const EdgeInsets.all(0),
                                children: [
                                  ui_component.Button(
                                    mainAxisSize: MainAxisSize.max,
                                    size: ButtonSize.large,
                                    type: ButtonType.primary,
                                    label: t.translate(i18.common.accept),
                                    onPressed: () {
                                      context.read<AcceptWorkOrderBloc>().add(
                                            WorkOrderAcceptEvent(
                                                contractsModel: workOrderList
                                                    .first['payload'],
                                                action: 'ACCEPT',
                                                comments:
                                                    'Work Order has been accepted by CBO'),
                                          );
                                    },
                                  ),
                                  // Center(
                                  //   child: Padding(
                                  //     padding: const EdgeInsets.all(4.0),
                                  //     child: ButtonLink(
                                  //       t.translate(i18.common.decline),
                                  //       () => DigitDialog.show(context,
                                  //           options: DigitDialogOptions(
                                  //               titleText:
                                  //                   AppLocalizations.of(context)
                                  //                       .translate(
                                  //                           i18.common.warning),
                                  //               contentText:
                                  //                   AppLocalizations.of(context)
                                  //                       .translate(i18.workOrder
                                  //                           .warningMsg),
                                  //               primaryAction:
                                  //                   DigitDialogActions(
                                  //                 label: AppLocalizations.of(
                                  //                         context)
                                  //                     .translate(
                                  //                         i18.common.confirm),
                                  //                 action:
                                  //                     (BuildContext context) {
                                  //                   context
                                  //                       .read<
                                  //                           DeclineWorkOrderBloc>()
                                  //                       .add(
                                  //                         WorkOrderDeclineEvent(
                                  //                             contractsModel:
                                  //                                 workOrderList
                                  //                                         .first[
                                  //                                     'payload'],
                                  //                             action: 'DECLINE',
                                  //                             comments:
                                  //                                 'Work Order has been declined by CBO'),
                                  //                       );
                                  //                   Navigator.of(context,
                                  //                           rootNavigator: true)
                                  //                       .pop();
                                  //                 },
                                  //               ),
                                  //               secondaryAction:
                                  //                   DigitDialogActions(
                                  //                 label: AppLocalizations.of(
                                  //                         context)
                                  //                     .translate(
                                  //                         i18.common.back),
                                  //                 action:
                                  //                     (BuildContext context) =>
                                  //                         Navigator.of(context,
                                  //                                 rootNavigator:
                                  //                                     true)
                                  //                             .pop(),
                                  //               ))),
                                  //       align: Alignment.center,
                                  //       textAlign: TextAlign.center,
                                  //     ),
                                  //   ),
                                  // ),
                                  ui_component.Button(
                                    mainAxisSize: MainAxisSize.max,
                                    label: t.translate(i18.common.decline),
                                    onPressed: () {
                                      showDialog(
                                        context: context,
                                        builder: (context) {
                                          return Popup(
                                            onCrossTap: () {
                                              Navigator.of(context,
                                                      rootNavigator: true)
                                                  .pop();
                                            },
                                            title: AppLocalizations.of(context)
                                                .translate(i18.common.warning),
                                            description: AppLocalizations.of(
                                                    context)
                                                .translate(
                                                    i18.workOrder.warningMsg),
                                            actions: [
                                              Button(
                                                label:
                                                    AppLocalizations.of(context)
                                                        .translate(
                                                            i18.common.confirm),
                                                onPressed: () {
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
                                                type: ButtonType.primary,
                                                size: ButtonSize.large,
                                                mainAxisSize: MainAxisSize.max,
                                              )
                                            ],
                                          );
                                        },
                                      );
                                    },
                                    type: ButtonType.tertiary,
                                    size: ButtonSize.large,
                                  ),
                                ],
                              )
                            : workOrderList.isNotEmpty &&
                                    workOrderList.first['payload']
                                            ['wfStatus'] !=
                                        acceptCode
                                ? ui_card.DigitCard(
                                    cardType: CardType.primary,
                                    padding: const EdgeInsets.all(8.0),
                                    margin: const EdgeInsets.all(0),
                                    children: [
                                      ui_component.Button(
                                        mainAxisSize: MainAxisSize.max,
                                        type: ButtonType.primary,
                                        size: ButtonSize.large,
                                        onPressed: () {
                                          showDialog(
                                            context: context,
                                            builder: (context) {
                                              return ActionCard(
                                                  onOutsideTap: () {
                                                    Navigator.of(
                                                      context,
                                                      rootNavigator: true,
                                                    ).popUntil(
                                                      (route) =>
                                                          route is! PopupRoute,
                                                    );
                                                  },
                                                  actions: [
                                                    Button(
                                                      label: AppLocalizations
                                                              .of(context)
                                                          .translate(i18.home
                                                              .manageWageSeekers),
                                                      onPressed: () {
                                                        context.router.push(AttendanceRegisterTableRoute(
                                                            registerId: (contracts
                                                                            ?.contracts ??
                                                                        [])
                                                                    .first
                                                                    .additionalDetails
                                                                    ?.attendanceRegisterNumber
                                                                    .toString() ??
                                                                ''.toString(),
                                                            tenantId: (contracts
                                                                        ?.contracts ??
                                                                    [])
                                                                .first
                                                                .tenantId
                                                                .toString()));
                                                        Navigator.of(context,
                                                                rootNavigator:
                                                                    true)
                                                            .pop();
                                                      },
                                                      type:
                                                          ButtonType.secondary,
                                                      size: ButtonSize.large,
                                                      mainAxisSize:
                                                          MainAxisSize.max,
                                                      prefixIcon:
                                                          Icons.fingerprint,
                                                    ),
                                                    Button(
                                                      label: AppLocalizations
                                                              .of(context)
                                                          .translate(i18
                                                              .workOrder
                                                              .requestTimeExtension),
                                                      onPressed: () {
                                                        Navigator.of(context,
                                                                rootNavigator:
                                                                    true)
                                                            .pop();
                                                        context
                                                            .read<
                                                                ValidTimeExtCreationsSearchBloc>()
                                                            .add(SearchValidTimeExtCreationsEvent(
                                                                contract: contracts
                                                                    ?.contracts
                                                                    ?.first,
                                                                contractNo: (contracts
                                                                            ?.contracts ??
                                                                        [])
                                                                    .first
                                                                    .contractNumber
                                                                    .toString(),
                                                                tenantId: (contracts
                                                                            ?.contracts ??
                                                                        [])
                                                                    .first
                                                                    .tenantId
                                                                    .toString(),
                                                                status:
                                                                    'APPROVED'));
                                                      },
                                                      type:
                                                          ButtonType.secondary,
                                                      size: ButtonSize.large,
                                                      mainAxisSize:
                                                          MainAxisSize.max,
                                                      prefixIcon: Icons
                                                          .calendar_today_rounded,
                                                    )
                                                  ]);
                                            },
                                          );
                                        },
                                        label: AppLocalizations.of(context)
                                            .translate(i18.common.takeAction),
                                      ),
                                    ],
                                  )
                                : const SizedBox.shrink();
                      });
                }));
      });
    });
  }

// content CBO

  ui_component.ScrollableContent cboScrollableContent(AppLocalizations t) {
    return ui_component.ScrollableContent(
      backgroundColor: Theme.of(context).colorTheme.generic.background,
      children: [
        BlocListener<WorkOrderPDFBloc, WorkOrderPDFState>(
          listener: (context, state) {
            state.maybeMap(
              orElse: () {
                const SizedBox.shrink();
              },
              error: (value) {
                Notifiers.getToastMessage(
                    context,
                    AppLocalizations.of(context)
                        .translate(i18.common.statementnotfound),

                    // value.error!,
                    'ERROR');

                // Toast.showToast(context,
                //     message: AppLocalizations.of(context)
                //         .translate(i18.common.statementnotfound),
                //     type: ToastType.error);
              },
            );
          },
          child: BlocBuilder<LocalizationBloc, LocalizationState>(
              builder: (context, localState) {
            return BlocBuilder<MyWorksSearchCriteriaBloc,
                    MyWorksSearchCriteriaBlocState>(
                builder: (context, searchState) {
              return searchState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (List<String>? searchCriteria, String? acceptCode) =>
                      BlocConsumer<SearchIndividualWorkBloc,
                          SearchIndividualWorkState>(
                        listener: (context, state) {
                          state.maybeWhen(
                              orElse: () => false,
                              initial: () => false,
                              loading: () =>
                                  shg_loader.Loaders.circularLoader(context),
                              error: (String? error) =>
                                  Notifiers.getToastMessage(
                                      context, error.toString(), 'ERROR'),
                              // ui_component.Toast.showToast(context,
                              //     message: t.translate(error.toString()),
                              //     type: ToastType.error),
                              loaded: (ContractsModel? contracts) {
                                if (contracts?.contracts != null) {
                                  termsNCond = contracts!
                                              .contracts!
                                              .first
                                              .additionalDetails!
                                              .termsAndConditions !=
                                          null
                                      ? contracts
                                          .contracts!
                                          .first
                                          .additionalDetails!
                                          .termsAndConditions!
                                          .where((w) =>
                                              w != null && w.description != "")
                                          .map((e) => e!.description.toString())
                                          .toList()
                                      : [];
                                  workOrderList = contracts.contracts!
                                      .map((e) => {
                                            'cardDetails': {
                                              i18.workOrder.workOrderNo:
                                                  e.contractNumber ?? 'NA',
                                              i18.attendanceMgmt.projectId: e
                                                      .additionalDetails
                                                      ?.projectId ??
                                                  'NA',
                                              i18.common.location:
                                                  '${t.translate('${CommonMethods.getConvertedLocalizedCode('locality', subString: e.additionalDetails?.locality ?? 'NA')}')}, ${t.translate(CommonMethods.getConvertedLocalizedCode('ward', subString: e.additionalDetails?.ward ?? 'NA'))}',
                                              i18.attendanceMgmt.projectType:
                                                  'ES_COMMON_${e.additionalDetails?.projectType ?? 'NA'}',
                                              i18.attendanceMgmt.projectName: e
                                                      .additionalDetails
                                                      ?.projectName ??
                                                  'NA',
                                              i18.attendanceMgmt.projectDesc: e
                                                      .additionalDetails
                                                      ?.projectDesc ??
                                                  'NA',
                                            },
                                            'payload': e.toMap()
                                          })
                                      .toList();
                                  contractDetails = contracts.contracts!
                                      .map((e) => {
                                            'cardDetails': {
                                              i18.workOrder.nameOfCBO: e
                                                      .additionalDetails
                                                      ?.cboName ??
                                                  'NA',
                                              i18.workOrder.roleOfCBO:
                                                  e.executingAuthority ?? 'NA',
                                              i18.attendanceMgmt
                                                  .engineerInCharge: e
                                                      .additionalDetails
                                                      ?.officerInChargeName
                                                      ?.name ??
                                                  'NA',
                                              i18.attendanceMgmt
                                                  .officeInCharge: e
                                                      .additionalDetails
                                                      ?.officerInChargeDesgn ??
                                                  'NA',
                                              i18.workOrder.completionPeriod:
                                                  '${e.completionPeriod} ${t.translate(i18.common.days)}',
                                              i18.workOrder.workOrderAmount:
                                                  '₹ ${NumberFormat('##,##,##,##,###').format(e.totalContractedAmount ?? 0)}',
                                              i18.common.status:
                                                  'WF_WORK_ORDER_STATE_${e.wfStatus.toString()}',
                                              Constants.activeInboxStatus:
                                                  e.wfStatus == acceptCode
                                                      ? 'true'
                                                      : 'false'
                                            },
                                            'payload': e.toMap()
                                          })
                                      .toList();
                                  workFlowDetails = contracts.contracts!
                                      .map((e) => {
                                            'cardDetails': {
                                              i18.workOrder.contractIssueDate: e
                                                          .issueDate !=
                                                      null
                                                  ? DateFormats.timeStampToDate(
                                                      e.issueDate,
                                                      format: "dd/MM/yyyy")
                                                  : 'NA',
                                              i18.workOrder.dueDate: e
                                                          .issueDate !=
                                                      null
                                                  ? DateFormats.getFilteredDate(
                                                      DateTime.fromMillisecondsSinceEpoch(
                                                              e.issueDate ?? 0)
                                                          .add(const Duration(
                                                              days: 7))
                                                          .toLocal()
                                                          .toString())
                                                  : 'NA',
                                              i18.workOrder.workStartDate: e
                                                              .startDate !=
                                                          null &&
                                                      e.startDate != 0
                                                  ? DateFormats.getFilteredDate(
                                                      DateTime.fromMillisecondsSinceEpoch(
                                                              e.startDate ?? 0)
                                                          .toString())
                                                  : 'NA',
                                              i18.workOrder.workEndDate: e
                                                              .endDate !=
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
                                  attachedFiles = contracts
                                                  .contracts!.first.documents !=
                                              null &&
                                          contracts
                                                  .contracts!
                                                  .first
                                                  .additionalDetails
                                                  ?.estimateDocs !=
                                              null
                                      ? [
                                          ...contracts
                                              .contracts!.first.documents!
                                              .where((d) =>
                                                  d.fileStore != null &&
                                                  d.status != 'INACTIVE')
                                              .map((e) => FileStoreModel(
                                                  name: e.documentType !=
                                                          'OTHERS'
                                                      ? e.documentType ?? ''
                                                      : e.additionalDetails
                                                              ?.otherCategoryName ??
                                                          '',
                                                  fileStoreId: e.fileStore)),
                                          ...contracts.contracts!.first
                                              .additionalDetails!.estimateDocs!
                                              .where(
                                                  (m) => m.fileStoreId != null)
                                              .map((e) => FileStoreModel(
                                                  name: e.fileType != 'Others'
                                                      ? e.fileType ?? ''
                                                      : e.fileName ?? '',
                                                  fileStoreId: e.fileStoreId))
                                        ]
                                      : contracts.contracts!.first.documents !=
                                                  null &&
                                              contracts
                                                      .contracts!
                                                      .first
                                                      .additionalDetails
                                                      ?.estimateDocs ==
                                                  null
                                          ? [
                                              ...contracts
                                                  .contracts!.first.documents!
                                                  .where((d) =>
                                                      d.fileStore != null &&
                                                      d.status != 'INACTIVE')
                                                  .map((e) => FileStoreModel(
                                                      name: e.documentType !=
                                                              'OTHERS'
                                                          ? e.documentType ?? ''
                                                          : e.additionalDetails
                                                                  ?.otherCategoryName ??
                                                              '',
                                                      fileStoreId: e.fileStore))
                                            ]
                                          : contracts.contracts!.first
                                                          .documents ==
                                                      null &&
                                                  contracts
                                                          .contracts!
                                                          .first
                                                          .additionalDetails
                                                          ?.estimateDocs !=
                                                      null
                                              ? [
                                                  ...contracts
                                                      .contracts!
                                                      .first
                                                      .additionalDetails!
                                                      .estimateDocs!
                                                      .where((m) =>
                                                          m.fileStoreId != null)
                                                      .map((e) => FileStoreModel(
                                                          name: e.fileType !=
                                                                  'Others'
                                                              ? e.fileType ?? ''
                                                              : e.fileName ??
                                                                  '',
                                                          fileStoreId:
                                                              e.fileStoreId))
                                                ]
                                              : [];
                                }
                              });
                        },
                        builder: (context, searchState) {
                          return searchState.maybeWhen(
                              orElse: () => Container(),
                              loading: () =>
                                  shg_loader.Loaders.circularLoader(context),
                              initial: () => Container(),
                              loaded: (ContractsModel? contractsModel) {
                                final contracts = contractsModel?.contracts;
                                if (contracts != null) {
                                  return Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.spaceBetween,
                                          children: [
                                            Padding(
                                              padding:  EdgeInsets.symmetric(
                                      horizontal: Theme.of(context).spacerTheme.spacer4,
                                      vertical: Theme.of(context).spacerTheme.spacer4,
                                    ),
                                              child: BackNavigationButton(
                                                backNavigationButtonThemeData:
                                                    const BackNavigationButtonThemeData()
                                                        .copyWith(
                                                            context: context,
                                                            backButtonIcon:
                                                                Icon(
                                                              Icons
                                                                  .arrow_circle_left_outlined,
                                                              size: MediaQuery.of(
                                                                              context)
                                                                          .size
                                                                          .width <
                                                                      500
                                                                  ? Theme.of(
                                                                          context)
                                                                      .spacerTheme
                                                                      .spacer5
                                                                  : Theme.of(
                                                                          context)
                                                                      .spacerTheme
                                                                      .spacer6,
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorTheme
                                                                  .primary
                                                                  .primary2,
                                                            )),
                                                backButtonText:
                                                    AppLocalizations.of(context)
                                                        .translate(
                                                            i18.common.back),
                                                handleBack: () {
                                                  if (GlobalVariables
                                                          .roleType ==
                                                      RoleType.cbo) {
                                                    context.router
                                                        .popUntilRouteWithPath(
                                                            'home');
                                                    context.router.push(
                                                        const WorkOrderRoute());
                                                  } else {
                                                    Navigator.of(context).pop();
                                                  }
                                                },
                                              ),
                                            ),

                                            //TODO:[CBO download]
                                            CommonWidgets.downloadButton(
                                                AppLocalizations.of(context)
                                                    .translate(
                                                        i18.common.download),
                                                () async {
                                              // await DigitActionDialog.show(
                                              //   context,
                                              //   widget: Column(
                                              //     crossAxisAlignment:
                                              //         CrossAxisAlignment.center,
                                              //     mainAxisAlignment:
                                              //         MainAxisAlignment.center,
                                              //     children: [
                                              //       DigitOutlineIconButton(
                                              //         buttonStyle: OutlinedButton.styleFrom(
                                              //             minimumSize: Size(
                                              //                 MediaQuery.of(
                                              //                         context)
                                              //                     .size
                                              //                     .width,
                                              //                 50),
                                              //             shape:
                                              //                 const RoundedRectangleBorder(),
                                              //             side: BorderSide(
                                              //                 color: const DigitColors()
                                              //                     .burningOrange,
                                              //                 width: 1)),
                                              //         onPressed: () {
                                              //           // Navigator.of(context).pop();
                                              // context
                                              //     .read<
                                              //         WorkOrderPDFBloc>()
                                              //     .add(PDFEventWorkOrder(
                                              //         contractId: widget
                                              //             .contractNumber,
                                              //         tenantId: contracts
                                              //             .first
                                              //             .tenantId));

                                              // Navigator.of(
                                              //   context,
                                              //   rootNavigator: true,
                                              // ).popUntil(
                                              //   (route) => route
                                              //       is! PopupRoute,
                                              // );
                                              //         },
                                              // label: AppLocalizations
                                              //         .of(context)
                                              //     .translate(i18.common
                                              //         .workOrderdownload),
                                              //         icon:
                                              //             Icons.download_sharp,
                                              //         textStyle:
                                              //             const TextStyle(
                                              //                 fontWeight:
                                              //                     FontWeight
                                              //                         .w700,
                                              //                 fontSize: 18),
                                              //       ),
                                              //       const SizedBox(
                                              //         height: 10,
                                              //       ),
                                              //       DigitOutlineIconButton(
                                              //         buttonStyle: OutlinedButton.styleFrom(
                                              //             minimumSize: Size(
                                              //                 MediaQuery.of(
                                              //                         context)
                                              //                     .size
                                              //                     .width,
                                              //                 50),
                                              //             shape:
                                              //                 const RoundedRectangleBorder(),
                                              //             side: BorderSide(
                                              //                 color: const DigitColors()
                                              //                     .burningOrange,
                                              //                 width: 1)),
                                              //         onPressed: () {
                                              //           context
                                              //               .read<
                                              //                   WorkOrderPDFBloc>()
                                              //               .add(
                                              //                 PDFEventAnalysis(
                                              //                     estimateId: contracts
                                              //                         .first
                                              //                         .lineItems!
                                              //                         .first
                                              //                         .estimateId,
                                              //                     tenantId:
                                              //                         contracts
                                              //                             .first
                                              //                             .tenantId,
                                              //                     workorder: widget
                                              //                         .contractNumber),
                                              //               );
                                              //           Navigator.of(
                                              //             context,
                                              //             rootNavigator: true,
                                              //           ).popUntil(
                                              //             (route) => route
                                              //                 is! PopupRoute,
                                              //           );
                                              //         },
                                              // label: AppLocalizations
                                              //         .of(context)
                                              //     .translate(i18.common
                                              //         .analysisdownload),
                                              //         icon:
                                              //             Icons.download_sharp,
                                              //         textStyle:
                                              //             const TextStyle(
                                              //                 fontWeight:
                                              //                     FontWeight
                                              //                         .w700,
                                              //                 fontSize: 18),
                                              //       ),
                                              //     ],
                                              //   ),
                                              // );

                                              await showDialog(
                                                context: context,
                                                builder: (context) {
                                                  return ActionCard(
                                                    actions: [
                                                      Button(
                                                          mainAxisSize:
                                                              MainAxisSize.max,
                                                          label: AppLocalizations
                                                                  .of(context)
                                                              .translate(i18
                                                                  .common
                                                                  .workOrderdownload),
                                                          onPressed: () {
                                                            context
                                                                .read<
                                                                    WorkOrderPDFBloc>()
                                                                .add(PDFEventWorkOrder(
                                                                    contractId:
                                                                        widget
                                                                            .contractNumber,
                                                                    tenantId: contracts
                                                                        .first
                                                                        .tenantId));

                                                            Navigator.of(
                                                              context,
                                                              rootNavigator:
                                                                  true,
                                                            ).popUntil(
                                                              (route) => route
                                                                  is! PopupRoute,
                                                            );
                                                          },
                                                          type: ButtonType
                                                              .secondary,
                                                          size:
                                                              ButtonSize.large),
                                                      Button(
                                                          mainAxisSize:
                                                              MainAxisSize.max,
                                                          label: AppLocalizations
                                                                  .of(context)
                                                              .translate(i18
                                                                  .common
                                                                  .analysisdownload),
                                                          onPressed: () {
                                                            context
                                                                .read<
                                                                    WorkOrderPDFBloc>()
                                                                .add(
                                                                  PDFEventAnalysis(
                                                                      estimateId: contracts
                                                                          .first
                                                                          .lineItems!
                                                                          .first
                                                                          .estimateId,
                                                                      tenantId: contracts
                                                                          .first
                                                                          .tenantId,
                                                                      workorder:
                                                                          widget
                                                                              .contractNumber),
                                                                );
                                                            Navigator.of(
                                                              context,
                                                              rootNavigator:
                                                                  true,
                                                            ).popUntil(
                                                              (route) => route
                                                                  is! PopupRoute,
                                                            );
                                                          },
                                                          type: ButtonType
                                                              .secondary,
                                                          size:
                                                              ButtonSize.large),
                                                    ],
                                                  );
                                                },
                                              );
                                            }),
                                          ],
                                        ),
                                        Column(
                                            mainAxisAlignment:
                                                MainAxisAlignment.start,
                                            crossAxisAlignment:
                                                CrossAxisAlignment.start,
                                            children: [
                                              Padding(
                                                padding: const EdgeInsets.only(
                                                    left: 8.0,
                                                    right: 16.0,
                                                    top: 16.0,
                                                    bottom: 16.0),
                                                child: DigitTextBlock(
                                                  heading:
                                                      '${AppLocalizations.of(context).translate(i18.workOrder.workOrderDetails)}',
                                                ),
                                              ),
                                              Padding(
                                                padding:
                                                    const EdgeInsets.all(8.0),
                                                child: ui_component.InfoCard(
                                                  title: AppLocalizations.of(
                                                          context)
                                                      .translate(
                                                          i18.common.info),
                                                  type: InfoType.info,
                                                  description:
                                                      AppLocalizations.of(
                                                              context)
                                                          .translate(i18.common
                                                              .workOrderInfo),
                                                ),
                                              ),
                                              workOrderList.isNotEmpty
                                                  ? Column(
                                                      crossAxisAlignment:
                                                          CrossAxisAlignment
                                                              .start,
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .center,
                                                      children: [
                                                        WorkDetailsCard(
                                                          workOrderList,
                                                          viewWorkOrder: true,
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
                                                        ),
                                                        WorkDetailsCard(
                                                          contractDetails,
                                                          viewWorkOrder: true,
                                                          cardTitle: AppLocalizations
                                                                  .of(context)
                                                              .translate(i18
                                                                  .workOrder
                                                                  .contractDetails),
                                                        ),
                                                        WorkDetailsCard(
                                                          workFlowDetails,
                                                          viewWorkOrder: true,
                                                          cardTitle: AppLocalizations
                                                                  .of(context)
                                                              .translate(i18
                                                                  .workOrder
                                                                  .timeLineDetails),
                                                        ),
                                                        ui_card.DigitCard(
                                                            margin: EdgeInsets
                                                                .all(Theme.of(
                                                                        context)
                                                                    .spacerTheme
                                                                    .spacer2),
                                                            cardType: CardType
                                                                .primary,
                                                            children: [
                                                              Attachments(
                                                                t.translate(i18
                                                                    .workOrder
                                                                    .relevantDocuments),
                                                                attachedFiles,
                                                              ),
                                                            ]),
                                                        Padding(
                                                          padding:
                                                              const EdgeInsets
                                                                  .all(8.0),
                                                          child: Button(
                                                            type: ButtonType
                                                                .tertiary,
                                                            size: ButtonSize
                                                                .large,
                                                            label: t.translate(i18
                                                                .common
                                                                .viewTermsAndConditions),
                                                            onPressed: () =>
                                                                showDialog(
                                                              context: context,
                                                              builder:
                                                                  (context) {
                                                                return Popup(
                                                                  actions: [
                                                                    Button(
                                                                        mainAxisSize:
                                                                            MainAxisSize
                                                                                .max,
                                                                        label: t.translate(i18
                                                                            .common
                                                                            .close),
                                                                        onPressed:
                                                                            () {
                                                                          Navigator.of(context, rootNavigator: true)
                                                                              .pop();
                                                                        },
                                                                        type: ButtonType
                                                                            .primary,
                                                                        size: ButtonSize
                                                                            .large)
                                                                  ],
                                                                  onCrossTap:
                                                                      () {
                                                                    Navigator.of(
                                                                            context,
                                                                            rootNavigator:
                                                                                true)
                                                                        .pop();
                                                                  },
                                                                  title: t.translate(i18
                                                                      .common
                                                                      .termsAndConditions),
                                                                  additionalWidgets: termsNCond
                                                                          .isNotEmpty
                                                                      ? List
                                                                          .generate(
                                                                          termsNCond
                                                                              .length,
                                                                          (i) =>
                                                                              Align(
                                                                            alignment:
                                                                                Alignment.centerLeft,
                                                                            child:
                                                                                Text(
                                                                              '${i + 1}. ${termsNCond[i]}',
                                                                              style: const TextStyle(
                                                                                fontSize: 16,
                                                                                fontWeight: FontWeight.w700,
                                                                              ),
                                                                              textAlign: TextAlign.start,
                                                                            ),
                                                                          ),
                                                                        ).toList()
                                                                      : [
                                                                          EmptyImage(
                                                                            align:
                                                                                Alignment.center,
                                                                            label:
                                                                                t.translate(i18.common.noTermsNConditions),
                                                                          ),
                                                                        ],
                                                                );
                                                              },
                                                            ),
                                                            // align: Alignment
                                                            //     .centerLeft,
                                                          ),
                                                        ),
                                                        BlocListener<
                                                            ValidTimeExtCreationsSearchBloc,
                                                            ValidTimeExtCreationsSearchState>(
                                                          listener: (context,
                                                              validContractState) {
                                                            validContractState
                                                                .maybeWhen(
                                                                    orElse: () =>
                                                                        false,
                                                                    loaded: (Contracts?
                                                                            contracts) =>
                                                                        context
                                                                            .router
                                                                            .push(
                                                                                CreateTimeExtensionRequestRoute(
                                                                          contractNumber:
                                                                              contracts?.contractNumber,
                                                                        )),
                                                                    error: (String? error) => Notifiers.getToastMessage(
                                                                        context,
                                                                        error ??
                                                                            'ERR!',
                                                                        'ERROR'));

                                                            // ui_component.Toast.showToast(
                                                            //     context,
                                                            //     message: t.translate(error ??
                                                            //         'ERR!'),
                                                            //     type:
                                                            //         ToastType.error));
                                                          },
                                                          child: const SizedBox
                                                              .shrink(),
                                                        ),
                                                      ],
                                                    )
                                                  : EmptyImage(
                                                      label: t.translate(i18
                                                          .workOrder
                                                          .noWorkOrderAssigned),
                                                      align: Alignment.center,
                                                    ),
                                              const SizedBox(
                                                height: 16.0,
                                              ),
                                              const Align(
                                                alignment:
                                                    Alignment.bottomCenter,
                                                child:
                                                    ui_component.PoweredByDigit(
                                                  version: Constants.appVersion,
                                                ),
                                              )
                                            ]),
                                      ]);
                                } else {
                                  return Container();
                                }
                              });
                        },
                      ));
            });
          }),
        ),
        BlocListener<DeclineWorkOrderBloc, DeclineWorkOrderState>(
          listener: (context, state) {
            state.maybeWhen(
                initial: () => Container(),
                loading: () => hasLoaded = false,
                error: (String? error) {
                  Notifiers.getToastMessage(context, error.toString(), 'ERROR');
                  // ui_component.Toast.showToast(context,
                  //     message: t.translate(error.toString()),
                  //     type: ToastType.error);
                },
                loaded: (ContractsModel? contractsModel) {
                  Notifiers.getToastMessage(
                      context,
                      '${contractsModel?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}',
                      'SUCCESS');

                  // ui_component.Toast.showToast(context,
                  //     message: t.translate(
                  //         '${contractsModel?.contracts?.first.contractNumber} ${AppLocalizations.of(context).translate(i18.workOrder.workOrderDeclineSuccess)}'),
                  //     type: ToastType.success);

                  Future.delayed(const Duration(seconds: 1));
                  context.router.popAndPush(const WorkOrderRoute());
                },
                orElse: () => false);
          },
          child: const SizedBox.shrink(),
        ),
        BlocListener<AcceptWorkOrderBloc, AcceptWorkOrderState>(
          listener: (context, state) {
            state.maybeWhen(
                initial: () => Container(),
                loading: () => shg_loader.Loaders.circularLoader(context),
                error: (String? error) {
                  Notifiers.getToastMessage(context, error.toString(), 'ERROR');
                  // ui_component.Toast.showToast(context,
                  //     message: t.translate(error.toString()),
                  //     type: ToastType.error);
                },
                loaded: (ContractsModel? contractsModel) {
                  Notifiers.getToastMessage(
                      context,
                      '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${contractsModel?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}',
                      'SUCCESS');

                  // ui_component.Toast.showToast(context,
                  //     message: t.translate(
                  //         '${AppLocalizations.of(context).translate(i18.workOrder.workOrderAcceptSuccess)}. ${contractsModel?.contracts?.first.additionalDetails?.attendanceRegisterNumber} ${AppLocalizations.of(context).translate(i18.attendanceMgmt.attendanceCreateSuccess)}'),
                  //     type: ToastType.success);
                  Future.delayed(const Duration(seconds: 1));
                  context.router.popAndPush(ViewWorkDetailsRoute(
                      contractNumber: workOrderList.first['payload']
                          ['contractNumber'],
                      wfStatus: contractsModel?.contracts?.first.wfStatus));
                },
                orElse: () => false);
          },
          child: Container(),
        ),
      ],
    );
  }
}
