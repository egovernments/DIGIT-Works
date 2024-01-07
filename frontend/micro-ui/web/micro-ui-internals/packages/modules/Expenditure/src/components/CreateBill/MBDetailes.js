import React from 'react'
import { useTranslation } from 'react-i18next'
import { useHistory } from "react-router-dom";
import { CardSectionHeader, Card, CardHeader, CardLabel, LinkLabel, Row, StatusTable} from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const getMBLinks = (mblinks, tenantId, workOrderNumber, history) => {

    return(
    <React.Fragment>
    <div style={{}}>
    {mblinks?.map((measurementNumber) => (
    <LinkLabel style={{marginRight:"13px", display:"inline", backgroundColor:"white", border:"1px solid #ed9e5b", padding:"1.5px"}} onClick={() => history.push(`/${window.contextPath}/employee/measurement/view?tenantId=${tenantId}&workOrderNumber=${workOrderNumber}&mbNumber=${measurementNumber}`)}>
        {measurementNumber}
    </LinkLabel>
    ))}
    </div>
    </React.Fragment>
    );

}

const MBDetailes = ({ formdata }) => {
  const { t } = useTranslation();
  const { tenantId, workOrderNumber } = Digit.Hooks.useQueryParams();
  const { allMeasurementsIds, totalMaterialAmount, totalPaidAmountForSuccessfulBills } = Digit.Hooks.paymentInstruction.useMBDataForPB({workOrderNumber,tenantId});
  const history = useHistory();
  const formattingNumber = (amount) => {
    if(amount)
      return Digit.Utils.dss.formatterWithoutRound((parseFloat(amount)).toFixed(2),"number");
    else
      return 0;
  }

 
  return (
        <div style={{marginTop:"2rem", marginBottom:"2rem"}}>
        <StatusTable>
        <Row className="border-none" label={t("WORKS_MB_NUMBERS")} amountStyle={{overflow:"auto", whiteSpace:"nowrap", marginBottom:"-15px"}} text={getMBLinks(allMeasurementsIds, tenantId, workOrderNumber, history)} textStyle={{ overflow:"hidden", width:"40%", marginRight:"20%" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_MATERIAL_UTILIZED")} text={formattingNumber(totalMaterialAmount) || "0"} textStyle={{ whiteSpace: "pre" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_PAID_AMOUNT")} text={formattingNumber(totalPaidAmountForSuccessfulBills) || "0"} textStyle={{ whiteSpace: "pre" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_UNPAID_AMOUNT")} text={formattingNumber(totalMaterialAmount - totalPaidAmountForSuccessfulBills) || "0"} textStyle={{ whiteSpace: "pre" }} />
       </StatusTable>
       </div>
  )
}

export default MBDetailes