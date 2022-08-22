import { WSService } from "../../elements/WS";
import { PTService } from "../../elements/PT";
import { PaymentService } from "../../elements/Payment";
import { MdmsService } from "../../elements/MDMS";
import { WorkflowService } from "../../elements/WorkFlow";
import cloneDeep from "lodash/cloneDeep";

const stringReplaceAll = (str = "", searcher = "", replaceWith = "") => {
  if (searcher == "") return str;
  while (str.includes(searcher)) {
    str = str.replace(searcher, replaceWith);
  }
  return str;
};

const convertEpochToDate = (dateEpoch) => {
  if (dateEpoch) {
    const dateFromApi = new Date(dateEpoch);
    let month = dateFromApi.getMonth() + 1;
    let day = dateFromApi.getDate();
    let year = dateFromApi.getFullYear();
    month = (month > 9 ? "" : "0") + month;
    day = (day > 9 ? "" : "0") + day;
    return `${day}/${month}/${year}`;
  } else {
    return null;
  }
};

const getAddress = (address, t) => {
  const result =  `${address?.doorNo ? `${address?.doorNo}, ` : ""} ${address?.street ? `${address?.street}, ` : ""}${
    address?.landmark ? `${address?.landmark}, ` : ""
  }${t(Digit.Utils.pt.getMohallaLocale(address?.locality.code, address?.tenantId))}, ${t(Digit.Utils.pt.getCityLocale(address?.tenantId))}${
    address?.pincode && t(address?.pincode) ? `, ${address.pincode}` : " "
  }`;
  return result
};

const getOwnerNames = (propertyData) => {
  const getActiveOwners = propertyData?.owners?.filter(owner => owner?.active);
  const getOwnersList = getActiveOwners?.map(activeOwner => activeOwner?.name)?.join(",");
  return getOwnersList ? getOwnersList : t("NA");
}

const checkUserExist = async (userInfo) => {
  const checkList = ["SW_FIELD_INSPECTOR", "WS_APPROVER", "WS_FIELD_INSPECTOR", "SW_APPROVER"];
  const filterList = [];
  checkList?.map(list => {
    let filterData = userInfo?.info?.roles?.filter(role => role?.code == list);
    if(filterData?.length > 0) {
      filterList.push(filterData?.[0]?.code);
    }
  })
  return filterList;
}

const checkExistStatus = async (processInstances) => {
  const checkStatus = processInstances?.filter(state => state?.state?.applicationStatus == "PENDING_FOR_PAYMENT");
  return checkStatus?.length > 0 ? checkStatus : [];
}

const checkFeeEstimateVisible = async (wsDatas) => {
  const dataDetails = wsDatas?.[0]?.applicationType?.includes("NEW");
  return dataDetails;
} 

export const WSSearch = {
  application: async (tenantId, filters = {}, serviceType) => {
    const response = await WSService.search({ tenantId, filters: { ...filters }, businessService: serviceType === "WATER" ? "WS" : "SW" });
    return response;
  },

  property: async (tenantId, propertyfilter = {}) => {
    const response = await PTService.search({ tenantId, filters: propertyfilter, auth: true });
    return response;
  },

  searchBills: async (tenantId, consumercodes) => {
    const response = await Digit.PaymentService.searchBill(tenantId, { consumerCode: consumercodes, Service: "WS.ONE_TIME_FEE" });
    return response;
  },

  searchAmendment: async (tenantId, consumercodes, businessService="WS") => {
    const response = await Digit.PaymentService.searchAmendment(tenantId, { amendmentId: consumercodes, businessService });
    return response;
  },

  workflowDataDetails: async (tenantId, businessIds) => {
    const response = await Digit.WorkflowService.getByBusinessId(tenantId, businessIds);
    return response;
  },

  wsEstimationDetails: async (data, serviceType) => {
    let businessService = serviceType === "WATER" ? "WS" : "SW";
    const response = await WSService.wsCalculationEstimate(data, businessService);
    return response;
  },

  colletionData: async ({tenantId, serviceTypeOfData, collectionNumber}) => {
    const businessService = serviceTypeOfData;
    const consumerCodes = collectionNumber;
    const response = await Digit.PaymentService.recieptSearch(tenantId, businessService, {consumerCodes: consumerCodes });
    return response;
  },

  fetchBillData: async ({tenantId, serviceTypeOfData, collectionNumber}) => {
    const businessService = serviceTypeOfData;
    const consumerCode = collectionNumber;
    const response = await Digit.PaymentService.fetchBill(tenantId, {
      businessService: businessService,
      consumerCode: consumerCode,
    });
    return response;
  },



  applicationDetails: async (t, tenantId, applicationNumber, serviceType = "WATER", userInfo, config = {}) => {

    const filters = { applicationNumber };

    let propertyids = "",
      consumercodes = "",
      businessIds = "";

    const response = await WSSearch.application(tenantId, filters, serviceType);

    const wsData = cloneDeep(serviceType == "WATER" ? response?.WaterConnection : response?.SewerageConnections);

    wsData?.forEach((item) => {
      propertyids = propertyids + item?.propertyId + ",";
      consumercodes = consumercodes + item?.applicationNo + ",";
    });

    let propertyfilter = { propertyIds: propertyids.substring(0, propertyids.length - 1) };

    if (propertyids !== "" && filters?.locality) propertyfilter.locality = filters?.locality;

    config = { enabled: propertyids !== "" ? true : false };

    const properties = await WSSearch.property(tenantId, propertyfilter);

    const billData = await WSSearch.searchBills(tenantId, consumercodes);

    if (filters?.applicationNumber) businessIds = filters?.applicationNumber;

    const workflowDetails = await WSSearch.workflowDataDetails(tenantId, businessIds);

    const isVisible = await checkFeeEstimateVisible(cloneDeep(wsData));

    const adhocRebateData = sessionStorage.getItem("Digit.ADHOC_ADD_REBATE_DATA");
    const parsedAdhocRebateData = adhocRebateData ? JSON.parse(adhocRebateData) : "";
    if (wsData?.[0]?.additionalDetails && parsedAdhocRebateData?.value) {
      // if (parsedAdhocRebateData?.value?.adhocPenalty) parsedAdhocRebateData?.value?.adhocPenalty = parseInt(parsedAdhocRebateData?.value?.adhocPenalty)
      // if (parsedAdhocRebateData?.value?.adhocRebate) parsedAdhocRebateData?.value?.adhocRebate = parseInt(parsedAdhocRebateData?.value?.adhocRebate)
      const data = { ...wsData?.[0]?.additionalDetails, ...parsedAdhocRebateData?.value };
      wsData[0].additionalDetails = data;
    }

    const data = {
      CalculationCriteria:
        serviceType == "WATER"
          ? [
              {
                applicationNo: filters?.applicationNumber,
                tenantId: wsData?.[0]?.tenantId ? wsData?.[0]?.tenantId : tenantId,
                waterConnection: { ...wsData?.[0], property: properties?.Properties?.[0] },
              },
            ]
          : [
              {
                applicationNo: filters?.applicationNumber,
                tenantId: wsData?.[0]?.tenantId ? wsData?.[0]?.tenantId : tenantId,
                sewerageConnection: { ...wsData?.[0], property: properties?.Properties?.[0], service: "SEWERAGE" },
              },
            ],
      isconnectionCalculation: false,
    };

    tenantId = wsData?.[0]?.tenantId ? wsData?.[0]?.tenantId : tenantId;
    const serviceTypeOfData = serviceType == "WATER" ? "WS.ONE_TIME_FEE" : "SW.ONE_TIME_FEE";
    const collectionNumber = filters?.applicationNumber;


    let fetchBillData = {}, colletionData = {}, estimationResponse = {}, mdmsRes = {}, isPaid = false;


    fetchBillData = await WSSearch.fetchBillData({ tenantId, serviceTypeOfData, collectionNumber });

    if (fetchBillData?.Bill?.length > 0 && isVisible) {
      const stateCode = Digit.ULBService.getStateId();
      mdmsRes = await MdmsService.getMultipleTypes(stateCode, "BillingService", ["TaxHeadMaster"]);
      let taxHeadMasterResponce = mdmsRes.BillingService.TaxHeadMaster;
      fetchBillData.Bill[0].billDetails[0].billAccountDetails.forEach(data => {
        taxHeadMasterResponce.forEach(taxHeadCode => { if (data.taxHeadCode == taxHeadCode.code) { data.category = taxHeadCode.category } });
      });

      let fee = 0, charge = 0, taxAmount = 0;
      fetchBillData.Bill[0].billSlabData = _.groupBy(fetchBillData.Bill[0].billDetails[0].billAccountDetails, 'category')
      if (fetchBillData?.Bill?.[0]?.billSlabData?.FEE?.length > 0) fetchBillData.Bill[0].billSlabData.FEE?.map(amount => { fee += parseFloat(amount.amount); });
      if (fetchBillData?.Bill?.[0]?.billSlabData?.CHARGES?.length > 0) fetchBillData.Bill[0].billSlabData.CHARGES?.map(amount => { charge += parseFloat(amount.amount); });
      if (fetchBillData?.Bill?.[0]?.billSlabData?.TAX?.length > 0) fetchBillData.Bill[0].billSlabData.TAX?.map(amount => { taxAmount += parseFloat(amount.amount); });
      fetchBillData.Bill[0].fee = fee;
      fetchBillData.Bill[0].charge = charge
      fetchBillData.Bill[0].taxAmount = taxAmount;
      fetchBillData.Bill[0].totalAmount = fee + charge + taxAmount;
    }

    if (fetchBillData?.Bill?.length == 0) {
      if (isVisible) {
        colletionData = await WSSearch.colletionData({ tenantId, serviceTypeOfData, collectionNumber });
        if (colletionData?.Payments?.length > 0) {
          const colletionDataDetails = cloneDeep(colletionData);
          const stateCode = Digit.ULBService.getStateId();
          mdmsRes = await MdmsService.getMultipleTypes(stateCode, "BillingService", ["TaxHeadMaster"]);
          let taxHeadMasterResponce = mdmsRes.BillingService.TaxHeadMaster;
          colletionDataDetails?.Payments?.[0]?.paymentDetails?.[0]?.bill?.billDetails?.[0]?.billAccountDetails.forEach(data => {
            taxHeadMasterResponce.forEach(taxHeadCode => { if (data.taxHeadCode == taxHeadCode.code) { data.category = taxHeadCode.category } });
          });
    
          let fee = 0, charge = 0, taxAmount = 0;
          fetchBillData = {};
          fetchBillData.Bill = [];
          fetchBillData.Bill[0] = {};
          fetchBillData.Bill[0].billSlabData = _.groupBy(colletionDataDetails?.Payments?.[0]?.paymentDetails?.[0]?.bill?.billDetails?.[0]?.billAccountDetails, 'category')
          if (fetchBillData?.Bill?.[0]?.billSlabData?.FEE?.length > 0) fetchBillData.Bill[0].billSlabData.FEE?.map(amount => { fee += parseFloat(amount.amount); });
          if (fetchBillData?.Bill?.[0]?.billSlabData?.CHARGES?.length > 0) fetchBillData.Bill[0].billSlabData.CHARGES?.map(amount => { charge += parseFloat(amount.amount); });
          if (fetchBillData?.Bill?.[0]?.billSlabData?.TAX?.length > 0) fetchBillData.Bill[0].billSlabData.TAX?.map(amount => { taxAmount += parseFloat(amount.amount); });
          fetchBillData.Bill[0].fee = fee;
          fetchBillData.Bill[0].charge = charge
          fetchBillData.Bill[0].taxAmount = taxAmount;
          fetchBillData.Bill[0].totalAmount = fee + charge + taxAmount;
        }
      }
    }

    if (colletionData?.Payments?.length == 0 && fetchBillData?.Bill?.length == 0) {
      if (isVisible) {
        if (serviceType == "WATER" && response?.WaterConnection?.length > 0) {
          estimationResponse = await WSSearch.wsEstimationDetails(data, serviceType);
        }
        if (serviceType !== "WATER" && response?.SewerageConnections?.length > 0) {
          estimationResponse = await WSSearch.wsEstimationDetails(data, serviceType);
        }

        if (estimationResponse?.Calculation?.[0]?.taxHeadEstimates?.length > 0) {
          estimationResponse.Calculation[0].taxHeadEstimates?.forEach(data => data.amount = data.estimateAmount);
          estimationResponse.Calculation[0].billSlabData = _.groupBy(estimationResponse.Calculation[0].taxHeadEstimates, 'category');
        }
        fetchBillData = {};
        fetchBillData.Bill = [];
        fetchBillData.Bill[0] = estimationResponse?.Calculation?.[0]
      }
    }

    const wsDataDetails = cloneDeep(wsData?.[0]);
    const propertyDataDetails = cloneDeep(properties?.Properties?.[0]);
    const billDetails = cloneDeep(billData);
    const workFlowDataDetails = cloneDeep(workflowDetails);
    const serviceDataType = cloneDeep(serviceType);


    const applicationHeaderDetails = {
      title: " ",
      asSectionHeader: true,
      values:
        serviceType == "WATER"
          ? [
              { title: "PDF_STATIC_LABEL_APPLICATION_NUMBER_LABEL", value: wsDataDetails?.applicationNo || t("NA") },
              { title: "WS_SERVICE_NAME_LABEL", value: serviceType == "WATER" ? t("WATER") : t("SEWERAGE") },
              { title: "WS_NO_OF_CONNECTIONS_PROPOSED_LABEL", value: wsDataDetails?.proposedTaps || t("NA") },
              { title: "WS_PROPOSED_PIPE_SIZE", value: wsDataDetails?.proposedPipeSize || t("NA") },
            ]
          : [
              { title: "PDF_STATIC_LABEL_APPLICATION_NUMBER_LABEL", value: wsDataDetails?.applicationNo || t("NA") },
              { title: "WS_SERVICE_NAME_LABEL", value: serviceType == "WATER" ? "WATER" : "SEWERAGE" },
              { title: "WS_NO_WATER_CLOSETS_LABEL", value: wsDataDetails?.proposedWaterClosets || t("NA") },
              { title: "WS_SERV_DETAIL_NO_OF_TOILETS", value: wsDataDetails?.proposedToilets || t("NA") },
            ],
    };

    let isAdhocRebate = false;
    const checkUserList = await checkUserExist(cloneDeep(userInfo));
    const checkStatus = await checkExistStatus(cloneDeep(workFlowDataDetails?.ProcessInstances));
    if (checkUserList?.length > 0 && checkStatus?.length == 0 && window.location.href.includes("/employee") && workFlowDataDetails?.ProcessInstances?.[0]?.nextActions?.length > 0) {
      isAdhocRebate = true;
    }

    let wtrSewDetails = cloneDeep(wsDataDetails);
    if (wtrSewDetails?.additionalDetails?.adhocRebateReason) {
      wtrSewDetails.additionalDetails.adhocRebateReason_data =  { 
        title : wtrSewDetails?.additionalDetails?.adhocRebateReason,
        value: t(`${wtrSewDetails?.additionalDetails?.adhocRebateReason}`)
      }
    }
    if (wtrSewDetails?.additionalDetails?.adhocPenaltyReason) {
      wtrSewDetails.additionalDetails.adhocPenaltyReason_data =  { 
        title : wtrSewDetails?.additionalDetails?.adhocPenaltyReason,
        value: t(`${wtrSewDetails?.additionalDetails?.adhocPenaltyReason}`)
      }
    }

    const feeEstimation = {
      title: "WS_TASK_DETAILS_FEE_ESTIMATE",
      asSectionHeader: true,
      additionalDetails: {
        estimationDetails: true,
        data: fetchBillData?.Bill?.[0],
        appDetails: {...wtrSewDetails, property: propertyDataDetails, service: serviceDataType},
        isAdhocRebate: isAdhocRebate,
        isVisible: isVisible,
        isPaid: colletionData?.Payments?.length > 0 ? true : false,
        isViewBreakup: isVisible,
        values: [
          { title: "WS_APPLICATION_FEE_HEADER", value: Number(fetchBillData?.Bill?.[0]?.fee).toFixed(2) },
          { title: "WS_SERVICE_FEE_HEADER", value: Number(fetchBillData?.Bill?.[0]?.charge).toFixed(2) },
          { title: "WS_TAX_HEADER", value: Number(fetchBillData?.Bill?.[0]?.taxAmount).toFixed(2) },
        ],
      },
    };

    const propertyDetails = {
      title: "WS_COMMON_PROPERTY_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WS_PROPERTY_ID_LABEL", value: propertyDataDetails?.propertyId },
        { title: "WS_COMMON_OWNER_NAME_LABEL", value: getOwnerNames(propertyDataDetails) },
        { title: "WS_PROPERTY_ADDRESS_LABEL", value: getAddress(propertyDataDetails?.address, t), isNotTranslated: true, privacy: { uuid: propertyDataDetails?.propertyId, fieldName: ["doorNo", "street", "landmark"], model: "Property" }, },
      ],
      additionalDetails: {
        redirectUrl: {
          title: "View Complete Property details",
          url: `/${window?.contextPath}/employee/pt/property-details/${propertyDataDetails?.propertyId}?from=WS_APPLICATION_DETAILS_HEADER`,
        },
      },
    };

    const uuid = wsDataDetails?.connectionHolders?.[0]?.uuid
    const applicationNoForPrivacy = wsDataDetails?.applicationNo
    const connectionHolderDetails = {
      title: "WS_COMMON_CONNECTION_HOLDER_DETAILS_HEADER",
      asSectionHeader: true,
      values:
        wsDataDetails?.connectionHolders?.length > 0
          ? [
            { title: "WS_OWN_DETAIL_NAME", value: wsDataDetails?.connectionHolders?.[0]?.name || t("NA") },
            { title: "WS_CONN_HOLDER_OWN_DETAIL_GENDER_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.gender, privacy: { uuid: uuid, fieldName: ["gender"], model: "WaterConnectionOwner" }, },
            { title: "CORE_COMMON_MOBILE_NUMBER", value: wsDataDetails?.connectionHolders?.[0]?.mobileNumber, privacy: { uuid: uuid, fieldName: ["connectionHoldersMobileNumber"], model: "WaterConnectionOwner" }, },
            { title: "WS_CONN_HOLDER_COMMON_FATHER_OR_HUSBAND_NAME", value: wsDataDetails?.connectionHolders?.[0]?.fatherOrHusbandName, privacy: { uuid: uuid, fieldName: ["fatherOrHusbandName"], model: "WaterConnectionOwner" }, },
              { title: "WS_CONN_HOLDER_OWN_DETAIL_RELATION_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.relationship,
              privacy: { uuid: uuid, fieldName: ["relationship"], model: "WaterConnectionOwner" },
            },
            {
              title: "WS_CORRESPONDANCE_ADDRESS_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.correspondenceAddress, 
              privacy: {
                uuid: uuid,
                fieldName: wsDataDetails?.connectionHolders[0]?.permanentAddress ? ["permanentAddress"] : ["correspondenceAddress"],
                model: "WaterConnectionOwner",
                hide: !(wsDataDetails?.connectionHolders[0]?.permanentAddress || wsDataDetails?.connectionHolders[0]?.correspondenceAddress),
              } 
            },
            {
              title: "WS_OWNER_SPECIAL_CATEGORY", value: wsDataDetails?.connectionHolders?.[0]?.ownerType ? (wsDataDetails?.connectionHolders?.[0]?.ownerType?.includes("*") ? wsDataDetails?.connectionHolders?.[0]?.ownerType : (`PROPERTYTAX_OWNERTYPE_${wsDataDetails?.connectionHolders?.[0]?.ownerType?.toUpperCase()}`) ): "NA",
              privacy: { uuid: applicationNoForPrivacy, fieldName: ["ownerType"], model: "WaterConnection" },
            },
            ]
          : [{ title: "WS_CONN_HOLDER_SAME_AS_OWNER_DETAILS", value: t("SCORE_YES") }],
    };

    const documentDetails = {
      title: "",
      asSectionHeader: true,
      additionalDetails: {
        documents: [
          {
            title: "WS_COMMON_DOCS",
            values: wsDataDetails?.documents?.map((document) => {
              return {
                title: `WS_${document?.documentType}`,
                documentType: document?.documentType,
                documentUid: document?.documentUid,
                fileStoreId: document?.fileStoreId,
              };
            }),
          },
        ],
      },
    };

    const AdditionalDetailsByWS = {
      title: "",
      isWaterConnectionDetails: true,
      additionalDetails: {
        values: [],
        connectionDetails:
          serviceType == "WATER"
            ? [
                {
                  title: "WS_SERV_DETAIL_CONN_TYPE",
                  value: wsDataDetails?.connectionType
                    ? t(`WS_SERVICES_MASTERS_WATERSOURCE_${stringReplaceAll(wsDataDetails?.connectionType?.toUpperCase(), " ", "_")}`)
                    : t("NA"),
                },
                { title: "WS_SERV_DETAIL_NO_OF_TAPS", value: wsDataDetails?.noOfTaps || t("NA") },
                {
                  title: "WS_SERV_DETAIL_WATER_SOURCE",
                  value: wsDataDetails?.waterSource
                    ? t(`WS_SERVICES_MASTERS_WATERSOURCE_${wsDataDetails?.waterSource?.toUpperCase()?.split(".")[0]}`)
                    : t("NA"),
                },
                { title: "WS_PIPE_SIZE_IN_INCHES_LABEL", value: wsDataDetails?.pipeSize || t("NA") },
                {
                  title: "WS_SERV_DETAIL_WATER_SUB_SOURCE",
                  value: wsDataDetails?.waterSource ? t(`${wsDataDetails?.waterSource?.toUpperCase()?.split(".")[1]}`) : t("NA"),
                },
              ]
            : [
                {
                  title: "WS_SERV_DETAIL_CONN_TYPE",
                  value: wsDataDetails?.connectionType
                    ? t(`WS_SERVICES_MASTERS_WATERSOURCE_${stringReplaceAll(wsDataDetails?.connectionType?.toUpperCase(), " ", "_")}`)
                    : t("NA"),
                },
                { title: "WS_NUMBER_WATER_CLOSETS_LABEL", value: wsDataDetails?.noOfWaterClosets || t("NA") },
                { title: "WS_SERV_DETAIL_NO_OF_TOILETS", value: wsDataDetails?.noOfToilets || t("NA") },
              ],
        plumberDetails:
          wsDataDetails?.additionalDetails?.detailsProvidedBy === "ULB"
            ? [
                {
                  title: "WS_ADDN_DETAILS_PLUMBER_PROVIDED_BY",
                  value: wsDataDetails?.additionalDetails?.detailsProvidedBy
                    ? t(`WS_PLUMBER_${wsDataDetails?.additionalDetails?.detailsProvidedBy?.toUpperCase()}`)
                    : t("NA"),
                },
                { title: "WS_ADDN_DETAILS_PLUMBER_LICENCE_NO_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.licenseNo || t("NA") },
                { title: "WS_ADDN_DETAILS_PLUMBER_NAME_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.name || t("NA") },
              {
                title: "WS_PLUMBER_MOBILE_NO_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.mobileNumber || t("NA"), privacy: { uuid: wsDataDetails?.applicationNo, fieldName: ["plumberInfoMobileNumber"], model: "WaterConnection" },
                //privacy:{} 
              },
              ]
            : [
                {
                  title: "WS_ADDN_DETAILS_PLUMBER_PROVIDED_BY",
                  value: wsDataDetails?.additionalDetails?.detailsProvidedBy
                    ? t(`WS_PLUMBER_${wsDataDetails?.additionalDetails?.detailsProvidedBy?.toUpperCase()}`)
                    : t("NA"),
                },
              ],
        roadCuttingDetails: wsDataDetails?.roadCuttingInfo
          ? wsDataDetails?.roadCuttingInfo?.map((info, index) => {
              return {
                title: "WS_ROAD_CUTTING_DETAIL",
                values: [
                  { title: "WS_ADDN_DETAIL_ROAD_TYPE", value: t(`WS_ROADTYPE_${info?.roadType}`) },
                  { title: "WS_ROAD_CUTTING_AREA_LABEL", value: info?.roadCuttingArea },
                ],
              };
            })
          : [
              {
                title: "WS_ROAD_CUTTING_DETAIL",
                values: [
                  { title: "WS_ADDN_DETAIL_ROAD_TYPE", value: t("NA") },
                  { title: "WS_ROAD_CUTTING_AREA_LABEL", value: t("NA") },
                ],
              },
            ],
        activationDetails:
          wsDataDetails?.connectionType == "Metered"
            ? [
                { title: "WS_SERV_DETAIL_METER_ID", value: wsDataDetails?.meterId || t("NA") },
                { title: "WS_INITIAL_METER_READING_LABEL", value: wsDataDetails?.additionalDetails?.initialMeterReading || t("NA") },
                {
                  title: "WS_INSTALLATION_DATE_LABEL",
                  value: wsDataDetails?.meterInstallationDate ? convertEpochToDate(wsDataDetails?.meterInstallationDate) : t("NA"),
                },
                {
                  title: "WS_SERV_DETAIL_CONN_EXECUTION_DATE",
                  value: wsDataDetails?.connectionExecutionDate ? convertEpochToDate(wsDataDetails?.connectionExecutionDate) : t("NA"),
                },
              ...(wsDataDetails?.dateEffectiveFrom ? [{
                  title: "WS_MODIFICATIONS_EFFECTIVE_FROM",
                  value: wsDataDetails?.dateEffectiveFrom ? convertEpochToDate(wsDataDetails?.dateEffectiveFrom) : t("NA"),
                }]:[]),
              ]
            : [
                {
                  title: "WS_SERV_DETAIL_CONN_EXECUTION_DATE",
                  value: wsDataDetails?.connectionExecutionDate ? convertEpochToDate(wsDataDetails?.connectionExecutionDate) : t("NA"),
                },
              ...(wsDataDetails?.dateEffectiveFrom ? [{
                  title: "WS_MODIFICATIONS_EFFECTIVE_FROM",
                  value: wsDataDetails?.dateEffectiveFrom ? convertEpochToDate(wsDataDetails?.dateEffectiveFrom) : t("NA"),
              }]:[])
              ],
      },
    };

    let details = [];
    const isLabelShow = {
      title: "",
      asSectionHeader: true,
      isLabelShow: true,
      additionalDetails: { isLabelShow: true },
    };
    details = [...details,isLabelShow, applicationHeaderDetails, feeEstimation, propertyDetails, connectionHolderDetails, AdditionalDetailsByWS, documentDetails];
    wsDataDetails.serviceType = serviceDataType;

    if (!isVisible) {
      const allDetails = cloneDeep(details)
      details = allDetails?.filter(data => data?.title != "WS_TASK_DETAILS_FEE_ESTIMATE");
    }

    return {
      applicationData: wsDataDetails,
      applicationDetails: details,
      tenantId: wsDataDetails?.tenantId,
      applicationNo: wsDataDetails?.applicationNo,
      applicationStatus: wsDataDetails?.applicationStatus,
      propertyDetails: propertyDataDetails,
      billDetails: billDetails?.Bill,
      processInstancesDetails: workFlowDataDetails?.ProcessInstances,
    };
  },

  applicationDetailsBillAmendment: async (t, tenantId, applicationNumber, serviceType = "WATER", config = {}, businessService="BS.AMENDMENT") => {
    // Call the apis here referring old ui and return the result
    
    // // get the business service egov-workflow-v2/egov-wf/businessservice/_search?tenantId=pb.amritsar&businessServices=BS.AMENDMENT
    const businessServiceWf = await WorkflowService.init(tenantId,businessService)
    // //get the amendment from billing-service/amendment/_search?tenantId=pb.amritsar&amendmentId=DN-WS/107/2021-22/223303-000180&businessService=WS
    // const bServ = serviceType=="WATER" ? "WS":"SW"
    // const amendment = await PaymentService.searchAmendment(tenantId, { businessService: bServ, amendmentId:applicationNumber})
    // //get process instance from workflow /egov-workflow-v2/egov-wf/process/_search?businessIds=DN-WS/107/2021-22/223303-000180&history=true&tenantId=pb.amritsar
    // const wfDetails = await WSSearch.workflowDataDetails(tenantId, applicationNumber);

    // // get connection details from ws/sw ws-services/wc/_search?tenantId=pb.amritsar&businessService=WS&connectionNumber=WS/107/2021-22/223303&searchType=CONNECTION&isPropertyDetailsRequired=true
    // const filtersWS = { 
    //   connectionNumber: amendment.Amendments[0].consumerCode,
    //   searchType:"CONNECTION",
    //   bServ,
    //   isPropertyDetailsRequired:true
    // }
    // const connDetails = await WSSearch.application(tenantId,filtersWS,serviceType)
    // //get document details from amnedment and call filestore api to fetch those

    // const fileStoreIds = amendment.Amendments[0].documents.map(document => document.fileStoreId)
    // const documents = await Digit.UploadServices.Filefetch(fileStoreIds, "pb")
    // const connectionDetails = cloneDeep(connDetails?.WaterConnection?.[0] || connDetails?.SewerageConnections?.[0]);
    // const detailsv1 = []
    // return {
    //   applicationData: connectionDetails,
    //   applicationDetails: detailsv1,
    //   businessService:bs,
    //   workFlowDetails:wfDetails,
    //   amendment,
    //   documents
    // }

    //----------------------------------Below is saurabh's code
    let billAmendSearchService="WS";
    if(serviceType!="WATER"){
     billAmendSearchService="SW"
    }
    const billAmendmentSearch = await WSSearch.searchAmendment(tenantId, applicationNumber,billAmendSearchService)

    const filtersForWSSearch = {
      connectionNumber: billAmendmentSearch.Amendments[0].consumerCode,
      searchType: "CONNECTION",
      billAmendSearchService,
      isPropertyDetailsRequired: true
    }
    // 1. sewarage or water search thru connection number
    // 2. property search thru propertyId from search swc or ws
    // 3. billing service demand search thru consumer code === connection number
    // 4. billing servic amendment search thru consumer code === connection number

    let propertyids = "", consumercodes = "", businessIds = "";

    const response = await WSSearch.application(tenantId, filtersForWSSearch, serviceType);
    
    const wsData = cloneDeep(response?.WaterConnection || response?.SewerageConnections)

    const filters = { applicationNumber: wsData?.[0]?.applicationNo };

    wsData?.forEach(item => {
      propertyids = propertyids + item?.propertyId + (",");
      consumercodes = consumercodes + item?.applicationNo + ",";
    });

    let propertyfilter = { propertyIds: propertyids.substring(0, propertyids.length - 1), }

    if (propertyids !== "" && filters?.locality) propertyfilter.locality = filters?.locality;

    config = { enabled: propertyids !== "" ? true : false }

    const properties = await WSSearch.property(tenantId, propertyfilter);

    const {Demands: BillDemandDetails} = await PaymentService.demandSearch(tenantId, filtersForWSSearch?.connectionNumber, billAmendSearchService)
    const billServiceTaxHeadMaster = await MdmsService.getWSTaxHeadMaster(tenantId, "WS")
    const billServiceTaxHeadMasterForBillAmendment = billServiceTaxHeadMaster?.BillingService?.TaxHeadMaster?.filter(w=>w.IsBillamend)
    const actualFieldsAndAmountOfBillDetails = BillDemandDetails?.[0]?.demandDetails.filter( e => billServiceTaxHeadMasterForBillAmendment.find(taxHeadMaster => taxHeadMaster.code === e.taxHeadMasterCode))
    const billData = await WSSearch.searchBills(tenantId, consumercodes);

    if (filters?.applicationNumber) businessIds = filters?.applicationNumber;
    
    const workflowDetails = await WSSearch.workflowDataDetails(tenantId, applicationNumber);
    
    const wsDataDetails = cloneDeep(response?.WaterConnection?.[0] || response?.SewerageConnections?.[0]);
    const propertyDataDetails = cloneDeep(properties?.Properties?.[0]);
    const billDetails = cloneDeep(billData);
    const workFlowDataDetails = cloneDeep(workflowDetails);
    const serviceDataType = cloneDeep(serviceType);

    const applicationHeaderDetails = {
      title: " ",
      asSectionHeader: true,
      values: [
        { title: "PDF_STATIC_LABEL_APPLICATION_NUMBER_LABEL", value: wsDataDetails?.applicationNo || t("NA") },
        { title: "WS_OWN_MOBILE_NO", value: propertyDataDetails?.owners?.[0]?.mobileNumber|| t("NA") },
        { title: "WS_ACKNO_CONNECTION_NO_LABEL", value: wsDataDetails?.connectionNo || t("NA") },
        { title: "WS_APPLICANT_NAME", value: propertyDataDetails?.owners?.[0]?.name || t("NA") },
        { title: "WS_APPLICANT_ADDRESS", value: propertyDataDetails?.owners?.[0]?.name || t("NA") },
        { title: "WS_NOTE_TYPE", value: t("NA") },
      ]
    };

    const propertyDetails = {
      title: "WS_AMOUNT_DETAILS",
      asSectionHeader: true,
      values: [...actualFieldsAndAmountOfBillDetails.map( e => ({
        title: e?.taxHeadMasterCode, value: `₹ ${e?.taxAmount}`
      })), { title: "WS_REVISED_DEMAND", value: `₹ ${Math.round(actualFieldsAndAmountOfBillDetails.reduce((acc, curr) => curr.taxAmount + acc, 0))}` }]
    };
    
    const tableData = billAmendmentSearch?.Amendments?.[0]?.additionalDetails?.searchBillDetails;
    const action = tableData?.action;
    const tableHeader = ["WS_TAX_HEAD","WS_CURRENT_DEMAND",action,"WS_REVISED_DEMAND"]
    const tableRows = []
    const taxHeads = Object.keys(tableData?.actionPerformed)
    const actionPerformed = tableData?.actionPerformed
    const originalDemand = tableData?.originalDemand
    const getTaxHeadAmount = (obj,taxHead) => {
      return parseInt(obj[taxHead] ? obj[taxHead] : 0)
    }
    
    let sumCurrent=0;
    let sumApplied=0;
    let sumRevised=0;
    taxHeads.map(taxHead => {
      const currentDemand = getTaxHeadAmount(originalDemand, taxHead)
      const appliedDemand = getTaxHeadAmount(actionPerformed, taxHead)
      const revisedDemand = action==="REBATE"?currentDemand-appliedDemand:currentDemand+appliedDemand
      sumCurrent += currentDemand
      sumApplied += appliedDemand
      sumRevised += revisedDemand
      tableRows.push([taxHead,currentDemand,appliedDemand,revisedDemand])
    })
    tableRows.push(["WS_TOTAL_DUE",sumCurrent,sumApplied,sumRevised])
    
    const tableDetails = {
      title: "WS_AMOUNT_DETAILS",
      asSectionHeader: true,
      isTable:true,
      headers:tableHeader,
      action,
      tableRows
    }

    const connectionHolderDetails = {
      title: "WS_DEMAND_REVISION_BASIS_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WS_DEMAND_REVISION_BASIS", value: billAmendmentSearch?.Amendments?.[0]?.amendmentReason },
        { title: "WS_DOCUMENT_NO", value: billAmendmentSearch?.Amendments?.[0]?.reasonDocumentNumber },
        { title: "WS_COMMON_FROM_DATE_LABEL", value: Digit.DateUtils.ConvertTimestampToDate(billAmendmentSearch?.Amendments?.[0]?.effectiveFrom) },
        { title: "WS_COMMON_TO_DATE_LABEL", value: Digit.DateUtils.ConvertTimestampToDate(billAmendmentSearch?.Amendments?.[0]?.effectiveTill) },
      ]
    };
    const documentDetails = {
      title: "",
      asSectionHeader: true,
      additionalDetails: {
        documents: [{
          title: "BILL_AMEND_DOCS_UPLOADED",
          BS:'BillAmend',
          values: billAmendmentSearch.Amendments[0]?.documents?.map((document) => {
            return {
              title: `WS_${document?.documentType}`,
              documentType: document?.documentType,
              documentUid: document?.documentUid,
              fileStoreId: document?.fileStoreId,
            };
          }),
        },
        ]
      }
    };

    const details = [applicationHeaderDetails, tableDetails , connectionHolderDetails, documentDetails]
    wsDataDetails.serviceType = serviceDataType;


    if (billAmendmentSearch?.Amendments?.[0]) {
      wsDataDetails.billAmendmentDetails = billAmendmentSearch.Amendments[0]
      wsDataDetails.isBillAmend = true
    }

    
    return {
      applicationData: wsDataDetails,
      applicationDetails: details,
      tenantId: wsDataDetails?.tenantId,
      applicationNo: wsDataDetails?.applicationNo,
      applicationStatus: wsDataDetails?.applicationStatus,
      propertyDetails: propertyDataDetails,
      billDetails: billDetails?.Bill,
      processInstancesDetails: workFlowDataDetails?.ProcessInstances,
      amendment: billAmendmentSearch.Amendments[0], 
      businessServiceWf
    };
  },
  applicationDetailsBillAmendmentv2: async() => {

  }
  , 
  connectionDetails: async (t, tenantId, connectionNumber, serviceType = "WATER", config = {}) => {
    const filters = { connectionNumber, searchType: "CONNECTION" };

    let propertyids = "",
      consumercodes = "",
      businessIds = [];

    const response = await WSSearch.application(tenantId, filters, serviceType);

    const wsData = cloneDeep(serviceType == "WATER" ? response?.WaterConnection : response?.SewerageConnections);

    wsData?.forEach((item) => {
      propertyids = propertyids + item?.propertyId + ",";
      consumercodes = consumercodes + item?.connectionNo + ",";
    });

    let propertyfilter = { propertyIds: propertyids.substring(0, propertyids.length - 1) };

    if (propertyids !== "" && filters?.locality) propertyfilter.locality = filters?.locality;

    config = { enabled: propertyids !== "" ? true : false };

    const properties = await WSSearch.property(tenantId, propertyfilter);

    const wsResponseForWorkflow = await WSSearch.application(tenantId, { connectionNumber }, serviceType);

    const wsResponseForWorkflowData = cloneDeep(serviceType == "WATER" ? wsResponseForWorkflow?.WaterConnection : wsResponseForWorkflow?.SewerageConnections);

    wsResponseForWorkflowData?.forEach((item) => {
      item?.applicationNo &&  businessIds.push(item?.applicationNo);
    });

    const workflowDetails = await WSSearch.workflowDataDetails(tenantId, businessIds.join(","));

    const wsDataDetails = cloneDeep(serviceType == "WATER" ? response?.WaterConnection?.[0] : response?.SewerageConnections?.[0]);
    
    const propertyDataDetails = cloneDeep(properties?.Properties?.[0]);
    const workFlowDataDetails = cloneDeep(workflowDetails);
    const serviceDataType = cloneDeep(serviceType);

    const serviceTypeOfData = serviceType == "WATER" ? "WS" : "SW";
    const collectionNumber = wsDataDetails?.connectionNo;
    const colletionOFData = await WSSearch.colletionData({tenantId, serviceTypeOfData, collectionNumber}, {});
    const fetchBills = await WSSearch.fetchBillData({ tenantId, serviceTypeOfData, collectionNumber});


    const applicationHeaderDetails = {
      title: "WS_COMMON_SERV_DETAIL",
      asSectionHeader: true,
      values:
        serviceType == "WATER"
          ? [
              { title: "PDF_STATIC_LABEL_CONSUMER_NUMBER_LABEL", value: wsDataDetails?.connectionNo || t("NA") },
              { title: "WS_SERVICE_NAME_LABEL", value: serviceType == "WATER" ? t("WATER") : t("SEWERAGE") },
              {
                title: "WS_SERV_DETAIL_CONN_TYPE",
                value: wsDataDetails?.connectionType
                  ? t(`WS_SERVICES_MASTERS_WATERSOURCE_${stringReplaceAll(wsDataDetails?.connectionType?.toUpperCase(), " ", "_")}`)
                  : t("NA"),
              },
              { title: "WS_SERV_DETAIL_NO_OF_TAPS", value: wsDataDetails?.noOfTaps || t("NA") },
              { title: "WS_PIPE_SIZE_IN_INCHES_LABEL", value: wsDataDetails?.pipeSize || t("NA") },
              {
                title: "WS_SERV_DETAIL_WATER_SOURCE",
                value: wsDataDetails?.waterSource
                  ? t(`WS_SERVICES_MASTERS_WATERSOURCE_${wsDataDetails?.waterSource?.toUpperCase()?.split(".")[0]}`)
                  : t("NA"),
              },
              {
                title: "WS_SERV_DETAIL_WATER_SUB_SOURCE",
                value: wsDataDetails?.waterSource ? t(`${wsDataDetails?.waterSource?.toUpperCase()?.split(".")[1]}`) : t("NA"),
              },
              {
                title: "WS_SERV_DETAIL_CONN_EXECUTION_DATE",
                value: wsDataDetails?.connectionExecutionDate ? convertEpochToDate(wsDataDetails?.connectionExecutionDate) : t("NA"),
              },
              { title: "WS_SERV_DETAIL_METER_ID", value: wsDataDetails?.meterId || t("NA") },
              {
                title: "WS_INSTALLATION_DATE_LABEL",
                value: wsDataDetails?.meterInstallationDate ? convertEpochToDate(wsDataDetails?.meterInstallationDate) : t("NA"),
              },
              { title: "WS_INITIAL_METER_READING_LABEL", value: wsDataDetails?.additionalDetails?.initialMeterReading || t("NA") },
              {
                title: "WS_VIEW_CONSUMPTION_DETAIL",
                to: `/${window?.contextPath}/employee/ws/consumption-details?applicationNo=${wsDataDetails?.connectionNo}&tenantId=${wsDataDetails?.tenantId}&service=${serviceType}&from=WS_COMMON_CONNECTION_DETAIL`,
                value: "",
                isLink: wsDataDetails?.connectionType ==="Metered" ? true:false,
              },
            ]
          : [
              { title: "PDF_STATIC_LABEL_CONSUMER_NUMBER_LABEL", value: wsDataDetails?.connectionNo || t("NA") },
              { title: "WS_SERVICE_NAME_LABEL", value: serviceType == "WATER" ? "WATER" : "SEWERAGE" },
              { title: "WS_NUMBER_WATER_CLOSETS_LABEL", value: wsDataDetails?.noOfWaterClosets || t("NA") },
              { title: "WS_SERV_DETAIL_NO_OF_TOILETS", value: wsDataDetails?.noOfToilets || t("NA") },
              {
                title: "WS_SERV_DETAIL_CONN_EXECUTION_DATE",
                value: wsDataDetails?.connectionExecutionDate ? convertEpochToDate(wsDataDetails?.connectionExecutionDate) : t("NA"),
              },
            ],
    };

    const propertyDetails = {
      title: "WS_COMMON_PROPERTY_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WS_PROPERTY_ID_LABEL", value: propertyDataDetails?.propertyId },
        { title: "WS_COMMON_OWNER_NAME_LABEL", 
          value: getOwnerNames(propertyDataDetails),
          privacy: {
            uuid: propertyDataDetails?.owners?.[0]?.uuid, 
            fieldName: "name", 
            model: "User"
          }
        },
        { title: "WS_PROPERTY_ADDRESS_LABEL",
          value: getAddress(propertyDataDetails?.address, t),
          privacy: {
            uuid: propertyDataDetails?.propertyId, 
            fieldName: ["doorNo" , "street" , "landmark"], 
            model: "Property"
          }
        },
        {
          title: "WS_VIEW_PROPERTY_DETAIL",
          to: `/${window?.contextPath}/employee/pt/property-details/${propertyDataDetails?.propertyId}?from=WS_COMMON_CONNECTION_DETAIL`,
          value: "",
          isLink: true,
        },
      ],
    };

    const connectionHolderDetails = {
      title: "WS_COMMON_CONNECTION_HOLDER_DETAILS_HEADER",
      asSectionHeader: true,
      values:
        wsDataDetails?.connectionHolders != null && wsDataDetails?.connectionHolders.length > 0
          ? [
              { 
                title: "WS_OWN_DETAIL_NAME", 
                value: wsDataDetails?.connectionHolders?.[0]?.name || t("NA"),
                privacy: {
                  uuid: wsDataDetails?.connectionHolders?.[0]?.uuid, 
                  fieldName: "name", 
                  model: "User"
                }
              },
              { 
                title: "WS_CONN_HOLDER_OWN_DETAIL_GENDER_LABEL", 
                value: wsDataDetails?.connectionHolders?.[0]?.gender,
                privacy: { 
                  uuid: wsDataDetails?.connectionHolders?.[0]?.uuid, 
                  fieldName: "gender", 
                  model: "WaterConnectionOwner" 
                } 
              },
              { 
                title: "CORE_COMMON_MOBILE_NUMBER", 
                value: wsDataDetails?.connectionHolders?.[0]?.mobileNumber,
                privacy: { 
                  uuid: wsDataDetails?.connectionHolders?.[0]?.uuid, 
                  fieldName: "connectionHoldersMobileNumber", 
                  model: "WaterConnectionOwner" 
                } 
              },
              { 
                title: "WS_CONN_HOLDER_COMMON_FATHER_OR_HUSBAND_NAME", 
                value: wsDataDetails?.connectionHolders?.[0]?.fatherOrHusbandName,
                privacy: { 
                  uuid: wsDataDetails?.connectionHolders?.[0]?.uuid, 
                  fieldName: "fatherOrHusbandName", 
                  model: "WaterConnectionOwner" 
                }
              },
              { title: "WS_CONN_HOLDER_OWN_DETAIL_RELATION_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.relationship },
              { 
                title: "WS_CORRESPONDANCE_ADDRESS_LABEL", 
                value: wsDataDetails?.connectionHolders?.[0]?.correspondenceAddress,
                privacy: { 
                  uuid: wsDataDetails?.connectionHolders?.[0]?.uuid, 
                  fieldName: "correspondenceAddress", 
                  model: "User" 
                }
              },
              { title: "WS_OWNER_SPECIAL_CATEGORY", value: wsDataDetails?.connectionHolders?.[0]?.ownerType ? `PROPERTYTAX_OWNERTYPE_${wsDataDetails?.connectionHolders?.[0]?.ownerType?.toUpperCase()}` : "NA"}
            ]
          : [{ title: "WS_CONN_HOLDER_SAME_AS_OWNER_DETAILS", value: t("SCORE_YES") }],
    };

    const isApplicationApproved =  workFlowDataDetails?.ProcessInstances?.[0]?.state.isTerminateState  
    const isLabelShow = {
      title: "",
      asSectionHeader: true,
      isLabelShow: true,
      additionalDetails: { isLabelShow: true },
    };

    let details = [];
    details = [...details, isLabelShow, applicationHeaderDetails, propertyDetails, connectionHolderDetails];
    wsDataDetails.serviceType = serviceDataType;
    wsDataDetails.property = propertyDataDetails;
    return {
      applicationData: wsDataDetails,
      applicationDetails: details,
      tenantId: wsDataDetails?.tenantId,
      applicationNo: wsDataDetails?.applicationNo,
      applicationStatus: wsDataDetails?.applicationStatus,
      propertyDetails: propertyDataDetails,
      processInstancesDetails: workFlowDataDetails?.ProcessInstances,
      colletionOfData: colletionOFData?.Payments,
      fetchBillsData: fetchBills?.Bill,
      isApplicationApproved: isApplicationApproved
    };
  },

  disConnectionDetails: async (t, tenantId, applicationNumber, serviceType = "WATER", config = {}) => {
    const filters = { applicationNumber };
    let propertyids = "", consumercodes = "", businessIds = "";

    const response = await WSSearch.application(tenantId, filters, serviceType);

    const wsData = cloneDeep(serviceType == "WATER" ? response?.WaterConnection : response?.SewerageConnections);

    wsData?.forEach((item) => { propertyids = propertyids + item?.propertyId + ","; consumercodes = consumercodes + item?.applicationNo + ","; });

    let propertyfilter = { propertyIds: propertyids.substring(0, propertyids.length - 1) };

    if (propertyids !== "" && filters?.locality) propertyfilter.locality = filters?.locality;

    config = { enabled: propertyids !== "" ? true : false };

    const properties = await WSSearch.property(tenantId, propertyfilter);

    if (filters?.applicationNumber) businessIds = filters?.applicationNumber;

    const workflowDetails = await WSSearch.workflowDataDetails(tenantId, businessIds);

    tenantId = wsData?.[0]?.tenantId ? wsData?.[0]?.tenantId : tenantId;
    const serviceTypeOfData = serviceType == "WATER" ? "WS" : "SW";
    const collectionNumber = wsData?.[0]?.connectionNo;

    const fetchBillData = await WSSearch.fetchBillData({ tenantId, serviceTypeOfData, collectionNumber });

    const wsDataDetails = cloneDeep(serviceType == "WATER" ? response?.WaterConnection?.[0] : response?.SewerageConnections?.[0]);
    const propertyDataDetails = cloneDeep(properties?.Properties?.[0]);
    const workFlowDataDetails = cloneDeep(workflowDetails);
    const serviceDataType = cloneDeep(serviceType);

    const applicationHeaderDetails = {
      title: " ",
      asSectionHeader: true,
      values:
        [
          { title: "PDF_STATIC_LABEL_APPLICATION_NUMBER_LABEL", value: wsDataDetails?.applicationNo || t("NA") },
          { title: "PDF_STATIC_LABEL_CONSUMER_NUMBER_LABEL", value: wsDataDetails?.connectionNo || t("NA") },
          { title: "WS_SERVICE_NAME_LABEL", value: serviceType == "WATER" ? t("WATER") : t("SEWERAGE") },
          { title: "WNS_COMMON_TABLE_COL_AMT_DUE_LABEL", value: fetchBillData.Bill[0]?.totalAmount ? fetchBillData.Bill[0]?.totalAmount : "NA" },
          { title: "WS_DISCONNECTION_PROPOSED_DATE", value: wsDataDetails?.dateEffectiveFrom ? convertEpochToDate(wsDataDetails?.dateEffectiveFrom) : t("NA") },
          { title: "WS_DISCONNECTION_REASON", value: wsDataDetails?.disconnectionReason || t("NA") },
        ]
    };

    const propertyDetails = {
      title: "WS_COMMON_PROPERTY_DETAILS",
      asSectionHeader: true,
      values: [
        { title: "WS_PROPERTY_ID_LABEL", value: propertyDataDetails?.propertyId },
        { title: "WS_COMMON_OWNER_NAME_LABEL", value: getOwnerNames(propertyDataDetails) },
        { title: "WS_PROPERTY_ADDRESS_LABEL", value: getAddress(propertyDataDetails?.address, t) },
      ],
      additionalDetails: {
        redirectUrl: {
          title: "View Complete Property details",
          url: `/${window?.contextPath}/employee/pt/property-details/${propertyDataDetails?.propertyId}?from=WS_APPLICATION_DETAILS_HEADER`,
        },
      },
    };

    const connectionHolderDetails = {
      title: "WS_COMMON_CONNECTION_HOLDER_DETAILS_HEADER",
      asSectionHeader: true,
      values:
        wsDataDetails?.connectionHolders?.length > 0
          ? [
            { title: "WS_OWN_DETAIL_NAME", value: wsDataDetails?.connectionHolders?.[0]?.name || t("NA") },
            { title: "WS_CONN_HOLDER_OWN_DETAIL_GENDER_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.gender },
            { title: "CORE_COMMON_MOBILE_NUMBER", value: wsDataDetails?.connectionHolders?.[0]?.mobileNumber },
            { title: "WS_CONN_HOLDER_COMMON_FATHER_OR_HUSBAND_NAME", value: wsDataDetails?.connectionHolders?.[0]?.fatherOrHusbandName },
            { title: "WS_CONN_HOLDER_OWN_DETAIL_RELATION_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.relationship },
            { title: "WS_CORRESPONDANCE_ADDRESS_LABEL", value: wsDataDetails?.connectionHolders?.[0]?.correspondenceAddress },
            { title: "WS_OWNER_SPECIAL_CATEGORY", value: wsDataDetails?.connectionHolders?.[0]?.ownerType ? `PROPERTYTAX_OWNERTYPE_${wsDataDetails?.connectionHolders?.[0]?.ownerType?.toUpperCase()}` : "NA" },
          ]
          : [{ title: "WS_CONN_HOLDER_SAME_AS_OWNER_DETAILS", value: t("SCORE_YES") }],
    };

    const plumberDetails = {
      title: "WS_COMMON_PLUMBER_DETAILS",
      asSectionHeader: true,
      values:
        wsDataDetails?.additionalDetails?.detailsProvidedBy === "ULB"
          ? [
            {
              title: "WS_ADDN_DETAILS_PLUMBER_PROVIDED_BY",
              value: wsDataDetails?.additionalDetails?.detailsProvidedBy
                ? t(`WS_PLUMBER_${wsDataDetails?.additionalDetails?.detailsProvidedBy?.toUpperCase()}`)
                : t("NA"),
            },
            { title: "WS_ADDN_DETAILS_PLUMBER_LICENCE_NO_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.licenseNo || t("NA") },
            { title: "WS_ADDN_DETAILS_PLUMBER_NAME_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.name || t("NA") },
            { title: "WS_PLUMBER_MOBILE_NO_LABEL", value: wsDataDetails?.plumberInfo?.[0]?.mobileNumber || t("NA") },
          ]
          : [
            {
              title: "WS_ADDN_DETAILS_PLUMBER_PROVIDED_BY",
              value: wsDataDetails?.additionalDetails?.detailsProvidedBy
                ? t(`WS_PLUMBER_${wsDataDetails?.additionalDetails?.detailsProvidedBy?.toUpperCase()}`)
                : t("NA"),
            },
          ]
    };

    const documentDetails = {
      title: "",
      asSectionHeader: true,
      additionalDetails: {
        documents: [
          {
            title: "WS_COMMON_DOCS",
            values: wsDataDetails?.documents?.map((document) => {
              return {
                title: `WS_${document?.documentType}`,
                documentType: document?.documentType,
                documentUid: document?.documentUid,
                fileStoreId: document?.fileStoreId,
              };
            }),
          },
        ],
      },
    };

    let details = [];
    details = [...details, applicationHeaderDetails, propertyDetails, connectionHolderDetails, plumberDetails, documentDetails];
    wsDataDetails.serviceType = serviceDataType;

    return {
      applicationData: wsDataDetails,
      applicationDetails: details,
      tenantId: wsDataDetails?.tenantId,
      applicationNo: wsDataDetails?.applicationNo,
      applicationStatus: wsDataDetails?.applicationStatus,
      propertyDetails: propertyDataDetails,
      processInstancesDetails: workFlowDataDetails?.ProcessInstances,
    };

  }
};
