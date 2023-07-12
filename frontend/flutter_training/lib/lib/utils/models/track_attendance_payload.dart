import 'package:works_shg_app/models/muster_rolls/muster_roll_model.dart';

class TrackAttendanceTableData {
  String? name;
  String? aadhaar;
  String? gender;
  String? individualId;
  String? individualGaurdianName;
  String? id;
  String? skill;
  List<String>? skillCodeList;
  void Function()? onTap;
  String? monEntryId;
  String? monExitId;
  double? monIndex;
  String? tueEntryId;
  String? tueExitId;
  double? tueIndex;
  String? wedEntryId;
  String? wedExitId;
  double? wedIndex;
  String? thuEntryId;
  String? thuExitId;
  double? thuIndex;
  String? friEntryId;
  String? friExitId;
  double? friIndex;
  String? satEntryId;
  String? satExitId;
  double? satIndex;
  String? sunEntryId;
  String? sunExitId;
  double? sunIndex;
  AuditDetails? auditDetails;

  double? getProperty(String property) {
    switch (property) {
      case 'mon':
        return monIndex;
      case 'tue':
        return tueIndex;
      case 'wed':
        return wedIndex;
      case 'thu':
        return thuIndex;
      case 'fri':
        return friIndex;
      case 'sat':
        return satIndex;
      case 'sun':
        return sunIndex;
      default:
        return null;
    }
  }

  void setProperty(String property, double value) {
    switch (property) {
      case 'mon':
        monIndex = value;
        break;
      case 'tue':
        tueIndex = value;
        break;
      case 'wed':
        wedIndex = value;
        break;
      case 'thu':
        thuIndex = value;
        break;
      case 'fri':
        friIndex = value;
        break;
      case 'sat':
        satIndex = value;
        break;
      case 'sun':
        sunIndex = value;
        break;
    }
  }
}

class IndividualSkills {
  String? individualId;
  String? skillCode;
  String? name;
  String? gender;
  String? aadhaar;
  String? id;
  String? individualGaurdianName;
  IndividualSkills(
      {this.individualId,
      this.skillCode,
      this.name,
      this.aadhaar,
      this.gender,
      this.id,
      this.individualGaurdianName});
}

class EntryExitModel {
  int? hours;
  String? code;
  EntryExitModel({this.hours, this.code});
}

class EntryExitList {
  List<EntryExitModel> entryExitList;
  EntryExitList(this.entryExitList);
}

List<Map<String, dynamic>> updateAttendanceLogPayload(
    TrackAttendanceTableData attendeeList,
    String registerId,
    int entryTime,
    int exitTime,
    String entryId,
    String exitId,
    String tenantId,
    AuditDetails auditDetails,
    bool status,
    bool onlyExit) {
  return onlyExit
      ? [
          {
            "id": exitId,
            "registerId": registerId,
            "individualId": attendeeList.individualId,
            "time": exitTime,
            "type": "EXIT",
            "status": "ACTIVE",
            "tenantId": tenantId,
            "documentIds": [],
            "auditDetails": auditDetails
          }
        ]
      : [
          {
            "id": entryId,
            "registerId": registerId,
            "individualId": attendeeList.individualId,
            "time": entryTime,
            "type": "ENTRY",
            "status": "ACTIVE",
            "tenantId": tenantId,
            "documentIds": [],
            "auditDetails": auditDetails
          },
          {
            "id": exitId,
            "registerId": registerId,
            "individualId": attendeeList.individualId,
            "time": exitTime,
            "type": "EXIT",
            "status": "ACTIVE",
            "tenantId": tenantId,
            "documentIds": [],
            "auditDetails": auditDetails
          }
        ];
}

List<Map<String, dynamic>> createAttendanceLogPayload(
    TrackAttendanceTableData attendeeList,
    String registerId,
    int entryTime,
    int exitTime,
    String tenantId,
    {bool isAbsent = false}) {
  return [
    {
      "registerId": registerId,
      "individualId": attendeeList.individualId,
      "time": entryTime,
      "type": "ENTRY",
      "status": "ACTIVE",
      "tenantId": tenantId,
      "documentIds": []
    },
    {
      "registerId": registerId,
      "individualId": attendeeList.individualId,
      "time": exitTime,
      "type": "EXIT",
      "status": "ACTIVE",
      "tenantId": tenantId,
      "documentIds": []
    }
  ];
}
