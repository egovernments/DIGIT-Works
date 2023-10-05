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
      if (data?.mdmsRes?.["WORKS-SOR"]?.SOR?.length > 0) {
        setSuggestions(data?.mdmsRes?.["WORKS-SOR"]?.SOR);
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
  };

  const handleSelectOption = (option) => {
    if (option?.id) {
      setInputValue(option.description);
      setSelectedSOR(option);
      setSuggestions([]);
    }
  };

  return (
    <div className={"search-bar-sor"} style={{ position: "relative", width: "300px", margin: "20px" }}>
      <TextInput type="text" name={"Search"} placeholder="Type any SOR description..." value={inputValue} onChange={handleInputChange} />
      {suggestions?.length > 0 && (
        <ul
          className="suggestions-sor"
          style={{
            listStyle: "none",
            padding: 0,
            margin: 0,
            position: "absolute",
            width: "100%",
            backgroundColor: "#fff",
            border: "1px solid #ccc",
            borderRadius: "0 0 4px 4px",
            boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)",
          }}
        >
          {suggestions.map((option) => (
            <li
              key={option.id}
              onClick={() => handleSelectOption(option)}
              style={{ padding: "10px", cursor: "pointer", transition: "background-color 0.2s ease-in-out" }}
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
