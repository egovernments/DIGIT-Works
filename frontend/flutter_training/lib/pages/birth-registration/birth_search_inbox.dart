import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/router/app_router.dart';
import 'package:flutter_training/utils/date_formats.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:reactive_forms/reactive_forms.dart';

import '../../blocs/bnd/search_birth_certificate_bloc.dart';
import '../../models/create-birth-registration/birth_certificates_model.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/drawer_wrapper.dart';

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
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: const DrawerWrapper(Drawer(child: SideBar())),
        body: ScrollableContent(
          header: const Column(
            children: [
              Back(),
              Padding(
                padding: EdgeInsets.all(8.0),
                child: Text(
                  'Search Birth Certificate',
                  style: TextStyle(fontSize: 32, fontWeight: FontWeight.w700),
                ),
              ),
            ],
          ),
          children: [
            DigitCard(
                child: ReactiveFormBuilder(
                    form: buildForm,
                    builder: (context, form, child) {
                      return Column(
                        children: [
                          DigitDateFormPicker(
                            label: 'From (Date of birth)',
                            isRequired: true,
                            icon: Icons.info_outline_rounded,
                            formControlName: fromDateKey,
                            autoValidation: AutovalidateMode.always,
                            requiredMessage: 'From Date is required',
                            cancelText: 'Cancel',
                            confirmText: 'OK',
                          ),
                          DigitDateFormPicker(
                            label: 'To (Date of birth)',
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
                          ),
                          DigitTextFormField(
                            label: 'Father Name',
                            formControlName: fatherNameKey,
                          ),
                          DigitTextFormField(
                            label: 'Mother Name',
                            formControlName: motherNameKey,
                          ),
                          const SizedBox(
                            height: 16.0,
                          ),
                          BlocListener<BirthSearchCertBloc,
                                  BirthSearchCertState>(
                              listener: (context, searchState) {
                                searchState.maybeWhen(
                                    orElse: () => false,
                                    loading: () =>
                                        Loaders.circularLoader(context),
                                    loaded: (BirthCertificatesList?
                                        birthCertificatesList) {
                                      context.router.push(
                                          const ViewBirthCertificatesRoute());
                                    },
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
                                      int fromDate = DateTime.parse(form
                                              .value[fromDateKey]
                                              .toString())
                                          .millisecondsSinceEpoch;
                                      int toDate = DateTime.parse(
                                              form.value[toDateKey].toString())
                                          .millisecondsSinceEpoch;
                                      print(form.value[fromDateKey]);
                                      print(fromDate);
                                      final Map<String, String> queryParams = {
                                        fromDateKey:
                                            DateFormats.getFilteredDate(
                                                form.value[fromDateKey]
                                                    .toString(),
                                                dateFormat: 'dd-MM-yyyy'),
                                        toDateKey: DateFormats.getFilteredDate(
                                            form.value[toDateKey].toString(),
                                            dateFormat: 'dd-MM-yyyy'),
                                      };
                                      queryParams['tenantId'] = GlobalVariables
                                          .userInfo!.tenantId
                                          .toString();
                                      print(queryParams);
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
        childNameKey: FormControl<String>(value: ''),
        fatherNameKey: FormControl<String>(
          value: '',
        ),
        motherNameKey: FormControl<String>(
          value: '',
        ),
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
