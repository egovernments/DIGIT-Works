import React, { useState } from "react";
import { Link, useHistory, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { Banner, Card, LinkLabel, AddFileFilled, ArrowLeftWhite, ActionBar, SubmitBar} from "@egovernments/digit-ui-react-components";

const CreatePurchaseBillResponse = () => {
    const {t} = useTranslation();
    const history = useHistory();
    const queryStrings = Digit.Hooks.useQueryParams();
    const [  billNumberList, setBillNumberList ] = useState(queryStrings?.billNumber.split(','));
    const [ isResponseSuccess, setIsResponseSuccess ] = useState(queryStrings?.isSuccess === "true" ? true : queryStrings?.isSuccess === "false" ? false : true);
    const {state} = useLocation();

    return (
        <Card>
            <Banner 
                successful={isResponseSuccess}
                message={t(state?.message)}
                info={`${state?.showID ? t("EXP_PB_ID") : ""}`}
                multipleResponseIDs={billNumberList}
                whichSvg={`${isResponseSuccess ? "tick" : null}`}
            />
            <ActionBar>
                <Link to={`/${window.contextPath}/employee`}>
                    <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
                </Link>
            </ActionBar>
        </Card>
    )
}

export default CreatePurchaseBillResponse;
