import { Banner, Card, Loader, CardText, ActionBar, SubmitBar } from "@egovernments/digit-ui-react-components";
import { useQueryClient } from "react-query";
import React, { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { CreateEstimateIcon,DownloadImgIcon,GotoInboxIcon } from "@egovernments/digit-ui-react-components";
import { useHistory,useLocation } from "react-router-dom";

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
const Response = (props) => {
  
  
    const {state}  = useLocation()
    const history = useHistory()
    const {t} = useTranslation()
    const tenantId = Digit.ULBService.getCurrentTenantId();
    
    //we have two types of icon currently -> add,inbox(CreateEstimateIcon,Inbox Icon)
    const renderIcon = (type,link) => {
      
      switch (type) {
        case "add":
          return <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p>
        case "inbox":
          return <p><GotoInboxIcon style={{ "display": "inline" }} /> {t(link.name)}</p>
        case "download":
          return <p style={{"display":"flex","flexDirection":"row"}}><DownloadImgIcon style={{ "display": "inline"}}/>{t(link.name)}</p>
        default:
          return <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p>
          
      }
    }
  const HandleDownloadPdf =async(tenantId,estimateNumber)=>{
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

  return (
    <Card>
          <Banner
              message={state.header}
              applicationNumber={state.id}
            //   info={props.mutation.isSuccess ? props.surveyTitle : ""}
               info={state.info}

              //successful={props.mutation.isSuccess}
              successful={true}
              whichSvg={"tick"}
          // svg={() => <TickMark fillColor="green" />}
          />
          <CardText>
              {/* {mutation.isSuccess ?
                  // ? t(`SURVEY_FORM_CREATION_MESSAGE`, {
                  //     surveyName: survey?.title,
                  //     fromDate: Digit.DateUtils.ConvertTimestampToDate(survey?.startDate),
                  //     toDate: Digit.DateUtils.ConvertTimestampToDate(survey?.endDate),
                  //   })
                  t("SURVEY_FORM_RESPONSE_MESSAGE")
                  : null} */}
                  {t(state.message)}
          </CardText>
          <div style={{"display":"flex","justifyContent":"end","flexDirection":"row","alignItems":"flex-end"}}>

              {/* <div className="primary-label-btn d-grid" style={{ marginLeft: "unset", marginBottom: "10px", padding: "0px 8px" }} 
                    onClick={handleDownloadPdf}>
                    <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t("Download")}</p>
              </div> */}

              {state.links.map(link => (
                link.isVisible && <div className="primary-label-btn d-grid" style={{ marginLeft: "unset", marginBottom: "10px", padding: "0px 8px" }} 
                onClick={
                  link.type === "download" 
                  ? ()=>{HandleDownloadPdf(tenantId,state.id)} 
                  : ()=> {history.push(link.redirectUrl)}
                  }>
                      {renderIcon(link.type,link)}
                      {/* <p><CreateEstimateIcon style={{ "display": "inline" }} /> {t(link.name)}</p> */}
                </div>
              ))}
              
          </div>
          <ActionBar>
              <Link to={`/${window.contextPath}/employee`}>
                  <SubmitBar label={t("CORE_COMMON_GO_TO_HOME")} />
              </Link>
          </ActionBar>

    </Card>
  )
}

export default Response