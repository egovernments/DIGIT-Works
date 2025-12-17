import { Card, Header, Button, Loader, Row, StatusTable, LinkButton ,CardSectionHeader} from "@egovernments/digit-ui-react-components";
import React, { Fragment, useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import ViewTotalEstAmount from "../../../Estimate/src/components/ViewTotalEstAmount";

const RateAmountGroup = (props) => {
  const { t } = useTranslation();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [existingData, setExistingData] = useState(0.0);

  const {
    mode,
    detail: { newValue, sorId ,uom},
  } = props;

  const requestCriteria = {
    url: "/mdms-v2/v2/_search",
    body: {
      MdmsCriteria: {
        tenantId: tenantId,
        filters: {
          sorId: sorId,
        },
        schemaCode: "WORKS-SOR.Rates",
        limit: 100,
        offset: 0,
      },
    },
    changeQueryName: "ratesQuery",
  };

  // const requestCriteria = {
  //   url: "/mdms-v2/v2/_search",
  //   body: {
  //     MdmsCriteria: {
  //       tenantId: tenantId,
  //       // filters: {
  //       //   sorId: sorId,
  //       // },
  //       uniqueIdentifiers:[`${sorId}`],
  //       schemaCode: "WORKS-SOR.Rates",
  //       limit: 100,
  //       offset: 0,
  //     },
  //   },
  //   changeQueryName: "ratesQuery",
  // };

  // const requestCriteria = {
  //   url: "/mdms-v2/v2/_search",
  //   body: {
  //     MdmsCriteria: {
  //       tenantId: tenantId,
  //       moduleDetails: [
  //         {
  //           moduleName: "WORKS-SOR",
  //           masterDetails: [
  //             {
  //               name: "Rates",
  //               filter: `[?(@.sorId=='${sorId}')]`,
  //             },
  //           ],
  //         },
  //       ],
  //     },
  //   },
  //   changeQueryName: "ratesQuery",
  // };

  const { isLoading, data: RatesData } = Digit.Hooks.useCustomAPIHook(requestCriteria);
  useEffect(() => {
    setExistingData(RatesData?.mdms.length !== 0 ? (RatesData?.mdms[0].data?.rate === undefined ? 0.0 : RatesData?.mdms[0].data?.rate) : 0.0);
  }, [RatesData]);

  return (
    <React.Fragment>
      <div
        className="flex"
        style={{
          display: "flex",
          flexDirection: "row",
          width: "100%",
          justifyContent: "flex-end",
          paddingRight: "0%",
          alignItems: "center",
          marginTop: "-30px",
        }}
      >
        <div style={{ paddingRight: "1%" }}>
        
        <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "2rem" }}>
        
            <div style={{ display: "flex", flexDirection: 'row', justifyContent: "space-between", padding: "1rem", border: "1px solid #D6D5D4", borderRadius: "5px" }}>
                <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize:"18px"  }}>{`${t("RA_EXISTING_RATE")}/${uom}`}</CardSectionHeader>
                <CardSectionHeader style={{ marginBottom: "0px", fontSize:"24px", fontWeight:"700"}}>{
                  Digit.Utils.dss.formatterWithoutRound(parseFloat(existingData).toFixed(2), "number", undefined, true, undefined, 2)
                  
                }</CardSectionHeader>
            </div>
        </div>
          {/*<ViewTotalEstAmount mode={"VIEW"} detail={{ showTitle: "RA_EXISTING_RATE", value: existingData }} />*/}
        </div>
        <div style={{ paddingLeft: "1%" }}>
        <div style={{ display: "flex", justifyContent: "flex-end", marginTop: "2rem" }}>
        
            <div style={{ display: "flex", flexDirection: 'row', justifyContent: "space-between", padding: "1rem", border: "1px solid #D6D5D4", borderRadius: "5px" }}>
                <CardSectionHeader style={{ marginRight: "1rem", marginBottom: "0px", color: "#505A5F", fontSize:"18px"  }}>{`${t("RA_NEW_RATE")}/${uom}`}</CardSectionHeader>
                <CardSectionHeader style={{ marginBottom: "0px", fontSize:"24px", fontWeight:"700"}}>{
                  
                  Digit.Utils.dss.formatterWithoutRound(parseFloat(newValue).toFixed(2), "number", undefined, true, undefined, 2)  
                }</CardSectionHeader>
            </div>
        </div>
          {/*<ViewTotalEstAmount mode={"VIEW"} detail={{ showTitle: "RA_NEW_RATE", value: newValue }} />*/}
        </div>
      </div>
    </React.Fragment>
  );
};

export default RateAmountGroup;