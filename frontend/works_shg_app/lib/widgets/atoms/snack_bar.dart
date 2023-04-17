// import 'package:flutter/cupertino.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_bloc/flutter_bloc.dart';
//
// class SnackbarManager extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return BlocListener<YourBloc, YourBlocState>(
//       listener: (context, state) {
//         if (state.hasMyData) {
//           Scaffold.of(context).showSnackBar(SnackBar(
//             content:
//             Text("I got data"),
//           ));
//         }
//       },
//       child: Container(),
//     );
//   }
