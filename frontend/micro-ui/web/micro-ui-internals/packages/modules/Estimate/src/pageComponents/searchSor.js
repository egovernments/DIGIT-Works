import { Button } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState, useCallback } from "react";
import { useTranslation } from "react-i18next";
import EstimateDropdown from "./EstimateDropdown";
import SearchBar from "./SearchBar";

const fetchData = async (sorid, state, setState) => {
  const tenantId = Digit.ULBService.getCurrentTenantId();

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
      state?.forEach((element) => {
        if (element?.sorId == sorid) {
          element.unitRate = Rates?.[0]?.rate || 0;
        }
      });
      setState(state);
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
  const buttonClick = () => {
    const sor = transformSOR(stateData?.selectedSor);
    if (formData?.length == 0 || (formData?.length == 1 && !formData?.[0]?.description)) {
      formData = [sor];
    } else {
      formData?.push(sor);
    }
    fetchData(stateData?.selectedSor?.id, formData, setFormValue);
    setFormValue(formData);
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
    </div>
  );
};

export default searchSor;
