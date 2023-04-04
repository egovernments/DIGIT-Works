import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/models/wage_seeker/financial_details_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/models.dart';
import 'package:works_shg_app/utils/models/file_picker_data.dart';
import 'package:works_shg_app/widgets/atoms/radio_button_list.dart';

import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../widgets/molecules/file_picker.dart';

class IndividualDetailsPage extends StatefulWidget {
  final void Function() onPressed;
  final WageSeekerMDMS? wageSeekerMDMS;
  const IndividualDetailsPage(
      {required this.onPressed, this.wageSeekerMDMS, super.key});

  @override
  State<StatefulWidget> createState() {
    return IndividualDetailsPageState();
  }
}

class IndividualDetailsPageState extends State<IndividualDetailsPage> {
  String genderController = '';
  IndividualDetails? individualDetails = IndividualDetails();
  LocationDetails? locationDetails = LocationDetails();
  SkillDetails? skillDetails = SkillDetails();
  FinancialDetails? financialDetails = FinancialDetails();
  String nameKey = 'name';
  String fatherNameKey = 'fatherName';
  String aadhaarNoKey = 'aadhaarNo';
  String relationshipKey = 'relationship';
  String dobKey = 'dob';
  String socialCategoryKey = 'socialCategory';
  String mobileKey = 'mobileNo';

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    individualDetails = registrationState.individualDetails;
    skillDetails = registrationState.skillDetails;
    locationDetails = registrationState.locationDetails;
    financialDetails = registrationState.financialDetails;
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context);
    String? photo;
    List<String> relationship = widget.wageSeekerMDMS!.commonMDMS!.relationship!
        .map((e) => (e.code))
        .toList();
    List<String> socialCategory = widget
        .wageSeekerMDMS!.commonMDMS!.socialCategory!
        .map((e) => (e.code))
        .toList();
    List<KeyValue> gender = widget.wageSeekerMDMS!.commonMDMS!.genderType!
        .map((e) => KeyValue(t.translate(e.code), e.code))
        .toList();

    return ReactiveFormBuilder(
      form: buildForm,
      builder: (context, form, child) {
        if (individualDetails != null) {
          form.control(nameKey).value = individualDetails?.name;
          form.control(fatherNameKey).value = individualDetails?.fatherName;
          form.control(aadhaarNoKey).value = individualDetails?.aadhaarNo;
          form.control(relationshipKey).value = individualDetails?.relationship;
          form.control(socialCategoryKey).value =
              individualDetails?.socialCategory;
          genderController = individualDetails!.gender.toString();
          form.control(dobKey).value = individualDetails?.dateOfBirth;
          form.control(mobileKey).value = individualDetails?.mobileNumber;
        }
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
                      formControlName: aadhaarNoKey,
                      label: t.translate(i18.common.aadhaarNumber),
                      isRequired: true,
                      minLength: 12,
                      keyboardType: TextInputType.number,
                      inputFormatter: [
                        FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                      ],
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.aadhaarRequired,
                            ),
                        'minLength': (_) => t.translate(
                              i18.wageSeeker.minAadhaarCharacters,
                            ),
                        'maxLength': (_) => t.translate(
                              i18.wageSeeker.maxAadhaarCharacters,
                            ),
                      },
                    ),
                    DigitTextFormField(
                      formControlName: nameKey,
                      isRequired: true,
                      label: t.translate(i18.common.nameLabel),
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.nameRequired,
                            ),
                      },
                    ),
                    DigitTextFormField(
                      formControlName: fatherNameKey,
                      isRequired: true,
                      label: t.translate(i18.common.guardianName),
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.fatherNameRequired,
                            ),
                      },
                    ),
                    DigitDropdown<String>(
                      label: t.translate(i18.common.relationship),
                      menuItems: relationship
                          .map((e) => t.translate(e).toString())
                          .toList(),
                      isRequired: true,
                      formControlName: relationshipKey,
                      valueMapper: (value) => value,
                      onChanged: (value) {},
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.relationshipRequired,
                            ),
                      },
                    ),
                    DigitDateFormPicker(
                      label: t.translate(i18.common.dateOfBirth) + '*',
                      isRequired: true,
                      formControlName: dobKey,
                      autoValidation: AutovalidateMode.always,
                      requiredMessage: t.translate(i18.wageSeeker.dobRequired),
                      validator: (val) {
                        if (val == null) {
                          return t.translate(i18.wageSeeker.dobRequired);
                        }
                      },
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.dobRequired,
                            ),
                      },
                    ),
                    StatefulBuilder(
                        builder: (BuildContext context, StateSetter setState) {
                      return DigitRadioButtonList(
                          context,
                          t.translate(i18.common.gender),
                          genderController,
                          '',
                          '',
                          true,
                          gender, (value) {
                        setState(() {
                          genderController = value;
                        });
                      });
                    }),
                    DigitDropdown<String>(
                      label: t.translate(i18.common.socialCategory),
                      menuItems: socialCategory
                          .map((e) => t.translate(e).toString())
                          .toList(),
                      isRequired: true,
                      formControlName: socialCategoryKey,
                      valueMapper: (value) => value,
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.socialCatRequired,
                            ),
                      },
                      onChanged: (value) {},
                    ),
                    DigitTextFormField(
                      label: t.translate(i18.common.mobileNumber),
                      formControlName: mobileKey,
                      prefixText: '+91 - ',
                      isRequired: true,
                      minLength: 10,
                      keyboardType: TextInputType.phone,
                      inputFormatter: [
                        FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                      ],
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.mobileRequired,
                            ),
                        'minLength': (_) => t.translate(
                              i18.wageSeeker.minMobileCharacters,
                            ),
                        'maxLength': (_) => t.translate(
                              i18.wageSeeker.maxMobileCharacters,
                            ),
                      },
                    ),
                    SHGFilePicker(
                      callBack: (List<FileStoreModel>? fileStore) {
                        if (fileStore != null && fileStore.isNotEmpty) {
                          // setState(() {
                          photo = fileStore.first.fileStoreId;
                          // });
                        } else {
                          // setState(() {
                          photo = '';
                          // });
                        }
                      },
                      extensions: const ['jpg', 'png'],
                      moduleName: 'works',
                      label: t.translate(i18.common.photoGraph),
                    )
                  ]),
                  const SizedBox(height: 16),
                  DigitCard(
                      child: Center(
                    child: DigitElevatedButton(
                        onPressed: () {
                          form.markAllAsTouched(updateParent: false);
                          if (!form.valid) return;
                          final individualDetails = IndividualDetails(
                              name: form.value[nameKey].toString() ?? '',
                              fatherName:
                                  form.value[fatherNameKey].toString() ?? '',
                              aadhaarNo:
                                  form.value[aadhaarNoKey].toString() ?? '',
                              relationship:
                                  form.value[relationshipKey].toString() ?? '',
                              socialCategory:
                                  form.value[socialCategoryKey].toString() ??
                                      '',
                              dateOfBirth: form.value[dobKey] as DateTime,
                              mobileNumber:
                                  form.value[mobileKey].toString() ?? '',
                              gender: genderController ?? '',
                              imageFile: FilePickerData.imageFile,
                              bytes: FilePickerData.bytes,
                              photo: photo);

                          BlocProvider.of<WageSeekerBloc>(context).add(
                            WageSeekerCreateEvent(
                                individualDetails: individualDetails,
                                skillDetails: skillDetails,
                                locationDetails: locationDetails,
                                financialDetails: financialDetails),
                          );
                          widget.onPressed();
                        },
                        child: Center(
                          child: Text(t.translate(i18.common.next)),
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
        aadhaarNoKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(12),
          Validators.maxLength(12)
        ]),
        nameKey:
            FormControl<String>(value: '', validators: [Validators.required]),
        fatherNameKey:
            FormControl<String>(value: '', validators: [Validators.required]),
        relationshipKey:
            FormControl<String>(value: null, validators: [Validators.required]),
        dobKey: FormControl<DateTime>(
            value: null, validators: [Validators.required]),
        socialCategoryKey:
            FormControl<String>(value: null, validators: [Validators.required]),
        mobileKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(10)
        ])
      });
}
