import React from 'react'
import getPDFData from "../utils/getWorksAcknowledgementData"
import { useTranslation } from "react-i18next";
import { useHistory } from 'react-router-dom';

const HandleDownloadPdf = () => {
  const {t} = useTranslation()
  const history=useHistory();
  const tenantInfo = Digit.ULBService.getCurrentTenantId();
  // Add redirect params (loiNumber,subEstiamteNumber) to download pdf
  const { loiNumber,subEstiamteNumber } = Digit.Hooks.useQueryParams();
  
  // to fetch a details of LOI by using params t, tenantInfo, loiNumber, subEstiamteNumber
  let { isLoading, isError, data: applicationDetails, error } = Digit.Hooks.works.useViewLOIDetails(t, tenantInfo, loiNumber, subEstiamteNumber);
  let result = applicationDetails;
  const PDFdata = getPDFData({...result },tenantInfo, t);
  PDFdata.then((ress) => Digit.Utils.pdf.generatev1(ress));
  // history.push(`/${window?.contextPath}/employee/works`)

      return null;
}

export default HandleDownloadPdf