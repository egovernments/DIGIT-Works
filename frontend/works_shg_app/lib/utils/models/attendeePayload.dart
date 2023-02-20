List<Map<String, dynamic>> deleteAttendeePayLoad(String registerId,
    String individualId, int deEnrollmentDate, String tenantId) {
  return [
    {
      "registerId": registerId.toString(),
      "individualId": individualId.toString(),
      "enrollmentDate": null,
      "denrollmentDate": deEnrollmentDate,
      "tenantId": tenantId.toString()
    }
  ];
}
