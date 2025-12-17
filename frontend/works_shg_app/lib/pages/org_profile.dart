// import 'package:digit_components/digit_components.dart';
import 'package:collection/collection.dart';
import 'package:digit_ui_components/theme/ComponentTheme/back_button_theme.dart';
import 'package:digit_ui_components/theme/digit_extended_theme.dart';
import 'package:digit_ui_components/widgets/atoms/digit_back_button.dart';
import 'package:digit_ui_components/widgets/atoms/text_block.dart';
import 'package:digit_ui_components/widgets/powered_by_digit.dart';

import 'package:easy_stepper/easy_stepper.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/string_extension.dart';

import '../blocs/localization/localization.dart';
import '../blocs/organisation/org_financial_bloc.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../models/organisation/organisation_model.dart';
import '../models/wage_seeker/banking_details_model.dart';
import '../utils/constants.dart';
import '../utils/global_variables.dart';
import '../widgets/work_details_card.dart';
import '../widgets/loaders.dart' as shg_loader;

@RoutePage()
class ORGProfilePage extends StatefulWidget {
  const ORGProfilePage({super.key});

  @override
  State<StatefulWidget> createState() {
    return _ORGProfilePage();
  }
}

class _ORGProfilePage extends State<ORGProfilePage> {
  List<Map<String, dynamic>> orgDetails = [];
  List<Map<String, dynamic>> functionalDetails = [];
  List<Map<String, dynamic>> contactDetails = [];
  List<Map<String, dynamic>> locationDetails = [];
  List<Map<String, dynamic>> financialDetails = [];
  String branchName = '';

  @override
  void initState() {
    super.initState();
    context.read<ORGSearchBloc>().add(
          SearchORGEvent(
              GlobalVariables.userRequestModel![Constants.userMobileNumberKey]),
        );
    context.read<ORGFinanceBloc>().add(
          FinanceORGEvent(
              GlobalVariables.organisationListModel!.organisations!.first.id
                  .toString(),
              GlobalVariables
                  .organisationListModel!.organisations!.first.tenantId
                  .toString()),
        );
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        backgroundColor: Theme.of(context).colorTheme.generic.background,
        // appBar: customAppBar(),
        // drawer: const MySideBar(),
        body: BlocBuilder<LocalizationBloc, LocalizationState>(
            builder: (context, localState) {
          return SingleChildScrollView(
              child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                BlocConsumer<ORGSearchBloc, ORGSearchState>(
                  listener: (context, state) {
                    state.maybeWhen(
                        orElse: () => false,
                        loading: () =>
                            shg_loader.Loaders.circularLoader(context),
                        loaded: (OrganisationListModel? organisationListModel) {
                          if (organisationListModel?.organisations != null) {
                            orgDetails = organisationListModel!.organisations!
                                .map((e) => {
                                      i18.common.orgId: e.orgNumber,
                                      i18.common.orgName: e.name,
                                      i18.common.registeredDept: e
                                                  .additionalDetails
                                                  ?.registeredByDept !=
                                              null
                                          ? (e.additionalDetails
                                                          ?.registeredByDept ??
                                                      '')
                                                  .isNotEmpty
                                              ? e.additionalDetails
                                                  ?.registeredByDept
                                              : i18.common.noValue
                                          : i18.common.noValue,
                                      i18.common.deptRegNo: e.additionalDetails
                                                  ?.deptRegistrationNum !=
                                              null
                                          ? (e.additionalDetails
                                                          ?.deptRegistrationNum ??
                                                      '')
                                                  .isNotEmpty
                                              ? e.additionalDetails
                                                  ?.deptRegistrationNum
                                              : i18.common.noValue
                                          : i18.common.noValue,
                                      i18.common.dateOfIncorporation:
                                          DateFormats.timeStampToDate(
                                              e.dateOfIncorporation),
                                      i18.common.status:
                                          e.applicationStatus.toString(),
                                      Constants.activeInboxStatus:
                                          e.applicationStatus !=
                                                  Constants.active
                                              ? 'false'
                                              : 'true'
                                    })
                                .toList();
                            functionalDetails =
                                organisationListModel.organisations!
                                    .map((e) => {
                                          i18.common.orgType:
                                              'COMMON_MASTERS_ORG_${e.functions?.first.type.toString().split('.').first.toUpperCase() ?? 'NA'}',
                                          i18.common.orgSubType:
                                              'COMMON_MASTERS_SUBORG_${e.functions?.first.type.toString().split('.').last.toUpperCase() ?? 'NA'}',
                                          i18.common.funcCat: e.functions?.first
                                                      .category !=
                                                  null
                                              ? '${t.translate('COMMON_MASTERS_ORG_${e.functions?.first.category?.split('.').first.toString()}')}, ${t.translate('COMMON_MASTERS_FUNCATEGORY_${e.functions?.first.category?.split('.').last.toString()}')}'
                                              : i18.common.noValue,
                                          i18.common.classOrRank:
                                              'COMMON_MASTERS_CLASS_${e.functions?.first.orgClass ?? 'NA'}',
                                          i18.common.validFrom:
                                              DateFormats.timeStampToDate(
                                                  e.functions?.first.validFrom),
                                          i18.common.validTo:
                                              DateFormats.timeStampToDate(
                                                  e.functions?.first.validTo)
                                        })
                                    .toList();
                            contactDetails =
                                organisationListModel.organisations!
                                    .map((e) => {
                                          i18.common.contactPersonName: e
                                                  .contactDetails
                                                  ?.first
                                                  .contactName ??
                                              i18.common.noValue,
                                          i18.common.mobileNumber: e
                                                  .contactDetails
                                                  ?.first
                                                  .contactMobileNumber ??
                                              i18.common.noValue,
                                          i18.common.email: e.contactDetails
                                                      ?.first.contactEmail !=
                                                  null
                                              ? (e.contactDetails?.first
                                                              .contactEmail ??
                                                          '')
                                                      .isNotEmpty
                                                  ? e.contactDetails?.first
                                                      .contactEmail
                                                  : i18.common.noValue
                                              : i18.common.noValue,
                                        })
                                    .toList();
                            locationDetails = organisationListModel
                                .organisations!
                                .mapIndexed((index,e) => {
                                      i18.common.city:
                                          'TENANT_TENANTS_${e.tenantId.toUpperCase().replaceAll('.', '_')}',
                                      i18.common.ward:
                                          '${GlobalVariables.organisationListModel?.organisations?.first.tenantId.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.orgAddress?.first.boundaryCode ?? 'NA'}',
                                      i18.common.locality:organisationListModel.organisations?[index].additionalDetails?.isLocalityMasked!=null&&organisationListModel.organisations?[index].additionalDetails?.isLocalityMasked==true?
                                         t.translate( '${GlobalVariables.organisationListModel?.organisations?.first.tenantId.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.additionalDetails?.locality}').toString().mask():'${GlobalVariables.organisationListModel?.organisations?.first.tenantId.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.additionalDetails?.locality}',
                                      i18.common.streetName:
                                          e.orgAddress?.first.street != null
                                              ? (e.orgAddress?.first.street ??
                                                          '')
                                                      .isNotEmpty
                                                  ? e.orgAddress?.first.street
                                                  : i18.common.noValue
                                              : i18.common.noValue,
                                      i18.common.doorNo:
                                          e.orgAddress?.first.doorNo != null
                                              ? (e.orgAddress?.first.doorNo ??
                                                          '')
                                                      .isNotEmpty
                                                  ? e.orgAddress?.first.doorNo
                                                  : i18.common.noValue
                                              : i18.common.noValue,
                                    })
                                .toList();
                          }
                        });
                  },
                  builder: (context, orgState) {
                    return orgState.maybeWhen(
                        orElse: () => Container(),
                        loading: () =>
                            shg_loader.Loaders.circularLoader(context),
                        initial: () => Container(),
                        loaded: (OrganisationListModel? organisationListModel) {
                          return organisationListModel?.organisations != null
                              ? Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                      Padding(
                                        padding: EdgeInsets.symmetric(
                                          horizontal: Theme.of(context)
                                              .spacerTheme
                                              .spacer4,
                                          vertical: Theme.of(context)
                                              .spacerTheme
                                              .spacer4,
                                        ),
                                        child: Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.start,
                                          children: [
                                            BackNavigationButton(
                                              backNavigationButtonThemeData:
                                                  const BackNavigationButtonThemeData()
                                                      .copyWith(
                                                          context: context,
                                                          backButtonIcon: Icon(
                                                            Icons
                                                                .arrow_circle_left_outlined,
                                                            size: MediaQuery.of(
                                                                            context)
                                                                        .size
                                                                        .width <
                                                                    500
                                                                ? Theme.of(
                                                                        context)
                                                                    .spacerTheme
                                                                    .spacer5
                                                                : Theme.of(
                                                                        context)
                                                                    .spacerTheme
                                                                    .spacer6,
                                                            color: Theme.of(
                                                                    context)
                                                                .colorTheme
                                                                .primary
                                                                .primary2,
                                                          )),
                                              backButtonText:
                                                  t.translate(i18.common.back),
                                              handleBack: () {
                                                Navigator.pop(context);
                                              },
                                            ),
                                          ],
                                        ),
                                      ),
                                      Padding(
                                          padding: EdgeInsets.only(
                                              top: 0.0,
                                              left: Theme.of(context)
                                                  .spacerTheme
                                                  .spacer4,
                                              bottom: Theme.of(context)
                                                  .spacerTheme
                                                  .spacer4),
                                          child: DigitTextBlock(
                                            heading:
                                                '${t.translate(i18.common.orgProfile)}',
                                          )),
                                      orgDetails.isNotEmpty
                                          ? WorkDetailsCard(
                                              orgDetails,
                                              orgProfile: true,
                                            )
                                          : const SizedBox.shrink(),
                                      functionalDetails.isNotEmpty
                                          ? WorkDetailsCard(
                                              functionalDetails,
                                              cardTitle: t.translate(
                                                  i18.common.functionalDetails),
                                              orgProfile: true,
                                            )
                                          : const SizedBox.shrink(),
                                      contactDetails.isNotEmpty
                                          ? WorkDetailsCard(
                                              contactDetails,
                                              cardTitle: t.translate(
                                                  i18.common.contactDetails),
                                              orgProfile: true,
                                            )
                                          : const SizedBox.shrink(),
                                      locationDetails.isNotEmpty
                                          ? WorkDetailsCard(
                                              locationDetails,
                                              cardTitle: t.translate(
                                                  i18.common.locationDetails),
                                              orgProfile: true,
                                            )
                                          : const SizedBox.shrink(),
                                    ])
                              : const SizedBox.shrink();
                        });
                  },
                ),
                BlocConsumer<ORGFinanceBloc, ORGFinanceState>(
                  listener: (context, financeState) {
                    financeState.maybeWhen(
                        orElse: () => false,
                        loading: () =>
                            shg_loader.Loaders.circularLoader(context),
                        loaded: (BankingDetailsModel? bankingDetailsModel) {
                          if (bankingDetailsModel!.bankAccounts != null) {
                            financialDetails = bankingDetailsModel.bankAccounts!
                                .map((e) => {
                                      i18.common.accountHolderName: e
                                              .bankAccountDetails
                                              ?.first
                                              .accountHolderName ??
                                          'NA',
                                      i18.common.accountNo: e.bankAccountDetails
                                              ?.first.accountNumber ??
                                          'NA',
                                      i18.common.ifscCode: e
                                              .bankAccountDetails
                                              ?.first
                                              .bankBranchIdentifier
                                              ?.code ??
                                          'NA',
                                      i18.common.branchName: e
                                              .bankAccountDetails
                                              ?.first
                                              .bankBranchIdentifier
                                              ?.additionalDetails
                                              ?.ifsccode ??
                                          'NA',
                                      i18.common.effectiveFrom:
                                          DateFormats.timeStampToDate(
                                              e.auditDetails?.createdTime),
                                      i18.common.effectiveTo: (e
                                                      .bankAccountDetails
                                                      ?.first
                                                      .isPrimary ==
                                                  true &&
                                              e.bankAccountDetails?.first
                                                      .isActive ==
                                                  true)
                                          ? t.translate('NA')
                                          : DateFormats.timeStampToDate(
                                              e.auditDetails?.lastModifiedTime)
                                    })
                                .toList();
                          }
                        });
                  },
                  builder: (context, finance) {
                    return finance.maybeWhen(
                        orElse: () => Container(),
                        loading: () =>
                            shg_loader.Loaders.circularLoader(context),
                        loaded: (BankingDetailsModel? bankingDetailsModel) {
                          return bankingDetailsModel?.bankAccounts != null
                              ? financialDetails.isNotEmpty
                                  ? WorkDetailsCard(
                                      financialDetails,
                                      cardTitle: t.translate(
                                          i18.common.financialDetails),
                                      orgProfile: true,
                                    )
                                  : Container()
                              : Container();
                        });
                  },
                ),
             
                Padding(
                   padding:  EdgeInsets.all(Theme.of(context).spacerTheme.spacer4),
                  child: const Align(
                    alignment: Alignment.bottomCenter,
                    child: PoweredByDigit(
                      version: Constants.appVersion,
                    ),
                  ),
                ),
              ]));
        }));
  }
}
