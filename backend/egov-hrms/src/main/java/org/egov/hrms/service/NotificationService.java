package org.egov.hrms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.utils.MultiStateInstanceUtil;
import org.egov.hrms.model.Employee;
import org.egov.hrms.model.SMSRequest;
import org.egov.hrms.producer.HRMSProducer;
import org.egov.hrms.repository.RestCallRepository;
import org.egov.hrms.utils.HRMSConstants;
import org.egov.hrms.web.contract.EmployeeRequest;
import org.egov.hrms.web.contract.RequestInfoWrapper;
import org.egov.hrms.web.contract.WorksSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	
	@Autowired
	private HRMSProducer producer;
	
	@Autowired
	private RestCallRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MultiStateInstanceUtil centralInstanceUtil;

	@Value("${kafka.topics.notification.sms}")
    private String smsTopic;
    
    @Value("${egov.hrms.employee.app.link}")
    private String appLink;
    
	@Value("${egov.localization.host}")
	private String localizationHost;

	@Value("${egov.localization.search.endpoint}")
	private String localizationSearchEndpoint;

	@Value("${egov.otp.host}")
	private String otpHost;

	@Value("${egov.otp.create.endpoint}")
	private String otpCreateEndpoint;

	@Value("${egov.environment.domain}")
	private String envHost;


	@Value("${kafka.topics.works.notification.sms.name}")
	private String muktaNotificationTopic;
	@Value("${sms.isAdditonalFieldRequired}")
	private boolean isAdditonalFieldRequired;

	/**
	 * Sends notification by putting the sms content onto the core-sms topic
	 * 
	 * @param request
	 * @param pwdMap
	 */
	public void sendNotification(EmployeeRequest request, Map<String, String> pwdMap) {
		Map<String,Object> addtionalFields= new HashMap<>();
		String message = getMessage(request,HRMSConstants.HRMS_EMP_CREATE_LOCLZN_CODE,addtionalFields);
		String tenantId = request.getEmployees().get(0).getTenantId(); 
				
		if(StringUtils.isEmpty(message)) {
			log.info("SMS content has not been configured for this case");
			return;
		}
		for(Employee employee: request.getEmployees()) {
			message = buildMessage(employee, message, pwdMap);
			checkAdditionalFieldAndPushONSmsTopic(tenantId,message,addtionalFields,employee.getUser().getMobileNumber());
		}
	}

	public void sendReactivationNotification(EmployeeRequest request){
		Map<String,Object> addtionalFields= new HashMap<>();
		String message = getMessage(request,HRMSConstants.HRMS_EMP_REACTIVATE_LOCLZN_CODE,addtionalFields);
		String tenantId = request.getEmployees().get(0).getTenantId(); 
		if(StringUtils.isEmpty(message)) {
			log.info("SMS content has not been configured for this case");
			return;
		}
		RequestInfo requestInfo = request.getRequestInfo();
		for(Employee employee: request.getEmployees()) {
			if(employee.getReactivationDetails()!=null && employee.getReActivateEmployee()){
				String OTP = getOTP(employee,requestInfo);
				String link = envHost + "employee/user/otp";

				message = message.replace("{Employee Name}",employee.getUser().getName()).replace("{Username}",employee.getCode());
				message = message.replace("{date}",(employee.getReactivationDetails().get(0).getEffectiveFrom()).toString());
				message = message.replace("{password}",OTP).replace("{link}",link);

				SMSRequest smsRequest = SMSRequest.builder().mobileNumber(employee.getUser().getMobileNumber()).message(message).build();
				log.info(message);
				producer.push(tenantId, smsTopic, smsRequest);
			}

		}

	}

	public String getOTP(Employee employee,RequestInfo requestInfo){
		Map<String, Object> OTPRequest= new HashMap<>();
		Map<String, Object> otp= new HashMap<>();
		otp.put("mobileNumber",employee.getUser().getMobileNumber());
		otp.put("type","passwordreset");
		otp.put("tenantId",employee.getTenantId());
		otp.put("userType","EMPLOYEE");
		otp.put("identity",employee.getUser().getMobileNumber());

		OTPRequest.put("RequestInfo",requestInfo);
		OTPRequest.put("otp",otp);

		Object response = null;
		StringBuilder url = new StringBuilder();
		url.append(otpHost).append(otpCreateEndpoint);
		try {
			response = restTemplate.postForObject(url.toString(), OTPRequest, Map.class);
		}catch(Exception e) {
			log.error("Exception while creating user: ", e);
			return null;
		}
		String result = JsonPath.read(response, "$.otp.otp");
		return result;
	}
	
	/**
	 * Gets the message from localization
	 * 
	 * @param request
	 * @return
	 */
	public String getMessage(EmployeeRequest request,String msgCode,Map<String,Object> addtionalFields) {
		String tenantId = request.getEmployees().get(0).getTenantId().split("\\.")[0];
		RequestInfo requestInfo=request.getRequestInfo();
		String locale = "en_IN";
		if(requestInfo.getMsgId().split("\\|").length > 1)
			locale = requestInfo.getMsgId().split("\\|")[1];
		Map<String, Map<String, String>> localizedMessageMap = getLocalisedMessages(request.getRequestInfo(), tenantId,
				locale, HRMSConstants.HRMS_LOCALIZATION_MODULE_CODE);
		if(isAdditonalFieldRequired){
			setAdditionalFields(requestInfo,tenantId,msgCode,addtionalFields);
		}
		return localizedMessageMap.get(locale +"|"+tenantId).get(msgCode);
	}
	
	/**
	 * Builds msg based on the format
	 * 
	 * @param employee
	 * @param message
	 * @param pwdMap
	 * @return
	 */
	public String buildMessage(Employee employee, String message, Map<String, String> pwdMap) {
		message = message.replace("$username", employee.getCode()).replace("$password", pwdMap.get(employee.getUuid()))
				.replace("$employeename", employee.getUser().getName())
				.replace("{username}", employee.getCode()).replace("{password}", pwdMap.get(employee.getUuid()));
		message = message.replace("$applink", appLink);
		return message;
	}
	
	/**
	 * Creates a cache for localization that gets refreshed at every call.
	 * 
	 * @param requestInfo
	 * @param tenantId
	 * @param locale
	 * @param module
	 * @return
	 */
	public Map<String, Map<String, String>> getLocalisedMessages(RequestInfo requestInfo, String tenantId, String locale, String module) {
		Map<String, Map<String, String>> localizedMessageMap = new HashMap<>();
		Map<String, String> mapOfCodesAndMessages = new HashMap<>();
		StringBuilder uri = new StringBuilder();
		RequestInfoWrapper requestInfoWrapper = new RequestInfoWrapper();
		requestInfoWrapper.setRequestInfo(requestInfo);
		tenantId = centralInstanceUtil.getStateLevelTenant(tenantId);
		uri.append(localizationHost).append(localizationSearchEndpoint).append("?tenantId=" + tenantId)
				.append("&module=" + module).append("&locale=" + locale);
		List<String> codes = null;
		List<String> messages = null;
		Object result = null;
		try {
			result = repository.fetchResult(uri, requestInfoWrapper);
			codes = JsonPath.read(result, HRMSConstants.HRMS_LOCALIZATION_CODES_JSONPATH);
			messages = JsonPath.read(result, HRMSConstants.HRMS_LOCALIZATION_MSGS_JSONPATH);
		} catch (Exception e) {
			log.error("Exception while fetching from localization: " + e);
		}
		if (null != result) {
			for (int i = 0; i < codes.size(); i++) {
				mapOfCodesAndMessages.put(codes.get(i), messages.get(i));
			}
			localizedMessageMap.put(locale + "|" + tenantId, mapOfCodesAndMessages);
		}
		
		return localizedMessageMap;
	}

	private void setAdditionalFields(RequestInfo requestInfo,String tenantId, String localizationCode, Map<String,Object> addtionalFields){
		addtionalFields.put("templateCode",localizationCode);
		addtionalFields.put("requestInfo",requestInfo);
		addtionalFields.put("tenantId",tenantId);
	}


	private void checkAdditionalFieldAndPushONSmsTopic(String tenantId, String customizedMessage , Map<String, Object> addtionalFields,String mobileNumber){
		if(!addtionalFields.isEmpty()){
			WorksSmsRequest smsRequest= WorksSmsRequest.builder().message(customizedMessage).additionalFields(addtionalFields)
					.mobileNumber(mobileNumber).build();
			log.info("SMS message with additonal Fields:::::" + smsRequest.toString());
			producer.push(tenantId,muktaNotificationTopic, smsRequest);

		}else{
			SMSRequest smsRequest = SMSRequest.builder().mobileNumber(mobileNumber).message(customizedMessage).build();
			log.info("SMS message without additonalFields:::::" + smsRequest.toString());
			producer.push(tenantId, smsTopic, smsRequest);
		}
	}

}
