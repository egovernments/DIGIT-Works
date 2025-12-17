import React, { useEffect, useState, useCallback } from "react";
import { useTranslation } from "react-i18next";
import EstimateDropdown from "./EstimateDropdown";
import SearchBar from "./SearchBar";
import { Toast,Button } from "@egovernments/digit-ui-components";


const fetchData = async (sorid, state, setState, setShowToast) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  if(sorid == null)
    {
      setShowToast({show: true, type: "error", label:"WORKS_CANNOT_ADD_EMPTY_DATA"});
      return true;
    }
  let currentDateInMillis = new Date().getTime(); 

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
                filter: `[?(@.sorId=='${sorid}')]`,
              },
            ],
          },
        ],
      },
    },
  };
  try {
    const data = await Digit.CustomService.getResponse(requestCriteria);
    if (data?.MdmsRes?.["WORKS-SOR"]?.Rates?.length > 0) {
      const Rates = data?.MdmsRes?.["WORKS-SOR"]?.Rates?.filter((rate) => {
        // Convert validFrom and validTo to milliseconds
        let validFromInMillis = new Date(parseInt(rate?.validFrom)).getTime();
        let validToInMillis = rate?.validTo ? new Date(parseInt(rate?.validTo)).getTime() : Infinity;
        // Check if the current date is within the valid date range
        return validFromInMillis <= currentDateInMillis
          && currentDateInMillis < validToInMillis;
      });
      if(Rates && Rates?.length <= 0)
      {
        setShowToast({show: true, type: "error", label:"WORKS_RATE_NOT_FOUND_ERROR"});
      }
      //if rates is not there then provide the error
      // state?.forEach((element) => {
      //   if (element?.sorId == sorid) {
      //     element.unitRate = Rates?.[0]?.rate || 0;
      //     element.amountDetails = Rates?.[0]?.amountDetails;
      //   }
      // });
      return Rates;
      //setState(state);
    }
    else
    {
      setShowToast({show: true, type:"error", label:"WORKS_RATE_NOT_FOUND_ERROR"});
    }
  } catch (error) {
    // Handle any errors here
    console.error(error);
  }
};

const searchSor = (props) => {
  const { t } = useTranslation();
  const [stateData, setStateData] = useState({SORType:"W"});
  const [selectedSOR, setSelectedSOR] = useState(null);
  const [showToast, setShowToast] = useState({show : false, label : "", type:""});
  const { register, setValue, watch } = props;
  let formData = watch("SOR");

  useEffect(() => {
    if(stateData?.SORType !== null)
    {
      setStateData({...stateData, SORSubType:null, SORVariant:null, selectedSor:null});
      setSelectedSOR(null);
    }
  },[stateData?.SORType]);

  useEffect(() => {
    if(stateData?.SORSubType !== null)
    {
      setStateData({...stateData, SORVariant:null, selectedSor:null});
      setSelectedSOR(null);
    }
  },[stateData?.SORSubType]);

  useEffect(() => {
    register("searchSor", stateData);
  }, []);
  useEffect(() => {
    setStateData({
      ...stateData,
      selectedSor: selectedSOR,
    });
    setValue("searchSor", stateData);
  }, [selectedSOR]);
  const setFormValue = useCallback(
    (value) => {
      setValue("SOR", value);
      setValue(`SORtable`, value);
    },
    [setValue]
  );
  const buttonClick = async () => {
    if (
      formData?.length > 0 &&
      formData?.find((ob) => ob?.sorCode && ob?.sorCode === stateData?.selectedSor?.id)
    ) {
      setShowToast({ show: true, type:"error", label: "WORKS_CANNOT_ADD_DUPLICATE_SOR" });
      return;
    }
    const sor = transformSOR(stateData?.selectedSor);
  
    try {
      const apiData = await fetchData(stateData?.selectedSor?.id, formData, setFormValue, setShowToast);
  
      // Check if rates are available
      if (apiData !== undefined && apiData?.[0]?.sorId === stateData?.selectedSor?.id && stateData?.selectedSor?.id) {
        // Add sor to formData only if rates are available
        if (formData?.length === 0 || (formData?.length === 1 && !formData?.[0]?.description) && stateData?.selectedSor?.id) {
          formData = [sor];
        } else {
          sor?.sorId && formData?.push(sor);
        }

      formData?.forEach((element) => {
        if (element?.sorId == stateData?.selectedSor?.id) {
          element.unitRate = apiData?.[0]?.rate || 0;
          element.amountDetails = apiData?.[0]?.amountDetails;
        }
      });
        setFormValue(formData);
        setStateData({...stateData, SORSubType:null, SORVariant:null, selectedSor:null});
      } else {
        // Rates are not available, handle it here (e.g., display an error message)
        console.error('Rates not available in fetchData response');
      }
    } catch (error) {
      // Handle the error from the API call
      console.error('Error fetching data:', error);
    }
  
    setSelectedSOR(null);
  };

  const transformSOR = (sor) => {
    const transformedSOR = {
      sNo: 1,
      description: sor?.description,
      uom: sor?.uom,
      category: "SOR",
      approvedQuantity: sor?.quantity,
      consumedQ: 0,
      sorType: sor?.sorType,
      sorSubType : sor?.sorSubType,
      sorCode : sor?.id,
      currentMBEntry: 0,
      amount: 0,
      measures: [],
      targetId: null,
      sorId: sor?.id,
    };
    return transformedSOR;
  };
  return (
    <div>
      <EstimateDropdown label={t("ESTIMATE_SOR_TYPE")} stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.Type"} type="SORType" />
      <EstimateDropdown label={t("ESTIMATE_SOR_SUB_TYPE")} stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.SubType"} type="SORSubType" />
      <EstimateDropdown label={t("ESTIMATE_SOR_VARIANT")} stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.Variant"} type="SORVariant" />
      <div className="search-sor-container">
      <span className="search-sor-label">{t("ESTIMATE_SEARCH_SOR_LABEL")}</span>
      <div className="search-sor-button"> 
      <SearchBar stateData={stateData} selectedSOR={selectedSOR} setSelectedSOR={setSelectedSOR} />
      <Button
        label={t("ESTIMATE_ADD_LABEL")}
        onClick={buttonClick}
        className={"add-sor-button"}
        style={{marginTop:"24px"}}
      />
      </div>
      </div>
      {showToast?.show && (
      <Toast  labelstyle={{width:"100%"}} type={showToast?.type} label={t(showToast?.label)} isDleteBtn={true} onClose={() => setShowToast({show : false, label : "", type : ""})} />
      )}
    </div>
  );
};

export default searchSor;
