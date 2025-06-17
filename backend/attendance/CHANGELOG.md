
All notable changes to this module will be documented in this file.

## 1.1.1 - 2025-06-13

### Changes

- Added new tag field in eg_wms_attendance_attendee table with supporting index
- Introduced support for attendee tagging and tag-based filtering
- Enhanced attendee retrieval logic to support:
  - Filtering attendees based on explicit tags
  - Resolving and including attendees with matching tags when includeTaggedAttendees is true
- Added new /_updateTag API to update attendee tags

### Configuration Enhancements

- Extended AttendanceRegisterSearchCriteria with:
  - tags – list of tags to filter attendees
  - includeTaggedAttendees – boolean to fetch all attendees with the same tags as a specified individual

## 1.1.0 - 2025-01-27

### Changes

- Added support for attendance register review status
- Introduced locality code and boundary validation for attendance registers
- Enhanced project and staff management capabilities
- Added configurable open search and registration permissions
- Added pagination and filtering capabilities
- Improved error handling and validation mechanisms

### Configuration Enhancements
- New configuration options for attendance register search and review
- Added boundary service integration
- Expanded staff type and permission management


## 1.0.2 - 2024-03-04

- Added user Id list changes in Individual search
- Upgraded PostgresSQL Driver version to 42.7.1
- Upgraded Flyway base image version to 10.7.1

## 1.0.1 - 2024-03-04

- Implemented Offline Enabled Functionality in Attendance Module

## 0.1.0 - 2023-04-17

- Base version
