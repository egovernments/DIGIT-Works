import 'package:digit_components/digit_components.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/models/wage_seeker/skill_details_model.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/models/file_picker_data.dart';
import 'package:works_shg_app/widgets/atoms/custom_details.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_bank_create.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_create_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/attendance/individual_list_model.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../models/wage_seeker/banking_details_model.dart';
import '../../models/wage_seeker/financial_details_model.dart';
import '../../models/wage_seeker/individual_details_model.dart';
import '../../models/wage_seeker/location_details_model.dart';
import '../../utils/global_variables.dart';
import '../../utils/notifiers.dart';

class SummaryDetailsPage extends StatefulWidget {
  final void Function(int index) onPressed;
  final WageSeekerMDMS? wageSeekerMDMS;
  const SummaryDetailsPage(
      {required this.onPressed, this.wageSeekerMDMS, super.key});

  @override
  SummaryDetailsPageState createState() {
    return SummaryDetailsPageState();
  }
}

class SummaryDetailsPageState extends State<SummaryDetailsPage> {
  bool stateChange = false;
  List<String> selectedOptions = [];
  IndividualDetails? individualDetails = IndividualDetails();
  LocationDetails? locationDetails = LocationDetails();
  SkillDetails? skillDetails = SkillDetails();
  FinancialDetails? financialDetails = FinancialDetails();

  @override
  void initState() {
    super.initState();
    final registrationState = BlocProvider.of<WageSeekerBloc>(context).state;
    skillDetails = registrationState.skillDetails;
    individualDetails = registrationState.individualDetails;
    locationDetails = registrationState.locationDetails;
    financialDetails = registrationState.financialDetails;
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          margin: const EdgeInsets.all(8.0),
          child: Text(
            t.translate(i18.wageSeeker.summaryDetails),
            style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                ?.apply(color: const DigitColors().black),
          ),
        ),
        DigitCard(
            child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  t.translate(i18.attendanceMgmt.individualDetails),
                  style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                      ?.apply(color: const DigitColors().black),
                  textAlign: TextAlign.left,
                ),
                GestureDetector(
                  child: Icon(
                    Icons.edit,
                    color: const DigitColors().burningOrange,
                  ),
                  onTap: () {
                    widget.onPressed(0);
                  },
                )
              ],
            ),
            getItemWidget(context,
                title: t.translate(i18.common.aadhaarNumber),
                description: individualDetails != null &&
                        individualDetails?.aadhaarNo != null
                    ? individualDetails!.aadhaarNo.toString()
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.wageSeeker.nameOfWageSeeker),
                description:
                    individualDetails != null && individualDetails?.name != null
                        ? individualDetails!.name.toString()
                        : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.guardianName),
                description: individualDetails != null &&
                        individualDetails?.fatherName != null
                    ? individualDetails!.fatherName.toString()
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.relationship),
                description: individualDetails != null &&
                        individualDetails?.relationship != null
                    ? t.translate(individualDetails!.relationship.toString())
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.dateOfBirth),
                description: individualDetails != null &&
                        individualDetails?.dateOfBirth != null
                    ? DateFormats.getFilteredDate(
                        individualDetails!.dateOfBirth.toString())
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.gender),
                description: individualDetails != null &&
                        individualDetails?.gender != null
                    ? t.translate(individualDetails!.gender.toString())
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.socialCategory),
                description: individualDetails != null &&
                        individualDetails?.socialCategory != null
                    ? t.translate(individualDetails!.socialCategory.toString())
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.mobileNumber),
                description: individualDetails != null &&
                        individualDetails?.mobileNumber != null
                    ? '+91 - ${individualDetails!.mobileNumber.toString()}'
                    : 'NA'),
            if (skillDetails != null &&
                skillDetails?.individualSkills != null &&
                skillDetails!.individualSkills!.isNotEmpty)
              for (var item in skillDetails!.individualSkills!)
                CustomDetailsCard(Column(
                  children: [
                    getItemWidget(context,
                        title: t.translate(i18.wageSeeker.skillCategory),
                        description: t.translate(item.level.toString())),
                    getItemWidget(context,
                        title: t.translate(i18.wageSeeker.skill),
                        description: t.translate(item.type.toString())),
                  ],
                )),
            kIsWeb && FilePickerData.bytes != null
                ? Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Text(
                        t.translate(i18.common.photoGraph),
                        style: const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                        textAlign: TextAlign.left,
                      ),
                      const SizedBox(
                        height: 10,
                      ),
                      Align(
                        alignment: Alignment.center,
                        child: Image.memory(
                          FilePickerData.bytes!,
                          fit: BoxFit.cover,
                          width: MediaQuery.of(context).size.width / 2,
                          height: MediaQuery.of(context).size.width / 2,
                        ),
                      ),
                    ],
                  )
                : !kIsWeb && FilePickerData.imageFile != null
                    ? Column(
                        mainAxisAlignment: MainAxisAlignment.start,
                        children: [
                          Text(
                            t.translate(i18.common.photoGraph),
                            style: const TextStyle(
                                fontSize: 16, fontWeight: FontWeight.w700),
                            textAlign: TextAlign.left,
                          ),
                          const SizedBox(
                            height: 10,
                          ),
                          Align(
                            alignment: Alignment.center,
                            child: Image.file(
                              FilePickerData.imageFile!,
                              fit: BoxFit.cover,
                              width: MediaQuery.of(context).size.width / 2,
                              height: MediaQuery.of(context).size.width / 2,
                            ),
                          ),
                        ],
                      )
                    : Container()
          ],
        )),
        DigitCard(
            child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  t.translate(i18.common.locationDetails),
                  style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                      ?.apply(color: const DigitColors().black),
                  textAlign: TextAlign.left,
                ),
                GestureDetector(
                  child: Icon(
                    Icons.edit,
                    color: const DigitColors().burningOrange,
                  ),
                  onTap: () {
                    widget.onPressed(2);
                  },
                )
              ],
            ),
            getItemWidget(context,
                title: t.translate(i18.common.pinCode),
                description:
                    locationDetails != null && locationDetails?.pinCode != null
                        ? locationDetails!.pinCode.toString()
                        : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.city),
                description: locationDetails != null &&
                        locationDetails?.city != null
                    ? t.translate(
                        'PG_${locationDetails!.city.toString().toUpperCase()}')
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.ward),
                description: locationDetails != null &&
                        locationDetails?.ward != null
                    ? t.translate(
                        '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase()}_ADMIN_${locationDetails!.ward.toString()}')
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.locality),
                description: locationDetails != null &&
                        locationDetails?.locality != null
                    ? t.translate(
                        '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase()}_ADMIN_${locationDetails!.locality.toString()}')
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.streetName),
                description: locationDetails != null &&
                        locationDetails?.streetName != null &&
                        locationDetails!.streetName.toString().isNotEmpty
                    ? locationDetails!.streetName.toString()
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.doorNo),
                description: locationDetails != null &&
                        locationDetails?.doorNo != null &&
                        locationDetails!.doorNo.toString().isNotEmpty
                    ? t.translate(locationDetails!.doorNo.toString())
                    : 'NA'),
          ],
        )),
        DigitCard(
            child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(
                  t.translate(i18.common.financialDetails),
                  style: DigitTheme.instance.mobileTheme.textTheme.displayMedium
                      ?.apply(color: const DigitColors().black),
                  textAlign: TextAlign.left,
                ),
                GestureDetector(
                  child: Icon(
                    Icons.edit,
                    color: const DigitColors().burningOrange,
                  ),
                  onTap: () {
                    widget.onPressed(3);
                  },
                )
              ],
            ),
            getItemWidget(context,
                title: t.translate(i18.common.accountHolderName),
                description: financialDetails != null &&
                        financialDetails?.accountHolderName != null
                    ? financialDetails!.accountHolderName.toString()
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.accountNo),
                description: financialDetails != null &&
                        financialDetails?.accountNumber != null
                    ? financialDetails!.accountNumber.toString()
                    : 'NA'),
            getItemWidget(context,
                title: t.translate(i18.common.ifscCode),
                description: financialDetails != null &&
                        financialDetails?.ifscCode != null
                    ? financialDetails!.ifscCode.toString()
                    : 'NA',
                subDescription: financialDetails != null &&
                        financialDetails?.bankName != null
                    ? '( ${financialDetails?.bankName} )'
                    : ''),
            const SizedBox(
              height: 10,
              width: 0,
            ),
            BlocListener<WageSeekerCreateBloc, WageSeekerCreateState>(
              listener: (context, individualState) {
                individualState.maybeWhen(
                    orElse: () => false,
                    loading: () => Loaders.circularLoader(context),
                    loaded: (SingleIndividualModel? individualListModel) {
                      context.read<WageSeekerBankCreateBloc>().add(
                            CreateBankWageSeekerEvent(
                                tenantId:
                                    individualListModel?.Individual?.tenantId,
                                accountHolderName:
                                    financialDetails?.accountHolderName,
                                accountNo: financialDetails?.accountNumber,
                                accountType: financialDetails?.accountType,
                                ifscCode: financialDetails?.ifscCode,
                                referenceId:
                                    individualListModel?.Individual?.id,
                                indId: individualListModel
                                    ?.Individual?.individualId,
                                bankName:
                                    '${financialDetails?.bankName}, ${financialDetails?.branchName}'),
                          );
                    },
                    error: (String? error) => Notifiers.getToastMessage(
                        context, error.toString(), 'ERROR'));
              },
              child: BlocListener<WageSeekerBankCreateBloc,
                  WageSeekerBankCreateState>(
                listener: (context, individualState) {
                  individualState.maybeWhen(
                      orElse: () => false,
                      loading: () => Loaders.circularLoader(context),
                      loaded: (BankingDetailsModel? bankingDetails,
                          BankAccounts? bankAccountDetails) {
                        var localizationText =
                            '${t.translate(i18.wageSeeker.wageSeekerSuccessSubText)}';
                        localizationText = localizationText.replaceFirst(
                            '{individualID}', bankAccountDetails?.indID ?? '');
                        context.router.popAndPush(SuccessResponseRoute(
                            header:
                                t.translate(i18.wageSeeker.createIndSuccess),
                            subTitle: localizationText,
                            backButton: true,
                            callBack: () =>
                                context.router.push(const HomeRoute()),
                            buttonLabel: t.translate(
                              i18.common.backToHome,
                            )));
                      },
                      error: (String? error) => Notifiers.getToastMessage(
                          context, error.toString(), 'ERROR'));
                },
                child: DigitElevatedButton(
                  onPressed: () {
                    context.read<WageSeekerCreateBloc>().add(
                          CreateWageSeekerEvent(
                              individualDetails: individualDetails,
                              skillDetails: skillDetails,
                              locationDetails: locationDetails,
                              financialDetails: financialDetails),
                        );
                  },
                  child: Center(child: Text(t.translate(i18.common.submit))),
                ),
              ),
            ),
          ],
        )),
      ],
    );
  }

  static getItemWidget(BuildContext context,
      {String title = '',
      String description = '',
      String subtitle = '',
      String subDescription = ''}) {
    return Container(
        padding: const EdgeInsets.all(8.0),
        child: (Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
                padding: const EdgeInsets.only(right: 16),
                width: MediaQuery.of(context).size.width / 3.5,
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        title,
                        style: const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                        textAlign: TextAlign.start,
                      ),
                      subtitle.trim.toString() != ''
                          ? Text(
                              subtitle,
                              style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.w400,
                                  color: Theme.of(context).primaryColorLight),
                            )
                          : const Text('')
                    ])),
            Column(
              children: [
                SizedBox(
                    width: MediaQuery.of(context).size.width / 2,
                    child: Text(
                      description,
                      style: const TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w400,
                      ),
                      textAlign: TextAlign.left,
                    )),
                subDescription.isNotEmpty
                    ? SizedBox(
                        width: MediaQuery.of(context).size.width / 2,
                        child: Text(
                          subDescription,
                          style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.w400,
                              color: const DigitColors().cloudGray),
                          textAlign: TextAlign.left,
                        ))
                    : Container()
              ],
            )
          ],
        )));
  }
}
