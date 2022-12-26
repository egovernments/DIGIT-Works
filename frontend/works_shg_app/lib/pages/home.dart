import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/router/app_router.dart';
import 'package:works_shg_app/widgets/ButtonLink.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ScrollableContent(
        children: [
          DigitCard(
            onPressed: null,
            padding: const EdgeInsets.fromLTRB(16, 30, 16, 16),
            child: Align(
              alignment: Alignment.topCenter,
              child: LabeledField(
                  label: 'Works Mgmt',
                  child: Column(
                    children: [
                      ButtonLink(
                          'Work Order',
                              () => context.router
                              .push(const WorkOrderPageRoute())),
                    ],
                  )),
            ),
          ),
          DigitCard(
            onPressed: null,
            padding: const EdgeInsets.fromLTRB(16, 30, 16, 16),
            child: Align(
              alignment: Alignment.topCenter,
              child: LabeledField(
                  label: 'Attendance Inbox',
                  child: Column(
                    children: [
                      ButtonLink(
                          'Manage Attendance',
                          () => context.router
                              .push(const AttendanceInboxRoute())),
                      ButtonLink(
                          'Register Individual (Wage seeker)', () => null),
                      ButtonLink(
                          'Muster Roll',
                          () => context.router
                              .push(const ViewMusterRollsRoute())),
                    ],
                  )),
            ),
          )
        ],
      ),
    );
  }
}
