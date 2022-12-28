package org.egov.works.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ProjectAddressQueryBuilder {

    private static final String FETCH_PROJECT_ADDRESS_QUERY = "SELECT prj.id as project_id, prj.tenantid as project_tenantid, prj.projecttype as project_projecttype, prj.projectsubtype as project_projectsubtype, " +
            " prj.department as project_department, prj.description as project_description, prj.reference_id as project_referenceid, prj.startdate as project_startdate, prj.enddate as project_enddate, " +
            "prj.istaskenabled as project_istaskenabled, prj.parent as project_parent, prj.additionaldetails as project_additionaldetails, prj.isdeleted as project_isdeleted, prj.rowversion as project_rowversion, " +
            " prj.createdby as project_createdby, prj.lastmodifiedby as project_lastmodifiedby, prj.createdtime as project_createdtime, prj.lastmodifiedtime as project_lastmodifiedtime, " +
            "addr.id as address_id, addr.tenantid as address_tenantid, addr.project_id as address_projectid, addr.doorno as address_doorno, addr.latitude as address_latitude, addr.longitude as address_longitude, addr.locationaccuracy as address_locationaccuracy, " +
            " addr.type as address_type, addr.addressline1 as address_addressline1, addr.addressline2 as address_addressline2, addr.landmark as address_landmark, addr.city as address_city, addr.pincode as address_pincode, " +
            " addr.buildingname as address_buildingname, addr.street as address_street, addr.createdby as address_createdby, addr.lastmodifiedby as address_lastmodifiedby, addr.createdtime as address_createdtime, addr.lastmodifiedtime as address_lastmodifiedtime " +
            " " +
            "from eg_pms_project prj " +
            "left join eg_pms_address addr " +
            "on prj.id = addr.project_id ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY project_lastmodifiedtime DESC , project_id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";
    public String getProjectSearchQuery(ProjectRequest projectRequest, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_PROJECT_ADDRESS_QUERY);
        Integer count = projectRequest.getProjects().size();

        for (Project project: projectRequest.getProjects()) {

            if (project.getId() != null && !project.getId().isEmpty()) {
                addClauseIfRequired(preparedStmtList, queryBuilder, true);
                queryBuilder.append("prj.id =? ");
                preparedStmtList.add(project.getId());
            }

            if (StringUtils.isNotBlank(tenantId)) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.tenantid=? ");
                preparedStmtList.add(tenantId);
            }

            if (StringUtils.isNotBlank(project.getProjectType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.projecttype=? ");
                preparedStmtList.add(project.getProjectType());
            }

            if (StringUtils.isNotBlank(project.getProjectSubType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.projectsubtype=? ");
                preparedStmtList.add(project.getProjectSubType());
            }

            if (StringUtils.isNotBlank(project.getReferenceID())) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.reference_id=? ");
                preparedStmtList.add(project.getReferenceID());
            }

            if (StringUtils.isNotBlank(project.getDepartment())) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.department=? ");
                preparedStmtList.add(project.getDepartment());
            }

            if (project.getStartDate() != null && project.getStartDate() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.startdate=? ");
                preparedStmtList.add(project.getStartDate());
            }

            if (project.getEndDate() != null && project.getEndDate() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" prj.enddate=? ");
                preparedStmtList.add(project.getEndDate());
            }

            if (lastChangedSince != null && lastChangedSince != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder, false);
                queryBuilder.append(" ( prj.createdtime > ? OR prj.lastmodifiedtime > ? )");
                preparedStmtList.add(lastChangedSince);
                preparedStmtList.add(lastChangedSince);
            }

            addIsDeletedCondition(preparedStmtList, queryBuilder, includeDeleted);

            queryBuilder.append(" )");
            count--;
            addORClause(count, queryBuilder);
        }

        return addPaginationWrapper(queryBuilder.toString(), preparedStmtList, limit, offset);
    }

    private void addIsDeletedCondition(List<Object> preparedStmtList, StringBuilder queryBuilder, Boolean includeDeleted) {
        addClauseIfRequired(preparedStmtList, queryBuilder, false);
        if (includeDeleted) {
            queryBuilder.append(" (prj.isdeleted = true OR prj.isdeleted = false) ");
        } else {
            queryBuilder.append(" prj.isdeleted = false ");
        }
    }

    private void addORClause(Integer count, StringBuilder queryBuilder) {
        if (count > 0) {
            queryBuilder.append(" OR ( ");
        }
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString, Boolean isFirstAttribute) {
        if (values.isEmpty())
            queryString.append(" WHERE ( ");
        else if (!isFirstAttribute) {
            queryString.append(" AND");
        }
    }

    private String addPaginationWrapper(String query,List<Object> preparedStmtList, Integer limit, Integer offset){
        String finalQuery = paginationWrapper.replace("{}",query);

        preparedStmtList.add(offset);
        preparedStmtList.add(limit+offset);

        return finalQuery;
    }

}
