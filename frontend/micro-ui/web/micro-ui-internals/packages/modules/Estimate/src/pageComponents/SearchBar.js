import { TextInput } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";

const SearchBar = (props) => {
  const { t } = useTranslation();
  const { selectedSOR, setSelectedSOR, stateData } = props;
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [inputValue, setInputValue] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const fetchData = async (searchText) => {
    const subtypeCondition = (stateData?.SORSubType && `&& @.sorSubType == '${stateData?.SORSubType}'`) || "";
    const variantCondition = (stateData?.SORVariant && `&& @.sorVariant == '${stateData?.SORVariant}'`) || "";
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
                  name: "SOR",
                  filter: `$[?((@.description=~/.*${searchText}.*/i || @.id=~/.*${searchText}.*/i )&& @.sorType == '${stateData?.SORType}' ${subtypeCondition}  ${variantCondition})]`,
                },
              ],
            },
          ],
        },
      },
    };
    try {
      console.log("selectedSOR", selectedSOR);
      const data = stateData?.SORType && (await Digit.CustomService.getResponse(requestCriteria));
      if (data?.MdmsRes?.["WORKS-SOR"]?.SOR?.length > 0) {
        setSuggestions(data?.MdmsRes?.["WORKS-SOR"]?.SOR);
      } else {
        setSuggestions([{ description: "No Matching SORs" }]);
      }
    } catch (error) {
      // Handle any errors here
      console.error(error);
    }
  };

  useEffect(() => {
    if (inputValue.length > 2 && selectedSOR == null) {
      const timer = setTimeout(() => {
        fetchData(inputValue);
      }, 750);
      return () => {
        clearTimeout(timer);
      };
    } else {
      setSuggestions([]);
    }
  }, [inputValue]);

  useEffect(() => {
    selectedSOR == null && setInputValue("");
  }, [selectedSOR]);

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
    if(e.target.value === "")
      setSelectedSOR(null);
  };

  const handleSelectOption = (option) => {
    if (option?.id) {
      setInputValue(option.description);
      setSelectedSOR(option);
      setSuggestions([]);
    }
  };

  return (
    <div className={"search-bar-sor"}>
      <TextInput type="text" name={"Search"} placeholder="Type any SOR description..." value={inputValue} onChange={handleInputChange} customClass="search-sor-input"/>
      {suggestions?.length > 0 && (
      <ul
        className="suggestions-sor" style={{zIndex:"100"}}
      >
        {suggestions.map((option) => (
          <li
            key={option.id}
            onClick={() => handleSelectOption(option)}
          >
            {option.description}
          </li>
        ))}
      </ul>
      )}
    </div>
  );
};

export default SearchBar;
