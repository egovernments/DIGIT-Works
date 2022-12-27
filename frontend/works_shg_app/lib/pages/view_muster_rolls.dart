import 'package:flutter/material.dart';
import 'package:works_shg_app/data/fake_muster_rolls.dart';
import 'package:works_shg_app/widgets/WorkDetailsCard.dart';

import '../widgets/Back.dart';

class ViewMusterRollsPage extends StatelessWidget {
  const ViewMusterRollsPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        const Back(),
        Padding(
          padding: const EdgeInsets.all(16.0),
          child: Text(
            'Muster Rolls(${fakeMusterRolls.length})',
            style: Theme.of(context).textTheme.displayMedium,
            textAlign: TextAlign.left,
          ),
        ),
        WorkDetailsCard(
          fakeMusterRolls,
          isSHGInbox: true,
        )
      ])),
    );
  }
}
