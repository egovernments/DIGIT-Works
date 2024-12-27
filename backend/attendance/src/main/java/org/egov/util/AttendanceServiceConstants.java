package org.egov.util;

import java.util.HashMap;
import java.util.Map;

public class AttendanceServiceConstants {
    public static final String MASTER_TENANTS = "tenants";
    public static final String MDMS_TENANT_MODULE_NAME = "tenant";
    public static final String LIMIT_OFFSET = "?limit=1000&offset=0";
    public static final String TWO_SESSIONS = "TWO_SESSIONS";
    public static final String TOTAL_COUNT = "totalCount";
    public static final Map<String, String> STATUS_MAP = Map.ofEntries(
        Map.entry("APPROVED", "APPROVED"),
        Map.entry("PENDINGFORAPPROVAL", "PENDINGFORAPPROVAL")
    );
}
