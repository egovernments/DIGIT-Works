import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';

import '../widgets/atoms/back_navigation_help_header.dart';

class HouseholdLocationPage extends StatelessWidget {
  const HouseholdLocationPage({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      body: ReactiveFormBuilder(
        form: buildForm,
        builder: (context, form, child) {
          return ScrollableContent(
            header: Column(children: const [
              BackNavigationHelpHeaderWidget(),
            ]),
            footer: Offstage(
              offstage: false,
              child: SizedBox(
                height: 90,
                child: DigitCard(
                  child: DigitElevatedButton(
                    onPressed: () {
                      if (form.valid) {
                        print(form.value);
                      } else {
                        form.markAllAsTouched();
                      }
                    },
                    child: Center(
                      child: Text('Action'),
                    ),
                  ),
                ),
              ),
            ),
            children: [
              DigitCard(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Text(
                      'household Location',
                      style: theme.textTheme.displayMedium,
                    ),
                    Column(children: const [
                      DigitTextFormField(
                        formControlName: 'administrationArea',
                        label: 'Administration Area',
                      ),
                      DigitTextFormField(
                        formControlName: 'housholdNo',
                        label: 'Household LocATION',
                      ),
                      DigitTextFormField(
                        formControlName: 'locality',
                        label: 'lOCALITY',
                      ),
                      DigitTextFormField(
                        formControlName: 'state',
                        label: 'State',
                      ),
                      DigitTextFormField(
                        formControlName: 'postalCode',
                        label: 'PIN',
                      ),
                    ]),
                    const SizedBox(height: 16),
                  ],
                ),
              ),
            ],
          );
        },
      ),
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
