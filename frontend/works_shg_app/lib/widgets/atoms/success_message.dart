import 'package:flutter/material.dart';

class SuccessMessage extends StatelessWidget {
  final String? label;
  final String? subText;
  final String? subTextHeader;
  const SuccessMessage(this.label,
      {super.key, this.subText, this.subTextHeader});

  @override
  Widget build(BuildContext context) {
    return Container(
        width: MediaQuery.of(context).size.width,
        padding: const EdgeInsets.all(30),
        decoration: BoxDecoration(color: Colors.green[900]),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Align(
              alignment: Alignment.center,
              child: Text(label ?? '',
                  textAlign: TextAlign.center,
                  style: const TextStyle(
                      color: Colors.white,
                      fontSize: 32,
                      fontFamily: 'Roboto Condensed',
                      fontWeight: FontWeight.w700)),
            ),
            const SizedBox(
              height: 20,
            ),
            const Icon(
              Icons.check_circle,
              color: Colors.white,
              size: 32,
            ),
            const SizedBox(
              height: 20,
            ),
            if (subTextHeader != null)
              Container(
                padding: const EdgeInsets.only(top: 8),
                alignment: Alignment.center,
                child: Text(subTextHeader ?? '',
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                        color: Colors.white,
                        fontSize: 18,
                        fontWeight: FontWeight.w700)),
              ),
            if (subText != null)
              Container(
                padding: const EdgeInsets.only(top: 8),
                alignment: Alignment.center,
                child: Text(subText ?? '',
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                        color: Colors.white,
                        fontSize: 24,
                        fontWeight: FontWeight.w700)),
              ),
          ],
        ));
  }
}
