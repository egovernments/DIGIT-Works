import React from "react";
import PopUp from "./PopUp";
import Card from "./Card";
import { AlertIcon } from "./svgindex";
import CardSubHeader from "./CardSubHeader";
import CardText from "./CardText";
import Button from "./Button";

  /* Alert popup when needed to have a warning action   */

const AlertPopUp = ({setIsPopupOpen,setShowModal,t,label,...props}) => {
    
    return (
            <PopUp>
            <div className="popup-view-alaysis" style={{marginTop: "20% !important", width:"27rem"}}>
                <Card style={{padding:"2rem"}}>
                <div style={{display:"flex", marginBottom:"-2rem"}}>
                    <AlertIcon style={{marginLeft:"-1.4rem"}} />
                    <CardSubHeader>{t("CS_ALERT")}</CardSubHeader>
                </div>
                <CardText className="estimate-analysis-cardheader">{t(label)}</CardText>
                <div style={{display:"inline-flex",width:"100%",gap:"5%"}}>
                    <Button
                    style={{width:"45%"}}
                    label={t("CANCEL")}
                    variation="secondary"
                    onButtonClick={props?.onButtonClickCancel ? props?.onButtonClickCancel : () => {
                        setIsPopupOpen(false);
                        setShowModal(false);
                    }}
                    type="button"
                    />
                    <Button
                    style={{width:"45%"}}
                    label={t("CONFIRM")}
                    variation="primary"
                    onButtonClick={props?.onButtonClickConfirm ? props?.onButtonClickConfirm : () => {
                        setIsPopupOpen(false);
                        setShowModal(true);
}}
                    type="button"
                    />
                </div>
                </Card>
            </div>
        </PopUp>
    )
}

export default AlertPopUp;