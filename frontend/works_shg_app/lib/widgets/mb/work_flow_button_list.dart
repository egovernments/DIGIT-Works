import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/widgets/atoms/digit_action_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/employee/mb/mb_crud.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/models/employee/mb/mb_detail_response.dart';
import 'package:works_shg_app/models/muster_rolls/business_service_workflow.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/notifiers.dart';

import '../../blocs/employee/emp_hrms/emp_hrms.dart';
import '../../blocs/employee/mb/mb_detail_view.dart';
import '../../models/muster_rolls/muster_workflow_model.dart';
import '../../router/app_router.dart';

import '../../utils/employee/mb/mb_logic.dart';
import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
    
class CommonButtonCard extends StatelessWidget {
  const CommonButtonCard({
    super.key,
    required this.g,
    required this.contractNumber,
    required this.mbNumber,
    required this.type,
    this.bs,
  });

  final List<ProcessInstances>? g;
  final String contractNumber;
  final String mbNumber;
  final MBScreen type;
  final List<BusinessServices>? bs;

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocListener<MeasurementCrudBloc, MeasurementCrudState>(
      listener: (context, stateCrud) {
        stateCrud.maybeMap(
          orElse: () => const SizedBox.shrink(),
          loaded: (value) {},
          loading: (value) {
            Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );
           // Loaders.showLoadingDialog(context);
            shg_loader.Loaders.showLoadingDialog(
              label:t.translate(i18.common.loading),
              context);
          },
          error: (value) {
            Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );
            Notifiers.getToastMessage(
              context,
              // AppLocalizations.of(context)
              //     .translate(i18.login.invalidOTP),

              value.error.toString(),
              'ERROR',
            );
            // Toast.showToast(context,
            //     message: value.error.toString(), type: ToastType.error);
          },
        );
      },
      child: BlocBuilder<MeasurementDetailBloc, MeasurementDetailState>(
        builder: (context, state) {
          return state.maybeMap(
            orElse: () => const SizedBox.shrink(),
            loaded: (value) {
              return type == MBScreen.update
                  ? ActionCard(
                    onOutsideTap: (){
                      Navigator.of(
              context,
              rootNavigator: true,
            ).popUntil(
              (route) => route is! PopupRoute,
            );
                    },
                      actions: List.generate(
                      g!.first.nextActions!.length,
                      (index) => Button(
                        size: ButtonSize.large,
                        type: ButtonType.secondary,
                        label: g!.first.nextActions![index].action ==
                                "EDIT/RE-SUBMIT"
                            ? t.translate("WF_MB_ACTION_EDIT_RE_SUBMIT")
                            : t.translate(
                                "WF_MB_ACTION_${g!.first.nextActions![index].action!}"),
                        onPressed: () {
                          if (g!.first.nextActions![index].action ==
                                  "EDIT/RE-SUBMIT" &&
                              value.viewStatus) {
                            context.read<MeasurementDetailBloc>().add(
                                  UpdateViewModeEvent(
                                    updateView: !value.viewStatus,
                                  ),
                                );

                            Navigator.of(context).pop();
                          } else {
                            if (g!.first.nextActions![index].action ==
                                "SAVE_AS_DRAFT") {
                              List<List<SorObject>> sorList = [
                                value.sor!,
                                value.nonSor!
                              ];
                              MBDetailResponse kkk = MBLogic.getMbPayloadUpdate(
                                data: value.data,
                                sorList: sorList,
                                workFlow: WorkFlow(
                                  action: g!.first.nextActions![index].action ==
                                          "SAVE_AS_DRAFT"
                                      ? "SAVE_AS_DRAFT"
                                      : g!.first.nextActions![index].action,
                                  documents: [],
                                ),
                                type: type,
                              );

                              context.read<MeasurementCrudBloc>().add(
                                    MeasurementUpdateBlocEvent(
                                      measurement: kkk.measurement!,
                                      tenantId: '',
                                      workFlow: WorkFlow(
                                        action: g!.first.nextActions![index]
                                                    .action ==
                                                "SAVE_AS_DRAFT"
                                            ? "SAVE_AS_DRAFT"
                                            : g!.first.nextActions![index]
                                                .action,
                                        comment: "",
                                        assignees: [""],
                                        documents: [],
                                      ),
                                      type: type,
                                    ),
                                  );
                            } else {
                              context.read<EmpHRMSBloc>().add(
                                    EmpHRMSLoadBlocEvent(
                                      isActive: true,
                                      roles: fetchRoles(
                                          g!.first.nextActions![index].action ??
                                              "MB_VERIFIER"),
                                      tenantId: GlobalVariables.tenantId!,
                                    ),
                                  );

                              Navigator.of(context).pop();
                              context.router.push(
                                MBTypeConfirmationRoute(
                                    nextActions: type == MBScreen.update
                                        ? g!.first.nextActions![index]
                                        : null,
                                    contractNumber: contractNumber,
                                    mbNumber: mbNumber,
                                    type: type,
                                    stateActions: type == MBScreen.create
                                        ? bs!.first.workflowState!.first
                                            .actions![index]
                                        : null),
                              );
                            }
                          }
                        },
                      ),
                    ).toList())
                  : ActionCard(
                      actions: List.generate(
                      bs!.first.workflowState!.first.actions!.length,
                      (index) => Button(
                        size: ButtonSize.large,
                        type: ButtonType.secondary,
                        label: bs!.first.workflowState!.first.actions![index]
                                    .action ==
                                "CREATE"
                            ? t.translate("WF_MB_ACTION_CREATE")
                            : t.translate(
                                "WF_MB_ACTION_${bs!.first.workflowState!.first.actions![index].action}"),
                        onPressed: () {
                          if (bs!.first.workflowState!.first.actions![index]
                                  .action ==
                              "SAVE_AS_DRAFT") {
                            List<List<SorObject>> sorList = [
                              value.sor!,
                              value.nonSor!
                            ];
                            MBDetailResponse kkk = MBLogic.getMbPayloadUpdate(
                              data: value.data,
                              sorList: sorList,
                              workFlow: WorkFlow(
                                action: bs!.first.workflowState!.first
                                            .actions![index].action ==
                                        "SAVE_AS_DRAFT"
                                    ? "SAVE_AS_DRAFT"
                                    : bs!.first.workflowState!.first
                                        .actions![index].action,
                                documents: [],
                              ),
                              type: type,
                            );

                            context.read<MeasurementCrudBloc>().add(
                                  MeasurementUpdateBlocEvent(
                                    measurement: kkk.measurement!,
                                    tenantId: '',
                                    workFlow: WorkFlow(
                                      action: bs!.first.workflowState!.first
                                                  .actions![index].action ==
                                              "SAVE_AS_DRAFT"
                                          ? "SAVE_AS_DRAFT"
                                          : bs!.first.workflowState!.first
                                              .actions![index].action,
                                      comment: "",
                                      assignees: [""],
                                      documents: [],
                                    ),
                                    type: type,
                                  ),
                                );
                          } else {
                            context.read<EmpHRMSBloc>().add(
                                  EmpHRMSLoadBlocEvent(
                                    isActive: true,
                                    roles: "MB_VERIFIER",
                                    tenantId: GlobalVariables.tenantId!,
                                  ),
                                );

                            Navigator.of(context).pop();
                            context.router.push(
                              MBTypeConfirmationRoute(
                                nextActions: type == MBScreen.update
                                    ? g!.first.nextActions![index]
                                    : null,
                                contractNumber: contractNumber,
                                mbNumber: mbNumber,
                                type: type,
                                stateActions: type == MBScreen.create
                                    ? bs!.first.workflowState!.first
                                        .actions![index]
                                    : null,
                              ),
                            );
                          }
                        },
                      ),
                    ).toList());

              // return SingleChildScrollView(
              //   child: Container(
              //     color: Colors.transparent,
              //     height: type == MBScreen.update
              //         ? g!.first.nextActions!.length.toDouble() * 45.0
              //         : bs!.first.workflowState!.first.actions!.length
              //                 .toDouble() *
              //             45.0,
              //     width: MediaQuery.sizeOf(context).width,
              //     child: Center(
              //       child: type == MBScreen.update
              //           ? ListView.builder(

              //               itemBuilder: (context, index) {
              //                 String editReSubmit = "EDIT_RE_SUBMIT";
              //                 return Padding(
              //                   padding: const EdgeInsets.only(bottom:4.0),
              //                   child: Button(
              //                     mainAxisSize: MainAxisSize.max,
              // label: g!.first.nextActions![index].action ==
              //         "EDIT/RE-SUBMIT"
              //     ? t.translate("WF_MB_ACTION_$editReSubmit")
              //     : t.translate(
              //         "WF_MB_ACTION_${g!.first.nextActions![index].action!}"),
              //                     // label: g!.first.nextActions![index].action! ?? "",
              // onPressed: () {
              //   // TODO:[temp]
              //   // final data = g
              //   //     ?.first.nextActions![index].roles
              //   //     ?.join(',');
              //   //end
              //   if (g!.first.nextActions![index].action ==
              //           "EDIT/RE-SUBMIT" &&
              //       value.viewStatus) {
              //     context.read<MeasurementDetailBloc>().add(
              //           UpdateViewModeEvent(
              //             updateView: !value.viewStatus,
              //           ),
              //         );

              //     Navigator.of(context).pop();
              //   } else {
              //     if (g!.first.nextActions![index].action ==
              //         "SAVE_AS_DRAFT") {

              //       List<List<SorObject>> sorList = [
              //         value.sor!,
              //         value.nonSor!
              //       ];
              //       MBDetailResponse kkk =
              //           MBLogic.getMbPayloadUpdate(
              //         data: value.data,
              //         sorList: sorList,
              //         workFlow: WorkFlow(
              //           action: g!.first.nextActions![index]
              //                       .action ==
              //                   "SAVE_AS_DRAFT"
              //               ? "SAVE_AS_DRAFT"
              //               : g!.first.nextActions![index]
              //                   .action,
              //           documents: [],
              //         ),
              //         type: type,
              //       );

              //       context.read<MeasurementCrudBloc>().add(
              //             MeasurementUpdateBlocEvent(
              //               measurement: kkk.measurement!,
              //               tenantId: '',
              //               workFlow: WorkFlow(
              //                 action: g!
              //                             .first
              //                             .nextActions![index]
              //                             .action ==
              //                         "SAVE_AS_DRAFT"
              //                     ? "SAVE_AS_DRAFT"
              //                     : g!
              //                         .first
              //                         .nextActions![index]
              //                         .action,
              //                 comment: "",
              //                 assignees: [""],
              //                 documents: [],
              //               ),
              //               type: type,
              //             ),
              //           );
              //     } else {
              //       context.read<EmpHRMSBloc>().add(
              //             EmpHRMSLoadBlocEvent(
              //               isActive: true,
              //               // roles: g!
              //               //             .first
              //               //             .nextActions![index]
              //               //             .action !=
              //               //         "EDIT/RE-SUBMIT"
              //               //     ? data ?? "MB_VERIFIER"
              //               //     :

              //               //     "MB_VERIFIER",
              //               roles: fetchRoles(g!
              //                       .first
              //                       .nextActions![index]
              //                       .action ??
              //                   "MB_VERIFIER"),
              //               tenantId:
              //                   GlobalVariables.tenantId!,
              //             ),
              //           );
              //       // Navigator.of(
              //       //   context,
              //       //   rootNavigator: true,
              //       // ).popUntil(
              //       //   (route) => route is! PopupRoute,
              //       // );
              //       Navigator.of(context).pop();
              //       context.router.push(
              //         MBTypeConfirmationRoute(
              //             nextActions: type == MBScreen.update
              //                 ? g!.first.nextActions![index]
              //                 : null,
              //             contractNumber: contractNumber,
              //             mbNumber: mbNumber,
              //             type: type,
              //             stateActions:
              //                 type == MBScreen.create
              //                     ? bs!.first.workflowState!
              //                         .first.actions![index]
              //                     : null),
              //       );
              //     }
              //   }
              // }, type: ButtonType.secondary,
              //                     size: ButtonSize.large,
              //                   ),
              //                 );
              //               },
              //               itemCount: g?.first.nextActions?.length,
              //             )
              //           : ListView.builder(

              //               itemBuilder: (context, index) {
              //                 String editReSubmit = "CREATE";
              //                 return Padding(
              //                   padding: const EdgeInsets.only(bottom:4.0),
              //                   child: Button(
              // label: bs!.first.workflowState!.first
              //             .actions![index].action ==
              //         "CREATE"
              //     ? t.translate("WF_MB_ACTION_$editReSubmit")
              //     : t.translate(
              //         "WF_MB_ACTION_${bs!.first.workflowState!.first.actions![index].action}"),
              //                     // label: g!.first.nextActions![index].action! ?? "",
              // onPressed: () {
              //   if (bs!.first.workflowState!.first
              //           .actions![index].action ==
              //       "SAVE_AS_DRAFT") {

              //     List<List<SorObject>> sorList = [
              //       value.sor!,
              //       value.nonSor!
              //     ];
              //     MBDetailResponse kkk =
              //         MBLogic.getMbPayloadUpdate(
              //       data: value.data,
              //       sorList: sorList,
              //       workFlow: WorkFlow(
              //         action: bs!.first.workflowState!.first
              //                     .actions![index].action ==
              //                 "SAVE_AS_DRAFT"
              //             ? "SAVE_AS_DRAFT"
              //             : bs!.first.workflowState!.first
              //                 .actions![index].action,
              //         documents: [],
              //       ),
              //       type: type,
              //     );

              //     context.read<MeasurementCrudBloc>().add(
              //           MeasurementUpdateBlocEvent(
              //             measurement: kkk.measurement!,
              //             tenantId: '',
              //             workFlow: WorkFlow(
              //               action: bs!
              //                           .first
              //                           .workflowState!
              //                           .first
              //                           .actions![index]
              //                           .action ==
              //                       "SAVE_AS_DRAFT"
              //                   ? "SAVE_AS_DRAFT"
              //                   : bs!
              //                       .first
              //                       .workflowState!
              //                       .first
              //                       .actions![index]
              //                       .action,
              //               comment: "",
              //               assignees: [""],
              //               documents: [],
              //             ),
              //             type: type,
              //           ),
              //         );
              //   } else {
              //     context.read<EmpHRMSBloc>().add(
              //           EmpHRMSLoadBlocEvent(
              //             isActive: true,
              //             roles: "MB_VERIFIER",
              //             tenantId: GlobalVariables.tenantId!,
              //           ),
              //         );

              //     Navigator.of(context).pop();
              //     context.router.push(
              //       MBTypeConfirmationRoute(
              //         nextActions: type == MBScreen.update
              //             ? g!.first.nextActions![index]
              //             : null,
              //         contractNumber: contractNumber,
              //         mbNumber: mbNumber,
              //         type: type,
              //         stateActions: type == MBScreen.create
              //             ? bs!.first.workflowState!.first
              //                 .actions![index]
              //             : null,
              //       ),
              //     );
              //   }
              // }, type: ButtonType.secondary,
              //                     size: ButtonSize.large,
              //                   ),
              //                 );
              //               },
              //               itemCount:
              // bs!.first.workflowState!.first.actions!.length,
              //             ),
              //     ),
              //   ),
              // );
            },
          );
        },
      ),
    );
  }

  String fetchRoles(String action) {
    switch (action) {
      case "EDIT/RE-SUBMIT":
        return "MB_VERIFIER";
      case "SUBMIT":
        return "MB_VERIFIER";
      case "VERIFY_AND_FORWARD":
        return "MB_APPROVER";
      case "SEND_BACK_TO_ORIGINATOR":
        return "MB_CREATOR";

      default:
        return "MB_VERIFIER";
    }
  }
}
