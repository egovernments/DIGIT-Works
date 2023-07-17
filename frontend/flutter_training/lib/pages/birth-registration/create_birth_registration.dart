import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/bnd/create_birth_bloc.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/models/create-birth-registration/child_address_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_father_details.dart';
import 'package:flutter_training/models/create-birth-registration/child_mother_details.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:flutter_training/widgets/atoms/app_bar_logo.dart';
import 'package:flutter_training/widgets/atoms/radio_button_list.dart';
import 'package:reactive_forms/reactive_forms.dart';

import '../../router/app_router.dart';
import '../../utils/constants.dart';
import '../../utils/notifiers.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/drawer_wrapper.dart';

class CreateBirthRegistrationPage extends StatefulWidget {
  const CreateBirthRegistrationPage({super.key});

  @override
  CreateBirthRegistrationPageState createState() =>
      CreateBirthRegistrationPageState();
}

/*
  * @author Ramkrishna
  * ramkrishna.sahoo@egovernments.org
  *
  * */
class CreateBirthRegistrationPageState
    extends State<CreateBirthRegistrationPage> {
  //Add all formKeys here
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
        titleSpacing: 0,
        title: const AppBarLogo(),
      ),
      drawer: const DrawerWrapper(Drawer(child: SideBar())),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: ScrollableContent(
          header: const Back(),
          children: [
            //Wrap all reactive form components under Reactive Form Builder to validate the form
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
                            Align(
                              alignment: Alignment.centerLeft,
                              child: Text(
                                t.translate(i18.bnd.childInfo),
                                style: const TextStyle(
                                    fontSize: 32, fontWeight: FontWeight.w700),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.registrationNo),
                              formControlName: regNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(
                                    i18.common.registrationNoRequired),
                                'minLength': (_) => t.translate(
                                    i18.common.registrationNoMinChars),
                                'maxLength': (_) => t.translate(
                                    i18.common.registrationNoMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.firstName),
                              formControlName: firstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.firstNameRequired),
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
                                'required': (_) =>
                                    t.translate(i18.common.lastNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.lastNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.lastNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.bnd.placeOfBirth),
                              formControlName: placeOfBirthKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.bnd.placeOfBirthRequired),
                                'minLength': (_) =>
                                    t.translate(i18.bnd.placeOfBirthMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.bnd.placeOfBirthMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.informantName),
                              formControlName: informantNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(
                                    i18.common.informantNameRequired),
                                'minLength': (_) => t.translate(
                                    i18.common.informantNameMinChars),
                                'maxLength': (_) => t.translate(
                                    i18.common.informantAddressMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.informantAddress),
                              formControlName: informantAddressKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) => t.translate(
                                    i18.common.informantAddressRequired),
                                'minLength': (_) => t.translate(
                                    i18.common.informantAddressMinChars),
                                'maxLength': (_) => t.translate(
                                    i18.common.informantAddressMaxChars),
                              },
                            ),
                            DigitReactiveDropdown<String>(
                              label: t.translate(i18.bnd.hospitalName),
                              menuItems: Constants.bndHospitalNamesList,
                              formControlName: hospitalKey,
                              valueMapper: (value) => value,
                              onChanged: (value) {},
                            ),
                            DigitRadioButtonList(
                                labelText: t.translate(i18.bnd.gender),
                                formControlName: genderKey,
                                valueMapper: (value) => value,
                                options: Constants.bndGenderList),
                            DigitDateFormPicker(
                              label: t.translate(i18.common.dateOfReport),
                              isRequired: true,
                              icon: Icons.info_outline_rounded,
                              formControlName: dobKey,
                              autoValidation: AutovalidateMode.always,
                              requiredMessage:
                                  t.translate(i18.common.dateOfReportRequired),
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
                              requiredMessage:
                                  t.translate(i18.common.dateOfBirthRequired),
                              toolTipMsg:
                                  t.translate(i18.common.dateOfBirthToolTip),
                              cancelText: t.translate(i18.common.cancel),
                              confirmText: t.translate(i18.common.confirm),
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            Align(
                              alignment: Alignment.centerLeft,
                              child: Text(
                                t.translate(i18.bnd.fatherInfo),
                                style: const TextStyle(
                                    fontSize: 32, fontWeight: FontWeight.w700),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.fatherFirstName),
                              formControlName: fatherFirstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.firstName),
                                'minLength': (_) => t.translate(
                                    i18.common.fatherFirstNameMinChars),
                                'maxLength': (_) => t.translate(
                                    i18.common.fatherFirstNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.fatherLastName),
                              formControlName: fatherLastNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.lastName),
                                'minLength': (_) => t.translate(
                                    i18.common.fatherLastNameMinChars),
                                'maxLength': (_) => t.translate(
                                    i18.common.fatherLastNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.fatherMobileNO),
                              formControlName: fatherMobileNoKey,
                              isRequired: true,
                              minLength: 10,
                              maxLength: 10,
                              keyboardType: TextInputType.phone,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.mobileNumber),
                                'minLength': (_) =>
                                    t.translate(i18.common.mobileNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.mobileNoMinChars),
                                'min': (_) =>
                                    t.translate(i18.common.mobileNoRangeValue),
                                'max': (_) =>
                                    t.translate(i18.common.mobileNoRangeValue),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.bnd.aadhaarNo),
                              formControlName: fatherAadhaarKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.aadhaarNoRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.aadhaarNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.aadhaarNoMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.religion),
                              formControlName: fatherReligionKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.religionRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.religionMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.religionMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.nationality),
                              formControlName: fatherNationalityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.nationalityRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.nationalityMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.nationalityMaxChars),
                              },
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            Align(
                              alignment: Alignment.centerLeft,
                              child: Text(
                                t.translate(i18.bnd.motherInfo),
                                style: const TextStyle(
                                  fontSize: 32,
                                  fontWeight: FontWeight.w700,
                                ),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.firstName),
                              formControlName: motherFirstNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.firstNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.firstNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.firstNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.lastName),
                              formControlName: motherLastNameKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.lastNameRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.lastNameMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.lastNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.mobileNumber),
                              formControlName: motherMobileNumberKey,
                              isRequired: true,
                              minLength: 10,
                              maxLength: 10,
                              keyboardType: TextInputType.phone,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.mobileNoRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.mobileNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.mobileNoMinChars),
                                'min': (_) =>
                                    t.translate(i18.common.mobileNoRangeValue),
                                'max': (_) =>
                                    t.translate(i18.common.mobileNoRangeValue),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.bnd.aadhaarNo),
                              formControlName: motherAadhaarKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.aadhaarNoRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.aadhaarNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.aadhaarNoMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.religion),
                              formControlName: motherReligionKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.religionRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.religionMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.religionMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.nationality),
                              formControlName: motherNationalityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.nationalityRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.nationalityMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.nationalityMaxChars),
                              },
                            ),
                          ],
                        )),
                        DigitCard(
                            child: Column(
                          children: [
                            Align(
                              alignment: Alignment.centerLeft,
                              child: Text(
                                t.translate(i18.common.addressDetails),
                                style: const TextStyle(
                                    fontSize: 32, fontWeight: FontWeight.w700),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.buildingNo),
                              formControlName: buildingNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.buildingNoRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.buildingNoMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.buildingNoMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.houseNo),
                              formControlName: houseNoKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.houseNoRequired),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.streetName),
                              formControlName: streetNameKey,
                              validationMessages: {
                                'maxLength': (_) =>
                                    t.translate(i18.common.streetNameMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.locality),
                              formControlName: localityKey,
                              validationMessages: {
                                'maxLength': (_) =>
                                    t.translate(i18.common.localityMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.tehsil),
                              formControlName: tehsilKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.tehsilRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.tehsilMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.tehsilMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.district),
                              formControlName: districtKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.districtRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.districtMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.districtMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.city),
                              formControlName: cityKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.cityRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.cityMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.cityMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.state),
                              formControlName: stateKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.stateRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.stateMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.stateMaxChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.pinCode),
                              formControlName: pinCodeKey,
                              isRequired: true,
                              maxLength: 6,
                              minLength: 6,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.pinCodeRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.pinCodeMinChars),
                              },
                            ),
                            DigitTextFormField(
                              label: t.translate(i18.common.country),
                              formControlName: countryKey,
                              isRequired: true,
                              validationMessages: {
                                'required': (_) =>
                                    t.translate(i18.common.countryRequired),
                                'minLength': (_) =>
                                    t.translate(i18.common.countryMinChars),
                                'maxLength': (_) =>
                                    t.translate(i18.common.countryMaxChars),
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
                                    context.router.popAndPush(
                                        SuccessResponseRoute(
                                            header: t.translate(i18
                                                .bnd.birthCertificateSuccess),
                                            backButton: true,
                                            callBack: () => context.router
                                                .push(const HomeRoute()),
                                            buttonLabel: t.translate(
                                                i18.common.backToHome)));
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
                                            titleText:
                                                t.translate(i18.common.warning),
                                            contentText: t.translate(i18
                                                .bnd.confirmCertificateDetails),
                                            primaryAction: DigitDialogActions(
                                              label: t.translate(
                                                  i18.common.confirm),
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
                                                                dateOfBirth: dob.millisecondsSinceEpoch ~/
                                                                    1000,
                                                                dateOfReport: dor.millisecondsSinceEpoch ~/
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
                                              label:
                                                  t.translate(i18.common.back),
                                              action: (BuildContext context) =>
                                                  Navigator.of(context,
                                                          rootNavigator: true)
                                                      .pop(),
                                            )));
                                  },
                                  child: Text(t.translate(i18.common.submit))),
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
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        lastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(1),
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
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        informantAddressKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        fatherFirstNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        fatherLastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(1),
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
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        fatherReligionKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(2), Validators.maxLength(128)]),
        motherFirstNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        motherLastNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(1),
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
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        motherReligionKey: FormControl<String>(
            value: '',
            validators: [Validators.minLength(2), Validators.maxLength(128)]),
        buildingNoKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.required,
          Validators.maxLength(128)
        ]),
        houseNoKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.required,
          Validators.maxLength(128)
        ]),
        streetNameKey: FormControl<String>(
            value: '', validators: [Validators.maxLength(64)]),
        localityKey: FormControl<String>(
            value: '', validators: [Validators.maxLength(64)]),
        tehsilKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.maxLength(64),
          Validators.required,
        ]),
        districtKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.maxLength(64),
          Validators.required,
        ]),
        cityKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.maxLength(64),
          Validators.required,
        ]),
        stateKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.maxLength(64),
          Validators.required,
        ]),
        pinCodeKey: FormControl<String>(value: '', validators: [
          Validators.minLength(6),
          Validators.required,
          Validators.maxLength(6)
        ]),
        countryKey: FormControl<String>(value: '', validators: [
          Validators.minLength(2),
          Validators.required,
          Validators.maxLength(128)
        ]),
      });
}
