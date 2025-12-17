import { Card, Header, Button, Loader, Row, StatusTable, LinkButton, CitizenInfoLabel } from "@egovernments/digit-ui-react-components";
import React, { Fragment } from "react";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";
import { Link } from "react-router-dom";

import { CardSubHeader } from "@egovernments/digit-ui-react-components";
import { useEffect } from "react";

const RateCardWithRightButton = (props) => {
  const { t } = useTranslation();

  const { sections } = props;

  useEffect(() => {}, [sections]);

  const bannerStyle = {
    marginTop: "10px",
    background: "#c7e0f1", // Default background
    padding: "10px",
    width: "100%",
     borderRadius: "4px"
    //boxSizing: "border-box",
  };

  return (
    <React.Fragment>
      {sections.cardHeader && <CardSubHeader style={sections?.cardHeader?.inlineStyles}>{t(sections.cardHeader.value)}</CardSubHeader>}
      <div>
        <div style={{ display: "flex", flexDirection: "row" }}>
          <div style={{ width: "60%" }}>
            <div className="rate-data-table">
              {sections?.values?.map((row, rowIdx) => {
                const displayValue = row?.value !== undefined && row?.value !== null ? row.value : "NA";
                return (
                  <Row
                    key={row.key}
                    label={t(row.key)}
                    text={
                      row?.isLink ? (
                        <div>
                          <Link to={row?.to}>
                            <span className="link" style={{ color: "#C84C0E" }}>
                              {t(displayValue)}
                            </span>
                          </Link>
                        </div>
                      ) : (
                        t(displayValue)
                      )
                    }
                    last={rowIdx === sections?.values?.length - 1}
                    caption={row.caption}
                    className="border-none"
                    /* privacy object set to the Row Component */
                    privacy={row?.value?.privacy}
                    rowContainerStyle={{marginBottom:"0px"}}
                    textStyle={{marginBottom:"0px"}}
                    labelStyle={{marginBottom:"0px"}}
                    amountStyle={{}}
                  />
                );
              })}
            </div>
          </div>
          {/* TODO:[kept on hold ]*/}
          {/*<div style={{ width: "40%", textAlign: "start", alignItems: "start", marginTop: "-20px" }}>
          <LinkButton
            className="view-rate-Analysis-button"
            style={false ? { marginTop: "-3.2%", textAlign: "center", width: "35%" } : { marginTop: "3.0%", textAlign: "center", width: "35%" ,padding:"5px"}}
            onClick={() => setIsPopupOpen(true)}
            label={true ? t("View Rate Analysis History") : t("MB_UTILIZATION_STM")}
          ></LinkButton>
          </div>*/}
        </div>

        <div style={bannerStyle}>
          <CitizenInfoLabel className="doc-banner" textType={"Componenet"} style={{ margin: "0px", padding:"8px", }} info={t("RA_ACTIVE_RATE_INFO_LABEL")} text={t("RA_ACTIVE_RATE_INFO_CONTENT_LABEL")} />
        </div>
      </div>
    </React.Fragment>
  );
};

export default RateCardWithRightButton;
