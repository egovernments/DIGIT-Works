import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:multi_select_flutter/multi_select_flutter.dart';
import 'package:reactive_forms/reactive_forms.dart';
import 'package:works_shg_app/models/wage_seeker/location_details_model.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/location_mdms.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/financial_details_model.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/skill_details_model.dart';
import '../../utils/global_variables.dart';

class LocationDetailsPage extends StatefulWidget {
  final void Function() onPressed;
  final String? city;
  final Location? location;
  final WageSeekerMDMS? wageSeekerMDMS;
  const LocationDetailsPage(
      {required this.onPressed,
      this.city,
      this.location,
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
  List<String> locality = [];

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    individualDetails = registrationState.individualDetails;
    skillDetails = registrationState.skillDetails;
    if (registrationState.locationDetails != null) {
      locationDetails = registrationState.locationDetails!;
      locality = registrationState.locationDetails?.ward != null
          ? widget.location!.tenantBoundaryList!.first.boundaryList!
              .where((w) => w.code == registrationState.locationDetails?.ward)
              .first
              .localityChildren!
              .map((e) => e.code.toString())
              .toList()
          : [];
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
    List<String> ward = widget.location!.tenantBoundaryList!.first.boundaryList!
        .map((e) => e.code.toString())
        .toList();
    return ReactiveFormBuilder(
      form: () => buildForm(locationDetails),
      builder: (context, form, child) {
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
                    t.translate(i18.common.locationDetails),
                    style: DigitTheme
                        .instance.mobileTheme.textTheme.displayMedium
                        ?.apply(color: const DigitColors().black),
                  ),
                  Column(children: [
                    DigitTextFormField(
                      formControlName: pinCodeKey,
                      label: t.translate(i18.common.pinCode),
                      keyboardType: TextInputType.number,
                      inputFormatter: [
                        FilteringTextInputFormatter.allow(RegExp("[0-9]"))
                      ],
                    ),
                    DigitDropdown<String>(
                      label: t.translate(i18.common.city),
                      menuItems: city.map((e) => e.toString()).toList(),
                      isRequired: true,
                      formControlName: cityKey,
                      valueMapper: (value) =>
                          t.translate('PG_${value.toUpperCase()}'),
                      onChanged: (value) {},
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.cityRequired,
                            ),
                      },
                    ),
                    DigitDropdown<String>(
                      label: t.translate(i18.common.ward),
                      menuItems: ward.map((e) => e.toString()).toList(),
                      isRequired: true,
                      formControlName: wardKey,
                      valueMapper: (value) => t.translate(
                          '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase()}_ADMIN_$value'),
                      validationMessages: {
                        'required': (_) => t.translate(
                              i18.wageSeeker.localityRequired,
                            ),
                      },
                      onChanged: (value) {
                        setState(() {
                          locality = widget
                              .location!.tenantBoundaryList!.first.boundaryList!
                              .where((w) => w.code == value)
                              .first
                              .localityChildren!
                              .map((e) => e.code.toString())
                              .toList();
                          form.control(localityKey).value = null;
                        });
                      },
                    ),
                    DigitDropdown<String>(
                        label: t.translate(i18.common.locality),
                        menuItems: locality.map((e) => e.toString()).toList(),
                        formControlName: localityKey,
                        valueMapper: (value) => t.translate(
                            '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase()}_ADMIN_$value'),
                        isRequired: true,
                        onChanged: (value) {},
                        validationMessages: {
                          'required': (_) => t.translate(
                                i18.wageSeeker.localityRequired,
                              ),
                        }),
                    DigitTextFormField(
                      formControlName: streetNameKey,
                      label: t.translate(i18.common.streetName),
                    ),
                    DigitTextFormField(
                      formControlName: doorNoKey,
                      label: t.translate(i18.common.doorNo),
                    ),
                  ]),
                  const SizedBox(height: 16),
                  Padding(
                    padding: const EdgeInsets.all(4.0),
                    child: Center(
                      child: DigitElevatedButton(
                          onPressed: () {
                            form.markAllAsTouched(updateParent: false);
                            if (!form.valid) return;
                            final locationDetails = LocationDetails(
                                pinCode: form.value[pinCodeKey].toString(),
                                city: form.value[cityKey].toString(),
                                locality: form.value[localityKey].toString(),
                                ward: form.value[wardKey].toString(),
                                streetName:
                                    form.value[streetNameKey].toString(),
                                doorNo: form.value[doorNoKey].toString());
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
                    ),
                  )
                ],
              ),
            ),
          ],
        );
      },
    );
  }

  FormGroup buildForm(LocationDetails locationDetails) =>
      fb.group(<String, Object>{
        pinCodeKey: FormControl<String>(value: locationDetails.pinCode ?? ''),
        cityKey: FormControl<String>(
            value: locationDetails.city, validators: [Validators.required]),
        wardKey: FormControl<String>(
            value: locationDetails.ward, validators: [Validators.required]),
        localityKey: FormControl<String>(
            value: locationDetails.locality, validators: [Validators.required]),
        streetNameKey:
            FormControl<String>(value: locationDetails.streetName ?? ''),
        doorNoKey: FormControl<String>(value: locationDetails.doorNo ?? ''),
      });
}
