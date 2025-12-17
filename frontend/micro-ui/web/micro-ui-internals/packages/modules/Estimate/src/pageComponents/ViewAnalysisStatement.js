import React, { useState, useEffect } from "react";
import { Loader, LinkButton } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom";
import { Toast, Button} from "@egovernments/digit-ui-components";

const ViewAnalysisStatement = ({ formData, ...props }) => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [showToast, setShowToast] = useState(null);
  const { revisionNumber,estimateNumber } = Digit.Hooks.useQueryParams();
  const isCreateOrUpdate = /(measurement\/create|estimate\/create-detailed-estimate|estimate\/update-detailed-estimate|measurement\/update|estimate\/create-revision-detailed-estimate|estimate\/update-revision-detailed-estimate)/.test(
    window.location.href
  );
  const isEstimate = window.location.href.includes("/estimate/");
  const isView = window.location.href.includes("estimate-details") || window.location.href.includes("measurement/view");

  const { mutate: AnalysisMutation } = Digit.Hooks.works.useCreateAnalysisStatement("WORKS");
  const { mutate: UtilizationMutation } = Digit.Hooks.works.useCreateUtilizationStatement("WORKS");

  const ChargesCodeMapping = {
    LabourCost: ["LA"],
    MaterialCost: ["MA", "RA", "CA", "EMF", "DMF", "ADC"],
    MachineryCost: ["MHA"],
    LabourCessCost:["LC"]
  };

  const requestCriteria = {
    url: "/mdms-v2/v1/_search",
    body: {
      MdmsCriteria: {
        tenantId: tenantId,
        moduleDetails: [
          {
            moduleName: "WORKS-SOR",
            masterDetails: [
              {
                name: "Rates",
                //filter: `[?(@.sorId=='${sorid}')]`,
              },
            ],
          },
        ],
      },
    },
    changeQueryName: "ratesQuery",
  };

  const { isLoading, data: RatesData } = Digit.Hooks.useCustomAPIHook(requestCriteria);
  let currentDateInMillis = isCreateOrUpdate ? new Date().getTime() : formData?.auditDetails?.createdTime;

  const getAnalysisCost = (categories) => {
    let SORAmount = 0;

    // if (categories.includes("LA") && SORAmount === 0 && formData?.additionalDetails?.labourMaterialAnalysis?.labour)
    // {

    //   SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.labour;
    // }
    // if (
    //   categories.some((cat) => ChargesCodeMapping.MaterialCost.includes(cat)) &&
    //   SORAmount === 0 &&
    //   formData?.additionalDetails?.labourMaterialAnalysis?.material
    // )
    // {
    //   SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.material;}
    // if (categories.includes("MHA") && SORAmount === 0 && formData?.additionalDetails?.labourMaterialAnalysis?.machinery)
    //  {
    //    SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.machinery;}

    // SORAmount = formData?.SORtable?.reduce((tot, ob) => {
    //   let amount = ob?.amountDetails?.reduce(
    //     (total, item) => total + (categories.some((category) => item?.heads?.includes(category)) ? item?.amount : 0),
    //     0
    //   );
    //   return tot + amount * ob?.currentMBEntry;
    // }, 0);

    SORAmount = SORAmount ? SORAmount : 0;
    if (SORAmount == 0) {
      SORAmount = formData?.SORtable?.reduce((tot, ob) => {
        //let amountDetails = RatesData?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => rate?.sorId === ob?.sorId || rate?.sorId === ob?.sorCode)?.[0]?.amountDetails;
        let amountDetails = RatesData?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => {
          // Convert validFrom and validTo to milliseconds
          let validFromInMillis = new Date(parseInt(rate?.validFrom)).getTime();
          let validToInMillis = rate?.validTo ? new Date(parseInt(rate?.validTo)).getTime() : Infinity;
          // Check if the current date is within the valid date range
          return (
            rate.sorId === ob?.sorId ||
            (rate.sorId === ob?.sorCode && validFromInMillis <= currentDateInMillis && currentDateInMillis < validToInMillis)
          );
        })?.[0]?.amountDetails;

        let amount = amountDetails?.reduce(
          (total, item) => total + (categories.some((category) => item?.heads?.includes(category)) ? item?.amount : 0),
          0
        );

        return tot + amount * ob?.currentMBEntry;
      }, 0);
    }

    // if (window.location.href.includes("estimate-details") || window.location.href.includes("measurement/view")) {
    //   if (categories?.includes("LA") && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.labour)
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.labour;
    //   if (
    //     categories.some((cat) => ChargesCodeMapping?.MaterialCost?.includes(cat)) &&
    //     SORAmount == 0 &&
    //     formData?.additionalDetails?.labourMaterialAnalysis?.material
    //   )
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.material;
    //   if (categories?.includes("MHA") && SORAmount == 0 && formData?.additionalDetails?.labourMaterialAnalysis?.machinery)
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.machinery;
    // }

    // if (SORAmount === 0) {
    //   if (categories.includes("LA") && formData?.additionalDetails?.labourMaterialAnalysis?.labour) {
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.labour;
    //   } else if (categories.some(cat => ChargesCodeMapping.MaterialCost.includes(cat)) && formData?.additionalDetails?.labourMaterialAnalysis?.material) {
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.material;
    //   } else if (categories.includes("MHA") && formData?.additionalDetails?.labourMaterialAnalysis?.machinery) {
    //     SORAmount = formData?.additionalDetails?.labourMaterialAnalysis?.machinery;
    //   }
    // }

    SORAmount = SORAmount ? SORAmount : 0;

    return Digit.Utils.dss.formatterWithoutRound(parseFloat(SORAmount).toFixed(2), "number", undefined, true, undefined, 2);
  };

  const requestSearchCriteria = {
    url: isEstimate ? "/statements/v1/analysis/_search" : "/statements/v1/utilization/_search",
    body: {
      searchCriteria: {
        tenantId: tenantId,
        referenceId: isEstimate ? formData?.SORtable?.[0]?.estimateId : formData?.Measurement?.id,
      },
    },
    config: {
      cacheTime: 0,
      enabled: formData?.SORtable?.[0]?.estimateId || formData?.Measurement?.id ? true : false,
    },
    changeQueryName: "analysisStatement",
  };

  const { data: searchResponse, isLoading: searchLoading } = Digit.Hooks.useCustomAPIHook(requestSearchCriteria);

  const checkMeasurement = () => {
    if (window.location.href.includes("measurement/update")) {
      return props?.data?.SORtable?.length > 0;
    } else {
      return formData?.SORtable?.length > 0;
    }
  };

  const callCreateApi = async (event) => {
    event.preventDefault();
    let payload = {
      statementRequest: {
        tenantId: tenantId,
        id: isEstimate
          ? formData?.SORtable?.[0]?.estimateId
          : window.location.href.includes("measurement/update")
          ? props.config.formData.Measurement.id
          : formData?.Measurement?.id,
      },
    };

    if (isEstimate) {
      await AnalysisMutation(payload, {
        onError: async (error) => {
          setTimeout(() => {
            history.push({
              pathname: `/${window?.contextPath}/employee/estimate/view-analysis-statement`,
              state: {
               
                estimateId: formData?.SORtable?.[0]?.estimateId,
                number: window.location.href.includes("revision") ? revisionNumber : formData?.estimateNumber?formData?.estimateNumber:estimateNumber,
                 downloadStatus: false,
                oldData: {
                  Labour: getAnalysisCost(ChargesCodeMapping.LabourCost),
                  Material: getAnalysisCost(ChargesCodeMapping.MaterialCost),
                  Machinery: getAnalysisCost(ChargesCodeMapping.MachineryCost),
                  LabourCessCost:getAnalysisCost(ChargesCodeMapping.LabourCessCost)
                },
              },
            });
          }, 1000);
        },
        onSuccess: async (responseData) => {
        
          setTimeout(() => {
            history.push({
              pathname: `/${window?.contextPath}/employee/estimate/view-analysis-statement`,
              state: {
                responseData: responseData,
                estimateId: formData?.SORtable?.[0]?.estimateId,
                number: window.location.href.includes("revision") ? revisionNumber : formData?.estimateNumber?formData?.estimateNumber:estimateNumber,
                 downloadStatus: true,
               
              },
            });
          }, 1000);
        },
      });
    } else {
      await UtilizationMutation(payload, {
        onError: async (error) => {
        
          setTimeout(() => {
            history.push({
              pathname: `/${window?.contextPath}/employee/measurement/utilizationstatement`,
              state: {
                
                estimateId: window.location.href.includes("measurement/update") ? props.config.formData.Measurement.id : formData?.Measurement?.id,
                number: window.location.href.includes("measurement/update")
                  ? props.config.formData.Measurement.measurementNumber
                  : formData?.Measurement?.measurementNumber,
                downloadStatus: false,
                oldData: {
                  Labour: getAnalysisCost(ChargesCodeMapping.LabourCost),
                  Material: getAnalysisCost(ChargesCodeMapping.MaterialCost),
                  Machinery: getAnalysisCost(ChargesCodeMapping.MachineryCost),
                   LabourCessCost:getAnalysisCost(ChargesCodeMapping.LabourCessCost)
                },
              },
            });
          }, 1000);
        },
        onSuccess: async (responseData) => {
          setTimeout(() => {
            history.push({
              pathname: `/${window?.contextPath}/employee/measurement/utilizationstatement`,
              state: {
                responseData: responseData,
                estimateId: window.location.href.includes("measurement/update") ? props.config.formData.Measurement.id : formData?.Measurement?.id,
                number: window.location.href.includes("measurement/update")
                  ? props.config.formData.Measurement.measurementNumber
                  : formData?.Measurement?.measurementNumber,
                downloadStatus: true,
              },
            });
          }, 1000);
        },
      });
    }
  };

  const handleNavigation = (isEstimate, isView, searchResponse, formData, props) => {
    const path = isEstimate
      ? `/${window?.contextPath}/employee/estimate/view-analysis-statement`
      : `/${window?.contextPath}/employee/measurement/utilizationstatement`;

    const estimateId = isEstimate
      ? formData?.SORtable?.[0]?.estimateId
      : window.location.href.includes("measurement/update")
      ? props.config.formData.Measurement.id
      : formData?.Measurement?.id;

    const number = isEstimate
      ? window.location.href.includes("revision")
        ? revisionNumber
        : formData?.estimateNumber?formData?.estimateNumber:estimateNumber
      : window.location.href.includes("measurement/update")
      ? props.config.formData.Measurement.measurementNumber
      : formData?.Measurement?.measurementNumber;

    const state = {
      responseData: searchResponse,
      estimateId,
      number,
      downloadStatus: true,
    };

    if (!searchResponse || searchResponse?.statement?.length <= 0) {
      state.oldData = {
        Labour: getAnalysisCost(ChargesCodeMapping.LabourCost),
        Material: getAnalysisCost(ChargesCodeMapping.MaterialCost),
        Machinery: getAnalysisCost(ChargesCodeMapping.MachineryCost),
         LabourCessCost:getAnalysisCost(ChargesCodeMapping.LabourCessCost)
      };

      state.downloadStatus = false;
    }

    history.push({ pathname: path, state });
  };

  const checkConditions = (isEstimate, formData, props) => {
    if (isEstimate) {
      return formData?.SORtable?.length > 0;
    } else {
      return checkMeasurement();
    }
  };

  const handleButtonClick = async (event) => {
    event.preventDefault();

    if (!checkConditions(isEstimate, formData, props)) {
      const message = isEstimate ? t("NO_ESTIMATE_SOR_FOUND") : t("NO_MEASUREMENT_SOR_FOUND");
      showToastMessage(message);
      return;
    }

    if (isView && searchResponse) {
      handleNavigation(isEstimate, isView, searchResponse, formData, props);
    } else {
      await callCreateApi(event);
    }
  };

  const showToastMessage = (message) => {
    setShowToast({ type: "warning", label: message });
    setTimeout(() => setShowToast(false), 5000);
  };

  // return (
  //   <div style={{ width: "100%", display: "flex", justifyContent: "center" }}>
  //     {searchLoading ? (
  //       <Loader />
  //     ) : (
  //       <button
  //         type="button"
  //         className="view-Analysis-button"
  //         style={isCreateOrUpdate ? { marginTop: "-3.5%", textAlign: "center", width: "17%" } : { textAlign: "center", width: "17%" }}
  //         onClick={handleButtonClick}
  //       >
  //         {isEstimate ? t("ESTIMATE_ANALYSIS_STM") : t("MB_UTILIZATION_STM")}
  //       </button>
  //     )}
  //     {showToast && (
  //       <Toast
  //         type={showToast?.type}
  //         label={t(showToast?.label)}
  //         isDleteBtn={true}
  //         onClose={() => setShowToast(null)}
  //       />
  //     )}
  //   </div>
  // );

  if (searchLoading) return <Loader />;

  if (!window.location.href.includes("create"))
    return (
      <div>
        {/* <LinkButton
          className="view-Analysis-button"
          style={isCreateOrUpdate ? { marginTop: "-3.5%", textAlign: "center", width: "282px" } : { textAlign: "center", width: "282px" }}
          onClick={handleButtonClick}
          label={isEstimate ? t("ESTIMATE_ANALYSIS_STM") : t("MB_UTILIZATION_STM")}
        /> */}
        {
          <Button
            label={isEstimate ? t("ESTIMATE_ANALYSIS_STM") : t("MB_UTILIZATION_STM")}
            onClick={handleButtonClick}
            variation={"secondary"}
            type="button"
          />
        }
        {showToast && (
          <Toast
            type={showToast?.type}
            label={t(showToast?.label)}
            isDleteBtn={true}
            // labelstyle={{ width: "100%" }}
            // style={{ width: "100%", display: "flex", justifyContent: "space-between" }}
            onClose={() => setShowToast(false)}
          />
        )}
      </div>
    );
  else return null;
};

export default ViewAnalysisStatement;
