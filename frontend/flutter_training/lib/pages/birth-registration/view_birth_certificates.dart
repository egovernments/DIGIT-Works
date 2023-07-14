import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_training/blocs/localization/app_localization.dart';
import 'package:flutter_training/utils/common_widgets.dart';
import 'package:flutter_training/utils/localization_constants/i18_key_constants.dart'
    as i18;

import '../../blocs/bnd/search_birth_certificate_bloc.dart';
import '../../models/create-birth-registration/birth_certificates_model.dart';
import '../../utils/date_formats.dart';
import '../../widgets/Back.dart';
import '../../widgets/SideBar.dart';
import '../../widgets/atoms/app_bar_logo.dart';
import '../../widgets/drawer_wrapper.dart';

class ViewBirthCertificatesPage extends StatefulWidget {
  const ViewBirthCertificatesPage({super.key});

  @override
  ViewBirthCertificatesPageState createState() =>
      ViewBirthCertificatesPageState();
}

class ViewBirthCertificatesPageState extends State<ViewBirthCertificatesPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var t = AppLocalizations.of(context);
    return Scaffold(
        appBar: AppBar(
          titleSpacing: 0,
          title: const AppBarLogo(),
        ),
        drawer: const DrawerWrapper(Drawer(child: SideBar())),
        body: BlocBuilder<BirthSearchCertBloc, BirthSearchCertState>(
            builder: (context, searchState) {
          return searchState.maybeWhen(
              orElse: () => const SizedBox.shrink(),
              loading: () => Loaders.circularLoader(context),
              loaded: (BirthCertificatesList? birthCertificatesList) =>
                  ScrollableContent(
                    header: const Column(
                      children: [
                        Back(),
                        Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Text(
                            'View Birth Certificate',
                            style: TextStyle(
                                fontSize: 32, fontWeight: FontWeight.w700),
                          ),
                        ),
                      ],
                    ),
                    children: (birthCertificatesList?.birthCerts ?? [])
                        .map((e) => DigitCard(
                                child: Column(
                              children: [
                                CommonWidgets.getItemWidget(context,
                                    title: 'Registration No.',
                                    description: e.registrationno),
                                CommonWidgets.getItemWidget(context,
                                    title: 'Name',
                                    description:
                                        '${e.firstname} ${e.lastname}'),
                                CommonWidgets.getItemWidget(context,
                                    title: 'Birth Date',
                                    description: e.dateofbirth != null
                                        ? DateFormats.getDateFromTimestamp(
                                            e.dateofbirth ?? 0)
                                        : i18.common.noValue),
                                CommonWidgets.getItemWidget(context,
                                    title: 'Gender', description: e.genderStr),
                                CommonWidgets.getItemWidget(context,
                                    title: 'Mother Name',
                                    description:
                                        '${e.birthMotherInfo?.firstname} ${e.birthMotherInfo?.lastname}'),
                                CommonWidgets.getItemWidget(context,
                                    title: 'Father Name',
                                    description:
                                        '${e.birthFatherInfo?.firstname} ${e.birthFatherInfo?.lastname}'),
                                // Padding(
                                //   padding: const EdgeInsets.only(
                                //       top: 4.0, bottom: 4.0),
                                //   child: ButtonLink(
                                //       t.translate('View Details'), () {}),
                                // )
                              ],
                            )))
                        .toList(),
                  ));
        }));
  }
}
