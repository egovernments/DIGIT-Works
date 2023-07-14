import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/bnd/search_birth_certificate_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';

import '../../blocs/bnd/create_birth_bloc.dart';
import '../../utils/notifiers.dart';

class BirthRegSearchInboxPage extends StatefulWidget {
  const BirthRegSearchInboxPage({super.key});

  @override
  BirthRegSearchInboxPageState createState() => BirthRegSearchInboxPageState();
}

class BirthRegSearchInboxPageState extends State<BirthRegSearchInboxPage> {
  String regNoKey = 'registrationNo';
  String childNameKey = 'childName';
  String fatherNameKey = 'fatherName';
  String motherNameKey = 'motherName';
  String hospitalNameKey = 'hospitalName';
  String fromDateKey = 'fromDate';
  String toDateKey = 'toDate';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Training'),
        ),
        body: ScrollableContent(
          header: const Text(
            'Search Birth Certificate',
            style: TextStyle(fontSize: 32, fontWeight: FontWeight.w700),
          ),
          children: [
            DigitCard(
                child: ReactiveFormBuilder(
                    form: buildForm,
                    builder: (context, form, child) {
                      return Column(
                        children: [
                          DigitDateFormPicker(
                            label: 'From Date',
                            isRequired: true,
                            icon: Icons.info_outline_rounded,
                            formControlName: fromDateKey,
                            autoValidation: AutovalidateMode.always,
                            requiredMessage: 'From Date is required',
                            cancelText: 'Cancel',
                            confirmText: 'OK',
                          ),
                          DigitDateFormPicker(
                            label: 'To Date',
                            isRequired: true,
                            icon: Icons.info_outline_rounded,
                            formControlName: toDateKey,
                            autoValidation: AutovalidateMode.always,
                            requiredMessage: 'To Date is required',
                            cancelText: 'Cancel',
                            confirmText: 'OK',
                          ),
                          DigitTextFormField(
                            label: "Child's Name",
                            formControlName: childNameKey,
                            isRequired: true,
                            validationMessages: {
                              'minLength': (_) =>
                                  "Child's Name should be minimum of 4 characters",
                              'maxLength': (_) =>
                                  "Child's Name should be maximum of 128 characters",
                            },
                          ),
                          DigitTextFormField(
                            label: 'Father Name',
                            formControlName: fatherNameKey,
                            isRequired: true,
                            validationMessages: {
                              'minLength': (_) =>
                                  "Father Name should be minimum of 4 characters",
                              'maxLength': (_) =>
                                  "Father Name should be maximum of 128 characters",
                            },
                          ),
                          DigitTextFormField(
                            label: 'Mother Name',
                            formControlName: motherNameKey,
                            validationMessages: {
                              'minLength': (_) =>
                                  "Mother Name should be minimum of 4 characters",
                              'maxLength': (_) =>
                                  "Mother Name should be maximum of 128 characters",
                            },
                          ),
                          const SizedBox(
                            height: 16.0,
                          ),
                          BlocListener<BirthRegBloc, BirthRegState>(
                              listener: (context, createState) {
                                createState.maybeWhen(
                                    orElse: () => false,
                                    loading: () =>
                                        Loaders.circularLoader(context),
                                    loaded: () {},
                                    error: (String? error) =>
                                        Notifiers.getToastMessage(context,
                                            error.toString(), 'ERROR'));
                              },
                              child: Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: DigitElevatedButton(
                                    onPressed: () {
                                      form.markAllAsTouched();
                                      if (!form.valid) return;
                                      final queryParams = <String, String>{
                                        fromDateKey: form
                                            .control(fromDateKey)
                                            .value
                                            .toString(),
                                        toDateKey: form
                                            .control(toDateKey)
                                            .value
                                            .toString(),
                                      };

                                      form.value.forEach((key, value) {
                                        if (value != null &&
                                            value.toString().isNotEmpty) {
                                          queryParams[key] = value.toString();
                                        }
                                      });
                                      context.read<BirthSearchCertBloc>().add(
                                          SearchBirthCertEvent(
                                              queryParams: queryParams));
                                    },
                                    child: const Text('Submit')),
                              )),
                        ],
                      );
                    }))
          ],
        ));
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        childNameKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        fatherNameKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        motherNameKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        hospitalNameKey: FormControl<String>(
          value: null,
        ),
        fromDateKey: FormControl<DateTime>(
          value: null,
          validators: [Validators.required, Validators.max(DateTime.now())],
        ),
        toDateKey: FormControl<DateTime>(
          value: null,
          validators: [Validators.required, Validators.max(DateTime.now())],
        ),
      });
}
