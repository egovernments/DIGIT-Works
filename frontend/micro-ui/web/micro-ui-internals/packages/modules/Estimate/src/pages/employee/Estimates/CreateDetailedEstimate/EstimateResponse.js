import { useQueryClient } from "react-query";
import React, { useEffect,Fragment } from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { CreateEstimateIcon, DownloadImgIcon, GotoInboxIcon, ArrowLeftWhite } from "@egovernments/digit-ui-react-components";
import { useHistory, useLocation } from "react-router-dom";
import { PanelCard, Button } from "@egovernments/digit-ui-components";

// state = {
//     header,idText,id,message,links
// }

// const state = {
//     header:"Estimate Created and Forwarded Successfully",
//     id:"EP/ENG/00001/07/2021-22",
//     info:"Estimate ID",
//     message:"A new Estimate has been created successfully and forwarded to Designation or the <Department>  Department for processing.",
//     links:[
//         {
//             name:"Create new Estimate",
//             redirectUrl:"/digit-ui/employee/works/create-estimate",
//             code:"",
//             svg:"CreateEstimateIcon"
//         }
//     ]
// }

/**
 * 
// A common reusable component to use for the response screen
// Pass the state object to it while doing history.push  
 */
const EstimateResponse = (props) => {
    const { state } = useLocation()
    const history = useHistory()
    const { t } = useTranslation()
    const tenantId = Digit.ULBService.getCurrentTenantId();

    const EstimateSession = Digit.Hooks.useSessionStorage("NEW_ESTIMATE_CREATE", {});
    const [sessionFormData, clearSessionFormData] = EstimateSession;
     // remove session form data if user navigates away from the estimate create screen
     useEffect(()=>{
        if (!window.location.href.includes("create-estimate") && sessionFormData && Object.keys(sessionFormData) != 0) {
        clearSessionFormData();
        }
    },[location]);

    //we have two types of icon currently -> add,inbox(CreateEstimateIcon,Inbox Icon)
    const renderIcon = (type, link) => {

        switch (type) {
            case "add":
                return <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p>
            case "inbox":
                return <p><ArrowLeftWhite fill="#C84C0E" style={{ display:"inline",marginRight:"0.5rem",marginTop:"-2px"}} /> {t(link.name)}</p>
            case "download":
                return <p style={{ "display": "flex", "flexDirection": "row" }}><DownloadImgIcon style={{ "display": "inline" }} />{t(link.name)}</p>
            default:
                return <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p>

        }
    }
    const HandleDownloadPdf = async (tenantId, estimateNumber) => {
        const response = await Digit.WorksService.downloadEstimate(tenantId, estimateNumber);
        downloadPdf(new Blob([response.data], { type: "application/pdf" }), `Estimate-${estimateNumber}.pdf`);
    }
    const downloadPdf = (blob, fileName) => {
        if (window.mSewaApp && window.mSewaApp.isMsewaApp() && window.mSewaApp.downloadBase64File) {
            var reader = new FileReader();
            reader.readAsDataURL(blob);
            reader.onloadend = function () {
                var base64data = reader.result;
                window.mSewaApp.downloadBase64File(base64data, fileName);
            };
        } else {
            const link = document.createElement("a");
            // create a blobURI pointing to our Blob
            link.href = URL.createObjectURL(blob);
            link.download = fileName;
            // some browser needs the anchor to be in the doc
            document.body.append(link);
            link.click();
            link.remove();
            // in case the Blob uses a lot of memory
            setTimeout(() => URL.revokeObjectURL(link.href), 7000);
        }
    };

    const children = [
        <div style={{ display: "flex", justifyContent: "start", flexDirection: "row", alignItems: "flex-end" }}>
          {state?.links?.map(
            (link) =>
              link.isVisible && (
                <div
                  className="primary-label-btn d-grid"
                  style={{ marginLeft: "unset", marginBottom: "10px", padding: "0px 8px" }}
                  onClick={
                    link.type === "download"
                      ? () => {
                          HandleDownloadPdf(tenantId, state.id);
                        }
                      : () => {
                          history.push(link.redirectUrl);
                        }
                  }
                >
                  {renderIcon(link.type, link)}
                </div>
              )
          )}
        </div>
      ];
      return (
        <>
          <PanelCard
            type={"success"}
            message={state?.header}
            footerChildren={[
              <Link to={`/${window.contextPath}/employee`}>
                <Button label={t("CORE_COMMON_GO_TO_HOME")} variation="primary" type="button" />
              </Link>
            ]}
            children={children}
            info={state?.info}
            response={state?.id}
            description={t(state?.message)}
          />
        </>
      );
}

export default EstimateResponse