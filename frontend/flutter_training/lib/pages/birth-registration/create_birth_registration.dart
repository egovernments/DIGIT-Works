import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/bnd/create_birth_bloc.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/models/create-birth-registration/child_address_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_father_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_mother_details.dart';
import 'package:flutter_training/widgets/atoms/radio_button_list.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
as i18;
import '../../router/app_router.dart';
import '../../utils/date_formats.dart';
import '../../utils/notifiers.dart';

class CreateBirthRegistrationPage extends StatefulWidget {
  const CreateBirthRegistrationPage({super.key});

  @override
  CreateBirthRegistrationPageState createState() =>
      CreateBirthRegistrationPageState();
}

class CreateBirthRegistrationPageState
    extends State<CreateBirthRegistrationPage> {
  String regNoKey = 'registrationNo';
  String firstNameKey = 'firstName';
  String genderKey = 'gender';
  String dobKey = 'dateOfBirth';
  String lastNameKey = 'lastName';
  String placeOfBirthKey = 'placeOfBirth';
  String informantNameKey = 'infoemantName';
  String informantAddressKey = 'infoemantAddress';
  String hospitalKey = 'hospitalName';
  String dorKey = 'dor';
  String fatherFirstNameKey = 'fatherFirstName';
  String fatherLastNameKey = 'fatherLastName';
  String fatherMobileNoKey = 'fatherMobileNo';
  String fatherAadhaarKey = 'fatherAadhaar';
  String fatherReligionKey = 'fatherReligion';
  String fatherNationalityKey = 'fatherNationality';
  String motherFirstNameKey = 'motherFirstName';
  String motherLastNameKey = 'motherLastName';
  String motherMobileNumberKey = 'motherMobileNumber';
  String motherAadhaarKey = 'motherAadhaar';
  String motherReligionKey = 'motherReligion';
  String motherNationalityKey = 'motherNationality';
  String buildingNoKey = 'buildingNo';
  String houseNoKey = 'houseNo';
  String streetNameKey = 'streetName';
  String localityKey = 'locality';
  String tehsilKey = 'tehsil';
  String districtKey = 'district';
  String cityKey = 'city';
  String stateKey = 'state';
  String pinCodeKey = 'pinCode';
  String countryKey = 'country';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter Training'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: ScrollableContent(
          header: const Text(
            'Child Details',
            style: TextStyle(fontSize: 32, fontWeight: FontWeight.w700),
          ),
          children: [
            ReactiveFormBuilder(
                form: buildForm,
                builder: (context, form, child) {
                  return Center(
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: <Widget>[
                        DigitCard(
                            child: Column(
                          children: [
                            DigitTextFormField(
                              label: t.translate(i18.common.registrationNo),
                              formControlName: regNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.registrationNoRequired) ,
                                'minLength': (_) =>
                                    t.translate(i18.common.registrationNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.registrationNoMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.firstName),
                              formControlName: firstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(i18.common.firstNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.firstNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.firstNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.lastName),
                              formControlName: lastNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(i18.common.lastNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.lastNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.lastNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.placeOfBirth),
                              formControlName: placeOfBirthKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(i18.common.placeOfBirthRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.placeOfBirthMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.placeOfBirthMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.informantName),
                              formControlName: informantNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(i18.common.informantNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.informantNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.informantAddressMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.informantAddress),
                              formControlName: informantAddressKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.informantAddressRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.informantAddressMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.informantAddressMaxChars),
                              },
                            ),
                            DigitReactiveDropdown<String>(
                              label: 'Hospital',
                              menuItems: const [
                                "Neelkanth Hospital",
                                "Ajit Hospital",
                                "Dashmesh Hospital",
                                "Biala Orthopaedics And Multispeciality Hospital",
                                "Dr. Parminder Singh Pannu Memorial Janta Hospital",
                                "Med Card Multispeciality Hospital",
                                "Taj Hospital Mattewal"
                              ],
                              formControlName: hospitalKey,
                              valueMapper: (value) => value,
                              onChanged: (value) {},
                            ),
                            DigitRadioButtonList(
                                formControlName: genderKey,
                                valueMapper: (value) => value,
                                options: const [
                                  'Male',
                                  'Female',
                                  'Transgender'
                                ]),
                            DigitDateFormPicker(
                              label: t.translate(i18.common.dateOfReport),
                              isRequired: true,
                              icon: Icons.info_outline_rounded,
                              formControlName: dobKey,
                              autoValidation: AutovalidateMode.always,
                              requiredMessage: t.translate(i18.common.dateOfReportRequired),
                              cancelText: t.translate(i18.common.cancel),
                              confirmText: t.translate(i18.common.confirm),
                              toolTipMsg:
                                  t.translate(i18.common.dateOfReportToolTip),
                            ),
                            DigitDateFormPicker(
                              label: t.translate(i18.common.dateOfBirth),
                              isRequired: true,
                              icon: Icons.info_outline_rounded,
                              formControlName: dorKey,
                              autoValidation: AutovalidateMode.always,
                              requiredMessage: t.translate(i18.common.dateOfBirthRequired),
                              toolTipMsg: t.translate(i18.common.dateOfBirthToolTip),
                              cancelText: t.translate(i18.common.cancel),
                              confirmText: t.translate(i18.common.confirm),
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            const Text(
                              "Child's Father Details",
                              style: TextStyle(
                                  fontSize: 32, fontWeight: FontWeight.w700),
                            ),
                            DigitTextFormField(
                              label: 'Father First Name',
                              formControlName: fatherFirstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Father First Name is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Father Last Name',
                              formControlName: fatherLastNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Father last name is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Father Mobile No',
                              formControlName: fatherMobileNoKey,
                              isRequired: true,
                              minLength: 10,
                              maxLength: 10,
                              keyboardType: TextInputType.phone,
                              validationMessages: {
                                'required': (_) => 'Mobile number is required',
                                'minLength': (_) =>
                                    'Mobile number should be of 10 digits',
                                'maxLength': (_) =>
                                    'Mobile number should be of 10 digits',
                                'min': (_) =>
                                    'Mobile number should start from 6-9',
                                'max': (_) =>
                                    'Mobile number should start from 6-9',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Father Aadhaar No',
                              formControlName: fatherAadhaarKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Father Aadhaar No is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Father Religion',
                              formControlName: fatherReligionKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Father Religion is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'FatherNationality',
                              formControlName: fatherNationalityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Father Nationality is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            const Text(
                              "Child's Mother Details",
                              style: TextStyle(
                                  fontSize: 32, fontWeight: FontWeight.w700),
                            ),
                            DigitTextFormField(
                              label: 'Mother First Name',
                              formControlName: motherFirstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Mother First Name is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Mother Last Name',
                              formControlName: motherLastNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Mother last name is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Mother Mobile No',
                              formControlName: motherMobileNumberKey,
                              isRequired: true,
                              minLength: 10,
                              maxLength: 10,
                              keyboardType: TextInputType.phone,
                              validationMessages: {
                                'required': (_) => 'Mobile number is required',
                                'minLength': (_) =>
                                    'Mobile number should be of 10 digits',
                                'maxLength': (_) =>
                                    'Mobile number should be of 10 digits',
                                'min': (_) =>
                                    'Mobile number should start from 6-9',
                                'max': (_) =>
                                    'Mobile number should start from 6-9',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Mother Aadhaar No',
                              formControlName: motherAadhaarKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Mother Aadhaar No is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Mother Religion',
                              formControlName: motherReligionKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Mother Religion is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Mother Nationality',
                              formControlName: motherNationalityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    'Mother Nationality is required',
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            const Text(
                              "Address Details",
                              style: TextStyle(
                                  fontSize: 32, fontWeight: FontWeight.w700),
                            ),
                            DigitTextFormField(
                              label: 'Building No',
                              formControlName: buildingNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'Building No is required',
                                'minLength': (_) =>
                                    'Building no should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Building no should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'House No',
                              formControlName: houseNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'House No is required',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Street Name',
                              formControlName: streetNameKey,
                              validationMessages: {
                                'minLength': (_) =>
                                    'Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Locality',
                              formControlName: localityKey,
                              validationMessages: {
                                'minLength': (_) =>
                                    'Locality should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Locality should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Tehsil',
                              formControlName: tehsilKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'Tehsil is required',
                                'minLength': (_) =>
                                    'Tehsil Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Tehsil Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'District',
                              formControlName: districtKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'District is required',
                                'minLength': (_) =>
                                    'District Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'District Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'City',
                              formControlName: cityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'City is required',
                                'minLength': (_) =>
                                    'City Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'City Name should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'State',
                              formControlName: stateKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'State is required',
                                'minLength': (_) =>
                                    'State should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'State should be maximum of 128 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Pin Code',
                              formControlName: pinCodeKey,
                              isRequired: true,
                              maxLength: 6,
                              minLength: 6,
                              validationMessages: {
                                'required': (_) => 'Pin Code is required',
                                'minLength': (_) =>
                                    'Pin Code should be minimum of 6 characters',
                              },
                            ),
                            DigitTextFormField(
                              label: 'Country',
                              formControlName: countryKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => 'Country is required',
                                'minLength': (_) =>
                                    'Country Name should be minimum of 2 characters',
                                'maxLength': (_) =>
                                    'Country Name should be maximum of 128 characters',
                              },
                            ),
                          ],
                        )),
                        const SizedBox(
                          height: 16.0,
                        ),
                        BlocListener<BirthRegBloc, BirthRegState>(
                            listener: (context, createState) {
                              createState.maybeWhen(
                                  orElse: () => false,
                                  loading: () =>
                                      Loaders.circularLoader(context),
                                  loaded: () {
                                    context.router.popAndPush(SuccessResponseRoute(
                                        header:
                                            'Birth Certificate of child created Successfully',
                                        backButton: true,
                                        callBack: () => context.router
                                            .push(const HomeRoute()),
                                        buttonLabel: 'Back To Home'));
                                  },
                                  error: (String? error) =>
                                      Notifiers.getToastMessage(
                                          context, error.toString(), 'ERROR'));
                            },
                            child: Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: DigitElevatedButton(
                                  onPressed: () {
                                    form.markAllAsTouched();
                                    if (!form.valid) return;
                                    DigitDialog.show(context,
                                        options: DigitDialogOptions(
                                            titleIcon: const Icon(
                                              Icons.warning,
                                              color: Colors.red,
                                            ),
                                            titleText: 'Warning',
                                            contentText:
                                                'Please confirm your details before submitting',
                                            primaryAction: DigitDialogActions(
                                              label: 'Confirm',
                                              action: (BuildContext context) {
                                                if (form.valid) {
                                                  DateTime dor =
                                                      form.value[dorKey]
                                                          as DateTime;
                                                  DateTime dob =
                                                      form.value[dobKey]
                                                          as DateTime;
                                                  context
                                                      .read<BirthRegBloc>()
                                                      .add(
                                                        RegBirthEvent(
                                                            childDetails: ChildDetails(
                                                                regNo: form.value[regNoKey]
                                                                    .toString(),
                                                                firstName: form.value[firstNameKey]
                                                                    .toString(),
                                                                lastName: form.value[lastNameKey]
                                                                    .toString(),
                                                                hospitalName: form.value[hospitalKey]
                                                                    .toString(),
                                                                placeOfBirth: form.value[placeOfBirthKey]
                                                                    .toString(),
                                                                genderStr: form.value[genderKey]
                                                                    .toString(),
                                                                informantName: form.value[informantNameKey]
                                                                    .toString(),
                                                                informantAddress: form.value[informantAddressKey]
                                                                    .toString(),
                                                                dateOfBirth: (DateFormats.dateToTimeStamp(dob.toString() ?? '')) ~/
                                                                    1000,
                                                                dateOfReport: (DateFormats.dateToTimeStamp(dor.toString() ?? '')) ~/
                                                                    1000),
                                                            childAddressDetails: ChildAddressDetails(
                                                                state: form.value[stateKey].toString(),
                                                                locality: form.value[localityKey].toString(),
                                                                city: form.value[cityKey].toString(),
                                                                district: form.value[districtKey].toString(),
                                                                country: form.value[countryKey].toString(),
                                                                buildingNo: form.value[buildingNoKey].toString(),
                                                                houseNo: form.value[houseNoKey].toString(),
                                                                pinNo: form.value[pinCodeKey].toString(),
                                                                streetname: form.value[streetNameKey].toString(),
                                                                tehsil: form.value[tehsilKey].toString()),
                                                            childFatherDetails: ChildFatherDetails(firstName: form.value[fatherFirstNameKey].toString(), lastName: form.value[fatherLastNameKey].toString(), nationality: form.value[fatherNationalityKey].toString(), aadhaarNo: form.value[fatherAadhaarKey].toString(), mobileNo: form.value[fatherMobileNoKey].toString(), religion: form.value[fatherReligionKey].toString()),
                                                            childMotherDetails: ChildMotherDetails(firstName: form.value[motherFirstNameKey].toString(), lastName: form.value[motherLastNameKey].toString(), nationality: form.value[motherNationalityKey].toString(), aadhaarNo: form.value[motherAadhaarKey].toString(), mobileNo: form.value[motherMobileNumberKey].toString(), religion: form.value[motherReligionKey].toString())),
                                                      );
                                                } else {
                                                  ScaffoldMessenger.of(context)
                                                      .showSnackBar(const SnackBar(
                                                          content: Text(
                                                              'Oops ! Please fill the mandatory details')));
                                                }
                                                Navigator.of(context,
                                                        rootNavigator: true)
                                                    .pop();
                                              },
                                            ),
                                            secondaryAction: DigitDialogActions(
                                              label: 'Back',
                                              action: (BuildContext context) =>
                                                  Navigator.of(context,
                                                          rootNavigator: true)
                                                      .pop(),
                                            )));
                                  },
                                  child: const Text('Submit')),
                            )),
                      ],
                    ),
                  );
                })
          ],
        ),
      ), // This trailing comma makes auto-formattig nicer for build methods.
    );
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        regNoKey: FormControl<String>(
            value: '',
            validators: [Validators.required, Validators.maxLength(32)]),
        firstNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        lastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        hospitalKey: FormControl<String>(value: null),
        dobKey: FormControl<DateTime>(
            value: null, validators: [Validators.required]),
        dorKey: FormControl<DateTime>(
            value: null, validators: [Validators.required]),
        genderKey: FormControl<String>(value: null),
        placeOfBirthKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        informantNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        informantAddressKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        fatherFirstNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        fatherLastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        fatherMobileNoKey: FormControl<String>(value: '', validators: [
          Validators.minLength(10),
          Validators.maxLength(10),
          Validators.min('5999999999'),
          Validators.max('9999999999'),
        ]),
        fatherAadhaarKey: FormControl<String>(value: '', validators: [
          Validators.minLength(12),
          Validators.maxLength(12),
        ]),
        fatherNationalityKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        fatherReligionKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        motherFirstNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        motherLastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        motherMobileNumberKey: FormControl<String>(value: '', validators: [
          Validators.minLength(10),
          Validators.maxLength(10),
          Validators.min('5999999999'),
          Validators.max('9999999999'),
        ]),
        motherAadhaarKey: FormControl<String>(value: '', validators: [
          Validators.minLength(12),
          Validators.maxLength(12),
        ]),
        motherNationalityKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(128)
        ]),
        motherReligionKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        buildingNoKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        houseNoKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        streetNameKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        localityKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        tehsilKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        districtKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        cityKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        stateKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        pinCodeKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
        countryKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(4), Validators.maxLength(128)]),
      });
}
