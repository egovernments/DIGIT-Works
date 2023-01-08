package org.egov.works.repository.querybuilder;

import org.apache.commons.lang3.StringUtils;
import org.egov.works.web.models.Project;
import org.egov.works.web.models.ProjectRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ProjectAddressQueryBuilder {

    private static final String FETCH_PROJECT_ADDRESS_QUERY = "SELECT prj.id as projectId, prj.tenant_id as project_tenantId, prj.project_number as project_projectNumber, prj.name as project_name, prj.project_type as project_projectType, prj.project_subtype as project_projectSubtype, " +
            " prj.department as project_department, prj.description as project_description, prj.reference_id as project_referenceId, prj.start_date as project_startDate, prj.end_date as project_endDate, " +
            "prj.is_task_enabled as project_isTaskEnabled, prj.parent as project_parent, prj.additional_details as project_additionalDetails, prj.is_deleted as project_isDeleted, prj.row_version as project_rowVersion, " +
            " prj.created_by as project_createdBy, prj.last_modified_by as project_lastModifiedBy, prj.created_time as project_createdTime, prj.last_modified_time as project_lastModifiedTime, " +
            "addr.id as addressId, addr.tenant_id as address_tenantId, addr.project_id as address_projectId, addr.door_no as address_doorNo, addr.latitude as address_latitude, addr.longitude as address_longitude, addr.location_accuracy as address_locationAccuracy, " +
            " addr.type as address_type, addr.address_line1 as address_addressLine1, addr.address_line2 as address_addressLine2, addr.landmark as address_landmark, addr.city as address_city, addr.pin_code as address_pinCode, " +
            " addr.building_name as address_buildingName, addr.street as address_street, addr.locality as address_locality, addr.created_by as address_createdBy, addr.last_modified_by as address_lastModifiedBy, addr.created_time as address_createdTime, addr.last_modified_time as address_lastModifiedTime " +
            " " +
            "from eg_pms_project prj " +
            "left join eg_pms_address addr " +
            "on prj.id = addr.project_id ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY project_lastModifiedTime DESC , projectId) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";
    public String getProjectSearchQuery(ProjectRequest projectRequest, Integer limit, Integer offset, String tenantId, Long lastChangedSince, Boolean includeDeleted, List<Object> preparedStmtList) {
        StringBuilder queryBuilder = null;
        queryBuilder = new StringBuilder(FETCH_PROJECT_ADDRESS_QUERY);
        Integer count = projectRequest.getProjects().size();

        for (Project project: projectRequest.getProjects()) {

            if (StringUtils.isNotBlank(tenantId)) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.tenant_id=? ");
                preparedStmtList.add(tenantId);
            }

            if (StringUtils.isNotBlank(project.getId())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.id =? ");
                preparedStmtList.add(project.getId());
            }

            if (StringUtils.isNotBlank(project.getProjectNumber())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.project_number =? ");
                preparedStmtList.add(project.getProjectNumber());
            }

            if (StringUtils.isNotBlank(project.getName())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.name =? ");
                preparedStmtList.add(project.getName());
            }

            if (StringUtils.isNotBlank(project.getProjectType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.project_type=? ");
                preparedStmtList.add(project.getProjectType());
            }

            if (StringUtils.isNotBlank(project.getProjectSubType())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.project_subtype=? ");
                preparedStmtList.add(project.getProjectSubType());
            }

            if (StringUtils.isNotBlank(project.getReferenceID())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.reference_id=? ");
                preparedStmtList.add(project.getReferenceID());
            }

            if (StringUtils.isNotBlank(project.getDepartment())) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.department=? ");
                preparedStmtList.add(project.getDepartment());
            }

            if (project.getStartDate() != null && project.getStartDate() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.start_date=? ");
                preparedStmtList.add(project.getStartDate());
            }

            if (project.getEndDate() != null && project.getEndDate() != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" prj.end_date=? ");
                preparedStmtList.add(project.getEndDate());
            }

            if (lastChangedSince != null && lastChangedSince != 0) {
                addClauseIfRequired(preparedStmtList, queryBuilder);
                queryBuilder.append(" ( prj.created_time > ? OR prj.last_modified_time > ? )");
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
        addClauseIfRequired(preparedStmtList, queryBuilder);
        if (includeDeleted) {
            queryBuilder.append(" (prj.is_deleted = true OR prj.is_deleted = false) ");
        } else {
            queryBuilder.append(" prj.is_deleted = false ");
        }
    }

    private void addORClause(Integer count, StringBuilder queryBuilder) {
        if (count > 0) {
            queryBuilder.append(" OR ( ");
        }
    }

    private static void addClauseIfRequired(List<Object> values, StringBuilder queryString) {
        if (values.isEmpty())
            queryString.append(" WHERE ( ");
        else {
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
