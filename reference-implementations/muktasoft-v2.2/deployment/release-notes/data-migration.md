# Data Migration

1. Remove the privacy data from additional details in the attendance tables:

```
UPDATE eg_wms_attendance_attendee SET additionaldetails = additionaldetails - 'gender' - 'identifierId' - 'individualGaurdianName' WHERE additionaldetails != 'null';
```

```
UPDATE eg_wms_attendance_summary SET additionaldetails = additionaldetails -'gender' -'mobileNo' -'fatherName' -'bankDetails' -'accountType' -'aadharNumber' -'accountHolderName' WHERE additionaldetails != 'null';
```

2. REPORT\_VIEWER is not currently used in MUKTASoft-v2.2 version, so remove the 'REPORT\_VIEWER' role from mdms-v2

```
delete from eg_mdms_data where schemacode = 'ACCESSCONTROL-ROLES.roles' and uniqueidentifier = 'REPORT_VIEWER';
```

3. Update the roles for existing CBO to access the masked information&#x20;
   * Add roles in the individual tables

```
UPDATE individual SET roles = '[{"code": "ORG_ADMIN", "name": "Organization admin", "tenantId": null, "description": null},{"code": "VIEW_ORG_UNMASKED", "name": "View ORG Unmasked", "tenantId": null, "description": null},{"code": "VIEW_DED_UNMASKED", "name": "View DED Unmasked", "tenantId": null, "description": null},{"code": "VIEW_WS_UNMASKED", "name": "View Ws Unmasked", "tenantId": null, "description": null}]'::jsonb WHERE id in (SELECT ocd.individual_id FROM eg_org_function ofn JOIN eg_org_contact_detail ocd ON ofn.org_id = ocd.org_id JOIN individual i ON ocd.individual_id =
 i.id
 WHERE ofn.category LIKE 'CBO%' AND ofn.is_active = true and i.roles != 'null' and roles != '[{"code": "CITIZEN", "name": "CITIZEN", "tenantId": null, "description": null}]');
```

* Insert role VIEW\_ORG\_UNMASKED

```
INSERT INTO eg_userrole_v1  (user_id, role_code, role_tenantId, user_tenantId, lastmodifieddate) SELECT DISTINCT t.user_id, 'VIEW_ORG_UNMASKED', 'od', 'od', '2024-11-12 15:57:41'::timestamp FROM eg_userrole_v1 t WHERE t.user_id in (SELECT i.userid::bigint FROM eg_org_function ofn JOIN eg_org_contact_detail ocd ON ofn.org_id = ocd.org_id JOIN individual i ON ocd.individual_id = i.id WHERE ofn.category LIKE 'CBO%' AND ofn.is_active = true and roles != '[{"code": "CITIZEN", "name": "CITIZEN", "tenantId": null, "description": null}]') AND NOT EXISTS (SELECT 1 FROM eg_userrole_v1 sub WHERE sub.user_id = t.user_id AND sub.role_code = 'VIEW_ORG_UNMASKED');
```

* Insert role VIEW\_DED\_UNMASKED

```
INSERT INTO eg_userrole_v1 (user_id, role_code, role_tenantId, user_tenantId, lastmodifieddate) SELECT DISTINCT t.user_id, 'VIEW_DED_UNMASKED', 'od', 'od', '2024-11-12 15:57:41'::timestamp FROM eg_userrole_v1 t WHERE t.user_id in (SELECT i.userid::bigint FROM eg_org_function ofn JOIN eg_org_contact_detail ocd ON ofn.org_id = ocd.org_id JOIN individual i ON ocd.individual_id = i.id WHERE ofn.category LIKE 'CBO%' AND ofn.is_active = true and roles != '[{"code": "CITIZEN", "name": "CITIZEN", "tenantId": null, "description": null}]') AND NOT EXISTS (SELECT 1 FROM eg_userrole_v1 sub WHERE sub.user_id = t.user_id AND sub.role_code = 'VIEW_DED_UNMASKED');
```

* Insert role VIEW\_WS\_UNMASKED

```
INSERT INTO eg_userrole_v1 (user_id, role_code, role_tenantId, user_tenantId, lastmodifieddate) SELECT DISTINCT t.user_id, 'VIEW_WS_UNMASKED', 'od', 'od', '2024-11-12 15:57:41'::timestamp FROM eg_userrole_v1 t WHERE t.user_id in (SELECT i.userid::bigint FROM eg_org_function ofn JOIN eg_org_contact_detail ocd ON ofn.org_id = ocd.org_id JOIN individual i ON ocd.individual_id = i.id WHERE ofn.category LIKE 'CBO%' AND ofn.is_active = true and roles != '[{"code": "CITIZEN", "name": "CITIZEN", "tenantId": null, "description": null}]') AND NOT EXISTS (SELECT 1 FROM eg_userrole_v1 sub WHERE sub.user_id = t.user_id AND sub.role_code = 'VIEW_WS_UNMASKED');
```

