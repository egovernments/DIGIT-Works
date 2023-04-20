import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/financial_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/individual_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/location_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/skills.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/summary_details.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/utils/constants.dart';
import 'package:works_shg_app/utils/global_variables.dart';
import 'package:works_shg_app/utils/notifiers.dart';
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/molecules/digit_stepper.dart';

import '../../blocs/localization/app_localization.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_location_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_mdms_bloc.dart';
import '../../blocs/wage_seeker_registration/wage_seeker_registration_bloc.dart';
import '../../models/mdms/location_mdms.dart';
import '../../models/mdms/wage_seeker_mdms.dart';
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
  }

  var t = AppLocalizations.of(scaffoldMessengerKey.currentContext!);
  int currentStep = 0;
  List<int> stepNumbers = [1, 2, 3, 4, 5];
  List<String> stepHeaders = [
    AppLocalizations.of(scaffoldMessengerKey.currentContext!)
        .translate(i18.attendanceMgmt.individualDetails),
    AppLocalizations.of(scaffoldMessengerKey.currentContext!)
        .translate(i18.attendanceMgmt.skillDetails),
    AppLocalizations.of(scaffoldMessengerKey.currentContext!)
        .translate(i18.common.locationDetails),
    AppLocalizations.of(scaffoldMessengerKey.currentContext!)
        .translate(i18.common.financialDetails),
    AppLocalizations.of(scaffoldMessengerKey.currentContext!)
        .translate(i18.wageSeeker.summaryDetails)
  ];

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
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: const DrawerWrapper(Drawer(
            child: SideBar(
          module: 'rainmaker-common,rainmaker-attendencemgmt',
        ))),
        body: SingleChildScrollView(
          child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Back(),
                DigitStepper(
                  stepColor: const DigitColors().cloudGray,
                  activeStepColor: const DigitColors().burningOrange,
                  numberStyle: TextStyle(color: const DigitColors().white),
                  lineDotRadius: 2.0,
                  lineLength: 50,
                  activeStepBorderPadding: 0.0,
                  lineColor: const DigitColors().regalBlue,
                  activeStepBorderColor: const DigitColors().burningOrange,
                  stepReachedAnimationEffect: Curves.ease,
                  stepRadius: 12.0,
                  numbers: stepNumbers,
                  headers: stepHeaders,
                  activeStep: currentStep,
                  enableNextPreviousButtons: false,
                  onStepReached: (index) {
                    setState(() {
                      currentStep = index;
                    });
                  },
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
                              loaded: (Location? location) {
                                return getFormConfig(wageSeekerMDMS, location);
                              });
                        });
                      },
                      error: (String? error) => Notifiers.getToastMessage(
                          context, error.toString(), 'ERROR'));
                })
              ]),
        ));
  }

  Widget getFormConfig(WageSeekerMDMS? wageSeekerMDMS, Location? location) {
    switch (currentStep) {
      case 0:
        return IndividualDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 1:
        return SkillDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 2:
        return LocationDetailsPage(
          onPressed: updateCurrentStep,
          city: 'pg.citya',
          location: location,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 3:
        return FinancialDetailsPage(
          onPressed: updateCurrentStep,
          wageSeekerMDMS: wageSeekerMDMS,
        );
      case 4:
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
