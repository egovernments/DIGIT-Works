import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/router/app_router.dart';
import 'package:flutter_training/utils/date_formats.dart';
import 'package:flutter_training/utils/global_variables.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
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

/*
  * @author Ramkrishna
  * ramkrishna.sahoo@egovernments.org
  *
  * */
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
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: const DrawerWrapper(Drawer(child: SideBar())),
        body: ScrollableContent(
          header: Column(
            children: [
              const Back(),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text(
                  t.translate(i18.bnd.searchBirthCertificate),
                  style: const TextStyle(
                      fontSize: 32, fontWeight: FontWeight.w700),
                ),
              ),
            ],
          ),
          footer: const PoweredByDigit(),
          children: [
            DigitCard(
                child: ReactiveFormBuilder(
                    form: buildForm,
                    builder: (context, form, child) {
                      return Column(
                        children: [
                          DigitDateFormPicker(
                            label: t.translate(i18.bnd.fromDateOfBirth),
                            isRequired: true,
                            icon: Icons.info_outline_rounded,
                            formControlName: fromDateKey,
                            autoValidation: AutovalidateMode.always,
                            requiredMessage: 'From Date is required',
                            cancelText: t.translate(i18.common.cancel),
                            confirmText: t.translate(i18.common.oK),
                          ),
                          DigitDateFormPicker(
                            label: t.translate(i18.bnd.toDateOfBirth),
                            isRequired: true,
                            icon: Icons.info_outline_rounded,
                            formControlName: toDateKey,
                            autoValidation: AutovalidateMode.always,
                            requiredMessage: 'To Date is required',
                            cancelText: t.translate(i18.common.cancel),
                            confirmText: t.translate(i18.common.oK),
                          ),
                          DigitTextFormField(
                            label: t.translate(i18.bnd.regNo),
                            formControlName: regNoKey,
                          ),
                          DigitTextFormField(
                            label: t.translate(i18.bnd.fatherName),
                            formControlName: fatherNameKey,
                          ),
                          DigitTextFormField(
                            label: t.translate(i18.bnd.motherName),
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
                                padding: const EdgeInsets.all(4.0),
                                child: DigitElevatedButton(
                                    onPressed: () {
                                      form.markAllAsTouched();
                                      if (!form.valid) return;
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
                                      context.read<BirthSearchCertBloc>().add(
                                          SearchBirthCertEvent(
                                              queryParams: queryParams));
                                    },
                                    child:
                                        Text(t.translate(i18.common.submit))),
                              )),
                        ],
                      );
                    }))
          ],
        ));
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        regNoKey: FormControl<String>(value: ''),
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
