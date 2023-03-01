import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../blocs/localization/app_localization.dart';
import '../utils/constants.dart';
import '../widgets/SideBar.dart';
import '../widgets/atoms/back_navigation_help_header.dart';
import '../widgets/atoms/radio_button_list.dart';
import '../widgets/drawer_wrapper.dart';

class RegisterIndividualPage extends StatelessWidget {
  const RegisterIndividualPage({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    var genderController = TextEditingController();
    return Scaffold(
      appBar: AppBar(),
      drawer: DrawerWrapper(const Drawer(
          child: SideBar(
        module: 'rainmaker-common,rainmaker-attendencemgmt',
      ))),
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
                    child: const Center(
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
                      'Individual Details',
                      style: theme.textTheme.displayMedium,
                    ),
                    Column(children: [
                      const DigitTextFormField(
                        formControlName: 'administrationArea',
                        label: 'Administration Area',
                      ),
                      DigitRadioButtonList(
                          context,
                          AppLocalizations.of(context)
                              .translate(i18.common.gender),
                          genderController,
                          '',
                          '',
                          false,
                          Constants.gender,
                          (val) {}),
                      const DigitTextFormField(
                        formControlName: 'HouseholdNo',
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
