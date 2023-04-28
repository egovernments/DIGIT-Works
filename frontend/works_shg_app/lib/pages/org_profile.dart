import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/localization/app_localization.dart';
import 'package:works_shg_app/utils/date_formats.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../blocs/organisation/org_financial_bloc.dart';
import '../blocs/organisation/org_search_bloc.dart';
import '../models/organisation/organisation_model.dart';
import '../models/wage_seeker/banking_details_model.dart';
import '../utils/common_methods.dart';
import '../utils/global_variables.dart';
import '../widgets/Back.dart';
import '../widgets/SideBar.dart';
import '../widgets/WorkDetailsCard.dart';
import '../widgets/atoms/app_bar_logo.dart';
import '../widgets/drawer_wrapper.dart';
import '../widgets/loaders.dart' as shg_loader;

class ORGProfilePage extends StatefulWidget {
  const ORGProfilePage({Key? key}) : super(key: key);

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
          SearchORGEvent(GlobalVariables.userRequestModel!['mobileNumber']),
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
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: DrawerWrapper(
            Drawer(child: SideBar(module: CommonMethods.getLocaleModules()))),
        body: SingleChildScrollView(
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          BlocConsumer<ORGSearchBloc, ORGSearchState>(
            listener: (context, state) {
              state.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (OrganisationListModel? organisationListModel) {
                    if (organisationListModel?.organisations != null) {
                      orgDetails = organisationListModel!.organisations!
                          .map((e) => {
                                i18.common.orgId: e.orgNumber,
                                i18.common.orgName: e.name,
                                i18.common.registeredDept:
                                    e.additionalDetails?.registeredByDept ??
                                        'NA',
                                i18.common.deptRegNo:
                                    e.additionalDetails?.deptRegistrationNum ??
                                        'NA',
                                i18.common.dateOfIncorporation:
                                    DateFormats.timeStampToDate(
                                        e.dateOfIncorporation),
                                i18.common.status:
                                    t.translate(e.applicationStatus.toString())
                              })
                          .toList();
                      functionalDetails = organisationListModel.organisations!
                          .map((e) => {
                                i18.common.orgType: t.translate(
                                        'COMMON_MASTERS_ORG_${e.functions?.first.type.toString().split('.').first.toUpperCase() ?? 'NA'}') ??
                                    'NA',
                                i18.common.orgSubType: t.translate(
                                        'COMMON_MASTERS_SUBORG_${e.functions?.first.type.toString().split('.').last.toUpperCase() ?? 'NA'}') ??
                                    'NA',
                                i18.common.funcCat: e
                                            .functions?.first.category !=
                                        null
                                    ? '${t.translate('COMMON_MASTERS_ORG_${e.functions?.first.category?.split('.').first.toString()}')}, ${t.translate('COMMON_MASTERS_FUNCATEGORY_${e.functions?.first.category?.split('.').last.toString()}')}'
                                    : t.translate('NA'),
                                i18.common.classOrRank: t.translate(
                                    'COMMON_MASTERS_CLASS_${e.functions?.first.orgClass ?? 'NA'}'),
                                i18.common.validFrom:
                                    DateFormats.timeStampToDate(
                                        e.functions?.first.validFrom),
                                i18.common.validTo: DateFormats.timeStampToDate(
                                    e.functions?.first.validTo)
                              })
                          .toList();
                      contactDetails = organisationListModel.organisations!
                          .map((e) => {
                                i18.common.contactPersonName:
                                    e.contactDetails?.first.contactName ?? 'NA',
                                i18.common.mobileNumber: e.contactDetails?.first
                                        .contactMobileNumber ??
                                    'NA',
                                i18.common.email:
                                    e.contactDetails?.first.contactEmail ??
                                        'NA',
                              })
                          .toList();
                      locationDetails = organisationListModel.organisations!
                          .map((e) => {
                                i18.common.pinCode:
                                    e.orgAddress?.first.pincode ?? 'NA',
                                i18.common.city: t.translate(
                                        'TENANT_TENANTS_${e.tenantId?.toUpperCase().replaceAll('.', '_')}') ??
                                    'NA',
                                i18.common.ward: t.translate(
                                        '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.orgAddress?.first.boundaryCode ?? 'NA'}') ??
                                    'NA',
                                i18.common.locality: t.translate(
                                    '${GlobalVariables.organisationListModel?.organisations?.first.tenantId?.toUpperCase().replaceAll('.', '_')}_ADMIN_${e.additionalDetails?.locality}'),
                                i18.common.streetName:
                                    e.orgAddress?.first.street ?? 'NA',
                                i18.common.doorNo:
                                    e.orgAddress?.first.doorNo ?? 'NA',
                              })
                          .toList();
                    }
                  });
            },
            builder: (context, orgState) {
              return orgState.maybeWhen(
                  orElse: () => Container(),
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  initial: () => Container(),
                  loaded: (OrganisationListModel? organisationListModel) {
                    return organisationListModel?.organisations != null
                        ? Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                                Back(backLabel: t.translate(i18.common.back)),
                                Padding(
                                    padding: const EdgeInsets.all(8.0),
                                    child: Text(
                                        '${t.translate(i18.common.orgProfile)}',
                                        style: DigitTheme.instance.mobileTheme
                                            .textTheme.displayMedium,
                                        textAlign: TextAlign.left)),
                                orgDetails.isNotEmpty
                                    ? WorkDetailsCard(
                                        orgDetails,
                                        orgProfile: true,
                                      )
                                    : Container(),
                                functionalDetails.isNotEmpty
                                    ? WorkDetailsCard(
                                        functionalDetails,
                                        cardTitle: t.translate(
                                            i18.common.functionalDetails),
                                        orgProfile: true,
                                      )
                                    : Container(),
                                contactDetails.isNotEmpty
                                    ? WorkDetailsCard(
                                        contactDetails,
                                        cardTitle: t.translate(
                                            i18.common.contactDetails),
                                        orgProfile: true,
                                      )
                                    : Container(),
                                locationDetails.isNotEmpty
                                    ? WorkDetailsCard(
                                        locationDetails,
                                        cardTitle: t.translate(
                                            i18.common.locationDetails),
                                        orgProfile: true,
                                      )
                                    : Container(),
                              ])
                        : Container();
                  });
            },
          ),
          BlocConsumer<ORGFinanceBloc, ORGFinanceState>(
            listener: (context, financeState) {
              financeState.maybeWhen(
                  orElse: () => false,
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (BankingDetailsModel? bankingDetailsModel) {
                    if (bankingDetailsModel!.bankAccounts != null) {
                      financialDetails = bankingDetailsModel.bankAccounts!
                          .map((e) => {
                                i18.common.accountHolderName: e
                                        .bankAccountDetails
                                        ?.first
                                        .accountHolderName ??
                                    'NA',
                                i18.common.accountNo:
                                    e.bankAccountDetails?.first.accountNumber ??
                                        'NA',
                                i18.common.ifscCode: e.bankAccountDetails?.first
                                        .bankBranchIdentifier?.code ??
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
                                i18.common.effectiveTo: (e.bankAccountDetails
                                                ?.first.isPrimary ==
                                            true &&
                                        e.bankAccountDetails?.first.isActive ==
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
                  loading: () => shg_loader.Loaders.circularLoader(context),
                  loaded: (BankingDetailsModel? bankingDetailsModel) {
                    return bankingDetailsModel?.bankAccounts != null
                        ? financialDetails.isNotEmpty
                            ? WorkDetailsCard(
                                financialDetails,
                                cardTitle:
                                    t.translate(i18.common.financialDetails),
                                orgProfile: true,
                              )
                            : Container()
                        : Container();
                  });
            },
          )
        ])));
  }
}
