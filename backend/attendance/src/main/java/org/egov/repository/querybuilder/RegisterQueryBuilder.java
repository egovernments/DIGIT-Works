package org.egov.repository.querybuilder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.egov.config.AttendanceServiceConfiguration;
import org.egov.tracer.model.CustomException;
import org.egov.web.models.AttendanceRegisterSearchCriteria;
import org.egov.web.models.PaymentStatus;
import org.egov.web.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class RegisterQueryBuilder {

    private final AttendanceServiceConfiguration config;

    private static final String ATTENDANCE_REGISTER_SELECT_QUERY = " SELECT reg.id, " +
            "reg.tenantid, " +
            "reg.registernumber, " +
            "reg.name , " +
            "reg.startdate, " +
            "reg.enddate, " +
            "reg.status, " +
            "reg.additionaldetails, " +
            "reg.createdby, " +
            "reg.lastmodifiedby, " +
            "reg.createdtime, " +
            "reg.lastmodifiedtime, " +
            "reg.referenceid, " +
            "reg.servicecode, " +
            "reg.localitycode, " +
            "reg.paymentstatus " +
            "reg.reviewstatus " +
            "FROM eg_wms_attendance_register reg ";

    private static final String JOIN_STAFF = " JOIN eg_wms_attendance_staff staff ";
    private static final String JOIN_STAFF_CONDITION = " ON reg.id = staff.register_id ";

    private static final String JOIN_ATTENDEE = " JOIN eg_wms_attendance_attendee attendee ";
    private static final String JOIN_ATTENDEE_CONDITION = " ON reg.id = attendee.register_id ";

    private final String paginationWrapper = "SELECT * FROM " +
            "(SELECT *, DENSE_RANK() OVER (ORDER BY lastmodifiedtime DESC , id) offset_ FROM " +
            "({})" +
            " result) result_offset " +
            "WHERE offset_ > ? AND offset_ <= ?";

    @Autowired
    public RegisterQueryBuilder(AttendanceServiceConfiguration config) {
        this.config = config;
    }


    public String getAttendanceRegisterSearchQuery(AttendanceRegisterSearchCriteria searchCriteria, List<Object> preparedStmtList, boolean excludePaymentStatus) {

        log.info("Search criteria of attendance search : " + searchCriteria.toString());
        StringBuilder query = new StringBuilder(ATTENDANCE_REGISTER_SELECT_QUERY);

        if(!ObjectUtils.isEmpty(searchCriteria.getStaffId())) {
            query.append(JOIN_STAFF);
            query.append(JOIN_STAFF_CONDITION);
        }

        if(!ObjectUtils.isEmpty(searchCriteria.getAttendeeId())) {
            query.append(JOIN_ATTENDEE);
            query.append(JOIN_ATTENDEE_CONDITION);
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getTenantId())) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.tenantid like ? ");
            preparedStmtList.add(searchCriteria.getTenantId()+"%");
        }

        List<String> registerIds = searchCriteria.getIds();
        if (registerIds != null && !registerIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.id IN (").append(createQuery(registerIds)).append(")");
            preparedStmtList.addAll(registerIds);
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getRegisterNumber())) {
            String registerNumber = searchCriteria.getRegisterNumber();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.registernumber = ? ");
            preparedStmtList.add(registerNumber);
        }
        List<String> referenceId = searchCriteria.getReferenceId();
        if (referenceId!=null && !referenceId.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.referenceid IN (").append(createQuery(referenceId)).append(")");
            preparedStmtList.addAll(referenceId);
        }

        List<String> referenceIds = searchCriteria.getReferenceIds();
        if (referenceIds != null && !referenceIds.isEmpty()) {
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.referenceid IN (").append(createQuery(referenceIds)).append(")");
            preparedStmtList.addAll(referenceIds);
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getServiceCode())) {
            String serviceCode = searchCriteria.getServiceCode();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.servicecode = ? ");
            preparedStmtList.add(serviceCode);
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getName())) {
            String name = searchCriteria.getName();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.name = ? ");
            preparedStmtList.add(name);
        }

        if (searchCriteria.getFromDate() != null) {
            addClauseIfRequired(query, preparedStmtList);

            //If user does not specify toDate, take today's date as toDate by default.
            if (searchCriteria.getToDate() == null) {
                searchCriteria.setToDate(BigDecimal.valueOf(Instant.now().toEpochMilli()));
            }

            query.append(" reg.startdate BETWEEN ? AND ?");
            preparedStmtList.add(searchCriteria.getFromDate());
            preparedStmtList.add(searchCriteria.getToDate());

        } else {
            //if only toDate is provided as parameter without fromDate parameter, throw an exception.
            if (searchCriteria.getToDate() != null) {
                throw new CustomException("INVALID_SEARCH_PARAM", "Cannot specify toDate without a fromDate");
            }
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getStatus())) {
            Status status = searchCriteria.getStatus();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.status = ? ");
            preparedStmtList.add(status.toString());
        }

        if(!ObjectUtils.isEmpty(searchCriteria.getStaffId())) {
            String staffId = searchCriteria.getStaffId();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" staff.individual_id = ? ");
            preparedStmtList.add(staffId);
        }

        if(!ObjectUtils.isEmpty(searchCriteria.getAttendeeId())) {
            String attendeeId = searchCriteria.getAttendeeId();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" attendee.individual_id = ? ");
            preparedStmtList.add(attendeeId);
        }

        if(!ObjectUtils.isEmpty(searchCriteria.getLocalityCode())) {
            String localityCode = searchCriteria.getLocalityCode();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.localitycode = ? ");
            preparedStmtList.add(localityCode);
        }

        if (!ObjectUtils.isEmpty(searchCriteria.getReviewStatus()) && !excludePaymentStatus) {
            String reviewStatus = searchCriteria.getReviewStatus();
            addClauseIfRequired(query, preparedStmtList);
            query.append(" reg.reviewstatus = ? ");
            preparedStmtList.add(reviewStatus);
        }


        addOrderByClause(query, searchCriteria);
        //addLimitAndOffset(query, searchCriteria, preparedStmtList);
        return query.toString();
    }

    public String addPaginationWrapper(String query, List<Object> preparedStmtList,
                                       AttendanceRegisterSearchCriteria criteria){
        int limit = config.getAttendanceRegisterDefaultLimit();
        int offset = config.getAttendanceRegisterDefaultOffset();

        String finalQuery = paginationWrapper.replace("{}",query);

        if(criteria.getLimit()!=null && criteria.getLimit()<=config.getAttendanceRegisterMaxLimit())
            limit = criteria.getLimit();

        if(criteria.getLimit()!=null && criteria.getLimit()>config.getAttendanceRegisterMaxLimit())
            limit = config.getAttendanceRegisterMaxLimit();

        if(criteria.getOffset()!=null)
            offset = criteria.getOffset();

        preparedStmtList.add(offset);
        preparedStmtList.add(limit+offset);

        return finalQuery;
    }

    private void addOrderByClause(StringBuilder queryBuilder, AttendanceRegisterSearchCriteria criteria) {
        //default
        if (criteria.getSortBy() == null || StringUtils.isEmpty(criteria.getSortBy().name())) {
            queryBuilder.append(" ORDER BY reg.lastmodifiedtime ");
        } else {
            switch (AttendanceRegisterSearchCriteria.SortBy.valueOf(criteria.getSortBy().name())) {
                case fromDate:
                    queryBuilder.append(" ORDER BY reg.startdate ");
                    break;
                case toDate:
                    queryBuilder.append(" ORDER BY reg.enddate ");
                    break;
                default:
                    queryBuilder.append(" ORDER BY reg.lastmodifiedtime ");
                    break;
            }
        }

        if (criteria.getSortOrder() == AttendanceRegisterSearchCriteria.SortOrder.ASC)
            queryBuilder.append(" ASC ");
        else queryBuilder.append(" DESC ");
    }

    private void addLimitAndOffset(StringBuilder queryBuilder, AttendanceRegisterSearchCriteria criteria, List<Object> preparedStmtList) {
        queryBuilder.append(" OFFSET ? ");
        preparedStmtList.add(criteria.getOffset());

        queryBuilder.append(" LIMIT ? ");
        preparedStmtList.add(criteria.getLimit());

    }

    private String createQuery(Collection<String> ids) {
        StringBuilder builder = new StringBuilder();
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            builder.append(" ? ");
            if (i != length - 1) builder.append(",");
        }
        return builder.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> preparedStmtList) {
        if (preparedStmtList.isEmpty()) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }
    }
}
