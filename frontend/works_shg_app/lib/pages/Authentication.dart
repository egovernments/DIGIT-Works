import 'dart:convert';

import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';

import '../data/fake_language_schema.dart';
import 'Login.dart';

class LandingPage extends StatelessWidget {
  const LandingPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final List list = json.decode(fakeLanguageSchema);
    List<DigitRowCardModel> languageList =
        list.map((e) => DigitRowCardModel.fromJson(e)).toList();

    return Scaffold(
      body: ScrollableContent(
        children: [
          DigitBanner(
            color: DigitTheme.instance.colors.regalBlue,
            imgUrl:
                'https://s3.ap-south-1.amazonaws.com/pb-egov-assets/pb.testing/Punjab-bg-QA.jpg',
            child: Align(
              alignment: Alignment.bottomCenter,
              child: DigitLanguageCard(
                list: languageList,
                onPressedButton: () =>
                    Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => const LoginPage(),
                )),
                onPressedicon: (data) {},
                actionlabel: 'Continue',
              ),
            ),
          ),
        ],
      ),
    );
  }
}
