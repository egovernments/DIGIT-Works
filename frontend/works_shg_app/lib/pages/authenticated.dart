import 'package:auto_route/auto_route.dart';
import 'package:digit_components/blocs/location/location.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:location/location.dart';
import 'package:works_shg_app/blocs/user/user_search.dart';

import '../blocs/muster_rolls/search_muster_roll.dart';
import '../widgets/SideBar.dart';

class AuthenticatedPageWrapper extends StatelessWidget {
  const AuthenticatedPageWrapper({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      drawer: Container(
          margin: const EdgeInsets.only(top: kToolbarHeight),
          child: Drawer(
            child: BlocBuilder<UserSearchBloc, UserSearchState>(
                builder: (context, state) {
              return !state.loading && state.userSearchModel != null
                  ? SideBar(
                      userName:
                          state.userSearchModel!.user!.first.name.toString(),
                      mobileNumber:
                          state.userSearchModel!.user!.first.mobileNumber,
                    )
                  : const CircularProgressIndicator();
            }),
          )),
      body: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (_) => LocationBloc(location: Location())
              ..add(const LoadLocationEvent()),
          ),
          BlocProvider(
            create: (_) => UserSearchBloc()..add(const SearchUserEvent()),
          ),
          BlocProvider(
            create: (_) =>
                MusterRollSearchBloc()..add(const SearchMusterRollEvent()),
          )
        ],
        child: BlocBuilder<UserSearchBloc, UserSearchState>(
            builder: (context, state) {
          return !state.loading && state.userSearchModel != null
              ? const AutoRouter()
              : const CircularProgressIndicator();
        }),
      ),
    );
  }
}
