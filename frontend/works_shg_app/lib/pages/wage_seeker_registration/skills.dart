import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/models/wage_seeker/skill_details_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/financial_details_model.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../widgets/atoms/multiselect_checkbox.dart';

class SkillDetailsPage extends StatefulWidget {
  final void Function() onPressed;
  final WageSeekerMDMS? wageSeekerMDMS;
  const SkillDetailsPage(
      {required this.onPressed, this.wageSeekerMDMS, super.key});

  @override
  SkillDetailsState createState() {
    return SkillDetailsState();
  }
}

class SkillDetailsState extends State<SkillDetailsPage> {
  bool stateChange = false;
  List<String> selectedOptions = [];
  IndividualDetails? individualDetails = IndividualDetails();
  LocationDetails? locationDetails = LocationDetails();
  SkillDetails? skillDetails = SkillDetails();
  FinancialDetails? financialDetails = FinancialDetails();

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    skillDetails = registrationState.skillDetails;
    individualDetails = registrationState.individualDetails;
    locationDetails = registrationState.locationDetails;
    financialDetails = registrationState.financialDetails;
    if (registrationState.skillDetails != null &&
        registrationState.skillDetails?.individualSkills != null) {
      selectedOptions = registrationState.skillDetails!.individualSkills!
              .any((a) => a.type == null)
          ? []
          : registrationState.skillDetails!.individualSkills!
              .where((e) => e.type != null)
              .map((e) => '${e.level}.${e.type}')
              .toList();
    }
  }

  void _onSelectedOptionsChanged(List<String> options) {
    setState(() {
      selectedOptions = options;
    });
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    List<String> skills = widget.wageSeekerMDMS!.commonMDMS!.wageSeekerSkills!
        .map((e) => e.code)
        .toList();

    return StatefulBuilder(
        builder: (BuildContext context, StateSetter setState) {
      return Column(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          DigitCard(
            margin: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  t.translate(i18.attendanceMgmt.skillDetails),
                  style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                      ?.apply(color: const DigitColors().black),
                ),
                Column(children: [
                  StatefulBuilder(
                      builder: (BuildContext context, StateSetter setState) {
                    return MultiSelectSearchCheckBox(
                      label: t.translate(i18.attendanceMgmt.skill) + '*',
                      onChange: _onSelectedOptionsChanged,
                      options: skills,
                      selectedOptions: selectedOptions,
                    );
                  })
                ]),
                const SizedBox(height: 16),
                DigitCard(
                    child: Center(
                  child: DigitElevatedButton(
                      onPressed: selectedOptions != null &&
                              selectedOptions.isNotEmpty
                          ? () {
                              final skillList = SkillDetails(
                                  individualSkills: selectedOptions
                                      .map((e) => IndividualSkill(
                                          type: e.toString().split('.').last,
                                          // .replaceAll('_', '')
                                          // .capitalize(),
                                          level: e.toString().split('.').first))
                                      .toList());
                              BlocProvider.of<WageSeekerBloc>(context).add(
                                WageSeekerCreateEvent(
                                    individualDetails: individualDetails,
                                    skillDetails: skillList,
                                    locationDetails: locationDetails,
                                    financialDetails: financialDetails),
                              );
                              widget.onPressed();
                            }
                          : null,
                      child: Center(
                        child: Text(t.translate(i18.common.next)),
                      )),
                ))
              ],
            ),
          ),
        ],
      );
    });
  }
}
