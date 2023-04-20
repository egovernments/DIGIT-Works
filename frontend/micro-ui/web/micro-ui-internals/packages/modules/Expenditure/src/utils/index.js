export const updateDefaultValues = ({t, tenantId, configs, findCurrentDate, isModify, sessionFormData, setSessionFormData, contract}) => {
  if(!sessionFormData?.basicDetails_workOrderNumber || !sessionFormData.basicDetails_projectID || !sessionFormData.basicDetails_projectDesc || !sessionFormData.basicDetails_location) {
    // if(isModify) {
    //   configs.defaultValues.basicDetails_purchaseBillNumber = bill?.billNumber ? bill?.Number  : "";
    //   configs.defaultValues.basicDetails_purchaseBillDate = bill?.billNumber ? bill?.Number  : "";
    // }
    configs.defaultValues.billDetails_billDate = findCurrentDate(); 

    configs.defaultValues.basicDetails_workOrderNumber = contract?.contractNumber || t("NA");
    configs.defaultValues.basicDetails_projectID = contract?.additionalDetails?.projectId || t("NA");
    configs.defaultValues.basicDetails_projectDesc = contract?.additionalDetails?.projectDesc || t("NA");
    configs.defaultValues.basicDetails_location =  location ? location : t("NA")
    configs.defaultValues.basicDetails_location = contract?.additionalDetails?.ward ? 
      String(
          `${t(Digit.Utils.locale.getCityLocale(tenantId))}, ${t(Digit.Utils.locale.getMohallaLocale(contract?.additionalDetails?.ward, tenantId))}`
      )  : t("NA"); 
    //const vendors = getVendorNames(vendorOptions);
    configs.defaultValues.invoiceDetails_vendor =  isModify ? { code: '1cb24e53-bfd6-4840-aa90-7b849b367f47', name: "IFSC test org", orgNumber: "ORG-000216"} : ""
    setSessionFormData({...sessionFormData, ...configs?.defaultValues});
  }
}