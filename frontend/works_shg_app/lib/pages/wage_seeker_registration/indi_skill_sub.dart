import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/atoms/multiselect_checkbox.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

class IndividualSkillSubPage extends StatefulWidget {
  final List<String> relationship;
  final List<String> gender;
  final List<String> socialCategory;
  final List<String> skills;
  final String? photo;
  final Function(int page) onPageChanged;
  final SkillDetails? skillDetails;
  const IndividualSkillSubPage({
    super.key,
    required this.relationship,
    required this.gender,
    required this.photo,
    required this.skills,
    required this.socialCategory,
    required this.onPageChanged,
    required this.skillDetails,
  });

  @override
  State<IndividualSkillSubPage> createState() => _IndividualSkillSubPageState();
}

class _IndividualSkillSubPageState extends State<IndividualSkillSubPage> {
  List<String> selectedOptions = [];
  List<String> initialOptions = [];
  List<String> options = [];

  @override
  void initState() {
    if (widget.skillDetails != null &&
        widget.skillDetails?.individualSkills != null) {
      selectedOptions =
          widget.skillDetails!.individualSkills!.any((a) => a.type == null)
              ? []
              : widget.skillDetails!.individualSkills!
                  .where((e) => e.type != null)
                  .map((e) => '${e.level}')
                  .toList();
      initialOptions =
          widget.skillDetails!.individualSkills!.any((a) => a.type == null)
              ? []
              : widget.skillDetails!.individualSkills!
                  .where((e) => e.type != null)
                  .map((e) => '${e.level}')
                  .toList();
    }

    options = widget.skills;

    super.initState();
  }

  void _onSelectedOptionsChanged(List<String> options) {
    setState(() {
      selectedOptions = options;
    });
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context);

    return SingleChildScrollView(
      child: SizedBox(
        height: MediaQuery.sizeOf(context).height * 0.72,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            SizedBox(
              height: MediaQuery.sizeOf(context).height * 0.50,
              child: ui_card.DigitCard(
                margin: const EdgeInsets.all(8),
                cardType: CardType.primary,
                children: [
                  DigitTextBlock(
                    //  "Individual's Skill Details",
                    heading: t.translate(i18.wageSeeker.individualSkillHeader),
                  ),
                  

                  MultiSelectDropDown(
                    //  showSelectAll: true,
                    isSearchable: true,
                    initialOptions: selectedOptions
                        .map((e) => DropdownItem(
                            name: t.translate("COMMON_MASTERS_SKILLS_$e"),
                            code: e))
                        .toList(),
                    //  selectAllText: 'Select All',
                    // options: const [DropdownItem(name: 'sdfsdf', code: '1'),
                    // DropdownItem(name: 'sdfsdhgf', code: '2'),
                    // ],
                    options: options
                        .map((e) => DropdownItem(
                            name: t.translate("COMMON_MASTERS_SKILLS_$e"),
                            code: e))
                        .toList(),
                    onOptionSelected: (List<DropdownItem> selectedOptionss) {
                      _onSelectedOptionsChanged(
                          selectedOptionss.map((e) => e.code).toList());
                    },
                    // selectedOptions: selectedOptions.map((e) => DropdownItem(
                    //     name: t.translate(
                    //         "COMMON_MASTERS_SKILLS_${e.toString()}"),
                    //     code: e.toString()))
                    // .toList(),
                  ),
                ],
              ),
            ),
             const Align(
                alignment: Alignment.bottomCenter,
                child: PoweredByDigit(
                  version: Constants.appVersion,
                ),
              ),
            Center(
              child: Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 10, vertical: 12),
                color: Colors.white,
                child: Button(
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                    mainAxisSize: MainAxisSize.max,
                    onPressed: () {
                      if (!getSkillsValid()) {
                        Notifiers.getToastMessage(context,
                            i18.wageSeeker.selectSkillValidation, 'ERROR');
                        // Toast.showToast(context,
                        //     message: t.translate(
                        //         i18.wageSeeker.selectSkillValidation),
                        //     type: ToastType.error);
                      } else if (selectedOptions.isEmpty) {
                        Notifiers.getToastMessage(
                            context, i18.wageSeeker.skillsRequired, 'ERROR');
                        // Toast.showToast(context,
                        //     message: t.translate(i18.wageSeeker.skillsRequired),
                        //     type: ToastType.error);
                      } else {
                        final skillList = SkillDetails(
                            individualSkills: selectedOptions
                                .map((e) => IndividualSkill(
                                    type: e.toString().split('.').last,
                                    level: e.toString().split('.').first))
                                .toList());

                        context.read<WageSeekerBloc>().add(
                              WageSeekerSkillCreateEvent(
                                  skillDetails: skillList),
                            );

                        widget.onPageChanged(3);
                      }
                    },
                    label: t.translate(i18.common.next)),
              ),
            ),
          ],
        ),
      ),
    );
  }

  bool getSkillsValid() {
    Map<String, int> beforeDotCount = {};
    Map<String, int> afterDotCount = {};

    for (String skill in selectedOptions) {
      List<String> skillParts = skill.split("_");
      String beforeDot = skillParts[0];
      String afterDot = skillParts[1];

      beforeDotCount[beforeDot] = (beforeDotCount[beforeDot] ?? 0) + 1;
      afterDotCount[afterDot] = (afterDotCount[afterDot] ?? 0) + 1;
    }

    int countAfterDot = afterDotCount.values.where((count) => count > 1).length;

    if (countAfterDot > 0) {
      return false;
    }
    return true;
  }
}
