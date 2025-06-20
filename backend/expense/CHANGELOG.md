
All notable changes to this module will be documented in this file.

## 1.2.0 - 2025-06-20

- Added tenant-based schema resolution using MultiStateInstanceUtil and schema placeholders in queries.
- Updated repository methods to require tenant ID and handle InvalidTenantIdException.
- Modified migration scripts for central instance compatibility.

## 1.0.0 - 2023-04-17

- Base version

## 1.1.0 - 2025-01-27

- Added localityCode to bill
- Added like query in bill referenceId search for health context
- Added search from and to date in bill search criteria
- Changed search query from INNER JOIN to LEFT join to handle empty lineItems in bill 