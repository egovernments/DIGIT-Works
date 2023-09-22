import React from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { EditIcon, AddNewIcon, InboxIcon , ArrowRightInbox } from "@egovernments/digit-ui-react-components";

import { useHistory } from "react-router-dom";

import { Banner, Card, ActionBar, SubmitBar } from "@egovernments/digit-ui-react-components"; // Import the Banner component you provided
import { withRouter } from "react-router-dom";
const MeasurementServiceResponse = () => {

    const { t } = useTranslation();
    const history = useHistory();

    // Get the current URL
    const currentURL = window.location.href;

    // console.log(currentURL);

    // Create a URL object from the current URL
    const url = new URL(currentURL);
    // console.log(url);

    // Get the value of the "mbreference" query parameter
    const mbReference = url.searchParams.get('mbreference');


    // console.log(mbReference); // This will log "Mb848484"







    //     const responseObj = location?.state?.responseData;

    //    console.log(responseObj?.get?.responseInfo?.status);

    // Now you can work with the response object as needed

    // const isApplicationSubmitted = responseObj?.get?.responseInfo?.status === "successful";


    const goToHome = () => {


        history.push({
            pathname: "/digit-ui/employee",

        });



    };

    // Customize the message based on success or failure
    const message = true
        ? t("MB_SUCCESSFUL_MESSAGE")
        : t("MB_ERROR_MESSAGE");

    return (
        <React.Fragment>
            <Card>
                <div>
                    <Banner
                        successful={true}
                        message={message}
                        applicationNumber={t("MB_REFERENCE_NUMBER") + " : "   + mbReference}
                    />
                </div>

                <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '8px' }}>


                    <div className="link " >
                        <ArrowRightInbox style={{ width: '24px', height: '24px' }} />
                        <Link to={`/${window.contextPath}/employee/measurement/inbox`}>
                            {"MB_GO_INBOX"}
                        </Link>
                    </div>

                    <div className="link">
                        <AddNewIcon style={{ width: '24px', height: '24px' }} />
                        <Link to={`/${window.contextPath}/employee/measurement/inbox`}>
                            {"MB_ADD_READING"}
                        </Link>
                    </div>
                </div>

            </Card>

            <ActionBar>
                <SubmitBar label={"MB_GO_HOME"} onSubmit={goToHome} />
            </ActionBar>
        </React.Fragment>

    );
};
export default MeasurementServiceResponse;