import { Button, Toast } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState, useCallback } from "react";
import { useTranslation } from "react-i18next";
import EstimateDropdown from "./EstimateDropdown";
import SearchBar from "./SearchBar";

const fetchData = async (sorid, state, setState, setShowToast) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  if(sorid == null)
  {
    setShowToast({show: true, error: true, label:"WORKS_CANNOT_ADD_EMPTY_DATA"});
    return true;
  }
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
      const Rates = data?.MdmsRes?.["WORKS-SOR"]?.Rates;
      //if rates is not there then provide the error
      state?.forEach((element) => {
        if (element?.sorId == sorid) {
          element.unitRate = Rates?.[0]?.rate || 0;
          element.amountDetails = Rates?.[0]?.amountDetails;
        }
      });
      return Rates;
      //setState(state);
    }
    else
    {
      setShowToast({show: true, error: true, label:"WORKS_RATE_NOT_FOUND_ERROR"});
    }
  } catch (error) {
    // Handle any errors here
    console.error(error);
  }
};

const searchSor = (props) => {
  const { t } = useTranslation();
  const [stateData, setStateData] = useState({});
  const [selectedSOR, setSelectedSOR] = useState(null);
  const [showToast, setShowToast] = useState({show : false, label : "", error : false});
  const { register, setValue, watch } = props;
  let formData = watch("SOR");
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
  // const buttonClick = async () => {
  //   if(formData?.length > 0 && formData?.find((ob) => ob?.sorCode && ob?.sorCode === stateData?.selectedSor?.id))
  //   {
  //     setShowToast({show: true, error: true, label:"WORKS_CANNOT_ADD_DUPLICATE_SOR"});
  //     return;
  //   }
  //   const sor = transformSOR(stateData?.selectedSor);
  //   console.log(sor,"sor")
  //   console.log(formData,"formData");
  //   console.log(stateData,"statedata");
  //   if (formData?.length === 0 || (formData?.length === 1 && !formData?.[0]?.description) && stateData?.selectedSor?.id) {
  //     formData = [sor];
  //   } else {
  //     sor?.sorId && formData?.push(sor);
  //   }
  
  //   try {
  //     const apiData = await fetchData(stateData?.selectedSor?.id, formData, setFormValue,setShowToast);
  //     console.log(apiData,"apidata")
  //     // Check if rates are available
  //     if (apiData !== undefined && apiData?.[0]?.sorId === stateData?.selectedSor?.id && stateData?.selectedSor?.id) {
  //       console.log("going inside the condition");
  //       setFormValue(formData);
  //     } else {
  //       // Rates are not available, handle it here (e.g., display an error message)
  //       console.error('Rates not available in fetchData response');
  //     }
  //   } catch (error) {
  //     // Handle the error from the API call
  //     console.error('Error fetching data:', error);
  //   }
  
  //   setSelectedSOR(null);
  // };

  const buttonClick = async () => {
    if (
      formData?.length > 0 &&
      formData?.find((ob) => ob?.sorCode && ob?.sorCode === stateData?.selectedSor?.id)
    ) {
      setShowToast({ show: true, error: true, label: "WORKS_CANNOT_ADD_DUPLICATE_SOR" });
      return;
    }
  
    const sor = transformSOR(stateData?.selectedSor);
    console.log(sor, "sor");
    console.log(formData, "formData");
    console.log(stateData, "statedata");
  
    try {
      const apiData = await fetchData(stateData?.selectedSor?.id, formData, setFormValue, setShowToast);
      console.log(apiData, "apidata");
  
      // Check if rates are available
      if (apiData !== undefined && apiData?.[0]?.sorId === stateData?.selectedSor?.id && stateData?.selectedSor?.id) {
        console.log("Rates are available for the selected SOR");
        // Add sor to formData only if rates are available
        if (formData?.length === 0 || (formData?.length === 1 && !formData?.[0]?.description) && stateData?.selectedSor?.id) {
          formData = [sor];
        } else {
          sor?.sorId && formData?.push(sor);
        }
        setFormValue(formData);
      } else {
        // Rates are not available, handle it here (e.g., display an error message)
        console.log(formData,"formdata final")
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
      <EstimateDropdown label="SOR Type*" stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.Type"} type="SORType" />
      <EstimateDropdown label="SOR Sub Type" stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.SubType"} type="SORSubType" />
      <EstimateDropdown label="SOR Variant" stateData={stateData} setStateData={setStateData} schemaCode={"WORKS-SOR.Variant"} type="SORVariant" />
      <div className="search-sor-container">
      <span className="search-sor-label">Search SOR</span>
      <div className="search-sor-button"> 
      <SearchBar stateData={stateData} selectedSOR={selectedSOR} setSelectedSOR={setSelectedSOR} />
      <Button
        label="Add"
        onButtonClick={buttonClick}
        className={"add-sor-button"}
      />
      </div>
      </div>
      {showToast?.show && (
      <Toast  labelstyle={{width:"100%"}} error={showToast?.error} label={t(showToast?.label)} isDleteBtn={true} onClose={() => setShowToast({show : false, label : "", error : false})} />
      )}
    </div>
  );
};

export default searchSor;
