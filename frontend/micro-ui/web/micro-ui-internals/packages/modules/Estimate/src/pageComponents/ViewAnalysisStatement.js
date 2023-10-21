import React,{Fragment, useState} from 'react'
import {
    Card,
    Header,
    CardSectionHeader,
    LabelFieldPair,
    CardLabel,
    CardText,
    CardSectionSubText,
    TextInput,
    Dropdown,
    UploadFile,
    MultiUploadWrapper,
    ActionBar,
    SubmitBar,
    CardLabelError,
    Loader,
    PopUp,
    LinkButton,
    CardHeader,
    Button,
} from "@egovernments/digit-ui-react-components";

const ViewAnalysisStatement = ({watch,formState,...props}) => {
    const { t, register, errors, setValue, getValues, formData } = props
    const [isPopupOpen, setIsPopupOpen] = useState(false);
    console.log(formData,"fff")
    const ChargesCodeMapping = {
        LabourCost : "LH",
        MaterialCost : "MA",
        MachineryCost : "MH",
    }

    function getAnalysisCost(category){
        console.log(category);
        let SORAmount = formData?.SORtable?.reduce((tot,ob) => {
            let amount = ob?.amountDetails?.reduce((total,item) => item?.heads?.includes(category) ? (item?.amount + total) : total,0);
            return (tot + amount * ob?.currentMBEntry)
        },0);
        SORAmount = SORAmount ? SORAmount : 0;

        // let NONSORAmount = formData?.NONSORtable?.reduce((tot,ob) => {
        //     let amount = ob?.amountDetails?.reduce((total,item) => item?.heads?.includes(category) ? item?.amount + total : total,0);
        //     return amount * ob?.currentMBEntry;
        // },0);
        // NONSORAmount = NONSORAmount ? NONSORAmount : 0;
        console.log(SORAmount,"SOR");
        return (SORAmount).toFixed(2);
    }
    
   
    
  return (
        <Fragment>
        <LinkButton className="view-Analysis-button" onClick={() => setIsPopupOpen(true)} label={"View Analysis Statement"}></LinkButton>
        {isPopupOpen && <PopUp>
            <div className="popup-view-alaysis">
            <Card>
            <CardSectionHeader className="estimate-analysis-cardheader">{t(`Cost Analysis`)}</CardSectionHeader>
            <LabelFieldPair style={{marginBottom:'1rem', marginTop:"5rem", justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{`${t(`ESTIMATE_LABOUR_COST`)}`}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.LabourCost)}</CardLabel>
            </LabelFieldPair >
            <LabelFieldPair style={{marginBottom:'1rem', justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{`${t(`ESTIMATE_MATERIAL_COST`)}`}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.MaterialCost)}</CardLabel>
            </LabelFieldPair>
            <LabelFieldPair style={{marginBottom:'3rem', justifyContent:"space-between"}}>
                <CardLabel className="analysis-estimate-label">{`${t(`Machinery Cost`)}`}</CardLabel>
                <CardLabel>{getAnalysisCost(ChargesCodeMapping?.MachineryCost)}</CardLabel>
            </LabelFieldPair>
            <Button
             style={{marginLeft:"85%", width:"16%"}}
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