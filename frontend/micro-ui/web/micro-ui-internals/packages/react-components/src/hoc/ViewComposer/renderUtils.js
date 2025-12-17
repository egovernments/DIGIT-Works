import React, { Fragment, useState, useEffect } from "react";
import CardSectionHeader from "../../atoms/CardSectionHeader";
import { StatusTable, Row } from "../../atoms/StatusTable";
import CardSubHeader from "../../atoms/CardSubHeader";
import { useTranslation } from "react-i18next";
import { PDFSvg } from "../../atoms/svgindex";
import WorkflowTimeline from "../../atoms/WorkflowTimeline";
import WorkflowActions from "../../atoms/WorkflowActions";
import { Link } from "react-router-dom";
import Photos from "../../atoms/Photos";
import {Card,Divider,TextBlock} from "@egovernments/digit-ui-components";

export const RenderDataSection = ({ section }) => {
  const { t } = useTranslation();
  return (
    <>
      {/* {section.cardHeader && <CardSubHeader style={section?.cardHeader?.inlineStyles}>{t(section.cardHeader.value)}</CardSubHeader>} */}
      {section.cardHeader && <TextBlock style={section?.cardHeader?.inlineStyles} headerClassName={`view-composer-header ${section?.cardHeader?.cardHeaderClassName}`} subHeader={t(section.cardHeader.value)}></TextBlock>}
      <StatusTable style={section?.inlineStyles}>
        {/* {section.sectionHeader && <CardSectionHeader style={section?.sectionHeader?.inlineStyles}>{t(section.sectionHeader.value)}</CardSectionHeader>} */}
        {section.sectionHeader && <TextBlock style={section?.sectionHeader?.inlineStyles} subHeaderClassName={`view-composer-subheader ${section?.sectionHeader?.sectionheaderClassName}`} subHeader={t(section.sectionHeader.value)}></TextBlock>}
        {section?.values?.filter((ob) => ob !== null && Object?.keys(ob)?.length > 0).map((row, rowIdx) => {
          const displayValue = row?.value !== undefined && row?.value !== null ? row.value : 'NA';
          
          return (
            <Row
              key={row.key}
              label={t(row.key)}
              text={row?.isLink ? <div>
                <Link to={row?.to}>
                  <span className="link" style={{ color: "#F47738" }}>
                    {  
                      t(displayValue)
                    }
                  </span>
                </Link>
              </div> : 
              row?.isTranslate ===false ?
                      (displayValue):
              t(displayValue)}
              last={rowIdx === section.values?.length - 1 && !section?.isDividerBelow}
              caption={row.caption}
              className="border-none"
              /* privacy object set to the Row Component */
              privacy={row?.value?.privacy}
              rowContainerStyle={row?.rowContainerStyle || {}}
              textStyle={row?.textStyle || {}}
              labelStyle={{}}
              amountStyle={row?.amountStyle || {}}
            />
          );
        })}
      </StatusTable>
    </>
  );
};

export const RenderDocumentsSection = ({ section }) => {
  const { documents } = section;
  const { t } = useTranslation();
  const [filesArray, setFilesArray] = useState(() => []);
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [pdfFiles, setPdfFiles] = useState({});

  useEffect(() => {
    let acc = [];
    documents?.forEach((element, index, array) => {
      acc = [...acc, ...(element.values ? element.values : [])];
    });
    setFilesArray(acc?.map((value) => value?.fileStoreId));
  }, [documents]);

  useEffect(() => {
    if (filesArray?.length && documents?.[0]?.BS === "BillAmend") {
      Digit.UploadServices.Filefetch(filesArray, Digit.ULBService.getCurrentTenantId()).then((res) => {
        setPdfFiles(res?.data);
      });
    } else if (filesArray?.length) {
      Digit.UploadServices.Filefetch(filesArray, Digit.ULBService.getCurrentTenantId()).then((res) => {
        setPdfFiles(res?.data);
      });
    }
  }, [filesArray]);

  return (
    <div style={section?.inlineStyles}>
      {documents?.map((document, index) => (
        <React.Fragment key={index}>
          {document?.title ? 
          // <CardSectionHeader style={section?.headerStyle}>{t(document?.title)}</CardSectionHeader> 
          <TextBlock style={{...section?.headerStyle}} subHeaderClassName={`view-composer-subheader ${section?.headerClassName}`} subHeader={t(document?.title)}></TextBlock>
          : null}
          <div style={{ display: "flex", flexWrap: "wrap", justifyContent: "flex-start" }}>
            {document?.values && document?.values.length > 0
              ? document?.values?.map((value, index) => (
                  <a
                    target="_"
                    href={pdfFiles[value.fileStoreId]?.split(",")[0]}
                    style={{ minWidth: "80px", marginRight: "10px", maxWidth: "100px", height: "auto" }}
                    key={index}
                  >
                    <div style={{ display: "flex", justifyContent: "center" }}>
                      <PDFSvg />
                    </div>
                    <p style={{ marginTop: "8px", fontWeight: "bold", wordBreak: "break-word", marginLeft: "1rem" }}>{t(value?.title)}</p>
                  </a>
                ))
              : !window.location.href.includes("citizen") && (
                  <div>
                    <p>{t("BPA_NO_DOCUMENTS_UPLOADED_LABEL")}</p>
                  </div>
                )}
          </div>
        </React.Fragment>
      ))}
    </div>
  );
};

export const RenderWfHistorySection = ({ section }) => {
  const { businessService, applicationNo, tenantId, timelineStatusPrefix = undefined, statusAttribute = undefined, config={}, breakLineRequired } = section;
  return (
    <WorkflowTimeline
      businessService={businessService}
      applicationNo={applicationNo}
      tenantId={tenantId}
      timelineStatusPrefix={timelineStatusPrefix}
      statusAttribute={statusAttribute}
      config = {config}
      breakLineRequired={breakLineRequired}
    />
  );
};

export const RenderWfActions = ({ section }) => {
  const {
    forcedActionPrefix = undefined,
    businessService,
    applicationNo,
    tenantId,
    applicationDetails,
    url,
    moduleCode = "Estimate",
    editApplicationNumber,
    editCallback,
  } = section;

  return (
    <WorkflowActions
      forcedActionPrefix={`WF_${businessService}_ACTION`}
      businessService={businessService}
      applicationNo={applicationNo}
      tenantId={tenantId}
      applicationDetails={applicationDetails}
      url={url}
      moduleCode={moduleCode}
      editApplicationNumber={editApplicationNumber}
      editCallback={editCallback}
    />
  );
};

export const RenderPhotos = ({section}) => {
  const { t } = useTranslation();
  function OpenImage(imageSource, index, thumbnailsToShow) {
    window.open(thumbnailsToShow?.fullImage?.[index ? index : 0], "_blank");
  }

  return (
    <>
    {section?.cardHeader && section?.cardHeader?.value && (
      // <CardSectionHeader style={section?.cardHeader?.inlineStyles}>{t(section.cardHeader.value)}</CardSectionHeader>
      <TextBlock style={{...section?.cardHeader?.inlineStyles}} subHeaderClassName={`view-composer-subheader ${section?.cardHeader?.className}`} subHeader={t(section?.cardHeader?.value)}></TextBlock>
    )}
    <Photos data = {section?.photo} OpenImage={OpenImage} fullImage={section?.fullImage} />
    </>
  )
}