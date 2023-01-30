class TrackAttendanceTableData {
  String? name;
  String? aadhaar;
  String? individualId;
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

class CreateAttendanceLogPayloadData {
  // String? registerId;
  // String? individualId;
  // int? time;
  // String? type;
  // String? status;
  // String? tenantId;
  // List<String>? documentIds;
}

List<Map<String, dynamic>> updateAttendanceLogPayload(
    TrackAttendanceTableData attendeeList,
    String registerId,
    int entryTime,
    int exitTime,
    String entryId,
    String exitId,
    String tenantId) {
  return [
    {
      "id": entryId,
      "registerId": registerId,
      "individualId": attendeeList.individualId,
      "time": entryTime,
      "type": "ENTRY",
      "status": "ACTIVE",
      "tenantId": tenantId,
      "documentIds": []
    },
    {
      "id": exitId,
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

List<Map<String, dynamic>> createAttendanceLogPayload(
    TrackAttendanceTableData attendeeList,
    String registerId,
    int entryTime,
    int exitTime,
    String tenantId) {
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
