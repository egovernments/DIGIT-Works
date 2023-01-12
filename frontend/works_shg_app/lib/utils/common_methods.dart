import 'package:flutter/foundation.dart';
import 'package:universal_html/html.dart' as html;
import 'package:works_shg_app/services/local_storage.dart';

import 'global_variables.dart';

class CommonMethods {
  void deleteLocalStorageKey() {
    if (kIsWeb) {
      html.window.localStorage.remove(GlobalVariables.selectedLocale());
    } else {
      storage.delete(key: GlobalVariables.selectedLocale());
    }
  }
}
