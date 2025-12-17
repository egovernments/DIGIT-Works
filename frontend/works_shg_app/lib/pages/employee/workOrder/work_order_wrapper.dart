import 'package:flutter/material.dart';

import '../../../router/app_router.dart';

@RoutePage()
class WorkOrderWrapperPage extends StatefulWidget implements AutoRouteWrapper {
  const WorkOrderWrapperPage({super.key});

  @override
  State<WorkOrderWrapperPage> createState() => _WorkOrderWrapperPageState();

  @override
  Widget wrappedRoute(BuildContext context) {
    return this;
  }
}

class _WorkOrderWrapperPageState extends State<WorkOrderWrapperPage> {
  @override
  Widget build(BuildContext context) {
    return const AutoRouter();
  }
}
