import 'package:digit_components/widgets/digit_card.dart';
import 'package:digit_components/widgets/digit_elevated_button.dart';
import 'package:flutter/material.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../../blocs/localization/app_localization.dart';
import '../../utils/constants.dart';
import '../../widgets/atoms/digit_text_form_field.dart';
import '../../widgets/atoms/radio_button_list.dart';

class FinancialDetails extends StatefulWidget {
  final void Function() onPressed;
  const FinancialDetails({required this.onPressed, super.key});

  @override
  FinancialDetailsState createState() {
    return FinancialDetailsState();
  }
}

class FinancialDetailsState extends State<FinancialDetails> {
  String accountType = '';

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
                    t.translate(i18.common.financialDetails),
                    style: Theme.of(context).textTheme.displayMedium,
                  ),
                  Column(children: [
                    DigitTextFormField(
                      formControlName: 'accountHolderName',
                      label: t.translate(i18.common.accountHolderName),
                    ),
                    DigitTextFormField(
                      formControlName: 'accountNo',
                      label: t.translate(i18.common.accountNo),
                    ),
                    DigitTextFormField(
                      formControlName: 'reAccountNo',
                      label: t.translate(i18.common.reEnterAccountNo),
                    ),
                    DigitRadioButtonList(
                        context,
                        t.translate(i18.common.accountType),
                        accountType,
                        '',
                        '',
                        true,
                        Constants.accountType, (value) {
                      setState(() {
                        accountType = value;
                      });
                    }),
                    DigitTextFormField(
                        formControlName: 'ifsc',
                        label: t.translate(i18.common.ifscCode),
                        hint: t.translate(i18.common.bankHint)),
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
                          context.router.push(const HomeRoute());
                        },
                        child: Center(
                          child: Text(t.translate(i18.common.submit)),
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
        'accountHolderName': FormControl<String>(value: ''),
        'accountNo': FormControl<String>(value: ''),
        'reAccountNo': FormControl<String>(value: ''),
        'ifsc': FormControl<String>(value: ''),
      });
}
