import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:works_shg_app/router/app_router.dart';

class WorkDetailsCard extends StatelessWidget {
  final List<Map<String, dynamic>> detailsList;
  final bool isAttendanceInbox;
  final bool isSHGInbox;
  const WorkDetailsCard(this.detailsList,
      {this.isAttendanceInbox = false, this.isSHGInbox = false, super.key});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        return ListView.builder(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          itemCount: detailsList.length,
          itemBuilder: (BuildContext context, int index) {
            return Column(children: [
              GestureDetector(
                onTap: () => isSHGInbox
                    ? context.router.push(
                        SHGInboxRoute(musterDetails: [detailsList[index]]))
                    : null,
                child: DigitCard(
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                      ListView.builder(
                          scrollDirection: Axis.vertical,
                          shrinkWrap: true,
                          itemCount: detailsList[index].length,
                          itemBuilder: (BuildContext context, int labelIndex) {
                            return Column(
                              children: [
                                getItemWidget(context,
                                    title: detailsList[index]
                                        .keys
                                        .elementAt(labelIndex)
                                        .toString(),
                                    description: detailsList[index]
                                        .values
                                        .elementAt(labelIndex)
                                        .toString())
                              ],
                            );
                          })
                    ])),
              )
            ]);
          },
        );
      },
    );
  }

  static getItemWidget(BuildContext context,
      {String title = '', String description = '', String subtitle = ''}) {
    return Container(
        padding: const EdgeInsets.all(8.0),
        child: (Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
                padding: const EdgeInsets.only(right: 16),
                width: MediaQuery.of(context).size.width / 3,
                child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        title,
                        style: const TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w700),
                        textAlign: TextAlign.start,
                      ),
                      subtitle.trim.toString() != ''
                          ? Text(
                              subtitle,
                              style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.w400,
                                  color: Theme.of(context).primaryColorLight),
                            )
                          : const Text('')
                    ])),
            Text(
              description,
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w400),
              textAlign: TextAlign.center,
            )
          ],
        )));
  }
}
