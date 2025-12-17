import 'dart:io';

import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/models/models.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/atoms/upload_popUp.dart';
import 'package:digit_ui_components/widgets/molecules/digit_card.dart';
import 'package:digit_ui_components/widgets/molecules/digit_footer.dart';
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/employee/emp_hrms/emp_hrms.dart';
import 'package:works_shg_app/blocs/employee/mb/mb_crud.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/data/repositories/core_repo/core_repository.dart';
import 'package:works_shg_app/models/employee/mb/mb_detail_response.dart';
import 'package:works_shg_app/models/muster_rolls/business_service_workflow.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/models/file_picker_data.dart';
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/loaders.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/mb/multi_image.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/employee/mb/mb_detail_view.dart';
import '../../blocs/muster_rolls/get_muster_workflow.dart';
import '../../models/employee/mb/role_based_hrms.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/muster_rolls/muster_workflow_model.dart';
import '../../utils/employee/mb/mb_logic.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;
import 'package:path/path.dart' as path;

@RoutePage()
class MBTypeConfirmationPage extends StatefulWidget {
  final NextActions? nextActions;
  final String? contractNumber;
  final String? mbNumber;
  final MBScreen type;
  final StateActions? stateActions;
  const MBTypeConfirmationPage({
    super.key,
    required this.nextActions,
    this.contractNumber,
    this.mbNumber,
    required this.type,
    this.stateActions,
  });

  @override
  State<MBTypeConfirmationPage> createState() => _MBTypeConfirmationPageState();
}

class _MBTypeConfirmationPageState extends State<MBTypeConfirmationPage> {
  List<String>? photo;
  List<WorkFlowSupportDocument> supportDocument = [];
  String? selectedAssignee;
  var comment = TextEditingController();
  String hrmsKey = "hrmsKey";

  @override
  void initState() {
    FilePickerData.imageFile = null;
    FilePickerData.bytes = null;

    super.initState();
  }

  @override
  void dispose() {
    comment.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocListener<MeasurementCrudBloc, MeasurementCrudState>(
      listener: (context, state) {
        state.maybeMap(
          orElse: () => const SizedBox.shrink(),
          loaded: (value) {
            Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );
            // Navigator.of(context).popUntil((route) => route is HomeRoute);
            context.read<MusterGetWorkflowBloc>().add(
                  FetchMBWorkFlowEvent(
                      tenantId: GlobalVariables.tenantId!,
                      mbNumber: widget.mbNumber!),
                );
            if (widget.type == MBScreen.update) {
              context.read<MeasurementDetailBloc>().add(
                    MeasurementDetailBookBlocEvent(
                      contractNumber: widget.contractNumber!,
                      measurementNumber: widget.mbNumber!,
                      tenantId: '',
                      screenType: widget.type,
                    ),
                  );
              Navigator.of(context).pop();
            } else {
              context.read<MeasurementDetailBloc>().add(
                    MeasurementDetailBookBlocEvent(
                      contractNumber: widget.contractNumber!,
                      measurementNumber:
                          value.measurement?.measurementNumber ?? '',
                      tenantId: '',
                      screenType: MBScreen.update,
                    ),
                  );
              if (value.measurement!.wfStatus == "SUBMITTED") {
                context.router.popUntilRouteWithPath('home');
              } else {
                Navigator.of(context).pop();
                Navigator.of(context).pop();

                context.router.push(MBDetailRoute(
                  contractNumber: widget.contractNumber!,
                  mbNumber: value.measurement?.measurementNumber ?? '',
                  tenantId: GlobalVariables.tenantId,
                  type: MBScreen.update,
                ));
              }
            }
          },
          loading: (value) {
            Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );
            //Loaders.showLoadingDialog(context);
            String msg = t.translate(i18.common.loading);
            shg_loader.Loaders.showLoadingDialog(label: msg, context);
          },
          error: (value) {
            Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );

            Notifiers.getToastMessage(
                context, t.translate(value.error.toString()), "ERROR");

            // ui_component.Toast.showToast(context,
            //     message: value.error.toString(),
            //     type: ui_component.ToastType.error);
          },
        );
      },
      child: Scaffold(
        backgroundColor: Theme.of(context).colorTheme.paper.primary,
        // appBar: customAppBar(),
        // drawer: const MySideBar(),
        body: BlocBuilder<LocalizationBloc, LocalizationState>(
          builder: (context, state) {
            return ReactiveFormBuilder(
                form: detailBuildForm,
                builder: (context, formGroup, child) {
                  return BlocBuilder<MeasurementDetailBloc,
                      MeasurementDetailState>(
                    builder: (context, state) {
                      return state.maybeMap(
                        orElse: () => const SizedBox.shrink(),
                        loaded: (value) {
                          if (widget.type == MBScreen.update) {
                            return ui_component.ScrollableContent(
                              backgroundColor:
                                  Theme.of(context).colorTheme.paper.primary,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              footer: DigitFooter(actions: [
                                FooterAction(
                                  button: Button(
                                    mainAxisSize: MainAxisSize.max,
                                    size: ButtonSize.large,
                                    type: ButtonType.primary,
                                    label: widget.nextActions!.action ==
                                            "EDIT/RE-SUBMIT"
                                        ? t.translate("WORKS_FORWARD")
                                        : t.translate(
                                            "WF_MODAL_SUBMIT_MB_${widget.nextActions!.action!}"),
                                    onPressed: () {
                                      if (widget.nextActions!.action ==
                                              "REJECT" &&
                                          comment.text == "") {
                                        Notifiers.getToastMessage(
                                          context,
                                          // AppLocalizations.of(context)
                                          //     .translate(i18.login.invalidOTP),

                                          t.translate(
                                              i18.common.allFieldsMandatory),
                                          'ERROR',
                                        );

                                        // Toast.showToast(context,
                                        //     message: t.translate(i18
                                        //         .common.allFieldsMandatory),
                                        //     type: ToastType.error);
                                      } else {
                                        List<List<SorObject>> sorList = [
                                          value.sor!,
                                          value.nonSor!
                                        ];
                                        MBDetailResponse kkk =
                                            MBLogic.getMbPayloadUpdate(
                                          data: value.data,
                                          sorList: sorList,
                                          workFlow: WorkFlow(
                                            action: widget.nextActions!.action,
                                            comment: comment.text,
                                            assignees: selectedAssignee != null
                                                ? selectedAssignee != null
                                                    ? [selectedAssignee!]
                                                    : null
                                                : null,
                                            documents: supportDocument,
                                          ),
                                          type: widget.type,
                                        );

                                        context.read<MeasurementCrudBloc>().add(
                                              MeasurementUpdateBlocEvent(
                                                measurement: kkk.measurement!,
                                                tenantId: '',
                                                workFlow: WorkFlow(
                                                  action: widget
                                                      .nextActions!.action,
                                                  comment: comment.text,
                                                  assignees: selectedAssignee !=
                                                          null
                                                      ? selectedAssignee != null
                                                          ? [selectedAssignee!]
                                                          : null
                                                      : null,
                                                  documents: supportDocument,
                                                ),
                                                type: widget.type,
                                              ),
                                            );
                                      }
                                    },
                                  ),
                                ),
                                FooterAction(
                                  button: Button(
                                    mainAxisSize: MainAxisSize.max,
                                    label: t.translate(
                                        i18.measurementBook.mbCancel),
                                    onPressed: () {
                                      context.router.maybePopTop();
                                    },
                                    type: ButtonType.secondary,
                                    size: ButtonSize.large,
                                  ),
                                ),
                              ]),
                              children: [
                                Padding(
                                  padding: const EdgeInsets.only(
                                      top: 16.0, right: 16.0, bottom: 0.0),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Button(
                                        mainAxisSize: MainAxisSize.min,
                                        label: '',
                                        size: ButtonSize.large,
                                        type: ButtonType.tertiary,
                                        onPressed: () {
                                          context.router.maybePopTop();
                                        },
                                        suffixIcon: Icons.close,
                                      ),
                                    ],
                                  ),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(
                                      top: 0.0,
                                      left: 16.0,
                                      right: 16.0,
                                      bottom: 0.0),
                                  child: Row(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.center,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      SizedBox(
                                        width:
                                            MediaQuery.sizeOf(context).width *
                                                0.9,
                                        child: DigitTextBlock(
                                          heading: widget.nextActions!.action ==
                                                  "EDIT/RE-SUBMIT"
                                              ? t.translate(
                                                  "WORKS_UPDATE_AND_FORWARD")
                                              : t.translate(
                                                  "WF_MB_ACTION_${widget.nextActions!.action}"),
                                          // style: DigitTheme.instance.mobileTheme
                                          //     .textTheme.headlineLarge,
                                          // overflow: TextOverflow.clip,
                                          // maxLines: 1,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                (widget.nextActions!.action ==
                                            "EDIT/RE-SUBMIT" ||
                                        widget.nextActions!.action ==
                                            "VERIFY_AND_FORWARD" ||
                                        widget.nextActions!.action ==
                                            "SUBMIT" ||
                                        widget.nextActions!.action ==
                                            "SEND_BACK_TO_ORIGINATOR")
                                    ? Padding(
                                        padding: const EdgeInsets.only(
                                            left: 16.0,
                                            right: 16.0,
                                            top: 0.0,
                                            bottom: 0.0),
                                        child: BlocBuilder<EmpHRMSBloc,
                                            EmpHRMsState>(
                                          builder: (context, state) {
                                            return state.maybeMap(
                                              orElse: () =>
                                                  const SizedBox.shrink(),
                                              loaded: (value) {
                                                if (value.hrmsEmployee !=
                                                        null &&
                                                    value.hrmsEmployee!
                                                        .isNotEmpty) {
                                                  return Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            top: 16.0),
                                                    child: ui_component
                                                        .LabeledField(
                                                      label: t.translate(
                                                          "WF_MODAL_APPROVER"),
                                                      child: ui_component.DigitDropdown<
                                                              HRMSEmployee>(
                                                          valueMapper: value
                                                              .hrmsEmployee!
                                                              .map((e) => ValueMapper(
                                                                  code: e.uuid!,
                                                                  name: t.translate(e
                                                                      .employeeUser!
                                                                      .name
                                                                      .toString())))
                                                              .toList(),
                                                          onSelect: (p0) {
                                                            setState(() {
                                                              selectedAssignee =
                                                                  p0.code!;
                                                            });
                                                          },
                                                          items: value
                                                              .hrmsEmployee!
                                                              .map((e) => DropdownItem(
                                                                  name: e
                                                                      .employeeUser!
                                                                      .name!,
                                                                  code: e.uuid!))
                                                              .toList()),
                                                    ),
                                                  );
                                                } else {
                                                  return const SizedBox
                                                      .shrink();
                                                }
                                              },
                                              error: (value) {
                                                return const SizedBox.shrink();
                                              },
                                            );
                                          },
                                        ),
                                      )
                                    : const SizedBox.shrink(),
                                Padding(
                                  padding: const EdgeInsets.all(16.0),
                                  child: ui_component.LabeledField(
                                    isRequired: widget.nextActions!.action == "REJECT"
                                              ? true
                                              : false,
                                    label:
                                        "${t.translate("WF_MODAL_COMMENTS")}",
                                    child: DigitTextAreaFormInput(
                                      controller: comment,
                                      maxLine: 5,
                                      isRequired:
                                          widget.nextActions!.action == "REJECT"
                                              ? true
                                              : false,
                                    ),
                                  ),
                                ),
                                widget.nextActions!.action !=
                                            "EDIT/RE-SUBMIT" &&
                                        widget.nextActions!.action != "SUBMIT"
                                    ? Padding(
                                        padding: const EdgeInsets.all(16.0),
                                        child: Column(
                                          children: [
                                            SizedBox(
                                              height: Theme.of(context)
                                                  .spacerTheme
                                                  .spacer4,
                                            ),
                                            LabeledField(
                                              label:
                                                  "${AppLocalizations.of(context).translate(i18.common.supportingDocumentHeader)}",
                                              child: FileUploadWidget(
                                                onFilesSelected: (files) {
                                                  uploadFileToServer(files,
                                                      context, supportDocument);
                                                  Map<PlatformFile, String?>
                                                      fileErrors = {};

                                                  return fileErrors;
                                                },
                                                label:
                                                    "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                                              ),
                                            ),
                                            DigitTextBlock(
                                                description: t.translate(
                                                    i18.common.photoInfo)),
                                          ],
                                        ),
                                      )
                                    : const SizedBox.shrink(),
                              ],
                            );
                          } else {
                            return ui_component.ScrollableContent(
                              backgroundColor:
                                  Theme.of(context).colorTheme.paper.primary,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              footer: DigitFooter(
                                actions: [
                                  FooterAction(
                                    button: Button(
                                      mainAxisSize: MainAxisSize.max,
                                      size: ButtonSize.large,
                                      type: ButtonType.primary,
                                      label: widget.stateActions!.action ==
                                              "SUBMIT"
                                          ? t.translate(
                                              i18.measurementBook.mbSubmitLabel)
                                          : t.translate(
                                              "WF_MODAL_SUBMIT_MB_${widget.stateActions!.action}"),
                                      onPressed: () {
                                        List<List<SorObject>> sorList = [
                                          value.sor!,
                                          value.nonSor!
                                        ];
                                        MBDetailResponse kkk =
                                            MBLogic.getMbPayloadUpdate(
                                          data: value.data,
                                          sorList: sorList,
                                          workFlow: WorkFlow(
                                            action: widget
                                                        .stateActions!.action ==
                                                    "CREATE"
                                                ? "SUBMIT"
                                                : widget.stateActions!.action,
                                            comment: comment.text,
                                            assignees: selectedAssignee != null
                                                ? selectedAssignee != null
                                                    ? [selectedAssignee!]
                                                    : null
                                                : null,
                                            documents: supportDocument,
                                          ),
                                          type: widget.type,
                                        );

                                        context.read<MeasurementCrudBloc>().add(
                                              MeasurementUpdateBlocEvent(
                                                measurement: kkk.measurement!,
                                                tenantId: '',
                                                workFlow: WorkFlow(
                                                  action: widget.stateActions!
                                                              .action ==
                                                          "CREATE"
                                                      ? "SUBMIT"
                                                      : widget
                                                          .stateActions!.action,
                                                  comment: comment.text,
                                                  assignees: selectedAssignee !=
                                                          null
                                                      ? selectedAssignee != null
                                                          ? [selectedAssignee!]
                                                          : null
                                                      : null,
                                                  documents: supportDocument,
                                                ),
                                                type: widget.type,
                                              ),
                                            );
                                        // Navigator.of(context)
                                        //     .popUntil((route) => route is HomeRoute);
                                        // context.router.push(const HomeRoute());
                                      },
                                    ),
                                  ),
                                  FooterAction(
                                    button: Button(
                                      mainAxisSize: MainAxisSize.max,
                                      type: ButtonType.secondary,
                                      size: ButtonSize.large,
                                      label: t.translate(
                                          i18.measurementBook.mbCancel),
                                      onPressed: () {
                                        context.router.maybePopTop();
                                      },
                                    ),
                                  ),
                                ],
                              ),
                              children: [
                                Padding(
                                  padding: const EdgeInsets.only(
                                      top: 16.0,
                                      right: 16.0,
                                      left: 16.0,
                                      bottom: 16.0),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    children: [
                                      Button(
                                        mainAxisSize: MainAxisSize.min,
                                        label: '',
                                        size: ButtonSize.large,
                                        type: ButtonType.tertiary,
                                        onPressed: () {
                                          context.router.maybePopTop();
                                        },
                                        suffixIcon: Icons.close,
                                      ),
                                    ],
                                  ),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(
                                      left: 16.0,
                                      right: 16.0,
                                      top: 0.0,
                                      bottom: 0.0),
                                  child: Row(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.center,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      SizedBox(
                                        width:
                                            MediaQuery.sizeOf(context).width *
                                                0.9,
                                        child: DigitTextBlock(
                                          heading: widget
                                                      .stateActions!.action ==
                                                  "SUBMIT"
                                              ? t.translate(i18.measurementBook
                                                  .mbcreateLabel)
                                              : t.translate(
                                                  "WF_MB_ACTION_${widget.stateActions!.action}"),
                                          // style: DigitTheme.instance.mobileTheme
                                          //     .textTheme.headlineLarge,
                                          // overflow: TextOverflow.clip,
                                          // maxLines: 1,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                (widget.stateActions!.action == "SUBMIT")
                                    ? Padding(
                                        padding: const EdgeInsets.symmetric(
                                            horizontal: 16.0),
                                        child: BlocBuilder<EmpHRMSBloc,
                                            EmpHRMsState>(
                                          builder: (context, state) {
                                            return state.maybeMap(
                                              orElse: () =>
                                                  const SizedBox.shrink(),
                                              loaded: (value) {
                                                if (value.hrmsEmployee !=
                                                        null &&
                                                    value.hrmsEmployee!
                                                        .isNotEmpty) {
                                                  return Padding(
                                                    padding:
                                                        const EdgeInsets.only(
                                                            top: 16.0),
                                                    child: ui_component
                                                        .LabeledField(
                                                      label: t.translate(
                                                          "WF_MODAL_APPROVER"),
                                                      child: ui_component.DigitDropdown<
                                                              HRMSEmployee>(
                                                          valueMapper: value
                                                              .hrmsEmployee!
                                                              .map((e) => ValueMapper(
                                                                  code: e.uuid!,
                                                                  name: t.translate(e
                                                                      .employeeUser!
                                                                      .name
                                                                      .toString())))
                                                              .toList(),
                                                          onSelect: (p0) {
                                                            setState(() {
                                                              selectedAssignee =
                                                                  p0.code;
                                                            });
                                                          },
                                                          items: value
                                                              .hrmsEmployee!
                                                              .map((e) => DropdownItem(
                                                                  name: e
                                                                      .employeeUser!
                                                                      .name!,
                                                                  code: e.uuid!))
                                                              .toList()),
                                                    ),
                                                  );
                                                } else {
                                                  return const SizedBox
                                                      .shrink();
                                                }
                                              },
                                              error: (value) {
                                                return const SizedBox.shrink();
                                              },
                                            );
                                          },
                                        ),
                                      )
                                    : const SizedBox.shrink(),
                                // DigitTextField(
                                // label: t.translate("WF_MODAL_COMMENTS"),
                                //   maxLines: 5,
                                //   controller: comment,
                                // ),
                                Padding(
                                  padding: const EdgeInsets.only(
                                      top: 16.0, left: 16.0, right: 16.0),
                                  child: ui_component.LabeledField(
                                    
                                    label: t.translate("WF_MODAL_COMMENTS"),
                                    child: DigitTextAreaFormInput(
                                      maxLine: 5,
                                      controller: comment,
                                    ),
                                  ),
                                ),
                                widget.stateActions!.action != null
                                    ? Padding(
                                        padding: const EdgeInsets.only(
                                            left: 16.0,
                                            right: 16.0,
                                            top: 8.0,
                                            bottom: 0.0),
                                        child: Column(
                                          children: [
                                            SizedBox(
                                              height: Theme.of(context)
                                                  .spacerTheme
                                                  .spacer4,
                                            ),
                                            LabeledField(
                                              label:
                                                  "${AppLocalizations.of(context).translate(i18.common.supportingDocumentHeader)}",
                                              child: FileUploadWidget(
                                                onFilesSelected: (files) {
                                                  uploadFileToServer(files,
                                                      context, supportDocument);

                                                  Map<PlatformFile, String?>
                                                      fileErrors = {};

                                                  return fileErrors;
                                                },
                                                label:
                                                    "${AppLocalizations.of(context).translate(i18.common.chooseFile)}",
                                              ),
                                            ),
                                            DigitTextBlock(
                                                description: t.translate(
                                                    i18.common.photoInfo)),
                                          ],
                                        ),
                                      )
                                    : const SizedBox.shrink(),
                              ],
                            );
                          }
                        },
                        error: (value) {
                          return const SizedBox.shrink();
                        },
                      );
                    },
                  );
                });
          },
        ),
      ),
    );
  }

  FormGroup detailBuildForm() => fb.group(<String, Object>{
        //hrmsKey: FormControl<HRMSEmployee>(value: selectedAssignee),
      });
}

void uploadFileToServer(List<PlatformFile> files, BuildContext context,
    List<WorkFlowSupportDocument> sendToServer) async {
  List<WorkFlowSupportDocument> workFlow = [];
  try {
    Navigator.of(
      context,
      rootNavigator: true,
    ).popUntil(
      (route) => route is! PopupRoute,
    );
    String msg=AppLocalizations.of(context).translate(i18.common.loading);
    shg_loader.Loaders.showLoadingDialog(context,
        label: msg);
    // var response = await CoreRepository().uploadFiles(
    //     files.map((e) => File(e.path ?? e.name)).toList(),
    //     "img_measurement_book");
    final List<dynamic> uploadableFiles = files.map((file) {
      if (kIsWeb) {
        // On web, use PlatformFile with bytes
        return PlatformFile(
          name: file.name,
          bytes: file.bytes,
          size: file.size,
        );
      } else {
        // On native platforms, use the File object
        return File(file.path ?? file.name); // Default to name if path is null
      }
    }).toList();

    // Cast the list appropriately based on the platform
    final List<dynamic> data = kIsWeb
        ? uploadableFiles.cast<PlatformFile>()
        : uploadableFiles.cast<File>();
    final response =
        await CoreRepository().uploadFiles(data, "img_measurement_book");

    for (int i = 0; i < response.length; i++) {
      workFlow.add(
          // WorkflowSupportDocument(
          //   indexing:
          //       dataDocument.isEmpty ? 0 : (dataDocument.last.indexing! + 1),
          //   isActive: true,
          //   tenantId: response[i].tenantId,
          //   fileStore: response[i].fileStoreId,
          //   documentType: path.extension(payload[i].name ?? ''),
          //   documentUid: path.basename(payload[i].name ?? ''),
          //   documentAdditionalDetails: DocumentAdditionalDetails(
          //     fileName: path.basename(payload[i].name ?? ''),
          //     fileType: "img_measurement_book",
          //     tenantId: response[i].tenantId,
          //   )
          //   )
          WorkFlowSupportDocument(
              documentType: path.extension(files[i].name ?? ''),
              documentUid: response[i].fileStoreId ?? '',
              fileName: path.extension(files[i].name ?? ''),
              fileStoreId: response[i].fileStoreId ?? '',
              tenantId: response[i].tenantId ?? ''));
    }

    sendToServer.addAll(workFlow);
    Navigator.of(
      context,
      rootNavigator: true,
    ).popUntil(
      (route) => route is! PopupRoute,
    );
  } catch (ex) {
    Navigator.of(
      context,
      rootNavigator: true,
    ).popUntil(
      (route) => route is! PopupRoute,
    );
  }
}
