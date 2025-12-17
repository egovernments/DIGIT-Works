import React, {useEffect, useMemo, useState} from "react";
import { useTranslation } from "react-i18next";
import { Header, InboxSearchComposer,Loader, MultiLink } from "@egovernments/digit-ui-react-components";
import { paymentTrackerSearchConfig } from "../../configs/paymentTrackerSearchConfig";
import { Toast, ActionBar, SubmitBar } from "@egovernments/digit-ui-components";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";

const PaymentTrackerSearch = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const tenantId = Digit.ULBService.getCurrentTenantId();
    const [toast, setToast] = useState({show : false, label : "", type : ""});

    // const configModuleName = Digit.Utils.getConfigModuleName()
    // const tenant = Digit.ULBService.getStateId();
    // const { isLoading, data } = Digit.Hooks.useCustomMDMS(
    //     tenant,
    //     configModuleName,
    //     [
    //         {
    //             "name": "SearchEstimateWMSConfig"
    //         }
    //     ],
    //     {
    //       select: (data) => {
            
    //           const config = data?.[Digit.Utils.getConfigModuleName()]?.SearchEstimateWMSConfig?.[0];
              
    //           return config
    //         },
    //     }
    // );
    //let configs = paymentTrackerSearchConfig?.paymentTrackerSearchConfig?.[0];
    
    let configs = useMemo(
        () => Digit.Utils.preProcessMDMSConfigInboxSearch(t, paymentTrackerSearchConfig?.paymentTrackerSearchConfig?.[0], "sections.search.uiConfig.fields",{
          updateDependent : [
            {
              key : "fromProposalDate",
              value : [new Date().toISOString().split("T")[0]]
            },
            {
              key : "toProposalDate",
              value : [new Date().toISOString().split("T")[0]]
            }
          ]
        }
        ),[paymentTrackerSearchConfig]);

    //remove Toast after 3s
    useEffect(()=>{
        if(toast?.show) {
        setTimeout(()=>{
          setToast({show : false, label : "", type : ""});
        },3000);
        }
    },[toast?.show]);
      
    const { mutate: generateexcel } = Digit.Hooks.useGenerateExcel();

    const handleGenerateExcel = async (data) => {

      const payload = {
        "report": {
          "tenantId": tenantId,
          "reportName": "expense",
          "requestPayload": {
              "tenantId": tenantId
          }
        }
      }

      await generateexcel(payload, {
        onError: async (error, variables) => {
          setToast(()=>({show : true, label : t("EXP_EXCEL_GENERATE_FAILED"), type : "error"}));
        },
        onSuccess: async (responseData, variables) => {
          setToast(()=>({show : true, label : t("EXP_EXCEL_GENERATING"), type : "success"}));
        },
    });
    }

    //if (isLoading) return <Loader />
    return (
      <React.Fragment>
        <div className={"employee-application-details"} style={{ marginBottom: "15px" }}>
          <Header className="works-header-search" styles={{ marginLeft: "0px", paddingTop: "10px" }}>
            {t(configs?.label)}
          </Header>
          <MultiLink onHeadClick={() => {history.push(`/${window.contextPath}/employee/expenditure/view-jobs-excel`) }} downloadBtnClassName={"employee-download-btn-className"} label={t("CS_COMMON_DOWNLOAD")} />
        </div>
        <div className="inbox-search-wrapper">
            <InboxSearchComposer configs={configs}></InboxSearchComposer>
        </div>
        {toast?.show && <Toast type={toast?.type} label={toast?.label} isDleteBtn={true} onClose={() => setToast({show : false, label : "", type : ""})} />}
        <ActionBar style={{ display: "flex", gap: "24px", justifyContent: "flex-end" }}>
          <SubmitBar
            label={t("EXP_GENERATE_EXCEL")}
            onSubmit={() => handleGenerateExcel()}
            style={{width: "auto"}}
            // disabled={!selectedSorIds.hasOwnProperty("sorIds") || selectedSorIds?.sorIds?.length <= 0 || selectedSorIds?.sorType !== "W"}
          />
        </ActionBar>
      </React.Fragment>
    )
}

export default PaymentTrackerSearch;