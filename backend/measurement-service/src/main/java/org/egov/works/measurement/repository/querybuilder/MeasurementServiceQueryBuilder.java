package org.egov.works.measurement.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MeasurementServiceQueryBuilder {
    private static final String BASE_MEASUREMENT_QUERY = "SELECT m.id as id, m.tenantId as tenantId, m.mbNumber as mbNumber, m.phyRefNumber as phyRefNumber, m.referenceId as referenceId, " +
            "m.entryDate as entryDate, m.isActive as isActive, m.createdby as createdby, m.lastmodifiedby as lastmodifiedby, " +
            "m.createdtime as createdtime, m.lastmodifiedtime as lastmodifiedtime, m.additionalDetails as additionalDetails, " +

            "md.id as mdid, md.targetId as targetId, md.mbNumber as mdmbNumber, md.isActive as mdisActive, md.description as mddescription, " +
            "md.createdby as mdcreatedby, md.lastmodifiedby as mdlastmodifiedby, md.createdtime as mdcreatedtime, md.lastmodifiedtime as mdlastmodifiedtime, " +

            "mm.id as mmid, mm.length as mmlength, mm.breadth as mmbreadth, mm.height as mmheight, mm.numOfItems as mmnumOfItems, mm.currentValue as mmcurrentValue, " +

            "mm.createdby as mmcreatedby, mm.lastmodifiedby as mmlastmodifiedby, mm.createdtime as mmcreatedtime, mm.lastmodifiedtime as mmlastmodifiedtime, " +

            "wf.action as wfaction, wf.comment as wfcomment, wf.assignees as wfassignees " + // Include workflow fields
            "FROM eg_mb_measurements m " +
            "LEFT JOIN eg_mb_measurement_details md ON m.id = md.referenceId " +
            "LEFT JOIN eg_mb_measurement_measures mm ON md.id = mm.id " +
            "LEFT JOIN eg_mb_measurement_workflow wf ON m.id = wf.id "; // Join with workflow table

}
