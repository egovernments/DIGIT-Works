// import 'package:digit_components/theme/digit_theme.dart';
// import 'package:flutter/cupertino.dart';
// import 'package:flutter/material.dart';
//
// class SummaryDetailsCard extends StatelessWidget {
//   const SummaryDetailsCard({super.key});
//
//
//
//   @override
//   Widget build(BuildContext context) {
//     return
//   }
//
//   Widget getCardDetails(BuildContext context, Map<String, dynamic> cardDetails) {
//     var labelList = <Widget>[];
//
//   }
//
//   static getItemWidget(BuildContext context,
//       {String title = '',
//         String description = '',
//         String subtitle = ''}) {
//     return Container(
//         padding: const EdgeInsets.all(8.0),
//         child: (Row(
//           crossAxisAlignment: CrossAxisAlignment.start,
//           children: [
//             Container(
//                 padding: const EdgeInsets.only(right: 16),
//                 width: MediaQuery.of(context).size.width > 720
//                     ? MediaQuery.of(context).size.width / 3.5
//                     : MediaQuery.of(context).size.width / 3.5,
//                 child: Column(
//                     mainAxisAlignment: MainAxisAlignment.start,
//                     crossAxisAlignment: CrossAxisAlignment.start,
//                     children: [
//                       Text(
//                         title,
//                         style: const TextStyle(
//                             fontSize: 16, fontWeight: FontWeight.w700),
//                         textAlign: TextAlign.start,
//                       ),
//                       subtitle.trim.toString() != ''
//                           ? Text(
//                         subtitle,
//                         style: TextStyle(
//                             fontSize: 14,
//                             fontWeight: FontWeight.w400,
//                             color: Theme.of(context).primaryColorLight),
//                       )
//                           : const Text('')
//                     ])),
//             SizedBox(
//                 width: MediaQuery.of(context).size.width / 2,
//                 child: Text(
//                   description,
//                   style: const TextStyle(
//                       fontSize: 16,
//                       fontWeight: FontWeight.w400,),
//                   textAlign: TextAlign.left,
//                 ))
//           ],
//         )));
//   }
// }
