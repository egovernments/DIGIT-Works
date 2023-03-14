import 'package:digit_components/widgets/digit_card.dart';
import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';

import '../../models/file_store/file_store_model.dart';
import '../../widgets/atoms/digit_text_form_field.dart';
import '../../widgets/molecules/file_picker.dart';

class IndividualDetails extends StatelessWidget {
  final void Function() onPressed;
  const IndividualDetails({required this.onPressed, super.key});

  @override
  Widget build(BuildContext context) {
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
                    'Individual Details',
                    style: Theme.of(context).textTheme.displayMedium,
                  ),
                  Column(children: [
                    const DigitTextFormField(
                      formControlName: 'administrationArea',
                      label: 'Administration Area',
                    ),
                    const DigitTextFormField(
                      formControlName: 'housholdNo',
                      label: 'Household Location',
                    ),
                    const DigitTextFormField(
                      formControlName: 'locality',
                      label: 'LOCALITY',
                    ),
                    const DigitTextFormField(
                      formControlName: 'state',
                      label: 'State',
                    ),
                    const DigitTextFormField(
                      formControlName: 'postalCode',
                      label: 'PIN',
                    ),
                    FilePickerDemo(
                      callBack: (List<FileStoreModel>? filestore) {
                        print('ghvjhb');
                      },
                      extensions: ['jpg', 'pdf', 'png'],
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
                                onPressed();
                                if (form.valid) {
                                  print(form.value);
                                } else {
                                  form.markAllAsTouched();
                                }
                              },
                              child: const Center(
                                child: Text('Action'),
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
        'administrationArea': FormControl<String>(value: ''),
        'housholdNo': FormControl<String>(value: ''),
        'locality': FormControl<String>(value: ''),
        'state': FormControl<String>(value: ''),
        'postalCode': FormControl<String>(value: ''),
      });
}
