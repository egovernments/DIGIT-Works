import React from 'react'
import { useTranslation } from 'react-i18next'
import { CardSectionHeader, Card, CardHeader, CardLabel, LinkLabel, Row, StatusTable} from "@egovernments/digit-ui-react-components";
import ApplicationDetails from "../../../../templates/ApplicationDetails";

const getMBLinks = (mblinks, tenantId, workOrderNumber) => {

    return(
    <React.Fragment>
    <div style={{}}>
    {mblinks?.map((measurementNumber) => (
    <LinkLabel style={{marginRight:"20px", display:"inline"}} onClick={() => history.push(`/${window.contextPath}/employee/measurement/view?tenantId=${tenantId}&workOrderNumber=${workOrderNumber}&mbNumber=${measurementNumber}`)}>
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



 
  return (
        <div style={{marginTop:"2rem", marginBottom:"2rem"}}>
        <StatusTable>
        <Row className="border-none" label={t("WORKS_MB_NUMBERS")} text={getMBLinks(allMeasurementsIds, tenantId, workOrderNumber)} textStyle={{ whiteSpace: "pre" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_MATERIAL_UTILIZED")} text={totalMaterialAmount || "NA"} textStyle={{ whiteSpace: "pre" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_PAID_AMOUNT")} text={totalPaidAmountForSuccessfulBills || "NA"} textStyle={{ whiteSpace: "pre" }} />
        <Row className="border-none" label={t("WORKS_TOTAL_UNPAID_AMOUNT")} text={totalMaterialAmount - totalPaidAmountForSuccessfulBills || "NA"} textStyle={{ whiteSpace: "pre" }} />
       </StatusTable>
       </div>
  )
}

export default MBDetailes