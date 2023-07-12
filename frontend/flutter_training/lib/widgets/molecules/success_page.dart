import 'package:digit_components/digit_components.dart';
import 'package:flutter/material.dart';
import 'package:flutter_training/router/app_router.dart';

import '../../utils/common_methods.dart';
import '../Back.dart';
import '../SideBar.dart';
import '../atoms/app_bar_logo.dart';
import '../atoms/success_message.dart';
import '../drawer_wrapper.dart';

class SuccessResponsePage extends StatelessWidget {
  final String header;
  final String? subHeader;
  final String? subText;
  final String? subTitle;
  final VoidCallback? callBack;
  final VoidCallback? callBackWhatsapp;
  final String? downloadLabel;
  final String? whatsAppLabel;
  final String? printLabel;
  final VoidCallback? callBackDownload;
  final VoidCallback? callBackPrint;
  final bool? backButton;
  final String? buttonLabel;
  final bool isWithoutLogin;
  final String? backButtonLabel;

  const SuccessResponsePage(
      {super.key,
      required this.header,
      this.subHeader,
      this.subText,
      this.subTitle,
      this.callBack,
      this.callBackWhatsapp,
      this.callBackDownload,
      this.callBackPrint,
      this.backButton,
      this.buttonLabel,
      this.isWithoutLogin = false,
      this.downloadLabel,
      this.printLabel,
      this.whatsAppLabel,
      this.backButtonLabel});

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        context.router.push(const HomeRoute());
        return false;
      },
      child: Scaffold(
          bottomNavigationBar: Container(
            height: 60,
            padding: const EdgeInsets.all(8.0),
            child: const Align(
              alignment: Alignment.bottomCenter,
              child: PoweredByDigit(),
            ),
          ),
          appBar: isWithoutLogin
              ? AppBar(
                  title: const Text('MuktaSoft'),
                  automaticallyImplyLeading: false,
                )
              : AppBar(
                  titleSpacing: 0,
                  title: const AppBarLogo(),
                ),
          drawer: isWithoutLogin
              ? null
              : DrawerWrapper(Drawer(
                  child: SideBar(
                  module: CommonMethods.getLocaleModules(),
                ))),
          body: SingleChildScrollView(
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                backButton == true
                    ? Back(
                        callback: () => context.router.push(const HomeRoute()))
                    : const Text(''),
                DigitCard(
                    margin: const EdgeInsets.only(left: 10, right: 10),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        SuccessMessage(header,
                            subTextHeader: subHeader, subText: subText),
                        Align(
                            alignment: Alignment.centerLeft,
                            child: Container(
                              margin: const EdgeInsets.only(
                                  left: 10, bottom: 20, top: 20, right: 10),
                              child: Text(
                                subTitle ?? '',
                                style: const TextStyle(
                                    fontSize: 14, fontWeight: FontWeight.w400),
                                textAlign: TextAlign.start,
                              ),
                            )),
                        Visibility(
                          visible:
                              downloadLabel != null && callBackDownload != null,
                          child: const SizedBox(
                            height: 20,
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Expanded(
                                child: Visibility(
                              visible: downloadLabel != null &&
                                  callBackDownload != null,
                              child: TextButton.icon(
                                onPressed: callBackDownload,
                                icon: const Icon(Icons.download_sharp),
                                label: Text(downloadLabel ?? '',
                                    style: TextStyle(
                                        fontSize: 16,
                                        color: DigitTheme
                                            .instance.colorScheme.primary)),
                              ),
                            )),
                            Expanded(
                                child: Visibility(
                              visible:
                                  printLabel != null && callBackPrint != null,
                              child: TextButton.icon(
                                onPressed: callBackPrint,
                                icon: Icon(
                                  Icons.print,
                                  color:
                                      DigitTheme.instance.colorScheme.primary,
                                ),
                                label: Text(printLabel ?? '',
                                    style: TextStyle(
                                        fontSize: 16,
                                        color: DigitTheme
                                            .instance.colorScheme.primary)),
                              ),
                            )),
                            Expanded(
                                child: Visibility(
                              visible: whatsAppLabel != null &&
                                  callBackWhatsapp != null,
                              child: TextButton.icon(
                                onPressed: callBackWhatsapp,
                                icon: (Image.asset('assets/png/whats_app.png')),
                                label: Text(
                                  whatsAppLabel ?? '',
                                  style: TextStyle(
                                      fontSize: 16,
                                      color: DigitTheme
                                          .instance.colorScheme.primary),
                                ),
                              ),
                            ))
                          ],
                        ),
                        Visibility(
                          visible: !isWithoutLogin,
                          child: DigitElevatedButton(
                            onPressed: () {
                              context.router.push(const HomeRoute());
                            },
                            child: Center(
                              child: Text(buttonLabel ?? '',
                                  style: Theme.of(context)
                                      .textTheme
                                      .titleMedium!
                                      .apply(color: Colors.white)),
                            ),
                          ),
                        ),
                        const SizedBox(
                          height: 20,
                        ),
                      ],
                    ))
              ]))),
    );
  }
}
