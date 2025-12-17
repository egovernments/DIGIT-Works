import { AlertIcon, Button, Card, CardSubHeader, CardText, Modal,PopUp,RadioButtons } from '@egovernments/digit-ui-react-components'
import React from 'react'

const WarningPopUp = ({setShowWfModal, label, setShowPopUp, t}) => {

  return (
      <div>
        {<PopUp>
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
            onButtonClick={() => {
                setShowPopUp(false);
                setShowWfModal(false);
            }}
            type="button"
            />
            <Button
            style={{width:"45%"}}
            label={t("CONFIRM")}
            variation="primary"
            onButtonClick={() => {
                setShowPopUp(false);
                setShowWfModal(true);
            }}
            type="button"
            />
            </div>
            </Card>
            </div>
        </PopUp>}
      </div>
  )
}

export default WarningPopUp;