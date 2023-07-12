import 'package:digit_components/theme/colors.dart';
import 'package:flutter/material.dart';

ThemeData get theme => ThemeData(
    primarySwatch: createMaterialColor(const Color(0XFFf47738)),
    highlightColor: createMaterialColor(const Color(0XFFC7E0F1)),
    backgroundColor:
        createMaterialColor(const Color.fromRGBO(238, 238, 238, 1)),
    primaryColor: const Color.fromRGBO(244, 119, 56, 1),
    hintColor: const Color.fromRGBO(177, 180, 182, 1),
    primaryColorDark: const Color.fromRGBO(11, 12, 12, 1),
    primaryColorLight: const Color.fromRGBO(80, 90, 95, 1),
    indicatorColor: const Color.fromRGBO(35, 107, 9, 1),
    colorScheme: colorScheme,
    // accentColor:  Color(0xff0B4B66),

    appBarTheme: const AppBarTheme(
      backgroundColor: Color(0xff0B4B66),
      centerTitle: false,
    ),
    textTheme: const TextTheme(
        displayLarge: TextStyle(
            fontWeight: FontWeight.w700,
            fontSize: 32,
            fontFamily: 'Roboto Condensed',
            fontStyle: FontStyle.normal,
            color: Color.fromRGBO(11, 12, 12, 1)),
        displaySmall: TextStyle(fontWeight: FontWeight.w700, fontSize: 24),
        labelLarge: TextStyle(
            fontWeight: FontWeight.w500,
            fontSize: 19,
            color: Colors.white), // Elevated Button(Orange)
        displayMedium: TextStyle(
            fontWeight: FontWeight.w700,
            fontSize: 16,
            fontFamily: 'Roboto',
            fontStyle: FontStyle.normal,
            color: Color.fromRGBO(11, 12, 12, 1)),
        titleMedium: TextStyle(fontWeight: FontWeight.w400, fontSize: 16),
        titleSmall: TextStyle(
            fontWeight: FontWeight.w400,
            fontSize: 16,
            color: Color.fromRGBO(
                244, 119, 56, 1)) // Only for outlined button text
        ),

    /// Background color
    // scaffoldBackgroundColor: Color.fromRGBO(238, 238, 238, 1),

    textButtonTheme: TextButtonThemeData(
        style: TextButton.styleFrom(
      textStyle: const TextStyle(color: Colors.black, fontSize: 16),
      // padding: EdgeInsets.symmetric(vertical: 10),
    )),
    elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(0)),
            padding: const EdgeInsets.symmetric(vertical: 15),
            textStyle:
                const TextStyle(fontSize: 19, fontWeight: FontWeight.w500))),
    outlinedButtonTheme: OutlinedButtonThemeData(
        style: OutlinedButton.styleFrom(
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(0)),
      textStyle: const TextStyle(
          color: Color(0XFFf47738), fontSize: 19, fontWeight: FontWeight.w500),
      padding: const EdgeInsets.symmetric(vertical: 15),
    )),
    inputDecorationTheme: const InputDecorationTheme(
      contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
      border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.zero)),
      prefixStyle: TextStyle(color: Colors.black),
      hintStyle: TextStyle(color: Color.fromRGBO(80, 90, 95, 1)),
      enabledBorder: OutlineInputBorder(
        borderRadius: BorderRadius.all(Radius.zero),
        borderSide: BorderSide(color: Color.fromRGBO(80, 90, 95, 1)),
      ),
      errorBorder: OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.zero),
          borderSide: BorderSide(color: Colors.red)),
      focusedErrorBorder: OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.zero),
          borderSide: BorderSide(color: Colors.red)),
      errorStyle: TextStyle(fontSize: 15),
      disabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.zero),
          borderSide: BorderSide(color: Colors.grey)),
    ),
    iconTheme: const IconThemeData(
      color: Color.fromRGBO(244, 119, 56, 1),
      // size: 25
    ));

DigitColors get colors => const DigitColors();

ColorScheme get colorScheme => ColorScheme(
      brightness: Brightness.light,
      primary: colors.burningOrange,
      onPrimary: colors.white,
      secondary: colors.regalBlue,
      onSecondary: colors.white,
      error: colors.lavaRed,
      onError: colors.white,
      background: colors.seaShellGray,
      onBackground: colors.woodsmokeBlack,
      surface: colors.alabasterWhite,
      onSurface: colors.woodsmokeBlack,
      onSurfaceVariant: colors.darkSpringGreen,
      tertiaryContainer: colors.tropicalBlue,
      inversePrimary: colors.paleLeafGreen,
      surfaceTint: colors.waterBlue,
    );

MaterialColor createMaterialColor(Color color) {
  List strengths = <double>[.05];
  final swatch = <int, Color>{};
  final int r = color.red, g = color.green, b = color.blue;

  for (int i = 1; i < 10; i++) {
    strengths.add(0.1 * i);
  }
  for (var strength in strengths) {
    final double ds = 0.5 - strength;
    swatch[(strength * 1000).round()] = Color.fromRGBO(
      r + ((ds < 0 ? r : (255 - r)) * ds).round(),
      g + ((ds < 0 ? g : (255 - g)) * ds).round(),
      b + ((ds < 0 ? b : (255 - b)) * ds).round(),
      1,
    );
  }
  return MaterialColor(color.value, swatch);
}
