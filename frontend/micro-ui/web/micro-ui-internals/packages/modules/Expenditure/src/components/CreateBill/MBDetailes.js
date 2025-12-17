import React from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from "react-router-dom";
import { CardSectionHeader, Card, CardHeader, CardLabel, LinkLabel, Row, StatusTable, CitizenInfoLabel} from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../templates/ApplicationDetails";
import { InfoCard,Button } from "@egovernments/digit-ui-components";

const getMBLinks = (mblinks, tenantId, workOrderNumber, history) => {
  return (
    <React.Fragment>
      <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
        {mblinks?.map((measurementNumber) => (
          <Button
            label={measurementNumber}
            onClick={() =>
              history.push(
                `/${window.contextPath}/employee/measurement/view?tenantId=${tenantId}&workOrderNumber=${workOrderNumber}&mbNumber=${measurementNumber}`
              )
            }
            style={{display:"inline" }}
            variation={"link"}
            textStyles={{width:"fit-content"}}
          />
        ))}
      </div>
    </React.Fragment>
  );
};

const MBDetailes = ({ formdata }) => {
  const { t } = useTranslation();
  const { tenantId, workOrderNumber } = Digit.Hooks.useQueryParams();
  let { allMeasurementsIds, totalMaterialAmount, totalPaidAmountForSuccessfulBills, isMeasurementLoading } = Digit.Hooks.paymentInstruction.useMBDataForPB({workOrderNumber,tenantId});

  //setting the value for mb details in view page
  if(formdata?.isMbDetails && formdata?.mbValidationData)
  {
    allMeasurementsIds = formdata?.mbValidationData?.allMeasurementsIds;
    totalMaterialAmount = formdata?.mbValidationData?.totalMaterialAmount
    totalPaidAmountForSuccessfulBills = formdata?.mbValidationData?.totalPaidAmountForSuccessfulBills;
  }
  const history = useHistory();
  const formattingNumber = (amount) => {
    if(amount)
      return Digit.Utils.dss.formatterWithoutRound((parseFloat(amount)).toFixed(2),"number");
    else
      return 0;
  }
  let rowStyle = window?.location.href.includes("create-purchase-bill") ? {marginRight:"9%"} : {}
 
  return (
    <div>
      {/* {!isMeasurementLoading && allMeasurementsIds && !(allMeasurementsIds?.length > 0) && <CitizenInfoLabel textStyle={{color:"#505A5F"}} fill={"#D4351C"} style={{marginBottom:"2rem", maxWidth:"40%",backgroundColor:"#EFC7C1"}} info={t("WORKS_PB_INFO")} text={t("WORKS_INFO_MB_NOT_CREATED")} />} */}
      {!isMeasurementLoading && allMeasurementsIds && !(allMeasurementsIds?.length > 0) && !(window.location.href.includes("-bill-details")) && (
        <InfoCard
          populators={{
            name: "doc-banner-infoCard",
          }}
          variant="error"
          text={t("WORKS_INFO_MB_NOT_CREATED")}
          label={t("WORKS_PB_INFO")}
          style={{ margin: "0px", marginBottom: "1.5rem" }}
        />
      )}
      <StatusTable>
        <Row
          className="border-none mb-numbers-container"
          label={t("WORKS_MB_NUMBERS")}
          amountStyle={{ overflow: "auto", whiteSpace: "nowrap", marginBottom: "-15px" }}
          text={allMeasurementsIds?.length > 0 ? getMBLinks(allMeasurementsIds, tenantId, workOrderNumber, history) : "NA"}
          textStyle={{ overflow: "hidden", width: "40%", marginRight: window.location.href.includes("create-purchase-bill") ? "29%" : "20%" }}
        />
        <Row
          className="border-none"
          label={t("WORKS_TOTAL_MATERIAL_UTILIZED")}
          text={formattingNumber(totalMaterialAmount) || "0"}
          textStyle={{ whiteSpace: "pre", ...rowStyle }}
        />
        <Row
          className="border-none"
          label={t("WORKS_TOTAL_PAID_AMOUNT")}
          text={formattingNumber(totalPaidAmountForSuccessfulBills) || "0"}
          textStyle={{ whiteSpace: "pre", ...rowStyle }}
        />
        <Row
          className="border-none"
          label={t("WORKS_TOTAL_UNPAID_AMOUNT")}
          text={formattingNumber(totalMaterialAmount - totalPaidAmountForSuccessfulBills) || "0"}
          textStyle={{ whiteSpace: "pre", ...rowStyle, color: totalMaterialAmount - totalPaidAmountForSuccessfulBills < 0 && "red" }}
        />
      </StatusTable>
    </div>
  );
}

export default MBDetailes