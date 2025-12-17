// import 'package:digit_components/digit_components.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/enum/app_enums.dart';
import 'package:digit_ui_components/models/models.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';

import 'package:digit_ui_components/widgets/molecules/digit_card.dart'
    as ui_card;
import 'package:digit_ui_components/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:multi_select_flutter/multi_select_flutter.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/models/wage_seeker/location_details_model.dart';
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/location_mdms.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/financial_details_model.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../utils/global_variables.dart';
import 'package:digit_ui_components/digit_components.dart' as ui_component;
import 'package:digit_ui_components/utils/validators/validator.dart'
    as ui_validation;

class LocationDetailsPage extends StatefulWidget {
  final void Function() onPressed;
  final String? city;
  final Location? ward;
  final Location? locality;
  final WageSeekerMDMS? wageSeekerMDMS;
  const LocationDetailsPage(
      {required this.onPressed,
      this.city,
      this.ward,
      this.locality,
      this.wageSeekerMDMS,
      super.key});

  @override
  LocationDetailsState createState() => LocationDetailsState();
}

class LocationDetailsState extends State<LocationDetailsPage> {
  IndividualDetails? individualDetails = IndividualDetails();
  LocationDetails locationDetails = LocationDetails();
  SkillDetails? skillDetails = SkillDetails();
  FinancialDetails? financialDetails = FinancialDetails();
  String pinCodeKey = 'pinCode';
  String cityKey = 'city';
  String wardKey = 'ward';
  String localityKey = 'locality';
  String streetNameKey = 'streetName';
  String doorNoKey = 'doorNo';

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    individualDetails = registrationState.individualDetails;
    skillDetails = registrationState.skillDetails;
    if (registrationState.locationDetails != null) {
      locationDetails = registrationState.locationDetails!;
      financialDetails = registrationState.financialDetails;
    }
  }

  List<MultiSelectItem> selectedSkills = [];

  List<String> selectedItems = [];
  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    List<String> city = widget
            .wageSeekerMDMS?.tenantMDMS?.cityModule?.first.tenants
            ?.where((t) => t.code == widget.city)
            .map((e) => e.code.toString())
            .toList() ??
        [];
    List<String> ward = widget.ward!.tenantBoundaryList!.first.boundaryList!
        .map((e) => e.code.toString())
        .toList();
    List<String> locality = widget
        .locality!.tenantBoundaryList!.first.boundaryList!
        .map((e) => e.code.toString())
        .toList();
    return ReactiveFormBuilder(
      form: () => buildForm(locationDetails),
      builder: (context, form, child) {
        return Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            ui_card.DigitCard(
              cardType: CardType.primary,
              margin: EdgeInsets.all(Theme.of(context).spacerTheme.spacer2),
              children: [
                DigitTextBlock(
                  heading: t.translate(i18.common.locationDetails),
                ),
                ui_component.LabeledField(
                  label: t.translate(i18.common.pinCode),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'maxLength': (error) => t.translate(
                            i18.wageSeeker.pinCodeValidation,
                          ),
                    },
                    formControlName: pinCodeKey,
                    builder: (field) {
                      return DigitTextFormInput(
                        charCount: true,
                        maxLength: 6,
                        keyboardType: TextInputType.number,
                        controller: TextEditingController()
                          ..text = form.control(pinCodeKey).value,
                        onChange: (value) {
                          field.control.markAsTouched();

                          form.control(pinCodeKey).value = value;
                        },
                        validations: [
                          ui_validation.Validator(
                              ui_validation.ValidatorType.maxLength, 6,
                              errorMessage: t
                                  .translate(i18.wageSeeker.pinCodeValidation)),
                        ],
                        //charCount: true,
                      );
                    },
                  ),
                ),
                ui_component.LabeledField(
                  isRequired: true,
                  label: t.translate(i18.common.city),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'required': (error) => t.translate(
                            i18.wageSeeker.cityRequired,
                          ),
                    },
                    formControlName: cityKey,
                    showErrors: (control) => control.invalid && control.touched,
                    builder: (field) {
                      return ui_component.DigitDropdown(
                          selectedOption: DropdownItem(
                              name: locationDetails.city != null
                                  ? AppLocalizations.of(context).translate(
                                      'TENANT_TENANTS_${locationDetails.city!.replaceAll('.', '_').toUpperCase()}')
                                  : AppLocalizations.of(context).translate(
                                      'TENANT_TENANTS_${widget.city!.replaceAll('.', '_').toUpperCase()}'),
                              code: locationDetails.city ??
                                  widget.city.toString()),
                          dropdownController: TextEditingController()
                            ..text = form.control(cityKey).value ?? '',
                          onSelect: (value) {
                            field.control.markAsTouched();

                            // form1.control(relationshipKey).value = value;
                            setState(() {
                              form.control(cityKey).value = value.code;
                            });

                            // form1.setErrors();
                          },
                          errorMessage: field.errorText,
                          items: city
                              .map((e) => DropdownItem(
                                  name: t.translate(
                                      'TENANT_TENANTS_${e.replaceAll('.', '_').toUpperCase()}'),
                                  code: e.toString()))
                              .toList());
                    },
                  ),
                ),
                ui_component.LabeledField(
                  isRequired: true,
                  label: t.translate(i18.common.ward),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'required': (error) => t.translate(
                            i18.wageSeeker.wardRequired,
                          ),
                    },
                    formControlName: wardKey,
                    showErrors: (control) => control.invalid && control.touched,
                    builder: (field) {
                      return ui_component.DigitDropdown(
                          isSearchable: true,
                          dropdownController: TextEditingController()
                            ..text = form.control(wardKey).value ?? '',
                          onSelect: (value) {
                            field.control.markAsTouched();

                            // form1.control(relationshipKey).value = value;
                            setState(() {
                              form.control(wardKey).value = value.code;
                            });

                            // form1.setErrors();
                          },
                          errorMessage: field.errorText,
                          items: ward.reversed
                              .map((e) => DropdownItem(
                                  name: t.translate(
                                      '${GlobalVariables.organisationListModel?.organisations?.first.tenantId.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.toString()}'),
                                  code: e.toString()))
                              .toList());
                    },
                  ),
                ),
                ui_component.LabeledField(
                  isRequired: true,
                  label: t.translate(i18.common.locality),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'required': (_) => t.translate(
                            i18.wageSeeker.localityRequired,
                          ),
                    },
                    formControlName: localityKey,
                    showErrors: (control) => control.invalid && control.touched,
                    builder: (field) {
                      return ui_component.DigitDropdown(
                          isSearchable: true,
                          dropdownController: TextEditingController()
                            ..text = form.control(localityKey).value ?? '',
                          onSelect: (value) {
                            field.control.markAsTouched();

                            // form1.control(relationshipKey).value = value;
                            setState(() {
                              form.control(localityKey).value = value.code;
                            });

                            // form1.setErrors();
                          },
                          errorMessage: field.errorText,
                          items: locality
                              .map((e) => DropdownItem(
                                  name: t.translate(
                                      '${GlobalVariables.organisationListModel?.organisations?.first.tenantId.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.toString()}'),
                                  code: e.toString()))
                              .toList());
                    },
                  ),
                ),
                ui_component.LabeledField(
                  label: t.translate(i18.common.streetName),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'maxLength': (error) => t.translate(
                            i18.wageSeeker.maxStreetCharacters,
                          ),
                    },
                    formControlName: streetNameKey,
                    builder: (field) {
                      return DigitTextFormInput(
                          errorMessage: field.errorText,
                          controller: TextEditingController()
                            ..text = form.control(streetNameKey).value,
                          onChange: (value) {
                            field.control.markAsTouched();

                            form.control(streetNameKey).value = value;
                          },
                          inputFormatters: [
                            FilteringTextInputFormatter.allow(
                                RegExp("[a-zA-Z0-9 .,\\/\\-_@#\\']"))
                          ]);
                    },
                  ),
                ),
                ui_component.LabeledField(
                  label: t.translate(i18.common.doorNo),
                  child: ReactiveWrapperField(
                    validationMessages: {
                      'maxLength': (error) => t.translate(
                            i18.wageSeeker.maxDoorNoCharacters,
                          ),
                    },
                    formControlName: doorNoKey,
                    builder: (field) {
                      return DigitTextFormInput(
                        charCount: true,
                        controller: TextEditingController()
                          ..text = form.control(doorNoKey).value,
                        onChange: (value) {
                          field.control.markAsTouched();

                          form.control(doorNoKey).value = value;
                        },
                        errorMessage: field.errorText,
                        inputFormatters: [
                          FilteringTextInputFormatter.allow(
                              RegExp("[a-zA-Z0-9 .,\\/\\-_@#\\']"))
                        ],
                        validations: [
                          ui_validation.Validator(
                              ui_validation.ValidatorType.maxLength, 8,
                              errorMessage: t.translate(
                                  i18.wageSeeker.maxDoorNoCharacters)),
                        ],
                      );
                    },
                  ),
                ),
              ],
            ),
            Column(
              children: [
                const Padding(
                  padding: EdgeInsets.all(16.0),
                  child: Align(
                    alignment: Alignment.bottomCenter,
                    child: PoweredByDigit(
                      version: Constants.appVersion,
                    ),
                  ),
                ),
                ui_card.DigitCard(
                  children: [
                    Button(
                      size: ButtonSize.large,
                      type: ButtonType.primary,
                      mainAxisSize: MainAxisSize.max,
                      onPressed: () {
                        form.markAllAsTouched(updateParent: false);
                        if (!form.valid) return;
                        if (form.value[pinCodeKey].toString().isNotEmpty &&
                            form.value[pinCodeKey].toString().length < 6) {
                          Notifiers.getToastMessage(
                              context,
                              t.translate(i18.wageSeeker.pinCodeValidation),
                              'ERROR');

                          // Toast.showToast(context,
                          //     message:
                          //         t.translate(i18.wageSeeker.pinCodeValidation),
                          //     type: ToastType.error);
                        } else if (form.value[pinCodeKey]
                                .toString()
                                .isNotEmpty &&
                            int.parse(form.value[pinCodeKey].toString()) <
                                100000) {
                          Notifiers.getToastMessage(
                              context,
                              t.translate(i18.wageSeeker.pinCodeValidation),
                              'ERROR');

                          // Toast.showToast(context,
                          //     message:
                          //         t.translate(i18.wageSeeker.pinCodeValidation),
                          //     type: ToastType.error);
                        } else {
                          final locationDetails = LocationDetails(
                              pinCode: form.value[pinCodeKey].toString(),
                              city: form.value[cityKey].toString(),
                              locality: form.value[localityKey].toString(),
                              ward: form.value[wardKey].toString(),
                              streetName: form.value[streetNameKey].toString(),
                              doorNo: form.value[doorNoKey].toString());
                          BlocProvider.of<WageSeekerBloc>(context).add(
                            WageSeekerCreateEvent(
                                individualDetails: individualDetails,
                                skillDetails: skillDetails,
                                locationDetails: locationDetails,
                                financialDetails: financialDetails),
                          );
                          widget.onPressed();
                        }
                      },
                      label: t.translate(i18.common.next),
                    )
                  ],
                ),
              ],
            ),
          ],
        );
      },
    );
  }

  FormGroup buildForm(LocationDetails locationDetails) =>
      fb.group(<String, Object>{
        pinCodeKey: FormControl<String>(
            value: locationDetails.pinCode ?? '',
            validators: [Validators.maxLength(6)]),
        cityKey: FormControl<String>(
            value: locationDetails.city ?? widget.city,
            // value: locationDetails.city != null
            //     ? AppLocalizations.of(context).translate(
            //         'TENANT_TENANTS_${locationDetails.city!.replaceAll('.', '_').toUpperCase()}')
            //     :AppLocalizations.of(context).translate(
            //         'TENANT_TENANTS_${widget.city!.replaceAll('.', '_').toUpperCase()}'),
            validators: [Validators.required]),
        wardKey: FormControl<String>(
            value: locationDetails.ward, validators: [Validators.required]),
        localityKey: FormControl<String>(
            value: locationDetails.locality, validators: [Validators.required]),
        streetNameKey: FormControl<String>(
            value: locationDetails.streetName ?? '',
            validators: [Validators.maxLength(64)]),
        doorNoKey: FormControl<String>(
            value: locationDetails.doorNo ?? '',
            validators: [Validators.maxLength(8)]),
      });
}
