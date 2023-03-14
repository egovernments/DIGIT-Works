import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/financial_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/individual_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/location_details.dart';
import 'package:works_shg_app/pages/wage_seeker_registration/organization_details.dart';
import 'package:works_shg_app/widgets/Back.dart';
import 'package:works_shg_app/widgets/molecules/digit_stepper.dart';

import '../../widgets/SideBar.dart';
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
  int currentStep = 0;
  List<int> stepNumbers = [1, 2, 3, 4, 5];
  List<String> stepHeaders = [
    'Individual Details',
    'LocationDetails',
    'Organization Details',
    'Financial Details',
    'Summary Details'
  ];

  void updateCurrentStep() {
    if (currentStep <= stepNumbers.length - 1) {
      setState(() {
        currentStep += 1;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    var genderController = TextEditingController();
    return Scaffold(
        appBar: AppBar(),
        drawer: DrawerWrapper(const Drawer(
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
                getFormConfig()
              ]),
        ));
  }

  Widget getFormConfig() {
    switch (currentStep) {
      case 0:
        return IndividualDetails(
          onPressed: updateCurrentStep,
        );
      case 1:
        return LocationDetails(
          onPressed: updateCurrentStep,
        );
      case 2:
        return OrganizationDetails(
          onPressed: updateCurrentStep,
        );
      case 3:
        return FinancialDetails(
          onPressed: updateCurrentStep,
        );
      default:
        return IndividualDetails(
          onPressed: updateCurrentStep,
        );
    }
  }
}
