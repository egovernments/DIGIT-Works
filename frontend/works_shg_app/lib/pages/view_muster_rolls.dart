import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:works_shg_app/blocs/muster_rolls/search_muster_roll.dart';
import 'package:works_shg_app/utils/Constants/i18_key_constants.dart' as i18;
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../blocs/localization/app_localization.dart';
import '../utils/date_formats.dart';
import '../widgets/Back.dart';

class ViewMusterRollsPage extends StatelessWidget {
  const ViewMusterRollsPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(body: SingleChildScrollView(child:
        BlocBuilder<MusterRollSearchBloc, MusterRollSearchState>(
            builder: (context, state) {
      if (!state.loading && state.musterRollsModel != null) {
        final List<Map<String, dynamic>> musterList = state
            .musterRollsModel!.musterRoll!
            .map((e) => {
                  "Name of the Work":
                      e.musterAdditionalDetails?.attendanceRegisterName ?? '',
                  "WIN Code":
                      e.musterAdditionalDetails?.attendanceRegisterNo ?? '',
                  "Muster Roll ID": e.musterRollNumber,
                  "Dates":
                      '${DateFormats.timeStampToDate(e.startDate, format: "dd/MM/yyyy")} - ${DateFormats.timeStampToDate(e.endDate, format: "dd/MM/yyyy")}',
                  "Status": e.status
                })
            .toList();
        return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          const Back(),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(
              '${AppLocalizations.of(context).translate(i18.home.musterRoll)}(${state.musterRollsModel!.musterRoll!.length})',
              style: Theme.of(context).textTheme.displayMedium,
              textAlign: TextAlign.left,
            ),
          ),
          musterList.isEmpty
              ? const Text('No Muster Rolls Found')
              : WorkDetailsCard(
                  musterList,
                  isSHGInbox: true,
                )
        ]);
      } else {
        return const CircularProgressIndicator();
      }
    })));
  }
}
