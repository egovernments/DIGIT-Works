// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/employee/work_order/workorder_book.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/blocs/organisation/org_search_bloc.dart';
import 'package:works_shg_app/blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import 'package:works_shg_app/models/organisation/organisation_model.dart';

import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/employee/support_services.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/widgets/mb/custom_side_bar.dart';
import 'package:works_shg_app/widgets/new_custom_app_bar.dart';

import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import 'package:works_shg_app/widgets/loaders.dart' as shg_loader;

@RoutePage()
class WOFilterPage extends StatefulWidget {
  const WOFilterPage({super.key});

  @override
  State<WOFilterPage> createState() => _WOFilterPageState();
}

class _WOFilterPageState extends State<WOFilterPage> {
  List<OrganisationModel>? orgId; // Initialize ward list
  // List<statusMap.StatusMap> workflow = []; // Initialize workflow list
  List<String> ward = [];
  TextEditingController woNumber = TextEditingController();
  TextEditingController projectId = TextEditingController();
  TextEditingController projectName = TextEditingController();
  bool workShow = true;
  bool project = true;

  String? orgNumberKey;
  String wardNoKey = "wardNoKey";
  String genderController = '';
  @override
  void initState() {
    super.initState();

    woNumber.addListener(mbNumberUpload);
    projectId.addListener(projectIdUpload);
    projectName.addListener(projectNameUpload);
  }

  void mbNumberUpload() {
    if (woNumber.text != "" || projectId.text != "" || projectName.text != "") {
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
    if (woNumber.text != "" || projectId.text != "" || projectName.text != "") {
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
    if (woNumber.text != "" || projectId.text != "" || projectName.text != "") {
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
    woNumber.removeListener(mbNumberUpload);
    projectId.removeListener(projectIdUpload);
    projectName.removeListener(projectNameUpload);

    woNumber.dispose();
    projectId.dispose();
    projectName.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);

    return BlocBuilder<ORGSearchBloc, ORGSearchState>(
      builder: (context, state) {
        return state.maybeWhen(
          orElse: () {
            return const SizedBox.shrink();
          },
          loaded: (organization) {
            return Scaffold(
              backgroundColor: Theme.of(context).colorTheme.paper.primary,
              // appBar: customAppBar(),
              // drawer: const MySideBar(),
              body: ReactiveFormBuilder(
                  form: detailBuildForm,
                  builder: (BuildContext context, FormGroup formGroup,
                      Widget? child) {
                    return Padding(
                      padding:
                          EdgeInsets.all(Theme.of(context).spacerTheme.spacer4),
                      child: ScrollableContent(
                        backgroundColor:
                            Theme.of(context).colorTheme.paper.primary,
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        footer: Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 8.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Expanded(
                                flex: 10,
                                child: Button(
                                  mainAxisSize: MainAxisSize.max,
                                  type: ButtonType.secondary,
                                  size: ButtonSize.large,
                                  label: t.translate(i18.measurementBook.clear),
                                  onPressed: () {
                                    context.read<WorkOrderInboxBloc>().add(
                                          WorkOrderInboxBlocCreateEvent(
                                            businessService: "MB",
                                            limit: 10,
                                            moduleName: 'contract-service',
                                            offset: 0,
                                            tenantId: GlobalVariables.tenantId!,
                                          ),
                                        );
                                    context.router.maybePopTop();
                                  },
                                ),
                              ),
                              const Expanded(flex: 1, child: SizedBox.shrink()),
                              Expanded(
                                flex: 10,
                                child: Button(
                                  mainAxisSize: MainAxisSize.max,
                                  type: ButtonType.primary,
                                  size: ButtonSize.large,
                                  label:
                                      t.translate(i18.measurementBook.filter),
                                  onPressed: () async {
                                    Map<String, dynamic> payload;
                                    String? selectedOrgId;
                                    if (orgNumberKey != null) {
                                      // OrganisationModel data =
                                      //     formGroup.value[orgNumberKey]!
                                      //         as OrganisationModel;
                                      selectedOrgId = orgNumberKey;
                                    }

                                    payload = {
                                      "status": "ACTIVE",
                                      "tenantId": GlobalVariables.tenantId ??
                                          GlobalVariables.organisationListModel!
                                              .organisations!.first.tenantId,
                                      "contractNumber": woNumber.text,
                                      "ward": ward,
                                      "orgIds": selectedOrgId != null
                                          ? [selectedOrgId]
                                          : [],
                                      "wfStatus": ["ACCEPTED", "APPROVED"],
                                      "pagination": {
                                        "limit": "10",
                                        "offSet": 0,
                                        "sortBy": "lastModifiedTime",
                                        "order": "desc"
                                      }
                                    };

                                    context.read<WorkOrderInboxBloc>().add(
                                        WorkOrderInboxSearchBlocEvent(
                                            data: payload,
                                            limit: 10,
                                            offset: 0));
                                    context.router.maybePopTop();
                                  },
                                ),
                              )
                            ],
                          ),
                        ),
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.end,
                            children: [
                              Button(
                                label: '',
                                size: ButtonSize.large,
                                type: ButtonType.tertiary,
                                onPressed: () {
                                  context.read<WorkOrderInboxBloc>().add(
                                        WorkOrderInboxBlocCreateEvent(
                                          businessService: "MB",
                                          limit: 10,
                                          moduleName: 'contract-service',
                                          offset: 0,
                                          tenantId: GlobalVariables.tenantId!,
                                        ),
                                      );
                                  context.router.maybePopTop();
                                },
                                suffixIcon: Icons.close,
                              ),
                            ],
                          ),
                          Row(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisAlignment: MainAxisAlignment.start,
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
                                padding: const EdgeInsets.only(left: 0.0),
                                child: DigitTextBlock(
                                    heading:
                                        t.translate(i18.measurementBook.filter),
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

                          Padding(
                            padding: EdgeInsets.only(
                                top: Theme.of(context).spacerTheme.spacer4),
                            child: LabeledField(
                              label: t.translate(
                                  i18.measurementBook.workOrderNumber),
                              child: DigitTextFormInput(
                                controller: woNumber,
                              ),
                            ),
                          ),

// new

                          Padding(
                            padding: EdgeInsets.only(
                                top: Theme.of(context).spacerTheme.spacer4),
                            child: LabeledField(
                              label: t.translate(i18.measurementBook.cboName),
                              child: DigitDropdown<OrganisationModel>(
                                onSelect: (value) {
                                  setState(() {
                                    orgNumberKey = value.code;
                                  });
                                },
                                // suggestionsCallback: (items, pattern) {
                                //   return items
                                //       .where((obj) => obj.name!
                                //           .toLowerCase()
                                //           .contains(pattern.toLowerCase()))
                                //       .toList();
                                // },
                                //label: t.translate(i18.measurementBook.cboName),
                                items: organization!.organisations!
                                    .map((e) => DropdownItem(
                                        name: e.name!, code: e.id!))
                                    .toList(),
                                // formControlName: orgNumberKey,
                                // valueMapper: (value) {
                                //   return value.name!;
                                // },
                              ),
                            ),
                          ),

//
                          BlocBuilder<WageSeekerLocationBloc,
                              WageSeekerLocationState>(
                            builder: (context, value) {
                              return value.maybeMap(
                                orElse: () => const SizedBox.shrink(),
                                loaded: (location) {
                                  return Padding(
                                    padding: EdgeInsets.only(
                                        top: Theme.of(context)
                                            .spacerTheme
                                            .spacer4),
                                    child: LabeledField(
                                      label: t.translate(i18.common.ward),
                                      child: DigitDropdown<String>(
                                        onSelect: (value) {
                                          setState(() {
                                            ward.add(value.code);
                                          });
                                        },
                                        items: location
                                            .location!
                                            .tenantBoundaryList!
                                            .first
                                            .boundaryList!.reversed
                                            .map((e) => DropdownItem(
                                                name: t.translate(
                                                    '${GlobalVariables.tenantId!.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.code.toString()}'),
                                                code: e.code.toString()))
                                            .toList()
                                            .toList(),
                                      ),
                                    ),
                                  );
                                },
                                loading: (value) {
                                  return const SizedBox.shrink();
                                },
                              );
                            },
                          )
                        ],
                      ),
                    );
                  }),
            );
          },
          loading: () {
            return Scaffold(
              // appBar: customAppBar(),
              // drawer: const MySideBar(),
              body: Center(
                child: shg_loader.Loaders.circularLoader(context),
              ),
            );
          },
        );
      },
    );
  }

  FormGroup detailBuildForm() => fb.group(<String, Object>{
        // orgNumberKey: FormControl<OrganisationModel>(value: null),
        wardNoKey: FormControl<String>(value: null),
      });

  String convertToWard(String input) {
    String tenant = Conversion.splitTenant(GlobalVariables.tenantId!);
    String result = "${tenant}_ADMIN_${input.toUpperCase()}";
    return result;
  }
}
