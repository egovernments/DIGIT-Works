import React, { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, LinkLabel, AddFileFilled, ArrowLeftWhite, ActionBar, SubmitBar} from "@egovernments/digit-ui-react-components";

const CreateWOResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [ contractNumberList, setContractNumberList ] = useState(queryStrings?.contractNumber.split(','));
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true);

    const navigate = (page) =>{
        switch(page){
            case "contracts-inbox" : {
                
            }
        }
    }

    return (
        <Card>
            <Banner 
                successful={isResponseSuccess}
                message={`${isResponseSuccess ? t("CONTRACTS_WO_CREATED_FORWARDED") : t("CONTRACTS_WO_FAILED")}`}
                info={`${isResponseSuccess ? t("CONTRACTS_WO_ID") : ""}`}
                multipleResponseIDs={contractNumberList}
                whichSvg={`${isResponseSuccess ? "tick" : null}`}
            />
            <div style={{display: "flex"}}>
                <LinkLabel style={{ display: "flex", marginRight : "3rem" }} onClick={()=>navigate('contracts-inbox')}>
                    <ArrowLeftWhite  fill="#F47738" style={{marginRight: "8px", marginTop : "3px"}}/>{t("COMMON_GO_TO_INBOX")}
                </LinkLabel> 
            </div>
            <ActionBar>
                <Link to={`/${window.contextPath}/employee`}>
                    <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
                </Link>
            </ActionBar>
        </Card>
    )
}

export default CreateWOResponse;
