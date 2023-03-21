import 'package:digit_components/digit_components.dart';
import 'package:digit_components/widgets/atoms/digit_dropdown.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/atoms/radio_button_list.dart';

import '../../models/file_store/file_store_model.dart';
import '../../utils/constants.dart';
import '../../widgets/molecules/file_picker.dart';

class IndividualDetails extends StatefulWidget {
  final void Function() onPressed;
  const IndividualDetails({required this.onPressed, super.key});

  @override
  State<StatefulWidget> createState() {
    return IndividualDetailsState();
  }
}

class IndividualDetailsState extends State<IndividualDetails> {
  String genderController = '';

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    List<MenuItemModel> relationship = [
      MenuItemModel('Father',
          AppLocalizations.of(context).translate('CORE_COMMON_FATHER')),
      MenuItemModel('Husband',
          AppLocalizations.of(context).translate('CORE_COMMON_HUSBAND'))
    ];
    List<MenuItemModel> socialCategory = [
      MenuItemModel(
          AppLocalizations.of(context).translate('CORE_COMMON_GENERAL'),
          'GENERAL'),
      MenuItemModel(
          AppLocalizations.of(context).translate('CORE_COMMON_OBC'), 'OBC'),
      MenuItemModel(
          AppLocalizations.of(context).translate('CORE_COMMON_SC'), 'SC'),
      MenuItemModel(
          AppLocalizations.of(context).translate('CORE_COMMON_ST'), 'ST')
    ];
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
                    t.translate(i18.attendanceMgmt.individualDetails),
                    style: Theme.of(context).textTheme.displayMedium,
                  ),
                  Column(children: [
                    DigitTextFormField(
                      formControlName: 'aadhaarNo',
                      label: t.translate(i18.common.aadhaarNumber),
                    ),
                    DigitTextFormField(
                      formControlName: 'fatherName',
                      label: t.translate(i18.common.fatherName),
                    ),
                    DigitDropdown(
                      label: t.translate(i18.common.relationship),
                      menuItems: relationship,
                      formControlName: 'relationship',
                      onChanged: (String? value) {},
                    ),
                    DigitDateFormPicker(
                        label: t.translate(i18.common.dateOfBirth),
                        isRequired: false,
                        formControlName: 'dob'),
                    DigitRadioButtonList(
                        context,
                        t.translate(i18.common.gender),
                        genderController,
                        '',
                        '',
                        false,
                        Constants.gender, (value) {
                      setState(() {
                        genderController = value;
                      });
                    }),
                    DigitDropdown(
                      label: t.translate(i18.common.socialCategory),
                      menuItems: socialCategory,
                      formControlName: 'socialCategory',
                      onChanged: (String? value) {},
                    ),
                    DigitTextField(
                      label: t.translate(i18.common.mobileNumber),
                      prefixText: '+91',
                      textInputType: TextInputType.number,
                      inputFormatter: [
                        FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                      ],
                    ),
                    FilePickerDemo(
                      callBack: (List<FileStoreModel>? filestore) {},
                      extensions: const ['jpg', 'pdf', 'png'],
                      moduleName: 'works',
                      label: 'File Pick',
                    )
                  ]),
                  const SizedBox(height: 16),
                  SizedBox(
                      height: 90,
                      child: DigitCard(
                          margin: const EdgeInsets.all(0.0),
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
                              ))))
                ],
              ),
            ),
          ],
        );
      },
    );
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        'aadhaarNo': FormControl<String>(value: ''),
        'fatherName': FormControl<String>(value: ''),
        'relationship': FormControl<String>(value: ''),
        'dob': FormControl<String>(value: ''),
        'socialCategory': FormControl<String>(value: ''),
      });
}
