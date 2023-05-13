import 'package:flutter/foundation.dart';
import 'package:intl/intl.dart';

import 'models.dart';

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

  static List<String> getFormattedDatesOfAWeek(
      int selectedStartDate, int selectedEndDate) {
    final DateTime startDate =
        DateTime.fromMillisecondsSinceEpoch(selectedStartDate);
    final DateTime endDate =
        DateTime.fromMillisecondsSinceEpoch(selectedEndDate);
    final DateFormat formatter = DateFormat('dd MMM');

    // Calculate the start of the week (Monday)
    final DateTime startOfWeek =
        startDate.subtract(Duration(days: startDate.weekday - 1));

    // Calculate the end of the week (Sunday)
    final DateTime endOfWeek = endDate.add(Duration(days: 7 - endDate.weekday));
    final List<DateTime> dates = [];
    for (var i = startOfWeek;
        i.isBefore(endOfWeek) || i == endOfWeek;
        i = i.add(const Duration(days: 1))) {
      dates.add(i);
    }
    return dates.map((date) => formatter.format(date)).toList();
  }

  static DaysInRange checkDaysInRange(int selectedStartDate,
      int selectedEndDate, int registerStartDate, int registerEndDate) {
    DateTime start = DateTime.fromMillisecondsSinceEpoch(selectedStartDate);
    DateTime end = DateTime.fromMillisecondsSinceEpoch(selectedEndDate);
    DaysInRange daysInRange = DaysInRange();
    int registerStartTime =
        dateToTimeStamp(getDateFromTimestamp(registerStartDate));
    int registerEndTime = DateTime(
            DateTime.fromMillisecondsSinceEpoch(registerEndDate).year,
            DateTime.fromMillisecondsSinceEpoch(registerEndDate).month,
            DateTime.fromMillisecondsSinceEpoch(registerEndDate).day + 1)
        .subtract(const Duration(milliseconds: 1))
        .millisecondsSinceEpoch;

    while (start.isBefore(end) || start.isAtSameMomentAs(end)) {
      DateTime currentDay = DateTime(start.year, start.month, start.day);
      int currentDayTimestamp = currentDay.millisecondsSinceEpoch;
      switch (start.weekday) {
        case 1:
          daysInRange.monday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 2:
          daysInRange.tuesday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 3:
          daysInRange.wednesday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 4:
          daysInRange.thursday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 5:
          daysInRange.friday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 6:
          daysInRange.saturday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
        case 7:
          daysInRange.sunday = currentDayTimestamp >= registerStartTime &&
              currentDayTimestamp <= registerEndTime &&
              currentDayTimestamp < DateTime.now().millisecondsSinceEpoch;
          break;
      }
      start = start.add(const Duration(days: 1));
    }

    return daysInRange;
  }

  static int getTimestampFromWeekDay(String date, String day, int hours) {
    DateTime parsedDate = DateFormat("dd/MM/yyyy").parse(date);
    int dayOfWeek = parsedDate.weekday;
    int daysToAdd = getDateTimeWeek(day) - dayOfWeek;
    int daysToSubtract = dayOfWeek - getDateTimeWeek(day);
    DateTime weekDay = day == 'sun'
        ? parsedDate.add(Duration(days: daysToAdd))
        : parsedDate.subtract(Duration(days: daysToSubtract));
    weekDay = weekDay
        .add(Duration(hours: hours))
        .subtract(Duration(minutes: hours > 12 ? 1 : 0));
    return weekDay.millisecondsSinceEpoch;
  }

  static String getTime(String date) {
    if (date.trim().isEmpty) return '';
    try {
      var dateTime = getDateFromString(date);
      return DateFormat.Hms().format(dateTime!);
    } on Exception catch (e) {
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
    } on Exception catch (e) {
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

  static getTimeLineDate(int timeInMillis) {
    try {
      DateTime date = DateTime.fromMillisecondsSinceEpoch(timeInMillis);
      String formattedDate = DateFormat('dd-MMM-yyyy, hh:mm a').format(date);
      String formattedDay = getDay(timeInMillis);
      return '$formattedDate $formattedDay';
    } catch (e) {
      return '';
    }
  }
}
