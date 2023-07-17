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

/*
  * @author Ramkrishna
  * ramkrishna.sahoo@egovernments.org
  *
  * */
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
                    header: Column(
                      children: [
                        const Back(),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              t.translate(i18.bnd.viewCertificate),
                              textAlign: TextAlign.start,
                              style: const TextStyle(
                                  fontSize: 32, fontWeight: FontWeight.w700),
                            ),
                          ),
                        ),
                      ],
                    ),
                    footer: const PoweredByDigit(),
                    children: (birthCertificatesList?.birthCerts ?? [])
                        .map((e) => DigitCard(
                                child: Column(
                              children: [
                                //CommonWidgets.getItemWidget(context,title: 'title',description: 'description),
                                // A card field widget to show the card title and the description in row
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.regNo),
                                    description: e.registrationno),
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.name),
                                    description:
                                        '${e.firstname} ${e.lastname}'),
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.birthDate),
                                    description: e.dateofbirth != null
                                        ? DateFormats.getDateFromTimestamp(
                                            e.dateofbirth ?? 0)
                                        : i18.common.noValue),
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.gender),
                                    description: e.genderStr),
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.motherName),
                                    description:
                                        '${e.birthMotherInfo?.firstname} ${e.birthMotherInfo?.lastname}'),
                                CommonWidgets.getItemWidget(context,
                                    title: t.translate(i18.bnd.fatherName),
                                    description:
                                        '${e.birthFatherInfo?.firstname} ${e.birthFatherInfo?.lastname}'),
                              ],
                            )))
                        .toList(),
                  ));
        }));
  }
}
