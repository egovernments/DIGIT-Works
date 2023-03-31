import 'package:digit_components/digit_components.dart';
import 'package:digit_components/widgets/atoms/digit_dropdown.dart';
import 'package:flutter/material.dart';
import 'package:multi_select_flutter/multi_select_flutter.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';

import '../../blocs/localization/app_localization.dart';

class LocationDetails extends StatefulWidget {
  final void Function() onPressed;
  const LocationDetails({required this.onPressed, super.key});

  @override
  LocationDetailsState createState() => LocationDetailsState();
}

class LocationDetailsState extends State<LocationDetails> {
  List<String> _selectedOptions = ['001'];
  List<MultiSelectItem> skills = [
    MultiSelectItem('UNSKILLED.MULIA', 'Unskilled Mulia'),
    MultiSelectItem('UNSKILLED.PLUMBER', 'Unskilled Plumber'),
    MultiSelectItem('SKILLED.MULIA', 'Skilled Mulia'),
    MultiSelectItem('SKILLED.MASON', 'Skilled Mason'),
    MultiSelectItem('SEMISKILLED.WELDER', 'Semiskilled Welder'),
    MultiSelectItem('SEMISKILLED.ELECTRICIAN', 'Semiskilled Electrician'),
  ];
  List<MenuItemModel> city = [
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('pg.cityA'),
        'pg.cityA'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('pg.cityB'),
        'pg.cityB'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('pg.cityC'),
        'pg.cityC')
  ];
  List<MenuItemModel> ward = [
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('locality'),
        'pg.cityA'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('pg.cityB'),
        'pg.cityB'),
    MenuItemModel(
        AppLocalizations.of(scaffoldMessengerKey.currentContext!)
            .translate('pg.cityC'),
        'pg.cityC')
  ];
  List<MultiSelectItem> selectedSkills = [];

  List<String> selectedItems = [];
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
                    t.translate(i18.common.locationDetails),
                    style: Theme.of(context).textTheme.displayMedium,
                  ),
                  Column(children: [
                    DigitTextFormField(
                      formControlName: 'pinCode',
                      label: t.translate(i18.common.pinCode),
                    ),
                    DigitDropdown(
                      label: t.translate(i18.common.city),
                      menuItems: city,
                      formControlName: 'city',
                      onChanged: (String? value) {},
                    ),
                    DigitDropdown(
                      label: t.translate(i18.common.ward),
                      menuItems: city,
                      formControlName: 'ward',
                      onChanged: (String? value) {},
                    ),
                    DigitDropdown(
                      label: t.translate(i18.common.locality),
                      menuItems: city,
                      formControlName: 'locality',
                      onChanged: (String? value) {},
                    ),
                    DigitTextFormField(
                      formControlName: 'streetName',
                      label: t.translate(i18.common.streetName),
                    ),
                    DigitTextFormField(
                      formControlName: 'doorNo',
                      label: t.translate(i18.common.doorNo),
                    ),
                    // SkillsDropdown(),
                    // MultiSelectCheckboxDropdown(
                    //     options: const ['001', '002', '003'],
                    //     selectedOptions: selectedItems,
                    //     label: 'Select Skill'),
                    // MultiSelectDialogField(
                    //   items: skills
                    //       .map((e) => MultiSelectItem(e, e.label))
                    //       .toList(),
                    //   onConfirm: (values) {
                    //     selectedSkills = values;
                    //   },
                    //   buttonIcon: Icon(Icons.search),
                    //   separateSelectedItems: true,
                    //   dialogHeight: 300,
                    //   dialogWidth: 300,
                    //   searchable: true,
                    //   decoration: BoxDecoration(
                    //       shape: BoxShape.rectangle,
                    //       border: Border.all(width: 2.0),
                    //       color: DigitColors().white),
                    //   // unselectedColor: DigitColors().seaShellGray,
                    //   // checkColor: DigitColors().burningOrange,
                    //   // selectedColor: DigitColors().burningOrange,
                    //   listType: MultiSelectListType.LIST,
                    // ),
                    // MultiselectDropdown(skills: [
                    //   Skill(
                    //       name: 'Unskilled',
                    //       categories: [SkillCategory(name: 'Mulia')]),
                    //   Skill(
                    //       name: 'Skilled',
                    //       categories: [SkillCategory(name: 'Plumber')])
                    // ]),
                  ]),
                  const SizedBox(height: 16),
                  DigitCard(
                      child: Center(
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
                  ))
                ],
              ),
            ),
          ],
        );
      },
    );
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        'pinCode': FormControl<String>(value: ''),
        'city': FormControl<String>(value: ''),
        'ward': FormControl<String>(value: ''),
        'locality': FormControl<String>(value: ''),
        'streetName': FormControl<String>(value: ''),
        'doorNo': FormControl<String>(value: ''),
      });
}
