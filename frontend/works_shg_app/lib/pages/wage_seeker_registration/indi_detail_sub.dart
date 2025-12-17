// import 'package:digit_components/widgets/digit_card.dart';
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/widgets/molecules/digit_footer.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/utils/constants.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:digit_ui_components/utils/validators/validator.dart'
    as ui_validation;

class IndividualSubDetailPage extends StatefulWidget {
  final List<String> relationship;
  final List<String> gender;
  final List<String> socialCategory;
  final List<String> skills;
  final String? photo;
  final IndividualDetails? individualDetails;
  final Function(int page) onPageChanged;
  const IndividualSubDetailPage({
    super.key,
    required this.relationship,
    required this.gender,
    required this.photo,
    required this.skills,
    required this.socialCategory,
    required this.onPageChanged,
    required this.individualDetails,
  });

  @override
  State<IndividualSubDetailPage> createState() =>
      _IndividualSubDetailPageState();
}

class _IndividualSubDetailPageState extends State<IndividualSubDetailPage> {
  String genderController = '';
  String fatherNameKey = 'fatherName';
  String aadhaarNoKey = 'aadhaarNo';
  String relationshipKey = 'relationship';
  String dobKey = 'dob';
  String genderKey = 'gender';
  String socialCategoryKey = 'socialCategory';
  String mobileKey = 'mobileNo';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final t = AppLocalizations.of(context);

    return ReactiveFormBuilder(
      form: detailBuildForm,
      builder: (contextt, form1, child) {
        return GestureDetector(
          onTap: () {
            if (FocusScope.of(context).hasFocus) {
              FocusScope.of(context).unfocus();
            }
          },
          child: Column(
            children: [
              ui_card.DigitCard(
                margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                cardType: CardType.primary,
                children: [
                  DigitTextBlock(
                    heading: t.translate(i18.wageSeeker.personalDetailHeader),
                  ),
                  LabeledField(
                    isRequired: true,
                    label: t.translate(i18.common.dateOfBirth),
                    child: ReactiveWrapperField(
                      showErrors: (control) =>
                          control.invalid && control.touched,
                      validationMessages: {
                        'required': (error) => t.translate(
                              i18.wageSeeker.dobRequired,
                            ),
                        'max': (error) =>
                            t.translate(i18.wageSeeker.ageValidation)
                      },
                      formControlName: dobKey,
                      builder: (field) {
                        return DigitDateFormInput(
                          controller: TextEditingController()
                            ..text = form1.control(dobKey).value != null
                                ? DateFormat('dd/MM/yyyy')
                                    .format(form1.control(dobKey).value)
                                : '',
                          errorMessage: field.errorText,
                          onChange: (p0) {
                            DateTime selectedDate =
                                DateFormat("dd/MM/yyyy").parse(p0);

                            field.control.markAsTouched();

                            // Calculate the current date minus 18 years
                            DateTime minDate = DateTime.now()
                                .subtract(const Duration(days: 365 * 18));

                            if (selectedDate.isAfter(minDate)) {
                              field.control.setErrors({
                                'max': t.translate(i18.wageSeeker.ageValidation)
                              });
                            }

                            form1.control(dobKey).value = selectedDate;
                          },

                          helpText: t.translate(i18.wageSeeker.ageValidation),
                          firstDate: DateTime(1900),
                          // lastDate: DateTime(DateTime.now().year),
                          isRequired: true,
                          cancelText: t.translate(i18.common.cancel),
                          confirmText: t.translate(i18.common.oK),
                          validations: [
                            // ui_validation.Validator(
                            //     ui_validation.ValidatorType.minValue, 18,
                            //     errorMessage:
                            //         t.translate(i18.wageSeeker.ageValidation)),
                            ui_validation.Validator(
                                ui_validation.ValidatorType.required, null,
                                errorMessage:
                                    t.translate(i18.wageSeeker.dobRequired)),
                          ],
                        );
                      },
                    ),
                  ),
                  LabeledField(
                    label: t.translate(i18.common.gender),
                    isRequired: true,
                    child: ReactiveWrapperField(
                      validationMessages: {
                        "required": (error) =>
                            t.translate(i18.wageSeeker.genderRequired),
                      },
                      showErrors: (control) =>
                          control.invalid && control.touched,
                      formControlName: genderKey,
                      builder: (field) {
                        return RadioList(
                          errorMessage: field.errorText,
                          groupValue: form1.control(genderKey).value,
                          radioButtons: widget.gender
                              .map((e) => RadioButtonModel(
                                  code: e.toString(),
                                  name: t.translate(e.toString())))
                              .toList(),
                          onChanged: (value) {
                            // genderController = value.code;
                            field.control.markAsTouched();

                            form1.control(genderKey).value = value.code;
                          },
                        );
                      },
                    ),
                  ),
                  LabeledField(
                    label: t.translate(i18.common.guardianName),
                    isRequired: true,
                    child: ReactiveWrapperField(
                      validationMessages: {
                        'required': (error) =>
                            t.translate(i18.wageSeeker.fatherNameRequired),
                        'minLength': (error) =>
                            t.translate(i18.wageSeeker.minFatherNameCharacters),
                        'maxLength': (error) =>
                            t.translate(i18.wageSeeker.maxFatherNameCharacters),
                      },
                      formControlName: fatherNameKey,
                      showErrors: (control) =>
                          control.invalid && control.touched,
                      builder: (field) {
                        return DigitTextFormInput(
                          controller: TextEditingController()
                            ..text = form1.control(fatherNameKey).value,
                          isRequired: true,
                          onChange: (value) {
                            field.control.markAsTouched();

                            form1.control(fatherNameKey).value = value;
                          },
                          errorMessage: field.errorText,
                          keyboardType: TextInputType.text,
                          validations: [
                            ui_validation.Validator(
                                ui_validation.ValidatorType.minLength, 2,
                                errorMessage: t.translate(
                                    i18.wageSeeker.minFatherNameCharacters)),
                            ui_validation.Validator(
                              ui_validation.ValidatorType.required,
                              "",
                              errorMessage: t
                                  .translate(i18.wageSeeker.fatherNameRequired),
                            ),
                          ],
                        );
                      },
                    ),
                  ),
                  LabeledField(
                    isRequired: true,
                    label: t.translate(i18.common.relationship),
                    child: ReactiveWrapperField(
                      formControlName: relationshipKey,
                      showErrors: (control) =>
                          control.invalid && control.touched,
                      builder: (field) {
                        return DigitDropdown(
                          dropdownController: TextEditingController()
                            ..text = form1.control(relationshipKey).value ?? '',
                          onSelect: (value) {
                            field.control.markAsTouched();

                            // form1.control(relationshipKey).value = value;
                            setState(() {
                              form1.control(relationshipKey).value = value.code;
                            });

                            // form1.setErrors();
                          },
                          errorMessage: field.errorText,
                          items: widget.relationship
                              .map((e) => DropdownItem(
                                  name: t
                                      .translate('CORE_COMMON_${e.toString()}'),
                                  code: e.toString()))
                              .toList(),
                        );
                      },
                    ),
                  ),
                  LabeledField(
                    label: t.translate(i18.common.mobileNumber),
                    isRequired: true,
                    child: ReactiveWrapperField(
                      validationMessages: {
                        'required': (error) => t.translate(
                              i18.wageSeeker.mobileRequired,
                            ),
                        'minLength': (error) => t.translate(
                              i18.wageSeeker.minMobileCharacters,
                            ),
                        'maxLength': (error) => t.translate(
                              i18.wageSeeker.maxMobileCharacters,
                            ),
                        // 'min': (error) => t.translate(
                        //       i18.wageSeeker.validMobileCharacters,
                        //     ),
                        // 'max': (error) => t.translate(
                        //       i18.wageSeeker.validMobileCharacters,
                        //     ),
                      },
                      formControlName: mobileKey,
                      showErrors: (control) =>
                          control.invalid && control.touched,
                      builder: (field) {
                        return DigitTextFormInput(
                          validations: [
                            ui_validation.Validator(
                                ui_validation.ValidatorType.minLength, 10,
                                errorMessage: t.translate(
                                    i18.wageSeeker.minMobileCharacters)),
                            ui_validation.Validator(
                                ui_validation.ValidatorType.maxLength, 10,
                                errorMessage: t.translate(
                                    i18.wageSeeker.maxMobileCharacters)),
                            // ui_validation.Validator(
                            //     ui_validation.ValidatorType.maxValue, 10,
                            //     errorMessage: t.translate(
                            //         i18.wageSeeker.validMobileCharacters)),
                            // ui_validation.Validator(
                            //     ui_validation.ValidatorType.minValue, 10,
                            //     errorMessage: t.translate(
                            //         i18.wageSeeker.validMobileCharacters)),
                            ui_validation.Validator(
                              ui_validation.ValidatorType.required,
                              "",
                              errorMessage:
                                  t.translate(i18.wageSeeker.mobileRequired),
                            ),
                          ],
                          controller: TextEditingController()
                            ..text = form1.control(mobileKey).value ?? '',
                          isRequired: true,
                          onChange: (value) {
                            field.control.markAsTouched();

                            form1.control(mobileKey).value = value;
                          },
                          charCount: true,
                          errorMessage: field.errorText,
                          keyboardType: TextInputType.phone,
                          inputFormatters: [
                            FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                          ],
                        );
                      },
                    ),
                  ),
                  LabeledField(
                    label: t.translate(i18.common.socialCategory),
                    child: ReactiveWrapperField(
                      formControlName: socialCategoryKey,
                      builder: (field) {
                        return DigitDropdown(
                          dropdownController: TextEditingController()
                            ..text =
                                form1.control(socialCategoryKey).value ?? '',
                          onSelect: (value) {
                            field.control.markAsTouched();

                            form1.control(socialCategoryKey).value = value.code;
                          },
                          items: widget.socialCategory
                              .map(
                                (e) => DropdownItem(
                                  name: t.translate(
                                      'COMMON_MASTERS_SOCIAL_${e.toString()}'),
                                  code: e.toString(),
                                ),
                              )
                              .toList(),
                        );
                      },
                    ),
                  ),
                ],
              ),
              const Padding(
                padding: EdgeInsets.all(16.0),
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: PoweredByDigit(
                    version: Constants.appVersion,
                  ),
                ),
              ),
              DigitFooter(actions: [
                FooterAction(
                  button: Button(
                    type: ButtonType.primary,
                    size: ButtonSize.large,
                    mainAxisSize: MainAxisSize.max,
                    onPressed: () {
                      form1.markAllAsTouched(updateParent: false);
                      if (!form1.valid) return;

                      context.read<WageSeekerBloc>().add(
                            WageSeekerDetailsCreateEvent(
                              dob: form1.value[dobKey] as DateTime,
                              fatherName: form1.value[fatherNameKey].toString(),
                              gender: form1.value[genderKey].toString(),
                              mobileNumber: form1.value[mobileKey].toString(),
                              relationShip:
                                  form1.value[relationshipKey].toString(),
                              socialCategory: form1.value[socialCategoryKey]
                                          .toString() ==
                                      ""
                                  ? "null"
                                  : form1.value[socialCategoryKey].toString(),
                            ),
                          );

                      widget.onPageChanged(2);
                      // }
                    },
                    label: t.translate(i18.common.next),
                  ),
                ),
              ]),
            ],
          ),
        );
      },
    );
  }

  FormGroup detailBuildForm() => fb.group(<String, Object>{
        genderKey: FormControl<String>(
            value: widget.individualDetails?.gender ?? '',
            validators: [
              Validators.required,
            ]),
        fatherNameKey: FormControl<String>(
            value: widget.individualDetails?.fatherName ?? '',
            validators: [
              Validators.required,
              Validators.minLength(2),
              Validators.maxLength(128)
            ]),
        relationshipKey: FormControl<String>(
            value: widget.individualDetails?.relationship,
            validators: [Validators.required]),
        dobKey: FormControl<DateTime>(
          value: widget.individualDetails?.dateOfBirth,
          validators: [
            Validators.required,
            Validators.max(DateTime(DateTime.now().year - 18,
                DateTime.now().month, DateTime.now().day))
          ],
        ),
        socialCategoryKey: FormControl<String>(
            value: widget.individualDetails?.socialCategory == "null"
                ? ''
                : widget.individualDetails?.socialCategory ?? ''),
        mobileKey: FormControl<String>(
            value: widget.individualDetails?.mobileNumber,
            validators: [
              Validators.required,
              Validators.minLength(10),
              Validators.min('5999999999'),
              Validators.max('9999999999'),
              Validators.maxLength(10)
            ]),
      });
}
