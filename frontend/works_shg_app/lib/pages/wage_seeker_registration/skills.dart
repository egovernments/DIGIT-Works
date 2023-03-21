import 'package:digit_components/widgets/atoms/digit_dropdown.dart';
import 'package:digit_components/widgets/digit_card.dart';
import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';

import '../../blocs/localization/app_localization.dart';

class SkillDetails extends StatefulWidget {
  final void Function() onPressed;
  const SkillDetails({required this.onPressed, super.key});

  @override
  SkillDetailsState createState() {
    return SkillDetailsState();
  }
}

class SkillDetailsState extends State<SkillDetails> {
  List<MenuItemModel> skills = [
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('UNSKILLED_MULIA'),
        'UNSKILLED_MULIA'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('UNSKILLED_PLUMBER'),
        'UNSKILLED_PLUMBER'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('SKILLED_MULIA'),
        'SKILLED_MULIA'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('SKILLED_MASON'),
        'SKILLED_MASON'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('SEMISKILLED_WELDER'),
        'SEMISKILLED_WELDER'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('SEMISKILLED_ELECTRICIAN'),
        'SEMISKILLED_ELECTRICIAN'),
  ];

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return ReactiveFormBuilder(
      form: buildForm,
      builder: (context, form, child) {
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
                    style: Theme.of(context).textTheme.displayMedium,
                  ),
                  Column(children: [
                    DigitDropdown(
                      label: t.translate(i18.attendanceMgmt.skill),
                      menuItems: skills,
                      formControlName: 'skills',
                      onChanged: (String? value) {},
                    ),
                  ]),
                  const SizedBox(height: 16),
                  SizedBox(
                      height: 60,
                      child: DigitCard(
                          margin: const EdgeInsets.all(0.0),
                          child: SizedBox(
                            height: 30,
                            child: DigitElevatedButton(
                                onPressed: () {
                                  widget.onPressed();
                                  if (form.valid) {
                                    print(form.value);
                                  } else {
                                    form.markAllAsTouched();
                                  }
                                },
                                child: Center(
                                  child: Text(t.translate(i18.common.next)),
                                )),
                          )))
                ],
              ),
            ),
          ],
        );
      },
    );
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        'skills': FormControl<String>(value: ''),
      });
}
