import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/wage_seeker_registration/wage_seeker_locality_bloc.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/financial_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/individual_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/location_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/summary_details.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/localization_constants/i18_key_constants.dart'
    as i18;
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/molecules/digit_stepper.dart';

import '../../blocs/app_initilization/app_initilization.dart';
import '../../blocs/localization/app_localization.dart';
import '../../blocs/localization/localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_mdms_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/location_mdms.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
import '../../utils/common_methods.dart';
import '../../utils/models/file_picker_data.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/drawer_wrapper.dart';

class RegisterIndividualPage extends StatefulWidget {
  const RegisterIndividualPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return RegisterIndividualPageState();
  }

  static final GlobalKey<RegisterIndividualPageState> pageKey =
      GlobalKey<RegisterIndividualPageState>();
}

class RegisterIndividualPageState extends State<RegisterIndividualPage> {
  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) => afterViewBuild());
    super.initState();
  }

  afterViewBuild() {
    context.read<WageSeekerBloc>().add(
          const WageSeekerClearEvent(),
        );
    FilePickerData.imageFile = null;
    FilePickerData.bytes = null;
    context.read<WageSeekerMDMSBloc>().add(
          const WageSeekerMDMSEvent(),
        );
    context.read<WageSeekerLocationBloc>().add(
          LocationEventWageSeeker(
              tenantId: GlobalVariables
                  .organisationListModel!.organisations!.first.tenantId
                  .toString()),
        );
    context.read<WageSeekerLocalityBloc>().add(
      LocalityEventWageSeeker(
          tenantId: GlobalVariables
              .organisationListModel!.organisations!.first.tenantId
              .toString()),
    );
  }

  int currentStep = 0;
  List<int> stepNumbers = [1, 2, 3, 4];

  void updateCurrentStep() {
    if (currentStep <= stepNumbers.length - 1) {
      setState(() {
        currentStep += 1;
      });
    }
  }

  void jumpToStep(int index) {
    setState(() {
      currentStep = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    List<String> stepHeaders = [
      i18.attendanceMgmt.individualDetails,
      // AppLocalizations.of(scaffoldMessengerKey.currentContext!)
      //     .translate(i18.attendanceMgmt.skillDetails),
      i18.common.locationDetails,
      i18.common.financialDetails,
      i18.wageSeeker.summaryDetails
    ];
    return BlocBuilder<LocalizationBloc, LocalizationState>(
        builder: (context, localState) {
      return Scaffold(
          appBar: AppBar(
            titleSpacing: 0,
            title: const AppBarLogo(),
          ),
          drawer: DrawerWrapper(Drawer(
              child: SideBar(
            module: CommonMethods.getLocaleModules(),
          ))),
          body: SingleChildScrollView(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Back(),
                  const SizedBox(
                    height: 8.0,
                  ),
                  BlocBuilder<AppInitializationBloc, AppInitializationState>(
                      builder: (context, initState) {
                    return DigitStepper(
                      stepColor: const DigitColors().cloudGray,
                      activeStepColor: const DigitColors().burningOrange,
                      numberStyle: TextStyle(color: const DigitColors().white),
                      lineDotRadius: 2.0,
                      lineLength: MediaQuery.of(context).size.width / 10,
                      activeStepBorderPadding: 0.0,
                      lineColor: const DigitColors().regalBlue,
                      activeStepBorderColor: const DigitColors().burningOrange,
                      stepReachedAnimationEffect: Curves.ease,
                      stepRadius: 12.0,
                      numbers: stepNumbers,
                      headers: stepHeaders
                          .map((e) => t.translate(e).toString())
                          .toList(),
                      activeStep: currentStep,
                      // enableNextPreviousButtons: false,
                      onStepReached: (index) {
                        setState(() {
                          currentStep = index;
                        });
                      },
                    );
                  }),
                  const SizedBox(
                    height: 16.0,
                  ),
                  BlocBuilder<WageSeekerMDMSBloc, WageSeekerMDMSState>(
                      builder: (context, mdmsState) {
                    return mdmsState.maybeWhen(
                        orElse: () => Container(),
                        loading: () => Loaders.circularLoader(context),
                        loaded: (WageSeekerMDMS? wageSeekerMDMS) {
                          return BlocBuilder<WageSeekerLocationBloc,
                                  WageSeekerLocationState>(
                              builder: (context, locationState) {
                            return locationState.maybeWhen(
                                orElse: () => Container(),
                                loaded: (Location? ward) {
                                  return BlocBuilder<WageSeekerLocalityBloc,
                                      WageSeekerLocalityState>(
                                      builder: (context, localityState) {
                                        return localityState.maybeWhen(orElse:  () => Container(),
                                          loaded: (Location? locality) {
                                            return getFormConfig(
                                                wageSeekerMDMS, ward,locality);
                                          }
                                        );

                                    }
                                  );
                                });
                          });
                        },
                        error: (String? error) => Notifiers.getToastMessage(
                            context, error.toString(), 'ERROR'));
                  }),
                  const SizedBox(
                    height: 16.0,
                  ),
                  const Align(
                    alignment: Alignment.bottomCenter,
                    child: PoweredByDigit(),
                  )
                ]),
          ));
    });
  }

  Widget getFormConfig(WageSeekerMDMS? wageSeekerMDMS, Location? ward,Location? locality) {
    switch (currentStep) {
      case 0:
        return IndividualDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      // case 1:
      //   return SkillDetailsPage(
      //     onPressed: updateCurrentStep,
      //     wageSeekerMDMS: wageSeekerMDMS,
      //   );
      case 1:
        return LocationDetailsPage(
          onPressed: updateCurrentStep,
          city: GlobalVariables
              .organisationListModel?.organisations?.first.tenantId,
          ward: ward,
          locality: locality,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 2:
        return FinancialDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 3:
        return SummaryDetailsPage(
          onPressed: jumpToStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      default:
        return IndividualDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
    }
  }
}
