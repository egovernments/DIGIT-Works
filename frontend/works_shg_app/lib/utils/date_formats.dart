import 'package:flutter/foundation.dart';
import 'package:intl/intl.dart';

class DateFormats {
  static getFilteredDate(String date, {String? dateFormat}) {
    if (date.trim().isEmpty) return '';
    try {
      var dateTime = DateTime.parse(date).toLocal();
      return DateFormat(dateFormat ?? "dd/MM/yyyy").format(dateTime);
    } on Exception catch (e) {
      if (kDebugMode) {
        print(e.toString());
      }
      return '';
    }
  }

  static DateTime? getDateFromString(String date) {
    if (date.trim().isEmpty) return null;
    try {
      var dateTime = DateTime.parse(date).toLocal();
      return dateTime;
    } on Exception catch (e) {
      if (kDebugMode) {
        print(e.toString());
      }
      return null;
    }
  }

  static DateTime? getFormattedDateToDateTime(String date) {
    try {
      DateFormat inputFormat;
      if (date.contains('-')) {
        inputFormat = DateFormat('dd-MM-yyyy');
      } else {
        inputFormat = DateFormat('dd/MM/yyyy');
      }
      var inputDate = inputFormat.parse(date);
      return inputDate;
    } on Exception catch (e) {
      if (kDebugMode) {
        print(e.toString());
      }
      return null;
    }
  }

  static String getDateFromTimestamp(int timestamp) {
    DateTime date = DateTime.fromMillisecondsSinceEpoch(timestamp);
    return DateFormat("dd/MM/yyyy").format(date);
  }

  static int getDateTimeWeek(String property) {
    switch (property) {
      case 'mon':
        return DateTime.monday;
      case 'tue':
        return DateTime.tuesday;
      case 'wed':
        return DateTime.wednesday;
      case 'thu':
        return DateTime.thursday;
      case 'fri':
        return DateTime.friday;
      case 'sat':
        return DateTime.saturday;
      case 'sun':
        return DateTime.sunday;
      default:
        return 0;
    }
  }

  static int getTimestampFromWeekDay(String date, String day, int hours) {
    DateTime parsedDate = DateFormat("dd/MM/yyyy").parse(date);
    int dayOfWeek = parsedDate.weekday;
    int daysToAdd = getDateTimeWeek(day) - dayOfWeek;
    int daysToSubtract = dayOfWeek - getDateTimeWeek(day);
    DateTime weekDay = day == 'sun'
        ? parsedDate.add(Duration(days: daysToAdd))
        : parsedDate.subtract(Duration(days: daysToSubtract));
    weekDay = weekDay.add(Duration(hours: hours));
    return weekDay.millisecondsSinceEpoch;
  }

  static String getTime(String date) {
    if (date.trim().isEmpty) return '';
    try {
      var dateTime = getDateFromString(date);
      return DateFormat.Hms().format(dateTime!);
    } on Exception catch (e, stackTrace) {
      if (kDebugMode) {
        print(e.toString());
      }
      return '';
    }
  }

  static String getLocalTime(String date) {
    try {
      var dateTime = getDateFromString(date);
      return DateFormat.jm().format(dateTime!);
    } on Exception catch (e, stackTrace) {
      if (kDebugMode) {
        print(e.toString());
      }
      return '';
    }
  }

  static int dateToTimeStamp(String dateTime) {
    try {
      return getFormattedDateToDateTime(dateTime)!
          .toUtc()
          .millisecondsSinceEpoch;
    } catch (e) {
      return 0;
    }
  }

  static String timeStampToDate(int? timeInMillis, {String? format}) {
    if (timeInMillis == null) return '';
    try {
      var date = DateTime.fromMillisecondsSinceEpoch(timeInMillis);
      return DateFormat(format ?? 'dd/MM/yyyy').format(date);
    } catch (e) {
      return '';
    }
  }

  static String getMonth(DateTime date) {
    try {
      return DateFormat.MMM().format(date);
    } catch (e) {
      return '';
    }
  }

  static String getDay(int timeInMillis) {
    try {
      DateTime date = DateTime.fromMillisecondsSinceEpoch(timeInMillis);
      return DateFormat.E().format(date);
    } catch (e) {
      return '';
    }
  }
}
