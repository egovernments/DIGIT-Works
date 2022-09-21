import React from 'react'
import getPDFData from "../utils/getWorksAcknowledgementData"
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';

const HandleDownloadPdf = () => {
  const {t} = useTranslation()
  const history=useHistory();
  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewLOIDetails(t);
  const tenantInfo = Digit.ULBService.getCurrentTenantId();
  let result = applicationDetails;
  const PDFdata = getPDFData({...result },tenantInfo, t);
  PDFdata.then((ress) => Digit.Utils.pdf.generatev1(ress));
  // history.push("/works-ui/employee/works")

      return null;
}

export default HandleDownloadPdf