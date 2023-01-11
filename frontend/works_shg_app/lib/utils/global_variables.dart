import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:universal_html/html.dart' as html;

import '../services/local_storage.dart';

class GlobalVariables {
  static dynamic getUserInfo() {
    if (kIsWeb) {
      return jsonDecode(html.window.localStorage['userRequest'].toString());
    } else {
      return jsonDecode(storage.read(key: 'userRequest').toString());
    }
  }

  static String? getAuthToken() {
    dynamic accessToken;
    if (kIsWeb) {
      accessToken = html.window.localStorage['accessToken'];
      return jsonDecode(accessToken.toString());
    } else {
      accessToken = storage.read(key: 'accessToken');
      return jsonDecode(accessToken.toString());
    }
  }

  static String? getTenantId() {
    dynamic tenantId;
    if (kIsWeb) {
      tenantId = html.window.localStorage['tenantId'];
      return jsonDecode(tenantId.toString());
    } else {
      tenantId = storage.read(key: 'tenantId');
      return jsonDecode(tenantId.toString());
    }
  }

  static String? getUUID() {
    dynamic uuid;
    if (kIsWeb) {
      uuid = html.window.localStorage['uuid'];
      return jsonDecode(uuid.toString());
    } else {
      uuid = storage.read(key: 'uuid');
      return jsonDecode(uuid.toString());
    }
  }

  static dynamic getInitData() {
    if (kIsWeb) {
      return jsonDecode(html.window.localStorage['initData'].toString());
    } else {
      return jsonDecode(storage.read(key: 'initData').toString());
    }
  }

  static dynamic getStateInfo() {
    if (kIsWeb) {
      return jsonDecode(html.window.localStorage['StateInfo'].toString());
    } else {
      return jsonDecode(storage.read(key: 'StateInfo').toString());
    }
  }

  static dynamic getLanguages() {
    if (kIsWeb) {
      return jsonDecode(html.window.localStorage['languages'].toString());
    } else {
      return jsonDecode(storage.read(key: 'languages').toString());
    }
  }

  static Future<bool> isLocaleSelect(String locale) async {
    if (kIsWeb) {
      return html.window.localStorage.keys.contains(locale);
    } else {
      return await storage.containsKey(key: locale);
    }
  }
}
