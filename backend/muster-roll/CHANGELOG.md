
All notable changes to this module will be documented in this file.

## 1.1.0 - 2025-01-27

### Changes
- Changed notification to be configurable
- Configurable start day as Monday validation
- Configurable bank account for muster roll health
- Configurable recomputing attendance for muster roll update
- Added configurable support for workflow in muster roll

### New features
- Update attendance register on approval of muster roll
- Added configurable validation for number of days for attendance for muster roll apis
- new /v2/_search API to search muster roll, to be able to use large request body instead of params. ie. bills with support of search using multiple register ids

### Backward Compatibility
- All changes are configurable and backward compatible

## 0.1.0 - 2023-04-17

- Base version