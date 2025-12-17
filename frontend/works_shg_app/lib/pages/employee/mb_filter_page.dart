// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/models/models.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/employee/mb/measurement_book.dart';
import 'package:works_shg_app/blocs/employee/mb/project_type.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/localization/localization.dart';
import 'package:works_shg_app/models/employee/mb/mb_inbox_response.dart'
    as status_map;
import 'package:works_shg_app/models/employee/mb/mb_project_type.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/employee/support_services.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import '../../blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

@RoutePage()
class MBFilterPage extends StatefulWidget {
  const MBFilterPage({super.key});

  @override
  State<MBFilterPage> createState() => _MBFilterPageState();
}

class _MBFilterPageState extends State<MBFilterPage> {
  List<String> ward = []; // Initialize ward list
  List<status_map.StatusMap> workflow = []; // Initialize workflow list

  TextEditingController mbNumber = TextEditingController();
  TextEditingController projectId = TextEditingController();
  String projectName = "";
  ProjectType? selectedType;
  bool workShow = true;
  bool project = true;

  // String assign = 'assign';
  String assign = "MB_ASSIGNED_TO_ALL";
  String projectTypeKey = "projectTypeKey";
  String wardNoKey = "wardNoKey";
  String statusMapKey = "statusMapKey";
  String genderController = '';
  @override
  void initState() {
    super.initState();

    mbNumber.addListener(mbNumberUpload);
    projectId.addListener(projectIdUpload);
    // projectName.addListener(projectNameUpload);
  }

  void mbNumberUpload() {
    if (mbNumber.text != "" || projectId.text != "" || projectName != "") {
      setState(() {
        workShow = false;
      });
    } else {
      setState(() {
        workShow = true;
      });
    }
  }

  void projectIdUpload() {
    if (mbNumber.text != "" || projectId.text != "" || projectName != "") {
      setState(() {
        workShow = false;
      });
    } else {
      setState(() {
        workShow = true;
      });
    }
  }

  void projectNameUpload() {
    if (mbNumber.text != "" || projectId.text != "" || projectName != "") {
      setState(() {
        workShow = false;
      });
    } else {
      setState(() {
        workShow = true;
      });
    }
  }

  @override
  void dispose() {
    mbNumber.removeListener(mbNumberUpload);
    projectId.removeListener(projectIdUpload);
    //projectName.removeListener(projectNameUpload);

    mbNumber.dispose();
    projectId.dispose();
    //projectName.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return BlocBuilder<LocalizationBloc, LocalizationState>(
      builder: (context, localizationState) {
        return BlocBuilder<MeasurementInboxBloc, MeasurementInboxState>(
          builder: (context, state) {
            return state.maybeMap(
              orElse: () => const SizedBox.shrink(),
              loaded: (valueMeasurement) {
                return BlocBuilder<WageSeekerLocationBloc,
                    WageSeekerLocationState>(
                  builder: (context, state) {
                    return state.maybeWhen(
                      orElse: () {
                        return const SizedBox.shrink();
                      },
                      loaded: (location) {
                        return Scaffold(
                          backgroundColor:
                              Theme.of(context).colorTheme.paper.primary,
                          // appBar: customAppBar(),
                          // drawer: const MySideBar(),
                          body: ReactiveFormBuilder(
                              form: () => detailBuildForm(valueMeasurement),
                              builder: (BuildContext context,
                                  FormGroup formGroup, Widget? child) {
                                return Padding(
                                  padding: const EdgeInsets.all(16.0),
                                  child: ui_component.ScrollableContent(
                                    backgroundColor: Theme.of(context)
                                        .colorTheme
                                        .paper
                                        .primary,
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.center,
                                    footer: Padding(
                                      padding: const EdgeInsets.symmetric(
                                          horizontal: 8.0),
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        crossAxisAlignment:
                                            CrossAxisAlignment.center,
                                        children: [
                                          Expanded(
                                              flex: 10,
                                              child: ui_component.Button(
                                                  label: t.translate(i18
                                                      .measurementBook.clear),
                                                  onPressed: () {
                                                    context
                                                        .read<
                                                            MeasurementInboxBloc>()
                                                        .add(
                                                          MeasurementBookInboxBlocEvent(
                                                            businessService:
                                                                "MB",
                                                            limit: 10,
                                                            moduleName:
                                                                'measurement-module',
                                                            offset: 0,
                                                            tenantId:
                                                                GlobalVariables
                                                                    .tenantId!,
                                                          ),
                                                        );
                                                    context.router
                                                        .maybePopTop();
                                                  },
                                                  type: ui_component
                                                      .ButtonType.secondary,
                                                  size: ui_component
                                                      .ButtonSize.large)),
                                          const Expanded(
                                              flex: 1,
                                              child: SizedBox.shrink()),
                                          Expanded(
                                              flex: 10,
                                              child: ui_component.Button(
                                                  label: t.translate(i18
                                                      .measurementBook.filter),
                                                  onPressed: () async {
                                                    Map<
                                                            String,
                                                            Map<String,
                                                                dynamic>>
                                                        filterPayload;

                                                    if (workShow && project) {
                                                      filterPayload = {
                                                        "inbox": {
                                                          "tenantId":
                                                              GlobalVariables
                                                                  .tenantId,
                                                          "moduleSearchCriteria":
                                                              {
                                                            "tenantId":
                                                                GlobalVariables
                                                                    .tenantId,
                                                          },
                                                          "processSearchCriteria":
                                                              {
                                                            "businessService": [
                                                              "MB"
                                                            ],
                                                            "moduleName":
                                                                "measurement-service"
                                                          },
                                                          "limit": 10,
                                                          "offset": 0
                                                        }
                                                      };

                                                      if (assign ==
                                                          "MB_ASSIGNED_TO_ME") {
                                                        filterPayload['inbox']![
                                                                    'moduleSearchCriteria']![
                                                                'assignee'] =
                                                            GlobalVariables
                                                                .uuid;
                                                        context
                                                            .read<
                                                                MeasurementInboxBloc>()
                                                            .add(
                                                              MeasurementBookInboxSearchBlocEvent(
                                                                limit: 10,
                                                                offset: 0,
                                                                data:
                                                                    filterPayload,
                                                              ),
                                                            );
                                                        context.router
                                                            .maybePopTop();
                                                      } else {
                                                        Toast.showToast(context,
                                                            message: t.translate(i18
                                                                .common
                                                                .searchCriteria),
                                                            type: ToastType
                                                                .warning);
                                                      }
                                                    } else {
                                                      if (workShow &&
                                                          !project) {
                                                        if (workflow.isEmpty &&
                                                            ward.isNotEmpty) {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                // "status": workflow
                                                                //     .map((e) => e.statusid!)
                                                                //     .toList(),
                                                                "ward": ward,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        } else if (workflow
                                                                .isNotEmpty &&
                                                            ward.isEmpty) {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "status": workflow
                                                                    .map((e) =>
                                                                        e.statusid!)
                                                                    .toList(),
                                                                // "ward": ward,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        } else {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "status": workflow
                                                                    .map((e) =>
                                                                        e.statusid!)
                                                                    .toList(),
                                                                "ward": ward,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        }

                                                        if (assign ==
                                                            "MB_ASSIGNED_TO_ME") {
                                                          filterPayload['inbox']![
                                                                      'moduleSearchCriteria']![
                                                                  'assignee'] =
                                                              GlobalVariables
                                                                  .uuid;
                                                        }
                                                        context
                                                            .read<
                                                                MeasurementInboxBloc>()
                                                            .add(
                                                              MeasurementBookInboxSearchBlocEvent(
                                                                limit: 10,
                                                                offset: 0,
                                                                data:
                                                                    filterPayload,
                                                              ),
                                                            );
                                                      } else {
                                                        if (mbNumber.text !=
                                                                "" &&
                                                            projectId.text ==
                                                                "" &&
                                                            projectName == "") {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "measurementNumber":
                                                                    mbNumber
                                                                        .text,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        } else if (mbNumber.text ==
                                                                "" &&
                                                            projectId.text !=
                                                                "" &&
                                                            projectName == "") {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "projectId":
                                                                    projectId
                                                                        .text,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        } else if (mbNumber.text ==
                                                                "" &&
                                                            projectId.text ==
                                                                "" &&
                                                            projectName != "") {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "projectType":
                                                                    projectName,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        } else {
                                                          filterPayload = {
                                                            "inbox": {
                                                              "tenantId":
                                                                  GlobalVariables
                                                                      .tenantId,
                                                              "moduleSearchCriteria":
                                                                  {
                                                                "tenantId":
                                                                    GlobalVariables
                                                                        .tenantId,
                                                                "measurementNumber":
                                                                    mbNumber
                                                                        .text,
                                                                "projectId":
                                                                    projectId
                                                                        .text,
                                                                "projectType":
                                                                    projectName,
                                                              },
                                                              "processSearchCriteria":
                                                                  {
                                                                "businessService":
                                                                    ["MB"],
                                                                "moduleName":
                                                                    "measurement-service"
                                                              },
                                                              "limit": 10,
                                                              "offset": 0
                                                            }
                                                          };
                                                        }

                                                        if (assign ==
                                                            "MB_ASSIGNED_TO_ME") {
                                                          filterPayload['inbox']![
                                                                      'moduleSearchCriteria']![
                                                                  'assignee'] =
                                                              GlobalVariables
                                                                  .uuid;
                                                        }

                                                        context
                                                            .read<
                                                                MeasurementInboxBloc>()
                                                            .add(
                                                              MeasurementBookInboxSearchBlocEvent(
                                                                limit: 10,
                                                                offset: 0,
                                                                data:
                                                                    filterPayload,
                                                              ),
                                                            );
                                                      }

                                                      context.router
                                                          .maybePopTop();
                                                    }
                                                  },
                                                  type: ui_component
                                                      .ButtonType.primary,
                                                  size: ui_component
                                                      .ButtonSize.large)
                                              //  DigitElevatedButton(
                                              //   child: Text(t.translate(
                                              //       i18.measurementBook.filter)),
                                              //   onPressed: () async {
                                              //     Map<String,
                                              //             Map<String, dynamic>>
                                              //         filterPayload;

                                              //     if (workShow && project) {
                                              //       filterPayload = {
                                              //         "inbox": {
                                              //           "tenantId":
                                              //               GlobalVariables
                                              //                   .tenantId,
                                              //           "moduleSearchCriteria": {
                                              //             "tenantId":
                                              //                 GlobalVariables
                                              //                     .tenantId,
                                              //           },
                                              //           "processSearchCriteria": {
                                              //             "businessService": [
                                              //               "MB"
                                              //             ],
                                              //             "moduleName":
                                              //                 "measurement-service"
                                              //           },
                                              //           "limit": 10,
                                              //           "offset": 0
                                              //         }
                                              //       };

                                              //       if (formGroup.value[assign] ==
                                              //               "MB_ASSIGNED_TO_ME" ||
                                              //           assign ==
                                              //               "MB_ASSIGNED_TO_ME") {
                                              //         filterPayload['inbox']![
                                              //                     'moduleSearchCriteria']![
                                              //                 'assignee'] =
                                              //             GlobalVariables.uuid;
                                              //         context
                                              //             .read<
                                              //                 MeasurementInboxBloc>()
                                              //             .add(
                                              //               MeasurementBookInboxSearchBlocEvent(
                                              //                 limit: 10,
                                              //                 offset: 0,
                                              //                 data: filterPayload,
                                              //               ),
                                              //             );
                                              //         context.router
                                              //             .maybePopTop();
                                              //       } else {
                                              //         ToastUtils.showCustomToast(
                                              //             context,
                                              //             t.translate(i18.common
                                              //                 .searchCriteria),
                                              //             "INFO");

                                              //         // TODO: digit component toast
                                              //         // DigitToast.show(
                                              //         //   context,
                                              //         //   options:
                                              //         //       DigitToastOptions(
                                              //         //     t.translate(i18.common
                                              //         //         .searchCriteria),
                                              //         //     true,
                                              //         //     DigitTheme.instance
                                              //         //         .mobileTheme,
                                              //         //   ),
                                              //         // );
                                              //         // end of it
                                              //       }
                                              //     } else {
                                              //       if (workShow && !project) {
                                              //         if (workflow.isEmpty &&
                                              //             ward.isNotEmpty) {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 // "status": workflow
                                              //                 //     .map((e) => e.statusid!)
                                              //                 //     .toList(),
                                              //                 "ward": ward,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         } else if (workflow
                                              //                 .isNotEmpty &&
                                              //             ward.isEmpty) {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "status": workflow
                                              //                     .map((e) =>
                                              //                         e.statusid!)
                                              //                     .toList(),
                                              //                 // "ward": ward,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         } else {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "status": workflow
                                              //                     .map((e) =>
                                              //                         e.statusid!)
                                              //                     .toList(),
                                              //                 "ward": ward,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         }

                                              //         if (formGroup.value[
                                              //                     assign] ==
                                              //                 "MB_ASSIGNED_TO_ME" ||
                                              //             assign ==
                                              //                 "MB_ASSIGNED_TO_ME") {
                                              //           filterPayload['inbox']![
                                              //                       'moduleSearchCriteria']![
                                              //                   'assignee'] =
                                              //               GlobalVariables.uuid;
                                              //         }
                                              //         context
                                              //             .read<
                                              //                 MeasurementInboxBloc>()
                                              //             .add(
                                              //               MeasurementBookInboxSearchBlocEvent(
                                              //                 limit: 10,
                                              //                 offset: 0,
                                              //                 data: filterPayload,
                                              //               ),
                                              //             );
                                              //       } else {
                                              //         if (mbNumber.text != "" &&
                                              //             projectId.text == "" &&
                                              //             projectName == "") {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "measurementNumber":
                                              //                     mbNumber.text,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         } else if (mbNumber.text ==
                                              //                 "" &&
                                              //             projectId.text != "" &&
                                              //             projectName == "") {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "projectId":
                                              //                     projectId.text,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         } else if (mbNumber.text ==
                                              //                 "" &&
                                              //             projectId.text == "" &&
                                              //             projectName != "") {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "projectType":
                                              //                     projectName,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         } else {
                                              //           filterPayload = {
                                              //             "inbox": {
                                              //               "tenantId":
                                              //                   GlobalVariables
                                              //                       .tenantId,
                                              //               "moduleSearchCriteria":
                                              //                   {
                                              //                 "tenantId":
                                              //                     GlobalVariables
                                              //                         .tenantId,
                                              //                 "measurementNumber":
                                              //                     mbNumber.text,
                                              //                 "projectId":
                                              //                     projectId.text,
                                              //                 "projectType":
                                              //                     projectName,
                                              //               },
                                              //               "processSearchCriteria":
                                              //                   {
                                              //                 "businessService": [
                                              //                   "MB"
                                              //                 ],
                                              //                 "moduleName":
                                              //                     "measurement-service"
                                              //               },
                                              //               "limit": 10,
                                              //               "offset": 0
                                              //             }
                                              //           };
                                              //         }

                                              //         if (formGroup.value[
                                              //                     assign] ==
                                              //                 "MB_ASSIGNED_TO_ME" ||
                                              //             assign ==
                                              //                 "MB_ASSIGNED_TO_ME") {
                                              //           filterPayload['inbox']![
                                              //                       'moduleSearchCriteria']![
                                              //                   'assignee'] =
                                              //               GlobalVariables.uuid;
                                              //         }

                                              //         context
                                              //             .read<
                                              //                 MeasurementInboxBloc>()
                                              //             .add(
                                              //               MeasurementBookInboxSearchBlocEvent(
                                              //                 limit: 10,
                                              //                 offset: 0,
                                              //                 data: filterPayload,
                                              //               ),
                                              //             );
                                              //       }

                                              //       context.router.maybePopTop();
                                              //     }
                                              //   },
                                              // ),
                                              )
                                        ],
                                      ),
                                    ),
                                    children: [
                                      Row(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.start,
                                        mainAxisAlignment:
                                            MainAxisAlignment.end,
                                        children: [
                                          Button(
                                            size: ButtonSize.large,
                                            type: ButtonType.tertiary,
                                            label: '',
                                            suffixIcon: Icons.close,
                                            // textDirection: TextDirection.rtl,
                                            onPressed: () {
                                              context
                                                  .read<MeasurementInboxBloc>()
                                                  .add(
                                                    MeasurementBookInboxBlocEvent(
                                                      businessService: "MB",
                                                      limit: 10,
                                                      moduleName:
                                                          'measurement-module',
                                                      offset: 0,
                                                      tenantId: GlobalVariables
                                                          .tenantId!,
                                                    ),
                                                  );
                                              context.router.maybePopTop();
                                            },
                                            // icon: Icons.close,
                                            // iconSize: 30,
                                            // iconColor:
                                            //     const DigitColors().black,
                                          ),
                                        ],
                                      ),

                                      Row(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.center,
                                        mainAxisAlignment:
                                            MainAxisAlignment.start,
                                        children: [
                                          SizedBox(
                                            child: Icon(
                                              Icons.filter_alt,
                                              size: 35,
                                              color: Theme.of(context)
                                                  .colorTheme
                                                  .primary
                                                  .primary1,
                                            ),
                                          ),
                                          Padding(
                                            padding: const EdgeInsets.only(
                                                left: 0.0),
                                            child: DigitTextBlock(
                                                heading: t.translate(
                                                    i18.measurementBook.filter),
                                                headingStyle: Theme.of(context)
                                                    .digitTextTheme(context)
                                                    .headingXl
                                                    .copyWith(
                                                        color: Theme.of(context)
                                                            .colorTheme
                                                            .text
                                                            .primary)),
                                          ),
                                        ],
                                      ),

                                      // workShow
                                      //     ? DigitRadioButtonList<String>(
                                      //         isEnabled: true,
                                      //         labelText: t.translate(
                                      //             i18.common.assignee),
                                      //         formControlName: assign,
                                      //         options: const [
                                      //           'MB_ASSIGNED_TO_ME',
                                      //           'MB_ASSIGNED_TO_ALL'
                                      //         ],
                                      //         isRequired: false,
                                      //         valueMapper: (value) =>
                                      //             t.translate(value),
                                      //         onValueChange: (value) {
                                      //           setState(() {
                                      //             assign = value;
                                      //           });
                                      //         },
                                      //       )
                                      //     : const SizedBox.shrink(),
                                      workShow
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 6.0,
                                                  left: 0.0,
                                                  bottom: 16),
                                              child: Row(
                                                mainAxisAlignment:
                                                    MainAxisAlignment.start,
                                                crossAxisAlignment:
                                                    CrossAxisAlignment.center,
                                                children: [
                                                  RadioList(
                                                    groupValue: assign,
                                                    containerPadding:
                                                        const EdgeInsets.only(
                                                            left: 0.0,
                                                            bottom: 16,
                                                            top: 16),
                                                    radioButtons: [
                                                      RadioButtonModel(
                                                          code:
                                                              "MB_ASSIGNED_TO_ME",
                                                          name: t.translate(
                                                              "MB_ASSIGNED_TO_ME")),
                                                      RadioButtonModel(
                                                          code:
                                                              "MB_ASSIGNED_TO_ALL",
                                                          name: t.translate(
                                                              "MB_ASSIGNED_TO_ALL")),
                                                    ],
                                                    onChanged: (RadioButtonModel
                                                        value) {
                                                      setState(() {
                                                        assign = value.code;
                                                      });
                                                    },
                                                  ),
                                                ],
                                              ),
                                            )
                                          : const SizedBox.shrink(),

                                      // project
                                      //     ? DigitTextField(
                                      // label: t.translate(
                                      //     i18.measurementBook.mbNumber),
                                      //         controller: mbNumber,
                                      //       )
                                      //     : const SizedBox.shrink(),
                                      project
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 0.0),
                                              child: ui_component.LabeledField(
                                                label: t.translate(i18
                                                    .measurementBook.mbNumber),
                                                child: DigitTextFormInput(
                                                  controller: mbNumber,
                                                ),
                                              ),
                                            )
                                          : const SizedBox.shrink(),
                                      project
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 16.0),
                                              child: ui_component.LabeledField(
                                                label: t.translate(i18
                                                    .measurementBook.projectId),
                                                child: DigitTextFormInput(
                                                  controller: projectId,
                                                ),
                                              ),
                                            )
                                          : const SizedBox.shrink(),

                                      project
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 16.0),
                                              child: ui_component.LabeledField(
                                                label: t.translate(i18
                                                    .measurementBook
                                                    .projectType),
                                                child: BlocBuilder<
                                                    ProjectTypeBloc,
                                                    ProjectTypeState>(
                                                  builder: (context, state) {
                                                    return state.maybeMap(
                                                      orElse: () =>
                                                          const SizedBox
                                                              .shrink(),
                                                      loaded: (value) {
                                                        return ui_component
                                                            .DigitDropdown<
                                                                ProjectType>(
                                                          onSelect: (value) {
                                                            setState(() {
                                                              projectName =
                                                                  value!.code!;
                                                              //selectedType = ProjectType(name: value.name,code: value.code) ;
                                                              workShow = false;
                                                            });
                                                          },
                                                          // formControlName:
                                                          //     projectTypeKey,
                                                          // onChanged: (value) {
                                                          // setState(() {
                                                          //   projectName =
                                                          //       value!.code!;
                                                          //   selectedType = value;
                                                          //   workShow = false;
                                                          // });
                                                          // },
                                                          // initialValue:
                                                          //     selectedType,
                                                          // label: t.translate(i18
                                                          //     .measurementBook
                                                          //     .projectType),
                                                          items: value
                                                              .mbProjectType!
                                                              .mdmsRes!
                                                              .mbWorks!
                                                              .projectType!
                                                              .map((e) => DropdownItem(
                                                                  name:
                                                                      e.name ??
                                                                          '',
                                                                  code:
                                                                      e.code ??
                                                                          ''))
                                                              .toList(),

                                                          // valueMapper:  value
                                                          //     .mbProjectType!
                                                          //     .mdmsRes!
                                                          //     .mbWorks!
                                                          //     .projectType!.map((e) => ValueMapper(name: t.translate(e.name??''), code: e.code??'')).toList()
                                                        );
                                                      },
                                                      error: (value) {
                                                        return const SizedBox
                                                            .shrink();
                                                      },
                                                      loading: (value) {
                                                        return const SizedBox
                                                            .shrink();
                                                      },
                                                    );
                                                  },
                                                ),
                                              ),
                                            )
                                          : const SizedBox.shrink(),

                                      // end of this
                                      workShow
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 16.0),
                                              child: ui_component.LabeledField(
                                                label: t
                                                    .translate(i18.common.ward),
                                                child: ui_component
                                                    .DigitDropdown<String>(
                                                  // formControlName: wardNoKey,
                                                  // onChanged: (value) {
                                                  // setState(() {
                                                  //   ward.add(value);
                                                  //   project = false;
                                                  // });
                                                  // },
                                                  // initialValue: ward.isNotEmpty
                                                  //     ? ward.first
                                                  //     : null,
                                                  // label:
                                                  // t.translate(i18.common.ward),
                                                  onSelect: (value) {
                                                    setState(() {
                                                      ward.add(value.code);
                                                      project = false;
                                                    });
                                                  },
                                                  items: location!
                                                      .tenantBoundaryList!
                                                      .first
                                                      .boundaryList!.reversed
                                                      .map((e) => DropdownItem(
                                                          name: t.translate(
                                                              '${GlobalVariables.tenantId!.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.code.toString()}'),
                                                          code: e.code
                                                              .toString()))
                                                      .toList(),
                                                  // valueMapper: (value) {
                                                  //   return t.translate(
                                                  //       convertToWard(
                                                  //           value.toString()));

                                                  // },
                                                ),
                                              ),
                                            )
                                          : const SizedBox.shrink(),
                                      // workShow
                                      //     ? DigitReactiveDropdown<String>(
                                      //         formControlName: wardNoKey,
                                      //         onChanged: (value) {
                                      //           setState(() {
                                      //             ward.add(value);
                                      //             project = false;
                                      //           });
                                      //         },
                                      //         initialValue: ward.isNotEmpty
                                      //             ? ward.first
                                      //             : null,
                                      //         label:
                                      //             t.translate(i18.common.ward),
                                      //         menuItems: location!
                                      //             .tenantBoundaryList!
                                      //             .first
                                      //             .boundaryList!
                                      //             .map((e) => e.code.toString())
                                      //             .toList(),
                                      //         valueMapper: (value) {
                                      //           return t.translate(
                                      //               convertToWard(
                                      //                   value.toString()));
                                      //           // return value.toString();
                                      //         },
                                      //       )
                                      //     : const SizedBox.shrink(),
                                      // workShow
                                      //     ? DigitReactiveDropdown<
                                      //         status_map.StatusMap>(
                                      //         formControlName: statusMapKey,
                                      //         onChanged: (value) {
                                      //           setState(() {
                                      //             workflow.add(value);
                                      //             project = false;
                                      //           });
                                      //         },
                                      //         initialValue: workflow.isNotEmpty
                                      //             ? workflow.first
                                      //             : null,
                                      //         label: t.translate(i18
                                      //             .measurementBook
                                      //             .workflowState),
                                      //         menuItems: valueMeasurement
                                      //             .mbInboxResponse.statusMap!
                                      //             .map((e) => e)
                                      //             .toList(),
                                      //         valueMapper: (value) {
                                      //           return t.translate(
                                      //               "MB_WFMB_STATE_${value.state.toString()}");
                                      //         },
                                      //       )
                                      //     : const SizedBox.shrink(),

                                      workShow
                                          ? Padding(
                                              padding: const EdgeInsets.only(
                                                  top: 16.0),
                                              child: ui_component.LabeledField(
                                                label: t.translate(i18
                                                    .measurementBook
                                                    .workflowState),
                                                child:
                                                    ui_component.DigitDropdown<
                                                        status_map.StatusMap>(
                                                  // formControlName: statusMapKey,
                                                  // onChanged: (value) {
                                                  // setState(() {
                                                  //   workflow.add(value);
                                                  //   project = false;
                                                  // });
                                                  // },
                                                  // initialValue: workflow.isNotEmpty
                                                  //     ? workflow.first
                                                  //     : null,
                                                  // label: t.translate(i18
                                                  //     .measurementBook
                                                  //     .workflowState),
                                                  onSelect: (value) {
                                                    setState(() {
                                                      workflow.add(
                                                          status_map.StatusMap(
                                                              statusid:
                                                                  value.code));
                                                      project = false;
                                                    });
                                                  },
                                                  items: valueMeasurement
                                                      .mbInboxResponse
                                                      .statusMap!
                                                      .map((e) => DropdownItem(
                                                          name: t.translate(
                                                              "MB_WFMB_STATE_${e.state.toString()}"),
                                                          code: e.statusid!))
                                                      .toList(),
                                                  // valueMapper: (value) {
                                                  // return t.translate(
                                                  //     "MB_WFMB_STATE_${value.state.toString()}");
                                                  // },
                                                ),
                                              ),
                                            )
                                          : const SizedBox.shrink(),
                                    ],
                                  ),
                                );
                              }),
                        );
                      },
                    );
                  },
                );
              },
            );
          },
        );
      },
    );
  }

  FormGroup detailBuildForm(dynamic valueMeasurement) =>
      fb.group(<String, Object>{
        assign: FormControl<String>(value: "MB_ASSIGNED_TO_ALL"),
        projectTypeKey: FormControl<ProjectType>(value: selectedType),
        wardNoKey: FormControl<String>(
            value: valueMeasurement.data['inbox'] != null
                ? valueMeasurement.data['inbox']!['moduleSearchCriteria']
                            ['ward'] !=
                        null
                    ? valueMeasurement.data['inbox']!['moduleSearchCriteria']
                        ['ward'][0]
                    : null
                : ward.isNotEmpty
                    ? ward.first
                    : null),
        statusMapKey: FormControl<status_map.StatusMap>(value: null),
      });

  String convertToWard(String input) {
    String tenant = Conversion.splitTenant(GlobalVariables.tenantId!);
    String result = "${tenant}_ADMIN_${input.toUpperCase()}";
    return result;
  }
}
