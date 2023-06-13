import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/models/wage_seeker/financial_details_model.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/models/file_picker_data.dart';
import 'package:works_shg_app/widgets/atoms/radio_button_list.dart';

import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/file_store/file_store_model.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../utils/notifiers.dart';
import '../../widgets/atoms/multiselect_checkbox.dart';
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
  String genderKey = 'gender';
  String socialCategoryKey = 'socialCategory';
  String mobileKey = 'mobileNo';
  List<String> selectedOptions = [];

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    individualDetails = registrationState.individualDetails;
    skillDetails = registrationState.skillDetails;
    locationDetails = registrationState.locationDetails;
    financialDetails = registrationState.financialDetails;

    if (registrationState.skillDetails != null &&
        registrationState.skillDetails?.individualSkills != null) {
      selectedOptions = registrationState.skillDetails!.individualSkills!
              .any((a) => a.type == null)
          ? []
          : registrationState.skillDetails!.individualSkills!
              .where((e) => e.type != null)
              .map((e) => '${e.level}.${e.type}')
              .toList();
    }
  }

  void _onSelectedOptionsChanged(List<String> options) {
    setState(() {
      selectedOptions = options;
    });
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
    List<String> gender = widget.wageSeekerMDMS!.commonMDMS!.genderType!
        .map((e) => (e.code))
        .toList();
    List<String> skills =
        widget.wageSeekerMDMS!.commonMDMS!.wageSeekerSkills != null
            ? widget.wageSeekerMDMS!.commonMDMS!.wageSeekerSkills!
                .map((e) => e.code)
                .toList()
            : [];

    return ReactiveFormBuilder(
      form: buildForm,
      builder: (context, form, child) {
        if (individualDetails != null) {
          form.control(nameKey).value =
              individualDetails?.name ?? form.control(nameKey).value;
          form.control(fatherNameKey).value = individualDetails?.fatherName ??
              form.control(fatherNameKey).value;
          form.control(aadhaarNoKey).value =
              individualDetails?.aadhaarNo ?? form.control(aadhaarNoKey).value;
          form.control(relationshipKey).value =
              individualDetails?.relationship ??
                  form.control(relationshipKey).value;
          form.control(socialCategoryKey).value =
              individualDetails?.socialCategory ??
                  form.control(socialCategoryKey).value;
          form.control(genderKey).value =
              individualDetails?.gender ?? form.control(genderKey).value;
          form.control(dobKey).value =
              individualDetails?.dateOfBirth ?? form.control(dobKey).value;
          form.control(mobileKey).value =
              individualDetails?.mobileNumber ?? form.control(mobileKey).value;
        }
        return GestureDetector(
          onTap: () {
            if (FocusScope.of(context).hasFocus) {
              FocusScope.of(context).unfocus();
            }
          },
          child: Column(
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
                      style: DigitTheme
                          .instance.mobileTheme.textTheme.displayMedium
                          ?.apply(color: const DigitColors().black),
                    ),
                    Column(children: [
                      DigitTextFormField(
                        formControlName: aadhaarNoKey,
                        label: t.translate(i18.common.aadhaarNumber),
                        isRequired: true,
                        minLength: 12,
                        maxLength: 12,
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
                        padding: const EdgeInsets.only(top: 0),
                        formControlName: nameKey,
                        isRequired: true,
                        label: t.translate(i18.common.nameLabel),
                        inputFormatter: [
                          FilteringTextInputFormatter.allow(RegExp("[A-Za-z ]"))
                        ],
                        validationMessages: {
                          'required': (_) => t.translate(
                                i18.wageSeeker.nameRequired,
                              ),
                          'minLength': (_) => t.translate(
                                i18.wageSeeker.minNameCharacters,
                              ),
                          'maxLength': (_) => t.translate(
                                i18.wageSeeker.maxNameCharacters,
                              ),
                        },
                      ),
                      DigitTextFormField(
                        formControlName: fatherNameKey,
                        isRequired: true,
                        label: t.translate(i18.common.guardianName),
                        inputFormatter: [
                          FilteringTextInputFormatter.allow(RegExp("[A-Za-z ]"))
                        ],
                        validationMessages: {
                          'required': (_) => t.translate(
                                i18.wageSeeker.fatherNameRequired,
                              ),
                          'minLength': (_) => t.translate(
                                i18.wageSeeker.minFatherNameCharacters,
                              ),
                          'maxLength': (_) => t.translate(
                                i18.wageSeeker.maxFatherNameCharacters,
                              ),
                        },
                      ),
                      DigitReactiveDropdown<String>(
                        label: t.translate(i18.common.relationship),
                        menuItems:
                            relationship.map((e) => e.toString()).toList(),
                        isRequired: true,
                        formControlName: relationshipKey,
                        valueMapper: (value) =>
                            t.translate('CORE_COMMON_$value'),
                        onChanged: (value) {},
                        validationMessages: {
                          'required': (_) => t.translate(
                                i18.wageSeeker.relationshipRequired,
                              ),
                        },
                      ),
                      DigitDateFormPicker(
                        label: t.translate(i18.common.dateOfBirth),
                        padding: const EdgeInsets.only(top: 32.0),
                        isRequired: true,
                        cancelText: t.translate(i18.common.cancel),
                        fieldHintText: 'dd/mm/yyyy',
                        confirmText: t.translate(i18.common.oK),
                        icon: Icons.info_outline_rounded,
                        toolTipMsg: t.translate(i18.wageSeeker.ageValidation),
                        formControlName: dobKey,
                        autoValidation: AutovalidateMode.always,
                        requiredMessage:
                            t.translate(i18.wageSeeker.dobRequired),
                        validationMessages: {
                          'required': (_) => t.translate(
                                i18.wageSeeker.dobRequired,
                              ),
                          'max': (_) =>
                              t.translate(i18.wageSeeker.ageValidation)
                        },
                      ),
                      StatefulBuilder(builder:
                          (BuildContext context, StateSetter setState) {
                        return DigitRadioButtonList<String>(
                          labelText: t.translate(i18.common.gender),
                          formControlName: genderKey,
                          options: gender.map((e) => e.toString()).toList(),
                          isRequired: true,
                          valueMapper: (value) => t.translate(value),
                          onValueChange: (value) {
                            genderController = value;
                          },
                        );
                      }),
                      DigitReactiveDropdown<String>(
                        label: t.translate(i18.common.socialCategory),
                        menuItems:
                            socialCategory.map((e) => e.toString()).toList(),
                        formControlName: socialCategoryKey,
                        valueMapper: (value) =>
                            t.translate('COMMON_MASTERS_SOCIAL_$value'),
                        onChanged: (value) {},
                      ),
                      DigitTextFormField(
                        label: t.translate(i18.common.mobileNumber),
                        padding: const EdgeInsets.only(top: 32),
                        formControlName: mobileKey,
                        prefixText: '+91 - ',
                        isRequired: true,
                        minLength: 10,
                        maxLength: 10,
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
                          'min': (_) => t.translate(
                                i18.wageSeeker.validMobileCharacters,
                              ),
                          'max': (_) => t.translate(
                                i18.wageSeeker.validMobileCharacters,
                              ),
                        },
                      ),
                      // StatefulBuilder(
                      //     builder: (BuildContext context, StateSetter setState) {
                      MultiSelectSearchCheckBox(
                        label: t.translate(i18.attendanceMgmt.skill) + ' *',
                        onChange: _onSelectedOptionsChanged,
                        options: skills,
                        hintText: t.translate(i18.attendanceMgmt.skill),
                        selectedOptions: selectedOptions,
                      ),
                      // }),
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
                        extensions: const ['jpg', 'png', 'jpeg'],
                        moduleName: 'works',
                        label: t.translate(i18.common.photoGraph),
                      )
                    ]),
                    const SizedBox(height: 16),
                    Center(
                      child: DigitElevatedButton(
                          onPressed: () {
                            form.markAllAsTouched(updateParent: false);
                            if (!form.valid) return;
                            if (form.value[genderKey] == null ||
                                form.value[genderKey].toString().isEmpty) {
                              Notifiers.getToastMessage(
                                  context,
                                  t.translate(i18.wageSeeker.genderRequired),
                                  'ERROR');
                            } else if (!getSkillsValid()) {
                              Notifiers.getToastMessage(
                                  context,
                                  i18.wageSeeker.selectSkillValidation,
                                  'ERROR');
                            } else if (selectedOptions.isEmpty) {
                              Notifiers.getToastMessage(context,
                                  i18.wageSeeker.skillsRequired, 'ERROR');
                            } else {
                              final individualDetails = IndividualDetails(
                                  name: form.value[nameKey].toString(),
                                  fatherName:
                                      form.value[fatherNameKey].toString(),
                                  aadhaarNo:
                                      form.value[aadhaarNoKey].toString(),
                                  relationship:
                                      form.value[relationshipKey].toString(),
                                  socialCategory:
                                      form.value[socialCategoryKey].toString(),
                                  dateOfBirth: form.value[dobKey] as DateTime,
                                  mobileNumber:
                                      form.value[mobileKey].toString(),
                                  gender: form.value[genderKey].toString(),
                                  imageFile: FilePickerData.imageFile,
                                  bytes: FilePickerData.bytes,
                                  photo: photo);
                              final skillList = SkillDetails(
                                  individualSkills: selectedOptions
                                      .map((e) => IndividualSkill(
                                          type: e.toString().split('.').last,
                                          level: e.toString().split('.').first))
                                      .toList());

                              BlocProvider.of<WageSeekerBloc>(context).add(
                                WageSeekerCreateEvent(
                                    individualDetails: individualDetails,
                                    skillDetails: skillList,
                                    locationDetails: locationDetails,
                                    financialDetails: financialDetails),
                              );
                              widget.onPressed();
                            }
                          },
                          child: Center(
                            child: Text(t.translate(i18.common.next)),
                          )),
                    )
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  bool getSkillsValid() {
    Map<String, int> beforeDotCount = {};
    Map<String, int> afterDotCount = {};

    for (String skill in selectedOptions) {
      List<String> skillParts = skill.split(".");
      String beforeDot = skillParts[0];
      String afterDot = skillParts[1];

      beforeDotCount[beforeDot] = (beforeDotCount[beforeDot] ?? 0) + 1;
      afterDotCount[afterDot] = (afterDotCount[afterDot] ?? 0) + 1;
    }

    // int countBeforeDot =
    //     beforeDotCount.values.where((count) => count > 1).length;
    int countAfterDot = afterDotCount.values.where((count) => count > 1).length;

    if (countAfterDot > 0) {
      return false;
    }
    return true;
  }

  FormGroup buildForm() => fb.group(<String, Object>{
        aadhaarNoKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(12),
          Validators.maxLength(12)
        ]),
        nameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        genderKey: FormControl<String>(value: null),
        fatherNameKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(128)
        ]),
        relationshipKey:
            FormControl<String>(value: null, validators: [Validators.required]),
        dobKey: FormControl<DateTime>(
          value: null,
          validators: [
            Validators.required,
            Validators.max(DateTime(DateTime.now().year - 18,
                DateTime.now().month, DateTime.now().day))
          ],
        ),
        socialCategoryKey: FormControl<String>(value: null),
        mobileKey: FormControl<String>(value: '', validators: [
          Validators.required,
          Validators.minLength(10),
          Validators.min('5999999999'),
          Validators.max('9999999999'),
          Validators.maxLength(10)
        ])
      });
}
