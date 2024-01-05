import React,{Fragment, useState} from 'react'
import {
    Card,
    CardSectionHeader,
    LabelFieldPair,
    CardLabel,
    PopUp,
    LinkButton,
    Button,
} from "@egovernments/digit-ui-react-components";
import { useTranslation } from 'react-i18next';

const ViewAnalysisStatement = ({watch,formState,...props}) => {
    const {t} = useTranslation();
    const { register, errors, setValue, getValues, formData } = props
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    let isCreateOrUpdate = /(measurement\/create|estimate\/create-detailed-estimate|estimate\/update-detailed-estimate|measurement\/update|estimate\/create-revision-detailed-estimate|estimate\/update-revision-detailed-estimate)/.test(window.location.href);
    let isEstimateCreateorUpdate = /(estimate\/create-detailed-estimate|estimate\/update-detailed-estimate|estimate\/create-revision-detailed-estimate|estimate\/update-revision-detailed-estimate)/.test(window.location.href);
    //Defined the codes for charges upserted in mdmsV2
    const ChargesCodeMapping = {
        LabourCost : "LA",
        MaterialCost : "MA",
        MachineryCost : "MHA",
    }

    const tenantId = Digit.ULBService.getCurrentTenantId();
    let isEstimate = window.location.href.includes("/estimate/");

    const requestCriteria = {
        url: "/mdms-v2/v1/_search",
        body: {
        MdmsCriteria: {
            tenantId: tenantId,
            moduleDetails: [
            {
                moduleName: "WORKS-SOR",
                masterDetails: [
                {
                    name: "Rates",
                    //filter: `[?(@.sorId=='${sorid}')]`,
                },
                ],
            },
            ],
        },
        },
        changeQueryName:"ratesQuery"
    };

    const { isLoading, data : RatesData} = Digit.Hooks.useCustomAPIHook(requestCriteria);
    let currentDateInMillis = isEstimateCreateorUpdate ? new Date().getTime() : formData?.auditDetails?.createdTime; 

    //this method is used for calculating labour charges which rate * qty(current Mb entry)
    function getAnalysisCost(category){
        let SORAmount = formData?.SORtable?.reduce((tot,ob) => {
            let amount = ob?.amountDetails?.reduce((total,item) => item?.heads?.includes(category) ? (item?.amount + total) : total,0);
            return (tot + amount * ob?.currentMBEntry)
        },0);
        SORAmount = SORAmount ? SORAmount : 0;
        if(SORAmount == 0)
        {
            SORAmount = formData?.SORtable?.reduce((tot,ob) => {
                //let amountDetails = RatesData?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => rate?.sorId === ob?.sorId || rate?.sorId === ob?.sorCode)?.[0]?.amountDetails;
                let amountDetails = RatesData?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => {
                    // Convert validFrom and validTo to milliseconds
                    let validFromInMillis = new Date(parseInt(rate?.validFrom)).getTime();
                    let validToInMillis = rate?.validTo ? new Date(parseInt(rate?.validTo)).getTime() : Infinity;
                    // Check if the current date is within the valid date range
                    return rate.sorId === ob?.sorId || rate.sorId === ob?.sorCode
                      && validFromInMillis <= currentDateInMillis
                      && currentDateInMillis < validToInMillis;
                  })?.[0]?.amountDetails;
                let amount = amountDetails?.reduce((total,item) => item?.heads?.includes(category) ? (item?.amount + total) : total,0);
                return (tot + amount * ob?.currentMBEntry)
            },0);
        }
        if(window.location.href.includes("estimate-details"))
        {
                    if(category === "LA" && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.labour) SORAmount =  formData?.additionalDetails?.labourMaterialAnalysis?.labour;
                    if(category === "MA" && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.material) SORAmount =  formData?.additionalDetails?.labourMaterialAnalysis?.material;
                    if(category === "MHA" && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.machinery) SORAmount =  formData?.additionalDetails?.labourMaterialAnalysis?.machinery;
        }
        //Conditions is used in the case of View details to capture the data from additional details
        // if(category === "LA" && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.labour) return formData?.additionalDetails?.labourMaterialAnalysis?.labour;
        // if(category === "MA" && SORAmount =getAnalysisCost= 0 && formData?.additionalDetails?.labourMaterialAnalysis?.material) return formData?.additionalDetails?.labourMaterialAnalysis?.material;
        // if(category === "MHA" && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.machinery) return formData?.additionalDetails?.labourMaterialAnalysis?.machinery;
        // if(window.location.href.includes("update-detailed-estimate"))
        // {
        // if(category === "LA" && SORAmount == 0  && formData?.labourMaterialAnalysis?.labour) return formData?.labourMaterialAnalysis?.labour;
        // if(category === "MA" && SORAmount == 0 && formData?.labourMaterialAnalysis?.material) return formData?.labourMaterialAnalysis?.material;
        // if(category === "MHA" && SORAmount == 0 && formData?.labourMaterialAnalysis?.machinery) return formData?.labourMaterialAnalysis?.machinery;
        // }

        SORAmount = SORAmount ? SORAmount : 0;
        return Digit.Utils.dss.formatterWithoutRound((parseFloat(SORAmount)).toFixed(2),"number")?.includes(".") ? Digit.Utils.dss.formatterWithoutRound((parseFloat(SORAmount)).toFixed(2),"number") : `${Digit.Utils.dss.formatterWithoutRound((parseFloat(SORAmount)).toFixed(2),"number")}.00`;        
    }
    
   
    
  return (
        <Fragment>
        <LinkButton className="view-Analysis-button" style={isCreateOrUpdate ? {marginTop:"-3.5%",textAlign:"center", width:"17%"}: {textAlign:"center",width:"17%"}} onClick={() => setIsPopupOpen(true)} label={isEstimate ? t("ESTIMATE_ANALYSIS_STM") : t("MB_UTILIZATION_STM")}></LinkButton>
        {isPopupOpen && <PopUp>
            <div className="popup-view-alaysis">
            <Card>
            <CardSectionHeader className="estimate-analysis-cardheader">{isEstimate ? t(`ESTIMATE_COST_ANALYSIS_HEADER`): t(`MB_UTILIZATION_STM_HEADER`)}</CardSectionHeader>
            <LabelFieldPair style={{marginBottom:'1rem', marginTop:"3rem", justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{isEstimate ? `${t(`ESTIMATE_LABOUR_COST`)}`: t(`MB_LABOUR_UTILIZATION`)}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.LabourCost)}</CardLabel>
            </LabelFieldPair >
            <LabelFieldPair style={{marginBottom:'1rem', justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{isEstimate ? `${t(`ESTIMATE_MATERIAL_COST`)}` : t(`MB_MATERIAL_UTILIZATION`)}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.MaterialCost)}</CardLabel>
            </LabelFieldPair>
            <LabelFieldPair style={{marginBottom:'3rem', justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{isEstimate ? `${t(`ESTIMATE_MACHINERY_COST`)}` : t("MB_MACHINERY_UTILIZATION")}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.MachineryCost)}</CardLabel>
            </LabelFieldPair>
            <Button
             style={{marginLeft:"70%", width:"30%"}}
             label={"OK"}
             variation="primary"
             onButtonClick={() => {
                setIsPopupOpen(false);
             }}
             type="button"
             />
            </Card>
            </div>
        </PopUp>}
        </Fragment>
  );
}

export default ViewAnalysisStatement;