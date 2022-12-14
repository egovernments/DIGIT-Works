import 'package:digit_components/digit_components.dart';
import 'package:flutter/cupertino.dart';

class AppRouterObserver extends NavigatorObserver {
  static const _base = 'appRouter';

  @override
  void didPop(Route<dynamic> route, Route<dynamic>? previousRoute) {
    AppLogger.instance.info(
      '${route.settings.name} -> ${previousRoute?.settings.name}',
      title: '$_base.didPop',
    );
  }

  @override
  void didPush(Route route, Route<dynamic>? previousRoute) {
    AppLogger.instance.info(
      '${previousRoute?.settings.name} -> ${route.settings.name}',
      title: '$_base.didPush',
    );
  }

  @override
  void didRemove(Route route, Route<dynamic>? previousRoute) {
    AppLogger.instance.info(
      '${route.settings.name}',
      title: '$_base.didRemove',
    );
  }

  @override
  void didReplace({Route? newRoute, Route<dynamic>? oldRoute}) {
    AppLogger.instance.info(
      '${oldRoute?.settings.name} -X- ${newRoute?.settings.name}',
      title: '$_base.didReplace',
    );
  }

  @override
  void didStartUserGesture(
    Route<dynamic> route,
    Route<dynamic>? previousRoute,
  ) {
    AppLogger.instance.info(
      '${previousRoute?.settings.name} -+- ${route.settings.name}',
      title: '$_base.didStartUserGesture',
    );
  }

  @override
  void didStopUserGesture() {
    AppLogger.instance.info(
      'Stopped gesture',
      title: '$_base.didStopUserGesture',
    );
  }
}
