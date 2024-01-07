import { Button, Card, CardText, Modal,PopUp,RadioButtons } from '@egovernments/digit-ui-react-components'
import React from 'react'

const WarningPopUp = ({setShowWfModal, label, setShowPopUp, t}) => {

  return (
      <div>
        {<PopUp>
            <div className="popup-view-alaysis" style={{marginTop: "20% !important"}}>
            <Card style={{padding:"2rem"}}>
            <CardText className="estimate-analysis-cardheader">{t(label)}</CardText>
            <CardText className="estimate-analysis-cardheader">{t("WORKS_MR_UNPAID_AMT_QUES")}</CardText>
            <div style={{display:"inline-flex",width:"100%",marginLeft:"30%",gap:"5%"}}>
            <Button
            style={{width:"16%"}}
            label={t("NO")}
            variation="primary"
            onButtonClick={() => {
                setShowPopUp(false);
                setShowWfModal(false);
            }}
            type="button"
            />
            <Button
            style={{width:"16%"}}
            label={t("YES")}
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