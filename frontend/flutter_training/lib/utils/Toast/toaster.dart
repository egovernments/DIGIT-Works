import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_training/utils/Toast/toast_animation.dart';

import '../../blocs/localization/app_localization.dart';

class ToastUtils {
  static Timer? toastTimer;
  static OverlayEntry? _overlayEntry;

  static void showCustomToast(
      BuildContext context, String message, String type) {
    if (toastTimer == null || toastTimer!.isActive == false) {
      _overlayEntry = createOverlayEntry(context, message, type);

      Overlay.of(context)!.insert(_overlayEntry!);
      toastTimer = Timer(const Duration(seconds: 5), () {
        if (_overlayEntry != null) {
          _overlayEntry!.remove();
        }
        _overlayEntry!.remove();
      });
    }
  }

  static OverlayEntry createOverlayEntry(
      BuildContext context, String message, String type) {
    return OverlayEntry(
        builder: ((context) => Positioned(
              bottom: 20.0,
              width: MediaQuery.of(context).size.width > 720
                  ? MediaQuery.of(context).size.width / 3
                  : MediaQuery.of(context).size.width,
              left: MediaQuery.of(context).size.width > 720
                  ? MediaQuery.of(context).size.width / 2.55
                  : 0,
              child: (Material(
                elevation: 5.0,
                borderRadius: BorderRadius.circular(10),
                child: Container(
                  padding: const EdgeInsets.only(
                      left: 4, right: 10, top: 13, bottom: 10),
                  decoration: BoxDecoration(
                      color: type == 'ERROR'
                          ? Colors.red
                          : type == 'INFO'
                              ? Colors.orangeAccent
                              : Colors.green[900]),
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: SlideInToastMessageAnimation(Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(left: 8.0, right: 8.0),
                          child: Icon(
                            type == 'ERROR' || type == 'INFO'
                                ? Icons.info_outline_rounded
                                : Icons.check_circle_outline_rounded,
                            color: Colors.white,
                          ),
                        ),
                        Expanded(
                          child: Text(
                            AppLocalizations.of(context).translate(message),
                            textAlign: TextAlign.start,
                            softWrap: true,
                            maxLines: 4,
                            overflow: TextOverflow.ellipsis,
                            style: const TextStyle(
                              fontSize: 16,
                              color: Color(0xFFFFFFFF),
                            ),
                          ),
                        ),
                        GestureDetector(
                          onTap: () {
                            toastTimer!.cancel();
                            _overlayEntry!.remove();
                          },
                          child: const Icon(
                            Icons.close,
                            color: Colors.white,
                          ),
                        )
                      ],
                    )),
                  ),
                ),
              )),
            )));
  }
}
