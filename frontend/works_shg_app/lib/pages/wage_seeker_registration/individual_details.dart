// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/digit_components.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/utils/validators/validator.dart'
    as ui_validation;
import 'package:digit_ui_components/widgets/molecules/digit_footer.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/models/adharModel/adhar_response.dart';
import 'package:works_shg_app/models/wage_seeker/financial_details_model.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../../blocs/wage_seeker_registration/wage_seeker_create_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../utils/constants.dart';
import '../../widgets/common_info_card.dart';
import 'indi_detail_sub.dart';
import 'indi_photo_sub.dart';
import 'indi_skill_sub.dart';

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
  String aadhaarNoKey = 'aadhaarNo';
  String identityDocument = "identityDocument";
  int switchPage = 0;
  bool adhar = false;
  bool isVerified = false;

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    individualDetails = registrationState.individualDetails;
    skillDetails = registrationState.skillDetails;
    locationDetails = registrationState.locationDetails;
    financialDetails = registrationState.financialDetails;
  }

  void _onPageChange(int count) {
    if (count == 4) {
      widget.onPressed();
    } else {
      setState(() {
        switchPage = count;
      });
    }
  }

  void updateAdhar(String adharName) {
    context
        .read<WageSeekerCreateBloc>()
        .add(const CreateWageSeekerDisposeEvent());
    if (adharName == "AADHAAR") {
      setState(() {
        adhar = true;
      });
    } else {
      setState(() {
        adhar = false;
      });
    }
  }

  void isVerifyDone(bool value) {
    setState(() {
      isVerified = value;
    });
  }

  @override
  Widget build(BuildContext context) {
    // Localization object
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
                .map((e) => e.id!)
                .toList()
            : [];
// Returning appropriate subpage based on the switchPage variable
    switch (switchPage) {
      case 0:
        return identificationMethod(
            context,
            t,
            relationship,
            gender,
            socialCategory,
            skills,
            photo,
            individualDetails,
            adhar,
            (adhar) {
              updateAdhar(adhar);
            },
            isVerified,
            (value) {
              isVerifyDone(value);
            });
      case 1:
        return IndividualSubDetailPage(
          gender: gender,
          photo: photo,
          relationship: relationship,
          skills: skills,
          socialCategory: socialCategory,
          onPageChanged: (int page) {
            _onPageChange(page);
          },
          individualDetails: individualDetails,
        );
      case 2:
        return IndividualSkillSubPage(
          gender: gender,
          photo: photo,
          relationship: relationship,
          skills: skills,
          socialCategory: socialCategory,
          onPageChanged: (int page) {
            _onPageChange(page);
          },
          skillDetails: skillDetails,
        );
      case 3:
        return IndividualPhotoSubPage(
          onPageChanged: (int page) {
            _onPageChange(page);
          },
          photo: individualDetails?.photo ?? '',
        );
      default:
        return identificationMethod(
            context,
            t,
            relationship,
            gender,
            socialCategory,
            skills,
            photo,
            individualDetails,
            adhar,
            (adhar) {
              updateAdhar(adhar);
            },
            isVerified,
            (value) {
              isVerifyDone(value);
            });
    }
  }

// Widget for displaying identification method form
  BlocListener identificationMethod(
    BuildContext context,
    AppLocalizations t,
    List<String> relationship,
    List<String> gender,
    List<String> socialCategory,
    List<String> skills,
    String? photo,
    IndividualDetails? individualDetails,
    bool adhar,
    final Function(String adhar) adharSelect,
    bool isVerified,
    final Function(bool value) isVerifyFunction,
  ) {
    return BlocListener<WageSeekerCreateBloc, WageSeekerCreateState>(
      listener: (context, state) {
        // Listen to state changes in WageSeekerCreateBloc
        state.maybeMap(
          orElse: () => {const SizedBox.shrink()},
          verified: (adharCardResponse) {
            // Update Aadhaar verification status
            isVerified = adharCardResponse.adharCardResponse!.status ==
                    Constants.verifyAdhar
                ? true
                : false;

            isVerifyDone(isVerified);
          },
          error: (error) {
            isVerified = false;
            isVerifyDone(isVerified);
          },
        );
      },
      child: ReactiveFormBuilder(
        form: () =>
            identificationBuildForm(individualDetails ?? IndividualDetails()),
        builder: (context, form, child) {
          return GestureDetector(
            onTap: () {
              if (FocusScope.of(context).hasFocus) {
                FocusScope.of(context).unfocus();
              }
            },
            child: SizedBox(
              height: MediaQuery.sizeOf(context).height * 0.719,
              child: ScrollableContent(
                backgroundColor: Theme.of(context).colorTheme.generic.background,
                
                footer: DigitFooter(actions: [
                  FooterAction(button: 
                   Button(
                          label: t.translate(i18.common.next),
                          onPressed: () {
                            form.markAllAsTouched(updateParent: false);
                            // setState(() {});
                            if (!form.valid) return;

                            context.read<WageSeekerBloc>().add(
                                  WageSeekerIdentificationCreateEvent(
                                      adharVerified: false,
                                      documentType: form.value[identityDocument]
                                          .toString(),
                                      name: form.value[nameKey].toString(),
                                      number:
                                          form.value[aadhaarNoKey].toString(),
                                      timeStamp:
                                          DateTime.now().millisecondsSinceEpoch,
                                      adharCardResponse:
                                          const AdharCardResponse()),
                                );
                            setState(() {
                              switchPage = 1;
                            });
                          },
                          type: ButtonType.primary,
                          size: ButtonSize.large,
                          mainAxisSize: MainAxisSize.max,
                        )
                  )
                ]),

                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  ui_card.DigitCard(
                    margin:
                        EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
                    cardType: CardType.primary,
                    children: [
                      DigitTextBlock(
                        heading: t.translate(
                          i18.wageSeeker.identificationHeader,
                        ),
                        // style: DigitTheme
                        //     .instance.mobileTheme.textTheme.displayMedium
                        //     ?.apply(color: const DigitColors().black),
                      ),

                      ui_component.DigitDropdown(
                        selectedOption: DropdownItem(
                            name: t.translate('CBO_CORE_COMMON_AADHAAR'),
                            code: "AADHAAR".toString()),
                        items: [
                          "AADHAAR",
                        ]
                            .map((e) => DropdownItem(
                                name: t.translate('CBO_CORE_COMMON_$e'),
                                code: e.toString()))
                            .toList(),
                      ),

                      ui_component.LabeledField(
                        label: t.translate(i18.wageSeeker.identityNumberLabel),
                        isRequired: true,
                        child: ReactiveWrapperField(
                          formControlName: aadhaarNoKey,
                          validationMessages: {
                            'required': (error) =>
                                t.translate(i18.wageSeeker.aadhaarRequired),
                            'minLength': (error) => t
                                .translate(i18.wageSeeker.minAadhaarCharacters),
                            'maxLength': (error) => t
                                .translate(i18.wageSeeker.maxAadhaarCharacters),
                          },
                          showErrors: (control) =>
                              control.invalid && control.touched,
                          builder: (field) {
                            return DigitTextFormInput(
                              controller: TextEditingController()
                                ..text = form.control(aadhaarNoKey).value,
                              isRequired: true,
                              // minLength: 12,
                              // maxLength: 12,
                              onChange: (value) {
                                field.control.markAsTouched();

                                form.control(aadhaarNoKey).value = value;
                              },
                              charCount: true,

                              errorMessage: field.errorText,
                              keyboardType: TextInputType.number,
                              inputFormatters: [
                                FilteringTextInputFormatter.allow(
                                    RegExp("[0-9]"))
                              ],
                              validations: [
                                ui_validation.Validator(
                                    ui_validation.ValidatorType.minLength, 12,
                                    errorMessage: t.translate(
                                        i18.wageSeeker.minAadhaarCharacters)),
                                ui_validation.Validator(
                                    ui_validation.ValidatorType.maxLength, 12,
                                    errorMessage: t.translate(
                                        i18.wageSeeker.maxAadhaarCharacters)),
                              ],
                            );
                          },
                        ),
                      ),

                      ui_component.LabeledField(
                        isRequired: true,
                        label: t.translate(i18.wageSeeker.identityNameLabel),
                        child: ReactiveWrapperField(
                          validationMessages: {
                            'required': (error) => t.translate(
                                  i18.wageSeeker.aadhaarRequired,
                                ),
                            'minLength': (error) => t.translate(
                                  i18.wageSeeker.minNameCharacters,
                                ),
                            'maxLength': (error) => t.translate(
                                  i18.wageSeeker.maxNameCharacters,
                                ),
                          },
                          showErrors: (control) =>
                              control.invalid && control.touched,
                          formControlName: nameKey,
                          builder: (field) {
                            return DigitTextFormInput(
                              errorMessage: field.errorText,
                              controller: TextEditingController()
                                ..text = form.control(nameKey).value,
                              isRequired: true,
                              // minLength: 12,
                              // maxLength: 12,
                              onChange: (value) {
                                field.control.markAsTouched();
                                form.control(nameKey).value = value;
                              },

                              validations: [
                                ui_validation.Validator(
                                    ui_validation.ValidatorType.minLength, 2,
                                    errorMessage: t.translate(
                                      i18.wageSeeker.minNameCharacters,
                                    )),
                                ui_validation.Validator(
                                  ui_validation.ValidatorType.required,
                                  "",
                                  errorMessage: t.translate(
                                    i18.wageSeeker.aadhaarRequired,
                                  ),
                                ),
                              ],
                              keyboardType: TextInputType.text,
                              inputFormatters: [
                                FilteringTextInputFormatter.allow(
                                    RegExp("[A-Za-z ]"))
                              ],
                            );
                          },
                        ),
                      ),

                      // Button(
                      //   label: t.translate(i18.common.next),
                      //   onPressed: () {
                      //     form.markAllAsTouched(updateParent: false);
                      //     // setState(() {});
                      //     if (!form.valid) return;

                      //     context.read<WageSeekerBloc>().add(
                      //           WageSeekerIdentificationCreateEvent(
                      //               adharVerified: false,
                      //               documentType:
                      //                   form.value[identityDocument].toString(),
                      //               name: form.value[nameKey].toString(),
                      //               number: form.value[aadhaarNoKey].toString(),
                      //               timeStamp: DateTime.now().millisecondsSinceEpoch,
                      //               adharCardResponse: const AdharCardResponse()),
                      //         );
                      //     setState(() {
                      //       switchPage = 1;
                      //     });
                      //   },
                      //   type: ButtonType.primary,
                      //   size: ButtonSize.large,
                      //   mainAxisSize: MainAxisSize.max,
                      // )
                    ],
                  ),
                 const Align(
                alignment: Alignment.bottomCenter,
                child: PoweredByDigit(
                  version: Constants.appVersion,
                ),
              )
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  // Build Reactive Forms FormGroup for identification details
  FormGroup identificationBuildForm(
    IndividualDetails individualDetails,
  ) =>
      fb.group(<String, Object>{
        aadhaarNoKey: FormControl<String>(
            value: individualDetails.aadhaarNo ?? '',
            validators: adhar
                ? [
                    Validators.required,
                    Validators.minLength(12),
                    Validators.maxLength(12)
                  ]
                : [
                    Validators.required,
                    Validators.minLength(12),
                    Validators.maxLength(12)
                  ]),
        nameKey: FormControl<String>(
            value: individualDetails.name ?? '',
            validators: [
              Validators.required,
              Validators.minLength(2),
              Validators.maxLength(128)
            ]),
        identityDocument: FormControl<String>(
            value: individualDetails.documentType ?? "AADHAAR",
            validators: [
              Validators.required,
            ]),
      });
}
