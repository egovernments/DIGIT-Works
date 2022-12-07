import {stringReplaceAll, convertEpochToDateDMY, mdmsData} from "./index";
  
  const capitalize = (text) => text.substr(0, 1).toUpperCase() + text.substr(1);
  const ulbCamel = (ulb) => ulb.toLowerCase().split(" ").map(capitalize).join(" ");
  
  const getHeaderDetails = async (application, t,tenantId) => {
    const dynamicHeaderData = await mdmsData(tenantId,t)
    let values = [];
    if (application?.applicationNo) values.push({ title: `${t("PDF_STATIC_LABEL_APPLICATION_NUMBER_LABEL")}:`, value: application?.applicationNo });
    if (application?.connectionNo) values.push({ title: `${t("PDF_STATIC_LABEL_CONSUMER_NUMBER_LABEL")}:`, value: application?.connectionNo });
    return {
      title: "",
      isHeader: true,
      typeOfApplication: t("WORKS_LOI_DETAILS"),
      date: Digit.DateUtils.ConvertEpochToDate(application?.applicationData?.auditDetails?.createdTime) || "NA",
      values: values,
      ...dynamicHeaderData
    }
  }

  const getLOIDetails=(application,t)=>{
    const LOIDetails=application?.applicationDetails[0]?.values;
    let arr=[];
    LOIDetails.map((item)=>arr.push({title:t(item.title),value:item.value}))
    return{
      title: t(application?.applicationDetails[0]?.title),
      values: arr
    }
  }

  const getFinancialDetails=(application,t)=>{
    const financialDetails=application?.applicationDetails[1]?.values;
    let arr=[];
    financialDetails.map((item)=>arr.push({title:t(item.title),value:item.value}))
    return{
      title:t(application?.applicationDetails[1]?.title),
      values:arr
    }
  }

  const getAgreementDetails=(application,t)=>{
    const agreementDetails=application?.applicationDetails[2]?.values;
    let arr=[];
    agreementDetails.map((item)=>arr.push({title:t(item.title),value:item.value}))
    return{
      title:t(application?.applicationDetails[2]?.title),
      values:arr
    }
  }
  
  const getDocumentDetails = (application, t) => {
    const documents = application?.applicationDetails[3]?.additionalDetails?.documents[0]
    return {
      title: t(documents.title),
      isAttachments:true,
      values: documents?.values?.map(doc => t(doc?.documentType))
    };
  };
  
  const getWorksAcknowledgementData = async (application, tenantInfo, t) => {
    const filesArray = application?.tradeLicenseDetail?.applicationDocuments?.map((value) => value?.fileStoreId);
    let res;
    if (filesArray) {
      res = await Digit.UploadServices.Filefetch(filesArray, Digit.ULBService.getStateId());
    }
    const header = await getHeaderDetails(application, t, tenantInfo)
    return {
      t: t,
      tenantId: tenantInfo,
      title: t(`PDF_STATIC_LABEL_WS_CONSOLIDATED_ACKNOWELDGMENT_LOGO_SUB_HEADER`),
      name: `${t("PDF_STATIC_LABEL_WS_CONSOLIDATED_ACKNOWELDGMENT_LOGO_SUB_HEADER")}`,
      email: "",
      phoneNumber: "",
      headerDetails: [
        header
      ],
      details: [
        getLOIDetails(application,t),
        getFinancialDetails(application,t),
        getAgreementDetails(application,t),
        getDocumentDetails(application,t)
      ],
    };
  };
  
  export default getWorksAcknowledgementData;