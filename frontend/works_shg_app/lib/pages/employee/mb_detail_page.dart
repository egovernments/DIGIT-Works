import 'dart:io';

import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/ComponentTheme/digit_tab_bar_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/digit_divider.dart';
import 'package:digit_ui_components/widgets/atoms/digit_tab.dart';
import 'package:digit_ui_components/widgets/atoms/label_value_list.dart';
import 'package:digit_ui_components/widgets/atoms/labelled_fields.dart'
    as ui_label;
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/atoms/upload_popUp.dart';
import 'package:digit_ui_components/widgets/molecules/bottom_sheet.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_component;
import 'package:collection/collection.dart';
// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/widgets/molecules/digit_timeline_molecule.dart';
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:works_shg_app/blocs/auth/auth.dart';
import 'package:works_shg_app/blocs/employee/mb/mb_crud.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/data/repositories/core_repo/core_repository.dart';
import 'package:works_shg_app/models/employee/mb/mb_detail_response.dart';
import 'package:works_shg_app/models/muster_rolls/muster_workflow_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/atoms/empty_image.dart';

import '../../blocs/employee/emp_hrms/emp_hrms.dart';
import '../../blocs/employee/mb/mb_detail_view.dart';
import '../../blocs/muster_rolls/get_business_workflow.dart';
import '../../blocs/muster_rolls/get_muster_workflow.dart';
import '../../blocs/work_orders/search_individual_work.dart';
import '../../models/employee/mb/filtered_measures.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/muster_rolls/business_service_workflow.dart';
import '../../utils/common_methods.dart';
import '../../utils/date_formats.dart';
import '../../utils/employee/mb/mb_logic.dart';
import '../../widgets/atoms/digit_timeline.dart';
import '../../widgets/mb/work_flow_button_list.dart';
import '../../widgets/mb/sor_item_add_mb.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;
import 'package:path/path.dart' as path;

@RoutePage()
class MBDetailPage extends StatefulWidget {
  final String contractNumber;
  final String mbNumber;
  final String? tenantId;
  final MBScreen type;
  const MBDetailPage(
      {super.key,
      required this.contractNumber,
      required this.mbNumber,
      this.tenantId,
      required this.type});

  @override
  State<MBDetailPage> createState() => _MBDetailPageState();
}

class _MBDetailPageState extends State<MBDetailPage>
    with SingleTickerProviderStateMixin {
  late TabController _tabController;
  //int _selectedIndex = 0;

  int phots = 0;
  List<DigitTimelineOptions> timeLineAttributes = [];

  late TextEditingController consumedQty;
  late TextEditingController currentAmt;

  int tabIndex = 0;

  // check points for creating new MB
//  ACTIVE
  String workorderStatus = "";
  //  INWORKFLOW
  String estimateStatus = "";
  @override
  void initState() {
    consumedQty = TextEditingController();
    currentAmt = TextEditingController();

    if (widget.type == MBScreen.create) {
      context.read<BusinessWorkflowBloc>().add(
            //hard coded
            GetBusinessWorkflowEvent(
              tenantId: widget.tenantId!,
              // businessService: 'CONTRACT',
              businessService: 'MB',
            ),
          );
      // SearchIndividualWorkBloc
      context.read<SearchIndividualWorkBloc>().add(
            IndividualWorkSearchEvent(
                contractNumber: widget.contractNumber, body: null),
          );
    } else {
      context.read<MusterGetWorkflowBloc>().add(
            FetchMBWorkFlowEvent(
                tenantId: widget.tenantId!, mbNumber: widget.mbNumber),
          );
    }
    context.read<MeasurementDetailBloc>().add(
          MeasurementDetailBookBlocEvent(
            tenantId: widget.tenantId!,
            contractNumber: widget.contractNumber,
            measurementNumber: widget.mbNumber,
            screenType: widget.type,
          ),
        );

    super.initState();
    _tabController = TabController(length: 3, vsync: this);
    // _tabController.addListener(_handleTabSelection);
  }

  // void _handleTabSelection() {
  //   setState(() {
  //     _selectedIndex = _tabController.index;
  //   });
  // }

  @override
  void dispose() {
    consumedQty.clear();
    currentAmt.clear();
    consumedQty.dispose();
    currentAmt.dispose();

    _tabController.dispose();
    // _tabController.removeListener(_handleTabSelection);
    super.dispose();
  }

  void uploadDocument(List<PlatformFile> files, BuildContext context,
      List<WorkflowDocument> serverData) async {
    List<WorkflowDocument> dataDocument = [];
    List<PlatformFile> payload = kIsWeb
        ? files.where((er) => er.bytes != null).toList()
        : files.where((er) => er.path != null).toList();

    try {
      Navigator.of(
        context,
        rootNavigator: true,
      ).popUntil(
        (route) => route is! PopupRoute,
      );
      if (payload.isNotEmpty) {
        // Loaders.showLoadingDialog(context, label: "Uploading...");
        shg_loader.Loaders.showLoadingDialog(context,
            label:
                AppLocalizations.of(context).translate(i18.common.uploading));
       final List<dynamic> uploadableFiles = payload.map((file) {
          if (kIsWeb) {
            // On web, use PlatformFile with bytes
            return PlatformFile(
              name: file.name,
              bytes: file.bytes,
              size: file.size,
            );
          } else {
            // On native platforms, use the File object
            return File(
                file.path ?? file.name); // Default to name if path is null
          }
        }).toList();

        // Cast the list appropriately based on the platform
        final List<dynamic> data = kIsWeb
            ? uploadableFiles.cast<PlatformFile>()
            : uploadableFiles.cast<File>();
        final response =
            await CoreRepository().uploadFiles(data, "img_measurement_book");

        // var response = await CoreRepository().uploadFiles(
        //     payload.map((e) => File(e.path ?? e.name!)).toList(),
        //     "img_measurement_book");

        for (int i = 0; i < response.length; i++) {
          dataDocument.add(WorkflowDocument(
              indexing:
                  dataDocument.isEmpty ? 0 : (dataDocument.last.indexing! + 1),
              isActive: true,
              tenantId: response[i].tenantId,
              fileStore: response[i].fileStoreId,
              documentType: path.extension(payload[i].name ?? ''),
              documentUid: path.basename(payload[i].name ?? ''),
              documentAdditionalDetails: DocumentAdditionalDetails(
                fileName: path.basename(payload[i].name ?? ''),
                fileType: "img_measurement_book",
                tenantId: response[i].tenantId,
              )));
        }
        List<PlatformFile> noPath = kIsWeb
            ? files.where((element) => element.bytes == null).toList()
            : files.where((element) => element.path == null).toList();

        // serverData = serverData.where((file) {
        //   return noPath.any(
        //       (ref) => ref.name == file.documentAdditionalDetails!.fileName);
        // }).toList();

        serverData = serverData.map((file) {
          if (noPath.any(
              (ref) => ref.name == file.documentAdditionalDetails!.fileName)) {
            // Return the WorkflowDocument with isActive: true
            return WorkflowDocument(
              documentUid: file.documentUid,
              documentType: file.documentType,
              fileStore: file.fileStore,
              fileStoreId: file.fileStoreId,
              id: file.id,
              tenantId: file.tenantId,
              indexing: file.indexing,
              isActive: true, // Set isActive to true
              documentAdditionalDetails: file.documentAdditionalDetails,
            );
          } else {
            // Return the WorkflowDocument with isActive: false
            return WorkflowDocument(
              documentUid: file.documentUid,
              documentType: file.documentType,
              fileStore: file.fileStore,
              fileStoreId: file.fileStoreId,
              id: file.id,
              tenantId: file.tenantId,
              indexing: file.indexing,
              isActive: false, // Set isActive to false
              documentAdditionalDetails: file.documentAdditionalDetails,
            );
          }
        }).toList();

        dataDocument.addAll(serverData);

        context.read<MeasurementDetailBloc>().add(
              MeasurementUploadDocumentBlocEvent(
                tenantId: '',
                workflowDocument: dataDocument,
              ),
            );
        Navigator.of(
          context,
          rootNavigator: true,
        ).popUntil(
          (route) => route is! PopupRoute,
        );
      } else {
        // serverData = serverData.where((file) {
        //   return files.any(
        //       (ref) => ref.name == file.documentAdditionalDetails!.fileName);
        // }).toList();
        serverData = serverData.map((file) {
          if (files.any(
              (ref) => ref.name == file.documentAdditionalDetails!.fileName)) {
            // Return the WorkflowDocument with isActive: true
            return WorkflowDocument(
              documentUid: file.documentUid,
              documentType: file.documentType,
              fileStore: file.fileStore,
              fileStoreId: file.fileStoreId,
              id: file.id,
              tenantId: file.tenantId,
              indexing: file.indexing,
              isActive: true, // Set isActive to true
              documentAdditionalDetails: file.documentAdditionalDetails,
            );
          } else {
            // Return the WorkflowDocument with isActive: false
            return WorkflowDocument(
              documentUid: file.documentUid,
              documentType: file.documentType,
              fileStore: file.fileStore,
              fileStoreId: file.fileStoreId,
              id: file.id,
              tenantId: file.tenantId,
              indexing: file.indexing,
              isActive: false, // Set isActive to false
              documentAdditionalDetails: file.documentAdditionalDetails,
            );
          }
        }).toList();

        dataDocument.addAll(serverData);
        context.read<MeasurementDetailBloc>().add(
              MeasurementUploadDocumentBlocEvent(
                tenantId: '',
                workflowDocument: dataDocument,
              ),
            );
      }
    } catch (ex) {
      Navigator.of(
        context,
        rootNavigator: true,
      ).popUntil(
        (route) => route is! PopupRoute,
      );
    }
    // serverData = serverData.where((item  ) => noPath.contains(item.documentAdditionalDetails!.fileName!)).toList();

// for (var element in serverData) {

//   dataDocument.add(WorkflowDocument(
//     indexing:
//               dataDocument.isEmpty ? 0 : (dataDocument.last.indexing! + 1),
//           isActive: true,
//           tenantId: response[i].tenantId,
//           fileStore: response[i].fileStoreId,
//           documentType: path.extension(element.name ?? ''),
//           documentUid: path.basename(files[i].path ?? ''),
//           documentAdditionalDetails: DocumentAdditionalDetails(
//             fileName: path.basename(files[i].path ?? ''),
//             fileType: "img_measurement_book",
//             tenantId: response[i].tenantId,
//           )
//   ));
// }
    // dataDocument.add(WorkflowDocument(
    //     indexing: dataDocument.isEmpty
    //         ? 0
    //         : (_selectedFiles.last.indexing! + 1),
    //     isActive: true,
    //     tenantId: _fileStoreList[i].tenantId,
    //     fileStore: _fileStoreList[i].fileStoreId,
    //     documentType: path.extension(files[i].path),
    //     documentUid: path.basename(files[i].path),
    //     documentAdditionalDetails: DocumentAdditionalDetails(
    //       fileName: path.basename(files[i].path),
    //       fileType: "img_measurement_book",
    //       tenantId: _fileStoreList[i].tenantId,
    //     )));
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);

    return MultiBlocListener(
      listeners: [
        BlocListener<MeasurementCrudBloc, MeasurementCrudState>(
          listener: (context, state) {
            state.maybeMap(
              orElse: () => {},
              loaded: (valueLoaded) {
                if (widget.type == MBScreen.update) {
                  context.read<MusterGetWorkflowBloc>().add(
                        FetchMBWorkFlowEvent(
                            tenantId: GlobalVariables.tenantId!,
                            mbNumber: widget.mbNumber),
                      );

                  context.read<MeasurementDetailBloc>().add(
                        MeasurementDetailBookBlocEvent(
                          tenantId: widget.tenantId!,
                          contractNumber: widget.contractNumber,
                          measurementNumber: widget.mbNumber,
                          screenType: widget.type,
                        ),
                      );
                  Navigator.of(
                    context,
                    rootNavigator: true,
                  ).popUntil(
                    (route) => route is! PopupRoute,
                  );
                } else if ((valueLoaded.measurement!.wfStatus == "SUBMITTED") &&
                    widget.type == MBScreen.create) {
                  Navigator.of(
                    context,
                    rootNavigator: true,
                  ).popUntil(
                    (route) => route is! PopupRoute,
                  );
                  context.router.popUntilRouteWithPath('home');
                } else if ((valueLoaded.measurement!.wfStatus == "DRAFTED") &&
                    widget.type == MBScreen.create) {
                  Navigator.of(
                    context,
                    rootNavigator: true,
                  ).popUntil(
                    (route) => route is! PopupRoute,
                  );
                }

                Notifiers.getToastMessage(
                    context,
                    t.translate(
                        "WF_UPDATE_SUCCESS_MB_${valueLoaded.measurement?.workflow?.action}"),
                    "SUCCESS");

                // Toast.showToast(
                //   context,
                //   message: t.translate(
                //       "WF_UPDATE_SUCCESS_MB_${valueLoaded.measurement?.workflow?.action}"),
                //   type: ToastType.success,
                // );

                context.read<MeasurementDetailBloc>().add(
                      MeasurementDetailBookBlocEvent(
                        tenantId: widget.tenantId!,
                        contractNumber: widget.contractNumber,
                        measurementNumber:
                            valueLoaded.measurement!.measurementNumber!,
                        screenType: MBScreen.update,
                      ),
                    );
              },
              error: (value) {
                Navigator.of(
                  context,
                  rootNavigator: true,
                ).popUntil(
                  (route) => route is! PopupRoute,
                );
                // Notifiers.getToastMessage(
                //     context, value.error.toString(), 'ERROR');

                Toast.showToast(
                  context,
                  message: value.error.toString(),
                  type: ToastType.error,
                );
              },
            );
          },
        ),
        BlocListener<MusterGetWorkflowBloc, MusterGetWorkflowState>(
          listener: (context, state) {
            state.maybeMap(
              orElse: () => const SizedBox.shrink(),
              loaded: (mbWorkFlow) {
                final g = mbWorkFlow.musterWorkFlowModel?.processInstances;
                if (g != null &&
                    g.first.nextActions != null &&
                    g.first.nextActions!.isNotEmpty) {
                  final data = g.first.nextActions!.first.roles?.join(',');

                  context.read<EmpHRMSBloc>().add(
                        EmpHRMSLoadBlocEvent(
                          isActive: true,
                          roles: data ?? "",
                          tenantId: widget.tenantId!,
                        ),
                      );
                }
              },
            );
          },
        ),
      ],
      child: DefaultTabController(
        length: 3,
        child: BlocBuilder<LocalizationBloc, LocalizationState>(
          builder: (context, state) {
            return Scaffold(
              backgroundColor: Theme.of(context).colorTheme.generic.background,

              // appBar: customAppBar(),
              // drawer: const MySideBar(),
              body: BlocBuilder<MeasurementDetailBloc, MeasurementDetailState>(
                builder: (context, state) {
                  return state.maybeMap(
                    orElse: () {
                      return const SizedBox.shrink();
                    },
                    loaded: (value) {
                      final dynamic mm;
                      if (widget.type == MBScreen.create) {
                        mm = null;
                      } else {
                        mm = value.data.first.documents
                            ?.map((d) => FileStoreModel(
                                  name: d.documentAdditionalDetails?.fileName,
                                  fileStoreId: d.fileStore,
                                  id: d.id,
                                  tenantId:
                                      d.documentAdditionalDetails?.tenantId,
                                ))
                            .toList();
                      }
                      return Stack(children: [
                        SingleChildScrollView(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Padding(
                                padding: EdgeInsets.symmetric(
                                  horizontal:
                                      Theme.of(context).spacerTheme.spacer4,
                                  vertical:
                                      Theme.of(context).spacerTheme.spacer4,
                                ),
                                child: Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      BackNavigationButton(
                                        backNavigationButtonThemeData:
                                            const BackNavigationButtonThemeData()
                                                .copyWith(
                                                    textColor: Theme.of(context)
                                                        .colorTheme
                                                        .primary
                                                        .primary2,
                                                    contentPadding:
                                                        EdgeInsets.zero,
                                                    context: context,
                                                    backButtonIcon: Icon(
                                                      Icons
                                                          .arrow_circle_left_outlined,
                                                      size: MediaQuery.of(
                                                                      context)
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
                                                    )),
                                        backButtonText: AppLocalizations.of(
                                                    context)
                                                .translate(i18.common.back) ??
                                            'Back',
                                        handleBack: () {
                                          context.router.popUntilRouteWithPath(
                                            widget.type == MBScreen.update
                                                ? 'measurement-inbox'
                                                : 'workOrder-inbox',
                                          );
                                        },
                                      ),
                                    ]),
                              ),

                              Padding(
                                padding: EdgeInsets.only(
                                  top: 0.0,
                                  left: Theme.of(context).spacerTheme.spacer4,
                                  // bottom:
                                  //     Theme.of(context).spacerTheme.spacer2,
                                ),
                                child: DigitTextBlock(
                                  heading: t.translate(
                                      i18.measurementBook.measurementBookTitle),
                                  //  headingStyle: Theme.of(context).digitTextTheme(context).headingXl,
                                ),
                              ),
                              ui_component.DigitCard(
                                margin: EdgeInsets.all(
                                    Theme.of(context).spacerTheme.spacer2),
                                cardType: CardType.primary,
                                padding: EdgeInsets.zero,
                                children: [
                                  ExpansionTile(
                                    collapsedIconColor: Theme.of(context)
                                        .colorTheme
                                        .text
                                        .secondary,
                                    iconColor: Theme.of(context)
                                        .colorTheme
                                        .primary
                                        .primary1,
                                    tilePadding: EdgeInsets.symmetric(
                                        horizontal: Theme.of(context)
                                            .spacerTheme
                                            .spacer2),
                                    // childrenPadding: EdgeInsets.zero,
                                    expandedCrossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    expandedAlignment: Alignment.topLeft,
                                    title: DigitTextBlock(
                                      subHeading: t.translate(
                                          i18.measurementBook.primaryDetails),
                                      subHeadingStyle: Theme.of(context)
                                          .digitTextTheme(context)
                                          .headingM,
                                    ),
                                    children: [
                                      ui_component.DigitCard(
                                          padding: const EdgeInsets.only(
                                              top: 0.0,
                                              left: 8.0,
                                              right: 8.0,
                                              bottom: 0.0),
                                          cardType: CardType.primary,
                                          children: [
                                            LabelValueList(
                                                maxLines: 3,
                                                labelFlex: 5,
                                                valueFlex: 5,
                                                items: primaryItems(t,
                                                        value.data, widget.type)
                                                    .entries
                                                    .map(
                                                  (e) {
                                                    return LabelValuePair(
                                                        label: e.key,
                                                        value: e.value);
                                                  },
                                                ).toList()),
                                            value.data.length > 1
                                                ? Button(
                                                    suffixIcon: Icons
                                                        .arrow_forward_outlined,
                                                    label: t.translate(i18
                                                        .measurementBook
                                                        .mbShowHistory),
                                                    onPressed: () {
                                                      context.router.push(
                                                        MBHistoryBookRoute(
                                                          contractNumber: widget
                                                              .contractNumber,
                                                          mbNumber:
                                                              widget.mbNumber,
                                                          tenantId:
                                                              widget.tenantId,
                                                          type: widget.type,
                                                        ),
                                                      );
                                                    },
                                                    type: ButtonType.link,
                                                    size: ButtonSize.large)
                                                : const SizedBox.shrink(),
                                          ]),
                                    ],
                                  ),
                                ],
                              ),

                              // tab
                              /////////////////////////
                              Padding(
                                padding: const EdgeInsets.only(
                                    top: 16.0,
                                    left: 8.0,
                                    right: 8.0,
                                    bottom: 0.0),
                                child: AnimatedBuilder(
                                  animation: _tabController.animation!,
                                  builder: (context, child) =>
                                      //Expanded(
                                      //child:
                                      DigitTabBar(
                                    tabBarThemeData:
                                        const DigitTabBarThemeData().copyWith(
                                            tabWidth: MediaQuery.sizeOf(context)
                                                    .width *
                                                0.32001,
                                            padding: const EdgeInsets.all(0)),
                                    tabs: [
                                      t.translate(i18.measurementBook.mbSor),
                                      t.translate(i18.measurementBook.mbNonSor),
                                      t.translate(
                                          i18.measurementBook.mbWorksitePhotos)
                                    ],
                                    onTabSelected: (index) {
                                      setState(() {
                                        tabIndex = index;
                                      });
                                      //  _tabController.animateTo(index);
                                    },
                                    initialIndex: tabIndex,
                                  ),
                                ),
                              ),

                              if (tabIndex == 0)
                                value.sor!.isEmpty
                                    ? Padding(
                                        padding: const EdgeInsets.only(
                                          left: 8.0,
                                          right: 8.0,
                                          bottom: 8.0,
                                          top: 0.0,
                                        ),
                                        child: ui_component.DigitCard(
                                          cardType: CardType.primary,
                                          children: [
                                            Center(
                                                child: EmptyImage(
                                              align: Alignment.center,
                                              label: t.translate(
                                                  i18.common.notFound),
                                            ))
                                          ],
                                        ),
                                      )
                                    : renderSor(value.sor!, "sor"),

                              if (tabIndex == 1)
                                value.nonSor!.isEmpty
                                    ? Padding(
                                        padding: const EdgeInsets.only(
                                          left: 8.0,
                                          right: 8.0,
                                          bottom: 8.0,
                                          top: 0.0,
                                        ),
                                        child: ui_component.DigitCard(
                                          cardType: CardType.primary,
                                          children: [
                                            Center(
                                              child: EmptyImage(
                                                align: Alignment.center,
                                                label: t.translate(
                                                    i18.common.notFound),
                                              ),
                                            ),
                                          ],
                                        ),
                                      )
                                    : renderSor(value.nonSor!, "NonSor"),

                              if (tabIndex == 2)
                                widget.type == MBScreen.create
                                    ? Padding(
                                        padding: const EdgeInsets.only(
                                            left: 8.0,
                                            right: 8.0,
                                            bottom: 8.0,
                                            top: 0.0),
                                        child: ui_component.DigitCard(
                                            cardType: CardType.primary,
                                            children: [
                                              Center(
                                                child: Column(
                                                  children: [
                                                    Padding(
                                                      padding: const EdgeInsets
                                                          .symmetric(
                                                          horizontal: 0.0),
                                                      child:
                                                          ui_label.LabeledField(
                                                        label:
                                                            "${AppLocalizations.of(context).translate(i18.measurementBook.workSitePhotos)}",
                                                        child: FileUploadWidget(
                                                          // onFileTap: (p0) {
                                                          //   print("hello");
                                                          // },
                                                          initialFiles: value
                                                                      .data
                                                                      .first
                                                                      .documents !=
                                                                  null
                                                              ? value.data.first
                                                                  .documents!
                                                                  .where((element) =>
                                                                      element
                                                                          .isActive ==
                                                                      true)
                                                                  .toList()
                                                                  .map((e) => PlatformFile(
                                                                      name: e
                                                                          .documentAdditionalDetails!
                                                                          .fileName!,
                                                                      size: 0))
                                                                  .toList()
                                                              : [],
                                                          noFileSelectedText:
                                                              t.translate(i18
                                                                  .common
                                                                  .noFileSelected),
                                                          allowMultiples: true,
                                                          label:
                                                              "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                                                          onFilesSelected:
                                                              (List<PlatformFile>
                                                                  files) {
                                                            Map<PlatformFile,
                                                                    String?>
                                                                fileErrors = {};

                                                            try {
                                                              const int
                                                                  fileSizeLimit =
                                                                  5 *
                                                                      1024 *
                                                                      1024; // 5 MB in bytes

                                                              // Iterate over each file and check the size
                                                              for (var file
                                                                  in files) {
                                                                if (file.size >
                                                                    fileSizeLimit) {
                                                                  fileErrors[
                                                                          file] =
                                                                      t.translate(i18
                                                                          .measurementBook
                                                                          .imageSize);
                                                                }
                                                              }

                                                              if (fileErrors
                                                                  .isEmpty) {
                                                                uploadDocument(
                                                                  files,
                                                                  context,
                                                                  value.data.first
                                                                              .documents !=
                                                                          null
                                                                      ? value
                                                                          .data
                                                                          .first
                                                                          .documents!
                                                                      : [],
                                                                );
                                                              }

                                                              return fileErrors;
                                                            } catch (e) {
                                                              return fileErrors;
                                                            }
                                                          },
                                                        ),
                                                      ),
                                                    ),
                                                    DigitTextBlock(
                                                        description:
                                                            t.translate(i18
                                                                .measurementBook
                                                                .mbPhotoInfo)),
                                                  ],
                                                ),
                                              ),
                                            ]),
                                      )
                                    : value.data.first.documents != null &&
                                            value.data.first.documents!.isEmpty
                                        ? !value.viewStatus
                                            ? Padding(
                                                padding: const EdgeInsets.only(
                                                    left: 8.0,
                                                    right: 8.0,
                                                    bottom: 8.0,
                                                    top: 0.0),
                                                child: ui_component.DigitCard(
                                                  cardType: CardType.primary,
                                                  children: [
                                                    Center(
                                                      child: Column(
                                                        crossAxisAlignment:
                                                            CrossAxisAlignment
                                                                .start,
                                                        mainAxisAlignment:
                                                            MainAxisAlignment
                                                                .center,
                                                        children: [
                                                          Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                    .symmetric(
                                                                    horizontal:
                                                                        0.0),
                                                            child: ui_label
                                                                .LabeledField(
                                                              label:
                                                                  "${AppLocalizations.of(context).translate(i18.measurementBook.workSitePhotos)}",
                                                              child:
                                                                  FileUploadWidget(
                                                                // onFileTap: (p0) {
                                                                //   print("hooo");
                                                                // },
                                                                initialFiles: value
                                                                            .data
                                                                            .first
                                                                            .documents !=
                                                                        null
                                                                    ? value
                                                                        .data
                                                                        .first
                                                                        .documents!
                                                                        .where((element) =>
                                                                            element.isActive ==
                                                                            true)
                                                                        .toList()
                                                                        .map((e) => PlatformFile(
                                                                            name:
                                                                                e.documentAdditionalDetails!.fileName!,
                                                                            size: 0))
                                                                        .toList()
                                                                    : [],
                                                                noFileSelectedText:
                                                                    t.translate(i18
                                                                        .common
                                                                        .noFileSelected),
                                                                allowMultiples:
                                                                    true,
                                                                label:
                                                                    "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                                                                onFilesSelected:
                                                                    (List<PlatformFile>
                                                                        files) {
                                                                  Map<PlatformFile,
                                                                          String?>
                                                                      fileErrors =
                                                                      {};

                                                                  try {
                                                                    const int
                                                                        fileSizeLimit =
                                                                        5 *
                                                                            1024 *
                                                                            1024; // 5 MB in bytes

                                                                    // Iterate over each file and check the size
                                                                    for (var file
                                                                        in files) {
                                                                      if (file.size >
                                                                          fileSizeLimit) {
                                                                        fileErrors[file] = t.translate(i18
                                                                            .measurementBook
                                                                            .imageSize);
                                                                      }
                                                                    }

                                                                    if (fileErrors
                                                                        .isEmpty) {
                                                                      uploadDocument(
                                                                        files,
                                                                        context,
                                                                        value.data.first.documents !=
                                                                                null
                                                                            ? value.data.first.documents!
                                                                            : [],
                                                                      );
                                                                    }

                                                                    return fileErrors;
                                                                  } catch (e) {
                                                                    return fileErrors;
                                                                  }
                                                                },
                                                              ),
                                                            ),
                                                          ),
                                                          // TODO:[text change]
                                                          DigitTextBlock(
                                                              description: t
                                                                  .translate(i18
                                                                      .measurementBook
                                                                      .mbPhotoInfo)),
                                                        ],
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                              )
                                            : Padding(
                                                padding: EdgeInsets.only(
                                                    left: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    right: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    bottom: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    top: 0.0),
                                                child: ui_component.DigitCard(
                                                    cardType: CardType.primary,
                                                    children: [
                                                      Center(
                                                        child: EmptyImage(
                                                          align:
                                                              Alignment.center,
                                                          label: t.translate(i18
                                                              .measurementBook
                                                              .noDocumentFound),
                                                        ),
                                                      )
                                                    ]),
                                              )
                                        : !value.viewStatus
                                            ? Padding(
                                                padding: EdgeInsets.only(
                                                    left: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    right: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    bottom: Theme.of(context)
                                                        .spacerTheme
                                                        .spacer2,
                                                    top: 0.0),
                                                child: ui_component.DigitCard(
                                                    cardType: CardType.primary,
                                                    children: [
                                                      Center(
                                                        child: Column(
                                                          crossAxisAlignment:
                                                              CrossAxisAlignment
                                                                  .start,
                                                          mainAxisAlignment:
                                                              MainAxisAlignment
                                                                  .center,
                                                          children: [
                                                            Padding(
                                                              padding:
                                                                  const EdgeInsets
                                                                      .symmetric(
                                                                      horizontal:
                                                                          0.0),
                                                              child: ui_label
                                                                  .LabeledField(
                                                                label:
                                                                    "${AppLocalizations.of(context).translate(i18.measurementBook.workSitePhotos)}",
                                                                child:
                                                                    FileUploadWidget(
                                                                  // onFileTap: (p0) {
                                                                  //   print("object");
                                                                  // },
                                                                  initialFiles: value
                                                                              .data
                                                                              .first
                                                                              .documents !=
                                                                          null
                                                                      ? value
                                                                          .data
                                                                          .first
                                                                          .documents!
                                                                          .where((element) =>
                                                                              element.isActive ==
                                                                              true)
                                                                          .toList()
                                                                          .map((e) => PlatformFile(
                                                                              name: e.documentAdditionalDetails!.fileName!,
                                                                              size: 0))
                                                                          .toList()
                                                                      : [],
                                                                  noFileSelectedText:
                                                                      t.translate(i18
                                                                          .common
                                                                          .noFileSelected),
                                                                  allowMultiples:
                                                                      true,
                                                                  label:
                                                                      "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                                                                  onFilesSelected:
                                                                      (List<PlatformFile>
                                                                          files) {
                                                                    Map<PlatformFile,
                                                                            String?>
                                                                        fileErrors =
                                                                        {};

                                                                    try {
                                                                      const int
                                                                          fileSizeLimit =
                                                                          5 *
                                                                              1024 *
                                                                              1024; // 5 MB in bytes

                                                                      // Iterate over each file and check the size
                                                                      for (var file
                                                                          in files) {
                                                                        if (file.size >
                                                                            fileSizeLimit) {
                                                                          fileErrors[file] = t.translate(i18
                                                                              .measurementBook
                                                                              .imageSize);
                                                                        }
                                                                      }

                                                                      if (fileErrors
                                                                          .isEmpty) {
                                                                        uploadDocument(
                                                                          files,
                                                                          context,
                                                                          value.data.first.documents != null
                                                                              ? value.data.first.documents!
                                                                              : [],
                                                                        );
                                                                      }

                                                                      return fileErrors;
                                                                    } catch (e) {
                                                                      return fileErrors;
                                                                    }
                                                                  },
                                                                ),
                                                              ),
                                                            ),
                                                            DigitTextBlock(
                                                                description: t
                                                                    .translate(i18
                                                                        .measurementBook
                                                                        .mbPhotoInfo)),
                                                          ],
                                                        ),
                                                      ),
                                                    ]),
                                              )
                                            : Padding(
                                                padding: const EdgeInsets.only(
                                                    left: 8.0,
                                                    right: 8.0,
                                                    bottom: 8.0,
                                                    top: 0.0),
                                                child: ui_component.DigitCard(
                                                  cardType: CardType.primary,
                                                  children: List.generate(
                                                      value
                                                          .data
                                                          .first
                                                          .documents!
                                                          .length, (index) {
                                                    if (index == 0) {
                                                      return Padding(
                                                        padding:
                                                            const EdgeInsets
                                                                .only(
                                                                bottom: 8.0),
                                                        child: Column(
                                                          children: [
                                                            Padding(
                                                              padding:
                                                                  const EdgeInsets
                                                                      .only(
                                                                      bottom:
                                                                          16.0),
                                                              child: InfoCard(
                                                                title: t
                                                                    .translate(i18
                                                                        .common
                                                                        .info),
                                                                type: InfoType
                                                                    .info,
                                                                description: t
                                                                    .translate(i18
                                                                        .measurementBook
                                                                        .infoImageTip),
                                                              ),
                                                            ),
                                                            InkWell(
                                                              onTap: () =>
                                                                  CommonMethods()
                                                                      .onTapOfAttachment(
                                                                mm![index],
                                                                mm![index]
                                                                    .tenantId!,
                                                                context,
                                                                roleType: RoleType
                                                                    .employee,
                                                              ),
                                                              child: Chip(
                                                                labelPadding:
                                                                    const EdgeInsets
                                                                        .all(
                                                                        10),
                                                                label: SizedBox(
                                                                  width: MediaQuery
                                                                          .sizeOf(
                                                                              context)
                                                                      .width,
                                                                  height: 25,
                                                                  child: Text(
                                                                    mm![index]
                                                                        .name
                                                                        .toString(),
                                                                    maxLines: 1,
                                                                    overflow:
                                                                        TextOverflow
                                                                            .ellipsis,
                                                                    style: Theme.of(
                                                                            context)
                                                                        .textTheme
                                                                        .bodyMedium,
                                                                  ),
                                                                ),
                                                              ),
                                                            ),
                                                          ],
                                                        ),
                                                      );
                                                    } else {
                                                      return Padding(
                                                        padding:
                                                            const EdgeInsets
                                                                .only(
                                                                bottom: 8.0),
                                                        child: InkWell(
                                                          onTap: () =>
                                                              CommonMethods()
                                                                  .onTapOfAttachment(
                                                            mm![index],
                                                            mm![index]
                                                                .tenantId!,
                                                            context,
                                                            roleType: RoleType
                                                                .employee,
                                                          ),
                                                          child: Chip(
                                                            labelPadding:
                                                                const EdgeInsets
                                                                    .all(10),
                                                            // padding: const EdgeInsets.symmetric(horizontal: 12.0, vertical: 8.0),
                                                            label: SizedBox(
                                                              width: MediaQuery
                                                                      .sizeOf(
                                                                          context)
                                                                  .width,
                                                              height: 25,
                                                              child: Text(
                                                                mm![index]
                                                                    .name
                                                                    .toString(),
                                                                maxLines: 1,
                                                                softWrap: true,
                                                                overflow:
                                                                    TextOverflow
                                                                        .ellipsis,
                                                                style: Theme.of(
                                                                        context)
                                                                    .textTheme
                                                                    .bodyMedium,
                                                              ),
                                                            ),
                                                          ),
                                                        ),
                                                      );
                                                    }
                                                  }).toList(),
                                                ),
                                              ),

                              // ////////////////
                              SizedBox(
                                height: Theme.of(context).spacerTheme.spacer4,
                              ),
                              widget.type == MBScreen.update
                                  ?
                                  //workflow
                                  BlocBuilder<MusterGetWorkflowBloc,
                                      MusterGetWorkflowState>(
                                      builder: (context, state) {
                                        return state.maybeMap(
                                          orElse: SizedBox.shrink,
                                          loaded: (value) {
                                            List<ProcessInstances>
                                                modifiedData = value
                                                    .musterWorkFlowModel!
                                                    .processInstances!
                                                    .where((element) =>
                                                        element.action !=
                                                        Constants.saveAsDraft)
                                                    .toList();
                                            // ..insert(0, value
                                            // .musterWorkFlowModel!
                                            // .processInstances!.first);
                                            if (modifiedData.isNotEmpty &&
                                                (modifiedData.first
                                                            .nextActions !=
                                                        null &&
                                                    modifiedData
                                                        .first
                                                        .nextActions!
                                                        .isNotEmpty)) {
                                              modifiedData = [
                                                ...[modifiedData.first],
                                                ...modifiedData
                                              ];
                                            } else if (modifiedData
                                                .isNotEmpty) {
                                              modifiedData = [
                                                ...[modifiedData.first],
                                                ...modifiedData
                                              ];
                                            }
                                            timeLineAttributes.clear();

                                            timeLineAttributes = modifiedData
                                                .mapIndexed((i, e) =>
                                                    DigitTimelineOptions(
                                                      title: t.translate((i ==
                                                                  0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? e.workflowState
                                                                      ?.state ==
                                                                  "EDIT_RE_SUBMIT"
                                                              ? 'WF_MB_STATUS_${e.workflowState?.state}'
                                                              : 'WF_MB_STATUS_${e.workflowState?.state}'
                                                          : i == 0
                                                              ? e.workflowState
                                                                          ?.state ==
                                                                      "EDIT_RE_SUBMIT"
                                                                  ? 'WF_MB_STATUS_${e.workflowState?.state}'
                                                                  : 'WF_MB_STATUS_${e.workflowState?.state}'
                                                              : 'WF_MB_STATUS_${e.action}'),
                                                      subTitle: (i == 0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? DateFormats
                                                              .getTimeLineDate(e
                                                                      .auditDetails
                                                                      ?.lastModifiedTime ??
                                                                  0)
                                                          : i != 0
                                                              ? DateFormats
                                                                  .getTimeLineDate(e
                                                                          .auditDetails
                                                                          ?.lastModifiedTime ??
                                                                      0)
                                                              : null,
                                                      isCurrentState:
                                                          e.action == "APPROVE"
                                                              ? false
                                                              : i == 0,
                                                      comments: (i == 0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? e.comment
                                                          : i != 0
                                                              ? e.comment
                                                              : null,
                                                      documents: (i == 0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? e.documents != null &&
                                                                  e.documents!
                                                                      .isNotEmpty
                                                              ? e.documents
                                                                  ?.map((d) => FileStoreModel(
                                                                      name: '',
                                                                      fileStoreId: d
                                                                          .documentUid))
                                                                  .toList()
                                                              : null
                                                          : i != 0
                                                              ? e.documents !=
                                                                          null &&
                                                                      e.documents!
                                                                          .isNotEmpty
                                                                  ? e.documents
                                                                      ?.map((d) => FileStoreModel(
                                                                          name: '',
                                                                          fileStoreId: d.documentUid))
                                                                      .toList()
                                                                  : null
                                                              : null,
                                                      assignee: (i == 0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? e.assigner?.name
                                                          : i != 0
                                                              ? e.assigner?.name
                                                              : null,
                                                      mobileNumber: (i == 0 &&
                                                              e.action ==
                                                                  "APPROVE")
                                                          ? e.assigner != null
                                                              ? '+91-${e.assigner?.mobileNumber}'
                                                              : null
                                                          : i != 0
                                                              ? e.assigner !=
                                                                      null
                                                                  ? '+91-${e.assigner?.mobileNumber}'
                                                                  : null
                                                              : null,
                                                    ))
                                                .toList();
                                            timeLineAttributes =
                                                timeLineAttributes.reversed
                                                    .toList();

                                            return timeLineAttributes.isNotEmpty
                                                ? ui_component.DigitCard(
                                                    margin: const EdgeInsets
                                                        .symmetric(
                                                        horizontal: 8),
                                                    cardType:
                                                        CardType.secondary,
                                                    children: [
                                                      LabelValueList(
                                                        heading: t.translate(i18
                                                            .common
                                                            .workflowTimeline),
                                                        items: const [],
                                                      ),
                                                      Builder(
                                                        builder: (context) {
                                                          return TimelineMolecule(
                                                            steps:
                                                                List.generate(
                                                              timeLineAttributes
                                                                  .length,
                                                              (i) =>
                                                                  TimelineStep(
                                                                additionalWidgets: timeLineAttributes[i].documents !=
                                                                            null &&
                                                                        timeLineAttributes[i]
                                                                            .documents!
                                                                            .isNotEmpty
                                                                    ? List.generate(
                                                                        timeLineAttributes[i].documents!.length,
                                                                        (index) => InkWell(
                                                                              onTap: () => CommonMethods().onTapOfAttachment(
                                                                                  timeLineAttributes[i].documents![index],
                                                                                  timeLineAttributes[i].documents![index].tenantId == null
                                                                                      ? GlobalVariables.roleType == RoleType.employee
                                                                                          ? GlobalVariables.tenantId!
                                                                                          : GlobalVariables.stateInfoListModel!.code.toString()
                                                                                      : timeLineAttributes[i].documents![index].tenantId!,
                                                                                  // "od.testing",
                                                                                  context,
                                                                                  roleType: GlobalVariables.roleType == RoleType.employee ? RoleType.employee : RoleType.cbo),
                                                                              child: Container(
                                                                                  width: 50,
                                                                                  margin: const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
                                                                                  child: Wrap(runSpacing: 5, spacing: 8, children: [
                                                                                    Image.asset('assets/png/attachment.png'),
                                                                                    Text(
                                                                                      AppLocalizations.of(context).translate(timeLineAttributes[i].documents![index].name.toString()),
                                                                                      maxLines: 2,
                                                                                      overflow: TextOverflow.ellipsis,
                                                                                    )
                                                                                  ])),
                                                                            )).toList()
                                                                    : [],
                                                                description: [
                                                                  timeLineAttributes[
                                                                              i]
                                                                          .subTitle ??
                                                                      '',
                                                                  timeLineAttributes[
                                                                              i]
                                                                          .assignee ??
                                                                      '',
                                                                  timeLineAttributes[
                                                                              i]
                                                                          .mobileNumber ??
                                                                      '',
                                                                      timeLineAttributes[
                                                                              i]
                                                                          .comments ??
                                                                      '',
                                                                ],
                                                                label:
                                                                    timeLineAttributes[
                                                                            i]
                                                                        .title,
                                                                state: timeLineAttributes[
                                                                            i]
                                                                        .isCurrentState
                                                                    ? TimelineStepState
                                                                        .present
                                                                    : TimelineStepState
                                                                        .completed,
                                                              ),
                                                            ).toList(),
                                                          );
                                                        },
                                                      ),
                                                    ],
                                                  )
                                                : const SizedBox.shrink();

                                            //
                                          },
                                        );
                                      },
                                    )
                                  : const SizedBox.shrink(),
                              const SizedBox(
                                height: 200,
                              ),
                            ],
                          ),
                        ),
                        BlocBuilder<MeasurementDetailBloc,
                            MeasurementDetailState>(
                          builder: (context, state) {
                            return state.maybeMap(
                              orElse: () {
                                return const SizedBox.shrink();
                              },
                              loaded: (value) {
                                if (widget.type == MBScreen.update) {
                                  return BlocBuilder<MusterGetWorkflowBloc,
                                      MusterGetWorkflowState>(
                                    builder: (context, state) {
                                      return state.maybeMap(
                                        orElse: () => const SizedBox.shrink(),
                                        loaded: (mbWorkFlow) {
                                          final g = mbWorkFlow
                                              .musterWorkFlowModel
                                              ?.processInstances;

                                          return DigitBottomSheet(
                                            primaryActionLabel: t.translate(
                                                i18.measurementBook.mbAction),
                                            onPrimaryAction: (g != null &&
                                                    (g.first.nextActions !=
                                                            null &&
                                                        g.first.nextActions!
                                                            .isEmpty))
                                                ? null
                                                : (ctx) {
                                                    // Navigator.of(context).pop();
                                                    showDialog(
                                                      context: context,
                                                      builder: (context) =>
                                                          CommonButtonCard(
                                                        g: mbWorkFlow
                                                            .musterWorkFlowModel
                                                            ?.processInstances,
                                                        contractNumber: widget
                                                            .contractNumber,
                                                        mbNumber:
                                                            widget.mbNumber,
                                                        type: widget.type,
                                                      ),
                                                    );
                                                  },
                                            fixedHeight: 200,
                                            initialHeightPercentage: 30,
                                            content: Column(
                                              mainAxisAlignment:
                                                  MainAxisAlignment
                                                      .spaceBetween,
                                              mainAxisSize: MainAxisSize.min,
                                              children: [
                                                Container(
                                                  decoration:
                                                      const BoxDecoration(
                                                    border: Border(
                                                      bottom: BorderSide(),
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 8.0),
                                                    child: ListTile(
                                                      title: Text(
                                                        // "Total SOR Amount",
                                                        t.translate(i18
                                                            .measurementBook
                                                            .totalSorAmount),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM
                                                            .copyWith(
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorScheme
                                                                  .secondary,
                                                            ),
                                                      ),
                                                      subtitle: Text(
                                                        t.translate(i18
                                                            .measurementBook
                                                            .forCurrentEntry),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .bodyS
                                                            .copyWith(
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorScheme
                                                                  .secondary,
                                                            ),
                                                      ),
                                                      trailing: Text(
                                                        value.data.first
                                                            .totalSorAmount!
                                                            .toDouble()
                                                            .toStringAsFixed(2),
                                                        // totalSorAmount.toDouble().toStringAsFixed(2),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingL
                                                            .copyWith(
                                                              color:
                                                                  Colors.black,
                                                            ),
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                const SizedBox(
                                                  height: 20,
                                                ),
                                                Container(
                                                  decoration:
                                                      const BoxDecoration(
                                                    border: Border(
                                                      bottom: BorderSide(),
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 8.0),
                                                    child: ListTile(
                                                      title: Text(
                                                        // "Total Non SOR Amount",
                                                        t.translate(i18
                                                            .measurementBook
                                                            .totalNonSorAmount),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM
                                                            .copyWith(
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorScheme
                                                                  .secondary,
                                                            ),
                                                      ),
                                                      subtitle: Text(
                                                        t.translate(i18
                                                            .measurementBook
                                                            .forCurrentEntry),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .bodyS
                                                            .copyWith(
                                                              color: Theme.of(
                                                                      context)
                                                                  .colorScheme
                                                                  .secondary,
                                                            ),
                                                      ),
                                                      trailing: Text(
                                                        value.data.first
                                                            .totalNorSorAmount!
                                                            .toDouble()
                                                            .toStringAsFixed(2),
                                                        // totalNonSorAmount.toDouble().toStringAsFixed(2),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingL
                                                            .copyWith(
                                                              color:
                                                                  Colors.black,
                                                            ),
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                const SizedBox(
                                                  height: 15,
                                                ),
                                                Container(
                                                  //  height: 80,
                                                  width:
                                                      MediaQuery.sizeOf(context)
                                                          .width,
                                                  decoration: BoxDecoration(
                                                    //color: Colors.red,
                                                    borderRadius:
                                                        const BorderRadius.all(
                                                      Radius.circular(10),
                                                    ),
                                                    border: Border.all(
                                                      color: Colors.grey,
                                                      width: 2,
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding: const EdgeInsets
                                                        .symmetric(
                                                        horizontal: 8.0),
                                                    child: Row(
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .spaceBetween,
                                                      children: [
                                                        Expanded(
                                                          flex: 4,
                                                          child: ListTile(
                                                            title: Text(
                                                              // "Total MB Amount",
                                                              t.translate(i18
                                                                  .measurementBook
                                                                  .totalMbAmount),
                                                              style: Theme.of(
                                                                      context)
                                                                  .digitTextTheme(
                                                                      context)
                                                                  .headingM
                                                                  .copyWith(
                                                                    color: Theme.of(
                                                                            context)
                                                                        .colorScheme
                                                                        .secondary,
                                                                  ),
                                                            ),
                                                            subtitle: Text(
                                                              t.translate(i18
                                                                  .measurementBook
                                                                  .forCurrentEntry),
                                                              style: Theme.of(
                                                                      context)
                                                                  .digitTextTheme(
                                                                      context)
                                                                  .bodyS
                                                                  .copyWith(
                                                                    color: Theme.of(
                                                                            context)
                                                                        .colorScheme
                                                                        .secondary,
                                                                  ),
                                                            ),
                                                          ),
                                                        ),
                                                        Expanded(
                                                            flex: 4,
                                                            child: Row(
                                                              mainAxisAlignment:
                                                                  MainAxisAlignment
                                                                      .end,
                                                              children: [
                                                                Text(
                                                                  // '3456',
                                                                  value
                                                                      .data
                                                                      .first
                                                                      .totalAmount!
                                                                      .roundToDouble()
                                                                      .toStringAsFixed(
                                                                          2),
                                                                  // mbAmount.roundToDouble().toStringAsFixed(2),
                                                                  style: Theme.of(
                                                                          context)
                                                                      .digitTextTheme(
                                                                          context)
                                                                      .headingL
                                                                      .copyWith(
                                                                        color: Colors
                                                                            .black,
                                                                      ),
                                                                ),
                                                              ],
                                                            )),
                                                      ],
                                                    ),
                                                  ),
                                                ),
                                              ],
                                            ),
                                          );
                                        },
                                      );
                                    },
                                  );
                                } else {
                                  return BlocBuilder<BusinessWorkflowBloc,
                                      BusinessGetWorkflowState>(
                                    builder: (context, state) {
                                      return state.maybeMap(
                                        orElse: () => const SizedBox.shrink(),
                                        loaded: (business) {
                                          const g = null;
                                          final bk = business
                                                  .businessWorkFlowModel!
                                                  .businessServices ??
                                              [];

                                          return DigitBottomSheet(
                                            primaryActionLabel: t.translate(
                                                i18.measurementBook.mbAction),
                                            onPrimaryAction: (ctx) {
                                              // Navigator.of(context).pop();
                                              showDialog(
                                                context: context,
                                                builder: (context) =>
                                                    CommonButtonCard(
                                                  g: g,
                                                  bs: bk,
                                                  contractNumber:
                                                      widget.contractNumber,
                                                  mbNumber: widget.mbNumber,
                                                  type: widget.type,
                                                ),
                                              );
                                            },
                                            fixedHeight: 200,
                                            initialHeightPercentage: 30,
                                            content: Column(
                                              mainAxisAlignment:
                                                  MainAxisAlignment
                                                      .spaceBetween,
                                              mainAxisSize: MainAxisSize.min,
                                              children: [
                                                Container(
                                                  decoration:
                                                      const BoxDecoration(
                                                    border: Border(
                                                      bottom: BorderSide(),
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 8.0),
                                                    child: ListTile(
                                                      title: Text(
                                                        // "Total SOR Amount",
                                                        t.translate(i18
                                                            .measurementBook
                                                            .totalSorAmount),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM,
                                                      ),
                                                      subtitle: Text(
                                                        t.translate(i18
                                                            .measurementBook
                                                            .forCurrentEntry),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .bodyS,
                                                      ),
                                                      trailing: Text(
                                                        value.data.first
                                                            .totalSorAmount!
                                                            .toDouble()
                                                            .toStringAsFixed(2),
                                                        // totalSorAmount.toDouble().toStringAsFixed(2),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM,
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                const SizedBox(
                                                  height: 20,
                                                ),
                                                Container(
                                                  decoration:
                                                      const BoxDecoration(
                                                    border: Border(
                                                      bottom: BorderSide(),
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            bottom: 8.0),
                                                    child: ListTile(
                                                      title: Text(
                                                        // "Total Non SOR Amount",
                                                        t.translate(i18
                                                            .measurementBook
                                                            .totalNonSorAmount),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM,
                                                      ),
                                                      subtitle: Text(
                                                        t.translate(i18
                                                            .measurementBook
                                                            .forCurrentEntry),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .bodyS,
                                                      ),
                                                      trailing: Text(
                                                        // "23.98",
                                                        value.data.first
                                                            .totalNorSorAmount!
                                                            .toDouble()
                                                            .toStringAsFixed(2),
                                                        // totalNonSorAmount.toDouble().toStringAsFixed(2),
                                                        style: Theme.of(context)
                                                            .digitTextTheme(
                                                                context)
                                                            .headingM,
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                const SizedBox(
                                                  height: 15,
                                                ),
                                                Container(
                                                  //  height: 80,
                                                  width:
                                                      MediaQuery.sizeOf(context)
                                                          .width,
                                                  decoration: BoxDecoration(
                                                    //color: Colors.red,
                                                    borderRadius:
                                                        const BorderRadius.all(
                                                      Radius.circular(10),
                                                    ),
                                                    border: Border.all(
                                                      color: Colors.grey,
                                                      width: 2,
                                                    ),
                                                  ),
                                                  child: Padding(
                                                    padding: const EdgeInsets
                                                        .symmetric(
                                                        horizontal: 8.0),
                                                    child: Row(
                                                      mainAxisAlignment:
                                                          MainAxisAlignment
                                                              .spaceBetween,
                                                      children: [
                                                        Expanded(
                                                          flex: 4,
                                                          child: ListTile(
                                                            title: Text(
                                                              // "Total MB Amount",
                                                              t.translate(i18
                                                                  .measurementBook
                                                                  .totalMbAmount),
                                                              style: Theme.of(
                                                                      context)
                                                                  .digitTextTheme(
                                                                      context)
                                                                  .headingM,
                                                            ),
                                                            subtitle: Text(
                                                              t.translate(i18
                                                                  .measurementBook
                                                                  .forCurrentEntry),
                                                              style: Theme.of(
                                                                      context)
                                                                  .digitTextTheme(
                                                                      context)
                                                                  .bodyS,
                                                            ),
                                                          ),
                                                        ),
                                                        Expanded(
                                                            flex: 4,
                                                            child: Row(
                                                              mainAxisAlignment:
                                                                  MainAxisAlignment
                                                                      .end,
                                                              children: [
                                                                Text(
                                                                  // '3456',
                                                                  value
                                                                      .data
                                                                      .first
                                                                      .totalAmount!
                                                                      .roundToDouble()
                                                                      .toStringAsFixed(
                                                                          2),
                                                                  // mbAmount.roundToDouble().toStringAsFixed(2),
                                                                  style: Theme.of(
                                                                          context)
                                                                      .digitTextTheme(
                                                                          context)
                                                                      .headingM,
                                                                ),
                                                              ],
                                                            )),
                                                      ],
                                                    ),
                                                  ),
                                                ),
                                              ],
                                            ),
                                          );
                                        },
                                      );
                                    },
                                  );
                                }
                              },
                              loading: (value) {
                                return const SizedBox.shrink();
                              },
                            );
                          },
                        ),
                      ]);
                    },
                    loading: (value) {
                      return Center(
                        child: shg_loader.Loaders.circularLoader(context),
                      );
                    },
                    error: (value) {
                      return Center(
                        child: EmptyImage(
                          align: Alignment.center,
                          label: t.translate(i18.common.wentWrong),
                        ),
                      );
                    },
                  );
                },
              ),
            );
          },
        ),
      ),
    );
  }

  Map<String, dynamic> primaryItems(
      AppLocalizations t, List<FilteredMeasurements> s, MBScreen mbScreen) {
    if (mbScreen == MBScreen.create) {
      return {
        t.translate(i18.common.musterRollId): s.first.musterRollNumber ?? "NA",

        t.translate(i18.measurementBook.measurementPeriod):
            "${s.first.startDate != null ? DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(s.first.startDate!)) : "NA"} - ${s.first.endDate != null ? DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(s.first.endDate!)) : "NA"}",
        t.translate(i18.attendanceMgmt.projectDesc): s.first.measures!.first
                .contracts!.first.contractAdditionalDetails?.projectDesc ??
            "NA",

        // "SLA Days remaining": 2,
      };
    } else {
      return {
        t.translate(i18.measurementBook.mbNumber): s.first.mbNumber ?? "NA",
        t.translate(i18.common.musterRollId): s.first.musterRollNumber ?? "NA",

        t.translate(i18.measurementBook.measurementPeriod):
            "${s.first.startDate != null ? DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(s.first.startDate!)) : "NA"} - ${s.first.endDate != null ? DateFormat('dd/MM/yyyy').format(DateTime.fromMillisecondsSinceEpoch(s.first.endDate!)) : "NA"}",
        t.translate(i18.attendanceMgmt.projectDesc): s.first.measures!.first
                .contracts!.first.contractAdditionalDetails?.projectDesc ??
            "NA",

        // "SLA Days remaining": 2,
      };
    }
  }

  double tabViewHeight(int sork, int nonSork, int photo) {
    switch (_tabController.index) {
      case 0:
        return sork == 0
            ? 300
            : sork == 1
                ? (sork * 500)
                : (sork * 500) + (sork * 20);
      case 1:
        return nonSork == 0
            ? 300
            : nonSork == 1
                ? (nonSork * 500)
                : (nonSork * 500) + (sork * 20);
      case 2:
        return photoSize(photo);
      default:
        return 350.0;
    }
  }

  double photoSize(int photok) {
    switch (photok) {
      case 1:
        return (photok * 115) + 132;
      case 2:
        return (photok * 100) + 120;
      case 3:
        return (photok * 100) + 90;
      case 4:
        return (photok * 90) + 100;
      case 5:
        return (photok * 86.2) + 100;

      default:
        return 350;
    }
  }

  Widget sorCard(
    AppLocalizations t,
    BuildContext ctx,
    int index, {
    List<FilteredMeasurementsMeasure>? magic,
    List<FilteredMeasurementsMeasure>? preSorNonSor,
    required String type,
    required String sorNonSorId,
    required String cardLevel,
    // required TextEditingController consumedQty,
    // required TextEditingController currentAmt,
  }) {
    List<FilteredMeasurementsEstimate> line = magic!.map(
      (e) {
        return e.contracts!.first.estimates!.first;
      },
    ).toList();
    String noOfQty = line.fold("0.0000", (sum, obj) {
      double m = double.parse(obj.noOfunit!.toString()).toDouble();
      return double.parse((double.parse(sum) + m.toDouble()).toString())
          .toStringAsFixed(4);
    });

    final String preConumed = preSorNonSor == null
        ? "0.0000"
        : preSorNonSor.fold("0.0000", (sum, obj) {
            double m = obj.contracts!.first.estimates!.first.isDeduction == true
                ? -(obj.cumulativeValue!)
                : (obj.cumulativeValue!);
            return double.parse((double.parse(sum) + m.toDouble()).toString())
                .toStringAsFixed(4);
          });

    return ui_component.DigitCard(
        margin: const EdgeInsets.only(top: 0, bottom: 8, left: 8, right: 8),
        cardType: CardType.primary,
        children: [
          LabelValueList(
              heading: "$cardLevel ${index + 1}",
              maxLines: 3,
              labelFlex: 5,
              valueFlex: 5,
              items: [
                LabelValuePair(
                    label: t.translate(i18.measurementBook.description),
                    value: magic.first.contracts!.first.estimates!.first.name ??
                        ""),
                LabelValuePair(
                    label: t.translate(i18.measurementBook.unit),
                    value: line[0].uom ?? ''),
                LabelValuePair(
                    label: t.translate(i18.measurementBook.rate),
                    value: line[0].unitRate == null
                        ? 0.00.toString()
                        : double.parse(line[0].unitRate!.toString())
                            .toStringAsFixed(2)),
                LabelValuePair(
                    label: t.translate(i18.measurementBook.approvedQty),
                    value: noOfQty),
                LabelValuePair(
                    label:
                        "${t.translate(i18.measurementBook.preConsumedKey)}\n${t.translate(i18.measurementBook.preConsumedPre)}",
                    value: preSorNonSor == null ? "0.0000" : preConumed),
              ]),

          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.only(bottom: 8.0),
                child: Text(t.translate(i18.measurementBook.currentMBEntry),
                    style: Theme.of(context).textTheme.headlineSmall),
              ),
              Container(
                padding: const EdgeInsets.all(5.0),
                height: 50,
                decoration: BoxDecoration(
                  border: Border.all(
                    color: Theme.of(context).colorTheme.paper.secondary,
                    width: 2.0,
                  ),
                  borderRadius: BorderRadius.circular(1),
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Padding(
                      padding: const EdgeInsets.only(left: 4.0),
                      child: Text(
                        (magic.fold(0.0, (sum, obj) {
                          double m;
                          if (obj.contracts?.first.estimates?.first
                                  .isDeduction ==
                              false) {
                            m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
                              double mk = double.parse(ob.quantity!.toString());
                              return subSum + mk;
                            });
                          } else {
                            m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
                              double mr = double.parse(ob.quantity!.toString());
                              return subSum + mr;
                            });
                            m = -m;
                          }
                          return sum + m;
                        })).toStringAsFixed(4),
                        style: const TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.w400,
                        ),
                        maxLines: 3,
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        showDialog(
                          context: ctx,
                          builder: (_) {
                            return HorizontalCardListDialog(
                              lineItems: magic,
                              index: index,
                              type: type,
                              noOfUnit: noOfQty,
                              cummulativePrevQty: preSorNonSor == null
                                  ? 0.0000
                                  : preSorNonSor.fold(0.0000, (sum, obj) {
                                      double m = obj.contracts!.first.estimates!
                                                  .first.isDeduction ==
                                              true
                                          ? -(obj.cumulativeValue!)
                                          : (obj.cumulativeValue!);
                                      return sum + m.toDouble();
                                    }),
                              sorId: sorNonSorId,
                            );
                          },
                        );
                      },
                      child: Padding(
                        padding: const EdgeInsets.only(right: 0.0),
                        child: Icon(
                          Icons.add_circle,
                          size: 30,
                          color: Theme.of(context).colorTheme.primary.primary1,
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),

          // // end
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.only(top: 8.0, bottom: 5.0),
                child: Text(
                  t.translate(i18.measurementBook.mbAmtCurrentEntry),
                  style: Theme.of(context).textTheme.headlineSmall,
                  textScaler: const TextScaler.linear(0.99),
                ),
              ),
              Container(
                width: MediaQuery.sizeOf(context).width,
                height: 50,
                padding: const EdgeInsets.only(
                    top: 10.0, left: 5.0, right: 5.0, bottom: 10),
                decoration: BoxDecoration(
                  border: Border.all(
                    color: Theme.of(context).colorTheme.paper.secondary,
                    width: 2.0,
                  ),
                  borderRadius: BorderRadius.circular(1),
                ),
                child: Padding(
                  padding: const EdgeInsets.only(left: 4.0),
                  child: Text(
                    (magic.fold(0.0, (sum, obj) {
                      double m = obj.mbAmount != null
                          ? (obj.mbAmount != null && obj.mbAmount! < 0)
                              ? (obj.mbAmount! * (-1))
                              : obj.mbAmount!
                          : 0.00;
                      if (obj.contracts?.first.estimates?.first.isDeduction ==
                          true) {
                        m = -(m); // Negate the amount for deductions
                      } else {
                        m = (m);
                      }
                      return sum + m;
                    })).toStringAsFixed(2),
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w400,
                    ),
                    maxLines: 3,
                  ),
                ),
              ),
            ],
          ),

//
        ]);
  }

  void _openBottomSheet(
    AppLocalizations t,
    BuildContext context,
    double totalSorAmount,
    double totalNonSorAmount,
    double mbAmount,
    List<ProcessInstances>? processInstances,
    String contractNumber,
    String mbNumber,
    MBScreen type,
    List<BusinessServices>? bs,
    bool showBtn,
    String workorderStatus,
    String estimateStatus,
    bool previousMBStatus,
  ) {
    showModalBottomSheet(
      isScrollControlled: true,
      isDismissible: true,
      enableDrag: true,
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.vertical(top: Radius.circular(20))),
      context: context,
      builder: (BuildContext context) {
        return Padding(
          padding: const EdgeInsets.only(
              left: 16.0, right: 16.0, top: 16.0, bottom: 0.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            mainAxisSize: MainAxisSize.min,
            children: [
              const Center(
                child: SizedBox(
                  width: 100,
                  child: DigitDivider(
                    dividerType: DividerType.large,
                  ),
                ),
              ),
              Container(
                decoration: const BoxDecoration(
                  border: Border(
                    bottom: BorderSide(),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 8.0),
                  child: ListTile(
                    title: Text(
                      t.translate(i18.measurementBook.totalSorAmount),
                      maxLines: 1,
                      style: Theme.of(context).textTheme.headlineSmall,
                    ),
                    subtitle: Text(
                      t.translate(i18.measurementBook.forCurrentEntry),
                      style: Theme.of(context).textTheme.bodySmall,
                    ),
                    trailing: Text(
                      totalSorAmount.toDouble().toStringAsFixed(2),
                      style: Theme.of(context).textTheme.headlineSmall,
                    ),
                  ),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              Container(
                decoration: const BoxDecoration(
                  border: Border(
                    bottom: BorderSide(),
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.only(bottom: 8.0),
                  child: ListTile(
                    title: Text(
                      t.translate(i18.measurementBook.totalNonSorAmount),
                      style: Theme.of(context).textTheme.headlineSmall,
                    ),
                    subtitle: Text(
                      t.translate(i18.measurementBook.forCurrentEntry),
                      style: Theme.of(context).textTheme.bodySmall,
                    ),
                    trailing: Text(
                      totalNonSorAmount.toDouble().toStringAsFixed(2),
                      style: Theme.of(context).textTheme.headlineSmall,
                    ),
                  ),
                ),
              ),
              const SizedBox(
                height: 15,
              ),
              Container(
                //height: 80,
                width: MediaQuery.sizeOf(context).width,
                decoration: BoxDecoration(
                  //color: Colors.red,
                  borderRadius: const BorderRadius.all(
                    Radius.circular(10),
                  ),
                  border: Border.all(
                    color: Colors.grey,
                    width: 2,
                  ),
                ),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Expanded(
                        flex: 4,
                        child: ListTile(
                          title: Text(
                            // "Total MB Amount",
                            t.translate(i18.measurementBook.totalMbAmount),
                            style: Theme.of(context).textTheme.headlineSmall,
                          ),
                          subtitle: Text(
                            // "(for current entry)",
                            t.translate(i18.measurementBook.forCurrentEntry),
                            style: Theme.of(context).textTheme.bodySmall,
                          ),
                        ),
                      ),
                      Expanded(
                          flex: 4,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              Text(
                                mbAmount.roundToDouble().toStringAsFixed(2),
                                style:
                                    Theme.of(context).textTheme.headlineSmall,
                              ),
                            ],
                          )),
                    ],
                  ),
                ),
              ),
              const SizedBox(
                height: 15,
              ),
              showBtn
                  ? Padding(
                      padding: const EdgeInsets.only(bottom: 8.0),
                      child: Button(
                        mainAxisSize: MainAxisSize.max,
                        label: t.translate(i18.measurementBook.mbAction),
                        onPressed: () {
                          Navigator.of(context).pop();
                          if (widget.type == MBScreen.update) {
                            showDialog(
                              context: context,
                              builder: (context) => CommonButtonCard(
                                g: processInstances,
                                contractNumber: contractNumber,
                                mbNumber: mbNumber,
                                type: widget.type,
                                bs: bs,
                              ),
                            );
                          } else {
                            showDialog(
                              context: context,
                              builder: (context) => CommonButtonCard(
                                g: processInstances,
                                contractNumber: contractNumber,
                                mbNumber: mbNumber,
                                type: widget.type,
                                bs: bs,
                              ),
                            );
                          }
                        },
                        type: ButtonType.primary,
                        size: ButtonSize.large,
                      ),
                    )
                  : const SizedBox.shrink(),
            ],
          ),
        );
      },
    );
  }
}

//

class SorCard extends StatefulWidget {
  final int index;
  final List<FilteredMeasurementsMeasure>? magic;
  final List<FilteredMeasurementsMeasure>? preSorNonSor;
  final String type;
  final String sorNonSorId;
  final String cardLevel;

  const SorCard(
      {super.key,
      required this.index,
      this.magic,
      this.preSorNonSor,
      required this.type,
      required this.sorNonSorId,
      required this.cardLevel});

  @override
  State<SorCard> createState() => _SorCardState();
}

class _SorCardState extends State<SorCard> {
  List<FilteredMeasurementsEstimate> line = [];
  String noOfQty = '';
  String preConumed = '';
  late TextEditingController consumedQtyController;
  late TextEditingController currentAmtController;
  @override
  void initState() {
    // TODO: implement initState
    consumedQtyController = TextEditingController();
    currentAmtController = TextEditingController();

    line = widget.magic!.map(
      (e) {
        return e.contracts!.first.estimates!.first;
      },
    ).toList();
    noOfQty = line.fold("0.0000", (sum, obj) {
      double m = double.parse(obj.noOfunit!.toString()).toDouble();
      return double.parse((double.parse(sum) + m.toDouble()).toString())
          .toStringAsFixed(4);
    });

    preConumed = widget.preSorNonSor == null
        ? "0.0000"
        : widget.preSorNonSor!.fold("0.0000", (sum, obj) {
            double m = obj.contracts!.first.estimates!.first.isDeduction == true
                ? -(obj.cumulativeValue!)
                : (obj.cumulativeValue!);
            return double.parse((double.parse(sum) + m.toDouble()).toString())
                .toStringAsFixed(4);
          });

    consumedQtyController.text = (widget.magic!.fold(0.0, (sum, obj) {
      double m;
      if (obj.contracts?.first.estimates?.first.isDeduction == false) {
        m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
          double mk = double.parse(ob.quantity!.toString());
          return subSum + mk;
        });
      } else {
        m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
          double mr = double.parse(ob.quantity!.toString());
          return subSum + mr;
        });
        m = -m;
      }
      return sum + m;
    })).toStringAsFixed(4);

    currentAmtController.text = (widget.magic!.fold(0.0, (sum, obj) {
      double m = obj.mbAmount != null
          ? (obj.mbAmount != null && obj.mbAmount! < 0)
              ? (obj.mbAmount! * (-1))
              : obj.mbAmount!
          : 0.00;
      if (obj.contracts?.first.estimates?.first.isDeduction == true) {
        m = -(m); // Negate the amount for deductions
      } else {
        m = (m);
      }
      return sum + m;
    })).toStringAsFixed(2);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final tk = AppLocalizations.of(context);
    return BlocBuilder<MeasurementDetailBloc, MeasurementDetailState>(
        builder: (context, state) {
      return state.maybeMap(
        orElse: () => const SizedBox.shrink(),
        loaded: (value) {
          line = widget.magic!.map(
            (e) {
              return e.contracts!.first.estimates!.first;
            },
          ).toList();
          noOfQty = line.fold("0.0000", (sum, obj) {
            double m = double.parse(obj.noOfunit!.toString()).toDouble();
            return double.parse((double.parse(sum) + m.toDouble()).toString())
                .toStringAsFixed(4);
          });

          preConumed = widget.preSorNonSor == null
              ? "0.0000"
              : widget.preSorNonSor!.fold("0.0000", (sum, obj) {
                  double m =
                      obj.contracts!.first.estimates!.first.isDeduction == true
                          ? -(obj.cumulativeValue!)
                          : (obj.cumulativeValue!);
                  return double.parse(
                          (double.parse(sum) + m.toDouble()).toString())
                      .toStringAsFixed(4);
                });

          consumedQtyController.text = (widget.magic!.fold(0.0, (sum, obj) {
            double m;
            if (obj.contracts?.first.estimates?.first.isDeduction == false) {
              m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
                double mk = double.parse(ob.quantity!.toString());
                return subSum + mk;
              });
            } else {
              m = obj.measureLineItems!.fold(0.0, (subSum, ob) {
                double mr = double.parse(ob.quantity!.toString());
                return subSum + mr;
              });
              m = -m;
            }
            return sum + m;
          })).toStringAsFixed(4);

          currentAmtController.text = (widget.magic!.fold(0.0, (sum, obj) {
            double m = obj.mbAmount != null
                ? (obj.mbAmount != null && obj.mbAmount! < 0)
                    ? (obj.mbAmount! * (-1))
                    : obj.mbAmount!
                : 0.00;
            if (obj.contracts?.first.estimates?.first.isDeduction == true) {
              m = -(m); // Negate the amount for deductions
            } else {
              m = (m);
            }
            return sum + m;
          })).toStringAsFixed(2);
          return ui_component.DigitCard(
              margin:
                  const EdgeInsets.only(top: 0, bottom: 8, left: 8, right: 8),
              cardType: CardType.primary,
              children: [
                LabelValueList(
                    heading: "${widget.cardLevel} ${widget.index + 1}",
                    maxLines: 3,
                    labelFlex: 5,
                    valueFlex: 5,
                    items: [
                      LabelValuePair(
                          label: tk.translate(i18.measurementBook.description),
                          value: widget.magic!.first.contracts!.first.estimates!
                                  .first.name ??
                              ""),
                      LabelValuePair(
                          label: tk.translate(i18.measurementBook.unit),
                          value: line[0].uom ?? ''),
                      LabelValuePair(
                          label: tk.translate(i18.measurementBook.rate),
                          value: line[0].unitRate == null
                              ? 0.00.toString()
                              : double.parse(line[0].unitRate!.toString())
                                  .toStringAsFixed(2)),
                      LabelValuePair(
                          label: tk.translate(i18.measurementBook.approvedQty),
                          value: noOfQty),
                      LabelValuePair(
                          label:
                              "${tk.translate(i18.measurementBook.preConsumedKey)}\n${tk.translate(i18.measurementBook.preConsumedPre)}",
                          value: widget.preSorNonSor == null
                              ? "0.0000"
                              : preConumed),
                    ]),
                ui_label.LabeledField(
                  label: tk.translate(i18.measurementBook.currentMBEntry),
                  labelStyle: Theme.of(context).textTheme.labelLarge,
                  child: InkWell(
                    onTap: () {
                      showDialog(
                        context: context,
                        builder: (_) {
                          return HorizontalCardListDialog(
                            lineItems: widget.magic,
                            index: widget.index,
                            type: widget.type,
                            noOfUnit: noOfQty,
                            cummulativePrevQty: widget.preSorNonSor == null
                                ? 0.0000
                                : widget.preSorNonSor!.fold(0.0000, (sum, obj) {
                                    double m = obj.contracts!.first.estimates!
                                                .first.isDeduction ==
                                            true
                                        ? -(obj.cumulativeValue!)
                                        : (obj.cumulativeValue!);
                                    return sum + m.toDouble();
                                  }),
                            sorId: widget.sorNonSorId,
                          );
                        },
                      );
                    },
                    child: IgnorePointer(
                      child: DigitSearchFormInput(
                        controller: consumedQtyController,
                        readOnly: true,
                        suffixIcon: Icons.add_circle,
                        iconColor:
                            Theme.of(context).colorTheme.primary.primary1,
                        onSuffixTap: (p0) {},
                      ),
                    ),
                  ),
                ),
                ui_label.LabeledField(
                  label: tk.translate(i18.measurementBook.mbAmtCurrentEntry),
                  labelStyle: Theme.of(context).textTheme.labelLarge,
                  child: DigitTextFormInput(
                    controller: currentAmtController,
                    readOnly: true,
                  ),
                ),
              ]);
        },
      );
    });
  }
}

Widget renderSor(
  List<SorObject> value,
  String type,
) {
  return ListView.builder(
    shrinkWrap: true,
    padding: EdgeInsets.zero,
    physics: const NeverScrollableScrollPhysics(),
    itemBuilder: (BuildContext context, int index) {
      return SorCard(
        // consumedQty: consumedQty,
        // currentAmt: currentAmt,

        index: index,
        magic: value![index].filteredMeasurementsMeasure,

        preSorNonSor: value == null
            ? null
            : value?.firstWhereOrNull(
                        (element) => element.sorId == value![index].sorId) ==
                    null
                ? null
                : value!
                    .firstWhereOrNull(
                        (element) => element.sorId == value![index].sorId)!
                    .filteredMeasurementsMeasure,
        // value.preSor![index]
        //     .filteredMeasurementsMeasure,
        type: type,
        sorNonSorId: value![index].sorId!,
        cardLevel: AppLocalizations.of(context).translate(type == "sor"
            ? i18.measurementBook.mbSor
            : i18.measurementBook.mbNonSor),
      );
    },
    itemCount: value!.length,
  );
}
