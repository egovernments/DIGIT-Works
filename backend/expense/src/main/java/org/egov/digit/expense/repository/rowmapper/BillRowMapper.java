package org.egov.digit.expense.repository.rowmapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.egov.digit.expense.web.models.Bill;
import org.egov.digit.expense.web.models.BillDetail;
import org.egov.digit.expense.web.models.LineItem;
import org.egov.digit.expense.web.models.Party;
import org.egov.digit.expense.web.models.enums.LineItemType;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import digit.models.coremodels.AuditDetails;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BillRowMapper implements ResultSetExtractor<List<Bill>>{

	@Autowired
	private ObjectMapper mapper;

	@Override
	public List<Bill> extractData(ResultSet rs) throws SQLException {
		Map<String, Bill> billMap = new LinkedHashMap<>();
		Map<String, BillDetail> billDetailMap = new LinkedHashMap<>();


		while (rs.next()) {
			String id = rs.getString("bill_id");
			String tenantId = rs.getString("bill_tenantId");
			Long billDate = rs.getLong("bill_billdate");
			Long dueDate = rs.getLong("bill_duedate");
			BigDecimal netPayableAmount = rs.getBigDecimal("bill_netpayableamount");
			BigDecimal netPaidAmount = rs.getBigDecimal("bill_netpaidamount");
			String businessService = rs.getString("bill_businessservice");
			String referenceId = rs.getString("bill_referenceid");
			Long fromPeriod = rs.getLong("bill_fromperiod");
			Long toPeriod = rs.getLong("bill_toperiod");
			String billStatus = rs.getString("bill_status");
			String paymentStatus = rs.getString("bill_paymentstatus");
			String billNumber = rs.getString("bill_billnumber");
			String createdby = rs.getString("bill_createdby");
			String lastmodifiedby = rs.getString("bill_lastModifiedBy");
			Long createdtime = rs.getLong("bill_createdTime");
			Long lastmodifiedtime = rs.getLong("bill_lastModifiedTime");

			AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
					.lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
					.build();


			JsonNode additionalDetails = getAdditionalDetail("bill_additionalDetails", rs);

			Bill bill=Bill.builder()
					.id(id)
					.tenantId(tenantId)
					.billDate(billDate)
					.dueDate(dueDate)
					.totalAmount(netPayableAmount)
					 .totalPaidAmount(netPaidAmount)
					.businessService(businessService)
					.referenceId(referenceId)
					.fromPeriod(fromPeriod)
					.toPeriod(toPeriod)
					.status(billStatus)
					.paymentStatus(paymentStatus)
					.billNumber(billNumber)
					.additionalDetails(additionalDetails)
					.auditDetails(auditDetails)
					.build();

			if (!billMap.containsKey(id)) {
				billMap.put(id, bill);
			}

			//one to many
			addBillDetails(rs, billMap.get(id),billDetailMap);

			//one to one
			addPayerDetails(rs,billMap.get(id));
		}
		return new ArrayList<>(billMap.values());
	}

	private void addBillDetails(ResultSet rs, Bill bill, Map<String,BillDetail> billDetailsMap) throws SQLException {

		Map<String,LineItem> lineItemsMap=new LinkedHashMap<>();
		BillDetail billDetail=null;

		String id = rs.getString("bd_id");
		String billId = rs.getString("bd_billid");
		if (StringUtils.isNotBlank(id) && billId.equalsIgnoreCase(bill.getId())) {
			String tenantId = rs.getString("bd_tenantId");
			String referenceId = rs.getString("bd_referenceid");
			String paymentStatus = rs.getString("bd_paymentstatus");
			Long fromPeriod = rs.getLong("bd_fromperiod");
			Long toPeriod = rs.getLong("bd_toperiod");
			String createdby = rs.getString("bd_createdby");
			String lastmodifiedby = rs.getString("bd_lastModifiedBy");
			Long createdtime = rs.getLong("bd_createdTime");
			Long lastmodifiedtime = rs.getLong("bd_lastModifiedTime");

			AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
					.lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
					.build();

			JsonNode additionalDetails = getAdditionalDetail("bd_additionalDetails", rs);

			billDetail = BillDetail.builder()
									.id(id)
									.billId(billId)
									.referenceId(referenceId)
									.paymentStatus(paymentStatus)
									.fromPeriod(fromPeriod)
									.toPeriod(toPeriod)
									.tenantId(tenantId)
									.auditDetails(auditDetails)
									.additionalDetails(additionalDetails)
									.build();

			if (!billDetailsMap.containsKey(id)) {
				billDetailsMap.put(id, billDetail);
			}

			addLineItems(rs,billDetailsMap.get(id),lineItemsMap);
			addPayeeDetails(rs,billDetailsMap.get(id));
		}

		//problem
		if (bill.getBillDetails() == null || bill.getBillDetails().isEmpty()) {
				List<BillDetail> billDetailList = new LinkedList<>();
				billDetailList.add(billDetail);
				bill.setBillDetails(billDetailList);
		} else {
			if (!billDetailsMap.containsKey(id)) {
				bill.getBillDetails().add(billDetail);
			}
		}

	}

	private void addLineItems(ResultSet rs, BillDetail billDetail,Map<String,LineItem> lineItemsMap) throws SQLException {
		LineItem lineItem=null;


		String id = rs.getString("li_id");
		String billDetailId = rs.getString("li_billdetailid");
		if (StringUtils.isNotBlank(id) && billDetailId.equalsIgnoreCase(billDetail.getId())) {
			String tenantId = rs.getString("li_tenantId");
			String headCode = rs.getString("li_headcode");
			BigDecimal amount = rs.getBigDecimal("li_amount");
			BigDecimal paidAmount = rs.getBigDecimal("li_paidamount");
			String type = rs.getString("li_type");
			String status = rs.getString("li_status");
			Boolean isLineItemPayable = rs.getBoolean("li_islineitempayable");
			String createdby = rs.getString("li_createdby");
			String lastmodifiedby = rs.getString("li_lastmodifiedby");
			Long createdtime = rs.getLong("li_createdtime");
			Long lastmodifiedtime = rs.getLong("li_lastmodifiedtime");

			AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
					.lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
					.build();

			JsonNode additionalDetails = getAdditionalDetail("li_additionaldetails", rs);

			 lineItem = LineItem.builder()
					.id(id)
					 .billDetailId(billDetailId)
					 .tenantId(tenantId)
					 .headCode(headCode)
					 .amount(amount)
					 .paidAmount(paidAmount)
					 .type(LineItemType.fromValue(type))
					 .status(status)
					 .isLineItemPayable(isLineItemPayable)
					 .additionalDetails(additionalDetails)
					 .auditDetails(auditDetails)
					.build();

			if (!lineItemsMap.containsKey(id)) {
				lineItemsMap.put(id, lineItem);
			}
		}

		if (billDetail.getLineItems() == null || billDetail.getLineItems().isEmpty()) {
			List<LineItem> lineItemList = new LinkedList<>();
			lineItemList.add(lineItem);
			billDetail.setLineItems(lineItemList);
		} else {
			billDetail.getLineItems().add(lineItem);
		}

	}

	private void addPayerDetails(ResultSet rs, Bill bill) throws SQLException {
		Party payer=null;

		String id = rs.getString("payer_id");
		String parentId = rs.getString("payer_parentid");

		if (StringUtils.isNotBlank(id) && parentId.equalsIgnoreCase(bill.getId())) {
			String tenantId = rs.getString("payer_tenantId");
			String type = rs.getString("payer_type");
			String status = rs.getString("payer_status");
			String identifier = rs.getString("payer_identifier");
			String createdby = rs.getString("payer_createdby");
			String lastmodifiedby = rs.getString("payer_lastmodifiedby");
			Long createdtime = rs.getLong("payer_createdtime");
			Long lastmodifiedtime = rs.getLong("payer_lastmodifiedtime");

			AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
					.lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
					.build();

			JsonNode additionalDetails = getAdditionalDetail("payer_additionaldetails", rs);

			payer = Party.builder()
					.id(id)
					.type(type)
					.status(status)
					.identifier(identifier)
					.parentId(parentId)
					.tenantId(tenantId)
					.additionalDetails(additionalDetails)
					.auditDetails(auditDetails)
					.build();
		}
			bill.setPayer(payer);
	}


	private void addPayeeDetails(ResultSet rs, BillDetail billDetail) throws SQLException {
		Party payee=null;

		String id = rs.getString("payee_id");
		String parentId = rs.getString("payee_parentid");

		if (StringUtils.isNotBlank(id) && parentId.equalsIgnoreCase(billDetail.getBillId())) {
			String tenantId = rs.getString("payee_tenantId");
			String type = rs.getString("payee_type");
			String status = rs.getString("payee_status");
			String identifier = rs.getString("payee_identifier");
			String createdby = rs.getString("payee_createdby");
			String lastmodifiedby = rs.getString("payee_lastmodifiedby");
			Long createdtime = rs.getLong("payee_createdtime");
			Long lastmodifiedtime = rs.getLong("payee_lastmodifiedtime");

			AuditDetails auditDetails = AuditDetails.builder().createdBy(createdby).createdTime(createdtime)
					.lastModifiedBy(lastmodifiedby).lastModifiedTime(lastmodifiedtime)
					.build();

			JsonNode additionalDetails = getAdditionalDetail("payee_additionaldetails", rs);

			payee = Party.builder()
					.id(id)
					.type(type)
					.status(status)
					.identifier(identifier)
					.parentId(parentId)
					.tenantId(tenantId)
					.additionalDetails(additionalDetails)
					.auditDetails(auditDetails)
					.build();
		}
		billDetail.setPayee(payee);
	}
	
	/**
	 * method parses the PGobject and returns the JSON node
	 * 
	 * @param columnName
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private JsonNode getAdditionalDetail(String columnName, ResultSet rs) throws SQLException {
		JsonNode additionalDetails = null;
		try {
			PGobject obj = (PGobject) rs.getObject(columnName);
			if (obj != null) {
				additionalDetails = mapper.readTree(obj.getValue());
			}
		} catch (IOException e) {
			log.error("Failed to parse additionalDetail object");
			throw new CustomException("PARSING_ERROR", "Failed to parse additionalDetail object");
		}
		if (additionalDetails.isEmpty())
			additionalDetails = null;
		return additionalDetails;
	}

}
