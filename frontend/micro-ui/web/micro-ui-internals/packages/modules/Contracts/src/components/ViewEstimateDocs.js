import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { CardSubHeader, PDFSvg } from "@egovernments/digit-ui-react-components";
import {
  TextBlock
} from "@egovernments/digit-ui-components";

function ViewEstimateDocs(props) {
  const documents = props?.props?.documents;
  const svgStyles = {};
  const { t } = useTranslation();
  const [filesArray, setFilesArray] = useState(() => [] );
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [pdfFiles, setPdfFiles] = useState({});

  useEffect(() => {
    let acc = [];
    documents?.forEach((element, index, array) => {
      acc = [...acc, ...(element.values?element.values:[])];
    });
    setFilesArray(acc?.map((value) => value?.fileStoreId));
  }, [documents]);

  useEffect(() => {
    if(filesArray?.length)
    { 
     Digit.UploadServices.Filefetch(filesArray, Digit.ULBService.getCurrentTenantId()).then((res) => {
      setPdfFiles(res?.data);
     });
    }
    
  }, [filesArray]);

  return (
    <div>
        {documents?.map((document, index) => (
            <React.Fragment key={index}>
            {/* {document?.title ? <CardSubHeader style={{ marginTop: "32px", marginBottom: "8px", color: "#505A5F", fontSize: "24px" }}>{t(document?.title)}</CardSubHeader>: null} */}
            {document?.title ? <TextBlock subHeader={t(document?.title)} subHeaderClassName={"view-subheader"}></TextBlock> : null}
            <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "flex-start" }}>
                {document?.values && document?.values.length>0 ? document?.values?.map((value, index) => (
                <a target="_" href={pdfFiles[value.fileStoreId]?.split(",")[0]} style={{ minWidth: "80px", marginRight: "10px", maxWidth: "100px", height: "auto" }} key={index}>
                    <div style={{ display: "flex", justifyContent: "center" }}>
                    <PDFSvg />
                    </div>
                    <p style={{ marginTop: "8px", fontWeight: "bold", wordBreak: "break-word", marginLeft: "1rem" }}>{t(value?.title)}</p>
                </a>
                )):!(window.location.href.includes("citizen"))&& <div><p>{t("BPA_NO_DOCUMENTS_UPLOADED_LABEL")}</p></div>}
            </div>
            </React.Fragment>
        ))}
    </div>
  );
}

export default ViewEstimateDocs;
